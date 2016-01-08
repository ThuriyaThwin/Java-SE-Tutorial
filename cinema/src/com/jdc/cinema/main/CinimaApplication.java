package com.jdc.cinema.main;

import com.jdc.cinema.security.LoginForm;
import com.jdc.cinema.security.SignUpForm;
import com.jdc.cinema.service.SecurityService;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CinimaApplication extends Application{
	
	private SecurityService security;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		security = SecurityService.getInstance();
		
		Scene scene = null;
		
		// check user size
		if(security.isNoUser()) {
			// create role
			security.createRole();
			
			// if no user -> sign up
			scene = SignUpForm.getScene();
			
		} else {
			// else load login
			scene = LoginForm.getScene();
		}
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
