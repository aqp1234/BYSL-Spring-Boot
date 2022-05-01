package com.kms.byslboot.conference.exception;

public class ConferenceNotFoundException extends RuntimeException{
	
	public ConferenceNotFoundException() {
	}
	
	public ConferenceNotFoundException(String message) {
		super(message);
	}
	
	public ConferenceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
