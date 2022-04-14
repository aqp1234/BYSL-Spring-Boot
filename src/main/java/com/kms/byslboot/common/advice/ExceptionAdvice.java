package com.kms.byslboot.common.advice;

import static com.kms.byslboot.common.ResponseEntityHttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kms.byslboot.member.exception.DuplicatedKeyException;
import com.kms.byslboot.member.exception.MemberNotFoundException;

@RestControllerAdvice
public class ExceptionAdvice {
	
	@ExceptionHandler(MemberNotFoundException.class)
	public ResponseEntity<HttpStatus> memberNotFoundException(){
		return RESPONSE_NOT_FOUND;
	}
	
	@ExceptionHandler(DuplicatedKeyException.class)
	public ResponseEntity<HttpStatus> duplicatedKeyException(){
		return RESPONSE_CONFLICT;
	}
}
