package modle;

import conn.DB;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
    public static String getTeime() {
        String time = null;
        try {
            ResultSet data = DB.getData("SELECT CURRENT_TIME() FROM DUAL");
            if (data.last()) {
                time = data.getString("CURRENT_TIME()");
            } else {
                time = new SimpleDateFormat("HH:mm:ss").format(new Date());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return time;
    }
}
