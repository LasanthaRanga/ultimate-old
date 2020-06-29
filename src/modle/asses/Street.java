package modle.asses;

import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.Ward;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class Street {

    public List<pojo.Street> getStreetListByWard(String ward) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            List<Ward> list = session.createCriteria(Ward.class).add(Restrictions.eq("wardName", ward)).list();
            Ward w = list.get(0);
            Criteria criteria = session.createCriteria(pojo.Street.class);
            criteria.add(Restrictions.eq("ward", w));
            criteria.add(Restrictions.eq("streetStatus", 1));
            List<pojo.Street> slist = criteria.list();
            return slist;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public ObservableList<String> getAllStreetObservableListSQL() {
        String quary = "SELECT street.street_name FROM street ORDER BY street.street_name ASC";
        ObservableList<String> wardObservableList = FXCollections.observableArrayList();
        try {
            ResultSet data = conn.DB.getData(quary);
            while (data.next()) {
                wardObservableList.add(data.getString("street_name"));
            }
            return wardObservableList;
        } catch (Exception ex) {
            Logger.getLogger(modle.asses.Ward.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ObservableList<String> getStreetObservableListSQLByWard(String ward) {
        String quary = "SELECT\n"
                + "ward.ward_name,\n"
                + "ward.idWard,\n"
                + "street.street_name,\n"
                + "street.idStreet\n"
                + "FROM\n"
                + "ward\n"
                + "INNER JOIN street ON street.Ward_idWard = ward.idWard WHERE ward_name = '"+ward+"'  ORDER BY street.street_name ASC";
        ObservableList<String> wardObservableList = FXCollections.observableArrayList();
        try {
            ResultSet data = conn.DB.getData(quary);
            while (data.next()) {
                wardObservableList.add(data.getString("street_name"));
            }
            return wardObservableList;
        } catch (Exception ex) {
            Logger.getLogger(modle.asses.Ward.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
        public pojo.Street getStreetsByStreetNameAndWard(String strret, String ward) {
        Session ses = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            Criteria cry = ses.createCriteria(pojo.Street.class);
            Ward ward1 = (Ward) ses.createCriteria(Ward.class).add(Restrictions.eq("wardName", ward)).uniqueResult();
            cry.add(Restrictions.eq("ward", ward1));
            pojo.Street stre = (pojo.Street) cry.add(Restrictions.eq("streetName", strret)).uniqueResult();
            return stre;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            ses.close();
        }

    }

}
