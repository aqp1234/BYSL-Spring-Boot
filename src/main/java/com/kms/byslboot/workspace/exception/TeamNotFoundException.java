package com.kms.byslboot.workspace.exception;

public class TeamNotFoundException extends RuntimeException{
	
	public TeamNotFoundException() {
	}
	
	public TeamNotFoundException(String message) {
		super(message);
	}
	
	public TeamNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
