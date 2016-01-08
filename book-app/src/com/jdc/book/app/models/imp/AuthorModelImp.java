package com.jdc.book.app.models.imp;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.jdc.book.app.BookAppException;
import com.jdc.book.app.models.AuthorModel;
import com.jdc.book.db.dao.ConnectionManager;
import com.jdc.book.db.dao.Dao;
import com.jdc.book.db.dao.imp.AuthorDao;
import com.jdc.book.db.entity.Author;

public class AuthorModelImp implements AuthorModel {

	private Dao<Author> dao;
	
	public AuthorModelImp() {
		dao = new AuthorDao();
	}
	
	@Override
	public void add(Author a) {
		
		if(a.getName() == null || a.getName().isEmpty()) {
			throw new BookAppException("Author's name must be entered!", true);
		}
		
		try {
			ConnectionManager.begin();
			dao.insert(a);
			ConnectionManager.commit();
		} catch (SQLException e) {
			try {
				ConnectionManager.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BookAppException(e.getMessage(), false);
		}
	}

	@Override
	public List<Author> find(String name) {
		String where = null;
		List<Object> param = null;
		
		if(null != name && !name.isEmpty()) {
			where = "name like ? ";
			param = Arrays.asList(name + "%");
		}
		return dao.select(where, param);
	}

	@Override
	public void update(int id, String name, String biography, String photo) {
		String set = "name = ?, biography = ?, photo = ? ";
		String where = "id = ? ";
		List<Object> param = Arrays.asList(name, biography, photo, id);
		
		try {
			ConnectionManager.begin();
			dao.update(set, where, param);
			ConnectionManager.commit();
		} catch (SQLException e) {
			try {
				ConnectionManager.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			throw new BookAppException(e.getMessage(), false);
		}
	}

	@Override
	public Author findById(int id) {
		String where = "id = ? ";
		List<Object> param = Arrays.asList(id);
		
		List<Author> list = dao.select(where, param);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
