/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modle.asses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class Discription {

    public ArrayList<String> getCustomerFnameList() {
        ArrayList<String> ar = new ArrayList();
       // HashMap<Integer, String> arr = new HashMap<>();
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            List<pojo.AssDiscription> c = session.createCriteria(pojo.AssDiscription.class).list();

            for (pojo.AssDiscription customer1 : c) {
          //      arr.put(customer1.getIdassDiscription(), customer1.getAssDiscription());
                ar.add(customer1.getAssDiscription());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return ar;
    }

    public pojo.AssDiscription getDiscriptionPojo(String disString) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            List<pojo.AssDiscription> discriptions = session.createCriteria(pojo.AssDiscription.class).add(Restrictions.eq("assDiscription", disString)).list();
            if (discriptions.size() > 0) {
                return discriptions.get(0);
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
}
