package com.seckill.pojo;

import java.util.Date;

public class SuccessKill {

	private String seckillId;
	
	private String userId;
	
	private int state = 1;
	
	private Date createTime;
	
	public SuccessKill() {
		super();
	}

	public SuccessKill(String seckillId, String userId, int state, Date createTime) {
		super();
		this.seckillId = seckillId;
		this.userId = userId;
		this.state = state;
		this.createTime = createTime;
	}

	public String getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(String seckillId) {
		this.seckillId = seckillId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
