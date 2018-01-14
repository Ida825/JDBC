package jdbc.day170912;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OracleLogin {

	public static void main(String[] args) {
		//����URL
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		//����jdbc��ʲô���ݿ�(������)
		String driverClass = "oracle.jdbc.OracleDriver";
		//�û�
		String username = "stu";
		//����
		String password = "123456";
				
		try {
			//��jvm����������
			Class.forName(driverClass);	
			//��ȡ����
			Connection con = DriverManager.getConnection(url, username, password);
			//Statement����ִ�о�̬SQL���
			Statement st = con.createStatement();
			//ResultSet �α����������
			ResultSet rs = st.executeQuery("select * from student");
			//��next�ж��Ƿ�����һ��
			while(rs.next()){
				String name = rs.getString("sname");
				int age = rs.getInt("sage");
				String sex = rs.getString("ssex");
				System.out.println(name+","+age+","+sex);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
