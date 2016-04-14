/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursemonitor.controller;

import com.coursemonitor.entity.AssignCourse;
import com.coursemonitor.entity.AssignCourseId;
import com.coursemonitor.entity.AssignFaculty;
import com.coursemonitor.entity.Course;
import com.coursemonitor.entity.Staff;
import com.coursemonitor.model.AssignCourseDao;
import com.coursemonitor.model.AssignFacultyDao;
import com.coursemonitor.model.CourseDao;
import com.coursemonitor.model.FacultyDao;
import com.coursemonitor.model.StaffDao;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Nghia
 */
@ManagedBean(name = "assignCourseBean", eager = true)
@RequestScoped
public class AssignCourseBean implements Serializable {
    
    @ManagedProperty("#{param.id}")
    private String id;
    
    private List<AssignCourse> list;
    private List<Staff> listStaff;
    private List<AssignCourse> listDetail;
    private String cid;
    private String cname;
    private String academicSession;
    private Staff cl;
    private int stdcount;
    private Integer status;
    
    private String search;
    private Part image;
    private String description;
    private String course;
    
    private String assignedCl;
    private String assignedCm;
    private int year;

    public String saveCourse() throws IOException {
        upload();
        CourseDao stud = new CourseDao();
        Course c = new Course(cid, cname, academicSession, getFilename(image), description);
        stud.save(c);
        System.out.println("User successfully saved.");
        return "index.xhtml?faces-redirect";
    }
    public String deleteCourse(String idCourse) throws IOException {
        CourseDao cd = new CourseDao();
        System.out.println("Delete id co :"+idCourse);
        List<Course> list = cd.searchCoursebyID(idCourse);
        list.get(0);
        Course c = list.get(0);
        cd.delete(c);
//        for (Course list1 : list) {
//            Course c = new Course(list1.getCourseId(),list1.getCourseName() , list1.getAcademicSession(), list1.getCourseImg(), list1.getDescription());
//            cd.delete(c);
//        }
        System.out.println("User deleted.");
        return "index_admin.xhtml?faces-redirect";
    }

//     public String editAction(Course course) {
//		course.setEditable(true);
//                System.out.println("FUck");
//		return null;
//     }
     public String editCourse() throws IOException {
//        upload();
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String cName = request.getParameter("editForm:txtCName");
        String cSession = request.getParameter("editForm:txtSession");
        String cID = request.getParameter("editForm:txtID");
        String cDescription = request.getParameter("editForm:txtDescription");
        CourseDao cd = new CourseDao();
        System.out.println("Edit id co :"+ cID);
//        List<Course> list = cd.searchCoursebyID(id);
//        list.get(0);
        Course c = new Course(cID, cName, cSession, null, cDescription);
//        for (Course list1 : list) {
//            Course c = new Course(list1.getCourseId(), list1.getCourseLeader(), list1.getCourseName(), list1.getAcademicSession(), "abcd", list1.getDescription());
//            System.out.println("Edit name" + c.getCourseName());
//            cd.save1(c,c.getCourseId());
//        }
        cd.save1(c);
        System.out.println("User edit.");
        return "index_admin.xhtml?faces-redirect";
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

    public List<AssignCourse> searchCourse() {
        AssignCourseDao acd = new AssignCourseDao();
        return acd.searchCourse(search);

    }
     public List<Staff> searchStaff() {
        StaffDao sd = new StaffDao();
        return sd.searchStaff(search);
    }

    public List<AssignCourse> getAllCourse() {
        AssignCourseDao acd = new AssignCourseDao();
        return acd.getCourse();
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

    public List<AssignCourse> getList() {
        if (search == null || search == "") {
            list = getAllCourse();
        } else {
            list = searchCourse();
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

    public void setList(List<AssignCourse> list) {
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
    
    public List<AssignCourse> getListDetail() {
        if (search == null || search == "") {
            System.out.println("da vao day 1");
            listDetail = getDetailCourse();
        } else {
            listDetail = searchCourse();
        }
        return listDetail;
    }
    public List<AssignCourse> getDetailCourse() {
        AssignCourseDao acd = new AssignCourseDao();
        CourseBean b = new CourseBean();
        b.setId(id);
        System.out.println("Da vao day co ID :"+id);
        return acd.getCourseID(id);
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
     public String redirectAssignCourseDetail(String id,int status,int stdcount,int year) {
        FacultyDao cd=  new FacultyDao();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("id", id);
         context.getExternalContext().getSessionMap().put("status", status);
           context.getExternalContext().getSessionMap().put("stdcount", stdcount);
             context.getExternalContext().getSessionMap().put("year", year);
        return "admin_assignCoursedetail.xhtml?faces-redirect";
    }

    public AssignCourseBean() {
    }

    public List<Course> getByQueryId() {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
        String id = (String)httpSession.getAttribute("id");
        status = (Integer)httpSession.getAttribute("status");
        stdcount = (Integer)httpSession.getAttribute("stdcount");
        year = (Integer)httpSession.getAttribute("year");
        
        CourseDao cd = new CourseDao();
        AssignCourseDao acd = new AssignCourseDao();
        AssignCourseBean b = new AssignCourseBean();
        b.setId(id);
        AssignCourse af = acd.getCourseID(id).get(0);

        List<Course> list = new ArrayList<Course>();
        list.add(af.getCourse());
       
        for (Course c : cd.getOtherCourse(af.getCourse().getCourseId())) {
            list.add(c);
        }
        return list;
    }

    public int getStdcount() {
        return stdcount;
    }

    public void setStdcount(int stdcount) {
        this.stdcount = stdcount;
    }
    public List<Staff> getByQueryIdCl() {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
        String id = (String)httpSession.getAttribute("id");
        
        StaffDao cd = new StaffDao();
        AssignCourseDao acd = new AssignCourseDao();
        AssignCourseBean b = new AssignCourseBean();
        b.setId(id);
        AssignCourse af = acd.getCourseID(id).get(0);

        List<Staff> list = new ArrayList<Staff>();
        list.add(af.getStaffByClId());
       
        for (Staff c : cd.getOtherStaff(af.getStaffByClId().getStId())) {
            list.add(c);
        }
        return list;
    }
    public List<Staff> getByQueryIdCM() {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
        String id = (String)httpSession.getAttribute("id");
        
        StaffDao cd = new StaffDao();
        AssignCourseDao acd = new AssignCourseDao();
        AssignCourseBean b = new AssignCourseBean();
        b.setId(id);
        AssignCourse af = acd.getCourseID(id).get(0);

        List<Staff> list = new ArrayList<Staff>();
        list.add(af.getStaffByCmId());
       
        for (Staff c : cd.getOtherStaff(af.getStaffByClId().getStId())) {
            list.add(c);
        }
        return list;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
     public String editAssignCourse() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
        String id = (String)httpSession.getAttribute("id");
        int idAC = Integer.parseInt(id);
        CourseDao cd = new CourseDao();
        StaffDao dd = new StaffDao();
        AssignCourseDao acd = new AssignCourseDao();
        FacultyDao fd = new FacultyDao();
        AssignCourseId ai = new AssignCourseId(course, year);
            int cl = Integer.parseInt(assignedCl);
            int cm = Integer.parseInt(assignedCm);
         System.out.println("||"+assignedCm +"||"+ assignedCl +"||"+stdcount+"||"+ status);
        AssignCourse ac = new AssignCourse(ai, cd.getCourseById(course), dd.getStaffByID(cm), dd.getStaffByID(cl), idAC, stdcount, status);

        acd.save1(ac);
        System.out.println("Assign Course edit.");
        return "admin_assignCourse.xhtml?faces-redirect";
    }
}
