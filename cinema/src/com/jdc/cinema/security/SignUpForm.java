package com.jdc.cinema.security;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jdc.cinema.entity.Role;
import com.jdc.cinema.manager.ManagerMainFrame;
import com.jdc.cinema.service.SecurityService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpForm implements Initializable{

	@FXML
	private TextField name;
	@FXML
	private TextField login;
	@FXML
	private PasswordField password;
	
	private SecurityService security;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		security = SecurityService.getInstance();
	}
	
	public void signUp() {
		
		// sign up
		security.signUp(name.getText(), login.getText(), password.getText());
		
		// login
		Role role = security.login(login.getText(), password.getText());
		
		// OK -> Manager Home
		if(null != role) {
			ManagerMainFrame.showDialog();
		}
		
	}
	
	public void clear() {
		name.clear();
		login.clear();
		password.clear();
	}

	public static Scene getScene() throws IOException {
		Parent root = FXMLLoader.load(LoginForm.class.getResource("SignUpForm.fxml"));
		return new Scene(root);
	}
}
