package com.blit.lp.jf.ext;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.render.Render;

//mini ui列表导出
public class MiniuiExcelRender extends Render {
	private String _columns;
	private List<Record> _list;
	private String _showName;
	private HttpServletResponse _response;
	public MiniuiExcelRender(String columns,List<Record> list, String showName, HttpServletResponse response){
		_columns = columns;
		_list = list;
		_showName = showName;
		_response = response;
	}
	
	@Override
	public void render() {
		renderExportTable();
	}

//	private void renderPoi(){
//		try {
//			_response.addHeader("Content-disposition", 
//                "attachment; filename=" + new String(_showName.getBytes("GBK"), "ISO8859-1"));
//			_response.setContentType("application/ms-excel");
//			JSONArray cols =  JSONArray.parseArray(_columns);
//			Workbook wb = new HSSFWorkbook();
//			Sheet sheet = wb.createSheet("export");
//			int rowIndex = 1;
//			Row rowHeader = sheet.createRow(rowIndex++);
//			for(int i=0;i<cols.size();i++){      
//				JSONObject col = cols.getJSONObject(i);
//				createHeaderCell(wb,rowHeader,(short)i,col.getString("header"));
//	        }
//			
//			for (Record data : _list) {
//				Row rowData = sheet.createRow(rowIndex++);
//				for(int j=0;j<cols.size();j++){     
//					JSONObject col = cols.getJSONObject(j);
//	            	String key = col.getString("header");
//	    			String value="";
//	    			if(StrKit.notBlank(key) && data.get(key) != null){
//	    				value = data.get(key).toString();
//	    			}
//	    			
//	    			createDataCell(wb,rowData,(short)j,value);
//	    		}
//			}
//			
//			OutputStream out=response.getOutputStream();
//			wb.write(out);
//			out.flush();
//			out.close();
//			
//		} catch (Exception e1) {
//            e1.printStackTrace();
//        }
//	}
//	/**
//	 * 
//	 * @param wb
//	 * @param row
//	 * @param column
//	 */
//    private static Cell createHeaderCell(Workbook wb, Row row, short column,String txt) {
//        Cell cell = row.createCell(column);
//        cell.setCellValue(txt);
//        CellStyle cellStyle = wb.createCellStyle();
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);
//        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        cellStyle.setBorderBottom(BorderStyle.THIN);
//        cellStyle.setBorderLeft(BorderStyle.THIN);
//        cellStyle.setBorderRight(BorderStyle.THIN);
//        cellStyle.setBorderTop(BorderStyle.THIN);
//        
//        Font font = wb.createFont();
//        font.setFontHeightInPoints((short)24);
//        font.setItalic(true);
//        font.setStrikeout(true);
//        cellStyle.setFont(font);
//        
//        cell.setCellStyle(cellStyle);
//        return cell;
//    }
//    
//    private static Cell createDataCell(Workbook wb, Row row, short column,String txt) {
//    	Cell cell = row.createCell(column);
//        cell.setCellValue(txt);
//        CellStyle cellStyle = wb.createCellStyle();
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);
//        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        cellStyle.setBorderBottom(BorderStyle.THIN);
//        cellStyle.setBorderLeft(BorderStyle.THIN);
//        cellStyle.setBorderRight(BorderStyle.THIN);
//        cellStyle.setBorderTop(BorderStyle.THIN);
//        
//        cell.setCellStyle(cellStyle);
//        return cell;
//    }
    
	
    private void renderExportTable()
    {
    	try{
	    	_response.addHeader("Content-disposition", 
	                "attachment; filename=" + new String(_showName.getBytes("GBK"), "ISO8859-1"));
			_response.setContentType("application/ms-excel");
			
			JSONArray cols =  JSONArray.parseArray(_columns);
			JSONArray columnsBottom = getColumnsBottom(cols);
	
	        JSONArray columnsTable = getColumnsTable(cols);
	
	        StringBuilder sb = new StringBuilder();
	        //data = ds.DataSetName + "\n";
	
	
	        //data += tb.TableName + "\n";
	        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
	        sb.append("<table cellspacing=\"0\" cellpadding=\"5\" rules=\"all\" border=\"1\">");
	        //写出列名
	
	        for (int i = 0; i < columnsTable.size(); i++){
	        	JSONArray columnsRow = columnsTable.getJSONArray(i);
	            sb.append("<tr style=\"font-weight: bold; white-space: nowrap; text-align: center; \">");
	            for (Object object : columnsRow) {
	            	JSONObject column = (JSONObject)object;
	            	String header = column.containsKey("header") ? column.getString("header").trim() : "";
	                sb.append("<td style=\"max-width:350px; font-size: 13pt; background-color:#DDDDDD\" colspan=" + column.getString("colspan") + " rowspan=" + column.getString("rowspan") + ">" + header + "</td>");
	            }
	            sb.append("</tr>");
	        }
	        //写出数据
	        int count = 0;
	        for (Record record : _list) {
	        	sb.append("<tr style=\"text-align: center;\">");
	        	
	        	for (Object obj : columnsBottom){
	            	JSONObject column = (JSONObject)obj;
	                Object value;
	                if (column.containsKey("field"))
	                {
	                    value = record.get(column.getString("field"));
	                    if(value == null)
	                    	value = "";
	                    
	                    String str = value.toString();
	                    if(Pattern.matches("^\\d{4}\\-\\d{2}\\-\\d{2} \\d{2}:\\d{2}:\\d{2}\\S*", str))
	                    	value = str.substring(0, 18);
	                }
	                else
	                {
	                    value = "";
	                }
	                if ("indexcolumn".equals(column.get("type"))) value = count++;
	                sb.append("<td>" + value + "</td>");
	            }
	            sb.append("</tr>");
			}
	        sb.append("</table>");
	        
	        
	        OutputStream out=response.getOutputStream();
	        out.write(sb.toString().getBytes());
			out.flush();
			out.close();
    	} catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static JSONArray getColumnsBottom(JSONArray cols)
    {
    	JSONArray columnsBottom = new JSONArray();

        for (int i = 0; i < cols.size(); i++)
        {
        	JSONObject column = cols.getJSONObject(i);

            if (column.containsKey("columns"))
            {
            	JSONArray childColumns = column.getJSONArray("columns");
            	columnsBottom.addAll(getColumnsBottom(childColumns));
            }
            else
            {
                columnsBottom.add(column);
            }

        }
        return columnsBottom;
    }

    public static JSONArray getColumnsTable(JSONArray columns)
    {
    	JSONArray table = new JSONArray();
        
        getColumnsRows(columns, 0, table);

        createTableSpan(table);

        return table;

    }

    public static void getColumnsRows(JSONArray columns, int level, JSONArray table)
    {
    	JSONArray row = null;
        if (table.size() > level)
        {
            row = table.getJSONArray(level);
        }
        else
        {
            row = new JSONArray();
            table.add(row);
        }

        for (int i = 0; i < columns.size(); i++)
        {

        	JSONObject column = columns.getJSONObject(i);
            JSONArray childColumns = column.containsKey("columns") ? column.getJSONArray("columns") : null;// (ArrayList)column["columns"];
            
            row.add(column);

            if (childColumns != null)
            {
                getColumnsRows(childColumns, level + 1, table);
            }

        }
    }

    public static void createTableSpan(JSONArray table)
    {
        for (int i = 0; i < table.size(); i++)
        {
        	JSONArray row = table.getJSONArray(i);  //row
            for (int l = 0; l < row.size(); l++)
            {
            	JSONObject cell = row.getJSONObject(l);   //column

                int colSpan = getColSpan(cell);

                cell.put("colspan", colSpan);
                if (colSpan > 1)
                {
                	cell.put("rowspan", 1);
                }
                else
                {
                	cell.put("rowspan", table.size() - i);
                }

            }
        }
    }

    public static int getColSpan(JSONObject column)
    {
        int colSpan = 0;
        JSONArray childColumns = column.containsKey("columns") ? column.getJSONArray("columns") : null;// (ArrayList)column["columns"];
        if (childColumns != null)
        {
            for (int i = 0; i < childColumns.size(); i++)
            {
            	JSONObject child = childColumns.getJSONObject(i);;
                colSpan += getColSpan(child);
            }
        }
        else
        {
            colSpan = 1;
        }
        return colSpan;
    }
}
