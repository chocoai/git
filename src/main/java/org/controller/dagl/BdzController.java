package org.controller.dagl;

import java.util.List;

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
import java.util.ArrayList;
import java.util.regex.Pattern;

@LPController(controllerkey = "/biandianzhan")
public class BdzController extends Controller {

    public void index() {
        renderTemplate("/org/zhutineirong/biandianzhan.html");
    }

    public void changzhan() {
        String questionText = getPara("questionText");
        String radioValue = getPara("radioValue");

        setAttr("questionText", questionText);
        setAttr("radioValue", radioValue);
        renderTemplate("/org/list/biandianzhan_list.html");
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
        String sql = "select a.bdzbh,a.bdzyxzt,a.dydjdm,a.bdzlxdm,c.bdzlxdm_mc,b.bdzyxztdm_mc,o.org_name,d.dydjdm_mc,a.bdzbs,a.bdzmc,a.cjsj,a.czsj \n"
                + "from dw_bdz a, vw_bdzyxztdm b,vw_bdzlxdm c ,vw_dydjdm d ,o_org o \n"
                + "where a.bdzlxdm = c.bdzlxdm_bm\n"
                + "and a.dydjdm = d.dydjdm_bm \n"
                + "and a.bdzyxzt = b.bdzyxztdm_bm \n"
                + "and o.org_no = a.gddwbm\n"
                + "and bdzbh='260000108494100'";
        if (StrKit.notBlank(id)) {
//		Record data=	Db.findFirst(sql,id);
            Record data = Db.use("jlzz").findFirst(sql);
            if (data == null) {
                throw new SysException("busi_route编辑数据丢失，tqbs:" + id);
            }
            setAttr("data", data);
//		setAttr("_editguid", id);
            setAttr("_editstate", "edit");

        }

        renderTemplate("/org/zhutineirong/biandianzhan.html");
    }


 

    
    //数据列表方法
    public void exportdata() {
        String select = loadFilter();
        String sql = select + " " + DSqlKit.getSql();
        List<Record> list = Db.use("jlzz").find(sql, DSqlKit.getParamList());
        render(new MiniuiExcelRender(getPara("columns"), list, "厂站数据列表.xls", this.getResponse()));
    }
    
    private String loadFilter() {

        String select = "select  a.bdzmc,a.bdzbh,a.bdzyxzt,a.dydjdm,a.bdzlxdm,c.bdzlxdm_mc,b.bdzyxztdm_mc,o.org_name,d.dydjdm_mc,f.cqgsdm_mc ";
        DSqlKit.init("from dw_bdz a ,vw_dydjdm d,o_org o,vw_bdzlxdm c , vw_bdzyxztdm b,vw_cqgsdm f where a.dydjdm = d.dydjdm_bm ");
        DSqlKit.append("and a.gddwbm = o.org_no  ");
        DSqlKit.append("and a.bdzlxdm = c.bdzlxdm_bm ");
        DSqlKit.append("and a.bdzyxzt = b.bdzyxztdm_bm ");
        DSqlKit.append("and a.cqgsdm  = f.cqgsdm_bm  ");

        String question = getPara("question");
        String radioValue = getPara("radioValue");
        if (StrKit.notBlank(question)) {
            DSqlKit.append("and (a.bdzmc like ?  ", "%" + question + "%");
            DSqlKit.append("or a.bdzbh = ? ) ", question);
        }
        if (StrKit.notBlank(radioValue)) {
            DSqlKit.append("and a.bdzlxdm = ?  ", radioValue);
        }
        
        String bdzbh = getPara("bdzbh");
        if (StrKit.notBlank(bdzbh)) {
            DSqlKit.append(" and a.bdzbh = ?", bdzbh);
        }
         String bdzmc = getPara("bdzmc");
        if (StrKit.notBlank(bdzmc)) {
            DSqlKit.append(" and a.bdzmc = ?", bdzmc);
        }
         String bdzlxdm = getPara("bdzlxdm");
         if (StrKit.notBlank(bdzlxdm)) {
            DSqlKit.append("and a.bdzlxdm = ?", bdzlxdm);
        }
        String bdzyxzt = getPara("bdzyxzt");
        if (StrKit.notBlank(bdzyxzt)) {
            DSqlKit.append("and a.bdzyxzt = ?", bdzyxzt);
        }
        String org_name = getPara("org_name");
        if (StrKit.notBlank(org_name)) {
            DSqlKit.append("and a.gddwbm = ?", org_name);
        }
        String dydjdm = getPara("dydjdm");
        if (StrKit.notBlank(dydjdm)) {
            DSqlKit.append("and a.dydjdm = ?", dydjdm);
        }
        String cqgsdm = getPara("cqgsdm");
        if (StrKit.notBlank(cqgsdm)) {
            DSqlKit.append("and a.cqgsdm = ?", cqgsdm);
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
        DSqlKit.append("a.bdzbh asc ");

        return select;
    }

    public void openSearch() {
        renderTemplate("/org/ssxz/changzhanxuanze.html");
    }

    public void query() {
        String bdzbh = getPara("bdzbh");
        String bdzmc = getPara("bdzmc");
        String dydjdm = getPara("dydjdm");
        String bdzyxzt = getPara("bdzyxzt");
        String bdzlxdm = getPara("bdzlxdm");
        String org_name = getPara("org_name");

        Record re = new Record();

        re.set("bdzbh", bdzbh);
        re.set("bdzmc", bdzmc);
        re.set("dydjdm", dydjdm);
        re.set("bdzyxzt", bdzyxzt);
        re.set("bdzlxdm", bdzlxdm);
        re.set("org_name", org_name);

        renderJson(re);

    }
      public void yxztSelect() {
        String sql = "select bdzyxztdm_bm as id, bdzyxztdm_mc  as text  from vw_bdzyxztdm where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }
    
      public void czlxSelect() {
        String sql = "select bdzlxdm_bm as id, bdzlxdm_mc  as text  from vw_bdzlxdm where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void gddwSelect() {
        String sql = "select org_no as id, org_name  as text  from o_org where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void dydjSelect() {
        String sql = "select dydjdm_bm as id, dydjdm_mc  as text  from vw_dydjdm where 1=1";
        List<Record> re = Db.use("jlzz").find(sql);
        renderJson(re);
    }

    public void bdzCx() {

        String id = getPara("id");
        String sql = "select a.BDZBH,a.bdzyxzt,a.dydjdm,a.bdzlxdm,c.bdzlxdm_mc,b.bdzyxztdm_mc,o.org_name,d.dydjdm_mc,a.BDZBS,a.BDZMC,a.cjsj,a.czsj,cq.cqgsdm_mc,a.bdzdz \n"
                + "                            from dw_bdz a, vw_bdzyxztdm b,vw_bdzlxdm c ,vw_dydjdm d ,o_org o ,VW_CQGSDM cq\n"
                + "                            where a.bdzlxdm = c.bdzlxdm_bm(+)\n"
                + "                            and a.dydjdm = d.dydjdm_bm (+)\n"
                + "                            and a.bdzyxzt = b.bdzyxztdm_bm(+) \n"
                + "                            and o.org_no = a.gddwbm(+)\n"
                + "                            and a.cqgsdm=cq.cqgsdm_bm(+)\n"
                + "                            and bdzbh= ? ";

        Record data = Db.use("jlzz").findFirst(sql, id);

        if (data == null) {
            throw new SysException("busi_route编辑数据丢失，tqbs:" + id);
        }
        setAttr("data", data);
        setAttr("_editstate", "edit");

        renderTemplate("/org/zhutineirong/biandianzhan.html");

    }

    public void LoadTree() {
        String bdzbh = getPara("bdzbh");
        String sql = "select * from sea.dw_bdz  where bdzbh = ? ";
        Record re = Db.findFirst(sql, bdzbh);

        String ids = getPara("zzlxdm");
        String subSql = "select bdzbh as id,bdzbs as text from sea.dw_bdz where bdzbs= ? ";
        Record subRe = Db.findFirst(subSql, ids);

        Record pra = new Record();
        pra.set("id", bdzbh);
        pra.set("text", bdzbh);

        List<Record> treeList = new ArrayList<Record>();
        treeList.add(pra);
        treeList.add(subRe);

        renderJson(treeList);

    }

    public void getCzlxRadio() {
        String sql = "select bdzlxdm_bm as id ,bdzlxdm_mc as text from vw_bdzlxdm where 1=1 ";
        List<Record> czlxRadio = Db.use("jlzz").find(sql);
        renderJson(czlxRadio);
    }
    
    public void testVal(){
        String wenzi = "贵港";
        String shuzi = "4054610ggg64";
        
    }
    
    public static boolean isInt(String str) {  
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
  }

}
