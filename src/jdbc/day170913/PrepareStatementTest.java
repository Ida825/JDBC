package jdbc.day170913;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.ResultSetMetaData;

public class PrepareStatementTest {
	
	public static void main(String[] args) {
		try {
			//sql注入:' or 1=1 or 1= '
			query("MANAGER","B");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 定义方法 可以传入以下参数query(ename,job,sal)，如果传入了某几个参数 以参数组合的形式查询结果
	    比如调用过程如下
		   query('Cleck',null,null);
		   查询的sql为
		   select * from emp where ename like '%Cleck%';
		    query('Cleck','Manager',null);
		   查询的sql为
		   select * from emp where ename like '%Cleck%' and job like '%Manager%'
		   要求输出查询的结果
	 * @param ename
	 * @param job
	 * @param sal
	 * @throws SQLException 
	 */
	public static void query(String job,String ename) throws SQLException{
		//创建SQL语句
		String sql = "select * from emp where job = ? and ename like ?";
		//获取连接
		Connection con = DbUtils.getConnection();
		//执行SQL语句
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, job);
		ps.setString(2, "%"+ename+"%");
		//返回一个查询结果集
		ResultSet rs = ps.executeQuery();
		//用next判断是否存在下一行
		while(rs.next()){
			String nameVar = rs.getString("ename");
			String jobVar = rs.getString("job");
			String salVar = rs.getString("sal");
			System.out.println(nameVar+"--"+jobVar+"--"+salVar);
		}
	}
	

	
}
