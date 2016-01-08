package com.jdc.cinema.manager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jdc.cinema.entity.Cinema;
import com.jdc.cinema.main.AllertManager;
import com.jdc.cinema.main.UserInputException;
import com.jdc.cinema.service.CinemaService;
import com.jdc.cinema.vh.CinemaVH;
import com.jdc.cinema.vh.SeatTypeVH;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class CinemaController implements Initializable{
	
	// all floors
	@FXML private TableView<SeatTypeVH> cinemaTable;
	@FXML private TableColumn<SeatTypeVH, String> colCinemaName;
	@FXML private TableColumn<SeatTypeVH, String> colFloor;
	@FXML private TableColumn<SeatTypeVH, String> colSeatType;
	@FXML private TableColumn<SeatTypeVH, String> colRowsCount;
	
	@FXML private TextField cinemaName;
	@FXML private Slider seatPerRowSlider;
	@FXML private ToggleButton isSingleFloorButton;
	
	// cinema floors
	@FXML private TableView<SeatTypeVH> floorTable;
	@FXML private TableColumn<SeatTypeVH, String> colTmpFloor;
	@FXML private TableColumn<SeatTypeVH, String> colTmpSeatType;
	@FXML private TableColumn<SeatTypeVH, String> colTmpRowsCount;
	
	private CinemaVH viewHelper;
	private CinemaService cinemaService;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.cinemaService = CinemaService.getInstance();
		
		// cinema name value factory
		Callback<TableColumn.CellDataFeatures<SeatTypeVH,String>, 
				ObservableValue<String>> cNameFactory = (param) -> {
			if(null != param) {
				SeatTypeVH obj = param.getValue();
				String name = obj.getFvh().getCvh().getCinema().getName();
				return new SimpleStringProperty(name);
			}
			return null;
		};
		// floor name value factory
		Callback<TableColumn.CellDataFeatures<SeatTypeVH,String>, 
				ObservableValue<String>> fNameFactory = (param) -> {
			if(null != param) {
				SeatTypeVH obj = param.getValue();
				String name = obj.getFvh().getFloor().getFloorName();
				return new SimpleStringProperty(name);
			}
			return null;
		};
		// seat type value factory
		Callback<TableColumn.CellDataFeatures<SeatTypeVH,String>, 
				ObservableValue<String>> stFactory = (param) -> {
			if(null != param) {
				SeatTypeVH obj = param.getValue();
				String name = obj.getSeatType().getType();
				return new SimpleStringProperty(name);
			}
			return null;
		};
		
		// init tables
		colCinemaName.setCellValueFactory(cNameFactory);
		colFloor.setCellValueFactory(fNameFactory);
		colSeatType.setCellValueFactory(stFactory);
		colRowsCount.setCellValueFactory(new PropertyValueFactory<>("rowCnt"));

		colTmpFloor.setCellValueFactory(fNameFactory);
		colTmpSeatType.setCellValueFactory(stFactory);
		colTmpRowsCount.setCellValueFactory(new PropertyValueFactory<>("rowCnt"));
		
		floorTable.setEditable(true);
		colTmpRowsCount.setCellFactory(TextFieldTableCell.<SeatTypeVH>forTableColumn());

		colTmpRowsCount.setOnEditCommit(t -> {
			try {
				int position = t.getTablePosition().getRow();
				SeatTypeVH vh = t.getTableView().getItems().get(position);
				String newVal = t.getNewValue();
				int count = Integer.parseInt(newVal);
				vh.setRowCnt(t.getNewValue());
				vh.setRowCounts(count);
			} catch (Exception e) {
				AllertManager.showAllert("Please set rows count with digit!!");
			}
		});
		
		search();
	}
	
	public void generate() {
		try {
			// create Cinema VO
			viewHelper = CinemaVH.getViewInstance(getCinemaFromView(), 
					getFloorCount(), 
					((Double)seatPerRowSlider.getValue()).intValue());
			
			if(viewHelper.getRowPerSeat() == 0) {
				throw new UserInputException("Please set rows per seat over 0");
			}
			
			List<SeatTypeVH> seatTypeVOList = viewHelper.getSeatTypeVHs();
			floorTable.getItems().clear();
			floorTable.getItems().addAll(seatTypeVOList);
			
		} catch(UserInputException e) {
			AllertManager.showAllert(e.getMessage());
		}
	}
	
	private int getFloorCount() {
		if(isSingleFloorButton.isSelected()) {
			return 1;
		}
		return 2;
	}

	private Cinema getCinemaFromView() {
		Cinema cinema = new Cinema();
		String name = cinemaName.getText();
		if(name.isEmpty()) {
			throw new UserInputException("Please set Cinema Name!");
		}
		
		cinema.setName(name);
		return cinema;
	}

	public void addCinema() {
		// set cinema data to vo
		viewHelper.addToDB();
		
		clear();
		search();
	}
	
	public void clear() {
		cinemaName.clear();
		seatPerRowSlider.setValue(0);
		isSingleFloorButton.setSelected(true);
		floorTable.getItems().clear();
	}
	
	private void search() {
		cinemaTable.getItems().clear();
		
		List<Cinema> cinemas = cinemaService.getAll();
		
		for(Cinema c : cinemas) {
			CinemaVH vh = CinemaVH.getDBInstance(c);
			cinemaTable.getItems().addAll(vh.getSeatTypeVHs());
		}
	}

}
