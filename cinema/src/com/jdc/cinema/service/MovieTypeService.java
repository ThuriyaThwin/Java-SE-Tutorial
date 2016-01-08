package com.jdc.cinema.service;

import java.util.List;

import com.jdc.cinema.entity.MovieType;
import com.jdc.cinema.service.imp.MovieTypeServiceImp;

public interface MovieTypeService {

	List<MovieType> getAll();
	void addType(MovieType type);
	MovieType findById(long idParam);
	
	public static MovieTypeService getInstance() {
		return MovieTypeServiceImp.getService();
	}


}
