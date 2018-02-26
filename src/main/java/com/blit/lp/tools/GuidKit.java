package com.blit.lp.tools;

import java.util.UUID;

/**
 * Guid生成工具
 * @author dkomj
 *
 */
public class GuidKit {
	public static String createGuid(){
	    String s = UUID.randomUUID().toString(); 
	    return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);  
	}
}
