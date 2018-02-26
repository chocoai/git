package com.blit.lp.bus.security;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpSession;

import com.blit.lp.core.context.User;
import com.blit.lp.core.session.SessionConfig;
import com.blit.lp.tools.LPCacheKit;
import com.blit.lp.tools.LPLogKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;

/**
 * 用户会话踢下线
 * @author dkomj
 *
 */
public class UserSessionKillOff {
	
	public static final String USERKILLOFFCACHENAME= "lp_user_killoff";
	
	private static ConcurrentMap <String,HttpSession> sessions = 
			new ConcurrentHashMap<String, HttpSession>();

	public static void killOff(String userNum,HttpSession session){
		boolean iskilloffusersession = PropKit.use("global.properties").getBoolean("securyty.session.killoffusersession",false);
		if(!iskilloffusersession)
			return;
		
		boolean isdistributed = PropKit.use("global.properties").getBoolean("web.distributed",false);
		if(isdistributed){
			killOffDistributed(userNum,session);
		}
		else{
			killOffSingle(userNum,session);
		}
	}
	
	private static void killOffSingle(String userNum,HttpSession session){
		
		
		if(sessions.containsKey(userNum)){
			HttpSession oldSession = sessions.get(userNum);
			if(!oldSession.equals(session)){
				String oldSessionId = oldSession.getId();
				oldSession.removeAttribute(User.CURR_USER_SESSION);
				oldSession.invalidate();
				sessions.put(userNum, session);
				String info = String.format("用户(%s)被踢下线，SessionID:%s", userNum,oldSessionId);
				LPLogKit.warn(info);
			}
		}
		else{
			sessions.put(userNum, session);
		}
	}
	
	private static void killOffDistributed(String userNum,HttpSession session){
		String sessionId = session.getId();
		String oldSessionId = LPCacheKit.get(USERKILLOFFCACHENAME, userNum);
		if(StrKit.notBlank(oldSessionId)){
			if(!sessionId.equals(oldSessionId)){
				String session_key = SessionConfig.getInstance().getCookieName() + ":" + oldSessionId;
				SessionConfig.getInstance().getStorage().remove(session_key);
				LPCacheKit.put(USERKILLOFFCACHENAME, userNum,sessionId);
				String info = String.format("用户(%s)被踢下线，SessionID:%s", userNum,session_key);
				LPLogKit.warn(info);
			}
		}
		else{
			LPCacheKit.put(USERKILLOFFCACHENAME, userNum,sessionId);
		}
	}
}
