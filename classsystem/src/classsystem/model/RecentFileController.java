/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystem.model;

import filemanager.FileManager;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 *
 * @author loxbae
 */
public class RecentFileController  implements Initializable{
    FileManager recent = new FileManager();
    
    @FXML
    private ListView<String> list;
     @Override
    public void initialize(URL url, ResourceBundle rb) {
      ObservableList<String> items =FXCollections.observableArrayList ();
      items= recent.recentFile();
       list.setItems(items);
           list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        // Your action here
      String  k =list.getSelectionModel().getSelectedItem().toString();
         File file = new File(k);
          System.out.print(k);
         Desktop desktop = Desktop.getDesktop();
           try{
    if (file != null) {
         System.out.print(file.getPath());
       desktop.open(file);
        System.out.print("file selected");
        } } catch (IOException ex) {            
   System.out.print(ex);
     }
       
    }
});
    }
}
