package com.kms.byslboot.member.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.kms.byslboot.member.dto.MemberDTO;
import com.kms.byslboot.member.entity.Member;

@Mapper
public interface MemberMapper {
	List<Member> findAll();
	int insertMember(Member member);
	Optional<Member> findMemberById(int memberId);
	Optional<Member> findMemberByEmail(String email);
	boolean existsByEmail(String email);
	boolean existsByPhone(String phone);
}
