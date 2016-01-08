package com.jdc.cinema.service.imp;

import java.util.List;

import com.jdc.cinema.dao.Dao;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.entity.MovieType;
import com.jdc.cinema.service.MovieTypeService;

public class MovieTypeServiceImp implements MovieTypeService {
	
	private static MovieTypeService SERVICE;
	
	public static MovieTypeService getService() {
		if(null == SERVICE)
			SERVICE = new MovieTypeServiceImp();
		
		return SERVICE;
	}

	private Dao<MovieType> dao;

	private MovieTypeServiceImp() {
		dao = Dao.getDao(MovieType.class);
	}
	
	@Override
	public List<MovieType> getAll() {
		return dao.getAll();
	}

	@Override
	public void addType(MovieType type) {
		dao.insert(type);
	}

	@Override
	public MovieType findById(long id) {
		return dao.findById(IdParam.getInstance("id", id));
	}

}
