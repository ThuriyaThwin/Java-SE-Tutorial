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
import com.jdc.book.db.dao.imp.CategoryDao;
import com.jdc.book.db.entity.Category;

public class CategoryTest {
	
	private Dao<Category> dao;

	@BeforeClass
	public static void init() {
		ConnectionManager.truncate("author", "book", "category", "customer", "customer_book");
	}
	
	@AfterClass
	public static void afterClass(){
		ConnectionManager.truncate("author", "book", "category", "customer", "customer_book");
	}

	@Before
	public void setUp() throws Exception {
		dao = new CategoryDao();
	}

	@Test
	public void test1() {
		
		try {
			Category a = new Category();
			a.setCategory("AAAA");

			ConnectionManager.begin();
			int result = dao.insert(a);
			ConnectionManager.commit();
			assertEquals(1, result);
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
		List<Category> list = dao.select(null, null);
		assertEquals(1, list.size());

		Category a = list.get(0);
		assertEquals("AAAA", a.getCategory());
		assertEquals(1, a.getId());
	}

	@Test
	public void test3() {

		try {
			
			ConnectionManager.begin();
			
			// update
			String set = "category = ? ";
			String where = "id = ? ";
			List<Object> param = Arrays.asList("Noval", 1);

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
		List<Category> list = dao.select("id = ? ", Arrays.asList(1));
		assertEquals(1, list.size());

		Category a = list.get(0);
		assertEquals("Noval", a.getCategory());
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
			List<Category> list = dao.select("id = ? ", Arrays.asList(1));
			assertEquals(0, list.size());
			
			
		} catch(SQLException e) {
			try {
				ConnectionManager.rollback();
			} catch (SQLException e1) {
			}
		}
	}
}
