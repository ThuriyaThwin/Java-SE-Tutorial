package com.jdc.cinema.manager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BiFunction;

import com.jdc.cinema.entity.Movie;
import com.jdc.cinema.entity.MovieType;
import com.jdc.cinema.main.AllertManager;
import com.jdc.cinema.main.UserInputException;
import com.jdc.cinema.service.MovieService;
import com.jdc.cinema.service.MovieTypeService;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AddMovieController implements Initializable {

	// form input
	@FXML private ComboBox<MovieType> inTypes;
	@FXML private TextField inName;
	@FXML private TextField inActors;
	@FXML private TextField inDirector;
	@FXML private TextArea inDescription;
	
	// search box
	@FXML private ComboBox<MovieType> schTypes;
	@FXML private TextField schName;
	
	// table
	@FXML private TableView<Movie> table;
	@FXML private TableColumn<Movie, String> colName;
	@FXML private TableColumn<Movie, String> colType;
	@FXML private TableColumn<Movie, String> colActors;
	
	private MovieService service;
	private MovieTypeService mtService;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = MovieService.getInstance();
		mtService = MovieTypeService.getInstance();
		
		final List<MovieType> types = mtService.getAll();
		inTypes.getItems().addAll(types);
		schTypes.getItems().addAll(types);
		
		BiFunction<Long, List<MovieType>, String> typeNameFinder = (id, list) -> {
			for(MovieType t : list) {
				if(t.getId() == id) {
					return t.getType();
				}
			}
			return null;
		};
		
		colType.setCellValueFactory(param -> {
			if(null != param) {
				Movie m = param.getValue();
				String typeName = typeNameFinder.apply(m.getMovieTypeId(), types);
				return new SimpleStringProperty(typeName);
			}
			return null;
		});
		
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colActors.setCellValueFactory(new PropertyValueFactory<>("actors"));
		
		schName.textProperty().addListener((a, b, c)->{search();});
		schTypes.valueProperty().addListener((a, b, c)-> {search();});
		
		clearSearch();
	}
	
	public void clear() {
		inActors.clear();
		inDescription.clear();
		inDirector.clear();
		inName.clear();
		inTypes.getSelectionModel().clearSelection();
	}
	
	public void clearSearch() {
		schName.clear();
		schTypes.getSelectionModel().clearSelection();
		search();
	}
	
	public void save() {
		try {
			Movie movie = getViewObject();
			service.create(movie);
			clear();
			clearSearch();
		} catch (UserInputException e) {
			AllertManager.showAllert(e.getMessage());
		}
	}
	
	private Movie getViewObject() {
		
		Movie m = new Movie();
		
		m.setName(checkAndGet(inName, "Please enter movie name!"));
		m.setActors(checkAndGet(inActors, "Please enter Actors!"));
		m.setDirector(checkAndGet(inDirector, "Plase enter Director!"));
		m.setDescription(inDescription.getText());
		
		MovieType mt = inTypes.getSelectionModel().getSelectedItem();
		if(null == mt) {
			throw new UserInputException("Please select movie type!!");
		}
		m.setMovieTypeId(mt.getId());
		return m;
	}

	private String checkAndGet(TextField text, String message) {
		String str = text.getText();
		if(str.isEmpty()) {
			throw new UserInputException(message);
		}
		return str;
	}

	private void search() {
		final List<Movie> list = service.search(
				schTypes.getSelectionModel().getSelectedItem(), 
				schName.getText());
		
		table.getItems().clear();
		table.getItems().addAll(list);
	}

}
