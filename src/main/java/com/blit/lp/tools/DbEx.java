package com.blit.lp.tools;

import com.jfinal.plugin.activerecord.Db;


/**
 * 
 * @author dkomj
 *
 */
public class DbEx {
	public static int queryInt(String sql, Object... paras) {
		 Object o = Db.queryFirst(sql,paras);
		 if(o == null)
			 return 0;
		 return Integer.parseInt(o.toString());
	}
}
