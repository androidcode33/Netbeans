/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystemserver.model;

import classsystemserver.dabaseConnect.DatabaseConn;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


/**
 * FXML Controller class
 *
 * @author loxbae
 */
public class AttendanceController implements Initializable {
  @FXML private TableView<StudentReg> table;
  @FXML private TableColumn name;
  @FXML private TableColumn regno;
  WritableWorkbook writableWorkbook;
  WritableCellFormat cf;
  WritableSheet writableSheet;
  WritableFont wf;
   @FXML private Button btncancel;
    /**
     * Initializes the controller class.
     */
   DatabaseConn c= new DatabaseConn("jdbc:mysql://localhost:3306/classdatabase", "root", "");
   @FXML void takeAttendance(ActionEvent event) {  try{generateTemp();
   }catch(Exception e){System.out.println(e);} }
    @FXML void canclelButton(ActionEvent event) {  
     final Node source = (Node) event.getSource();
    final Stage stage = (Stage) source.getScene().getWindow();
    stage.close();
    }
   @Override
    public void initialize(URL url, ResourceBundle rb) {   
    c.dBConnect();
        ObservableList<StudentReg> data = FXCollections.observableArrayList();
     String SQL = ("SELECT student_no,name FROM student");
            ResultSet rs = c.queryDatabase(SQL); 
            try{
             while (rs.next()) {
                 System.out.println(rs.getString("student_no")+"\t"+rs.getString("name"));
	 data.add(new StudentReg(rs.getString("student_no"),rs.getString("name")));
             }
             
            }catch(Exception e){
            }

  name.setCellValueFactory(
  new PropertyValueFactory<StudentReg,String>("firstName"));
        // TODO
        
regno.setCellValueFactory(
  new PropertyValueFactory<StudentReg,String>("lastName"));
        // TODO
          table.setItems(data);
          c.disconnect();
    }

   public void generateTemp() throws Exception {
       c.dBConnect();
        String path = "D:\\excel";
        File f = new File(path);
        f.mkdir();
        File exlFile = new File(path + "\\" + "student"+ ".xls");

        wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
        cf = new WritableCellFormat(wf);
        cf.setBorder(Border.ALL, BorderLineStyle.THIN);
        CellView hidden = new CellView();
        hidden.setHidden(true);

        writableWorkbook = Workbook.createWorkbook(exlFile);
        writableSheet = writableWorkbook.createSheet("student sheet", 0);
            village();
            writableWorkbook.write();
            writableWorkbook.close();
            System.out.println("Write complete");
            c.disconnect();
       }
   public void village() throws Exception {
       if (c.dBConnect()) {
           String SQL = ("SELECT * FROM student");
           ResultSet rs = c.queryDatabase(SQL);
          
                    int x, y = 1, z = rs.getMetaData().getColumnCount();
                      try {
                    Label dis = new Label(0,0, "Student Number :");
                    WritableCellFormat c1 = new WritableCellFormat(wf);
                    c1.setBorder(Border.ALL, BorderLineStyle.THIN);
                    c1.setFont(wf);
                    dis.setCellFormat(c1);
                    writableSheet.addCell(dis);

                    Label ct = new Label(1,0, " Student Registration   :");
                    WritableCellFormat c = new WritableCellFormat(wf);
                    c.setBorder(Border.ALL, BorderLineStyle.THIN);
                    c.setFont(wf);
                    ct.setCellFormat(c);
                    writableSheet.addCell(ct);
                    
                    Label cty = new Label(2,0, "Student Name:");
                    WritableCellFormat coty = new WritableCellFormat(wf);
                    coty.setBorder(Border.ALL, BorderLineStyle.THIN);
                    coty.setFont(wf);
                    cty.setCellFormat(coty);
                    writableSheet.addCell(cty);
                    
                          while (rs.next()) {
                            for (x = 0; x < z; x++) {
                            Label label = new Label(x, y, rs.getString(x + 1));
                            label.setCellFormat(cf);
                            writableSheet.addCell(label);
                        }
                            y=y+1;
                              
                          }
                      
   } catch (Exception e) {
            }
       }
            
        c.disconnect();  
   }
    
 }
    
    

