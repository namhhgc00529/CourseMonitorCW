/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursemonitor.controller;

import com.coursemonitor.entity.AssignCourse;
import com.coursemonitor.entity.AssignCourseId;
import com.coursemonitor.entity.Course;
import com.coursemonitor.entity.Dlt;
import com.coursemonitor.entity.Pvc;
import com.coursemonitor.entity.Staff;
import com.coursemonitor.model.AssignCourseDao;
import com.coursemonitor.model.CourseDao;
import com.coursemonitor.model.DltDao;
import com.coursemonitor.model.PvcDao;
import com.coursemonitor.model.StaffDao;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Nghia
 */
@ManagedBean(name = "dltBean", eager = true)
@RequestScoped
public class DLTBean implements Serializable {
    
    
    private String id;
    private String nameDLT;
    private String emailDLT;
    
    private List<Dlt> list;
    private List<Staff> listStaff;
    private List<Dlt> listDetail;
    private String cid;
    private String cname;
    private String academicSession;
    private Staff cl;

    private String search;
    private Part image;
    private String description;
    private String course;
    
    private String assignedCl;
    private String assignedCm;
    private int year;

    public String saveDLT() throws IOException {
        DltDao stud = new DltDao();
        int id1 = Integer.parseInt(id);
        Dlt c = new Dlt(id1,nameDLT,emailDLT);
        stud.save(c);
        System.out.println("DLT successfully saved.");
        return "admin_dlt.xhtml?faces-redirect";
    }
    public String deleteDLT(String idDLT) throws IOException {
        DltDao cd = new DltDao();
        System.out.println("Delete id co :"+idDLT);
        List<Dlt> list = cd.searchDltbyID(idDLT);
        list.get(0);
        Dlt c = list.get(0);
        cd.delete(c);
//        for (Course list1 : list) {
//            Course c = new Course(list1.getCourseId(),list1.getCourseName() , list1.getAcademicSession(), list1.getCourseImg(), list1.getDescription());
//            cd.delete(c);
//        }
        System.out.println("Dlt deleted.");
        return "admin_dlt.xhtml?faces-redirect";
    }
    public String redirectDLTDetail(String id) {
        DltDao cd=  new DltDao();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("id", id);
        return "admin_dltdetail.xhtml?faces-redirect";
    }
//     public String editAction(Course course) {
//		course.setEditable(true);
//                System.out.println("FUck");
//		return null;
//     }
     public String editDLT() throws IOException {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
        String id = (String)httpSession.getAttribute("id");
        int dltId = Integer.parseInt(id);
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String dltName = request.getParameter("editForm:txtDLTName");
        String dltEmail = request.getParameter("editForm:txtDLTEmail");
//        String pvcID = request.getParameter("editForm:txtID");
        DltDao cd = new DltDao();
        Dlt c = new Dlt(dltId, dltName, dltEmail);

        cd.save1(c);
        System.out.println("Dlt edit.");
        return "admin_dlt.xhtml?faces-redirect";
    }
   public String assignCourse() {
       CourseDao cd = new CourseDao();
       AssignCourseDao stud = new AssignCourseDao();
       StaffDao sd = new StaffDao();
       AssignCourse as = new AssignCourse(new AssignCourseId(course, year), cd.getCourseById(course),sd.getStaffByName(assignedCl), sd.getStaffByName(assignedCm),80);
       stud.save(as);
      return "index.xhtml?faces-redirect";
   }

    public String redirectReport(String id) {
        CourseDao cd=  new CourseDao();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("course", cd.getCourseById(id));
        return "writeReport.xhtml?faces-redirect";
    }

    public List<Dlt> searchDlt() {
        DltDao cd = new DltDao();
        return cd.searchDlt(search);

    }
     public List<Staff> searchStaff() {
        StaffDao sd = new StaffDao();
        return sd.searchStaff(search);
    }

    public List<Dlt> getAllDLT() {
        DltDao cd = new DltDao();
        return cd.getDLT();
    }
    public List<Staff> getAllStaff() {
        StaffDao sd = new StaffDao();
        return sd.getStaff();
    }
    

    public List<Course> getCourseByCourseLeader() {
        AssignCourseDao cd = new AssignCourseDao();
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Staff courseleader = (Staff) req.getSession().getAttribute("user");
        return cd.getLeaderAssignedByCL(courseleader);
    }
    
     public List<Course> getCourseByCourseModerator() {
        AssignCourseDao cd = new AssignCourseDao();
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Staff coursemoderator = (Staff) req.getSession().getAttribute("user");
        return cd.getModeratorAssignedByCL(coursemoderator);
    }

    public List<Staff> getAllLeader() {
        StaffDao cld = new StaffDao();
        return cld.getStaff();
    }

    public String getNameDLT() {
        return nameDLT;
    }

    public void setNameDLT(String nameDLT) {
        this.nameDLT = nameDLT;
    }

    public String getEmailDLT() {
        return emailDLT;
    }

    public void setEmailDLT(String emailDLT) {
        this.emailDLT = emailDLT;
    }
    
    
    public String getAllLeaderName() {
        return cl.getStName();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getAcademicSession() {
        return academicSession;
    }

    public void setAcademicSession(String academicSession) {
        this.academicSession = academicSession;
    }

    public Staff getCl() {
        return cl;
    }

    public void setCl(Staff cl) {
        this.cl = cl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<Dlt> getList() {
        if (search == null || search == "") {
            list = getAllDLT();
        } else {
            list = searchDlt();
        }
        return list;
    }
     public List<Staff> getListStaff() {
        if (search == null || search == "") {
            listStaff = getAllStaff();
        } else {
            listStaff = searchStaff();
        }
        return listStaff;
    }

    public void setList(List<Dlt> list) {
        this.list = list;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Part getImage() {
        return image;
    }

    public void setImage(Part image) {
        this.image = image;
    }

    public String getAssignedCl() {
        return assignedCl;
    }

    public void setAssignedCl(String assignedCl) {
        this.assignedCl = assignedCl;
    }

    public String getAssignedCm() {
        return assignedCm;
    }

    public void setAssignedCm(String assignedCm) {
        this.assignedCm = assignedCm;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public List<Dlt> getListDetail() {
        if (search == null || search == "") {
            System.out.println("da vao day 1");
            listDetail = getDetailDlt();
        } else {
            listDetail = searchDlt();
        }
        return listDetail;
    }
    public List<Dlt> getDetailDlt() {
         ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
        String id = (String)httpSession.getAttribute("id");
        int id1 = Integer.parseInt(id);
        DltDao cd = new DltDao();
        DLTBean b = new DLTBean();
//        b.setId(id);
        System.out.println("Da vao day co ID :"+id);
        return cd.getDltID(id1);
    }

    
    
    
    public void upload() throws IOException {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();

        InputStream inputStream = image.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(ctx.getRealPath("images/Course/") + getFilename(image));

        byte[] buffer = new byte[4096];
        int bytesRead = 0;
        while (true) {
            bytesRead = inputStream.read(buffer);
            if (bytesRead > 0) {
                outputStream.write(buffer, 0, bytesRead);
            } else {
                break;
            }
        }
        outputStream.close();
        inputStream.close();
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    public DLTBean() {
    }

}
