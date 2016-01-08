package com.jdc.book.app.controllers.utils;

import java.io.IOException;

import com.jdc.book.app.BookAppException;
import com.jdc.book.app.controllers.MainFrame;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class Navigator {
	
	private static StackPane contentView;
	
	public static void setContentView(StackPane contentView) {
		Navigator.contentView = contentView;
	}
	
	public static void navigate(Node view) {
		contentView.getChildren().clear();
		contentView.getChildren().add(view);
	}
	
	public static void navigate(String viewName) {
		String fxmlFile = viewName.replaceAll(" ", "").concat(".fxml");
		
		try {
			Parent view = FXMLLoader.load(MainFrame.class.getResource(fxmlFile));
			Navigator.navigate(view);
		} catch (IOException e) {
			throw new BookAppException(e.getMessage(), false);
		}		
	}

}
