package com.blit.lp.bus.flow.c;

//流程条件类型枚举
public enum ConditionTypeEnum {
	PARAM_PARAM(0),
	PARAM_CONST(1),
	PARAM_CON(2),
	CON_CON(3),
	CON_CONST(4),
	CONST_CONST(5);
	
	private final int val;
	ConditionTypeEnum(int _val){
		this.val = _val;
	}
	
	public static ConditionTypeEnum valueOf(int val){
		switch (val) {
	        case 0:
	            return PARAM_PARAM;
	        case 1:
	            return PARAM_CONST;
	        case 2:
	            return PARAM_CON;
	        case 3:
	            return CON_CON;
	        case 4:
	            return CON_CONST;
	        case 5:
	            return CONST_CONST;
	        default:
	            return null;
        }
	}
	
	public int value(){
		return this.val;
	}
}