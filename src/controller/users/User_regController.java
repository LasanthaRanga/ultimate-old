package controller.users;

import controller.FXMLDocumentController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import modle.asses.OfficeHolder;
import modle.user.UserReg;
import pojo.User;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class User_regController implements Initializable {

    @FXML
    private JFXTextField txt_fullname;
    @FXML
    private JFXTextField txt_nic;
    @FXML
    private JFXTextField txt_uname;
    @FXML
    private JFXPasswordField txt_pword;
    @FXML
    private JFXTextField txt_answer;
    @FXML
    private JFXComboBox<String> com_question;
    @FXML
    private JFXButton btn_save;
    @FXML
    private JFXButton btn_go;
    @FXML
    private AnchorPane birthday;
    @FXML
    private JFXDatePicker bdate;
    @FXML
    private JFXTextField txt_mobileNo;

    @FXML
    private JFXComboBox<OfficeHolder> com_user;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getSQ();
        com_question.setItems(List);
        com_user.setItems(modle.GetInstans.getOffice().loadOfficeCombo());

    }

    @FXML
    private void save(ActionEvent event) {

        String full_name = txt_fullname.getText();
        String nic = txt_nic.getText();
        String uname = txt_uname.getText();
        String pword = txt_pword.getText();
        String answer = txt_answer.getText();
        String question = com_question.getSelectionModel().getSelectedItem();

        //new SimpleDateFormat("yyyy-MM-dd").format(date);
        if (full_name.length() > 2) {
            if (nic.length() > 9) {
                if (uname.length() > 2) {
                    //cheack username exxist
                    if (new modle.user.UserReg().userExsist(uname)) {
                        modle.Allert.notificationError("ERROR", "This Username Already Exist \n please Enter Other One");
                    } else {
                        if (pword.length() > 3) {
                            if (!com_question.getSelectionModel().isEmpty()) {
                                if (answer.length() > 1) {
                                    if (bdate.getValue() != null) {
                                        if (com_user.getSelectionModel().getSelectedItem() != null) {
                                            Date date = Date.from(Instant.from(bdate.getValue().atStartOfDay(ZoneId.systemDefault())));

                                            User user = new User();
                                            user.setUserFullName(full_name);
                                            user.setUserBirthDay(date);
                                            user.setUserDate(new Date());
                                            user.setUserNic(nic);
                                            user.setUserUsername(uname);
                                            user.setUserPassword(pword);
                                            user.setUserQuestion(question);
                                            user.setUserAnswer(answer);
                                            user.setUserStatus(1);
                                            user.setUserSyn(1);
                                            user.setOfficeIdOffice(com_user.getSelectionModel().getSelectedItem().getIdOffice());
                                            int i = new UserReg().saveUser(user);
                                            if (i > 0) {
                                                try {
                                                    conn.DB.setData("UPDATE `user`\n" +
                                                            "SET \n" +
                                                            " `mobile_no` = '"+txt_mobileNo.getText()+"'\n" +
                                                            "WHERE\n" +
                                                            "\t(`idUser` = '" + i + "')");

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                modle.Allert.notificationGood("Save", nic);
                                            }
                                        } else {
                                            modle.Allert.notificationError("ERROR", "Select Office");
                                        }

                                    } else {
                                        modle.Allert.notificationError("ERROR", "Select Your Birth Day");
                                    }
                                } else {
                                    modle.Allert.notificationError("ERROR", "Answer is Short");
                                }
                            } else {
                                modle.Allert.notificationError("ERROR", "Select Question");
                            }
                        } else {
                            modle.Allert.notificationError("ERROR", "Password is Short");
                        }
                    }
                } else {
                    modle.Allert.notificationError("ERROR", "Cheack User Name");
                }
            } else {
                modle.Allert.notificationError("ERROR", "Cheack NIC");
            }
        } else {
            modle.Allert.notificationError("ERROR", "Cheack Full Name");
        }

    }

    @FXML
    private void gotoLogin(ActionEvent event) {

        Parent root;
        try {
            // btn_singup.getParent().getScene().getWindow().hide();
            root = FXMLLoader.load(getClass().getResource("/cat/ultimate/FXMLDocument.fxml"));
            btn_go.getParent();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            modle.StaticViews.getPrimaryStage().setScene(scene);
            modle.StaticViews.getPrimaryStage().show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    ObservableList<String> List = FXCollections.observableArrayList();

    public ObservableList getSQ() {
        List.add("What was your childhood nickname?");
        List.add("What is the middle name of your oldest child?");
        List.add("What is your favorite movie?");
        List.add("What was your favorite sport in high school?");
        List.add("What is the first name of the boy or girl that you first kissed?");
        List.add("What was the name of the hospital where you were born?");
        List.add("What school did you attend for sixth grade?");
        List.add("In what town was your first job?");
        List.add("What was the name of the company where you had your first job?");
        return List;
    }

}
