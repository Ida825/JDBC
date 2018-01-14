package jdbc.day170913;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.junit.Test;



public class Call {
	
	/**
	 * ���ô洢����
	 */
	@Test
	public void callProcedure(){
		String sql = "{call mulvar(?,?,?)}";
		
		try {
			//��ȡ����
			Connection con = DbUtils.getConnection();
			CallableStatement co = con.prepareCall(sql);
			CallableStatement cc =  con.prepareCall("select * from cc");
			System.out.println(cc.execute()+"--------------------");
			co.setInt(1, 25);
			co.setInt(2, 56);
			//���÷���ֵ��������
			co.registerOutParameter(3,Types.INTEGER);
			//ִ��SQL���
			co.executeUpdate();
			//��ȡ���صĽ��
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
		//����SQL���
		String sql = "{?=call fun_add(?,?)}";
		
		try {
			//��ȡ����
			Connection con = DbUtils.getConnection();
			//���ú���
			CallableStatement cs = con.prepareCall(sql);
			cs.setInt(2, 23);
			cs.setInt(3, 55);
			//���÷��ز���������
			cs.registerOutParameter(1,Types.INTEGER);
			//ִ��SQL���
			cs.executeUpdate();
			//��ȡ����ֵ
			int result = cs.getInt(1);
			System.out.println(result);
			cs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
