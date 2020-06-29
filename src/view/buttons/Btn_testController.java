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
public class Btn_testController implements Initializable ,BTN{

    public JFXButton btn_t1;


    @FXML
    public ImageView img_t;
    @FXML
    private JFXButton btn_dipartment;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clickOnAction(ActionEvent event) {
        modle.StaticViews.getBoxController().clickOnAction(btn_t1.getAccessibleText(), img_t);
    }

    @Override
    public void setImage() {
        
    }

    @Override
    public void setImage(String imagePath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
