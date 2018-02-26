package com.blit.lp.jf.controller.security;

import java.sql.Timestamp;
import java.util.Date;

import com.blit.lp.jf.config.LPController;
import com.blit.lp.tools.DSqlKit;
import com.blit.lp.tools.DateKit;
import com.jfinal.core.Controller;
import com.jfinal.ext.LPJsonRender;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;


@LPController(controllerkey="/sys/log")
public class SysLogController extends Controller {
	public void index(){
		renderTemplate("/sys/security/syslog.html");
	}
	
	public void list(){
		String logtype = getPara("logtype");
		String info = getPara("info");
		String time1 = getPara("time1");
		String time2 = getPara("time2");
		int pageIndex = getParaToInt("pageIndex") + 1;
		int pageSize = getParaToInt("pageSize");
		String select="select * ";
		DSqlKit.init(" from lp_sys_log where 1=1 ");
		if(StrKit.notBlank(logtype)){
			DSqlKit.append(" and logtype=?", logtype);
		}
		
		if(StrKit.notBlank(info)){
			DSqlKit.append(" and info like ?", "%" +info+ "%");
		}
		
		if(StrKit.notBlank(time1)){
			DSqlKit.append(" and addtime >=?", new Timestamp(DateKit.parse(time1 + " 00:00:00").getTime()));
		}
		
		if(StrKit.notBlank(time2)){
			DSqlKit.append(" and addtime <=?", new Timestamp(DateKit.parse(time2 + " 23:59:59").getTime()));
		}
		
		DSqlKit.append(" order by addtime desc ");
		Page<Record> page =  Db.paginate(pageIndex, pageSize,select,
				DSqlKit.getSql(),DSqlKit.getParamList());
		renderJson(new LPJsonRender(Ret.ok()
				.set("total", page.getTotalRow())
				.set("data", page.getList())));
	}
}
