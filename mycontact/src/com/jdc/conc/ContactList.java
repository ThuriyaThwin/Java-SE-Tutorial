package com.jdc.conc;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jdc.conc.model.Contact;
import com.jdc.conc.model.ContactModel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ContactList implements Initializable{
	@FXML
	private TableView<Contact> table;
	@FXML
	private TableColumn<Contact, String> colName;
	@FXML
	private TableColumn<Contact, String> colDob;
	@FXML
	private TableColumn<Contact, String> colPhone;
	
	private ContactModel model;
	
	public static void showView() throws IOException {
		// load fxml
		Parent root = FXMLLoader.load(ContactList.class.getResource("ContactList.fxml"));
		// create scene
		Scene scene = new Scene(root);
		// create stage
		Stage stage = new Stage();
		// set scene to stage
		stage.setScene(scene);
		// show stage
		stage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
		colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		
		model = ContactModel.getModel();
		table.getItems().addAll(model.find(a -> true));
	}
}
