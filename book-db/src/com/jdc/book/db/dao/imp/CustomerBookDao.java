package com.jdc.book.db.dao.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.jdc.book.db.dao.BaseDao;
import com.jdc.book.db.entity.CustomerBook;

public class CustomerBookDao extends BaseDao<CustomerBook> {

	@Override
	protected String getTableName() {
		return "customer_book";
	}

	@Override
	protected String getInsertColumns() {
		return "customer_id, book_id, sell_date, amount, total";
	}

	@Override
	protected String getInsertValueParams() {
		return "?, ?, ?, ?, ?";
	}

	@Override
	protected List<Object> getInsertValues(CustomerBook t) {
		return Arrays.asList(t.getCustomer_id(), t.getBook_id(), t.getSell_date(), t.getAmount(), t.getTotal());
	}

	@Override
	protected CustomerBook getObjectFromResultSet(ResultSet rs) throws SQLException {
		CustomerBook cb = new CustomerBook();
		cb.setAmount(rs.getInt("amount"));
		cb.setBook_id(rs.getInt("book_id"));
		cb.setCustomer_id(rs.getInt("customer_id"));
		cb.setSell_date(new Date(rs.getTimestamp("sell_date").getTime()));
		cb.setTotal(rs.getDouble("total"));
		return cb;
	}

}
