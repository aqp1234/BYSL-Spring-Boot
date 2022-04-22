package com.kms.byslboot.workspace.exception;

public class PermissionNotFoundException extends RuntimeException{
	
	public PermissionNotFoundException() {
	}
	
	public PermissionNotFoundException(String message) {
		super(message);
	}
	
	public PermissionNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
