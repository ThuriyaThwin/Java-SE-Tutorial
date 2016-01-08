package com.jdc.book.app.controllers.utils;

import com.jdc.book.app.controllers.utils.CartView.Mode;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CartItemAdaptor extends HBox{
	
	private CartItem item;
	
	private Label lbName;
	private Label lbTotal;
	private Label lbCount;
	private Label lbSeq;
	
	private ComboBox<Integer> combo;
	
	public CartItemAdaptor(Mode mode, CartItem item, ChangeListener<Integer> changeHandler) {
		super();
		this.item = item;
		
		combo = new ComboBox<>();
		combo.getItems().addAll(0, 1, 2, 3, 4, 5);
		
		lbName = new Label();
		lbName.getStyleClass().add("cart-item-name");
		lbTotal = new Label();
		lbTotal.getStyleClass().add("cart-item-price");
		lbCount = new Label();
		lbSeq = new Label();
		
		lbName.textProperty().bind(this.item.getNameProperty());
		lbTotal.textProperty().bind(this.item.getTotalProperty().asString());
		lbCount.textProperty().bind(this.item.getCountProperty().asString());
		this.item.getCountProperty().bind(combo.valueProperty());
		
		combo.setValue(1);
		
		getChildren().add(lbSeq);
		getChildren().add(lbName);
		
		if(mode.equals(Mode.Edit)) {
			getChildren().add(combo);
			combo.valueProperty().addListener(changeHandler);
		} else {
			getChildren().add(lbCount);
		}
		
		getChildren().add(lbTotal);
		getStyleClass().add("cart-item");
		
	}
	
	public StringProperty getSeqProperty() {
		return lbSeq.textProperty();
	}
	
	public DoubleProperty getTotalProperty() {
		return this.item.getTotalProperty();
	}
	
	public CartItem getItem() {
		return item;
	}

}
