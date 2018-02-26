package com.blit.lp.tools;

import java.util.List;

import com.blit.lp.core.cache.LPCacheManager;

/**
 * LP缓存工具类
 * @author dkomj
 *
 */
public class LPCacheKit {
	
	public static final String DEFAULT_CACHENAME = "lp_common";
	/**
	 * 设置缓存,cacheName默认为"lp_common"
	 * @param key
	 * @param value,缓存对象(可序列化)
	 */
	public static void put(String key, Object value) {
		LPCacheManager.getCacheHelper().put(DEFAULT_CACHENAME, key, value);
	}
	/**
	 * 设置缓存
	 * @param cacheName
	 * @param key
	 * @param value,缓存对象(可序列化)
	 */
	public static void put(String cacheName, String key, Object value) {
		LPCacheManager.getCacheHelper().put(cacheName, key, value);
	}
	/**
	 * 设置缓存,带过期时间,cacheName默认为"lp_common"
	 * @param key
	 * @param value,缓存对象(可序列化)
	 * @param expiredTime(单位:秒)
	 */
	public static void put(String key, Object value, int expiredTime) {
		LPCacheManager.getCacheHelper().put(DEFAULT_CACHENAME, key, value,expiredTime);
	}
	/**
	 * 设置缓存,带过期时间
	 * @param cacheName
	 * @param key
	 * @param value
	 * @param expiredTime(单位:秒)
	 */
	public static void put(String cacheName, String key, Object value, int expiredTime) {
		LPCacheManager.getCacheHelper().put(cacheName, key, value,expiredTime);
	}
	/**
	 * 获取缓存对象,cacheName默认为"lp_common"
	 * @param key
	 * @return
	 */
	public static <T> T get(String key) {
		return LPCacheManager.getCacheHelper().get(DEFAULT_CACHENAME, key);
	}
	/**
	 * 获取缓存对象
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static <T> T get(String cacheName, String key) {
		return LPCacheManager.getCacheHelper().get(cacheName, key);
	}
	/**
	 * 删除获取缓存, cacheName默认为"lp_common"
	 * @param key
	 */
	public static void remove(String key) {
		LPCacheManager.getCacheHelper().remove(DEFAULT_CACHENAME, key);
	}
	/**
	 * 删除获取缓存
	 * @param cacheName
	 * @param key
	 */
	public static void remove(String cacheName, String key) {
		LPCacheManager.getCacheHelper().remove(cacheName, key);
	}
	/**
	 * 删除cacheName默认为"lp_common"的所有缓存
	 */
	public static void removeAll() {
		LPCacheManager.getCacheHelper().removeAll(DEFAULT_CACHENAME);
	}
	/**
	 * 删除指定cacheName下所有缓存
	 * @param cacheName
	 */
	public static void removeAll(String cacheName) {
		LPCacheManager.getCacheHelper().removeAll(cacheName);
	}
	/**
	 * 获取cacheName默认为"lp_common"下的所有key列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List getKeys() {
		return LPCacheManager.getCacheHelper().getKeys(DEFAULT_CACHENAME);
	}
	/**
	 * 获取指定cacheName下的所有key列表
	 * @param cacheName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List getKeys(String cacheName) {
		return LPCacheManager.getCacheHelper().getKeys(cacheName);
	}

}
