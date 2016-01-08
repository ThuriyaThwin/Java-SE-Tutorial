package com.jdc.cinema.manager;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.jdc.cinema.entity.Cinema;
import com.jdc.cinema.entity.Movie;
import com.jdc.cinema.entity.MovieShowTime;
import com.jdc.cinema.entity.ShowTime;
import com.jdc.cinema.main.AllertManager;
import com.jdc.cinema.main.UserInputException;
import com.jdc.cinema.service.CinemaService;
import com.jdc.cinema.service.MovieService;
import com.jdc.cinema.service.MovieShowTimeService;
import com.jdc.cinema.service.ShowTimeService;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MovieShowTimeController implements Initializable{
	
	@FXML private TextField mvName;
	@FXML private ListView<Movie> inMovies;
	@FXML private ComboBox<Cinema> inCinema;
	@FXML private DatePicker inStartDate;
	@FXML private DatePicker inEndDate;

	@FXML private TableView<MovieShowTime> table;
	@FXML private TableColumn<MovieShowTime, String> colMovie;
	@FXML private TableColumn<MovieShowTime, String> colCinema;
	@FXML private TableColumn<MovieShowTime, String> colShowDate;
	@FXML private TableColumn<MovieShowTime, String> colShowTime;
	
	@FXML private ComboBox<Cinema> schCinema;
	@FXML private DatePicker schDate;
	
	private MovieShowTimeService msService;
	private MovieService movieService;
	private CinemaService cinemaService;
	private ShowTimeService showTimeService;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		msService = MovieShowTimeService.getInsatnce();
		movieService = MovieService.getInstance();
		cinemaService = CinemaService.getInstance();
		showTimeService = ShowTimeService.getInstance();
		
		final List<Cinema> cList = cinemaService.getAll();
		inCinema.getItems().addAll(cList);
		schCinema.getItems().addAll(cList);
		
		this.searchMovies();
		mvName.textProperty().addListener((a, b, c) -> {
			searchMovies();
		});
		
		schCinema.valueProperty().addListener((a, b, c) -> {
			search();
		});
		
		schDate.valueProperty().addListener((a, b, c) -> {
			search();
		});
		
		colCinema.setCellValueFactory(p -> {
			if(null != p) {
				MovieShowTime obj = p.getValue();
				Cinema c = cinemaService.get(obj.getCinemaId());
				return new SimpleStringProperty(c.getName());
			}
			return null;
		});
		
		colMovie.setCellValueFactory(p -> {
			if(null != p) {
				MovieShowTime obj = p.getValue();
				Movie mov = movieService.get(obj.getMovieId());
				return new SimpleStringProperty(mov.getName());
			}
			return null;
		});
		
		colShowDate.setCellValueFactory(p -> {
			if(null != p) {
				MovieShowTime obj = p.getValue();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				return new SimpleStringProperty(df.format(obj.getShowDate()));
			}
			return null;
		});
		
		colShowTime.setCellValueFactory(p -> {
			if(null != p) {
				MovieShowTime obj = p.getValue();
				ShowTime st = showTimeService.get(obj.getShowTimeId());
				return new SimpleStringProperty(st.toString());
			}
			return null;
		});
		clear();
		clearSearch();
	}
	
	private void searchMovies() {
		inMovies.getItems().clear();
		inMovies.getItems().addAll(movieService.search(null, mvName.getText()));
	}

	public void save() {
		
		try {
			msService.create(check(inMovies.getSelectionModel().getSelectedItem(), "Movie"), 
					check(inCinema.getValue(), "Cinema"), 
					check(inStartDate.getValue(), "Start Date"), 
					check(inEndDate.getValue(), "End Date"));
			
			this.clearSearch();
		} catch (UserInputException e) {
			AllertManager.showAllert(e.getMessage());
		}
		
	}
	
	public void search() {
		
		try {
			List<MovieShowTime> list = msService.search(schCinema.getValue(), schDate.getValue());
			table.getItems().clear();
			table.getItems().addAll(list);
		} catch (UserInputException e) {
			AllertManager.showAllert(e.getMessage());
		}
	}
	
	public void clearSearch() {
		schCinema.getSelectionModel().clearSelection();
		schDate.setValue(null);
		
		search();
	}
	
	public void clear() {
		inStartDate.setValue(LocalDate.now().plusDays(7));
		inEndDate.setValue(LocalDate.now().plusDays(15));
		inCinema.getSelectionModel().clearSelection();
		inMovies.getSelectionModel().clearSelection();
	}

	private<T> T check(T item, String string) {
		String message = String.format("Please define %s!", string);
		if(null == item) {
			throw new UserInputException(message);
		}
		return item;
	}

}
