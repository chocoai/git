package com.blit.lp.core.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.blit.lp.core.session.storage.Storage;
import com.jfinal.core.JFinal;

/**
 * LP用于分布式的会话
 *
 */
@SuppressWarnings({ "deprecation", "unused" })
public class LPSession implements HttpSession {
	private final SessionConfig config = SessionConfig.getInstance();
	private final Storage storage = config.getStorage();
	private final String cookieName = config.getCookieName();
	private final int maxAlive = config.getSessionTimeout();

	private final String id;
	private final String session_key;
	private SessionObj sessionObj;
	private boolean invalid = false;

	public LPSession(String id) {
		this.id = id;
		this.session_key = cookieName + ":" + id;
		sessionObj = storage.get(session_key);
		if (sessionObj == null) {
			sessionObj = new SessionObj();
			sessionObj.setLastAccessTime(System.currentTimeMillis());
			storage.put(session_key, sessionObj, maxAlive);
		} else {
			if (sessionObj.isNew()) {
				sessionObj.setNew(false);
			}
			
			/**
			 * 两种存储方式，目前使用方式2
			 * 1)、1分钟重新存储sessionObj对象和过期，但是需要分布式应用服务的时间同步
			 * 2)、每次访问session都重新更新，过期时间
			 */
//			long oldTime = sessionObj.getLastAccessTime();
//			long nowTime = System.currentTimeMillis();
//			long dis = nowTime - oldTime;
//			
//			if(dis > 1000 * 60){
//				sessionObj.setLastAccessTime(nowTime);
//				storage.put(session_key, sessionObj, maxAlive);
//			}
			storage.expire(session_key, maxAlive);
		}
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public long getCreationTime() {
		return sessionObj.getCreateTime();
	}

	@Override
	public long getLastAccessedTime() {
		return sessionObj.getLastAccessTime();
	}

	@Override
	public ServletContext getServletContext() {
		return JFinal.me().getServletContext();
	}

	@Override
	public void setMaxInactiveInterval(int maxInactiveInterval) {
		// ignore
	}

	@Override
	public int getMaxInactiveInterval() {
		return maxAlive;
	}

	@Override
	public Object getAttribute(String attributeName) {
		checkSessionInvalild();
		Serializable attribute = sessionObj.getAttribute(attributeName);
		return attribute;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		checkSessionInvalild();
		Set<String> attributeNameSet = sessionObj.getAttributeNames();
		return new Enumerator(attributeNameSet);
	}

	@Override
	public void setAttribute(String attributeName, Object attributeValue) {
		checkSessionInvalild();
		checkSerializable(attributeValue);
		sessionObj.putAttribute(attributeName, (Serializable) attributeValue);
		storage.put(session_key, sessionObj, maxAlive);
	}

	@Override
	public void removeAttribute(String attributeName) {
		checkSessionInvalild();
		sessionObj.removeAttribute(attributeName);
		if (sessionObj.isEmpaty()) {
			storage.remove(session_key);
		} else {
			storage.put(session_key, sessionObj, maxAlive);
		}
	}

	@Override
	public void invalidate() {
		invalid = true;
		storage.remove(session_key);
	}

	@Override
	public boolean isNew() {
		checkSessionInvalild();
		return sessionObj.isNew();
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}



	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Session id is ");
		sb.append(this.id);
		sb.append(" ,detail is ");
		sb.append(sessionObj);
		return sb.toString();
	}

	@Override
	public HttpSessionContext getSessionContext() {
		throw new UnsupportedOperationException("Not supported .");
	}

	@Override
	public void removeValue(String name) {
		removeAttribute(name);
	}

	@Override
	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	@Override
	public String[] getValueNames() {
		Enumeration<String> attributeNames = getAttributeNames();
		List<String> attributeNameList = new ArrayList<String>();
		while (attributeNames.hasMoreElements()) {
			attributeNameList.add((String) attributeNames.nextElement());
		}
		return attributeNameList.toArray(new String[0]);
	}

	@Override
	public Object getValue(String name) {
		return getAttribute(name);
	}

	private void checkSerializable(Object value) {
		if (value == null) {
			return;
			//throw new NullPointerException();
		}
		if (!Serializable.class.isInstance(value)) {
			throw new RuntimeException(value.getClass().getName() + " NotSerializable  ");
		}
	}

	/**
	 * 判断当前Session是否已经失效.
	 * 
	 * @throws IllegalStateException
	 *             Session已经失效的异常.
	 */
	private void checkSessionInvalild() {
		if (invalid) {
			throw new IllegalStateException("Session is invalid.");
		}
	}

	private static class Enumerator implements Enumeration<String> {
		private Iterator<String> iter;

		public Enumerator(Set<String> attributeNames) {
			this.iter = attributeNames.iterator();
		}

		@Override
		public boolean hasMoreElements() {
			return iter.hasNext();
		}

		@Override
		public String nextElement() {
			return iter.next();
		}
	}

}