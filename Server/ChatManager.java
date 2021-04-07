import java.util.HashMap;
import java.util.Vector;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ChatManager {
	public static HashMap<String, Socket> sokets = new HashMap<String, Socket>();
	public static boolean sendMsg(String source, String target, String msg) throws UnsupportedEncodingException, IOException{
		Socket targetSocket = sokets.get(target);
		if ( targetSocket != null ) {
			putln(targetSocket, source + ":" + msg);
			return true;
		}else{
			System.out.println("target socket not exit!");
			return false;
		}
	}
	
	public static void sendOnlineMsgToAll(String newUserName){
		try {
			for ( String key : sokets.keySet() ){
				if ( key.equals(newUserName)){
					putln(sokets.get(newUserName), "online:"+ key);
				}else{
					putln(sokets.get(key), "online:"+newUserName);
					putln(sokets.get(newUserName), "online:"+ key);
				}
			}
		} catch (IOException e) { e.printStackTrace();}
	}
	public static void userOutLine(String userName){
		sokets.remove(userName);
		try{
			for ( String key : sokets.keySet() ) {
				putln(sokets.get(key), "outline:"+userName);
			}
		}catch (IOException e) {e.printStackTrace();}
	}
	
	private static void putln(Socket targetSocket, String str) throws UnsupportedEncodingException, IOException{
		PrintWriter out = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                        		targetSocket.getOutputStream(), "UTF-8")));
		out.println(str);
		System.out.println("sent a msg to " + targetSocket + ":" + str);
		out.flush();
	}
	
	
	
	
	
	
	
	private ChatManager(){}
	private static final ChatManager cm = new ChatManager();
	public static ChatManager getChatManager(){
		return cm;
	}
	
	static Vector<ChatSocket> v = new Vector<ChatSocket>();
	
	public void add(ChatSocket cs){
		v.add(cs);
	}
	
	public static void publish(String out){
		for ( int i=0; i<v.size(); i++ ) {
			ChatSocket csChatSocket = v.get(i);
//			if ( !cs.equals(csChatSocket) ) {
				csChatSocket.out(out+"\r\n");
//			}
		}
	}
}
