package controller.assess;

import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import modle.GetInstans;
import modle.asses.ChangeAllocation;
import modle.asses.PayNowModle;
import modle.asses.ChangeAllocation.changeHistry;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ranga on 2019-02-05.
 */
public class AllocationChangeController implements Initializable {

    @FXML
    public JFXTextField txt_idAssess;

    @FXML
    public Text txt_ward;

    @FXML
    public Text txt_street;

    @FXML
    public Text txt_assessment;

    @FXML
    public Text txt_customer;

    @FXML
    public Text txt_nature;

    @FXML
    public Text txt_quater;

    @FXML
    public Text txt_allocation;

    @FXML
    public Text txt_arrias;

    @FXML
    public Text txt_warrant;


    @FXML
    public Text txt_creditDebit;

    @FXML
    public JFXComboBox<String> com_nature;

    @FXML
    public JFXTextField txt_newAllocation;

    @FXML
    public JFXRadioButton radio_Debit;

    @FXML
    public ToggleGroup cd;

    @FXML
    public JFXRadioButton radio_Credit;

    @FXML
    public JFXTextField txt_newCredit;

    @FXML
    public JFXTextArea txt_description;

    @FXML
    public JFXButton btn_Save;

    @FXML
    public TableView<changeHistry> tbl_histry;

    @FXML
    public TableColumn<changeHistry, String> col_date;

    @FXML
    public TableColumn<changeHistry, Double> col_allocation;

    @FXML
    public TableColumn<changeHistry, Double> col_new_allocation;

    @FXML
    public TableColumn<changeHistry, String> col_nature;

    @FXML
    public TableColumn<changeHistry, String> col_new_nature;

    @FXML
    public TableColumn<changeHistry, Double> col_cd;

    @FXML
    public TableColumn<changeHistry, String> col_discription;

    @FXML
    public TableColumn<changeHistry, String> col_change;

    @FXML
    public JFXCheckBox check_new;


    @FXML
    public Text txt_overpay;

    @FXML
    public JFXTextField txt_balance;

    @FXML
    void checkNewOnAction(ActionEvent event) {
        if (check_new.isSelected()) {
            txt_newAllocation.setDisable(false);
        } else {
            txt_newAllocation.setDisable(true);
        }
    }

    ChangeAllocation changeAllocation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeAllocation = new ChangeAllocation(this);
        ObservableList<String> natureObservableListSQL = GetInstans.getNature().getNatureObservableListSQL();
        com_nature.setItems(natureObservableListSQL);
        modle.StaticViews.getMc().changeTitle("Allocation Change And Credit Debit");
    }


    double overpayment = 0.0;

    @FXML
    void onKeyReleasedAssessmentID(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            clearAll();
            changeAllocation.getAllocation();
            String overPay = changeAllocation.getOverPay();
            txt_overpay.setText(overPay);
            overpayment = Double.parseDouble(overPay);
        }
        if (txt_idAssess.getText().length() <= 0) {
            clearAll();
            changeAllocation.getAllocation();

        }
    }


    @FXML
    void keyUpDebit(KeyEvent event) {
        try {
            double deb = 0.0;
            if (txt_newCredit.getText().length() > 0) {
                deb = Double.parseDouble(txt_newCredit.getText());
            } else {
                deb = 0.0;
            }

            double ove = overpayment;

            double bal = 0;
            double over = 0;

            if (deb >= ove) {
                bal = deb - ove;
                over = 0;

            } else {
                over = ove - deb;
                bal = 0;
            }

            txt_balance.setText(modle.Round.round(bal)+"");
            txt_overpay.setText(modle.Round.roundFormat(over));


        } catch (Exception e) {

        }


    }


    @FXML
    void onMouseClickedSaveButton(MouseEvent event) {
        double cd = 0;

        String text = txt_balance.getText();
        try {
            cd = Double.parseDouble(text);


            if (radio_Credit.isSelected()) {
                cd = cd * -1;
                if (cd <= 0) {
                    changeAllocation.saveAllocationChangeHistory(cd);
                    //  btn_Save.setDisable(true);
                } else {
                    modle.Allert.notificationInfo("Credit Value Must Be - ", "Please Check It");
                }
            } else if (radio_Debit.isSelected()) {
                if (cd >= 0) {
                    changeAllocation.saveAllocationChangeHistory(cd);
                    //  btn_Save.setDisable(true);
                    changeAllocation.updateOverPay(txt_overpay.getText());
                } else {
                    modle.Allert.notificationInfo("Debit Value Must Be + ", "Please Check It");
                }
            } else {
                if (cd == 0) {
                    changeAllocation.saveAllocationChangeHistory(cd);
                    //   btn_Save.setDisable(true);
                } else {
                    modle.Allert.notificationInfo("Credit Debit Value Must Be 00 ", "Please Check It");
                }
            }

        } catch (Exception e) {
            modle.Allert.notificationInfo("Credit Debit Value Error", "Please Check It");
        }

    }

    @FXML
    void radioOnAction(ActionEvent event) {
        if (radio_Credit.isSelected()) {

        }
        if (radio_Debit.isSelected()) {

        }
    }

    public void clearAll() {
        btn_Save.setDisable(false);
        txt_ward.setText("");
        txt_street.setText("");
        txt_assessment.setText("");
        txt_customer.setText("");
        txt_nature.setText("");
        txt_quater.setText("");
        txt_allocation.setText("");
        txt_creditDebit.setText("");
        txt_newAllocation.setText("");
        radio_Debit.setSelected(false);
        radio_Credit.setSelected(false);
        txt_newCredit.setText("");
        txt_description.setText("");
        txt_overpay.setText("");
    }


}
