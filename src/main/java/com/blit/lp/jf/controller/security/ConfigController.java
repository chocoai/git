package com.blit.lp.jf.controller.security;

import com.blit.lp.jf.config.LPController;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;

@LPController(controllerkey = "/sys/config")
@Clear
public class ConfigController extends Controller{
	public void getAjaxEncode(){
		renderJson(Ret.ok("ajaxEncode", PropKit.use("global.properties").getBoolean("web.ajaxencode",true)));
	}
	
	public void getDefaultPwd(){
		renderJson(Ret.ok("data", PropKit.use("global.properties").get("web.defaultpassword","")));
	}
	
	public void getPwdPolicy(){
		renderJson(Ret.ok("minlength", PropKit.use("global.properties").getInt("securyty.pwd.minlength",-1))
				.set("minspecialcharnum", PropKit.use("global.properties").getInt("securyty.pwd.minspecialcharnum",-1)));
	}
}
