package com.jdc.cinema.manager;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jdc.cinema.entity.Cinema;
import com.jdc.cinema.entity.Movie;
import com.jdc.cinema.service.ReportService;
import com.jdc.cinema.vh.ReportVO;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DailyReportController implements Initializable {
	
	@FXML private DatePicker date;
	@FXML private ComboBox<Cinema> cinemas;
	@FXML private ComboBox<Movie> movies;
	@FXML private TableView<ReportVO> table;
	
	@FXML private TableColumn<ReportVO, String> colCinema;
	@FXML private TableColumn<ReportVO, String> colMovie;
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
		colShowTime.setCellValueFactory(new PropertyValueFactory<>("showTime"));
		colSeatType.setCellValueFactory(new PropertyValueFactory<>("seatType"));
		colTotalSeat.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));
		colSoldOut.setCellValueFactory(new PropertyValueFactory<>("soldOut"));
		colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		
		// set initial value to combo
		cinemas.getItems().addAll(service.getAllCinema());
		date.setValue(LocalDate.now().minusDays(1));
		
		// set value change listener for inputs
		date.valueProperty().addListener((a, b, c) -> changeDate());

		cinemas.valueProperty().addListener((a, b, c) -> load());
		movies.valueProperty().addListener((a, b, c) -> load());

		// load data
		load();
	}
	
	public void clear() {
		cinemas.getSelectionModel().clearSelection();
		movies.getSelectionModel().clearSelection();
		date.setValue(LocalDate.now());
	}
	
	private void changeDate() {
		movies.getItems().clear();
		movies.getItems().addAll(service.getAllMovies(date.getValue()));
		
		load();
	}
	
	private void load() {
		Cinema cinema = cinemas.getValue();
		Movie movie = movies.getValue();
		LocalDate target = date.getValue();
		
		table.getItems().clear();
		table.getItems().addAll(service.find(cinema, movie, target, target));
	}

}
