package com.blit.lp.bus.autocode;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.alibaba.druid.util.JdbcConstants;
import com.jfinal.kit.JMap;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.activerecord.dialect.Dialect;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.activerecord.generator.ColumnMeta;
import com.jfinal.plugin.activerecord.generator.TableMeta;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;

public class LPGenerator {
	private static String tmplDir = "\\WebRoot\\view";
	private static Scanner scan = new Scanner(System.in);

	/**
	 * 自动生成代码 
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String dir = "";//保存路径
		String projectname = "";//项目名称(必填)
		String modelname = "";//模块名称(可空)
		String tableNames = "";//生成的表名(必填)，多张表用逗号隔开。
		int renderIndex = -1;//选择的生成代码类型(必填)
		
		List<IRender> list = new ArrayList<IRender>();
		list.add(new BaseRednerContainer("生成miniui列表编辑代码"
				,new BaseRender("/sys/autocode/miniui_list_edit_html.txt",RenderTypeEnum.HTML,"/#(projectName)#(modelName ? '/' + modelName : '')/#(tableName)_list.html")
				,new BaseRender("/sys/autocode/miniui_list_edit_java.txt",RenderTypeEnum.JAVA,"/#(projectName)/controller#(modelName ? '/' + modelName : '')/#(tableName)Controller.java")
				));
		
		list.add(new BaseRednerContainer("生成miniui详细编辑界面代码"
				,new BaseRender("/sys/autocode/miniui_edit_list_html.txt",RenderTypeEnum.HTML,"/#(projectName)#(modelName ? '/' + modelName : '')/#(tableName)_list.html")
				,new BaseRender("/sys/autocode/miniui_edit_html.txt",RenderTypeEnum.HTML,"/#(projectName)#(modelName ? '/' + modelName : '')/#(tableName)_edit.html")
				,new BaseRender("/sys/autocode/miniui_edit_java.txt",RenderTypeEnum.JAVA,"/#(projectName)/controller#(modelName ? '/' + modelName : '')/#(tableName)Controller.java")
				));
		
		list.add(new BaseRednerContainer("生成elementui详细编辑界面代码(暂无实现)"
				//,new BaseRender("/sys/autocode/elementui_list_html.txt",RenderTypeEnum.HTML,"#(tablename)_list.html")
				//,new BaseRender("/sys/autocode/elementui_edit.txt",RenderTypeEnum.HTML,"#(tablename)_edit.html")
				//,new BaseRender("/sys/autocode/elementui_edit_java.txt",RenderTypeEnum.JAVA,"#(tablename)Controller.java")
				));
		
		list.add(new BaseRednerContainer("生成控制台表Record填充语句"
				,new BaseRender("/sys/autocode/console_record.txt",RenderTypeEnum.CONSOLE,"")
				));

		if(StrKit.isBlank(dir)){
			dir = System.getProperty("user.dir");
		}
		while(StrKit.isBlank(projectname)){
			projectname = readformconsole("请输入项目名称(必填)：");
			
			modelname = readformconsole("请输入模块名称(可空)：");
		}
		
		
		while (renderIndex < 0 || renderIndex > list.size() ) {
			String s = getRenderDescText(list);
			String tmp = readformconsole(s);
			try{
				renderIndex = Integer.parseInt(tmp);
			}catch (Exception e) {}
		}

		while (StrKit.isBlank(tableNames)){
			String s = "生成的表名(必填)，多表用逗号隔开：";
			tableNames = readformconsole(s);
			
		}
		
		projectname = projectname.toLowerCase();
		modelname = modelname.toLowerCase();
		tableNames = tableNames.toLowerCase();
		String s = String.format("\r/***************\r*  代码输出路径：%s\r*  项目名称：%s\r*  模块名称：%s\r*  生成代码类型：%s\r*  生成的表名：%s\r***************/\r\r确定要生成代码吗？(Y/N)"
				, dir,projectname,modelname,list.get(renderIndex - 1).getName(),tableNames);
		System.out.print(s);
		String r = scan.nextLine();
		if("y".equalsIgnoreCase(r)){
			long t = System.currentTimeMillis();
			System.out.println("\r\r开始生成代码.....");
			create(dir,projectname,modelname,list.get(renderIndex - 1),tableNames);
			long dis = System.currentTimeMillis() - t;
			System.out.println("完成，耗时：" +dis+ "ms");
		}
		else{
			System.out.println("退出代码生成!");
		}
	}
	
	private static String readformconsole(String info) {
		String s = "\r/***************\r*\r*" +info+ "\r*\r***************/";
		System.out.println(s);
		return scan.nextLine();
	}
	
	private static String getRenderDescText(List<IRender> list){
		StringBuffer sbBuffer = new StringBuffer("生成代码类型(必填)\r");
		int index = 0;
		for (IRender iRender : list) {
			index++;
			sbBuffer.append("\r*      " +index+ "、" +iRender.getName()+ "；");
		}
		
		return sbBuffer.toString();
	}
	
	private static void create(String dir,String projectname,String modelname,IRender iRender,String tableNames) throws Exception {
		Engine engine = new Engine();
		String baseTemplatePath = dir + tmplDir;
		engine.setBaseTemplatePath(baseTemplatePath);
		List<TableMeta> list = getMeta(tableNames);
		
		for (String tableName : tableNames.split(",")) {
			JMap data = JMap.create();
			TableMeta tableMeta = null;
			for (TableMeta m : list) {
				if(m.name.equalsIgnoreCase(tableName)){
					tableMeta = m;
					break;
				}
			}
			data.set("tableMeta", tableMeta);
			data.set("dir", dir);
			data.set("projectName", projectname);
			data.set("modelName", modelname);
			data.set("tableName", tableName);
			
			String controllerPath = "";
			if(StrKit.notBlank(modelname)){
				controllerPath += "/" + modelname;
			}
			data.set("controllerPath", controllerPath + "/" + tableName);
			
			iRender.render(engine, data);
		}
	}
	

	private static List<TableMeta> getMeta(String tableNames) {
		String sqlRemarks = "";
		//只读global.properties 中第一个数据源
		String ds = PropKit.use("global.properties").get("db.datasources");
		for (String dbname : ds.split(",")) {
			String dbKey = dbname + ".";
			String url = PropKit.get(dbKey + "url");
			String username = PropKit.get(dbKey + "username");
			String password = PropKit.get(dbKey + "password");
			String driver_class = PropKit.get(dbKey + "driver_class");
			int initialSize = PropKit.getInt(dbKey + "initialSize");
			int minIdle = PropKit.getInt(dbKey + "minIdle");
			int maxActive = PropKit.getInt(dbKey + "maxActive");
			boolean showSql = "true".equalsIgnoreCase(PropKit.get(dbKey + "showSql"));
			boolean toLowerCase = PropKit.getBoolean(dbKey + "record_to_json_lowercase", true); 
			
			DruidPlugin dp=new DruidPlugin(url,username,password);
			dp.set(initialSize, minIdle, maxActive);
			dp.setDriverClass(driver_class);
			
			String dbType = null;
			Dialect dialect = null;
			if(url.toLowerCase().contains(JdbcConstants.ORACLE)){
				dbType = JdbcConstants.ORACLE;
				dialect = new OracleDialect();
				dp.setValidationQuery("select 1 from dual");
				sqlRemarks = "select column_name as col,comments as remarks from all_col_comments where lower(table_name)=? and lower(owner)='" +username.toLowerCase()+ "'";
			}
			else if(url.toLowerCase().contains(JdbcConstants.SQL_SERVER)){
				dbType = JdbcConstants.SQL_SERVER;
				dialect = new AnsiSqlDialect();
				sqlRemarks = "SELECT  col.name AS col,cast(ISNULL(ep.[value], '') as varchar(500)) AS remarks "
								+" FROM    dbo.syscolumns col "  
								+" 	INNER JOIN dbo.sysobjects obj ON col.id = obj.id  AND obj.xtype = 'U' AND obj.status >= 0  "
								+" 	LEFT  JOIN sys.extended_properties ep ON col.id = ep.major_id  AND col.colid = ep.minor_id  AND ep.name = 'MS_Description' "  
								+" WHERE   lower(obj.name) = ?  ORDER BY col.colorder ";
			}
			else if(url.toLowerCase().contains(JdbcConstants.MYSQL)){
				dbType = JdbcConstants.MYSQL;
				dialect = new MysqlDialect();
				sqlRemarks = "select column_name as col, column_comment as remarks from information_schema.COLUMNS t where lower(table_name)=?";
			}
			else if(url.toLowerCase().contains(JdbcConstants.POSTGRESQL)){
				dbType = JdbcConstants.POSTGRESQL;
				dialect = new PostgreSqlDialect();
				sqlRemarks = "select a.attname as col,d.description as remarks from pg_class c join pg_description d on c.relfilenode=d.objoid join pg_attribute a on d.objsubid=a.attnum and d.objoid=a.attrelid  where lower(c.relname)=?";
			}
			else{
				throw new RuntimeException("不支持的数据库！");
			}
			
			dp.start();
			
			ActiveRecordPlugin arp = new ActiveRecordPlugin(dbname,dp);
			arp.setShowSql(showSql);
			arp.setDialect(dialect);
			arp.setContainerFactory(new CaseInsensitiveContainerFactory(toLowerCase));
			arp.start();
			
			MyMetaBuilder builder = new MyMetaBuilder(dp.getDataSource());
			builder.setDialect(dialect);
			List<TableMeta> list = builder.build(tableNames);
			for (TableMeta tableMeta : list) {
				tableMeta.name = tableMeta.name.toLowerCase();
				List<Record> rows = Db.find(sqlRemarks, tableMeta.name);
				for (ColumnMeta col : tableMeta.columnMetas) {
					col.name = col.name.toLowerCase();
					for (Record record : rows) {
						if(col.name.equalsIgnoreCase(record.getStr("col"))){
							col.remarks = record.get("remarks") == null ? "" : record.getStr("remarks");
							break;
						}
					}
				}
			}
			
			return list;
		}

		return null;
	}

}
