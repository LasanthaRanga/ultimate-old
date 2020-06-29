package controller.popup;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modle.popup.BarcodeStatic;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Barcode implements Initializable {
    @FXML
    private Text txt_subject;

    @FXML
    private Text txt_customer;

    @FXML
    private Text txt_total;

    @FXML
    private Text txt_id;

    @FXML
    private JFXButton btn_print;

    @FXML
    private JFXButton btn_done;

    int print = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
    }


    @FXML
    void clickOnDone(MouseEvent event) {
        if (print == 1) {
            clearData();
            System.out.println("Clear");


            Stage stage = (Stage) btn_print.getScene().getWindow();
            stage.close();
            clearData();
        }else{

            System.out.println("Confirm");
          //  modle.Allert.notificationGood("OK", "Success " );

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Are you sure to close");

            alert.setHeaderText(null);

            alert.setContentText("Didn't Print Barcode are you sure to close this");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                System.out.println("OK OK");
                Stage stage = (Stage) btn_print.getScene().getWindow();
                stage.close();
                clearData();
            } else {
                // ... user chose CANCEL or closed the dialog
                System.out.println("NOT");
            }
        }
    }

    @FXML
    void clickOnPrint(MouseEvent event) {
        print = 1;
        modle.GetInstans.getPrintBarcode().print(BarcodeStatic.customerName,BarcodeStatic.idRecipt+"",modle.Round.roundToString(BarcodeStatic.reTotal) ,BarcodeStatic.subject);

    }


    public void setData() {
        txt_customer.setText(BarcodeStatic.customerName);
        txt_subject.setText(BarcodeStatic.subject);
        txt_id.setText(BarcodeStatic.idRecipt + "");
        txt_total.setText("Rs. " + modle.Round.roundToString(BarcodeStatic.reTotal) + " /=");
    }

    public void clearData() {
        BarcodeStatic.customerName = null;
        BarcodeStatic.subject = null;
        BarcodeStatic.idRecipt = 0+"";
        BarcodeStatic.reTotal = 0;
    }


}
