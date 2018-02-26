package org.controller.dagl;


import com.alibaba.fastjson.JSONArray; 
import com.blit.lp.core.exception.SysException;
import com.blit.lp.jf.config.LPController; 
import com.blit.lp.tools.DSqlKit; 
import com.blit.lp.tools.DateKit;
import com.jfinal.core.Controller;
import com.jfinal.ext.LPJsonRender;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record; 
import com.sun.jmx.snmp.Timestamp;

@LPController(controllerkey="/gongdianju")
public class gongdianjuController extends Controller{

	public void index(){
		renderTemplate("/org/zhutineirong/gongdianju_list.html");
	}
	
	public void list() {
		
		String select = loadFilter();
		int pageIndex = getParaToInt("pageIndex") + 1;
		int pageSize = getParaToInt("pageSize");
		
		

		Page<Record> page =  Db.use("jlzz").paginate(pageIndex, pageSize,select,
				DSqlKit.getSql(),DSqlKit.getParamList());
		renderJson(new LPJsonRender(Ret.ok()
				.set("total", page.getTotalRow())
				.set("data", page.getList())));
	}
	
	public void edit() {

		String id=getPara("id");
		String sql="select * from   dw_bdz a ,vw_dydjdm d,o_org o,vw_bdzlxdm c , vw_bdzyxztdm b  where bdzbs=?";
		if(StrKit.notBlank(id)){
		Record data=	Db.use("jlzz").findFirst(sql,id);
		if(data == null)
			throw new SysException("busi_route编辑数据丢失，tqbs:" + id);
		setAttr("data", data);
		setAttr("_editguid", id);
		setAttr("_editstate", "edit");
		
		}
		
		renderTemplate("/org/zhutineirong/biandianzhan.html");
	}
	//查询条件
	private String loadFilter(){
	 
		String select="select  a.org_no,a.org_name,b.YHSL,c.czs,d.ZXZDS ";
		DSqlKit.init("from    o_org a,tj_ryhda_xs b,tj_dahz_dc c,tj_rzdzx_s d  ");
//		DSqlKit.append("and a.gddwbm = o.org_no  ");
//		DSqlKit.append("and a.bdzlxdm = c.bdzlxdm_bm ");
//		DSqlKit.append("and a.BDZYXZT = b.bdzyxztdm_bm ");
//		DSqlKit.append("and a.cqgsdm  = f.cqgsdm_bm  ");
			
	
		 
//		String bdzlxdm_mc = getPara("bdzlxdm_mc");
//		if(StrKit.notBlank(bdzlxdm_mc)){
//			DSqlKit.append("and a.bdzlxdm = ?", bdzlxdm_mc);
//		} 
		
		 	 
		String org_no = getPara("org_no");
		if(StrKit.notBlank(org_no)){
			DSqlKit.append(" and org_no like ?", "%" +org_no+ "%");
		}
		
		String org_name = getPara("org_name");
		if(StrKit.notBlank(org_name)){
			DSqlKit.append(" and org_name >=?", new Timestamp(DateKit.parse(org_name + " 00:00:00").getTime()));
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
				DSqlKit.append("a.org_no asc ");
				
				return select;
	}
	
	public void openSearch(){
		renderTemplate("/sea3000/changzhanxuanzeB.html");
	}
	
	public void query(){
		String bdzbh = getPara("bdzbh"); 
		String bdzmc = getPara("bdzmc");
		String dydjdm_mc = getPara("dydjdm_mc");
		String bdzyxztdm_mc = getPara("bdzyxztdm_mc"); 
		String bdzlxdm_mc = getPara("bdzlxdm_mc"); 
		String org_name = getPara("org_name");
		
		Record re =new Record();
		
		re.set("bdzbh", bdzbh);
		re.set("org_name", org_name);
		re.set("bdzmc", bdzmc);
		re.set("dydjdm_mc", dydjdm_mc);
		re.set("bdzyxztdm_mc", bdzyxztdm_mc); 
		re.set("bdzlxdm_mc", bdzlxdm_mc); 
		
		renderJson(re);
		
	}
	
	
	
	
}
