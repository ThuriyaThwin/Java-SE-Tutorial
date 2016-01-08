package com.jdc.cinema.service.imp;

import java.util.List;

import com.jdc.cinema.dao.Dao;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.entity.ShowTime;
import com.jdc.cinema.service.ShowTimeService;

public class ShowTimeServiceImp implements ShowTimeService {


	private Dao<ShowTime> dao;
	
	private static ShowTimeService SERVICE;
	
	public static ShowTimeService getService() {
		if(SERVICE == null)
			SERVICE = new ShowTimeServiceImp();
		
		return SERVICE;
	}
	
	private ShowTimeServiceImp() {
		dao = Dao.getDao(ShowTime.class);
	}
	
	@Override
	public void create(ShowTime st) {
		dao.insert(st);
	}

	@Override
	public List<ShowTime> getAll() {
		return dao.getAll();
	}

	@Override
	public ShowTime get(long id) {
		return dao.findById(IdParam.getInstance("id", id));
	}

}
