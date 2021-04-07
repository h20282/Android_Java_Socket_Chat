import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatSocket extends Thread {
	Socket socket;
	String userName;
	public ChatSocket(String userName, Socket s) {
		this.userName = userName;
		this.socket = s;
	}
	
	public void out(String out) {
		try {
			socket.getOutputStream().write((out).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream(), "UTF-8"));
			String line = null;
			System.out.println("waiting for read a line from " + socket + " : ");
//			/*
//			System.out.println("complete !!!");
//			PrintWriter out = new PrintWriter(
//                    new BufferedWriter(
//                            new OutputStreamWriter(
//                            		socket.getOutputStream(), "UTF-8")));
//			while(socket!=null){
//				out.println("server msg:中文");
//				out.flush();
////				socket.getOutputStream().write("server msg:中文".getBytes());
////				socket.getOutputStream().flush();
//				Thread.sleep(200);
//			}
			
			while ( (line = br.readLine()) != null ) {
				System.out.println("readed :" + line);
				String source=null;
				String target=null;
				String msg=null;
				for ( int i=0; i<line.length(); i++ ) {
					if ( line.charAt(i)==':' ) {
						msg = line.substring(i+1);
						line = line.substring(0, i);
					}
				}
				for ( int i=0; i<line.length(); i++ ) {
					if ( line.charAt(i)=='-' && line.charAt(i+1)=='>' ) {
						source = line.substring(0,i);
						target = line.substring(i+2);
					}
				}
				ChatManager.sendMsg(source, target, msg);
//				ChatManager.publish(line);
			}
			br.close();
			ChatManager.userOutLine(userName);
			
			System.out.println(socket + userName + " disconnect");
//			*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
// telnet localhost 1033