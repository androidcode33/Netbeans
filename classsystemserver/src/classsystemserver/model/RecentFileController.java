/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystemserver.model;

import classsystemserver.filemanager.FileManager;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 *
 * @author loxbae
 */
public class RecentFileController  implements Initializable{
    FileManager recent = new FileManager();
       Desktop desktop = Desktop.getDesktop();
    @FXML
    private ListView<String> list;
     @Override
    public void initialize(URL url, ResourceBundle rb) {
      ObservableList<String> items =FXCollections.observableArrayList ();
      items= recent.recentFile();
       list.setItems(items);
         list.setCellFactory(new Callback<ListView<String>, 
            ListCell<String>>() {
                @Override 
                public ListCell<String> call(ListView<String> list) {
                    return new ColorRectCell();
                }
            }
        );
                // TODO
   list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        // Your action here
      String  l =list.getSelectionModel().getSelectedItem().toString();
      File file =new File(l);
      try{
       desktop.open(file);}catch(IOException e){
       System.out.println(e);
       }
    }
});
        
    }
     static class ColorRectCell extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
           Label label1;       
          
            if (item != null) {  
                System.out.println(item);
                System.out.println(item);
        String dir="",name="";
        int n=0;
        StringTokenizer token = new StringTokenizer (item,"/");
        while(token.hasMoreTokens()){
            name=token.nextToken();
            if(n==0){dir=name;}else{
            dir=dir+"\\\\"+name;} 
            n++;
        }  
       label1 = new Label(dir);
      label1.setTextFill(Color.web("#0076a3"));
        if(item.contains(".pdf")){            
            Image image = new Image(getClass().getResourceAsStream("/classsystemserver/ui/1497561617_pdf.png"));
            label1.setGraphic(new ImageView(image));
        }else if(item.contains(".docx")){
         Image image = new Image(getClass().getResourceAsStream("/classsystemserver/ui/1497561130_file-word.png"));
           label1.setGraphic(new ImageView(image));          
        }else if(item.contains(".ppt")) {
        Image image = new Image(getClass().getResourceAsStream("/classsystemserver/ui/1497560665_eps_extension_file_name-16.png"));
           label1.setGraphic(new ImageView(image));                
                }       
           setGraphic(label1);      
            }
        }
    }
}
