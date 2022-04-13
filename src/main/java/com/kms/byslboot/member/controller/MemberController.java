package com.kms.byslboot.member.controller;

import static com.kms.byslboot.common.ResponseEntityHttpStatus.*;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kms.byslboot.member.dto.MemberDTO;
import com.kms.byslboot.member.service.MemberService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
	
	private MemberService memberService;
	
	@GetMapping
	public List<MemberDTO> test(){
		List<MemberDTO> members = memberService.findAll();
		return members;
	}
	
	@PostMapping
	public ResponseEntity<HttpStatus> insertMember(@RequestBody @Valid MemberDTO member){
		memberService.insertMember(member);
		
		return RESPONSE_CREATED;
	}
}
