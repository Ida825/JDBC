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
	 * 1.д��һ����ҳ�ķ���   ��������
	 * tablePager(tableName,curPage,pageSize)
	 * ����
	 * tablePager("emp",2,10)
	 * ��ѯemp���� �ڶ�ҳ�����ݣ�ÿҳ��ʾ10�� �ڶ�ҳ���� 10-20����	
	 * @param tableName ����
	 * @param curPage �ڼ�ҳ
	 * @param pageSize ÿҳ��ʾ������
	 */

	public static void tablePager(String tableName,int curPage,int pageSize){
		String sql = "select * from (select t.*,rownum as rn from "+tableName+" t) where rn>=? and rn<=?";
		try {
			//��ȡ����
			Connection con = DbUtils.getConnection();									
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, (curPage-1)*pageSize+1);
			ps.setInt(2, curPage*pageSize);
			
			//��ȡ�����
			ResultSet rs = ps.executeQuery();
			//��ȡ��ѯ�е���Ϣ
			ResultSetMetaData rd= rs.getMetaData();			
			//�������������next�ж��Ƿ�����һ��
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
			//��ȡ����
			Connection con = DbUtils.getConnection();
			Statement st = con.createStatement();
			String sql1 = "select * from "+tableName;
			//��ȡ�����
			ResultSet rs = st.executeQuery(sql1);
			//��ȡ��ѯ�е���Ϣ
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
			
			//����һ��SQL���
			String sql = "select "+str+" from (select t.*,rownum as rn from "+tableName+" t) where rn>=? and rn<=?";
			//Ԥ����SQL���
			PreparedStatement ps = con.prepareStatement(sql);
			//ps.setString(1,tableName);
			ps.setInt(1, (curPage-1)*pageSize+1);
			ps.setInt(2, curPage*pageSize);
			//�������������next�ж��Ƿ�����һ��
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
	 * ����һ������ ������� ��ɾ���ñ��е��ظ���¼
	 * ���� deleteMul(tableName)
              ���� deleteMul("emp"); ����ɾ����emp���ظ�����  ��execute��
	 * @param tableName ����
	 * @throws SQLException 
	 */

	public static void deleteMul(String tableName) throws SQLException{
		//select wm_concat(column_name) from user_tab_columns where table_name='EMP';
		//delete from emp where rowid not in(select min(rowid) from emp group by ename);
		String sql1 = "select wm_concat(column_name) from user_tab_columns where table_name='"+tableName+"'";
		//��ȡ����
		Connection con = DbUtils.getConnection();
		//����һ������ִ�о�̬SQL����Statement
		Statement st = con.createStatement();
		//ִ��SQL���
		ResultSet rs = st.executeQuery(sql1);
		//��ȡ�ñ��������
		String str = "";
		while(rs.next()){
			 str= rs.getString(1);
		}
		
		String sql2 = "delete from "+tableName+" where rowid not in(select min(rowid) from "+tableName+" group by "+str+")";
		//Ԥ����SQL���
		PreparedStatement ps = con.prepareStatement(sql2);
		//ִ��SQL���
		ps.execute();	
	}
	
	/**
	 * ʹ���α���� scott�����еĹ�Ա���ƣ��������ƣ���н
	 */

	public static void scottInfo(){
		//����SQL���
		String sql = "select e.ename c1,d.dname c2,sal*12 c3 from emp e inner join dept d on e.deptno = d.deptno";		
		try {
			//��ȡ����
			Connection con = DbUtils.getConnection();
			//Ԥ����SQL���
			PreparedStatement ps = con.prepareStatement(sql);
			//��ȡ�����
			ResultSet rs = ps.executeQuery();
			//���������,��next�ж��Ƿ�����һ��
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
