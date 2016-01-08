package com.jdc.cinema.service;

import java.util.List;

import com.jdc.cinema.entity.Cinema;
import com.jdc.cinema.service.imp.CinemaServiceImp;

public interface CinemaService {
	
	public static CinemaService getInstance() {
		return CinemaServiceImp.getService();
	}
	
	List<Cinema> getAll();
	Cinema get(String id);
}
