package com.blit.lp.jf.controller.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blit.lp.bus.autocode.BaseRednerContainer;
import com.blit.lp.bus.autocode.BaseRender;
import com.blit.lp.bus.autocode.IRender;
import com.blit.lp.bus.autocode.MyMetaBuilder;
import com.blit.lp.bus.autocode.RenderTypeEnum;
import com.blit.lp.jf.config.LPController;
import com.jfinal.core.Controller;
import com.jfinal.kit.JMap;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Config;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.generator.ColumnMeta;
import com.jfinal.plugin.activerecord.generator.TableMeta;
import com.jfinal.template.Engine;

@LPController(controllerkey="/sys/generator")
public class GeneratorController extends Controller {
	public void index(){
		renderTemplate("/sys/autocode/generator.html");
	}
	
	
	public void templetlist() throws IOException{
		File configFile = new File(PathKit.getWebRootPath() + "/view/sys/autocode/generator.config");
		InputStreamReader isr = new InputStreamReader(new FileInputStream(configFile), "utf-8");       
		BufferedReader br = new BufferedReader(isr);       
		StringBuffer sb = new StringBuffer();
		String lineTxt = null;
		while ((lineTxt = br.readLine()) != null) {         
			sb.append("\r\n").append(lineTxt);       
		}       
		br.close();
		
		JSONArray ar = JSONArray.parseArray(sb.toString());
		List<Ret> reList = new ArrayList<Ret>();
		for (int i=0; i < ar.size(); i++) {
			JSONObject o = ar.getJSONObject(i);
			reList.add(Ret.create("id", o.getString("name")).set("text", o.getString("name")));
		}
		renderJson(reList);
	}
	
	public void dslist(){
		List<Ret> reList = new ArrayList<Ret>();
		String dss = PropKit.use("global.properties").get("db.datasources");
		for (String ds : dss.split(",")) {
			reList.add(Ret.create("id", ds).set("text", ds));
		}
		renderJson(reList);
	}
	
	public void tablelist(){
		String ds = getPara("ds");
		Config config = StrKit.isBlank(ds) ? DbKit.getConfig() : DbKit.getConfig(ds);
		MyMetaBuilder builder = new MyMetaBuilder(config.getDataSource());
		builder.setDialect(config.getDialect());
		List<TableMeta> list = builder.getTableList();
		builder.setDialect(config.getDialect());
		List<Ret> reList = new ArrayList<Ret>();
		for (TableMeta tableMeta : list) {
			reList.add(Ret.create("id", tableMeta.name.toLowerCase()).set("text", tableMeta.name.toLowerCase()));
		}
		renderJson(reList);
	}
	
	public void build() {
		try{
			String projectName = getPara("projectName","");
			String modelName = getPara("modelName","");
			String templet = getPara("templet","");
			String ds = getPara("ds","");
			String tableName = getPara("tableName","");
			String controllerPath = getPara("controllerPath","");
			if(StrKit.isBlank(controllerPath)){
				controllerPath = "";
				if(StrKit.notBlank(modelName)){
					controllerPath += "/#(modelName)";
				}
				controllerPath += "/#(tableName)";
			}
			String pfx = getPara("pfx","");
			if(StrKit.isBlank(pfx)){
				pfx = "#(tableName)";
			}
			
			Engine engine = new Engine();
			String baseTemplatePath = PathKit.getWebRootPath() + "/view";
			engine.setBaseTemplatePath(baseTemplatePath);
			
			String dir =new File(PathKit.getWebRootPath()).getParent();
			
			IRender iRender = getLPRender(templet);
			List<TableMeta> tableList = getMeta(ds,tableName);
			for (TableMeta tableMeta : tableList) {
				JMap data = JMap.create();
				
				data.set("tableMeta", tableMeta);
				data.set("dir", dir);
				data.set("ds", ds);
				data.set("projectName", projectName.toLowerCase());
				data.set("modelName", modelName.toLowerCase());
				data.set("tableName", tableMeta.name.toLowerCase());
				data.set("pfx", engine.getTemplateByString(pfx).renderToString(data));
				data.set("controllerPath", engine.getTemplateByString(controllerPath).renderToString(data));
				
				iRender.render(engine, data);
			}
			
                        Ret.setToOldWorkMode();
			renderJson(Ret.ok());
		}
		catch (Exception e) {
			e.printStackTrace();
			renderJson(Ret.fail("msg",e.getMessage()));
		}
	}
	
	private IRender getLPRender(String name) throws IOException {
		File configFile = new File(PathKit.getWebRootPath() + "/view/sys/autocode/generator.config");
		InputStreamReader isr = new InputStreamReader(new FileInputStream(configFile), "utf-8");       
		BufferedReader br = new BufferedReader(isr);       
		StringBuffer sb = new StringBuffer();
		String lineTxt = null;
		while ((lineTxt = br.readLine()) != null) {         
			sb.append("\r\n").append(lineTxt);       
		}       
		br.close();
		
		JSONArray ar = JSONArray.parseArray(sb.toString());
		for (int i=0; i < ar.size(); i++) {
			JSONObject o = ar.getJSONObject(i);
			
			if(name.equalsIgnoreCase(o.getString("name"))){
				List<IRender> renderList = new ArrayList<IRender>();
				JSONArray renders = o.getJSONArray("renders");
				for(int j=0; j < renders.size(); j++){
					JSONObject o2 = renders.getJSONObject(j);
					String type = o2.getString("type");
					String view = o2.getString("view");
					String targetName = o2.getString("targetName");
					
					if(type.equalsIgnoreCase("html")){
						String path = "/#(projectName)#(modelName ? '/' + modelName : '')/" + targetName; 
						renderList.add(
								new BaseRender(view, RenderTypeEnum.HTML,path));
					}
					else if(type.equalsIgnoreCase("java")){
						String path = "/#(projectName)/controller#(modelName ? '/' + modelName : '')/#(pfx)Controller.java";
						renderList.add(
								new BaseRender(view, RenderTypeEnum.JAVA,path));
					}
					else if(type.equalsIgnoreCase("java")){
						renderList.add(
								new BaseRender(view, RenderTypeEnum.CONSOLE,""));
					}
				}
				
				return new BaseRednerContainer(name,renderList.toArray(new IRender[]{}));
			}
			
		}
		
		return null;
	}
	
	private List<TableMeta> getMeta(String ds,String tableNames){
		String dsName = StrKit.isBlank(ds) ? DbKit.getConfig().getName() : ds;
		String url = PropKit.get(dsName + ".url");
		String username = PropKit.get(dsName + ".username");
		String sqlRemarks = "";
		
		if(url.toLowerCase().contains(JdbcConstants.ORACLE)){
			sqlRemarks = "select column_name as col,comments as remarks from all_col_comments where lower(table_name)=? and lower(owner)='" +username.toLowerCase()+ "'";
		}
		else if(url.toLowerCase().contains(JdbcConstants.SQL_SERVER)){
			sqlRemarks = "SELECT  col.name AS col,cast(ISNULL(ep.[value], '') as varchar(500)) AS remarks "
							+" FROM    dbo.syscolumns col "  
							+" 	INNER JOIN dbo.sysobjects obj ON col.id = obj.id  AND obj.xtype = 'U' AND obj.status >= 0  "
							+" 	LEFT  JOIN sys.extended_properties ep ON col.id = ep.major_id  AND col.colid = ep.minor_id  AND ep.name = 'MS_Description' "  
							+" WHERE   lower(obj.name) = ?  ORDER BY col.colorder ";
		}
		else if(url.toLowerCase().contains(JdbcConstants.MYSQL)){
			sqlRemarks = "select column_name as col, column_comment as remarks from information_schema.COLUMNS t where lower(table_name)=?";
		}
		else if(url.toLowerCase().contains(JdbcConstants.POSTGRESQL)){
			sqlRemarks = "select a.attname as col,d.description as remarks from pg_class c join pg_description d on c.relfilenode=d.objoid join pg_attribute a on d.objsubid=a.attnum and d.objoid=a.attrelid  where lower(c.relname)=?";
		}
		else{
			throw new RuntimeException("不支持的数据库！");
		}
		
		Config config = DbKit.getConfig(dsName);
		MyMetaBuilder builder = new MyMetaBuilder(config.getDataSource());
		builder.setDialect(config.getDialect());
		List<TableMeta> list = builder.build(tableNames);
		for (TableMeta tableMeta : list) {
			tableMeta.name = tableMeta.name.toLowerCase();
			List<Record> rows = Db.use(dsName).find(sqlRemarks, tableMeta.name);
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
}
