package com.jdc.book.app.models;

import java.util.List;

import com.jdc.book.db.entity.Category;

public interface CategoryModel {

	void create(Category c);
	List<Category> find(String name);
	void update(int id, String name);
	Category findById(int id);
}
