package com.jdc.cinema.service;

import java.util.List;

import com.jdc.cinema.entity.MovieType;
import com.jdc.cinema.entity.Price;
import com.jdc.cinema.entity.SeatType;
import com.jdc.cinema.service.imp.PriceServiceImp;

public interface PriceService {

	List<Price> searchPrice(MovieType movieType, SeatType seatType);

	void addNewPrice(MovieType movieType, SeatType seatType, double priceData);
	
	double getPrice(MovieType movieType, SeatType seatType);
	double getPrice(long movieType, long seatType);
	
	public static PriceService getInstance() {
		return PriceServiceImp.getService();
	}

}
