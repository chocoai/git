package org.controller.dagl;

import com.alibaba.fastjson.JSONArray;
import com.blit.lp.core.exception.SysException;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.jf.ext.MiniuiExcelRender;
import com.blit.lp.tools.DSqlKit;
import com.jfinal.core.Controller;
import com.jfinal.ext.LPJsonRender;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import java.util.List;

@LPController(controllerkey = "/ZhongDuanDNL")
public class ZhongDuanDNLController extends Controller {

    public void index() {
        renderTemplate("/org/zhutineirong/diannengliang.html");
    }

    public void first() {
        String questionText = getPara("questionText");
        String radioValue = getPara("radioValue");

        setAttr("questionText", questionText);
        setAttr("radioValue", radioValue);
        renderTemplate("/org/list/zhongduan_dnl_list.html");
    }

    //构建列表方法
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
        render(new MiniuiExcelRender(getPara("columns"), list, "终端数据列表.xls", this.getResponse()));
    }

    public String getList() {
        String select = "select a.zdbs,a.zdzcbh,a.zdmc,a.zddz,b.zdlxdm_mc,c.txgydm_mc,d.zdyxztdm_mc,o.org_name ";
        DSqlKit.init("from sb_yxzd a ,vw_zdlxdm b,vw_txgydm c,vw_zdyxztdm d,o_org o where a.zdlxdm=b.zdlxdm_bm(+) ");

        DSqlKit.append("and   a.GYBBH = c.TXGYDM_bm(+)  ");
        DSqlKit.append("and   a.YXZTDM = d.ZDYXZTDM_bm(+)  ");
        DSqlKit.append("and   a.GDDWBM = o.org_no(+) ");
        DSqlKit.append("and  a.zdlxdm = b.zdlxdm_bm(+) ");

        String question = getPara("question");
        String radioValue = getPara("radioValue");
        if (StrKit.notBlank(question)) {
            DSqlKit.append("and ( a.ZDMC like ?  ", "%" + question + "%");
            DSqlKit.append("or a.ZDZCBH = ? ) ", question);
        }
        if (StrKit.notBlank(radioValue)) {
            DSqlKit.append("and a.ZDLXDM = ?  ", radioValue);
        }
        
        String zdmc = getPara("zdmc");
        if(StrKit.notBlank(zdmc)){
                DSqlKit.append(" and a.zdmc = ?",zdmc);
        }
        String gddwbm = getPara("gddwbm");
        if(StrKit.notBlank(gddwbm)){
                DSqlKit.append(" and a.gddwbm = ?",gddwbm);
        }
        String zdlxdm = getPara("zdlxdm");
        if(StrKit.notBlank(zdlxdm)){
                DSqlKit.append(" and a.zdlxdm = ?", zdlxdm);
        }

        String txfs = getPara("txfs");
        if(StrKit.notBlank(txfs)){
                DSqlKit.append(" and a.txfs = ?", txfs);
        }
        String gybbh = getPara("gybbh");
        if(StrKit.notBlank(gybbh)){
                DSqlKit.append(" and a.gybbh = ?", gybbh);
        }
        String yxztdm = getPara("yxztdm");
        if(StrKit.notBlank(yxztdm)){
                DSqlKit.append(" and a.yxztdm = ?", yxztdm);
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
        DSqlKit.append(" a.zdbs desc ");
        return select;
    }

    //list页面传过来的“onSearcz”动作
    public void openSearch() {
        renderTemplate("/org/ssxz/zhongduanxuanze.html");
    }

    public void query() {
        String zdmc = getPara("zdmc");
        String zdlxdm = getPara("zdlxdm");
        String txfs = getPara("txfs");
        String gybbh = getPara("gybbh");
        String yxztdm = getPara("yxztdm");
        String org_name = getPara("org_name");

        Record re = new Record();

        re.set("zdmc", zdmc);
        re.set("zdlxdm", zdlxdm);
        re.set("txfs", txfs);
        re.set("gybbh", gybbh);
        re.set("yxztdm", yxztdm);
        re.set("org_name", org_name);

        renderJson(re);
    }
    //下拉列表url

    public void gddwSelect() {
        String sql = "select org_no as id, org_name  as text  from o_org where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }
   
     public void zdlxSelect() {
        String sql = "select zdlxdm_bm as id, zdlxdm_mc  as text  from vw_zdlxdm where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }
     public void txfsSelect() {
        String sql = "select txfsdm_bm as id, txfsdm_mc  as text  from vw_txfsdm where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }
   
      public void txgySelect() {
        String sql = "select txgydm_bm as id, txgydm_mc  as text  from vw_txgydm where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }
      public void yxztSelect() {
        String sql = "select zdyxztdm_bm as id, zdyxztdm_mc  as text  from vw_zdyxztdm where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }
      
      
      
    //删除方法
    public void del() {
        String ids = getPara("ids");
        for (String id : ids.split(",")) {
            Db.use("jlzz").deleteById("SB_YXZD", "JYJG", id);
        }
        renderJson(Ret.ok());
    }

    public void zhongduanCx() {

        String id = getPara("id");
        String sql = "SELECT          zc.RJBBH,\n"
                + "                jx.JXFSDM_mc,\n"
                + "                zd.zdzcbs,\n"
                + "                zd.zdmc,\n"
                + "                zc.JDRQ,\n"
                + "                zc.CCBH,\n"
                + "                zd.ZDDZ,\n"
                + "                jc.JCBZ_MC,\n"
                + "        		    ZD.ZDBS,                                                     \n"
                + "        		    ZD.ZDDZ,                                                     \n"
                + "        		    ZD.ZDZCBH,                                                   \n"
                + "        		    GY.TXGYDM_MC,                                                \n"
                + "        		    CJ.ZDCJ_MC ZDCJ,                                             \n"
                + "        		    XH.ZDSBXH_MC ZDSBXH,                                         \n"
                + "        		    TXFS.TXFSDM_MC AS TXFS,                                      \n"
                + "        		    to_char(ZC.CCRQ, 'yyyy-mm-dd') CCRQ,                         \n"
                + "        		    to_char(ZD.AZSJ, 'yyyy-mm-dd') AZSJ,                         \n"
                + "        		    LX.ZDLXDM_MC AS ZDLXDM,                                      \n"
                + "        		    YXZT.ZDYXZTDM_MC AS YXZTDM,                                  \n"
                + "        		    ZZ.ZZMC  AS GDDWBM                                           \n"
                + "        		    FROM SB_YXZD ZD,SB_ZDZC ZC,VW_ZDCJ CJ,VW_ZDSBXH XH,VW_ZDLXDM LX,VW_ZDYXZTDM YXZT,VW_TXFSDM TXFS, VW_JCBZ jc,VW_JXFSDM jx, \n"
                + "        		    XT_ZZJG ZZ,VW_TXGYDM GY                               \n"
                + "        		    WHERE ZD.ZDZCBH = ZC.ZCBH(+)                           \n"
                + "        		    AND ZD.GYBBH = GY.TXGYDM_BM(+)                            \n"
                + "        		    AND ZC.SCCJBS = CJ.ZDCJ_BM(+)                            \n"
                + "        		    AND ZC.SBXHDM = XH.ZDSBXH_BM(+)                            \n"
                + "        		    AND ZD.ZDLXDM = LX.ZDLXDM_BM(+)                            \n"
                + "        		    AND ZD.YXZTDM = YXZT.ZDYXZTDM_BM(+)                         \n"
                + "        		    AND ZD.TXFS = TXFS.TXFSDM_BM(+)                             \n"
                + "        		    AND ZD.GDDWBM = ZZ.ZZBMDM(+)\n"
                + "                and zc.JCBZ=jc.JCBZ_BM(+)\n"
                + "                and zd.jxfsdm=jx.JXFSDM_BM(+)\n"
                + "                and zd.zdbs= ? ";

        Record data = Db.use("jlzz").findFirst(sql, id);
        if (data == null) {
            throw new SysException("busi_route编辑数据丢失，route_no:" + id);
        }
        setAttr("data", data);
        setAttr("_editstate", "edit");
        renderTemplate("/org/zhutineirong/diannengliang.html");

    }

    public void getZdlxRadio() {
        String sql = "select ZDLXDM_bm as id ,zdlxdm_mc as text from VW_ZDLXDM where 1=1 ";
        List<Record> zdlxRadio = Db.use("jlzz").find(sql);
        renderJson(zdlxRadio);

    }

}
