package modle.asses;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga on 2019-01-23.
 */
public class ErrorCheack {

    public static void main(String[] args) {

        try {
            System.out.println("OK");
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
                    "ass_payment.pay_user_id\n" +
                    "FROM\n" +
                    "ass_payment  WHERE\n" +
                    "ass_payment.ass_Payment_date = '2019-01-11'  ");

            while (data.next()) {
                double ass_cash = data.getDouble("ass_cash");
                double ass_check = data.getDouble("ass_check");
                double ass_payment_goto_debit = data.getDouble("ass_Payment_goto_debit");
                double ass_payment_fullTotal = data.getDouble("ass_Payment_fullTotal");




                if (ass_cash + ass_check == ass_payment_fullTotal + ass_payment_goto_debit) {
                } else {
                    System.out.println(data.getInt("Receipt_idReceipt"));
                    System.out.println("---------------------");
                    System.out.println("Ca " + ass_cash);
                    System.out.println("qq " + ass_check);
                    System.out.println("ov " + ass_payment_goto_debit);

                    System.out.println("ekathuwa" + (ass_cash + ass_check ));
                    System.out.println("Full Tot" + ass_payment_fullTotal + ass_payment_goto_debit);
                    System.out.println("=====================");
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
