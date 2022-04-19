package com.kms.byslboot.member.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.byslboot.member.dto.LoginDTO;
import com.kms.byslboot.member.dto.MemberDTO;
import com.kms.byslboot.member.entity.Member;
import com.kms.byslboot.member.exception.DuplicatedKeyException;
import com.kms.byslboot.member.exception.MemberNotFoundException;
import com.kms.byslboot.member.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeneralMemberService implements MemberService{

	@Value("${api.NICE_KEY}")
	private String NICE_KEY;
	
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public List<Member> findAll() {
		List<Member> members;
		
		members = memberMapper.findAll();
		
		return members;
	}

	@Override
	public int insertMember(MemberDTO memberDTO) {
		RestTemplate restTemplate = new RestTemplate();
		final String url= "https://open.neis.go.kr/hub/schoolInfo?KEY=" + NICE_KEY + 
				"&Type=json&pindex=1&pSize=100&SCHUL_NM=" + memberDTO.getSchoolName() + 
				"&LCTN_SC_NM=" + memberDTO.getLocationName();
		
		Member member = MemberDTO.toEntity(memberDTO, passwordEncoder);
		
		HttpEntity<String> response = restTemplate.getForEntity(url, String.class);
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = null;
		try {
			jsonNode = objectMapper.readTree(response.getBody());
			JsonNode schoolInformation = jsonNode.get("schoolInfo").get(1).get("row").get(0);
			String location_code = schoolInformation.get("ATPT_OFCDC_SC_CODE").toString();
			String school_code = schoolInformation.get("SD_SCHUL_CODE").toString();
			location_code = location_code.substring(1, location_code.length() - 1);
			school_code = school_code.substring(1, school_code.length() - 1);
			member.setSchoolLocationCode(school_code, location_code);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		int memberId = memberMapper.insertMember(member);
		
		return memberId;
	}

	@Override
	public Member findMemberById(int memberId) {
		Optional<Member> member = memberMapper.findMemberById(memberId);
		return memberMapper.findMemberById(memberId).orElseThrow(MemberNotFoundException::new);
	}

	@Override
	public Member findMemberByEmail(String email) {
		return memberMapper.findMemberByEmail(email).orElseThrow(MemberNotFoundException::new);
	}

	@Override
	public void existsByEmail(String email) {
		if(memberMapper.existsByEmail(email) == true) {
			throw new DuplicatedKeyException("이미 가입된 이메일입니다.");
		}
	}

	@Override
	public void existsByPhone(String phone) {
		if(memberMapper.existsByPhone(phone) == true) {
			throw new DuplicatedKeyException("이미 가입된 핸드폰 번호입니다.");
		}
	}

	@Override
	public boolean checkPassword(LoginDTO login) {
		Member member = memberMapper.findMemberByEmail(login.getEmail()).orElseThrow(MemberNotFoundException::new);
		
		if(passwordEncoder.matches(login.getPassword(), member.getPassword())) {
			return true;
		}
		return false;
	}
}
