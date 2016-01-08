package com.jdc.cinema.entity;

import com.jdc.cinema.dao.Column;
import com.jdc.cinema.dao.Entity;
import com.jdc.cinema.dao.ID;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.dao.Param;

public class Price implements Entity{

	private static final long serialVersionUID = 1L;

	@ID
	@Column("movie_type_id")
	private long movieTypeId;
	@ID
	@Column("seat_type_id")
	private long seatTypeId;
	
	private double price;
	
	@Override
	public Param getInsertParam() {
		return Param.getInstance("movie_type_id", movieTypeId)
				.param("seat_type_id", seatTypeId)
				.param("price", price);
	}

	@Override
	public IdParam getIdParam() {
		return IdParam.getInstance("movie_type_id", movieTypeId)
				.param("seat_type_id", seatTypeId);
	}

	public long getMovieTypeId() {
		return movieTypeId;
	}

	public void setMovieTypeId(long movieTypeId) {
		this.movieTypeId = movieTypeId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getSeatTypeId() {
		return seatTypeId;
	}

	public void setSeatTypeId(long seatTypeId) {
		this.seatTypeId = seatTypeId;
	}

}
