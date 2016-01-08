package com.jdc.cinema.vh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jdc.cinema.dao.Dao;
import com.jdc.cinema.entity.Floor;
import com.jdc.cinema.entity.Seat;
import com.jdc.cinema.entity.SeatType;

public class SeatTypeVH implements ViewHelper{
	
	private FloorVH fvh;
	private SeatType seatType;
	
	private List<Seat> seats;
	private int rowCounts;
	private String rowCnt;

	private Dao<Seat> seatDao;
	
	SeatTypeVH(FloorVH fvh, SeatType seatType) {
		super();
		this.fvh = fvh;
		this.seatType = seatType;
		seatDao = Dao.getDao(Seat.class);
		seats = new ArrayList<>();
	}
	
	SeatTypeVH(Floor floor, SeatType seatType) {
		super();
		seatDao = Dao.getDao(Seat.class);
		this.seatType = seatType;
		
		this.seats = seatDao.getWhere("seat_type_id = ? and floor_id = ? ", Arrays.asList(seatType.getId(), floor.getId()));
		
		generateRowCounts();
	}

	private void generateRowCounts() {
		Set<String> rowNames = new HashSet<>();
		for(Seat s : this.seats) {
			rowNames.add(s.getRowNumber());
		}
		
		this.rowCounts = rowNames.size();
		this.rowCnt = String.valueOf(rowCounts);
	}

	@Override
	public List<SeatTypeVH> getSeatTypeVHs() {
		return Arrays.asList(this);
	}

	@Override
	public void addToDB() {
		for(Seat s : seats) {
			s.setFloorId(fvh.getFloor().getId());
			seatDao.insert(s);
		}
	}

	public FloorVH getFvh() {
		return fvh;
	}

	public void setFvh(FloorVH fvh) {
		this.fvh = fvh;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public int getRowCounts() {
		return rowCounts;
	}

	public void setRowCounts(int rowCounts) {
		this.rowCounts = rowCounts;
		seats.clear();
		
		for(int i=1; i <= rowCounts; i++) {
			String rowName = fvh.getRowNumber();
			
			for(int j=1; j <= fvh.getCvh().getRowPerSeat(); j++) {
				Seat s = new Seat();
				s.setNumber(j);
				s.setSeatTypeId(seatType.getId());
				s.setRowNumber(rowName);
				
				seats.add(s);
			}
		}
	}

	public SeatType getSeatType() {
		return seatType;
	}

	public void setSeatType(SeatType seatType) {
		this.seatType = seatType;
	}

	public String getRowCnt() {
		return rowCnt;
	}

	public void setRowCnt(String rowCnt) {
		this.rowCnt = rowCnt;
	}
	
	

}
