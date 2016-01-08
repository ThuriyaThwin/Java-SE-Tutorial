package com.jdc.cinema.service.imp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.jdc.cinema.dao.Dao;
import com.jdc.cinema.entity.Cinema;
import com.jdc.cinema.entity.CinemaMovie;
import com.jdc.cinema.entity.Movie;
import com.jdc.cinema.entity.MovieShowTime;
import com.jdc.cinema.entity.Seat;
import com.jdc.cinema.entity.ShowTime;
import com.jdc.cinema.entity.Ticket;
import com.jdc.cinema.main.UserInputException;
import com.jdc.cinema.service.MovieShowTimeService;
import com.jdc.cinema.service.PriceService;

public class MovieShowTimeServiceImp implements MovieShowTimeService {
	
	private static MovieShowTimeService SERVICE;
	
	public static MovieShowTimeService getService() {
		
		if(null == SERVICE)
			SERVICE = new MovieShowTimeServiceImp();
		
		return SERVICE;
	}

	private Dao<CinemaMovie> cmDao;
	private Dao<MovieShowTime> msDao;
	private Dao<Ticket> tiDao;
	private Dao<Seat> seatDao;
	private Dao<ShowTime> showTimeDao;
	private PriceService priceService;
	
	
	private MovieShowTimeServiceImp() {
		cmDao = Dao.getDao(CinemaMovie.class);
		msDao = Dao.getDao(MovieShowTime.class);
		tiDao = Dao.getDao(Ticket.class);
		seatDao = Dao.getDao(Seat.class);
		showTimeDao = Dao.getDao(ShowTime.class);
		priceService = PriceService.getInstance();
	}
	
	@Override
	public void create(Movie movie, Cinema cinema, LocalDate dateFrom, LocalDate dateTo) {
		// check cinema movie
		this.checkCinema(cinema.getId(), dateFrom, dateTo);
		
		List<ShowTime> showTimes = showTimeDao.getAll();
		
		if(showTimes.size() == 0) {
			throw new UserInputException("Please define show time at first!!");
		}
		
		LocalDate businessDate = dateFrom;
		
		// create cinema movie
		CinemaMovie cm = new CinemaMovie(cinema, movie, getDate(dateFrom), getDate(dateTo));
		cmDao.insert(cm);

		while(businessDate.compareTo(dateTo) <= 0) {
			
			for(ShowTime showTime : showTimes) {
				// movie show time
				MovieShowTime mst = new MovieShowTime(cinema, movie, getDate(businessDate), showTime);
				msDao.insert(mst);
				
				for(Seat seat : seatDao.getAll()) {
					// create ticket
					Ticket tk = new Ticket(mst, seat, priceService.getPrice(movie.getMovieTypeId(), 
							seat.getSeatTypeId()));
					tiDao.insert(tk);
				}
			}
			businessDate = businessDate.plusDays(1);
		}
		
	}

	@Override
	public List<MovieShowTime> search(Cinema cinema, LocalDate date) {
		
		StringBuffer sb = new StringBuffer();
		List<Object> param = new ArrayList<>();
		
		if(null != cinema) {
			sb.append("cinema_id = ? ");
			param.add(cinema.getId());
		}
		
		if(null != date) {
			if(param.size() > 0) {
				sb.append("and ");
			}
			
			sb.append("show_date = ?");
			param.add(java.sql.Date.valueOf(date));
		}
		
		return msDao.getWhere(sb.toString(), param);
	}

	private Date getDate(LocalDate lcDate) {
		return Date.from(lcDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	private void checkCinema(String id, LocalDate dateFrom, LocalDate dateTo) {
		String where = "cinema_id = ? and show_date >= ? and show_date <= ?";
		List<Object> param = Arrays.asList(id, java.sql.Date.valueOf(dateFrom), java.sql.Date.valueOf(dateTo));
		
		int count = msDao.getWhereCount(where, param);
		if(count > 0) {
			throw new UserInputException("Cinema in that date has been already used!!");
		}
	}

	@Override
	public List<LocalDate> getShowDates(Cinema c, Movie m) {
		
		if(null != c && null != m) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Set<String> dates = new TreeSet<>();
			List<MovieShowTime> showTimes = Dao.getDao(MovieShowTime.class)
					.getWhere("cinema_id = ? and movie_id = ?", Arrays.asList(c.getId(), m.getId()));
			
			showTimes.forEach(a -> dates.add(df.format(a.getShowDate())));
			
			return dates.stream().map(a -> LocalDate.parse(a)).collect(Collectors.toList());
		}
		
		return null;
	}
	

}
