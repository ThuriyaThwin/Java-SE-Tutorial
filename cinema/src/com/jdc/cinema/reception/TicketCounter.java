package com.jdc.cinema.reception;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jdc.cinema.entity.Cinema;
import com.jdc.cinema.entity.Floor;
import com.jdc.cinema.entity.Movie;
import com.jdc.cinema.entity.MovieShowTime;
import com.jdc.cinema.entity.Seat;
import com.jdc.cinema.entity.ShowTime;
import com.jdc.cinema.entity.Ticket;
import com.jdc.cinema.service.TicketService;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TicketCounter implements Initializable, TotalTableHelper{
	
	@FXML private ComboBox<Movie> inMovies;
	@FXML private ComboBox<Cinema> inCinemas;
	@FXML private ComboBox<LocalDate> inShowDates;
	@FXML private ComboBox<ShowTime> inShowTimes;
	
	@FXML private Label outMovie;
	@FXML private Label outCinema;
	@FXML private Label outShowDate;
	@FXML private Label outShowTime;
	@FXML private Label outTotal;
	
	@FXML private Label outMessage;
	
	@FXML private TableView<SeatViewAdapter> table;
	@FXML private TableColumn<SeatViewAdapter, String> colFloor;
	@FXML private TableColumn<SeatViewAdapter, String> colSeat;
	@FXML private TableColumn<SeatViewAdapter, String> colPrice;
	@FXML private StackPane seatSelect;
	
	private List<Label> outputs;
	private List<ComboBox<?>> inputs;
	
	private TicketService ticketService;

	public static void showDialog() {
		try {
			Parent root = FXMLLoader.load
					(TicketCounter.class.getResource("TicketCounter.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			
			stage.setOnHiding(a -> {
				Platform.exit();
			});
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ticketService = TicketService.getInstance();
		inputs = Arrays.asList(inCinemas, inMovies, inShowDates, inShowTimes);
		outputs = Arrays.asList(outCinema, outMovie, outShowDate, outShowTime, outTotal, outMessage);
		
		clear();
		
		inMovies.getItems().addAll(ticketService.getAvaliableMovies());
		
		inMovies.valueProperty().addListener((a, b, c) -> {
			if(null != c) {
				inCinemas.getItems().clear();
				inShowDates.getItems().clear();
				inShowTimes.getItems().clear();
				
				outputs.forEach(out -> out.setText(""));
				table.getItems().clear();
				
				inCinemas.getItems().addAll(this.ticketService.getCinemaByMovie(c));
				outMovie.setText(c.getName());
				this.loadSeats();
			}
		});
		
		inCinemas.valueProperty().addListener((a, b, c) -> {
			if(null != c) {
				inShowDates.getItems().clear();
				inShowTimes.getItems().clear();
				
				this.outShowDate.setText("");
				this.outShowTime.setText("");

				table.getItems().clear();
				this.outTotal.setText("");
				this.outMessage.setText("");
				
				Movie mv = inMovies.getValue();

				inShowDates.getItems().addAll(this.ticketService.getShowDates(c, mv));
				inShowTimes.getItems().addAll(this.ticketService.getShowTimes());
				outCinema.setText(c.getName());
				this.loadSeats();
			}
		});
		
		inShowDates.valueProperty().addListener((a, b, c) -> {
			
			table.getItems().clear();
			this.outTotal.setText("");
			this.outMessage.setText("");

			if(null != c) {
				outShowDate.setText(c.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				this.loadSeats();
			}
		});
		
		inShowTimes.valueProperty().addListener((a, b, c) -> {
			
			table.getItems().clear();
			this.outTotal.setText("");
			this.outMessage.setText("");

			if(null != c) {
				outShowTime.setText(c.toString());
				this.loadSeats();
			}
		});
		
		// tables
		colFloor.setCellValueFactory(p -> {
			if(null != p) {
				SeatViewAdapter data = p.getValue();
				Floor f = data.getFloor();
				return new SimpleStringProperty(f.getFloorName());
			}
			return null;
		});
		
		colSeat.setCellValueFactory(p -> {
			if(null != p) {
				SeatViewAdapter data = p.getValue();
				Seat s = data.getSeat();
				return new SimpleStringProperty(s.getSeatName());
			}
			return null;
		});
		
		colPrice.setCellValueFactory(p -> {
			if(null != p) {
				SeatViewAdapter data = p.getValue();
				Ticket tk = data.getTicket();
				return new SimpleStringProperty(String.valueOf(tk.getPrice()));
			}
			return null;
		});
	}
	
	private void loadSeats() {
		seatSelect.getChildren().clear();
		
		Movie movie = inMovies.getValue();
		Cinema cinema = inCinemas.getValue();
		LocalDate showDate = inShowDates.getValue();
		ShowTime showTime = inShowTimes.getValue();
		
		if(null != movie && null != cinema && 
				null != showDate && null != showTime) {
			Date date = Date.from(showDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			MovieShowTime mst = new MovieShowTime(cinema, movie, date, showTime);
			CinemaView view = new CinemaView(mst, this);
			seatSelect.getChildren().add(view);
		}
	}
	
	public void clear() {
		table.getItems().clear();
		inputs.forEach(a -> a.getSelectionModel().clearSelection());
		outputs.forEach(a -> a.setText(""));
		seatSelect.getChildren().clear();
	}
	
	public void paid() {
		// sell tickets
		ticketService.sell(table.getItems());
		
		// clear
		this.clear();
	}

	@Override
	public TableView<SeatViewAdapter> table() {
		return this.table;
	}

	@Override
	public Label total() {
		return this.outTotal;
	}

}
