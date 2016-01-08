package com.jdc.cinema.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Param {
	
	private Map<String, Object> params;
	
	private Param() {
		params = new LinkedHashMap<String, Object>();
	}
	
	public static Param getInstance(String key, Object value) {
		Param param = new Param();
		return param.param(key, value);
	}
	
	public Param param(String key, Object value) {
		params.put(key, value);
		return this;
	}
	
	public String getKeys() {
		StringBuilder sb = new StringBuilder();
		
		List<String> keys = new ArrayList<String>(params.keySet());
		
		for(int i=0; i < keys.size(); i++) {
			if(i > 0) {
				sb.append(" ,");
			}
			sb.append(keys.get(i));
		}

		return sb.toString();
	}
	
	public String getValueString() {
		StringBuilder sb = new StringBuilder();
		
		List<String> keys = new ArrayList<String>(params.keySet());
		
		for(int i=0; i < keys.size(); i++) {
			if(i > 0) {
				sb.append(" ,");
			}
			sb.append("?");
		}

		return sb.toString();
	}
	
	public List<Object> getValues() {
		return new ArrayList<>(params.values());
	}
	

}
