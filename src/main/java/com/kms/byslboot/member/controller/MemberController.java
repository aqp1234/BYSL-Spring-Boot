package com.kms.byslboot.member.controller;

import static com.kms.byslboot.common.ResponseEntityHttpStatus.RESPONSE_BAD_REQUEST;
import static com.kms.byslboot.common.ResponseEntityHttpStatus.RESPONSE_OK;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kms.byslboot.common.annotation.LoginRequired;
import com.kms.byslboot.member.dto.LoginDTO;
import com.kms.byslboot.member.dto.MemberDTO;
import com.kms.byslboot.member.entity.Member;
import com.kms.byslboot.member.service.LoginService;
import com.kms.byslboot.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
	
	private final MemberService memberService;
	private final LoginService loginService;
	
	@GetMapping
	public List<Member> test(){
		List<Member> members = memberService.findAll();
		return members;
	}
	
	@PostMapping("/checkEmail")
	public ResponseEntity<HttpStatus> checkDuplicatedByEmail(@RequestBody String email){
		memberService.existsByEmail(email);
		return RESPONSE_OK;
	}

	@PostMapping("/checkPhone")
	public ResponseEntity<HttpStatus> checkDuplicatedByPhone(@RequestBody String phone){
		memberService.existsByPhone(phone);
		return RESPONSE_OK;
	}
	
	@PostMapping
	public ResponseEntity<Member> insertMember(@RequestBody @Valid MemberDTO memberDTO) throws URISyntaxException{
		int memberId;
		
		// 이메일, 핸드폰 중복 가입을 확인하는 함수는 있지만 API로 호출하는 경우가 있을 수 있어 재확인
		memberService.existsByEmail(memberDTO.getEmail());
		memberService.existsByPhone(memberDTO.getPhone());
		
		memberId = memberService.insertMember(memberDTO);
		
		Member member = memberService.findMemberById(memberId);
		
		return ResponseEntity.created(new URI("/")).header("Content-Location", "/api/member/" + memberId).body(member);
	}
	
	@GetMapping("/{memberId}")
	public ResponseEntity<Member> findMemberById(@PathVariable int memberId){
		return ResponseEntity.ok(memberService.findMemberById(memberId));
	}
	
	@PostMapping("/login")
	public ResponseEntity<HttpStatus> login(@RequestBody @Valid LoginDTO login){
		boolean isValidLogin = memberService.checkPassword(login);
		if(isValidLogin) {
			loginService.login(memberService.findMemberByEmail(login.getEmail()).getId());
			return RESPONSE_OK;
		}
		return RESPONSE_BAD_REQUEST;
	}
	
	@LoginRequired
	@GetMapping("/logout")
	public ResponseEntity<HttpStatus> logout(){
		loginService.logout();
		return RESPONSE_OK;
	}
}
