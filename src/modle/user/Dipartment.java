/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modle.user;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.User;
import pojo.UserHasDipartment;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class Dipartment {

    public boolean save(pojo.Dipartment dipartment) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(dipartment);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            modle.Allert.notificationError("ERROR", e.getMessage());
            return false;
        } finally {
            session.close();
        }
    }

    public List<pojo.Dipartment> getDipartmants() {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<pojo.Dipartment> list = session.createCriteria(pojo.Dipartment.class).add(Restrictions.eq("dipStatus", 1)).list();
            return list;
        } catch (Exception e) {
            transaction.rollback();
            modle.Allert.notificationError("ERROR", e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    public boolean saveApprovalCat(pojo.ApprovalCat cat) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(cat);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            modle.Allert.notificationError("ERROR", e.getMessage());
            return false;
        } finally {
            session.close();
        }

    }

    public List<pojo.ApprovalCat> getApprvalCats() {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            List<pojo.ApprovalCat> list = session.createCriteria(pojo.ApprovalCat.class).add(Restrictions.eq("statusAppCat", 1)).list();
            return list;
        } catch (Exception e) {
            modle.Allert.notificationError("ERROR", e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    public List<pojo.ApplicationCatagory> getApplicationCatagorys() {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            List<pojo.ApplicationCatagory> list = session.createCriteria(pojo.ApplicationCatagory.class).add(Restrictions.eq("applicationCatagoryStatus", 1)).list();
            return list;
        } catch (Exception e) {
            modle.Allert.notificationError("ERROR", e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    //Dipartment Assign Load Remove
    public boolean assignDipartment(String uid, String dipName) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User u = (User) session.load(User.class, Integer.parseInt(uid));
            List<pojo.Dipartment> list = session.createCriteria(pojo.Dipartment.class).add(Restrictions.eq("dipName", dipName)).list();
            pojo.Dipartment d = list.get(0);

            Criteria cry = session.createCriteria(UserHasDipartment.class);
            cry.add(Restrictions.eq("user", u));
            cry.add(Restrictions.eq("dipartment", d));
            List l = cry.list();

            UserHasDipartment uhd;

            if (l.size() > 0) {
                uhd = (UserHasDipartment) l.get(0);
            } else {
                uhd = new UserHasDipartment();
            }

            uhd.setDipartment(d);
            uhd.setUser(u);

            session.saveOrUpdate(uhd);

            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            modle.Allert.notificationError("ERROR", e.getMessage());
            return false;
        } finally {
            session.close();
        }

    }

    public List<String> getDipartmentsByUser(int uid) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            User u = (User) session.load(User.class, uid);
            Criteria criteria = session.createCriteria(UserHasDipartment.class);
            List<UserHasDipartment> l = criteria.add(Restrictions.eq("user", u)).list();
            ArrayList<String> list = new ArrayList<>();
            for (UserHasDipartment userHasDipartment : l) {
                list.add(userHasDipartment.getDipartment().getDipName());
            }
            return list;
        } catch (Exception e) {
            modle.Allert.notificationError("ERROR", e.getMessage());
            return null;
        } finally {
            session.close();
        }

    }

    public boolean removeDipartment(String uid, String dipName) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User u = (User) session.load(User.class, Integer.parseInt(uid));
            List<pojo.Dipartment> list = session.createCriteria(pojo.Dipartment.class).add(Restrictions.eq("dipName", dipName)).list();
            pojo.Dipartment d = list.get(0);

            Criteria cry = session.createCriteria(UserHasDipartment.class);
            cry.add(Restrictions.eq("user", u));
            cry.add(Restrictions.eq("dipartment", d));
            List l = cry.list();
            UserHasDipartment get = (UserHasDipartment) l.get(0);

            session.delete(get);

            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            modle.Allert.notificationError("ERROR", e.getMessage());
            return false;
        } finally {
            session.close();
        }

    }

    //Application Assign Load Remove
    public boolean assignApplication(String uid, String appName) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User u = (User) session.load(User.class, Integer.parseInt(uid));
            List<pojo.ApplicationCatagory> list = session.createCriteria(pojo.ApplicationCatagory.class).add(Restrictions.eq("applicationName", appName)).list();
            pojo.ApplicationCatagory a = list.get(0);

            Criteria cry = session.createCriteria(pojo.UserHasApplicationCatagory.class);
            cry.add(Restrictions.eq("user", u));
            cry.add(Restrictions.eq("applicationCatagory", a));
            List l = cry.list();

            pojo.UserHasApplicationCatagory uhac;

            if (l.size() > 0) {
                uhac = (pojo.UserHasApplicationCatagory) l.get(0);
            } else {
                uhac = new pojo.UserHasApplicationCatagory();
            }

            uhac.setApplicationCatagory(a);
            uhac.setUser(u);
            session.saveOrUpdate(uhac);

            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            modle.Allert.notificationError("ERROR", e.getMessage());
            return false;
        } finally {
            session.close();
        }

    }

    public List<String> getApplicaListByUser(int uid) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            User u = (User) session.load(User.class, uid);
            Criteria criteria = session.createCriteria(pojo.UserHasApplicationCatagory.class);
            List<pojo.UserHasApplicationCatagory> l = criteria.add(Restrictions.eq("user", u)).list();
            ArrayList<String> list = new ArrayList<>();
            for (pojo.UserHasApplicationCatagory uhac : l) {
                list.add(uhac.getApplicationCatagory().getApplicationName());
            }
            return list;
        } catch (Exception e) {
            modle.Allert.notificationError("ERROR", e.getMessage());
            return null;
        } finally {
            session.close();
        }

    }

    public boolean removeApplication(String uid, String appName) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User u = (User) session.load(User.class, Integer.parseInt(uid));
            List<pojo.ApplicationCatagory> list = session.createCriteria(pojo.ApplicationCatagory.class).add(Restrictions.eq("applicationName", appName)).list();
            pojo.ApplicationCatagory a = list.get(0);

            Criteria cry = session.createCriteria(pojo.UserHasApplicationCatagory.class);
            cry.add(Restrictions.eq("user", u));
            cry.add(Restrictions.eq("applicationCatagory", a));
            List l = cry.list();
            pojo.UserHasApplicationCatagory get = (pojo.UserHasApplicationCatagory) l.get(0);

            session.delete(get);

            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            modle.Allert.notificationError("ERROR", e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }

    }

    public boolean assignApproval(String uid, String approvName) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User u = (User) session.load(User.class, Integer.parseInt(uid));
            List<pojo.ApprovalCat> list = session.createCriteria(pojo.ApprovalCat.class).add(Restrictions.eq("approvalName", approvName)).list();
            pojo.ApprovalCat a = list.get(0);

            Criteria cry = session.createCriteria(pojo.UserHasApprovalCat.class);
            cry.add(Restrictions.eq("user", u));
            cry.add(Restrictions.eq("approvalCat", a));
            List l = cry.list();

            pojo.UserHasApprovalCat uhac;

            if (l.size() > 0) {
                uhac = (pojo.UserHasApprovalCat) l.get(0);
            } else {
                uhac = new pojo.UserHasApprovalCat();
            }

            uhac.setApprovalCat(a);
            uhac.setUser(u);
            uhac.setDipStatus(1);
            uhac.setDipSyn(1);

            session.saveOrUpdate(uhac);

            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            modle.Allert.notificationError("ERROR", e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }

    }

    public List<String> getApprovalListByUser(int uid) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            User u = (User) session.load(User.class, uid);
            Criteria criteria = session.createCriteria(pojo.UserHasApprovalCat.class);
            List<pojo.UserHasApprovalCat> l = criteria.add(Restrictions.eq("user", u)).list();
            ArrayList<String> list = new ArrayList<>();
            for (pojo.UserHasApprovalCat uhac : l) {
                list.add(uhac.getApprovalCat().getApprovalName());
            }
            return list;
        } catch (Exception e) {
            modle.Allert.notificationError("ERROR", e.getMessage());
            return null;
        } finally {
            session.close();
        }

    }

    public boolean removeApproval(String uid, String appName) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User u = (User) session.load(User.class, Integer.parseInt(uid));
            List<pojo.ApprovalCat> list = session.createCriteria(pojo.ApprovalCat.class).add(Restrictions.eq("approvalName", appName)).list();
            pojo.ApprovalCat a = list.get(0);

            Criteria cry = session.createCriteria(pojo.UserHasApprovalCat.class);
            cry.add(Restrictions.eq("user", u));
            cry.add(Restrictions.eq("approvalCat", a));
            List l = cry.list();
            pojo.UserHasApprovalCat get = (pojo.UserHasApprovalCat) l.get(0);

            session.delete(get);

            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            modle.Allert.notificationError("ERROR", e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }

    }

}
