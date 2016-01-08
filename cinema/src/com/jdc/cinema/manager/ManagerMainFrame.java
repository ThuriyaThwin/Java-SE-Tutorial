package com.jdc.cinema.manager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManagerMainFrame implements Initializable {

	@FXML
	private StackPane stack;

	@FXML
	private Menu edit;
	@FXML
	private Menu metadata;
	@FXML
	private Menu reports;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		edit.getItems().filtered(a -> a instanceof MenuItem).forEach(a -> {
			a.setOnAction(this::handleMenuAction);
		});
		metadata.getItems().filtered(a -> a instanceof MenuItem).forEach(a -> {
			a.setOnAction(this::handleMenuAction);
		});
		reports.getItems().filtered(a -> a instanceof MenuItem).forEach(a -> {
			a.setOnAction(this::handleMenuAction);
		});

		try {
			Node home = FXMLLoader.load(getClass().getResource("Home.fxml"));
			stack.getChildren().add(home);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clickButton(ActionEvent action) {

		try {
			Button b = (Button) action.getSource();
			String id = b.getId();

			String viewName = id.concat("Controller.fxml");
			Node view = FXMLLoader.load(getClass().getResource(viewName));
			stack.getChildren().clear();
			stack.getChildren().add(view);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void handleMenuAction(ActionEvent action) {
		try {

			MenuItem item = (MenuItem) action.getSource();
			String viewName = item.getText().replace(" ", "").concat("Controller.fxml");
			Node view = FXMLLoader.load(getClass().getResource(viewName));
			stack.getChildren().clear();
			stack.getChildren().add(view);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		Platform.exit();
	}

	public static void showDialog() {

		try {
			Parent root = FXMLLoader.load(ManagerMainFrame.class.getResource("ManagerMainFrame.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);

			stage.setOnHidden(a -> {
				Platform.exit();
			});

			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
