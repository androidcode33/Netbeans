/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystem.model;

import java.util.StringTokenizer;
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
public class DesignManager {
    public void designLecturerList(ListView list){
     list.setCellFactory(new Callback<ListView<String>, 
            ListCell<String>>() {
                @Override 
                public ListCell<String> call(ListView<String> list) {
                    return new CustomizedLecturer();
                }
            }
        );}
    static class CustomizedLecturer extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
           Label label1;       
          
            if (item != null) {  
                System.out.println(item);  
       label1 = new Label(item);
      label1.setTextFill(Color.web("#0076a3"));          
      Image image = new Image(getClass().getResourceAsStream("/classsystemserver/ui/1497561617_pdf.png"));
      label1.setGraphic(new ImageView(image));
     setGraphic(label1);      
            }
        }
    }
}
