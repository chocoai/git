package com.blit.lp.core.cache;

import java.util.List;

public interface LPCacheHelper {
	
	public void start();
	
	public void stop();
	
	public void put(String cacheName, String key, Object value);
	
	public void put(String cacheName, String key, Object value, int expiredSeconds);

	public <T> T get(String cacheName, String key);

	public void remove(String cacheName, String key);
	
	public void removeAll(String cacheName);
	
	@SuppressWarnings("rawtypes")
	public List getKeys(String cacheName);

}
