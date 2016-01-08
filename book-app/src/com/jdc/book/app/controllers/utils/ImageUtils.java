package com.jdc.book.app.controllers.utils;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;

import com.jdc.book.app.BookAppException;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class ImageUtils {
	
	public static String savePhoto(WritableImage writeImage) {
		
		try {
			Path outFile = Paths.get("image", DateTimeFormatter.ofPattern("yyyyMMdd_HHmm_ss_SSS").format(LocalDateTime.now()) + ".png");
			
			BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writeImage, null);
			ImageIO.write(bufferedImage, "png", outFile.toFile());
			return outFile.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Image getImage(String imageName) {
		try {
			if(null != imageName && !imageName.isEmpty()) {
				return new Image(new FileInputStream(imageName));
			}
			return DefaultImage.getImage();
		} catch (Exception e) {
			throw new BookAppException(e.getMessage(), false);
		}
	}
	
	public static void delete(String imageName) {
		try {
			if(null != imageName && !imageName.isEmpty()) {
				Files.delete(Paths.get(imageName));
			}
		} catch (Exception e) {
			throw new BookAppException(e.getMessage(), false);
		}
	}
}
