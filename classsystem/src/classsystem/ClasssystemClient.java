/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystem;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author loxbae
 */
public class ClasssystemClient extends Application {
    public  Stage postquiz;
     private Stage recentfiles;
     public  Stage login;
     private static PrintStream os = null;
  // The input stream
  private static DataInputStream is = null;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        
        Scene scene = new Scene(root);
         int portNumber=2222;
   try{
   String host ="localhost";
    clientSocket = new Socket(host, portNumber);
   os = new PrintStream(clientSocket.getOutputStream());
   is = new DataInputStream(clientSocket.getInputStream());}
   catch( Exception e){
  
  }
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
     Stage chsCls = new Stage();
     public void login() {
         try {
            chsCls.setTitle(" Post Quiz Window");
            chsCls.initModality(Modality.APPLICATION_MODAL);
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene cene = new Scene(root);
            chsCls.setScene(cene); 
            chsCls.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(ClasssystemClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void closeLogin(){
     chsCls .close();
     }
      public void postQuizes() {
         try {
            Stage chsCls = new Stage();
            postquiz =chsCls;
            chsCls.setTitle(" Post Quiz Window");
           // chsCls.initOwner(application.stage);
            chsCls.initModality(Modality.APPLICATION_MODAL);
            Parent root = FXMLLoader.load(getClass().getResource("postquiz.fxml"));
            Scene cene = new Scene(root);
            chsCls.setScene(cene); 
            chsCls.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(ClasssystemClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
           
   public void recentFileWindow() {
      try {
            Stage report = new Stage();
            recentfiles =report;
            report.setTitle("Recent Files window");
           // report.initOwner(application.stage);
            report.initModality(Modality.APPLICATION_MODAL);
            Parent root = FXMLLoader.load(getClass().getResource("recentFile.fxml"));
            Scene cene = new Scene(root);
            report.setScene(cene); 
            report.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(ClasssystemClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    Socket clientSocket;
   public DataInputStream startreciving(){  
   return is ;
   }
   public PrintStream startsending(){  
   return os ;
   }
    
}
