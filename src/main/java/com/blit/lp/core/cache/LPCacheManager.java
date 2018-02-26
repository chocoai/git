package com.blit.lp.core.cache;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

public class LPCacheManager {
	private static LPCacheHelper cacheHelper = null;
	private static LPCacheHelper localCacheHelper = null;
	
	public static void start(){ 
		Prop pro = PropKit.use("global.properties");
		boolean isdistributed = pro.getBoolean("web.distributed",false);
		if(isdistributed){
			System.out.println("系统启用分布式模式");
			cacheHelper = new J2CacheHelper();
			localCacheHelper = new EhCacheHelper();
			localCacheHelper.start();
		}
		else{
			cacheHelper = new EhCacheHelper();
		}
		
		cacheHelper.start();
	}
	
	/**
	 * 非分布式模式，EhCache
	 * 分布式模式,J2Cache
	 * @return
	 */
	public static LPCacheHelper getCacheHelper(){
		return cacheHelper;
	}
	
	/**
	 * 只是本地EhCache
	 * @return
	 */
	public static LPCacheHelper getLocalCacheHelper(){
		if(localCacheHelper == null)
			return cacheHelper;
		else
			return localCacheHelper;
	}
	
	public static void close(){
		cacheHelper.stop();
		if(localCacheHelper != null)
			localCacheHelper.stop();
	}
}
