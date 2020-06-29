/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;

import java.net.URL;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import conn.DB;
import conn.NewHibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import modle.asses.OfficeHolder;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.AssPayment;
import pojo.AssPayto;

/**
 * FXML Controller class
 *
 * @author Ranga Rathnayake
 */
public class HomeAssessController implements Initializable {

    @FXML
    private Text txt_warrant;
    @FXML
    private Text txt_arrears;
    @FXML
    private Text txt_total;
    @FXML
    private Label txt_over;
    @FXML
    private Label txt_totalFull;
    @FXML
    private JFXDatePicker day_form;
    @FXML
    private JFXDatePicker day_to;
    @FXML
    private JFXButton btn_load;
    @FXML
    private Text q1pay;

    @FXML
    private Text q2pay;

    @FXML
    private Text q3pay;

    @FXML
    private Text q4pay;
    @FXML
    private PieChart chart;

    @FXML
    private JFXComboBox<OfficeHolder> com_office;

    DecimalFormat df;


    double warrant;
    double arrias;
    double q1, q2, q3, q4, over, total;

    public void clear() {
        warrant = 0;
        arrias = 0;
        q1 = 0;
        q2 = 0;
        q3 = 0;
        q4 = 0;
        over = 0;
        total = 0;
        pieData.clear();
    }

    ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        modle.StaticViews.getMc().changeTitle("Dashboard");
        com_office.setItems(modle.GetInstans.getOffice().loadOfficeCombo());
    }


    public void getTotals() {

        OfficeHolder selectedItem = com_office.getSelectionModel().getSelectedItem();
        int officeid = 0;
        if (selectedItem != null) {
            officeid = selectedItem.getIdOffice();
        }


        clear();
        if (day_form.getValue() != null && day_to.getValue() != null) {
            Date form = Date.from(day_form.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
            Date to = Date.from(day_to.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            try {
                List<AssPayment> list = session.createCriteria(AssPayment.class)
                        .add(Restrictions.eq("assPaymentStatus", 1))
                        .add(Restrictions.between("assPaymentDate", form, to)).list();

                for (AssPayment ap : list) {

                    int x = 0;
                    boolean y = true;
                    ResultSet data = DB.getData("SELECT\n" +
                            "ass_payment.Receipt_idReceipt,\n" +
                            "receipt.office_idOffice,\n" +
                            "receipt.idReceipt\n" +
                            "FROM\n" +
                            "ass_payment\n" +
                            "INNER JOIN receipt ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                            "WHERE\n" +
                            "ass_payment.idass_Payment = " + ap.getIdassPayment());

                    if (data.last()) {
                        x = data.getInt("office_idOffice");
                    } else {
                        y = false;
                    }

                    if (y) {
                        if (officeid == 0) {
                            warrant += ap.getAssPaymentLyWarrant();
                            arrias += ap.getAssPaymentLyArrears();
                            over += ap.getAssPaymentGotoDebit();
                            total += ap.getAssPaymentFullTotal();
                            Set<AssPayto> assPaytos = ap.getAssPaytos();
                            for (AssPayto pt : assPaytos) {
                                if (pt.getAssPaytoQno() == 1) {
                                    q1 += pt.getAssPaytoQvalue();
                                } else if (pt.getAssPaytoQno() == 2) {
                                    q2 += pt.getAssPaytoQvalue();
                                } else if (pt.getAssPaytoQno() == 3) {
                                    q3 += pt.getAssPaytoQvalue();
                                } else if (pt.getAssPaytoQno() == 4) {
                                    q4 += pt.getAssPaytoQvalue();
                                }
                            }
                        } else if (x == officeid) {
                            warrant += ap.getAssPaymentLyWarrant();
                            arrias += ap.getAssPaymentLyArrears();
                            over += ap.getAssPaymentGotoDebit();
                            total += ap.getAssPaymentFullTotal();
                            Set<AssPayto> assPaytos = ap.getAssPaytos();
                            for (AssPayto pt : assPaytos) {
                                if (pt.getAssPaytoQno() == 1) {
                                    q1 += pt.getAssPaytoQvalue();
                                } else if (pt.getAssPaytoQno() == 2) {
                                    q2 += pt.getAssPaytoQvalue();
                                } else if (pt.getAssPaytoQno() == 3) {
                                    q3 += pt.getAssPaytoQvalue();
                                } else if (pt.getAssPaytoQno() == 4) {
                                    q4 += pt.getAssPaytoQvalue();
                                }
                            }

                        }
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }


            txt_warrant.setText(String.format("%,.2f", warrant));
            txt_arrears.setText(String.format("%,.2f", arrias));
            q1pay.setText(String.format("%,.2f", q1));
            q2pay.setText(String.format("%,.2f", q2));
            q3pay.setText(String.format("%,.2f", q3));
            q4pay.setText(String.format("%,.2f", q4));
            txt_total.setText(String.format("%,.2f", total));
            txt_over.setText(String.format("%,.2f", over));
            txt_totalFull.setText(String.format("%,.2f", total + over));

            pieData.clear();
            pieData.add(new PieChart.Data("Warrant", warrant));
            pieData.add(new PieChart.Data("Arrears", warrant));
            pieData.add(new PieChart.Data("Q1", q1));
            pieData.add(new PieChart.Data("Q2", q2));
            pieData.add(new PieChart.Data("Q3", q3));
            pieData.add(new PieChart.Data("Q4", q4));

            chart.setData(pieData);
        }
    }

    @FXML
    void clickOnLoad(MouseEvent event) {
        getTotals();
    }

}
