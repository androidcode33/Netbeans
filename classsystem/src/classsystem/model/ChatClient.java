package classsystem.model;


import classsystem.dadaseConnect.DatabaseConn;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.StringTokenizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author loxbae
 */
public class ChatClient extends Thread {
  private  DataInputStream is;
    public ChatClient( DataInputStream is){
    this.is=is;
    }
  public void run() {
    String responseLine;
    String name[]= new String [1000];
     String id;
     DatabaseConn database= new DatabaseConn("jdbc:mysql://localhost:3306/classdatabase", "root", "");
    
     
    try {
      while ((responseLine = is.readLine()) != null) {          
       System.out.println(responseLine);
        String nam[]= new String [6]; 
if(responseLine.contains("*")){  
      database.dBConnect();
        int i=0; id="";
        StringTokenizer token = new StringTokenizer (responseLine,"*");
        while(token.hasMoreTokens()){
            id =token.nextToken();
            System.out.println(id);
            if(id.contains("<")){
            int f =0;
             StringTokenizer token1 = new StringTokenizer (id,"<");
             while(token1.hasMoreTokens()){
              nam[f]=token1.nextToken();
              System.out.println(nam[f]);
              f++;             
             }
            String inV = "INSERT INTO clientresponse(messageID,username,response) values('"+nam[0]+"','"+nam[1]+"','"+nam[2]+"'"+");";
            database.insert_or_Update(inV);
            }
              i++;
        }
          database.databaseDisconnect();
}/*
         if(i==1){
            String inV = "INSERT INTO clientmessages(messageID,username,message,options) values('"+name[0]+"','"+name[1]+"','"+name[2]+"','"+name[3]+"'"+");";
            database.insert_or_Update(inV);
            }else{
             String inV = "INSERT INTO clientmessages(messageID,username,message,options) values('"+name[0]+"','"+name[1]+"','"+name[2]+"','"+""+"'"+");";
            database.insert_or_Update(inV);
            }        
        */
        if (responseLine.indexOf("*** Bye") != -1)
          break;
      }   
    } catch (IOException e) {
      System.err.println("IOException:  " + e);
    }
  }
}
