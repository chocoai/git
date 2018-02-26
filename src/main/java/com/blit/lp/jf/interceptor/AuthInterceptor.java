package com.blit.lp.jf.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.blit.lp.core.context.Global;
import com.blit.lp.core.context.User;
import com.blit.lp.core.exception.SysException;
import com.blit.lp.jf.interceptor.accesscontrol.AccessInterceptor;
import com.blit.lp.jf.interceptor.flow.FlowInterceptor;
import com.blit.lp.tools.LPLogKit;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * 登录验证
 * @author dkomj
 *
 */
public class AuthInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		//测试自动用户登录
		if(Global.isDebug() && Global.getProp("debuguser") != null && User.getCurrUser() == null){
			User.doLogin(Global.getProp("debuguser"));
		}
		
		//登录验证
		if(User.getCurrUser() == null){
			inv.getController().redirect301("/login");
			return;
		}
		
		if(User.getCurrUser() != null){
			inv.getController().setAttr("g_user", User.getCurrUser().getRecord());
		}
		
		AccessInterceptor.me().intercept(inv);
		
		inv.invoke();
	}

}

