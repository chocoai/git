package org.controller.dagl;


import com.alibaba.fastjson.JSONArray;
import com.blit.lp.core.exception.SysException;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.tools.DSqlKit;
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

//@LPController(controllerkey="/jiliangdiancz")
public class JiLiangDianCZController extends Controller {
	
	public void index() {
		renderTemplate("/org/zhutineirong/JiLiangDianCZ_list.html");
	}
	
	public void list() {
		int pageIndex = getParaToInt("pageIndex") + 1;
		int pageSize = getParaToInt("pageSize");
		
		String select = loadFilter();

		Page<Record> page =  Db.use("jlzz").paginate(pageIndex, pageSize,select,
				DSqlKit.getSql(),DSqlKit.getParamList());
		renderJson(new LPJsonRender(Ret.ok()
				.set("total", page.getTotalRow())
				.set("data", page.getList())));
	}
	
	
	
	//查询条件
	private String loadFilter(){

		String select="select * ";
		DSqlKit.init(" from KH_JLD a,VW_JLDLX b,VW_JLDZTDM c  where a.jldztdm = c.jldztdm_bm");
		//DSqlKit.append("and a.jldlxdm=b.jldlx_bm ");
		
		//DSqlKit.append("and d.org_no = a.gddwbm ");
		
		
		String zcbh = getPara("zcbh");
		if(StrKit.notBlank(zcbh)){
			DSqlKit.append(" and a.zcbh = ?",zcbh);
		}
		
		String jldmc = getPara("jldmc");
		if(StrKit.notBlank(jldmc)){
			DSqlKit.append(" and  a.jldmc= ?",jldmc);
		}
		
		String jldlx_mc = getPara("jldlx_mc");
		if(StrKit.notBlank(jldlx_mc)){
			DSqlKit.append(" and a.jldlxdm = ?",jldlx_mc);
		}
		
		String jldytdm = getPara("jldytdm");
		if(StrKit.notBlank(jldytdm)){
			DSqlKit.append(" and a.jldytdm = ?",jldytdm);
		}
		
		String jldztdm = getPara("jldztdm");
		if(StrKit.notBlank(jldztdm)){
			DSqlKit.append(" and a.jldztdm = ?",jldztdm);
		}
		
		String eddy = getPara("eddy");
		if(StrKit.notBlank(eddy)){
			DSqlKit.append(" and a.eddy = ?",eddy);
		}
		
		String eddl = getPara("eddl");
		if(StrKit.notBlank(eddl)){
			DSqlKit.append(" and a.eddl = ?",eddl);
		}
		
		String zhbl = getPara("zhbl");
		if(StrKit.notBlank(zhbl)){
			DSqlKit.append(" and a.zhbl = ?",zhbl);
		}
		
		String gddwbm = getPara("gddwbm");
		if(StrKit.notBlank(gddwbm)){
			DSqlKit.append(" and a.gddwbm = ?",gddwbm);
		}
		//添加排序
		DSqlKit.append(" order by ");
		String sortFields = getPara("sortFields");
		if(StrKit.notBlank(sortFields)){
			JSONArray sortAr = JSONArray.parseArray(sortFields);
			for(int i=0; i < sortAr.size(); i++){
				DSqlKit.append(" " +sortAr.getJSONObject(i).getString("field")+ " " +sortAr.getJSONObject(i).getString("dir")+ ", ");
			}
		}
		DSqlKit.append("a.jldbh asc ");
		
		return select;
	}
	
	public void openSearch(){
		renderTemplate("/sea3000/jiliangdianxuanze.html");
	}
	
	public void query(){
		String zcbh = getPara("zcbh");
		String jldmc = getPara("jldmc");
		String jldlx_mc = getPara("jldlx_mc");
		String jldytdm = getPara("jldytdm");
		String jldztdm_mc = getPara("jldztdm_mc");
		String eddy = getPara("eddy");
		String eddl = getPara("eddl");
		String zhbl = getPara("zhbl");
		String gddwbm = getPara("gddwbm");
		
		Record re = new Record();
		
		re.set("zcbh", zcbh);
		re.set("jldmc", jldmc);
		re.set("jldlx_mc", jldlx_mc);
		re.set("jldytdm", jldytdm);
		re.set("jldztdm_mc", jldztdm_mc);
		re.set("eddy", eddy);
		re.set("eddl", eddl);
		re.set("zhbl", zhbl);
		re.set("gddwbm", gddwbm);
		
		renderJson(re);
	}
	

	public void del(){
		String ids =getPara("ids"); 
		for(String id: ids.split(",")){
			Db.use("jlzz").deleteById("ceshi", "id", id);
		}
		renderJson(Ret.ok());
	}
	
	public void edit(){
		String id=getPara("id");
		String sql="select * from KH_JLD where jldbh=?";
		if(StrKit.notBlank(id)){
		Record data=Db.use("jlzz").findFirst(sql,id);
		if(data == null)
			throw new SysException("busi_route编辑数据丢失，route_no:" + id);
		setAttr("data", data);
		setAttr("_editguid", id);
		setAttr("_editstate", "edit");
		
		}
		
		renderTemplate("/sea3000/jiliangdian_cz.html");
	}
	
	@Before(Tx.class)
	public void save(){
		String _editstate = getPara("_editstate");
		String _editguid = getPara("_editguid");
		Record row = LPRecordKit.createFromRequest("ceshi","data.");
		
		row.set("id", _editguid);
		if("edit".equals(_editstate)){
			Db.use("jlzz").update("ceshi", "id", row);
		}
		renderJson(Ret.ok());
	}
	
	
//	public void gddwSelect(){
//		String sql="select org_no as id, org_name  as text  from o_org where 1=1";
//		List<Record> re = Db.find(sql);
//		renderJson(re);
//	}
//	
//	public void ccjzcbhSelect(){
//		String sql="select CJQBH from SB_YXCJQ  ";
//		List<Record> re = Db.find(sql);
//		renderJson(re);
//	}
}