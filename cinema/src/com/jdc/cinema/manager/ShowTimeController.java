package com.jdc.cinema.manager;

import java.net.URL;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.jdc.cinema.entity.ShowTime;
import com.jdc.cinema.service.ShowTimeService;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ShowTimeController implements Initializable {
	
	@FXML
	private TableView<ShowTime> showTimeTable;
	@FXML
	private TableColumn<ShowTime, String> colId;
	@FXML
	private TableColumn<ShowTime, String> colName;
	@FXML
	private TableColumn<ShowTime, String> colTimeFrom;
	@FXML
	private TableColumn<ShowTime, String> colTimeTo;

	@FXML
	private ComboBox<String> selHourTo;
	@FXML
	private ComboBox<String> selMinTo;
	@FXML
	private ComboBox<String> selHourFrom;
	@FXML
	private ComboBox<String> selMinFrom;
	
	private static final List<String> hours = Arrays.asList("09", "12", "16", "20", "21");
	private static final List<String> mins = Arrays.asList("00", "15", "30", "45");
	
	private CinemaTime timeFrom;
	private CinemaTime timeTo;
	
	private ShowTimeService service;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = ShowTimeService.getInstance();
		
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(p -> {
			if(null != p) {
				ShowTime st = p.getValue();
				String value = String.format("%s to %s", st.getTimeFrom(), st.getTimeTo());
				return new SimpleStringProperty(value);
			}
			return null;
		});
		colTimeFrom.setCellValueFactory(new PropertyValueFactory<>("timeFrom"));
		colTimeTo.setCellValueFactory(new PropertyValueFactory<>("timeTo"));
		
		selHourFrom.getItems().addAll(hours);
		selHourTo.getItems().addAll(hours);
		selMinFrom.getItems().addAll(mins);
		selMinTo.getItems().addAll(mins);
		
		timeFrom = new CinemaTime(selHourFrom, selMinFrom);
		timeTo = new CinemaTime(selHourTo, selMinTo);

		showTimeTable.getItems().clear();
		showTimeTable.getItems().addAll(service.getAll());
	}
	
	public void add() {
		ShowTime st = new ShowTime();
		st.setStatus(0);
		st.setTimeFrom(timeFrom.getTime());
		st.setTimeTo(timeTo.getTime());
		
		service.create(st);
		
		showTimeTable.getItems().clear();
		showTimeTable.getItems().addAll(service.getAll());
	}
	
	private class CinemaTime {
		private ComboBox<String> hour;
		private ComboBox<String> min;
		
		CinemaTime(ComboBox<String> hour, ComboBox<String> min) {
			super();
			this.hour = hour;
			this.min = min;
		}
		
		Time getTime() {
			return Time.valueOf(String.format("%s:%s:00", 
					hour.getSelectionModel().getSelectedItem(), 
					min.getSelectionModel().getSelectedItem()));
		}
		
	}



}
