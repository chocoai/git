package com.blit.lp.jf.controller.demo;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blit.lp.bus.flow.FlowInstance;
import com.blit.lp.bus.flow.FlowUser;
import com.blit.lp.bus.monitor.MonitorInfo;
import com.blit.lp.bus.monitor.MonitorService;
import com.blit.lp.core.exception.SysException;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.jf.ext.MiniuiExcelRender;
import com.blit.lp.tools.DSqlKit;
import com.blit.lp.tools.DateKit;
import com.blit.lp.tools.GuidKit;
import com.blit.lp.tools.LPRecordKit;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.LPJsonRender;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

//表单测试演示示例
@LPController(controllerkey="/sys/demo")
//@Clear(AuthInterceptor.class)  //跳过系统登录及授权认证
public class DemoController extends Controller {
	
	public void index() {
		renderTemplate("/sys/demo/demo.html");
	}
	
	public void miniui_showlist() {
		renderTemplate("/sys/demo/miniui_list.html");
	}
	
	//mini ui 列表查询
	public void miniui_query() {
		int pageIndex = getParaToInt("pageIndex") + 1;
		int pageSize = getParaToInt("pageSize");
		
		
		String select="select * ";
		DSqlKit.init(" from lp_test where 1=1 ");
		
		String txt1 = getPara("txt1");
		if(StrKit.notBlank(txt1)){
			DSqlKit.append(" and txt1 like ?", "%" +txt1+ "%");
		}
		
		String int1 = getPara("int1");
		if(StrKit.notBlank(int1)){
			DSqlKit.append(" and int1 = ?", int1);
		}

		String time1 = getPara("time1");
		if(StrKit.notBlank(time1)){
			DSqlKit.append(" and ts1 >=?", new Timestamp(DateKit.parse(time1 + " 00:00:00").getTime()));
		}
		
		String time2 = getPara("time2");
		if(StrKit.notBlank(time1)){
			DSqlKit.append(" and ts1 <=?", new Timestamp(DateKit.parse(time2 + " 23:59:59").getTime()));
		}
		
		//添加排序
		DSqlKit.append(" order by ");
		String sortFields = getPara("sortFields");
		if(StrKit.notBlank(sortFields)){
			JSONArray sortAr = JSONArray.parseArray(sortFields);
			for(int i=0; i < sortAr.size(); i++){
				DSqlKit.append(" " +sortAr.getJSONObject(i).getString("field")+ " " +sortAr.getJSONObject(i).getString("dir")+ ", ");
			}
		}
		DSqlKit.append("id desc ");
		
		Page<Record> page =  Db.paginate(pageIndex, pageSize,select,
				DSqlKit.getSql(),DSqlKit.getParamList());
		renderJson(new LPJsonRender(Ret.ok()
				.set("total", page.getTotalRow())
				.set("data", page.getList())));
	}
	
	//mini ui 列表删除,事务示例
	@Before(Tx.class)
	public void miniui_listdel() {
		String ids = getPara("ids");
		for (String id : ids.split(",")) {
			Db.deleteById("lp_test", "id", id);
		}

		renderJson(Ret.ok());
	}
	
	//mini ui 列表保存,事务示例
	@Before(Tx.class)
	public void miniui_listsave() {
		String data = getPara("data");
		JSONArray list = JSONArray.parseArray(data);
		for(int i=0; i < list.size(); i++){
			JSONObject obj = list.getJSONObject(i);
			String _state = obj.getString("_state");
			Record editRow = LPRecordKit.createFromJson(obj, "lp_test", "");

			if("added".equals(_state)){
				editRow.set("id", GuidKit.createGuid());
				Db.save("lp_test", "id", editRow);
			}
			else if("modified".equals(_state)){
				Db.update("lp_test", "id", editRow);
			}
			else if("removed".equals(_state)){
				Object val =  editRow.get("id");
				Db.deleteById("lp_test", "id", val);
			}
		}
		
		renderJson(Ret.ok());
	}
	
	//mini ui 打开详情页
	public void miniui_showedit() {
		String id = getPara("id");
		if(StrKit.notBlank(id)){
			Record data = Db.findFirst("select * from lp_test where id=?",id);
			if(data == null)
				throw new SysException("lp_test编辑数据丢失，id:" + id);
			
			setAttr("data", data);
			setAttr("_editstate", "edit");
		}
		else{
			//如果是新增状态，为附件上传或者子表保存等功能，预先创建guid主键
			id = GuidKit.createGuid();
		}
		
		setAttr("_editguid", id);
		renderTemplate("/sys/demo/miniui_edit.html");
	}
	
	//mini ui 保存详情页
	public void miniui_editsave() {
		String _editstate = getPara("_editstate");
		String _editguid = getPara("_editguid");
		
		Record row = LPRecordKit.createFromRequest("lp_test");
		row.set("id", _editguid);
		if("edit".equals(_editstate)){
			Db.update("lp_test", "id", row);
		}
		else{
			Db.save("lp_test", "id", row);
		}
		renderJson(Ret.ok());
	}
	
	public void miniui_export(){
		DSqlKit.init("select *  from lp_test where 1=1 ");
		
		String txt1 = getPara("txt1");
		if(StrKit.notBlank(txt1)){
			DSqlKit.append(" and txt1 like ?", "%" +txt1+ "%");
		}
		
		String int1 = getPara("int1");
		if(StrKit.notBlank(int1)){
			DSqlKit.append(" and int1 = ?", int1);
		}

		String time1 = getPara("time1");
		if(StrKit.notBlank(time1)){
			DSqlKit.append(" and ts1 >=?", new Timestamp(DateKit.parse(time1 + " 00:00:00").getTime()));
		}
		
		String time2 = getPara("time2");
		if(StrKit.notBlank(time1)){
			DSqlKit.append(" and ts1 <=?", new Timestamp(DateKit.parse(time2 + " 23:59:59").getTime()));
		}
		
		//添加排序
		DSqlKit.append(" order by ");
		String sortFields = getPara("sortFields");
		if(StrKit.notBlank(sortFields)){
			JSONArray sortAr = JSONArray.parseArray(sortFields);
			for(int i=0; i < sortAr.size(); i++){
				DSqlKit.append(" " +sortAr.getJSONObject(i).getString("field")+ " " +sortAr.getJSONObject(i).getString("dir")+ ", ");
			}
		}
		DSqlKit.append("id desc ");
		
		List<Record> list = Db.find(DSqlKit.getSql(), DSqlKit.getParamList());

		render(new MiniuiExcelRender(getPara("columns"),list, "导出列表.xls",this.getResponse()));
		
	}
	public void elementui_showlist() {
		renderTemplate("/sys/demo/elementui_list.html");
	}

	public void elementui_showedit() {
		renderTemplate("/sys/demo/elementui_edit.html");
	}
	
	
	public void flowedit(){
		renderTemplate("/sys/flow/edit.html");
	}
	
	public void flowtestdata(){
		Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				for(int i=0; i < 1; i++){
					System.out.print("i:" + i);
					Record r1 = new Record();
					r1.set("id", GuidKit.createGuid());
					r1.set("flowname", "测试流徎" + i);
					r1.set("addtime", new Timestamp(new Date().getTime()));
					Db.save("lp_wfs_flow", r1);
					
					Record r2 = new Record();
					r2.set("id", GuidKit.createGuid());
					r2.set("flowid", r1.get("id"));
					r2.set("versionnum", 1);
					r2.set("userid", "-1");
					r2.set("username", "系统");
					r2.set("starttime", new Timestamp(new Date().getTime()));
					r2.set("addtime", new Timestamp(new Date().getTime()));
					Db.save("lp_wfs_flowversion", r2);
					
					List<String> nodeidList = new ArrayList<String>();
					for(int j=0; j < 10; j++){
						Record r3 = new Record();
						r3.set("id", GuidKit.createGuid());
						r3.set("flowversionid", r2.get("id"));
						if(j == 0){
							r3.set("nodetype", 0);
							r3.set("flownodename", "开始");
						}
						else if(j == 9){
							r3.set("nodetype", 9);
							r3.set("flownodename", "结束");
						}
						else{
							r3.set("nodetype", 1);
							r3.set("flownodename", "节点" + j);
						}
						
						r3.set("embranch", 0);
						r3.set("converge", 0);
						r3.set("px", j);
						r3.set("addtime", new Timestamp(new Date().getTime()));
						Db.save("lp_wfs_flownode", r3);
						nodeidList.add(r3.getStr("id"));
						
						Record r5 = new Record();
						r5.set("id", r3.get("id"));
						r5.set("actorjson", "{\"type\":\"自定义\"}");
						r5.set("extjson", "{\"hq\":{\"enable\":true,\"completetype\":0,\"completenum\":0}}");
						Db.save("lp_wfs_flownodeext", r5);
						
					}
					
					for(int j=0; j < 9; j++){
						Record r4 = new Record();
						r4.set("id", GuidKit.createGuid());
						r4.set("flowversionid", r2.get("id"));
						r4.set("fromnodeid", nodeidList.get(j));
						r4.set("tonodeid", nodeidList.get(j + 1));
						r4.set("trantype", 0);
						r4.set("px", j);
						Db.save("lp_wfs_flowtrans", r4);
					}
				}
				
				return true;
			}
		});
		
		renderNull();
	}
	
	public void flowstart(){
		FlowUser creater = new FlowUser();
		FlowInstance fi = FlowInstance.create("80a02cde900e41e4819916e425e8c700", creater);
		fi.start();
		renderText("OK:" + fi.getFlowInstanceRow().getStr("id"));
	}
	
	public void flowsubmit(){
		String f = getPara("f");
		String n = getPara("n");
		if(StrKit.isBlank(f)){
			Record r = Db.findFirst("select * from lp_wfi_flownode where runstatus=0");
			f = r.getStr("flowinstanceid");
			n = r.getStr("id");
		}
		
		FlowInstance fi = FlowInstance.load(f);
		JSONArray ar = fi.getNextSubmitNodeActor(n);
		System.out.println("参与者：\r\n" + ar.toString());
		int re = fi.submit(new FlowUser(), n, null, false);
		renderText("OK:" + re);
	}
	
	public void flowuntread(){
		String f = getPara("f");
		String n = getPara("n");
		if(StrKit.isBlank(f)){
			Record r = Db.findFirst("select * from lp_wfi_flownode where runstatus=0");
			f = r.getStr("flowinstanceid");
			n = r.getStr("id");
		}
		
		FlowInstance fi = FlowInstance.load(f);
		JSONArray ar = fi.getUntreadNode(n);
		System.out.println("回退接收人：\r\n" + ar.toString());
		int re = fi.untread(new FlowUser(), n, null, "回退原因：");
		renderText("OK:" + re);
	}
	
	public void showMonitorInfo() throws Exception{
		MonitorInfo monitorInfo = MonitorService.getMonitorInfo();
        System.out.println("cpu占有率=" + monitorInfo.getCpuRatio());
        System.out.println("cpu核数=" + monitorInfo.getCpuCount());
        System.out.println("服务器操作系统=" + monitorInfo.getOsName());
        System.out.println("服务器IP=" + monitorInfo.getOsName());
        System.out.println("最大内存=" + monitorInfo.getMaxMemory() + "Kb");
        System.out.println("已用内容=" + monitorInfo.getUsedMemory() + "Kb");
        System.out.println("线程总数=" + monitorInfo.getTotalThread());
        renderJson(monitorInfo);
	}
}
