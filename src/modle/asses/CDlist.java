package modle.asses;

import conn.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.time.YearMonth;

public class CDlist {
//    public static void main(String[] args) {
//        YearMonth yearMonthObject = YearMonth.of(2000, 2);
//        int daysInMonth = yearMonthObject.lengthOfMonth();
//        System.out.println(daysInMonth);
//    }


    public ObservableList<CDholder> loadTable(String from, String to) {

        ObservableList<CDholder> cDholders = FXCollections.observableArrayList();
        cDholders.clear();

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_creditdebit.idAss_CreditDebit,\n" +
                    "assessment.idAssessment,\n" +
                    "ass_creditdebit.Ass_CreditDebit_cd,\n" +
                    "ass_creditdebit.Ass_CreditDebit_amount,\n" +
                    "ward.ward_no,\n" +
                    "street.street_name,\n" +
                    "assessment.assessment_no,\n" +
                    "ass_creditdebit.Ass_CreditDebit_discription,\n" +
                    "`user`.user_full_name,\n" +
                    "ass_creditdebit.Ass_CreditDebit_date\n" +
                    "FROM\n" +
                    "ass_creditdebit\n" +
                    "INNER JOIN assessment ON ass_creditdebit.Assessment_idAssessment = assessment.idAssessment\n" +
                    "INNER JOIN ward ON assessment.Ward_idWard = ward.idWard\n" +
                    "INNER JOIN street ON street.Ward_idWard = ward.idWard AND assessment.Street_idStreet = street.idStreet\n" +
                    "LEFT JOIN `user` ON `user`.idUser = ass_creditdebit.user_id\n" +
                    "WHERE\n" +
                    "ass_creditdebit.Ass_CreditDebit_date BETWEEN '" + from + "' AND '" + to + "'\n" +
                    "ORDER BY\n" +
                    "ass_creditdebit.idAss_CreditDebit ASC");

            while (data.next()) {

                double credit = 0;
                double debit = 0;

                int ass_creditDebit_cd = data.getInt("Ass_CreditDebit_cd");
                if (ass_creditDebit_cd > 0) {
                    debit = data.getDouble("Ass_CreditDebit_amount");
                } else {
                    credit = data.getDouble("Ass_CreditDebit_amount");
                }

                cDholders.add(new CDholder(
                        data.getInt("idAss_CreditDebit"),
                        data.getInt("idAssessment"),
                        data.getString("Ass_CreditDebit_date"),
                        data.getString("user_full_name"),
                        data.getString("ward_no"),
                        data.getString("street_name"),
                        data.getString("assessment_no"),
                        credit, debit,
                        data.getString("Ass_CreditDebit_discription"),
                        "", 0
                ));
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return cDholders;

    }


}
