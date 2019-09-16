package com.silrais.toolkit.permission;

import java.util.HashMap;
import java.util.Map;


public enum VisibilityPermission { 
	
	ShowAll("showAll"), 
	ShowOnly("showOnly"), 
	HideAll("hideAll"), 
	HideOnly("hideOnly");
	
	static Map<String, VisibilityPermission> cache;
	
	String token;
	
	VisibilityPermission(String token) {
		this.token = token;
		addToCache(token, this);
	}
	
	public String token() { 
		return this.token; 
	}
	
	static void addToCache(String token, VisibilityPermission permission) {
		if (cache == null) {
			cache = new HashMap<String, VisibilityPermission>();
		}
		cache.put(token, permission);
	}
	
	public VisibilityPermission find(String token) {
		VisibilityPermission permission = cache.get(token);
		if (permission == null) {
			permission = VisibilityPermission.HideAll;
		}
		return permission;
	}
	
};
