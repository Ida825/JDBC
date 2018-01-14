package jdbc.day170912;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DbUtils {
	static Properties pro = new Properties();
	static {
		InputStream is = DbUtils.class.getResourceAsStream("jdbc.properties");
		try {
			pro.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取连接
	 * @return
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException{
		String url = pro.getProperty("url");
		String username = pro.getProperty("username");
		String password = pro.getProperty("password");
		Connection con = DriverManager.getConnection(url, username, password);
		return con;
	}
	
}
