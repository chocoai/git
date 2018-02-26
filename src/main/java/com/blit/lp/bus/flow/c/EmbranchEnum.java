package com.blit.lp.bus.flow.c;

/**
 * 分支模式
 * @author dkomj
 *
 */
public enum EmbranchEnum {
	SinglePath_NotSelect(0),
	SinglePath_Select(1),
	MultiPath_NotSelect(2),
	MultiPath_Select(3);
	
	private final int val;
	EmbranchEnum(int _val){
		this.val = _val;
	}
	
	public static EmbranchEnum valueOf(int val){
		switch (val) {
	        case 0:
	            return SinglePath_NotSelect;
	        case 1:
	            return SinglePath_Select;
	        case 2:
	            return MultiPath_NotSelect;
	        case 3:
	            return MultiPath_Select;
	        default:
	            return null;
        }
	}
	
	public int value(){
		return this.val;
	}
}
