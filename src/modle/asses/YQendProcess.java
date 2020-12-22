package modle.asses;

import com.jfoenix.controls.JFXProgressBar;
import conn.DB;
import modle.GetInstans;
import modle.KeyVal;
import modle.StaticViews;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YQendProcess {

    public static void main(String[] args) {
        System.out.println("Start");
        //collectMainData();
    }

    static int currentYear;
    static int currentQuater;
    static Date systemDate;
    static String stringDate;
    static int prviasQuater;
    static double count = 0;
    static double x = 0;
    static double pro = 0;


    public static void collectMainData(JFXProgressBar progras) {

        currentYear = GetInstans.getQuater().getCurrentYear();
        currentQuater = GetInstans.getQuater().getCurrentQuater();
        systemDate = GetInstans.getQuater().getSystemDate();
        stringDate = GetInstans.getQuater().getSystemDateStringByQuary();
        prviasQuater = GetInstans.getQuater().getPrviasQuater();

        String format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());

        try {

            conn.DB.setData("INSERT INTO `ass_process`( `process_date`, `quater_number`, `user_id`, `start_time`) VALUES ('" + stringDate + "', " + currentQuater + ", " + StaticViews.getLogUser().getIdUser() + ", '" + format + "')");

            ResultSet data = DB.getData("SELECT count(assessment.idAssessment) as count FROM assessment WHERE assessment_syn = 0");
            if (data.last()) {
                count = data.getDouble("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


        if (currentQuater == 1) {   //Year End Process
            yearEndProcess(progras);
        } else { // Queter End Process

        }

        try {
            String end = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            conn.DB.setData("UPDATE `ass_process` SET `end_time`='" + end + "' WHERE process_date='" + stringDate + "' AND quater_number=" + currentQuater);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }


    public static void yearEndProcess(JFXProgressBar progras) {
        try {
            ResultSet allAssess = DB.getData("SELECT assessment.idAssessment, assessment.isWarrant FROM assessment WHERE assessment_syn = 0");
            while ((allAssess.next())) {// Get All Activ Assessment

                x = x + 1;
                pro = x / count;
                progras.setProgress(pro);

                int idAssessment = allAssess.getInt("idAssessment");
                int isWarrant = allAssess.getInt("isWarrant");


                ResultSet overs = DB.getData("SELECT\n" +
                        "sum(ass_payhistry.ass_PayHistry_Over) as over\n" +
                        "FROM\n" +
                        "ass_payhistry\n" +
                        "WHERE\n" +
                        "ass_PayHistry_year = " + (currentYear - 1) + "\n" +
                        "AND Assessment_idAssessment = " + idAssessment + "\n" +
                        "GROUP BY\n" +
                        "ass_payhistry.Assessment_idAssessment\n");
                ResultSet allo = DB.getData("SELECT\n" +
                        "ass_allocation.ass_allocation\n" +
                        "FROM\n" +
                        "ass_allocation\n" +
                        "WHERE\n" +
                        "ass_allocation.ass_allocation_status = 1 AND\n" +
                        "ass_allocation.Assessment_idAssessment = " + idAssessment);
                ResultSet natrue = DB.getData("SELECT\n" +
                        "ass_nature.ass_nature_year_rate, ass_nature_warrant_rate \n" +
                        "FROM\n" +
                        "assessment\n" +
                        "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                        "WHERE\n" +
                        "assessment.idAssessment = " + idAssessment);

                ResultSet payStatus = DB.getData("SELECT\n" +
                        "ass_payhistry.Assessment_idAssessment,\n" +
                        "ass_payhistry.ass_PayHistry_Q4Status\n" +
                        "FROM\n" +
                        "ass_payhistry\n" +
                        "WHERE\n" +
                        "ass_payhistry.Assessment_idAssessment = " + idAssessment + " AND\n" +
                        "ass_payhistry.ass_PayHistry_year = " + (currentYear - 1) + " AND\n" +
                        "ass_payhistry.ass_PayHistry_Qcunt = 4");


                //// chek status in pay history


                int q4status = 0;

                if (payStatus.next()) {
                    q4status = payStatus.getInt("ass_PayHistry_Q4Status");
                }

                double yearrate = 0;
                double warrantrate = 0;
                if (natrue.last()) {
                    yearrate = natrue.getDouble("ass_nature_year_rate");
                    warrantrate = natrue.getDouble("ass_nature_warrant_rate");
                }
                double allocation = 0;
                if (allo.last()) {
                    allocation = allo.getDouble("ass_allocation");
                }
                double quater = modle.Maths.round2((allocation * yearrate / 100) / 4);
                double over = 0;
                if (overs.last()) {
                    over = overs.getDouble("over");
                    System.out.println(idAssessment + "    ===    " + over + "    ============    " + quater);
                }

                double dis10 = (quater * 10 / 100);
                double dis5 = (quater * 5 / 100);


                if (over >= ((quater * 4) - (dis10 * 4))) {
                    System.out.println("10");


                    saveQstart(1, stringDate, idAssessment, currentYear, 0, 0, 0, 0, 0,
                            modle.Maths.round2(quater - dis10), modle.Maths.round2(dis10), modle.Maths.round2(quater - dis10), modle.Maths.round2((quater - dis10) * 4), 0, 0, "Year Start Process Q4 10%", 0, 0);
                    updateQstartOldStatus(idAssessment, currentYear - 1, 4);
                    double ov = over - ((quater * 4) - (dis10 * 4));
                    payHistry(idAssessment, 1, currentYear, stringDate, modle.Maths.round2((quater - dis10) * 4), modle.Maths.round2(quater - dis10), modle.Maths.round2(quater - dis10), modle.Maths.round2(quater - dis10), modle.Maths.round2(quater - dis10), modle.Maths.round2(ov), 10, 10, 10, 10, 1, 1, 1, 1);


                    //  }  else if (over >= (quater * 3) - (dis5 * 3)) {

                    //  System.out.println(idAssessment + " =======================================================" + quater + "  =====  3   ======   " + (dis5));
                    //  double change = over - ((quater * 3) - (dis5 * 3));

                    //  saveQstart(1, stringDate, idAssessment, currentYear, 0, 0, 0, 0, 0,
                    //          modle.Maths.round2(quater - dis5), modle.Maths.round2(dis5), modle.Maths.round2(quater - dis5), modle.Maths.round2((quater - dis5) * 3), 0, 0, "Year Start Process Q3 5%", 0, 0);
                    //  updateQstartOldStatus(idAssessment, currentYear - 1, 4);
                    //  payHistry(idAssessment, 1, currentYear, stringDate, modle.Maths.round2((quater - dis5) * 3), modle.Maths.round2(quater - dis5), modle.Maths.round2(quater - dis5), modle.Maths.round2(quater - dis5), modle.Maths.round2(change), 0, 5, 5, 5, 0, 1, 1, 1, 0);


                    // System.out.println("change :  - " + change);


                    // }  else if (over >= (quater * 2) - (dis5 * 2)) {
                    //  System.out.println(idAssessment + " =======================================================" + quater + "  =====   2  ======   " + (dis5));
                    //  double change = over - ((quater * 2) - (dis5 * 2));


                    //   saveQstart(1, stringDate, idAssessment, currentYear, 0, 0, 0, 0, 0,
                    //          modle.Maths.round2(quater - dis5), modle.Maths.round2(dis5), modle.Maths.round2(quater - dis5), modle.Maths.round2((quater - dis5) * 2), 0, 0, "Year Start Process Q2 5%", 0, 0);
                    //  updateQstartOldStatus(idAssessment, currentYear - 1, 4);
                    //  payHistry(idAssessment, 1, currentYear, stringDate, modle.Maths.round2((quater - dis5) * 2), modle.Maths.round2(quater - dis5), modle.Maths.round2(quater - dis5), modle.Maths.round2(change), 0, 0, 5, 5, 0, 0, 1, 1, 0, 0);


                    // System.out.println("change :  - " + change);
                    //  } else if (over >= (quater) - (dis5)) {
                    //   System.out.println(idAssessment + " =======================================================" + quater + "  =====   1  ======   " + (dis5));
                    //   double change = over - ((quater) - (dis5));


                    //   saveQstart(1, stringDate, idAssessment, currentYear, 0, 0, 0, 0, 0,
                    //          modle.Maths.round2(quater - dis5), modle.Maths.round2(dis5), modle.Maths.round2(quater - dis5), modle.Maths.round2((quater - dis5)), 0, 0, "Year Start Process Q1 5%", 0, 0);
                    //  updateQstartOldStatus(idAssessment, currentYear - 1, 4);
                    //  payHistry(idAssessment, 1, currentYear, stringDate, modle.Maths.round2((quater - dis5)), modle.Maths.round2(quater - dis5), modle.Maths.round2(change), 0, 0, 0, 5, 0, 0, 0, 1, 0, 0, 0);


                    // System.out.println("change :  - " + change);
                } else if (over > 0) {
                    System.out.println("====== quater one ");

                    // double change = quater - over;

                    saveQstart(1, stringDate, idAssessment, currentYear, 0, 0, 0, 0, quater,
                            0, 0, 0, 0, 0, over, "Collect Over Payment", 0, 0);

                    updateQstartOldStatus(idAssessment, currentYear - 1, 4);
                    payHistry(idAssessment, 1, currentYear, stringDate, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);


                } else {// Arriars

                    ResultSet q1data = DB.getData("SELECT\n" +
                            "ass_qstart.idass_Qstart,\n" +
                            "ass_qstart.ass_Qstart_QuaterNumber,\n" +
                            "ass_qstart.ass_Qstart_process_date,\n" +
                            "ass_qstart.ass_Qstart_LYC_Arreas,\n" +
                            "ass_qstart.ass_Qstart_LYC_Warrant,\n" +
                            "ass_qstart.ass_Qstart_LQC_Arreas,\n" +
                            "ass_qstart.ass_Qstart_LQC_Warrant,\n" +
                            "ass_qstart.ass_Qstart_HaveToQPay,\n" +
                            "ass_qstart.ass_Qstart_QPay,\n" +
                            "ass_qstart.ass_Qstart_QDiscont,\n" +
                            "ass_qstart.ass_Qstart_QTot,\n" +
                            "ass_qstart.ass_Qstart_FullTotal,\n" +
                            "ass_qstart.ass_Qstart_status,\n" +
                            "ass_qstart.Assessment_idAssessment,\n" +
                            "ass_qstart.ass_Qstart_year,\n" +
                            "ass_qstart.process_update_warant,\n" +
                            "ass_qstart.process_update_arrears,\n" +
                            "ass_qstart.process_update_comment,\n" +
                            "ass_qstart.ass_Qstart_tyold_arrias,\n" +
                            "ass_qstart.ass_Qstart_tyold_warant\n" +
                            "FROM\n" +
                            "ass_qstart\n" +
                            "WHERE\n" +
                            "ass_qstart.ass_Qstart_year = " + (currentYear - 1) + " AND \n" +
                            "ass_qstart.Assessment_idAssessment =" + idAssessment);

                    double arriars = 0;
                    double warrant = 0;
                    double havetopay = 0;
                    double lya = 0;
                    double lyw = 0;
                    int idQstart = 0;
                    while (q1data.next()) {
                        int qn = q1data.getInt("ass_Qstart_QuaterNumber");
                        if (qn == 2) {
                            arriars += q1data.getDouble("ass_Qstart_LQC_Arreas");
                            warrant += q1data.getDouble("ass_Qstart_LQC_Warrant");
                        }
                        if (qn == 3) {
                            arriars += q1data.getDouble("ass_Qstart_LQC_Arreas");
                            warrant += q1data.getDouble("ass_Qstart_LQC_Warrant");
                        }
                        if (qn == 4) {
                            arriars += q1data.getDouble("ass_Qstart_LQC_Arreas");
                            warrant += q1data.getDouble("ass_Qstart_LQC_Warrant");
                            if (q4status == 0) {
                                havetopay = q1data.getDouble("ass_Qstart_HaveToQPay");
                            }
                            lya = q1data.getDouble("ass_Qstart_LYC_Arreas");
                            lyw = q1data.getDouble("ass_Qstart_LYC_Warrant");
                            idQstart = q1data.getInt("idass_Qstart");
                        }
                    }

                    double q4warrant = 0;

                    if (q4status == 0 && havetopay > 10) {
                        q4warrant = modle.Maths.round2(quater * warrantrate / 100);
                    }

                    arriars += havetopay;


                    String val = KeyVal.getVal("Min_Arriars_For_Warrant");
                    double minValue = Double.parseDouble(val);

                    if (arriars < minValue) {
                        q4warrant = 0;
                    }

                    if (isWarrant == 0) {
                        q4warrant = 0;
                    }

                    System.out.println(q4warrant);

                    warrant += q4warrant;

                    saveQstart(1, stringDate, idAssessment, currentYear, modle.Maths.round2(arriars + lya), modle.Maths.round2(warrant + lyw), modle.Maths.round2(havetopay), modle.Maths.round2(q4warrant), quater, 0, 0, 0, 0, 0, 0, "Year Start Process", modle.Maths.round2(arriars), modle.Maths.round2(warrant));
                    updateQstartOldStatus(idQstart);
                    payHistry(idAssessment, 1, currentYear, stringDate, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                }
            }// Get All Activ Assessment


            String end = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            conn.DB.setData("UPDATE `ass_process` SET `end_time`='" + end + "' WHERE process_date='" + stringDate + "' AND quater_number=" + currentQuater);


            progras.setProgress(0.0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }


    public static void collectArriars(int idAssessment) {

        try {


            if (currentQuater == 1) {


            } else if (currentQuater == 2) {

            } else if (currentQuater == 3) {

            } else if (currentQuater == 4) {

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


    public static void makeWorrant(int idAssessment) {

        String getQstart = "SELECT\n"
                + "assessment.idAssessment,\n"
                + "ass_qstart.idass_Qstart,\n"
                + "ass_qstart.ass_Qstart_QuaterNumber,\n"
                + "ass_qstart.ass_Qstart_process_date,\n"
                + "ass_qstart.ass_Qstart_LY_Arreas,\n"
                + "ass_qstart.ass_Qstart_LY_Warrant,\n"
                + "ass_qstart.ass_Qstart_LYC_Arreas,\n"
                + "ass_qstart.ass_Qstart_LYC_Warrant,\n"
                + "ass_qstart.ass_Qstart_LQ_Arreas,\n"
                + "ass_qstart.ass_Qstart_LQC_Arreas,\n"
                + "ass_qstart.ass_Qstart_LQ_Warrant,\n"
                + "ass_qstart.ass_Qstart_LQC_Warrant,\n"
                + "ass_qstart.ass_Qstart_HaveToQPay,\n"
                + "ass_qstart.ass_Qstart_QPay,\n"
                + "ass_qstart.ass_Qstart_QDiscont,\n"
                + "ass_qstart.ass_Qstart_QTot,\n"
                + "ass_qstart.ass_Qstart_FullTotal,\n"
                + "ass_qstart.ass_Qstart_status,\n"
                + "ass_qstart.Assessment_idAssessment,\n"
                + "ass_qstart.ass_Qstart_year\n"
                + "FROM\n"
                + "ass_qstart\n"
                + "INNER JOIN assessment ON ass_qstart.Assessment_idAssessment = assessment.idAssessment\n"
                + "WHERE\n"
                + "assessment.idAssessment = '" + idAssessment + "' AND\n"
                + "ass_qstart.ass_Qstart_QuaterNumber = '" + prviasQuater + "' AND\n"
                + "ass_qstart.ass_Qstart_status = 1 AND\n ";
        if (prviasQuater == 1) {
            getQstart += " ass_qstart.ass_Qstart_year = " + (currentYear - 1);
        } else {
            getQstart += " ass_qstart.ass_Qstart_year = " + currentYear;
        }


    }

    public static void saveQstart(
            int qno,
            String pdate,
            int idAssessment, int year,
            double lya, double lyw,
            double lqa, double lqw,
            double hvpay, double pay,
            double discount, double qtot, double fulltot,
            double upwarant, double uparriars,
            String com,
            double qendtotA, double qendtotW) {

        String q = "INSERT INTO `ass_qstart` (\n" +
                "\t`ass_Qstart_QuaterNumber`,\n" +
                "\t`ass_Qstart_process_date`,\n" +
                "\t`ass_Qstart_LY_Arreas`,\n" +
                "\t`ass_Qstart_LY_Warrant`,\n" +
                "\t`ass_Qstart_LYC_Arreas`,\n" +
                "\t`ass_Qstart_LYC_Warrant`,\n" +
                "\t`ass_Qstart_LQ_Arreas`,\n" +
                "\t`ass_Qstart_LQC_Arreas`,\n" +
                "\t`ass_Qstart_LQ_Warrant`,\n" +
                "\t`ass_Qstart_LQC_Warrant`,\n" +
                "\t`ass_Qstart_HaveToQPay`,\n" +
                "\t`ass_Qstart_QPay`,\n" +
                "\t`ass_Qstart_QDiscont`,\n" +
                "\t`ass_Qstart_QTot`,\n" +
                "\t`ass_Qstart_FullTotal`,\n" +
                "\t`ass_Qstart_status`,\n" +
                "\t`Assessment_idAssessment`,\n" +
                "\t`ass_Qstart_year`,\n" +
                "\t`process_update_warant`,\n" +
                "\t`process_update_arrears`,\n" +
                "\t`process_update_comment`,\n" +
                "\t`ass_Qstart_tyold_arrias`,\n" +
                "\t`ass_Qstart_tyold_warant` \n" +
                ")\n" +
                "VALUES\n" +
                "\t(\n"
                + qno + ",\n " +
                "'" + pdate + "',\n" +
                "\t\t" + lya + ",\n" +
                "\t\t" + lyw + ",\n" +
                "\t\t" + lya + ",\n" +
                "\t\t" + lyw + ",\n" +
                "\t\t" + lqa + ",\n" +
                "\t\t" + lqa + ",\n" +
                "\t\t" + lqw + ",\n" +
                "\t\t" + lqw + ",\n" +
                "\t\t" + hvpay + ",\n" +
                "\t\t" + pay + ",\n" +
                "\t\t" + discount + ",\n" +
                "\t\t" + qtot + ",\n" +
                "\t\t" + fulltot + ",\n" +
                "\t\t1,\n" +
                "\t\t" + idAssessment + ",\n" +
                "\t\t" + year + ",\n" +
                "\t\tNULL,\n" +
                "\t\t" + uparriars + ",\n" +
                "\t\t'" + com + "',\n" +
                "\t\t" + qendtotA + ",\n" +
                "\t\t" + qendtotW + " \n" +
                "\t)";

        try {
            conn.DB.setData(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateQstartOldStatus(int idQustart) {
        try {
            conn.DB.setData("UPDATE `ass_qstart`\n" +
                    "SET `ass_Qstart_status`=0 WHERE `idass_Qstart`= " + idQustart);

        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static void updateQstartOldStatus(int idAssessment, int year, int qno) {
        try {
            conn.DB.setData("UPDATE ass_qstart \n" +
                    "SET ass_Qstart_status = 0 \n" +
                    "WHERE\n" +
                    "\tAssessment_idAssessment = " + idAssessment + " \n" +
                    "\tAND ass_Qstart_year = '" + year + "' \n" +
                    "\tAND ass_Qstart_QuaterNumber = '" + qno + "'");

        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static void updateQstartLQCAW(double qcw, double qca, int qsid, int qno) {

        try {
            String comment = "Credit" + qno;
            conn.DB.setData("UPDATE `ass_qstart`\n" +
                    "SET `ass_Qstart_LQC_Arreas` = '" + qca + "',\n" +
                    " `ass_Qstart_LQC_Warrant` = '" + qcw + "',\n" +
                    " `process_update_warant` = '" + qcw + "',\n" +
                    " `process_update_arrears` = '" + qca + "',\n" +
                    "`process_update_comment` = '" + comment + "'\n" +
                    "WHERE\n" +
                    "\t(`idass_Qstart` = '" + qsid + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void saveNewPayHistory(int idAssessment, int qn, int curentYear, String day, double tot,
                                         double q1p, double q2p, double q3p, double q4p, double over,
                                         double q1dr, double q2dr, double q3dr, double q4dr,
                                         int q1s, int q2s, int q3s, int q4s) {

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
                "\t\t'22',\n" +
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void payHistry(int idAssessment, int qn, int curentYear, String day, double tot,
                                 double q1p, double q2p, double q3p, double q4p, double over,
                                 double q1dr, double q2dr, double q3dr, double q4dr,
                                 int q1s, int q2s, int q3s, int q4s) {
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
                "\t\t'22',\n" +
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
