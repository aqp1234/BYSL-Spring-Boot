package com.kms.byslboot.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class LoginDTO {

	@NotEmpty
	@Size(max=100, message = "이메일은 최대 100글자만 가능합니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.",
    	regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
	private String email;
	
	@NotEmpty(message = "비밀번호는 필수 값입니다.")
	private String password;
}
