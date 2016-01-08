package com.jdc.diceGame;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class WelcomeView {
	
	public void loadGame() {
		
		try {
			Node node = LoadGameView.getView();
			GameApplication.switchView(node);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void newGame() {
		
		try {
			Node node = UserConfiguration.getView();
			GameApplication.switchView(node);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Node getView() throws IOException {
		return FXMLLoader.load(WelcomeView.class.getResource("WelcomeView.fxml"));
	}
}
