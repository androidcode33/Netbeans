/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsystemserver.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author loxbae
 */
public class StudentReg {
    private SimpleStringProperty firstName;
    private  SimpleStringProperty lastName;
    private SimpleStringProperty email;
 
    public StudentReg(String fName, String lName) {
        this.firstName = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);    }
 
    public String getFirstName() {
        return firstName.get();
    }
    public void setFirstName(String fName) {
        firstName.set(fName);
    }
        
    public String getLastName() {
        return lastName.get();
    }
    public void setLastName(String fName) {
        lastName.set(fName);
    }
        
}
