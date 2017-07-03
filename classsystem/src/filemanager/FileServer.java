/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author loxbae
 */
public class FileServer extends Thread  {
int port =2222;
private static ServerSocket mServerSocket;
public void startit(){
      try {
    mServerSocket = new ServerSocket(port);
   // Thread mReceiveMessage = new Thread(new ReceiveMessage());
   // mReceiveMessage.run();
     // Thread t = new GreetingServer();
       // t.start();
    } catch (IOException e) {
        System.out.println("ServerSocket(0) FAILED");
     System.out.println(e.getMessage());
    }
      
    
}
    public void sendFile(Socket server) {
      try {
     
       File transferFile = new File ("C:\\Users\\DELL\\Desktop\\mm.txt"); 
		      byte [] bytearray  = new byte [(int)transferFile.length()];
		      FileInputStream fin = new FileInputStream(transferFile);
		      BufferedInputStream bin = new BufferedInputStream(fin);
		      bin.read(bytearray,0,bytearray.length);
		      OutputStream os = server.getOutputStream();
		      System.out.println("Sending Files...");
		      os.write(bytearray,0,bytearray.length);
		      os.flush();
    
		      System.out.println("File transfer complete");
      
      } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to the host ");
    }
    
    
    }
}
