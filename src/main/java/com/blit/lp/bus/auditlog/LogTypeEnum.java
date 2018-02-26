package com.blit.lp.bus.auditlog;

public enum LogTypeEnum {
	SYSLOGIN,SYSMANAGER,SYSSECURITY,BUSS;
	
	public String toString(){
		switch (this) {
		case SYSLOGIN:
			return "系统登录";

		case SYSMANAGER:
			return "系统管理";
		
		case SYSSECURITY:
			return "系统安全";
		
		case BUSS:
			return "业务日志";
			
		default:
			break;
		}
		
		return "";
	}
}
