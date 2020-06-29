package controller;

import com.jfoenix.controls.JFXButton;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.VBox;
import modle.KeyVal;
import modle.user.Privilege;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class BoxController implements Initializable {

    @FXML
    private VBox vbox1;
    @FXML
    private VBox vbox2;

    @FXML
    private ImageView logo;


    private modle.user.Privilege prv;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String logos = KeyVal.getVal("logo");
        try {

            logo.setImage(new Image(logos));
        } catch (Exception e) {
            e.printStackTrace();
        }


        modle.StaticViews.setBoxController(this);


        prv = new Privilege();

        ArrayList<String> privilagesList = prv.getPrivilageListByUserQuary(modle.StaticViews.getLogUser());


        try {
            if (privilagesList != null) {
                HashMap<String, JFXButton> buttoneMap = new HashMap();
                for (String privilage : privilagesList) {
//                    System.out.println(privilage +"    ==============");
                    JFXButton btn = FXMLLoader.load(getClass().getResource(privilage));
                    btn.setAccessibleText(privilage);
                    vbox2.getChildren().add(btn);
                    buttoneMap.put(privilage, btn);
                }
                modle.StaticViews.setButtonMap(buttoneMap);
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickOnAction(ActionEvent event) {
    }

    public void clickOnAction(String text, ImageView image) {
    }
}
