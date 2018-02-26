package com.blit.lp.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 时间操作工具类
 * @author dkomj
 *
 */
public class DateKit {
	public static String format(){
		Date date = new Date();
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return format(date, pattern);
	}
	
	public static String format(Date date)
	{
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return format(date, pattern);
	}
	
	public static String format(Date date, String pattern)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}
	
	public static Date parse(String dateStr){
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parse(dateStr,pattern);
	}
	
	public static Date parse(String dateStr,String pattern){
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		Date date = null;
		try
		{
			date = dateFormat.parse(dateStr);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return date;
	}
}
