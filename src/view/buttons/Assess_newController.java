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

public class Assess_newController implements Initializable, BTN {

    @FXML
    private ImageView img_t;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modle.StaticViews.getBtnConMap().put("/view/buttons/assess_new.fxml", this);
        img_t.setImage(new Image("/grafics/privilage_w.png"));
    }

    @FXML
    private void clickOnAction(ActionEvent event) {
        AnchorPane container = modle.StaticViews.getContainer();
        container.getChildren().removeAll();
        container.getChildren().clear();
        AnchorPane dashh;
        try {
            dashh = FXMLLoader.load(getClass().getResource("/view/assess/new.fxml"));
            container.getChildren().add(dashh);
        } catch (IOException ex) {
            Logger.getLogger(Assign_user_btnController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Set<String> keySet = modle.StaticViews.getButtonMap().keySet();
        String s = "/view/buttons/assess_new.fxml";

        for (String string : keySet) {
            if (string.equals(s)) {
                JFXButton get = modle.StaticViews.getButtonMap().get(string);
                get.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #03A9F4;");
                img_t.setImage(new Image("/grafics/privilage_b.png"));

            } else {
                JFXButton btn = modle.StaticViews.getButtonMap().get(string);
                btn.setStyle("");
                btn.setStyle("-fx-background-color: #03A9F4; -fx-text-fill: #FFFFFF;");
                BTN get = modle.StaticViews.getBtnConMap().get(string);
                if (get != null) {
                    get.setImage();
                }
            }

        }
    }

    @Override
    public void setImage() {
        img_t.setImage(new Image("/grafics/privilage_w.png"));
    }

    @Override
    public void setImage(String imagePath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
