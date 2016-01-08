package com.jdc.cinema.entity;

import java.sql.Time;

import com.jdc.cinema.dao.Column;
import com.jdc.cinema.dao.Entity;
import com.jdc.cinema.dao.ID;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.dao.Param;
import com.jdc.cinema.dao.Table;
import com.jdc.cinema.dao.Temporal;
import com.jdc.cinema.dao.Temporal.Type;

@Table("show_time")
public class ShowTime implements Entity{

	private static final long serialVersionUID = 1L;
	
	@ID(generated=true)
	private long id;
	@Temporal(Type.TIME)
	@Column("time_from")
	private Time timeFrom;
	@Temporal(Type.TIME)
	@Column("time_to")
	private Time timeTo;
	private int status;
	
	@Override
	public Param getInsertParam() {
		return Param.getInstance("time_from", timeFrom)
				.param("time_to", timeTo)
				.param("status", status);
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

	public Time getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(Time timeFrom) {
		this.timeFrom = timeFrom;
	}

	public Time getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(Time timeTo) {
		this.timeTo = timeTo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", timeFrom.toString(), timeTo.toString());
	}

}
