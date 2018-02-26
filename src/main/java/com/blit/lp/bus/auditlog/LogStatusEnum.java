package com.blit.lp.bus.auditlog;

public enum LogStatusEnum {
	SUCCESS,FAIL;
	
	public String toString(){
		switch (this) {
		case SUCCESS:
			return "成功";

		case FAIL:
			return "失败";
		
		default:
			break;
		}
		
		return "";
	}
}
