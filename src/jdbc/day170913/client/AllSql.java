package jdbc.day170913.client;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
/**
 * 具体操作类
 * @author Administrator
 *
 */
public class AllSql {
	/**
	 * 数据的插入、删除、修改
	 * @param str  insert、delete、update语句
	 */
	public static void crd(String str){
		PreparedStatement ps = null;
		try {
			ps = Test.con.prepareStatement(str);
			int len = ps.executeUpdate();
			if(len>0){
				System.out.println("Query OK, "+len+" row affected");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * 数据的查询
	 * @param str  select语句
	 */
	public static void select(String str) {
		try {
			PreparedStatement ps = Test.con.prepareStatement(str);	
			//获取执行的结果集
			ResultSet rs = ps.executeQuery();
			//获取查询列的信息
			ResultSetMetaData rmd = rs.getMetaData();			
			//遍历结果集并输出
			while(rs.next()){
				StringBuilder builder = new StringBuilder("");
				for(int i=1;i<=rmd.getColumnCount();i++){
					
					String newStr = rs.getString(i);				
					builder.append(newStr+" ");
				}
				System.out.println(builder);
			}			
		} catch (SQLException e) {
			System.out.println("ERROR "+e.getErrorCode()+" ("+e.getSQLState()+") :"+e.getMessage());
		}	
	}

	/**
	 * 切换数据库
	 * @param str use语句
	 */
	public static void useDatabase(String str) {
		String newStr = str.replace("use ","");//获取要切换的数据库名
		//先判断要切换的数据库是否存在
		try {
			//获取当前连接下的DatabaseMetaData对象
			DatabaseMetaData dd = Test.con.getMetaData();
			//获取所有数据库名
			ResultSet rs = dd.getCatalogs();
			boolean bool = false;
			while(rs.next()){					
				String dname = rs.getString("TABLE_CAT");
				//如果存在该数据库就替换
				if(dname.equalsIgnoreCase(newStr)){
					//将用户所查数据库名替换默认数据库名
					ConnectionTools.p.setProperty("dbname",newStr );
					//调用获取连接的方法
					Test.con = ConnectionTools.getConnection();
					System.out.println("Database changed");
					bool = true;
					break;
				}
			}
			if(bool==false){
				System.out.println("Unknown database "+newStr);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 查看表结构
	 * @param str  desc语句
	 */
	public static void desc(String str) {
		try {
			PreparedStatement ps = Test.con.prepareStatement(str);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rd = rs.getMetaData();
			
			while(rs.next()){
				StringBuilder builder = new StringBuilder("");
				for(int i=1;i<rd.getColumnCount();i++){
					String newStr = rs.getString(i);			
					builder.append(newStr+"		");
				}
				System.out.println(builder);
			}	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 显示所有表名
	 * @param str show tables语句
	 */
	public static void show_tables(String str) {
		try {
			PreparedStatement ps = Test.con.prepareStatement(str);
			ResultSet rs = ps.executeQuery();
			//获取列信息
			ResultSetMetaData rd = rs.getMetaData();
			//打印出列的标签名
			System.out.println(rd.getColumnLabel(1));
			while(rs.next()){
				StringBuilder builder = new StringBuilder("");
				for(int i=1;i<=rd.getColumnCount();i++){
					String newStr = rs.getString(i);
					builder.append(newStr);
				}
				System.out.println(builder);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 显示所有数据库
	 * @param str  show databases语句
	 */
	public static void show_databases(String str) {
		try {
			DatabaseMetaData dd = Test.con.getMetaData();
			System.out.println(dd.getCatalogTerm());
			ResultSet rs = dd.getCatalogs();
			while(rs.next()){
				//获取数据库名
				String newStr = rs.getString("TABLE_CAT");
				System.out.println(newStr);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	public static void quit(String str) {
		Menu.bool = false;
		try {
			Test.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("bye");
	}
}
