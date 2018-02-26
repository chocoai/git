package com.blit.lp.core.session;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.blit.lp.tools.EncodeKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;


public class LPAjaxEncodeRequest extends HttpServletRequestWrapper {
	
	private static final boolean xssfilter 
	= PropKit.use("global.properties").getBoolean("web.xssfilter", false);
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private Set<String> keySet = new HashSet<String>(); 
	public LPAjaxEncodeRequest(HttpServletRequest request, HttpServletResponse response) {
		super(request);
		this.request = request;
		this.response = response;
		
		String _lpkeys = super.getParameter("_lpkeys");
		if(StrKit.notBlank(_lpkeys)){
			_lpkeys = EncodeKit.AjaxDecode(_lpkeys);
			for (String key : _lpkeys.split("&")) {
				if(StrKit.notBlank(key) && 
						!keySet.contains(key)){
					keySet.add(key);
				}
			};
		}
	}

	@Override
	public String getParameter(String name) {
		String val = super.getParameter(name);
		if(keySet.contains(name)){
			val = EncodeKit.AjaxDecode(val);
		}
		
		val = xssFilter(val);
		return val;
	}
	
	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = super.getParameterMap();
		Map<String, String[]> newMap = new HashMap<String, String[]>();
		for (String key : map.keySet()) {
			newMap.put(key, getParameterValues(key));
		}

		return newMap;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return super.getParameterNames();
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] vals = super.getParameterValues(name);
		if(vals != null){
			for(int i=0; i < vals.length; i++){
				String val = vals[i];
				if(StrKit.notBlank(val) && keySet.contains(name)){
					vals[i] = EncodeKit.AjaxDecode(val);
				}
				
				vals[i] = xssFilter(vals[i]);
			}
			
		}
		
		return vals;
	}
	
	private static String xssFilter(String val){
		if(xssfilter)
			return StrKit.isBlank(val) ? val : Jsoup.clean(val, Whitelist.relaxed());
		else
			return val;
	}
}