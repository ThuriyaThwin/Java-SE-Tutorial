package com.jdc.cinema.service.imp;

import java.util.List;

import com.jdc.cinema.dao.Dao;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.entity.Cinema;
import com.jdc.cinema.service.CinemaService;

public class CinemaServiceImp implements CinemaService {
	
	private static CinemaService SERVICE;
	
	private Dao<Cinema> dao;

	@Override
	public List<Cinema> getAll() {
		return dao.getAll();
	}
	
	private CinemaServiceImp() {
		dao = Dao.getDao(Cinema.class);
	}

	@Override
	public Cinema get(String id) {
		return dao.findById(IdParam.getInstance("id", id));
	}
	
	public static CinemaService getService() {
		
		if(null == SERVICE)
			SERVICE = new CinemaServiceImp();
		return SERVICE;
	}
}
