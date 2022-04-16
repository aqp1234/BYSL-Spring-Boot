package com.kms.byslboot.member.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.kms.byslboot.member.dto.MemberDTO;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

	private final HttpSession session;
	private final MemberService memberService;
	public static final String MEMBER_ID = "MEMBER_ID";
	
	@Override
	public void login(int memberId) {
		session.setAttribute(MEMBER_ID, memberId);
	}

	@Override
	public void logout() {
		session.removeAttribute(MEMBER_ID);
	}

	@Override
	public Integer getLoginMemberID() {
		return (Integer) session.getAttribute(MEMBER_ID);
	}

	@Override
	public MemberDTO getLoginMember() {
		return memberService.findMemberById((int) session.getAttribute(MEMBER_ID));
	}

}
