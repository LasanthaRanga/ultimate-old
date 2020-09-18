package modle.Payment;

import conn.DB;
import modle.StaticViews;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ranga Rathnayake on 2020-09-18.
 */
public class CancleRecipt {
    public static boolean cancleRecipt(int idRecipt, String reson) {
        Date date = new Date();
        String format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        Integer idUser = StaticViews.getLogUser().getIdUser();
        boolean b = false;
        try {
            int i = DB.setData("UPDATE `receipt` \n" +
                    "SET `receipt_status` = 2,\n" +
                    "`cancle_user` = " + idUser + ",\n" +
                    "`cancle_dt` = '" + format + "',\n" +
                    "`cancle_reson` = '" + reson + "' \n" +
                    "WHERE\n" +
                    "\t`idReceipt` = " + idRecipt);
            b = true;
            return b;
        } catch (Exception e) {
            e.printStackTrace();
            return b;
        }
    }
}
