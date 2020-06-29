package controller.account;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import conn.DB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import modle.GetInstans;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Program implements Initializable {

    @FXML
    private JFXTextField txt_vat;

    @FXML
    private JFXTextField txt_nbt;

    @FXML
    private JFXTextField txt_stamp;

    @FXML
    private JFXButton txt_update;

    @FXML
    void clickOnUpdate(MouseEvent event) {

        String v = txt_vat.getText();
        String n = txt_nbt.getText();
        String s = txt_stamp.getText();

        try {
            double vat = Double.parseDouble(v);
            double nbt = Double.parseDouble(n);
            double stamp = Double.parseDouble(s);

            conn.DB.setData("UPDATE `interest` SET  `rate` = '" + vat + "'  WHERE name = 'VAT'");
            conn.DB.setData("UPDATE `interest` SET  `rate` = '" + nbt + "'  WHERE name = 'NBT'");
            conn.DB.setData("UPDATE `interest` SET  `rate` = '" + stamp + "'  WHERE name = 'STAMP'");

            modle.Allert.notificationGood("Updated Successful", "VAT, NBT, STAMP");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String query = "SELECT\n" +
                "interest.idInterest,\n" +
                "interest.`name`,\n" +
                "interest.rate,\n" +
                "interest.`status`\n" +
                "FROM\n" +
                "interest\n";

        try {
            ResultSet data = DB.getData(query);
            while (data.next()) {

                String name = data.getString("name");
                double rate = data.getDouble("rate");
                switch (name) {
                    case "VAT":
                        txt_vat.setText(rate + "");
                        break;

                    case "NBT":
                        txt_nbt.setText(rate + "");
                        break;

                    case "STAMP":
                        txt_stamp.setText(rate + "");
                        break;
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


}
