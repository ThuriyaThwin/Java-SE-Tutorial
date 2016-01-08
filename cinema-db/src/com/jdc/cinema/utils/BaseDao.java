package com.jdc.cinema.utils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.jdc.cinema.dao.Column;
import com.jdc.cinema.dao.Dao;
import com.jdc.cinema.dao.DaoException;
import com.jdc.cinema.dao.Entity;
import com.jdc.cinema.dao.ID;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.dao.Table;
import com.jdc.cinema.dao.Temporal;
import com.jdc.cinema.dao.Temporal.Type;

public class BaseDao<T extends Entity> implements Dao<T> {
	
	private Class<T> type;
	
	public BaseDao(Class<T> type) {
		super();
		this.type = type;
	}

	@Override
	public T insert(T t) {

		String sql = "insert into %s (%s) values (%s)";
		sql = String.format(sql, getTableName(), t.getInsertParam().getKeys(),
				t.getInsertParam().getValueString());

		if (isGeneratedId()) {

			// for auto increase id
			try (Connection conn = ConnectionManager.getConnection();
					PreparedStatement stmt = conn.prepareStatement(sql, 
							Statement.RETURN_GENERATED_KEYS)) {

				List<Object> params = t.getInsertParam().getValues();

				for (int i = 0; i < params.size(); i++) {
					stmt.setObject(i + 1, params.get(i));
				}

				stmt.executeUpdate();

				ResultSet rs = stmt.getGeneratedKeys();
				while(rs.next()) {
					Object id = rs.getObject(1);
					return this.setId(t, id);
				}
			} catch (Exception e) {
				throw new DaoException(e);
			}

		} else {
			try (Connection conn = ConnectionManager.getConnection();
					PreparedStatement stmt = conn.prepareStatement(sql)) {
				List<Object> params = t.getInsertParam().getValues();

				for (int i = 0; i < params.size(); i++) {
					stmt.setObject(i + 1, params.get(i));
				}

				stmt.executeUpdate();

				return t;

			} catch (SQLException e) {
				throw new DaoException(e);
			}
		}

		return null;
	}

	@Override
	public int update(String set, String where, List<Object> param) {

		String sql = String.format("update %s set %s where %s", getTableName(),
				set, where);

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			for (int i = 0; i < param.size(); i++) {
				stmt.setObject(i + 1, param.get(i));
			}

			return stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException(e);
		}

	}

	@Override
	public int delete(String where, List<Object> param) {

		String sql = String.format("delete from %s where %s", getTableName(),
				where);

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			for (int i = 0; i < param.size(); i++) {
				stmt.setObject(i + 1, param.get(i));
			}

			return stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public T findById(IdParam id) {

		String sql = String.format("select * from %s where %s", getTableName(),
				id.query());
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			List<Object> param = id.values();
			
			for (int i = 0; i < param.size(); i++) {
				stmt.setObject(i + 1, param.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				return getObject(rs);
			}


		} catch (Exception e) {
			throw new DaoException(e);
		}

		return null;
	}

	@Override
	public List<T> getWhere(String where, List<Object> param) {
		
		String sql = String.format("select * from %s", getTableName());
		
		if(null != where && !where.isEmpty()) {
			sql = sql + " where " + where;
		}
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			if(null != param) {
				for (int i = 0; i < param.size(); i++) {
					stmt.setObject(i + 1, param.get(i));
				}
			}
			
			List<T> list = new ArrayList<>();
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				list.add(getObject(rs));
			}

			return list;
			
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	private T getObject(ResultSet rs) throws InstantiationException, IllegalAccessException, SQLException  {
		
		T obj = type.newInstance();
		Field [] fs = type.getDeclaredFields();
		
		for(Field f : fs) {
			f.setAccessible(true);
			String columnName = getColumnName(f);
			
			if(!"serialversionuid".equals(columnName)) {
				
				Object value = null;
				
				if("password".equals(columnName)) {
					value = rs.getString(columnName);
				} else {
					value = rs.getObject(columnName);
				}
				
				if(null != value) {
					
					Type temporalType = temporalType(f);
					
					if(null != temporalType) {
						
						if(temporalType.equals(Type.DATE)) {
							Date date = (Date)value;
							f.set(obj, new java.util.Date(date.getTime()));
						} else if (temporalType.equals(Type.TIMESTAMP)) {
							Timestamp date = (Timestamp)value;
							f.set(obj, new java.util.Date(date.getTime()));
						} else if (temporalType.equals(Type.TIME)) {
							f.set(obj, value);
						}
						
					} else {
						f.set(obj, value);
					}
				}
			}
			
		}
		
		return obj;
	}
	
	private Type temporalType(Field f) {
		Temporal temporal = f.getAnnotation(Temporal.class);
		if(null != temporal) {
			return temporal.value();
		}
		return null;
	}

	private boolean isGeneratedId() {
		
		Field [] fs = type.getDeclaredFields();
		
		for(Field f : fs) {
			f.setAccessible(true);
			ID id = f.getAnnotation(ID.class);
			if(null != id) {
				return id.generated();
			}
		}
		
		return false;
	}
	
	private T setId(T t, Object id) throws IllegalArgumentException, IllegalAccessException {
		Field [] fs = type.getDeclaredFields();
		
		for(Field f : fs) {
			f.setAccessible(true);
			ID idField = f.getAnnotation(ID.class);
			if(null != idField) {
				f.set(t, id);
			}
		}
		
		return t;
	}

	private String getColumnName(Field field) {
		
		Column column = field.getAnnotation(Column.class);
		
		if(null == column) {
			return field.getName().toLowerCase();
		}
		
		return column.value();
	}

	private String getTableName() {
		
		Table annotation = type.getAnnotation(Table.class);
		
		if(null == annotation) {
			return type.getSimpleName().toLowerCase();
		}

		return annotation.value();
	}

	@Override
	public List<T> getAll() {
		return this.getWhere(null, null);
	}

	@Override
	public int getAllCount() {
		String sql = String.format("select count(*) from %s", getTableName());
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return 0;
	}

	@Override
	public int getWhereCount(String where, List<Object> param) {
		String sql = String.format("select count(*) from %s", getTableName());
		
		if(null != where && !where.isEmpty()) {
			sql = sql + " where " + where;
		}
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			if(null != param) {
				for (int i = 0; i < param.size(); i++) {
					stmt.setObject(i + 1, param.get(i));
				}
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				return rs.getInt(1);
			}

			return 0;
			
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public <U>List<U> find(String sql, List<Object> param, Function<ResultSet, U> converter) {

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			
			List<U> list = new ArrayList<>();
			
			for(int i=0; i < param.size(); i++) {
				stmt.setObject(i + 1, param.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				list.add(converter.apply(rs));
			}
			
			return list;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

}
