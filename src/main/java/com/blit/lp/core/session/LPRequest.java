package com.blit.lp.core.session;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LP分布式请求
 *
 */
public class LPRequest extends HttpServletRequestWrapper {

	private LPSession session = null;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private SessionConfig config = SessionConfig.getInstance();
	
	public LPRequest(HttpServletRequest request, HttpServletResponse response) {
		super(request);
		this.request = request;
		this.response = response;
	}

	@Override
	public HttpSession getSession() {
		return getSession(true);
	}

	@Override
	public HttpSession getSession(boolean create) {
		return doGetSession(create);
	}
	
	private HttpSession doGetSession(boolean create) {
		if (session == null) {
			Cookie cookie = null;
			Cookie[] cookies = super.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					if (c.getName().equals(config.getCookieName())) {
						cookie = c;
						break;
					}
				}
			}
			if (cookie != null) {
				String sessionId = cookie.getValue();
				session = buildSession(sessionId, false);
			} else {
				session = buildSession();
			}
		}
		return session;
	}
	
	private LPSession buildSession() {
		String sessionId = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		return buildSession(sessionId, true);
	}
	
	private LPSession buildSession(String sessionId, boolean create) {
		LPSession session = new LPSession(sessionId);
		if (create) {
			addCookie(sessionId);
		}
		return session;
	}

	private void addCookie(String sessionId) {
		Cookie cookie = new Cookie(config.getCookieName(), sessionId);
		cookie.setMaxAge(config.getCookieMaxAge());
		cookie.setSecure(request.isSecure());
		cookie.setPath(config.getCookiePath());
		String cookieDomain = config.getCookieDomain();
		if (cookieDomain != null && !"".equals(cookieDomain.trim())) {
			cookie.setDomain(cookieDomain);
		}
		response.addCookie(cookie);
	}
}