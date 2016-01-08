package com.jdc.cinema.entity;

import com.jdc.cinema.dao.Entity;
import com.jdc.cinema.dao.ID;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.dao.Param;
import com.jdc.cinema.dao.Table;

@Table("seat_type")
public class SeatType implements Entity{

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeatType other = (SeatType) obj;
		if (id != other.id)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
