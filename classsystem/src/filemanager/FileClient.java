/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import classsystem.dadaseConnect.DatabaseConn;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author loxbae
 */
public class FileClient extends Thread {
     Socket clientSocket;
   
     private  DataInputStream is;
    public FileClient(Socket clientSocket){
    this.clientSocket=clientSocket;
    }
    public void run() {
    String responseLine;
    int filesize=2022386; int bytesRead;
    int currentTot = 0;
    byte [] bytearray  = new byte [filesize];     
  PrintStream os = null;
    try {      
        is = new DataInputStream(clientSocket.getInputStream());
       String dir="C:\\Users\\loxbae\\Desktop\\";
       while(true){
      while ((responseLine = is.readLine()) != null) {
        bytesRead = is.read(bytearray,0,bytearray.length);
          currentTot = bytesRead;
         FileOutputStream fos = new FileOutputStream(dir+responseLine);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
	    do {
	       bytesRead = is.read(bytearray, currentTot, (bytearray.length-currentTot));
	       if(bytesRead >= 0) currentTot += bytesRead;
	    } while(bytesRead > -1);
	    bos.write(bytearray, 0 , currentTot);
	    bos.flush();
	    bos.close();
              }
       // System.out.println(responseLine);        
       // if (responseLine.indexOf("*** Bye") != -1)
       }
    } catch (IOException e) {
      System.err.println("IOException:  " + e);
    }
  }
}
