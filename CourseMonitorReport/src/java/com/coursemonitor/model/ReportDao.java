/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursemonitor.model;

import com.coursemonitor.entity.Course;
import com.coursemonitor.entity.Faculty;
import com.coursemonitor.entity.Report;
import com.coursemonitor.entity.Staff;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Nghia
 */
public class ReportDao {

    public void save(Report r) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(r);
        session.getTransaction().commit();
        session.close();
    }

    public void update(Report r, int status) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        r.setStatus(status);
        session.update(r);
        session.getTransaction().commit();
        session.close();
    }

    public void updateComment(Report r, String c) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        r.setComment(c);
        session.update(r);
        session.getTransaction().commit();
        session.close();
    }

    public Report getReportByAssignId(Course c, int year) {
        List<Report> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Report where course = :id and year = :y";
        Query query = session.createQuery(hql);
        query.setParameter("id", c);
        query.setParameter("y", year);
        Report r = (Report) query.list().get(0);
        session.close();
        return r;
    }

    public List<Report> getApprovedByCMReport(Faculty s) {
        List<Report> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Report where status = 1 and staffByCmId.faculty = :cm";
        Query query = session.createQuery(hql);
        query.setParameter("cm", s);
        list = query.list();
        session.close();
        return list;
    }

    public List<Report> getCommentedReport() {
        List<Report> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Report where status = 2";
        Query query = session.createQuery(hql);
        list = query.list();
        session.close();
        return list;
    }

}
