package modle.asses;

import conn.DB;
import modle.GetInstans;

import java.sql.ResultSet;

public class Janawari10Discount {


    public static void getOverPay() {
        try {
            Quater qqq = GetInstans.getQuater();
            int currentYear = qqq.getCurrentYear();

            ResultSet data = DB.getData("SELECT\n" +
                    "ass_qstart.process_update_arrears,\n" +
                    "ass_qstart.process_update_warant,\n" +
                    "ass_qstart.Assessment_idAssessment,\n" +
                    "ass_qstart.ass_Qstart_HaveToQPay,\n" +
                    "ass_qstart.idass_Qstart\n" +
                    "FROM\n" +
                    "ass_qstart\n" +
                    "WHERE\n" +
                    "ass_qstart.ass_Qstart_year = 2020 AND\n" +
                    "ass_qstart.ass_Qstart_QuaterNumber = 1 AND\n" +
                    "ass_qstart.process_update_arrears > 0");

            while (data.next()) {
                int idAssessment = data.getInt("Assessment_idAssessment");
                int idass_qstart = data.getInt("idass_Qstart");
                double over = data.getDouble("process_update_arrears");
                double ass_qstart_haveToQPay = data.getDouble("ass_Qstart_HaveToQPay");

                ResultSet quater = DB.getData("SELECT\n" +
                        "assessment.idAssessment,\n" +
                        "ass_allocation.ass_allocation,\n" +
                        "ass_nature.ass_nature_name,\n" +
                        "ass_nature.ass_nature_year_rate,\n" +
                        "ass_allocation.ass_allocation* ass_nature.ass_nature_year_rate /400 AS quater\n" +
                        "FROM\n" +
                        "assessment\n" +
                        "INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                        "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                        "WHERE\n" +
                        "ass_allocation.ass_allocation_status = 1 AND\n" +
                        "assessment.idAssessment = " + idAssessment);
                if (quater.last()) {
                    double qq = quater.getDouble("quater");
                    double dis5 = qq * 5 / 100;
                    double dis10 = qq * 10 / 100;
                    double q10 = qq - dis10;
                    double q5 = qq - dis5;

                    if (over >= q10 * 4) {
                        double bal = over - q10 * 4;
                        payHistry(idAssessment, 1, currentYear, currentYear + "-01-31", over, q10, q10, q10, q10, bal, 10, 10, 10, 10, 1, 1, 1, 1, idass_qstart, 0);
                        System.out.println("============= 3");

                    } else if (over >= q5 * 3) {
                        double bal = over - q5 * 3;
                        payHistry(idAssessment, 1, currentYear, currentYear + "-01-31", over, q5, q5, q5, bal, 0, 5, 5, 5, 0, 1, 1, 1, 0, idass_qstart, 0);
                        System.out.println("============= 3");
                    } else if (over >= q5 * 2) {
                        double bal = over - q5 * 2;
                        payHistry(idAssessment, 1, currentYear, currentYear + "-01-31", over, q5, q5, bal, 0, 0, 5, 5, 0, 0, 1, 1, 0, 0, idass_qstart, 0);
                        System.out.println("==== 2");
                    } else if (over >= q5) {
                        double bal = over - q5;
                        payHistry(idAssessment, 1, currentYear, currentYear + "-01-31", over, q5, bal, 0, 0, 0, 5, 0, 0, 0, 1, 0, 0, 0, idass_qstart, 0);
                        System.out.println("==== 1");
                    } else if (over > 0) {
                        double bal = over;
                        double v = modle.Round.round(ass_qstart_haveToQPay - bal);
                        payHistry(idAssessment, 1, currentYear, currentYear + "-01-31", over, bal, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, idass_qstart, v);
                        System.out.println("==== 0");


                    }


                    double o = -1 * (over);

                    conn.DB.setData("UPDATE `ass_qstart` \n" +
                            "SET \n" +
                            "`process_update_warant` = '" + o + "',\n" +
                            "`process_update_arrears` = '" + 0 + "'\n" +
                            "WHERE\n" +
                            "\t`idass_Qstart` = " + idass_qstart);
                }


                System.out.println(over);


            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    public static void payHistry(int idAssessment, int qn, int curentYear, String day, double tot,
                                 double q1p, double q2p, double q3p, double q4p, double over,
                                 double q1dr, double q2dr, double q3dr, double q4dr,
                                 int q1s, int q2s, int q3s, int q4s, int idqstart, double havetopay) {
        String qq = "INSERT INTO `ass_payhistry` (\n" +
                "\t`ass_PayHistry_Qcunt`,\n" +
                "\t`ass_PayHistry_year`,\n" +
                "\t`ass_PayHistry_Date`,\n" +
                "\t`ass_PayHistry_status`,\n" +
                "\t`ass_PayHistry_comment`,\n" +
                "\t`ass_PayHistry_TotalPayid`,\n" +
                "\t`ass_PayHistry_Q1`,\n" +
                "\t`ass_PayHistry_Q2`,\n" +
                "\t`ass_PayHistry_Q3`,\n" +
                "\t`ass_PayHistry_Q4`,\n" +
                "\t`ass_PayHistry_Over`,\n" +
                "\t`Assessment_idAssessment`,\n" +
                "\t`ass_PayHistry_DRQ1`,\n" +
                "\t`ass_PayHistry_DRQ2`,\n" +
                "\t`ass_PayHistry_DRQ3`,\n" +
                "\t`ass_PayHistry_DRQ4`,\n" +
                "\t`ass_PayHistry_Q1Status`,\n" +
                "\t`ass_PayHistry_Q2Status`,\n" +
                "\t`ass_PayHistry_Q3Status`,\n" +
                "\t`ass_PayHistry_Q4Status`\n" +
                ")\n" +
                "VALUES\n" +
                "\t(\n" +
                "\t\t'" + qn + "',\n" +
                "\t\t'" + curentYear + "',\n" +
                "\t\t'" + day + "',\n" +
                "\t\t'1',\n" +
                "\t\t'131',\n" +
                "\t\t'" + tot + "',\n" +
                "\t\t'" + q1p + "',\n" +
                "\t\t'" + q2p + "',\n" +
                "\t\t'" + q3p + "',\n" +
                "\t\t'" + q4p + "',\n" +
                "\t\t'" + over + "',\n" +
                "\t\t'" + idAssessment + "',\n" +
                "\t\t'" + q1dr + "',\n" +
                "\t\t'" + q2dr + "',\n" +
                "\t\t'" + q3dr + "',\n" +
                "\t\t'" + q4dr + "',\n" +
                "\t\t'" + q1s + "',\n" +
                "\t\t'" + q2s + "',\n" +
                "\t\t'" + q3s + "',\n" +
                "\t\t'" + q4s + "'\n" +
                "\t)\n" +
                "\n";


        try {
            conn.DB.setData(qq);
            conn.DB.setData("UPDATE `ass_qstart` SET `ass_Qstart_HaveToQPay`=" + havetopay + " WHERE `idass_Qstart`=" + idqstart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
