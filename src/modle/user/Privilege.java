package modle.user;

import conn.DB;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.Privilage;
import pojo.User;
import pojo.UserHasPrivilage;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class Privilege {

    public List<Privilage> getPrivilages() {

        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            return session.createCriteria(Privilage.class).list();
        } catch (Exception e) {
            transaction.rollback();
            return null;
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
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean setPrivilage(int prid, int uid) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Privilage privilage = (Privilage) session.load(Privilage.class, prid);
            User user = (User) session.load(User.class, uid);

            Criteria cry = session.createCriteria(UserHasPrivilage.class);
            cry.add(Restrictions.eq("user", user));
            List list = cry.add(Restrictions.eq("privilage", privilage)).list();
            if (list.size() > 0) {

            } else {
                UserHasPrivilage uhp = new UserHasPrivilage();
                uhp.setUser(user);
                uhp.setPrivilage(privilage);
                uhp.setUserHasPrivilageStatus(1);
                uhp.setUserHasPrivilageSyn(1);
                session.save(uhp);
            }

            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean removePrivilage(int prid, int uid) {

        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Privilage privilage = (Privilage) session.load(Privilage.class, prid);
            User user = (User) session.load(User.class, uid);

            Criteria cry = session.createCriteria(UserHasPrivilage.class);
            cry.add(Restrictions.eq("user", user));
            List<UserHasPrivilage> list = cry.add(Restrictions.eq("privilage", privilage)).list();

            if (list.size() > 0) {
                session.delete(list.get(0));
                transaction.commit();
            }
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public HashSet<String> getPrivilageListByUser(User user) {
        //System.out.println(user.getIdUser());
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User pu = (User) session.load(User.class, user.getIdUser());
            System.out.println(pu.getUserFullName());
            List<UserHasPrivilage> list = session.createCriteria(UserHasPrivilage.class).add(Restrictions.eq("user", pu)).list();
            System.out.println(list.size());
            HashSet<String> hashSet = new HashSet<String>();
            for (UserHasPrivilage uhp : list) {
                hashSet.add(uhp.getPrivilage().getView());
            }

            return hashSet;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public ArrayList<String> getPrivilageListByUserQuary(User user) {

        try {

            ResultSet data = DB.getData("   SELECT\n"
                    + "privilage.`view`\n"
                    + "FROM\n"
                    + "`user`\n"
                    + "LEFT JOIN user_has_privilage ON user_has_privilage.User_idUser = `user`.idUser\n"
                    + "LEFT JOIN privilage ON user_has_privilage.Privilage_idPrivilage = privilage.idPrivilage\n"
                    + "WHERE\n"
                    + "`user`.idUser = '" + user.getIdUser() + "' AND\n"
                    + "privilage.type = 'fx'\n"
                    + "GROUP BY\n"
                    + "privilage.idPrivilage\n"
                    + "ORDER BY\n"
                    + "privilage.arange ASC     ");

            ArrayList<String> hashSet = new ArrayList<String>();

            while (data.next()) {
               System.out.println(data.getString("view")+"   ---------");
                 hashSet.add(data.getString("view"));
            }

           

            return hashSet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

        }
    }

    public boolean havePrivilage(int user, int privilage) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User u = (User) session.load(User.class, user);
            Privilage p = (Privilage) session.load(Privilage.class, privilage);

            Criteria cry = session.createCriteria(UserHasPrivilage.class);
            cry.add(Restrictions.eq("user", u));
            List list = cry.add(Restrictions.eq("privilage", p)).list();
            if (list.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }

    }

}
