package com.blit.lp.jf.controller.base;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.tools.DSqlKit;
import com.blit.lp.tools.DataSaveKit;
import com.blit.lp.tools.GuidKit;
import com.jfinal.core.Controller;
import com.jfinal.ext.LPJsonRender;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@LPController(controllerkey="/user")
public class UserController extends Controller {
	public void index(){
		renderTemplate("/sys/base/user.html");
	}
	
	public void list(){
		String pid = getPara("pid");
		String key = getPara("key");
		int pageIndex = getParaToInt("pageIndex") + 1;
		int pageSize = getParaToInt("pageSize");
		String select="select * ";
		DSqlKit.init(" from lp_sys_user where deptid = ? ", pid);
		if(StrKit.notBlank(key)){
			String val = "%" +key+ "%";
			DSqlKit.append(" and (usernum like ? or username like ?)", val, val);
		}
		DSqlKit.append(" order by px asc,addtime asc ");
		Page<Record> page =  Db.paginate(pageIndex, pageSize,select,
				DSqlKit.getSql(),DSqlKit.getParamList());
		renderJson(new LPJsonRender(Ret.ok()
				.set("total", page.getTotalRow())
				.set("data", page.getList())));
	}
	
	public void save(){
		String data = getPara("data");
		Record addDefaultRow = new Record();
		addDefaultRow.set("addtime", new Timestamp(new Date().getTime()));
		DataSaveKit.miniuiListSave(Db.use(), "lp_sys_user", "id", data, addDefaultRow);
                Ret.setToOldWorkMode();
		renderJson(Ret.ok());
	}
	
	public void showUserRole(){
		renderTemplate("/sys/base/userrole.html");
	}
	
	//用户机构树型数据
	public void userDeptTreeData(){
		String sql = "select id,pid,deptname as text,'dept' as type,'icon-dept' as iconcls from lp_sys_dept  order by px asc,addtime asc ";
		List<Record> list = Db.find(sql);
		sql = "select id,deptid as pid,username as text,'user' as type,'icon-user' as iconcls from lp_sys_user where status = '1'  order by px asc,addtime asc ";
		List<Record> list2 = Db.find(sql);
		list.addAll(list2);
		renderJson(new LPJsonRender(list));
	}
	
	public void getUserRoleList(){
		String sql = "select * from lp_sys_userrole where userid=?";
		List<Record> list = Db.find(sql,getPara("userid"));
		renderJson(new LPJsonRender(list));
	}
	
	public void userRoleSave(){
		String userid = getPara("userid");
		JSONArray roleAr = JSONArray.parseArray(getPara("roleids"));
		Db.update("delete from lp_sys_userrole where userid=?", userid);
		for(int i=0; i < roleAr.size(); i++){
			String roleid = roleAr.getString(i);
			Record row = new Record();
			row.set("id", GuidKit.createGuid());
			row.set("userid", userid);
			row.set("roleid", roleid);
			Db.save("lp_sys_userrole", "id", row);
		}
                Ret.setToOldWorkMode();
		renderJson(Ret.ok());
	}
}
