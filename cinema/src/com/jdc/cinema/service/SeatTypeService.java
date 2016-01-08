package com.jdc.cinema.service;

import java.util.List;

import com.jdc.cinema.entity.SeatType;
import com.jdc.cinema.service.imp.SeatTypeServiceImp;

public interface SeatTypeService {

	public static SeatTypeService getInstance() {
		return SeatTypeServiceImp.getService();
	}

	List<SeatType> getAll();

	void addType(SeatType type);
	
	SeatType findById(long movieTypeId);

}
