package modle.account;

import conn.DB;

import java.sql.ResultSet;

public class AHA {

    public int getAppAccountByOffice(int appcatId, int officeId) {
        int bankinfo_id = 0;
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "aha.idAHA,\n" +
                    "aha.appcat_id,\n" +
                    "aha.bankinfo_id,\n" +
                    "aha.office_id,\n" +
                    "aha.aha_status\n" +
                    "FROM `aha`\n" +
                    "WHERE\n" +
                    "aha.appcat_id = '" + appcatId + "' AND\n" +
                    "aha.office_id = '" + officeId + "'");

            if (data.last()) {
                bankinfo_id = data.getInt("bankinfo_id");

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return bankinfo_id;
    }

}
