package com.jdc.book.app.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.jdc.book.app.BookAppException;
import com.jdc.book.app.controllers.utils.CategoryItem;
import com.jdc.book.app.controllers.utils.MessageUtils;
import com.jdc.book.app.models.CategoryModel;
import com.jdc.book.app.models.imp.CategoryModelImp;
import com.jdc.book.db.entity.Category;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class Categories implements Initializable {

	@FXML
	private TextField name;
	@FXML
	private FlowPane container;

	private CategoryModel model;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		model = new CategoryModelImp();
		loadCategories();
	}

	private void loadCategories() {
		container.getChildren().clear();

		container.getChildren().addAll(model.find(null).stream()
				.map(a -> new CategoryItem(a, model))
				.collect(Collectors.toList()));
	}
	
	public void addCategory() {
		try {
			Category c = new Category();
			c.setCategory(name.getText());
			model.create(c);
			loadCategories();
			name.clear();
		} catch(BookAppException e) {
			MessageUtils.handle(e);
		}
	}

}
