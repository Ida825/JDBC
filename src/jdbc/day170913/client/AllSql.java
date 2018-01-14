package jdbc.day170913.client;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
/**
 * ���������
 * @author Administrator
 *
 */
public class AllSql {
	/**
	 * ���ݵĲ��롢ɾ�����޸�
	 * @param str  insert��delete��update���
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
	 * ���ݵĲ�ѯ
	 * @param str  select���
	 */
	public static void select(String str) {
		try {
			PreparedStatement ps = Test.con.prepareStatement(str);	
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
	 * �л����ݿ�
	 * @param str use���
	 */
	public static void useDatabase(String str) {
		String newStr = str.replace("use ","");//��ȡҪ�л������ݿ���
		//���ж�Ҫ�л������ݿ��Ƿ����
		try {
			//��ȡ��ǰ�����µ�DatabaseMetaData����
			DatabaseMetaData dd = Test.con.getMetaData();
			//��ȡ�������ݿ���
			ResultSet rs = dd.getCatalogs();
			boolean bool = false;
			while(rs.next()){					
				String dname = rs.getString("TABLE_CAT");
				//������ڸ����ݿ���滻
				if(dname.equalsIgnoreCase(newStr)){
					//���û��������ݿ����滻Ĭ�����ݿ���
					ConnectionTools.p.setProperty("dbname",newStr );
					//���û�ȡ���ӵķ���
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
	 * �鿴��ṹ
	 * @param str  desc���
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
	 * ��ʾ���б���
	 * @param str show tables���
	 */
	public static void show_tables(String str) {
		try {
			PreparedStatement ps = Test.con.prepareStatement(str);
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
	 * @param str  show databases���
	 */
	public static void show_databases(String str) {
		try {
			DatabaseMetaData dd = Test.con.getMetaData();
			System.out.println(dd.getCatalogTerm());
			ResultSet rs = dd.getCatalogs();
			while(rs.next()){
				//��ȡ���ݿ���
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
