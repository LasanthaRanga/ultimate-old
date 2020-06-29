/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.adv;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.tradelicens.AccApproveController;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modle.GetInstans;
import modle.adv.Advertising;
import org.hibernate.Session;
import pojo.ApproveDetails;
import pojo.SendToApprove;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class SendtoController implements Initializable {

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
    private JFXRadioButton r_not;
    @FXML
    private ToggleGroup approvGroup;
    @FXML
    private JFXRadioButton r_yes;
    @FXML
    private JFXRadioButton r_pending;
    @FXML
    private JFXDatePicker today;

    int sendtoapproveID = 0;
    @FXML
    private Label lbl_bordNo;
    @FXML
    private JFXTextArea txt_comment;
    @FXML
    private JFXButton btn_approve;
    @FXML
    private JFXButton btn_No;
    @FXML
    private JFXTextField txt_vistPrice;

    @FXML
    private Text txt_approvecat;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadApprovalList();
    }

    ObservableList<ApprovTbl> oal = FXCollections.observableArrayList();

    public void loadApprovalList() {
        int status = 0;
        if (r_pending.isSelected()) {
            status = 0;
        } else if (r_yes.isSelected()) {
            status = 1;
        } else if (r_not.isSelected()) {
            status = 2;
        }

        Date day = null;
        if (today.getValue() != null) {
            day = Date.from(today.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
        }
        oal.clear();
        List<SendToApprove> approvalList = modle.GetInstans.getSendToApprove().getApprovalList(day, status);
        for (SendToApprove sa : approvalList) {

            Advertising.SelectedData sd = modle.GetInstans.getAdvertisingModle().getSelectedDataList(sa.getApplicationId());
            oal.add(new ApprovTbl(sa.getIdSendToApprove(),
                    sd.getBordNo(), sd.getSdate(), sd.getEdate(), sa.getStatusApprove(), sd.getCusName()));

        }
        tbl_Approval.setItems(oal);

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_bordNo.setCellValueFactory(new PropertyValueFactory<>("bordNo"));
        col_start_date.setCellValueFactory(new PropertyValueFactory<>("sdate"));
        col_end_date.setCellValueFactory(new PropertyValueFactory<>("edate"));
        col_fullAmount.setCellValueFactory(new PropertyValueFactory<>("amunt"));
        col_customerName.setCellValueFactory(new PropertyValueFactory<>("customer"));

    }

    @FXML
    private void setStatus(ActionEvent event) {
        loadApprovalList();
        txt_vistPrice.setText("");
        txt_comment.setText("");
        lbl_bordNo.setText("");

    }

    @FXML
    private void setDate(ActionEvent event) {
        loadApprovalList();
        txt_vistPrice.setText("");
        txt_comment.setText("");
        lbl_bordNo.setText("");
    }

    @FXML
    private void selectOnTable(MouseEvent event) {
        ApprovTbl selectedItem = tbl_Approval.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            sendtoapproveID = selectedItem.getId();
            lbl_bordNo.setText(selectedItem.getBordNo());
            ApproveDetails approveDetails = GetInstans.getSendToApprove().getApprovalDetailsBySendToApprovalId(sendtoapproveID);
            if (approveDetails != null) {
                txt_comment.setText(approveDetails.getComment());
                txt_vistPrice.setText(approveDetails.getPricing() + "");
            }
        }
    }

    @FXML
    private void MoreDetails(MouseEvent event) {
        modle.adv.StaticBaduAdv.setSendToApprovID(sendtoapproveID);
        if (sendtoapproveID > 0) {
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
            modle.Allert.notificationInfo("Select First", "Select row after click this more button");
        }
    }

    @FXML
    private void clickApprove(MouseEvent event) {

        String comment = txt_comment.getText();
        double visitprice;
        if (!txt_vistPrice.getText().isEmpty()) {
            try {
                visitprice = Double.parseDouble(txt_vistPrice.getText());
            } catch (Exception e) {
                visitprice = 0;
            }
        } else {
            visitprice = 0;
        }
        boolean setApproval = modle.GetInstans.getSendToApprove().setApproval(sendtoapproveID, comment, visitprice, 1);
        if (setApproval) {
            modle.Allert.notificationGood("Approval Success", "");
            loadApprovalList();
        }
    }

    @FXML
    private void clickNo(MouseEvent event) {
        String comment = txt_comment.getText();
        double visitprice;
        if (!txt_vistPrice.getText().isEmpty()) {
            try {
                visitprice = Double.parseDouble(txt_vistPrice.getText());
            } catch (Exception e) {
                visitprice = 0;
            }
        } else {
            visitprice = 0;
        }
        boolean setApproval = modle.GetInstans.getSendToApprove().setApproval(sendtoapproveID, comment, visitprice, 2);
        if (setApproval) {
            modle.Allert.notificationGood("Non Approval Success", "");
            loadApprovalList();
        }

    }

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


}
