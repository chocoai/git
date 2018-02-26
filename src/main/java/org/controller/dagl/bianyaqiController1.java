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
import java.util.ArrayList;

@LPController(controllerkey = "/bianyaqi")
public class bianyaqiController1 extends Controller {

    public void index() {
        String questionText = getPara("questionText");
        setAttr("questionText", questionText);
        renderTemplate("/org/list/bianyaqi_list.html");
    }

    public void first() {
        renderTemplate("/org/zhutineirong/bianyagongbian_edit.html");
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

    public void del() {
        String ids = getPara("ids");
        for (String id : ids.split(",")) {
            Db.use("jlzz").deleteById("ceshi", "id", id);
        }
        renderJson(Ret.ok());
    }
    
     public void bianyaqi_list() {
        String questionText = getPara("questionText");
        String radioValue = getPara("radioValue");

        setAttr("questionText", questionText);
        setAttr("radioValue", radioValue);
        renderTemplate("/org/list/bianyaqi_list.html");
    }
    
    
      public void exportdata() {
        String select = loadFilter();
        String sql = select + " " + DSqlKit.getSql();
        List<Record> list = Db.use("jlzz").find(sql, DSqlKit.getParamList());
        render(new MiniuiExcelRender(getPara("columns"), list, "变压器数据列表.xls", this.getResponse()));
    }

    //查询条件
    private String loadFilter() {
        String select = " select dy.byqmc,dy.yxbyqbs,dw.xlmc,vwb.BYQLX_MC,oo.org_name,dt.tqmc,vs.SBYXZTDM_MC";
        DSqlKit.init("from dw_yxbyq dy,DW_XLXD dw,VW_BYQLX vwb,o_org oo,DW_TQ dt,VW_SBYXZTDM vs"
                + " where dy.tqbs=dt.tqbs(+) ");
        DSqlKit.append("and dy.gddwbm = oo.ORG_NO(+) ");
        DSqlKit.append("and dy.YXZTDM = vs.SBYXZTDM_BM(+)  ");
        DSqlKit.append("and dy.SBLXDM= vwb.BYQLX_BM(+) ");

        String question = getPara("question");
        if (StrKit.notBlank(question)) {
            DSqlKit.append("and ( dy.byqmc like ?  ", "%" + question + "%");
            DSqlKit.append("or dy.yxbyqbs = ? ) ", question);
        }

        String org_no = getPara("org_no");
        if (StrKit.notBlank(org_no)) {
            DSqlKit.append(" and org_no = ?", org_no);
        }
        String byqmc = getPara("byqmc");
        if (StrKit.notBlank(byqmc)) {
            DSqlKit.append(" and byqmc = ?", byqmc);
        }
        String sbyxztdm_bm = getPara("sbyxztdm_bm");
        if (StrKit.notBlank(sbyxztdm_bm)) {
            DSqlKit.append(" and sbyxztdm_bm = ?", sbyxztdm_bm);
        }
        String byqlx_bm = getPara("byqlx_bm");
        if (StrKit.notBlank(byqlx_bm)) {
            DSqlKit.append(" and byqlx_bm = ?", byqlx_bm);
        }
        String yxbyqbs = getPara("yxbyqbs");
        if (StrKit.notBlank(yxbyqbs)) {
            DSqlKit.append(" and yxbyqbs = ?", yxbyqbs);
        }
        String xlmc = getPara("xlmc");
        if (StrKit.notBlank(xlmc)) {
            DSqlKit.append(" and xlmc = ?", xlmc);
        }

        //排序
        DSqlKit.append(" order by ");
        String sortFields = getPara("sortFields");
        if (StrKit.notBlank(sortFields)) {
            JSONArray sortAr = JSONArray.parseArray(sortFields);
            for (int i = 0; i < sortAr.size(); i++) {
                DSqlKit.append(" " + sortAr.getJSONObject(i).getString("field") + " " + sortAr.getJSONObject(i).getString("dir") + ", ");
            }
        }
        DSqlKit.append(" dy.yxbyqbs asc ");

        return select;
    }

    public void bianyaqi(){
        
                String id=getPara("id");
                
		String sql="select a.YXBYQBS,a.BYQMC,o.org_name,a.BYQZCBH,lx.byqlx_mc,a.edrl,yx.SBYXZTDM_MC,a.DQZJXFS,a.LQFSDM,xh.BYQXHDM_MC,a.tqbs,tq.tqbh,a.BYQZBXZ,dy.dydjdm_mc,a.yhbh,a.gyeddy,a.zyeddy,a.dyeddy,a.ccbh,a.cjsj,a.czsj,a.cjmc\n" +
                            "from dw_yxbyq a,o_org o,VW_BYQLX lx ,VW_SBYXZTDM yx,VW_BYQXHDM xh,dw_tq tq,VW_DYDJDM dy\n" +
                            "where a.yxbyqbs=?\n" +
                            "and a.gddwbm=o.org_no(+)\n" +
                            "and a.sblxdm=lx.byqlx_bm(+)\n" +
                            "and a.yxztdm=yx.SBYXZTDM_bm(+)\n" +
                            "and a.sbxhdm=xh.BYQXHDM_BM(+)\n" +
                            "and a.tqbs=tq.tqbs(+)\n" +
                            "and a.gyeddy=dy.dydjdm_bm(+)\n" +
                            "and a.dyeddy=dy.dydjdm_bm(+)\n" +
                            "and a.zyeddy=dy.dydjdm_bm(+)";
                
		if(StrKit.notBlank(id)){
                    
		Record data=Db.use("jlzz").findFirst(sql,id);
		if(data == null)
			throw new SysException("busi_route编辑数据丢失，route_no:" + id);
		setAttr("data", data);
		}
		
		renderTemplate("/org/zhutineirong/bianyagongbian_edit.html");
    
    }      

    public void LoadTree() {
        String yxbyqbs = getPara("yxbyqbs");
        String sql = "select * from sea.dw_yxbyq  where yxbyqbs = ? ";
        Record re = Db.findFirst(sql, yxbyqbs);

        String ids = getPara("zdbs");
        String subSql = "select zdbs as id,zdmc as text from sea.SB_YXZD where zdbs= ? ";
        Record subRe = Db.findFirst(subSql, ids);

        Record pra = new Record();
        pra.set("id", yxbyqbs);
        pra.set("text", yxbyqbs);

        List<Record> treeList = new ArrayList<Record>();
        treeList.add(pra);
        treeList.add(subRe);

        renderJson(treeList);

    }

    public void edit() {
        String id = getPara("id");
        String sql = "select * from dw_yxbyq where yxbyqbs=?";
        if (StrKit.notBlank(id)) {
            Record data = Db.use("jlzz").findFirst(sql, id);
            if (data == null) {
                throw new SysException("dw_yxbyq编辑数据丢失yxbyqbs:" + id);
            }
            setAttr("data", data);
            setAttr("_editguid", id);
            setAttr("_editstate", "edit");

        }

        renderTemplate("/org/zhutineirong/bianyagongbian_edit.html");
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

    public void onSearcj(){
		renderTemplate("/org/ssxz/bianyaxuanze.html");
	}
        
        public void search(){
		String byqmc=getPara("byqmc");
		String org_no=getPara("org_no");
		String sbyxztdm_bm=getPara("sbyxztdm_bm");
		String yxbyqbs=getPara("yxbyqbs");
		String byqlx_bm=getPara("byqlx_bm");
		String xlmc=getPara("xlmc");
		Record re = new Record();
		re.set("byqmc", byqmc);
		re.set("org_no", org_no);
		re.set("sbyxztdm_bm", sbyxztdm_bm);
		re.set("byqlx_bm", byqlx_bm);
                re.set("yxbyqbs", yxbyqbs);
		re.set("xlmc", xlmc);
		renderJson(re);
		
	}
        
	//下拉
		public void yxztSelect(){
			String sql="select sbyxztdm_bm as id, sbyxztdm_mc  as text  from vw_sbyxztdm where 1=1";
			List<Record> re = Db.use("jlzz").find(sql);
			renderJson(re);
		}
		//下拉
	    public void gddwSelect() {
		   String sql = "select org_no as id, org_name  as text  from o_org where 1=1";
		   List<Record> re = Db.use("jlzz").find(sql);
		  renderJson(re);
	    }
		//下拉
		public void byqlxSelect(){
			String sql="select byqlx_bm as id, byqlx_mc  as text  from vw_byqlx where 1=1";
			List<Record> re = Db.use("jlzz").find(sql);
			renderJson(re);
	    }

}
