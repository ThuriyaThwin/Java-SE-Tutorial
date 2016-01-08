package com.jdc.book.app.models;

import java.util.List;

import com.jdc.book.db.entity.Author;

public interface AuthorModel {

	void add(Author a);
	List<Author> find(String name);
	void update(int id, String name, String biography, String photo);
	Author findById(int id);
}
