package com.coursemonitor.entity;
// Generated Mar 28, 2016 9:57:55 PM by Hibernate Tools 4.3.1



/**
 * Guest generated by hbm2java
 */
public class Guest  implements java.io.Serializable {


     private int GId;
     private String GName;
     private String GEmail;
     private String GUsername;
     private String GPassword;

    public Guest() {
    }

	
    public Guest(int GId) {
        this.GId = GId;
    }
    public Guest(int GId, String GName, String GEmail, String GUsername, String GPassword) {
       this.GId = GId;
       this.GName = GName;
       this.GEmail = GEmail;
       this.GUsername = GUsername;
       this.GPassword = GPassword;
    }
   
    public int getGId() {
        return this.GId;
    }
    
    public void setGId(int GId) {
        this.GId = GId;
    }
    public String getGName() {
        return this.GName;
    }
    
    public void setGName(String GName) {
        this.GName = GName;
    }
    public String getGEmail() {
        return this.GEmail;
    }
    
    public void setGEmail(String GEmail) {
        this.GEmail = GEmail;
    }
    public String getGUsername() {
        return this.GUsername;
    }
    
    public void setGUsername(String GUsername) {
        this.GUsername = GUsername;
    }
    public String getGPassword() {
        return this.GPassword;
    }
    
    public void setGPassword(String GPassword) {
        this.GPassword = GPassword;
    }




}


