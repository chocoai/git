package com.blit.lp.core.cache;

import java.util.List;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import net.oschina.j2cache.J2Cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class J2CacheHelper implements LPCacheHelper {

	private CacheChannel cache = null;
	@Override
	public void start() {
		cache = J2Cache.getChannel();
	}

	@Override
	public void stop() {
		cache.close();
	}

	@Override
	public void put(String cacheName, String key, Object value) {
		cache.set(cacheName, key, value);
	}

	@Override
	public void put(String cacheName, String key, Object value, int expiredSeconds) {
		cache.set(cacheName, key, value, expiredSeconds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String cacheName, String key) {
		CacheObject obj = cache.get(cacheName,key);
		if(obj == null){
			return null;
		}
		else {
			return (T) obj.getValue();
		}
	}

	@Override
	public void remove(String cacheName, String key) {
		cache.evict(cacheName, key);
	}

	@Override
	public void removeAll(String cacheName) {
		cache.clear(cacheName);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getKeys(String cacheName) {
		return cache.keys(cacheName);
	}


}
