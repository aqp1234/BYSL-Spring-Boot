package com.kms.byslboot.member.service;

import com.kms.byslboot.member.dto.MemberDTO;

public interface LoginService {
	public void login(int memberId);
	public void logout();
	public Integer getLoginMemberID();
	public MemberDTO getLoginMember();
}
