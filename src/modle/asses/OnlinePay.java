package modle.asses;

import conn.DB;
import controller.assess.OnlineApprove;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;

public class OnlinePay {


    public void startProcess(ObservableList<OnlineApprove.Online> list) {
        int size = list.size();
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            OnlineApprove.Online online = list.get(i);
            if (online.getSelect().isSelected()) {
                completePayment(online.getOnpayid());
            }
            System.gc();
        }
    }


    public void completePayment(int onlineid) {

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "online_pay.idOnPaid,\n" +
                    "online_pay.oncus_id,\n" +
                    "online_pay.appcat_id,\n" +
                    "online_pay.app_id,\n" +
                    "online_pay.date,\n" +
                    "online_pay.amount,\n" +
                    "online_pay.`status`,\n" +
                    "online_pay.description,\n" +
                    "online_pay.other,\n" +
                    "online_pay.other2\n" +
                    "FROM\n" +
                    "online_pay\n" +
                    "WHERE\n" +
                    "online_pay.idOnPaid = " + onlineid);

            if (data.last()) {
                int appcat_id = data.getInt("appcat_id");
                int app_id = data.getInt("app_id");
                double amount = data.getDouble("amount");
                Date date = data.getDate("date");
                if (appcat_id == 2) {//  Assessment Process
                    AssessmentPayProcess(app_id, date, amount, onlineid);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


    public void AssessmentPayProcess(int appid, Date date, double amount, int onpay) {
        System.out.println("-------------------------");
        System.out.println(appid);
        System.out.println(date);
        System.out.println(amount);
        System.out.println("==========================");

        OnlineAssessPayProcess oapp = new OnlineAssessPayProcess();

        oapp.cal(appid, date);
        oapp.WarrantArrirasCal(amount);
        int pay = oapp.pay(onpay);
        if (pay > 0) {
            oapp.dayEnd(pay);
        }

        System.out.println(pay);


    }


}
