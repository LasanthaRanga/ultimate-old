package controller.mix;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import modle.GetInstans;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class Report implements Initializable {
    @FXML
    private JFXDatePicker dp_from;

    @FXML
    private JFXDatePicker dp_to;

    @FXML
    private JFXButton btn_report;

    @FXML
    private JFXTextField txt_type;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modle.StaticViews.getMc().changeTitle("Mix Report");

        ArrayList<String> mixTypes = GetInstans.getGenarateRecipt().getMixType();
        TextFields.bindAutoCompletion(txt_type, mixTypes);


    }

    @FXML
    void typeKeyUp(KeyEvent event) {
        System.out.println(txt_type.getText());
    }

    @FXML
    void btnReportOnAction(ActionEvent event) {

        try {
            if (dp_from.getValue() != null && dp_to.getValue() != null && txt_type.getText() != null && txt_type.getText().length() > 0) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date selectDateFrom = Date.from(dp_from.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
                Date selectDateTo = Date.from(dp_to.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
                String from = simpleDateFormat.format(selectDateFrom);
                String to = simpleDateFormat.format(selectDateTo);
                String text = txt_type.getText();
                System.out.println(from);
                System.out.println(to);
                System.out.println(text);

                modle.GetInstans.getGenarateRecipt().getReport(from, to, txt_type.getText());


            } else {
                modle.Allert.notificationWorning("Empty", "Please Fill All");
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


}
