package com.blit.lp.bus.flow.c;

public enum RunStatusEnum {
	NotStart(-1),
	Runing(0),
	Complete(1);
	
	private final int val;
	RunStatusEnum(int _val){
		this.val = _val;
	}
	
	public static RunStatusEnum valueOf(int val){
		switch (val) {
	        case -1:
	            return NotStart;
	        case 0:
	            return Runing;
	        case 1:
	            return Complete;
	        default:
	            return null;
        }
	}
	
	public int value(){
		return this.val;
	}
}
