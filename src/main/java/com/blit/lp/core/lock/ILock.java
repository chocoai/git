package com.blit.lp.core.lock;

/**
 * LP锁
 * @author dkomj
 *
 */
public interface ILock {
	public void lock();
	public boolean tryLock(long milliseconds);
	public void unlock();
}
