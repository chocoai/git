package com.blit.lp.jf.controller.base;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.blit.lp.core.context.User;
import com.blit.lp.core.exception.SysException;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.jf.ext.MyFileRender;
import com.blit.lp.tools.GuidKit;
import com.blit.lp.tools.LPLogKit;
import com.jfinal.core.Controller;
import com.jfinal.kit.JMap;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

@LPController(controllerkey="/sys/attach")
public class AttachController extends Controller {
	//读取附件列表
	public void list(){
		String tablename = getPara("tablename");
		String field = getPara("field");
		String objid = getPara("objid");
		String sql = "select id as fileid,filename from lp_sys_attach where tablename=? and field=? and objid=? order by ADDTIME";
		List<Record> list = Db.find(sql,tablename,field,objid);
		renderJson(list);
	}
	//上传附件
	public void upload(){
		UploadFile file =  getFile();
		String cellId = getPara("cellid");
		String tablename = getPara("tablename");
		String field = getPara("field");
		String objid = getPara("objid");
		String fileName = file.getOriginalFileName();
		String newFileName = file.getFileName();
		fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
        String fileExtName = fileName.substring(fileName.lastIndexOf("."));
		long fileszie = file.getFile().length();
		
        String saveWebPath = newFileName;
        
        Record row = new Record();
        String guid = GuidKit.createGuid();
        row.set("id", guid);
        row.set("filename", fileName);
        row.set("filesize", fileszie);
        row.set("fileext", fileExtName);
        row.set("savepath", saveWebPath);
        row.set("tablename", tablename);
        row.set("field", field);
        row.set("objid", objid);
        if(User.getCurrUser() != null){
	        row.set("userid", User.getCurrUser().get("id"));
	        row.set("username", User.getCurrUser().get("username"));
        }
        row.set("addtime", new Timestamp(new Date().getTime()));
        Db.save("lp_sys_attach", "id", row);
        
        String script = String.format("<script>lpsys.PlUpload.get('%s').addFile('%s','%s');</script>", cellId, guid, fileName);
        renderText(script);
	}
	//删除附件
	public void del(){
		String fileid = getPara("fileid");
		Db.deleteById("lp_sys_attach", "id", fileid);
		renderJson(Ret.ok());
	}
	//查看附件
	public void show(){
		String fileid = getPara("fileid");
		Record row = Db.findFirst("select * from lp_sys_attach where id=?",fileid);
		String uploaddir = PropKit.use("global.properties").get("uploaddir");
		if(!uploaddir.endsWith("\\"))
			uploaddir += "\\";
		String filePath = uploaddir + row.getStr("savepath");
		String fileName = row.getStr("fileName");
		MyFileRender myFileRender = new MyFileRender(filePath, fileName, getResponse());
		render(myFileRender);
	}
	
	//导入excel
	public void importExcel(){
		UploadFile file =  getFile();
		String callback = getPara("callback");
		
        Ret ret;
        try{
        	List<JMap> list = readExcel(file);
        	ret = Ret.ok("data", list);
        }
        catch (SysException e) {
        	ret = Ret.fail("msg", e.getMessage());
		}
        catch (Exception e) {
			LPLogKit.error("导入Excel出错", e);
			ret = Ret.fail("msg", "导入Excel出错");
		}
        
		String script = String.format("<script>%s(%s);</script>", callback, JSON.toJSONString(ret));
        renderText(script);
	}
	
	//读取上传的Excel文件
	public static List<JMap> readExcel(UploadFile file) throws Exception{
		String fileName = file.getOriginalFileName();
		fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
        String fileExtName = fileName.substring(fileName.lastIndexOf("."));
        
        List<JMap> list = null;
        if(".xlsx".equalsIgnoreCase(fileExtName)){
			list = readExcel2007(file.getFile());
		}
		else if(".xls".equalsIgnoreCase(fileExtName)){
			list = readExcel2003(file.getFile());
		}
		else{
			throw new SysException("不支持的文件类型");
		}
        
        //删除上传的Excel文件
        try{
	    if (file.getFile().exists())
	    	file.getFile().delete();
        }
        catch (Exception e) {
			e.printStackTrace();
		}
        
        return list;
	}
	
	@SuppressWarnings("unchecked")
	private static List<JMap> readExcel2003(File f) throws Exception {
		HSSFWorkbook hw = new HSSFWorkbook(new FileInputStream(f));
		HSSFSheet sheet0 = hw.getSheetAt(0);
		
		//读取表头
		List<String> headerList = new ArrayList<String>();
		HSSFRow headerRow = sheet0.getRow(0);
		for (int j = 0; j < headerRow.getLastCellNum(); j++) {
            HSSFCell cellz = headerRow.getCell(j);
            String val = getCellValue(cellz);
            headerList.add(val);
        }
		
		//读取数据
		List<JMap> list = new ArrayList<JMap>();
		int totalrows = sheet0.getPhysicalNumberOfRows();
		for (int i = 1; i < totalrows; i++) {
			HSSFRow dataRow = sheet0.getRow(i);
			JMap map = new JMap();
			for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                HSSFCell cellz = dataRow.getCell(j);
                String header = headerList.get(j);
                String val = getCellValue(cellz);
                headerList.add(val);
                map.put(header, val);
            }
			list.add(map);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private static List<JMap> readExcel2007(File f) throws Exception {
		XSSFWorkbook hw = new XSSFWorkbook(new FileInputStream(f));
		XSSFSheet sheet0 = hw.getSheetAt(0);
		
		//读取表头
		List<String> headerList = new ArrayList<String>();
		XSSFRow headerRow = sheet0.getRow(0);
		for (int j = 0; j < headerRow.getLastCellNum(); j++) {
			XSSFCell cellz = headerRow.getCell(j);
            String val = getCellValue(cellz);
            headerList.add(val);
        }
		
		//读取数据
		List<JMap> list = new ArrayList<JMap>();
		int totalrows = sheet0.getPhysicalNumberOfRows();
		for (int i = 1; i < totalrows; i++) {
			XSSFRow dataRow = sheet0.getRow(i);
			JMap map = new JMap();
			for (int j = 0; j < headerRow.getLastCellNum(); j++) {
				XSSFCell cellz = dataRow.getCell(j);
                String header = headerList.get(j);
                String val = getCellValue(cellz);
                headerList.add(val);
                map.put(header, val);
            }
			list.add(map);
		}
		return list;
	}
	
	private static String getCellValue(Cell cell) throws Exception  
    {  
	   String cellvalue = "";  
       if (cell!=null) {
           switch (cell.getCellTypeEnum()) {
               case BOOLEAN:  
                   cellvalue = String.valueOf(cell.getBooleanCellValue());  
                   break;  
               case NUMERIC:  
                   cellvalue = String.valueOf(cell.getNumericCellValue());  
                   break;  
               case STRING:  
                   cellvalue = cell.getStringCellValue();  
                   break;  
               case BLANK:  
                   break;  
               case ERROR:  
                   break;  
               case FORMULA:
                   break;  
           }  
        }  
       return cellvalue;  
    }
}
