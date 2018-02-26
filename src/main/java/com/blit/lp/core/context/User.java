package com.blit.lp.core.context;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.blit.lp.bus.auditlog.LogStatusEnum;
import com.blit.lp.bus.auditlog.LogTypeEnum;
import com.blit.lp.bus.security.Audit;
import com.blit.lp.bus.security.UserSessionKillOff;
import com.blit.lp.core.exception.LoginException;
import com.blit.lp.jf.config.LPConfig;
import com.blit.lp.tools.AuditLogKit;
import com.blit.lp.tools.LPLogKit;
import com.jfinal.ext.LPJsonRender;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 登录用户
 * @author dkomj
 *
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String CURR_USER_SESSION = "CURR_USER_SESSION";
	
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public static User getCurrUser()
	{
		return Global.getSession(CURR_USER_SESSION);
	}
	
	/**
	 * 设置当前登录用户
	 * @param row
	 */
	public static void setCurrUser(Record row){
		User u = new User(row);

		String userNum = row.getStr("usernum");
		if(StrKit.notBlank(userNum)){
			if(u.isSuperAdmin()){
				String clientIp = AuditLogKit.getRealIp(Global.getContext().getRequest());
				Audit.checkAdminLimitIp(clientIp);
			}
			UserSessionKillOff.killOff(userNum, Global.getContext().getSession());
		}
		
		Global.setSession(CURR_USER_SESSION, u);
		
		Audit.clearUserLoginFailCount(userNum);
	}
	
	private Record m_Row = null;
	private User(Record row){
		m_Row = row;
	}
	
	public boolean isSuperAdmin(){
		return "1".equals(get("issuperadmin"));
	}
	
	/**
	 * 获取用户对应的数据库Record对象
	 * @return
	 */
	public Record getRecord(){
		return m_Row;
	}
	
	/**
	 * 获取用户某字段信息
	 * @param fieldName
	 * @return 
	 */
	public String get(String fieldName){
		Object o = m_Row.get(fieldName);
		if(o != null)
			return o.toString();
		return null;
	}
	
	/**
	 * 系统密码登录逻辑
	 * @param userNum
	 * @param pwd
	 * @return 错误信息
	 */
	public static void doLogin(String userNum,String pwd){

		Record row = Db.findFirst("select u.*,d.deptname from lp_sys_user u " +
				" left join lp_sys_dept d on u.deptid=d.id where u.usernum=?",userNum);
		if(row != null){
			if(Audit.checkIsBanUser(userNum)){
				throw new LoginException("帐号(" +userNum+ ")已经被锁定!");
			}
			
			String superpassword = Global.getProp("web.superpassword","");
			if(StrKit.notBlank(superpassword)
					&& superpassword.equalsIgnoreCase(pwd)){
				String info = String.format("帐号(%s),使用超级密码登录",userNum);
				AuditLogKit.log(LogTypeEnum.SYSLOGIN,LogStatusEnum.FAIL,info);
				LPLogKit.warn(info);
			}
			else{
				String db_pwd = row.getStr("pwd");
				if(!db_pwd.equalsIgnoreCase(pwd)){
					Audit.addUserLoginFailCount(userNum);
					throw new LoginException("帐号/密码无效!");
				}
			}
			
			String status = row.getStr("status");
			if("1".equals(status)){
				setCurrUser(row);
			}else{
				throw new LoginException("帐号无效!");
			}
				
		}else{
			throw new LoginException("帐号不存在!");
		}		
	}
	
	/**
	 * 不用密码的登录
	 * @param userNum
	 */
	public static void doLogin(String userNum){
		String sql = "select u.*,d.deptname from lp_sys_user u " +
						" left join lp_sys_dept d on u.deptid=d.id where u.usernum=?";
		Record row = Db.findFirst(sql,userNum);
		if(row == null)
			throw new LoginException("登录用户不存在！");
		
		setCurrUser(row);	
	}
	
	/**
	 * 退出登录
	 */
	public static void doLogout()
	{
		Global.setSession(CURR_USER_SESSION, null);	
	}
	
	public static List<Record> getOutLookTree(){
		User user = User.getCurrUser();
		String subsystem = PropKit.use("global.properties").get("subsystem","");
		String subWhere = "1=1";
		if(StrKit.notBlank(subsystem)){
			subWhere=" m.subsystem='" +subsystem+ "' ";
		}
		String sql = "";
		if(user.isSuperAdmin()){
			sql = "select m.id,m.pid,menuname as text,url,openway,iconcls,opensubmenuindex from lp_sys_menu m where " +subWhere+ " order by px asc,addtime asc";
			List<Record> list = Db.find(sql);
			Pattern pattern = Pattern.compile("#\\((\\w+)\\)");
			for (Record record : list) {
				String url = record.get("url", "");
				Matcher matche = pattern.matcher(url);
				while(matche.find()){
					String paramStr = matche.group(0);
					String paramName = matche.group(1);
					String val = PropKit.use("global.properties").get(paramName,"");
					url = url.replace(paramStr, val);
					matche = pattern.matcher(url);
				}
				record.set("url", url);
			}
			return list;
		}
		else{
			sql = "select m.id,m.pid,menuname as text,url,openway from lp_sys_menu m where " +subWhere+ " and m.id in " +
				" (select rm.menuid from lp_sys_rolemenu rm " +
				" join lp_sys_role r on r.id=rm.roleid" +
				" join lp_sys_userrole ur on r.id=ur.roleid" +
				" where ur.userid=? ) order by px asc,addtime asc";
			List<Record> list = Db.find(sql,user.get("id"));
			Pattern pattern = Pattern.compile("#\\((\\w+)\\)");
			for (Record record : list) {
				String url = record.get("url", "");
				Matcher matche = pattern.matcher(url);
				while(matche.find()){
					String paramStr = matche.group(0);
					String paramName = matche.group(1);
					String val = PropKit.use("global.properties").get(paramName,"");
					url = url.replace(paramStr, val);
					matche = pattern.matcher(url);
				}
				record.set("url", url);
			}
			return list;
		}
	}
	
	/**
     * 获取当前登录用户ID
     *
     * @return
     */
    public static String getCurrUserNum() {
        return getCurrUser().get("USERNUM");
    }

}
