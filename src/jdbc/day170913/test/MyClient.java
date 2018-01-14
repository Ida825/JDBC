package jdbc.day170913.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

/**
 * DatabaseMetaData (interface)
 * 获取数据库的结构信息 表信息 视图
 * ResultSetMetaData (interface)
 * 获取查询列的信息
 * 
 * 实现MySQL客户端（select、cud、use、show databases、show tables、desc）
 * @author Administrator
 *
 */
public class MyClient {
	static Properties p = new Properties();
	static Scanner sc = new Scanner(System.in);
	static{
		System.out.println("Microsoft Windows [版本 6.1.7601]\n版权所有 (c) 2009 Microsoft Corporation。保留所有权利。");
		System.out.print("C:/Users/Administrator>");
	}
	//mysql -uroot -p123456
	
	public static void main(String[] args) {
		//获取用户输入登录信息
		//mysql -uroot -p123456 -hlocalhost -P3306 test
		String str = sc.nextLine();		
		login(str);
		//获取连接
		Connection con = getConnection();
		if(con==null){
			return;
		}	
		
		menu(con);	
	}

	/**
	 * 切换数据库
	 * @param con
	 * @param dbname
	 */

	private static void use(Connection con,String dbname){
		String sql = "use "+dbname;
		try {
			Statement ps = con.createStatement();
			ps.execute(sql);
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 用户输入的SQL语句
	 * @param con
	 */
	public static void menu(Connection con){		
		while(true){
			System.out.print("mysql>");
			//接收用户输入的SQL语句
			String str = sc.nextLine();
			str = str.trim().toLowerCase().replace(" +"," ");//去掉多余的空格
			if(str.startsWith("insert ") || str.startsWith("update ") || str.startsWith("delete ") || str.startsWith("select ")){
				//增删改查
				crud(con, str);				
			}else if(str.startsWith("use ")){
				String newStr = str.replace("use ","");//获取要切换的数据库名
				p.setProperty("dbname",newStr );
				try {
					DatabaseMetaData dd = con.getMetaData();
					//获取所有数据库名
					ResultSet rs = dd.getCatalogs();
					boolean bool = false;
					while(rs.next()){					
						String dname = rs.getString("TABLE_CAT");
						//如果存在该数据库就替换
						if(dname.equalsIgnoreCase(newStr)){
							con = getConnection();
							System.out.println("Database changed");
							bool = true;
							break;
						}
					}
					if(bool==false){
						System.out.println("Unknown database "+newStr);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}			
			}else if(str.startsWith("show ")){				
				if(str.split(" ")[1].equalsIgnoreCase("databases;")){					
					show_databases(con,str);
				}
				
				if(str.split(" ")[1].equalsIgnoreCase("tables")){
					show_tables(con,str);
				}
				
			}else if(str.startsWith("desc ")){
				desc(con,str);
			}		
		}
	}

	private static void crud(Connection con, String str) {
		if(str.startsWith("insert ")){
			insert(con,str);
		}else if(str.startsWith("update ")){
			update(con,str);
		}else if(str.startsWith("delete ")){
			delete(con,str);
		}else if(str.startsWith("select ")){
			select(con,str);
		}
	}

	/**
	 * 查看表结构
	 * @param con
	 * @param str
	 */
	private static void desc(Connection con, String str) {
		try {
			PreparedStatement ps = con.prepareStatement(str);
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
			e.printStackTrace();
		}
	}

	/**
	 * 显示所有表
	 * @param con
	 * @param str
	 */
	private static void show_tables(Connection con, String str) {
		try {
			PreparedStatement ps = con.prepareStatement(str);
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
	 * @param con
	 * @param str
	 */
	private static void show_databases(Connection con, String str) {
		try {
			DatabaseMetaData dd = con.getMetaData();
			System.out.println(dd.getCatalogTerm());
			ResultSet rs = dd.getCatalogs();
			while(rs.next()){
				//获取数据库名
				String newStr = rs.getString("TABLE_CAT");
				System.out.println(newStr);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 查询语句
	 * @param con 连接
	 * @param str SQL语句
	 */
	private static void select(Connection con, String str) {
		try {
			PreparedStatement ps = con.prepareStatement(str);	
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
	 * 删除语句
	 * @param con
	 * @param str
	 */
	private static void delete(Connection con, String str) {
		try {
			//预编译SQL语句
			PreparedStatement ps = con.prepareStatement(str);
			//获取受影响的行数
			int len = ps.executeUpdate();
			if(len>=0){
				System.out.println("Query OK, "+len+" row affected");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 修改语句
	 * @param con
	 * @param str
	 */
	private static void update(Connection con, String str) {
		
		try {
			//预编译SQL语句
			PreparedStatement ps = con.prepareStatement(str);
			int len = ps.executeUpdate();
			if(len>=0){
				System.out.println("Query OK, "+len+" row affected");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 插入语句
	 * @param con
	 * @param str
	 */
	private static void insert(Connection con, String str) {
		try {
			PreparedStatement ps = con.prepareStatement(str);
			int len = ps.executeUpdate();
			if(len>0){
				System.out.println("Query OK, "+len+" row affected");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 获取连接
	 * @return
	 */
	public static Connection getConnection(){
		Connection con = null;
		try {
			//加载驱动类
			Class.forName("com.mysql.jdbc.Driver");
			try {
				//jdbc:mysql://localhost:3306/test
				String url = "jdbc:mysql://"+p.getProperty("host")+":"+p.getProperty("port")+"/"+p.getProperty("dbname");
				//给URL赋值
				p.setProperty("url",url );
				//获取连接
				con = DriverManager.getConnection(p.getProperty("url"),p.getProperty("username"), p.getProperty("password"));				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return con;
	}
	
	/**
	 * 用户登录
	 * @param str
	 */
	private static void login(String str) {
		str = str.replace(" +"," ").trim();
		String[] newStr =str.split(" ");
		if(!newStr[0].equalsIgnoreCase("MYSQL")){
			System.out.println("'"+newStr[0]+"'不是内部或外部命令，也不是可运行的程序或批处理文件。");
			return;
		}

		String username = "";
		String password = "";
		String host = "localhost";
		String port = "3306";
		String dbname = "mysql";
		
		for(int i=1;i<newStr.length;i++){
			//截取密码
			if(newStr[i].startsWith("-p")){
				password = newStr[i].replace("-p", "");
			}
			//截取端口号
			if(newStr[i].startsWith("-P")){
				port = newStr[i].replace("-P", "");
			}
			//截取用户名
			if(newStr[i].startsWith("-u")){
				username = newStr[i].replace("-u", "");
			}
			//截取用户IP
			if(newStr[i].startsWith("-h")){
				host = newStr[i].replace("-h", "");
			}
			
			//截取数据库名
			if(!newStr[i].startsWith("-")){
				dbname = newStr[i];
			}
		}
		
		p.setProperty("username", username);
		p.setProperty("password", password);
		p.setProperty("dbname",dbname);
		p.setProperty("host", host);
		p.setProperty("port",port);
	}
	
}
