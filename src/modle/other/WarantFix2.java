package modle.other;

import conn.DB;

import java.sql.ResultSet;

public class WarantFix2 {


    public static void main(String[] args) {
        getData();
    }


    public static void getData() {
        String q1 =
                "SELECT\n" +
                "warantcheck1.idass_Qstart\n" +
                "FROM\n" +
                "warantcheck1\n" +
                "INNER JOIN ass_qstart ON ass_qstart.Assessment_idAssessment = warantcheck1.Assessment_idAssessment\n" +
                "WHERE\n" +
                "ass_qstart.ass_Qstart_QuaterNumber = 1";

        try {

            ResultSet data = DB.getData(q1);
            while (data.next()){
                String idass_qstart = data.getString("idass_Qstart");
                System.out.println(idass_qstart);

                String q2 = "UPDATE `ass_qstart`\n" +
                        "SET \n" +
                        " `ass_Qstart_LQ_Arreas` = '0',\n" +
                        " `ass_Qstart_LQC_Arreas` = '0',\n" +
                        " `ass_Qstart_LQ_Warrant` = '0',\n" +
                        " `ass_Qstart_LQC_Warrant` = '0',\n" +
                        " `ass_Qstart_HaveToQPay` = '0'\n" +
                        "WHERE\n" +
                        "\t(`idass_Qstart` = '"+idass_qstart+"')";

                conn.DB.setData(q2);


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
