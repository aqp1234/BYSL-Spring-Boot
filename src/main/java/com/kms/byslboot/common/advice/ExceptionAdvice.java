package com.kms.byslboot.common.advice;

import static com.kms.byslboot.common.ResponseEntityHttpStatus.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kms.byslboot.member.exception.DuplicatedKeyException;
import com.kms.byslboot.member.exception.MemberNotFoundException;
import com.kms.byslboot.member.exception.UnAuthenticatedException;
import com.kms.byslboot.workspace.exception.WorkspaceNotFoundException;

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
		return new ResponseEntity<>(getErrorMessage(e.getBindingResult().getFieldErrors()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(WorkspaceNotFoundException.class)
	public ResponseEntity<HttpStatus> workspaceNotFoundException(){
		return RESPONSE_NOT_FOUND;
	}
	
	private String getErrorMessage(List<FieldError> errors) {
		String errorString = "";
		for(FieldError error : errors) {
			errorString += "[";
			errorString += error.getField();
			errorString += "](은)는 ";
			errorString += error.getDefaultMessage();
			errorString += " 입력된 값: [";
			errorString += error.getRejectedValue() == null ? "없음" : error.getRejectedValue();
			errorString += "]";
		}
		return errorString;
	}
}
