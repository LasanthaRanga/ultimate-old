package controller.users;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import conn.DB;
import controller.FXMLDocumentController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ForgotPass implements Initializable {

    @FXML
    private JFXTextField text_nic;

    @FXML
    private JFXTextField txt_user;

    @FXML
    private JFXTextArea txt_question;

    @FXML
    private JFXTextField txt_answer;

    @FXML
    private JFXButton btn_ok;

    @FXML
    private JFXPasswordField pword1;

    @FXML
    private JFXPasswordField pword2;

    @FXML
    private JFXButton btn_applay;

    int userid = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        btn_ok.setDisable(true);
        btn_applay.setDisable(true);
        txt_answer.setDisable(true);
        txt_question.setDisable(true);
        pword1.setDisable(true);
        pword2.setDisable(true);


    }

    @FXML
    void clickOnBack(MouseEvent event) {

        Parent root;
        try {
            // btn_singup.getParent().getScene().getWindow().hide();
            root = FXMLLoader.load(getClass().getResource("/cat/ultimate/FXMLDocument.fxml"));
            //   btn_go.getParent();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            modle.StaticViews.getPrimaryStage().setScene(scene);
            modle.StaticViews.getPrimaryStage().show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    @FXML
    void answerkeyRelesed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            checkAnswer(txt_answer.getText());
        }
    }

    @FXML
    void clickOnApplay(MouseEvent event) {
        reset();
    }

    @FXML
    void clickOnOk(MouseEvent event) {
        checkAnswer(txt_answer.getText());
    }

    @FXML
    void nickeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String text = text_nic.getText();
            System.out.println(text);
            getDataByNic(text_nic.getText());

        }
    }

    @FXML
    void p1keyRelesed(KeyEvent event) {

    }

    @FXML
    void p2keyRelesed(KeyEvent event) {

    }

    @FXML
    void userKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            getDataByUname(txt_user.getText());
        }

    }


    public void getDataByNic(String nic) {

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "`user`.idUser,\n" +
                    "`user`.user_full_name,\n" +
                    "`user`.user_nic,\n" +
                    "`user`.user_date,\n" +
                    "`user`.user_birth_day,\n" +
                    "`user`.user_status,\n" +
                    "`user`.user_syn,\n" +
                    "`user`.user_question,\n" +
                    "`user`.user_answer,\n" +
                    "`user`.user_username,\n" +
                    "`user`.user_password,\n" +
                    "`user`.user_level,\n" +
                    "`user`.office_idOffice,\n" +
                    "`user`.user_name_sinhala\n" +
                    "FROM\n" +
                    "`user`\n" +
                    "where user_nic = '" + nic + "'");


            if (data.last()) {
                modle.Allert.notificationGood("Answer The Qustion", "Reset Password");
                loadData(data);
            } else {
                modle.Allert.notificationInfo("Please Check NIC", nic);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }

    public void getDataByUname(String uname) {

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "`user`.idUser,\n" +
                    "`user`.user_full_name,\n" +
                    "`user`.user_nic,\n" +
                    "`user`.user_date,\n" +
                    "`user`.user_birth_day,\n" +
                    "`user`.user_status,\n" +
                    "`user`.user_syn,\n" +
                    "`user`.user_question,\n" +
                    "`user`.user_answer,\n" +
                    "`user`.user_username,\n" +
                    "`user`.user_password,\n" +
                    "`user`.user_level,\n" +
                    "`user`.office_idOffice,\n" +
                    "`user`.user_name_sinhala\n" +
                    "FROM\n" +
                    "`user`\n" +
                    "where user_username = '" + uname + "'");


            if (data.last()) {
                modle.Allert.notificationGood("Answer The Qustion", "Reset Password");
                loadData(data);
            } else {
                modle.Allert.notificationInfo("Please Check NIC", uname);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }


    public void loadData(ResultSet data) {
        try {
            userid = data.getInt("idUser");
            text_nic.setText(data.getString("user_nic"));
            txt_user.setText(data.getString("user_username"));
            txt_question.setText(data.getString("user_question"));
            txt_question.setDisable(false);
            txt_answer.setDisable(false);
            btn_ok.setDisable(false);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }

    int x = 0;

    public void checkAnswer(String answer) {

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "`user`.idUser,\n" +
                    "`user`.user_full_name,\n" +
                    "`user`.user_nic,\n" +
                    "`user`.user_date,\n" +
                    "`user`.user_birth_day,\n" +
                    "`user`.user_status,\n" +
                    "`user`.user_syn,\n" +
                    "`user`.user_question,\n" +
                    "`user`.user_answer,\n" +
                    "`user`.user_username,\n" +
                    "`user`.user_password,\n" +
                    "`user`.user_level,\n" +
                    "`user`.office_idOffice,\n" +
                    "`user`.user_name_sinhala\n" +
                    "FROM `user`\n" +
                    "WHERE idUser = '" + userid + "'");

            if (data.last()) {
                String user_answer = data.getString("user_answer");
                if (user_answer.equals(answer)) {
                    modle.Allert.notificationGood("Enter New Password", "And Reset");
                    pword1.setDisable(false);
                    pword2.setDisable(false);
                    btn_applay.setDisable(false);

                } else {
                    modle.Allert.notificationError("Answer is wrong", "Retry");
                    x++;
                    if (x >= 4) {
                        Platform.exit();
                        System.exit(0);
                    }
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }


    public void reset() {
        String text = pword1.getText();
        String text1 = pword2.getText();
        if (text.length() > 3 && text1.length() > 3) {


            if (text.equals(text1)) {
                try {
                    int i = DB.setData("UPDATE `user`\n" +
                            "SET \n" +
                            " `user_password` = '" + text + "' \n" +
                            "WHERE\n" +
                            "\t(`idUser` = '" + userid + "')");

                    if (i > 0) {
                        modle.Allert.notificationGood("Password Reset Succeed", "Go To Login");
                        clearaAll();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }

            } else {
                modle.Allert.notificationWorning(" don't match", "");
            }
        } else {
            modle.Allert.notificationInfo("too short", "length must be more then 4");
        }


    }

    public void clearaAll() {
        txt_question.setText(null);
        txt_answer.setText(null);
        pword2.setText(null);
        pword1.setText(null);
        text_nic.setText(null);
        txt_user.setText(null);
        btn_ok.setDisable(true);
        btn_applay.setDisable(true);
        txt_answer.setDisable(true);
        txt_question.setDisable(true);
        pword1.setDisable(true);
        pword2.setDisable(true);

    }


}
