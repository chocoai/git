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

@LPController(controllerkey = "/taiqu")
public class taiquController extends Controller {

    public void index() {
        renderTemplate("/org/zhutineirong/taiqu_edit.html");
    }

    public void taiqu_list() {
        String questionText = getPara("questionText");
        String radioValue = getPara("radioValue");

        setAttr("questionText", questionText);
        setAttr("radioValue", radioValue);
        renderTemplate("/org/list/taiqu_list.html");
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

    public void edit() {

        String id = getPara("id");
        if (StrKit.notBlank(id)) {
            String sql = "select a.tqbs,a.tqmc,a.tqbh,f.tqlxdm_mc,a.gddwbm,b.yxzt_mc,c.dqtzdm_mc,d.org_name,a.CJSJ,a.CZSJ\n"
                    + "                       from DW_TQ a,vw_yxzt b,vw_dqtzdm c,o_org d ,VW_TQLXDM f where tqbs= ? \n"
                    + "                            and a.yxztdm = b.yxzt_bm (+)\n"
                    + "                            and a.dqtzdm = c.dqtzdm_bm (+)\n"
                    + "                            and d.org_no = a.gddwbm (+)\n"
                    + "                            and a.tqlx   = f.tqlxdm_bm (+)";
//		if(StrKit.notBlank(id)){
            Record data = Db.use("jlzz").findFirst(sql, id);
            if (data == null) {
                throw new SysException("busi_route编辑数据丢失，tqbs:" + id);
            }
            setAttr("data", data);
//		setAttr("_editguid", id);
            setAttr("_editstate", "edit");

//		}
        }

        renderTemplate("/org/zhutineirong/taiqu_edit.html");
    }

    @Before(Tx.class)
    public void save() {
        String _editstate = getPara("_editstate");
        String _editguid = getPara("_editguid");

        Record row = LPRecordKit.createFromRequest("dw_tq", "data.");
        row.set("tqbs", _editguid);
        if ("edit".equals(_editstate)) {
            Db.use("jlzz").update("dw_tq", "tqbs", row);
        }

        renderJson(Ret.ok());
    }

    public void exportdata() {
        String select = loadFilter();
        String sql = select + " " + DSqlKit.getSql();
        List<Record> list = Db.use("jlzz").find(sql, DSqlKit.getParamList());
        render(new MiniuiExcelRender(getPara("columns"), list, "台区数据列表.xls", this.getResponse()));
    }

    //查询条件
    private String loadFilter() {

        String select = "select * ";
        DSqlKit.init(" from DW_TQ a,vw_yxzt b,"
                + "vw_dqtzdm c,o_org d where a.yxztdm = b.yxzt_bm ");
        DSqlKit.append("and a.dqtzdm = c.dqtzdm_bm ");
        DSqlKit.append("and d.org_no = a.gddwbm ");

        String question = getPara("question");
        String radioValue = getPara("radioValue");
        if (StrKit.notBlank(question)) {
            DSqlKit.append("and ( a.tqmc like ?  ", "%" + question + "%");
            DSqlKit.append("or a.tqbh = ? ) ", question);
        }
        if (StrKit.notBlank(radioValue)) {
            DSqlKit.append("and a.tqlx = ?  ", radioValue);
        }

        String yxzt_mc = getPara("yxzt_mc");
        if (StrKit.notBlank(yxzt_mc)) {
            DSqlKit.append(" and a.yxztdm = ?", yxzt_mc);
        }

        String tqbh = getPara("tqbh");
        if (StrKit.notBlank(tqbh)) {
            DSqlKit.append(" and  a.tqbh= ?", tqbh);
        }

        String dqtz_mc = getPara("dqtz_mc");
        if (StrKit.notBlank(dqtz_mc)) {
            DSqlKit.append(" and a.dqtzdm = ?", dqtz_mc);
        }

        String tqmc = getPara("tqmc");
        if (StrKit.notBlank(tqmc)) {
            DSqlKit.append(" and a.tqmc = ?", tqmc);
        }
        
        String org_name = getPara("org_name");
        if (StrKit.notBlank(org_name)) {
            DSqlKit.append(" and d.org_name = ?", org_name);
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
        DSqlKit.append("a.czsj asc ");

        return select;
    }

    public void openSearch() {
        renderTemplate("/org/ssxz/taiquxuanze.html");
    }
    
    

  public void query() {
        String YXZT_MC = getPara("YXZT_MC");
        String TQBH = getPara("TQBH");
        String DQTZ_MC = getPara("DQTZ_MC");
        String TQMC = getPara("TQMC");
        String ORG_NAME = getPara("ORG_NAME");

        Record re = new Record();
        re.set("YXZT_MC", YXZT_MC);
        re.set("TQBH", TQBH);
        re.set("DQTZ_MC", DQTZ_MC);
        re.set("TQMC", TQMC);
        re.set("ORG_NAME", ORG_NAME);

        renderJson(re);
    }
    //下拉

    public void yxztSelect() {
        String sql = "select yxzt_bm as id, YXZT_MC  as text  from vw_yxzt where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void gddwSelect() {
        String sql = "select org_no as id, org_name  as text  from o_org where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void dqtzSelect() {
        String sql = "select dqtzdm_bm as id, dqtzdm_mc  as text  from vw_dqtzdm where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }


    public void taiquCx() {

        String id = getPara("id");
        String sql = "select a.tqbs,a.tqmc,a.tqbh,f.tqlxdm_mc,a.gddwbm,b.yxzt_mc,c.dqtzdm_mc,d.org_name,a.CJSJ,a.CZSJ\n"
                + "                       from DW_TQ a,vw_yxzt b,vw_dqtzdm c,o_org d ,VW_TQLXDM f where tqbs= ? \n"
                + "                            and a.yxztdm = b.yxzt_bm (+)\n"
                + "                            and a.dqtzdm = c.dqtzdm_bm (+)\n"
                + "                            and d.org_no = a.gddwbm (+)\n"
                + "                            and a.tqlx   = f.tqlxdm_bm (+)";

        Record data = Db.use("jlzz").findFirst(sql, id);
        //             Record data=	Db.findFirst(sql);
        if (data == null) {
            throw new SysException("busi_route编辑数据丢失，tqbs:" + id);
        }
        setAttr("data", data);
        setAttr("_editstate", "edit");

//		}
        renderTemplate("/org/zhutineirong/taiqu_edit.html");

    }

    public void getTqlxRadio() {
        String sql = "select tqlxdm_bm as id ,tqlxdm_mc as text from VW_TQLXDM where 1=1 ";
        List<Record> tqlxRadio = Db.use("jlzz").find(sql);
        renderJson(tqlxRadio);

    }

}
