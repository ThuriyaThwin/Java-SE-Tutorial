package com.jdc.book.app.controllers.utils;

import com.jdc.book.app.models.CategoryModel;
import com.jdc.book.db.entity.Category;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class CategoryItem extends HBox {
	
	private CategoryModel model;
	
	private Label lbId;
	private Label lbName;
	private Button btn;
	private TextField text;
	
	enum Mode {Edit, Save}
	
	public CategoryItem(Category category, CategoryModel model) {
		this.model = model;
		
		lbId = new Label();
		lbName = new Label();
		text = new TextField();
		
		text.textProperty().addListener((a, b, c) -> {
			lbName.setText(c);
		});
		
		setCategory(category);

		btn = new Button(Mode.Edit.toString());
		btn.setOnAction(this::edit);
		btn.getStyleClass().add("color-400");
		
		getStyleClass().add("category-item");
		getStyleClass().add("color-200");
		getStyleClass().add("card-shadow");
		
		getChildren().addAll(btn, lbId, lbName);
	}
	
	public void setCategory(Category category) {
		lbId.setText(String.valueOf(category.getId()));
		text.setText(category.getCategory());
	}
	
	private void edit(ActionEvent e) {
		if(Mode.Edit.toString().equals(btn.getText())) {
			btn.setText(Mode.Save.toString());
			getChildren().remove(lbName);
			getChildren().add(text);
		} else {
			btn.setText(Mode.Edit.toString());
			model.update(Integer.parseInt(lbId.getText()), text.getText());
			getChildren().remove(text);
			getChildren().add(lbName);
		}
	}

}
