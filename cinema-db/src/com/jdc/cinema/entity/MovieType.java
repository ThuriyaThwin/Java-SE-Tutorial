package com.jdc.cinema.entity;

import com.jdc.cinema.dao.Entity;
import com.jdc.cinema.dao.ID;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.dao.Param;
import com.jdc.cinema.dao.Table;

@Table("movie_type")
public class MovieType implements Entity{

	private static final long serialVersionUID = 1L;
	
	@ID(generated=true)
	private long id;
	private String type;

	@Override
	public Param getInsertParam() {
		return Param.getInstance("id", id)
				.param("type", type);
	}

	@Override
	public IdParam getIdParam() {
		return IdParam.getInstance("id", id);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}

}
