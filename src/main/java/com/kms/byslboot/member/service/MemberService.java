package com.kms.byslboot.member.service;

import java.util.List;

import com.kms.byslboot.member.dto.MemberDTO;

public interface MemberService {
	public List<MemberDTO> findAll();
	public int insertMember(MemberDTO member);
}
