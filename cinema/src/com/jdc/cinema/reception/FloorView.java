package com.jdc.cinema.reception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jdc.cinema.entity.Floor;
import com.jdc.cinema.entity.Ticket;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FloorView extends Tab implements TicketSellable{

	private Floor floor;
	private Map<String, List<Ticket>> floorData;
	private TotalTableHelper helper;
	
	public FloorView(Floor floor, Map<String, List<Ticket>> floorData, TotalTableHelper helper) {
		super();
		this.floor = floor;
		this.floorData = floorData;
		this.helper = helper;
		setText(floor.getFloorName());
		setContent(getVBox());
		
	}

	private VBox getVBox() {
		VBox box = new VBox(10);
		box.getStyleClass().add("floor-view");
		
		for(List<Ticket> rowData : floorData.values()) {
			box.getChildren().add(getRow(rowData));
		}
		
		return box;
	}

	private HBox getRow(List<Ticket> rowData) {
		HBox row = new HBox(10);
		for(Ticket tk : rowData) {
			row.getChildren().add(new SeatView(new SeatViewAdapter(tk), helper));
		}
		return row;
	}

	public Floor getFloor() {
		return floor;
	}

	public void setFloor(Floor floor) {
		this.floor = floor;
	}

	@Override
	public List<Ticket> getSellTickets() {
		List<Ticket> tickets = new ArrayList<>();
		
		VBox rows = (VBox) getContent();
		for(Node row : rows.getChildren()) {
			HBox hb = (HBox) row;
			
			for(Node cell : hb.getChildren()) {
				SeatView seat = (SeatView) cell;
				if(seat.isSell()) {
					tickets.add(seat.getTicket());
				}
			}
		}
		return tickets;
	}

	
}
