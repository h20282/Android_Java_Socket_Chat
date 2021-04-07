import java.io.FileOutputStream;
import java.io.PrintStream;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Time;
import java.util.Date;

import javax.swing.JOptionPane;

class Test{
	public static void main(String[] args) throws Exception {
		PrintStream ps=new PrintStream(new FileOutputStream("logs.txt"));
	        System.setOut(ps);
		new SeverListener().start();
		
		
		
		/*
		try {
			Socket s = new Socket("172.19.27.184", 1033);
			PrintWriter out = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									s.getOutputStream())));
			while(true){
				Date d = new Date();
				out.println("now time is " + d);
				out.flush();
//				ChatManager.publish(d.toString());
//				System.out.println(d);
				Thread.sleep(1000);
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		*/
	}
 
}
