package com.jdc.book.app.models;

import java.util.List;

import com.jdc.book.db.entity.Author;
import com.jdc.book.db.entity.Book;
import com.jdc.book.db.entity.Category;

public interface BookModel {

	void create(Book b, Category c, Author a);
	Book getBook(int id);
	List<Book> search(Category c, Author a, String keyword);
}
