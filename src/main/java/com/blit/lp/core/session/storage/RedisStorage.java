package com.blit.lp.core.session.storage;

import net.oschina.j2cache.redis.RedisCacheProvider;
import net.oschina.j2cache.redis.RedisCacheProxy;
import net.oschina.j2cache.redis.client.RedisClient;
import net.oschina.j2cache.util.SerializationUtils;
import net.sf.ehcache.CacheException;

public class RedisStorage implements Storage {

	@Override
	public void start() {
		
	}

	@Override
	public void stop() {

	}

	@Override
	public void put(String key, Object value, int expiredTime) {
		if(value == null)
			remove(key);
		
		RedisCacheProxy redisCacheProxy = new RedisCacheProvider().getResource();
		RedisClient redisClient = null;
        try {
            redisClient = redisCacheProxy.getResource();
            redisClient.set(key.getBytes("utf-8"), SerializationUtils.serialize(value));
        }  catch (Exception e) {
        	throw new CacheException(e);
		}  finally {
        	redisCacheProxy.returnResource(redisClient);
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key) {
		RedisCacheProxy redisCacheProxy = new RedisCacheProvider().getResource();
		RedisClient redisClient = null;
		Object obj = null;
        try {
            redisClient = redisCacheProxy.getResource();
            byte[] b = redisClient.get(key.getBytes("utf-8"));
            if (b != null)
                obj = SerializationUtils.deserialize(b);
        } catch (Exception e) {
        	throw new CacheException(e);
		} finally {
        	redisCacheProxy.returnResource(redisClient);
        }
        
        return (T) obj;
	}

	@Override
	public void remove(String key) {
		RedisCacheProxy redisCacheProxy = new RedisCacheProvider().getResource();
		RedisClient redisClient = null;
        try {
            redisClient = redisCacheProxy.getResource();
            redisClient.del(key);
        }  catch (Exception e) {
        	throw new CacheException(e);
		} finally {
        	redisCacheProxy.returnResource(redisClient);
        }
	}

	@Override
	public void expire(String key, int expiredTime) {
		RedisCacheProxy redisCacheProxy = new RedisCacheProvider().getResource();
		RedisClient redisClient = null;
        try {
            redisClient = redisCacheProxy.getResource();
            redisClient.expire(key.getBytes("utf-8"), expiredTime);
        }  catch (Exception e) {
        	throw new CacheException(e);
		} finally {
        	redisCacheProxy.returnResource(redisClient);
        }
	}

	
}
