package com.jdc.cinema.main;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class AllertManager {
	
	public static void showAllert(String message) {
		Dialog<String> dialog = new Dialog<>();
		dialog.getDialogPane().getButtonTypes().add(new ButtonType("OK", ButtonData.YES));
		dialog.getDialogPane().setHeaderText("User Input Error");
		dialog.getDialogPane().setContentText(message);
		dialog.show();
	}

}
