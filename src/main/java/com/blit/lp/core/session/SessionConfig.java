package com.blit.lp.core.session;

import com.blit.lp.core.session.storage.Storage;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

public class SessionConfig {
	
	public static final String DEFAULT_SESSION_COOKIENNAME = "LP_SESSIONID";

	public static final int DEFAULT_SESSION_COOKIEMAXAGE = -1;

	public static final int DEFAULT_SESSION_TIMEOUT = 30;
	
	private SessionConfig(){
		
	}
	
	private void init() {
		Prop pro = PropKit.use("global.properties");
		this.setCookieName(pro.get("session.cookie.name", DEFAULT_SESSION_COOKIENNAME));
		this.setCookieDomain(pro.get("session.cookie.domain", ""));
		this.setCookiePath(pro.get("session.cookie.path", "/"));
		this.setCookieMaxAge(pro.getInt("cookieMaxAge", DEFAULT_SESSION_COOKIEMAXAGE));
		this.setSessionTimeout(pro.getInt("session.timeout", DEFAULT_SESSION_TIMEOUT) * 60);
	}
	
	public static SessionConfig m_ObjConfig = null;
	public static SessionConfig getInstance(){
		if(m_ObjConfig == null){
			m_ObjConfig = new SessionConfig();
			m_ObjConfig.init();
		}
		return m_ObjConfig;
	}
	
	private String cookieDomain;

	private String cookieName;

	private int cookieMaxAge;

	private String cookiePath;

	private int sessionTimeout;

	private Storage storage;

	public String getCookieDomain() {
		return cookieDomain;
	}

	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

	public String getCookieName() {
		return cookieName;
	}

	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}

	public int getCookieMaxAge() {
		return cookieMaxAge;
	}

	public void setCookieMaxAge(int cookieMaxAge) {
		this.cookieMaxAge = cookieMaxAge;
	}

	public String getCookiePath() {
		return cookiePath;
	}

	public void setCookiePath(String cookiePath) {
		this.cookiePath = cookiePath;
	}

	public int getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

}
