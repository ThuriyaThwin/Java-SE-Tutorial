package com.jdc.diceGame;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar.ButtonData;

public class DialogHelper {
	
	public static void showMessage(String message) {
		Dialog<String> dialog = new Dialog<>();
		
		dialog.setTitle("Dice Game Error");
		dialog.setHeaderText("Error Message");
		dialog.setContentText(message);
		
		ButtonType btn = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(btn);
		
		dialog.show();
	}
}
