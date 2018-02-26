package com.blit.lp.bus.flow.c;

/**
 * 流程事件枚举
 * @author dkomj
 *
 */
public enum FlowEventEnum {
	Before_Start,//流程启动前
	After_Start,//流程启动后
	Before_Submit,//流程节点提交前
	After_Submit,//流程节点提交后
	Before_Untread,//流程节点回退前
	After_Untread,//流程节点回退后
	Before_Complete,//流程完成前
	After_Complete;//流程完成后
}
