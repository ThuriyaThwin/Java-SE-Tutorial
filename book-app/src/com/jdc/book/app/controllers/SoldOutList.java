package com.jdc.book.app.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jdc.book.app.models.BookModel;
import com.jdc.book.app.models.CheckOutModel;
import com.jdc.book.app.models.CustomerModel;
import com.jdc.book.app.models.imp.BookModelImp;
import com.jdc.book.app.models.imp.CheckOutModelImp;
import com.jdc.book.app.models.imp.CustomerModelImp;
import com.jdc.book.db.entity.Book;
import com.jdc.book.db.entity.Customer;
import com.jdc.book.db.entity.CustomerBook;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SoldOutList implements Initializable {

	@FXML
	private TableView<CustomerBook> table;
	@FXML
	private TableColumn<CustomerBook, String> colCustomer;
	@FXML
	private TableColumn<CustomerBook, String> colBook;
	@FXML
	private TableColumn<CustomerBook, String> colDateTime;
	@FXML
	private TableColumn<CustomerBook, String> colCount;
	@FXML
	private TableColumn<CustomerBook, String> colPrice;
	@FXML
	private TableColumn<CustomerBook, String> colTotal;
	@FXML
	private TextField schCustomer;
	@FXML
	private TextField schBook;
	@FXML
	private DatePicker schDateFrom;
	@FXML
	private DatePicker schDateTo;

	@FXML
	private Label total;
	private DoubleProperty totalProperty = new SimpleDoubleProperty();
	private LocalDate dateFrom;
	private LocalDate dateTo;

	private BookModel bookModel;
	private CustomerModel custModel;
	private CheckOutModel model;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		bookModel = new BookModelImp();
		custModel = new CustomerModelImp();
		model = new CheckOutModelImp();

		// table configuration
		initTableColumns();

		// search listener
		initSearchFields();

		// binding
		total.textProperty().bind(totalProperty.asString());
	}

	private void initSearchFields() {

		dateFrom = LocalDate.now().withDayOfMonth(1);
		dateTo = dateFrom.plusMonths(1).minusDays(1);
		schDateFrom.setValue(dateFrom);

		schBook.textProperty().addListener((a, b, c) -> search());
		schCustomer.textProperty().addListener((a, b, c) -> search());
		schDateFrom.valueProperty().addListener((a, b, c) -> search());
		schDateTo.valueProperty().addListener((a, b, c) -> search());

		schDateTo.setValue(dateTo);
	}

	private void initTableColumns() {
		colBook.setCellValueFactory(p -> {
			if (null != p) {
				CustomerBook cb = p.getValue();
				Book b = bookModel.getBook(cb.getBook_id());
				return new SimpleStringProperty(b.getName());
			}
			return null;
		});

		colCount.setCellValueFactory(p -> {
			if (null != p) {
				CustomerBook cb = p.getValue();
				return new SimpleStringProperty(String.valueOf(cb.getAmount()));
			}
			return null;
		});

		colCustomer.setCellValueFactory(p -> {
			if (null != p) {
				CustomerBook cb = p.getValue();
				Customer b = custModel.get(cb.getCustomer_id());
				return new SimpleStringProperty(b.getName());
			}
			return null;
		});

		colDateTime.setCellValueFactory(p -> {
			if (null != p) {
				CustomerBook cb = p.getValue();
				return new SimpleStringProperty(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
						.format(LocalDateTime.ofInstant(cb.getSell_date().toInstant(), ZoneId.systemDefault())));
			}
			return null;
		});

		colPrice.setCellValueFactory(p -> {
			if (null != p) {
				CustomerBook cb = p.getValue();
				Book b = bookModel.getBook(cb.getBook_id());
				return new SimpleStringProperty(String.valueOf(b.getPrice()));
			}
			return null;
		});

		colTotal.setCellValueFactory(p -> {
			if (null != p) {
				CustomerBook cb = p.getValue();
				return new SimpleStringProperty(String.valueOf(cb.getTotal()));
			}
			return null;
		});
	}

	public void search() {
		table.getItems().clear();
		table.getItems().addAll(
				model.find(schDateFrom.getValue(), schDateTo.getValue(), schCustomer.getText(), schBook.getText()));
		totalProperty.set(table.getItems().stream().mapToDouble(a -> a.getTotal()).sum());
	}

	public void clear() {
		schBook.clear();
		schCustomer.clear();
		schDateFrom.setValue(dateFrom);
		schDateTo.setValue(dateTo);
	}

}
