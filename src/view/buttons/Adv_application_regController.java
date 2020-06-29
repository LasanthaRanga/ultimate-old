package view.buttons;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class Adv_application_regController implements Initializable, BTN {

    @FXML
    private ImageView img_t;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modle.StaticViews.getBtnConMap().put("/view/buttons/adv_application_reg.fxml", this);
        img_t.setImage(new Image("/grafics/app_w.png"));
    }

    @FXML
    private void clickOnAction(ActionEvent event) {
        AnchorPane container = modle.StaticViews.getContainer();
        container.getChildren().removeAll();
        container.getChildren().clear();
        AnchorPane dashh;
        try {
            dashh = FXMLLoader.load(getClass().getResource("/view/adv/application.fxml"));
            container.getChildren().add(dashh);
        } catch (IOException ex) {
            Logger.getLogger(Assign_user_btnController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Set<String> keySet = modle.StaticViews.getButtonMap().keySet();
        String s = "/view/buttons/adv_application_reg.fxml";

        for (String string : keySet) {
            if (string.equals(s)) {
                try {
                    JFXButton get = modle.StaticViews.getButtonMap().get(string);
                    img_t.setImage(new Image("/grafics/app_b.png"));
                    get.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #03A9F4;");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    JFXButton btn = modle.StaticViews.getButtonMap().get(string);
                    btn.setStyle("-fx-background-color: #03A9F4; -fx-text-fill: #FFFFFF;");
                    BTN get = modle.StaticViews.getBtnConMap().get(string);
                    if (get != null) {
                        get.setImage();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setImage() {
        this.img_t.setImage(new Image("/grafics/app_w.png"));
    }

    @Override
    public void setImage(String imagePath) {
        this.img_t.setImage(new Image(imagePath));
    }

}
