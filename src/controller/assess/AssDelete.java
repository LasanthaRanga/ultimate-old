package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import modle.asses.AssDeleteRecipt;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ranga Rathnayake on 2020-08-27.
 */
public class AssDelete implements Initializable {

    @FXML
    public JFXTextField recipt_no;

    @FXML
    public JFXButton btn_delete;

    @FXML
    public Label idRecipt;

    @FXML
    void clickOnDelete(MouseEvent event) {


    }

    @FXML
    void enterRNo(KeyEvent event) {
        idRecipt.setText("");
        btn_delete.setDisable(true);
        if (event.getCode() == KeyCode.ENTER) {

            AssDeleteRecipt adr = new AssDeleteRecipt(this);

            adr.getReciptData(recipt_no.getText());

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_delete.setDisable(true);
    }


}
