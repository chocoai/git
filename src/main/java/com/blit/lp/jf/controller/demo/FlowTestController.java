package com.blit.lp.jf.controller.demo;



import com.blit.lp.bus.flow.FlowInstance;
import com.blit.lp.core.exception.SysException;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.jf.interceptor.flow.LPFlowOpen;
import com.blit.lp.jf.interceptor.flow.LPFlowSave;

import com.blit.lp.tools.LPRecordKit;
import com.jfinal.core.Controller;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

//流程测试演示示例
@LPController(controllerkey = "/demo/flowtest")
public class FlowTestController extends Controller {
	@LPFlowOpen(flowid="378168376607899648")
	public void openflow(){
		String i = getPara("i");
		if(StrKit.notBlank(i)){
			Record data = Db.findFirst("select * from lp_test where id = ?", i);
			if(data == null)
				throw new SysException("编辑的业务数据丢失：id:" + i);
			setAttr("data", data);
		}
		renderTemplate("/sys/demo/flowtestform.html");
	}
	
	@LPFlowSave(ischeckdataversion=true)
	public void saveflow(){
		
		String g_iscreateform = getPara("g_iscreateform");
		String g_flowinstanceid = getPara("g_flowinstanceid");
		String g_flownodetype = getPara("g_flownodetype");
		
		Record row = LPRecordKit.createFromRequest("lp_test","data.");
		row.set("id", g_flowinstanceid);

		if("true".equals(g_iscreateform)){
			Db.save("lp_test", "id", row);
		}
		else{
			Db.update("lp_test", "id", row);
		}
		
		if(g_flownodetype.equals("0")){//开始节点设置流程参数
			FlowInstance fi = getAttr("flow");
			fi.setParam("day", row.getStr("txt1"));//设置流程参数
		}
		
	}

}
