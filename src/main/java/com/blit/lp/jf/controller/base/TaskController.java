package com.blit.lp.jf.controller.base;

import java.sql.Timestamp;
import java.util.Date;

import com.blit.lp.jf.config.LPController;
import com.blit.lp.tools.DSqlKit;
import com.blit.lp.tools.DataSaveKit;
import com.jfinal.core.Controller;
import com.jfinal.ext.LPJsonRender;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;


@LPController(controllerkey="/task")
public class TaskController extends Controller {
	public void index(){
		renderTemplate("/sys/base/task.html");
	}
	
	public void list(){
		String key = getPara("key");
		int pageIndex = getParaToInt("pageIndex") + 1;
		int pageSize = getParaToInt("pageSize");
		String select="select * ";
		DSqlKit.init(" from lp_sys_task where 1=1 ");
		if(StrKit.notBlank(key)){
			String val = "%" +key+ "%";
			DSqlKit.append(" and (taskname like ?)", val, val);
		}
		DSqlKit.append(" order by addtime asc ");
		Page<Record> page =  Db.paginate(pageIndex, pageSize,select,
				DSqlKit.getSql(),DSqlKit.getParamList());
		renderJson(new LPJsonRender(Ret.ok()
				.set("total", page.getTotalRow())
				.set("data", page.getList())));
	}
	
	public void save(){
		// TODO 之后应该要处理关联数据
		String data = getPara("data");
		Record addDefaultRow = new Record();
		addDefaultRow.set("addtime", new Timestamp(new Date().getTime()));
		DataSaveKit.miniuiListSave(Db.use(), "lp_sys_task", "id", data, addDefaultRow);
		renderJson(Ret.ok());
	}
}
