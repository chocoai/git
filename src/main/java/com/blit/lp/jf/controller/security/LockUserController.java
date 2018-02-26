package com.blit.lp.jf.controller.security;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.blit.lp.bus.security.Audit;
import com.blit.lp.bus.security.LockUser;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.tools.DSqlKit;
import com.blit.lp.tools.DataSaveKit;
import com.blit.lp.tools.DateKit;
import com.blit.lp.tools.LPCacheKit;
import com.jfinal.core.Controller;
import com.jfinal.ext.LPJsonRender;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@LPController(controllerkey="/sys/lockuser")
public class LockUserController extends Controller {
	public void index(){
		renderTemplate("/sys/security/lockuser.html");
	}
	
	public void list(){
		List<Record> list = new ArrayList<Record>();
		List<String> keyList = LPCacheKit.getKeys(Audit.USERLOCKCACHENAME);
		for (String key : keyList) {
			LockUser lockUser = LPCacheKit.get(Audit.USERLOCKCACHENAME, key);
			Record row = new Record();
			row.set("usernum", lockUser.getUserNum());
			long t = lockUser.getLockStartTime();
			row.set("lockstarttime", DateKit.format(new Date(t)));
			list.add(row);
		}
		renderJson(new LPJsonRender(list));
	}
	
	public void clearLockUser(){
		String data = getPara("data");
		for (String userCode : data.split(",")) {
			LPCacheKit.remove(Audit.USERLOCKCACHENAME, userCode);
		}
		
		renderJson(Ret.ok());
	}
}
