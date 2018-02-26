package com.blit.lp.core.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


public class EhCacheHelper implements LPCacheHelper {

	private static CacheManager cacheManager;
	private static Object locker = new Object();
	private final static Logger log = LoggerFactory.getLogger(EhCacheHelper.class);
	
	@Override
	public void start() {
		cacheManager = CacheManager.create();
	}

	@Override
	public void stop() {
		cacheManager.shutdown();
	}
	
	private static Cache getOrAddCache(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			synchronized(locker) {
				cache = cacheManager.getCache(cacheName);
				if (cache == null) {
					log.warn("Could not find cache config [" + cacheName + "], using default.");
					cacheManager.addCacheIfAbsent(cacheName);
					cache = cacheManager.getCache(cacheName);
					log.debug("Cache [" + cacheName + "] started.");
				}
			}
		}
		return cache;
	}
	
	@Override
	public void put(String cacheName, String key, Object value){
		put(cacheName,key,value, -1);
	}
	
	@Override
	public void put(String cacheName, String key, Object value, int expiredSeconds) {
		Element element = new Element( key, value );
		if(expiredSeconds > 0)
			element.setTimeToLive(expiredSeconds);
		getOrAddCache(cacheName).put(element);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String cacheName, String key) {
		if ( key == null ) 
			return null;
		else {
            Element element = getOrAddCache(cacheName).get( key );
			if ( element != null )
				return (T) element.getObjectValue();				
		}
		return null;
	}

	@Override
	public void remove(String cacheName, String key) {
		getOrAddCache(cacheName).remove(key);
	}

	@Override
	public void removeAll(String cacheName) {
		getOrAddCache(cacheName).removeAll();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getKeys(String cacheName) {
		return getOrAddCache(cacheName).getKeys();
	}

	
}
