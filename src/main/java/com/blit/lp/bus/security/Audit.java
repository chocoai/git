package com.blit.lp.bus.security;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.blit.lp.bus.auditlog.LogStatusEnum;
import com.blit.lp.bus.auditlog.LogTypeEnum;
import com.blit.lp.core.context.Global;
import com.blit.lp.core.exception.LoginException;
import com.blit.lp.tools.AuditLogKit;
import com.blit.lp.tools.LPCacheKit;
import com.blit.lp.tools.LPLogKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;

/**
 * 安全审计
 * @author dkomj
 *
 */
public class Audit {
	public static final String LOGINCOUNTCACHENAME= "lp_user_loginfail_count";
	public static final String USERLOCKCACHENAME= "lp_user_lock";
	/**
	 * 检查IP白名单和黑白单
	 * @param clientIp
	 */
	public static void checkLimitIp(String clientIp) {
		String allowip = PropKit.use("global.properties").get("securyty.ip.allow", "");
		if(StrKit.notBlank(allowip)){
			if(!checkIp(clientIp,allowip)){
				String info = "IP地址不在允许访问范围内。";
				throw new LoginException(info);
			}
		}
		
		String denyip = PropKit.use("global.properties").get("securyty.ip.deny", "");
		if(StrKit.notBlank(denyip)){
			if(checkIp(clientIp,denyip)){
				String info = "IP地址不允许访问。";
				throw new LoginException(info);
			}
		}
	}
	/**
	 * 管理员登录检查IP白名单和黑白单
	 * @param clientIp
	 */
	public static void checkAdminLimitIp(String clientIp) {
		String adminallowip = PropKit.use("global.properties").get("securyty.ip.adminallow", "");
		if(StrKit.notBlank(adminallowip)){
			if(!checkIp(clientIp,adminallowip)){
				String info = "系统管理员IP地址不在允许访问范围内。";
				throw new LoginException(info);
			}
		}
	}
	
	/**
	 * 检验密码是否满足规范(已放弃在服务端检验,用js检验)
	 * @param newPwd
	 * @return
	 */
	@Deprecated
	public static String checkPwd(String newPwd) {
		if(StrKit.isBlank(newPwd))
			return "不能为空!";
		
		int minlength = PropKit.use("global.properties").getInt("securyty.pwd.minlength", -1);
		int minspecialcharnum = PropKit.use("global.properties").getInt("securyty.pwd.minspecialcharnum", -1);
		
		if(minlength > 0 && newPwd.length() < minlength){
			return "长度至少" +minlength+ "位!";
		}
		
		if(minspecialcharnum > 0){
			List<String> specialcharList = new ArrayList<String>();
			Pattern pat = Pattern.compile("[^0-9a-zA-Z]");
			Matcher ma = pat.matcher(newPwd);
			while (ma.find()) {
				specialcharList.add(ma.group());
			}
			
			if(specialcharList.size() < minspecialcharnum){
				return "特殊字符(非数字、字母)至少" +minlength+ "位!";
			}
		}
		return "";
	}
	
	/**
	 * 添加用户登录出错次数
	 * @param userCode
	 */
	public static void addUserLoginFailCount(String userCode) {
		int faillocknum = PropKit.use("global.properties").getInt("securyty.login.faillocknum", -1);
		int faillocktime = PropKit.use("global.properties").getInt("securyty.login.faillocktime", -1);
		if(faillocknum <= 0 || faillocktime <= 0)
			return;
		
		Integer val = LPCacheKit.get(LOGINCOUNTCACHENAME, userCode);
		int count = val == null ? 0 : val;
		val = count + 1;
		LPCacheKit.put(LOGINCOUNTCACHENAME, userCode, val);
		
		if(val >= faillocknum)
		{
			banUser(userCode,faillocktime);
			String info = String.format("帐号(%s)登录错误%d次，已被锁定%d分钟。", userCode,val,faillocktime);
			clearUserLoginFailCount(userCode);
			throw new LoginException(info);
		}
		else{
			int tryCount = faillocknum-val;
			String info = String.format("帐号(%s)登录错误%d次，还有%d次机会将会被锁定。", userCode,val,tryCount);
			throw new LoginException(info);
		}
	}
	
	
	/**
	 * 清空用户登录出错次数
	 * @param userCode
	 */
	public static void clearUserLoginFailCount(String userCode) {
		LPCacheKit.remove(LOGINCOUNTCACHENAME, userCode);
	}
	
	/**
	 * 锁定用户
	 * @param userCode
	 * @param lockMin
	 */
	public static void banUser(String userCode,int lockMin){
		LockUser lockUser = new LockUser(userCode);
		int expiredSeconds = lockMin * 60;
		LPCacheKit.put(USERLOCKCACHENAME, userCode, lockUser, expiredSeconds);
	}
	
	/**
	 * 取消锁定用户
	 * @param userCode
	 */
	public static void clearBanUser(String userCode){
		LPCacheKit.remove(USERLOCKCACHENAME, userCode);
			
	}
	
	/**
	 * 检测用户是否被锁定
	 * @param userCode
	 */
	public static boolean checkIsBanUser(String userCode) {
		LockUser lockUser = LPCacheKit.get(USERLOCKCACHENAME, userCode);
		if(lockUser != null){
			return true;
		}
		
		return false;
	}
	
	//检查ip是否符合表达式
	private static boolean checkIp(String clientip,String ipexp){
		if(StrKit.isBlank(clientip) || StrKit.isBlank(clientip))
			return false;
		
		clientip = clientip.replaceAll("\\s", "");
		ipexp = ipexp.replaceAll("\\s", "");
		
		boolean bl = false;
		String[] ips1 = clientip.split("\\.");
		for (String exp : ipexp.split(",")) {
			String[] ipTmps = exp.split("\\.");
			if(ips1.length == ipTmps.length){
				bl = true;
				for (int i=0; i < ips1.length; i++) {
					if(!"*".equals(ipTmps[i]) && !ips1[i].equals(ipTmps[i])){
						bl = false;
						break;
					}
				}
			}
		}
		return bl;
	}

}
