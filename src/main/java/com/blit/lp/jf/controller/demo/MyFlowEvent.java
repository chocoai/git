package com.blit.lp.jf.controller.demo;

import com.blit.lp.bus.flow.FlowInstance;
import com.blit.lp.bus.flow.i.LPFlowEvent;
import com.blit.lp.core.exception.FlowException;
import com.jfinal.plugin.activerecord.Record;

/**
 * 流程自定义事件处理示例
 * @author dkomj
 *
 */
public class MyFlowEvent extends LPFlowEvent {

	@Override
	public void onBeforeStart(FlowInstance fi) throws Exception {
		//流程创建前时设置流程参数
		//fi.setParam("days", "3");
		System.out.println("流程创建前事件，id:" + fi.getFlowInstanceRow().getStr("id"));
	}

	@Override
	public void onAfterStart(FlowInstance fi) throws Exception {
		System.out.println("流程创建后事件，id:" + fi.getFlowInstanceRow().getStr("id"));
	}

	@Override
	public void onBeforeNodeSubmit(FlowInstance fi, Record nodeInstanceRow) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("流程节点提交前事件，" + nodeInstanceRow.getStr("flownodename"));
	}

	@Override
	public void onAfterNodeSubmit(FlowInstance fi, Record nodeInstanceRow,
			Record toNodeInstanceRow) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("流程节点提交后事件，" + nodeInstanceRow.getStr("flownodename") + " -> " + toNodeInstanceRow.getStr("flownodename"));
	}

	@Override
	public void onBeforeNodeUntread(FlowInstance fi, Record nodeInstanceRow) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("流程节点回退前事件，" + nodeInstanceRow.getStr("flownodename"));
	}

	@Override
	public void onAfterNodeUntread(FlowInstance fi, Record nodeInstanceRow,
			Record toNodeInstanceRow) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("流程节点回退后事件，" + nodeInstanceRow.getStr("flownodename") + " -> " + toNodeInstanceRow.getStr("flownodename"));
	}

	@Override
	public void onBeforeComplete(FlowInstance fi) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("流程完成前事件，id:" + fi.getFlowInstanceRow().getStr("id"));
	}

	@Override
	public void onAfterComplete(FlowInstance fi) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("流程完成后事件，id:" + fi.getFlowInstanceRow().getStr("id"));
	}

	

}
