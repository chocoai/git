package com.blit.lp.core.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blit.lp.core.cache.LPCacheManager;
import com.blit.lp.core.session.LPAjaxEncodeRequest;
import com.blit.lp.core.session.LPRequest;
import com.blit.lp.core.session.SessionConfig;
import com.blit.lp.core.session.storage.RedisStorage;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

/**
 * LP能力支持Filter
 *
 */
public class LPSupportFilter implements Filter {
	
	private FilterConfig filterConfig = null;
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
		Prop pro = PropKit.use("global.properties");
		boolean isdistributed = pro.getBoolean("web.distributed",false);
		LPCacheManager.start();
		RedisStorage storage = new RedisStorage();
		storage.start();
		SessionConfig.getInstance().setStorage(storage);
		
		if(isdistributed){
			storage.get("lp_test");
		}
	}
	
	@Override
	public void destroy() {
		LPCacheManager.close();
		SessionConfig.getInstance().getStorage().stop();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest newReq = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		this.setEncoding(newReq,resp);
		
		//启用分布式缓存
		if(PropKit.getBoolean("web.distributed", false))
			newReq = new LPRequest(newReq,resp);
		
		//启用Ajax加密
		if(PropKit.getBoolean("web.ajaxencode", false))
			newReq = new LPAjaxEncodeRequest(newReq,resp);
		
		chain.doFilter(newReq, resp);
	}

	private void setEncoding(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		String encoding = this.filterConfig.getInitParameter("encoding");
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		
		String url = request.getRequestURL().toString();
		if(url.endsWith(".js")){
			response.setContentType("text/javascript;charset=" + encoding);
		}
		else if(url.endsWith(".css")){
			response.setContentType("text/css;charset=" + encoding);
		}
	}
}
