package com.coursemonitor.entity;
// Generated Apr 10, 2016 10:03:50 PM by Hibernate Tools 4.3.1



/**
 * AssignFaculty generated by hbm2java
 */
public class AssignFaculty  implements java.io.Serializable {


     private int idAf;
     private Course course;
     private Dlt dlt;
     private Faculty faculty;
     private Pvc pvc;

    public AssignFaculty() {
    }

	
    public AssignFaculty(int idAf) {
        this.idAf = idAf;
    }
    public AssignFaculty(int idAf, Course course, Dlt dlt, Faculty faculty, Pvc pvc) {
       this.idAf = idAf;
       this.course = course;
       this.dlt = dlt;
       this.faculty = faculty;
       this.pvc = pvc;
    }
   
    public int getIdAf() {
        return this.idAf;
    }
    
    public void setIdAf(int idAf) {
        this.idAf = idAf;
    }
    public Course getCourse() {
        return this.course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    public Dlt getDlt() {
        return this.dlt;
    }
    
    public void setDlt(Dlt dlt) {
        this.dlt = dlt;
    }
    public Faculty getFaculty() {
        return this.faculty;
    }
    
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
    public Pvc getPvc() {
        return this.pvc;
    }
    
    public void setPvc(Pvc pvc) {
        this.pvc = pvc;
    }




}


