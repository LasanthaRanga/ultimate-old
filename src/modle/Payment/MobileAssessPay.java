package modle.Payment;

import conn.DB;

import java.sql.ResultSet;
import java.util.Date;

import javafx.scene.text.Text;
import modle.asses.MobileAssessPayProcess;
import modle.asses.OnlineAssessPayProcess;

/**
 * Created by Ranga Rathnayake on 2020-10-08.
 */
public class MobileAssessPay {


    public void showInfor(String nTotId, Text tot, Text des1, Text des2) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "mobile_tot.idMobTot,\n" +
                    "mobile_tot.user_id,\n" +
                    "mobile_tot.total,\n" +
                    "mobile_tot.date_time,\n" +
                    "mobile_tot.`no`,\n" +
                    "mobile_tot.`status`,\n" +
                    "mobile_tot.`comment`,\n" +
                    "mobile_tot.appcat,\n" +
                    "mobile_tot.oder,\n" +
                    "mobile_tot.payType,\n" +
                    "mobile_tot.chequeno,\n" +
                    "mobile_tot.recipt_date,\n" +
                    "`user`.user_full_name,\n" +
                    "`user`.user_nic\n" +
                    "FROM\n" +
                    "mobile_tot\n" +
                    "INNER JOIN `user` ON `user`.idUser = mobile_tot.user_id\n" +
                    "WHERE\n" +
                    "mobile_tot.idMobTot = " + nTotId);

            if (data.last()) {
                double total = data.getDouble("total");
                String user_full_name = data.getString("user_full_name");
                tot.setText(total + "");
                des1.setText(user_full_name);
                des2.setText("Please wait until the process complete");
            } else {
                tot.setText(00 + "");
                des1.setText("Not Found Bill");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void getData(String mTotId) {
        System.out.println(mTotId);

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "mobile_tot.idMobTot,\n" +
                    "mobile_tot.user_id,\n" +
                    "mobile_tot.total,\n" +
                    "mobile_tot.date_time,\n" +
                    "mobile_tot.`no`,\n" +
                    "mobile_tot.`status`,\n" +
                    "mobile_tot.`comment`,\n" +
                    "mobile_tot.appcat,\n" +
                    "mobile_tot.oder,\n" +
                    "mobile_tot.payType,\n" +
                    "mobile_tot.chequeno,\n" +
                    "mobile_tot.recipt_date,\n" +
                    "mobile_pay.idMobilePay,\n" +
                    "mobile_pay.app_cat,\n" +
                    "mobile_pay.app_id,\n" +
                    "mobile_pay.user_id,\n" +
                    "mobile_pay.collect_time,\n" +
                    "mobile_pay.amount,\n" +
                    "mobile_pay.pay_type,\n" +
                    "mobile_pay.cheque_no,\n" +
                    "mobile_pay.bank_id,\n" +
                    "mobile_pay.oder,\n" +
                    "mobile_pay.mobile_recipt_no,\n" +
                    "mobile_pay.cus_id,\n" +
                    "mobile_pay.cus_email,\n" +
                    "mobile_pay.cus_mobile,\n" +
                    "mobile_pay.`status` as st ,\n" +
                    "mobile_pay.status_time,\n" +
                    "mobile_pay.recipt_id,\n" +
                    "mobile_pay.recipt_no,\n" +
                    "mobile_pay.mob_tot_id\n" +
                    "FROM\n" +
                    "mobile_tot\n" +
                    "INNER JOIN mobile_pay ON mobile_tot.idMobTot = mobile_pay.mob_tot_id\n" +
                    "WHERE\n" +
                    "mobile_tot.idMobTot = " + mTotId);

            while (data.next()) {
                int ass_id = data.getInt("app_id");
                double amount = data.getDouble("amount");
                Date recipt_date = data.getDate("recipt_date");
                if (data.getInt("st") == 1) {
                    System.out.println(ass_id + "   -   " + amount);
                    MobileAssessPayProcess mapp = new MobileAssessPayProcess();
                    mapp.cal(ass_id, recipt_date);
                    mapp.WarrantArrirasCal(amount);
                    int pay = mapp.pay(Integer.parseInt(mTotId),ass_id, amount, 0);
                    if (pay > 0) {
                        mapp.dayEnd(pay);
                    }
                    System.out.println(pay);
                }
                System.gc();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
