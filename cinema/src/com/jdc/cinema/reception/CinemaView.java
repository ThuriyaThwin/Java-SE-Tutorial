package com.jdc.cinema.reception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jdc.cinema.entity.Floor;
import com.jdc.cinema.entity.MovieShowTime;
import com.jdc.cinema.entity.Ticket;
import com.jdc.cinema.service.TicketService;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class CinemaView extends TabPane implements TicketSellable{
	
	private MovieShowTime movieShowTime;
	private TicketService ticketService;

	public MovieShowTime getMovieShowTime() {
		return movieShowTime;
	}

	public void setMovieShowTime(MovieShowTime movieShowTime) {
		this.movieShowTime = movieShowTime;
	}

	public CinemaView(MovieShowTime movieShowTime, TotalTableHelper helper) {
		super();
		ticketService = TicketService.getInstance();
		this.movieShowTime = movieShowTime;
		
		Map<Floor, Map<String, List<Ticket>>> data = ticketService.getTickets(movieShowTime);
		
		for(Floor f : data.keySet()) {
			Map<String, List<Ticket>> rows = data.get(f);
			FloorView floorView = new FloorView(f, rows, helper);
			getTabs().add(floorView);
		}
	}

	@Override
	public List<Ticket> getSellTickets() {
		List<Ticket> tickets = new ArrayList<>();
		for(Tab tab : getTabs()) {
			FloorView fv = (FloorView)tab;
			tickets.addAll(fv.getSellTickets());
		}
		return tickets;
	}

}
