/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursemonitor.controller;

import com.coursemonitor.entity.Staff;
import com.coursemonitor.model.StaffDao;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nghia
 */
@ManagedBean
@SessionScoped
public class CourseLeaderBean {

    private int clId;
    private String clName;
    private String clEmail;
    private String clUsername;
    private String clPassword;
    private String role;

    public List<Staff> getAllLeader() {
        StaffDao cld = new StaffDao();
        return cld.getStaff();
    }

    public String checkLeaderLogin() {
    
            StaffDao cld = new StaffDao();
            if (cld.checkLogin(clUsername, clPassword).equals(null)) {
                return "login.xhtml?faces-redirect=true";
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getSessionMap().put("user", cld.checkLogin(clUsername, clPassword));
                return "clIndex.xhtml?faces-redirect=true";
            }
        
       
    }

    public String getCurrentUsername() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
      
            Staff cl = (Staff) session.getAttribute("user");
            return cl.getStUsername();
       
        
//        else {
//            CourseModerator cl = (CourseModerator) session.getAttribute("user");
//            return cl.getCmUsername();
//        }
    }

    public int getClId() {
        return clId;
    }

    public void setClId(int clId) {
        this.clId = clId;
    }

    public String getClName() {
        return clName;
    }

    public void setClName(String clName) {
        this.clName = clName;
    }

    public String getClEmail() {
        return clEmail;
    }

    public void setClEmail(String clEmail) {
        this.clEmail = clEmail;
    }

    public String getClUsername() {
        return clUsername;
    }

    public void setClUsername(String clUsername) {
        this.clUsername = clUsername;
    }

    public String getClPassword() {
        return clPassword;
    }

    public void setClPassword(String clPassword) {
        this.clPassword = clPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public CourseLeaderBean() {
    }

}
