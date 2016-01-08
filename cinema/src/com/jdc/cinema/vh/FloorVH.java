package com.jdc.cinema.vh;

import java.util.ArrayList;
import java.util.List;

import com.jdc.cinema.dao.Dao;
import com.jdc.cinema.entity.Floor;
import com.jdc.cinema.entity.SeatType;

public class FloorVH implements ViewHelper {
	
	private List<SeatTypeVH> svhList;
	private CinemaVH cvh;
	private Floor floor;
	private char rowNum = 'A';
	private Dao<Floor> floorDao;
	private Dao<SeatType> stDao;

	FloorVH(CinemaVH cvh, Floor floor) {
		super();
		floorDao = Dao.getDao(Floor.class);
		stDao = Dao.getDao(SeatType.class);
		
		this.cvh = cvh;
		this.floor = floor;
		svhList = new ArrayList<>();
		
		for(SeatType st : stDao.getAll()) {
			SeatTypeVH svh = new SeatTypeVH(this, st);
			svhList.add(svh);
		}
		
	}
	
	FloorVH(Floor floor) {
		super();
		this.floor = floor;
		svhList = new ArrayList<>();
		
		stDao = Dao.getDao(SeatType.class);
		
		for(SeatType st : stDao.getAll()) {
			SeatTypeVH svh = new SeatTypeVH(floor, st);
			svh.setFvh(this);
			svhList.add(svh);
		}
		
	}

	@Override
	public List<SeatTypeVH> getSeatTypeVHs() {
		return this.svhList;
	}

	@Override
	public void addToDB() {
		floor.setCinemaId(cvh.getCinema().getId());
		floorDao.insert(floor);
		
		for(SeatTypeVH svh : svhList) {
			svh.addToDB();
		}
	}


	public String getRowNumber() {
		String rowNumber = Character.toString(rowNum);
		rowNum ++;
		return rowNumber;
	}

	public List<SeatTypeVH> getSvhList() {
		return svhList;
	}

	public void setSvhList(List<SeatTypeVH> svhList) {
		this.svhList = svhList;
	}

	public CinemaVH getCvh() {
		return cvh;
	}

	public void setCvh(CinemaVH cvh) {
		this.cvh = cvh;
	}

	public Floor getFloor() {
		return floor;
	}

	public void setFloor(Floor floor) {
		this.floor = floor;
	}

	public char getRowNum() {
		return rowNum;
	}

	public void setRowNum(char rowNum) {
		this.rowNum = rowNum;
	}

}
