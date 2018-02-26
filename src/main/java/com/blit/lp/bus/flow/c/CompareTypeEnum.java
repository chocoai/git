package com.blit.lp.bus.flow.c;

//流程比较符类型枚举
public enum CompareTypeEnum {
	CONTAIN(0),//包含
	LESS(1),//<
	LESSEQUAL(2),//<=
	BIG(3),//>
	BIGEQUAL(4),//>=
	EQUAL(5),//==
	NOTEQUAL(6),//!=
	AND(7),//与
	OR(8),//或
	NOTCONTAIN(10),//10不包含
	LEFTCONTAIN(11),//11左包含
	RIGHTCONTAIN(12);//12右包含
	
	private final int val;
	CompareTypeEnum(int _val){
		this.val = _val;
	}
	
	public static CompareTypeEnum valueOf(int val){
		switch (val) {
	        case 0:
	            return CONTAIN;
	        case 1:
	            return LESS;
	        case 2:
	            return LESSEQUAL;
	        case 3:
	            return BIG;
	        case 4:
	            return BIGEQUAL;
	        case 5:
	            return EQUAL;
	        case 6:
	            return NOTEQUAL;
	        case 7:
	            return AND;
	        case 8:
	            return OR;
	        case 10:
	            return NOTCONTAIN;
	        case 11:
	            return LEFTCONTAIN;
	        case 12:
	            return RIGHTCONTAIN;
	        default:
	            return null;
        }
	}
	
	public int value(){
		return this.val;
	}
}
