package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modle.asses.CDholder;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class CDlist implements Initializable {

    @FXML
    private TableView<CDholder> tbl_cd;

    @FXML
    private TableColumn<CDholder, String> col_day;

    @FXML
    private TableColumn<CDholder, String> col_user;

    @FXML
    private TableColumn<CDholder, String> col_ward;

    @FXML
    private TableColumn<CDholder, String> col_street;

    @FXML
    private TableColumn<CDholder, String> col_assessment;

    @FXML
    private TableColumn<CDholder, Double> col_credit;

    @FXML
    private TableColumn<CDholder, Double> col_debit;

    @FXML
    private TableColumn<CDholder, String> col_des;

    @FXML
    private JFXDatePicker dp_from;

    @FXML
    private JFXDatePicker dp_to;

    @FXML
    private JFXButton btn_report;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_day.setCellValueFactory(new PropertyValueFactory<>("day"));
        col_user.setCellValueFactory(new PropertyValueFactory<>("user"));
        col_ward.setCellValueFactory(new PropertyValueFactory<>("ward"));
        col_street.setCellValueFactory(new PropertyValueFactory<>("street"));
        col_assessment.setCellValueFactory(new PropertyValueFactory<>("assessment"));
        col_credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        col_debit.setCellValueFactory(new PropertyValueFactory<>("debit"));
        col_des.setCellValueFactory(new PropertyValueFactory<>("description"));
    }


    @FXML
    void dateOnAction(ActionEvent event) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (dp_to.getValue() != null && dp_from.getValue() != null) {
            Date from = Date.from(dp_from.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
            Date to = Date.from(dp_to.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
            String f = simpleDateFormat.format(from);
            String t = simpleDateFormat.format(to);
            tbl_cd.setItems(modle.GetInstans.getCdlist().loadTable(f, t));
        }
    }


    public void loadTable() {


    }

    @FXML
    void clickonReport(ActionEvent event) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (dp_to.getValue() != null && dp_from.getValue() != null) {
            Date from = Date.from(dp_from.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
            Date to = Date.from(dp_to.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
            String f = simpleDateFormat.format(from);
            String t = simpleDateFormat.format(to);
            System.out.println("asdf");
            modle.GetInstans.getAssessReport().cdList(f, t);
        }
    }
}
