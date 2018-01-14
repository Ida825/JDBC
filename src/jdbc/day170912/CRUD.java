package jdbc.day170912;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class CRUD {
	/**
	 * 查看
	 * @throws SQLException
	 */
	
	@Test
	public void select() throws SQLException{
		String sql = "select * from student";
		//获取连接
		Connection con = DbUtils.getConnection();
		//创建可以执行静态SQL语句的Statement
		Statement st = con.createStatement();
		//获取结果集
		ResultSet rs = st.executeQuery(sql);
	    //用next判断是否有下一行
		while(rs.next()){
			String sno = rs.getString("s#");
			String name = rs.getString("sname");
			String age = rs.getString("sage");
			String sex = rs.getString("ssex");
			System.out.println(sno+","+name+","+age+","+sex);
		}
	}
	
	
	
	/**
	 * 添加
	 * @throws Exception
	 */
	//@Test
	public void insert() throws Exception {
		String sql = "insert into student(s#,sname,sage,ssex) values((select max(s#)+1 from student),'刘星',25,'女')";
		//获取连接
		Connection con = DbUtils.getConnection();
		Statement st = con.createStatement();
		//执行SQL语句
		st.executeUpdate(sql);
		st.close();//关闭Statement
		con.close();//关闭连接
	}

	/**
	 * 修改
	 * @throws Exception
	 */
	//@Test
	public void update() throws Exception {
		String sql = "update student set sage = 20 where sname='王五'";
		//获取连接
		Connection con = DbUtils.getConnection();
		//创建Statement（用来执行静态SQL语句）
		Statement st = con.createStatement();
		//执行SQL语句
		st.executeUpdate(sql);
		//关闭Statement
		st.close();
		//关闭连接
		con.close();
	}

	/**
	 * 删除
	 * @throws Exception
	 */
	//@Test
	public void delete() throws Exception {
		String sql = "delete from student where sname = '钱二'";
		//获取连接
		Connection con = DbUtils.getConnection();
		//创建可以执行静态SQL语句的Statement
		Statement st = con.createStatement();
		//执行SQL语句
		st.executeUpdate(sql);
		//关闭Statement
		st.close();
		//关闭连接
		con.close();
	}

}
