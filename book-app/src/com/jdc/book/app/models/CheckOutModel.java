package com.jdc.book.app.models;

import java.time.LocalDate;
import java.util.List;

import com.jdc.book.app.controllers.utils.CartItem;
import com.jdc.book.db.entity.Customer;
import com.jdc.book.db.entity.CustomerBook;

public interface CheckOutModel {

	List<CustomerBook> find(LocalDate dtFrom, LocalDate dtTo, String customer, String book);
	void checkOut(Customer c, List<CartItem> items);
}
