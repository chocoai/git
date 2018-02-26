package com.blit.lp.bus.flow.c;

public enum FlowNodeTypeEnum {
	StartNode(0),
	CommonNode(1),
	ChildNode(2),
	AutoNode(3),
	EndNode(9);
	
	private final int val;
	FlowNodeTypeEnum(int _val){
		this.val = _val;
	}
	
	public static FlowNodeTypeEnum valueOf(int val){
		switch (val) {
	        case 1:
	            return StartNode;
	        case 2:
	            return CommonNode;
	        case 3:
	            return AutoNode;
	        case 9:
	            return EndNode;
	        default:
	            return null;
        }
	}
	
	public int value(){
		return this.val;
	}
}
