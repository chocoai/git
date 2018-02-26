package com.blit.lp.bus.autocode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.activerecord.generator.TableMeta;


public class MyMetaBuilder extends MetaBuilder {

	public MyMetaBuilder(DataSource dataSource) {
		super(dataSource);
	}
	
	public List<TableMeta> getTableList(){
		try {
			conn = dataSource.getConnection();
			dbMeta = conn.getMetaData();
			
			List<TableMeta> ret = new ArrayList<TableMeta>();

			buildTableNames(ret);
			return ret;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (conn != null) {
				try {conn.close();} catch (SQLException e) {throw new RuntimeException(e);}
			}
		}
	}
	
	public List<TableMeta> build(String tableNames) {
		try {
			conn = dataSource.getConnection();
			dbMeta = conn.getMetaData();
			
			List<TableMeta> ret = new ArrayList<TableMeta>();
			List<TableMeta> ret2 = new ArrayList<TableMeta>();
			buildTableNames(ret);
			
			for (TableMeta tableMeta : ret) {
				for (String tableName : tableNames.split(",")) {
					if(tableName.equalsIgnoreCase(tableMeta.name)){
						buildPrimaryKey(tableMeta);
						tableMeta.primaryKey = tableMeta.primaryKey.split(",")[0].toLowerCase();
						buildColumnMetas(tableMeta);
						ret2.add(tableMeta);
					}
				}
			}
			return ret2;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (conn != null) {
				try {conn.close();} catch (SQLException e) {throw new RuntimeException(e);}
			}
		}
	}

	
}
