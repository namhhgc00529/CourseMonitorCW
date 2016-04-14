/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursemonitor.model;

import com.coursemonitor.entity.AssignCourse;
import com.coursemonitor.entity.AssignFaculty;
import com.coursemonitor.entity.Course;
import com.coursemonitor.entity.Dlt;
import com.coursemonitor.entity.Faculty;
import com.coursemonitor.entity.Staff;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Nghia
 */
public class FacultyDao {
       public void save(AssignCourse c) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(c);
        session.getTransaction().commit();
        session.close();
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
        
        public List<Faculty> getFaculty() {
        List<Faculty> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Faculty";
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
        public List<Faculty> getFacultyID(int id) {
        System.out.println("getCourseId: "+ id);
        List<Faculty> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Faculty where FacultyId like :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", id);
        list = query.list();
        System.out.println("list la : " +list.toString());
        session.close();
        return list;
    }
        public Faculty getFacultyById(int id) {
        List<Faculty> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Faculty where FacultyId = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        Faculty c = (Faculty)query.list().get(0);
        session.close();
        return c;
    }
        public List<AssignFaculty> searchFacultyByID(int key) {
        List<AssignFaculty> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Faculty where FacultyId like :keyword ";
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
        
        public List<Faculty> getOtherFaculty(int id) {
        List<Faculty> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Faculty where FacultyId <> :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", id);
        list = query.list();
        session.close();
        return list;
    }
}
