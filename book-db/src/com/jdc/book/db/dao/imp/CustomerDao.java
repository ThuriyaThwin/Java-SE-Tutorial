package com.jdc.book.db.dao.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.jdc.book.db.dao.BaseDao;
import com.jdc.book.db.entity.Customer;

public class CustomerDao extends BaseDao<Customer>{

	@Override
	protected String getTableName() {
		return "customer";
	}

	@Override
	protected String getInsertColumns() {
		return "name, phone, mail, address";
	}

	@Override
	protected String getInsertValueParams() {
		return "?, ?, ?, ?";
	}

	@Override
	protected List<Object> getInsertValues(Customer t) {
		return Arrays.asList(t.getName(), t.getPhone(), t.getMail(), t.getAddress());
	}

	@Override
	protected Customer getObjectFromResultSet(ResultSet rs) throws SQLException {
		Customer c = new Customer();
		c.setId(rs.getInt("id"));
		c.setName(rs.getString("name"));
		c.setPhone(rs.getString("phone"));
		c.setMail(rs.getString("mail"));
		c.setAddress(rs.getString("address"));
		return c;
	}

}
