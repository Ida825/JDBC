package jdbc.day170913;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

public class MetaDataTest {

	public static void main(String[] args) throws Exception {
		
		
		Connection conn = DbUtils.getConnection();
		DatabaseMetaData dbm = conn.getMetaData();
		/*
		 * getColumns�����Ĳ�����⣺ 
		 * 1.MySQL:���ݿ��� Oracle:���ݿ�ʵ�������Բ��Ҳ������getCatalog����
		 * 2.����Oracle���Կ������Ϊ�û�����ע�⣺һ��Ҫ��д 
		 * 3.�����ƣ�����Ҫ���ҵı� 
		 * 4.�����ͣ�Ϊnullʱ���ȡ��������С�
		 * ����ֵ�Ǹ�ResultSet����23�У����ﲻչ����˵����ȡһ��COLUMN_NAME�ͺá�
		 */
		ResultSet rs = dbm.getColumns(conn.getCatalog(), "SCOTT", "EMP", null);
		while (rs.next()) {
			System.out.print(rs.getString("COLUMN_NAME") + "  ");
		}
	}
}
