package com.kms.byslboot.member.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.byslboot.member.dto.MemberDTO;
import com.kms.byslboot.member.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService{

	@Value("${api.NICE_KEY}")
	private String NICE_KEY;
	
	private MemberMapper memberMapper;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public MemberServiceImpl(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
		this.memberMapper = memberMapper;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public List<MemberDTO> findAll() {
		List<MemberDTO> members;
		
		members = memberMapper.findAll();
		
		return members;
	}

	@Override
	public int insertMember(MemberDTO member) {
		RestTemplate restTemplate = new RestTemplate();
		final String url= "https://open.neis.go.kr/hub/schoolInfo?KEY=" + NICE_KEY + 
				"&Type=json&pindex=1&pSize=100&SCHUL_NM=" + member.getSchoolName() + 
				"&LCTN_SC_NM=" + member.getLocationName();
		
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
			member.setLocationCode(location_code);
			member.setSchoolCode(school_code);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		
		memberMapper.insertMember(member);
		
		return member.getId();
	}
}
