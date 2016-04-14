/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursemonitor.model;

import com.coursemonitor.entity.Course;
import com.coursemonitor.entity.Dlt;
import com.coursemonitor.entity.Pvc;
import com.coursemonitor.entity.Staff;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Nghia
 */
public class DltDao {

    public void save(Dlt c) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(c);
        session.getTransaction().commit();
        session.close();
    }

    
//    public void update(Course c,CourseLeader cl,CourseModerator cm) {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();
//        c.setCourseLeader(cl);
//        c.setCourseModerator(cm);
//        session.update(c);
//        session.getTransaction().commit();
//        session.close();
//    }
    public List<Dlt> searchDltbyID(String key) {
        List<Dlt> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Course where courseId like :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", "%" + key + "%");
        list = query.list();
        session.close();
        return list;
    }

    public List<Dlt> getDLT() {
        List<Dlt> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Dlt";
        Query query = session.createQuery(hql);
        list = query.list();
        session.close();
        return list;
    }
    
      public Dlt getCourseById(int id) {
        List<Dlt> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Dlt where DLT_Id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        Dlt c = (Dlt)query.list().get(0);
        session.close();
        return c;
    }
    

  
    public List<Dlt> searchDlt(String key) {
        List<Dlt> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Dlt where DLT_Name like :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", "%" + key + "%");
        list = query.list();
        session.close();
        return list;
    }

    public List<Course> getCourseByCL(Staff cl) {
        List<Course> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Course where courseLeader = :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", cl);
        list = query.list();
        session.close();
        return list;
    }
    public void delete(Dlt c) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(c);
        tx.commit();
        session.close();
    }
    public void save1(Dlt c) {
        Session session = HibernateUtil.getSessionFactory().openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         session.update(c); 
          System.out.println("xxx");
         tx.commit();
         System.out.println("done");
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      } 
    }
    public List<Dlt> getDltID(int id) {
        System.out.println("getDltId: "+ id);
        List<Dlt> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Dlt where DLT_Id = :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", id);
        list = query.list();
        System.out.println("list la : " +list.toString());
        session.close();
        return list;
    }
    public List<Dlt> getOtherDLT(int id) {
        List<Dlt> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Dlt where DLT_Id <> :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", id);
        list = query.list();
        session.close();
        return list;
    }
    
    }


