package com.blit.lp.core.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 存储的Session对象
 *
 */
public class SessionObj implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean isNew;
	private long lastAccessTime;
	private long createTime;

	private Map<String, Serializable> attribute;

	public SessionObj() {
		this.createTime = System.currentTimeMillis();
		this.attribute = new HashMap<String, Serializable>();
		this.isNew = true;
	}

	public long getCreateTime() {
		return createTime;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public long getLastAccessTime() {
		return this.lastAccessTime;
	}

	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public void putAttribute(String name, Serializable value) {
		attribute.put(name, value);
	}

	public Serializable removeAttribute(String name) {
		return attribute.remove(name);
	}

	public Serializable getAttribute(String name) {
		return attribute.get(name);
	}

	public Set<String> getAttributeNames() {
		return attribute.keySet();
	}

	public boolean isEmpaty() {
		return attribute == null || attribute.isEmpty();
	}

	@Override
	public String toString() {
		return "Session [isNew=" + isNew + ", lastAccessTime=" + lastAccessTime + ", createTime=" + createTime + ", attribute=" + attribute + "]";
	}
}
