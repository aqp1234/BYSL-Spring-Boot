package com.kms.byslboot.common.advice;

import static com.kms.byslboot.common.ResponseEntityHttpStatus.RESPONSE_BAD_REQUEST;
import static com.kms.byslboot.common.ResponseEntityHttpStatus.RESPONSE_CONFLICT;
import static com.kms.byslboot.common.ResponseEntityHttpStatus.RESPONSE_NOT_FOUND;
import static com.kms.byslboot.common.ResponseEntityHttpStatus.RESPONSE_UNAUTHORIZED;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kms.byslboot.calendar.exception.CalendarNotFoundException;
import com.kms.byslboot.conference.exception.ConferenceNotFoundException;
import com.kms.byslboot.dashboard.exception.DashboardNotFoundException;
import com.kms.byslboot.member.exception.DuplicatedKeyException;
import com.kms.byslboot.member.exception.MemberNotFoundException;
import com.kms.byslboot.member.exception.UnAuthenticatedException;
import com.kms.byslboot.workspace.exception.PermissionNotFoundException;
import com.kms.byslboot.workspace.exception.TeamNotFoundException;
import com.kms.byslboot.workspace.exception.UserWorkspaceNotFoundException;
import com.kms.byslboot.workspace.exception.WorkspaceNotFoundException;

@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler({MemberNotFoundException.class, WorkspaceNotFoundException.class, 
		UserWorkspaceNotFoundException.class, TeamNotFoundException.class, CalendarNotFoundException.class
		, ConferenceNotFoundException.class, DashboardNotFoundException.class})
	public ResponseEntity<HttpStatus> notFoundException(){
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
	
	@ExceptionHandler(PermissionNotFoundException.class)
	public ResponseEntity<HttpStatus> badRequestException(){
		return RESPONSE_BAD_REQUEST;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> notValidException(MethodArgumentNotValidException e){
		String errorString = "";
		List<ObjectError> errors = e.getAllErrors();
		
		for(ObjectError error : errors) {
			errorString += error.getDefaultMessage();
			errorString += "\n";
		}
		return new ResponseEntity<>(errorString, HttpStatus.BAD_REQUEST);
	}
	
//	private String getErrorMessage(List<FieldError> errors) {
//		for(FieldError error : errors) {
//			System.out.println(error.getClass());
//			System.out.println(error.isBindingFailure());
//			System.out.println(error.getClass().equals(SpringValidatorAdapter.class));
//			errorString += "[";
//			errorString += error.getField();
//			errorString += "](은)는 ";
//			errorString += error.getDefaultMessage();
//			errorString += " 입력된 값: [";
//			errorString += error.getRejectedValue() == "" ? "없음" : error.getRejectedValue();
//			errorString += "]";
//		}
//		return errorString;
//	}
}
