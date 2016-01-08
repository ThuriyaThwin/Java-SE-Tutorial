package com.jdc.cinema.service.imp;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jdc.cinema.dao.Dao;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.entity.Cinema;
import com.jdc.cinema.entity.CinemaMovie;
import com.jdc.cinema.entity.Movie;
import com.jdc.cinema.entity.MovieType;
import com.jdc.cinema.service.MovieService;

public class MovieServiceImp implements MovieService {
	
	private Dao<Movie> dao;
	
	private static MovieService SERVICE;
	
	public static MovieService getService() {
		
		if(null == SERVICE)
			SERVICE = new MovieServiceImp();
		
		return SERVICE;
	}
	
	private MovieServiceImp() {
		dao = Dao.getDao(Movie.class);
	}

	@Override
	public void create(Movie m) {
		dao.insert(m);
	}

	@Override
	public List<Movie> getAll() {
		return dao.getAll();
	}

	@Override
	public List<Movie> search(MovieType type, String name) {
		StringBuffer where = new StringBuffer();
		List<Object> param = new ArrayList<>();
		
		if(null != type) {
			where.append("movie_type_id = ? ");
			param.add(type.getId());
		}
		
		if(null != name && !name.isEmpty()) {
			if(param.size() > 0) {
				where.append("and ");
			}
			
			where.append("name like ?");
			param.add(name + "%");
		}
		
		return dao.getWhere(where.toString(), param);
	}

	@Override
	public Movie get(long id) {
		return dao.findById(IdParam.getInstance("id", id));
	}

	@Override
	public List<Movie> getAvaliableMovies() {
		Date now = Date.valueOf(LocalDate.now());
		List<CinemaMovie> cms = Dao.getDao(CinemaMovie.class).getWhere("date_to >= ?", Arrays.asList(now));
		
		Set<Long> ids = new HashSet<>();
		
		cms.forEach(a -> ids.add(a.getMovieId()));
		
		List<Movie> result = new ArrayList<>();
		ids.forEach(a -> result.add(dao.findById(IdParam.getInstance("id", a))));
		
		return result;
	}
	
	@Override
	public List<Cinema> getCinemaByMovie(Movie m) {
		
		Date now = Date.valueOf(LocalDate.now());
		List<CinemaMovie> cms = Dao.getDao(CinemaMovie.class).getWhere("movie_id = ? and date_to >= ?", 
				Arrays.asList(m.getId(), now));
		
		Set<String> ids = new HashSet<>();
		
		cms.forEach(a -> ids.add(a.getCinemaId()));
		
		List<Cinema> result = new ArrayList<>();
		ids.forEach(a -> result.add(Dao.getDao(Cinema.class).findById(IdParam.getInstance("id", a))));
		
		return result;
	}



}
