package com.jdc.book.app.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.jdc.book.app.BookAppException;
import com.jdc.book.app.controllers.utils.AuthorItem;
import com.jdc.book.app.controllers.utils.DefaultImage;
import com.jdc.book.app.controllers.utils.ImageUtils;
import com.jdc.book.app.controllers.utils.MessageUtils;
import com.jdc.book.app.models.AuthorModel;
import com.jdc.book.app.models.imp.AuthorModelImp;
import com.jdc.book.db.entity.Author;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Authors implements Initializable{

	@FXML
	private TextField name;
	@FXML
	private ImageView photo;
	@FXML
	private TextArea biography;
	@FXML
	private FlowPane container;
	@FXML
	private TextField search;
	
	private String photoName;
	
	private AuthorModel model;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.model = new AuthorModelImp();
		photo.setFitHeight(200);
		photo.setPreserveRatio(true);
		
		search.textProperty().addListener((a,b,c) -> {
			search();
		});
		
		this.search();
	}
	
	public void clear() {
		try {
			try {
				photo.setImage(DefaultImage.getImage());
				if(photoName != null) {
					Files.delete(Paths.get(photoName));
					photoName = null;
				}
			} catch (Exception e) {
				throw new BookAppException(e.getMessage(), false);
			}
			name.clear();
			biography.clear();
		} catch(BookAppException be) {
			MessageUtils.handle(be);
		}
	}
	
	public void add() {
		try {
			Author a = new Author();
			a.setName(name.getText());
			a.setBiography(biography.getText());
			a.setPhoto(photoName);
			
			model.add(a);
			
			photo.setImage(DefaultImage.getImage());
			photoName = null;
			name.clear();
			biography.clear();

			search();
		} catch (BookAppException e) {
			MessageUtils.handle(e);
		}
	}
	
	public void upload() {
		try {
			try {
				FileChooser fc = new FileChooser();
				fc.setTitle("Select Author's Photo");
				fc.getExtensionFilters().add(new ExtensionFilter("Image", "*.gif", "*.png", "*.jpg"));
				
				File file = fc.showOpenDialog(photo.getScene().getWindow());
				
				if(null != file) {
					Image image = new Image(new FileInputStream(file));
					photo.setImage(image);
					// save image
					photoName = ImageUtils.savePhoto(photo.snapshot(null, null));
				}
				
			} catch(Exception e) {
				e.printStackTrace();
				throw new BookAppException(e.getMessage(), false);
			}
		} catch (BookAppException be) {
			MessageUtils.handle(be);
		}
	}
	

	private void search() {
		container.getChildren().clear();
		
		List<Author> authors = model.find(search.getText());
		container.getChildren().addAll(authors.stream()
				.map(a -> new AuthorItem(a, model))
				.collect(Collectors.toList()));
	}

}
