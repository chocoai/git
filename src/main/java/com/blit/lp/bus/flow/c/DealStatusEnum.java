package com.blit.lp.bus.flow.c;

public enum DealStatusEnum {
	NotDeal(0),
	Submit(1),
	Untread(2),
	FetchBack(3),
	Invalid(4);
	
	private final int val;
	DealStatusEnum(int _val){
		this.val = _val;
	}
	
	public static DealStatusEnum valueOf(int val){
		switch (val) {
	        case 0:
	            return NotDeal;
	        case 1:
	            return Submit;
	        case 2:
	            return Untread;
	        case 3:
	            return FetchBack;
	        case 4:
	            return Invalid;
	        default:
	            return null;
        }
	}
	
	public int value(){
		return this.val;
	}
}
