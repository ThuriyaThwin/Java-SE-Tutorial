package com.jdc.book.app.controllers.utils;

import com.jdc.book.db.entity.Book;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CartItem {
	
	private Book book;
	private IntegerProperty countProperty = new SimpleIntegerProperty(1);
	private DoubleProperty priceProperty = new SimpleDoubleProperty(0);
	private DoubleProperty totalProperty = new SimpleDoubleProperty();
	private StringProperty nameProperty = new SimpleStringProperty();
	
	public CartItem(Book b) {
		setBook(b);
		totalProperty.bind(priceProperty.multiply(countProperty));
	}
	
	public void setBook(Book book) {
		this.book = book;
		if(this.book != null) {
			priceProperty.set(book.getPrice());
			nameProperty.set(book.getName());
		}
	}

	public Book getBook() {
		return book;
	}

	public IntegerProperty getCountProperty() {
		return countProperty;
	}

	public DoubleProperty getPriceProperty() {
		return priceProperty;
	}

	public DoubleProperty getTotalProperty() {
		return totalProperty;
	}

	public StringProperty getNameProperty() {
		return nameProperty;
	}
	
}
