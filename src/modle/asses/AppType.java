package modle.asses;

import conn.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modle.ComboItem;

import java.sql.ResultSet;

public class AppType {

    public ObservableList<ComboItem> loadAppTypeCombo() {
        ObservableList<ComboItem> arrayList = FXCollections.observableArrayList();
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_apptype.idAss_apptype,\n" +
                    "ass_apptype.type_name\n" +
                    "FROM\n" +
                    "ass_apptype");
            while (data.next()) {
                int idAss_apptype = data.getInt("idAss_apptype");
                String type_name = data.getString("type_name");
                arrayList.add(new ComboItem(idAss_apptype, type_name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return arrayList;
    }


    public String getRefNoString(int appcatid, int officeid) {
        String refno = "";
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "referenceno_tbl.refno\n" +
                    "FROM\n" +
                    "referenceno_tbl\n" +
                    "WHERE application_catagory_idApplication_Catagory = 2 AND office_idOffice = 1");


            if (data.last()) {
                refno = data.getString("refno");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return refno;
    }

    public int getRefNoOder(int appcatid, int officeid) {
        int refno = 0;
        try {
            ResultSet data1 = DB.getData("SELECT\n" +
                    "MAX(referenceno.oder) as oder\n" +
                    "FROM\n" +
                    "referenceno\n" +
                    "WHERE application_catagory_idApplication_Catagory = '" + appcatid + "' AND\n" +
                    "office_id = " + officeid);
            if (data1.last()) {
                refno = data1.getInt("oder");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return refno;
    }


}
