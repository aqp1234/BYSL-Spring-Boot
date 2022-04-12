package com.kms.byslboot.member.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	private int id;
	private String email;
	private String name;
	private String password;
	private String phone;
	private String schoolCode;
	private String schoolName;
	private String locationCode;
	private String locationName;
	private Date createdAt;
	private Date updatedAt;
	private Date deletedAt;
}
