package com.blit.lp.jf.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




import com.blit.lp.bus.auditlog.LogStatusEnum;
import com.blit.lp.bus.auditlog.LogTypeEnum;
import com.blit.lp.bus.security.Audit;
import com.blit.lp.core.context.Global;
import com.blit.lp.core.context.User;
import com.blit.lp.core.exception.SysException;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.jf.interceptor.AuthInterceptor;
import com.blit.lp.jf.interceptor.accesscontrol.LPSysAdminAccess;
import com.blit.lp.tools.AuditLogKit;
import com.blit.lp.tools.LPCacheKit;
import com.blit.lp.tools.LPLogKit;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.LPJsonRender;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;


/**
 * 登录和首面控制器
 * @author dkomj
 *
 */
//@LPController(controllerkey="/")
public class IndexController extends Controller {
	public void indexold(){
		renderTemplate("/sys/index.html");
	}
	
	public void index(){
		setAttr("title", Global.getProp("title"));
		setAttr("homepage", Global.getProp("homepage"));
		renderTemplate("/sys/index/themes/default/index.html");
		//renderTemplate("/sys/index.html");
	}
	
	
	@Clear(AuthInterceptor.class)
	public void login(){
		setAttr("title", Global.getProp("title"));
		renderTemplate("/sys/index/themes/default/login.html");
		
		//setAttr("title", Global.getProp("title"));
		//renderTemplate("/sys/login.html");
	}
	
	//密码登录处理逻辑
	@Clear(AuthInterceptor.class)
	public void dologin(){
		String usernum = getPara("usernum");
		String pwd = getPara("pwd");
		
		String clientIp = AuditLogKit.getRealIp(getRequest());
		Audit.checkLimitIp(clientIp);

		User.doLogin(usernum, pwd);
                
                Ret.setToOldWorkMode();
		renderJson(Ret.ok());
                
		AuditLogKit.log(LogTypeEnum.SYSLOGIN,LogStatusEnum.SUCCESS,"登录成功");
	}
	
	//获取菜单树
	public void getOutLookTree(){
		renderJson(new LPJsonRender(User.getOutLookTree()));
	}
	
	//退出登录逻辑
	@Clear(AuthInterceptor.class)
	public void dologout(){
		User.doLogout();
                Ret.setToOldWorkMode();
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
                Ret.setToOldWorkMode();
		renderJson(Ret.ok("msg", ""));
		AuditLogKit.log(LogTypeEnum.SYSMANAGER,LogStatusEnum.SUCCESS,"修改密码成功");
	}
}
