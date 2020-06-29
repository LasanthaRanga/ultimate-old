package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import conn.NewHibernateUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.AssPayhistry;
import pojo.AssQstart;
import pojo.Assessment;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;


public class ErrorFix implements Initializable {

    @FXML
    private JFXTextField txt_lyw;

    @FXML
    private JFXTextField txt_lya;

    @FXML
    private JFXTextField txt_lqa;

    @FXML
    private JFXTextField txt_lqw;

    @FXML
    private JFXTextField txt_q1p;

    @FXML
    private JFXTextField txt_q1dr;

    @FXML
    private JFXTextField txt_q1d;

    @FXML
    private JFXTextField txt_q2p;

    @FXML
    private JFXTextField txt_q2dr;

    @FXML
    private JFXTextField txt_q2d;

    @FXML
    private JFXTextField txt_q3p;

    @FXML
    private JFXTextField txt_q3dr;

    @FXML
    private JFXTextField txt_q3d;

    @FXML
    private JFXTextField txt_q4p;

    @FXML
    private JFXTextField txt_q4dr;

    @FXML
    private JFXTextField txt_q4d;

    @FXML
    private JFXTextField txt_over;

    @FXML
    private JFXTextField txt_lycw;

    @FXML
    private JFXTextField txt_lyca;

    @FXML
    private JFXTextField txt_lqca;

    @FXML
    private JFXTextField txt_lqcw;

    @FXML
    private JFXTextField id_assess;

    @FXML
    private JFXButton btn_update;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    @FXML
    void clickOnUpdate(MouseEvent event) {

    }

    @FXML
    void idKeyRelesed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

        }
    }


    public void LoadData() {



        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().commit();
        try {

            Assessment assessment = (Assessment) session.load(Assessment.class, Integer.parseInt(id_assess.getText()));
            Set<AssQstart> assQstarts = assessment.getAssQstarts();
            for (AssQstart qss : assQstarts) {
                Integer assQstartYear = qss.getAssQstartYear();

            }


        } catch (Exception e) {
            e.printStackTrace();
            modle.ErrorLog.writeLog(e.getMessage(), "ErrorFix", "loadData", "modle.assess.ErrorFix");
        } finally {
            session.close();
        }
    }


}
