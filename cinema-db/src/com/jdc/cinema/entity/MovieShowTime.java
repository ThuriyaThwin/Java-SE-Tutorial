package com.jdc.cinema.entity;

import java.util.Date;

import com.jdc.cinema.dao.Column;
import com.jdc.cinema.dao.Entity;
import com.jdc.cinema.dao.ID;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.dao.Param;
import com.jdc.cinema.dao.Table;
import com.jdc.cinema.dao.Temporal;
import com.jdc.cinema.dao.Temporal.Type;

@Table("movie_show_time")
public class MovieShowTime implements Entity{
	
	@ID
	@Column("cinema_id")
	private String cinemaId;
	@ID
	@Column("movie_id")
	private long movieId;
	@ID
	@Column("show_date")
	@Temporal(Type.DATE)
	private Date showDate;
	@ID
	@Column("show_time_id")
	private long showTimeId;
	
	public MovieShowTime() {
		// TODO Auto-generated constructor stub
	}
	
	public MovieShowTime(Cinema cinema, Movie movie, Date showDate, ShowTime showTime) {
		cinemaId = cinema.getId();
		movieId = movie.getId();
		this.showDate = showDate;
		showTimeId = showTime.getId();
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Param getInsertParam() {
		return Param.getInstance("cinema_id", cinemaId)
			.param("movie_id", movieId)
			.param("show_date", showDate)
			.param("show_time_id", showTimeId);
	}

	@Override
	public IdParam getIdParam() {
		return IdParam.getInstance("cinema_id", cinemaId)
				.param("movie_id", movieId)
				.param("show_date", showDate)
				.param("show_time_id", showTimeId);
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

	public Date getShowDate() {
		return showDate;
	}

	public void setShowDate(Date showDate) {
		this.showDate = showDate;
	}

	public long getShowTimeId() {
		return showTimeId;
	}

	public void setShowTimeId(long showTimeId) {
		this.showTimeId = showTimeId;
	}

}
