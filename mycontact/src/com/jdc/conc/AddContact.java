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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddContact implements Initializable{
	
	private ContactModel model;
	
	@FXML
	private TextField name;
	@FXML
	private DatePicker dob;
	@FXML
	private TextField phone;
	@FXML
	private TextArea address;
	
	public void clear() {
		name.clear();
		dob.setValue(null);
		phone.clear();
		address.clear();
	}
	
	public void save() {
		try {
			Contact c = new Contact();
			c.setName(name.getText());
			c.setDob(dob.getValue());
			c.setPhone(phone.getText());
			c.setAddress(address.getText());
			
			model.create(c);
			
			ContactList.showView();
			
			name.getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		model = ContactModel.getModel();
	}
	
	public static void showView() throws IOException {
		// load fxml
		Parent root = FXMLLoader.load(AddContact.class.getResource("AddContact.fxml"));
		// create scene
		Scene scene = new Scene(root);
		// create stage
		Stage stage = new Stage();
		// set scene to stage
		stage.setScene(scene);
		// show stage
		stage.show();
	}

}
