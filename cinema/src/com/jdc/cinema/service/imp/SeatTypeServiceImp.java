package com.jdc.cinema.service.imp;

import java.util.List;

import com.jdc.cinema.dao.Dao;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.entity.SeatType;
import com.jdc.cinema.service.SeatTypeService;

public class SeatTypeServiceImp implements SeatTypeService {
	
	private static SeatTypeService SERVICE;
	
	public static SeatTypeService getService() {
		
		if(null == SERVICE)
			SERVICE = new SeatTypeServiceImp();
		
		return SERVICE;
	}
	
	private Dao<SeatType> dao;
	
	private SeatTypeServiceImp() {
		dao = Dao.getDao(SeatType.class);
	}

	@Override
	public List<SeatType> getAll() {
		return dao.getAll();
	}

	@Override
	public void addType(SeatType type) {
		dao.insert(type);
	}

	@Override
	public SeatType findById(long movieTypeId) {
		return dao.findById(IdParam.getInstance("id", movieTypeId));
	}

}
