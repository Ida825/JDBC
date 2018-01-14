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
 * ��ȡ���ݿ�Ľṹ��Ϣ ����Ϣ ��ͼ
 * ResultSetMetaData (interface)
 * ��ȡ��ѯ�е���Ϣ
 * 
 * ʵ��MySQL�ͻ��ˣ�select��cud��use��show databases��show tables��desc��
 * @author Administrator
 *
 */
public class MyClient {
	static Properties p = new Properties();
	static Scanner sc = new Scanner(System.in);
	static{
		System.out.println("Microsoft Windows [�汾 6.1.7601]\n��Ȩ���� (c) 2009 Microsoft Corporation����������Ȩ����");
		System.out.print("C:/Users/Administrator>");
	}
	//mysql -uroot -p123456
	
	public static void main(String[] args) {
		//��ȡ�û������¼��Ϣ
		//mysql -uroot -p123456 -hlocalhost -P3306 test
		String str = sc.nextLine();		
		login(str);
		//��ȡ����
		Connection con = getConnection();
		if(con==null){
			return;
		}	
		
		menu(con);	
	}

	/**
	 * �л����ݿ�
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
	 * �û������SQL���
	 * @param con
	 */
	public static void menu(Connection con){		
		while(true){
			System.out.print("mysql>");
			//�����û������SQL���
			String str = sc.nextLine();
			str = str.trim().toLowerCase().replace(" +"," ");//ȥ������Ŀո�
			if(str.startsWith("insert ") || str.startsWith("update ") || str.startsWith("delete ") || str.startsWith("select ")){
				//��ɾ�Ĳ�
				crud(con, str);				
			}else if(str.startsWith("use ")){
				String newStr = str.replace("use ","");//��ȡҪ�л������ݿ���
				p.setProperty("dbname",newStr );
				try {
					DatabaseMetaData dd = con.getMetaData();
					//��ȡ�������ݿ���
					ResultSet rs = dd.getCatalogs();
					boolean bool = false;
					while(rs.next()){					
						String dname = rs.getString("TABLE_CAT");
						//������ڸ����ݿ���滻
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
	 * �鿴��ṹ
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
	 * ��ʾ���б�
	 * @param con
	 * @param str
	 */
	private static void show_tables(Connection con, String str) {
		try {
			PreparedStatement ps = con.prepareStatement(str);
			ResultSet rs = ps.executeQuery();
			//��ȡ����Ϣ
			ResultSetMetaData rd = rs.getMetaData();
			//��ӡ���еı�ǩ��
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
	 * ��ʾ�������ݿ�
	 * @param con
	 * @param str
	 */
	private static void show_databases(Connection con, String str) {
		try {
			DatabaseMetaData dd = con.getMetaData();
			System.out.println(dd.getCatalogTerm());
			ResultSet rs = dd.getCatalogs();
			while(rs.next()){
				//��ȡ���ݿ���
				String newStr = rs.getString("TABLE_CAT");
				System.out.println(newStr);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * ��ѯ���
	 * @param con ����
	 * @param str SQL���
	 */
	private static void select(Connection con, String str) {
		try {
			PreparedStatement ps = con.prepareStatement(str);	
			//��ȡִ�еĽ����
			ResultSet rs = ps.executeQuery();
			//��ȡ��ѯ�е���Ϣ
			ResultSetMetaData rmd = rs.getMetaData();			
			//��������������
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
	 * ɾ�����
	 * @param con
	 * @param str
	 */
	private static void delete(Connection con, String str) {
		try {
			//Ԥ����SQL���
			PreparedStatement ps = con.prepareStatement(str);
			//��ȡ��Ӱ�������
			int len = ps.executeUpdate();
			if(len>=0){
				System.out.println("Query OK, "+len+" row affected");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * �޸����
	 * @param con
	 * @param str
	 */
	private static void update(Connection con, String str) {
		
		try {
			//Ԥ����SQL���
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
	 * �������
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
	 * ��ȡ����
	 * @return
	 */
	public static Connection getConnection(){
		Connection con = null;
		try {
			//����������
			Class.forName("com.mysql.jdbc.Driver");
			try {
				//jdbc:mysql://localhost:3306/test
				String url = "jdbc:mysql://"+p.getProperty("host")+":"+p.getProperty("port")+"/"+p.getProperty("dbname");
				//��URL��ֵ
				p.setProperty("url",url );
				//��ȡ����
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
	 * �û���¼
	 * @param str
	 */
	private static void login(String str) {
		str = str.replace(" +"," ").trim();
		String[] newStr =str.split(" ");
		if(!newStr[0].equalsIgnoreCase("MYSQL")){
			System.out.println("'"+newStr[0]+"'�����ڲ����ⲿ���Ҳ���ǿ����еĳ�����������ļ���");
			return;
		}

		String username = "";
		String password = "";
		String host = "localhost";
		String port = "3306";
		String dbname = "mysql";
		
		for(int i=1;i<newStr.length;i++){
			//��ȡ����
			if(newStr[i].startsWith("-p")){
				password = newStr[i].replace("-p", "");
			}
			//��ȡ�˿ں�
			if(newStr[i].startsWith("-P")){
				port = newStr[i].replace("-P", "");
			}
			//��ȡ�û���
			if(newStr[i].startsWith("-u")){
				username = newStr[i].replace("-u", "");
			}
			//��ȡ�û�IP
			if(newStr[i].startsWith("-h")){
				host = newStr[i].replace("-h", "");
			}
			
			//��ȡ���ݿ���
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
