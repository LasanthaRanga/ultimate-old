package controller.assess;

import com.jfoenix.controls.*;
import conn.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modle.asses.OnlinePay;

import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class OnlineApprove implements Initializable {

    @FXML
    private TableView<Online> tbl;

    @FXML
    private TableColumn<Online, Integer> col_index;

    @FXML
    private TableColumn<Online, String> col_customer;

    @FXML
    private TableColumn<Online, String> col_appcat;

    @FXML
    private TableColumn<Online, String> col_amount;

    @FXML
    private TableColumn<Online, String> col_status;

    @FXML
    private TableColumn<Online, JFXCheckBox> col_checks;

    @FXML
    private JFXCheckBox check;

    @FXML
    private JFXDatePicker dp;

    @FXML
    private JFXRadioButton radio_confirmed;

    @FXML
    private ToggleGroup confirmed;

    @FXML
    private JFXRadioButton radio_reject;

    @FXML
    private JFXRadioButton radio_completed;

    @FXML
    private ToggleGroup finish;

    @FXML
    private JFXRadioButton radio_pending;

    @FXML
    private JFXButton btn_complete;

    @FXML
    private JFXProgressBar progress;

    ObservableList<Online> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        col_index.setCellValueFactory(new PropertyValueFactory<>("onpayid"));
        col_customer.setCellValueFactory(new PropertyValueFactory<>("cus_name"));
        col_appcat.setCellValueFactory(new PropertyValueFactory<>("appcat"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_checks.setCellValueFactory(new PropertyValueFactory<>("select"));

        loadTable(1);
    }


    @FXML
    void checkAll(ActionEvent event) {

    }

    @FXML
    void clickOnTbl(MouseEvent event) {

    }

    @FXML
    void completeOnAction(ActionEvent event) {
        new OnlinePay().startProcess(list);
    }

    @FXML
    void dateChange(ActionEvent event) {

        radioOnAction(event);

    }

    @FXML
    void radioOnAction(ActionEvent event) {

        int status = 0;

        if (radio_confirmed.isSelected()) {
            radio_completed.setDisable(false);
            radio_pending.setDisable(false);
            if (radio_completed.isSelected()) {
                status = 2;
                loadTable(status);
            }
            if (radio_pending.isSelected()) {
                status = 1;
                loadTable(status);
            }
        }


        if (radio_reject.isSelected()) {
            status = 0;
            radio_completed.setDisable(true);
            radio_pending.setDisable(true);
            loadTable(status);
        }


    }


    public void loadTable(int status) {
        list.clear();
        String st = "";
        if (status == 1) {
            st = "Paid Confirmed";
        }

        if (status == 0 || status == 3) {
            st = "rejected";
        }

        if (status == 2) {
            st = "completed";
        }

        try {
            String query = "SELECT\n" +
                    "online_pay.idOnPaid,\n" +
                    "online_pay.oncus_id,\n" +
                    "online_pay.appcat_id,\n" +
                    "online_pay.app_id,\n" +
                    "online_pay.date,\n" +
                    "online_pay.amount,\n" +
                    "online_pay.`status`,\n" +
                    "online_pay.description,\n" +
                    "online_pay.other,\n" +
                    "online_pay.other2,\n" +
                    "online_cus.fullname,\n" +
                    "online_cus.nic,\n" +
                    "online_cus.email,\n" +
                    "online_cus.mobile,\n" +
                    "online_cus.`status`,\n" +
                    "application_catagory.application_name\n" +
                    "FROM\n" +
                    "online_pay\n" +
                    "INNER JOIN online_cus ON online_cus.idOnline = online_pay.oncus_id\n" +
                    "INNER JOIN application_catagory ON application_catagory.idApplication_Catagory = online_pay.appcat_id\n" +
                    "WHERE\n" +
                    "online_pay.`status` = '" + status + "' ";

            if (dp.getValue() != null) {

                Date selectDate = Date.from(dp.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String day = simpleDateFormat.format(selectDate);

                query += " AND DATE(online_pay.date) = '" + day + "' ";
            }


            ResultSet data = DB.getData(query);

            while (data.next()) {
                list.add(new Online(data.getInt("idOnPaid"), data.getInt("oncus_id"), data.getString("fullname"), data.getInt("appcat_id"), data.getString("application_name"),
                        data.getInt("app_id"), data.getDouble("amount"), data.getInt("status"), st));
                // System.out.println(list.size());
            }

            tbl.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


    public class Online {

        public Online(int onpayid, int cusid, String cus_name, int appcatid, String appcat, int appid, double amount, int statusid, String status) {
            this.onpayid = onpayid;
            this.cusid = cusid;
            this.cus_name = cus_name;
            this.appcatid = appcatid;
            this.appcat = appcat;
            this.appid = appid;
            this.amount = amount;
            this.statusid = statusid;
            this.status = status;
            this.select = new JFXCheckBox();
        }

        private int onpayid;
        private int cusid;
        private String cus_name;
        private int appcatid;
        private String appcat;
        private int appid;
        private double amount;
        private int statusid;
        private String status;
        private JFXCheckBox select;

        public void setOnpayid(int onpayid) {
            this.onpayid = onpayid;
        }

        public void setCusid(int cusid) {
            this.cusid = cusid;
        }

        public void setCus_name(String cus_name) {
            this.cus_name = cus_name;
        }

        public void setAppcatid(int appcatid) {
            this.appcatid = appcatid;
        }

        public void setAppcat(String appcat) {
            this.appcat = appcat;
        }

        public void setAppid(int appid) {
            this.appid = appid;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public void setStatusid(int statusid) {
            this.statusid = statusid;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setSelect(boolean select) {
            this.select.setSelected(true);
        }

        public int getOnpayid() {
            return onpayid;
        }

        public int getCusid() {
            return cusid;
        }

        public String getCus_name() {
            return cus_name;
        }

        public int getAppcatid() {
            return appcatid;
        }

        public String getAppcat() {
            return appcat;
        }

        public int getAppid() {
            return appid;
        }

        public double getAmount() {
            return amount;
        }

        public int getStatusid() {
            return statusid;
        }

        public String getStatus() {
            return status;
        }

        public JFXCheckBox getSelect() {
            return select;
        }
    }


}
