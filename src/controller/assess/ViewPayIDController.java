/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Ranga Rathnayake
 */
public class ViewPayIDController implements Initializable {

    @FXML
    private Text txt_id;
    @FXML
    private Text txt_tot;
    @FXML
    private JFXButton btn_ok;
    @FXML
    private JFXTextField txt_nic;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txt_id.setText(modle.asses.StaticBadu.getPayid());
        txt_tot.setText(modle.asses.StaticBadu.getPayTot());
    }

    @FXML
    private void clickOnOk(MouseEvent event) {
        modle.asses.StaticBadu.setPayTot("Total Rs : 00 /=");
        modle.asses.StaticBadu.setPayid("ID : 00");
        btn_ok.getScene().getWindow().hide();
        int cusid = modle.asses.StaticBadu.getCusid();
        String nic = txt_nic.getText();

        if (txt_nic.getText().length() > 0) {
            try {
                conn.DB.setData("UPDATE customer\n"
                        + "SET  \n"
                        + " cus_nic = '" + nic + "'\n"
                        + "WHERE\n"
                        + "	(idCustomer = '" + cusid + "')");
            } catch (Exception e) {
                modle.ErrorLog.writeLog(e.getMessage(), "ViewPayIDController", "clickOnOk", "controller.assess");
            }
        }

    }

}
