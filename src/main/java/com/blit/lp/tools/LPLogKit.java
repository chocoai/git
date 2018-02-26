package com.blit.lp.tools;

import com.jfinal.kit.LogKit;
import com.jfinal.log.Log;

/**
 * LP日志工具类
 * @author dkomj
 *
 */
public class LPLogKit {
	
	private static class Holder {
		private static Log log = Log.getLog(LogKit.class);
	}
	
	public static void synchronizeLog() {
		Holder.log = Log.getLog(LogKit.class);
	}
	
	/**
	 * Do nothing.
	 */
	public static void logNothing(Throwable t) {
		
	}
	
	public static void debug(String message) {
		Holder.log.debug(message);
	}
	
	public static void debug(String message, Throwable t) {
		Holder.log.debug(message, t);
	}
	
	public static void info(String message) {
		Holder.log.info(message);
	}
	
	public static void info(String message, Throwable t) {
		Holder.log.info(message, t);
	}
	
	public static void warn(String message) {
		Holder.log.warn(message);
	}
	
	public static void warn(String message, Throwable t) {
		Holder.log.warn(message, t);
	}
	
	public static void error(String message) {
		Holder.log.error(message);
	}
	
	public static void error(String message, Throwable t) {
		Holder.log.error(message, t);
	}
	
	public static void fatal(String message) {
		Holder.log.fatal(message);
	}
	
	public static void fatal(String message, Throwable t) {
		Holder.log.fatal(message, t);
	}
}
