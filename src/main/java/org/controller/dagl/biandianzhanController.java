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


@LPController(controllerkey="/biandianzhan1")
public class biandianzhanController extends Controller {

	public void index() {
		renderTemplate("/org/zhutineirong/biandianzhan_list.html");
	}
	
	public void list(){
		
		int pageIndex = getParaToInt("pageIndex") + 1;
		int pageSize = getParaToInt("pageSize");
		String select =getList();
		Page<Record> page =  Db.use("jlzz").paginate(pageIndex, pageSize,select,
				DSqlKit.getSql(),DSqlKit.getParamList());
		
		renderJson(new LPJsonRender(Ret.ok()
				.set("total", page.getTotalRow())
				.set("data", page.getList())));
              //renderTemplate("/org/zhutineirong/biandianzhan_list.html");
	}
	
	public String getList(){
		String select ="  select cz.BDZMC,cz.GDDWBM,dy.DYDJDM_MC,lb.BDZLXDM_MC,yx.BDZYXZTDM_MC,gl.org_name,zj.JZRL,ny.gddylxdm_mc,dc.dcxz,dc.ddgxdm,dc.zjzrl ";
		DSqlKit.init(" from DW_BDZ cz,VW_DYDJDM dy,VW_BDZLXDM lb,VW_BDZYXZTDM yx,o_org gl,DW_JZ zj,VW_GDDYLXDM ny,DW_DC dc where cz.DYDJDM = dy.DYDJDM_BM " );
		
		//where条件
		DSqlKit.append("and cz.BDZLXDM = lb.BDZLXDM_BM ");
		DSqlKit.append("and cz.BDZYXZT = yx.BDZYXZTDM_BM ");
		DSqlKit.append("and cz.GDDWBM = gl.ORG_NO ");
		
		
		//把查询窗口所有的条件添加进来
		String dydjdm = getPara("dydjdm");
		if(StrKit.notBlank(dydjdm)){
			DSqlKit.append(" and cz.dydjdm = ?",dydjdm);
		}
		String bdzlxdm = getPara("bdzlxdm");
		if(StrKit.notBlank(bdzlxdm)){
			DSqlKit.append(" and cz.bdzlxdm = ?", bdzlxdm);
		}
		String bdzmc = getPara("bdzmc");
		if(StrKit.notBlank(bdzmc)){
			DSqlKit.append(" and cz.bdzmc = ?",bdzmc);
		}
		String bdzyxzt = getPara("bdzyxzt");
		if(StrKit.notBlank(bdzyxzt)){
			DSqlKit.append(" and cz.bdzyxzt = ?", bdzyxzt);
		}
		String gddwbm = getPara("gddwbm");
		if(StrKit.notBlank(gddwbm)){
			DSqlKit.append(" and cz.gddwbm = ?", gddwbm);
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
                               
				DSqlKit.append(" cz.bdzbs desc ");  //jldbh主表的主键
		
		return select ;
	}
	
	
	
	
	
	//删除
	public void del(){
		String ids =getPara("ids"); 
		for(String id: ids.split(",")){
			Db.use("jlzz").deleteById("kh_jld", "jldbh", id);
		}
		renderJson(Ret.ok());
	}
	
	
	
	//判断是保存还是编辑的操作
	public void edit(){
		String id=getPara("id");
		String sql="select * from  kh_jld where jldbh=?";
		if(StrKit.notBlank(id)){
		Record data=	Db.use("jlzz").findFirst(sql,id);
		if(data == null)
			throw new SysException("jiliangdian_peibian编辑数据丢失，jldbh:" + id);
		setAttr("data", data);
		setAttr("_editguid", id);
		setAttr("_editstate", "edit");
		
		}
		
		renderTemplate("/org/zhutineirong/jiliangdian_peibian.html");
	}
	
	//保存方法
	@Before(Tx.class)
	public void save(){
		String _editstate = getPara("_editstate");
		String _editguid = getPara("_editguid");
		Record row = LPRecordKit.createFromRequest("kh_jld","data.");
		
		row.set("jldbh", _editguid);
		if("edit".equals(_editstate)){
			Db.use("jlzz").update("kh_jld", "jldbh", row);
		}
		renderJson(Ret.ok());
	}
	
	
	 
	public void query(){
		//查询条件
		String dydjdm_bm = getPara("dydjdm_bm");
		String bdzlxdm_bm = getPara("bdzlxdm_bm");
		String bdzyxztdm_bm = getPara("bdzyxztdm_bm");
		String bdzmc = getPara("bdzmc");
		String org_no = getPara("org_no");
		
		Record re = new Record();
		re.set("dydjdm_bm", dydjdm_bm);
		re.set("bdzlxdm_bm", bdzlxdm_bm);
		re.set("bdzyxztdm_bm", bdzyxztdm_bm);
		re.set("bdzmc", bdzmc);
		re.set("org_no", org_no);
		renderJson(re);
		
	}
	

//选择器下拉框
public void jzdy_query(){
		String sql="select dydjdm_bm as id, dydjdm_mc  as text  from vw_dydjdm where 1=1";
		List<Record> re = Db.use("jlzz").find(sql);
		renderJson(re);
	}

//管理机构下拉框
public void ssgljg_query(){
		String sql="select ORG_NO as id, ORG_NAME  as text  from o_org where 1=1";
		List<Record> re = Db.use("jlzz").find(sql);
		renderJson(re);
	}
public void czlb_query(){
	String sql="select BDZLXDM_BM as id, BDZLXDM_MC  as text  from VW_BDZLXDM where 1=1";
	List<Record> re = Db.use("jlzz").find(sql);
	renderJson(re);
}

public void yxzt_query(){
	String sql="select BDZYXZTDM_BM as id, BDZYXZTDM_MC  as text  from VW_BDZYXZTDM where 1=1";
	List<Record> re = Db.use("jlzz").find(sql);
	renderJson(re);
}

}
