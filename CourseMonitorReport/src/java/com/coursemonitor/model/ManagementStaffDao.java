/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursemonitor.model;

import com.coursemonitor.entity.ManagementStaff;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Nghia
 */
public class ManagementStaffDao {
    
      public ManagementStaff checkLogin(String user, String pass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from ManagementStaff where msUsername = :username AND msPassword = :password ";
        Query query = session.createQuery(hql);
        query.setParameter("username", user);
        query.setParameter("password", pass);
          ManagementStaff s = new ManagementStaff();
        if (query.list().isEmpty()) {
            return null;
        } else {
            s = (ManagementStaff) query.list().get(0);
        }
        session.close();
        return s;
    }
}
