package modle;

import conn.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class ComboLoad {


    public static ObservableList<ComboItem> loadCombo(String query) {
        ObservableList<ComboItem> arrayList = FXCollections.observableArrayList();
        try {
            ResultSet data = DB.getData(query);
            while (data.next()) {arrayList.add(new ComboItem(data.getInt(1), data.getString(2)));}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }


}
