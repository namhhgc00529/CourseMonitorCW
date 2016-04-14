/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursemonitor.controller;

import com.coursemonitor.entity.Staff;
import com.coursemonitor.model.StaffDao;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author NamHoang
 */
@ManagedBean(name = "staffBean", eager = true)
@RequestScoped
public class StaffBean implements Serializable{
    
    @ManagedProperty("#{param.id}")
    private String id;
    
    private List<Staff> listStaff;
    private List<Staff> listDetail;
    private String search;
    
     private int stId;
     private String stName;
     private String stEmail;
     private String stUsername;
     private String stPassword;
    
    public StaffBean() {
    }
     public String saveStaff() throws IOException {
        StaffDao stud = new StaffDao();
        Staff c = new Staff(stId, stName, stEmail, stUsername, stPassword);
        stud.save(c);
        System.out.println("User successfully saved.");
        return "index_staff.xhtml?faces-redirect";
    }
    public List<Staff> getListStaff() {
        if (search == null || search == "") {
            listStaff = getAllStaff();
        } else {
            listStaff = searchStaff();
        }
        return listStaff;
    }

    public void setListStaff(List<Staff> listStaff) {
        this.listStaff = listStaff;
    }
    
    public List<Staff> getAllStaff() {
        StaffDao sd = new StaffDao();
        return sd.getStaff();
    }
    public List<Staff> searchStaff() {
        StaffDao sd = new StaffDao();
        return sd.searchStaff(search);
    }
    public String deleteStaff(int idStaff) throws IOException {
        StaffDao sd = new StaffDao();
        System.out.println("Delete id co :"+idStaff);
        List<Staff> list = sd.searchStaffbyID(idStaff);
        list.get(0);
        Staff c = list.get(0);
        sd.delete(c);
        System.out.println("Staff deleted.");
        return "index_staff.xhtml?faces-redirect";
    }
    public List<Staff> getListDetail() {
        if (search == null || search == "") {
            System.out.println("da vao getlistDetail");
            listDetail = getDetailCourse();
        } else {
            listDetail = searchStaff();
        }
        return listDetail;
    }
    public List<Staff> getDetailCourse() {
        StaffDao cd = new StaffDao();
        StaffBean b = new StaffBean();
        b.setId(id);
        System.out.println("Da vao day co ID :"+id);
        return cd.getStaffID(id);
    }
    
    public String editStaff(){
//        upload();
        System.out.println("EditStaff"+ id);
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        int sId = Integer.parseInt(request.getParameter("editForm:txtId"));
        String uName = request.getParameter("editForm:txtUsername");
        String name = request.getParameter("editForm:txtName");
        String password = request.getParameter("editForm:txtPassword");
        String email = request.getParameter("editForm:txtEmail");
        StaffDao sd = new StaffDao();
        System.out.println("Edit id co :"+ sId);
//        List<Course> list = cd.searchCoursebyID(id);
//        list.get(0);
        Staff s = new Staff(sId, name,email , uName, password);
//        for (Course list1 : list) {
//            Course c = new Course(list1.getCourseId(), list1.getCourseLeader(), list1.getCourseName(), list1.getAcademicSession(), "abcd", list1.getDescription());
//            System.out.println("Edit name" + c.getCourseName());
//            cd.save1(c,c.getCourseId());
//        }
        sd.save1(s);
        System.out.println("Staff edit.");
        return "index_staff.xhtml?faces-redirect";
    }
    public void editTest(){
        System.out.println("Fuck");
        
    }

    public void setListDetail(List<Staff> listDetail) {
        this.listDetail = listDetail;
    }
    

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getStId() {
        return stId;
    }

    public void setStId(int stId) {
        this.stId = stId;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getStEmail() {
        return stEmail;
    }

    public void setStEmail(String stEmail) {
        this.stEmail = stEmail;
    }

    public String getStUsername() {
        return stUsername;
    }

    public void setStUsername(String stUsername) {
        this.stUsername = stUsername;
    }

    public String getStPassword() {
        return stPassword;
    }

    public void setStPassword(String stPassword) {
        this.stPassword = stPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
}
