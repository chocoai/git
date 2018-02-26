package com.blit.lp.bus.flow.c;

/**
 * 聚合模式
 * @author dkomj
 *
 */
public enum ConvergeEnum {
	SinglePath(0),
	MultiPath(1);
	
	private final int val;
	ConvergeEnum(int _val){
		this.val = _val;
	}
	
	public static ConvergeEnum valueOf(int val){
		switch (val) {
	        case 0:
	            return SinglePath;
	        case 1:
	            return MultiPath;
	        default:
	            return null;
        }
	}
	
	public int value(){
		return this.val;
	}
}
