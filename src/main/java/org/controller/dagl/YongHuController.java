package org.controller.dagl;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.blit.lp.core.exception.SysException;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.jf.ext.MiniuiExcelRender;
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

@LPController(controllerkey = "/YongHu")
public class YongHuController extends Controller {

    public void index() {
        String questionText = getPara("questionText");
        setAttr("questionText", questionText);
        renderTemplate("/org/list/yonghu_list.html");
    }

    public void first() {
        renderTemplate("/org/zhutineirong/yonghu.html");
    }

    public void list() {
        String select = getList();

        int pageIndex = getParaToInt("pageIndex") + 1;
        int pageSize = getParaToInt("pageSize");

        Page<Record> page = Db.use("jlzz").paginate(pageIndex, pageSize, select,
                DSqlKit.getSql(), DSqlKit.getParamList());

        renderJson(new LPJsonRender(Ret.ok()
                .set("total", page.getTotalRow())
                .set("data", page.getList())));

    }
    
        //数据列表方法
    public void exportdata() {
        String select = getList();
        String sql = select + " " + DSqlKit.getSql();
        List<Record> list = Db.use("jlzz").find(sql, DSqlKit.getParamList
());
        render(new MiniuiExcelRender(getPara("columns"), list, "用户数据列表.xls", this.getResponse()));
    }

   public String getList(){
		String select = "select  a.YHBH, a.YHMC,a.HTRL,a.YXRL,o.org_name, v.YHZTDM_MC, b.YDLBDM_MC,gj.GHNHYLBDM_MC  ";
		DSqlKit.init("from KH_YDKH a ,VW_YHZTDM v, VW_YDLBDM b ,o_org o,VW_GHNHYLBDM gj where  a.GDDWBM = o.org_no " );
		DSqlKit.append("and   a.YHZTDM = v.yhztdm_bm ");
		DSqlKit.append(" and  a.YDLBDM= b.YDLBDM_BM ");
		DSqlKit.append(" and a.GHNHYLBDM  = gj.GHNHYLBDM_BM ");
                
		String org_no = getPara("org_no");
		if(StrKit.notBlank(org_no)){
			DSqlKit.append(" and org_no = ?",org_no);
		}
                String yhbh = getPara("yhbh");
		if(StrKit.notBlank(yhbh)){
			DSqlKit.append(" and yhbh = ?",yhbh);
		}
                 String yhmc = getPara("yhmc");
		if(StrKit.notBlank(yhmc)){
			DSqlKit.append(" and yhmc = ?",yhmc);
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
                
                String ghnhylbdm = getPara("ghnhylbdm");
		if(StrKit.notBlank(ghnhylbdm)){
			DSqlKit.append(" and ghnhylbdm = ?", ghnhylbdm);
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
		DSqlKit.append(" a.cjsj desc ");
		return select ;
                
	}

    public void del() {
        String ids = getPara("ids");
        for (String id : ids.split(",")) {
            Db.use("jlzz").deleteById("ceshi", "id", id);
        }
        renderJson(Ret.ok());
    }

    @Before(Tx.class)
    public void save() {
        String _editstate = getPara("_editstate");
        String _editguid = getPara("_editguid");
        Record row = LPRecordKit.createFromRequest("ceshi", "data.");

        row.set("id", _editguid);
        if ("edit".equals(_editstate)) {
            Db.use("jlzz").update("ceshi", "id", row);
        }
        renderJson(Ret.ok());
    }

     public void openSearch(){
        renderTemplate("/org/ssxz/yonghuxuanze.html");
    }
	
	public void query(){
                String yhbh=getPara("yhbh");
                String yhmc=getPara("yhmc");
		String org_name=getPara("org_name");
		String yhztdm_mc=getPara("yhztdm_mc");
		String yhlbdm_mc=getPara("yhlbdm_mc");
		String hyfldm_mc=getPara("hyfldm_mc");
		String ydlbdm_mc=getPara("ydlbdm_mc");
		String ghnhylbdm_mc=getPara("ghnhylbdm_mc");
                
		Record re = new Record();
                
                re.set("yhbh", yhbh);
                re.set("yhmc", yhmc);
		re.set("org_name", org_name);
		re.set("yhztdm_mc", yhztdm_mc);
		re.set("yhlbdm_mc", yhlbdm_mc);
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

    public void yhxxCx() {

        String id = getPara("id");
        String sql = "select a.htrl,a.yxrl,a.yhbh,a.yhmc,o.org_name,a.csncbs,a.xhrq,a.cjsj,a.czsj,a.yddz, \n"
                + "                                ydlb.ydlbdm_mc ,dydj.dydjdm_mc,hyfk.HYFLDM_MC,jlfs.jlfsdm_mc,yhlb.YHLBDM_MC,fhxz.fhxzdm_MC,ghnhylb.GHNHYLBDM_MC,yhzt.YHZTDM_MC,yfflx.YFFLXDM_MC,khfqbz.KHFQBZDM_MC ,fkms.FKMSDM_MC ,ffms.FFMSDM_MC \n"
                + "                                from KH_YDKH a ,\n"
                + "                                VW_YDLBDM ydlb ,\n"
                + "                                VW_DYDJDM dydj ,\n"
                + "                                VW_HYFLDM hyfk ,\n"
                + "                              VW_JLFSDM jlfs ,\n"
                + "                                VW_YHLBDM yhlb ,\n"
                + "                                VW_FHXZDM fhxz ,\n"
                + "                                VW_GHNHYLBDM ghnhylb ,\n"
                + "                                VW_YHZTDM yhzt ,\n"
                + "                                VW_YFFLXDM yfflx ,\n"
                + "                                VW_KHFQBZDM khfqbz ,\n"
                + "                                VW_FFMSDM ffms ,\n"
                + "                                VW_FKMSDM fkms ,\n"
                + "                                o_org o\n"
                + "                                where yhbh= ? \n"
                + "                                and a.YDLBDM = ydlb.ydlbdm_bm (+)\n"
                + "                                and a.DYDJDM = dydj.dydjdm_bm (+)\n"
                + "                                and a.HYFLDM = hyfk.HYFLDM_BM (+)\n"
                + "                                and a.JLFSDM =jlfs.jlfsdm_bm (+)\n"
                + "                              and a.YHLBDM =yhlb.YHLBDM_BM (+)\n"
                + "                                and a.FHXZDM =fhxz.fhxzdm_bm (+)\n"
                + "                                and a.GHNHYLBDM =ghnhylb.GHNHYLBDM_BM (+)\n"
                + "                                and a.YHZTDM =yhzt.YHZTDM_BM (+)\n"
                + "                                and a.YFFLXDM =yfflx.YFFLXDM_BM (+)\n"
                + "                                and a.FKMSDM =fkms.FKMSDM_BM (+)\n"
                + "                                and a.FFMSDM =ffms.FFMSDM_BM (+)\n"
                + "                                and a.KHFQBZDM =khfqbz.KHFQBZDM_BM (+)\n"
                + "                                and a.GDDWBM=o.org_no (+)";

        Record data = Db.use("jlzz").findFirst(sql, id);

        if (data == null) {
            throw new SysException("busi_route编辑数据丢失，route_no:" + id);
        }
        setAttr("data", data);
        setAttr("_editstate", "edit");
        renderTemplate("/org/zhutineirong/yonghu.html");
    }

}
