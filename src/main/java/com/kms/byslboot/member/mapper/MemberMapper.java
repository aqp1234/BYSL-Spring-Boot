package com.kms.byslboot.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kms.byslboot.member.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	List<MemberDTO> findAll();
	int insertMember(MemberDTO member);
}
