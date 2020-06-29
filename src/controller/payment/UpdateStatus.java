package controller.payment;

public class UpdateStatus {


    //============================================================================================ Update Recipt Type

    public static void updateRecipt(String idRecipt, int paytype, int cusid, int cros, double amount) {
//        ------------------
//        pay_type
//        cash 1
//        check 2
//        online 3
//        croce 4
//        ------------------
//        --------------------
//            cross status
//            1 recipt
//            2 voucher
//            3 cross
//        --------------------

        try {
            if (cusid > 0) {
                conn.DB.setData("UPDATE `receipt` \n" +
                        "SET \n" +
                        "`income_expense` = 1,\n" +
                        "`cus_id` = " + cusid + " ,\n" +
                        "`cross_recipt_or_voucher` = " + cros + " ,\n" +
                        "`pay_type` = " + paytype + " ,\n" +
                        "`amount` = " + amount + " \n" +
                        "WHERE\n" +
                        "\t`idReceipt` = " + idRecipt);
            } else {
                conn.DB.setData("UPDATE `receipt` \n" +
                        "SET \n" +
                        "`income_expense` = 1,\n" +
                        "`cross_recipt_or_voucher` = " + cros + " ,\n" +
                        "`pay_type` = " + paytype + " ,\n" +
                        "`amount` = " + amount + " \n" +
                        "WHERE\n" +
                        "\t`idReceipt` = " + idRecipt);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


//=============================================================================================


}
