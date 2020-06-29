package view.buttons;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class Btn_testController_1 implements Initializable {

   
    @FXML
    public JFXButton btn_t2;
    @FXML
    private ImageView img_t2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clickOnAction(ActionEvent event) {
        modle.StaticViews.getBoxController().clickOnAction(btn_t2.getAccessibleText(), img_t2);
    }

}
