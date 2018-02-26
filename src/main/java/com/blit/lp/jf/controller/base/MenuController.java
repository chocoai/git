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
import com.blit.lp.tools.IdKit;
import com.blit.lp.tools.LPRecordKit;
import com.blit.lp.tools.TreeSqlKit;
import com.jfinal.core.Controller;
import com.jfinal.ext.LPJsonRender;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@LPController(controllerkey="/menu")
public class MenuController extends Controller {
	public void index(){
		renderTemplate("/sys/base/menu.html");
	}
	
	public void treeData(){
		String subsystem = PropKit.use("global.properties").get("subsystem","");
		String subWhere = "1=1";
		if(StrKit.notBlank(subsystem)){
			subWhere=" subsystem='" +subsystem+ "' ";
		}
		
		String sql ="select id,menuname,pid from lp_sys_menu where "+subWhere+" order by px asc,addtime asc ";
		List<Record> tree = Db.find(sql);
		Record root = new Record();
		root.set("id", "-1");
		root.set("menuname", "系统菜单");
		tree.add(root);
		renderJson(new LPJsonRender(tree));
	}
	
	public void list(){
		String subsystem = PropKit.use("global.properties").get("subsystem","");
		String subWhere = "1=1";
		if(StrKit.notBlank(subsystem)){
			subWhere=" subsystem='" +subsystem+ "' ";
		}
		
		String pid = getPara("pid");
		String key = getPara("key");
		int pageIndex = getParaToInt("pageIndex") + 1;
		int pageSize = getParaToInt("pageSize");
		String select="select * ";
		DSqlKit.init(" from lp_sys_menu where pid = ? and " + subWhere, pid);
		
		if(StrKit.notBlank(key)){
			String val = "%" +key+ "%";
			DSqlKit.append(" and (menuname like ?)", val, val);
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
		JSONArray list = JSONArray.parseArray(data);
		for(int i=0; i < list.size(); i++){
			JSONObject obj = list.getJSONObject(i);
			String _state = obj.getString("_state");
			Record editRow = LPRecordKit.createFromJson(obj, "lp_sys_menu", "");

			if("added".equals(_state)){
				editRow.set("id", String.valueOf(IdKit.nextId()));
				editRow.set("addtime", new Timestamp(new Date().getTime()));
				String subsystem = PropKit.use("global.properties").get("subsystem","");
				if(StrKit.notBlank(subsystem)){
					editRow.set("subsystem", subsystem);
				}
				Db.save("lp_sys_menu", "id", editRow);
			}
			else if("modified".equals(_state)){
				Db.update("lp_sys_menu", "id", editRow);
			}
			else if("removed".equals(_state)){
				String val =  editRow.getStr("id");
				val = val.replaceAll("'", "''");//防止sql漏洞注入
				String sql = TreeSqlKit.GetChildSql("select id", "lp_sys_menu", "id='" +val+ "'", "id", "pid", 5);
				Db.update("delete from lp_sys_menu where id in (select id from (" +sql+ ") tmp)");
			}
		}
		
		renderJson(Ret.ok());
	}
	
	public void showRoleMenu(){
		renderTemplate("/sys/base/rolemenu.html");
	}
	
	public void getMenuTreeData(){
		String subsystem = PropKit.use("global.properties").get("subsystem","");
		String subWhere = "1=1";
		if(StrKit.notBlank(subsystem)){
			subWhere=" subsystem='" +subsystem+ "' ";
		}
		
		String sql ="select id,menuname,pid from lp_sys_menu where "+subWhere+" order by px asc,addtime asc ";
		List<Record> list = Db.find(sql);
		renderJson(new LPJsonRender(list));
	}
	
	public void getRoleMenuList(){
		String subsystem = PropKit.use("global.properties").get("subsystem","");
		String subWhere = "1=1";
		if(StrKit.notBlank(subsystem)){
			subWhere = " menuid in (select id from lp_sys_menu where subsystem='" +subsystem+ "') ";
		}
		
		String sql = "select * from lp_sys_rolemenu where roleid=? and " + subWhere;
		List<Record> list = Db.find(sql,getPara("roleid"));
		renderJson(new LPJsonRender(list));
	}
	
	public void roleMenuSave(){
		String subsystem = PropKit.use("global.properties").get("subsystem","");
		String subWhere = "1=1";
		if(StrKit.notBlank(subsystem)){
			subWhere = " menuid in (select id from lp_sys_menu where subsystem='" +subsystem+ "') ";
		}
		
		String roleid = getPara("roleid");
		JSONArray menuAr = JSONArray.parseArray(getPara("menuids"));
		Db.update("delete from lp_sys_rolemenu where roleid=? and " + subWhere, roleid);
		for(int i=0; i < menuAr.size(); i++){
			String menuid = menuAr.getString(i);
			Record row = new Record();
			row.set("id", GuidKit.createGuid());
			row.set("roleid", roleid);
			row.set("menuid", menuid);
			Db.save("lp_sys_rolemenu", "id", row);
		}
		renderJson(Ret.ok());
	}
}
