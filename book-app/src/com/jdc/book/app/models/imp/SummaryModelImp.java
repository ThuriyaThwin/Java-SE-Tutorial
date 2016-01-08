package com.jdc.book.app.models.imp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdc.book.app.BookAppException;
import com.jdc.book.app.models.Summary;
import com.jdc.book.app.models.SummaryModel;
import com.jdc.book.db.dao.ConnectionManager;

public class SummaryModelImp implements SummaryModel {
	
	private static String catSql = "select c.category, count(*) from customer_book cb "
			+ "left join book b on cb.book_id = b.id  "
			+ "left join category c on c.id = b.category_id "
			+ "group by c.category;";
	
	private static String authSql = "select a.name, count(*) from customer_book cb "
			+ "left join book b on cb.book_id = b.id  "
			+ "left join author a on a.id = b.author_id "
			+ "group by a.name;";

	@Override
	public List<Summary> getSummary(Type type) {
		String sql = (type == Type.Author)? authSql : catSql;
		
		try(Connection conn = ConnectionManager.generate();
				Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);
			List<Summary> list = new ArrayList<>();
			while(rs.next()) {
				String key = rs.getString(1);
				int value = rs.getInt(2);
				
				list.add(new Summary(key, value));
			}
			return list;
		} catch (SQLException e) {
			throw new BookAppException(e.getMessage(), false);
		}
	}

}
