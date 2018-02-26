package com.blit.lp.bus.flow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.blit.lp.core.exception.FlowException;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

/**
 * 不可修改Record
 * 为了防止项目组，直接修改Flow模版数据
 * @author dkomj
 *
 */
public class NoModifyRecord extends Record {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoModifyRecord(Record record){
		super.setColumns(record.getColumns());
	}
	
	@Override
	public Record clear() {
		throw new FlowException("不能编辑的Record");
	}

	@Override
	public Record setColumns(Map<String, Object> columns) {
		throw new FlowException("不能编辑的Record");
	}

	@Override
	public Record setColumns(Record record) {
		throw new FlowException("不能编辑的Record");
	}

	@Override
	public Record setColumns(Model<?> model) {
		throw new FlowException("不能编辑的Record");
	}

	@Override
	public Record set(String column, Object value) {
		throw new FlowException("不能编辑的Record");
	}
	
	
	public static List<Record> convertList(List<Record> list){
		List<Record> newList = new ArrayList<Record>();
		if(list != null){
			for (Record record : list) {
				newList.add(new NoModifyRecord(record));
			}
		}
		return newList;
	}
}
