package com.jdc.book.db.dao.imp;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.jdc.book.db.dao.BaseDao;
import com.jdc.book.db.entity.Book;

public class BookDao extends BaseDao<Book> {

	@Override
	protected String getTableName() {
		return "book";
	}

	@Override
	protected String getInsertColumns() {
		return "author_id, category_id, name, short_description, issue_date, price";
	}

	@Override
	protected String getInsertValueParams() {
		return "?, ?, ?, ?, ?, ?";
	}

	@Override
	protected List<Object> getInsertValues(Book t) {
		return Arrays.asList(t.getAuthor_id(), 
				t.getCategory_id(), 
				t.getName(), 
				t.getShort_description(), 
				new Date(t.getIssue_date().getTime()), 
				t.getPrice());
	}

	@Override
	protected Book getObjectFromResultSet(ResultSet rs) throws SQLException {
		Book b = new Book();
		b.setId(rs.getInt("id"));
		b.setAuthor_id(rs.getInt("author_id"));
		b.setCategory_id(rs.getInt("category_id"));
		b.setName(rs.getString("name"));
		b.setPrice(rs.getDouble("price"));
		b.setIssue_date(new java.util.Date(rs.getDate("issue_date").getTime()));
		b.setShort_description(rs.getString("short_description"));
		return b;
	}

}
