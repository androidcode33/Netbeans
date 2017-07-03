/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystem.model;

import classsystem.dadaseConnect.DatabaseConn;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author loxbae
 */
public class LecturerModel {
    DatabaseConn c = new DatabaseConn("jdbc:mysql://localhost:3306/classdatabase", "root", "");
    ObservableList<String> items =FXCollections.observableArrayList ();
       public String flag="";
        int counter=0;
    String questionType;
    RadioButton rb1 = new RadioButton();
    RadioButton rb2 = new RadioButton();
    RadioButton rb3 = new RadioButton();
   RadioButton rb4 = new RadioButton(); 
   public TextField m0;
    TextField m1;
    TextField m2;
    TextField m3;
    TextField m4;          
    HBox f1,f2,f3,f4;
   public  ToggleGroup group = new ToggleGroup(); 
     VBox fin;
        public ObservableList<String> fetchNames(){
        try{
            items.clear();
            c.dBConnect();
            String SQL = ("SELECT message FROM messages where username like 'Lecturer'");
            ResultSet rs = c.queryDatabase(SQL); 
       
             while (rs.next()) {
	items.add(rs.getString("message"));
             }
          }catch(Exception e){
             System.out.println(e);            
          }
          c.databaseDisconnect();
    return items;
    }
         ObservableList<String> data = FXCollections.observableArrayList("chocolate");
         String nam[]= new String [6];int c1=0,c2=1,c3=2,c4=3;int n;
 public void viewpostDetails(String k,Label studentPostDetails, int x){
   c.dBConnect();
 try{
            String SQL = ("SELECT * FROM messages where message like'"+k+"'");
            ResultSet rs = c.queryDatabase(SQL);       
          while (rs.next()) {
          String temp=rs.getString("messageID");
          String temp1=rs.getString("options");
          if(x==0){
          if(temp1.contains("-")){   
              flag="structured";
         StringTokenizer token;
         token = new StringTokenizer (temp1,"-");
         n=0;
         while(token.hasMoreTokens()){
            nam[n]=token.nextToken();
            System.out.println(nam[n]);
            n++;}   
         flag="structured";
         viewStructured(k,studentPostDetails);        
          }else{
           flag="unstructured";
          Label header = new Label(k);
          m0 = new TextField();
          VBox details = new VBox(header,m0);
          m0.setPromptText("Enter Your Here");
          m0.setPrefHeight(100);
          header.setTextFill(Color.web("#0076a3"));
          studentPostDetails.setText(null);
          studentPostDetails.setGraphic(details);
          }
          }
          if(x==1){
          String SQLL = ("SELECT * FROM response where messageID like'"+temp+"'");
            ResultSet rss = c.queryDatabase(SQLL); 
            data.clear();
           while (rss.next()) {
	   String resp=rss.getString("response");
          data.add(resp);
           }
           ListView <String>list= new ListView(data);
            Label header = new Label(k);
          VBox details = new VBox(header,list);
          header.setTextFill(Color.web("#0076a3"));
          studentPostDetails.setText(null);
          studentPostDetails.setGraphic(details);
             }
 }
          }catch(Exception e){
             System.out.println(e);            
          }
       c.databaseDisconnect();
    }

public void viewStructured(String k, Label studentPostDetails){
    for(int counter=0; counter<n;counter++){     
    if(counter==1){
    m0 = new TextField();
    m0.setText(k);
    m1 = new TextField();
    m1.setText(nam[0]);
    m2 = new TextField();
    m2.setText(nam[1]);
    rb1.setUserData(m1.getText());
    rb2.setUserData(m2.getText());    
    rb1.setToggleGroup(group);
    rb2.setToggleGroup(group);
    f1= new HBox(rb1,m1);
    f2= new HBox(rb2,m2);
    fin = new VBox(m0,f1,f2);
   // fin.setSpacing(5);
    }
    if(counter==2){
    m3= new TextField();
    m3.setText(nam[2]);
    rb3.setUserData(m3.getText());
    f3= new HBox(rb3,m3);
    fin = new VBox(m0,f1,f2,f3);    
    rb3.setToggleGroup(group);}
    if(counter==3){
    m4= new TextField();
    m4.setText(nam[3]);
    rb4.setUserData(m4.getText());
    f4= new HBox(rb4,m4);    
    rb4.setToggleGroup(group);
    fin = new VBox(m0,f1,f2,f3,f4);}
   studentPostDetails.setText(null);
   studentPostDetails.setGraphic(fin);}
};
public void unstructured(String temp,String k,Label studentPostDetails, int i){
     try{
    String SQLL = ("SELECT * FROM response where messageID like'"+temp+"'");
            ResultSet rss = c.queryDatabase(SQLL); 
            data.clear();
           while (rss.next()) {
	   String resp=rss.getString("response");
          data.add(rss.getString("response"));
           }
             
          }catch(Exception e){
             System.out.println(e);            
          }
         //A label with the text element
Label header = new Label(k);
ListView<String> list = new ListView<String>();
list.setItems(data);
VBox details = new VBox(header,list);
header.setTextFill(Color.web("#0076a3"));
studentPostDetails.setText(null);
studentPostDetails.setGraphic(details);
 }
String totalResponse="";
String resp;
 public void structured(String temp,String k,Label studentPostDetails,int i){
     try{
         
    String SQLL = ("SELECT * FROM response where messageID like'"+temp+"'");
            ResultSet rss = c.queryDatabase(SQLL); 
            data.clear();
           while (rss.next()) {
	   resp=rss.getString("response");
           data.add(resp);
           /*    if(resp==nam[0]){
                 totalResponse=totalResponse+">"+resp;
              c1++;   
              
         }
               if(resp==nam[1]){
                   totalResponse=totalResponse+">"+resp;
              c2++;                        
         }
                 if(resp==nam[3]){
                     totalResponse=totalResponse+">"+resp;
              c3++;                        
         }
                   if(resp==nam[3]){
                       totalResponse=totalResponse+">"+resp;
                                    c4++;                        
         }*/
          
           }
           totalResponse=totalResponse+">"+resp;
          }catch(Exception e){
             System.out.println(e);            
          }
         //A label with the text element
         
Label header = new Label(k);
if(i==0){
ListView<String> list = new ListView<String>();
list.setItems(data);
VBox details = new VBox(header,list);
header.setTextFill(Color.web("#0076a3"));
studentPostDetails.setText(null);
studentPostDetails.setGraphic(details);
 }else{ 
    ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Grapefruit", 13),
                new PieChart.Data("Oranges", 25),
                new PieChart.Data("Plums", 10),
                new PieChart.Data("Pears", 22),
                new PieChart.Data("Apples", 30));
    PieChart chart = new PieChart(pieChartData);
    chart.setTitle("Imported Fruits");
VBox details = new VBox(header, chart);
header.setTextFill(Color.web("#0076a3"));
studentPostDetails.setText(null);
studentPostDetails.setGraphic(chart);
}}
}
