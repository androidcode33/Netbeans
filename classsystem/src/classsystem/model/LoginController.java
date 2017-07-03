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
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author loxbae
 */
public class LoginController implements Initializable {
 
    /**
     * Initializes the controller class.
     */
    @FXML TextField username,password;
        // The output stream
    private static PrintStream os = null;
   private static DataInputStream is = null;
     Socket clientSocket = null;
   //   ClasssystemClient app =new ClasssystemClient();  
       @FXML void login(ActionEvent event) {
        os.println(username.getText());
        password.clear();    
        username.clear();
    }
       ClasssystemClient app =new ClasssystemClient(); 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       try{        
       os = app.startsending();
       is = app.startreciving();               
     if(is!=null){
       ChatClient client =new ChatClient(is);
       client.start();}
      }catch(Exception e){
      System.out.println(e);
      }
    }    
    
}
