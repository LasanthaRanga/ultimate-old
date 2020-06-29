/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modle.adv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import pojo.ApplicationCatagory;
import pojo.Customer;
import pojo.CustomerHasApplicationCatagory;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class customer {

    public Customer save(Customer cus) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(cus);
            ApplicationCatagory applicationCatagory = (ApplicationCatagory) session.load(ApplicationCatagory.class, 1);

            List<CustomerHasApplicationCatagory> list = session.createCriteria(CustomerHasApplicationCatagory.class).add(Restrictions.eq("customer", cus)).list();
            CustomerHasApplicationCatagory chac;

            if (list.size() > 0) {
                chac = list.get(0);
            } else {
                chac = new CustomerHasApplicationCatagory();
            }
            chac.setApplicationCatagory(applicationCatagory);
            chac.setCustomer(cus);
            chac.setCustomerHasApplicationCatagoryStatus(1);
            chac.setCustomerHasApplicationCatagorySyn(1);
            session.saveOrUpdate(chac);
            transaction.commit();
            return cus;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            modle.Allert.notificationError("ERROR", e.getMessage());
            return null;
        } finally {
            session.close();
        }

    }

    public boolean save2(Customer cus) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(cus);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            modle.Allert.notificationError("ERROR", e.getMessage());
            return false;
        } finally {
            session.close();
        }

    }

    public ArrayList<Customer> getAdvCustomerList() {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            ApplicationCatagory adv = (ApplicationCatagory) session.load(ApplicationCatagory.class, 1);
            Criteria criteria = session.createCriteria(CustomerHasApplicationCatagory.class);
            criteria.add(Restrictions.eq("customerHasApplicationCatagoryStatus", 1)).list();
            criteria.add(Restrictions.eq("applicationCatagory", adv));
            criteria.addOrder(Order.desc("idCustomerHasApplicationCatagory"));
            List<CustomerHasApplicationCatagory> list = criteria.list();
            ArrayList<Customer> cusList = new ArrayList<Customer>();
            for (CustomerHasApplicationCatagory chac : list) {
                cusList.add(chac.getCustomer());
            }
            return cusList;
        } catch (Exception e) {
            e.printStackTrace();
            modle.Allert.notificationError("ERROR", e.getMessage());
            return null;
        } finally {
            session.close();
        }

    }

    public String getCustomerName(int cusId) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            Customer customer = (Customer) session.load(Customer.class, cusId);
            return customer.getCusName();
        } catch (Exception e) {
            e.printStackTrace();
            modle.Allert.notificationError("ERROR", e.getMessage());
            return null;
        } finally {
            session.close();
        }

    }

}
