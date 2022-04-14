package com.kms.byslboot.member.exception;

public class DuplicatedKeyException extends RuntimeException{
	
	public DuplicatedKeyException() {
	}
	
	public DuplicatedKeyException(String message) {
		super(message);
	}

	public DuplicatedKeyException(String message, Throwable cause) {
		super(message, cause);
	}
}
