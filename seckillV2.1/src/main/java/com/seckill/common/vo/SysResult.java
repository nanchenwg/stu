package com.seckill.common.vo;

public class SysResult {
	
	private int status;
	
	private String message;
	
	public SysResult() {

	}

	public SysResult(int status, String message) {
		this.status = status;
		this.message = message;
	}
	
    public static SysResult build(Integer status, String msg) {
        return new SysResult(status, msg);
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
