package com.blit.lp.bus.flow;

import java.io.Serializable;

import com.blit.lp.core.context.User;
import com.jfinal.plugin.activerecord.Record;

/**
 * 流程操作用户
 * @author dkomj
 *
 */
public class FlowUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Record m_Row = null;
	public FlowUser(){
		if(User.getCurrUser() != null){
			m_Row = User.getCurrUser().getRecord();
		}
		else{
			m_Row = new Record();
		}
	}
	
	public FlowUser(Record row){
		m_Row = row;
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
}
