package com.jdc.diceGame;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameApplication extends Application implements Initializable{
	
	private static StackPane CONTENT;

	@FXML
	private StackPane stack;
	
	public static void switchView(Node node) {
		CONTENT.getChildren().clear();
		CONTENT.getChildren().add(node);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// load fxml
		Parent root = FXMLLoader.load(getClass().getResource("GameApplication.fxml"));

		// create scene
		Scene scene = new Scene(root);

		// set scene to stage
		primaryStage.setScene(scene);

		// show stage
		primaryStage.show();

	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CONTENT = stack;
		try {
			switchView(WelcomeView.getView());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
