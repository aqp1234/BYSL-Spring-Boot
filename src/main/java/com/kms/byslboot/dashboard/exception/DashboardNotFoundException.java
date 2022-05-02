package com.kms.byslboot.dashboard.exception;

public class DashboardNotFoundException extends RuntimeException{
	
	public DashboardNotFoundException() {
	}
	
	public DashboardNotFoundException(String message) {
		super(message);
	}
	
	public DashboardNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
