package com.jdc.book.app.models.imp;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.jdc.book.app.BookAppException;
import com.jdc.book.app.controllers.utils.CartItem;
import com.jdc.book.app.models.BookModel;
import com.jdc.book.app.models.CheckOutModel;
import com.jdc.book.app.models.CustomerModel;
import com.jdc.book.db.dao.ConnectionManager;
import com.jdc.book.db.dao.Dao;
import com.jdc.book.db.dao.imp.CustomerBookDao;
import com.jdc.book.db.dao.imp.CustomerDao;
import com.jdc.book.db.entity.Book;
import com.jdc.book.db.entity.Customer;
import com.jdc.book.db.entity.CustomerBook;

public class CheckOutModelImp implements CheckOutModel {
	
	private CustomerModel custModel;
	private BookModel bookModel;
	private Dao<CustomerBook> custBookDao;
	private Dao<Customer> custDao;
	
	public CheckOutModelImp() {
		custModel = new CustomerModelImp();
		bookModel = new BookModelImp();
		custBookDao = new CustomerBookDao();
		custDao = new CustomerDao();
	}

	@Override
	public List<CustomerBook> find(LocalDate dtFrom, LocalDate dtTo, String customer, String book) {

		List<Customer> custList = null;
		List<Book> bookList = null;
		
		String where = "sell_date >= ? and sell_date <= ? ";
		List<Object> params = Arrays.asList(
				Timestamp.from(dtFrom.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()), 
				Timestamp.from(dtTo.atTime(23, 59).atZone(ZoneId.systemDefault()).toInstant()));
		
		List<CustomerBook> tempList = custBookDao.select(where, params);

		if(customer != null && !customer.isEmpty()) {
			custList = custModel.find(customer, null);
		}

		if(null != book && !book.isEmpty()) {
			bookList = bookModel.search(null, null, book);
		}
		
		if(tempList != null && !tempList.isEmpty() && custList != null) {
			List<Integer> idList = custList.stream().map(a -> a.getId()).collect(Collectors.toList());
			tempList = tempList.stream().filter(a -> idList.contains(a.getCustomer_id())).collect(Collectors.toList());
		}
		
		if(tempList != null && !tempList.isEmpty() && bookList != null) {
			List<Integer> idList = bookList.stream().map(a -> a.getId()).collect(Collectors.toList());
			tempList = tempList.stream().filter(a -> idList.contains(a.getBook_id())).collect(Collectors.toList());
		}
		
		
		return tempList;
	}
	
	

	@Override
	public void checkOut(Customer c, List<CartItem> items) {
		
		try {
			ConnectionManager.begin();
			
			if(c.getId() == 0) {
				custDao.insert(c);
			}
			
			for(CartItem item : items) {
				CustomerBook cb = new CustomerBook();
				cb.setAmount(item.getCountProperty().get());
				cb.setBook_id(item.getBook().getId());
				cb.setCustomer_id(c.getId());
				cb.setSell_date(new Date());
				cb.setTotal(item.getTotalProperty().get());
				
				custBookDao.insert(cb);
			}
			
			ConnectionManager.commit();
		}catch (SQLException e) {
			try {
				ConnectionManager.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new BookAppException("SQL Exception : " + e.getSQLState(), true);
		}
		
	}
	
	public String getWhereString(int count) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i< count; i++) {
			if(i > 0) {
				sb.append(", ");
			}
			sb.append("? ");
		}
		return sb.toString();
	}
	
	public List<Integer> getCustIds(List<Customer> list) {
		return list.stream().map(a -> a.getId()).collect(Collectors.toList());
	}
	
	public List<Integer> getBookIds(List<Book> list) {
		return list.stream().map(a -> a.getId()).collect(Collectors.toList());
	}
}
