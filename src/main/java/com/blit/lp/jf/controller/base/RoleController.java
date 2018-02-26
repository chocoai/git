package com.blit.lp.jf.controller.base;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
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

@LPController(controllerkey="/role")
public class RoleController extends Controller {
	public void index(){
		renderTemplate("/sys/base/role.html");
	}
	
	public void list(){
		String pid = getPara("pid");
		String key = getPara("key");
		int pageIndex = getParaToInt("pageIndex") + 1;
		int pageSize = getParaToInt("pageSize");
		String select="select * ";
		DSqlKit.init(" from lp_sys_role where deptid = ? ", pid);
		if(StrKit.notBlank(key)){
			String val = "%" +key+ "%";
			DSqlKit.append(" and rolename like ?", val);
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
		DataSaveKit.miniuiListSave(Db.use(), "lp_sys_role", "id", data, addDefaultRow);
		renderJson(Ret.ok());
	}
	
	public void showRoleUser(){
		renderTemplate("/sys/base/roleuser.html");
	}
	
	//角色机构树型数据
	public void roleDeptTreeData(){
		String sql = "select id,pid,deptname as text,'dept' as type,'icon-dept' as iconcls from lp_sys_dept order by px asc,addtime asc ";
		List<Record> list = Db.find(sql);
		sql = "select id,deptid as pid,rolename as text,'role' as type,'icon-role' as iconcls from lp_sys_role order by px asc,addtime asc ";
		List<Record> list2 = Db.find(sql);
		list.addAll(list2);
		renderJson(new LPJsonRender(list));
	}
	
	public void getRoleUserList(){
		String sql = "select * from lp_sys_userrole where roleid=?";
		List<Record> list = Db.find(sql,getPara("roleid"));
		renderJson(new LPJsonRender(list));
	}
	
	public void roleUserSave(){
		String roleid = getPara("roleid");
		JSONArray userAr = JSONArray.parseArray(getPara("userids"));
		Db.update("delete from lp_sys_userrole where roleid=?", roleid);
		for(int i=0; i < userAr.size(); i++){
			String userid = userAr.getString(i);
			Record row = new Record();
			row.set("id", GuidKit.createGuid());
			row.set("userid", userid);
			row.set("roleid", roleid);
			Db.save("lp_sys_userrole", "id", row);
		}
		renderJson(Ret.ok());
	}
}
