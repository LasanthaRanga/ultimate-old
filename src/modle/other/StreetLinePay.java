package modle.other;

import conn.DB;
import conn.NewHibernateUtil;
import modle.GetInstans;
import org.hibernate.Cache;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.ApplicationCatagory;
import pojo.Interest;
import pojo.Receipt;
import pojo.Streetline;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

/**
 * Created by Ranga on 2019-03-04.
 */
public class StreetLinePay {

    public  static Double DIPOSIT = 100.0;
    public  static Double AMOUNT = 600.0;
    public  static Integer ApplicationCatID = 7;

    public final static int StillNotApprove = 0; // Ricipt eka lagata enakan awin ne 0 ho null dekama wenna puluwan
    public final static int ApproveToPayStatus = 1; //recipt eka genarate kala
    public final static int PaidStatus = 2; //Pay Kala


    public static void main(String[] args) {


        //Street Line ID eka pass karanna meken Ricipt Ekat Data pass wenawa
        StreetLinePay.setPayment(63);

        //Recipt ekata data pass kalata passe meken ena ID eka vive karanna meken //bar cord eka wanuwata denata
        // meke ApplicationCatagory ID eka 7 hethuwa Street line nisa
        // Apllication ID eka  10 ho onema ekak Street line tabal eke ID eka
        String riciptID = getRiciptID(7, 7);
        System.out.println(riciptID);


    }

    public static String setPayment(int idStrretLine) {


        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "\tfixcharge.idFix,\n" +
                    "\tfixcharge.idAppCat,\n" +
                    "\tfixcharge.paytype,\n" +
                    "\tfixcharge.AppName,\n" +
                    "\tfixcharge.charge,\n" +
                    "\tfixcharge.hasVat,\n" +
                    "\tfixcharge.hasNbt,\n" +
                    "\tfixcharge.hasStamp,\n" +
                    "\tfixcharge.hasOther,\n" +
                    "\tfixcharge.vote_id\n" +
                    "FROM\n" +
                    "\t`FixCharge`\n" +
                    "WHERE\n" +
                    "\tidAppCat = 7");
            while (data.next()) {
                if(data.getInt("paytype")==1) {
                    AMOUNT = data.getDouble("charge");
                }
                if(data.getInt("paytype")==2) {
                    DIPOSIT = data.getDouble("charge");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().commit();
        Transaction transaction = session.beginTransaction();
        try {
            Interest vat = (Interest) session.createCriteria(Interest.class).add(Restrictions.eq("name", "VAT")).uniqueResult();
            Interest nbt = (Interest) session.createCriteria(Interest.class).add(Restrictions.eq("name", "NBT")).uniqueResult();

            double sv = AMOUNT * vat.getRate() / 100;
            double sn = AMOUNT * nbt.getRate() / 100;

            Streetline sl = (Streetline) session.load(Streetline.class, idStrretLine);
            sl.setVat(sv);
            sl.setNbt(sn);
            sl.setAmount(AMOUNT);
            sl.setDeposit(DIPOSIT);
            sl.setApproveTopPayStatus(1);
            session.update(sl);


            Receipt receipt = new Receipt();

            receipt.setReceiptStatus(0);
            ApplicationCatagory load = (ApplicationCatagory) session.load(ApplicationCatagory.class, ApplicationCatID);
            receipt.setApplicationCatagory(load);
            receipt.setReceptApplicationId(sl.getIdStreetLine());
            receipt.setReceiptTotal(DIPOSIT + AMOUNT + sv + sn);
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


    public static String getRiciptID(int applicationCatID, int ApplicationID) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            Receipt receipt = (Receipt) session.createCriteria(Receipt.class)
                    .add(Restrictions.eq("applicationCatagory", (ApplicationCatagory) session.load(ApplicationCatagory.class, applicationCatID)))
                    .add(Restrictions.eq("receptApplicationId", ApplicationID)).uniqueResult();
            return receipt.getIdReceipt() + "";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }

    }


    //me method eka use wenne ne oyage application eken meka mama karana wedak
    public static String genarateRisiptNo(int applicationcat, String ricipt, int idRecipt, double cheque, double cashe) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String rn = "";
        try {
            Criteria criteria = session.createCriteria(Receipt.class);

            List<Receipt> list = criteria.add(Restrictions.eq("applicationCatagory", (ApplicationCatagory) session.load(ApplicationCatagory.class, applicationcat)))
                    .add(Restrictions.ne("receiptPrintNo", null))
                    .list();

            Receipt receipt = (Receipt) session.load(Receipt.class, idRecipt);


            int size = list.size();

            if (applicationcat == 7) {
                rn = "MCK/SL : " + (size + 1);
            } else if (applicationcat == 3) {
                rn = "MCK/BOP : " + (size + 1);
            }


            receipt.setReceiptPrintNo(rn);
            receipt.setCheack(cheque);
            receipt.setCesh(cashe);
            receipt.setReceiptStatus(1);
            Date systemDateByQuary = GetInstans.getQuater().getSystemDateByQuary();
            receipt.setReceiptDay(systemDateByQuary);

            Streetline sl = (Streetline) session.load(Streetline.class, receipt.getReceptApplicationId());
            sl.setApproveTopPayStatus(2);

            session.update(receipt);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return rn;
    }


}
