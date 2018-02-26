package com.blit.lp.jf.interceptor.accesscontrol;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


import com.blit.lp.core.context.User;
import com.blit.lp.core.exception.NoAccessException;
import com.blit.lp.tools.TreeSqlKit;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;


/**
 * 访问控制处理器，
 * 只需要在Controller类、或者方法前添加注解(LPSysAdminAccess、LPDeptAccess、LPRoleAccess)
 * @author dkomj
 * 
 */
@SuppressWarnings("unchecked")
public class AccessInterceptor implements Interceptor {
	
	public static AccessInterceptor me = new AccessInterceptor();
	public static AccessInterceptor me(){
		return me;
	}
	private static final Class<? extends Annotation>[] AnnotationClass
	= new Class[]{LPSysAdminAccess.class,LPDeptAccess.class,LPRoleAccess.class};
			
	@Override
	public void intercept(Invocation inv) {
		List<Annotation> list = new ArrayList<Annotation>();
		Controller c = inv.getController();
		for (Class<? extends Annotation> cls : AnnotationClass) {
			Annotation ann = c.getClass().getAnnotation(cls);
			if(ann != null){
				list.add(ann);
			}
		}
		
		Method m = inv.getMethod();
		for (Class<? extends Annotation> cls : AnnotationClass) {
			Annotation ann = m.getAnnotation(cls);
			if(ann != null){
				list.add(ann);
			}
		}
		
		for (Annotation ann : list) {
			if(ann instanceof LPSysAdminAccess){
				doSysAdminControl((LPSysAdminAccess)ann);
			}
			else if(ann instanceof LPDeptAccess){
				doDeptControl((LPDeptAccess)ann);
			}
			if(ann instanceof LPRoleAccess){
				doRoleControl((LPRoleAccess)ann);
			}
		}

	}
	
	private void doSysAdminControl(LPSysAdminAccess ann){
		User user = User.getCurrUser();
		if(!user.isSuperAdmin())
			throw new NoAccessException("非系统管理员，禁止访问");
	}
	
	private void doDeptControl(LPDeptAccess ann){
		User user = User.getCurrUser();
		String sql = TreeSqlKit.GetParentSql("select deptcode,deptname"
				, "lp_sys_dept"
				, "id='" +user.get("deptid")+ "'"
				, "id", "pid", 5);
		
		List<Record> list = Db.find(sql);
		
		boolean bl = false;
		String[] depts = ann.value();
		for (String val : depts) {
			for (Record r : list) {
				if(val.equalsIgnoreCase(r.getStr("deptcode"))
						|| val.equalsIgnoreCase(r.getStr("deptname"))){
					bl = true;
					break;
				}
				
				if(bl)
					break;
			}
		}
		
		if(bl){
			throw new NoAccessException("所在部门无权访问");
		}
	}

	private void doRoleControl(LPRoleAccess ann){
		User user = User.getCurrUser();
		String sql = "select rolecode,roleName from lp_sys_role where in in (select roleid from lp_sys_userrole where userid=?)";
		List<Record> list = Db.find(sql,user.get("id"));
		boolean bl = false;
		String[] depts = ann.value();
		for (String val : depts) {
			for (Record r : list) {
				if(val.equalsIgnoreCase(r.getStr("rolecode"))
						|| val.equalsIgnoreCase(r.getStr("roleName"))){
					bl = true;
					break;
				}
				
				if(bl)
					break;
			}
		}
		
		if(bl){
			throw new NoAccessException("用户角色无权访问");
		}
	}

}
