/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class ConfirmDialog implements Runnable {

    public ConfirmDialog showConfirm() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ConfirmDialog.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.getIcons().add(new Image("/grafics/info.png"));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ConfirmDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    /**
     * @return the title
     */
    public static String getTitle() {
        return title;
    }

    /**
     * @param aTitle the title to set
     */
    public static void setTitle(String aTitle) {
        title = aTitle;
    }

    /**
     * @return the mg
     */
    public static String getMg() {
        return mg;
    }

    /**
     * @param aMg the mg to set
     */
    public static void setMg(String aMg) {
        mg = aMg;
    }

    /**
     * @return the conferm
     */
    public static boolean isConferm() {
        return conferm;
    }

    /**
     * @param aConferm the conferm to set
     */
    public static void setConferm(boolean aConferm) {
        conferm = aConferm;
    }

    private static String title;
    private static String mg;
    private static boolean conferm;
    private static boolean click = false;

    public void cheack() {
        boolean b = true;
        while (b) {
            if (click) {
                b = false;
            }
        }
    }

    /**
     * @return the click
     */
    public static boolean isClick() {
        return click;
    }

    /**
     * @param aClick the click to set
     */
    public static void setClick(boolean aClick) {
        click = aClick;
    }

    @Override
    public void run() {
        boolean b = true;
        while (b) {
            if (click) {
                b = false;
            }
        }
    }

}
