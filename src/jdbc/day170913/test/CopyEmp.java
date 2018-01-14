package jdbc.day170913.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CopyEmp {
/**
 * 3 ʹ��jdbc����emp�����ݵ��±�emp1���ñ���ִ�д���ʱ�����ڣ���20��
  ����ֱ��ִ��sql (create table emp1 as select * from emp)
  ����ͨ��ResultSet��ȡemp������д��emp1
 */
	static Connection con = null;
	static List<String> list = new ArrayList<>();
	public static void main(String[] args) {
		String sql1 ="select * from emp";
		
		//��ȡ����		
		try {
			con = DbUtils.getConnection();
			//Ԥ����SQL���
			PreparedStatement ps = con.prepareStatement(sql1);
			//��ȡ�����
			ResultSet rs = ps.executeQuery();
			//��ȡ��ѯ�е���Ϣ
			ResultSetMetaData rd= rs.getMetaData();			
			//�������������next�ж��Ƿ�����һ��
			while(rs.next()){
				for(int i=1;i<=rd.getColumnCount();i++){
					String newStr = rs.getString(i);
					list.add(newStr);
				}				

			}
			ps.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		createEmp1();
		try {
			insert();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * �������
	 * @throws SQLException
	 */
	private static void insert() throws SQLException {
		String sql = "insert into emp1 values (?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		for (String str : list) {
			String[] s = str.split(",");		
			ps.setString(1, s[0]);
			ps.setString(2, s[0]);
			ps.setString(3, s[0]);
			ps.setString(4, s[0]);
			ps.setString(5, s[0]);
			ps.setString(6, s[0]);
			ps.setString(7, s[0]);
			ps.setString(8, s[0]);
			}
	}



	/**
	 * ����emp1��
	 */
	private static void createEmp1() {		
		String sql="create table emp1(empno NUMBER(4) ,ename VARCHAR2(10),job VARCHAR2(9),mgr NUMBER(4),hiredate DATE,sal NUMBER(7,2),comm NUMBER(7,2),deptno NUMBER(2),empdesc VARCHAR2(20))";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	
}
