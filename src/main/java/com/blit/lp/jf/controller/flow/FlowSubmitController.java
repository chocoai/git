package com.blit.lp.jf.controller.flow;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blit.lp.bus.flow.FlowInstance;
import com.blit.lp.bus.flow.FlowUser;
import com.blit.lp.core.context.User;
import com.blit.lp.core.exception.FlowException;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.tools.DSqlKit;
import com.blit.lp.tools.DateKit;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.LPJson;
import com.jfinal.ext.LPJsonRender;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

//流程流转相关的controller
@LPController(controllerkey="/sys/flowctrl")
public class FlowSubmitController extends Controller {
	public void showwaitdo(){
		renderTemplate("/sys/flow/waitdo.html");
	}
	
	public void waitdocount(){
		String count = Db.findFirst("select count(1) as icount from lp_wfi_waitdo where receiverid=?",User.getCurrUser().get("id")).get("icount").toString();
		renderJson(Ret.ok("count", count));
	}
	
	public void waitdo(){
		int pageIndex = getParaToInt("pageIndex") + 1;
		int pageSize = getParaToInt("pageSize");
		
		String select="select w.*,f.flowname ";
		DSqlKit.init(" from lp_wfi_waitdo w join lp_wfs_flowversion fv on w.flowversionid=fv.id join lp_wfs_flow f on fv.flowid=f.id where 1=1 ");
		
		DSqlKit.append(" and w.receiverid=? ", User.getCurrUser().get("id"));
		
		String key = getPara("key");
		if(StrKit.notBlank(key)){
			String val = "%" +key+ "%";
			DSqlKit.append(" and (f.flowname like ? or w.sender like ? or w.flownodename like ?) ", val,val,val);
		}
		
		String time1 = getPara("time1");
		if(StrKit.notBlank(time1)){
			DSqlKit.append(" and w.addtime >=?", new Timestamp(DateKit.parse(time1 + " 00:00:00").getTime()));
		}
		
		String time2 = getPara("time2");
		if(StrKit.notBlank(time2)){
			DSqlKit.append(" and w.addtime <=?", new Timestamp(DateKit.parse(time2 + " 23:59:59").getTime()));
		}

		//添加排序
		DSqlKit.append(" order by w.id desc");
		
		Page<Record> page =  Db.paginate(pageIndex, pageSize,select,
				DSqlKit.getSql(),DSqlKit.getParamList());
		renderJson(new LPJsonRender(Ret.ok()
				.set("total", page.getTotalRow())
				.set("data", page.getList())));
	}
	
	@Before(Tx.class)
	public void submit(){
		String g_flowinstanceid = getPara("g_flowinstanceid");
		String g_flownodeinstanceid = getPara("g_flownodeinstanceid");
		String submitdata = getPara("submitdata");
		Map<String,String[]> toNodes = null;
		if(StrKit.notBlank(submitdata)){
			toNodes = new HashMap<String, String[]>();
			JSONObject submitDataJSON = JSONObject.parseObject(submitdata);
			for (String key : submitDataJSON.keySet()) {
				String[] userids = new String[]{};
				userids = submitDataJSON.getJSONArray(key).toArray(userids);
				toNodes.put(key, userids);
			}
		}
		
		FlowInstance fi = FlowInstance.load(g_flowinstanceid);
		int resultCode = fi.submit(new FlowUser(), g_flownodeinstanceid, toNodes, false);
		Ret reJson = Ret.ok("resultCode", resultCode);
		if(resultCode == 0){
			JSONArray ar = fi.getNextSubmitNodeActor(g_flownodeinstanceid);
			reJson.set("submitNode", ar);
		}
		renderJson(reJson);
	}
	@Before(Tx.class)
	public void untread(){
		String g_flowinstanceid = getPara("g_flowinstanceid");
		String g_flownodeinstanceid = getPara("g_flownodeinstanceid");
		String untreaddata = getPara("untreaddata");
		String note = getPara("note");
		Map<String,String[]> toNodes = null;
		if(StrKit.notBlank(untreaddata)){
			toNodes = new HashMap<String, String[]>();
			JSONObject untreadDataJSON = JSONObject.parseObject(untreaddata);
			for (String key : untreadDataJSON.keySet()) {
				toNodes.put(key, null);
			}
		}
		
		FlowInstance fi = FlowInstance.load(g_flowinstanceid);
		int resultCode = fi.untread(new FlowUser(), g_flownodeinstanceid, toNodes, note);
		Ret reJson = Ret.ok("resultCode", resultCode);
		if(resultCode == 0){
			JSONArray ar = fi.getUntreadNode(g_flownodeinstanceid);
			reJson.set("untreadNode", ar);
		}
		renderJson(reJson);
	}
	
	public void selectsubmitnode(){
		renderTemplate("/sys/flow/selectsubmitnode.html");
	}
	
	public void selectuntreadnode(){
		renderTemplate("/sys/flow/selectuntreadnode.html");
	}
	
	public void untreadnote(){
		renderTemplate("/sys/flow/untreadnote.html");
	}
	
	public void showlog(){
		String fiid = getPara("i");
		String flowversionid = getPara("flowversionid");
		Record flowIRow = Db.findFirst("select * from lp_wfi_flow where id=?", fiid);
		if(flowIRow == null){
			flowversionid = getPara("flowversionid");
			fiid = "norecordid";
		}
		else{
			fiid = flowIRow.getStr("id");
			flowversionid = flowIRow.getStr("flowversionid");
		}
		
		if(StrKit.isBlank(flowversionid)){
			renderText("");
			return;
		}
		
		Record data = FlowManagerController.loadFlowData(flowversionid);
		List<Record> list = Db.find("select * from lp_wfi_flownode where flowinstanceid=? order by px",fiid);
		
		List<Record> wfs_flownode_list = data.get("wfs_flownode_list");
		List<Record> wfs_flowtrans_list = data.get("wfs_flowtrans_list");
		for (Record record : list) {
			String flownodeid = record.getStr("flownodeid").toString();
			String runstatus = record.get("runstatus").toString();

			Record flowNodeRow = getFlowNodeRow(wfs_flownode_list,flownodeid);
			if(flowNodeRow == null)
				continue;
			
			if(runstatus.equals("0") || runstatus.equals("1")){
				flowNodeRow.set("runstatus", runstatus);
				
			}
			
			JSONArray pidAr = JSONArray.parseArray(record.getStr("pids"));
			for(int j=0; j < pidAr.size() ;j++){
				String preNodeInstanceId = pidAr.getString(j);
				Record record2 = getFlowNodeRow(list,preNodeInstanceId);
				if(record2 == null)
					continue;
				
				String flownodeid2 = record2.getStr("flownodeid").toString();
				String dealstatus2 = record2.get("dealstatus").toString();
				String trantype = dealstatus2.equals("2") ? "1" : "0";
				Record flowtranRow = getFlowtrans(wfs_flowtrans_list,flownodeid2,flownodeid,trantype);
				if(flowtranRow != null)
					flowtranRow.set("runstatus", "1");
			}
		}
		
		String josnText = LPJson.getJson().toJson(data);
		josnText = josnText.replaceAll("<", "&lt;");
		setAttr("josnText",josnText);
		
		List<Record> operatorList = Db.find("select * from lp_wfi_operator where flownodeinstanceid in (select id from lp_wfi_flownode where flowinstanceid=?) order by id",fiid);
		List<Record> receiverList = Db.find("select * from lp_wfi_receiver where flownodeinstanceid in (select id from lp_wfi_flownode where flowinstanceid=?) order by id",fiid);
		
		for (Record record : list) {
			String nodeinstanceid = record.getStr("id");
			List<Record> operatorList2 = new ArrayList<Record>();
			for (Record record2 : operatorList) {
				if(nodeinstanceid.equalsIgnoreCase(record2.getStr("flownodeinstanceid"))){
					operatorList2.add(record2);
				}
			}
			record.set("lp_wfi_operator", operatorList2);
			
			String receiverStr = "";
			for (Record record2 : receiverList) {
				if(nodeinstanceid.equalsIgnoreCase(record2.getStr("flownodeinstanceid"))){
					if(StrKit.notBlank(receiverStr)){
						receiverStr += ",";
					}
					receiverStr += record2.getStr("receiver");
				}
			}
			record.set("lp_wfi_receiver", receiverStr);
		}
		setAttr("logs", list);
		renderTemplate("/sys/flow/flowlog.html");
	}
	
	private Record getFlowNodeRow(List<Record> list,String nodeid) {
		for (Record record : list) {
			if(record.getStr("id").equals(nodeid)){
				return record;
			}
		}
		
		return null;
	}
	
	private Record getFlowtrans(List<Record> list,String fromnodeid,String tonodeid,String trantype) {
		for (Record record : list) {
			if(record.getStr("fromnodeid").equals(fromnodeid)
					&& record.getStr("tonodeid").equals(tonodeid)
					&& record.get("trantype").toString().equals(trantype)){
				return record;
			}
		}
		
		return null;
	}
}
