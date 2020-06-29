package Test;

import conn.DB;

import java.sql.ResultSet;

public class ArriasReportFix {

    public static void main(String[] args) {


        try {

            ResultSet data1 = DB.getData("SELECT\n" +
                    "assessment.idAssessment\n" +
                    "FROM\n" +
                    "assessment");

            while (data1.next()) {

                int idAssessment = data1.getInt("idAssessment");

                ResultSet data = DB.getData("SELECT\n" +
                        "ass_qstart.idass_Qstart,\n" +
                        "ass_qstart.ass_Qstart_LQC_Arreas,\n" +
                        "ass_qstart.ass_Qstart_LQC_Warrant,\n" +
                        "ass_qstart.Assessment_idAssessment\n" +
                        "FROM\n" +
                        "ass_qstart\n" +
                        "WHERE\n" +
                        "ass_qstart.ass_Qstart_QuaterNumber <> 1 AND\n" +
                        "ass_qstart.ass_Qstart_year = 2019 AND\n" +
                        "ass_qstart.Assessment_idAssessment = " + idAssessment);

                double arrias = 0;
                double warrant = 0;
                int qsid = 0;
                while (data.next()) {
                    warrant += data.getDouble("ass_Qstart_LQC_Warrant");
                    arrias += data.getDouble("ass_Qstart_LQC_Arreas");
                    qsid = data.getInt("idass_Qstart");
                }

                arrias = modle.Round.round(arrias);
                warrant = modle.Round.round(warrant);

                DB.setData("UPDATE `ass_qstart` \n" +
                        "SET\n" +
                        "`ass_Qstart_tyold_arrias` = " + arrias + ",\n" +
                        "`ass_Qstart_tyold_warant` = " + warrant + " \n" +
                        "WHERE\n" +
                        "\t`idass_Qstart` = " + qsid);
                System.out.println(idAssessment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


}
