package modle.adv;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class BoardType {

    public List<pojo.AdvBoardType> getBoardTypes() {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            return session.createCriteria(pojo.AdvBoardType.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }

    }

    public pojo.AdvBoardType getBoardType(String bordType) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            List<pojo.AdvBoardType> list = session.createCriteria(pojo.AdvBoardType.class).add(Restrictions.eq("advBoardTypeName", bordType)).list();
            return list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

}
