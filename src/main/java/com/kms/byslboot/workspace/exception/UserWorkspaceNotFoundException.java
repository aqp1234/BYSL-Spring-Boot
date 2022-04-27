package com.kms.byslboot.workspace.exception;

public class UserWorkspaceNotFoundException extends RuntimeException{
	
	public UserWorkspaceNotFoundException() {
	}
	
	public UserWorkspaceNotFoundException(String message) {
		super(message);
	}
	
	public UserWorkspaceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
