package org.controller.dagl;

import java.util.List;

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

@LPController(controllerkey="/XiaoShuiDian")
public class XiaoShuiDianController extends Controller{

	public void index(){
		renderTemplate("/org/zhutineirong/XiaoShuiDian_list.html");
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
		DSqlKit.init(" from DW_DC a,VW_DYDJDM b,VW_DCYXZTDM c ,o_org o  " +
				"where a.dydjdm = b.dydjdm_bm ");
		DSqlKit.append("and a.yxztdm = c.dcyxztdm_bm ");
		DSqlKit.append("and a.gddwbm=o.org_no	");
		
		String dcbh = getPara("dcbh");
		if(StrKit.notBlank(dcbh)){
			DSqlKit.append(" and a.dcbh = ?",dcbh);
		}
		String org_name = getPara("org_name");
		if(StrKit.notBlank(org_name)){
			DSqlKit.append(" and a.gddwbm = ?", org_name);
		}
		String dcmc = getPara("dcmc");
		if(StrKit.notBlank(dcmc)){
			DSqlKit.append(" and a.dcmc = ?", dcmc);
		}
		String dydjdm_mc = getPara("dydjdm_mc");
		if(StrKit.notBlank(dydjdm_mc)){
			DSqlKit.append(" and a.dydjdm = ?", dydjdm_mc);
		}
		String dcyxztdm_mc = getPara("dcyxztdm_mc");
		if(StrKit.notBlank(dcyxztdm_mc)){
			DSqlKit.append(" and a.yxztdm = ?", dcyxztdm_mc);
		}
		String dclx = getPara("dclx");
		if(StrKit.notBlank(dclx)){
			DSqlKit.append(" and dclx = ?", dclx);
		}
		String zjrl = getPara("zjrl");
		if(StrKit.notBlank(zjrl)){
			DSqlKit.append(" and a.zjrl = ?", zjrl);
		}
		String gdnylxdm = getPara("gdnylxdm");
		if(StrKit.notBlank(gdnylxdm)){
			DSqlKit.append(" and a.gdnylxdm = ?", gdnylxdm);
		}
		String dcxz = getPara("dcxz");
		if(StrKit.notBlank(dcxz)){
			DSqlKit.append(" and a.dcxz = ?", dcxz);
		}
		String ddgxdm = getPara("ddgxdm");
		if(StrKit.notBlank(ddgxdm)){
			DSqlKit.append(" and a.ddgxdm = ?", ddgxdm);
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
		DSqlKit.append(" a.cjsj asc ");
		
		return select ;
	}
	
	public void openSearch(){
		renderTemplate("/sea3000/changzhanxuanzeX.html");
	}
	
	public void query(){
		String dcmc=getPara("dcmc");
		String org_name=getPara("org_name");
		String dcbh=getPara("dcbh");
		String dclx=getPara("dclx");
		String dydjdm_mc=getPara("dydjdm_mc");
		String dcyxztdm_mc=getPara("dcyxztdm_mc");
		String gdnylxdm=getPara("gdnylxdm");
		String ddgxdm=getPara("ddgxdm");
		String dcxz=getPara("dcxz");
		
		Record re =new Record();
		
		re.set("dcmc", dcmc);
		re.set("org_name", org_name);		
		re.set("dcbh", dcbh);
		re.set("dclx", dclx);
		re.set("dydjdm_mc", dydjdm_mc);
		re.set("dcyxztdm_mc", dcyxztdm_mc);
		re.set("gdnylxdm", gdnylxdm);
		re.set("ddgxdm", ddgxdm);
		re.set("dcxz", dcxz);
		
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
		String sql="select dy.DYDJDM_MC ,zt.DCYXZTDM_MC, dc.DCBH,dc.dcmc,dc.DCXZ,dc.ZJZRL,dc.DDGXDM,o.org_name,dc.JZTS, ny.GDDYLXDM_MC ,dc.CJSJ,dc.CZSJ,dc.TCSJ,dc.DCDZ\n" +
                            "from DW_DC dc ,VW_DYDJDM dy,VW_DCYXZTDM zt, o_org o,VW_GDDYLXDM ny \n" +
                            "where  dcbh='0253990000022750'\n" +
                            "and  dc.DYDJDM=dy.DYDJDM_BM\n" +
                            "and  dc.YXZTDM=zt.DCYXZTDM_Bm\n" +
                            "and  dc.GDDWBM=o.org_no\n" +
                            "and dc.GDNYLXDM=ny.GDDYLXDM_BM";
//		if(StrKit.notBlank(id)){
		Record data=Db.use("jlzz").findFirst(sql);
		if(data == null)
			throw new SysException("busi_route编辑数据丢失，route_no:" + id);
		setAttr("data", data);
//		setAttr("_editguid", id);
		setAttr("_editstate", "edit");
		
//		}
		
		renderTemplate("/org/zhutineirong/xiaoshuidian.html");
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
	
	
	//下拉
	public void gddwSelect(){
		String sql="select org_no as id, org_name  as text  from o_org  where 1=1";
		List<Record> re = Db.use("jlzz").find(sql);
		renderJson(re);
	}

	

}
