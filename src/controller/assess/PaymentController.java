package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import modle.asses.AssesProcessType;


public class PaymentController implements Initializable {

    @FXML
    private JFXTextField txt_idAssess;
    @FXML
    private JFXButton btnPay;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clickOnPay(MouseEvent event) {
        int id  = Integer.parseInt(txt_idAssess.getText());
      
    }
    
}
