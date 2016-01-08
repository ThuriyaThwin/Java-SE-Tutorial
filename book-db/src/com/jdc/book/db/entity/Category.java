package com.jdc.book.db.entity;

import com.jdc.book.db.dao.GeneratedKeyEntity;

public class Category implements GeneratedKeyEntity{

	private int id;
	private String category;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return category;
	}
}
