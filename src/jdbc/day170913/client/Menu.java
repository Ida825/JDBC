package jdbc.day170913.client;

import java.util.Scanner;
/**
 * �û������������
 * @author Administrator
 *
 */
public class Menu {
	static Scanner sc = new Scanner(System.in);
	static boolean bool = true;
	/**
	 * ����˵�
	 */
	public void menu(){	
		while(bool){
			System.out.print("mysql>");
			//�����û������SQL���
			String str = sc.nextLine();
			str = str.trim().toLowerCase().replace(" +"," ");//ȥ������Ŀո�
			if(str.startsWith("insert ") || str.startsWith("update ") || str.startsWith("delete ") || str.startsWith("select ")){
				//������ɾ�ġ���ķ���
				crud(str);				
			}else if(str.startsWith("use ")){
				//������use��ʼ�ķ���
				use(str);
			}else if(str.startsWith("show ")){	
				//���� ��show��ʼ�ķ���
				show(str);			
			}else if(str.startsWith("desc ")){
				//������desc��ʼ�ķ���
				AllSql.desc(str);
			}else if(str.equalsIgnoreCase("quit") || str.equalsIgnoreCase("exit")){
				AllSql.quit(str);
			}else {
				System.out.println("��������");
			}
		}
	}


	


	/**
	 * ��show��ʼ�ķ���
	 * @param str
	 */
	private void show(String str) {
		if(str.substring(5).equalsIgnoreCase("databases")){					
			AllSql.show_databases(str);
		}else if(str.substring(5).equalsIgnoreCase("tables")){
			AllSql.show_tables(str);
		}else{
			System.out.println("��������");
		}

	}

	/**
	 * ��use��ʼ�ķ���
	 * @param str
	 */
	private void use(String str) {
		AllSql.useDatabase(str);
	}

	/**
	 * ��ɾ�ġ���ķ���
	 * @param str
	 */
	private static void crud(String str) {
		if(str.startsWith("insert ") || str.startsWith("update ") || str.startsWith("delete ")){
			AllSql.crd(str);
		}else if(str.startsWith("select ")){
			AllSql.select(str);
		}
	}
}
