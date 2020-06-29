package modle.server;

import conn.DB;
import modle.CheckConnection;
import modle.asses.DayEndProcess;

import java.sql.Date;
import java.sql.ResultSet;

public class DE {

    public void runDE() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {

                        searchDayEndList();
                        Thread.sleep(60000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                    }
                }
            }
        }).start();
    }


    public void searchDayEndList() {
        String query = "SELECT\n" +
                "de.idde,\n" +
                "de.receipt_id,\n" +
                "de.appcat_id,\n" +
                "de.user_id,\n" +
                "de.staus,\n" +
                "de.dayendtime,\n" +
                "de.`comment`,\n" +
                "de.receipt_date\n" +
                "FROM `de`\n" +
                "WHERE\n" +
                "de.staus = 0";
        try {
            ResultSet data = DB.getData(query);
            while (data.next()) {
                int idde = data.getInt("idde");
                int receipt_id = data.getInt("receipt_id");
                int appcat_id = data.getInt("appcat_id");
                Date receipt_date = data.getDate("receipt_date");

                if (appcat_id == 2) { //Assessment Text Day End
                    DayEndProcess de = new modle.asses.DayEndProcess();
                    if (de.dayEndProcessForOne(receipt_id, receipt_date)) {
                        conn.DB.setData("UPDATE `de`\n" +
                                "SET \n" +
                                " `staus` = '1' \n" +
                                "WHERE\n" +
                                "\t(`idde` = '" + idde + "')");
                    }
                }

                if (appcat_id == 13) {//Ri Bill

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


    public void AssessmentDayEnd() {

    }


}
