package com.jdc.cinema.manager;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.jdc.cinema.entity.MovieType;
import com.jdc.cinema.entity.SeatType;
import com.jdc.cinema.main.AllertManager;
import com.jdc.cinema.main.UserInputException;
import com.jdc.cinema.service.MovieTypeService;
import com.jdc.cinema.service.SeatTypeService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TypesController implements Initializable{

	@FXML
	private ComboBox<String> types;
	@FXML
	private TextField name;
	
	@FXML
	private TableView<SeatType> seatTypes;
	@FXML
	private TableView<MovieType> movieTypes;
	
	@FXML
	private TableColumn<SeatType, String> colSeatId;
	@FXML
	private TableColumn<SeatType, String> colSeatName;
	@FXML
	private TableColumn<MovieType, String> colMovieId;
	@FXML
	private TableColumn<MovieType, String> colMovieName;
	
	
	private SeatTypeService seatService;
	private MovieTypeService movieService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		seatService = SeatTypeService.getInstance();
		movieService = MovieTypeService.getInstance();
		
		types.getItems().addAll(Arrays.asList("Seat", "Movie"));
		
		// seat type table
		colSeatId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colSeatName.setCellValueFactory(new PropertyValueFactory<>("type"));
		
		// movie type table
		colMovieId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colMovieName.setCellValueFactory(new PropertyValueFactory<>("type"));
	
		loadTables();
	}
	
	public void clear() {
		types.getSelectionModel().clearSelection();
		name.clear();
		loadTables();
	}
	
	public void add() {
		
		try {
			if("Seat".equals(types.getValue())) {
				SeatType type = new SeatType();
				type.setType(checkAndGetString(name.getText()));
				seatService.addType(type);
			} else if("Movie".equals(types.getValue())){
				MovieType type = new MovieType();
				type.setType(checkAndGetString(name.getText()));
				movieService.addType(type);
			} else {
				throw new UserInputException("Please select type!");
			}
			
			clear();
		} catch (UserInputException e) {
			AllertManager.showAllert(e.getMessage());
		}
	}
	
	private String checkAndGetString(String str) {
		if(null == str || str.isEmpty()) {
			throw new UserInputException("Please enter type name!");
		}
		return str;
	}

	private void loadTables() {
		seatTypes.getItems().clear();
		movieTypes.getItems().clear();
		
		seatTypes.getItems().addAll(seatService.getAll());
		movieTypes.getItems().addAll(movieService.getAll());
	}

}
