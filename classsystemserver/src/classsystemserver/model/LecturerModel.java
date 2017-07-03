/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystemserver.model;

import classsystemserver.dabaseConnect.DatabaseConn;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author loxbae
 */
public class LecturerModel {

    public PieChart chart;
    DatabaseConn c = new DatabaseConn("jdbc:mysql://localhost:3306/classdatabase", "root", "");
    ObservableList<String> items = FXCollections.observableArrayList();

    public ObservableList<String> fetchNames() {
        try {
            c.dBConnect();
            String SQL = ("SELECT message FROM messages where username like 'Lecturer'");
            ResultSet rs = c.queryDatabase(SQL);

            while (rs.next()) {
                items.add(rs.getString("message"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        c.dBConnect();
        return items;
    }
    ObservableList<String> data = FXCollections.observableArrayList();
    public ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    String nam[] = new String[6];
    int c1 = 0, c2, c3, c4, n;
    String compose;
    Label header;
    Label ss;

    public void viewpostDetails(String k, Label studentPostDetails, int i) {
        c1 = 0;
        c2 = 0;
        c3 = 0;
        c4 = 0;
        n = 0;
        compose = "";
        ss = studentPostDetails;
        try {
            data.clear();
            c.dBConnect();
            String SQL = ("SELECT * FROM messages where message like'" + k + "'");
            ResultSet rs = c.queryDatabase(SQL);
            while (rs.next()) {
                String temp = rs.getString("messageID");
                String temp1 = rs.getString("options");
                if (temp1.contains("-")) {
                    n = 0;
                    pieChartData.clear();
                    StringTokenizer token;
                    token = new StringTokenizer(temp1, "-");
                    while (token.hasMoreTokens()) {
                        nam[n] = token.nextToken();
                        n++;
                    }
                    for (int x = 0; x < n; x++) {
                        String SQLL = ("SELECT * FROM response where response like'%" + nam[x] + "'");
                        ResultSet rss = c.queryDatabase(SQLL);

                        while (rss.next()) {
                            String resp = rss.getString("response");
                            String user = rss.getString("username");
                            if (x == 0) {
                                data.add(resp);
                                compose = compose + "*" + temp + "<" + user + "<" + resp;
                                c1++;
                            }
                            if (x == 1) {
                                data.add(resp);
                                compose = compose + "*" + temp + "<" + user + "<" + resp;
                                c2++;
                            }
                            if (x == 2) {
                                data.add(resp);
                                compose = compose + "*" + temp + "<" + user + "<" + resp;
                                c3++;
                            }
                            if (x == 3) {
                                data.add(resp);
                                compose = compose + "*" + temp + "<" + user + "<" + resp;
                                c4++;
                            }
                        }

                    }
                    System.out.println(compose);
                    if (i == 0) {
                        header = new Label(k);
                        Structured2(ss, k);
                    }
//  if(i==1){
// unStructured();
// 
//    }      
                } else {
                    String SQLL = ("SELECT * FROM response where messageID like'" + temp + "'");
                    ResultSet rss = c.queryDatabase(SQLL);
                    data.clear();
                    while (rss.next()) {
                        String resp = rss.getString("response");
                        data.add(resp);
                    }
                    Structured2(studentPostDetails, k);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        c.disconnect();

    }

   
    private void Structured2(Label studentPostDetails, String k) {
        //A label with the text element
        Label header = new Label("\t\t" + k);;
        header.setPrefHeight(61);
        ListView<String> list = new ListView<String>();
        list.setItems(data);
        VBox details = new VBox(header, list);
        header.setTextFill(Color.web("#0076a3"));
        header.setPrefHeight(60);
        header.setPrefWidth(400);
        header.setFont(new Font("Arial", 16));
//new DesignManager().postResults(list);
        studentPostDetails.setText(null);
        studentPostDetails.setGraphic(details);
    }

    public void unStructured() {
        if (n == 2) {
            pieChartData.add(new PieChart.Data(nam[0], c1));
            pieChartData.add(new PieChart.Data(nam[1], c2));
            chart = new PieChart(pieChartData);
        }
        if (n == 3) {
            pieChartData.add(new PieChart.Data(nam[0], c1));
            pieChartData.add(new PieChart.Data(nam[1], c2));
            pieChartData.add(new PieChart.Data(nam[2], c3));
            chart = new PieChart(pieChartData);
        }
        if (n == 4) {
            pieChartData.add(new PieChart.Data(nam[0], c1));
            pieChartData.add(new PieChart.Data(nam[1], c2));
            pieChartData.add(new PieChart.Data(nam[2], c3));
            pieChartData.add(new PieChart.Data(nam[3], c4));
            chart = new PieChart(pieChartData);

        }

        header.setPrefHeight(61);
        chart.setTitle("Grades");
        VBox details = new VBox(header, chart);
        header.setTextFill(Color.web("#0076a3"));
        ss.setText(null);
        ss.setGraphic(details);
    }

    public String broadcast() {
        return compose;
    }

}
