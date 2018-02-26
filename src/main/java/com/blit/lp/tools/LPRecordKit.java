package com.blit.lp.tools;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSONObject;
import com.blit.lp.bus.record.TableMetaLoader;
import com.blit.lp.bus.record.TypeConverter;

import com.blit.lp.core.context.Global;
import com.blit.lp.core.exception.SysException;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

/**
 * 从Request 请求数据自动填充生成jFinal Record对象
 * 从Json 对象数据中自动填充生成jFinal Record对象
 * @author dkomj
 *
 */
public class LPRecordKit {
	/**
	 * 从JSONObject 对象数据生成Record对象(重载方法)
	 * configName默认为null，paramNamePfx默认为空字符串
	 * 
	 * @param data, fastjson的JSONObject对象
	 * @param tableName, 数据库表名
	 * @return Record
	 */
	public static Record createFromJson(JSONObject data,String tableName){
		return createFromJson(data,tableName,"");
	}
	/**
	 * 从JSONObject 对象数据生成Record对象(重载方法)
	 * configName默认为null,
	 * 
	 * @param data, fastjson的JSONObject对象
	 * @param tableName, 表名
	 * @param paramNamePfx, JSONObject属性前缀
	 * @return
	 */
	public static Record createFromJson(JSONObject data,String tableName,String paramNamePfx){
		return createFromJson(data,null,tableName,paramNamePfx);
	}
	
	/**
	 * 从JSONObject 对象数据生成Record对象
	 * 
	 * @param data, fastjson的JSONObject对象
	 * @param configName, 数据源名称
	 * @param tableName, 表名
	 * @param paramNamePfx, JSONObject属性前缀
	 * @return
	 */
	public static Record createFromJson(JSONObject data,String configName,String tableName,String paramNamePfx){
		Record record = new Record();
		try {
			for (String paraName : data.keySet()) {
				String attrName;

				if (StrKit.notBlank(paramNamePfx)) {
					if (paraName.startsWith(paramNamePfx)) {
						attrName = paraName.substring(paramNamePfx.length());
					} else {
						continue ;
					}
				} else {
					attrName = paraName;
				}
				
				String paraValue = data.get(paraName) != null ? data.get(paraName).toString() : null;
				
				if(paraValue != null){
					if(Pattern.matches("^\\d{4}\\-\\d{2}\\-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\S*", paraValue))
						paraValue = paraValue.replace("T", " ").substring(0, 18);
				}
				
				if(StrKit.notBlank(tableName)){
					Map<String, Object> cols = TableMetaLoader.LoadTableColumnMeta(configName,tableName);
					if(cols.containsKey(attrName)){
						Class<?> colType = (Class<?>) cols.get(attrName);
						Object value = paraValue != null ? TypeConverter.convert(colType, paraValue) : null;
						record.set(attrName, value);
					}
				}
				else{
					record.set(attrName, paraValue);
				}
			}
			
			return record;
			
		} catch (Exception e) {
			throw new SysException("注入Record对象时出错",e);
		}
	}
	
	/**
	 * 从Request请求数据生成Record对象(重载方法)
	 * configName默认为null,paramNamePfx默认为"data."
	 * 
	 * @param tableName, 表名
	 * @return Record
	 */
	public static Record createFromRequest(String tableName){
		return createFromRequest(tableName,"data.");
	}
	
	/**
	 * 从Request请求数据生成Record对象(重载方法)
	 * configName默认为null,
	 * 
	 * @param tableName, 表名
	 * @param paramNamePfx, JSONObject属性前缀
	 * @return Record
	 */
	public static Record createFromRequest(String tableName,String paramNamePfx){
		return createFromRequest(null,tableName,paramNamePfx);
	}
	
	/**
	 * 从Request请求数据生成Record对象
	 * 
	 * @param configName, 数据源名称
	 * @param tableName, 表名
	 * @param paramNamePfx, JSONObject属性前缀
	 * @return Record
	 */
	public static Record createFromRequest(String configName,String tableName,String paramNamePfx){
		Record record = new Record();
		try {
			HttpServletRequest  req = Global.getContext().getRequest();
			Map<String, String[]> parasMap = req.getParameterMap();
			
			for (Entry<String, String[]> entry : parasMap.entrySet()) {
				String paraName = entry.getKey();
				String attrName;
	
				if (StrKit.notBlank(paramNamePfx)) {
					if (paraName.startsWith(paramNamePfx)) {
						attrName = paraName.substring(paramNamePfx.length());
					} else {
						continue ;
					}
				} else {
					attrName = paraName;
				}
				
				String[] paraValueArray = entry.getValue();
				String paraValue = (paraValueArray != null && paraValueArray.length > 0) ? paraValueArray[0] : null;
				
				if(paraValue != null){
					if(Pattern.matches("^\\d{4}\\-\\d{2}\\-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\S*", paraValue))
						paraValue = paraValue.replace("T", " ").substring(0, 18);
				}
				
				if(StrKit.notBlank(tableName)){
					Map<String, Object> cols = TableMetaLoader.LoadTableColumnMeta(configName,tableName);
					if(cols.containsKey(attrName)){
						Class<?> colType = (Class<?>) cols.get(attrName);
						Object value = paraValue != null ? TypeConverter.convert(colType, paraValue) : null;
						record.set(attrName, value);
					}
				}
				else{
					record.set(attrName, paraValue);
				}
			}
			
			return record;
			
		} catch (Exception e) {
			throw new SysException("注入Record对象时出错",e);
		}
	}
	
	
}
