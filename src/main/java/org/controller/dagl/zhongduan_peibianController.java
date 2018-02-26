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

@LPController(controllerkey="/zhongduan_peibian")
public class zhongduan_peibianController extends Controller {
	
	public void index() {
		renderTemplate("/org/zhutineirong/zhongduan_peibian_list.html");
	}
	
	
	//构建列表方法
	public void list() {
		int pageIndex = getParaToInt("pageIndex") + 1;
		int pageSize = getParaToInt("pageSize");
		
		String select = getList();

		Page<Record> page =  Db.use("jlzz").paginate(pageIndex, pageSize,select,
				DSqlKit.getSql(),DSqlKit.getParamList());
		renderJson(new LPJsonRender(Ret.ok()
				.set("total", page.getTotalRow())
				.set("data", page.getList())));
	}
	
	public String getList(){
		String select ="  ";
		DSqlKit.init("  " );
		
		//kj表的gddwbm字段等于oo表的org_no字段
//		DSqlKit.append("and kj.gddwbm = oo.org_no ");
//		DSqlKit.append("and  kj.JLDLXDM  = vj.jldlx_bm ");
//		DSqlKit.append("and kj.JLDYTDM  = vjld.jldytdm_bm ");
		
		//把查询窗口所有的条件添加进来
//		String jldmc = getPara("jldmc");
//		if(StrKit.notBlank(jldmc)){
//			DSqlKit.append(" and kj.jldmc = ?",jldmc);
//		}
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
				DSqlKit.append(" kj.jldbh desc ");  //主表的主键
		
		return select ;
	}
	
	
	//list页面传过来的“onSearcz”动作
		public void onSearcz(){
			renderTemplate("/sea3000/zhongduanxuanze.html");
		}
		
	
		//xuanze 页面传过来的query动作。
		public void query(){
			//查询条件
//			String jldytdm = getPara("jldytdm");
//			String jldlbdm = getPara("jldlbdm");
//			String jldmc = getPara("jldmc");
//			
//			Record re = new Record();
//			re.set("jldytdm", jldytdm);
//			re.set("jldlbdm", jldlbdm);
//			re.set("jldmc", jldmc);
//			renderJson(re);
			
		}
	
	//保存、修改方法
	public void edit(){
		String id=getPara("id");
		String sql="select * from sea.sb_yxzd where zdbs=?";
		if(StrKit.notBlank(id)){
		Record data=	Db.use("jlzz").findFirst(sql,id);
		if(data == null)
			throw new SysException("zhongduan_paibian编辑数据丢失，jyjg:" + id);
		setAttr("data", data);
		setAttr("_editguid", id);
		setAttr("_editstate", "edit");
		
		}
		
		renderTemplate("/org/zhutineirong/zhongduan_peibian.html");
	}
	
	@Before(Tx.class)
	public void save(){
		String _editstate = getPara("_editstate");
		String _editguid = getPara("_editguid");
		Record row = LPRecordKit.createFromRequest("sea.i_yxzd_zc","data.");
		
		row.set("id", _editguid);
		if("edit".equals(_editstate)){
			Db.use("jlzz").update("sea.i_yxzd_zc", "id", row);
		}
		renderJson(Ret.ok());
	}
	
	
	//删除方法
		public void del(){
			String ids =getPara("ids"); 
			for(String id: ids.split(",")){
				Db.use("jlzz").deleteById("sea.i_yxzd_zc", "JYJG", id);
			}
			renderJson(Ret.ok());
		}
	
}
