package com.kms.byslboot.fixture;

import java.sql.Date;
import java.sql.Timestamp;

import com.kms.byslboot.member.entity.Member;

public class MemberFixture {
	
	public static final Member MEMBER1 = Member.builder()
												.id(0)
												.email("test@naver.com")
												.password("password")
												.name("김민석")
												.phone("01011111111")
												.schoolCode("7010154")
												.schoolName("동북고등학교")
												.locationCode("B10")
												.locationName("서울특별시")
												.createdAt(new Timestamp(System.currentTimeMillis()))
												.updatedAt(new Timestamp(System.currentTimeMillis()))
												.deletedAt(null)
												.build();
}
