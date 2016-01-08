package com.jdc.cinema.service;

import java.util.List;

import com.jdc.cinema.entity.ShowTime;
import com.jdc.cinema.service.imp.ShowTimeServiceImp;

public interface ShowTimeService {
	
	public static ShowTimeService getInstance() {
		return ShowTimeServiceImp.getService();
	}
	
	void create(ShowTime st);
	List<ShowTime> getAll();
	ShowTime get(long id);
}
