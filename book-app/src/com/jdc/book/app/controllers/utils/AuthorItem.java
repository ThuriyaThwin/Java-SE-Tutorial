package com.jdc.book.app.controllers.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.jdc.book.app.BookAppException;
import com.jdc.book.app.models.AuthorModel;
import com.jdc.book.db.entity.Author;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class AuthorItem extends VBox {
	
	private Label id;
	private Label name;
	private ImageView image;
	private Label biography;
	
	private TextField edName;
	private TextArea edBiography;
	
	private HBox header;
	private VBox cardBody;
	private HBox buttonGroup;
	private Button btnEdit;
	private Button btnUpload;
	private Button btnDelPhoto;
	
	private AuthorModel model;
	private String photoName;
	
	public AuthorItem(Author a, AuthorModel model) {
		this.model = model;
		setPrefWidth(300);
		getStyleClass().addAll("card-shadow");
		
		id = new Label(String.valueOf(a.getId()));
		name = new Label();
		
		photoName = a.getPhoto();
		
		biography = new Label();
		biography.setPrefWidth(380);
		biography.setWrapText(true);
		
		edName = new TextField();
		edBiography = new TextArea(a.getBiography());
		edBiography.setPrefWidth(280);
		edBiography.setPrefHeight(80);
		edBiography.setWrapText(true);
		
		name.textProperty().bind(edName.textProperty());
		biography.textProperty().bind(edBiography.textProperty());
		
		edName.setText(a.getName());
		edBiography.setText(a.getBiography());
		
		header = new HBox(10);
		header.setAlignment(Pos.CENTER_LEFT);
		header.getStyleClass().add("color-600");
		header.getStyleClass().add("card-title");
		
		header.getChildren().addAll(id, name);
		
		cardBody = new VBox();
		cardBody.getStyleClass().add("card-body");
		cardBody.getStyleClass().add("color-200");
		
		buttonGroup = new HBox(10);
		btnEdit = new Button("Edit");
		btnEdit.setOnAction(e -> this.edit());
		btnEdit.getStyleClass().add("color-600");
		
		btnUpload = new Button("Upload Photo");
		btnUpload.setOnAction(e -> this.upload());
		btnUpload.getStyleClass().add("color-400");
		
		btnDelPhoto = new Button("Delete Photo");
		btnDelPhoto.setOnAction(b -> deletePhoto());
		btnDelPhoto.getStyleClass().add("color-300");
		
		buttonGroup.getChildren().add(btnEdit);
		
		image = new ImageView(DefaultImage.getImage());
		image.setFitHeight(120);
		image.setPreserveRatio(true);
		
		cardBody.getChildren().addAll(image, biography, buttonGroup);
		
		if(a.getPhoto() != null && !a.getPhoto().isEmpty()) {
			try {
				Image img = new Image(new FileInputStream(a.getPhoto()));
				image.setImage(img);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		getChildren().addAll(header, cardBody);
	}
	
	private void edit() {
		if(btnEdit.getText().equals("Edit")) {
			this.setEditView();
		} else {
			model.update(Integer.parseInt(id.getText()), edName.getText(), edBiography.getText(), photoName);
			this.setReferenceView();
		}
	}
	
	private void upload() {
		try {
			try {
				FileChooser fc = new FileChooser();
				fc.setTitle("Select Author's Photo");
				fc.getExtensionFilters().add(new ExtensionFilter("Image", "*.gif", "*.png", "*.jpg"));
				
				File file = fc.showOpenDialog(image.getScene().getWindow());
				
				if(null != file) {
					Image imageIn = new Image(new FileInputStream(file));
					image.setImage(imageIn);
					// save image
					photoName = ImageUtils.savePhoto(image.snapshot(null, null));
				}
				
			} catch(Exception e) {
				e.printStackTrace();
				throw new BookAppException(e.getMessage(), false);
			}
		} catch (BookAppException be) {
			MessageUtils.handle(be);
		}		
	}
	
	private void deletePhoto() {
		try {
			try {
				image.setImage(DefaultImage.getImage());
				if(photoName != null) {
					Files.delete(Paths.get(photoName));
					photoName = null;
				}
			} catch (Exception e) {
				throw new BookAppException(e.getMessage(), false);
			}
		} catch(BookAppException be) {
			MessageUtils.handle(be);
		}		
	}
	
	private void setEditView() {
		header.getChildren().remove(name);
		header.getChildren().add(edName);
		cardBody.getChildren().remove(biography);
		cardBody.getChildren().add(1, edBiography);
		
		btnEdit.setText("Save");
		buttonGroup.getChildren().clear();
		buttonGroup.getChildren().addAll(btnDelPhoto, btnUpload, btnEdit);
	}
	
	private void setReferenceView() {
		header.getChildren().remove(edName);
		header.getChildren().add(name);
		cardBody.getChildren().remove(edBiography);
		cardBody.getChildren().add(1, biography);
		
		btnEdit.setText("Edit");
		buttonGroup.getChildren().clear();
		buttonGroup.getChildren().add(btnEdit);
	}
	
}
