package com.jdc.cinema.service;

import java.time.LocalDate;
import java.util.List;

import com.jdc.cinema.entity.Cinema;
import com.jdc.cinema.entity.Movie;
import com.jdc.cinema.entity.MovieShowTime;
import com.jdc.cinema.service.imp.MovieShowTimeServiceImp;

public interface MovieShowTimeService {
	
	public static MovieShowTimeService getInsatnce() {
		return MovieShowTimeServiceImp.getService();
	}
	
	void create(Movie movie, Cinema cinema, LocalDate dateFrom, LocalDate dateTo);
	List<MovieShowTime> search(Cinema cinema, LocalDate date);

	List<LocalDate> getShowDates(Cinema c, Movie m);
}
