package jdbc.day170913.client;
/**
 * ��¼��
 * @author Administrator
 *
 */
public class Login {
	/**
	 * ��¼���ݿ�
	 * @param str  
	 */
	public boolean login(String str) {
		str = str.replace(" +"," ").trim();
		String[] newStr =str.split(" ");
		if(!newStr[0].equalsIgnoreCase("MYSQL")){
			System.out.println("'"+newStr[0]+"'�����ڲ����ⲿ���Ҳ���ǿ����еĳ�����������ļ���");
			return false;
		}
		
		for(int i=1;i<newStr.length;i++){		
			if(newStr[i].startsWith("-p")){
				//��ȡ����
				ConnectionTools.p.setProperty("password", newStr[i].replace("-p", ""));
			}else if(newStr[i].startsWith("-P")){
				//
				ConnectionTools.p.setProperty("port", newStr[i].replace("-P", ""));
			}else if(newStr[i].startsWith("-u")){
				ConnectionTools.p.setProperty("username", newStr[i].replace("-u", ""));
			}else if(newStr[i].startsWith("-h")){
				//��ȡ�û�IP
				ConnectionTools.p.setProperty("host", newStr[i].replace("-h", ""));
			}else if(!newStr[i].startsWith("-")){
				//��ȡ���ݿ���
				ConnectionTools.p.setProperty("dbname",newStr[i]);
			}
		}
		
		return true;
	}
}
