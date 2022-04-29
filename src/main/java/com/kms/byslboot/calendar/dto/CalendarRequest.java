package com.kms.byslboot.calendar.dto;

import java.sql.Timestamp;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.kms.byslboot.common.annotation.DateValid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DateValid
public class CalendarRequest {
	
	@NotEmpty(message = "제목은 빈 값일 수 없습니다.")
	@Size(max = 50, message = "제목은 50 글자를 넘을 수 없습니다.")
	private String subject;
	
	@Size(max = 100, message = "내용은 100 글자를 넘을 수 없습니다.")
	private String content;
	
	private String startDate;
	private String endDate;
	
}
