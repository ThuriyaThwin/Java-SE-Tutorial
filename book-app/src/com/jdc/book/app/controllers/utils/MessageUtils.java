package com.jdc.book.app.controllers.utils;

import com.jdc.book.app.BookAppException;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonBar.ButtonData;

public class MessageUtils {
	
	private static Label messageView;
	
	public static void setMessage(Label message) {
		MessageUtils.messageView = message;
	}
	
	public static void clearMessage() {
		messageView.setText("");
	}
	
	public static void handle(BookAppException e) {
		if(e.isNeedToAlart()) {
			showAlert(e.getMessage());
		} else {
			showMessage(e.getMessage());
		}
	}
	
	private static void showAlert(String message) {
		Dialog<String> dialog = new Dialog<>();

		dialog.setTitle("User Operation Error");
		dialog.setContentText(message);
		
		ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(okButton);
		
		dialog.show();
	}
	
	private static void showMessage(String message) {
		messageView.setText(message);
	}

}
