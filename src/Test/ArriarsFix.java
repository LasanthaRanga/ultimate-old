package Test;

import conn.DB;

import java.sql.ResultSet;

public class ArriarsFix {


    public static void main(String[] args) {

        String qq = "SELECT\n" +
                "ass_qstart.idass_Qstart,\n" +
                "ass_qstart.ass_Qstart_tyold_arrias,\n" +
                "ass_qstart.Assessment_idAssessment\n" +
                "FROM\n" +
                "ass_qstart\n" +
                "WHERE\n" +
                "ass_qstart.Assessment_idAssessment IN ((\n" +
                "\n" +
                "SELECT\n" +
                "ass_payhistry.Assessment_idAssessment\n" +
                "FROM\n" +
                "ass_payhistry\n" +
                "WHERE\n" +
                "ass_payhistry.ass_PayHistry_Q1Status = 1 AND\n" +
                "ass_payhistry.ass_PayHistry_Q2Status = 1 AND\n" +
                "ass_payhistry.ass_PayHistry_Q3Status = 1 AND\n" +
                "ass_payhistry.ass_PayHistry_Q4Status = 1 AND\n" +
                "ass_payhistry.ass_PayHistry_Qcunt = 4\n" +
                "\n" +
                "\n" +
                ")) AND\n" +
                "ass_qstart.ass_Qstart_QuaterNumber = 4 AND\n" +
                "ass_qstart.ass_Qstart_year = 2019 AND\n" +
                "ass_qstart.ass_Qstart_tyold_arrias > 0\n";


        try {

            ResultSet data = DB.getData(qq);

            while (data.next()) {
                int idAssessment = data.getInt("Assessment_idAssessment");
                int idass_qstart = data.getInt("idass_Qstart");

                double ass_qstart_tyold_arrias = data.getDouble("ass_Qstart_tyold_arrias");
                if (ass_qstart_tyold_arrias > 0) {

                    ResultSet data1 = DB.getData("SELECT\n" +
                            "ass_qstart.idass_Qstart,\n" +
                            "ass_qstart.ass_Qstart_LQC_Arreas\n" +
                            "FROM\n" +
                            "ass_qstart\n" +
                            "WHERE\n" +
                            "ass_qstart.Assessment_idAssessment = " + idAssessment);

                    while (data1.next()) {
                        int idass_qstart1 = data1.getInt("idass_Qstart");
                        double ass_qstart_lqc_arreas = data1.getDouble("ass_Qstart_LQC_Arreas");
                        if (ass_qstart_lqc_arreas == ass_qstart_tyold_arrias) {
                            conn.DB.setData("UPDATE `ass_qstart` \n" +
                                    "SET `ass_Qstart_LQC_Arreas` = 0 \n" +
                                    "WHERE\n" +
                                    "\t`idass_Qstart` = " + idass_qstart1);
                        }

                    }

                    conn.DB.setData("UPDATE `ass_qstart` \n" +
                            "SET `ass_Qstart_tyold_arrias` = 0.00\n" +
                            "WHERE\n" +
                            "\t`idass_Qstart` = " + idass_qstart);


                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
