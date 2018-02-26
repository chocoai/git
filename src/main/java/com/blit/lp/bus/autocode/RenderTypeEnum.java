package com.blit.lp.bus.autocode;

//输出代码类型
public enum RenderTypeEnum {
	HTML,JAVA,CONSOLE;
	
	public String toString(){
		switch (this) {
		case HTML:
			return "html代码";

		case JAVA:
			return "java代码输出";
		
		case CONSOLE:
			return "java代码";
			
		default:
			break;
		}
		
		return "";
	}
}
