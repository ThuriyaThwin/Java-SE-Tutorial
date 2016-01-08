package com.jdc.cinema.entity;

import com.jdc.cinema.dao.Column;
import com.jdc.cinema.dao.Entity;
import com.jdc.cinema.dao.ID;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.dao.Param;

public class Floor implements Entity{

	private static final long serialVersionUID = 1L;
	
	@ID(generated=true)
	private long id;
	@Column("floor_name")
	private String floorName;
	@Column("cinema_id")
	private String cinemaId;

	@Override
	public Param getInsertParam() {
		return Param.getInstance("id", id)
				.param("floor_name", floorName)
				.param("cinema_id", cinemaId);
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

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}

}
