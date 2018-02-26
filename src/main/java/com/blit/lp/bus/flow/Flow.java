package com.blit.lp.bus.flow;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.blit.lp.core.exception.FlowException;
import com.blit.lp.tools.LPCacheKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;


/**
 * 流程建模数据
 * @author dkomj
 *
 */
public class Flow implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String FLOWCACHENAME = "lp_flow";
	private static final Object LOCKOBJ = new Object();
	/**
	 * 根据流程id获取Flow对象（使用最新的流程版本）
	 * @param flowId，流程id
	 * @return
	 */
	public static Flow loadFlow(String flowId) {
		String sql = "select id from lp_wfs_flowversion where" +
				" flowid=? and starttime <= ? and (endtime is null or endtime >= ?) order by versionnum desc";
		String flowVersionId = Db.queryStr(sql, flowId, new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()));
		if(StrKit.isBlank(flowVersionId))
			throw new FlowException("获取流程版本id出错，流程id：" + flowId);
		
		return loadFlowVersion(flowVersionId);
	}
	
	/**
	 * 根据流程版本id获取Flow对象
	 * @param flowVersionId
	 * @return
	 */
	public static Flow loadFlowVersion(String flowVersionId) {
		Flow flow = LPCacheKit.get(FLOWCACHENAME, flowVersionId);
		if(flow == null){
			synchronized (LOCKOBJ){
				flow = LPCacheKit.get(FLOWCACHENAME, flowVersionId);
				
				if(flow == null){
					flow = new Flow();
					flow.init(flowVersionId);
					
					LPCacheKit.put(FLOWCACHENAME, flowVersionId,flow);
				}
			}
		}
		
		return flow;
	}
	
	/**
	 * 清除Flow缓存对象
	 * @param flowVersionId，
	 * @return
	 */
	public static void clearFlowCache(String flowVersionId) {
		if(StrKit.notBlank(flowVersionId)){
			LPCacheKit.remove(FLOWCACHENAME, flowVersionId);
		}
		else{
			LPCacheKit.removeAll(FLOWCACHENAME);
		}
	}
	
	private Flow()
	{
	}
	
	private void init(String flowVersionId){
		String  sql = "select v.*,f.flowname from lp_wfs_flowversion v join lp_wfs_flow f on v.flowid=f.id " +
				"where v.id=?";
		flowRow = Db.findFirst(sql,flowVersionId);
		if(flowRow == null)
			throw new FlowException("获取流程版本数据出错，流程版本id：" + flowVersionId);
		flowRow = new NoModifyRecord(flowRow);
		
		sql = "select * from lp_wfs_event where flowversionid=? order by px,id";
		evetList = Db.find(sql,flowVersionId);
		evetList = NoModifyRecord.convertList(evetList);
		
		sql = "select * from lp_wfs_param where flowversionid=? order by id";
		paramList = Db.find(sql,flowVersionId);
		paramList = NoModifyRecord.convertList(paramList);
		
		sql = "select * from lp_wfs_condition where flowversionid=? order by id";
		conditionList = Db.find(sql,flowVersionId);
		conditionList = NoModifyRecord.convertList(conditionList);
		
		sql = "select n.*,e.drawjson,e.actorjson,e.extjson from lp_wfs_flownode n left join lp_wfs_flownodeext e on n.id=e.id where n.flowversionid=? order by n.id";
		flownodeList = Db.find(sql,flowVersionId);
		flownodeList = NoModifyRecord.convertList(flownodeList);
		
		sql = "select t.*,e.drawjson from lp_wfs_flowtrans t left join lp_wfs_flowtransext e on t.id=e.id where t.flowversionid=? order by t.id";
		transList = Db.find(sql,flowVersionId);
		transList = NoModifyRecord.convertList(transList);
	}
	
	private Record flowRow = null;
	private List<Record> evetList = null;
	private List<Record> paramList = null;
	private List<Record> conditionList = null;
	private List<Record> flownodeList = null;
	private List<Record> transList = null;
	
	private Record startNode = null;
	private Record endNode = null;
	
	
	public Record getStartNode() {
		if(this.startNode == null)
		{
			for (Record record : flownodeList) {
				int nodetype = Integer.parseInt(record.get("nodetype").toString());
				if(nodetype == 0){
					this.startNode = record;
					break;
				}
			}
		}
		
		return this.startNode;
	}
	
	public Record getEndNode() {
		if(this.endNode == null)
		{
			for (Record record : flownodeList) {
				int nodetype = Integer.parseInt(record.get("nodetype").toString());
				if(nodetype == 9){
					this.endNode = record;
					break;
				}
			}
		}
		
		return this.endNode;
	}
	
	public Record getFlowRow() {
		return flowRow;
	}

	public List<Record> getEvetList() {
		return evetList;
	}

	public List<Record> getParamList() {
		return paramList;
	}

	public List<Record> getConditionList() {
		return conditionList;
	}
	
	public List<Record> getFlownodeList() {
		return flownodeList;
	}
	
	public List<Record> getTransList() {
		return transList;
	}
	
	/**
	 * 获取节点实例id获取节点实例数据
	 * @param flowNodeId
	 * @return
	 */
	public Record getFlowNode(String flowNodeId){
		for (Record r : flownodeList) {
			if(r.getStr("id").equals(flowNodeId)){
				return r;
			}
		}
		return null;
	}
}
