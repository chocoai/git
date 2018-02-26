package com.jfinal.plugin.activerecord.dialect;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DbSqlBuiler {
	private static String rxEmpy = "(\\s)+";
    private static String rxOrder = "[oO][rR][dD][eE][rR] [bB][yY] [\u4e00-\u9fa5a-zA-Z0-9_.]+((\\s)+(([dD][eE][sS][cC])|([aA][sS][cC])))?(( )*,( )*[\u4e00-\u9fa5a-zA-Z0-9_.]+(( )+(([dD][eE][sS][cC])|([aA][sS][cC])))?)*";
    private static Pattern pOrder = Pattern.compile(rxOrder);
    
    /// <summary>
    /// 获取去掉最外层order by后的语句
    /// </summary>
    /// <param name="sql"></param>
    /// <returns></returns>
    public static String ReplaceFormatSqlOrderBy(String sql)
    {
        sql = sql.replaceAll(rxEmpy, " ");
        
        int index = sql.toLowerCase().lastIndexOf("order by");
        if (index > sql.toLowerCase().lastIndexOf(")"))
        {
            String sql1 = sql.substring(0, index);
            String sql2 = sql.substring(index);
            sql2 = sql2.replaceAll(rxOrder, "");
            return sql1 + sql2;
        }

        return sql;
    }

    /// <summary>
    /// 获取最外层的order by 语句
    /// </summary>
    /// <param name="sql"></param>
    /// <returns></returns>
    public static String GetOrderBy(String sql)
    {
        sql = sql.replaceAll(rxEmpy, " ");

        int index = sql.toLowerCase().lastIndexOf("order by");
        if (index > sql.toLowerCase().lastIndexOf(")"))
        {
            String sql2 = sql.substring(index);
            Matcher ma = pOrder.matcher(sql2);
            ma.find();
            return ma.group();
        }

        return "";
    }
}
