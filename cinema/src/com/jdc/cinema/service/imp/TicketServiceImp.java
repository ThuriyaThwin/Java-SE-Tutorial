package com.jdc.cinema.service.imp;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jdc.cinema.dao.Dao;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.entity.Cinema;
import com.jdc.cinema.entity.Floor;
import com.jdc.cinema.entity.Movie;
import com.jdc.cinema.entity.MovieShowTime;
import com.jdc.cinema.entity.Seat;
import com.jdc.cinema.entity.ShowTime;
import com.jdc.cinema.entity.Ticket;
import com.jdc.cinema.main.UserInputException;
import com.jdc.cinema.reception.SeatViewAdapter;
import com.jdc.cinema.service.MovieService;
import com.jdc.cinema.service.MovieShowTimeService;
import com.jdc.cinema.service.ShowTimeService;
import com.jdc.cinema.service.TicketService;

public class TicketServiceImp implements TicketService {
	
	private Dao<Ticket> ticketDao;
	private Dao<Floor> floorDao;
	
	private static TicketService SERVICE;
	
	public static TicketService getService() {
		if(null == SERVICE)
			SERVICE = new TicketServiceImp();
		
		return SERVICE;
	}
	
	private TicketServiceImp() {
		ticketDao = Dao.getDao(Ticket.class);
		floorDao = Dao.getDao(Floor.class);
	}

	@Override
	public Map<Floor, Map<String, List<Ticket>>> getTickets(MovieShowTime mst) {
		
		Map<Floor, Map<String, List<Ticket>>> result = new HashMap<>();
		
		List<Floor> floors = floorDao.getWhere("cinema_id = ?", Arrays.asList(mst.getCinemaId()));
		
		for(Floor f : floors) {
			Map<String, List<Ticket>> rows = getTickets(f, mst);
			if(null != rows) {
				result.put(f, rows);
			}
		}
		
		return result;
	}

	private Map<String, List<Ticket>> getTickets(Floor f, MovieShowTime mst) {
		Map<String, List<Ticket>> result = new HashMap<>();
		List<Seat> seats = Dao.getDao(Seat.class).getWhere("floor_id = ?", Arrays.asList(f.getId()));
		for(Seat s : seats) {
			if(result.get(s.getRowNumber()) == null) {
				result.put(s.getRowNumber(), new ArrayList<>());
			}
			
			Ticket tk = ticketDao.findById(IdParam.getInstance("seat_id", s.getId())
					.param("cinema_id", mst.getCinemaId())
					.param("movie_id", mst.getMovieId())
					.param("date", mst.getShowDate())
					.param("show_time_id", mst.getShowTimeId()));
			
			if(null != tk) {
				result.get(s.getRowNumber()).add(tk);
			}
		}
		
		return result;
	}

	@Override
	public List<Movie> getAvaliableMovies() {
		return MovieService.getInstance().getAvaliableMovies();
	}

	@Override
	public List<Cinema> getCinemaByMovie(Movie m) {
		return MovieService.getInstance().getCinemaByMovie(m);
	}

	@Override
	public List<LocalDate> getShowDates(Cinema c, Movie m) {
		return MovieShowTimeService.getInsatnce().getShowDates(c, m);
	}

	@Override
	public List<ShowTime> getShowTimes() {
		return ShowTimeService.getInstance().getAll();
	}

	@Override
	public void sell(List<SeatViewAdapter> seats) {
		try {
			seats.stream()
				.map(a -> a.getTicket())
				.forEach(this::sell);
		} catch(Exception e) {
			throw new UserInputException("Please select the seats!");
		}
	}
	
	private void sell(Ticket t) {
		String set = "status = ?";
		String where = "cinema_id = ? and movie_id = ? and date = ? "
				+ "and show_time_id = ? and seat_id = ?";
		
		Date date = new Date(t.getDate().getTime());
		List<Object> param = Arrays.asList(1, 
				t.getCinemaId(), t.getMovieId(), date, 
				t.getShowTimeId(), t.getSeatId());
		
		ticketDao.update(set, where, param);
	}


}
