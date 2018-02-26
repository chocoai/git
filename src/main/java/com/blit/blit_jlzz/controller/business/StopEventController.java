/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.controller.business;

import com.blit.blit_jlzz.service.dagl.BaseService;
import com.blit.lp.jf.config.LPController;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import java.util.List;

/**
 *
 * @author Administrator
 */
@LPController(controllerkey="/StopEvent")
public class StopEventController extends Controller{
    private final BaseService baseService = Duang.duang(BaseService.class).useDb("main");
    public void index(){
        renderTemplate("/sys/busi/StopElect_tjCX.html");
    }
    
//   public void list(){
//		String select = getList();
//		int pageIndex = getParaToInt("pageIndex") + 1;
//		int pageSize = getParaToInt("pageSize");
//		
//		Page<Record> page =  Db.use("jlzz").paginate(pageIndex, pageSize,select,
//				DSqlKit.getSql(),DSqlKit.getParamList());
//		
//		renderJson(new LPJsonRender(Ret.ok()
//				.set("total", page.getTotalRow())
//				.set("data", page.getList())));
//	}
    
    public void getList(){         
        setAttr("data", baseService.queryPara("app.StopElectTimequery", getParaMap()));
        setAttr("_editstate", "edit");
        renderTemplate("/sys/busi/StopElect_tjCX.html");

}
    
//    public void search(){
//		String tdkssj=getPara("tdkssj");
//		String tdjssj=getPara("tdjssj");
//		String xlmc=getPara("xlmc");
//		String dqtzdm=getPara("dqtzdm");
//		String yhmc=getPara("yhmc");
//		String yhbh=getPara("yhbh");
//                String yhxz=getPara("yhxz");
//                String sffsyxdm=getPara("sffsyxdm");
//                
//		Record re = new Record();
//		re.set("tdkssj", tdkssj);
//		re.set("tdjssj", tdjssj);
//		re.set("xlmc", xlmc);
//		re.set("dqtzdm", dqtzdm);
//		re.set("yhmc", yhmc);
//		re.set("yhbh", yhbh);
//              re.set("yhxz", yhxz);
//              re.set("sffsyxdm", sffsyxdm);
//		renderJson(re);
//	}
    
    public void yhxzSelect(){
		String sql="select YHZTDM_BM as id, YHZTDM_MC  as text  from sea.VW_YHZTDM where 1=1 ";
		List<Record> re = Db.find(sql);
		renderJson(re);
    }
    
    public void tdflSelect(){
		String sql="select * from TD_YHTDSJXX where TDFXDM=? ";
		List<Record> re = Db.find(sql);
		renderJson(re);
    }
    
    
     public void tdtzSelect(){
		String sql="select DQTZDM_BM as id, DQTZDM_MC  as text  from sea.VW_DQTZDM where 1=1 ";
		List<Record> re = Db.find(sql);
		renderJson(re);
    } 
     public void yhmcSelect(){
		String sql="select * from (select yhbh as id,yhmc as text from KH_YDKH) where rownum <= 20 ";
		
                List<Record> re = Db.use("jlzz").find(sql);
                System.out.println(re);
		renderJson(re);
    }
}