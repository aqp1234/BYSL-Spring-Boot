package com.kms.byslboot.common.advice;

import static com.kms.byslboot.common.ResponseEntityHttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kms.byslboot.member.exception.DuplicatedKeyException;
import com.kms.byslboot.member.exception.MemberNotFoundException;
import com.kms.byslboot.member.exception.UnAuthenticatedException;

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
	
	@ExceptionHandler(UnAuthenticatedException.class)
	public ResponseEntity<HttpStatus> unAuthenticatedException(){
		return RESPONSE_UNAUTHORIZED;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> notValidException(MethodArgumentNotValidException e){
		String errors = "";
		
		for(FieldError error: e.getBindingResult().getFieldErrors()) {
			errors += "[";
			errors += error.getField();
			errors += "](은)는 ";
			errors += error.getDefaultMessage();
			errors += " 입력된 값: [";
			errors += error.getRejectedValue() == null ? "없음" : error.getRejectedValue();
			errors += "]";
		}
		System.out.println("테스트");
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}
