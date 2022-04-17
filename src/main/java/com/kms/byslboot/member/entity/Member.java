package com.kms.byslboot.member.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	
	private int id;
	private String email;
	private String name;
	private String password;
	private String phone;
	private String schoolCode;
	private String schoolName;
	private String locationCode;
	private String locationName;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;
	
	public void setSchoolLocationCode(String schoolCode, String locationCode) {
		this.schoolCode = schoolCode;
		this.locationCode = locationCode;
	}
}
