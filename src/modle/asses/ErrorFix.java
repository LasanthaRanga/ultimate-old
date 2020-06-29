package modle.asses;

import conn.NewHibernateUtil;
import org.hibernate.Session;
import pojo.AssPayhistry;


/**
 * Created by Ranga on 2019-01-16.
 */
public class ErrorFix {


    public void loadData(int idAssess) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().commit();
        try {

//            AssPayhistry assPayhistry = (AssPayhistry) session.load(AssPayhistry.class, idAssess);



        } catch (Exception e) {
            e.printStackTrace();
            modle.ErrorLog.writeLog(e.getMessage(), "ErrorFix", "loadData", "modle.assess.ErrorFix");
        } finally {
            session.close();
        }
    }


}
