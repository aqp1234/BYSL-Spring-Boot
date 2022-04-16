package com.kms.byslboot.member.service;

import java.util.List;

import com.kms.byslboot.member.dto.LoginDTO;
import com.kms.byslboot.member.dto.MemberDTO;

public interface MemberService {
	public List<MemberDTO> findAll();
	public int insertMember(MemberDTO member);
	public MemberDTO findMemberById(int memberId);
	public MemberDTO findMemberByEmail(String email);
	public void existsByEmail(String email);
	public void existsByPhone(String phone);
	public boolean checkPassword(LoginDTO login);
}
