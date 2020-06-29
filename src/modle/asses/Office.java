package modle.asses;

import conn.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Office {
    ObservableList<modle.asses.OfficeHolder> officeList = FXCollections.observableArrayList();

    public ObservableList<modle.asses.OfficeHolder> loadOfficeCombo() {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "office.idOffice,\n" +
                    "office.office_name\n" +
                    "FROM\n" +
                    "office\n");
            officeList.clear();
            while (data.next()) {
                officeList.add(new OfficeHolder(data.getInt("idOffice"), data.getString("office_name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return officeList;
    }
}
