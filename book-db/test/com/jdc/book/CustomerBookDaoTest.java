package com.jdc.book;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jdc.book.db.dao.ConnectionManager;
import com.jdc.book.db.dao.Dao;
import com.jdc.book.db.dao.imp.AuthorDao;
import com.jdc.book.db.dao.imp.BookDao;
import com.jdc.book.db.dao.imp.CategoryDao;
import com.jdc.book.db.dao.imp.CustomerBookDao;
import com.jdc.book.db.dao.imp.CustomerDao;
import com.jdc.book.db.entity.Author;
import com.jdc.book.db.entity.Book;
import com.jdc.book.db.entity.Category;
import com.jdc.book.db.entity.Customer;
import com.jdc.book.db.entity.CustomerBook;

public class CustomerBookDaoTest {

	private Dao<CustomerBook> dao;

	@BeforeClass
	public static void init() {
		try {
			
			ConnectionManager.truncate("author", "book", "category", "customer", "customer_book");

			ConnectionManager.begin();
			// create category
			Category c = new Category();
			c.setCategory("AAAA");

			new CategoryDao().insert(c);
			
			// create author
			Author a = new Author();
			a.setName("AAAA");
			a.setPhoto("BBBB");
			a.setBiography("CCCC");

			new AuthorDao().insert(a);

			Book b = new Book();
			b.setName("AAAA");;
			b.setCategory_id(1);
			b.setAuthor_id(1);
			b.setIssue_date(new Date());
			b.setPrice(2000);
			b.setShort_description("Short Description");
			
			new BookDao().insert(b);
			
			Customer u = new Customer();
			u.setName("AAAA");
			u.setAddress("BBBB");
			u.setMail("CCCC");
			u.setPhone("DDDD");
			
			new CustomerDao().insert(u);
			
			ConnectionManager.commit();
			
		} catch(SQLException e) {
			try {
				ConnectionManager.rollback();
			} catch (SQLException e1) {
			}
		}

	}
	
	@AfterClass
	public static void afterClass(){
		ConnectionManager.truncate("author", "book", "category", "customer", "customer_book");
	}

	@Before
	public void setUp() throws Exception {
		dao = new CustomerBookDao();
	}

	@Test
	public void test1() {
		try {
			
			ConnectionManager.begin();
			
			CustomerBook cb = new CustomerBook();
			cb.setAmount(1);
			cb.setTotal(2000);
			cb.setCustomer_id(1);
			cb.setBook_id(1);
			cb.setSell_date(new Date());
			
			assertEquals(1, dao.insert(cb));
			ConnectionManager.commit();
			
		} catch(SQLException e) {
			try {
				ConnectionManager.rollback();
			} catch (SQLException e1) {
			}
		}
	}

	@Test
	public void test2() {
		// select test
		List<CustomerBook> list = dao.select(null, null);
		assertEquals(1, list.size());

		CustomerBook a = list.get(0);
		assertTrue(2000 == a.getTotal());;
		assertEquals(1, a.getAmount());
	}

	@Test
	public void test3() {

		try {
			
			ConnectionManager.begin();
			
			// update
			String set = "total = ? ";
			String where = "book_id = ? and customer_id = ?";
			
			List<Object> param = Arrays.asList(3000, 1, 1);

			int result = dao.update(set, where, param);
			assertEquals(1, result);

			ConnectionManager.commit();
			
		} catch(SQLException e) {
			try {
				ConnectionManager.rollback();
			} catch (SQLException e1) {
			}
		}
	}

	@Test
	public void test4() {

		// where
		// select test
		List<CustomerBook> list = dao.select("book_id = ? and customer_id = ?", Arrays.asList(1, 1));
		assertEquals(1, list.size());

		CustomerBook a = list.get(0);
		assertTrue(3000 == a.getTotal());

	}

	@Test
	public void test5() {
		try {
			
			ConnectionManager.begin();
			// delete
			int result = dao.delete("book_id = ? and customer_id = ?", Arrays.asList(1, 1));
			assertEquals(1, result);
			ConnectionManager.commit();

			List<CustomerBook> list = dao.select("book_id = ? and customer_id = ?", Arrays.asList(1, 1));
			assertEquals(0, list.size());
			
			
		} catch(SQLException e) {
			try {
				ConnectionManager.rollback();
			} catch (SQLException e1) {
			}
		}

	}

}
