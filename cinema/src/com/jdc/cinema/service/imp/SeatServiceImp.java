package com.jdc.cinema.service.imp;

import com.jdc.cinema.dao.Dao;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.entity.Seat;
import com.jdc.cinema.service.SeatService;

public class SeatServiceImp implements SeatService {
	
	private Dao<Seat> seatDao;
	
	private static SeatService SERVICE;
	
	public static SeatService getService() {
		if(null == SERVICE)
			SERVICE = new SeatServiceImp();
		
		return SERVICE;
	}
	
	private SeatServiceImp() {
		seatDao = Dao.getDao(Seat.class);
	}

	@Override
	public Seat get(long id) {
		return seatDao.findById(IdParam.getInstance("id", id));
	}

}
