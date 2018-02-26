package com.blit.lp.tools;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import com.blit.lp.bus.auditlog.LogStatusEnum;
import com.blit.lp.bus.auditlog.LogTypeEnum;
import com.blit.lp.core.context.Global;
import com.blit.lp.core.context.User;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 审计日志工具类
 * @author dkomj
 *
 */
public class AuditLogKit {
	/**
	 * 记录系统审计日志
	 * @param type
	 * @param status
	 * @param info
	 */
	public static void log(LogTypeEnum type,LogStatusEnum status, String info){
		Record row = new Record();
		row.set("id", GuidKit.createGuid());
		row.set("logtype", type.toString());
		row.set("status", status.toString());
		row.set("info", info);
		row.set("ip", getRealIp(Global.getContext().getRequest()));
		row.set("addtime", new Timestamp(System.currentTimeMillis()));
		if(User.getCurrUser() != null){
			row.set("userid", User.getCurrUser().get("id"));
			row.set("username", User.getCurrUser().get("username"));			
		}
		Db.save("lp_sys_log","id", row);
	}
	
	public static String getRealIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
