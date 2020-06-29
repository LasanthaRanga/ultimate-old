package modle.asses;

import com.jfoenix.controls.JFXProgressBar;
import conn.DB;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.text.Text;
import modle.BUP;

/**
 *
 * @author Ranga Rathnayake
 */
public class QuaterStart {

    //Get All Data
    String getAll = "SELECT\n" +
            "assessment.idAssessment,\n" +
            "assessment.Customer_idCustomer,\n" +
            "assessment.Ward_idWard,\n" +
            "assessment.Street_idStreet,\n" +
            "assessment.ass_nature_idass_nature,\n" +
            "assessment.ass_discription_idass_discription,\n" +
            "assessment.User_idUser,\n" +
            "assessment.assessment_oder,\n" +
            "assessment.assessment_no,\n" +
            "assessment.assessment_status,\n" +
            "assessment.assessment_syn,\n" +
            "assessment.assessment_comment,\n" +
            "assessment.assessment_obsolete,\n" +
            "ass_nature.idass_nature,\n" +
            "ass_nature.ass_nature_name,\n" +
            "ass_nature.ass_nature_year_rate,\n" +
            "ass_nature.ass_nature_status,\n" +
            "ass_nature.ass_nature_warrant_rate,\n" +
            "ass_allocation.idass_allocation,\n" +
            "ass_allocation.ass_allocation,\n" +
            "ass_allocation.ass_allocation_status,\n" +
            "ass_allocation.Assessment_idAssessment,\n" +
            "ass_allocation.ass_allocation_change_date,\n" +
            "ass_allocation.ass_allocation_discription,\n" +
            "ass_allocation.ass_allocation_idUser\n" +
            "FROM\n" +
            "assessment\n" +
            "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
            "LEFT JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
            "WHERE\n" +
            "ass_allocation.ass_allocation_status = 1 AND\n" +
            "assessment.assessment_syn = 0\n";

    String getCount = "SELECT \n" +
            "COUNT(idAssessment)\n" +
            "FROM\n" +
            "assessment\n" +
            "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
            "LEFT JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
            "WHERE\n" +
            "ass_allocation.ass_allocation_status = 1 AND\n" +
            "assessment.assessment_syn = 0";

    double count = 0;
    double x = 0;
    int qs = 0;
    int ph = 0;

    int idAssessment = 0;
    double allocation = 0;
    double warantRate = 0;
    double yarrate = 0;
    double quatervalue = 0;

    //Qstart
    double idQstart = 0;
    double qslqa = 0;
    double qslqca = 0;
    double qslqw = 0;
    double qslqcw = 0;
    double qsHtoPay = 0;
    double qslya = 0;
    double qslyca = 0;
    double qslyw = 0;
    double qslycw = 0;

    //payHistry
    double idPayHistry = 0;
    double phOver = 0;  //over payment eka
    int phqcount = 0;

    //Insert
    double lYA = 0;
    double lQA = 0;
    double lYW = 0;
    double lQW = 0;
    double over = 0;
    double t4qp = 0;

    int q4status = 0;

    public void clearVariable() {
        idAssessment = 0;
        allocation = 0;
        warantRate = 0;
        yarrate = 0;
        quatervalue = 0;

        //Qstart
        idQstart = 0;
        qslqa = 0;
        qslqca = 0;
        qslqw = 0;
        qslqcw = 0;
        qsHtoPay = 0;
        qslya = 0;
        qslyca = 0;
        qslyw = 0;
        qslycw = 0;

        //payHistry
        idPayHistry = 0;
        phOver = 0;
        phqcount = 0;

        //Insert
        lYA = 0;
        lQA = 0;
        lYW = 0;
        lQW = 0;
        t4qp = 0;
        over = 0;
        q4status = 0;
    }

    int currentYear;
    int currentMonth;
    int currentQuater;
    int priviasQuater;

    public void calculateNewQuaterValue() {
        quatervalue = allocation * yarrate / 100 / 4;

    }

    public void calculateWarrant() {

        if (phqcount == 3) {
            double warant = qsHtoPay * warantRate / 100; // Have to pay eken warant hedenne me sere witharai nethnama quater value eken hedenna one
            if (priviasQuater == 4) {
                lYW = qslycw + warant;
                lQW = warant;
            } else {
                lYW = qslycw;
                lQW = warant;
            }
        } else {

        }
    }

    public boolean backup(String text) {
        BUP bup = new modle.BUP();
        return bup.backupDataWithDatabase(text);
    }

    public void getAll(JFXProgressBar progras, Text txt_qStartText) {

        txt_qStartText.setText(" Start Process ");
        txt_qStartText.setText(" Start Backup Befor Process ");

//        if (backup("Befor_QStart_process")) {
//            txt_qStartText.setText(" Backup Process Complete ");
//        } else {
//            txt_qStartText.setText(" Backup Process Not Complete ");
//        }

        Date systemDate = modle.GetInstans.getQuater().getSystemDate();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(systemDate);
        currentYear = modle.GetInstans.getQuater().getCurrentYear();
        currentMonth = modle.GetInstans.getQuater().getCurrentMonth();
        currentQuater = modle.GetInstans.getQuater().getCurrentQuater();

        if (currentQuater == 1) {
            priviasQuater = 4;
        } else {
            priviasQuater = currentQuater - 1;
        }

        try {
            ResultSet ad = DB.getData(getAll);
            ResultSet countData = DB.getData(getCount);
            if (countData.last()) {
                count = countData.getDouble("COUNT(idAssessment)");
            }
            while (ad.next()) {
                clearVariable();
                x = x + 1;
             
                double pro = x / count;
                progras.setProgress(pro);

                idAssessment = ad.getInt("idAssessment");
                allocation = ad.getDouble("ass_allocation");
                warantRate = ad.getDouble("ass_nature_warrant_rate");
                yarrate = ad.getDouble("ass_nature_year_rate");
                quatervalue = allocation * yarrate / 100 / 4;
                quatervalue = modle.Maths.round2(quatervalue);

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
                        + "ass_qstart.ass_Qstart_QuaterNumber = 4 AND\n"
                        + "ass_qstart.ass_Qstart_status = 1 AND\n"
                        + "ass_qstart.ass_Qstart_year = 2018";

                String payHistryQuary = "SELECT\n"
                        + "assessment.idAssessment,\n"
                        + "ass_payhistry.idass_PayHistry,\n"
                        + "ass_payhistry.ass_PayHistry_Qcunt,\n"
                        + "ass_payhistry.ass_PayHistry_year,\n"
                        + "ass_payhistry.ass_PayHistry_Date,\n"
                        + "ass_payhistry.ass_PayHistry_status,\n"
                        + "ass_payhistry.ass_PayHistry_comment,\n"
                        + "ass_payhistry.ass_PayHistry_TotalPayid,\n"
                        + "ass_payhistry.ass_PayHistry_Q1,\n"
                        + "ass_payhistry.ass_PayHistry_Q2,\n"
                        + "ass_payhistry.ass_PayHistry_Q3,\n"
                        + "ass_payhistry.ass_PayHistry_Q4,\n"
                        + "ass_payhistry.ass_PayHistry_Over,\n"
                        + "ass_payhistry.Assessment_idAssessment,\n"
                        + "ass_payhistry.ass_PayHistry_DRQ1,\n"
                        + "ass_payhistry.ass_PayHistry_DRQ2,\n"
                        + "ass_payhistry.ass_PayHistry_DRQ3,\n"
                        + "ass_payhistry.ass_PayHistry_DRQ4,\n"
                        + "ass_payhistry.ass_PayHistry_Q1Status,\n"
                        + "ass_payhistry.ass_PayHistry_Q2Status,\n"
                        + "ass_payhistry.ass_PayHistry_Q3Status,\n"
                        + "ass_payhistry.ass_PayHistry_Q4Status\n"
                        + "FROM\n"
                        + "assessment\n"
                        + "INNER JOIN ass_payhistry ON ass_payhistry.Assessment_idAssessment = assessment.idAssessment\n"
                        + "WHERE\n"
                        + "assessment.idAssessment = '" + idAssessment + "' AND\n"
                        + "ass_payhistry.ass_PayHistry_year = 2018 AND\n"
                        + "ass_payhistry.ass_PayHistry_status = 1";

                ResultSet data = DB.getData(getQstart);
//                System.out.println("========================");
                if (data.first()) {
                    qs++;
                    idQstart = data.getInt("idass_Qstart");
                    qsHtoPay = data.getDouble("ass_Qstart_HaveToQPay");
                    qslqa = data.getDouble("ass_Qstart_LQ_Arreas");
                    qslqca = data.getDouble("ass_Qstart_LQC_Arreas");
                    qslqw = data.getDouble("ass_Qstart_LQ_Warrant");
                    qslqcw = data.getDouble("ass_Qstart_LQC_Warrant");
                    qslya = data.getDouble("ass_Qstart_LY_Arreas");
                    qslyca = data.getDouble("ass_Qstart_LYC_Arreas");
                }
                ResultSet phdata = DB.getData(payHistryQuary);
                if (phdata.first()) {
                    ph++;
                    idPayHistry = phdata.getInt("idass_PayHistry");
                    phOver = phdata.getDouble("ass_PayHistry_Over");
                    phqcount = phdata.getInt("ass_PayHistry_Qcunt");
                }

                //     calculateArrears();
                calculateNewQuaterValue();

                if (phqcount == 4) {
                    if (phOver >= qsHtoPay) {
                        over = phOver - qsHtoPay;
                    } else {
                        over = 0;
                    }

                    lYA = 0;
                    lQA = 0;
                    lQW = 0;
                    lQW = 0;
                    lYW = 0;
                } else {
                    lYA = qslqca + qsHtoPay;
                    lQA = qsHtoPay;
                    lQW = qsHtoPay * warantRate / 100;
                    lQW = round2(lQW);
                    lYW = qslqcw + lQW;
                    over = 0;
                }

                //  calculateWarrant();
                String QstartNewQuary = "INSERT INTO `ass_qstart` (	\n"
                        + "	`ass_Qstart_QuaterNumber`,\n"
                        + "	`ass_Qstart_process_date`,\n"
                        + "	`ass_Qstart_LY_Arreas`,\n"
                        + "	`ass_Qstart_LY_Warrant`,\n"
                        + "	`ass_Qstart_LYC_Arreas`,\n"
                        + "	`ass_Qstart_LYC_Warrant`,\n"
                        + "	`ass_Qstart_LQ_Arreas`,\n"
                        + "	`ass_Qstart_LQC_Arreas`,\n"
                        + "	`ass_Qstart_LQ_Warrant`,\n"
                        + "	`ass_Qstart_LQC_Warrant`,\n"
                        + "	`ass_Qstart_HaveToQPay`,\n"
                        + "	`ass_Qstart_QPay`,\n"
                        + "	`ass_Qstart_QDiscont`,\n"
                        + "	`ass_Qstart_QTot`,\n"
                        + "	`ass_Qstart_FullTotal`,\n"
                        + "	`ass_Qstart_status`,\n"
                        + "	`Assessment_idAssessment`,\n"
                        + "	`ass_Qstart_year`\n"
                        + ")\n"
                        + "VALUES\n"
                        + "	(\n"
                        + "		'" + currentQuater + "',\n"
                        + "		'" + today + "',\n"
                        + "		'" + modle.Maths.round2(lYA) + "',\n"
                        + "		'" + modle.Maths.round2(lYW) + "',\n"
                        + "		'" + modle.Maths.round2(lYA) + "',\n"
                        + "		'" + modle.Maths.round2(lYW) + "',\n"
                        + "		'" + modle.Maths.round2(lQA) + "',\n"
                        + "		'" + modle.Maths.round2(lQA) + "',\n"
                        + "		'" + modle.Maths.round2(lQW) + "',\n"
                        + "		'" + modle.Maths.round2(lQW) + "',\n"
                        + "		'" + modle.Maths.round2(quatervalue) + "',\n"
                        + "		'0',\n"
                        + "		'0',\n"
                        + "		'0',\n"
                        + "		'0',\n"
                        + "		'1',\n"
                        + "		'" + idAssessment + "',\n"
                        + "		'2019'\n"
                        + "	);";

                DB.setData(QstartNewQuary);

                String updateQstart = "UPDATE `ass_qstart`\n"
                        + "SET \n"
                        + "`ass_Qstart_QPay` = '" + t4qp + "',\n" // me sere witharai mek danne meka danna be nethnam
                        + "`ass_Qstart_status` = '0'\n"
                        + "\n"
                        + "WHERE\n"
                        + "	(`idass_Qstart` = '" + idQstart + "');";

                DB.setData(updateQstart);

                String phQuary = "INSERT INTO `ass_payhistry` (	\n"
                        + "	`ass_PayHistry_Qcunt`,\n"
                        + "	`ass_PayHistry_year`,\n"
                        + "	`ass_PayHistry_Date`,\n"
                        + "	`ass_PayHistry_status`,\n"
                        + "	`ass_PayHistry_comment`,\n"
                        + "	`ass_PayHistry_TotalPayid`,\n"
                        + "	`ass_PayHistry_Q1`,\n"
                        + "	`ass_PayHistry_Q2`,\n"
                        + "	`ass_PayHistry_Q3`,\n"
                        + "	`ass_PayHistry_Q4`,\n"
                        + "	`ass_PayHistry_Over`,\n"
                        + "	`Assessment_idAssessment`,\n"
                        + "	`ass_PayHistry_DRQ1`,\n"
                        + "	`ass_PayHistry_DRQ2`,\n"
                        + "	`ass_PayHistry_DRQ3`,\n"
                        + "	`ass_PayHistry_DRQ4`,\n"
                        + "	`ass_PayHistry_Q1Status`,\n"
                        + "	`ass_PayHistry_Q2Status`,\n"
                        + "	`ass_PayHistry_Q3Status`,\n"
                        + "	`ass_PayHistry_Q4Status`\n"
                        + ")\n"
                        + "VALUES\n"
                        + "	(	\n"
                        + "		'" + currentQuater + "',\n"
                        + "		'" + currentYear + "',\n"
                        + "		'" + today + "',\n"
                        + "		'1',\n"
                        + "		'0',\n"
                        + "		'" + modle.Maths.round2(over) + "',\n"// total Paid meka hadanna one
                        + "		'" + modle.Maths.round2(over) + "',\n"
                        + "		'0',\n"
                        + "		'0',\n"
                        + "		'0',\n"
                        + "		'0',\n"
                        + "		'" + idAssessment + "',\n"
                        + "		'0',\n" // discount Rate
                        + "		'0',\n"
                        + "		'0',\n"
                        + "		'0',\n"
                        + "		'0',\n"// Status me tikath hadanna one
                        + "		'0',\n"
                        + "		'0',\n"
                        + "		'0'\n"
                        + "	);";

                String updatePayHistry = "UPDATE `ass_payhistry`\n"
                        + "SET \n"
                        + " `ass_PayHistry_status` = '0' \n"
                        + "WHERE\n"
                        + "	(`idass_PayHistry` = '" + idAssessment + "')";

                DB.setData(phQuary);
                DB.setData(updatePayHistry);

                String creditQuary = "INSERT INTO `ass_creditdebit` (	\n"
                        + "	`Ass_CreditDebit_discription`,\n"
                        + "	`Ass_CreditDebit_cd`,\n"
                        + "	`Ass_CreditDebit_amount`,\n"
                        + "	`Ass_balance`,\n"
                        + "	`Ass_CreditDebit_date`,\n"
                        + "	`Assessment_idAssessment`,\n"
                        + "	`Ass_CreditDebit_status`\n"
                        + ")\n"
                        + "VALUES\n"
                        + "	(	\n"
                        + "		'Quater Start Process',\n"
                        + "		'1',\n"
                        + "		'0',\n"
                        + "		'0',\n"
                        + "		'2018-10-01',\n"
                        + "		'" + idAssessment + " ',\n"
                        + "		'1'\n"
                        + "	)";

                String credtUpdate = "UPDATE `ass_creditdebit`\n"
                        + "SET \n"
                        + " `Ass_CreditDebit_discription` = 'Old Data To New Software',\n"
                        + " `Ass_CreditDebit_cd` = '1',\n"
                        + " `Ass_CreditDebit_amount` = '0',\n"
                        + " `Ass_balance` = '0',\n"
                        + " `Ass_CreditDebit_date` = '2018-10-01',\n"
                        + " `Assessment_idAssessment` = '" + idAssessment + "',\n"
                        + " `Ass_CreditDebit_status` = '1'\n"
                        + "WHERE\n"
                        + "	(`idAss_CreditDebit` = '')";

                System.out.println(x);

            }
            System.out.println(" FINISH ");
            System.out.println("QS = " + qs);
            System.out.println("ph = " + ph);
            System.out.println("X = " + x);
        } catch (Exception e) {
            e.printStackTrace();
            modle.ErrorLog.writeLog(e.getMessage(), "QuaterStart", "getAll", "modle.asess");
        }

        txt_qStartText.setText(" Start Backup After Process Please Waite ");

        if (backup("After_QStart_pocess")) {
            txt_qStartText.setText(" After Process Complete");
        } else {
            txt_qStartText.setText(" Backup Process Not Complete");
        }
        txt_qStartText.setText(" Full Process Completed");

    }

    public static double round2(double d) {
        return Math.round(d * 100.0) / 100.0;
    }

    //Select Process Types
    // Start New Quater
    //Update Old Quater 
}
