/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modle.adv;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import com.sun.jmx.snmp.SnmpUnknownModelLcdException;
import conn.DB;
import modle.GetInstans;
import modle.StaticViews;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.AdvAdvertising;
import pojo.Cheack;
import pojo.Receipt;

/**
 * @author RM.LasanthaRanga@gmail.com
 */
public class Pay {

    public int payNow(int advID, double cash, double check, double total, String chNo, Date chDate, int bank) {

        Date systemDate = GetInstans.getQuater().getSystemDate();

        String genarateRisiptNo = genarateRisiptNo();

        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        int idRecipt = 0;
        try {
            pojo.ApplicationCatagory ac = (pojo.ApplicationCatagory) session.load(pojo.ApplicationCatagory.class, 1);
            AdvAdvertising Advertising = (AdvAdvertising) session.load(AdvAdvertising.class, advID);
            //Recipt ======= 
            Receipt receipt = new Receipt(ac);
            receipt.setReceptApplicationId(advID);
            receipt.setReceiptPrintNo(genarateRisiptNo);
            receipt.setCheack(check);
            receipt.setCesh(cash);
            receipt.setReceiptTotal(total);
            receipt.setReceiptStatus(0);
            receipt.setReceiptSyn(1);
            receipt.setChqueNo(chNo);
            receipt.setChqueBank(bank + "");
            receipt.setReceiptDay(systemDate);
            receipt.setChqueDate(chDate);
            Integer officeIdOffice = StaticViews.getLogUser().getOfficeIdOffice();
            int appAccountByOffice = GetInstans.getAha().getAppAccountByOffice(1, officeIdOffice);
            receipt.setOfficeIdOffice(officeIdOffice);
            pojo.User u = (pojo.User) session.load(pojo.User.class, StaticViews.getLogUser().getIdUser());
            receipt.setUser(u);
            receipt.setReceiptAccountId(appAccountByOffice);


            Serializable save = session.save(receipt);
            idRecipt = Integer.parseInt(save.toString());

            Advertising.setAdvPaidNotpaid(0);
            Advertising.setAdvCheque(check);
            Advertising.setAdvCash(cash);
            Advertising.setAdvChequeNo(chNo);
            Advertising.setAdvChequeBank(bank);
            Advertising.setAdvChequeDate(chDate);
            Advertising.setReceiptIdReceipt(receipt.getIdReceipt());
            Advertising.setUserIdUser(modle.StaticViews.getLogUser().getIdUser());
            Advertising.setOfficeId(officeIdOffice);

            if (check > 0) {
                Cheack ch = new Cheack(receipt);
                ch.setCheackToday(new Date());
                ch.setCheackNo(chNo);
                ch.setCheackDate(chDate);
                ch.setCheackStatus(1);
                ch.setCheackSyn(1);
                ch.setCheackPrice(check);
                session.save(ch);
            }

            session.update(Advertising);
            transaction.commit();
            return idRecipt;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return 0;
        } finally {
            session.close();
        }
    }

    public String genarateRisiptNo() {
        int currentYear = GetInstans.getQuater().getCurrentYear();
        String rn = "";
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "\tcount(idReceipt) as count\n" +
                    "FROM\n" +
                    "\treceipt \n" +
                    "WHERE\n" +
                    "\treceipt.Application_Catagory_idApplication_Catagory = 1 \n" +
                    "\tAND receipt.office_idOffice = 1 \n" +
                    "\tAND receipt.receipt_account_id = 1 \n" +
                    "\tAND EXTRACT( YEAR FROM receipt.receipt_day ) = 2020");
            int x = 0;
            if (data.last()) {
                x = data.getInt("count");
            }
            x++;
            rn = "MCK/ADV:"+currentYear+"/ " + (x);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return rn;
    }


    public void compleetePayment(int payid) {
        System.out.println(payid);
        try {
            conn.DB.setData("UPDATE `adv_advertising` SET   `adv_paid_notpaid`='1' WHERE (`receipt_idReceipt`='" + payid + "')");
            conn.DB.setData("UPDATE `receipt` SET  `receipt_status`='1', receipt_time='" + modle.Time.getTeime() + "' WHERE (`idReceipt`='" + payid + "')");
            ResultSet data = DB.getData("SELECT\n" +
                    "adv_advertising.idAdv_Advertising,\n" +
                    "adv_advertising.adv_total,\n" +
                    "adv_advertising.adv_vat,\n" +
                    "adv_advertising.adv_nbt,\n" +
                    "adv_advertising.adv_stamp,\n" +
                    "adv_advertising.adv_diposit,\n" +
                    "adv_advertising.adv_ground_total,\n" +
                    "adv_advertising.adv_visiting_price,\n" +
                    "adv_advertising.adv_others,\n" +
                    "adv_advertising.adv_full_total,\n" +
                    "adv_advertising.adv_cheque,\n" +
                    "adv_advertising.adv_cash,\n" +
                    "adv_advertising.receipt_idReceipt,\n" +
                    "receipt.recept_applicationId,\n" +
                    "receipt.receipt_print_no,\n" +
                    "receipt.receipt_day,\n" +
                    "receipt.idReceipt,\n" +
                    "adv_advertising.user_idUser,\n" +
                    "adv_advertising.office_id\n" +
                    "FROM\n" +
                    "adv_advertising\n" +
                    "INNER JOIN receipt ON receipt.recept_applicationId = adv_advertising.idAdv_Advertising\n" +
                    "WHERE\n" +
                    "receipt.Application_Catagory_idApplication_Catagory = 1 AND\n" +
                    "receipt.idReceipt = " + payid);
            if (data.last()) {
                double adv_total = data.getDouble("adv_total");
                double adv_vat = data.getDouble("adv_vat");
                double adv_nbt = data.getDouble("adv_nbt");
                double adv_stamp = data.getDouble("adv_stamp");
                double adv_diposit = data.getDouble("adv_diposit");
                double adv_ground_total = data.getDouble("adv_ground_total");
                double adv_visiting_price = data.getDouble("adv_visiting_price");
                double adv_others = data.getDouble("adv_others");
                double adv_full_total = data.getDouble("adv_full_total");
                double adv_cheque = data.getDouble("adv_cheque");
                double adv_cash = data.getDouble("adv_cash");
                String receipt_print_no = data.getString("receipt_print_no");
                String receipt_day = data.getString("receipt_day");
                int user_idUser = data.getInt("user_idUser");
                int idAdv_advertising = data.getInt("idAdv_Advertising");

                if (adv_total > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, payid, getVoteId("AF"), 1, adv_total, user_idUser, idAdv_advertising, 1,1);
                }
                if (adv_vat > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, payid, getVoteId("VAT"), 1, adv_vat, user_idUser, idAdv_advertising, 1,1);
                }
                if (adv_nbt > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, payid, getVoteId("NBT"), 1, adv_nbt, user_idUser, idAdv_advertising, 1,1);
                }
                if (adv_stamp > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, payid, getVoteId("STAMP"), 1, adv_stamp, user_idUser, idAdv_advertising, 1,1);
                }
                if (adv_diposit > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, payid, getVoteId("DE"), 1, adv_diposit, user_idUser, idAdv_advertising, 1,1);
                }
                if (adv_ground_total > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, payid, getVoteId("GR"), 1, adv_ground_total, user_idUser, idAdv_advertising, 1,1);
                }
                if (adv_visiting_price > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, payid, getVoteId("SV"), 1, adv_visiting_price, user_idUser, idAdv_advertising, 1,1);
                }
                if (adv_visiting_price > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, payid, getVoteId("SV"), 1, adv_visiting_price, user_idUser, idAdv_advertising, 1,1);
                }
                if (adv_others > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, payid, getVoteId("Others"), 1, adv_others, user_idUser, idAdv_advertising, 1,1);
                }
                if (adv_cheque > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, payid, getVoteId("CHQUE"), 1, adv_cheque, user_idUser, idAdv_advertising, 1,1);
                }
                if (adv_cash > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, payid, getVoteId("CASH"), 1, adv_cash, user_idUser, idAdv_advertising, 1,1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    public int getVoteId(String key) {
        int adv_vote_table_id = 0;
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "adv_vote.adv_vote_table_id\n" +
                    "FROM\n" +
                    "adv_vote\n" +
                    "WHERE\n" +
                    "adv_vote.`key` = '" + key + "'");
            if (data.last()) {
                adv_vote_table_id = data.getInt("adv_vote_table_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return adv_vote_table_id;
    }


}
