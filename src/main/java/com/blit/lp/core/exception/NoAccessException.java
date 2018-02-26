package com.blit.lp.core.exception;

/**
 * 无权访问异常
 * @author dkomj
 *
 */
public class NoAccessException extends SysException {

	private static final long serialVersionUID = 1L;

	public NoAccessException(String errMsg) {
		super(errMsg);
	}
}
