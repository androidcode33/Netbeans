/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystemserver.model;
import classsystemserver.FXMLDocumentController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author loxbae
 */
public class LoginController implements Initializable {
 
    /**
     * Initializes the controller class.
     */
      @FXML Button loginn;
    @FXML TextField username,password;
     private FXMLDocumentController  application = new FXMLDocumentController();
    @FXML void login(ActionEvent event) {
 
    try{ 
      application.informationDialog("Creating Wifi Hot spot ..."); 
      Process myappProcess1 = Runtime.getRuntime().exec("powershell -command \"Start-Process cmd -ArgumentList '/c netsh wlan set hostednetwork mode=allow ssid="+username.getText()+" key=" +password.getText()+" && netsh wlan start hostednetwork && pause' -Verb runas\"");
     
    }catch(Exception e){ System.out.println(e);
     }
    }   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }    
    
}
