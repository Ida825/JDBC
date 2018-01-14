package jdbc.day170912;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OracleLogin {

	public static void main(String[] args) {
		//声明URL
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		//告诉jdbc是什么数据库(驱动类)
		String driverClass = "oracle.jdbc.OracleDriver";
		//用户
		String username = "stu";
		//密码
		String password = "123456";
				
		try {
			//用jvm加载驱动类
			Class.forName(driverClass);	
			//获取连接
			Connection con = DriverManager.getConnection(url, username, password);
			//Statement用于执行静态SQL语句
			Statement st = con.createStatement();
			//ResultSet 游标遍历数据行
			ResultSet rs = st.executeQuery("select * from student");
			//用next判断是否有下一行
			while(rs.next()){
				String name = rs.getString("sname");
				int age = rs.getInt("sage");
				String sex = rs.getString("ssex");
				System.out.println(name+","+age+","+sex);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
