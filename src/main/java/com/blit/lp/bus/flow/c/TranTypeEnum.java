package com.blit.lp.bus.flow.c;

public enum TranTypeEnum {
	Submit(0),
	Untread(1);
	
	private final int val;
	TranTypeEnum(int _val){
		this.val = _val;
	}
	
	public static TranTypeEnum valueOf(int val){
		switch (val) {
	        case 0:
	            return Submit;
	        case 1:
	            return Untread;
	        default:
	            return null;
        }
	}
	
	public int value(){
		return this.val;
	}
}