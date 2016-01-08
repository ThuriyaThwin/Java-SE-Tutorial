package com.jdc.book.app.models;

import java.util.List;

import com.jdc.book.db.entity.Customer;

public interface CustomerModel {
	void create(Customer cust);
	List<Customer> find(String name, String phone);
	List<Customer> find(String name);
	Customer get(int id);
}
