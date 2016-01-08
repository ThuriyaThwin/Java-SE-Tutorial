package com.jdc.cinema.service;

import java.time.LocalDate;
import java.util.List;

import com.jdc.cinema.entity.Cinema;
import com.jdc.cinema.entity.Movie;
import com.jdc.cinema.service.imp.ReportServiceImp;
import com.jdc.cinema.vh.ReportVO;

public interface ReportService {

	public static ReportService getInstance() {
		return ReportServiceImp.getService();
	}
	
	List<String> getYears();
	List<String> getMonths();
	List<Cinema> getAllCinema();
	List<ReportVO> find(Cinema cinmea, Movie movie, LocalDate from, LocalDate dateTo);
	List<Movie> getAllMovies(LocalDate target);
	List<Movie> getAllMovies(LocalDate from, LocalDate dateTo);
}
