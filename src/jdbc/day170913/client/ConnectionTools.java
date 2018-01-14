package jdbc.day170913.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * 连接工具类
 * @author Administrator
 *
 */
public class ConnectionTools {
	static Properties p = new Properties();
	static{
		try {
			//将配置文件中的信息读入Properties集合中
			p.load(new FileInputStream("src/jdbc/day170913/client/mysql.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回连接的方法
	 * @return  Connection连接
	 */
	public static Connection getConnection(){
		Connection con = null;
		try {
			//加载驱动类
			Class.forName(p.getProperty("driverClass"));
			try {
				//jdbc:mysql://localhost:3306/test
				//拼接URL
				String url = "jdbc:mysql://"+p.getProperty("host")+":"+p.getProperty("port")+"/"+p.getProperty("dbname");			
				//获取连接
				con = DriverManager.getConnection(url,p.getProperty("username"), p.getProperty("password"));				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return con;
	}
}
