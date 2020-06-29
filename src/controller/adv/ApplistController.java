/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.adv;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modle.adv.Advertising;
import pojo.AdvAdvertising;
import pojo.SendToApprove;
import view.buttons.BTN;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class ApplistController implements Initializable {

    @FXML
    private TableView<ApprovTbl> tbl_Approval;
    @FXML
    private TableColumn<ApprovTbl, Integer> col_id;
    @FXML
    private TableColumn<ApprovTbl, String> col_bordNo;
    @FXML
    private TableColumn<ApprovTbl, String> col_start_date;
    @FXML
    private TableColumn<ApprovTbl, String> col_end_date;
    @FXML
    private TableColumn<ApprovTbl, Double> col_fullAmount;
    @FXML
    private TableColumn<ApprovTbl, String> col_customerName;
    @FXML
    private Label lbl_bordNo;
    @FXML
    private JFXDatePicker today;
    @FXML
    private JFXRadioButton r_not;
    @FXML
    private JFXRadioButton r_yes;
    @FXML
    private JFXRadioButton r_pending;
    @FXML
    private JFXRadioButton r_notsent;
    @FXML
    private ToggleGroup group;

    int appid = 0;
    @FXML
    private JFXButton btn_send;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadApprovalList();
        if (r_notsent.isSelected()) {
            btn_send.setDisable(false);
        }
        modle.StaticViews.getMc().changeTitle("Advertisements List");
    }

    @FXML
    private void selectOnTable(MouseEvent event) {
        ApprovTbl selectedItem = tbl_Approval.getSelectionModel().getSelectedItem();
        appid = selectedItem.getId();
        lbl_bordNo.setText(selectedItem.getBordNo());
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
    private void setDate(ActionEvent event) {
        loadApprovalList();
    }

    @FXML
    private void setStatus(ActionEvent event) {
        loadApprovalList();
        if (r_notsent.isSelected()) {
            btn_send.setDisable(false);
        } else {
            btn_send.setDisable(true);
        }
    }

    @FXML
    private void goToPaymant(MouseEvent event) {

        modle.adv.StaticBaduAdv.setAdvApplicationID(appid);

        if (modle.adv.StaticBaduAdv.getAdvApplicationID() > 0) {

            AnchorPane container = modle.StaticViews.getContainer();
            container.getChildren().removeAll();
            container.getChildren().clear();
            AnchorPane dashh;

            try {
                dashh = FXMLLoader.load(getClass().getResource("/view/adv/Payment.fxml"));
                container.getChildren().add(dashh);
            } catch (IOException ex) {
                ex.printStackTrace();
                Logger.getLogger(Customer_regController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Set<String> keySet = modle.StaticViews.getButtonMap().keySet();
            String s = "/view/buttons/adv_payment.fxml";

            for (String string : keySet) {
                if (string.equals(s)) {
                    try {
                        JFXButton get = modle.StaticViews.getButtonMap().get(string);
                        BTN get1 = modle.StaticViews.getBtnConMap().get(string);
                        get1.setImage("/grafics/pay_b.png");
                        get.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #03A9F4;");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JFXButton btn = modle.StaticViews.getButtonMap().get(string);
                        btn.setStyle("-fx-background-color: #03A9F4; -fx-text-fill: #FFFFFF;");
                        BTN get = modle.StaticViews.getBtnConMap().get(string);
                        if (get != null) {
                            get.setImage();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    @FXML
    private void Delete(MouseEvent event) {

        boolean delete = modle.GetInstans.getAdvertisingModle().deleteApplication(appid);
        if (delete) {
            modle.Allert.notificationGood("delete complete", "ok");
            loadApprovalList();
        }

    }

    @FXML
    private void clickOnSend(MouseEvent event) {
        if (r_notsent.isSelected()) {
            if (appid > 0) {
                boolean sendToApprove = modle.GetInstans.getSendToApprove().sendToApprove(appid);
                if (sendToApprove) {
                    modle.Allert.notificationGood("Sent", "");
                }
            } else {
            }
        }
    }

    ///table Load
    public class ApprovTbl {

        private int id;
        private SimpleStringProperty bordNo;
        private SimpleStringProperty sdate;
        private SimpleStringProperty edate;
        private double amunt;
        private SimpleStringProperty customer;

        public ApprovTbl(int id, String bordNo, String sdate, String edate, double amunt, String customer) {
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

    ObservableList<ApprovTbl> oal = FXCollections.observableArrayList();

    public void loadApprovalList() {
        System.out.println("call Approval List");
        int status = 0;
        if (r_pending.isSelected()) {
            status = 0;
        } else if (r_yes.isSelected()) {
            status = 1;
        } else if (r_not.isSelected()) {
            status = 2;
        } else if (r_notsent.isSelected()) {
            status = 3;
        }

        Date day = null;
        if (today.getValue() != null) {
            day = Date.from(today.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
        }
        oal.clear();

        List<AdvAdvertising> apps = modle.GetInstans.getAdvertisingModle().getApplicationList(day, status);
        for (AdvAdvertising app : apps) {
            oal.add(new ApprovTbl(app.getIdAdvAdvertising(), app.getAdvBoardNo(), app.getAdvStartDate().toString(), app.getAdvEndDate().toString(), app.getAdvFullTotal(), modle.GetInstans.getAdvCustomerModle().getCustomerName(app.getCustomerIdCustomer())));
        }
        tbl_Approval.setItems(oal);

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_bordNo.setCellValueFactory(new PropertyValueFactory<>("bordNo"));
        col_start_date.setCellValueFactory(new PropertyValueFactory<>("sdate"));
        col_end_date.setCellValueFactory(new PropertyValueFactory<>("edate"));
        col_fullAmount.setCellValueFactory(new PropertyValueFactory<>("amunt"));
        col_customerName.setCellValueFactory(new PropertyValueFactory<>("customer"));

    }

}
