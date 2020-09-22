package modle.Payment;

import conn.DB;
import conn.NewHibernateUtil;
import modle.GetInstans;
import org.apache.tools.ant.taskdefs.Get;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ranga on 2019-03-06.
 */
public class PaymentByID {

    public static String genarateRisiptNo(int applicationcat, String ricipt, int idApp, double cheque, double cashe, String date, String chno, String bank, int account) {
        String rn = "";

        System.out.println("awa");

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "MAX(receipt.oder)\n" +
                    "FROM `receipt`\n" +
                    "WHERE\n" +
                    "receipt.Application_Catagory_idApplication_Catagory = '" + applicationcat + "' AND\n" +
                    "receipt.office_idOffice = " + modle.StaticViews.getLogUser().getOfficeIdOffice());


            ResultSet data3 = DB.getData("SELECT\n" +
                    "receipt_code_create.receipt_code\n" +
                    "FROM `receipt_code_create`\n" +
                    "WHERE\n" +
                    "receipt_code_create.application_id = '" + applicationcat + "' AND\n" +
                    "receipt_code_create.receipt_code_office_id = " + modle.StaticViews.getLogUser().getOfficeIdOffice());

            String receipt_code = "";
            if (data3.last()) {
                receipt_code = data3.getString("receipt_code");
                System.out.println(receipt_code + "  code");
            }

            if (applicationcat != 9) {
                if (account == 0) {
                    ResultSet data2 = DB.getData("SELECT\n" +
                            "aha.idAHA,\n" +
                            "aha.bankinfo_id\n" +
                            "FROM\n" +
                            "\t`AHA`\n" +
                            "WHERE\n" +
                            "aha.aha_status = 1\n" +
                            "AND aha.appcat_id = '" + applicationcat + "'\n" +
                            "AND aha.office_id = '" + modle.StaticViews.getLogUser().getOfficeIdOffice() + "'");


                    if (data2.last()) {
                        account = data2.getInt("bankinfo_id");
                        System.out.println("account");
                    }
                }
            }


            if (data.last()) {
                int anInt = data.getInt("MAX(receipt.oder)");
                anInt++;
                rn = receipt_code + anInt;
                System.out.println(rn);

                Date systemDateByQuary = GetInstans.getQuater().getSystemDateByQuary();
                String sysdate = new SimpleDateFormat("yyyy-MM-dd").format(systemDateByQuary);

                System.out.println("Max");

                String qu;
                if (cheque == 0) {

                    qu = "UPDATE `receipt`\n" +
                            "SET `Application_Catagory_idApplication_Catagory` = '" + applicationcat + "',\n" +
                            " `receipt_print_no` = '" + rn + "',\n" +
                            " `cheack` = '" + cheque + "',\n" +
                            " `cesh` = '" + cashe + "',\n" +
                            " `receipt_day` = '" + sysdate + "',\n" +
                            " `receipt_status` = '1',\n" +
                            " `chque_no` = '',\n" +
                            " `chque_date` = NULL,\n" +
                            " `chque_bank` = '',\n" +
                            " `oder` = '" + anInt + "',\n" +
                            " `office_idOffice` = '" + modle.StaticViews.getLogUser().getOfficeIdOffice() + "',\n" +
                            " `receipt_account_id` = '" + account + "' \n" +

                            "WHERE\n" +
                            "\tApplication_Catagory_idApplication_Catagory = '" + applicationcat + "'\n" +
                            "AND recept_applicationId = '" + idApp + "'";


                } else {
                    qu = "UPDATE `receipt`\n" +
                            "SET `Application_Catagory_idApplication_Catagory` = '" + applicationcat + "',\n" +
                            " `receipt_print_no` = '" + rn + "',\n" +
                            " `cheack` = '" + cheque + "',\n" +
                            " `cesh` = '" + cashe + "',\n" +
                            " `receipt_day` = '" + sysdate + "',\n" +
                            " `receipt_status` = '1',\n" +
                            " `chque_no` = '" + chno + "',\n" +
                            " `chque_date` = '" + date + "',\n" +
                            " `chque_bank` = '" + bank + "',\n" +
                            " `oder` = '" + anInt + "',\n" +
                            " `office_idOffice` = '" + modle.StaticViews.getLogUser().getOfficeIdOffice() + "',\n" +
                            " `receipt_account_id` = '" + account + "' \n" +

                            "WHERE\n" +
                            "\tApplication_Catagory_idApplication_Catagory = '" + applicationcat + "'\n" +
                            "AND recept_applicationId = '" + idApp + "'";


                }

                int i = DB.setData(qu);
                System.out.println(i);
                if (i > 0) {
                    Session session = NewHibernateUtil.getSessionFactory().openSession();
                    session.beginTransaction().commit();
                    session.close();
                    modle.StaticBadu.setAppid(idApp);

                } else {
                    System.out.println("i wedak ne");
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return rn;
    }

    static int x;

    public static String checkDuplicate(String rn, int x, String risipt) {
        String rnn = rn;
        try {
            ResultSet dup = DB.getData("SELECT\n" +
                    "receipt.idReceipt,\n" +
                    "receipt.receipt_print_no\n" +
                    "FROM `receipt`\n" +
                    "WHERE\n" +
                    "receipt.receipt_print_no = '" + rn + "'");
            if (dup.last()) {
                rnn = risipt + x + 1;
                return rnn;
            } else {
                return rnn;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return rn;
        }

    }


    public String genarateRisitNoByOfficeAndOder(int officeid, int idRecipt, int appcat, int accountId) {
        String no = "";
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "receipt_code_create.receipt_code\n" +
                    "FROM `receipt_code_create`\n" +
                    "WHERE\n" +
                    "receipt_code_create.application_id = '" + appcat + "' AND\n" +
                    "receipt_code_create.receipt_code_office_id = " + officeid);

            int bankinfo_id = accountId;

            if (bankinfo_id == 0) {
                ResultSet data2 = DB.getData("SELECT\n" +
                        "aha.idAHA,\n" +
                        "aha.bankinfo_id\n" +
                        "FROM\n" +
                        "\t`AHA`\n" +
                        "WHERE\n" +
                        "aha.aha_status = 1\n" +
                        "AND aha.appcat_id = '" + appcat + "'\n" +
                        "AND aha.office_id = '" + officeid + "'");


                if (data2.last()) {
                    bankinfo_id = data2.getInt("bankinfo_id");
                }
            }


            int xx = 0;
            ResultSet data1 = DB.getData("SELECT\n" +
                    "MAX(receipt.oder)\n" +
                    "FROM `receipt`\n" +
                    "WHERE\n" +
                    "receipt.Application_Catagory_idApplication_Catagory = 2 AND\n" +
                    "receipt.office_idOffice = " + officeid);
            if (data1.last()) {
                xx = data1.getInt("MAX(receipt.oder)");
                xx++;
            }

            if (data.last()) {
                String receipt_code = data.getString("receipt_code");
                no += receipt_code;
            }

            no += "/ " + xx;
            conn.DB.setData("UPDATE `receipt`\n" +
                    "SET \n" +
                    " `receipt_print_no` = '" + no + "', \n" +
                    " `oder` = '" + xx + "',\n" +
                    " `office_idOffice` = '" + officeid + "',\n" +
                    " `receipt_account_id` = '" + bankinfo_id + "',\n" +
                    " `receipt_user_id` = '" + modle.StaticViews.getLogUser().getIdUser() + "'\n" +
                    "WHERE\n" +
                    "\t(`idReceipt` = '" + idRecipt + "')");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


        return no;
    }


    public static boolean genarateRisiptNo(int applicationcat, String ricipt, int idApp) {  //nonvesting


        boolean status = false;
        try {

            int receipt_account_id = 0;
            ResultSet acc = DB.getData("SELECT\n" +
                    "receipt.receipt_account_id\n" +
                    "FROM\n" +
                    "receipt\n" +
                    "Where Application_Catagory_idApplication_Catagory = '" + applicationcat + "' \n" +
                    "AND recept_applicationId = " + idApp);

            if (acc.last()) {
                receipt_account_id = acc.getInt("receipt_account_id");
            }


            int currentYear = GetInstans.getQuater().getCurrentYear();

            // By Account
            ResultSet data = DB.getData("SELECT\n" +
                    "\tMax(receipt.oder)\t\n" +
                    "FROM\n" +
                    "\treceipt \n" +
                    "WHERE\n" +
                    "\treceipt.Application_Catagory_idApplication_Catagory = '" + applicationcat + "' \n" +
                    "\tAND receipt.office_idOffice = '" + modle.StaticViews.getLogUser().getOfficeIdOffice() + "' \n" +
                    "\tAND receipt.receipt_account_id = '" + receipt_account_id + "' AND" +
                    " EXTRACT(YEAR FROM receipt.receipt_day)= " + currentYear);


            ResultSet data3 = DB.getData("SELECT\n" +
                    "\treceipt_code_create.receipt_code \n" +
                    "FROM\n" +
                    "\treceipt_code_create \n" +
                    "WHERE\n" +
                    "\treceipt_code_create.application_id = '" + applicationcat + "' \n" +
                    "\tAND receipt_code_create.receipt_code_office_id = '" + modle.StaticViews.getLogUser().getOfficeIdOffice() + "' \n" +
                    "\tAND receipt_code_create.account_id = '" + receipt_account_id + "'");

            String receipt_code = "";

            System.out.println("==================================");
            System.out.println("app cat " + applicationcat);
            System.out.println("office  " + modle.StaticViews.getLogUser().getOfficeIdOffice() );
            System.out.println("acoount " + receipt_account_id);
            System.out.println("==================================");

            if (data3.last()) {
                receipt_code = data3.getString("receipt_code");
            }

            String rn = "";
            int anInt = 1;
            if (data.last()) {
                anInt = data.getInt("MAX(receipt.oder)");
                anInt++;
            }

            rn = receipt_code + currentYear + "/ " + anInt;
            String qu = "UPDATE `receipt`\n" +
                    "SET `Application_Catagory_idApplication_Catagory` = '" + applicationcat + "',\n" +
                    " `receipt_print_no` = '" + rn + "',\n" +
                    " `receipt_status` = '1',\n" +
                    " `oder` = '" + anInt + "',\n" +
                    " `office_idOffice` = '" + modle.StaticViews.getLogUser().getOfficeIdOffice() + "', receipt_time='" + modle.Time.getTeime() + "' \n" +
                    "WHERE\n" +
                    "\tApplication_Catagory_idApplication_Catagory = '" + applicationcat + "'\n" +
                    "AND recept_applicationId = '" + idApp + "'";
            conn.DB.setData(qu);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return status;
    }


}
