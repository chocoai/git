package com.blit.lp.tools;

import com.blit.lp.tools.Encoder.AjaxEncoder;
import com.blit.lp.tools.Encoder.Md5;

/**
 * 编码工具类
 * @author dkomj
 *
 */
public class EncodeKit {
	
	public static String md5(String str) {
        return Md5.md5(str);
    }
	
	public static String AjaxEncode(String str){
		return AjaxEncoder.encode(str);
	}
	
	public static String AjaxDecode(String str){
		return AjaxEncoder.decode(str);
	}

}
