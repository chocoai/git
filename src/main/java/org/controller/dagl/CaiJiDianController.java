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
import java.sql.Timestamp;
import java.util.Date;

@LPController(controllerkey = "/CaiJiDian")
public class CaiJiDianController extends Controller {

    public void index() {
        String questionText = getPara("questionText");
        setAttr("questionText", questionText);
        renderTemplate("/org/list/caijidian_list.html");
    }

    public void first() {
        renderTemplate("/org/zhutineirong/caijidian.html");
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
        List<Record> list = Db.use("jlzz").find(sql, DSqlKit.getParamList());
        render(new MiniuiExcelRender(getPara("columns"), list, "采集器数据列表.xls", this.getResponse()));
    }

    public String getList() {
        String select = "select a.cjqbs,a.cjqbh,a.cjqmc,  o.org_name ,a.zdljdz ,b.sbyxztdm_mc,a.txdz ";
        DSqlKit.init("from sb_yxcjq a,  vw_sbyxztdm b, o_org o  ,tj_yc_yxcjq tj where a.gddwbm=o.org_no ");
        DSqlKit.append("and a.yxztdm=b.sbyxztdm_bm ");
        DSqlKit.append(" and a.zdbs=tj.zdbs ");

        String question = getPara("question");
        if (StrKit.notBlank(question)) {
            DSqlKit.append("and ( a.cjqmc like ?  ", "%" + question + "%");
            DSqlKit.append("or a.cjqbh = ? ) ", question);
        }

          String sbyxztdm_mc = getPara("sbyxztdm_mc");
        if (StrKit.notBlank(sbyxztdm_mc)) {
            DSqlKit.append(" and a.yxztdm = ?", sbyxztdm_mc);
        }
          String cjqbh = getPara("cjqbh");
        if (StrKit.notBlank(cjqbh)) {
            DSqlKit.append(" and a.cjqbh = ?", cjqbh);
        }
          String cjqmc = getPara("cjqmc");
        if (StrKit.notBlank(cjqmc)) {
            DSqlKit.append(" and a.cjqmc = ?", cjqmc);
        }
          String org_name = getPara("org_name");
        if (StrKit.notBlank(org_name)) {
            DSqlKit.append(" and o.org_no = ?", org_name);
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
        DSqlKit.append(" a.cjsj desc ");
        return select;
    }
    
    public void Cjdxz() {
        renderTemplate("/org/ssxz/caijidianxuanze.html");
    }

    public void query() {
        String org_name = getPara("org_name");
        String cjqmc = getPara("cjqmc");
        String sbyxztdm_mc = getPara("sbyxztdm_mc");
        String cjqbh = getPara("cjqbh");

        Record re = new Record();
        re.set("sbyxztdm_mc", sbyxztdm_mc);
        re.set("cjqbh", cjqbh);
        re.set("cjqmc", cjqmc);
        re.set("org_name", org_name);

        renderJson(re);
    }

    public void del() {
        String ids = getPara("ids");
        for (String id : ids.split(",")) {
            Db.use("jlzz").deleteById("ceshi", "id", id);
        }
        renderJson(Ret.ok());
    }

    public void edit() {
        String id = getPara("id");
        String sql = "select * from SB_YXCJQ where cjqbs=?";
        if (StrKit.notBlank(id)) {
            Record data = Db.use("jlzz").findFirst(sql, id);
            if (data == null) {
                throw new SysException("busi_route编辑数据丢失，route_no:" + id);
            }
            setAttr("data", data);
            setAttr("_editguid", id);
            setAttr("_editstate", "edit");

        }

        renderTemplate("/org/zhutineirong/caijidian.html");
    }

    @Before(Tx.class)
    public void save() {
        String _editstate = getPara("_editstate");
        String _editguid = getPara("_editguid");

        Record row = LPRecordKit.createFromRequest("SB_YXCJQ", "data.");
        row.set("cjqbs", _editguid);

        if ("edit".equals(_editstate)) {
            Db.update("SB_YXCJQ", "cjqbs", row);
        } else {
            row.set("cjsj", new Timestamp(new Date().getTime()));
            Db.save("SB_YXCJQ", "cjqbs", row);
        }

        // System.out.println(row);
        renderJson(Ret.ok());
    }

//    public void Cjdxz() {
//        renderTemplate("/org/ssxz/caijidianxuanze.html");
//    }

    public void gddwSelect() {
        String sql = "select org_no as id, org_name  as text  from o_org where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void yxztSelect() {
        String sql = "select sbyxztdm_bm as id, sbyxztdm_mc  as text  from vw_sbyxztdm where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void ccjzcbhSelect() {
        String sql = "select CJQBH from SB_YXCJQ  ";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void CaiJiDianCx() {

        String id = getPara("id");
        String sql = "select a.cjqbs,a.cjqbh, a.cjqmc,a.zdljdz,o.org_name,b.sbyxztdm_mc,a.azdz,a.ccrq,a.ccbh,tq.tqmc,a.cjsj,a.czsj,xh.cjqxhdm_mc,a.SCCJBS,a.txdz\n" +
                    "from sb_yxcjq a,o_org o ,vw_sbyxztdm b, dw_tq tq, VW_CJQXHDM xh\n" +
                    "where a.cjqbs= ? \n" +
                    "and a.gddwbm = o.org_no (+)\n" +
                    "and a.YXZTDM =b.sbyxztdm_bm (+)\n" +
                    "and a.tqbh=tq.tqbh(+)\n" +
                    "and a.sbxhdm=xh.cjqxhdm_bm(+)";
               
               
        Record data = Db.use("jlzz").findFirst(sql, id);
        //             Record data=	Db.findFirst(sql);
        if (data == null) {
            throw new SysException("SB_YXCJQ编辑数据丢失，cjqbs:" + id);
        }
        setAttr("data", data);
        setAttr("_editstate", "edit");

//		}
        renderTemplate("/org/zhutineirong/caijidian.html");

    }
}
