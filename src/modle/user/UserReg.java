package modle.user;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class UserReg {

    public boolean userExsist(String username) {
        Session openSession = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            List list = openSession.createCriteria(pojo.User.class).add(Restrictions.eq("userUsername", username)).list();
            if (list.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        } finally {
            openSession.close();
        }
    }

    public int saveUser(pojo.User user) {

        int x = 0;
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Serializable save = session.save(user);
            transaction.commit();
            x = Integer.parseInt(save.toString());
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            modle.Allert.notificationError("ERROR", e.getMessage());

        } finally {
            session.close();
        }
        return x;
    }

    public boolean updateUser() {

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

}
