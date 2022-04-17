package com.kms.byslboot.member.service;

import com.kms.byslboot.member.entity.Member;

public interface LoginService {
	public void login(int memberId);
	public void logout();
	public Integer getLoginMemberID();
	public Member getLoginMember();
}
