package modle.adv;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.AdvBords;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class Position {

    public boolean savePosition(pojo.AdvPosition position) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(position);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            modle.Allert.notificationError("Save Position Error", e.getMessage());
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public List<pojo.AdvPosition> getPossitionList() {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            List<pojo.AdvPosition> list = session.createCriteria(pojo.AdvPosition.class).add(Restrictions.eq("advPositionStatus", 1)).list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }

    }

    public boolean isExsist(String position) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            List<pojo.AdvPosition> list = session.createCriteria(pojo.AdvPosition.class).add(Restrictions.eq("advPositionName", position)).list();
            if (list.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        } finally {
            session.close();
        }
    }

    public pojo.AdvPosition getPosition(String position) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            List<pojo.AdvPosition> list = session.createCriteria(pojo.AdvPosition.class).add(Restrictions.eq("advPositionName", position)).list();
            if (list.size() > 0) {
                return list.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public Double getCurrentAddByPossitionID(int idPos) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(AdvBords.class);
            criteria.add(Restrictions.eq("advBordStatus", 1));
            criteria.add(Restrictions.eq("advPosition", (pojo.AdvPosition)session.load(pojo.AdvPosition.class, idPos)));
            List<AdvBords> list = criteria.list();
            double qty = 0;
            for (AdvBords advBords : list) {
                qty += advBords.getAdvBordQty();
            }
            return qty;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        } finally {
            session.close();
        }
    }

}
