/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modle.user;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.Dipartment;
import pojo.User;
import pojo.UserHasDipartment;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class User_Assign {

    public List<User> getUsers() {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            List<User> list = session.createCriteria(User.class).add(Restrictions.eq("userStatus", 1)).list();
            return list;
        } catch (Exception e) {
            return null;
        } finally {
            session.close();
        }
    }

    public boolean assignDip(String uid, String dip) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User u = (User) session.load(User.class, Integer.parseInt(uid));
            Dipartment d = (Dipartment)session.createCriteria(Dipartment.class).add(Restrictions.eq("dipName", dip)).uniqueResult();
            
          //  UserHasDipartment userHasDipartment = new pojo.UserHasDipartment(d, u);
            
            

            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            session.close();
        }
    }

}
