package com.jdc.cinema.service;

import java.util.List;

import com.jdc.cinema.entity.Cinema;
import com.jdc.cinema.entity.Movie;
import com.jdc.cinema.entity.MovieType;
import com.jdc.cinema.service.imp.MovieServiceImp;

public interface MovieService {

	public static MovieService getInstance() {
		return MovieServiceImp.getService();
	}
	
	void create(Movie m);
	List<Movie> getAll();
	List<Movie> search(MovieType type, String name);
	Movie get(long id);
	
	List<Movie> getAvaliableMovies();
	List<Cinema> getCinemaByMovie(Movie m);

}
