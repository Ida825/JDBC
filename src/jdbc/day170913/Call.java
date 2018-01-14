package jdbc.day170913;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.junit.Test;



public class Call {
	
	/**
	 * 调用存储过程
	 */
	@Test
	public void callProcedure(){
		String sql = "{call mulvar(?,?,?)}";
		
		try {
			//获取连接
			Connection con = DbUtils.getConnection();
			CallableStatement co = con.prepareCall(sql);
			CallableStatement cc =  con.prepareCall("select * from cc");
			System.out.println(cc.execute()+"--------------------");
			co.setInt(1, 25);
			co.setInt(2, 56);
			//设置返回值参数类型
			co.registerOutParameter(3,Types.INTEGER);
			//执行SQL语句
			co.executeUpdate();
			//获取返回的结果
			int result = co.getInt(3);
			System.out.println(result);
			co.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void callFunction(){
		//创建SQL语句
		String sql = "{?=call fun_add(?,?)}";
		
		try {
			//获取连接
			Connection con = DbUtils.getConnection();
			//调用函数
			CallableStatement cs = con.prepareCall(sql);
			cs.setInt(2, 23);
			cs.setInt(3, 55);
			//设置返回参数的类型
			cs.registerOutParameter(1,Types.INTEGER);
			//执行SQL语句
			cs.executeUpdate();
			//获取返回值
			int result = cs.getInt(1);
			System.out.println(result);
			cs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
