/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modle.user;

import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.User;
import pojo.UserLogin;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class User_Login {

    public boolean login(String uname, String pword) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        transaction.commit();
        try {

            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("userUsername", uname));
            criteria.add(Restrictions.eq("userPassword", pword));
            User user = (User) criteria.uniqueResult();
            System.out.println(uname);
            System.out.println(pword);
            if (user != null) {
                if (user.getUserUsername().equals(uname) && user.getUserPassword().equals(pword)) {

                    modle.StaticViews.setLogUser(user);

                    return true;
                } else {
                    System.out.println("Not mach");
                    return false;
                }
            } else {
                System.out.println(" User Null");

                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean save() {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean login(int user) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            UserLogin ul = new UserLogin((User) session.load(User.class, user));
            ul.setUserLoginIn(new Date());
            ul.setUserLoginSyn(1);
            session.save(ul);
            transaction.commit();
            modle.StaticViews.setLog(ul);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean logout(int user, int log) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            UserLogin ul = (UserLogin) session.load(UserLogin.class, log);
            ul.setUser((User) session.load(User.class, user));
            ul.setUserLoginOut(new Date());
            ul.setUserLoginSyn(1);
            session.update(ul);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public String getLogUserName(int user) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        transaction.commit();
        try {
            User u = (User) session.load(User.class, user);
            return u.getUserFullName();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
    }

}
