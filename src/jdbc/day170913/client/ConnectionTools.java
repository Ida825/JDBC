package jdbc.day170913.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * ���ӹ�����
 * @author Administrator
 *
 */
public class ConnectionTools {
	static Properties p = new Properties();
	static{
		try {
			//�������ļ��е���Ϣ����Properties������
			p.load(new FileInputStream("src/jdbc/day170913/client/mysql.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �������ӵķ���
	 * @return  Connection����
	 */
	public static Connection getConnection(){
		Connection con = null;
		try {
			//����������
			Class.forName(p.getProperty("driverClass"));
			try {
				//jdbc:mysql://localhost:3306/test
				//ƴ��URL
				String url = "jdbc:mysql://"+p.getProperty("host")+":"+p.getProperty("port")+"/"+p.getProperty("dbname");			
				//��ȡ����
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
