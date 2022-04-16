package com.kms.byslboot.member.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kms.byslboot.member.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	List<MemberDTO> findAll();
	int insertMember(MemberDTO member);
	Optional<MemberDTO> findMemberById(int memberId);
	Optional<MemberDTO> findMemberByEmail(String email);
	boolean existsByEmail(String email);
	boolean existsByPhone(String phone);
}
