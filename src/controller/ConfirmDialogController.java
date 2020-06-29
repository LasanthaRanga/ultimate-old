/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class ConfirmDialogController implements Initializable {

    @FXML
    private Label txt_title;
    @FXML
    private Label txt_discription;
    @FXML
    private JFXButton btn_yes;
    @FXML
    private JFXButton btn_no;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txt_title.setText(modle.ConfirmDialog.getTitle());
        txt_discription.setText(modle.ConfirmDialog.getMg());

    }

    @FXML
    private void clickYes(MouseEvent event) {
        modle.ConfirmDialog.setConferm(true);
        modle.ConfirmDialog.setClick(true);
        btn_yes.getParent().getScene().getWindow().hide();
    }

    @FXML
    private void clickNo(MouseEvent event) {
        modle.ConfirmDialog.setConferm(false);
        modle.ConfirmDialog.setClick(true);
        btn_no.getParent().getScene().getWindow().hide();
    }

}
