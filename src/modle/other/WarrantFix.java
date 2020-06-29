package modle.other;

import conn.DB;

import java.sql.ResultSet;

public class WarrantFix {

    public static void main(String[] args) {
        String qq = "SELECT\n" +
                "ass_qstart.idass_Qstart,\n" +
                "ass_qstart.ass_Qstart_QuaterNumber,\n" +
                "ass_qstart.ass_Qstart_LQ_Warrant,\n" +
                "ass_qstart.ass_Qstart_LQC_Warrant\n" +
                "FROM\n" +
                "assessment\n" +
                "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                "INNER JOIN ass_qstart ON ass_qstart.Assessment_idAssessment = assessment.idAssessment\n" +
                "WHERE\n" +
                "ass_nature.idass_nature = 9 AND\n" +
                "ass_qstart.ass_Qstart_QuaterNumber = 2";


        try {
            ResultSet data = DB.getData(qq);
            int x = 0;
            while (data.next()) {
                x++;
                int idass_qstart = data.getInt("idass_Qstart");

//                conn.DB.setData("UPDATE `ultimate2`.`ass_qstart`SET \n" +
//                        " `ass_Qstart_LQ_Warrant` = '0',\n" +
//                        " `ass_Qstart_LQC_Warrant` = '0' \n" +
//                        "WHERE\n" +
//                        "\t(`idass_Qstart` = '" + idass_qstart + "');\n");
//                System.out.println(x);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
