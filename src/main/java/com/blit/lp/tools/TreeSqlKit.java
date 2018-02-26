package com.blit.lp.tools;

import com.jfinal.kit.StrKit;

public class TreeSqlKit {
	/**
	 * 
	 * @param select
	 * @param tableName
	 * @param initWhere
	 * @param idField
	 * @param pidField
	 * @param maxLevel
	 * @return
	 */
	public static String GetParentSql(String select,String tableName,String initWhere,String idField,String pidField,int maxLevel){
		StringBuffer sbBuffer = new StringBuffer(select);
		sbBuffer.append(" from ").append(tableName).append(" where 1=1 ");
		if(StrKit.notBlank(initWhere))
			sbBuffer.append(" and " + initWhere);
		
		String tmp = String.format("from %s where %s", tableName,initWhere);
		for(int i=0; i < maxLevel; i++){
			tmp = String.format(" from %s where %s in ( select %s %s)", tableName,idField,pidField,tmp);
			sbBuffer.append(" union ").append(select).append(tmp);
		}
		
		return sbBuffer.toString();
	}
	
	/**
	 * 
	 * @param select
	 * @param tableName
	 * @param initWhere
	 * @param idField
	 * @param pidField
	 * @param maxLevel
	 * @return
	 */
	public static String GetChildSql(String select,String tableName,String initWhere,String idField,String pidField,int maxLevel){
		StringBuffer sbBuffer = new StringBuffer(select);
		sbBuffer.append(" from ").append(tableName).append(" where 1=1 ");
		if(StrKit.notBlank(initWhere))
			sbBuffer.append(" and " + initWhere);
		
		String tmp = String.format("from %s where %s", tableName,initWhere);
		for(int i=0; i < maxLevel; i++){
			tmp = String.format(" from %s where %s in ( select %s %s)", tableName,pidField,idField,tmp);
			sbBuffer.append(" union ").append(select).append(tmp);
		}
		
		return sbBuffer.toString();
	}
}
