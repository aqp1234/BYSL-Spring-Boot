package com.kms.byslboot.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	MockMvc mvc;
	
	@BeforeEach()
	public void setup() { 
		this.mvc = MockMvcBuilders.webAppContextSetup(wac)
				.addFilters(new CharacterEncodingFilter("UTF-8", true)) 
				.alwaysDo(print()) 
				.build(); 
	}
	
	@Test
	void getAllTset() throws Exception {
		mvc.perform(get("/"))
			.andExpect(status().isOk())
			.andDo(print());
	}
}
