package jdbc.day170912;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class CRUD {
	/**
	 * �鿴
	 * @throws SQLException
	 */
	
	@Test
	public void select() throws SQLException{
		String sql = "select * from student";
		//��ȡ����
		Connection con = DbUtils.getConnection();
		//��������ִ�о�̬SQL����Statement
		Statement st = con.createStatement();
		//��ȡ�����
		ResultSet rs = st.executeQuery(sql);
	    //��next�ж��Ƿ�����һ��
		while(rs.next()){
			String sno = rs.getString("s#");
			String name = rs.getString("sname");
			String age = rs.getString("sage");
			String sex = rs.getString("ssex");
			System.out.println(sno+","+name+","+age+","+sex);
		}
	}
	
	
	
	/**
	 * ���
	 * @throws Exception
	 */
	//@Test
	public void insert() throws Exception {
		String sql = "insert into student(s#,sname,sage,ssex) values((select max(s#)+1 from student),'����',25,'Ů')";
		//��ȡ����
		Connection con = DbUtils.getConnection();
		Statement st = con.createStatement();
		//ִ��SQL���
		st.executeUpdate(sql);
		st.close();//�ر�Statement
		con.close();//�ر�����
	}

	/**
	 * �޸�
	 * @throws Exception
	 */
	//@Test
	public void update() throws Exception {
		String sql = "update student set sage = 20 where sname='����'";
		//��ȡ����
		Connection con = DbUtils.getConnection();
		//����Statement������ִ�о�̬SQL��䣩
		Statement st = con.createStatement();
		//ִ��SQL���
		st.executeUpdate(sql);
		//�ر�Statement
		st.close();
		//�ر�����
		con.close();
	}

	/**
	 * ɾ��
	 * @throws Exception
	 */
	//@Test
	public void delete() throws Exception {
		String sql = "delete from student where sname = 'Ǯ��'";
		//��ȡ����
		Connection con = DbUtils.getConnection();
		//��������ִ�о�̬SQL����Statement
		Statement st = con.createStatement();
		//ִ��SQL���
		st.executeUpdate(sql);
		//�ر�Statement
		st.close();
		//�ر�����
		con.close();
	}

}
