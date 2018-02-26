package com.blit.blit_jlzz.controller;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.blit.lp.core.exception.SysException;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.jf.ext.MiniuiExcelRender;
import com.blit.lp.tools.DSqlKit;
import com.blit.lp.tools.GuidKit;
import com.blit.lp.tools.LPRecordKit;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.LPJsonRender;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.activerecord.tx.TxConfig;

@LPController(controllerkey="/mydemo")
public class mydemoController extends Controller {
	
	public void index() {
		renderTemplate("/blit_jlzz/mydemo_list.html");
	}
	
	public void list() {
		int pageIndex = getParaToInt("pageIndex") + 1;
		int pageSize = getParaToInt("pageSize");
		
		String select = loadFilter();

		Page<Record> page =  Db.paginate(pageIndex, pageSize,select,
				DSqlKit.getSql(),DSqlKit.getParamList());
		renderJson(new LPJsonRender(Ret.ok()
				.set("total", page.getTotalRow())
				.set("data", page.getList())));
	}
	
	
	@Before(Tx.class)
	public void del() {
		String ids = getPara("ids");
		for (String id : ids.split(",")) {
			Db.deleteById("lp_sys_log", "id", id);
		}

		renderJson(Ret.ok());
	}
	
	public void edit() {
		String id = getPara("id");
		if(StrKit.notBlank(id)){
			Record data = Db.findFirst("select * from lp_sys_log where id=?",id);
			if(data == null)
				throw new SysException("lp_sys_log编辑数据丢失，id:" + id);
			
			setAttr("data", data);
			setAttr("_editstate", "edit");
		}
		else{
			id = GuidKit.createGuid();
		}
		
		setAttr("_editguid", id);
		renderTemplate("/blit_jlzz/mydemo_edit.html");
	}
	
	
	@Before(Tx.class)
	public void save() {
		String _editstate = getPara("_editstate");
		String _editguid = getPara("_editguid");
		
		Record row = LPRecordKit.createFromRequest("lp_sys_log","data.");
		row.set("id", _editguid);
		if("edit".equals(_editstate)){
			Db.update("lp_sys_log", "id", row);
		}
		else{
			Db.save("lp_sys_log", "id", row);
		}
		renderJson(Ret.ok());
	}
	
	public void exportdata(){
		String select = loadFilter();
		String sql = select + " " + DSqlKit.getSql();
		List<Record> list = Db.find(sql, DSqlKit.getParamList());
		render(new MiniuiExcelRender(getPara("columns"),list, "数据列表.xls",this.getResponse()));
	}
	
	//查询条件
	private String loadFilter(){
		String select="select * ";
		DSqlKit.init(" from lp_sys_log where 1=1 ");
		
			 
		String key = getPara("txt");
		if(StrKit.notBlank(key)){
			DSqlKit.append(" and info like ?", "%" +key+ "%");
		}
		/*	
		String time1 = getPara("time1");
		if(StrKit.notBlank(time1)){
			DSqlKit.append(" and ts1 >=?", new Timestamp(DateKit.parse(time1 + " 00:00:00").getTime()));
		}
		
		String time2 = getPara("time2");
		if(StrKit.notBlank(time1)){
			DSqlKit.append(" and ts1 <=?", new Timestamp(DateKit.parse(time2 + " 23:59:59").getTime()));
		}*/
		
		//添加排序
		DSqlKit.append(" order by ");
		String sortFields = getPara("sortFields");
		if(StrKit.notBlank(sortFields)){
			JSONArray sortAr = JSONArray.parseArray(sortFields);
			for(int i=0; i < sortAr.size(); i++){
				DSqlKit.append(" " +sortAr.getJSONObject(i).getString("field")+ " " +sortAr.getJSONObject(i).getString("dir")+ ", ");
			}
		}
		DSqlKit.append(" id asc ");
		
		return select;
	}
}