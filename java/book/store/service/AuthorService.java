package book.store.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import book.store.db.util.DBUtil;
import book.store.entity.Author;


public class AuthorService {
	public static List<Author> getAuthorsByBookISBN(int bookISBN) throws SQLException {
		// connect to DB
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Author author;
		List<Author> list = new ArrayList<Author>();

		try {
			// make connection to mysql
			conn = DBUtil.makeConnection();
			String query = "SELECT a.* FROM project.author a " +
                    "JOIN book_author ba ON a.id = ba.author_id " +
                    "WHERE ba.book_isbn = ?";

			ps = conn.prepareStatement(query);
			ps.setInt(1, bookISBN);
			// execute and get result SET
			rs = ps.executeQuery();

			// mapping data in result set (IF RESULT SET HAS DATA) into ENTITY CLASS
			while (rs.next()) {
				int isbn = rs.getInt("isbn");
				String title = rs.getString("name");
				String description = rs.getString("description");


				author = new Author(isbn, description);
				list.add(author);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		return list;
	}


}
