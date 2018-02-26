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

@LPController(controllerkey = "/xianlu")
public class xianluController extends Controller {

    public void index() {
        renderTemplate("/org/zhutineirong/xianluzhuti.html");
    }

    public void xianlu_list() {
        String questionText = getPara("questionText");
        String radioValue = getPara("radioValue");

        setAttr("questionText", questionText);
        setAttr("radioValue", radioValue);
        renderTemplate("/org/list/xianluzhuti_list.html");
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

    public void exportdata() {
        String select = loadFilter();
        String sql = select + " " + DSqlKit.getSql();
        List<Record> list = Db.use("jlzz").find(sql, DSqlKit.getParamList());
        render(new MiniuiExcelRender(getPara("columns"), list, "线路数据列表.xls", this.getResponse()));
    }

    //查询条件
     private String loadFilter() {

        String select = "select a.yxztdm, a.dydjdm, a.xlbh ,a.xlmc, o.org_name ,e.xllbdm_mc,f.xllxdm_mc,b.xlyxztdm_mc ,c.DYDJDM_mc ";
        DSqlKit.init(" from dw_xlxd a ,vw_xlyxztdm b,vw_dydjdm c ,o_org o , dw_xlgldw d ,"
                + "vw_xllbdm e  ,vw_xllxdm f where a.yxztdm = b.xlyxztdm_bm  ");
        DSqlKit.append("and  a.dydjdm = c.dydjdm_bm  ");
        DSqlKit.append("and  d.gddwbm = o.org_no ");
        DSqlKit.append("and  a.xlxdbs = d.xlxdbs  ");
        DSqlKit.append("and  a.xllbdm = e.xllbdm_bm ");
        DSqlKit.append("and  a.xllxdm = f.xllxdm_bm ");

        String question = getPara("question");
        String radioValue = getPara("radioValue");
        if (StrKit.notBlank(question)) {
            DSqlKit.append("and org_name like ?  ", "%" + question + "%");
        }
        if (StrKit.notBlank(radioValue)) {
            DSqlKit.append("and a.xllbdm = ?  ", radioValue);
        }

        String xlbh = getPara("xlbh");
        if (StrKit.notBlank(xlbh)) {
            DSqlKit.append(" and a.xlbh = ?", xlbh);
        }

        String xlmc = getPara("xlmc");
        if (StrKit.notBlank(xlmc)) {
            DSqlKit.append(" and  a.xlmc= ?", xlmc);
        }

        String xllbdm_mc = getPara("xllbdm_mc");
        if (StrKit.notBlank(xllbdm_mc)) {
            DSqlKit.append(" and a.xllbdm = ?", xllbdm_mc);
        }

        String xllxdm_mc = getPara("xllxdm_mc");
        if (StrKit.notBlank(xllxdm_mc)) {
            DSqlKit.append(" and a.xllxdm = ?", xllxdm_mc);
        }
        String yxzt_mc = getPara("yxzt_mc");
        if (StrKit.notBlank(yxzt_mc)) {
            DSqlKit.append(" and a.yxztdm = ?", yxzt_mc);
        }
        String dydjdm_mc = getPara("dydjdm_mc");
        if (StrKit.notBlank(dydjdm_mc)) {

            DSqlKit.append(" and a.dydjdm = ?", dydjdm_mc);
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
        DSqlKit.append("a.cjsj asc ");
        return select;
    }

    public void edit() {
        String id = getPara("id");
        if (StrKit.notBlank(id)) {
            String sql = "select lx.XLLXDM_MC,lb.XLLBDM_MC,dy.DYDJDM_MC,zt.XLYXZTDM_MC,nw.NWBZ_MC,hw.HWBZ_MC,\n"
                    + "dw.XLXDBS,dw.XLBH,dw.XLMC,dw.FDBZ,dw.XLZDFH,dw.XLYYFH,dw.CJSJ,dw.CZSJ\n"
                    + "from dw_xlxd dw,\n"
                    + "VW_XLLXDM lx,\n"
                    + "VW_XLLBDM lb,\n"
                    + "VW_DYDJDM dy,\n"
                    + "VW_XLYXZTDM zt,\n"
                    + "VW_NWBZ nw,\n"
                    + "VW_HWBZ hw  "
                    + "where xlbh= ?\n"
                    + "and dw.xllxdm=lx.XLLXDM_BM\n"
                    + "and dw.xllbdm=lb.xllbdm_bm\n"
                    + "and dw.dydjdm=dy.dydjdm_bm\n"
                    + "and dw.NWBZ=nw.NWBZ_BM\n"
                    + "and dw.hwbz=hw.hwbz_bm\n"
                    + "and dw.yxztdm=zt.XLYXZTDM_BM";

            Record data = Db.use("jlzz").findFirst(sql);
            if (data == null) {
                throw new SysException("busi_route编辑数据丢失，tqbs:" + id);
            }
            setAttr("data", data);
            //		setAttr("_editguid", id);
            setAttr("_editstate", "edit");

            //		}
        }

        renderTemplate("/org/zhutineirong/xianluzhuti.html");
    }

    @Before(Tx.class)
    public void save() {
        String _editstate = getPara("_editstate");
        String _editguid = getPara("_editguid");
        Record row = LPRecordKit.createFromRequest("DW_XLXD", "data.");

        row.set("xlbh", _editguid);
        if ("edit".equals(_editstate)) {
            Db.use("jlzz").update("DW_XLXD", "xlbh", row);
        }
        renderJson(Ret.ok());
    }

    public void openSearch() {
        renderTemplate("/org/ssxz/xianluxuanze.html");
    }

    public void query() {
        String xlbh = getPara("xlbh");
        String xlmc = getPara("xlmc");
        String xllbdm_mc = getPara("xllbdm_mc");
        String xllxdm_mc = getPara("xllxdm_mc");
        String xlyxztdm_mc = getPara("xlyxztdm_mc");
        String dydjdm_mc = getPara("dydjdm_mc");
        String org_name = getPara("org_name");

        Record re = new Record();

        re.set("xlbh", xlbh);
        re.set("xlmc", xlmc);
        re.set("xllbdm_mc", xllbdm_mc);
        re.set("xllxdm_mc", xllxdm_mc);
        re.set("xlyxztdm_mc", xlyxztdm_mc);
        re.set("dydjdm_mc", dydjdm_mc);
        re.set("org_name", org_name);

        renderJson(re);
    }
    //下拉列表url

    public void gddwSelect() {
        String sql = "select org_no as id, org_name  as text  from o_org where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void xllbSelect() {
        String sql = "select XLLBDM_BM as id, XLLBDM_MC  as text  from VW_XLLBDM where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void xlmcSelect() {
        String sql = "select XLMC as id, XLMC  as text  from dw_xlxd where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void xllxSelect() {
        String sql = "select xllxdm_bm as id, xllxdm_mc  as text  from vw_xllxdm where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void dydjSelect() {
        String sql = "select dydjdm_bm as id, dydjdm_mc  as text  from vw_dydjdm where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void xianluCx() {

        String id = getPara("id");
        String sql = "select lx.XLLXDM_MC,dw.xlxdbs,lb.XLLBDM_MC,dy.DYDJDM_MC,zt.XLYXZTDM_MC,nw.NWBZ_MC,hw.HWBZ_MC,dw.XLXDBS,dw.XLBH,dw.XLMC,dw.FDBZ,dw.XLZDFH,dw.XLYYFH,dw.CJSJ,dw.CZSJ\n"
                + "                       from dw_xlxd dw,\n"
                + "                            VW_XLLXDM lx,\n"
                + "                            VW_XLLBDM lb,\n"
                + "                            VW_DYDJDM dy,\n"
                + "                            VW_XLYXZTDM zt,\n"
                + "                            VW_NWBZ nw,\n"
                + "                            VW_HWBZ hw  \n"
                + "                            where xlbh= ? \n"
                + "                            and dw.xllxdm=lx.XLLXDM_BM (+)\n"
                + "                            and dw.xllbdm=lb.xllbdm_bm (+)\n"
                + "                            and dw.dydjdm=dy.dydjdm_bm (+)\n"
                + "                            and dw.NWBZ=nw.NWBZ_BM (+)\n"
                + "                            and dw.hwbz=hw.hwbz_bm (+)\n"
                + "                            and dw.yxztdm=zt.XLYXZTDM_BM (+)";

        Record data = Db.use("jlzz").findFirst(sql, id);
        //             Record data=	Db.findFirst(sql);
        if (data == null) {
            throw new SysException("busi_route编辑数据丢失，xlbh:" + id);
        }
        setAttr("data", data);
        setAttr("_editstate", "edit");

//		}
        renderTemplate("/org/zhutineirong/xianluzhuti.html");

    }

    public void getXllxRadio() {
        String sql = "select xllbdm_bm as id ,xllbdm_mc as text from VW_XLLBDM where 1=1 ";
        List<Record> zdlxRadio = Db.use("jlzz").find(sql);
        renderJson(zdlxRadio);

    }

}
