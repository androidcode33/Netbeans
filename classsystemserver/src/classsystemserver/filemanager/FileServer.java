/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystemserver.filemanager;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author loxbae
 */
public class FileServer extends Thread  {
  // The server socket.
  private static ServerSocket serverSocket = null;
  // The client socket.
  private static Socket clientSocket = null;
File file ;   
  // This chat server can accept up to maxClientsCount clients' connections.
  private static final int maxClientsCount = 10;
  private static final clientThread[] threads = new clientThread[maxClientsCount];
  public FileServer(ServerSocket serverSocket,File file ){
      this.serverSocket=serverSocket;
      this.file= file ;
  }
    /*
     * Create a client socket for each connection and pass it to a new client
     * thread.
     */
  
    public void run (){
       
        while (true) {
        
      try {
        
         clientSocket = serverSocket.accept();   
         System.out.println("sever started");
        int i = 0;
        for (i = 0; i < maxClientsCount; i++) {            
          if (threads[i] == null) {
            (threads[i] = new clientThread(clientSocket, threads)).start();            
            break;
          }
        }
        if (i == maxClientsCount) {
          PrintStream os = new PrintStream(clientSocket.getOutputStream());
          os.println("Server too busy. Try later.");
          os.close();
          clientSocket.close();
        }         
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }


class clientThread extends Thread {
 public String clientName = null;
  private DataInputStream is = null;
 public PrintStream os = null;
  private Socket clientSocket = null;
  private final clientThread[] threads;
  private int maxClientsCount;

  public clientThread(Socket clientSocket, clientThread[] threads) {
    this.clientSocket = clientSocket;
    this.threads = threads;
    maxClientsCount = threads.length;
  }

  public void run() {
    int maxClientsCount = this.maxClientsCount;
    clientThread[] threads = this.threads;
    try {
      /*
       * Create input and output streams for this client.
       */
      is = new DataInputStream(clientSocket.getInputStream());
      os = new PrintStream(clientSocket.getOutputStream());
      /* Start the conversation. */
      while (true) {
        File transferFile = new File (file.getPath()); 
		      byte [] bytearray  = new byte [(int)transferFile.length()];
		      FileInputStream fin = new FileInputStream(transferFile);
		      BufferedInputStream bin = new BufferedInputStream(fin);
		      bin.read(bytearray,0,bytearray.length);      
          synchronized (this) {
            for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null) {
                threads[i].os.println(file.getName());
                System.out.println("Sending Files...");
                threads[i].os.write(bytearray,0,bytearray.length);
                os.flush();
                System.out.println("File transfer complete");
              }
              }
            }
          break;
        }

      /*
       * Clean up. Set the current thread variable to null so that a new client
       * could be accepted by the server.
       */
      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] == this) {
            threads[i] = null;
          }
        }
      }
     
      /*
       * Close the output stream, close the input stream, close the socket.
       */
      is.close();
      os.close();
      clientSocket.close();
    } catch (IOException e) {
    }
}}
}


     /* int port =2222;
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
}*/
