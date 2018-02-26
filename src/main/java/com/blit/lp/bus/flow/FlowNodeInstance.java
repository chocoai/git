package com.blit.lp.bus.flow;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blit.lp.bus.flow.actor.FlowActor;
import com.blit.lp.bus.flow.c.ConvergeEnum;
import com.blit.lp.bus.flow.c.DealStatusEnum;
import com.blit.lp.bus.flow.c.EmbranchEnum;
import com.blit.lp.bus.flow.c.FlowEventEnum;
import com.blit.lp.bus.flow.c.FlowNodeTypeEnum;
import com.blit.lp.bus.flow.c.ReceiverTypeEnum;
import com.blit.lp.bus.flow.c.RunStatusEnum;
import com.blit.lp.core.exception.FlowException;
import com.blit.lp.tools.DbEx;
import com.blit.lp.tools.IdKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class FlowNodeInstance {
	private FlowInstance flowInstance = null;
	private Record nodeInstanceRow = null;
	private Record flowNodeRow = null;
	
	public FlowNodeInstance(FlowInstance fi, Record row){
		flowInstance = fi;
		nodeInstanceRow = row;
		flowNodeRow = flowInstance.getFlow().getFlowNode(row.getStr("flownodeid"));
	}
	
	public FlowInstance getFlowInstance() {
		return flowInstance;
	}
	public Record getNodeInstanceRow() {
		return nodeInstanceRow;
	}
	public Record getFlowNodeRowRow() {
		return flowNodeRow;
	}
	
	/**
	 * 
	 * @param sender 发送人
	 * @param toNodes 提交节点及接收人
	 * @param isFreeFlow 是否自由流，自由流不会判断提交节点是否可用及接收人是否有权限
	 * @return 0:需要选择接收人，1:提交成功
	 */
	public int submit(final FlowUser sender,final Map<String,String[]> toNodes,Boolean isFreeFlow){
		FlowNodeInstance.checkFlowNodeInstanceIsRun(nodeInstanceRow.getStr("id"));
		
		boolean[] hqRes = checkHqSubmit();
		if(checkIsNeedSelectFlowUser(hqRes)){
			if(toNodes == null || toNodes.size() < 1){
				this.flowInstance.invokeEvent(FlowEventEnum.Before_Submit, this.flowInstance, this.nodeInstanceRow);
				return 0;
			}
		}
		else{
			this.flowInstance.invokeEvent(FlowEventEnum.Before_Submit, this.flowInstance, this.nodeInstanceRow);
		}
		
		boolean isComplete = this.submitTask(sender,hqRes);
		if(isComplete){
			this.completeTask(sender, DealStatusEnum.Submit, "");
			
			Map<String,List<FlowUser>> submitNodeActorMap = this.loadSubmitFlowNodeAndUser(toNodes,isFreeFlow);
			for (String flowNodeId : submitNodeActorMap.keySet()) {
				Record flowNodeRow = flowInstance.getFlow().getFlowNode(flowNodeId);
				Record nextNodeInstanceRow = this.createNextTask(sender, flowNodeRow);
				if(this.checkNextTaskIsCanStart(nextNodeInstanceRow,flowNodeRow)){
					List<FlowUser> receiverList = submitNodeActorMap.get(flowNodeId);
					if(receiverList==null || receiverList.size() < 1){
						throw new FlowException("提交节点没有接收人，flownodename:" + flowNodeRow.getStr("flownodename"));
					}
					this.startNextTask(nextNodeInstanceRow, sender, submitNodeActorMap.get(flowNodeId));
				}
				this.flowInstance.invokeEvent(FlowEventEnum.After_Submit, this.flowInstance, this.nodeInstanceRow, nextNodeInstanceRow);
			}
		}

		return 1;
	}
	
	/**
	 * 获取当前节点提交，可以提交的下一个节点及参与者
	 * @return
	 */
	public Map<String,List<FlowUser>> loadSubmitNextActor(){
		Set<String> flowNodeIds = loadSubmitNextNodes();
		Map<String,List<FlowUser>> map = new HashMap<String, List<FlowUser>>();
		for (String flowNodeId : flowNodeIds) {
			List<FlowUser> userList = FlowActor.getFlowActor(flowInstance, flowNodeId).getActors();
			map.put(flowNodeId, userList);
		}
		return map;
	}
	
	/**
	 * 获取当前节点提交，可以提交的下一个节点
	 * @return
	 */
	public Set<String> loadSubmitNextNodes(){
		Set<String> set = new LinkedHashSet<String>();
		String currFlowNodeId = flowNodeRow.getStr("id");
		List<Record> list = flowInstance.getFlow().getTransList();
		for (Record record : list) {
			if(currFlowNodeId.equals(record.getStr("fromnodeid"))
					&& Integer.parseInt(record.get("trantype").toString()) == 0){
				boolean bl = new FlowTrans(flowInstance, record).isEnable();
				if(bl){
					set.add(record.getStr("tonodeid"));
				}
			}
		}
		return set;
	}
	
	//获取节点会签信息
	private boolean[] checkHqSubmit(){
		//如果节点是会签节点,非最后一个提交人，也是不需要选择
		boolean[] re = new boolean[]{false,false};
		JSONObject jsonObj = JSONObject.parseObject(flowNodeRow.getStr("extjson"));
		jsonObj = jsonObj.getJSONObject("hq");
		if(jsonObj == null)
			return re;
		
		boolean enable = jsonObj.getBooleanValue("enable");
		int completetype = jsonObj.getIntValue("completetype");
		int completenum = jsonObj.getIntValue("completenum");
		if(enable){
			boolean isFinish = false;
			if(completetype == 0){//全部完成
				int waitDoCount = DbEx.queryInt("select count(1) from lp_wfi_waitdo where flownodeinstanceid=?",nodeInstanceRow.get("id"));
				if(waitDoCount < 2){
					isFinish = true;
				}
			}
			else if(completetype == 1){//完成个数
				int receiverCount = DbEx.queryInt("select count(1) from lp_wfi_receiver where flownodeinstanceid=?",nodeInstanceRow.get("id"));
				int operatorCount = DbEx.queryInt("select count(1) from lp_wfi_operator where flownodeinstanceid=?",nodeInstanceRow.get("id"));
				operatorCount++;
				if(operatorCount >= receiverCount || operatorCount >= completenum){
					isFinish = true;
				}
			}
			else if(completetype == 2){//完成百分比
				int receiverCount = DbEx.queryInt("select count(1) from lp_wfi_receiver where flownodeinstanceid=?",nodeInstanceRow.get("id"));
				int operatorCount = DbEx.queryInt("select count(1) from lp_wfi_operator where flownodeinstanceid=?",nodeInstanceRow.get("id"));
				operatorCount++;
				double per = operatorCount * 100.0 / receiverCount;
				if(operatorCount >= receiverCount || per >= completenum){
					isFinish = true;
				}
			}
			
			re[0] = enable;
			re[1] = isFinish;
		}
		
		return re;
	}
	
	//检查节点提交时，是否需要选择接收人
	private boolean checkIsNeedSelectFlowUser(boolean[] hqRes){
		boolean isSel = false;
		int embranch = Integer.parseInt(flowNodeRow.get("embranch").toString());
		if(embranch == EmbranchEnum.SinglePath_Select.value()
				|| embranch == EmbranchEnum.MultiPath_Select.value()){
			isSel = true;
			if(hqRes[0] && !hqRes[1]){
				isSel = false;
			}
		}
		
		return isSel;
	}
	
	//获取提交下一节点及接收人
	private Map<String,List<FlowUser>> loadSubmitFlowNodeAndUser(final Map<String,String[]> toNodes,boolean isFreeFlow){
		Map<String,List<FlowUser>> realToNodes = new HashMap<String, List<FlowUser>>();
		if(isFreeFlow){
			throw new FlowException("暂未实现自由流提交！");
		}
		else{
			Map<String, List<FlowUser>> map = loadSubmitNextActor();
			if(toNodes == null || toNodes.size() < 1){
				for (String flowNodeId : map.keySet()) {
					List<FlowUser> list = map.get(flowNodeId);
					if(list.size() < 1){
						throw new FlowException("提交节点没有接收人，flownodeid:" + flowNodeId);
					}
					realToNodes.put(flowNodeId, list);
				}
			}
			else{
				for (String flowNodeId : toNodes.keySet()) {
					if(!map.containsKey(flowNodeId)){
						throw new FlowException("不能提交到指定节点，flownodeid:" + flowNodeId);
					}
					
					List<FlowUser> list = new ArrayList<FlowUser>();
					Map<String,FlowUser> actorMap = new HashMap<String, FlowUser>();
					for (FlowUser flowUser : map.get(flowNodeId)) {
						actorMap.put(flowUser.get("id"), flowUser);
					}
					
					for (String userid : toNodes.get(flowNodeId)) {
						if(!actorMap.containsKey(userid))
							throw new FlowException("接收人不是节点参与者，flownodeid:" + flowNodeId);
						list.add(actorMap.get(userid));
					}
					
//					if(list.size() < 1){
//						throw new FlowException("提交节点没有接收人，flownodeid:" + flowNodeId);
//					}
					
					realToNodes.put(flowNodeId, list);
				}
			}
		}

		return realToNodes;
	}
	
	//提交节点实例任务
	private boolean submitTask(final FlowUser sender,boolean[] hqRes){
		Record opRow = new Record();
		opRow.set("id", String.valueOf(IdKit.nextId()));
		opRow.set("flownodeinstanceid", nodeInstanceRow.get("id"));
		opRow.set("operatortype", ReceiverTypeEnum.User.value());
		opRow.set("operatorid", sender.get("id"));
		opRow.set("operator", sender.get("username"));
		opRow.set("addtime", new Timestamp(new Date().getTime()));
		Db.save("lp_wfi_operator", "id", opRow);
		
		Db.update("delete from lp_wfi_waitdo where flownodeinstanceid=? and receiverid=?",nodeInstanceRow.get("id"),sender.get("id"));
		//会签节点没完成
		if(hqRes[0] && !hqRes[1])
			return false;
		
		return true;
	}
	
	//完成节点实例任务
	private void completeTask(final FlowUser sender,DealStatusEnum dealstatus,String note) {
		nodeInstanceRow.set("runstatus", RunStatusEnum.Complete.value());
		nodeInstanceRow.set("dealstatus", dealstatus.value());
		nodeInstanceRow.set("note", note);
		nodeInstanceRow.set("endtime", new Timestamp(new Date().getTime()));
		Db.update("lp_wfi_flownode","id",nodeInstanceRow);
		
		Db.update("delete from lp_wfi_waitdo where flownodeinstanceid=?",nodeInstanceRow.get("id").toString());
	}
	

	//创建节点实例任务
	private Record createNextTask(final FlowUser sender,Record flowNodeRow){
		String flowNodeId = flowNodeRow.getStr("id");
		Record newNodeInstanceRow = flowInstance.getNotStartFlowNodeInstanceRow(flowNodeId);
		if(newNodeInstanceRow == null){
			int nodeType = Integer.parseInt(flowNodeRow.get("nodetype").toString());
			int maxPx = DbEx.queryInt("select max(px) as px from lp_wfi_flownode where flowinstanceid=?", nodeInstanceRow.get("flowinstanceid"));
			maxPx++;
			newNodeInstanceRow = new Record();
			newNodeInstanceRow.set("id", String.valueOf(IdKit.nextId()));
			newNodeInstanceRow.set("flowversionid", nodeInstanceRow.get("flowversionid"));
			newNodeInstanceRow.set("flowinstanceid", nodeInstanceRow.get("flowinstanceid"));
			newNodeInstanceRow.set("flownodeid", flowNodeRow.get("id"));
			newNodeInstanceRow.set("flownodename", flowNodeRow.get("flownodename"));
			newNodeInstanceRow.set("nodetype", nodeType);
			newNodeInstanceRow.set("runstatus", RunStatusEnum.NotStart.value());
			newNodeInstanceRow.set("dealstatus", DealStatusEnum.NotDeal.value());
			newNodeInstanceRow.set("pids", "[\"" +nodeInstanceRow.getStr("id")+ "\"]");
			newNodeInstanceRow.set("px", maxPx);
			newNodeInstanceRow.set("senderid", sender.get("id"));
			newNodeInstanceRow.set("sender", sender.get("username"));
			newNodeInstanceRow.set("addtime", new Timestamp(new Date().getTime()));
			Db.save("lp_wfi_flownode", "id", newNodeInstanceRow);
			flowInstance.getFlowNodeInstanceList().add(newNodeInstanceRow);
		}
		else{
			String pids = newNodeInstanceRow.getStr("pids");
			JSONArray ar = JSONArray.parseArray(pids);
			ar.add(nodeInstanceRow.getStr("id"));
			newNodeInstanceRow.set("pids", ar.toString());
			Db.update("lp_wfi_flownode", "id", newNodeInstanceRow);
		}
		return newNodeInstanceRow;
	}
	
	//检查节点是否可以启动
	private boolean checkNextTaskIsCanStart(Record nextNodeInstanceRow,Record flowNodeRow){
		int nodeType = Integer.parseInt(flowNodeRow.get("nodetype").toString());

		int converge = Integer.parseInt(flowNodeRow.get("converge").toString());
		if(converge == ConvergeEnum.MultiPath.value()){
			List<Record> list = flowInstance.getRuningFlowNodeInstanceRows();
			if(list.size() > 0){
				return false;
			}
		}
		
		//提交到结束节点
		if(nodeType == FlowNodeTypeEnum.EndNode.value()){
			this.flowInstance.invokeEvent(FlowEventEnum.Before_Complete, this.flowInstance);
			nextNodeInstanceRow.set("runstatus", RunStatusEnum.Complete.value());
			nextNodeInstanceRow.set("begintime", new Timestamp(new Date().getTime()));
			nextNodeInstanceRow.set("endtime", new Timestamp(new Date().getTime()));
			Db.update("lp_wfi_flownode", "id", nextNodeInstanceRow);
			
			Record r = flowInstance.getFlowInstanceRow();
			r.set("runstatus", RunStatusEnum.Complete.value());
			r.set("endtime", new Timestamp(new Date().getTime()));
			Db.update("lp_wfi_flow", "id", r);
			this.flowInstance.invokeEvent(FlowEventEnum.After_Complete, this.flowInstance);
			return false;
		}
				
		return true;
	}
	
	//启动节点实例任务
	private void startNextTask(Record nextNodeInstanceRow,final FlowUser sender,List<FlowUser> receiverList){	
		nextNodeInstanceRow.set("begintime", new Timestamp(new Date().getTime()));
		nextNodeInstanceRow.set("runstatus", RunStatusEnum.Runing.value());
		Db.update("lp_wfi_flownode", "id", nextNodeInstanceRow);
		
		String openurl = flowInstance.getFlow().getFlowRow().getStr("openurl");
		openurl = openurl.replaceAll("#\\(g_flowversionid\\)", nextNodeInstanceRow.getStr("flowversionid"));
		openurl = openurl.replaceAll("#\\(g_flownodeid\\)", nextNodeInstanceRow.getStr("flownodeid"));
		openurl = openurl.replaceAll("#\\(g_flownodename\\)", nextNodeInstanceRow.getStr("flownodename"));
		openurl = openurl.replaceAll("#\\(g_flowinstanceid\\)", nextNodeInstanceRow.getStr("flowinstanceid"));
		openurl = openurl.replaceAll("#\\(g_flownodeinstanceid\\)", nextNodeInstanceRow.getStr("id"));
		
		for (FlowUser receiverUser : receiverList) {
			Record waitdoRow = new Record();
			waitdoRow.set("id", String.valueOf(IdKit.nextId()));
			waitdoRow.set("flowversionid", nextNodeInstanceRow.get("flowversionid"));
			waitdoRow.set("flowinstanceid", nextNodeInstanceRow.get("flowinstanceid"));
			waitdoRow.set("flownodeinstanceid", nextNodeInstanceRow.get("id"));
			waitdoRow.set("flownodeid", nextNodeInstanceRow.get("flownodeid"));
			waitdoRow.set("flownodename", nextNodeInstanceRow.get("flownodename"));
			waitdoRow.set("senderid", sender.get("id"));
			waitdoRow.set("sender", sender.get("username"));
			waitdoRow.set("receivertype", ReceiverTypeEnum.User.value());
			waitdoRow.set("receiverid", receiverUser.get("id"));
			waitdoRow.set("receiver", receiverUser.get("username"));
			waitdoRow.set("openurl", openurl);
			waitdoRow.set("addtime", new Timestamp(new Date().getTime()));
			Db.save("lp_wfi_waitdo", "id", waitdoRow);
			
			Record receiverRow = new Record();
			receiverRow.set("id", String.valueOf(IdKit.nextId()));
			receiverRow.set("flownodeinstanceid", nextNodeInstanceRow.get("id"));
			receiverRow.set("receivertype", ReceiverTypeEnum.User.value());
			receiverRow.set("receiverid", receiverUser.get("id"));
			receiverRow.set("receiver", receiverUser.get("username"));
			receiverRow.set("addtime", new Timestamp(new Date().getTime()));
			Db.save("lp_wfi_receiver", "id", receiverRow);
		}
	}
	
	/**
	 * 
	 * @param sender 回退人
	 * @param toNodes 回退节点及接收人
	 * @param note 备注
	 * @return 0:需要选择回退节点，1:回退成功
	 */
	public int untread(final FlowUser sender,final Map<String,String[]> toNodes,String note){
		FlowNodeInstance.checkFlowNodeInstanceIsRun(nodeInstanceRow.getStr("id"));
		
		List<Record> list = loadUntreadNodes();
		if(toNodes == null || toNodes.size() < 1){
			if(list.size() > 1){
				return 0;
			}
		}
		
		this.flowInstance.invokeEvent(FlowEventEnum.Before_Untread, this.flowInstance, this.nodeInstanceRow);
		
		if(untreadTask(sender,note)){
			Map<String,List<FlowUser>> untreadNodeActorMap = this.loadUntreadFlowNodeAndUser(list,toNodes);
			for (String flowNodeId : untreadNodeActorMap.keySet()) {
				Record flowNodeRow = flowInstance.getFlow().getFlowNode(flowNodeId);
				Record nextNodeInstanceRow = this.createNextTask(sender, flowNodeRow);
				if(this.checkNextTaskIsCanStart(nextNodeInstanceRow,flowNodeRow)){
					this.startNextTask(nextNodeInstanceRow, sender, untreadNodeActorMap.get(flowNodeId));
				}
				this.flowInstance.invokeEvent(FlowEventEnum.After_Untread, this.flowInstance, this.nodeInstanceRow, nextNodeInstanceRow);
			}
		}
		
		return 1;
	}
	
	//回退节点实例任务
	private boolean untreadTask(final FlowUser sender,String note){
		this.completeTask(sender,DealStatusEnum.Untread,note);
		
		Record opRow = new Record();
		opRow.set("id", String.valueOf(IdKit.nextId()));
		opRow.set("flownodeinstanceid", nodeInstanceRow.get("id"));
		opRow.set("operatortype", ReceiverTypeEnum.User.value());
		opRow.set("operatorid", sender.get("id"));
		opRow.set("operator", sender.get("username"));
		opRow.set("addtime", new Timestamp(new Date().getTime()));
		Db.save("lp_wfi_operator", "id", opRow);
		
		return true;
	}
	
	/**
	 * 获取当前节点回退，可以回退的节点
	 * @return
	 */
	public List<Record> loadUntreadNodes(){
		Set<String> set = new LinkedHashSet<String>();
		String currFlowNodeId = flowNodeRow.getStr("id");
		List<Record> list = flowInstance.getFlow().getTransList();
		for (Record record : list) {
			if(currFlowNodeId.equals(record.getStr("fromnodeid"))
					&& Integer.parseInt(record.get("trantype").toString()) == 1){
				set.add(record.getStr("tonodeid"));
			}
			
			if(currFlowNodeId.equals(record.getStr("tonodeid"))
					&& Integer.parseInt(record.get("trantype").toString()) == 0){
				set.add(record.getStr("fromnodeid"));
			}
		}
		
		
		List<Record> nodeList = flowInstance.getFlowNodeInstanceList();
		List<Record> re = new ArrayList<Record>();
		for (int i= nodeList.size() - 1; i >= 0; i--) {
			Record row = nodeList.get(i);
			String flownodeid = row.get("flownodeid");
			int runstatus = Integer.parseInt(row.get("runstatus").toString());
			if(runstatus==1 && set.contains(flownodeid)){
				re.add(row);
				set.remove(flownodeid);
			}
		}
		return re;
	}
	
	private Map<String,List<FlowUser>> loadUntreadFlowNodeAndUser(List<Record> allList,final Map<String,String[]> toNodes){
		Map<String,List<FlowUser>> realToNodes = new HashMap<String, List<FlowUser>>();
		
		if(toNodes == null || toNodes.size() < 1){
			for (Record nodeInstanceRow : allList) {
				List<Record> opUserList = Db.find("select * from LP_SYS_USER where id in (select operatorid from LP_WFI_OPERATOR where flownodeinstanceid=?)", nodeInstanceRow.get("id"));
				String flowNodeId = nodeInstanceRow.getStr("flownodeid");
				List<FlowUser> list = new ArrayList<FlowUser>();
				for (Record opUserRow : opUserList) {
					list.add(new FlowUser(opUserRow));
				}
				if(list.size() < 1){
					throw new FlowException("回退节点没有接收人，flownodeid:" + flowNodeId);
				}
				realToNodes.put(flowNodeId, list);
			}
		}
		else{
			for (String flowNodeId : toNodes.keySet()) {
				String[] selUserIds = toNodes.get(flowNodeId);
				
				List<FlowUser> list = new ArrayList<FlowUser>();
				if(selUserIds == null || selUserIds.length < 1){
					for (Record nodeInstanceRow : allList) {
						if(flowNodeId.equals(nodeInstanceRow.getStr("flownodeid"))){
							List<Record> opUserList = Db.find("select * from LP_SYS_USER where id in (select operatorid from LP_WFI_OPERATOR where flownodeinstanceid=?)", nodeInstanceRow.get("id"));
							for (Record opUserRow : opUserList) {
								list.add(new FlowUser(opUserRow));
							}
							break;
						}
					}
				}
				else{
					throw new FlowException("回退操作暂时不支持指定回退人!");
				}

				if(list.size() < 1){
					throw new FlowException("回退节点没有接收人，flownodeid:" + flowNodeId);
				}
				
				realToNodes.put(flowNodeId, list);
			}
		}
		
		if(realToNodes.keySet().size() < 1)
			throw new FlowException("没有可回退的流程节点！");
		
		return realToNodes;
	}
	
	//检查节点实例是否正在运行状态
	private static void checkFlowNodeInstanceIsRun(String nodeInstanceId) {
		Record dbNodeInstance = Db.findFirst("select * from lp_wfi_flownode where id=?",nodeInstanceId);
		int runstatus = Integer.parseInt(dbNodeInstance.get("runstatus").toString());
		if(runstatus != 0){
			throw new FlowException("流程节点实例已经被他人处理，节点实例id：" + nodeInstanceId);
		}
	}
}
