package com.kms.byslboot.member.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.kms.byslboot.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	
	@NotEmpty
	@Size(max=100, message = "이메일은 최대 100글자만 가능합니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.",
            regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
	private String email;
	
	@Size(max=20, message = "이름은 20글자를 넘어갈 수 없습니다.")
	@NotEmpty(message = "이름은 필수 값입니다.")
	private String name;
	
	@NotEmpty(message = "비밀번호는 필수 값입니다.")
	private String password;
	
	@NotEmpty(message = "핸드폰번호는 필수 값입니다.")
	private String phone;

	@NotEmpty(message = "학교는 필수 값입니다.")
	private String schoolName;
	
	private String locationName;
	
	public static Member toEntity(MemberDTO memberDTO, PasswordEncoder passwordEncoder) {
		return Member.builder()
				.email(memberDTO.getEmail())
				.name(memberDTO.getName())
				.password(passwordEncoder.encode(memberDTO.getPassword()))
				.phone(memberDTO.getPhone())
				.schoolName(memberDTO.getSchoolName())
				.locationName(memberDTO.getLocationName())
				.build();
	}
}
