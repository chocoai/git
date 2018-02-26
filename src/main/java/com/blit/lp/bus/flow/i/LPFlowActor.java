package com.blit.lp.bus.flow.i;

import java.util.List;

import com.blit.lp.bus.flow.FlowInstance;
import com.blit.lp.bus.flow.FlowUser;
import com.jfinal.plugin.activerecord.Record;

/**
 * 流程自定义参与者抽象类
 * @author dkomj
 *
 */
public abstract class LPFlowActor {
	/**
	 * 
	 * @param flowInstance:流程实例
	 * @param flowNodeRow:流程节点
	 * @return
	 */
	public abstract List<FlowUser> loadActors(FlowInstance flowInstance,Record flowNodeRow); 
}
