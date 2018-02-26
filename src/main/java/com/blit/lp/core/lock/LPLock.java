package com.blit.lp.core.lock;

import com.jfinal.kit.PropKit;

/**
 * LP锁
 * 	单机运行模式下,LocalLock锁
 *  分布式运行，RedisLock锁
 * @author dkomj
 *
 */
public class LPLock implements ILock {
	private static boolean isdistributed = 
			PropKit.use("global.properties").getBoolean("web.distributed",false);
	
	private ILock lock = null;
	public LPLock(String lockKey){
		if(isdistributed){
			//锁最长占用时间30分钟，
			long expireMsecs = 1000 * 60 * 30;
			lock = new RedisLock(lockKey, expireMsecs);
		}
		else{
			lock = new LocalLock(lockKey);
		}
	}
	
	@Override
	public void lock() {
		this.lock.lock();
	}

	@Override
	public boolean tryLock(long milliseconds) {
		return this.lock.tryLock(milliseconds);
	}

	@Override
	public void unlock() {
		this.lock.unlock();
	}
	
}
