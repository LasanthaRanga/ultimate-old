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

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class Ward {

    public List<pojo.Ward> getWardList() {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            List<pojo.Ward> list = session.createCriteria(pojo.Ward.class).add(Restrictions.eq("wardStatus", 1)).list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public ObservableList<String> getWardObservableListSQL() {
        String quary = "SELECT ward.ward_name FROM ward";
        ObservableList<String> wardObservableList = FXCollections.observableArrayList();
        try {
            ResultSet data = conn.DB.getData(quary);
            while (data.next()) {
                wardObservableList.add(data.getString("ward_name"));
            }
            return wardObservableList;
        } catch (Exception ex) {
            Logger.getLogger(Ward.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public pojo.Ward getWardPojo(String ward){
         Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
             return (pojo.Ward)session.createCriteria(pojo.Ward.class).add(Restrictions.eq("wardName", ward)).uniqueResult();          
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

}
