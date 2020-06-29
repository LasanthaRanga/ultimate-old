package modle.other;

import conn.DB;

import java.sql.ResultSet;

public class HavetopayFix {

    public static void main(String[] args) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_allocation.ass_allocation,\n" +
                    "ass_qstart.ass_Qstart_HaveToQPay,\n" +
                    "ass_payment.ass_Payment_fullTotal,\n" +
                    "ass_payment.ass_Payment_goto_debit,\n" +
                    "ass_payment.ass_Payment_date,\n" +
                    "ass_payment.ass_Payment_Q_Number,\n" +
                    "ass_qstart.idass_Qstart,\n" +
                    "ass_qstart.Assessment_idAssessment\n" +
                    "FROM\n" +
                    "ass_qstart\n" +
                    "LEFT JOIN ass_allocation ON ass_qstart.Assessment_idAssessment = ass_allocation.Assessment_idAssessment\n" +
                    "LEFT JOIN ass_payment ON ass_payment.ass_allocation_idass_allocation = ass_allocation.idass_allocation\n" +
                    "WHERE\n" +
                    "ass_qstart.ass_Qstart_HaveToQPay = 0 AND\n" +
                    "ass_qstart.ass_Qstart_QuaterNumber = 2 AND\n" +
                    "ass_qstart.ass_Qstart_QPay = 0 AND\n" +
                    "ass_payment.idass_Payment IS NULL AND\n" +
                    "ass_allocation.ass_allocation > 0");

            double qv;
            while (data.next()) {
            int idQs = 0;
                qv = 0;
                int assessment_idAssessment = data.getInt("Assessment_idAssessment");
                idQs = data.getInt("idass_Qstart");
                System.out.println(assessment_idAssessment);
                ResultSet data1 = DB.getData("SELECT\n" +
                        "assessment.idAssessment,\n" +
                        "ROUND(ass_allocation.ass_allocation * ass_nature.ass_nature_year_rate /100 /4,2) as qv\n" +
                        "FROM\n" +
                        "assessment\n" +
                        "LEFT JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                        "LEFT JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                        "WHERE\n" +
                        "assessment.idAssessment = '" + assessment_idAssessment + "'");

                if (data1.last()) {
                    qv = data1.getDouble("qv");
                }
                System.out.println(qv);

                conn.DB.setData("UPDATE `ass_qstart`\n" +
                        "SET `ass_Qstart_HaveToQPay` = '"+qv+"'\n" +
                        "WHERE\n" +
                        "\t`idass_Qstart` = '"+idQs+"';");


            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
