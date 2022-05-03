package com.kms.byslboot.share.controller;

import static com.kms.byslboot.common.ResponseEntityHttpStatus.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kms.byslboot.common.annotation.LoginRequired;
import com.kms.byslboot.share.dto.ShareRequest;
import com.kms.byslboot.share.dto.ShareResponse;
import com.kms.byslboot.share.service.ShareService;

import lombok.RequiredArgsConstructor;

@LoginRequired
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/share/{workspaceId}")
public class ShareController {
	
	private final ShareService shareService;
	
	@GetMapping
	public ResponseEntity<List<ShareResponse>> findShareByWorkspaceId(@PathVariable int workspaceId){
		List<ShareResponse> shares = shareService.findShareByWorkspaceId(workspaceId);
		return ResponseEntity.ok(shares);
	}
	
	@PostMapping
	public ResponseEntity<ShareResponse> insertShare(@RequestBody @Valid ShareRequest shareRequest, int workspaceId) throws URISyntaxException{
		int shareId = shareService.insertShare(shareRequest, workspaceId);
		ShareResponse shareResponse = shareService.findShareById(shareId);
		return ResponseEntity.created(new URI("/share/" + workspaceId + "/" + shareId))
				.header("Content-Location", "/api/share/" + workspaceId + "/" + shareId)
				.body(shareResponse);
	}
	
	@GetMapping("/{shareId}")
	public ResponseEntity<ShareResponse> findShareById(@PathVariable int shareId){
		return ResponseEntity.ok(shareService.findShareById(shareId));
	}

	@PostMapping("/{shareId}")
	public ResponseEntity<HttpStatus> updateShare(@RequestBody @Valid ShareRequest shareRequest, @PathVariable int workspaceId
			, @PathVariable int shareId){
		shareService.updateShare(shareRequest, shareId, workspaceId);
		return RESPONSE_OK;
	}
	
	@DeleteMapping("/{shareId}")
	public ResponseEntity<HttpStatus> deleteShare(@PathVariable int shareId){
		shareService.deleteShareById(shareId);
		return RESPONSE_OK;
	}
}
