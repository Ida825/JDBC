package jdbc.day170913.client;

import java.sql.Connection;
import java.util.Scanner;
/**
 * ������
 * @author Administrator
 *
 */
public class Test {
	static Scanner sc = new Scanner(System.in);
	static Connection con = null;
	public static void main(String[] args) {	
		System.out.println("Microsoft Windows [�汾 6.1.7601]\n��Ȩ���� (c) 2009 Microsoft Corporation����������Ȩ����\n");
		while(true){		
			System.out.print("C:/Users/Administrator>");			
			//��ȡ�û������¼��Ϣ
			//mysql -uroot -p123456 -hlocalhost -P3306 test
			String str = sc.nextLine();	
			if(str.trim().equalsIgnoreCase("exit")){
				System.exit(0);
			}
			Login login = new Login();
			if(!login.login(str)){
				continue;
			}
			
			//��ȡ����
			con = ConnectionTools.getConnection();
			if(con==null){
				continue;
			}
			System.out.println(con);
			Menu.bool = true;
			Menu m = new Menu();
			m.menu();	
		}		
	}
}
