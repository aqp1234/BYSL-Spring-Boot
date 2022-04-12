package com.kms.byslboot.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kms.byslboot.member.dto.MemberDTO;
import com.kms.byslboot.member.mapper.MemberMapper;

@RestController
public class MemberController {
	
	@Autowired
	private MemberMapper memberMapper;
	
	@GetMapping("/")
	public List<MemberDTO> test(){
		List<MemberDTO> members = memberMapper.findAll(41);
		System.out.println(members.get(0).getName());
		System.out.println(members.get(0).getSchoolName());
		return members;
	}
}
