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

@Table("cinema_movie")
public class CinemaMovie implements Entity{

	private static final long serialVersionUID = 1L;
	
	@ID
	@Column("cinema_id")
	private String cinemaId;
	@ID
	@Column("movie_id")
	private long movieId;
	@Column("date_from")
	@Temporal(Type.DATE)
	private Date dateFrom;
	@Column("date_to")
	@Temporal(Type.DATE)
	private Date dateTo;
	
	public CinemaMovie() {
		// TODO Auto-generated constructor stub
	}
	
	

	public CinemaMovie(Cinema cinema, Movie movie, Date dateFrom, Date dateTo) {
		super();
		this.cinemaId = cinema.getId();
		this.movieId = movie.getId();
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}



	@Override
	public Param getInsertParam() {
		return Param.getInstance("cinema_id", cinemaId)
				.param("movie_id", movieId)
				.param("date_from", dateFrom)
				.param("date_to", dateTo);
	}

	@Override
	public IdParam getIdParam() {
		return IdParam.getInstance("cinema_id", cinemaId)
				.param("movie_id", movieId);
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

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

}
