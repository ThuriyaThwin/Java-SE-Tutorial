package com.jdc.book.app.controllers.utils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.jdc.book.app.BookAppException;
import com.jdc.book.app.controllers.CheckOut;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CartView implements Initializable{
	
	public enum Mode {Reference, Edit}

	private Mode mode;
	@FXML
	private HBox controls;
	@FXML
	private VBox items;
	@FXML
	private Label totalHead;
	@FXML
	private Label totalFoot;

	private StringProperty totalProperty = new SimpleStringProperty();
	private DoubleProperty totalIntProperty = new SimpleDoubleProperty();
	
	private Button addToCart;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		totalHead.textProperty().bind(totalProperty);
		totalFoot.textProperty().bind(totalProperty);
		totalProperty.bind(totalIntProperty.asString());
		items.getChildren().addListener(this::onChange);
		controls.getStyleClass().add("controls-view");
	}

	public void setMode(Mode mode) {
		this.mode = mode;
		initControls();
	}
	
	public void checkOut(ActionEvent event) {
		try {
			List<CartItem> list = items.getChildren().stream().filter(a -> a instanceof CartItemAdaptor)
				.map(a -> (CartItemAdaptor)a)
				.map(a -> a.getItem())
				.collect(Collectors.toList());
			
			Node checkOutView = CheckOut.getView(list);
			
			Navigator.navigate(checkOutView);
			
		} catch (BookAppException e) {
			MessageUtils.handle(e);
		}
	}
	
	private void initControls() {
		if(mode.equals(Mode.Edit)) {
			// add 
			addToCart = new Button("Add");
			
			// clear
			Button clear = new Button("Clear");
			clear.setOnAction(a -> items.getChildren().clear());
			
			// check out
			Button checkOut = new Button("Check Out");
			checkOut.setOnAction(this::checkOut);
			
			controls.getChildren().addAll(addToCart, clear, checkOut);
			
		} else {
			
		}
	}
	
	public void setAddToCartListener(EventHandler<ActionEvent> handler) {
		addToCart.setOnAction(handler);
	}
	
	public void addToCart(CartItem item) {
		CartItemAdaptor adaptor = new CartItemAdaptor(mode, item, this::onChange);
		adaptor.getSeqProperty().set(String.valueOf(items.getChildren().size() + 1));
		this.items.getChildren().add(adaptor);
	}
	
	private void onChange(Change<? extends Node> c) {
		this.onChange();
	}
	
	private void onChange(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
		this.onChange();
	}
	
	private void onChange() {
		totalIntProperty.set(0);
				
		this.items.getChildren().stream().filter(a -> a instanceof CartItemAdaptor)
			.map(a -> (CartItemAdaptor)a)
			.forEach(a -> totalIntProperty.set(totalIntProperty.get() + a.getTotalProperty().get()));
	}
}
