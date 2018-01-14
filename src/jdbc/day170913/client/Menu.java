package jdbc.day170913.client;

import java.util.Scanner;
/**
 * 用户输入的请求类
 * @author Administrator
 *
 */
public class Menu {
	static Scanner sc = new Scanner(System.in);
	static boolean bool = true;
	/**
	 * 请求菜单
	 */
	public void menu(){	
		while(bool){
			System.out.print("mysql>");
			//接收用户输入的SQL语句
			String str = sc.nextLine();
			str = str.trim().toLowerCase().replace(" +"," ");//去掉多余的空格
			if(str.startsWith("insert ") || str.startsWith("update ") || str.startsWith("delete ") || str.startsWith("select ")){
				//调用增删改、查的方法
				crud(str);				
			}else if(str.startsWith("use ")){
				//调用以use开始的方法
				use(str);
			}else if(str.startsWith("show ")){	
				//调用 以show开始的方法
				show(str);			
			}else if(str.startsWith("desc ")){
				//调用以desc开始的方法
				AllSql.desc(str);
			}else if(str.equalsIgnoreCase("quit") || str.equalsIgnoreCase("exit")){
				AllSql.quit(str);
			}else {
				System.out.println("输入有误");
			}
		}
	}


	


	/**
	 * 以show开始的方法
	 * @param str
	 */
	private void show(String str) {
		if(str.substring(5).equalsIgnoreCase("databases")){					
			AllSql.show_databases(str);
		}else if(str.substring(5).equalsIgnoreCase("tables")){
			AllSql.show_tables(str);
		}else{
			System.out.println("输入有误");
		}

	}

	/**
	 * 以use开始的方法
	 * @param str
	 */
	private void use(String str) {
		AllSql.useDatabase(str);
	}

	/**
	 * 增删改、查的方法
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
