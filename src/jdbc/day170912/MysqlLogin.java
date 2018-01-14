package jdbc.day170912;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * connection ���ں����ݿ�������ӵ���
 * DriverManager ����������  ͨ����ȡ��ͬ�����ݿ�������� ʵ�ֲ�ͬ�ĵ�¼��ʽ
 * @author Administrator
 *
 */
public class MysqlLogin {

	public static void main(String[] args) {
		//jdbc���ӵ����ݿ��������IP �˿�  ���ݿ���
		//mysql ��URL��jdbc:mysql://localhost:3306/���ݿ���
		//oracle ��URL ��jdbc:oracle:thin:@server:1521:sid(orcl)
		String url = "jdbc:mysql://localhost:3306/test";
		//����jdbcʹ�õ���ʲô���ݿ�  ��ͬ���ݿ��ṩһЩ��ͬ����
		String driverClass="com.mysql.jdbc.Driver";
		
		String username = "root";
		String password = "123456";		
		try {
			//��Ҫʹ��jvm���ظ���
			Class.forName(driverClass);
			//��¼�ɹ�
			Connection con = DriverManager.getConnection(url, username, password);		
			//System.out.println(con);
			//Statement����ִ�о�̬��SQL���
			Statement sta = con.createStatement();
			//ResultSet �α���������� next()
			ResultSet sr = sta.executeQuery("select * from student");
			//ͨ��next()�ж��Ƿ�����һ��
			while(sr.next()){
				//��������ץȡ����
				String uname = sr.getString("sname");
				String sex = sr.getString("ssex");
				//ͨ����������ץȡ����1��ʼ������
				int age = sr.getInt(3);
				System.out.println(uname+"--"+sex+"--"+age);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}

}
