package com.blit.lp.bus.record;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Map;

import com.blit.lp.core.cache.LPCacheManager;
import com.blit.lp.core.exception.SysException;
import com.blit.lp.tools.LPLogKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.Config;
import com.jfinal.plugin.activerecord.DbKit;

public class TableMetaLoader {
	
	public static final String TABLE_META_CACHENAME = "lp_table_meta";
	private static TypeMapping typeMapping = new TypeMapping();
	
	/**
	 * 读取数据库表结构信息
	 * @param configName
	 * @param tableName
	 * @return
	 */
	public static synchronized Map<String, Object> LoadTableColumnMeta(String configName,String tableName) {
		Map<String, Object> cols = LPCacheManager.getLocalCacheHelper().get(TABLE_META_CACHENAME, tableName.toLowerCase());
		if(cols == null){
			cols = new CaseInsensitiveContainerFactory(true).getAttrsMap();
			Config config = StrKit.isBlank(configName) ? 
					DbKit.getConfig() : DbKit.getConfig(configName);
			Connection conn = null;
			try {
				conn = config.getDataSource().getConnection();
				String sql = config.getDialect().forTableBuilderDoBuild(tableName);
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(sql);
				ResultSetMetaData rsmd = rs.getMetaData();
				
				fillTableColumnMeta(cols,rsmd,config,tableName);
				LPCacheManager.getLocalCacheHelper().put(TABLE_META_CACHENAME, tableName.toLowerCase(),cols);
			}
			catch (Exception e) {
				throw new SysException("读取表(" +tableName+ ")元数据时出错",e);
			}
			finally {
				config.close(conn);
			}
		}
		return cols;
	}
	
	private static synchronized void fillTableColumnMeta(Map<String, Object> cols,ResultSetMetaData rsmd,Config config,String tableName) throws SQLException{
		int columnCount = rsmd.getColumnCount();

		for (int i=1; i<=columnCount; i++) {
			String colName = rsmd.getColumnLabel(i);
			String colClassName = rsmd.getColumnClassName(i);
			Class<?> typeStr = typeMapping.getType(colClassName);
			if (typeStr != null) {
				cols.put(colName, typeStr);
			}
			else {
				int type = rsmd.getColumnType(i);
				if (type == Types.BINARY || type == Types.VARBINARY || type == Types.BLOB) {
					cols.put(colName, byte[].class);
				}
				else if (type == Types.CLOB || type == Types.NCLOB) {
					cols.put(colName, java.lang.String.class);
				}
				else if (colClassName.equals("oracle.sql.TIMESTAMP") || 
						colClassName.equals("oracle.sql.TIMESTAMPLTZ") || 
						colClassName.equals("oracle.sql.TIMESTAMPTZ") ||
						colClassName.equals("oracle.sql.TIMEZONETAB")
						){
					cols.put(colName, java.sql.Timestamp.class);
				}
				else {
					LPLogKit.warn("不能识别的数据库字段类型：" + tableName + "," + colName + "," +colClassName);
					cols.put(colName, java.lang.String.class);
				}
			}
		}

	}
}
