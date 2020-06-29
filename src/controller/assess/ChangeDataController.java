/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.assess;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.hibernate.Session;
import pojo.AssQstart;

/**
 * FXML Controller class
 *
 * @author Ranga Rathnayake
 */
public class ChangeDataController implements Initializable {

    @FXML
    private JFXTextField txt_warrant;
    @FXML
    private JFXTextField txt_arrears;
    @FXML
    private JFXTextField txt_id;
    @FXML
    private Text txt_assess;
    @FXML
    private Text txt_customer;

    @FXML
    private JFXTextField txt_warrantQ;
    @FXML
    private JFXTextField txt_arrearsQ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void idType(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            getData(txt_id.getText());
        }
    }

    @FXML
    private void warantType(KeyEvent event) {

    }

    @FXML
    private void arrearsType(KeyEvent event) {

    }

    public void getData(String id) {
        String Quary = "SELECT\n"
                + "assessment.idAssessment,\n"
                + "ward.ward_name,\n"
                + "street.street_name,\n"
                + "customer.cus_name,\n"
                + "ass_allocation.ass_allocation,\n"
                + "ass_qstart.idass_Qstart,\n"
                + "ass_qstart.ass_Qstart_QuaterNumber,\n"
                + "ass_qstart.ass_Qstart_process_date,\n"
                + "ass_qstart.ass_Qstart_LY_Arreas,\n"
                + "ass_qstart.ass_Qstart_LY_Warrant,\n"
                + "ass_qstart.ass_Qstart_LYC_Arreas,\n"
                + "ass_qstart.ass_Qstart_LYC_Warrant,\n"
                + "ass_qstart.ass_Qstart_LQ_Arreas,\n"
                + "ass_qstart.ass_Qstart_LQC_Arreas,\n"
                + "ass_qstart.ass_Qstart_LQ_Warrant,\n"
                + "ass_qstart.ass_Qstart_LQC_Warrant,\n"
                + "ass_qstart.ass_Qstart_HaveToQPay,\n"
                + "ass_qstart.ass_Qstart_QPay,\n"
                + "ass_qstart.ass_Qstart_QDiscont,\n"
                + "ass_qstart.ass_Qstart_QTot,\n"
                + "ass_qstart.ass_Qstart_FullTotal,\n"
                + "ass_qstart.ass_Qstart_status,\n"
                + "ass_qstart.Assessment_idAssessment,\n"
                + "ass_qstart.ass_Qstart_year,\n"
                + "assessment.Customer_idCustomer,\n"
                + "assessment.Ward_idWard,\n"
                + "assessment.Street_idStreet,\n"
                + "assessment.ass_nature_idass_nature,\n"
                + "assessment.ass_discription_idass_discription,\n"
                + "assessment.User_idUser,\n"
                + "assessment.assessment_oder,\n"
                + "assessment.assessment_no,\n"
                + "assessment.assessment_status,\n"
                + "assessment.assessment_syn,\n"
                + "assessment.assessment_comment,\n"
                + "assessment.assessment_obsolete\n"
                + "FROM\n"
                + "assessment\n"
                + "LEFT JOIN ward ON assessment.Ward_idWard = ward.idWard\n"
                + "LEFT JOIN street ON street.Ward_idWard = ward.idWard AND assessment.Street_idStreet = street.idStreet\n"
                + "LEFT JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n"
                + "LEFT JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n"
                + "LEFT JOIN ass_qstart ON ass_qstart.Assessment_idAssessment = assessment.idAssessment\n"
                + "WHERE\n"
                + "ass_allocation.ass_allocation_status = 1 AND\n"
                + "ass_qstart.ass_Qstart_status = 1 AND\n"
                + "assessment.idAssessment = " + id;

        try {
            ResultSet data = conn.DB.getData(Quary);

            if (data.last()) {
                txt_assess.setText(data.getString("ward_name") + " " + data.getString("street_name") + " " + data.getString("assessment_no"));
                txt_customer.setText(data.getString("cus_name"));
                qstartid = data.getInt("idass_Qstart");
                

                txt_arrears.setText(data.getString("ass_Qstart_LQC_Arreas"));
                txt_warrant.setText(data.getString("ass_Qstart_LYC_Warrant"));
                txt_arrearsQ.setText(data.getString("ass_Qstart_LQC_Arreas"));
                txt_warrantQ.setText(data.getString("ass_Qstart_LQC_Warrant"));
                modle.Allert.notificationWorning(qstartid+"", "OK");
            }

        } catch (Exception e) {
            modle.ErrorLog.writeLog(e.getMessage(), "ChangeDataController.java", "getData", "Controller.assess");

        }

    }

    int qstartid = 0;

    public boolean update(double arrearsY, double warrantY, double arrearsQ, double warannatQ, int qsid) {

        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();

        try {
            AssQstart qs = (AssQstart) session.load(AssQstart.class, qsid);
            qs.setAssQstartLyArreas(arrearsY);
            qs.setAssQstartLycArreas(arrearsY);
            qs.setAssQstartLyWarrant(warrantY);
            qs.setAssQstartLycWarrant(warrantY);

            qs.setAssQstartLqArreas(arrearsQ);
            qs.setAssQstartLqcArreas(arrearsQ);
            qs.setAssQstartLqWarrant(warannatQ);
            qs.setAssQstartLqcWarrant(warannatQ);

            session.update(qs);
            session.beginTransaction().commit();
            return true;
        } catch (Exception e) {
            modle.ErrorLog.writeLog(e.getMessage(), "ChangeDataController", "update", "controller.assess");
            return false;
        } finally {
            session.close();
        }

    }

    @FXML
    private void clickOnUpdate(MouseEvent event) {

        try {

            double lyw = Double.parseDouble(txt_warrant.getText());

            double lya = Double.parseDouble(txt_arrears.getText());

            double qw = Double.parseDouble(txt_warrantQ.getText());

            double qa = Double.parseDouble(txt_arrearsQ.getText());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferm Message");
            alert.setHeaderText("You are going to update Arrears Warrant");
            alert.setContentText("If you want to apply this date \n click ok");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean update = update(lya, lyw, qa, qw, qstartid);
                if (update) {
                    modle.Allert.notificationGood("Update Compeeted", "");
                    cleare();
                } else {
                    modle.Allert.notificationGood("Not Updated", "");
                }
            } else {
                modle.Allert.notificationWorning("Not Update", "Please Check Values Again");
            }
        } catch (Exception e) {
            
            modle.Allert.notificationError("Please Check Again", "Please Check Values Again");
        }

    }

    public void cleare() {
        txt_arrears.setText("");
        txt_warrant.setText("");
        txt_arrearsQ.setText("");
        txt_warrantQ.setText("");
        txt_assess.setText("");
        txt_customer.setText("");
        qstartid = 0;
    }
}
