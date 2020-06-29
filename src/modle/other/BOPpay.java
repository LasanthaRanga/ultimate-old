package modle.other;

import conn.DB;
import conn.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.*;

import java.io.Serializable;

public class BOPpay {


//    public static void main(String[] args) {
//        setBOPPayment(3,5000);
//    }


    static int ApplicationCatID = 3;

    public static String setBOPPayment(int idBop, double AMOUNT) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().commit();
        Transaction transaction = session.beginTransaction();
        try {
            Interest vat = (Interest) session.createCriteria(Interest.class).add(Restrictions.eq("name", "VAT")).uniqueResult();
            Interest nbt = (Interest) session.createCriteria(Interest.class).add(Restrictions.eq("name", "NBT")).uniqueResult();

            double sv = AMOUNT * vat.getRate() / 100;
            double sn = AMOUNT * nbt.getRate() / 100;

            int i = DB.setData("UPDATE `ultimate2`.`bop`\n" +
                    "SET \n" +
                    " `BOP_total_price` = " + AMOUNT + ", \n" +
                    " `BOP_vat` = " + sv + ",\n" +
                    " `BOP_nbt` = " + sn + ",\n" +
                    " `BOP_stamp` = 0,\n" +
                    " `BOP_other` = 0\n" +
                    "WHERE\n" +
                    "\tbop.idBOP =" + idBop);



            Receipt receipt = new Receipt();

            receipt.setReceiptStatus(0);
            ApplicationCatagory load = (ApplicationCatagory) session.load(ApplicationCatagory.class, ApplicationCatID);
            receipt.setApplicationCatagory(load);
            receipt.setReceptApplicationId(idBop);
            receipt.setReceiptTotal(AMOUNT + sv + sn);
            receipt.setReceiptSyn(1);
            Serializable save = session.save(receipt);
            System.out.println(save);

            transaction.commit();
            return save + "";
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return "";
        } finally {
            session.close();
        }
    }

}
