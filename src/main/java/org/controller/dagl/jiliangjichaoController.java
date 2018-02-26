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

@LPController(controllerkey = "/jiliang_jichao")
// Controller
public class jiliangjichaoController extends Controller {

	public void index() {
		renderTemplate("/org/zhutineirong/jiliang_jichao_list.html");
	}

	public void list(){
		int pageIndex = getParaToInt("pageIndex") + 1;
		int pageSize = getParaToInt("pageSize");
		String select =getList();
		Page<Record> page =  Db.use("jlzz").paginate(pageIndex, pageSize,select,
				DSqlKit.getSql(),DSqlKit.getParamList());
		
		System.out.println("getSQL"+DSqlKit.getSql());
		renderJson(new LPJsonRender(Ret.ok()
				.set("total", page.getTotalRow())
				.set("data", page.getList())));
	
		
	}
	
	public String getList(){
		String select =" select kj.jldbh,kj.jldmc,ky.yhmc,oo.ORG_NAME,vj.JLDLX_MC,vjld.JLDYTDM_MC ,dx.xlmc";
		DSqlKit.init("from kh_jld kj, kh_ydkh  ky,o_org oo ,VW_JLDLX vj ,VW_JLDYTDM vjld,DW_XLXD dx" +
				" where kj.yhbh =  ky.yhbh " );
		DSqlKit.append("and kj.gddwbm = oo.org_no ");
		DSqlKit.append("and  kj.JLDLXDM  = vj.jldlx_bm ");
		DSqlKit.append("and kj.JLDYTDM  = vjld.jldytdm_bm ");
		DSqlKit.append("and kj.XLXDBS  = dx.xlxdbs ");
		
		String jldmc = getPara("jldmc");
		if(StrKit.notBlank(jldmc)){
			DSqlKit.append(" and kj.jldmc = ?",jldmc);
		}
		String jldlbdm = getPara("jldlbdm");
		if(StrKit.notBlank(jldlbdm)){
			DSqlKit.append(" and kj.jldlbdm = ?",jldlbdm);
		}
		String jldytdm = getPara("jldytdm");
		if(StrKit.notBlank(jldytdm)){
			DSqlKit.append(" and kj.jldytdm = ?", jldytdm);
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
				DSqlKit.append(" kj.jldbh desc ");
		
		return select ;
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
		String sql="select * from kh_jld where jldbh=?";
		if(StrKit.notBlank(id)){
		Record data=	Db.use("jlzz").findFirst(sql,id);
		if(data == null)
			throw new SysException("jldbh编辑数据丢失jldbh:" + id);
		setAttr("data", data);
		setAttr("_editguid", id);
		setAttr("_editstate", "edit");
		
		}
		
		renderTemplate("/org/zhutineirong/jiliang_jichao_edit.html");
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
	
	
	public void onsearch(){
		renderTemplate("/org/jiliang_jichao/jiliang_jichao_edit.html");
	}
	
	public void query(){
		String jldytdm = getPara("jldytdm");
		String jldlbdm = getPara("jldlbdm");
		String jldmc = getPara("jldmc");
		
		Record re = new Record();
		re.set("jldytdm", jldytdm);
		re.set("jldlbdm", jldlbdm);
		re.set("jldmc", jldmc);
		renderJson(re);
		
	}
	
}
