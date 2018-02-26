package com.blit.lp.bus.flow.i;

import com.blit.lp.bus.flow.FlowInstance;
import com.jfinal.plugin.activerecord.Record;

/**
 * 流程事件抽象类
 * @author dkomj
 *
 */
public abstract class LPFlowEvent {
	/**
	 * 流程启动前
	 * @param fi，流程对象
	 * @throws Exception,抛出异常时，流程操作回滚
	 */
	public abstract void onBeforeStart(FlowInstance fi) throws Exception;
	/**
	 * 流程启动后
	 * @param fi，流程对象
	 * @throws Exception,抛出异常时，流程操作回滚
	 */
	public abstract void onAfterStart(FlowInstance fi) throws Exception;
	
	/**
	 * 流程节点提交前
	 * @param fi，流程对象
	 * @param nodeInstanceRow, 流程节点实例Record数据
	 * @throws Exception,抛出异常时，流程操作回滚
	 */
	public abstract void onBeforeNodeSubmit(FlowInstance fi
			,Record nodeInstanceRow) throws Exception;
	/**
	 * 流程节点提交后
	 * @param fi，流程对象
	 * @param nodeInstanceRow, 流程节点实例Record数据
	 * @param toNodeInstanceRow, 提交后的节点实例Record数据
	 * @throws Exception,抛出异常时，流程操作回滚
	 */
	public abstract void onAfterNodeSubmit(FlowInstance fi
			,Record nodeInstanceRow,Record toNodeInstanceRow) throws Exception;
	
	/**
	 * 流程节点回退前
	 * @param fi，流程对象
	 * @param nodeInstanceRow, 流程节点实例Record数据
	 * @throws Exception,抛出异常时，流程操作回滚
	 */
	public abstract void onBeforeNodeUntread(FlowInstance fi
			,Record nodeInstanceRow) throws Exception;
	/**
	 * 流程节点提交后
	 * @param fi，流程对象
	 * @param nodeInstanceRow, 流程节点实例Record数据
	 * @param toNodeInstanceRow, 回退后的节点实例Record数据
	 * @throws Exception,抛出异常时，流程操作回滚
	 */
	public abstract void onAfterNodeUntread(FlowInstance fi
			,Record nodeInstanceRow,Record toNodeInstanceRow) throws Exception;
	
	/**
	 * 流程完成前
	 * @param fi，流程对象
	 * @throws Exception,抛出异常时，流程操作回滚
	 */
	public abstract void onBeforeComplete(FlowInstance fi) throws Exception;
	/**
	 * 流程完成后
	 * @param fi，流程对象
	 * @throws Exception,抛出异常时，流程操作回滚
	 */
	public abstract void onAfterComplete(FlowInstance fi) throws Exception;
	
}
