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

@LPController(controllerkey="/zhongduanfukong")
public class zhongduanFCController extends Controller  {

	public void index() {
		renderTemplate("/org/zhutineirong/zhongduan_list.html");
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
	
	
	public void del(){
		String ids =getPara("ids"); 
		for(String id: ids.split(",")){
			Db.use("jlzz").deleteById("ceshi", "id", id);
		}
		renderJson(Ret.ok());
	}

	//查询条件
	private String loadFilter(){
		String select =" select dy.sbxhdm,dy.byqmc,dy.yxbyqbs,vb.byqxhdm_mc,dt.tqmc,dw.xlmc,oo.org_name,vwb.BYQLX_MC,vs.SBYXZTDM_MC";
		DSqlKit.init("from dw_yxbyq dy,VW_BYQXHDM vb,DW_XLXD dw,DW_TQ dt,o_org oo,VW_BYQLX vwb,VW_SBYXZTDM vs" +
				" where dy.sbxhdm =  vb.byqxhdm_bm " );
		DSqlKit.append("and dy.tqbs=dt.tqbs ");
		DSqlKit.append("and dy.gddwbm = oo.ORG_NO ");
		DSqlKit.append("and dy.SBLXDM= vwb.BYQLX_BM");
		
		
		String yxbyqbs = getPara("yxbyqbs");
		if(StrKit.notBlank(yxbyqbs)){
			DSqlKit.append(" and yxbyqbs = ?",yxbyqbs);
		}
		String byqmc = getPara("byqmc");
		if(StrKit.notBlank(byqmc)){
			DSqlKit.append(" and byqmc = ?", byqmc);
		}
		String xlmc = getPara("xlmc");
		if(StrKit.notBlank(xlmc)){
			DSqlKit.append(" and xlmc = ?", xlmc);
		}
		String byqlx_bm = getPara("byqlx_bm");
		if(StrKit.notBlank(byqlx_bm)){
			DSqlKit.append(" and byqlx_bm = ?",byqlx_bm);
		}
		String org_no = getPara("org_no");
		if(StrKit.notBlank(org_no)){
			DSqlKit.append(" and org_no = ?",org_no);
		}
		String sbyxztdm_bm = getPara("sbyxztdm_bm");
		if(StrKit.notBlank(sbyxztdm_bm)){
			DSqlKit.append(" and sbyxztdm_bm = ?", sbyxztdm_bm);
		}
		//排序
		DSqlKit.append(" order by ");
		String sortFields = getPara("sortFields");
		if(StrKit.notBlank(sortFields)){
			JSONArray sortAr = JSONArray.parseArray(sortFields);
			for(int i=0; i < sortAr.size(); i++){
				DSqlKit.append(" " +sortAr.getJSONObject(i).getString("field")+ " " +sortAr.getJSONObject(i).getString("dir")+ ", ");
			}
		}
		DSqlKit.append(" dy.yxbyqbs desc ");

return select ;
}
	public void edit(){
		String id=getPara("id");
		String sql="select * from sb_yxzd where zdbs=?";
		if(StrKit.notBlank(id)){
		Record data=	Db.use("jlzz").findFirst(sql,id);
		if(data == null)
			throw new SysException("i_yxzd_zc编辑数据丢失zdbs:" + id);
		setAttr("data", data);
		setAttr("_editguid", id);
		setAttr("_editstate", "edit");
		
		}
		
		renderTemplate("/org/zhutineirong/zhongduanfukong.html");
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
	
	
	public void onSearcj(){
		renderTemplate("/org/bocs_gongbian/bianyagongbian_edit.html");
	}
	
	public void query(){
		String yxbyqbs=getPara("yxbyqbs");
		String byqmc=getPara("byqmc");
		String xlmc=getPara("xlmc");
		String byqlx_bm=getPara("byqlx_bm");
		String org_no=getPara("org_no");
		String sbyxztdm_bm=getPara("sbyxztdm_bm");
		Record re =new Record();
		re.set("yxbyqbs", yxbyqbs);
		re.set("byqmc", byqmc);
		re.set("xlmc", xlmc);
		re.set("byqlx_bm", byqlx_bm);
		re.set("org_no", org_no);
		re.set("sbyxztdm_bm", sbyxztdm_bm);
		renderJson(re);
		
	}
	
	//下拉
		public void yxztSelect(){
			String sql="select sbyxztdm_bm as id, sbyxztdm_mc  as text  from vw_sbyxztdm where 1=1";
			List<Record> re = Db.use("jlzz").find(sql);
			renderJson(re);
		}
		//下拉
	    public void gddwSelect() {
		   String sql = "select org_no as id, org_name  as text  from o_org where 1=1";
		   List<Record> re = Db.use("jlzz").find(sql);
		  renderJson(re);
	    }
		//下拉
		public void byqlxSelect(){
			String sql="select byqlx_bm as id, byqlx_mc  as text  from vw_byqlx where 1=1";
			List<Record> re = Db.use("jlzz").find(sql);
			renderJson(re);
	    }

}
