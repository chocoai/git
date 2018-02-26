package com.blit.lp.bus.flow.actor;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.blit.lp.bus.flow.FlowInstance;
import com.blit.lp.bus.flow.FlowUser;
import com.blit.lp.core.exception.FlowException;
import com.jfinal.plugin.activerecord.Record;

public abstract class FlowActor {
	
	public static FlowActor getFlowActor(FlowInstance flowInstance,String flowNodeId){
		Record nodeRow = flowInstance.getFlow().getFlowNode(flowNodeId);
		JSONObject jsonObj = JSONObject.parseObject(nodeRow.getStr("actorjson"));
		String type = jsonObj.getString("type");
		FlowActor actor = null;
		switch(type){
			case "参与者列表":
				actor = new FlowActorList();
				break;
			case "流程启动者":
				actor = new FlowActorCreater();
				break;
			case "节点处理人":
				actor = new FlowActorNodeOperator();
				break;
			case "流程参数":
				actor = new FlowActorParam();
				break;
			case "自定义":
				actor = new FlowActorCustom();
				break;
		}
		if(actor == null)
			throw new FlowException("不支持的参与者类型扩展！");
		
		actor.init(flowInstance, nodeRow, jsonObj);
		return actor;
	}
	
	protected FlowInstance flowInstance = null;
	protected Record flowNodeRow = null;
	protected JSONObject jsonObj = null;
	protected void init(FlowInstance flowInstance,Record nodeRow,JSONObject jsonObj){
		this.flowInstance = flowInstance;
		this.flowNodeRow = nodeRow;
		this.jsonObj = jsonObj;
	}
	
	
	public abstract List<FlowUser> getActors();
}
