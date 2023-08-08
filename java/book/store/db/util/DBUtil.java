package book.store.db.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
	public static Connection makeConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","221197");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	}