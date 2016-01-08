package com.jdc.book.db.dao.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.jdc.book.db.dao.BaseDao;
import com.jdc.book.db.entity.Author;

public class AuthorDao extends BaseDao<Author> {

	@Override
	protected String getTableName() {
		return "author";
	}

	@Override
	protected String getInsertColumns() {
		return "name, biography, photo";
	}

	@Override
	protected String getInsertValueParams() {
		return "?, ?, ?";
	}

	@Override
	protected List<Object> getInsertValues(Author a) {
		return Arrays.asList(a.getName(), a.getBiography(), a.getPhoto());
	}

	@Override
	protected Author getObjectFromResultSet(ResultSet rs) throws SQLException {
		Author a = new Author();
		a.setId(rs.getInt("id"));
		a.setName(rs.getString("name"));
		a.setPhoto(rs.getString("photo"));
		a.setBiography(rs.getString("biography"));
		return a;
	}

}
