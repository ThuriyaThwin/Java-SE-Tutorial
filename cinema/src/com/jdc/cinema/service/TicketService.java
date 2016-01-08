package com.jdc.cinema.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.jdc.cinema.entity.Cinema;
import com.jdc.cinema.entity.Floor;
import com.jdc.cinema.entity.Movie;
import com.jdc.cinema.entity.MovieShowTime;
import com.jdc.cinema.entity.ShowTime;
import com.jdc.cinema.entity.Ticket;
import com.jdc.cinema.reception.SeatViewAdapter;
import com.jdc.cinema.service.imp.TicketServiceImp;

public interface TicketService{
	
	public static TicketService getInstance() {
		return TicketServiceImp.getService();
	}
	
	List<Movie> getAvaliableMovies();
	List<Cinema> getCinemaByMovie(Movie m);
	List<LocalDate> getShowDates(Cinema c, Movie m);
	List<ShowTime> getShowTimes();
	
	Map<Floor, Map<String, List<Ticket>>> getTickets(MovieShowTime movieShowtime);
	
	void sell(List<SeatViewAdapter> seats);

}
