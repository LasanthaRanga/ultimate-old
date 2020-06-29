/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.adv;

import com.jfoenix.controls.*;

import java.net.URL;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import conn.DB;
import controller.popup.BarcodePrintMessage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import modle.GetInstans;
import modle.popup.BarcodeStatic;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.AdvAdvertising;
import pojo.Customer;
import sun.security.pkcs11.Secmod;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class PaymentController implements Initializable {

    @FXML
    private Label lbl_advTot;
    @FXML
    private Label lbl_vat;
    @FXML
    private Label lbl_nbt;
    @FXML
    private Label lbl_stamp;
    @FXML
    private Label lbl_grund;
    @FXML
    private Label lbl_searching;
    @FXML
    private Label lbl_price;
    @FXML
    private Label lbl_Full_tot;
    @FXML
    private Label lbl_customer;
    @FXML
    private Label lbl_nic;
    @FXML
    private Label lbl_bordNo;
    @FXML
    private Label lbl_diposit;
    @FXML
    private JFXButton btn_click;
    @FXML
    private JFXCheckBox cash;
    @FXML
    private JFXCheckBox check;
    @FXML
    private JFXTextField txt_cash;
    @FXML
    private JFXTextField txt_check;
    @FXML
    private JFXTextField txt_checkNO;
    //   private JFXDatePicker day_cheack;
    double fullTot;
    int appid;
    @FXML
    private JFXDatePicker day_cheack;

    @FXML
    private JFXComboBox<String> com_bank;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (modle.adv.StaticBaduAdv.getAdvApplicationID() > 0) {
            viewAppData();
        }
        loadBankCombo();
        modle.StaticViews.getMc().changeTitle("Advertisement Pay");
    }

    public void viewAppData() {
        int advApplicationID = modle.adv.StaticBaduAdv.getAdvApplicationID();

        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            AdvAdvertising adv = (AdvAdvertising) session.load(AdvAdvertising.class, advApplicationID);

            Customer customer = (Customer) session.createCriteria(Customer.class).add(Restrictions.eq("idCustomer", adv.getCustomerIdCustomer())).uniqueResult();
            appid = adv.getIdAdvAdvertising();
            lbl_customer.setText(customer.getCusName());
            lbl_nic.setText(customer.getCusNic());
            lbl_bordNo.setText(adv.getAdvBoardNo());

            lbl_advTot.setText(modle.Round.roundToString(adv.getAdvTotal()));
            lbl_vat.setText(modle.Round.roundToString(adv.getAdvVat()));
            lbl_nbt.setText(modle.Round.roundToString(adv.getAdvNbt()));
            lbl_stamp.setText(modle.Round.roundToString(adv.getAdvStamp()));

            lbl_grund.setText(modle.Round.roundToString(adv.getAdvGroundTotal()));
            lbl_diposit.setText(modle.Round.roundToString(adv.getAdvDiposit()));
            lbl_searching.setText(modle.Round.roundToString(adv.getAdvVisitingPrice()));
            lbl_price.setText(modle.Round.roundToString(adv.getAdvOthers()));
            fullTot = adv.getAdvFullTotal();
            lbl_Full_tot.setText(modle.Round.roundToString(adv.getAdvFullTotal()));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    double cash_p = 0;
    double check_p = 0;
    int bank = 0;
    String ch_no;
    Date checkDate;

    public boolean collectData() {

        if (cash.isSelected() && check.isSelected()) {
            try {
                cash_p = Double.parseDouble(txt_cash.getText());
                check_p = Double.parseDouble(txt_check.getText());
                ch_no = txt_checkNO.getText();
                bank = GetInstans.getBanks().getBankIdByBankName(com_bank.getSelectionModel().getSelectedItem());
                if (day_cheack.getValue() != null) {
                    checkDate = Date.from(day_cheack.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
                    return true;
                } else {
                    modle.Allert.notificationError("Plese Select Check Date", ch_no);
                    return false;
                }
            } catch (Exception e) {
                modle.Allert.notificationError("Check Prices", ch_no);
                return false;
            }

        } else if (check.isSelected()) {
            try {
                cash_p = Double.parseDouble(txt_cash.getText());
                check_p = Double.parseDouble(txt_check.getText());
                ch_no = txt_checkNO.getText();
                if (day_cheack.getValue() != null) {
                    checkDate = Date.from(day_cheack.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
                    return true;
                } else {
                    modle.Allert.notificationError("Plese Select Check Date", ch_no);
                    return false;
                }
            } catch (Exception e) {
                modle.Allert.notificationError("Check Prices", ch_no);
                return false;
            }
        } else if (cash.isSelected()) {
            try {
                cash_p = Double.parseDouble(txt_cash.getText());
                check_p = Double.parseDouble(txt_check.getText());
                checkDate = null;
                ch_no = "";
            } catch (Exception e) {
                return false;
            }
            return true;
        } else {
            return false;
        }

    }

    @FXML
    private void clickOnPay(MouseEvent event) {

        if (collectData()) {
            int payNow = modle.GetInstans.getAdvPayModle().payNow(appid, cash_p, check_p, cash_p + check_p, ch_no, checkDate, modle.GetInstans.getBanks().getBankIdByBankName(com_bank.getSelectionModel().getSelectedItem()));
            System.out.println("bank id ========================="+bank);
            if (payNow>0) {

                try {
                    ResultSet data = DB.getData("SELECT\n" +
                            "adv_advertising.idAdv_Advertising,\n" +
                            "adv_advertising.receipt_idReceipt\n" +
                            "FROM `adv_advertising`\n" +
                            "WHERE\n" +
                            "adv_advertising.idAdv_Advertising = " + appid);
                    System.out.println("Mekata awa");
                    if (data.last()) {
                        int receipt_idReceipt = data.getInt("receipt_idReceipt");
//                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                        alert.setTitle("Receipt Number");
//                        alert.setHeaderText(null);
//                        alert.setContentText("This Adverticing Payment ID IS :   " + receipt_idReceipt);
//                        alert.showAndWait();
//                        System.out.println("data walata awa");

                        BarcodeStatic.customerName = "";
                        BarcodeStatic.idRecipt = receipt_idReceipt+"";
                        BarcodeStatic.reTotal = cash_p + check_p;
                        BarcodeStatic.subject = "Advertising";

                        BarcodePrintMessage.popup();

                    }




                } catch (Exception e) {
                    e.printStackTrace();
                }


                modle.Allert.notificationGood("PAID", "Successful");
                new modle.adv.Report().pringAdvBill(appid,false);
                cleare();
            } else {
                modle.Allert.notificationError("Somthing Wrong", "Cheack Again");
            }
        }
    }

    @FXML
    private void cash_onAction(ActionEvent event) {
        if (cash.isSelected() && check.isSelected()) {
            txt_cash.setDisable(false);
            txt_check.setDisable(false);
            txt_cash.setEditable(true);
            txt_check.setEditable(true);
            txt_checkNO.setDisable(false);
            day_cheack.setDisable(false);
        } else if (cash.isSelected()) {
            txt_cash.setDisable(false);
            txt_cash.setText(fullTot + "");
            txt_cash.setEditable(false);
            txt_checkNO.setDisable(true);
            day_cheack.setDisable(true);
        } else {
            txt_cash.setText("00");
            txt_cash.setDisable(true);
        }
    }

    @FXML
    private void check_onAction(ActionEvent event) {
        if (cash.isSelected() && check.isSelected()) {
            txt_cash.setDisable(false);
            txt_check.setDisable(false);
            txt_cash.setEditable(true);
            txt_check.setEditable(true);
            txt_checkNO.setDisable(false);
            day_cheack.setDisable(false);

        } else if (check.isSelected()) {
            txt_check.setDisable(false);
            txt_check.setText(fullTot + "");
            txt_check.setEditable(false);
            txt_checkNO.setDisable(false);
            day_cheack.setDisable(false);
            txt_cash.setText("00");
        } else {
            txt_check.setText("00");
            txt_check.setDisable(true);
            txt_checkNO.setDisable(true);
            day_cheack.setDisable(true);
        }
    }

    double ca, ch;

    @FXML
    private void onKeyReleased(KeyEvent event) {
        JFXTextField tf = (JFXTextField) event.getSource();

        if (tf == txt_cash) {
            if (txt_cash.getText().matches("\\d*(\\.\\d*)?") && txt_cash.getText().length() > 0) {

                if (cash.isSelected() && check.isSelected()) {
                    if (tf == txt_cash) {
                        System.out.println("Cash");
                        ca = Double.parseDouble(txt_cash.getText());
                        txt_check.setText((fullTot - ca) + "");
                    }
                    if (tf == txt_check) {
                        ch = Double.parseDouble(txt_check.getText());
                        txt_cash.setText((fullTot - ch) + "");
                    }
                } else if (cash.isSelected()) {
                    txt_cash.setText(fullTot + "");
                    txt_cash.setEditable(false);
                    txt_check.setText("00");
                    txt_check.setDisable(true);
                } else if (check.isSelected()) {
                    txt_check.setText(fullTot + "");
                    txt_check.setEditable(false);
                    txt_cash.setText("00");
                    txt_cash.setDisable(true);
                }

            } else {
                java.awt.Toolkit.getDefaultToolkit().beep();
                txt_cash.deletePreviousChar();
            }
        } else if (tf == txt_check) {
            if (txt_check.getText().matches("\\d*(\\.\\d*)?") && txt_check.getText().length() > 0) {
                if (cash.isSelected() && check.isSelected()) {
                    if (tf == txt_cash) {
                        System.out.println("Cash");
                        ca = Double.parseDouble(txt_cash.getText());
                        txt_check.setText((fullTot - ca) + "");
                    }
                    if (tf == txt_check) {
                        ch = Double.parseDouble(txt_check.getText());
                        txt_cash.setText((fullTot - ch) + "");
                    }
                } else if (cash.isSelected()) {
                    txt_cash.setText(fullTot + "");
                    txt_cash.setEditable(false);
                    txt_check.setText("00");
                    txt_check.setDisable(true);
                } else if (check.isSelected()) {
                    txt_check.setText(fullTot + "");
                    txt_check.setEditable(false);
                    txt_cash.setText("00");
                    txt_cash.setDisable(true);
                }

            } else {
                java.awt.Toolkit.getDefaultToolkit().beep();
                txt_check.deletePreviousChar();
            }
        }

    }

    public void cleare() {
        appid = 0;
        lbl_customer.setText("");
        lbl_nic.setText("");
        lbl_bordNo.setText("");

        lbl_advTot.setText("");
        lbl_vat.setText("");
        lbl_nbt.setText("");
        lbl_stamp.setText("");

        lbl_grund.setText("");
        lbl_diposit.setText("");
        lbl_searching.setText("");
        lbl_price.setText("");
        fullTot = 0;
        lbl_Full_tot.setText("");
    }


    public void loadBankCombo() {
        ObservableList<String> strings = GetInstans.getBanks().loadBanksCombo();
        com_bank.setItems(strings);
    }


}
