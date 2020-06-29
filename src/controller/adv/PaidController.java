/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.adv;

import com.jfoenix.controls.JFXDatePicker;
import conn.DB;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pojo.AdvAdvertising;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class PaidController implements Initializable {

    @FXML
    private TableView<PaidTbl> tbl_Approval;
    @FXML
    private TableColumn<PaidTbl, Integer> col_id;
    @FXML
    private TableColumn<PaidTbl, String> col_bordNo;
    @FXML
    private TableColumn<PaidTbl, String> col_start_date;
    @FXML
    private TableColumn<PaidTbl, String> col_end_date;
    @FXML
    private TableColumn<PaidTbl, Double> col_fullAmount;
    @FXML
    private TableColumn<PaidTbl, String> col_customerName;
    @FXML
    private Label lbl_bordNo;
    @FXML
    private JFXDatePicker today;

    int appid = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadPaidList();
    }

    @FXML
    private void selectOnTable(MouseEvent event) {
        appid = tbl_Approval.getSelectionModel().getSelectedItem().getId();
        lbl_bordNo.setText(tbl_Approval.getSelectionModel().getSelectedItem().getBordNo());
    }

    @FXML
    private void MoreDetails(MouseEvent event) {
        if (appid > 0) {
            modle.adv.StaticBaduAdv.setAdvApplicationID(appid);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/adv/more.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                //scene.setFill(Color.TRANSPARENT);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.getIcons().add(new Image("/grafics/info.png"));
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SendtoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Plese select
        }
    }

    @FXML
    private void sheduleReport(MouseEvent event) {
        Date date = null;
        if (today.getValue() != null) {
            date = Date.from(today.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
        } else {
            date = new Date();
        }

        String format = new SimpleDateFormat("yyyy-MM-dd").format(date);

        modle.GetInstans.getAdvReport().pringAdvShedule(format);
        System.out.println("print");

    }

    @FXML
    private void setDate(ActionEvent event) {
        loadPaidList();
    }

    ///table Load
    public class PaidTbl {

        private int id;
        private SimpleStringProperty bordNo;
        private SimpleStringProperty sdate;
        private SimpleStringProperty edate;
        private double amunt;
        private SimpleStringProperty customer;

        public PaidTbl(int id, String bordNo, String sdate, String edate, double amunt, String customer) {
            this.id = id;
            this.bordNo = new SimpleStringProperty(bordNo);
            this.sdate = new SimpleStringProperty(sdate);
            this.edate = new SimpleStringProperty(edate);
            this.amunt = amunt;
            this.customer = new SimpleStringProperty(customer);
        }

        /**
         * @return the id
         */
        public int getId() {
            return id;
        }

        /**
         * @return the bordNo
         */
        public String getBordNo() {
            return bordNo.get();
        }

        /**
         * @return the sdate
         */
        public String getSdate() {
            return sdate.get();
        }

        /**
         * @return the edate
         */
        public String getEdate() {
            return edate.get();
        }

        /**
         * @return the amunt
         */
        public double getAmunt() {
            return amunt;
        }

        /**
         * @return the customer
         */
        public String getCustomer() {
            return customer.get();
        }

    }

    ObservableList<PaidTbl> oal = FXCollections.observableArrayList();

    public void loadPaidList() {
        try {
            ResultSet dataSet = null;
            Date day = null;
            if (today.getValue() != null) {
                day = Date.from(today.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
                String quwary = "SELECT\n"
                        + "adv_advertising.idAdv_Advertising,\n"
                        + "customer.cus_name,\n"
                        + "adv_advertising.adv_board_no,\n"
                        + "adv_advertising.adv_start_date,\n"
                        + "adv_advertising.adv_end_date,\n"
                        + "adv_advertising.adv_full_total,\n"
                        + "adv_advertising.adv_paid_notpaid,\n"
                        + "adv_advertising.adv_status,\n"
                        + "adv_advertising.adv_current_date\n"
                        + "FROM\n"
                        + "customer\n"
                        + "INNER JOIN adv_advertising ON adv_advertising.Customer_idCustomer = customer.idCustomer\n"
                        + "WHERE\n"
                        + "adv_advertising.adv_status = 1 AND\n"
                        + "adv_advertising.adv_paid_notpaid = 1 AND\n"
                        + "adv_advertising.adv_current_date = '" + new SimpleDateFormat("yyyy-MM-dd").format(day) + "'";
                dataSet = DB.getData(quwary);
            } else {
                String quwary = "SELECT\n"
                        + "adv_advertising.idAdv_Advertising,\n"
                        + "customer.cus_name,\n"
                        + "adv_advertising.adv_board_no,\n"
                        + "adv_advertising.adv_start_date,\n"
                        + "adv_advertising.adv_end_date,\n"
                        + "adv_advertising.adv_full_total,\n"
                        + "adv_advertising.adv_paid_notpaid,\n"
                        + "adv_advertising.adv_status,\n"
                        + "adv_advertising.adv_current_date\n"
                        + "FROM\n"
                        + "customer\n"
                        + "INNER JOIN adv_advertising ON adv_advertising.Customer_idCustomer = customer.idCustomer\n"
                        + "WHERE\n"
                        + "adv_advertising.adv_status = 1 AND\n"
                        + "adv_advertising.adv_paid_notpaid = 1";
                dataSet = DB.getData(quwary);
            }
            oal.clear();

            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_bordNo.setCellValueFactory(new PropertyValueFactory<>("bordNo"));
            col_start_date.setCellValueFactory(new PropertyValueFactory<>("sdate"));
            col_end_date.setCellValueFactory(new PropertyValueFactory<>("edate"));
            col_fullAmount.setCellValueFactory(new PropertyValueFactory<>("amunt"));
            col_customerName.setCellValueFactory(new PropertyValueFactory<>("customer"));
            while (dataSet.next()) {
                oal.add(new PaidTbl(dataSet.getInt("idAdv_Advertising"), dataSet.getString("adv_board_no"), dataSet.getString("adv_start_date"), dataSet.getString("adv_end_date"), dataSet.getDouble("adv_full_total"), dataSet.getString("cus_name")));
                tbl_Approval.setItems(oal);
            }

        } catch (Exception ex) {
            Logger.getLogger(PaidController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
