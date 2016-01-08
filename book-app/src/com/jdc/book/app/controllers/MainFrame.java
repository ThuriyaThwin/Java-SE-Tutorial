package com.jdc.book.app.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jdc.book.app.BookAppException;
import com.jdc.book.app.controllers.utils.MessageUtils;
import com.jdc.book.app.controllers.utils.Navigator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;

public class MainFrame implements Initializable{
	
	@FXML
	private Label message;
	@FXML
	private StackPane contentView;
	@FXML
	private MenuBar menuBar;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MessageUtils.setMessage(message);
		Navigator.setContentView(contentView);
		
		menuBar.getMenus().forEach(a -> {
			a.getItems().forEach(b -> b.setOnAction(this::handle));
		});
		
		this.loadView("Home");
	}
	
	private void handle(ActionEvent event) {
		
		try {
			MenuItem menu = (MenuItem) event.getSource();
			if (menu.getText().equals("Close")) {
				Platform.exit();
			} else {
				this.loadView(menu.getText());
			}
		} catch (BookAppException e) {
			e.printStackTrace();
			MessageUtils.handle(e);
		}
	}
	
	private void loadView(String name) {
		
		message.setText("");
		
		String fxmlFile = name.replaceAll(" ", "").concat(".fxml");
		
		try {
			Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));
			Navigator.navigate(view);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BookAppException(e.getMessage(), false);
		}
	}
	
}
