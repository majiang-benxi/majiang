package com.mahjong.server.util;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;

import com.mahjong.server.entity.ManageUser;

public class ManageUserSessionUtil {
	
	private static ConcurrentHashMap<String, ManageUser> loginAdminUserInfos = new ConcurrentHashMap<String, ManageUser>();
	
	public static String createManegeSession(ManageUser manageUser){
		
		String uuid = UUID.randomUUID().toString();
		
		loginAdminUserInfos.put(uuid, manageUser);
		
		return uuid;
		
	}
	
	public static ManageUser getManegeSession(String uuid){
		return loginAdminUserInfos.get(uuid);
	}
	
	public static ManageUser removeManegeSession(String uuid){
		return loginAdminUserInfos.remove(uuid);
	}
	
	
	public static String getCookieValue(Cookie[] cookies , String key){
		
		if(cookies!=null&&cookies.length>0){
			for(Cookie cookie :  cookies){
				if(cookie.getName().equals(key)){
					return cookie.getValue();
				}
				
			}
			
		}
		return null;
	}

}
