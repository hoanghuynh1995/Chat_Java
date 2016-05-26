/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatPackage;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class User implements Serializable{
    String id;
    String pass;
    String gender;
    String status;

    public User() {
    }

    

    public User(String id, String pass, String gender, String status) {
        this.id = id;
        this.pass = pass;
        this.gender = gender;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

    
    
}
