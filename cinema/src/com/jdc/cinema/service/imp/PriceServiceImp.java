package com.jdc.cinema.service.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jdc.cinema.dao.Dao;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.entity.MovieType;
import com.jdc.cinema.entity.Price;
import com.jdc.cinema.entity.SeatType;
import com.jdc.cinema.service.PriceService;

public class PriceServiceImp implements PriceService {
	
	private Dao<Price> dao;
	
	private static PriceService SERVICE;
	
	public static PriceService getService() {
		
		if(null == SERVICE)
			SERVICE = new PriceServiceImp();
		
		return SERVICE;
	}
	
	private PriceServiceImp() {
		dao = Dao.getDao(Price.class);
	}

	@Override
	public synchronized List<Price> searchPrice(MovieType movieType, SeatType seatType) {
		
		StringBuffer where = new StringBuffer();
		List<Object> param = Collections.synchronizedList(new ArrayList<>());
		boolean firstTime = true;
		
		if(null != movieType) {
			where.append("movie_type_id = ?");
			param.add(movieType.getId());
			firstTime = false;
		}
		
		if(null != seatType) {
			
			if(!firstTime) {
				where.append(" and ");
			} 
			
			where.append("seat_type_id = ?");
			param.add(seatType.getId());
		}
		
		return dao.getWhere(where.toString(), param);
	}

	@Override
	public synchronized void addNewPrice(MovieType movieType, SeatType seatType, double priceData) {
		Price p = new Price();
		p.setMovieTypeId(movieType.getId());
		p.setSeatTypeId(seatType.getId());
		p.setPrice(priceData);
		dao.insert(p);
	}

	@Override
	public double getPrice(MovieType movieType, SeatType seatType) {
		Price price = dao.findById(IdParam.getInstance("movie_type_id", movieType.getId())
				.param("seat_type_id", seatType.getId()));
		if(null != price) {
			return price.getPrice();
		}
		return 0;
	}

	@Override
	public double getPrice(long movieType, long seatType) {
		Price price = dao.findById(IdParam.getInstance("movie_type_id", movieType)
				.param("seat_type_id", seatType));
		if(null != price) {
			return price.getPrice();
		}
		return 0;
	}

}
