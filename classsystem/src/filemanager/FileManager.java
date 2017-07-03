/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import classsystem.dadaseConnect.DatabaseConn;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author loxbae
 */
public class FileManager {
    Desktop desktop = Desktop.getDesktop();
   ObservableList<String> items =FXCollections.observableArrayList ();
   DatabaseConn c = new DatabaseConn("jdbc:mysql://localhost:3306/classdatabase", "root", "");
    public void openFile(){ 
    c.dBConnect();
     FileChooser chooser = new FileChooser();
    chooser.setTitle("Open File");
    File file = chooser.showOpenDialog(new Stage());  
    try{
    if (file != null) {
        String x=file.getPath();
         System.out.print(x);
      String recentfiles="insert into recentfiles(fname) value('"+x+"');";
      c.insert_or_Update(recentfiles);
       desktop.open(file);       
        } } catch (IOException ex) {            
   System.out.print(ex);
     }
     c.databaseDisconnect();
    }
   public ObservableList<String> recentFile(){
        try{
            int i =0;
            c.dBConnect();
            String SQL = ("SELECT fname FROM recentfiles");
            ResultSet rs = c.queryDatabase(SQL);        
             while (rs.next()) {
	   items.add(rs.getString("fname"));
           i++;
             }
          }catch(Exception e){
             System.out.println(e);            
          } 
         c.databaseDisconnect();
    return items;
    }
     public String sendFile(){
         String k="";
    FileChooser chooser = new FileChooser();
    chooser.setTitle("Open File");
    File file = chooser.showOpenDialog(new Stage());  
    try{
    if (file != null) {
         System.out.print(file.getPath());
       //label.setText(file.getPath());
       desktop.open(file);
        System.out.print("file selected");
        } } catch (IOException ex) {            
   System.out.print(ex);
     }
    k=file.getPath();
    return k; }
}
