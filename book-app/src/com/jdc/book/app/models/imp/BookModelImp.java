package com.jdc.book.app.models.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jdc.book.app.models.BookModel;
import com.jdc.book.db.dao.ConnectionManager;
import com.jdc.book.db.dao.Dao;
import com.jdc.book.db.dao.imp.AuthorDao;
import com.jdc.book.db.dao.imp.BookDao;
import com.jdc.book.db.dao.imp.CategoryDao;
import com.jdc.book.db.entity.Author;
import com.jdc.book.db.entity.Book;
import com.jdc.book.db.entity.Category;

public class BookModelImp implements BookModel {
	
	private Dao<Book> bookDao;
	private Dao<Category> categoryDao;
	private Dao<Author> authorDao;
	
	public BookModelImp() {
		bookDao = new BookDao();
		categoryDao = new CategoryDao();
		authorDao = new AuthorDao();
	}

	@Override
	public void create(Book b, Category c, Author a) {
		
		try {
			ConnectionManager.begin();
			// check category
			if(c.getId() == 0) {
				categoryDao.insert(c);
			}
			b.setCategory_id(c.getId());
			
			// check author
			if(a.getId() == 0) {
				authorDao.insert(a);
			}
			b.setAuthor_id(a.getId());
			
			// create book
			bookDao.insert(b);
			
			ConnectionManager.commit();
		} catch (SQLException e) {
			try {
				ConnectionManager.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public Book getBook(int id) {
		String where = "id = ?";
		List<Object> param = Arrays.asList(id);
		
		List<Book> result = bookDao.select(where, param);
		
		if(result.size() > 0) {
			return result.get(0);
		}
		
		return null;
	}

	@Override
	public List<Book> search(Category c, Author a, String keyword) {
		
		StringBuilder sb = new StringBuilder();
		List<Object> param = new ArrayList<>();
		
		// category
		if(null != c) {
			sb.append("category_id = ? ");
			param.add(c.getId());
		}
		
		// author
		if(null != a) {
			if(param.size() > 0) {
				sb.append("and ");
			}
			
			sb.append("author_id = ? ");
			param.add(a.getId());
		}
		
		// keyword
		if(null != keyword && !keyword.isEmpty()) {
			if(param.size() > 0) {
				sb.append("and ");
			}

			sb.append("(name like ? or short_description like ?) ");
			param.add("%" + keyword + "%");
			param.add("%" + keyword + "%");
		}
		
		return bookDao.select(sb.toString(), param);
	}

}
