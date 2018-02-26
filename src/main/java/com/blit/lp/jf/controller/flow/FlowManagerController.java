package com.blit.lp.jf.controller.flow;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blit.lp.bus.flow.Flow;
import com.blit.lp.core.context.User;
import com.blit.lp.core.exception.FlowException;
import com.blit.lp.core.exception.SysException;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.jf.interceptor.accesscontrol.LPSysAdminAccess;
import com.blit.lp.tools.DSqlKit;
import com.blit.lp.tools.DateKit;
import com.blit.lp.tools.IdKit;
import com.blit.lp.tools.LPRecordKit;
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
import com.jfinal.render.RenderException;
import com.jfinal.upload.UploadFile;

//流程设计器相关的Controller
@LPController(controllerkey="/sys/flow")
@LPSysAdminAccess()
public class FlowManagerController extends Controller {
	public void index(){
		renderTemplate("/sys/flow/list.html");
	}
	
	public void list(){
		int pageIndex = getParaToInt("pageIndex") + 1;
		int pageSize = getParaToInt("pageSize");
		
		String select="select f.*,v.username,v.id as flowversionid ";
		DSqlKit.init(" from lp_wfs_flow f join lp_wfs_flowversion v on f.id=v.flowid where 1=1 ");
		
		String key = getPara("key");
		if(StrKit.notBlank(key)){
			DSqlKit.append(" and f.flowname like ?", "%" +key+ "%");
		}
		
		//添加排序
		DSqlKit.append(" order by f.id");
		
		Page<Record> page =  Db.paginate(pageIndex, pageSize,select,
				DSqlKit.getSql(),DSqlKit.getParamList());
		renderJson(new LPJsonRender(Ret.ok()
				.set("total", page.getTotalRow())
				.set("data", page.getList())));
	}
	
	public void newFlow(){
		renderTemplate("/sys/flow/new.html");
	}
	
	public void edit(){
		Record row = loadFlowData(getPara("flowversionid"));
		String josnText = LPJson.getJson().toJson(row);
		josnText = josnText.replaceAll("<", "&lt;");
		setAttr("josnText",josnText);
		renderTemplate("/sys/flow/edit.html");
	}
	
	public static Record loadFlowData(String flowversionid){
		Record row = Db.findFirst("select f.*,v.id as flowversionid,v.saveversion,v.openurl from lp_wfs_flow f join lp_wfs_flowversion v on f.id=v.flowid where v.id=?",flowversionid);
		if(row == null)
			throw new FlowException("编辑的流程数据丢失，flowversionid:" + flowversionid);
		
		List<Record> list = Db.find("select * from lp_wfs_param where flowversionid=? order by id",flowversionid);
		row.set("wfs_param_list", list);
		
		List<Record> list2 = Db.find("select * from lp_wfs_condition where flowversionid=? order by id",flowversionid);
		row.set("wfs_condition_list", list2);
		
		List<Record> list3 = Db.find("select * from lp_wfs_event where flowversionid=? order by px,id",flowversionid);
		row.set("wfs_event_list", list3);
		
		List<Record> list4 = Db.find("select n.*,ne.drawjson,ne.actorjson,ne.extjson from lp_wfs_flownode n join lp_wfs_flownodeext ne on n.id=ne.id where n.flowversionid=? order by n.id",flowversionid);
		row.set("wfs_flownode_list", list4);
		for (Record r : list4) {
			r.set("drawjson", JSONObject.parseObject(r.getStr("drawjson")));
			r.set("actorjson", JSONObject.parseObject(r.getStr("actorjson")));
			r.set("extjson", JSONObject.parseObject(r.getStr("extjson")));
		}
		
		List<Record> list5 = Db.find("select t.*,te.drawjson from lp_wfs_flowtrans t join lp_wfs_flowtransext te on t.id=te.id where t.flowversionid=? order by t.id",flowversionid);
		row.set("wfs_flowtrans_list", list5);
		for (Record r : list5) {
			r.set("drawjson", JSONObject.parseObject(r.getStr("drawjson")));
		}
		
		return row;
	}
	
	@Before(Tx.class)
	public void del(){
		String ids = getPara("ids");
		for (String id : ids.split(",")) {
			String sql = "delete from lp_wfs_flow where id=?";
			Db.update(sql, id);
			
			sql = "delete from lp_wfs_param where flowversionid in (select id from lp_wfs_flowversion where flowid=?)";
			Db.update(sql, id);
			
			sql = "delete from lp_wfs_condition where flowversionid in (select id from lp_wfs_flowversion where flowid=?)";
			Db.update(sql, id);
			
			sql = "delete from lp_wfs_event where flowversionid in (select id from lp_wfs_flowversion where flowid=?)";
			Db.update(sql, id);
			
			sql = "delete from lp_wfs_flowtransext where id in (select id from lp_wfs_flowtrans where flowversionid in (select id from lp_wfs_flowversion where flowid=?))";
			Db.update(sql, id);
			
			sql = "delete from lp_wfs_flowtrans where flowversionid in (select id from lp_wfs_flowversion where flowid=?)";
			Db.update(sql, id);
			
			sql = "delete from lp_wfs_flownodeext where id in (select id from lp_wfs_flownode where flowversionid in (select id from lp_wfs_flowversion where flowid=?))";
			Db.update(sql, id);
			
			sql = "delete from lp_wfs_flownode where flowversionid in (select id from lp_wfs_flowversion where flowid=?)";
			Db.update(sql, id);
			
			sql = "delete from lp_wfi_waitdo where flowversionid in (select id from lp_wfs_flowversion where flowid=?)";
			Db.update(sql, id);
			
			sql = "delete from lp_wfs_flowversion where flowid=?";
			Db.update(sql, id);
		}
		renderJson(Ret.ok());
	}
	
	public void createId(){
		int num = getParaToInt("num", 1);
		List<String> idList = new ArrayList<String>();
		for(int i=0; i < num; i++){
			idList.add(String.valueOf(IdKit.nextId()));
		}
		renderJson(Ret.ok("data", idList)); 
	}
	
	public void edit_actorjson(){
		renderTemplate("/sys/flow/edit_actorjson.html");
	}
	
	public void edit_actorjson_edit(){
		renderTemplate("/sys/flow/edit_actorjson_edit.html");
	}
	
	public void edit_extjson(){
		renderTemplate("/sys/flow/edit_extjson.html");
	}
	
	public void edit_flowparam(){
		renderTemplate("/sys/flow/edit_flowparam.html");
	}
	
	public void edit_flowcondition(){
		renderTemplate("/sys/flow/edit_flowcondition.html");
	}
	
	public void edit_flowevent(){
		renderTemplate("/sys/flow/edit_flowevent.html");
	}
	
	@Before(Tx.class)
	public void newSave(){
		Record newRow = new Record();
		String id = String.valueOf(IdKit.nextId());
		String flowversionid = String.valueOf(IdKit.nextId());
		newRow.set("id", id);
		newRow.set("flowname", getPara("flowname"));
		newRow.set("addtime", new Timestamp(new Date().getTime()));
		Db.save("lp_wfs_flow", newRow);
		
		newRow = new Record();
		newRow.set("id", flowversionid);
		newRow.set("flowid", id);
		newRow.set("versionnum", 1);
		newRow.set("userid", User.getCurrUser().get("id"));
		newRow.set("username", User.getCurrUser().get("username"));
		newRow.set("starttime", new Timestamp(DateKit.parse("1900-01-01", "yyyy-MM-dd").getTime()));
		newRow.set("saveversion", String.valueOf(IdKit.nextId()));
		newRow.set("openurl", "/xxx/xxx?i=#(g_flowinstanceid)&n=#(g_flownodeinstanceid)");
		
		newRow.set("addtime", new Timestamp(new Date().getTime()));
		Db.save("lp_wfs_flowversion", newRow);
		
		renderJson(Ret.ok("flowversionid", flowversionid).set("flowname", getPara("flowname")));
	}
	
	@Before(Tx.class)
	public void flowSave(){
		JSONObject obj = JSONObject.parseObject(getPara("data"));
		Record wfs_flowversionRow = LPRecordKit.createFromJson(obj, "lp_wfs_flowversion");
		wfs_flowversionRow.set("id", obj.getString("flowversionid"));
		String sqlCheck = "select id from lp_wfs_flowversion where id=? and saveversion=?";
		if(Db.findFirst(sqlCheck,wfs_flowversionRow.get("id"),wfs_flowversionRow.get("saveversion")) == null){
			renderJson(Ret.fail("msg", "流程已经被他人保存，请刷新数据！"));
			return;
		}
		
		String newSaveVersion = String.valueOf(IdKit.nextId());
		wfs_flowversionRow.set("saveversion", newSaveVersion);
		Db.update("lp_wfs_flowversion",wfs_flowversionRow);
		
		Record wfs_flowRow = LPRecordKit.createFromJson(obj, "lp_wfs_flow");
		Db.update("lp_wfs_flow",wfs_flowRow);
		
		String[] childTables = new String[]{
				"wfs_param_list","wfs_condition_list","wfs_event_list",
				"wfs_flownode_list","wfs_flowtrans_list"};
		
		for (int i = 0; i < childTables.length; i++) {
			String proName = childTables[i];
			String tableName = "lp_" + proName.replace("_list", "");
			JSONArray list = obj.getJSONArray(proName);
			JSONObject item = null;
			Record itemRow = null;
			for(int j=0; j < list.size(); j++){
				item = list.getJSONObject(j);
				itemRow = LPRecordKit.createFromJson(item, tableName);

				Object o = item.get("__state");
				String __state = o == null ? "edit" : o.toString();
				if(__state.equalsIgnoreCase("add")){
					itemRow.set("addtime", new Timestamp(new Date().getTime()));
					Db.save(tableName, itemRow);
					
					if(tableName.equalsIgnoreCase("lp_wfs_flownode")){
						Record rowTmp = LPRecordKit.createFromJson(item, "lp_wfs_flownodeext");
						rowTmp.set("addtime", new Timestamp(new Date().getTime()));
						Db.save("lp_wfs_flownodeext", rowTmp);
					}
					else if(tableName.equalsIgnoreCase("lp_wfs_flowtrans")){
						Record rowTmp = LPRecordKit.createFromJson(item, "lp_wfs_flownodeext");
						rowTmp.set("addtime", new Timestamp(new Date().getTime()));
						Db.save("lp_wfs_flowtransext", rowTmp);
					}
				}
				else if(__state.equalsIgnoreCase("edit")){
					Db.update(tableName, itemRow);
					
					if(tableName.equalsIgnoreCase("lp_wfs_flownode")){
						Record rowTmp = LPRecordKit.createFromJson(item, "lp_wfs_flownodeext");
						Db.update("lp_wfs_flownodeext", rowTmp);
					}
					else if(tableName.equalsIgnoreCase("lp_wfs_flowtrans")){
						Record rowTmp = LPRecordKit.createFromJson(item, "lp_wfs_flownodeext");
						Db.update("lp_wfs_flowtransext", rowTmp);
					}
				}
				else if(__state.equalsIgnoreCase("del")){
					Db.delete(tableName, itemRow);
					
					if(tableName.equalsIgnoreCase("lp_wfs_flownode")){
						Record rowTmp = LPRecordKit.createFromJson(item, "lp_wfs_flownodeext");
						Db.delete("lp_wfs_flownodeext", rowTmp);
					}
					else if(tableName.equalsIgnoreCase("lp_wfs_flowtrans")){
						Record rowTmp = LPRecordKit.createFromJson(item, "lp_wfs_flownodeext");
						Db.delete("lp_wfs_flowtransext", rowTmp);
					}
				}
				else{
					throw new SysException("未知__state编辑类型：" + __state);
				}
			}
		}
		
		Flow.clearFlowCache(obj.getString("flowversionid"));
		renderJson(Ret.ok("msg", newSaveVersion));
	}
	
	public void actortree(){
		List<Ret> list = new ArrayList<Ret>();
		List<Record> deptList = Db.find("select * from lp_sys_dept order by px,addtime");
		for (Record record : deptList) {
			Ret ret = Ret.create("id", record.get("id"));
			ret.set("pid", record.get("pid"));
			ret.set("text", record.get("deptname"));
			ret.set("nodetype", "机构");
			ret.set("iconCls", "icon-dept");
			list.add(ret);
		}
		
		List<Record> roleList = Db.find("select * from lp_sys_role order by px,addtime");
		for (Record record : roleList) {
			Ret ret = Ret.create("id", record.get("id"));
			ret.set("pid", record.get("deptid"));
			ret.set("text", record.get("rolename"));
			ret.set("nodetype", "角色");
			ret.set("iconCls", "icon-role");
			list.add(ret);
		}
		
		List<Record> userList = Db.find("select * from lp_sys_user order by px,addtime");
		for (Record record : userList) {
			Ret ret = Ret.create("id", record.get("id"));
			ret.set("pid", record.get("deptid"));
			ret.set("text", record.get("username"));
			ret.set("nodetype", "人员");
			ret.set("iconCls", "icon-user");
			list.add(ret);
		}
		
		renderJson(list);
	}
	
	public void exportsql() throws IOException{
		String ids = getPara("ids");
		List<Record> list = new ArrayList<Record>();
		for (String id : ids.split(",")) {
			Record data = Db.findFirst("select * from lp_wfs_flow where id=?",id);
			List<Record> lp_wfs_flowversion = Db.find("select * from lp_wfs_flowversion where flowid=? order by id",id);
			data.set("lp_wfs_flowversion", lp_wfs_flowversion);
			for (Record record : lp_wfs_flowversion) {
				String flowversionid = record.getStr("id");
				List<Record> lp_wfs_param = Db.find("select * from lp_wfs_param where flowversionid=? order by id", flowversionid);
				data.set("lp_wfs_param", lp_wfs_param);
				
				List<Record> lp_wfs_condition = Db.find("select * from lp_wfs_condition where flowversionid=? order by id", flowversionid);
				data.set("lp_wfs_condition", lp_wfs_condition);
				
				List<Record> lp_wfs_event = Db.find("select * from lp_wfs_event where flowversionid=? order by id", flowversionid);
				data.set("lp_wfs_event", lp_wfs_event);
				
				List<Record> lp_wfs_flowtrans = Db.find("select * from lp_wfs_flowtrans where flowversionid=? order by id", flowversionid);
				data.set("lp_wfs_flowtrans", lp_wfs_flowtrans);
				
				List<Record> lp_wfs_flowtransext = Db.find("select * from lp_wfs_flowtransext where id in (select id from lp_wfs_flowtrans where flowversionid=?) order by id", flowversionid);
				data.set("lp_wfs_flowtransext", lp_wfs_flowtransext);
				
				List<Record> lp_wfs_flownode = Db.find("select * from lp_wfs_flownode where flowversionid=? order by id", flowversionid);
				data.set("lp_wfs_flownode", lp_wfs_flownode);
				
				List<Record> lp_wfs_flownodeext = Db.find("select * from lp_wfs_flownodeext where id in (select id from lp_wfs_flownode where flowversionid=?) order by id", flowversionid);
				data.set("lp_wfs_flownodeext", lp_wfs_flownodeext);
			}
			list.add(data);
		}
		
		renderNull();
		
		String jsonTxt = LPJson.getJson().toJson(list);
		byte[] bs = jsonTxt.getBytes("utf-8");
		HttpServletResponse response = getResponse();
		response.addHeader("Content-disposition", 
                "attachment; filename=exportsql.txt");
		response.setContentType("application/octet-stream");
        response.setContentLength(bs.length);
        response.getOutputStream().write(bs);
	}
	
	@Before(Tx.class)
	public void importsql() throws IOException{
		UploadFile file =  getFile();
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file.getFile()), "utf-8");       
		BufferedReader br = new BufferedReader(isr);       
		StringBuffer sb = new StringBuffer();
		String lineTxt = null;
		while ((lineTxt = br.readLine()) != null) {         
			sb.append("\r\n").append(lineTxt);       
		}       
		br.close();
		String[] tableNames = new String[]{"lp_wfs_param","lp_wfs_condition","lp_wfs_event","lp_wfs_flowversion"
				,"lp_wfs_flowtrans","lp_wfs_flowtransext","lp_wfs_flownode","lp_wfs_flownodeext"};
		
		JSONArray ar = JSONArray.parseArray(sb.toString());
		for (int i=0; i < ar.size(); i++) {
			JSONObject o = ar.getJSONObject(i);
			Record r = LPRecordKit.createFromJson(o, "lp_wfs_flow", "");
			Db.deleteById("lp_wfs_flow", "id", r.getStr("id"));
			Db.save("lp_wfs_flow", r);
			for (String tableName : tableNames) {
				importTable(o,tableName);
			}
		}
		
		renderJson(Ret.ok());
	}
	
	private void importTable(JSONObject o,String tableName){
		JSONArray ar = o.getJSONArray(tableName);
		for (int i=0; i < ar.size(); i++) {
			JSONObject obj = ar.getJSONObject(i);
			Record r = LPRecordKit.createFromJson(obj, tableName, "");
			Db.deleteById(tableName, "id", r.getStr("id"));
			Db.save(tableName, r);
		}
	}
}
