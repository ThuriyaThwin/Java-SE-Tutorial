package com.jdc.cinema.manager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jdc.cinema.entity.MovieType;
import com.jdc.cinema.entity.Price;
import com.jdc.cinema.entity.SeatType;
import com.jdc.cinema.main.AllertManager;
import com.jdc.cinema.main.UserInputException;
import com.jdc.cinema.service.MovieTypeService;
import com.jdc.cinema.service.PriceService;
import com.jdc.cinema.service.SeatTypeService;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PriceController implements Initializable{
	
	@FXML
	private ComboBox<MovieType> inMovieTypes;
	@FXML
	private ComboBox<MovieType> schMovieTypes;
	@FXML
	private ComboBox<SeatType> inSeatTypes;
	@FXML
	private ComboBox<SeatType> schSeatTypes;
	@FXML
	private TextField inPrice;
	
	@FXML
	private TableView<Price> priceTable;
	@FXML
	private TableColumn<Price, String> colMovie;
	@FXML
	private TableColumn<Price, String> colSeat;
	@FXML
	private TableColumn<Price, String> colPrice;
	
	private SeatTypeService seatService;
	private MovieTypeService movieService;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		seatService = SeatTypeService.getInstance();
		movieService = MovieTypeService.getInstance();
		
		final List<SeatType> seatData = seatService.getAll();
		final List<MovieType> movieData = movieService.getAll();
		
		inMovieTypes.getItems().addAll(movieData);
		inSeatTypes.getItems().addAll(seatData);
		schMovieTypes.getItems().addAll(movieData);
		schSeatTypes.getItems().addAll(seatData);
		
		colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		colMovie.setCellValueFactory(param -> {
			if(null != param) {
				Price price = param.getValue();
				MovieType type = movieService.findById(price.getMovieTypeId());
				if(null != type) {
					return new SimpleStringProperty(type.getType());
				}
			}
			return null;
		});
		colSeat.setCellValueFactory(param -> {
			if(null != param) {
				Price price = param.getValue();
				SeatType type = seatService.findById(price.getSeatTypeId());
				if(null != type) {
					return new SimpleStringProperty(type.getType());
				}
			}
			return null;
		});
		
		schMovieTypes.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
			search();
		});
		
		schSeatTypes.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
			search();
		});
		
		clearSearch();
	}
	
	public void clear() {
		inMovieTypes.getSelectionModel().clearSelection();
		inSeatTypes.getSelectionModel().clearSelection();
		inPrice.clear();
	}
	
	public void clearSearch() {
		schMovieTypes.getSelectionModel().clearSelection();
		schSeatTypes.getSelectionModel().clearSelection();
		
		search();
	}
	
	public void addPrice() {
		try {
			MovieType movieType = inMovieTypes.getValue();
			if(null == movieType) {
				throw new UserInputException("Please select Movie Type!");
			}
			
			SeatType seatType = inSeatTypes.getValue();
			if(null == seatType) {
				throw new UserInputException("Please select Seat Type!");
			}
			
			double priceData = getAndCheck(inPrice.getText());
			
			PriceService.getInstance().addNewPrice(movieType, seatType, priceData);
			
			clear();
			clearSearch();

		} catch(UserInputException e) {
			AllertManager.showAllert(e.getMessage());
		}
	}
	
	private double getAndCheck(String text) {
		try {
			return Double.parseDouble(text);
		} catch (Exception e) {
			throw new UserInputException("Please check price data!");
		}
	}

	private void search() {
		MovieType movieType = schMovieTypes.getValue();
		SeatType seatType = schSeatTypes.getValue();

		List<Price> prices = PriceService.getInstance().searchPrice(movieType, seatType);
		priceTable.getItems().clear();
		priceTable.getItems().addAll(prices);
	}

}
