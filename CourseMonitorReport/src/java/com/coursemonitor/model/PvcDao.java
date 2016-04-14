/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursemonitor.model;

import com.coursemonitor.entity.Course;
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
public class PvcDao {

    public void save(Pvc c) {
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
    public List<Pvc> searchPvcbyID(int key) {
        List<Pvc> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Pvc where PVC_Id like :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", "%" + key + "%");
        list = query.list();
        session.close();
        return list;
    }

    public List<Pvc> getPVC() {
        List<Pvc> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Pvc";
        Query query = session.createQuery(hql);
        list = query.list();
        session.close();
        return list;
    }
    
      public Course getCourseById(String id) {
        List<Course> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Course where courseId = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        Course c = (Course)query.list().get(0);
        session.close();
        return c;
    }
    

  
    public List<Pvc> searchPVC(String key) {
        List<Pvc> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from PVC where PVC_Name like :keyword ";
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
    public void delete(Pvc c) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(c);
        tx.commit();
        session.close();
    }
    public void save1(Pvc c) {
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
    public List<Pvc> getPvcID(int id) {
        System.out.println("getCourseId: "+ id);
        List<Pvc> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Pvc where PVC_Id = :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", id);
        list = query.list();
        System.out.println("list la : " +list.toString());
        session.close();
        return list;
    }
    public Pvc getPvcByID(int id) {
        List<Pvc> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Pvc where PVC_Id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        Pvc c = (Pvc)query.list().get(0);
        session.close();
        return c;
    }
    public List<Pvc> getOtherPVC(int id) {
        List<Pvc> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Pvc where PVC_Id <> :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", id);
        list = query.list();
        session.close();
        return list;
    }
    
    }


