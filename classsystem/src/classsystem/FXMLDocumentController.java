/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystem;

import classsystem.dadaseConnect.DatabaseConn;
import classsystem.model.ChatClient;
import classsystem.model.DesignManager;
import classsystem.model.LecturerModel;
import classsystem.model.StudentModel;
import filemanager.FileClient;
import filemanager.FileManager;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.sql.ResultSet;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 *
 * @author loxbae
 */
public class FXMLDocumentController implements Initializable {
     // The client socket
  private static Socket clientSocket = null;
  // The output stream
  private static PrintStream os = null;
  // The input stream
  private static DataInputStream is = null;

  private static BufferedReader inputLine = null;
  private static boolean closed = false;
   FileManager f= new FileManager(); 
    private Stage reportStage;
   private ClasssystemClient application = new ClasssystemClient();
    public  Stage Chooseclass;
  @FXML  private Label LecturerPostDetails;
  @FXML  private Button answer;
 @FXML  private Button postIt;
 @FXML  private Button help;
    LecturerModel lecturer = new LecturerModel();    
    StudentModel student = new StudentModel();
      String k,l;
       ToggleGroup group = lecturer.group; 
    @FXML
    private TextField feedBackField;
    @FXML
    private TextField LecturerFeedBack;
    @FXML   private  ListView<String> studentlist;
     @FXML   private  ListView<String> LecturerList;
    @FXML  private Label studentPostDetails;
   DatabaseConn database= new DatabaseConn("jdbc:mysql://localhost:3306/classdatabase", "root", "");
    ObservableList<String> data = FXCollections.observableArrayList();
   ObservableList<String> studentListData = FXCollections.observableArrayList();
    ClasssystemClient app =new ClasssystemClient();    
           String choice="";
        // The client socket
    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
        @FXML
    void handleStudentPostFeedback(ActionEvent event) {
        
          os = app.startsending();
        if(os!=null){
              try{
            database.dBConnect();
            String SQL = ("SELECT messageID FROM messages where message like '"+k+"'");
            ResultSet rs = database.queryDatabase(SQL); 
             while (rs.next()) {
        os.println(rs.getString("messageID")+"#"+feedBackField.getText());
        feedBackField.clear(); 
             }
             database.databaseDisconnect();
          }catch(Exception e){
             System.out.println(e);            
          }
          } 
        
    }
@FXML    void handleLecturerPostFeedback(ActionEvent event){ 
    lecturer.viewpostDetails(l,LecturerPostDetails,0);
    System.out.println(answer.getText());
     String messageID="";  
             try{
           os = app.startsending();
            database.dBConnect();
            String SQL = ("SELECT messageID FROM messages where message like '"+l+"'");
            ResultSet rs = database.queryDatabase(SQL); 
             while (rs.next()) {
          messageID=rs.getString("messageID");
             }
             System.out.println(lecturer.flag);
             if(lecturer.flag=="structured"&&choice!=""){
            // System.out.println(messageID+"#"+choice);
             os.println(messageID+"#"+choice);
             choice="";
             }else{
             os.println(messageID+"#"+lecturer.m0.getText());
             lecturer.m0.clear();           
           }
          }catch(Exception e){
             System.out.println(e);            
          }
          database.databaseDisconnect();
}
   Timer timer = new java.util.Timer();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     // updating listview interfaces
     
 
  
      timer.schedule(new TimerTask() {
    public void run() {
  Platform.runLater(new Runnable() {
  public void run() {
     studentListData=student.fetchNames();
     studentlist.setItems(studentListData);
     data=lecturer.fetchNames();
     LecturerList.setItems(data);
            }
        });
    }
},0,3000);
       // application.login();
           // timer.cancel();

       try{  
    String host ="localhost";
    int portNumber=8888;
       clientSocket = new Socket(host, portNumber);
        FileClient client2 =new FileClient(clientSocket);
        client2.start();
       os = app.startsending();
       is = app.startreciving();
       if(is!=null){
       ChatClient client =new ChatClient(is);
       client.start();}
      }catch(Exception e){
      System.out.println(e);
      }    studentlist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        k =studentlist.getSelectionModel().getSelectedItem().toString();
        System.out.println(k);
        student.viewpostDetails(k,studentPostDetails);
    }
});
         // TODO
    LecturerList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        l =LecturerList.getSelectionModel().getSelectedItem().toString();
         lecturer.viewpostDetails(l,LecturerPostDetails,0);
       
    }
    
});
   //new DesignManager(). designLecturerList(LecturerList);
    group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
    public void changed(ObservableValue<? extends Toggle> ov,
        Toggle old_toggle, Toggle new_toggle) {
            if (group.getSelectedToggle() != null) {
                choice=group.getSelectedToggle().getUserData().toString();                            
              
            }                
        }
});

    }    
  /*  @FXML
public void handleCloseButtonAction(ActionEvent event) {
    ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
}*/
    
  @FXML void viewAnswers(ActionEvent event) {
     // if(answer.getText()=="View Answer"){
    lecturer.viewpostDetails(l,LecturerPostDetails,1);
    // answer.setText("View Question");
    // postIt.setVisible(false);
   // }/*else{
     /// lecturer.viewpostDetails(l,LecturerPostDetails,1);
      // answer.setText("View Answer");
     // postIt.setVisible(true);
     // }*/
    }
    @FXML
    void openFile(ActionEvent event) {
      f.openFile();
      // app.login();
    }
     @FXML
    void recentFileWindow(ActionEvent event) {
     app.recentFileWindow();
    }
     @FXML void postQuiz(ActionEvent event) {
      app.postQuizes();
    }
}
/* while (true)
 {
   final int finalI = i++;
   Platform.runLater ( () -> label.setText ("" + finalI));
   Thread.sleep (1000);
 }*/