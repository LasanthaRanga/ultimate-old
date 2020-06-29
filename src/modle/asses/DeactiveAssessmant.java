/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modle.asses;

import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.AssAllocation;
import pojo.Assessment;

/**
 *
 * @author Ranga Rathnayake
 */
public class DeactiveAssessmant {

    private controller.assess.DeactiveController dac;

    public void loadAssess(int id) {
        System.out.println(id);
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().commit();
        try {
            Assessment ass = (Assessment) session.createCriteria(Assessment.class).add(Restrictions.eq("idAssessment", id)).uniqueResult();

            dac.txt_ward.setText(ass.getWard().getWardName());
            dac.txt_street.setText(ass.getStreet().getStreetName());
            dac.txt_assessmant.setText(ass.getAssessmentNo());
            dac.txt_customer.setText(ass.getCustomer().getCusName());
            dac.txt_oder.setText(ass.getAssessmentOder() + "");
            double allocation = 0;
            Set<AssAllocation> assAllocations = ass.getAssAllocations();
            for (AssAllocation assAllocation : assAllocations) {
                if (assAllocation.getAssAllocationStatus() == 1) {
                    allocation = assAllocation.getAssAllocation();
                }
            }
            dac.txt_allocation.setText(modle.Maths.get2String(allocation));
            dac.x = ass.getIdAssessment();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    /**
     * @param dac the dac to set
     */
    public void setDac(controller.assess.DeactiveController dac) {
        this.dac = dac;
    }

    public void deactiveAssessment(int id, String com) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Assessment ass = (Assessment) session.createCriteria(Assessment.class).add(Restrictions.eq("idAssessment", id)).uniqueResult();
            ass.setAssessmentSyn(1);
            ass.setAssessmentComment(com);
            session.update(ass);
            transaction.commit();
            modle.Allert.notificationGood("Deactivated Assessment", " ID : " + id);
        } catch (Exception e) {
            modle.Allert.notificationError("Deactivated Assessment", e.getMessage());
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

}
