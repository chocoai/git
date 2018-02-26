package org.controller.dagl;

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
import java.util.List;

@LPController(controllerkey = "/simka")
public class SIMController extends Controller {

    public void index() {
        renderTemplate("/org/zhutineirong/simka.html");
    }

    public void sim_list() {
        String questionText = getPara("questionText");
        setAttr("questionText", questionText);
        renderTemplate("/org/list/sim_list.html");
    }

    public void list() {
        int pageIndex = getParaToInt("pageIndex") + 1;
        int pageSize = getParaToInt("pageSize");

        String select = loadFilter();

        Page<Record> page = Db.use("jlzz").paginate(pageIndex, pageSize, select,
                DSqlKit.getSql(), DSqlKit.getParamList());
        renderJson(new LPJsonRender(Ret.ok()
                .set("total", page.getTotalRow())
                .set("data", page.getList())));
    }

    //查询条件
    private String loadFilter() {

        String select = "select sb.SIMKZCBH ,sb.SJHM, sb.IPDZ,  v.YYSDM_MC,sb.GDDWBM ";
        DSqlKit.init(" from SB_YXSIMK sb, vw_yysdm v  where  sb.YYSDM = v .YYSDM_BM ");
        //DSqlKit.append("and  a.dydjdm = c.dydjdm_bm  ");
        String question = getPara("question");
        if (StrKit.notBlank(question)) {
            DSqlKit.append("and ( sb.SJHM = ?  ", question);
            DSqlKit.append("or sb.SIMKZCBH = ? ) ", question);
            
        }
         String simkzczt = getPara("simkzczt");
        if (StrKit.notBlank(simkzczt)) {
            DSqlKit.append(" and sb.simkzczt = ?", simkzczt);
        }

        String sblbdm = getPara("sblbdm");
        if (StrKit.notBlank(sblbdm)) {
            DSqlKit.append(" and sb.sblbdm = ?", sblbdm);
        }
        //添加排序
        DSqlKit.append(" order by ");
        String sortFields = getPara("sortFields");
        if (StrKit.notBlank(sortFields)) {
            JSONArray sortAr = JSONArray.parseArray(sortFields);
            for (int i = 0; i < sortAr.size(); i++) {
                DSqlKit.append(" " + sortAr.getJSONObject(i).getString("field") + " " + sortAr.getJSONObject(i).getString("dir") + ", ");

            }
        }
        DSqlKit.append("sb.cjsj desc ");
        return select;
    }

    public void del() {
        String ids = getPara("ids");
        for (String id : ids.split(",")) {
            Db.deleteById("simkzcbh", "id", id);
            Db.use("jlzz").deleteById("sb_yxsimk", "simkzcbh", id);
        }
        renderJson(Ret.ok());
    }

    public void edit() {
        String id = getPara("id");
        if (StrKit.notBlank(id)) {
            String sql = "select * from sea.sb_yxsimk  where simkzcbh = ?";
            Record data = Db.findFirst(sql, id);
            if (data == null) {
                throw new SysException("busi_route编辑数据丢失，route_no:" + id);
            }
            setAttr("data", data);
            setAttr("_editguid", id);
            setAttr("_editstate", "edit");

        }

        renderTemplate("/org/zhutineirong/simka.html");
    }

    @Before(Tx.class)
    public void save() {
        String _editstate = getPara("_editstate");
        String _editguid = getPara("_editguid");
        Record row = LPRecordKit.createFromRequest("ceshi", "data.");

        row.set("id", _editguid);
        if ("edit".equals(_editstate)) {
            Db.update("ceshi", "id", row);
        }
        renderJson(Ret.ok());
    }

    public void onSearcj(){
		renderTemplate("/org/ssxz/simxuanze.html");
	}
       public void query(){
		//查询条件
		String SIMXLH = getPara("SIMXLH");
		String SJHM = getPara("SJHM");
		String IPDZ = getPara("IPDZ");
		String YYSDM_MC = getPara("YYSDM_MC");
                String sim_kzt_mc = getPara("sim_kzt_mc");
                String zcsblbdm_mc = getPara("zcsblbdm_mc");
		
		Record re = new Record();
		re.set("SIMXLH", SIMXLH);
		re.set("SJHM", SJHM);
		re.set("IPDZ", IPDZ);
		re.set("YYSDM_MC", YYSDM_MC);
                re.set("sim_kzt_mc", sim_kzt_mc);
                re.set("zcsblbdm_mc", zcsblbdm_mc);
                
		renderJson(re);
		
	}
       
       //下拉列表url
	public void gysSelect(){
		String sql="select YYSDM_BM as id, YYSDM_MC  as text  from vw_yysdm where 1=1";
		List<Record> re = Db.use("jlzz").find(sql);
		renderJson(re);
	}
        public void yxztSelect(){
		String sql="select SIM_KZT_BM as id, SIM_KZT_MC  as text  from VW_SIM_KZT where 1=1";
		List<Record> re = Db.use("jlzz").find(sql);
		renderJson(re);
	}
        public void simklbSelect() {
        String sql = "select ZCSBLBDM_BM as id, ZCSBLBDM_MC  as text  from VW_ZCSBLBDM where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }
        
    public void simCx() {

        String id = getPara("id");
        String sql = "select sb.SIMKZCBH ,sb.SJHM, sb.IPDZ,  v.YYSDM_MC,sb.GDDWBM,SB.SIMXLH,SB.SIMKYXBS,SB.CZSJ,SB.CJSJ,SB.SIMRL\n "
                + "from SB_YXSIMK sb, vw_yysdm v  where simkzcbh=?  "
                + "and sb.YYSDM = v .YYSDM_BM";

        Record data = Db.use("jlzz").findFirst(sql, id);
        //             Record data=	Db.findFirst(sql);
        if (data == null) {
            throw new SysException("busi_route编辑数据丢失，simkzcbh:" + id);
        }
        setAttr("data", data);
        setAttr("_editstate", "edit");

//		}
        renderTemplate("/org/zhutineirong/simka.html");

    }
    
    

    //数据列表方法
    public void exportdata() {
        String select = loadFilter();
        String sql = select + " " + DSqlKit.getSql();
        List<Record> list = Db.use("jlzz").find(sql, DSqlKit.getParamList());
        render(new MiniuiExcelRender(getPara("columns"), list, "SIMK数据列表.xls", this.getResponse()));
    }
}
