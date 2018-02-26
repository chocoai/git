package org.controller.dagl;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.blit.lp.core.exception.SysException;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.tools.DSqlKit;
import com.blit.lp.tools.LPRecordKit;
import com.jfinal.core.Controller;
import com.jfinal.aop.Before;
import com.jfinal.ext.LPJsonRender;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import java.util.ArrayList;



@LPController(controllerkey="/jichaoyonghu1")
public class jichaoyonghuController extends Controller {

	public void index() {
		renderTemplate("/org/list/jichaoyonghu_list.html");
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
		String select ="select ky.yhbh,ky.yhmc,ky.htrl,ky.yxrl,ky.GDDWBM,ky.YHZTDM,oo.org_name,vy.yhztdm_mc,vwy.ydlbdm_mc";
		DSqlKit.init("from kh_ydkh ky,o_org oo,VW_YHZTDM vy,VW_YDLBDM vwy" +
				" where ky.gddwbm = oo.ORG_NO");
                                   	String org_no = getPara("org_no");
		if(StrKit.notBlank(org_no)){
			DSqlKit.append(" and org_no = ?",org_no);
		}
		String ydlbdm_bm = getPara("ydlbdm_bm");
		if(StrKit.notBlank(ydlbdm_bm)){
			DSqlKit.append(" and ydlbdm_bm = ?", ydlbdm_bm);
		}
		String yhztdm_bm = getPara("yhztdm_bm");
		if(StrKit.notBlank(yhztdm_bm)){
			DSqlKit.append(" and yhztdm_bm = ?", yhztdm_bm);
		}
		String hyfldm_bm = getPara("hyfldm_bm");
		if(StrKit.notBlank(hyfldm_bm)){
			DSqlKit.append(" and hyfldm_bm = ?", hyfldm_bm);
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
				DSqlKit.append(" ky.yhbh desc ");
		
		return select ;
	}
        public void yonghu(){
        
                String id=getPara("id");
                
		String sql="select ky.yhbh,ky.yhmc,ky.yxrl,ky.htrl,ky.XHRQ,vy.YDLBDM_mc,vh.HYFLDM_MC,vf.FFMSDM_MC,\n" +
"vwy.YFFLXDM_MC,vwf.FKMSDM_MC,vj.JLFSDM_MC,vk.KHFQBZDM_MC,vd.DYDJDM_MC,vyh.YHZTDM_MC,vfh.FHXZDM_MC,vg.GHNHYLBDM_MC,\n" +
"oo.ORG_NAME,vwyh.YHLBDM_MC\n" +
                        " from \n"
                        + "sea.kh_ydkh ky,\n"
                        + "sea.VW_YDLBDM vy,\n"
                        + "sea.VW_HYFLDM vh,\n"
                        + "sea.VW_FFMSDM vf,\n"
                        + "sea.VW_YFFLXDM vwy,\n"
                        + "sea.VW_FKMSDM vwf,\n"
                        + "sea.VW_JLFSDM vj,\n"
                        + "sea.VW_KHFQBZDM vk,\n"
                        + "sea.VW_DYDJDM vd,\n"
                        + "sea.VW_YHZTDM vyh,\n"
                        + "sea.VW_FHXZDM vfh,\n" 
                         +"sea.VW_GHNHYLBDM vg,\n"
                        + "sea.o_org oo,\n"
                        + "sea.VW_YHLBDM vwyh\n" +
                        " where ky.yhbh= ? \n" +
                        "  and ky.YDLBDM = vy.YDLBDM_BM(+)\n" +
                        "  and ky.HYFLDM = vh.HYFLDM_BM (+)\n" +
                        "  and ky.FFMSDM = vf.FFMSDM_BM (+)\n" +
                         " and ky.YFFLXDM = vwy.YFFLXDM_BM (+)\n" +
                        "  and ky.FKMSDM = vwf.FKMSDM_BM (+)\n" +
                        "  and ky.JLFSDM = vj.JLFSDM_BM (+)\n" +
                         " and ky.KHFQBZDM = vk.KHFQBZDM_BM (+)\n" +
                        " and ky.DYDJDM = vd.DYDJDM_BM (+)\n" +
                        "  and ky.YHZTDM = vyh.YHZTDM_BM(+)\n" +
                         "  and ky.FHXZDM = vfh.FHXZDM_BM (+)\n" +
                        "and ky.GHNHYLBDM = vg.GHNHYLBDM_BM (+)\n" +
                        " and ky.GDDWBM = oo.ORG_NO (+)\n" +
                        "and ky.YHLBDM = vwyh.YHLBDM_BM (+)";
                
		if(StrKit.notBlank(id)){
                    
		Record data=Db.use("jlzz").findFirst(sql,id);
		if(data == null)
			throw new SysException("busi_route编辑数据丢失，route_no:" + id);
		setAttr("data", data);
		}
		
		renderTemplate("/org/zhutineirong/jichaoyonghu_edit.html");
    
    }      
        
        public void LoadTree() { 	
            String yhbh = getPara("yhbh");
            String sql = "select * from sea.kh_ydkh  where yhbh = ? ";
            Record re = Db.findFirst(sql,yhbh);
            
            String ids = getPara("zdbs");
            String subSql = "select zdbs as id,zdmc as text from sea.SB_YXZD where zdbs= ? ";
            Record subRe = Db.findFirst(subSql,ids);

            Record pra = new Record();
            pra.set("id", yhbh);
            pra.set("text", yhbh);
            
           
           List<Record> treeList = new ArrayList<Record>();
           treeList.add(pra);
           treeList.add(subRe);
            
            renderJson(treeList);

        }
              public void add(){
		
		renderTemplate("/org/zhutineirong/jichaoyonghu_edit.html");
	}
        @Before(Tx.class)
	public void save(){
		String _editstate = getPara("_editstate");
		//String _editguid = getPara("_editguid");
		Record row = LPRecordKit.createFromRequest("sea.kh_ydkh","data.");
		//row.set("yhbh", _editguid);
                
		if("edit".equals(_editstate)){
                        Db.update("sea.kh_ydkh","yhbh",row);
		}else{  
                    Db.save("sea.kh_ydkh", "yhbh", row);
                }
		renderJson(Ret.ok());
	}
        


        
	
	public void onSearcj(){
		renderTemplate("/org/zhutineirong/yonghuxuanze.html");
	}
	
	public void search(){
		String yhbh=getPara("yhbh");
		String yhmc=getPara("yhmc");
		String org_no=getPara("org_no");
		String yhztdm_bm=getPara("yhztdm_bm");
		String ydlbdm_bm=getPara("ydlbdm_bm");
                String hyfldm_mc=getPara("hyfldm_mc");
		String ydlbdm_mc=getPara("ydlbdm_mc");
		String ghnhylbdm_mc=getPara("ghnhylbdm_mc");
		Record re =new Record();
		re.set("yhbh", yhbh);
		re.set("yhmc", yhmc);
		re.set("org_no", org_no);
		re.set("yhztdm_bm", yhztdm_bm);
		re.set("ydlbdm_bm", ydlbdm_bm);
                re.set("hyfldm_mc", hyfldm_mc);
		re.set("ydlbdm_mc", ydlbdm_mc);
		re.set("ghnhylbdm_mc", ghnhylbdm_mc);
                
		renderJson(re);
		
	}
	public void yhztSelect(){
		String sql="select YHZTDM_BM as id, YHZTDM_MC  as text  from VW_YHZTDM where 1=1 ";
		List<Record> re = Db.use("jlzz").find(sql);
		renderJson(re);
		
	}
	
	public void ydlbSelect(){
		String sql="select YDLBDM_BM as id, YDLBDM_MC  as text  from VW_YDLBDM where 1=1 ";
		List<Record> re = Db.use("jlzz").find(sql);
		renderJson(re);
		
	}
	
	public void hyflSelect(){
		String sql="select HYFLDM_BM as id, HYFLDM_MC  as text  from VW_HYFLDM where 1=1 ";
		List<Record> re = Db.use("jlzz").find(sql);
		renderJson(re);
		
	}
	public void yhlbSelect(){
		String sql="select YHLBDM_BM as id, YHLBDM_MC  as text  from VW_YHLBDM where 1=1 ";
		List<Record> re = Db.use("jlzz").find(sql);
		renderJson(re);
		
	}
	public void gddwSelect(){
		String sql="select org_no as id, org_name  as text  from o_org where 1=1 ";
		List<Record> re = Db.use("jlzz").find(sql);
		renderJson(re);
	}

	public void ghydhySelect(){
		String sql="select GHNHYLBDM_BM as id, GHNHYLBDM_MC  as text  from VW_GHNHYLBDM where 1=1 " ;
		List<Record> re = Db.use("jlzz").find(sql);
		renderJson(re);
	}
	    
}
