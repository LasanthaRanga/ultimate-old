package modle.Payment;

import modle.StaticViews;

import java.security.cert.TrustAnchor;
import java.util.Date;

public class CompleteAcc {

    public static boolean insertToAccount(String date, String recipt_no, int recipt_id, int vort_id, int acoount_id, double amount, int user_id, int app_id, int appcatid, int incom) {

        String quary = "INSERT INTO `account_ps_three` (\n" +
                "\t`report_date`,\n" +
                "\t`report_ricipt_no`,\n" +
                "\t`report_ricipt_id`,\n" +
                "\t`report_vort_id`,\n" +
                "\t`report_account_id`,\n" +
                "\t`report_amount`,\n" +
                "\t`report_user_id`,\n" +
                "\t`report_application_id`,\n" +
                "\t`report_application_cat_id`,\n" +
                "\t`report_status`,`office_idOffice`,income_or_expence\n" +
                ")\n" +
                "VALUES\n" +
                "\t(\n" +
                "\t\t'" + date + "',\n" +
                "\t\t'" + recipt_no + "',\n" +
                "\t\t'" + recipt_id + "',\n" +
                "\t\t'" + vort_id + "',\n" +
                "\t\t'" + acoount_id + "',\n" +
                "\t\t'" + amount + "',\n" +
                "\t\t'" + user_id + "',\n" +
                "\t\t'" + app_id + "',\n" +
                "\t\t'" + appcatid + "',\n" +
                "\t\t'1','" + StaticViews.getLogUser().getOfficeIdOffice() + "', '" + incom + "' \n" +
                "\t);";
        try {
            conn.DB.setData(quary);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


}
