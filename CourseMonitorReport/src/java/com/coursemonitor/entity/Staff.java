package com.coursemonitor.entity;
// Generated Apr 10, 2016 10:03:50 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Staff generated by hbm2java
 */
public class Staff  implements java.io.Serializable {


     private int stId;
     private String stName;
     private String stEmail;
     private String stUsername;
     private String stPassword;
     private Set assignCoursesForCmId = new HashSet(0);
     private Set assignCoursesForClId = new HashSet(0);

    public Staff() {
    }

    public Staff(int stId, String stName, String stEmail, String stUsername, String stPassword) {
        this.stId = stId;
        this.stName = stName;
        this.stEmail = stEmail;
        this.stUsername = stUsername;
        this.stPassword = stPassword;
    }

	
    public Staff(int stId) {
        this.stId = stId;
    }
    public Staff(int stId, String stName, String stEmail, String stUsername, String stPassword, Set assignCoursesForCmId, Set assignCoursesForClId) {
       this.stId = stId;
       this.stName = stName;
       this.stEmail = stEmail;
       this.stUsername = stUsername;
       this.stPassword = stPassword;
       this.assignCoursesForCmId = assignCoursesForCmId;
       this.assignCoursesForClId = assignCoursesForClId;
    }
   
    public int getStId() {
        return this.stId;
    }
    
    public void setStId(int stId) {
        this.stId = stId;
    }
    public String getStName() {
        return this.stName;
    }
    
    public void setStName(String stName) {
        this.stName = stName;
    }
    public String getStEmail() {
        return this.stEmail;
    }
    
    public void setStEmail(String stEmail) {
        this.stEmail = stEmail;
    }
    public String getStUsername() {
        return this.stUsername;
    }
    
    public void setStUsername(String stUsername) {
        this.stUsername = stUsername;
    }
    public String getStPassword() {
        return this.stPassword;
    }
    
    public void setStPassword(String stPassword) {
        this.stPassword = stPassword;
    }
    public Set getAssignCoursesForCmId() {
        return this.assignCoursesForCmId;
    }
    
    public void setAssignCoursesForCmId(Set assignCoursesForCmId) {
        this.assignCoursesForCmId = assignCoursesForCmId;
    }
    public Set getAssignCoursesForClId() {
        return this.assignCoursesForClId;
    }
    
    public void setAssignCoursesForClId(Set assignCoursesForClId) {
        this.assignCoursesForClId = assignCoursesForClId;
    }




}


