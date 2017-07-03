/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystemserver;

import classsystemserver.dabaseConnect.DatabaseConn;
import classsystemserver.filemanager.FileManager;
import classsystemserver.filemanager.FileServer;
import classsystemserver.model.ChatServer;
import classsystemserver.model.DiscoveryThread;
import classsystemserver.model.LecturerModel;
import classsystemserver.model.StudentModel;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 *
 * @author loxbae
 */
public class FXMLDocumentController implements Initializable {
    @FXML TextField LecturerFeedBack;
    @FXML private Label label;
    @FXML  private Label studentPostDetails;
    @FXML  private Label LecturerPostDetails;
    @FXML   private  ListView<String> studentlist;
    @FXML   private  ListView<String> LecturerList;
    @FXML   private Button viewgrade;
    @FXML   private Button about;
    @FXML   private Button create;
    @FXML   private TextField feedBackField;  
    @FXML   private Label date;
    @FXML   private Label time;
       // The server socket.
   private static ServerSocket serverSocket = null; 
   private static ServerSocket serverSocket2 = null;
   int portNumber1=8888;   int portNumber=2222;
    ObservableList<String> studentListData= FXCollections.observableArrayList();
    ObservableList<String> data = FXCollections.observableArrayList();
    FileManager f= new FileManager();   
    StudentModel student = new StudentModel();
    LecturerModel lecturer = new LecturerModel();
    private Stage reportStage;
    private Classsystemserver application = new Classsystemserver();
    DatabaseConn database= new DatabaseConn("jdbc:mysql://localhost:3306/classdatabase", "root", "");
    public  Stage Chooseclass;
    String k,l;       
    ChatServer server;
    FileServer fileserver;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // time and date events
      Date dNow = new Date();       
      SimpleDateFormat ftt =   new SimpleDateFormat (" hh:mm:ss a");
      SimpleDateFormat ftd =  new SimpleDateFormat (" E dd/MM/yyyy");
      time.setText(ftt.format(dNow));  date.setText(ftd.format(dNow));
      // chat server events
      Classsystemserver app =new Classsystemserver();
      serverSocket=app.startServer();      
      try{      
             
      serverSocket2= new ServerSocket(8888);
      // updating listview interfaces
      Timer timer = new java.util.Timer();
      timer.schedule(new TimerTask() {
    public void run() {
    Platform.runLater(new Runnable() {
    public void run() {
    Date dNow = new Date();
     SimpleDateFormat ftt =  new SimpleDateFormat (" hh:mm:ss a");
     time.setText(ftt.format(dNow));
     studentListData.clear();
     data.clear();
     studentListData=student.fetchNames();
     studentlist.setItems(studentListData);
     data=lecturer.fetchNames();
     LecturerList.setItems(data);
            }
        });
    }
},0,3000);
      }
   catch( Exception e){
  
  }
        // list view manipulations
    studentlist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        // Your action here
        k =studentlist.getSelectionModel().getSelectedItem().toString();
        student.viewpostDetails(k,studentPostDetails);
    }
});
   
        // TODO
    LecturerList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        // Your action here
        l =LecturerList.getSelectionModel().getSelectedItem().toString();
        lecturer.viewpostDetails(l,LecturerPostDetails,0);       
    }
    
});
   // new DesignManager().postResults(LecturerList);
   // new DesignManager(). designLecturerList(studentlist);
   }

      @FXML
    void handleStudentPostFeedback(ActionEvent event) {
        // handle posting feedback student
        database.dBConnect();
         try{            
            String SQL = ("SELECT messageID FROM messages where message like '"+k+"'");
            ResultSet rs = database.queryDatabase(SQL); 
             while (rs.next()) {
	System.out.println(rs.getString("messageID"));
        ChatServer server = new ChatServer(serverSocket,rs.getString("messageID")+"#"+feedBackField.getText(),1);     
        server.start();
        feedBackField.clear();  
             }
          }catch(Exception e){
             System.out.println(e);            
          }
      database.disconnect();
      
    }
    @FXML    void handleLecturerPostFeedback(ActionEvent event){
        // handle posting feedback lecturer
        database.dBConnect();
             try{
            String SQL = ("SELECT messageID FROM messages where message like '"+l+"'");
            ResultSet rs = database.queryDatabase(SQL); 
             while (rs.next()) {
            ChatServer server = new ChatServer(serverSocket,rs.getString("messageID")+"#"+LecturerFeedBack.getText(),1);     
            server.start();
             }
          LecturerFeedBack .clear();
          }catch(Exception e){
             System.out.println(e);            
          }
             database.disconnect();
}
         @FXML
    void openFile(ActionEvent event) {
       f.openFile();
    }
      
           @FXML
    void stopServer(ActionEvent event){
        // The client socket.
      try{
      server = new ChatServer(serverSocket,"",3);
      server.start();
      server.stop();
      //serverSocket.close();
      
      }catch(Exception e){
     System.out.println(e);
     }
     
    
    }
     @FXML
    void startServer(ActionEvent event){
        // The client socket.
      try{
      Thread discoveryThread=new Thread(DiscoveryThread.getInstance());
      discoveryThread.start();
      server = new ChatServer(serverSocket,"",0);
      server.start();
      }catch(Exception e){
     System.out.println(e);
     }   
    
    }
     @FXML
    void recentFileWindow(ActionEvent event) {
        application.recentFileWindow();
    }
         @FXML
    void viewAttendanceWindow(ActionEvent event) {
     application.viewAttendanceWindow();
              }
     @FXML
    void shareFile(ActionEvent event) {
      File file  = f.sendFile();
     fileserver = new FileServer(serverSocket2,file);
     fileserver.start();
    }
     @FXML
    void postQuiz(ActionEvent event) {
        application.postQuizes();
    }
        @FXML
    void handlegrade(ActionEvent event) {
        lecturer.unStructured();
    // lecturer.viewpostDetails(k,LecturerPostDetails,1);
    // System.out.println(lecturer.broadcast());
//      ChatServer server = new ChatServer(serverSocket,lecturer.broadcast(),1);     
//      server.start();
      
    }
      public void  stopWifi(){
        try{
    Process myappProcess1 = Runtime.getRuntime().exec("powershell -command \"Start-Process cmd -ArgumentList '/c netsh wlan stop hostednetwork && pause' -Verb runas\"");
       informationDialog("wifi stopped!");
        }catch(IOException e){System.out.println(e);
        }     
     }
     public void startWifi(){
          application.login();
     }
     public void informationDialog(String info){
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information Dialog");
    alert.setHeaderText(null);
    alert.setContentText(info);
    alert.showAndWait();
     }


    
}
