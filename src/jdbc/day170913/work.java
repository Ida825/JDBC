package jdbc.day170913;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class work {
	public static void main(String[] args) throws SQLException {
		//tablePager("emp",2,4);
		deleteMul("CC");
		//scottInfo();
	}
	
	
	/**
	 * 1.写出一个分页的方法   定义如下
	 * tablePager(tableName,curPage,pageSize)
	 * 调用
	 * tablePager("emp",2,10)
	 * 查询emp表中 第二页的数据（每页显示10条 第二页就是 10-20条）	
	 * @param tableName 表名
	 * @param curPage 第几页
	 * @param pageSize 每页显示的条数
	 */

	public static void tablePager(String tableName,int curPage,int pageSize){
		String sql = "select * from (select t.*,rownum as rn from "+tableName+" t) where rn>=? and rn<=?";
		try {
			//获取连接
			Connection con = DbUtils.getConnection();									
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, (curPage-1)*pageSize+1);
			ps.setInt(2, curPage*pageSize);
			
			//获取结果集
			ResultSet rs = ps.executeQuery();
			//获取查询列的信息
			ResultSetMetaData rd= rs.getMetaData();			
			//遍历结果集，用next判断是否有下一行
			while(rs.next()){
				StringBuilder builder = new StringBuilder("");
				for(int i=1;i<=rd.getColumnCount();i++){
					String newStr = rs.getString(i);
					builder.append(newStr+" ");
				}				
				System.out.println(builder);
			}
			
			ps.close();
			con.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
			
	}
	
	/*public static void tablePager(String tableName,int curPage,int pageSize){
			
		try {
			//获取连接
			Connection con = DbUtils.getConnection();
			Statement st = con.createStatement();
			String sql1 = "select * from "+tableName;
			//获取结果集
			ResultSet rs = st.executeQuery(sql1);
			//获取查询列的信息
			ResultSetMetaData rd= rs.getMetaData();
			StringBuilder str = new StringBuilder("");
			for(int i=1;i<=rd.getColumnCount();i++){
				str.append(rd.getColumnName(i));
				if(i!=rd.getColumnCount()){
					str.append("||' '||");
				}
				if(i==rd.getColumnCount()){
					str.append(" a");
				}
				
			}
			//select * from (select t.*,rownum rn from emp) t where t.rn between curpage and pageSize;
			
			//定义一个SQL语句
			String sql = "select "+str+" from (select t.*,rownum as rn from "+tableName+" t) where rn>=? and rn<=?";
			//预编译SQL语句
			PreparedStatement ps = con.prepareStatement(sql);
			//ps.setString(1,tableName);
			ps.setInt(1, (curPage-1)*pageSize+1);
			ps.setInt(2, curPage*pageSize);
			//遍历结果集，用next判断是否有下一行
			ResultSet rs2 = ps.executeQuery();
			while(rs2.next()){
				String newStr = rs2.getString(1);
				System.out.println(newStr);
			}
			ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	
	/**
	 * 定义一个方法 传入表名 ，删除该表中的重复记录
	 * 比如 deleteMul(tableName)
              调用 deleteMul("emp"); 必须删除表emp的重复数据  （execute）
	 * @param tableName 表名
	 * @throws SQLException 
	 */

	public static void deleteMul(String tableName) throws SQLException{
		//select wm_concat(column_name) from user_tab_columns where table_name='EMP';
		//delete from emp where rowid not in(select min(rowid) from emp group by ename);
		String sql1 = "select wm_concat(column_name) from user_tab_columns where table_name='"+tableName+"'";
		//获取连接
		Connection con = DbUtils.getConnection();
		//创建一个可以执行静态SQL语句的Statement
		Statement st = con.createStatement();
		//执行SQL语句
		ResultSet rs = st.executeQuery(sql1);
		//获取该表的所有列
		String str = "";
		while(rs.next()){
			 str= rs.getString(1);
		}
		
		String sql2 = "delete from "+tableName+" where rowid not in(select min(rowid) from "+tableName+" group by "+str+")";
		//预编译SQL语句
		PreparedStatement ps = con.prepareStatement(sql2);
		//执行SQL语句
		ps.execute();	
	}
	
	/**
	 * 使用游标输出 scott中所有的雇员名称，部门名称，年薪
	 */

	public static void scottInfo(){
		//定义SQL语句
		String sql = "select e.ename c1,d.dname c2,sal*12 c3 from emp e inner join dept d on e.deptno = d.deptno";		
		try {
			//获取连接
			Connection con = DbUtils.getConnection();
			//预编译SQL语句
			PreparedStatement ps = con.prepareStatement(sql);
			//获取结果集
			ResultSet rs = ps.executeQuery();
			//遍历结果集,用next判断是否有下一行
			while(rs.next()){
				String ename = rs.getString("c1");
				String dname = rs.getString("c2");
				int sal = rs.getInt("c3");
				
				System.out.println(ename+"--"+dname+"--"+sal);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
