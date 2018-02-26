package com.blit.lp.jf.interceptor.flow;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.blit.lp.bus.flow.FlowInstance;
import com.blit.lp.bus.flow.FlowUser;
import com.blit.lp.core.exception.FlowException;
import com.blit.lp.tools.IdKit;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

public class FlowInterceptor implements Interceptor {

	private static final Class<? extends Annotation>[] AnnotationClass
	= new Class[]{LPFlowOpen.class,LPFlowSave.class};
			
	@Override
	public void intercept(Invocation inv) {
		final List<Annotation> list = new ArrayList<Annotation>();
		final Controller c = inv.getController();
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
		
		final Invocation inv2 = inv;
		Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				for (Annotation ann : list) {
					if(ann instanceof LPFlowOpen){
						doFlowOpen(c,(LPFlowOpen)ann);
					}
					
					if(ann instanceof LPFlowSave){
						doFlowSave(c,(LPFlowSave)ann);
					}
				}
				
				inv2.invoke();
				return true;
			}
		});

	}

	private void doFlowOpen(Controller c,LPFlowOpen ann){
		String flowId = ann.flowid();
		String i = c.getPara("i");
		String n = c.getPara("n");
		FlowUser user = new FlowUser();
		
		FlowInstance fi = null;
		Record nodeInstanceRow = null;
		String isCreateForm = "false";
		if(StrKit.isBlank(i)){
			isCreateForm = "true";
			fi = FlowInstance.create(flowId, user);
			nodeInstanceRow = fi.getLastFlowNodeInstanceRow();
		}
		else{
			if(StrKit.isBlank(n))
				throw new FlowException("缺少必要参数:n！");
			fi = FlowInstance.load(i);
			nodeInstanceRow = fi.getFlowNodeInstanceRow(n);
			fi.exec(user, nodeInstanceRow);
		}
		
		String flowInstanceId = fi.getFlowInstanceRow().getStr("id");
		String flowNodeInstanceId = nodeInstanceRow.getStr("id");
		String flowVersionId = nodeInstanceRow.getStr("flowversionid");
		String flowNodeId = nodeInstanceRow.getStr("flownodeid");
		Record nodeRow = fi.getFlow().getFlowNode(flowNodeId);
		String flowNodeName = nodeRow.getStr("flownodename");
		String nodetype = nodeRow.get("nodetype").toString();
		String embranch = nodeRow.get("embranch").toString();
		
		c.setAttr("flow",fi);
		c.setAttr("g_iscreateform", isCreateForm);
		c.setAttr("g_formdataversion", fi.getFlowInstanceRow().get("saveversion"));
		c.setAttr("g_isreadonly", "false");
		c.setAttr("g_flowid", flowId);
		c.setAttr("g_flowversionid", flowVersionId);
		c.setAttr("g_flownodeid", flowNodeId);
		c.setAttr("g_flownodename", flowNodeName);
		c.setAttr("g_flownodetype", nodetype);
		c.setAttr("g_flownodeembranch", embranch);
		c.setAttr("g_flowinstanceid", flowInstanceId);
		c.setAttr("g_flownodeinstanceid", flowNodeInstanceId);
	}
	
	private void doFlowSave(Controller c,LPFlowSave ann){
		String g_iscreateform = c.getPara("g_iscreateform");
		String g_formdataversion = c.getPara("g_formdataversion");
		String g_flowinstanceid = c.getPara("g_flowinstanceid");
		//String g_flownodeinstanceid = c.getPara("g_flownodeinstanceid");

		FlowInstance fi = FlowInstance.load(g_flowinstanceid);
		//Record nodeInstanceRow = fi.getFlowNodeInstanceRow(g_flownodeinstanceid);
		
		String dbSaveVersion = fi.getFlowInstanceRow().getStr("saveversion");
		if(ann.ischeckdataversion()){
			if(!dbSaveVersion.equalsIgnoreCase(g_formdataversion)){
				
			}
			dbSaveVersion = fi.nextSaveVersion();
		}
		
		if(g_iscreateform.equalsIgnoreCase("true")){
			fi.start();
		}
		
		c.setAttr("flow",fi);
		c.renderJson(Ret.ok("g_formdataversion",dbSaveVersion));
	}
}
