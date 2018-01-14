package jdbc.day170913;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CRUD {
	/**
	 * 查询
	 */
	@Test
	public void select(){
		//创建SQL语句
		String sql = "select * from emp";
		
		try {
			//获取连接
			Connection con = DbUtils.getConnection();
			//预先编译SQL语句
			PreparedStatement ps = con.prepareStatement(sql);
			//执行SQL语句
			ResultSet rs = ps.executeQuery();
			//用next判断是否还有下一行
			while(rs.next()){
				String empno = rs.getString("empno");
				String ename = rs.getString("ename");
				String sal = rs.getString("sal");
				String comm = rs.getString("comm");
				
				System.out.println(empno+"--"+ename+"--"+sal+"--"+comm);
			}
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加
	 */
	//@Test
	public void insert(){
		String sql = "insert into emp (empno,ename,sal) values ((select max(empno)+10 from emp),?,?)";
		
		try {
			//获取连接
			Connection con = DbUtils.getConnection();
			//预编译SQL语句
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,"LL");
			ps.setString(2,"1000");
			ps.executeUpdate();
			ps.close();
			con.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 修改
	 */
	//@Test
	public void update(){
		//创建SQL语句
		String sql = "update emp set sal = ? where ename = ?";
		
		try {
			//获取连接
			Connection con = DbUtils.getConnection();
			//预先编译SQL语句
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "2200");
			ps.setString(2, "LL");
			//执行SQL语句
			ps.executeUpdate();
			ps.close();
			con.close();//关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 删除
	 */
	//@Test
	public void delete(){
		String sql = "delete from emp where ename = ?";
		
		try {
			//获取连接
			Connection con = DbUtils.getConnection();
			//预编译SQL语句
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "LL");
			//执行SQL语句
			ps.executeUpdate();
			//关闭连接
			ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 事务练习
	 */
	//@Test
	public void tran(){		
		//获取连接
		Connection con = null;
		PreparedStatement ps = null;
		try {
			String sql = "delete from emp where empno=6666";
			con = DbUtils.getConnection();
			con.setAutoCommit(false);//设置不自动提交
			//预编译SQL语句
			ps = con.prepareStatement(sql);
			//执行SQL语句
			ps.executeUpdate();
			ps.close();
			
			String str = null;
			str.toString();//这里会报错
			
			
			String sql1 = "delete from emp where empno=8888";
			ps = con.prepareStatement(sql1);
			ps.executeUpdate();
			ps.close();
			con.close();
			con.commit();
		
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();//回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	static class Dept{
		public String dname;
		public String dloc;
	}
	static List<Dept> list = new ArrayList<>();
	static{
		Dept d = null;
		for(int i=0;i<100;i++){
			d=new Dept();
			d.dname="zs"+i;
			d.dloc="身份"+i;
			list.add(d);
		}
	}
	
	//@Test
	public void batchInsert(){
		int next = 8000;
		String sql = "insert into dept values(?,?,?) ";
		//获取连接
		try {
			Connection con = DbUtils.getConnection();
			//预编译SQL语句
			PreparedStatement ps = con.prepareStatement(sql);
			
			for(int i=0;i<list.size();i++){
				Dept d = list.get(i);
				next=next+1;
				ps.setInt(1,next);
				ps.setString(2,d.dname);
				ps.setString(3,d.dloc);
				ps.addBatch();//添加批处理
			}
			
			int[] result = ps.executeBatch();//返回受影响的数
			System.out.println(result.length);
			for (int i : result) {
				System.out.println(i);
			}
			ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

}
