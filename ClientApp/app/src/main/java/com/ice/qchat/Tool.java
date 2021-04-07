package com.ice.qchat;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;

public class Tool {
    public static final String host = "8.136.145.220";
    public static Socket mySocket;
    public static ArrayList<Handler> handlers = new ArrayList<Handler>();
    public static void updataUI(){
        for (Handler h:handlers){
            h.sendEmptyMessage(100); // updata the List
        }
    }

    public static void waitServerMsg(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(mySocket==null){
                    System.out.println("mySocket = null");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                BufferedReader br = null;
                try {
                    br = new BufferedReader(
                            new InputStreamReader(
                                    mySocket.getInputStream(), "UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("before loop:");
                String line = null;
                while (true) {
                    try {
                        if (!((line = br.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("readed :" + line);
                    if ( line.indexOf("outline:")==0 ){ // the user is outline now
                        String user = line.substring("outline:".length());
                        MainActivity.map.remove(user);
                    }else if ( line.indexOf("online:")==0 ){ // new user is online now
                        String newUserName = line.substring("online:".length());
                        MainActivity.map.put(newUserName, new ArrayList<String>());
                        System.out.println(MainActivity.map);
                    }else{ // msg
                        String source = null;
                        String msg = null;
                        for ( int i=0; i<line.length(); i++ ) {
                            if ( line.charAt(i) == ':' ) {
                                source = line.substring(0,i);
                                msg = line.substring(i+1);
                            }
                        }
                        if ( MainActivity.map.get(source)==null ) {
                            MainActivity.map.put(source, new ArrayList<String>());
                        }
                        MainActivity.map.get(source).add("o"+msg);
                    }
                    updataUI();


//                    msgList.add(line);
                }
            }
        }).start();

    }

    public static boolean sendmsg(final String sourceUsername,final String targetUsername, final String msg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    PrintWriter out = new PrintWriter(
                            new BufferedWriter(
                                    new OutputStreamWriter(
                                            mySocket.getOutputStream(), "UTF-8")));
                    Date d = new Date();
                    if (sourceUsername==null && targetUsername==null){ // login progress
                        out.println(msg);
                    }else{
                        out.println(sourceUsername + "->" + targetUsername + ":" + msg);
                    }
                    out.flush();
                    updataUI();
                }
                catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) .start();
        return true;
    }

    public static void connect(final String username) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mySocket == null) {
                    try {
                        mySocket = new Socket(host, 1033);
                        sendmsg(null,null,username);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void Test(View view){
//        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket(host, 1033);
                    PrintWriter out = new PrintWriter(
                            new BufferedWriter(
                                    new OutputStreamWriter(
                                            s.getOutputStream())));
                    Date d = new Date();
                    out.println("now time is " + d);
                    out.flush();
                }
                catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) .start();

//        catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

//         */

    }
}
