/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursemonitor.model;

import com.coursemonitor.entity.AssignCourse;
import com.coursemonitor.entity.AssignFaculty;
import com.coursemonitor.entity.Course;
import com.coursemonitor.entity.Staff;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Nghia
 */
public class AssignFacultyDao {
       public void save(AssignFaculty c) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(c);
        session.getTransaction().commit();
        session.close();
    }
       public void save1(AssignFaculty c) {
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
       
       
       
        public List<Course> getLeaderAssignedByCL(Staff cl) {
        List<AssignCourse> list = null;   
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from AssignCourse where staffByClId = :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", cl);
        list = query.list();
        List<Course> lc = new ArrayList<Course>();      
        for(AssignCourse ac : list)
        {
        lc.add(ac.getCourse());
        }      
        session.close();
        return lc;
    }
        
          
        public List<Course> getModeratorAssignedByCL(Staff cl) {
        List<AssignCourse> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from AssignCourse where staffByCmId = :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", cl);
        list = query.list();
        List<Course> lc = new ArrayList<Course>();       
        for(AssignCourse ac : list)
        {
        lc.add(ac.getCourse());
        }      
        session.close();
        return lc;
    }
        
        public List<AssignFaculty> getCourse() {
        List<AssignFaculty> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from AssignFaculty";
        Query query = session.createQuery(hql);
        list = query.list();
        session.close();
        return list;
    }
        public List<AssignFaculty> searchAssignFaculty(String key) {
        List<AssignFaculty> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from AssignFaculty where facultyId like :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", key);
        list = query.list();
        session.close();
        return list;
    }
        public List<AssignFaculty> getFacultyID(String id) {
        List<AssignFaculty> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from AssignFaculty where idAF like :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", id);
        list = query.list();
        System.out.println("list la : " +list.toString());
        session.close();
        return list;
    }
        public List<AssignFaculty> searchFacultyByID(int key) {
        List<AssignFaculty> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from AssignFaculty where idAF like :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", key);
        list = query.list();
        session.close();
        return list;
    }
        public void delete(AssignFaculty c) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(c);
        tx.commit();
        session.close();
    }
        
        
        
          public List<AssignFaculty> getFacultyByDiffirentID(Course c) {
        List<AssignFaculty> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from AssignFaculty where course <> :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", c);
        list = query.list();
        session.close();
        return list;
    }
        
        
        
}
