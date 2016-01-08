package com.jdc.cinema.vh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jdc.cinema.dao.Dao;
import com.jdc.cinema.entity.Cinema;
import com.jdc.cinema.entity.Floor;

public class CinemaVH implements ViewHelper {
	
	private List<FloorVH> fvhList;
	private Cinema cinema;
	private int floorCounts;
	private int rowPerSeat;
	
	private Dao<Cinema> cinemaDao;
	
	/**
	 * Factory method for View Object
	 * @param cinema
	 * @param floorCounts
	 * @param rowPerSeat
	 * @return
	 */
	public static CinemaVH getViewInstance(Cinema cinema, int floorCounts, int rowPerSeat) {
		return new CinemaVH(cinema, floorCounts, rowPerSeat);
	}
	
	/**
	 * Factory method for DB
	 * @param c
	 * @return
	 */
	public static CinemaVH getDBInstance(Cinema c) {
		return new CinemaVH(c);
	}
	
	private CinemaVH(Cinema cinema, int floorCounts, int rowPerSeat) {
		super();
		cinemaDao = Dao.getDao(Cinema.class);
		fvhList = new ArrayList<>();
		
		this.cinema = cinema;
		this.floorCounts = floorCounts;
		this.rowPerSeat = rowPerSeat;
		
		for(int i=1; i <= floorCounts; i++) {
			String floorName = String.format("Floor %d", i);
			Floor f = new Floor();
			f.setFloorName(floorName);
			
			FloorVH fvh = new FloorVH(this, f);
			this.fvhList.add(fvh);
		}
		
	}
	
	private CinemaVH(Cinema cinema) {
		super();
		this.cinema = cinema;
		this.fvhList = new ArrayList<>();
		List<Floor> floors = Dao.getDao(Floor.class).getWhere("cinema_id = ?", Arrays.asList(cinema.getId()));
		this.floorCounts = floors.size();
		
		for(Floor f : floors) {
			FloorVH fvh = new FloorVH(f);
			fvh.setCvh(this);
			fvhList.add(fvh);
		}
	}

	@Override
	public List<SeatTypeVH> getSeatTypeVHs() {
		List<SeatTypeVH> list = new ArrayList<>();
		
		for(FloorVH fvh : fvhList) {
			list.addAll(fvh.getSeatTypeVHs());
		}
		
		return list;
	}

	@Override
	public void addToDB() {
		if(cinema.getId() == null) {
			cinema.setId(cinema.getName().trim().toLowerCase().replaceAll(" ", ""));
			// add cinema
			cinemaDao.insert(cinema);
			// loop floor view helper
			for(FloorVH fvh : fvhList) {
				fvh.addToDB();
			}
		}
	}


	public List<FloorVH> getFvhList() {
		return fvhList;
	}

	public void setFvhList(List<FloorVH> fvhList) {
		this.fvhList = fvhList;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public int getFloorCounts() {
		return floorCounts;
	}

	public void setFloorCounts(int floorCounts) {
		this.floorCounts = floorCounts;
	}

	public int getRowPerSeat() {
		return rowPerSeat;
	}

	public void setRowPerSeat(int rowPerSeat) {
		this.rowPerSeat = rowPerSeat;
	}
	
}
