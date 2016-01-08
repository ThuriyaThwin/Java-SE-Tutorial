package com.jdc.cinema.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class IdParam {
	
	private Map<String, Object> params;
	
	private IdParam() {
		params = new LinkedHashMap<String, Object>();
	}
	
	public static IdParam getInstance(String key, Object value) {
		IdParam param = new IdParam();
		return param.param(key, value);
	}
	
	public IdParam param(String key, Object value) {
		params.put(key, value);
		return this;
	}
	
	public String query() {
		
		StringBuilder sb = new StringBuilder();
		
		List<String> keys = new ArrayList<String>(params.keySet());
		
		for(int i=0; i < keys.size(); i++) {
			if(i > 0) {
				sb.append(" and ");
			}
			sb.append(keys.get(i));
			sb.append(" = ?");
		}
		
		return sb.toString();
	}
	
	public List<Object> values() {
		return new ArrayList<>(params.values());
	}

}
