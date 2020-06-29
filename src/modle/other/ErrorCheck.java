package modle.other;

import conn.DB;

import java.sql.ResultSet;

public class ErrorCheck {

    public static void main(String[] args) {
        getPayment();

    }


    public static void getPayment() {

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_payment.idass_Payment,\n" +
                    "ass_payment.ass_Payment_Q_Number,\n" +
                    "ass_payment.ass_Payment_ThisYear,\n" +
                    "ass_payment.ass_Payment_date,\n" +
                    "ass_payment.ass_Payment_LY_Arrears,\n" +
                    "ass_payment.ass_Payment_LY_Warrant,\n" +
                    "ass_payment.ass_Payment_fullTotal,\n" +
                    "ass_payment.ass_Payment_idUser,\n" +
                    "ass_payment.Assessment_idAssessment,\n" +
                    "ass_payment.Receipt_idReceipt,\n" +
                    "ass_payment.ass_nature_idass_nature,\n" +
                    "ass_payment.ass_allocation_idass_allocation,\n" +
                    "ass_payment.ass_Payment_goto_debit,\n" +
                    "ass_payment.ass_Payment_Status,\n" +
                    "ass_payment.ass_cash,\n" +
                    "ass_payment.ass_check,\n" +
                    "ass_payment.ass_check_no,\n" +
                    "ass_payment.ass_bank,\n" +
                    "ass_payment.pay_user_id,\n" +
                    "ass_payment.cd_balance\n" +
                    "FROM\n" +
                    "ass_payment\n" +
                    "WHERE\n" +
                    "ass_payment.ass_Payment_Status = 1\n");

            while (data.next()){
                int idass_payment = data.getInt("idass_Payment");
//                System.out.println(idass_payment);
                int assessment_idAssessment = data.getInt("Assessment_idAssessment");
//                System.out.println(assessment_idAssessment);


                ResultSet data1 = DB.getData("SELECT\n" +
                        "ass_payto.idass_payto,\n" +
                        "ass_payto.ass_payto_Qno,\n" +
                        "ass_payto.ass_payto_warrant,\n" +
                        "ass_payto.ass_payto_arrears,\n" +
                        "ass_payto.ass_payto_qvalue,\n" +
                        "ass_payto.ass_payto_discount,\n" +
                        "ass_payto.ass_payto_discount_rate,\n" +
                        "ass_payto.ass_Payment_idass_Payment,\n" +
                        "ass_payto.ass_payto_status\n" +
                        "FROM\n" +
                        "ass_payto\n" +
                        "WHERE\n" +
                        "ass_payto.ass_Payment_idass_Payment = " + idass_payment);

                while (data1.next()){
                    int ass_payto_qno = data1.getInt("ass_payto_Qno");
                    double ass_payto_warrant = data1.getDouble("ass_payto_warrant");
                    double ass_payto_arrears = data1.getDouble("ass_payto_arrears");
                    System.out.println(ass_payto_arrears);
                    System.out.println(ass_payto_warrant);
                }


            }



        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
