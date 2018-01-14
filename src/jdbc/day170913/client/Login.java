package jdbc.day170913.client;
/**
 * 登录类
 * @author Administrator
 *
 */
public class Login {
	/**
	 * 登录数据库
	 * @param str  
	 */
	public boolean login(String str) {
		str = str.replace(" +"," ").trim();
		String[] newStr =str.split(" ");
		if(!newStr[0].equalsIgnoreCase("MYSQL")){
			System.out.println("'"+newStr[0]+"'不是内部或外部命令，也不是可运行的程序或批处理文件。");
			return false;
		}
		
		for(int i=1;i<newStr.length;i++){		
			if(newStr[i].startsWith("-p")){
				//截取密码
				ConnectionTools.p.setProperty("password", newStr[i].replace("-p", ""));
			}else if(newStr[i].startsWith("-P")){
				//
				ConnectionTools.p.setProperty("port", newStr[i].replace("-P", ""));
			}else if(newStr[i].startsWith("-u")){
				ConnectionTools.p.setProperty("username", newStr[i].replace("-u", ""));
			}else if(newStr[i].startsWith("-h")){
				//截取用户IP
				ConnectionTools.p.setProperty("host", newStr[i].replace("-h", ""));
			}else if(!newStr[i].startsWith("-")){
				//截取数据库名
				ConnectionTools.p.setProperty("dbname",newStr[i]);
			}
		}
		
		return true;
	}
}
