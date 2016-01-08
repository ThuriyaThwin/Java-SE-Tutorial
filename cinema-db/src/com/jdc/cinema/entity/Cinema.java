package com.jdc.cinema.entity;

import com.jdc.cinema.dao.Entity;
import com.jdc.cinema.dao.ID;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.dao.Param;

public class Cinema implements Entity{

	private static final long serialVersionUID = 1L;
	
	@ID
	private String id;
	private String name;
	
	@Override
	public Param getInsertParam() {
		return Param.getInstance("id", id).param("name", name);
	}
	
	@Override
	public IdParam getIdParam() {
		return IdParam.getInstance("id", id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
