package com.jdc.book.app.controllers.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.jdc.book.app.BookAppException;

import javafx.scene.image.Image;

public class DefaultImage {
	
	private static Image IMAGE;
	
	public static Image getImage() {
		if(IMAGE == null) {
			try {
				IMAGE = new Image(new FileInputStream("image/no-profile-img.gif"));
			} catch (FileNotFoundException e) {
				throw new BookAppException(e.getMessage(), false);
			}
		}
		
		return IMAGE;
	}

}
