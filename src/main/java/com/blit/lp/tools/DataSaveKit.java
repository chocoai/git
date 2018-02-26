package com.blit.lp.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

/**
 * 数据保存工具
 * 
 * @author dkomj
 *
 */
public class DataSaveKit {
	/**
	 * mini ui列表保存
	 * @param db
	 * @param tableName
	 * @param primaryKey
	 * @param data
	 * @param addDefaultRow
	 * @param excludeFields
	 */
	public static void miniuiListSave(DbPro db,String tableName, String primaryKey, String data,Record addDefaultRow, String... excludeFields){
		
		List<String> excludeList = new ArrayList<String>();
		String[] miniuiExcludeFields = new String[]{"_id","_uid","_state"};
		for (String f : miniuiExcludeFields) {
			excludeList.add(f);
		}
		for (int i=0; i< excludeFields.length; i++) {
			excludeList.add(excludeFields[i]);
		}
		
		JSONArray list = JSONArray.parseArray(data);
		for(int i=0; i < list.size(); i++){
			JSONObject obj = list.getJSONObject(i);
			String _state = obj.getString("_state");
			Record editRow = new Record();
			for (Entry<String, Object> entry : obj.entrySet()) {
				boolean isExclude = false;
				for (String field : excludeList) {
					if(field.equalsIgnoreCase(entry.getKey())){
						isExclude = true;
						break;
					}
				}
				
				if(!isExclude)
					editRow.set(entry.getKey(), entry.getValue());
			}
			
			if("added".equals(_state)){
				editRow.set(primaryKey, GuidKit.createGuid());
				if(addDefaultRow != null){
					for (String col : addDefaultRow.getColumnNames()) {
						editRow.set(col, addDefaultRow.get(col));
					}
				}
				
				Db.save(tableName, primaryKey, editRow);
			}
			else if("modified".equals(_state)){
				Db.update(tableName, primaryKey, editRow);
			}
			else if("removed".equals(_state)){
				Object val =  editRow.get(primaryKey);
				Db.deleteById(tableName, primaryKey, val);
			}
		}

	}
}
