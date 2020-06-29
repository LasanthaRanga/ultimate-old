package modle;

import conn.DB;

import java.sql.ResultSet;

public class KeyVal {
    public static String getVal(String key) {
        String value = null;
        try {
            ResultSet data = DB.getData("SELECT *\n" +
                    "FROM `fxkeyvalue`\n" +
                    "WHERE\n" +
                    "fxkeyvalue.`key` = '" + key + "'");
            if (data.last()) {
                value = data.getString("value");
            }
        } catch (Exception e) {
            e.printStackTrace();
            value = "/grafics/disconnect.jpg";
        }
        return value;
    }

    public static String updateVal(String key, String val) {
        String value = null;
        try {
            int i = DB.setData("UPDATE  `fxkeyvalue` SET  `value` = '"+val+"' WHERE `key` = '"+key+"'");
            value = "Updated key to :"+ val;
        } catch (Exception e) {
            e.printStackTrace();
            value = "Error";
        }
        return value;

    }
}
