package com.jdc.book.db.dao.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.jdc.book.db.dao.BaseDao;
import com.jdc.book.db.entity.Category;

public class CategoryDao extends BaseDao<Category> {

	@Override
	protected String getTableName() {
		return "category";
	}

	@Override
	protected String getInsertColumns() {
		return "category";
	}

	@Override
	protected String getInsertValueParams() {
		return "?";
	}

	@Override
	protected List<Object> getInsertValues(Category t) {
		return Arrays.asList(t.getCategory());
	}

	@Override
	protected Category getObjectFromResultSet(ResultSet rs) throws SQLException {
		Category c = new Category();
		c.setId(rs.getInt("id"));
		c.setCategory(rs.getString("category"));
		return c;
	}

}
