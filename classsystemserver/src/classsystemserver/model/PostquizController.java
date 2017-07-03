/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystemserver.model;

import classsystemserver.Classsystemserver;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author loxbae
 */
public class PostquizController implements Initializable {
    int counter=0;
    String questionType;
    RadioButton rb1 = new RadioButton();
    RadioButton rb2 = new RadioButton();
    RadioButton rb3 = new RadioButton();
    RadioButton rb4 = new RadioButton(); 
    TextField m0;
    TextField m1;
    TextField m2;
    TextField m3;
    TextField m4;          
    HBox f1,f2,f3,f4;
     VBox fin;
 @FXML
    private TextField questionField;  
   @FXML private Label postQuizArea;
   @FXML
    private ComboBox combox;
   @FXML
    void handlePost(ActionEvent event) {
        String results="";
     if(questionType=="Structured"){
     if(counter==2){        
          results=m0.getText()+">>"+m1.getText()+"-"+m2.getText();        
       }
     if(counter==3){  results= m0.getText()+">>"+m1.getText()+"-"+m2.getText()+"-"+m3.getText();
      }
    
     if(counter==4){
      results=m0.getText()+">>"+m1.getText()+"-"+m2.getText()+"-"+m3.getText()+"-"+m4.getText();
      }
     } else{
     if(counter==0){
      results=m0.getText();
     }
     }
     if( results!=""){
     ServerSocket serverSocket = null;
     Classsystemserver app =new Classsystemserver();
     serverSocket=app.startServer(); 
     ChatServer server = new ChatServer(serverSocket,results,1);     
     server.start();}
     
    }    ToggleGroup group = new ToggleGroup(); 
    public void viewStructured(int counter){
    if(counter==2){
    m0 = new TextField();
    m0.setPrefHeight(60);
    m0.setPromptText("Type Question Here");
    m1 = new TextField();
    m0.setPrefHeight(20);
    m2 = new TextField();
    m2.setPrefHeight(20);
    rb1.setUserData(m1.getText());
    rb2.setUserData(m2.getText());    
    rb1.setToggleGroup(group);
    rb2.setToggleGroup(group);
    f1= new HBox(rb1,m1);
    f2= new HBox(rb2,m2);
    fin = new VBox(m0,f1,f2);
   // fin.setSpacing(5);
    }
    if(counter==3){
    m3= new TextField();
    m3.setPrefHeight(20);
    rb3.setUserData(m3.getText());
    f3= new HBox(rb3,m3);
    fin = new VBox(m0,f1,f2,f3);    
    rb3.setToggleGroup(group);}
    if(counter==4){
    m4= new TextField();
     m4.setPrefHeight(20);
    rb4.setUserData(m4.getText());
    f4= new HBox(rb4,m4);    
    rb4.setToggleGroup(group);
    fin = new VBox(m0,f1,f2,f3,f4);
    fin.setSpacing(10);}
    }
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    ObservableList<String> items =FXCollections.observableArrayList (
    "Structured", "Unstructured");
    combox.setItems(items);
   group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
    public void changed(ObservableValue<? extends Toggle> ov,
        Toggle old_toggle, Toggle new_toggle) {
            if (group.getSelectedToggle() != null) {
                System.out.println(group.getSelectedToggle().getUserData().toString());                 
              
            }                
        }
});
    combox.setOnAction((Event ev) -> {
     questionType =  combox.getSelectionModel().getSelectedItem().toString();  
    System.out.println(questionType);
    if(questionType=="Structured"){
    counter=2;
    viewStructured(counter);
    postQuizArea.setText(null);
    postQuizArea.setGraphic(fin);
    }
    if(questionType=="Unstructured"){
     counter=0;
     postQuizArea.setText(null);
     m0 = new TextField();
      m0.clear();
      m0.setPrefHeight(70);
    postQuizArea.setGraphic(m0);
    }
});
    
    postQuizArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
    public void handle(KeyEvent ke) {
       System.out.println(ke.getCode());
        if(questionType=="Structured"){
            if(ke.getCode().toString()=="ENTER"){
         counter++;
        viewStructured(counter);
        postQuizArea.setText(null);
        postQuizArea.setGraphic(fin);
            }  if(ke.getCode().toString()=="ESCAPE"){
                counter--;
                viewStructured(counter);
                 postQuizArea.setText(null);
                postQuizArea.setGraphic(fin);
            }
       }
    }
});
    }    
    
}
