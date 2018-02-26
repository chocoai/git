package com.blit.blit_jlzz.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blit.blit_jlzz.service.index.TreeNodeService;
import com.blit.lp.bus.auditlog.LogStatusEnum;
import com.blit.lp.bus.auditlog.LogTypeEnum;
import com.blit.lp.bus.security.Audit;
import com.blit.lp.core.context.Global;
import com.blit.lp.core.context.User;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.jf.interceptor.AuthInterceptor;
import com.blit.lp.tools.AuditLogKit;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;


@LPController(controllerkey="/")
public class IndexController extends Controller {
	private final TreeNodeService apiTreeNodeService = Duang.duang(TreeNodeService.class);
	
	@Clear(AuthInterceptor.class)
	public void login(){
		setAttr("title", Global.getProp("title"));
		renderTemplate("/sys/index/themes/default/login.html");
	}
	
	public void index(){
		List<Record> list = User.getOutLookTree();
		Record root = new Record().set("id", "-1");
		loadChilds(list,root);
		List<Record> newTreeList = root.get("childs");
		setAttr("menuList", newTreeList);
		renderTemplate("/jlzz/index/index.html");
	}
	private void loadChilds(List<Record> list,Record record){
		String id = record.getStr("id");
		List<Record> childList = new ArrayList<Record>();
		record.set("childs", childList);
		for (Record r : list) {
			if(r.getStr("pid").equalsIgnoreCase(id)){
				childList.add(r);
				loadChilds(list,r);
			}
		}
	}
	
	public void sc(){
		renderTemplate("/jlzz/index/sc.html");
	}
	
	public void pwTree(){
		renderTemplate("/jlzz/index/pwtree.html");
	}
        
        public void lieBiao(){
		renderTemplate("/jlzz/index/liebiao.html");
	}
	
	public void pwTreeData(){
		String id = getPara("id");
		String strData = apiTreeNodeService.leftTree(id, User.getCurrUserNum());
		
		JSONArray list = JSONObject.parseArray(strData);
		for (Object object : list) {
			JSONObject itemObj = (JSONObject)object;
			itemObj.put("pid", id);
		}
		renderJson(list);
		
		/*String id = getPara("id");
		if(StrKit.isBlank(id))
			id = "orgtree_root";
		
		String url = "http://jlzz.default.paas.blit.cloud/osp/sysman/rest/org/leftTree";
		Map<String,String> headers = new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID=5BB548822E1F46A6B0DA27DFB97C6880; __guid=76565326.4373120161706614000.1516258555068.4844; token=DA29BE78C9655B65.75CD1D7488ACC7CA8DF5D126349EA640.E6112D25AD549908");
		String strData = HttpKit.post(url, "node=" + id,headers);
		
		JSONObject jsonObj = JSONObject.parseObject(strData);
		JSONArray list = jsonObj.getJSONArray("treeNodeList");
		for (Object object : list) {
			JSONObject itemObj = (JSONObject)object;
			itemObj.put("pid", id);
			itemObj.put("isLeaf", itemObj.get("leaf"));
		}
		renderJson(list);*/
	}
	
	public void zwTree(){
		renderTemplate("/jlzz/index/zwtree.html");
	}
	
	public void zwTreeData(){
		String id = getPara("id");
		String strData = apiTreeNodeService.leftTreeTrans_leftTree(id, User.getCurrUserNum());
		
		JSONArray list = JSONObject.parseArray(strData);
		for (Object object : list) {
			JSONObject itemObj = (JSONObject)object;
			itemObj.put("pid", id);
		}
		renderJson(list);
		
		/*String id = getPara("id");
		if(StrKit.isBlank(id))
			id = "trans_net_root";
		
		String url = "http://jlzz.default.paas.blit.cloud/osp/sysman/rest/org/leftTreeTrans_leftTree";
		Map<String,String> headers = new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID=5BB548822E1F46A6B0DA27DFB97C6880; __guid=76565326.4373120161706614000.1516258555068.4844; token=DA29BE78C9655B65.75CD1D7488ACC7CA8DF5D126349EA640.E6112D25AD549908");
		String strData = HttpKit.post(url, "node=" + id,headers);
		
		JSONObject jsonObj = JSONObject.parseObject(strData);
		JSONArray list = jsonObj.getJSONArray("treeNodeList");
		for (Object object : list) {
			JSONObject itemObj = (JSONObject)object;
			itemObj.put("pid", id);
			itemObj.put("isLeaf", itemObj.get("leaf"));
		}*/
		//renderJson(list);
	}
	
	//密码登录处理逻辑
	@Clear(AuthInterceptor.class)
	public void dologin(){
		String usernum = getPara("usernum");
		String pwd = getPara("pwd");
		
		String clientIp = AuditLogKit.getRealIp(getRequest());
		Audit.checkLimitIp(clientIp);

		User.doLogin(usernum, pwd);
		renderJson(Ret.ok());
		AuditLogKit.log(LogTypeEnum.SYSLOGIN,LogStatusEnum.SUCCESS,"登录成功");
	}
		
	@Clear(AuthInterceptor.class)
	public void dologout(){
		User.doLogout();
		renderJson(Ret.ok());
	}
	
	//修改密码
	public void dorestpwd(){
		String pwd1 = getPara("pwd1");
		String pwd2 = getPara("pwd2");
		
		if(StrKit.isBlank("pwd1") || StrKit.isBlank("pwd2")){
			renderJson(Ret.fail("msg", "原、新密码不能为空!"));
			return;
		}
		
		User user = User.getCurrUser();
		
		String dbPwd = user.get("pwd");
		if(!dbPwd.equalsIgnoreCase(pwd1)){
			renderJson(Ret.fail("msg", "原密码错误！"));
			return;
		}
		
		Db.update("update lp_sys_user set pwd=? where id=?",pwd2,user.get("id"));
		renderJson(Ret.ok("msg", ""));
		AuditLogKit.log(LogTypeEnum.SYSMANAGER,LogStatusEnum.SUCCESS,"修改密码成功");
	}
}
