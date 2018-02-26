package com.blit.lp.bus.online;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpSession;

@Deprecated
public class OnlineManager {

	private static final OnlineManager Instance = new OnlineManager();
	
	private ConcurrentMap <String,HttpSession> sessions = 
			new ConcurrentHashMap<String, HttpSession>();
	
	private OnlineManager() {
		
	}
	
	public static OnlineManager getInstance() {
		return Instance;
	}
	
	public void addSession(HttpSession se){
		sessions.put(se.getId(), se);
	}
	
	public void removeSession(HttpSession se){
		sessions.remove(se.getId());
	}
}
