/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystemserver.model;

import classsystemserver.dabaseConnect.DatabaseConn;
import java.sql.ResultSet;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
      public void postResults(ListView list){
     list.setCellFactory(new Callback<ListView<String>, 
            ListCell<String>>() {
                @Override 
                public ListCell<String> call(ListView<String> list) {
                    return new CustomizedResults();
                }
            }
        );}
   DatabaseConn c = new DatabaseConn("jdbc:mysql://localhost:3306/classdatabase", "root", "");
    class CustomizedLecturer extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
           Label label1;       
      if (item != null) { 
          Label label2 = new Label();
          try{
            c.dBConnect();
            String SQL = ("SELECT username FROM messages where message like '"+item+"'");
        ResultSet rs = c.queryDatabase(SQL);        
        while (rs.next()) {  
        label1 = new Label(item);
        label1.setFont(new Font("Arial",14));
        label2.setTextFill(Color.web("#0076a3"));
	label2.setText(rs.getString("username")+":");
        label2.setFont(new Font("Arial",16));
         label1.setGraphic(label2);
         label1.setPrefHeight(20);
          Label label= new Label();
          label.setPrefHeight(40);
          label.setGraphic( label1);
          setGraphic(label); 
             }
        c.disconnect();
          }catch(Exception e){
             System.out.println(e);            
          }     
    
      Image image = new Image(getClass().getResourceAsStream("/classsystemserver/ui/1497561617_pdf.png"));
     
       
            }
        }
    }
    class CustomizedResults extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
           Label label1;       
      if (item != null) {   
        label1 = new Label(item);
        label1.setAlignment(Pos.CENTER);
        label1.setFont(new Font("Arial",14));
         label1.setPrefHeight(20);
          setGraphic(label1); 
            }
        }
    }
}
