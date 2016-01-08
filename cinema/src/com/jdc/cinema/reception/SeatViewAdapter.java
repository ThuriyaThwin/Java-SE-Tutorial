package com.jdc.cinema.reception;

import com.jdc.cinema.dao.Dao;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.entity.Floor;
import com.jdc.cinema.entity.Movie;
import com.jdc.cinema.entity.Seat;
import com.jdc.cinema.entity.SeatType;
import com.jdc.cinema.entity.Ticket;
import com.jdc.cinema.service.MovieService;
import com.jdc.cinema.service.SeatService;
import com.jdc.cinema.service.SeatTypeService;

public class SeatViewAdapter  {
	
	private Ticket ticket;
	
	private Movie movie;
	private Seat seat;
	
	private SeatType seatType;
	private Floor floor;

	public SeatViewAdapter(Ticket ticket) {
		super();
		this.ticket = ticket;
		movie = MovieService.getInstance().get(ticket.getMovieId());
		seat = SeatService.getInstance().get(ticket.getSeatId());
		floor = Dao.getDao(Floor.class).findById(IdParam.getInstance("id", seat.getFloorId()));
		seatType = SeatTypeService.getInstance().findById(seat.getSeatTypeId());
	}

	public Ticket getTicket() {
		return ticket;
	}

	public Movie getMovie() {
		return movie;
	}

	public Seat getSeat() {
		return seat;
	}

	public SeatType getSeatType() {
		return seatType;
	}

	public Floor getFloor() {
		return floor;
	}

}
