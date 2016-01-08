package com.jdc.cinema.service;

import com.jdc.cinema.entity.Seat;
import com.jdc.cinema.service.imp.SeatServiceImp;

public interface SeatService {
	
	public static SeatService getInstance() {
		return SeatServiceImp.getService();
	}
	
	Seat get(long id);
}
