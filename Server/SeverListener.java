import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class SeverListener extends Thread {
	@Override
	public void run() {
		System.out.println("serverListener is running...");
		try {
			ServerSocket serverSocket = new ServerSocket(1033);
			
			
			System.out.println(serverSocket);
			System.out.println("befor acc:");
			while(true){
				Socket socket = serverSocket.accept();
				String userName = 
						new BufferedReader(
								new InputStreamReader(
										socket.getInputStream(), "UTF-8")).readLine();
				ChatManager.sokets.put(userName, socket);
				ChatManager.sendOnlineMsgToAll(userName);
				System.out.println("get a socket from " + userName + " : " + socket);
				ChatSocket cs = new ChatSocket(userName,socket);
				cs.start();
//				ChatManager.getChatManager().add(cs);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
