package com.jdc.cinema.dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Function;

import com.jdc.cinema.utils.BaseDao;

public interface Dao<T extends Entity> {

	T insert(T t);
	int update(String set, String where, List<Object> param);
	int delete(String where, List<Object> param);
	
	T findById(IdParam id);
	
	List<T> getWhere(String where, List<Object> param);
	int getWhereCount(String where, List<Object> param);
	
	List<T> getAll();
	int getAllCount();
	
	<U>List<U> find(String sql, List<Object> param, Function<ResultSet, U> converter);
	
	public static <T extends Entity> Dao<T> getDao(Class<T> type) {
		return new BaseDao<>(type);
	}
	
}
