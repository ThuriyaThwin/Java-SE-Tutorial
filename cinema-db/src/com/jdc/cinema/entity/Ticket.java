package com.jdc.cinema.entity;

import java.util.Date;

import com.jdc.cinema.dao.Column;
import com.jdc.cinema.dao.Entity;
import com.jdc.cinema.dao.ID;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.dao.Param;

public class Ticket implements Entity {

	private static final long serialVersionUID = 1L;

	@ID
	@Column("cinema_id")
	private String cinemaId;
	@ID
	@Column("movie_id")
	private long movieId;
	@ID
	private Date date;
	@ID
	@Column("show_time_id")
	private long showTimeId;
	@ID
	@Column("seat_id")
	private long seatId;

	private int status;
	private double price;
	
	public Ticket() {
	}

	public Ticket(MovieShowTime mst, Seat seat, double price) {
		super();
		this.cinemaId = mst.getCinemaId();
		this.movieId = mst.getMovieId();
		this.date = mst.getShowDate();
		this.showTimeId = mst.getShowTimeId();
		this.seatId = seat.getId();
		this.price = price;
	}

	@Override
	public Param getInsertParam() {
		return Param.getInstance("cinema_id", cinemaId)
				.param("movie_id", movieId)
				.param("date", date)
				.param("show_time_id", showTimeId)
				.param("seat_id", seatId)
				.param("price", price);
	}

	@Override
	public IdParam getIdParam() {
		return IdParam.getInstance("cinema_id", cinemaId)
				.param("movie_id", movieId)
				.param("show_date", date)
				.param("show_time_id", showTimeId)
				.param("seatId", seatId);
	}

	public String getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}

	public long getMovieId() {
		return movieId;
	}

	public void setMovieId(long movieId) {
		this.movieId = movieId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getShowTimeId() {
		return showTimeId;
	}

	public void setShowTimeId(long showTimeId) {
		this.showTimeId = showTimeId;
	}

	public long getSeatId() {
		return seatId;
	}

	public void setSeatId(long seatId) {
		this.seatId = seatId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
