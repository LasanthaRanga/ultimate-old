/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modle;

import com.jfoenix.controls.JFXButton;

import java.util.HashMap;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.buttons.BTN;

/**
 * @author RM.LasanthaRanga@gmail.com
 */
public class StaticViews {

    private static pojo.User logUser;
    private static pojo.UserLogin log;
    private static controller.BoxController boxController;
    private static Stage primaryStage;
    private static AnchorPane container;
    private static HashMap<String, JFXButton> buttonMap;
    private static HashMap<String, BTN> btnConMap = new HashMap<>();
    private static controller.MainController mc;
    private static boolean print = false;


    /**
     * @return the boxController
     */
    public static controller.BoxController getBoxController() {
        return boxController;
    }

    /**
     * @param aBoxController the boxController to set
     */
    public static void setBoxController(controller.BoxController aBoxController) {
        boxController = aBoxController;
    }

    /**
     * @return the primaryStage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * @param aPrimaryStage the primaryStage to set
     */
    public static void setPrimaryStage(Stage aPrimaryStage) {
        primaryStage = aPrimaryStage;
    }

    /**
     * @return the logUser
     */
    public static pojo.User getLogUser() {
        return logUser;
    }

    /**
     * @param aLogUser the logUser to set
     */
    public static void setLogUser(pojo.User aLogUser) {
        logUser = aLogUser;
    }

    /**
     * @return the log
     */
    public static pojo.UserLogin getLog() {
        return log;
    }

    /**
     * @param aLog the log to set
     */
    public static void setLog(pojo.UserLogin aLog) {
        log = aLog;
    }

    /**
     * @return the container
     */
    public static AnchorPane getContainer() {
        return container;
    }

    /**
     * @param aContainer the container to set
     */
    public static void setContainer(AnchorPane aContainer) {
        container = aContainer;
    }

    /**
     * @return the buttonMap
     */
    public static HashMap<String, JFXButton> getButtonMap() {
        return buttonMap;
    }

    /**
     * @param aButtonMap the buttonMap to set
     */
    public static void setButtonMap(HashMap<String, JFXButton> aButtonMap) {
        buttonMap = aButtonMap;
    }

    /**
     * @return the btnConMap
     */
    public static HashMap<String, BTN> getBtnConMap() {
        return btnConMap;
    }

    /**
     * @param aBtnConMap the btnConMap to set
     */
    public static void setBtnConMap(HashMap<String, BTN> aBtnConMap) {
        btnConMap = aBtnConMap;
    }

    /**
     * @return the mc
     */
    public static controller.MainController getMc() {
        return mc;
    }

    /**
     * @param aMc the mc to set
     */
    public static void setMc(controller.MainController aMc) {
        mc = aMc;
    }

    public static void setPrint(boolean print) {
        StaticViews.print = print;
    }

    public static boolean isPrint() {
        return print;
    }

}
