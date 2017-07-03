/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystemserver.filemanager;

import classsystemserver.dabaseConnect.DatabaseConn;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author loxbae
 */
public class FileManager {
    DatabaseConn c;
    public FileManager(){
    c = new DatabaseConn("jdbc:mysql://localhost:3306/classdatabase", "root", "");}
    Desktop desktop = Desktop.getDesktop();
    ObservableList<String> items =FXCollections.observableArrayList ();
     
    public void openFile(){    
     FileChooser chooser = new FileChooser();
    chooser.setTitle("Open File");
    c.dBConnect();
    File file = chooser.showOpenDialog(new Stage());  
    try{
    if (file != null) {
        String r=file.getPath();
       //items.add(r);
        String name[]= new String [6];
        int n=0;
        String dir="";
       if(r.contains("\\")){
           System.out.println(r);
        StringTokenizer token = new StringTokenizer (r,"\\");
        while(token.hasMoreTokens()){
            name[n]=token.nextToken();
            dir= dir+"/"+name[n];
            n++;            
        } 
        String inV = "INSERT INTO recentfiles(fname) values('"+dir.replaceFirst("/","")+"');";
          c.insert_or_Update(inV);
       }
       desktop.open(file);
        } } catch (IOException ex) {            
   System.out.print(ex);
     }
    }
    public ObservableList<String> recentFile(){
        try{
            c.dBConnect();
            String SQL = ("SELECT fname FROM recentfiles");
            ResultSet rs = c.queryDatabase(SQL);       
             while (rs.next()) {
	items.add(rs.getString("fname"));
             }
          }catch(Exception e){
             System.out.println(e);            
          }
          
    return items;
    }
     public File sendFile(){
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
    return file; }
}
