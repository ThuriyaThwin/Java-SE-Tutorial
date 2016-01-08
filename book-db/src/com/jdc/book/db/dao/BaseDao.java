package com.jdc.book.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseDao<T> implements Dao<T> {
	
	private static final String DELETE = "delete from %s where %s ";
	private static final String INSERT = "insert into %s (%s) values (%s)";
	private static final String UPDATE = "update %s set %s where %s"; 
	private static final String SELECT = "select * from %s ";
	
	protected abstract String getTableName();
	protected abstract String getInsertColumns();
	protected abstract String getInsertValueParams();
	protected abstract List<Object> getInsertValues(T t);
	protected abstract T getObjectFromResultSet(ResultSet rs) throws SQLException;

	@Override
	public int insert(T t) throws SQLException {
		String sql = String.format(INSERT, getTableName(), getInsertColumns(), getInsertValueParams());
		Connection conn = ConnectionManager.getConnection();
		
		try(PreparedStatement stmt = (t instanceof GeneratedKeyEntity) ?
				conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) : conn.prepareStatement(sql)) {
			List<Object> param = getInsertValues(t);
			
			for (int i = 0; i < param.size(); i++) {
				stmt.setObject(i + 1, param.get(i));
			}
			
			int result = stmt.executeUpdate();
			
			if(t instanceof GeneratedKeyEntity) {
				ResultSet rs = stmt.getGeneratedKeys();
				while(rs.next()) {
					((GeneratedKeyEntity)t).setId(rs.getInt(1));
				}
			}
			
			return result;
		}
	}

	@Override
	public int update(String set, String where, List<Object> param) throws SQLException {
		
		String sql = String.format(UPDATE, getTableName(), set, where);
		Connection conn = ConnectionManager.getConnection();
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			for (int i = 0; i < param.size(); i++) {
				stmt.setObject(i + 1, param.get(i));
			}
			
			return stmt.executeUpdate();
		} 
	}

	@Override
	public int delete(String where, List<Object> param) throws SQLException {
		String sql = String.format(DELETE, getTableName(), where);
		Connection conn = ConnectionManager.getConnection();
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			for (int i = 0; i < param.size(); i++) {
				stmt.setObject(i + 1, param.get(i));
			}
			return stmt.executeUpdate();
		} 
	}

	@Override
	public List<T> select(String where, List<Object> param)  {
		List<T> list = new ArrayList<>();
		String sql = String.format(SELECT, getTableName());
		
		if(null != where && !where.isEmpty()) {
			sql = sql.concat("where ").concat(where);
		}
		
		try(Connection conn = ConnectionManager.generate();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			if(null != where && null != param && param.size() > 0) {
				for (int i = 0; i < param.size(); i++) {
					stmt.setObject(i + 1, param.get(i));
				}
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				list.add(getObjectFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
