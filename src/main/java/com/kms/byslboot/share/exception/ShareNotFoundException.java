package com.kms.byslboot.share.exception;

public class ShareNotFoundException extends RuntimeException{
	
	public ShareNotFoundException() {
	}
	
	public ShareNotFoundException(String message) {
		super(message);
	}
	
	public ShareNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
