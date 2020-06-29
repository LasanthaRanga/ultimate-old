package controller.adv;

import com.jfoenix.controls.*;
import conn.DB;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modle.adv.Position;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class RemoveController implements Initializable {

    @FXML
    private JFXRadioButton radio_show;
    @FXML
    private ToggleGroup Remove;
    @FXML
    private JFXRadioButton radio_remove;
    @FXML
    private JFXComboBox<String> com_pos;
    @FXML
    private JFXDatePicker sday;
    @FXML
    private JFXDatePicker eday;
    @FXML
    private JFXTextField txt_boardNo;
    @FXML
    private TableView<PaidTbl> tbl_Approval;
    @FXML
    private TableColumn<PaidTbl, Integer> col_id;
    @FXML
    private TableColumn<PaidTbl, String> col_customerName;
    @FXML
    private TableColumn<PaidTbl, String> col_bordNo;
    @FXML
    private TableColumn<PaidTbl, String> col_start_date;
    @FXML
    private TableColumn<PaidTbl, String> col_end_date;
    @FXML
    private TableColumn<PaidTbl, Double> col_fullAmount;
    @FXML
    private Label lbl_bordNo;
    @FXML
    private JFXButton btn_remove;


    int appId = 1;
    static int status = 1;
    static String position = "";
    static Date sdate, edate;
    static String bordNo = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadloacationCombo();
        new Thread(() -> loadPaidList()).start();
    }


    public void loadloacationCombo() {
        ObservableList<String> position = FXCollections.observableArrayList();
        position.clear();
        position.add("");
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "adv_position.adv_position_name\n" +
                    "FROM `adv_position`\n" +
                    "ORDER BY\n" +
                    "adv_position.adv_position_name ASC");
            while (data.next()) {
                position.add(data.getString("adv_position_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        com_pos.setItems(position);
    }

    @FXML
    private void radioOnAction(ActionEvent event) {
        if (radio_remove.isSelected()) {
            status = 0;
        } else if (radio_show.isSelected()) {
            status = 1;
        }
        new Thread(() -> loadPaidList()).start();
    }

    @FXML
    private void comOnAction(ActionEvent event) {
        position = com_pos.getSelectionModel().getSelectedItem();
        new Thread(() -> loadPaidList()).start();
    }

    @FXML
    private void startDateAction(ActionEvent event) {
        sdate = Date.from(sday.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
        new Thread(() -> loadPaidList()).start();
    }

    @FXML
    private void endDateAction(ActionEvent event) {
        edate = Date.from(eday.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
        new Thread(() -> loadPaidList()).start();
    }

    @FXML
    private void keyReleaseBoardNo(KeyEvent event) {
        bordNo = txt_boardNo.getText();
        loadloacationCombo();
        new Thread(() -> loadPaidList()).start();

    }

    int advid =0;

    @FXML
    private void selectOnTable(MouseEvent event) {
        advid = tbl_Approval.getSelectionModel().getSelectedItem().getId();
        System.out.println(tbl_Approval.getSelectionModel().getSelectedItem().getId());
        lbl_bordNo.setText(tbl_Approval.getSelectionModel().getSelectedItem().getBordNo());
        System.out.println(advid);
    }




    @FXML
    private void MoreDetails(MouseEvent event) {
        if (advid > 0) {
            modle.adv.StaticBaduAdv.setAdvApplicationID(advid);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/adv/more.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                // scene.setFill(Color.TRANSPARENT);
                // stage.initStyle(StageStyle.TRANSPARENT);
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

    //=====================================
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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String qq = "SELECT\n"
                + "adv_advertising.idAdv_Advertising,\n"
                + "adv_advertising.adv_start_date,\n"
                + "adv_advertising.adv_end_date,\n"
                + "adv_advertising.adv_board_no,\n"
                + "adv_advertising.adv_full_total,\n"
                + "customer.cus_name,\n"
                + "customer.cus_nic,\n"
                + "adv_position.adv_position_name\n"
                + "FROM\n"
                + "adv_advertising\n"
                + "INNER JOIN customer ON adv_advertising.Customer_idCustomer = customer.idCustomer\n"
                + "INNER JOIN adv_bords ON adv_bords.Adv_Advertising_idAdv_Advertising = adv_advertising.idAdv_Advertising\n"
                + "INNER JOIN adv_position ON adv_bords.Adv_position_idAdv_position = adv_position.idAdv_position\n"
                + "WHERE\n"
                + "adv_advertising.adv_status = '" + status + "' ";
        if (position.length() > 0) {
            qq += "AND adv_position.adv_position_name = '" + position + "' ";
        }

        if (sdate != null) {
            qq += "AND adv_advertising.adv_start_date = '" + df.format(sdate) + "' ";
        }

        if (edate != null) {
            qq += "AND adv_advertising.adv_end_date = '" + df.format(edate) + "' ";
        }

        if (bordNo.length() > 0) {
            qq += "AND adv_advertising.adv_board_no LIKE '%" + bordNo + "%'";
        }

        qq += "ORDER BY UNIX_TIMESTAMP(adv_advertising.adv_end_date) ASC";

        try {
            ResultSet dataSet = null;
            Date day = null;
            dataSet = DB.getData(qq);
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
                System.out.println(dataSet.getInt("idAdv_Advertising"));
            }

        } catch (Exception ex) {
            Logger.getLogger(PaidController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    void clickOnRemove(MouseEvent event) {
       if (advid > 0) {
           modle.adv.StaticBaduAdv.setAdvApplicationID(advid);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/adv/removeinfor.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            // scene.setFill(Color.TRANSPARENT);
            // stage.initStyle(StageStyle.TRANSPARENT);
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


}
