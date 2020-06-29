/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Ranga Rathnayake
 */
public class DeactiveController implements Initializable {

    @FXML
    public JFXTextField txt_id;
    @FXML
    public Text txt_ward;
    @FXML
    public Text txt_street;
    @FXML
    public Text txt_assessmant;
    @FXML
    public Text txt_oder;
    @FXML
    public Text txt_customer;
    @FXML
    public Text txt_allocation;

    public modle.asses.DeactiveAssessmant da_modle;
    @FXML
    private JFXButton btn_remove;

    public int x;
    @FXML
    private JFXTextArea txt_comment;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        da_modle = new modle.asses.DeactiveAssessmant();
        modle.StaticViews.getMc().changeTitle("Delete Property");

    }

    @FXML
    private void onKeyReleased(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            da_modle.setDac(this);
            da_modle.loadAssess(Integer.parseInt(txt_id.getText()));
        }

    }

    @FXML
    private void btn_onclick(KeyEvent event) {
        //  System.out.println("asdf");

    }

    @FXML
    private void clickOnRemove(MouseEvent event) {
        System.out.println("asdfasdf");
        //
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deactivate");
        alert.setHeaderText("You are going to delete this assessment \n Are you sure to delete this? \n Click Ok");
        alert.setContentText("If you want to cancle this. \n Click Cancel");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            da_modle.deactiveAssessment(x, txt_comment.getText());
            clean();
        } else {
            // ... user chose CANCEL or closed the dialog්‍    
             modle.Allert.notificationInfo("Cancled Deactive", "ID : " + x);
            clean();
        }

    }

    public void clean() {
        x = 0;
        txt_id.setText("");
        txt_ward.setText("");
        txt_street.setText("");
        txt_assessmant.setText("");
        txt_oder.setText("");
        txt_customer.setText("");
        txt_allocation.setText("");
    }

}
