package com.jdc.cinema.manager;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

import com.jdc.cinema.entity.Cinema;
import com.jdc.cinema.entity.Movie;
import com.jdc.cinema.service.ReportService;
import com.jdc.cinema.vh.ReportVO;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MonthlyReportController implements Initializable{
	
	@FXML private ComboBox<String> years;
	@FXML private ComboBox<String> months;
	@FXML private ComboBox<Cinema> cinemas;
	@FXML private ComboBox<Movie> movies;
	@FXML private TableView<ReportVO> table;
	
	@FXML private TableColumn<ReportVO, String> colCinema;
	@FXML private TableColumn<ReportVO, String> colMovie;
	@FXML private TableColumn<ReportVO, String> colDate;
	@FXML private TableColumn<ReportVO, String> colShowTime;
	@FXML private TableColumn<ReportVO, String> colSeatType;
	@FXML private TableColumn<ReportVO, String> colTotalSeat;
	@FXML private TableColumn<ReportVO, String> colSoldOut;
	@FXML private TableColumn<ReportVO, String> colTotal;
	
	private ReportService service;	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = ReportService.getInstance();
		
		// init table columns
		colCinema.setCellValueFactory(new PropertyValueFactory<>("cinema"));
		colMovie.setCellValueFactory(new PropertyValueFactory<>("movie"));
		colDate.setCellValueFactory(new PropertyValueFactory<>("showDate"));
		colShowTime.setCellValueFactory(new PropertyValueFactory<>("showTime"));
		colSeatType.setCellValueFactory(new PropertyValueFactory<>("seatType"));
		colTotalSeat.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));
		colSoldOut.setCellValueFactory(new PropertyValueFactory<>("soldOut"));
		colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		
		// set initial value to combo
		cinemas.getItems().addAll(service.getAllCinema());	
		years.getItems().addAll(service.getYears());
		months.getItems().addAll(service.getMonths());
		
		years.valueProperty().addListener((a, b, c) -> changeDate());
		months.valueProperty().addListener((a, b, c) -> changeDate());
		
		cinemas.valueProperty().addListener((a, b, c) -> search());
		movies.valueProperty().addListener((a, b, c) -> search());

		clear();
		
		search();
	}
	
	public void clear() {
		LocalDate now = LocalDate.now();
		years.setValue(String.valueOf(now.getYear()));
		months.setValue(String.valueOf(now.getMonth()));
		
		cinemas.getSelectionModel().clearSelection();
		movies.getSelectionModel().clearSelection();
		
	}
	
	public void search() {
		LocalDate dateFrom = LocalDate.of(Integer.valueOf(years.getValue()), 
				Month.valueOf(months.getValue()), 1);
		LocalDate dateTo = dateFrom.plusMonths(1).minusDays(1);
		
		table.getItems().clear();
		table.getItems().addAll(service.find(cinemas.getValue(), 
				movies.getValue(), dateFrom, dateTo));
	}
	
	public void changeDate() {
		if(null != years.getValue() && null != months.getValue()) {
			LocalDate dateFrom = LocalDate.of(Integer.valueOf(years.getValue()), 
					Month.valueOf(months.getValue()), 1);
			LocalDate dateTo = dateFrom.plusMonths(1).minusDays(1);
			movies.getItems().clear();
			movies.getItems().addAll(service.getAllMovies(dateFrom, dateTo));
			
			search();
		}
	}

}
