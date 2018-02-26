package org.controller.dagl;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.blit.lp.core.exception.SysException;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.jf.ext.MiniuiExcelRender;
import com.blit.lp.tools.DSqlKit;
import com.blit.lp.tools.GuidKit;
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

@LPController(controllerkey = "/jiliangdian_peibian")
public class jiliagndian_paibianController extends Controller {

    public void index() {
        renderTemplate("/org/zhutineirong/jiliangdian_peibian_list.html");
    }

    public void jiliangdian_list() {
        String questionText = getPara("questionText");
        setAttr("questionText", questionText);
        renderTemplate("/org/list/jiliangdian_peibian_list.html");
    }

    public void jiliangdian() {
        renderTemplate("/org/zhutineirong/jiliangdian_peibian.html");
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

        String select = "select kj.jldbh,kj.jldmc,oo.org_name,vj.jldlx_mc,vjld.jldytdm_mc,jldlb.jldlbdm_mc,ydlb.ydlbdm_mc,hyfl.hyfldm_mc,dydj.dydjdm_mc,kj.fsjfbz,jsfs.JXFSDM_MC,"
                + "kj.jldxz,kj.ydrl,wz.JLDWZMC,jlfs.jlfsdm_mc,zzfl.jlzzfldm_mc,yxzt.JLDZTDM_MC,kj.yhbh ";
        DSqlKit.init(" from kh_jld kj,o_org oo ,VW_JLDLX vj ,VW_JLDYTDM vjld ,VW_JLDLBDM jldlb,VW_YDLBDM ydlb,VW_HYFLDM hyfl,VW_DYDJDM dydj,VW_JXFSDM jsfs,vw_jldwzdm wz,VW_JLFSDM jlfs,"
                + "VW_JLZZFLDM zzfl,VW_JLDZTDM yxzt where kj.gddwbm = oo.org_no(+) ");

        String question = getPara("question");
        if (StrKit.notBlank(question)) {
            DSqlKit.append("and ( kj.jldmc like ?  ", "%" + question + "%");
            DSqlKit.append("or kj.jldbh = ? ) ", question);
        }

        DSqlKit.append("and kj.JLDLXDM  = vj.jldlx_bm(+) ");
        DSqlKit.append("and kj.JLDYTDM  = vjld.jldytdm_bm(+) ");
        DSqlKit.append("and kj.JLDLBDM = jldlb.JLDLBDM_BM(+) ");
        DSqlKit.append("and kj.YDLBDM = ydlb.YDLBDM_BM(+) ");
        DSqlKit.append("and kj.HYFLDM = hyfl.HYFLDM_BM(+) ");
        DSqlKit.append("and kj.JLDYDJDM = dydj.DYDJDM_BM(+) ");
        DSqlKit.append("and kj.JXFSDM = jsfs.JXFSDM_BM(+) ");
        DSqlKit.append("and kj.JLDWZDM = wz.JLDWZDM(+) ");
        DSqlKit.append("and kj.JLFSDM = jlfs.JLFSDM_BM(+) ");
        DSqlKit.append("and kj.JLZZFLDM = zzfl.JLZZFLDM_BM(+)  ");
        DSqlKit.append("and kj.JLDZTDM = yxzt.JLDZTDM_BM(+) ");

        //把查询窗口所有的条件添加进来
        String jldmc = getPara("jldmc");
        if (StrKit.notBlank(jldmc)) {
            DSqlKit.append(" and kj.jldmc = ?", jldmc);
        }
        String jldlxdm = getPara("jldlxdm");
        if (StrKit.notBlank(jldlxdm)) {
            DSqlKit.append(" and kj.jldlxdm = ?", jldlxdm);
        }
        String jldytdm = getPara("jldytdm");
        if (StrKit.notBlank(jldytdm)) {
            DSqlKit.append(" and kj.jldytdm = ?", jldytdm);
        }

        String jldztdm = getPara("jldztdm");
        if (StrKit.notBlank(jldztdm)) {
            DSqlKit.append(" and kj.jldztdm = ?", jldztdm);
        }
        String gddwbm = getPara("gddwbm");
        if (StrKit.notBlank(gddwbm)) {
            DSqlKit.append(" and kj.gddwbm = ?", gddwbm);
        }
        String jlfsdm = getPara("jlfsdm");
        if (StrKit.notBlank(jlfsdm)) {
            DSqlKit.append(" and kj.jlfsdm = ?", jlfsdm);
        }
        String jldbh = getPara("jldbh");
        if (StrKit.notBlank(jldbh)) {
            DSqlKit.append(" and kj.jldbh = ?", jldbh);
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
        DSqlKit.append("kj.cjsj desc ");
        return select;
    }

    //删除
    public void del() {
        String ids = getPara("ids");
        for (String id : ids.split(",")) {
            Db.use("jlzz").deleteById("kh_jld", "jldbh", id);
        }
        renderJson(Ret.ok());
    }

    //判断是保存还是编辑的操作
    public void edit() {
        String id = getPara("id");
        if (StrKit.notBlank(id)) {
            String sql = "select * from  kh_jld where jldbh=?";
            Record data = Db.use("jlzz").findFirst(sql, id);
            if (data == null) {
                throw new SysException("jiliangdian_peibian编辑数据丢失，jldbh:" + id);
            }
            setAttr("data", data);
            setAttr("_editguid", id);
            setAttr("_editstate", "edit");

        }

        renderTemplate("/org/zhutineirong/jiliangdian_peibian.html");
    }

    //保存方法
    @Before(Tx.class)
    public void save() {
        String _editstate = getPara("_editstate");
        String _editguid = getPara("_editguid");
        Record row = LPRecordKit.createFromRequest("sea.kh_jld", "data.");
        row.set("jldbh", _editguid);

        if ("edit".equals(_editstate)) {
            Db.update("sea.kh_jld", "jldbh", row);
        } else {
            row.set("cjsj", new Timestamp(new Date().getTime()));
            Db.save("sea.kh_jld", "jldbh", row);
        }
        renderJson(Ret.ok());
    }

    public void add() {
        String id = GuidKit.createGuid();
        String st = id.substring(0, 10);
        setAttr("_editguid", st);
        renderTemplate("/org/zhutineirong/jiliangdian_peibian.html");
    }

    //list页面传过来的“onSearcj”动作
    public void onSearcj() {
        renderTemplate("/org/ssxz/jiliangdianxuanze_pb.html");
    }

    //xuanze 页面传过来的query动作。
    public void query() {
        //查询条件
        String jldytdm = getPara("jldytdm");
        String jldlbdm = getPara("jldlbdm");
        String jldmc = getPara("jldmc");
        String gddwbm = getPara("gddwbm");
        String jlfsdm = getPara("jlfsdm");
        String jldbh = getPara("jldbh");

        Record re = new Record();
        re.set("jldytdm", jldytdm);
        re.set("jldlbdm", jldlbdm);
        re.set("jldmc", jldmc);
        re.set("gddwbm", gddwbm);
        re.set("jlfsdm", jlfsdm);
        re.set("jldbh", jldbh);
        renderJson(re);

    }

//选择器下拉框
    public void jldyt_query() {
        String sql = "select JLDYTDM_BM as id, JLDYTDM_MC  as text  from VW_JLDYTDM where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void jldlx_query() {
        String sql = "select JLDLX_BM as id, JLDLX_MC  as text  from VW_JLDLX where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void jldyxzt_query() {
        String sql = "select JLDZTDM_BM as id, JLDZTDM_MC  as text  from VW_JLDZTDM where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void gddw_query() {
        String sql = "select ORG_NO as id, ORG_NAME  as text  from O_ORG where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void jlfs_query() {
        String sql = "select JLFSDM_BM as id, JLFSDM_MC  as text  from VW_JLFSDM where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void jld_pbCx() {

        String id = getPara("id");
        String sql = " select kj.jldbh,kj.jldmc,oo.org_name,vj.jldlx_mc,vjld.jldytdm_mc,jldlb.jldlbdm_mc,ydlb.ydlbdm_mc,hyfl.hyfldm_mc,dydj.dydjdm_mc,kj.fsjfbz,jsfs.JXFSDM_MC,"
                + "kj.jldxz,kj.ydrl,wz.JLDWZMC,jlfs.jlfsdm_mc,zzfl.jlzzfldm_mc,yxzt.JLDZTDM_MC "
                + "from kh_jld kj,o_org oo ,VW_JLDLX vj ,VW_JLDYTDM vjld ,VW_JLDLBDM jldlb,VW_YDLBDM ydlb,VW_HYFLDM hyfl,VW_DYDJDM dydj,VW_JXFSDM jsfs,vw_jldwzdm wz,VW_JLFSDM jlfs,"
                + "VW_JLZZFLDM zzfl,VW_JLDZTDM yxzt    where jldbh = ? "
                + "and kj.gddwbm = oo.org_no(+)"
                + "and kj.JLDLXDM  = vj.jldlx_bm(+) "
                + "and kj.JLDYTDM  = vjld.jldytdm_bm(+) "
                + "and kj.JLDLBDM = jldlb.JLDLBDM_BM(+) "
                + "and kj.YDLBDM = ydlb.YDLBDM_BM(+) "
                + "and kj.HYFLDM = hyfl.HYFLDM_BM(+) "
                + "and kj.JLDYDJDM = dydj.DYDJDM_BM(+)  "
                + "and kj.JXFSDM = jsfs.JXFSDM_BM(+)  "
                + "and kj.JLDWZDM= wz.JLDWZDM(+) "
                + "and kj.JLFSDM=jlfs.JLFSDM_BM(+) "
                + "and kj.JLZZFLDM=zzfl.JLZZFLDM_BM(+) "
                + "and kj.JLDZTDM = yxzt.JLDZTDM_BM(+) ";

        Record data = Db.use("jlzz").findFirst(sql, id);
        //             Record data=	Db.findFirst(sql);
        if (data == null) {
            throw new SysException("busi_route编辑数据丢失，jldbh:" + id);
        }
        setAttr("data", data);
        setAttr("_editstate", "edit");

//		}
        renderTemplate("/org/zhutineirong/jiliangdian_peibian.html");

    }

    //数据列表方法
    public void exportdata() {
        String select = loadFilter();
        String sql = select + " " + DSqlKit.getSql();
        List<Record> list = Db.use("jlzz").find(sql, DSqlKit.getParamList());
        render(new MiniuiExcelRender(getPara("columns"), list, "计量点数据列表.xls", this.getResponse()));
    }
}
