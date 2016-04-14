/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursemonitor.model;

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
public class StaffDao {
public void save(Staff c) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(c);
        session.getTransaction().commit();
        session.close();
    }
    public List<Staff> getStaff() {
        List<Staff> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Staff";
        Query query = session.createQuery(hql);
        list = query.list();
        session.close();
        return list;
    }

    public Staff getStaffByName(String name) {
        List<Staff> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Staff where stName = :name";
        Query query = session.createQuery(hql);
        query.setParameter("name", name);
        Staff result = (Staff) query.list().get(0);
        session.close();
        return result;
    }
    public Staff getStaffByID(int id) {
        List<Staff> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Staff where stId = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        Staff result = (Staff) query.list().get(0);
        session.close();
        return result;
    }
    public List<Staff> searchStaff(String key) {
        List<Staff> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Staff where stName like :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", "%" + key + "%");
        list = query.list();
        session.close();
        return list;
    }
    public List<Staff> searchStaffbyID(int key) {
        List<Staff> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Staff where stId like :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", key );
        list = query.list();
        System.out.println("list la : " +list.toString());
        session.close();
        return list;
    }
    public List<Staff> getStaffID(String id) {
        System.out.println("getCourseId: "+ id);
        int idS = 0 ;
        List<Staff> list = null;
        if(id == null){
            
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Staff where stId = :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", null);
        list = query.list();
        System.out.println("list la : " +list.toString());
        session.close();
        return list;
        }else{
            idS = Integer.parseInt(id);
//        List<Staff> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Staff where stId = :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", idS);
        list = query.list();
        System.out.println("list la : " +list.toString());
        session.close();
        return list;
        }
    }
    public void delete(Staff s) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(s);
        tx.commit();
        session.close();
    }

    public Staff checkLogin(String user, String pass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Staff where stUsername = :username AND stPassword = :password ";
        Query query = session.createQuery(hql);
        query.setParameter("username", user);
        query.setParameter("password", pass);
        Staff s = (Staff) query.list().get(0);
        session.close();
        return s;
    }
    public void save1(Staff c) {
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
    public List<Staff> getOtherStaff(int id) {
        List<Staff> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Staff where stId <> :keyword ";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", id);
        list = query.list();
        session.close();
        return list;
    }
}
