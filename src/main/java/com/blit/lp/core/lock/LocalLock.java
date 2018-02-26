package com.blit.lp.core.lock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 单机锁
 * @author dkomj
 *
 */
public class LocalLock implements ILock {
	
	private static ConcurrentHashMap<String, Lock> lockMap
		 = new ConcurrentHashMap<String, Lock>();
	
	private String lockKey = "";
	private boolean locked = false;
	public LocalLock(String lockKey){
		this.lockKey = "lp_lock_" + lockKey;
		synchronized (lockMap) {
			if(!lockMap.containsKey(this.lockKey)){
				lockMap.put(this.lockKey, new ReentrantLock());
			}
		}
	}
	
	@Override
	public void lock() {
		Lock lock = lockMap.get(this.lockKey);
		lock.lock();
		this.locked = true;
	}
	
	@Override
	public boolean tryLock(long milliseconds) {
		Lock lock = lockMap.get(this.lockKey);
		try {
			this.locked = lock.tryLock(milliseconds,  TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			this.locked = false;
			e.printStackTrace();
		}
		return this.locked;
	}

	@Override
	public void unlock() {
		if(this.locked){
			Lock lock = lockMap.get(this.lockKey);
			lock.unlock();
			this.locked = false;
		}
	}

}
