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
	 * ��ѯ
	 */
	@Test
	public void select(){
		//����SQL���
		String sql = "select * from emp";
		
		try {
			//��ȡ����
			Connection con = DbUtils.getConnection();
			//Ԥ�ȱ���SQL���
			PreparedStatement ps = con.prepareStatement(sql);
			//ִ��SQL���
			ResultSet rs = ps.executeQuery();
			//��next�ж��Ƿ�����һ��
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
	 * ���
	 */
	//@Test
	public void insert(){
		String sql = "insert into emp (empno,ename,sal) values ((select max(empno)+10 from emp),?,?)";
		
		try {
			//��ȡ����
			Connection con = DbUtils.getConnection();
			//Ԥ����SQL���
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
	 * �޸�
	 */
	//@Test
	public void update(){
		//����SQL���
		String sql = "update emp set sal = ? where ename = ?";
		
		try {
			//��ȡ����
			Connection con = DbUtils.getConnection();
			//Ԥ�ȱ���SQL���
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "2200");
			ps.setString(2, "LL");
			//ִ��SQL���
			ps.executeUpdate();
			ps.close();
			con.close();//�ر�����
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ɾ��
	 */
	//@Test
	public void delete(){
		String sql = "delete from emp where ename = ?";
		
		try {
			//��ȡ����
			Connection con = DbUtils.getConnection();
			//Ԥ����SQL���
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "LL");
			//ִ��SQL���
			ps.executeUpdate();
			//�ر�����
			ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ������ϰ
	 */
	//@Test
	public void tran(){		
		//��ȡ����
		Connection con = null;
		PreparedStatement ps = null;
		try {
			String sql = "delete from emp where empno=6666";
			con = DbUtils.getConnection();
			con.setAutoCommit(false);//���ò��Զ��ύ
			//Ԥ����SQL���
			ps = con.prepareStatement(sql);
			//ִ��SQL���
			ps.executeUpdate();
			ps.close();
			
			String str = null;
			str.toString();//����ᱨ��
			
			
			String sql1 = "delete from emp where empno=8888";
			ps = con.prepareStatement(sql1);
			ps.executeUpdate();
			ps.close();
			con.close();
			con.commit();
		
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();//�ع�
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
			d.dloc="���"+i;
			list.add(d);
		}
	}
	
	//@Test
	public void batchInsert(){
		int next = 8000;
		String sql = "insert into dept values(?,?,?) ";
		//��ȡ����
		try {
			Connection con = DbUtils.getConnection();
			//Ԥ����SQL���
			PreparedStatement ps = con.prepareStatement(sql);
			
			for(int i=0;i<list.size();i++){
				Dept d = list.get(i);
				next=next+1;
				ps.setInt(1,next);
				ps.setString(2,d.dname);
				ps.setString(3,d.dloc);
				ps.addBatch();//���������
			}
			
			int[] result = ps.executeBatch();//������Ӱ�����
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
