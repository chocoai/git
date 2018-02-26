package com.blit.lp.core.exception;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;

public class SysException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SysException(String errMsg){
		super(errMsg);
	}
	
	public SysException(Throwable cause){
		super(cause);
	}
	
	public SysException(String errMsg, Throwable cause){
		super(errMsg, cause);
	}
	
	public void render(Controller c){
		c.renderJson(Ret.fail("msg", this.getMessage()));
	}
}
