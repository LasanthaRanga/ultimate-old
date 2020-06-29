package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.adv.Customer_regController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import modle.asses.PayObj;
import modle.asses.TablePay;
import view.buttons.BTN;

public class PayNowController implements Initializable {
    @FXML
    public JFXTextField txt_idAssess;
    @FXML
    public Text txt_ward;
    @FXML
    public Text txt_street;
    @FXML
    public Text txt_assessmant;
    @FXML
    public Text txt_Obsaloot;
    @FXML
    public Text txt_customer;
    @FXML
    public Text txt_allocation;
    @FXML
    public Text txt_status;
    @FXML
    public Text txt_currentQuater;
    @FXML
    public Text txt_currentYear;
    @FXML
    public Text txt_lyArrears;
    @FXML
    public Text txt_lyWarrant;
    @FXML
    public Text txt_tyArrears;
    @FXML
    public Text txt_tyWarrant;
    @FXML
    public Text txt_Total;
    @FXML
    public Text txt_quaterValue;
    @FXML
    public Text txt_nature;
    @FXML
    public JFXTextField txt_Pay;
    @FXML
    public TableView<TablePay> table_pay;
    @FXML
    public TableColumn<TablePay, String> col_dis;
    @FXML
    public TableColumn<TablePay, String> col_amount;
    @FXML
    public TableColumn<TablePay, String> col_discount;
    @FXML
    public TableColumn<TablePay, String> col_value;
    @FXML
    public Text txt_cd;
    @FXML
    public Text q1_val;
    @FXML
    public Text q2_val;
    @FXML
    public Text q3_val;
    @FXML
    public Text q4_val;
    @FXML
    public Text q4_tot;
    @FXML
    public JFXComboBox<String> combo_bank;
    @FXML
    public JFXTextField txt_chq_no;
    @FXML
    public JFXTextField txt_cash;
    @FXML
    public JFXTextField txt_check;
    @FXML
    public JFXCheckBox cash;
    @FXML
    public JFXCheckBox check;
    @FXML
    public JFXCheckBox cq1;
    @FXML
    public JFXCheckBox cq2;
    @FXML
    public JFXCheckBox cq3;
    @FXML
    public JFXCheckBox cq4;
    @FXML
    public JFXButton btn_pay;
    @FXML
    private JFXButton btn_search;
    @FXML
    public Text q1pidv;
    @FXML
    public Text q2pidv;
    @FXML
    public Text q3pidv;
    @FXML
    public Text q4pidv;


    /////////////////////////////////////
    modle.asses.PayObj payObj;

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }

    @FXML//Find Assessment By ID
    void idKeyReleased(KeyEvent event) {

    }

    @FXML
    void cbOnAction(ActionEvent event) {

    }

    @FXML
    void cheackValues(ActionEvent event) {

    }

    @FXML
    void clickOnPay(MouseEvent event) {

    }

    @FXML
    void clickOnSearchBTN(MouseEvent event) {

    }



    @FXML
    void onKeyRelesed(KeyEvent event) {

    }

    @FXML
    void payKeyRelesed(KeyEvent event) {

    }

}
