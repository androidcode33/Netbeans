/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystem.dadaseConnect;
/**
 *
 * @author Miche
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * @author Micheal
 */
public class DatabaseConn{
    String dbURL;
    String username;
    String password;
     String results,data;
    Connection dbCon = null;
    
    public DatabaseConn(String dbURL, String username, String password){
        this.dbURL = dbURL;
        this.username = username;
        this.password = password;
    }
    
   public  Boolean dBConnect(){
        try{
            dbCon = DriverManager.getConnection(dbURL, username, password);
            
            return true;
        }catch(SQLException ex){
            System.out.println(ex);
            return false;
        }
    }
    
    public ResultSet queryDatabase(String query){
        ResultSet rs;
        Statement stmt;
        try {
            stmt = dbCon.prepareStatement(query);
            rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
    
   public Boolean insert_or_Update(String state){
        Statement stmt = null;
        try {
            stmt = dbCon.createStatement();
            stmt.executeUpdate(state);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }
   public void databaseDisconnect(){
       try{
   dbCon.close();}catch(SQLException e){
   System.out.println(e);
   }
   }
}



//results = data.queryDatabase("SELECT * FROM Users where Name like '%"+uName.getText()+"%' order by Name");
//data.insert_or_Update("SELECT * FROM Users where Name like '%"+uName.getText()+"%' order by Name");
//float a;
//String inV = "INSERT INTO Users value('"+uName.getText()+"','"+passWord.getText()+"'"+");";
//data.insert_or_Update(inV);