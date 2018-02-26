package com.blit.lp.core.session.storage;

/**
 * 分布式会员外部存储接口
 *
 */
public interface Storage {
	
	public void start();
	
	public void stop();
	
	public void put(String key, Object value, int expiredTime);

	public <T> T get(String key);

	public void remove(String key);

	public void expire(String key, int expiredTime);
}
