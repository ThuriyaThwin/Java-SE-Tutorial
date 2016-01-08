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
import com.jdc.book.db.dao.imp.AuthorDao;
import com.jdc.book.db.entity.Author;

public class AuthorDaoTest {

	private Dao<Author> dao;

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
		dao = new AuthorDao();
	}

	@Test
	public void test1() {
		Author a = new Author();
		a.setName("AAAA");
		a.setPhoto("BBBB");
		a.setBiography("CCCC");

		int result;
		try {
			ConnectionManager.begin();
			result = dao.insert(a);
			ConnectionManager.commit();
			assertEquals(1, result);
		} catch (SQLException e) {
			try {
				ConnectionManager.rollback();
			} catch (SQLException e1) {
			}
		}

	}

	@Test
	public void test2() {
		// select test
		List<Author> list = dao.select(null, null);
		assertEquals(1, list.size());

		Author a = list.get(0);
		assertEquals("AAAA", a.getName());
		assertEquals("BBBB", a.getPhoto());
		assertEquals("CCCC", a.getBiography());
		assertEquals(1, a.getId());
	}

	@Test
	public void test3() {

		// update
		String set = "name = ? ";
		String where = "id = ? ";
		List<Object> param = Arrays.asList("Chit Oo Nyo", 1);
		
		try {
			ConnectionManager.begin();
			int result = dao.update(set, where, param);
			ConnectionManager.commit();
			assertEquals(1, result);
			
		} catch (SQLException e) {
			try {
				ConnectionManager.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test
	public void test4() {

		// where
		// select test
		List<Author> list = dao.select("id = ? ", Arrays.asList(1));
		assertEquals(1, list.size());
		Author a = list.get(0);
		assertEquals("Chit Oo Nyo", a.getName());
		assertEquals("BBBB", a.getPhoto());
		assertEquals("CCCC", a.getBiography());
		assertEquals(1, a.getId());

	}

	@Test
	public void test5() {

		// delete
		int result;
		try {
			
			ConnectionManager.begin();
			result = dao.delete("id = ? ", Arrays.asList(1));
			ConnectionManager.commit();
			assertEquals(1, result);

			List<Author> list = dao.select("id = ? ", Arrays.asList(1));
			assertEquals(0, list.size());
		} catch (SQLException e) {
			try {
				ConnectionManager.rollback();
			} catch (SQLException e1) {
			}
		}
	}

}
