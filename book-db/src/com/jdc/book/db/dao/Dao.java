package com.jdc.book.db.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
	int insert(T t) throws SQLException;
	int update(String set, String where, List<Object> param) throws SQLException;
	int delete(String where, List<Object> param) throws SQLException;
	List<T> select(String where, List<Object> param) ;
}
