/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modle;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class Interest {

    public double getInterestRate(String type) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            pojo.Interest interest = (pojo.Interest) session.createCriteria(pojo.Interest.class).add(Restrictions.eq("name", type)).uniqueResult();
            return interest.getRate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
    }
}
