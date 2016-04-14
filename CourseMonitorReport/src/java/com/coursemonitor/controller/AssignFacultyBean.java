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
import com.coursemonitor.entity.Dlt;
import com.coursemonitor.entity.Faculty;
import com.coursemonitor.entity.Pvc;
import com.coursemonitor.entity.Staff;
import com.coursemonitor.model.AssignCourseDao;
import com.coursemonitor.model.AssignFacultyDao;
import com.coursemonitor.model.CourseDao;
import com.coursemonitor.model.DltDao;
import com.coursemonitor.model.FacultyDao;
import com.coursemonitor.model.PvcDao;
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
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Nghia
 */
@ManagedBean(name = "assignFacultyBean", eager = true)
@RequestScoped
public class AssignFacultyBean implements Serializable {

    private String id;

    private List<AssignFaculty> list;
    private List<Staff> listStaff;
    private List<AssignFaculty> listDetail;
    private String cid;
    private String cname;
    private String academicSession;
    private Staff cl;

    private String search;
    private Part image;
    private String description;
    private String course;
    private String pvc;
    private String dlt;

    private String assignedCl;
    private String assignedCm;
    private int facultyId;
    private String pvcId;
    private String dltId;
    private int year;

    

   public String saveFaculty() throws IOException {
        AssignFacultyDao stud = new AssignFacultyDao();
        CourseDao cd = new CourseDao();
        DltDao dd = new DltDao();
        FacultyDao fd = new FacultyDao();
        PvcDao pd = new PvcDao();
        int idAF = Integer.parseInt(id);
        AssignFaculty c = new AssignFaculty(idAF,cd.getCourseById(course),dd.getCourseById(Integer.parseInt(dltId)),fd.getFacultyById(facultyId),pd.getPvcByID(Integer.parseInt(pvcId)));
        stud.save(c);
        System.out.println("User successfully saved.");
        return "admin_assignFaculty.xhtml?faces-redirect";
    }

    public String deleteAssignCourse(int idCourse) throws IOException {
        AssignFacultyDao cd = new AssignFacultyDao();
        System.out.println("Delete id co :" + idCourse);
        List<AssignFaculty> list = cd.searchFacultyByID(idCourse);
        list.get(0);
        AssignFaculty c = list.get(0);
        cd.delete(c);
//        for (Course list1 : list) {
//            Course c = new Course(list1.getCourseId(),list1.getCourseName() , list1.getAcademicSession(), list1.getCourseImg(), list1.getDescription());
//            cd.delete(c);
//        }
        System.out.println("Assign Faculty deleted.");
        return "admin_assignFaculty.xhtml?faces-redirect";
    }

//     public String editAction(Course course) {
//		course.setEditable(true);
//                System.out.println("FUck");
//		return null;
//     }
    public String editCourse() throws IOException {
//        upload();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String cName = request.getParameter("editForm:txtCName");
        String cSession = request.getParameter("editForm:txtSession");
        String cID = request.getParameter("editForm:txtID");
        String cDescription = request.getParameter("editForm:txtDescription");
        CourseDao cd = new CourseDao();
        System.out.println("Edit id co :" + cID);
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
        AssignCourse as = new AssignCourse(new AssignCourseId(course, year), cd.getCourseById(course), sd.getStaffByName(assignedCl), sd.getStaffByName(assignedCm), 80);
        stud.save(as);
        return "index.xhtml?faces-redirect";
    }

    public String redirectReport(String id) {
        CourseDao cd = new CourseDao();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("course", cd.getCourseById(id));
        return "writeReport.xhtml?faces-redirect";
    }

    public List<AssignFaculty> searchAssignFaculty() {
        AssignFacultyDao acd = new AssignFacultyDao();
        return acd.searchAssignFaculty(search);

    }

    public List<Staff> searchStaff() {
        StaffDao sd = new StaffDao();
        return sd.searchStaff(search);
    }

    public List<AssignFaculty> getAllAssignFaculty() {
        AssignFacultyDao afd = new AssignFacultyDao();
        return afd.getCourse();
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

    public List<AssignFaculty> getList() {
        if (search == null || search == "") {
            list = getAllAssignFaculty();
        } else {
            list = searchAssignFaculty();
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

    public void setList(List<AssignFaculty> list) {
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

    public List<AssignFaculty> getListDetail() {
        if (search == null || search == "") {
            System.out.println("da vao day 1");
            listDetail = getDetailFaculty();
        } else {
            listDetail = searchAssignFaculty();
        }
        return listDetail;
    }

    public List<AssignFaculty> getDetailFaculty() {
        AssignFacultyDao acd = new AssignFacultyDao();
        AssignFacultyBean b = new AssignFacultyBean();
        b.setId(id);
        System.out.println("Da vao day co ID :" + id);
        return acd.getFacultyID(id);
    }

    public List<Course> getByQueryId() {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
        String id = (String)httpSession.getAttribute("id");
        
        CourseDao cd = new CourseDao();
        AssignFacultyDao acd = new AssignFacultyDao();
        AssignFacultyBean b = new AssignFacultyBean();
        b.setId(id);
        AssignFaculty af = acd.getFacultyID(id).get(0);

        List<Course> list = new ArrayList<Course>();
        list.add(af.getCourse());
       
        for (Course c : cd.getOtherCourse(af.getCourse().getCourseId())) {
            list.add(c);
        }
        return list;
    }
    public List<Pvc> getByQueryIdPVC() {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
        String id = (String)httpSession.getAttribute("id");
        
        PvcDao cd = new PvcDao();
        AssignFacultyDao acd = new AssignFacultyDao();
        AssignFacultyBean b = new AssignFacultyBean();
        b.setId(id);
        AssignFaculty af = acd.getFacultyID(id).get(0);

        List<Pvc> list = new ArrayList<Pvc>();
        list.add(af.getPvc());
       
        for (Pvc c : cd.getOtherPVC(af.getPvc().getPvcId())) {
            list.add(c);
        }
        return list;
    }
    public List<Dlt> getByQueryIdDLT() {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
        String id = (String)httpSession.getAttribute("id");
        
        DltDao cd = new DltDao();
        AssignFacultyDao acd = new AssignFacultyDao();
        AssignFacultyBean b = new AssignFacultyBean();
        b.setId(id);
        AssignFaculty af = acd.getFacultyID(id).get(0);

        List<Dlt> list = new ArrayList<Dlt>();
        list.add(af.getDlt());
       
        for (Dlt c : cd.getOtherDLT(af.getDlt().getDltId())) {
            list.add(c);
        }
        return list;
    }
     public List<Faculty> getByQueryIdFaculty() {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
        String id = (String)httpSession.getAttribute("id");
        
        FacultyDao cd = new FacultyDao();
        AssignFacultyDao acd = new AssignFacultyDao();
        AssignFacultyBean b = new AssignFacultyBean();
        b.setId(id);
        AssignFaculty af = acd.getFacultyID(id).get(0);

        List<Faculty> list = new ArrayList<Faculty>();
        list.add(af.getFaculty());
       
        for (Faculty c : cd.getOtherFaculty(af.getFaculty().getFacultyId())) {
            list.add(c);
        }
        return list;
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

    public String editAssignFaculty() {
//        upload();
        System.out.println("EditStaff" + id);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
        String id = (String)httpSession.getAttribute("id");
        int idAF = Integer.parseInt(id);
//        int idAF = Integer.parseInt(request.getParameter("editForm:txtId"));
//        String cId = request.getParameter("editForm:txtCId");
//        String cName = request.getParameter("editForm:txtCname");
//        String fId = request.getParameter("editForm:txtFId");
//        String pvcName = request.getParameter("editForm:txtPvcName");
//        String dltName = request.getParameter("editForm:txtDltName");
        CourseDao cd = new CourseDao();
        DltDao dd = new DltDao();
        AssignFacultyDao afd = new AssignFacultyDao();
        FacultyDao fd = new FacultyDao();
        PvcDao pd = new PvcDao();
        System.out.println("Course" + course);
        System.out.println("PVC" + pvcId);
        System.out.println("DLT" + dltId);
        System.out.println("FAL" + facultyId);
        AssignFaculty af = new AssignFaculty(idAF,cd.getCourseById(course),dd.getCourseById(Integer.parseInt(dltId)),fd.getFacultyById(facultyId),pd.getPvcByID(Integer.parseInt(pvcId)));

        afd.save1(af);
        System.out.println("Staff edit.");
        return "admin_assignFaculty.xhtml?faces-redirect";
    }
     public String redirectAssignFacultyDetail(String id) {
        CourseDao cd=  new CourseDao();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("id", id);
        return "admin_assignFacultydetail.xhtml?faces-redirect";
    }

    public AssignFacultyBean() {
    }

    public List<Course> getAllCourse() {
        CourseDao cd = new CourseDao();
        return cd.getCourse();
    }

    public List<Pvc> getAllPVC() {
        PvcDao cd = new PvcDao();
        return cd.getPVC();
    }

    public List<Dlt> getAllDLT() {
        DltDao cd = new DltDao();
        return cd.getDLT();
    }

    public String getPvc() {
        return pvc;
    }

    public void setPvc(String pvc) {
        this.pvc = pvc;
    }

    public String getDlt() {
        return dlt;
    }

    public void setDlt(String dlt) {
        this.dlt = dlt;
    }
    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getPvcId() {
        return pvcId;
    }

    public void setPvcId(String pvcId) {
        this.pvcId = pvcId;
    }

    public String getDltId() {
        return dltId;
    }

    public void setDltId(String dltId) {
        this.dltId = dltId;
    }
}
