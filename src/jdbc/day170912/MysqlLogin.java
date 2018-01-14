package jdbc.day170912;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * connection 用于和数据库进行连接的类
 * DriverManager 驱动管理类  通过获取不同的数据库的驱动类 实现不同的登录方式
 * @author Administrator
 *
 */
public class MysqlLogin {

	public static void main(String[] args) {
		//jdbc连接的数据库服务器的IP 端口  数据库名
		//mysql 的URL：jdbc:mysql://localhost:3306/数据库名
		//oracle 的URL ：jdbc:oracle:thin:@server:1521:sid(orcl)
		String url = "jdbc:mysql://localhost:3306/test";
		//告诉jdbc使用的是什么数据库  不同数据库提供一些不同类型
		String driverClass="com.mysql.jdbc.Driver";
		
		String username = "root";
		String password = "123456";		
		try {
			//需要使用jvm加载该类
			Class.forName(driverClass);
			//登录成功
			Connection con = DriverManager.getConnection(url, username, password);		
			//System.out.println(con);
			//Statement用于执行静态的SQL语句
			Statement sta = con.createStatement();
			//ResultSet 游标遍历数据行 next()
			ResultSet sr = sta.executeQuery("select * from student");
			//通过next()判断是否有下一行
			while(sr.next()){
				//用列名来抓取数据
				String uname = sr.getString("sname");
				String sex = sr.getString("ssex");
				//通过列索引来抓取（从1开始）数据
				int age = sr.getInt(3);
				System.out.println(uname+"--"+sex+"--"+age);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}

}
