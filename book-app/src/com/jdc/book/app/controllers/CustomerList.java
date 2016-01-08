package com.jdc.book.app.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jdc.book.app.models.CustomerModel;
import com.jdc.book.app.models.imp.CustomerModelImp;
import com.jdc.book.db.entity.Customer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerList implements Initializable{
	
	@FXML
	private TableView<Customer> table;
	@FXML
	private TableColumn<Customer, String> colId;
	@FXML
	private TableColumn<Customer, String> colName;
	@FXML
	private TableColumn<Customer, String> colPhone;
	@FXML
	private TableColumn<Customer, String> colEmail;
	@FXML
	private TableColumn<Customer, String> colAddress;
	
	@FXML
	private TextField schName;
	@FXML
	private TextField schPhone;
	
	private CustomerModel model;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		model = new CustomerModelImp();
		
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("mail"));
		colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		
		schName.textProperty().addListener((a, b, c) -> search());
		schPhone.textProperty().addListener((a, b, c) -> search());
		
		search();
	}
	
	public void search() {
		table.getItems().clear();
		table.getItems().addAll(model.find(schName.getText(), schPhone.getText()));
	}
	
	public void clear() {
		schName.clear();
		schPhone.clear();
	}

}
