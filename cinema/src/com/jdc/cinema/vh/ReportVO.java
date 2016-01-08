package com.jdc.cinema.vh;

public class ReportVO {
	
	private String cinema;
	private String movie;
	private String showDate;
	private String showTime;
	private String seatType;
	private int totalSeats;
	private int soldOut;
	private double total;
	
	public ReportVO() {}
	
	public ReportVO(String key) {
		String [] arrray = key.split("\t");
		cinema = arrray[0];
		movie = arrray[1];
		showDate = arrray[2];
		showTime = arrray[3];
		seatType = arrray[4];
	}
	
	public String getCinema() {
		return cinema;
	}
	public void setCinema(String cinema) {
		this.cinema = cinema;
	}
	public String getMovie() {
		return movie;
	}
	public void setMovie(String movie) {
		this.movie = movie;
	}
	public String getShowTime() {
		return showTime;
	}
	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
	public String getSeatType() {
		return seatType;
	}
	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}
	public int getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}
	public int getSoldOut() {
		return soldOut;
	}
	public void setSoldOut(int soldOut) {
		this.soldOut = soldOut;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}

	public String getShowDate() {
		return showDate;
	}
	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

}
