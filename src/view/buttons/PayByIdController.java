package view.buttons;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Ranga on 2019-03-06.
 */
public class PayByIdController implements Initializable, BTN{
    @FXML
    private ImageView img_t;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modle.StaticViews.getBtnConMap().put("/view/buttons/payByID.fxml", this);
        img_t.setImage(new Image("/grafics/app_w.png"));
    }

    @FXML
    private void clickOnAction(ActionEvent event) {
        AnchorPane container = modle.StaticViews.getContainer();
        container.getChildren().removeAll();
        container.getChildren().clear();
        AnchorPane dashh;
        try {
            dashh = FXMLLoader.load(getClass().getResource("/view/payment/payByID.fxml"));
            container.getChildren().add(dashh);
        } catch (IOException ex) {
            Logger.getLogger(Assign_user_btnController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Set<String> keySet = modle.StaticViews.getButtonMap().keySet();
        String s = "/view/buttons/payByID.fxml";

        for (String string : keySet) {

            System.out.println(string);

            if (string.equals(s)) {
                System.out.println("======================");
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
