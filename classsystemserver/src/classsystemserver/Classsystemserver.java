/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystemserver;

import classsystemserver.dabaseConnect.DatabaseConn;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author loxbae
 */
public class Classsystemserver extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
   ServerSocket serverSocket = null;
   public ServerSocket startServer(){
   int portNumber=2222;
   try{
  serverSocket = new ServerSocket(portNumber);}
   catch( Exception e){
  }
   return serverSocket;
   }
      private Stage reportStage;
    private Classsystemserver application;
       void postQuizes() {
         try {
          Stage chsCls = new Stage();
            chsCls.setTitle(" Post Quiz Window");
           // chsCls.initOwner(application.stage);
            chsCls.initModality(Modality.APPLICATION_MODAL);
            Parent root = FXMLLoader.load(getClass().getResource("postquiz.fxml"));
            Scene cene = new Scene(root);
            chsCls.setScene(cene); 
            chsCls.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(Classsystemserver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
         void viewAttendanceWindow() {
      try {
            Stage report = new Stage();
            reportStage =report;
            report.setTitle("Students' Attendance window");
           // report.initOwner(application.stage);
            report.initModality(Modality.APPLICATION_MODAL);
            Parent root = FXMLLoader.load(getClass().getResource("viewAttendance.fxml"));
            Scene cene = new Scene(root);
            report.setScene(cene); 
            report.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(Classsystemserver.class.getName()).log(Level.SEVERE, null, ex);
        }}
            void recentFileWindow() {
      try {
            Stage report = new Stage();
           
            report.setTitle("Recent Files window");
           // report.initOwner(application.stage);
            report.initModality(Modality.APPLICATION_MODAL);
            Parent root = FXMLLoader.load(getClass().getResource("recentFile.fxml"));
            Scene cene = new Scene(root);
            report.setScene(cene); 
            report.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(Classsystemserver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      public Stage chsCls2 = new Stage();
     public void login() {
         try {
            chsCls2.setTitle("Start WifiHotspot");
           // chsCls.initOwner(application.stage);
            chsCls2.initModality(Modality.APPLICATION_MODAL);
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene cene = new Scene(root);
           chsCls2.setScene(cene); 
            chsCls2.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(Classsystemserver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
