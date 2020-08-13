package modle.asses;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-08-12.
 */
public class CurrentArrears {


    public static void main(String[] args) {
        double currentArrears = getCurrentArrears(200, 2020);
        System.out.println(currentArrears);

    }

    public static double getCurrentArrears(int idAss, int date) {
        System.out.println("asdf");
        double tot = 0;
        try {

            ResultSet data = DB.getData("SELECT\n" +
                    "\tass_qstart.idass_Qstart,\n" +
                    "\tass_qstart.ass_Qstart_QuaterNumber,\n" +
                    "\tass_qstart.ass_Qstart_process_date,\n" +
                    "\tass_qstart.ass_Qstart_LYC_Arreas,\n" +
                    "\tass_qstart.ass_Qstart_LYC_Warrant,\n" +
                    "\tass_qstart.ass_Qstart_LQC_Arreas,\n" +
                    "\tass_qstart.ass_Qstart_LQC_Warrant,\n" +
                    "\tass_qstart.ass_Qstart_HaveToQPay,\n" +
                    "\tass_qstart.ass_Qstart_status,\n" +
                    "\tass_qstart.Assessment_idAssessment,\n" +
                    "\tass_qstart.ass_Qstart_year \n" +
                    "FROM\n" +
                    "\tass_qstart \n" +
                    "WHERE\n" +
                    "\tass_qstart.Assessment_idAssessment = '" + idAss + "' \n" +
                    "\tAND ass_qstart.ass_Qstart_year = '" + date + "'");

            double lya = 0;
            double lyw = 0;
            double tya = 0;
            double tyw = 0;
            double qpay = 0;

            while (data.next()) {
                int ass_qstart_status = data.getInt("ass_Qstart_status");
                int ass_qstart_quaterNumber = data.getInt("ass_Qstart_QuaterNumber");
                double ass_qstart_lyc_arreas = data.getDouble("ass_Qstart_LYC_Arreas");
                double ass_qstart_lyc_warrant = data.getDouble("ass_Qstart_LYC_Warrant");
                double ass_qstart_haveToQPay = data.getDouble("ass_Qstart_HaveToQPay");
                double ass_qstart_lqc_arreas = data.getDouble("ass_Qstart_LQC_Arreas");
                double ass_qstart_lqc_warrant = data.getDouble("ass_Qstart_LQC_Warrant");

                if (ass_qstart_status == 1) {
                    if (ass_qstart_quaterNumber != 1) {
                        tya += ass_qstart_lqc_arreas;
                        tyw += ass_qstart_lqc_warrant;
                    }
                    lya = ass_qstart_lyc_arreas;
                    lyw = ass_qstart_lyc_warrant;
                    qpay = ass_qstart_haveToQPay;

                } else {
                    if (ass_qstart_quaterNumber != 1) {
                        tya += ass_qstart_lqc_arreas;
                        tyw += ass_qstart_lqc_warrant;
                    }
                }

            }
            tot = lyw + lya + tya + tyw;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tot;
    }


}
