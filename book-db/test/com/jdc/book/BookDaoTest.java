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
import com.jdc.book.db.entity.Author;
import com.jdc.book.db.entity.Book;
import com.jdc.book.db.entity.Category;

public class BookDaoTest {

	private Dao<Book> dao;

	@BeforeClass
	public static void init() {
		ConnectionManager.truncate("author", "book", "category", "customer", "customer_book");

		try {
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
		dao = new BookDao();
	}

	@Test
	public void test1() {
		try {
			ConnectionManager.begin();
			Book b = new Book();
			b.setName("AAAA");;
			b.setCategory_id(1);
			b.setAuthor_id(1);
			b.setIssue_date(new Date());
			b.setPrice(2000);
			b.setShort_description("Short Description");
			int result = dao.insert(b);
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
		List<Book> list = dao.select(null, null);
		assertEquals(1, list.size());

		Book a = list.get(0);
		assertEquals("AAAA", a.getName());
		assertEquals(1, a.getId());
	}

	@Test
	public void test3() {

		try {
			// update
			String set = "name = ? ";
			String where = "id = ? ";
			List<Object> param = Arrays.asList("BBBB", 1);
			ConnectionManager.begin();
			int result = dao.update(set, where, param);
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
	public void test4() {

		// where
		// select test
		List<Book> list = dao.select("id = ? ", Arrays.asList(1));
		assertEquals(1, list.size());

		Book a = list.get(0);
		assertEquals("BBBB", a.getName());
		assertEquals(1, a.getId());

	}

	@Test
	public void test5() {
		
		try {
			// delete
			ConnectionManager.begin();
			int result = dao.delete("id = ? ", Arrays.asList(1));
			ConnectionManager.commit();
			assertEquals(1, result);

			List<Book> list = dao.select("id = ? ", Arrays.asList(1));
			assertEquals(0, list.size());
		} catch(SQLException e) {
			try {
				ConnectionManager.rollback();
			} catch (SQLException e1) {
			}
		}
	}

}
