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
import java.util.ArrayList;
import java.util.List;

@LPController(controllerkey = "/bumen_zhuti")
public class bumenzhutiController extends Controller {

    public void index() {
        renderTemplate("/org/zhutineirong/bumen_zhuti.html");
    }

    public void bumen() {
        String questionText = getPara("questionText");
        setAttr("questionText", questionText);
        renderTemplate("/org/list/bumen_zhuti_list.html");
    }

    //构建列表方法
    public void list() {
        int pageIndex = getParaToInt("pageIndex") + 1;
        int pageSize = getParaToInt("pageSize");

        String select = getList();

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
        render(new MiniuiExcelRender(getPara("columns"), list, "部门数据列表.xls", this.getResponse()));
    }

        public String getList(){
		String select =" select  xt.dwsxdm ,xt.tjkjlxdm, lx.zzlxdm_mc ,xt.zzlj,xt.zzjc,xt.pxh,xt.cjrq,xt.czrq, xt.zzbs, xt.zzbmdm, xt.zzmc,xt.sjzzbs,tj.tjkjfldm_mc ";
		DSqlKit.init("from xt_zzjg xt ,vw_zzlxdm lx,vw_tjkjfldm tj where xt.zzlxdm=lx.zzlxdm_bm " );
		DSqlKit.append("and xt.tjkjlxdm = tj.tjkjfldm_bm ");
		
                 String question = getPara("question");
                 if(StrKit.notBlank(question)){
                    DSqlKit.append("and (xt.zzmc like ?  ", "%" + question + "%");
                    DSqlKit.append("or xt.zzbmdm = ? ) ", question);
                 }
                
		//把查询窗口所有的条件添加进来
		String zzmc = getPara("zzmc");
		if(StrKit.notBlank(zzmc)){
			DSqlKit.append(" and xt.zzmc = ?",zzmc);
		}
		String tjkjlxdm = getPara("tjkjlxdm");
		if(StrKit.notBlank(tjkjlxdm)){
			DSqlKit.append(" and xt.tjkjlxdm = ?",tjkjlxdm);
		}
		String zzbmdm = getPara("zzbmdm");
		if(StrKit.notBlank(zzbmdm)){
			DSqlKit.append(" and xt.zzbmdm = ?", zzbmdm);
		}
		String zzjc = getPara("zzjc");
		if(StrKit.notBlank(zzjc)){
			DSqlKit.append(" and xt.zzjc = ?", zzjc);
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
                DSqlKit.append(" xt.ZZBMDM desc ");  //主表的主键
		
		return select ;
	}
//list页面传过来的“onSearcz”动作
		public void onSearbm(){
			renderTemplate("/org/ssxz/bumenxuanze.html");
		}
		
	
		//xuanze 页面传过来的query动作。
		public void query(){
			//查询条件
			String zzmc = getPara("zzmc");
			String tjkjlxdm = getPara("tjkjlxdm");
			String zzbmdm = getPara("zzbmdm");
                        String zzjc = getPara("zzjc");
			
			Record re = new Record();
			re.set("zzmc", zzmc);
			re.set("tjkjlxdm", tjkjlxdm);
			re.set("zzbmdm", zzbmdm);
                        re.set("zzjc", zzjc);
			renderJson(re);
			
		}
                
	//下拉列表url
	public void tjSelect(){
		String sql="select tjkjfldm_bm as id, tjkjfldm_mc  as text  from vw_tjkjfldm where 1=1";
		List<Record> re = Db.use("jlzz").find(sql);
		renderJson(re);
	}  
        
        public void gddwSelect(){
		String sql="select zzjc  from xt_zzjg ";
		List<Record> re = Db.use("jlzz").find(sql);
		renderJson(re);
	}         

    //保存、修改方法
    public void edit() {
        String id = getPara("id");
        String sql = "select  xt.DWSXDM ,xt.TJKJLXDM, lx.ZZLXDM_MC ,xt.ZZLJ,xt.ZZJC,xt.PXH,xt.cjrq,xt.czrq, xt.zzbs, xt.ZZBMDM, xt.zzmc,xt.sjzzbs\n"
                + "from xt_zzjg xt ,VW_ZZLXDM lx\n"
                + "where zzbs='040500000007681'\n"
                + "and xt.zzlxdm=lx.ZZLXDM_BM";
        if (StrKit.notBlank(id)) {
//		Record data=	Db.use("jlzz").findFirst(sql,id);
            Record data = Db.use("jlzz").findFirst(sql);
            if (data == null) {
                throw new SysException("busi_route编辑数据丢失，route_no:" + id);
            }
            setAttr("data", data);
//		setAttr("_editguid", id);
            setAttr("_editstate", "edit");

        }

        renderTemplate("/org/zhutineirong/bumen_zhuti.html");
    }

    @Before(Tx.class)
    public void save() {
        String _editstate = getPara("_editstate");
        String _editguid = getPara("_editguid");
        Record row = LPRecordKit.createFromRequest("i_yxzd_zc", "data.");

        row.set("id", _editguid);
        if ("edit".equals(_editstate)) {
            Db.use("jlzz").update("i_yxzd_zc", "id", row);
        }
        renderJson(Ret.ok());
    }

    //删除方法
    public void del() {
        String ids = getPara("ids");
        for (String id : ids.split(",")) {
            Db.use("jlzz").deleteById("i_yxzd_zc", "JYJG", id);
        }
        renderJson(Ret.ok());
    }

    public void orgCx() {

        String id = getPara("id");
        String sql = "select  xt.DWSXDM ,xt.TJKJLXDM, lx.ZZLXDM_MC ,xt.ZZLJ,xt.ZZJC,xt.PXH,xt.cjrq,xt.czrq, xt.zzbs, xt.ZZBMDM, xt.zzmc,xt.sjzzbs\n"
                + "from xt_zzjg xt ,VW_ZZLXDM lx\n"
                + "where zzbmdm= ? \n"
                + "and xt.zzlxdm=lx.ZZLXDM_BM (+)";

        Record data = Db.use("jlzz").findFirst(sql, id);
        if (data == null) {
            throw new SysException("busi_route编辑数据丢失，route_no:" + id);
        }
        setAttr("data", data);

        setAttr("_editstate", "edit");

        renderTemplate("/org/zhutineirong/bumen_zhuti.html");
    }

    public void LoadTree() {
        String zzbmdm = getPara("zzbmdm");
        String sql = "select * from sea.xt_zzjg  where zzbmdm = ? ";
        Record re = Db.findFirst(sql, zzbmdm);

        String ids = getPara("zzlxdm");
        String subSql = "select zzlxdm as id,zzlxdm as text from sea.xt_zzjg where zzlxdm= ? ";
        Record subRe = Db.findFirst(subSql, ids);

        Record pra = new Record();
        pra.set("id", zzbmdm);
        pra.set("text", zzbmdm);

        List<Record> treeList = new ArrayList<Record>();
        treeList.add(pra);
        treeList.add(subRe);

        renderJson(treeList);

    }

}
