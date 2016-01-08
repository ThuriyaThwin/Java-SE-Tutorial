package com.jdc.cinema.reception;

import java.util.List;

import com.jdc.cinema.entity.Ticket;

public interface TicketSellable {

	List<Ticket> getSellTickets();
}
