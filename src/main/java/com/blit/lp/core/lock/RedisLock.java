package com.blit.lp.core.lock;

import com.blit.lp.bus.auditlog.LogStatusEnum;
import com.blit.lp.bus.auditlog.LogTypeEnum;
import com.blit.lp.core.exception.SysException;
import com.blit.lp.tools.AuditLogKit;
import com.blit.lp.tools.LPLogKit;
import com.jfinal.kit.LogKit;

import net.oschina.j2cache.redis.RedisCacheProvider;
import net.oschina.j2cache.redis.RedisCacheProxy;
import net.oschina.j2cache.redis.client.RedisClient;
import net.oschina.j2cache.util.SerializationUtils;

/**
 * 基于Redis的分布式锁
 * @author dkomj
 *
 */
public class RedisLock implements ILock {
	private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;

	private String lockKey = "";
	private boolean locked = false;
	private long expireMsecs = 0;//锁超时时间，防止线程在入锁以后，无限的执行等待
	public RedisLock(String lockKey,long expireMsecs){
		this.lockKey = "lp_lock_" + lockKey;
		if(expireMsecs <= 1)
			throw new SysException("为防止分布式锁死锁，请设置锁过期时间!");
		this.expireMsecs = expireMsecs;
	}
	
	private String get(final String key) {
		RedisCacheProxy redisCacheProxy = new RedisCacheProvider().getResource();
		RedisClient redisClient = null;
        try {
            redisClient = redisCacheProxy.getResource();
            byte[] bytes = redisClient.get(key.getBytes("utf-8"));
            return bytes == null ? null : new String(bytes, "utf-8");
        }  catch (Exception e) {
        	e.printStackTrace();
        	return null;
		}  finally {
        	redisCacheProxy.returnResource(redisClient);
        }
    }
	
	private boolean setNX(final String key, final String value) {
		RedisCacheProxy redisCacheProxy = new RedisCacheProvider().getResource();
		RedisClient redisClient = null;
        try {
            redisClient = redisCacheProxy.getResource();
            redisClient.setnx(key.getBytes("utf-8"), SerializationUtils.serialize(value));
            return true;
        }  catch (Exception e) {
        	e.printStackTrace();
        	return false;
		}  finally {
        	redisCacheProxy.returnResource(redisClient);
        }
    }

    private String getSet(final String key, final String value) {
    	RedisCacheProxy redisCacheProxy = new RedisCacheProvider().getResource();
		RedisClient redisClient = null;
        try {
            redisClient = redisCacheProxy.getResource();
            byte[] bytes = redisClient.getSet(key.getBytes("utf-8"), SerializationUtils.serialize(value));
            return bytes == null ? null : new String(bytes, "utf-8");
        }  catch (Exception e) {
        	e.printStackTrace();
        	return null;
		}  finally {
        	redisCacheProxy.returnResource(redisClient);
        }
    }
    
	@Override
	public void lock() {
		long timeoutMsecs = 1000 * 60 * 10;
		if(!this.tryLock(timeoutMsecs)){
			throw new SysException("获取分布式锁失败！");
		}
	}

	@Override
	public boolean tryLock(long timeoutMsecs) {
		long timeout = timeoutMsecs;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + this.expireMsecs + 1;
            String expiresStr = String.valueOf(expires); //锁到期时间
            if (this.setNX(lockKey, expiresStr)) {
                this.locked = true;
                return true;
            }

            String currentValueStr = this.get(lockKey); //redis里的时间
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
            	LPLogKit.warn("分布式锁占用时间过期！lockKey:" + lockKey);
                AuditLogKit.log(LogTypeEnum.SYSMANAGER, LogStatusEnum.FAIL, "警告：分布式锁占用时间过期！lockKey:" + lockKey);
            	String oldValueStr = this.getSet(lockKey, expiresStr);
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    this.locked = true;
                    return true;
                }
            }
            timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
            try {
				Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        return false;
	}

	@Override
	public void unlock() {
		if (this.locked) {
			RedisCacheProxy redisCacheProxy = new RedisCacheProvider().getResource();
			RedisClient redisClient = null;
	        try {
	            redisClient = redisCacheProxy.getResource();
	            redisClient.del(this.lockKey.getBytes("utf-8"));
	        }  catch (Exception e) {
	        	e.printStackTrace();
			}  finally {
	        	redisCacheProxy.returnResource(redisClient);
	        }
            this.locked = false;
        }
	}

}
