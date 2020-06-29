package modle.adv;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import conn.DB;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.AdvAdvertising;
import pojo.ApplicationCatagory;
import pojo.ApprovalBy;
import pojo.ApproveDetails;

/**
 * @author RM.LasanthaRanga@gmail.com
 */
public class SendToApprove {

    public boolean sendToApprove(int applicationId) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {

            pojo.SendToApprove sendToApprove = new pojo.SendToApprove();

            ApprovalBy ab = (ApprovalBy) session.load(ApprovalBy.class, 1);
            ApplicationCatagory ac = (ApplicationCatagory) session.load(ApplicationCatagory.class, ab.getApplicationCatagory().getIdApplicationCatagory());

            sendToApprove.setApplicationCatagory(ac);
            sendToApprove.setApprovalBy(ab);
            sendToApprove.setApplicationId(applicationId);
            sendToApprove.setStatusApprove(0);
            sendToApprove.setUserBySendByUser(modle.StaticViews.getLogUser());
            sendToApprove.setSendDateTime(new Date());

            session.save(sendToApprove);

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }

    }

    public List<pojo.SendToApprove> getApprovalList(Date day, int status) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(pojo.SendToApprove.class);
            if (day != null) {
                criteria.add(Restrictions.eq("sendDateTime", day));
            }
            criteria.add(Restrictions.eq("statusApprove", status));
            ArrayList<pojo.SendToApprove> arrayList = new ArrayList<pojo.SendToApprove>();
            List<pojo.SendToApprove> list = criteria.list();
            for (pojo.SendToApprove sendToApprove : list) {
                AdvAdvertising app = (AdvAdvertising) session.load(AdvAdvertising.class, sendToApprove.getApplicationId());
                if (app.getAdvPaidNotpaid() == 0) {
                    arrayList.add(sendToApprove);
                }
            }
            return arrayList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public boolean setApproval(int advID, String coment, Double price, int status) {

        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            pojo.SendToApprove sendToApprove = (pojo.SendToApprove) session.load(pojo.SendToApprove.class, advID);
            sendToApprove.setStatusApprove(status);
            sendToApprove.setUserBySendToUser(modle.StaticViews.getLogUser());

            Set<ApproveDetails> approveDetailses = sendToApprove.getApproveDetailses();
            ApproveDetails ad = null;
            if (approveDetailses.size() > 0) {
                for (ApproveDetails approveDetailse : approveDetailses) {
                    ad = approveDetailse;
                }
            } else {
                ad = new ApproveDetails();
            }

            AdvAdvertising adv = (AdvAdvertising) session.load(AdvAdvertising.class, sendToApprove.getApplicationId());
            adv.setAdvVisitingPrice(price);
            adv.setAdvFullTotal(adv.getAdvFullTotal() + price);

            ad.setSendToApprove(sendToApprove);
            ad.setComment(coment);
            ad.setPricing(price);
            ad.setStatusApprovDetails(status);
            ad.setDateApproveDetails(new Date());
            session.update(adv);

            session.update(sendToApprove);
            session.saveOrUpdate(ad);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }

    }


    public ApproveDetails getApprovalDetailsBySendToApprovalId(int idSendToApproval) {

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "\tapprove_details.idApprove_details,\n" +
                    "\tapprove_details.`comment`,\n" +
                    "\tapprove_details.pricing,\n" +
                    "\tapprove_details.status_approv_details,\n" +
                    "\tapprove_details.Others,\n" +
                    "\tapprove_details.date_approve_details,\n" +
                    "\tapprove_details.Send_to_approve_idSend_to_approve\n" +
                    "FROM\n" +
                    "\tapprove_details\n" +
                    "WHERE\n" +
                    "\tapprove_details.Send_to_approve_idSend_to_approve = "+idSendToApproval+"");

            if (data.last()) {
                ApproveDetails approveDetails = new ApproveDetails();
                approveDetails.setIdApproveDetails(data.getInt("idApprove_details"));
                approveDetails.setComment(data.getString("comment"));
                approveDetails.setPricing(data.getDouble("pricing"));
                approveDetails.setStatusApprovDetails(data.getInt("status_approv_details"));
                approveDetails.setDateApproveDetails(data.getDate("date_approve_details"));
                return approveDetails;
            }else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
