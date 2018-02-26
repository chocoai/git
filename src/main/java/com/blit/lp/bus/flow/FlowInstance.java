package com.blit.lp.bus.flow;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blit.lp.bus.flow.c.DealStatusEnum;
import com.blit.lp.bus.flow.c.FlowEventEnum;
import com.blit.lp.bus.flow.c.FlowNodeTypeEnum;
import com.blit.lp.bus.flow.c.ReceiverTypeEnum;
import com.blit.lp.bus.flow.c.RunStatusEnum;
import com.blit.lp.bus.flow.i.LPFlowEvent;
import com.blit.lp.core.context.Global;
import com.blit.lp.core.exception.FlowException;
import com.blit.lp.tools.IdKit;
import com.blit.lp.tools.Reflect;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

/**
 * 流程运行实例
 * @author dkomj
 *
 */
public class FlowInstance implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 创建流程实例对象
	 * @param flowId
	 * @param creater
	 * @return
	 */
	public static FlowInstance create(String flowId,FlowUser creater){
		Flow flow = Flow.loadFlow(flowId);
		FlowInstance flowInstance = new FlowInstance();
		flowInstance._create(flow.getFlowRow().getStr("id"),creater);
		//只在创建流程时，存放在Session会话中
		if(Global.getContext() != null){
			String flowInstanceId = flowInstance.getFlowInstanceRow().getStr("id");
			Global.setSession(flowInstanceId,flowInstance);
		}
		return flowInstance;
	}
	
	/**
	 * 获取流程实例对象
	 * @param flowInstanceId
	 * @return
	 */
	public static FlowInstance load(String flowInstanceId){
		FlowInstance flowInstance = null;
		if(flowInstance == null){
			if(Global.getContext() != null){
				flowInstance = Global.getSession(flowInstanceId);
			}
			
			if(flowInstance == null){
				flowInstance = new FlowInstance();
				flowInstance._load(flowInstanceId);
			}
			
		}
		return flowInstance;
	}
	
	/**
	 * 清除缓存
	 * @param flowInstanceId
	 * @return
	 */
	public static void clearCache(String flowInstanceId){
		if(Global.getContext() != null){
			Global.setSession(flowInstanceId, null);	
		}
	}
	
	private FlowInstance(){
		
	}
	
	private void _create(String flowVersionId,FlowUser creater){
		this.createUser = creater;
		this.flowVersionId = flowVersionId;
		flowInstanceRow = new Record();
		
		flowNodeInstanceList = new ArrayList<Record>();
		paramList = new ArrayList<Record>();
		
		this._start();
	}
	
	private void _start(){
		Flow flow = this.getFlow();
		flowInstanceRow.set("id", String.valueOf(IdKit.nextId()));
		flowInstanceRow.set("flowversionid", this.flowVersionId);
		flowInstanceRow.set("flowname", flow.getFlowRow().get("flowname"));
		flowInstanceRow.set("runstatus", RunStatusEnum.Runing.value());
		flowInstanceRow.set("saveversion", "-1");
		
		Record startNodeRow = flow.getStartNode();
		Record startNodeInstanceRow = new Record();
		startNodeInstanceRow.set("id", String.valueOf(IdKit.nextId()));
		startNodeInstanceRow.set("flowversionid", this.flowVersionId);
		startNodeInstanceRow.set("flowinstanceid", flowInstanceRow.get("id"));
		startNodeInstanceRow.set("flownodeid", startNodeRow.get("id"));
		startNodeInstanceRow.set("flownodename", startNodeRow.get("flownodename"));
		startNodeInstanceRow.set("nodetype", FlowNodeTypeEnum.StartNode.value());
		startNodeInstanceRow.set("runstatus", RunStatusEnum.Runing.value());
		startNodeInstanceRow.set("dealstatus", DealStatusEnum.NotDeal.value());
		startNodeInstanceRow.set("pids", "[]");
		startNodeInstanceRow.set("px", 0);
		
		flowNodeInstanceList.add(startNodeInstanceRow);
	}
	
	private void _load(String flowInstanceId){
		String  sql = "select * from lp_wfi_flow where id=?";
		flowInstanceRow = Db.findFirst(sql,flowInstanceId);
		if(flowInstanceRow == null)
			throw new FlowException("流程实例数据丢失，流程实例id：" + flowInstanceId);
		
		sql = "select * from lp_wfi_flownode where flowinstanceid=? order by id";
		flowNodeInstanceList = Db.find(sql,flowInstanceId);
		
		sql = "select * from lp_wfi_param where flowinstanceid=? order by id";
		paramList = Db.find(sql,flowInstanceId);
		
		Record userRow = Db.findFirst("select * from lp_sys_user where id=?", flowInstanceRow.get("createuserid").toString());
		this.createUser = new FlowUser(userRow);
		this.flowVersionId = flowInstanceRow.getStr("flowversionid");
		this.isStarted = true;
	}
	
	private boolean isStarted = false;
	private String flowVersionId = null;
	private Record flowInstanceRow = null;
	private FlowUser createUser = null;
	private List<Record> flowNodeInstanceList = null;
	private List<Record> paramList = null;
	
	/**
	 * 获取流程实例，对应的流程模版
	 * @return
	 */
	public Flow getFlow(){
		return Flow.loadFlowVersion(this.flowVersionId);
	}
	
	/**
	 * 启动流程
	 */
	public boolean start(){
		if(!this.isStarted){
			this.isStarted = Db.tx(new IAtom() {
				@Override
				public boolean run() throws SQLException {
					FlowInstance.this.invokeEvent(FlowEventEnum.Before_Start, FlowInstance.this);
					FlowUser user = FlowInstance.this.createUser;
					Record flowInstanceRow = FlowInstance.this.flowInstanceRow;
					flowInstanceRow.set("starttime", new Timestamp(new Date().getTime()));
					flowInstanceRow.set("createuserid", user.get("id"));
					flowInstanceRow.set("createuser", user.get("username"));
					
					flowInstanceRow.set("addtime", new Timestamp(new Date().getTime()));
					Db.save("lp_wfi_flow", "id", flowInstanceRow);
					
					for (Record nodeInstanceRow : FlowInstance.this.flowNodeInstanceList) {
						nodeInstanceRow.set("senderid", user.get("id"));
						nodeInstanceRow.set("sender", user.get("username"));
						nodeInstanceRow.set("begintime", new Timestamp(new Date().getTime()));
						nodeInstanceRow.set("addtime", new Timestamp(new Date().getTime()));
						Db.save("lp_wfi_flownode", "id", nodeInstanceRow);
						
						String openurl = FlowInstance.this.getFlow().getFlowRow().getStr("openurl");
						openurl = openurl.replaceAll("#\\(g_flowversionid\\)", nodeInstanceRow.getStr("flowversionid"));
						openurl = openurl.replaceAll("#\\(g_flownodeid\\)", nodeInstanceRow.getStr("flownodeid"));
						openurl = openurl.replaceAll("#\\(g_flownodename\\)", nodeInstanceRow.getStr("flownodename"));
						openurl = openurl.replaceAll("#\\(g_flowinstanceid\\)", nodeInstanceRow.getStr("flowinstanceid"));
						openurl = openurl.replaceAll("#\\(g_flownodeinstanceid\\)", nodeInstanceRow.getStr("id"));
						
						Record waitdoRow = new Record();
						waitdoRow.set("id", String.valueOf(IdKit.nextId()));
						waitdoRow.set("flowversionid", nodeInstanceRow.get("flowversionid"));
						waitdoRow.set("flowinstanceid", nodeInstanceRow.get("flowinstanceid"));
						waitdoRow.set("flownodeinstanceid", nodeInstanceRow.get("id"));
						waitdoRow.set("flownodeid", nodeInstanceRow.get("flownodeid"));
						waitdoRow.set("flownodename", nodeInstanceRow.get("flownodename"));
						waitdoRow.set("senderid", user.get("id"));
						waitdoRow.set("sender", user.get("username"));
						waitdoRow.set("receivertype", ReceiverTypeEnum.User.value());
						waitdoRow.set("receiverid", user.get("id"));
						waitdoRow.set("receiver", user.get("username"));
						waitdoRow.set("openurl", openurl);
						waitdoRow.set("addtime", new Timestamp(new Date().getTime()));
						Db.save("lp_wfi_waitdo", "id", waitdoRow);
						
						Record receiverRow = new Record();
						receiverRow.set("id", String.valueOf(IdKit.nextId()));
						receiverRow.set("flownodeinstanceid", nodeInstanceRow.get("id"));
						receiverRow.set("receivertype", ReceiverTypeEnum.User.value());
						receiverRow.set("receiverid", user.get("id"));
						receiverRow.set("receiver", user.get("username"));
						receiverRow.set("addtime", new Timestamp(new Date().getTime()));
						Db.save("lp_wfi_receiver", "id", receiverRow);
					}
					
					for (Record r : FlowInstance.this.paramList) {
						Db.save("lp_wfi_param", "id", r);
					}
					FlowInstance.this.invokeEvent(FlowEventEnum.After_Start, FlowInstance.this);
					
					//流程启动后，清除缓存会话
					FlowInstance.clearCache(flowInstanceRow.getStr("id"));
					return true;
				}
			});
		}
		
		return this.isStarted;
	}
	
	/**
	 * 执行节点实例
	 * @param sender
	 * @param nodeInstanceId
	 */
	public boolean exec(final FlowUser sender,final Record nodeInstanceRow){
		String nodetype = nodeInstanceRow.get("nodetype").toString();
		String runstatus = nodeInstanceRow.get("runstatus").toString();
		if(nodetype.equals("0") || nodetype.equals("1")){
			if(!runstatus.equals("0")){
				throw new FlowException("流程节点已经被他人处理，请重新刷新待办!");
			}
			
			Record row = Db.findFirst("select id from lp_wfi_receiver where flownodeinstanceid=? and receiverid=?"
					, nodeInstanceRow.get("id"),sender.get("id"));
			if(row == null){
				throw new FlowException("您没有权限处理流程节点!");
			}
		}
		
		return true;
	}
	
	/**
	 * 流程提交
	 * @param sender，提交人
	 * @param nodeInstanceId，当前节点实例id
	 * @param toNodes，提交到节点参与者( 数据格式如：{"节点id":["用户id"]} )
	 * @param isFreeFlow 是否自由流，自由流不会判断提交节点是否可用及接收人是否有权限
	 * @return 0:需要选择接收人，1:提交成功
	 */
	public int submit(final FlowUser sender,final String nodeInstanceId,final Map<String,String[]> toNodes,Boolean isFreeFlow){
		final Map<String,Integer> result = new HashMap<String,Integer>();
		Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				FlowInstance.this.start();
				
				int re = FlowInstance.this.getFlowNodeInstance(nodeInstanceId).submit(sender,toNodes,false);
				result.put("result", re);
				return true;
			}
		});
		
		return result.get("result");
	}
	
	/**
	 * 获取节点实例提交到下节点及参与者
	 * @param nodeInstanceId，当前节点实例id
	 * @return 返回的JSON格式：[{id:"",flownodename:"",flownodetype:"",users:[{id:"",username:""}]}]
	 */
	public JSONArray getNextSubmitNodeActor(final String nodeInstanceId){
		final JSONArray result = new JSONArray();
		Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				FlowInstance.this.start();
				
				Map<String,List<FlowUser>> map = FlowInstance.this.getFlowNodeInstance(nodeInstanceId).loadSubmitNextActor();
				for (String flowNodeId : map.keySet()) {
					Record flowRow = FlowInstance.this.getFlow().getFlowNode(flowNodeId);
					JSONObject o = new JSONObject();
					o.put("id", flowRow.getStr("id"));
					o.put("flownodename", flowRow.getStr("flownodename"));
					o.put("flownodetype", flowRow.get("nodetype").toString());
					JSONArray userAr = new JSONArray();
					o.put("users", userAr);
					if(flowRow.get("nodetype").toString().equals("1")){
						List<FlowUser> userList = map.get(flowNodeId);
						for (FlowUser user : userList) {
							JSONObject userObj = new JSONObject();
							userObj.put("id", user.get("id"));
							userObj.put("username", user.get("username"));
							userAr.add(userObj);
						}
					}
					result.add(o);
				}
				return true;
			}
		});
		
		return result;
	}
	
	/**
	 * 
	 * @param sender,回退人
	 * @param nodeInstanceId，当前节点实例id
	 * @param toNodes,回退节点参与者( 数据格式如：{"节点id":null} )
	 * @param note,回退原因
	 * @return 0:需要选择回退节点，1:回退成功
	 */
	public int untread(final FlowUser sender,final String nodeInstanceId,final Map<String,String[]> toNodes,final String note){
		final Map<String,Integer> result = new HashMap<String,Integer>();
		Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				FlowInstance.this.start();
				
				int re = FlowInstance.this.getFlowNodeInstance(nodeInstanceId).untread(sender, toNodes, note);
				result.put("result", re);
				return true;
			}
		});
		
		return result.get("result");
	}
	
	/**
	 * 获取节点实例可以回退的节点列表
	 * @param nodeInstanceId，当前节点实例id
	 * @return 返回的JSON格式：[{id:"",flownodename:""}]
	 */
	public JSONArray getUntreadNode(final String nodeInstanceId){
		final JSONArray result = new JSONArray();
		Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				FlowInstance.this.start();
				
				List<Record> list = FlowInstance.this.getFlowNodeInstance(nodeInstanceId).loadUntreadNodes();
				for (Record nodeInstanceRow : list) {
					JSONObject o = new JSONObject();
					o.put("id", nodeInstanceRow.getStr("flownodeid"));
					o.put("flownodename", nodeInstanceRow.getStr("flownodename"));
					result.add(o);
				}
				return true;
			}
		});
		
		return result;
	}
	
	
	/**
	 * 删除流程实例(数据库物理删除）
	 * @return
	 */
	public boolean delete(){
		if(this.isStarted){
			return Db.tx(new IAtom() {

				@Override
				public boolean run() throws SQLException {
					String flowinstanceid = FlowInstance.this.getFlowInstanceRow().getStr("id");
					Db.update("delete from lp_wfi_flow where id",flowinstanceid);
					Db.update("delete from lp_wfi_flownode where flowinstanceid",flowinstanceid);
					Db.update("delete from lp_wfi_param where flowinstanceid",flowinstanceid);
					Db.update("delete from lp_wfi_waitdo where flowinstanceid",flowinstanceid);
					Db.update("delete from lp_wfi_receiver where flownodeinstanceid in (select id from lp_wfi_flownode where flowinstanceid=?)",flowinstanceid);
					Db.update("delete from lp_wfi_operator where flownodeinstanceid in (select id from lp_wfi_flownode where flowinstanceid=?)",flowinstanceid);
					return true;
				}
			});
			
		}
		
		return true;
	}
	
	
	/**
	 * 获取流程参数值
	 * @param parambm，参数编码
	 * @return 如果流程参数编码不存在，都会返回空字符串，不会返回 null值
	 */
	public String getParam(String parambm){
		Record record = null;
		for (Record r : paramList) {
			String _parambm = r.getStr("parambm");
			if(_parambm.equalsIgnoreCase(parambm)){
				record = r;
				break;
			}
		}
		
		if(record == null){
			return "";
		}
		else{
			String val = record.getStr("val");
			if(val == null)
				val = "";
			return val;
		}
	}
	
	/**
	 * 设置流程参数值
	 * @param parambm，参数编码
	 * @param value，参数值
	 */
	public void setParam(String parambm,String value){
		if(StrKit.isBlank(parambm))
			return;
		
		Record record = null;
		for (Record r : paramList) {
			String _parambm = r.getStr("parambm");
			if(_parambm.equalsIgnoreCase(parambm)){
				record = r;
				break;
			}
		}
		
		if(record == null){
			record = new Record();
			record.set("id", String.valueOf(IdKit.nextId()));
			record.set("flowinstanceid", this.flowInstanceRow.get("id"));
			record.set("parambm", parambm);
			record.set("val", value);
			record.set("addtime", new Timestamp(new Date().getTime()));
			paramList.add(record);
			
			if(this.isStarted){
				Db.save("lp_wfi_param", "id", record);
			}
		}
		else{
			record.set("parambm", parambm);
			record.set("val", value);
			if(this.isStarted){
				Db.update("lp_wfi_param", "id", record);
			}
		}
	}
	
	/**
	 * 获取流程实例数据
	 * @return
	 */
	public Record getFlowInstanceRow() {
		return flowInstanceRow;
	}
	/**
	 * 获取流程节点实例列表数据
	 * @return
	 */
	public List<Record> getFlowNodeInstanceList() {
		return flowNodeInstanceList;
	}
	
	/**
	 * 根据节点实例id获取节点实例Record
	 * @param nodeInstanceId
	 * @return
	 */
	public Record getFlowNodeInstanceRow(String nodeInstanceId){
		for (Record r : flowNodeInstanceList) {
			if(r.getStr("id").equals(nodeInstanceId)){
				return r;
			}
		}
		return null;
	}
	
	/**
	 * 根据节点id获取未启动的节点实例Record
	 * @param nodeInstanceId
	 * @return
	 */
	public Record getNotStartFlowNodeInstanceRow(String flowNodeId){
		for (Record r : flowNodeInstanceList) {
			int runstatus = Integer.parseInt(r.get("runstatus").toString());
			if(runstatus == RunStatusEnum.NotStart.value()
					&& r.getStr("flownodeid").equals(flowNodeId)){
				return r;
			}
		}
		return null;
	}
	
	/**
	 * 根据节点id获取未启动的节点实例Record
	 * @param nodeInstanceId
	 * @return
	 */
	public List<Record> getRuningFlowNodeInstanceRows(){
		List<Record> list = new ArrayList<Record>();
		for (Record r : flowNodeInstanceList) {

			int runstatus = Integer.parseInt(r.get("runstatus").toString());
			if(runstatus == RunStatusEnum.Runing.value()){
				list.add(r);
			}		
		}
		return list;
	}
	
	/**
	 * 获取按排序顺序获取最后一个流程节点Record
	 * @return
	 */
	public Record getLastFlowNodeInstanceRow()
	{
		Record row = null;
		int len = this.flowNodeInstanceList.size();
		for (int i = len-1; i >= 0; i--)
		{
			row = this.flowNodeInstanceList.get(i);
			break;		
		}
		return row;
	}

	/**
	 * 获取流程启动者
	 * @return
	 */
	public FlowUser getCreateUser() {
		return createUser;
	}
	
	public String nextSaveVersion(){
		String newSaveVersion = String.valueOf(IdKit.nextId());
		flowInstanceRow.set("saveversion", newSaveVersion);
		if(this.isStarted){
			Db.update("lp_wfi_flow", "id", flowInstanceRow);
		}
		return newSaveVersion;
	}
	
	//内部使用
	private FlowNodeInstance getFlowNodeInstance(String nodeInstanceId){
		Record row = this.getFlowNodeInstanceRow(nodeInstanceId);
		if(row == null){
			throw new FlowException("流程节点实例不存在，节点实例id：" + nodeInstanceId);
		}
		return new FlowNodeInstance(this,row);
	}
	
	/**
	 * 触发流程事件
	 * @param event
	 * @param args
	 */
	public void invokeEvent(FlowEventEnum event,Object... args){
		switch(event)
		{
		case Before_Start:
			runEventMethod("onBeforeStart",args);
			break;
		case After_Start:
			runEventMethod("onAfterStart",args);
			break;
		case Before_Submit:
			runEventMethod("onBeforeNodeSubmit",args);
			break;
		case After_Submit:
			runEventMethod("onAfterNodeSubmit",args);
			break;
		case Before_Untread:
			runEventMethod("onBeforeNodeUntread",args);
			break;
		case After_Untread:
			runEventMethod("onAfterNodeUntread",args);
			break;
		case Before_Complete:
			runEventMethod("onBeforeComplete",args);
			break;
		case After_Complete:
			runEventMethod("onAfterComplete",args);
			break;	
		}
	}
	
	private void runEventMethod(String method,Object... args){
		List<Record> list = this.getFlow().getEvetList();
		for (Record record : list) {
			String javaClass = record.getStr("javaclass");
			Reflect f = Reflect.on(javaClass);
			Class<LPFlowEvent> classObj = f.get();
			if(classObj == null)
				throw new FlowException("自定义流程参数者类需要继承com.blit.lp.bus.flow.i.LPFlowEvent抽象类！");
			f.create().call(method, args);
		}
	}
}
