package com.seckill.common.exception;

public class SellException extends RuntimeException{
	private static final long serialVersionUID = -3408542041465969987L;
	
	private int status;
	private String message;
	
	public SellException(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
