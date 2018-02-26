package com.blit.lp.jf.controller.base;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.tools.DSqlKit;
import com.blit.lp.tools.DataSaveKit;
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

@LPController(controllerkey="/dept")
public class DeptController extends Controller {
	public void index(){
		renderTemplate("/sys/base/dept.html");
	}
	
	public void treeData(){
		String sql ="select id,deptname,pid,depttype,'icon-dept' as iconcls from lp_sys_dept order by px asc,addtime asc ";
		List<Record> tree = Db.find(sql);
		renderJson(new LPJsonRender(tree));
	}
	
	public void list(){
		String pid = getPara("pid");
		String key = getPara("key");
		int pageIndex = getParaToInt("pageIndex") + 1;
		int pageSize = getParaToInt("pageSize");
		String select="select dept.*,dict.val as depttypename ";
		DSqlKit.init(" from lp_sys_dept dept left join lp_sys_dict dict on dept.depttype=dict.code and dict.sortcode='lp.depttype' where dept.pid = ? ", pid);
		if(StrKit.notBlank(key)){
			String val = "%" +key+ "%";
			DSqlKit.append(" and (dept.deptcode like ? or dept.deptname like ?)", val, val);
		}
		DSqlKit.append(" order by dept.px,dept.addtime ");
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
			Record editRow = LPRecordKit.createFromJson(obj, "lp_sys_dept", "");

			if("added".equals(_state)){
				editRow.set("id", String.valueOf(IdKit.nextId()));
				editRow.set("addtime", new Timestamp(new Date().getTime()));
				Db.save("lp_sys_dept", "id", editRow);
			}
			else if("modified".equals(_state)){
				Db.update("lp_sys_dept", "id", editRow);
			}
			else if("removed".equals(_state)){
				String val =  editRow.getStr("id");
				val = val.replaceAll("'", "''");//防止sql漏洞注入
				String sql = TreeSqlKit.GetChildSql("select id", "lp_sys_dept", "id='" +val+ "'", "id", "pid", 5);
				Db.update("delete from lp_sys_dept where id in (select id from (" +sql+ ") tmp)");
			}
		}
		
                Ret.setToOldWorkMode();
		renderJson(Ret.ok());
	}
}
