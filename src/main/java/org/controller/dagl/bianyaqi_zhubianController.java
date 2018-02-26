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


@LPController(controllerkey="/bianyaqi_zhubian")
public class bianyaqi_zhubianController extends Controller {

	public void index() {
		renderTemplate("/org/zhutineirong/bianyaqi_zhubian_list.html");
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
	}
	
	public String getList(){
		String select =" select  a.byqzcbh,a.byqmc, b.org_name,c.tqmc ";
		DSqlKit.init("from DW_YXBYQ a,o_org b,DW_TQ c where 1=1" );
		
		//kj表的gddwbm字段等于oo表的org_no字段
		DSqlKit.append("and a.gddwbm = b.org_no ");
		//DSqlKit.append("and  kj.JLDLXDM  = vj.jldlx_bm ");
		//DSqlKit.append("and kj.JLDYTDM  = vjld.jldytdm_bm ");
		
		//接受要查询的条件字段
		String org_no = getPara("org_no");
		if(StrKit.notBlank(org_no)){
			DSqlKit.append(" and b.org_no = ?",org_no);
		}
		
//		String jldlbdm = getPara("jldlbdm");
//		if(StrKit.notBlank(jldlbdm)){
//			DSqlKit.append(" and kj.jldlbdm = ?",jldlbdm);
//		}
//		String jldytdm = getPara("jldytdm");
//		if(StrKit.notBlank(jldytdm)){
//			DSqlKit.append(" and kj.jldytdm = ?", jldytdm);
//		}
//		
		
		//添加排序
				DSqlKit.append(" order by ");
				String sortFields = getPara("sortFields");
				if(StrKit.notBlank(sortFields)){
					JSONArray sortAr = JSONArray.parseArray(sortFields);
					for(int i=0; i < sortAr.size(); i++){
						DSqlKit.append(" " +sortAr.getJSONObject(i).getString("field")+ " " +sortAr.getJSONObject(i).getString("dir")+ ", ");
					}
				}
				DSqlKit.append(" a.yxbyqbs desc ");  //jldbh主表的主键
		
		return select ;
	}
	
	//删除
	public void del(){
		String ids =getPara("ids"); 
		for(String id: ids.split(",")){
			Db.use("jlzz").deleteById("dw_yxbyq", "yxbyqbs", id);
		}
		renderJson(Ret.ok());
	}
	
	//判断是保存还是编辑的操作
	public void edit(){
		String id=getPara("id");
		String sql="select * from  dw_yxbyq where yxbyqbs=?";
		if(StrKit.notBlank(id)){
		Record data=	Db.use("jlzz").findFirst(sql,id);
		if(data == null)
			throw new SysException("bianyaqi_zhubian编辑数据丢失，yxbyqbs:" + id);
		setAttr("data", data);
		setAttr("_editguid", id);
		setAttr("_editstate", "edit");
		
		}
		
		renderTemplate("/org/zhutineirong/bianyaqi_zhubian.html");
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
	
	//list页面传过来的“onSearcj”动作
	public void onSearcj(){
		renderTemplate("/sea3000/bianyaqixuanze.html");
	}
	
	//xuanze 页面传过来的query动作。
	public void query(){
		//查询条件
		String jldytdm = getPara("jldytdm");
		String jldlbdm = getPara("jldlbdm");
		String jldmc = getPara("jldmc");
		
		Record re = new Record();
		re.set("jldytdm", jldytdm);
		re.set("jldlbdm", jldlbdm);
		re.set("jldmc", jldmc);
		renderJson(re);
		
	}
	

//选择器下拉框
public void gddw_query(){
		String sql="select JLDYTDM_BM as id, JLDYTDM_MC  as text  from VW_JLDYTDM where 1=1";
		List<Record> re = Db.use("jlzz").find(sql);
		renderJson(re);
	}


//public void jldlx_query(){
//		String sql="select JLDLX_BM as id, JLDLX_MC  as text  from VW_JLDLX where 1=1";
//		List<Record> re = Db.find(sql);
//		renderJson(re);
//	}
}
