package com.jdc.book.app.models.imp;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.jdc.book.app.BookAppException;
import com.jdc.book.app.models.CategoryModel;
import com.jdc.book.db.dao.ConnectionManager;
import com.jdc.book.db.dao.Dao;
import com.jdc.book.db.dao.imp.CategoryDao;
import com.jdc.book.db.entity.Category;

public class CategoryModelImp implements CategoryModel {
	
	private Dao<Category> dao;
	
	public CategoryModelImp() {
		dao = new CategoryDao();
	}

	@Override
	public void create(Category c) {
		
		if(c.getCategory() == null || c.getCategory().isEmpty()) {
			throw new BookAppException("Category name must be entered!", true);
		}

		try {
			ConnectionManager.begin();
			
			dao.insert(c);
			
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
	public List<Category> find(String name) {
		String where = null;
		List<Object> param = null;
		
		if(null != name && !name.isEmpty()) {
			where = "category like ? ";
			param = Arrays.asList("%" + name + "%");
		}
		
		return dao.select(where, param);
	}

	@Override
	public void update(int id, String name) {
		String set = "category = ? ";
		String where = "id = ?";
		List<Object> param = Arrays.asList(name, id);
		
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
	public Category findById(int id) {
		String where = "id = ? ";
		List<Object> param = Arrays.asList(id);
		
		List<Category> list = dao.select(where, param);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
