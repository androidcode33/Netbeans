/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystemserver.model;

import classsystemserver.dabaseConnect.DatabaseConn;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author loxbae
 */
public class StudentModel {
        DatabaseConn c = new DatabaseConn("jdbc:mysql://localhost:3306/classdatabase", "root", "");
        ObservableList<String> items =FXCollections.observableArrayList ();
        public ObservableList<String> fetchNames(){
        try{
            items.clear();
            c.dBConnect();
            String SQL = ("SELECT message FROM messages where username != 'Lecturer' ");
            ResultSet rs = c.queryDatabase(SQL); 
       
             while (rs.next()) {
             
	items.add(rs.getString("message"));
             }
          }catch(Exception e){
             System.out.println(e);            
          }
          c.disconnect();
    return items;
    }
ObservableList<String> data = FXCollections.observableArrayList("chocolate");
public void viewpostDetails(String k,Label studentPostDetails){
    //A label with the text element
Label header = new Label(k);
 try{
            c.dBConnect();
            String SQL = ("SELECT messageID FROM messages where message like'"+k+"'");
            ResultSet rs = c.queryDatabase(SQL); 
       
             while (rs.next()) {
          String temp=rs.getString("messageID");
          String SQLL = ("SELECT * FROM response where messageID like'"+temp+"'");
            ResultSet rss = c.queryDatabase(SQLL); 
            data.clear();
           while (rss.next()) {
	data.add(rss.getString("response"));}
             }
          }catch(Exception e){
             System.out.println(e);            
          }
ListView<String> list = new ListView<String>();
list.setItems(data);
VBox details = new VBox(header,list);
header.setTextFill(Color.web("#0076a3"));
header.setPrefHeight(60);
header.setPrefWidth(400);
//header.setFont(new Font("Arial",16));
new DesignManager().postResults(list);
studentPostDetails.setText(null);
studentPostDetails.setGraphic(details);
c.disconnect();
    }
}
