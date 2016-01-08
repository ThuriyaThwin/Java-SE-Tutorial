package com.jdc.book.app.models.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jdc.book.app.BookAppException;
import com.jdc.book.app.models.CustomerModel;
import com.jdc.book.db.dao.ConnectionManager;
import com.jdc.book.db.dao.Dao;
import com.jdc.book.db.dao.imp.CustomerDao;
import com.jdc.book.db.entity.Customer;

public class CustomerModelImp implements CustomerModel {
	
	private Dao<Customer> dao;
	
	public CustomerModelImp() {
		dao = new CustomerDao();
	}

	@Override
	public void create(Customer cust) {
		try {
			ConnectionManager.begin();
			dao.insert(cust);
			ConnectionManager.commit();
		} catch (SQLException e) {
			try {
				ConnectionManager.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			throw new BookAppException(e.getSQLState(), false);
		}	
	}

	@Override
	public List<Customer> find(String name, String phone) {
		
		String where = "";
		List<Object> param = new ArrayList<>();
		
		if(null != name && !name.isEmpty()) {
			where = "name like ? ";
			param.add(name + "%");
		}
		
		if(null != phone && !phone.isEmpty()) {
			if(param.size() > 0) {
				where = where + "and ";
			}
			where = where + "phone like ? ";
			param.add(phone + "%");
			
		}
		
		return dao.select(where, param);
	}

	@Override
	public List<Customer> find(String name) {
		return this.find(name, null);
	}

	@Override
	public Customer get(int id) {
		String where = "id = ? ";
		List<Object> param = Arrays.asList(id);
		
		List<Customer> list = dao.select(where, param);
		if(null != list && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

}
