package com.jdc.book;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jdc.book.db.dao.ConnectionManager;
import com.jdc.book.db.dao.Dao;
import com.jdc.book.db.dao.imp.CustomerDao;
import com.jdc.book.db.entity.Customer;

public class CustomerDaoTest {

	private Dao<Customer> dao;

	@BeforeClass
	public static void init() {
		ConnectionManager.truncate("author", "book", "category", "customer", "customer_book");
	}
	
	@AfterClass
	public static void afterClass(){
		ConnectionManager.truncate("author", "book", "category", "customer", "customer_book");
	}

	@Before
	public void setUp() {
		dao = new CustomerDao();
	}

	@Test
	public void test1() {
		try {
			
			ConnectionManager.begin();

			Customer a = new Customer();
			a.setName("AAAA");
			a.setAddress("BBBB");
			a.setMail("CCCC");
			a.setPhone("DDDD");

			int result = dao.insert(a);

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
	public void test2() {
		// select test
		List<Customer> list = dao.select(null, null);
		assertEquals(1, list.size());

		Customer a = list.get(0);
		assertEquals("AAAA", a.getName());
		assertEquals("BBBB", a.getAddress());
		assertEquals("CCCC", a.getMail());
		assertEquals("DDDD", a.getPhone());
		assertEquals(1, a.getId());
	}

	@Test
	public void test3() {

		try {
			
			ConnectionManager.begin();
			// update
			String set = "name = ? ";
			String where = "id = ? ";
			List<Object> param = Arrays.asList("Cho Cho", 1);

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
		List<Customer> list = dao.select("id = ? ", Arrays.asList(1));
		assertEquals(1, list.size());

		Customer a = list.get(0);
		assertEquals("Cho Cho", a.getName());
		assertEquals("BBBB", a.getAddress());
		assertEquals("CCCC", a.getMail());
		assertEquals("DDDD", a.getPhone());
		assertEquals(1, a.getId());

	}

	@Test
	public void test5() {

		try {
			
			ConnectionManager.begin();
			
			// delete
			int result = dao.delete("id = ? ", Arrays.asList(1));
			assertEquals(1, result);
			ConnectionManager.commit();

			List<Customer> list = dao.select("id = ? ", Arrays.asList(1));
			assertEquals(0, list.size());

			
		} catch(SQLException e) {
			try {
				ConnectionManager.rollback();
			} catch (SQLException e1) {
			}
		}
	}

}
