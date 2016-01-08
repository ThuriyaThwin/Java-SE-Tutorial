package com.jdc.cinema.security;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jdc.cinema.entity.Role;
import com.jdc.cinema.entity.Role.Roles;
import com.jdc.cinema.manager.ManagerMainFrame;
import com.jdc.cinema.reception.TicketCounter;
import com.jdc.cinema.service.SecurityService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginForm implements Initializable{
	
	@FXML
	private TextField login;
	@FXML
	private PasswordField password;
	@FXML
	private Label message;
	
	private SecurityService security;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		security = SecurityService.getInstance();
	}
	
	public void login() {
		
		// login
		Role role = security.login(login.getText(), password.getText());
		
		if(null == role) {
			// if login NG
			message.setText("Please your login information!");
		} else {
			// if login OK
			if(Roles.Manager.toString().equals(role.getId())) {
				// Open manager main frame
				ManagerMainFrame.showDialog();
				
			} else {
				// Open Ticket Counter
				TicketCounter.showDialog();
			}
			
		}
	}
	
	public void clear() {
		message.setText("");
		login.clear();
		password.clear();
	}
	
	public static Scene getScene() throws IOException {
		Parent root = FXMLLoader.load(LoginForm.class.getResource("LoginForm.fxml"));
		return new Scene(root);
	}

}
