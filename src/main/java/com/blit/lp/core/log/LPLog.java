package com.blit.lp.core.log;

import com.jfinal.log.Log;

public class LPLog extends Log {

	@Override
	public void debug(String message) {
		
	}

	@Override
	public void debug(String message, Throwable t) {

	}

	@Override
	public void info(String message) {

	}

	@Override
	public void info(String message, Throwable t) {

	}

	@Override
	public void warn(String message) {

	}

	@Override
	public void warn(String message, Throwable t) {

	}

	@Override
	public void error(String message) {

	}

	@Override
	public void error(String message, Throwable t) {

	}

	@Override
	public void fatal(String message) {

	}

	@Override
	public void fatal(String message, Throwable t) {

	}

	@Override
	public boolean isDebugEnabled() {
		return false;
	}

	@Override
	public boolean isInfoEnabled() {
		return false;
	}

	@Override
	public boolean isWarnEnabled() {
		return false;
	}

	@Override
	public boolean isErrorEnabled() {
		return false;
	}

	@Override
	public boolean isFatalEnabled() {
		return false;
	}

}
