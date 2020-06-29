package modle.mix;

import conn.DB;
import controller.payment.UpdateStatus;
import modle.GetInstans;
import modle.StaticViews;

import java.awt.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MixPrasaThre {

    final static int VATID = 33;
    final static int NBTID = 34;
    final static int STAMPID = 35;
    final static int CASH = 65;
    final static int CHQUE = 66;
    final static int NOCASH = 145;

    public void updateMixIncomeStatus(int appid, int appcat) {
        try {
            conn.DB.setData("UPDATE mixincome\n" +
                    "SET \n" +
                    " mixincome_status = '1'\n" +
                    "WHERE\n" +
                    "\tidMixincome = '" + appid + "'");

            ResultSet data1 = DB.getData("SELECT\n" +
                    "\treceipt.idReceipt,\n" +
                    "\treceipt.receipt_print_no,\n" +
                    "\tmixincome.idMixincome,\n" +
                    "\tmixincome.mixincome_userid,\n" +
                    "\treceipt.cheack,\n" +
                    "\treceipt.cesh, " +
                    "receipt_total ,\n" +
                    "mixincome.cros_ref,\n" +
                    "mixincome.others,\n" +
                    "mixincome.mixincome_paytype,\n" +
                    "mixincome.customer_idCustomer\n" +
                    "FROM\n" +
                    "\treceipt\n" +
                    "INNER JOIN mixincome ON mixincome.idMixincome = receipt.recept_applicationId\n" +
                    "WHERE\n" +
                    "\treceipt.Application_Catagory_idApplication_Catagory =  '" + appcat + "'" +
                    "AND mixincome.idMixincome = " + appid);

            String reciptNo = null;
            int idRecipt = 0;
            int mixincome_userid = 0;
            double receipt_total = 0;

            int payType = 0;
            String cros_ref = "";

            int cusid = 0;

            double cheack = 0;
            double cesh = 0;
            if (data1.last()) {
                reciptNo = data1.getString("receipt_print_no");
                idRecipt = data1.getInt("idReceipt");
                mixincome_userid = data1.getInt("mixincome_userid");

                cheack = data1.getDouble("cheack");
                cesh = data1.getDouble("cesh");
                receipt_total = data1.getDouble("receipt_total");
                payType = data1.getInt("mixincome_paytype");
                cros_ref = data1.getString("cros_ref");
                cusid = data1.getInt("customer_idCustomer");
            }

            String quary = "SELECT\n" +
                    "mixdata.idMixdata,\n" +
                    "mixdata.md_description,\n" +
                    "mixdata.md_amount,\n" +
                    "mixdata.md_vat,\n" +
                    "mixdata.md_nbt,\n" +
                    "mixdata.md_stamp,\n" +
                    "mixdata.md_total,\n" +
                    "mixdata.mixintype_idMixintype,\n" +
                    "mixdata.mixincome_IdMixincome,\n" +
                    "mixintype.idMixintype,\n" +
                    "mixintype.mixintype_name,\n" +
                    "mixintype.account_receipt_title_idAccount_receipt_title,\n" +
                    "mixintype.mixintype_status,\n" +
                    "account_receipt_title.idAccount_receipt_title,\n" +
                    "mixintype.bankinfo_idBank\n" +
                    "FROM\n" +
                    "\tmixdata\n" +
                    "INNER JOIN mixintype ON mixdata.mixintype_idMixintype = mixintype.idMixintype\n" +
                    "INNER JOIN account_receipt_title ON mixintype.account_receipt_title_idAccount_receipt_title = account_receipt_title.idAccount_receipt_title\n" +
                    "WHERE\n" +
                    "\tmixincome_IdMixincome =" + appid;

            ResultSet data = DB.getData(quary);

            Date systemDate = GetInstans.getQuater().getSystemDate();
            String date = new SimpleDateFormat("yyyy-MM-dd").format(systemDate);
            Integer idUser = StaticViews.getLogUser().getIdUser();


            int bankinfo_idBank = 0;
            while (data.next()) {
                double md_amount = data.getDouble("md_amount");
                int idVote = data.getInt("account_receipt_title_idAccount_receipt_title");
                bankinfo_idBank = data.getInt("bankinfo_idBank");

                if (md_amount > 0) {
                    if (payType == 3) {
                        insertToCross(cros_ref, md_amount, idVote, date, cusid, idRecipt);
                        modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, idVote, bankinfo_idBank, md_amount, mixincome_userid, appid, appcat,3);
                    } else {
                        modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, idVote, bankinfo_idBank, md_amount, mixincome_userid, appid, appcat,1);
                    }
                }

                double vat = data.getDouble("md_vat");
                if (vat > 0) {
                    if (payType == 3) {
                        insertToCross(cros_ref, vat, VATID, date, cusid, idRecipt);
                        modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, VATID, bankinfo_idBank, vat, mixincome_userid, appid, appcat,3);
                    } else {
                        modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, VATID, bankinfo_idBank, vat, mixincome_userid, appid, appcat,1);
                    }
                }

                double nbt = data.getDouble("md_nbt");
                if (nbt > 0) {
                    if (payType == 3) {
                        insertToCross(cros_ref, nbt, NBTID, date, cusid, idRecipt);
                        modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, NBTID, bankinfo_idBank, nbt, mixincome_userid, appid, appcat,3);
                    } else {
                        modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, NBTID, bankinfo_idBank, nbt, mixincome_userid, appid, appcat,1);
                    }
                }

                double stamp = data.getDouble("md_stamp");
                if (stamp > 0) {
                    if (payType == 3) {
                        insertToCross(cros_ref, stamp, STAMPID, date, cusid, idRecipt);
                        modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, STAMPID, bankinfo_idBank, stamp, mixincome_userid, appid, appcat,3);
                    } else {
                        modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, STAMPID, bankinfo_idBank, stamp, mixincome_userid, appid, appcat,1);
                    }
                }
            }


            if (cesh > 0) {
                modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, CASH, bankinfo_idBank, cesh, mixincome_userid, appid, appcat,1);
                UpdateStatus.updateRecipt(idRecipt + "", 1, 0, 1, cesh); // update Recipt Status
            }


            if (cheack > 0) {
                modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, CHQUE, bankinfo_idBank, cheack, mixincome_userid, appid, appcat,1);
                UpdateStatus.updateRecipt(idRecipt + "", 2, 0, 1, cheack); // update Recipt Status
            }


            if (cheack == 0 && cesh == 0) {
                modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, NOCASH, bankinfo_idBank, receipt_total, mixincome_userid, appid, appcat,3);
                UpdateStatus.updateRecipt(idRecipt + "", 3, 0, 1, receipt_total); // update Recipt Status
            }


            conn.DB.setData("UPDATE \n" +
                    "`receipt`\n" +
                    "SET \n" +
                    " `receipt_account_id` = '" + bankinfo_idBank + "',\n" +
                    " `receipt_user_id` = '" + mixincome_userid + "'\n" +
                    "WHERE\n" +
                    "\t(`idReceipt` = '" + idRecipt + "');\n");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void insertToCross(String ref, double ex_amount, int vote, String date, int cusid, int reciptid) {
        try {
            conn.DB.setData("INSERT INTO `ex_cross` (  `ex_refno`, `ex_amount`, `ex_cross_vote_id`, `ex_income_or_expence`, `ex_active_status`,  `date`, `cus_id`, `voucher_id`, `recipt_id`, `is_cross` )\n" +
                    "VALUES\n" +
                    "\t( '" + ref + "', " + ex_amount + ", " + vote + ", 1, 1,  '" + date + "'," + cusid + ", 0, " + reciptid + ", 0 )");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


}
