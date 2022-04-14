package com.kms.byslboot.member.service;

import java.util.List;
import java.util.Optional;

import com.kms.byslboot.member.dto.MemberDTO;

public interface MemberService {
	public List<MemberDTO> findAll();
	public int insertMember(MemberDTO member);
	public MemberDTO findMemberById(int memberId);
	public void existsByEmail(String email);
	public void existsByPhone(String phone);
}
