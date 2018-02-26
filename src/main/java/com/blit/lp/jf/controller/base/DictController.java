package com.blit.lp.jf.controller.base;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.tools.DSqlKit;
import com.blit.lp.tools.IdKit;
import com.blit.lp.tools.LPRecordKit;
import com.blit.lp.tools.TreeSqlKit;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.LPJsonRender;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

@LPController(controllerkey="/sys/dict")
public class DictController extends Controller {
	public void index(){
		renderTemplate("/sys/base/dict.html");
	}
	
	public void treeData(){
		String sql ="select id,pid,code,val,sortcode from lp_sys_dict order by px asc,addtime asc ";
		List<Record> tree = Db.find(sql);
		renderJson(new LPJsonRender(tree));
	}
	
	public void list(){
		String pid = getPara("pid");
		String key = getPara("key");
		int pageIndex = getParaToInt("pageIndex") + 1;
		int pageSize = getParaToInt("pageSize");
		String select="select * ";
		DSqlKit.init(" from lp_sys_dict where pid = ? ", pid);
		if(StrKit.notBlank(key)){
			String val = "%" +key+ "%";
			DSqlKit.append(" and (code like ? or val like ?)", val, val);
		}
		DSqlKit.append(" order by px asc,addtime asc ");
		Page<Record> page =  Db.paginate(pageIndex, pageSize,select,
				DSqlKit.getSql(),DSqlKit.getParamList());
		renderJson(new LPJsonRender(Ret.ok()
				.set("total", page.getTotalRow())
				.set("data", page.getList())));
	}
	
	@Before(Tx.class)
	public void save(){
		String data = getPara("data");
		JSONArray list = JSONArray.parseArray(data);
		for(int i=0; i < list.size(); i++){
			JSONObject obj = list.getJSONObject(i);
			String _state = obj.getString("_state");
			Record editRow = LPRecordKit.createFromJson(obj, "lp_sys_dict", "");

			if("added".equals(_state)){
				editRow.set("id", String.valueOf(IdKit.nextId()));
				editRow.set("addtime", new Timestamp(new Date().getTime()));
				Db.save("lp_sys_dict", "id", editRow);
			}
			else if("modified".equals(_state)){
				Db.update("lp_sys_dict", "id", editRow);
			}
			else if("removed".equals(_state)){
				String val =  editRow.getStr("id");
				val = val.replaceAll("'", "''");//防止sql漏洞注入
				String sql = TreeSqlKit.GetChildSql("select id", "lp_sys_dict", "id='" +val+ "'", "id", "pid", 5);
				Db.update("delete from lp_sys_dict where id in (select id from (" +sql+ ") tmp)");
			}
		}
		
		renderJson(Ret.ok());
	}
	
	public void getlist(){
		String sortcode = getPara("sortcode");
		List<Record> list = Db.find("select code as id,val text from lp_sys_dict where sortcode=? order by px,id",sortcode);
		renderJson(new LPJsonRender(list));
	}
}
