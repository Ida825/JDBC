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
		 * getColumns方法的参数详解： 
		 * 1.MySQL:数据库名 Oracle:数据库实例，可以不填，也可以用getCatalog代替
		 * 2.对于Oracle而言可以理解为用户名。注意：一定要大写 
		 * 3.表名称，传入要查找的表 
		 * 4.列类型，为null时则获取表的所有列。
		 * 返回值是个ResultSet，有23列，这里不展开了说明，取一个COLUMN_NAME就好。
		 */
		ResultSet rs = dbm.getColumns(conn.getCatalog(), "SCOTT", "EMP", null);
		while (rs.next()) {
			System.out.print(rs.getString("COLUMN_NAME") + "  ");
		}
	}
}
