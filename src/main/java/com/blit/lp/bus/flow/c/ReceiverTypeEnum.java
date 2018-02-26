package com.blit.lp.bus.flow.c;

public enum ReceiverTypeEnum {
	User(0),
	Role(1),
	Dept(2);
	
	private final int val;
	ReceiverTypeEnum(int _val){
		this.val = _val;
	}
	
	public static ReceiverTypeEnum valueOf(int val){
		switch (val) {
	        case 0:
	            return User;
	        case 1:
	            return Role;
	        case 2:
	            return Dept;
	        default:
	            return null;
        }
	}
	
	public int value(){
		return this.val;
	}
}