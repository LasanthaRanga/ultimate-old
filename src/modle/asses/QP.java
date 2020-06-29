package modle.asses;

import conn.DB;
import modle.GetInstans;

import java.sql.ResultSet;
import java.util.Date;

public class QP {
    public static void main(String[] args) {

    }

    Date systemDate;
    int currentQuater;
    int currentYear;
    double count = 0;

    static String getAll = "SELECT\n" +
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

    public void loadMainData() {
        systemDate = GetInstans.getQuater().getSystemDate();
        currentQuater = GetInstans.getQuater().getCurrentQuater();
        currentYear = GetInstans.getQuater().getCurrentYear();

        String getCount = "SELECT \n" +
                "COUNT(idAssessment)\n" +
                "FROM\n" +
                "assessment\n" +
                "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                "LEFT JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                "WHERE\n" +
                "ass_allocation.ass_allocation_status = 1 AND\n" +
                "assessment.assessment_syn = 0";
        try {
            ResultSet totcount = DB.getData(getCount);
            if (totcount.last()) {
                count = totcount.getDouble("COUNT(idAssessment)");
                System.out.println(count + "   +++++++++++++++++++++++");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }


}
