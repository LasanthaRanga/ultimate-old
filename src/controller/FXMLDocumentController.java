/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import conn.DB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import modle.KeyVal;
import modle.user.User_Login;


/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private JFXButton btn_login;
    @FXML
    private JFXButton btn_singup;
    @FXML
    private JFXTextField txt_uname;
    @FXML
    private JFXPasswordField txt_pword;

    @FXML
    private ImageView iv_logo;


    @FXML
    private JFXButton btn_log;

    @FXML
    private JFXButton btn_foget;

    private final int version = 3;

    String urll = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        String logo = KeyVal.getVal("logo");
        try {

            iv_logo.setImage(new Image(logo));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            ResultSet datas = DB.getData("SHOW VARIABLES LIKE 'max_connections'");
            if (datas.last()) {
                int max_connections = datas.getInt("Value");
                System.out.println("Max Connecton "+ max_connections);
                if (max_connections < 10000) {
                    DB.setData("SET GLOBAL max_connections = 100000");
                    System.out.println("Max Connection Reset to 100000");
                }
            }


            ResultSet data = DB.getData("SELECT\n" +
                    "version.idVersion,\n" +
                    "version.vnoint,\n" +
                    "version.vnostring,\n" +
                    "version.vdate,\n" +
                    "version.priority,\n" +
                    "version.downlink,\n" +
                    "version.description, downpage,\n" +
                    "version.`status`\n" +
                    "FROM `version`\n" +
                    "WHERE\n" +
                    "version.`status` = 1");

            if (data.last()) {
                int vnoint = data.getInt("vnoint");
                urll = data.getString("downpage");
                if (version == vnoint) {
                    btn_update.setDisable(true);
                } else {
                    btn_update.setDisable(false);
                    btn_update.setText("Please Update First");
                    btn_login.setDisable(true);
                    btn_singup.setDisable(true);
                    txt_uname.setDisable(true);
                    txt_pword.setDisable(true);
                    btn_log.setDisable(true);
                    btn_foget.setDisable(true);

                }
            }


        } catch (Exception e) {
            e.printStackTrace();


        } finally {
        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3600);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                }
//                Platform.exit();
//                System.exit(0);
//            }
//        }).start();


    }

    @FXML
    private void loginClick(ActionEvent event) {
        login();
    }

    @FXML
    private void mouseRelesed(MouseEvent event) {
    }

    @FXML
    private void mouseDragged(MouseEvent event) {
    }

    @FXML
    private void mousePressed(MouseEvent event) {
    }

    @FXML
    private void singup(ActionEvent event) {

        Parent root;
        try {
            // btn_singup.getParent().getScene().getWindow().hide();
            root = FXMLLoader.load(getClass().getResource("/view/users/user_reg.fxml"));
            btn_singup.getParent();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            modle.StaticViews.getPrimaryStage().setScene(scene);
            modle.StaticViews.getPrimaryStage().show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void userKeyRelesed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txt_pword.requestFocus();
        }
    }

    @FXML
    private void passKeyRelesed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    public void login() {

        String u = txt_uname.getText();
        String p = txt_pword.getText();
        User_Login ul = new modle.user.User_Login();
        boolean login = ul.login(u, p);
        if (login) {
            System.out.println("LOG");
            ul.login(modle.StaticViews.getLogUser().getIdUser());

            Parent root;
            try {
                btn_singup.getParent().getScene().getWindow().hide();
                root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));

                btn_singup.getParent();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                modle.StaticViews.getPrimaryStage().setScene(scene);
                modle.StaticViews.getPrimaryStage().show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            modle.Allert.notificationWorning("Wrong", "Recheack Username And Password");

            System.out.println("UNN");
        }

    }

    @FXML
    void clickOnFoget(MouseEvent event) {

        Parent root;
        try {
            // btn_singup.getParent().getScene().getWindow().hide();
            root = FXMLLoader.load(getClass().getResource("/view/users/ForgotPass.fxml"));
            btn_singup.getParent();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            modle.StaticViews.getPrimaryStage().setScene(scene);
            modle.StaticViews.getPrimaryStage().show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    @FXML
    private JFXButton btn_update;


    @FXML
    void btn_updateOnAction(ActionEvent event) {
        try {
            System.out.println("clickOnUpdate");
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(urll));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        System.out.println("EXIT");
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void close(ActionEvent event) {
        System.out.println("EXIT");
        Platform.exit();
        System.exit(0);
    }

}
