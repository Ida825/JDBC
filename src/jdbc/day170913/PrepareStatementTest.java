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
			//sqlע��:' or 1=1 or 1= '
			query("MANAGER","B");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * ���巽�� ���Դ������²���query(ename,job,sal)�����������ĳ�������� �Բ�����ϵ���ʽ��ѯ���
	    ������ù�������
		   query('Cleck',null,null);
		   ��ѯ��sqlΪ
		   select * from emp where ename like '%Cleck%';
		    query('Cleck','Manager',null);
		   ��ѯ��sqlΪ
		   select * from emp where ename like '%Cleck%' and job like '%Manager%'
		   Ҫ�������ѯ�Ľ��
	 * @param ename
	 * @param job
	 * @param sal
	 * @throws SQLException 
	 */
	public static void query(String job,String ename) throws SQLException{
		//����SQL���
		String sql = "select * from emp where job = ? and ename like ?";
		//��ȡ����
		Connection con = DbUtils.getConnection();
		//ִ��SQL���
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, job);
		ps.setString(2, "%"+ename+"%");
		//����һ����ѯ�����
		ResultSet rs = ps.executeQuery();
		//��next�ж��Ƿ������һ��
		while(rs.next()){
			String nameVar = rs.getString("ename");
			String jobVar = rs.getString("job");
			String salVar = rs.getString("sal");
			System.out.println(nameVar+"--"+jobVar+"--"+salVar);
		}
	}
	

	
}
