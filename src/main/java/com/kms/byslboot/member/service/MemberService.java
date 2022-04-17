package com.kms.byslboot.member.service;

import java.util.List;

import com.kms.byslboot.member.dto.LoginDTO;
import com.kms.byslboot.member.dto.MemberDTO;
import com.kms.byslboot.member.entity.Member;

public interface MemberService {
	public List<Member> findAll();
	public int insertMember(MemberDTO member);
	public Member findMemberById(int memberId);
	public Member findMemberByEmail(String email);
	public void existsByEmail(String email);
	public void existsByPhone(String phone);
	public boolean checkPassword(LoginDTO login);
}
