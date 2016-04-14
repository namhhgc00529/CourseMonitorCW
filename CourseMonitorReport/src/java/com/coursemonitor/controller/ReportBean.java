/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursemonitor.controller;

import com.coursemonitor.entity.AssignCourse;
import com.coursemonitor.entity.AssignCourseId;
import com.coursemonitor.entity.Course;
import com.coursemonitor.entity.ManagementStaff;
import com.coursemonitor.entity.Report;
import com.coursemonitor.entity.Staff;
import com.coursemonitor.model.AssignCourseDao;
import com.coursemonitor.model.CourseDao;
import com.coursemonitor.model.ReportDao;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Nghia
 */
@ManagedBean
@SessionScoped
public class ReportBean {

    private Part image;
    private String description;
    private String comment;

    public Report getReportByAssignCourse() {
        ReportDao cd = new ReportDao();
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        AssignCourse ac = (AssignCourse) req.getSession().getAttribute("assign");
        return cd.getReportByAssignId(ac.getCourse(), ac.getId().getYear());
    }

    public Report getReportToComment() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Report r = (Report) req.getSession().getAttribute("commentReport");
        return r;
    }

    public Report getFinalReport() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Report r = (Report) req.getSession().getAttribute("commentedReport");
        return r;
    }

    public List<Report> getApprovedReport() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        ManagementStaff ms = (ManagementStaff) req.getSession().getAttribute("user");
        ReportDao cd = new ReportDao();
        return cd.getApprovedByCMReport(ms.getFaculty());
    }

    public List<Report> getCommentedReport() {
        ReportDao cd = new ReportDao();
        return cd.getCommentedReport();
    }

    public String commentReport(Report r) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("commentReport", r);
        return "comment.xhtml?faces-redirect";
    }

    public String viewCommentedReport(Report r) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("commentedReport", r);
        return "viewFinalReport.xhtml?faces-redirect";
    }

    public void makeComment() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Report r = (Report) req.getSession().getAttribute("commentReport");
        ReportDao rd = new ReportDao();
        rd.update(r, 2);
        rd.updateComment(r, comment);
    }

    public void approveReport(Report r) {
        ReportDao rd = new ReportDao();
        rd.update(r, 1);
    }

    public void sendMail() throws IOException {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        upload();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
        AssignCourseId c = (AssignCourseId) httpSession.getAttribute("assigncourse");
        Staff cl = (Staff) httpSession.getAttribute("user");
        AssignCourseDao acd = new AssignCourseDao();
        AssignCourse assign = acd.getAssignedById(c).get(0);
        acd.updateStatus(assign, 1);

        Report r = new Report(assign.getCourse(), assign.getStaffByClId(), assign.getStaffByCmId(), c.getYear(), description, 0);
        ReportDao rd = new ReportDao();
        rd.save(r);

        final String username = "silverflash1210@gmail.com";
        final String password = "michaelholmes";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("silverflash1210@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("nghianttgt00492@fpt.edu.vn"));
            message.setSubject("Testing Subject");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText(description + "send by: " + cl.getStName() + "CourseId: " + c.getCourseId());

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = ctx.getRealPath("document/") + getFilename(image);
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);
            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public ReportBean() {
    }

    public Part getImage() {
        return image;
    }

    public void setImage(Part image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void upload() throws IOException {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();

        InputStream inputStream = image.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(ctx.getRealPath("document/") + getFilename(image));

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

}
