/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modle;

import com.jfoenix.controls.JFXTreeTableView;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class TableLoad {

    public void load(ObservableList list, TableView<?> tbl, HashMap<TableColumn, String> hashMap) {
        for (Map.Entry<TableColumn, String> hm : hashMap.entrySet()) {
            TableColumn key = hm.getKey();
            String value = hm.getValue();
            key.setCellValueFactory(new PropertyValueFactory<>(value));
        }
        tbl.setItems(list);
    }

    
}
