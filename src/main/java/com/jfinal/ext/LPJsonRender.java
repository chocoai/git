package com.jfinal.ext;

import com.jfinal.render.JsonRender;

/**
 * 为了不影响业务应用（Record对象输入到前端Json属性大小写）
 * LP系统应用专用，Json输出(Record属性全部转为小写)
 * 
 *
 */
public class LPJsonRender extends JsonRender {
	
	public LPJsonRender(Object obj)
	{
		super(LPJson.getJson().toJson(obj));
	}
	
	
}
