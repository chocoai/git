package com.blit.lp.bus.security;

import java.io.Serializable;
import java.util.Date;

public class LockUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userNum;
	private long lockStartTime;
	
	public LockUser(String userNum) {
		this.userNum = userNum;
		this.lockStartTime = new Date().getTime();
	}
	
	public String getUserNum() {
		return userNum;
	}
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	public long getLockStartTime() {
		return lockStartTime;
	}
	public void setLockStartTime(long lockStartTime) {
		this.lockStartTime = lockStartTime;
	}
}
