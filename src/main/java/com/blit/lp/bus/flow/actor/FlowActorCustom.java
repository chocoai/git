package com.blit.lp.bus.flow.actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blit.lp.bus.flow.FlowUser;
import com.blit.lp.bus.flow.i.LPFlowActor;
import com.blit.lp.core.exception.FlowException;
import com.blit.lp.core.exception.SysException;
import com.blit.lp.tools.Reflect;
import com.jfinal.plugin.activerecord.Record;

/**
 * 流程自定义参与者
 * @author dkomj
 *
 */
public class FlowActorCustom extends FlowActor {

	@Override
	public List<FlowUser> getActors() {
		String cls = this.jsonObj.getString("data");
		Class<LPFlowActor> classObj = Reflect.on(cls).get();
		if(classObj == null)
			throw new FlowException("自定义流程参数者类需要继承com.blit.lp.bus.flow.i.LPFlowActor抽象类！");

		try {
			return classObj.newInstance().loadActors(this.flowInstance, this.flowNodeRow);
		} catch (InstantiationException e) {
			throw new SysException("创建自定义流程参与者出错！",e);
		} catch (IllegalAccessException e) {
			throw new SysException("创建自定义流程参与者出错！",e);
		}
	}

}
