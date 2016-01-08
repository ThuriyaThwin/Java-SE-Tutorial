package com.jdc.cinema.entity;

import java.text.DecimalFormat;

import com.jdc.cinema.dao.Column;
import com.jdc.cinema.dao.Entity;
import com.jdc.cinema.dao.ID;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.dao.Param;

public class Seat implements Entity{

	private static final long serialVersionUID = 1L;
	
	@ID(generated=true)
	private long id;
	@Column("row_number")
	private String rowNumber;
	private int number;
	@Column("seat_type_id")
	private long seatTypeId;
	@Column("floor_id")
	private long floorId;

	@Override
	public Param getInsertParam() {
		return Param.getInstance("row_number", rowNumber)
				.param("number", number)
				.param("seat_type_id", seatTypeId)
				.param("floor_id", floorId);
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

	public String getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public long getSeatTypeId() {
		return seatTypeId;
	}

	public void setSeatTypeId(long seatTypeId) {
		this.seatTypeId = seatTypeId;
	}

	public long getFloorId() {
		return floorId;
	}

	public void setFloorId(long floorId) {
		this.floorId = floorId;
	}
	
	public String getSeatName() {
		DecimalFormat fmt = new DecimalFormat("00");
		return String.format("%s-%s", this.rowNumber, fmt.format(this.number));
	}

}
