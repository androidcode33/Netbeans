/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystem.model;

import classsystem.ClasssystemClient;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author loxbae
 */
public class PostquizController implements Initializable {
  @FXML
    private TextField quizField; 
     // The output stream
    private static PrintStream os = null;
   private static DataInputStream is = null;
     Socket clientSocket = null;
    @FXML
    void postQuiz(ActionEvent event) {
        // The client socket
       
        os.println(quizField.getText());
        quizField.clear();    
        if(is!=null){
       ChatClient client =new ChatClient(is);
       client.start();}
     
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ClasssystemClient app =new ClasssystemClient();   
       try{        
       os = app.startsending();
       is = app.startreciving();
      }catch(Exception e){
      System.out.println(e);
      }
    
        // TODO
    }    
    
}
