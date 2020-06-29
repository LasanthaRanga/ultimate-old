package modle.adv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.AdvAdvertising;
import pojo.AdvBords;
import pojo.Customer;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class Advertising {

    public int saveAdd(AdvAdvertising adv, ArrayList<AdvBords> bords) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Serializable save = session.save(adv);
            for (AdvBords bord : bords) {
                session.saveOrUpdate(bord);
            }

            AdvAdvertising aa = (AdvAdvertising) session.load(AdvAdvertising.class, save);

            transaction.commit();
            return aa.getIdAdvAdvertising();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return 0;
        } finally {
            session.close();
        }
    }

    public Integer getCurrentAddByCustomer(int idCus) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            Criteria cry = session.createCriteria(AdvAdvertising.class);
            cry.add(Restrictions.eq("customerIdCustomer", idCus));
            cry.add(Restrictions.eq("advStatus", 1));
            return cry.list().size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
    }

    public SelectedData getSelectedDataList(int applicationID) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            AdvAdvertising aa = (AdvAdvertising) session.load(AdvAdvertising.class, applicationID);

            Customer customer = (Customer) session.load(Customer.class, aa.getCustomerIdCustomer());
            SelectedData selectedData = new SelectedData(
                    aa.getAdvBoardNo(),
                    customer.getCusName(),
                    aa.getAdvStartDate().toString(),
                    aa.getAdvEndDate().toString(),
                    aa.getAdvFullTotal());

            return selectedData;
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }

    }

    public List<AdvAdvertising> getApplicationList(Date day, int status) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().commit();
        ArrayList<AdvAdvertising> advertisings = new ArrayList<AdvAdvertising>();
        try {
            Criteria criteria = session.createCriteria(AdvAdvertising.class);
            Criteria sendCriteria = session.createCriteria(pojo.SendToApprove.class);
            criteria.add(Restrictions.eq("advPaidNotpaid", 0));
            if (day != null) {
                criteria.add(Restrictions.eq("advCurrentDate", day));
                sendCriteria.add(Restrictions.eq("sendDateTime", day));
            }
            if (status >= 3) {
                HashMap<Integer, AdvAdvertising> hashMap = new HashMap<Integer, AdvAdvertising>();
                List<AdvAdvertising> applist = criteria.list();
                for (AdvAdvertising advAdvertising : applist) {
                    hashMap.put(advAdvertising.getIdAdvAdvertising(), advAdvertising);
                }
                List<pojo.SendToApprove> list = sendCriteria.list();
                for (pojo.SendToApprove sendToApprove : list) {
                    hashMap.remove(sendToApprove.getApplicationId());
                }

                for (Map.Entry<Integer, AdvAdvertising> entry : hashMap.entrySet()) {
                    Integer key = entry.getKey();
                    AdvAdvertising value = entry.getValue();
                    advertisings.add(value);
                }
                return advertisings;
            } else {
                sendCriteria.add(Restrictions.eq("statusApprove", status));
                List<pojo.SendToApprove> sendList = sendCriteria.list();
                for (pojo.SendToApprove sendToApprove : sendList) {
                    AdvAdvertising adv = (AdvAdvertising) session.load(AdvAdvertising.class, sendToApprove.getApplicationId());
                    if (adv.getAdvPaidNotpaid() == 0) {
                        advertisings.add(adv);
                    }
                }
                return advertisings;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public boolean deleteApplication(int idApp) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {

            AdvAdvertising ad = (AdvAdvertising) session.load(AdvAdvertising.class, idApp);
            if (ad.getAdvPaidNotpaid() == 0) {
                pojo.SendToApprove sta = (pojo.SendToApprove) session.createCriteria(pojo.SendToApprove.class).add(Restrictions.eq("applicationId", ad.getIdAdvAdvertising())).uniqueResult();

                if (sta == null) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete");
                    alert.setHeaderText("Look, a Confirmation Dialog");
                    alert.setContentText("Are you sure to delete this?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        // ... user chose OK
                        Set<AdvBords> advBordses = ad.getAdvBordses();
                        for (AdvBords advBordse : advBordses) {
                            session.delete(advBordse);
                        }
                        session.delete(ad);
                        transaction.commit();
                        return true;
                    } else {
                        // ... user chose CANCEL or closed the dialog    
                        return false;
                    }

                } else {
                    modle.Allert.notificationInfo("Can not Delete This", "This application goes to approval");
                    return false;
                }
            } else {
                modle.Allert.notificationInfo("Can Not Delete This", "This is paid application");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }

    }

    public class SelectedData {

        private final String bordNo;
        private final String cusName;
        private final String sdate;
        private final String edate;
        private final double fullAmount;

        /**
         * @return the bordNo
         */
        public String getBordNo() {
            return bordNo;
        }

        /**
         * @return the cusName
         */
        public String getCusName() {
            return cusName;
        }

        /**
         * @return the sdate
         */
        public String getSdate() {
            return sdate;
        }

        /**
         * @return the edate
         */
        public String getEdate() {
            return edate;
        }

        /**
         * @return the fullAmount
         */
        public double getFullAmount() {
            return fullAmount;
        }

        public SelectedData(String bordNo, String cusName, String sdate, String edate, double fullAmount) {
            this.bordNo = bordNo;
            this.cusName = cusName;
            this.sdate = sdate;
            this.edate = edate;
            this.fullAmount = fullAmount;
        }

    }

}
