/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystemserver.model;

import classsystemserver.dabaseConnect.DatabaseConn;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.StringTokenizer;

/**
 *
 * @author loxbae
 */
public class ChatServer extends Thread {
   // The server socket.
  private static ServerSocket serverSocket = null;
  // The client socket.
  private static Socket clientSocket = null;
String message;
int index;
Random random = new Random();
DatabaseConn database= new DatabaseConn("jdbc:mysql://localhost:3306/classdatabase", "root", "");
   
  // This chat server can accept up to maxClientsCount clients' connections.
  private static final int maxClientsCount = 50;
  private static final clientThread[] threads = new clientThread[maxClientsCount];
  public ChatServer(ServerSocket serverSocket,String message,int index){
  this. serverSocket= serverSocket;
  this.message=message;
  this.index=index; 
  }
    /*
     * Create a client socket for each connection and pass it to a new client
     * thread.
     */
  
    public void run (){
    database.dBConnect();
        while (true) {
        
      try {
         if(index==1){ 
   if(message.contains("*")){ 
             synchronized (this) {              
            for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i].clientName != null) {
                threads[i].os.println(message);                 
              }
            }
          }
      }
   else if(message.contains("#")){ 
             synchronized (this) {
              
            for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i].clientName != null) {
                threads[i].os.println(message);                 
              }
            }
          }
         String nam[]= new String [6];
         int n=0;
         StringTokenizer token;
         token = new StringTokenizer (message,"#");
        while(token.hasMoreTokens()){
            nam[n]=token.nextToken();
            n++;
        }
           String inV = "INSERT INTO response(messageID,username,response) values('"+nam[0]+"','"+"Lecturer"+"','"+nam[1]+"'"+");";
           database.insert_or_Update(inV);
      }else{
            String name[]= new String [6];
             int n=0;
             int randomInt = random.nextInt(50000);
              /* The message is public, broadcast it to all other clients. */
          synchronized (this) {
              
            for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i].clientName != null) {
                threads[i].os.println(randomInt+">>Lecturer>>"+message);
                 System.out.println(randomInt+">>Lecturer>>"+message);
              }
            }
          }
          StringTokenizer token = new StringTokenizer (message,">>");
        while(token.hasMoreTokens()){
            name[n]=token.nextToken();
            n++;
        }
            if(n==2){
            String inV = "INSERT INTO messages(messageID,username,message,options) values('"+randomInt+"','"+"Lecturer"+"','"+name[0]+"','"+name[1]+"'"+");";
            database.insert_or_Update(inV);
            }else{
             String inV = "INSERT INTO messages(messageID,username,message,options) values('"+randomInt+"','"+"Lecturer"+"','"+message+"','"+""+"'"+");";
            database.insert_or_Update(inV);
            }
         
             } 
          break;}else if(index==2){
            String name[]= new String [6];
             int n=0;
             int randomInt = random.nextInt(50000);
        StringTokenizer token = new StringTokenizer (message,">>");
        while(token.hasMoreTokens()){
            name[n]=token.nextToken();
            n++;
        }
         if(n==1){
            String inV = "INSERT INTO messages(messageID,username,message,options) values('"+randomInt+"','"+"Lecturer"+"','"+name[0]+"','"+name[1]+"'"+");";
            database.insert_or_Update(inV);
            }else{
             String inV = "INSERT INTO messages(messageID,username,message,options) values('"+randomInt+"','"+"Lecturer"+"','"+message+"','"+""+"'"+");";
            database.insert_or_Update(inV);
            }
              /* The message is public, broadcast it to all other clients. */
          synchronized (this) {
              
            for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i].clientName != null) {
                threads[i].os.println(randomInt+">Lecturer>"+message);
                 System.out.println(randomInt+">Lecturer>"+message);
              }
            }
          }
          break;
         }else if(index==3){
         for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i].clientName != null) {
                threads[i].os.close();
               threads[i].is.close();
               threads[i].clientSocket.close();
              }
            }
         if(clientSocket!=null||serverSocket!=null){
         this.clientSocket.close();
        this.serverSocket.close();}
         break;
         }
         else{
        clientSocket = serverSocket.accept();       
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
          
        }
         }
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  database.disconnect();
  this.stop();
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
      String name;
      os.println("ENTER YOUR NAME");
      while (true) {
        name = is.readLine().trim();
        if (name.indexOf('@') == -1) {
            System.out.println(name);
          break;
        } else {
          os.println("The name should not contain '@' character.");
        }
      }
      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] != null && threads[i] == this) {
            clientName = "@" + name;
            break;
          }
        }
      }
      /* Start the conversation. */
      while (true) {
        String line = is.readLine();
        if (line.startsWith("/quit")) {
          break;
        }
         String nam[]= new String [6];
         int n=0;
         StringTokenizer token;
        if(line.contains("#")){ 
             synchronized (this) {
              
            for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i].clientName != null) {
                threads[i].os.println(line);   
                
              }
            }
          }
         System.out.println(line); 
         token = new StringTokenizer (line,"#");
        while(token.hasMoreTokens()){
            nam[n]=token.nextToken();
            n++;
        }
           String inV = "INSERT INTO response(messageID,username,response) values('"+nam[0]+"','"+name+"','"+nam[1]+"'"+");";
           database.insert_or_Update(inV);
      }else{
          int randomInt = random.nextInt(50000);            
           String inV = "INSERT INTO messages(messageID,username,message,options) values('"+randomInt+"','"+name+"','"+line+"','"+""+"'"+");";
           database.insert_or_Update(inV);
          /* The message is public, broadcast it to all other clients. */
          synchronized (this) {
            for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i].clientName != null) {
                threads[i].os.println(+randomInt+">>" + name + ">> " + line);
                 System.out.println(+randomInt+">>" + name + ">> " + line);
              }
              }
            }
          }
        }
      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] != null && threads[i] != this
              && threads[i].clientName != null) {
            threads[i].os.println("*** The user " + name
                + " is leaving the chat room !!! ***");
          }
        }
      }
      os.println("*** Bye " + name + " ***");

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
      database.disconnect();
      is.close();
      os.close();
      clientSocket.close();
    } catch (IOException e) {
    }
}}
}