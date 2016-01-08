package com.jdc.cinema.reception;

import java.util.function.ToDoubleFunction;

import com.jdc.cinema.entity.Ticket;

import javafx.scene.control.Button;

public class SeatView extends Button {
	
	private SeatViewAdapter data;
	private Ticket ticket;
	private boolean sell;
	
	public SeatView(SeatViewAdapter data, TotalTableHelper helper) {
		
		this.data = data;
		this.ticket = data.getTicket();
		
		setOnAction(a -> {
			if(sell) {
				sell = false;
				getStyleClass().remove("btn-ticket-sell");
				helper.table().getItems().remove(data);
				getStyleClass().add(data.getSeatType().getType().replaceAll(" ", ""));
			} else {
				sell = true;
				getStyleClass().remove(data.getSeatType().getType().replaceAll(" ", ""));
				getStyleClass().add("btn-ticket-sell");
				helper.table().getItems().add(data);
			}
			
			ToDoubleFunction<SeatViewAdapter> toDouble = b -> b.getTicket().getPrice();
			
			double total = helper.table().getItems().stream().mapToDouble(toDouble).sum();
			helper.total().setText(String.valueOf(total));
		});
		
		// set text
		setText(data.getSeat().getSeatName());
		
		// set status
		setDisable((ticket.getStatus() == 0)?false: true);
		
		// set background color
		getStyleClass().add(data.getSeatType().getType().replaceAll(" ", ""));
		getStyleClass().add("seat-view");
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public boolean isSell() {
		return sell;
	}

	public void setSell(boolean sell) {
		this.sell = sell;
	}

	public SeatViewAdapter getData() {
		return data;
	}

	public void setData(SeatViewAdapter data) {
		this.data = data;
	}
}
