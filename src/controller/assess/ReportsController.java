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
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import conn.DB;
import javafx.beans.binding.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import modle.asses.OfficeHolder;
import modle.asses.PayNowModle;
import modle.asses.RipHolder;

/**
 * FXML Controller class
 *
 * @author Ranga Rathnayake
 */
public class ReportsController implements Initializable {

    @FXML
    private JFXButton btn_dayEnd;
    @FXML
    private JFXDatePicker date_cuser;
    @FXML
    private Text txt_date;

    @FXML
    private JFXDatePicker from_date;

    @FXML
    private JFXDatePicker to_date;

    @FXML
    private JFXButton btn_reports;

    @FXML
    private JFXComboBox<String> com_ri;

    @FXML
    private JFXComboBox<OfficeHolder> com_office;

    @FXML
    private JFXComboBox<OfficeHolder> com_office1;


    SimpleDateFormat format = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        format = new SimpleDateFormat("yyyy-MM-dd");
        Date systemdate = new Date();
        String system = format.format(systemdate);
        txt_date.setText(system);
        loadRiReports();
        com_office.setItems(modle.GetInstans.getOffice().loadOfficeCombo());
        com_office1.setItems(modle.GetInstans.getOffice().loadOfficeCombo());
        modle.StaticViews.getMc().changeTitle("Assessment Payment Reports");
    }

    @FXML
    private void clickOnDayEnd(MouseEvent event) {
        OfficeHolder selectedItem = com_office.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            modle.GetInstans.getAssessReport().getDayEndSheduleByOffice(txt_date.getText(), selectedItem.getIdOffice());
        } else {

            modle.GetInstans.getAssessReport().getDayEndShedule(txt_date.getText());
        }
    }

    @FXML
    private void onActionDate(ActionEvent event) {
        Date selectDate = Date.from(date_cuser.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());

        String selected = format.format(selectDate);
        txt_date.setText(selected);

    }


    public void loadRiReports() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "`user`.user_full_name,\n" +
                    "`user`.idUser,\n" +
                    "`user`.user_status\n" +
                    "FROM\n" +
                    "`user`\n" +
                    "INNER JOIN user_has_approval_cat ON user_has_approval_cat.User_idUser = `user`.idUser\n" +
                    "INNER JOIN approval_cat ON user_has_approval_cat.Approval_cat_idApproval_cat = approval_cat.idApproval_cat\n" +
                    "WHERE\n" +
                    "approval_cat.approval_name = 'RI' AND\n" +
                    "`user`.user_status = 1\n");

            while (data.next()) {
                String user_full_name = data.getString("user_full_name");
                list.add(user_full_name);
            }

            com_ri.setItems(list);

        } catch (Exception e) {
        }
    }


    @FXML
    private JFXButton btn_befor;

    @FXML
    void clickOnBefor(MouseEvent event) {
        String selectedItem = com_ri.getSelectionModel().getSelectedItem();
        if (selectedItem.length() > 0) {
            if (from_date.getValue() != null && to_date.getValue() != null) {
                Date fromdate = Date.from(from_date.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
                Date todate = Date.from(to_date.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String from = simpleDateFormat.format(fromdate);
                String to = simpleDateFormat.format(todate);

//                modle.GetInstans.getAssessReport().getRiShedulePending(from, to, selectedItem);
                ariaswarantPayReport(from, to, selectedItem, 0);
            }
        }

    }


    @FXML
    void clickOnRiReports(MouseEvent event) {

        String selectedItem = com_ri.getSelectionModel().getSelectedItem();

        if (selectedItem.length() > 0) {
            if (from_date.getValue() != null && to_date.getValue() != null) {
                Date fromdate = Date.from(from_date.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
                Date todate = Date.from(to_date.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String from = simpleDateFormat.format(fromdate);
                String to = simpleDateFormat.format(todate);

                ariaswarantPayReport(from, to, selectedItem, 1);
            }


        }


    }


    public void ariaswarantPayReport(String start, String end) {

        OfficeHolder selectedItem = com_office1.getSelectionModel().getSelectedItem();
        String quary = "";
        if (selectedItem == null) {

            quary = "SELECT\n" +
                    "ass_payment.idass_Payment,\n" +
                    "ass_payment.ass_Payment_Q_Number,\n" +
                    "ass_payment.ass_Payment_ThisYear,\n" +
                    "ass_payment.ass_Payment_date,\n" +
                    "ass_payment.ass_Payment_LY_Arrears,\n" +
                    "ass_payment.ass_Payment_LY_Warrant,\n" +
                    "ass_payment.ass_Payment_fullTotal,\n" +
                    "ass_payment.ass_Payment_idUser,\n" +
                    "ass_payment.Assessment_idAssessment,\n" +
                    "ass_payment.Receipt_idReceipt,\n" +
                    "ass_payment.ass_nature_idass_nature,\n" +
                    "ass_payment.ass_allocation_idass_allocation,\n" +
                    "ass_payment.ass_Payment_goto_debit,\n" +
                    "ass_payment.ass_Payment_Status,\n" +
                    "ass_payment.ass_cash,\n" +
                    "ass_payment.ass_check,\n" +
                    "ass_payment.ass_check_no,\n" +
                    "ass_payment.ass_bank,\n" +
                    "ass_payment.pay_user_id,\n" +
                    "ass_payment.cd_balance,\n" +
                    "receipt.receipt_print_no,\n" +
                    "receipt.idReceipt,\n" +
                    "assessment.idAssessment,\n" +
                    "assessment.Customer_idCustomer,\n" +
                    "assessment.Ward_idWard,\n" +
                    "assessment.Street_idStreet,\n" +
                    "assessment.ass_nature_idass_nature,\n" +
                    "ass_nature.ass_nature_name,\n" +
                    "assessment.assessment_no,\n" +
                    "assessment.assessment_oder,\n" +
                    "customer.cus_name,\n" +
                    "street.street_name,\n" +
                    "ward.ward_no,\n" +
                    "receipt.receipt_status\n" +
                    "FROM\n" +
                    "ass_payment\n" +
                    "INNER JOIN receipt ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                    "INNER JOIN assessment ON ass_payment.Assessment_idAssessment = assessment.idAssessment\n" +
                    "INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n" +
                    "INNER JOIN street ON assessment.Street_idStreet = street.idStreet\n" +
                    "INNER JOIN ward ON assessment.Ward_idWard = ward.idWard\n" +
                    "INNER JOIN ass_nature ON ass_payment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                    "WHERE\n" +
                    "ass_payment.ass_Payment_date BETWEEN '" + start + "' AND '" + end + "' AND\n" +
                    "ass_payment.ass_Payment_Status = 1 \n";
        } else {
            quary = "SELECT\n" +
                    "ass_payment.idass_Payment,\n" +
                    "ass_payment.ass_Payment_Q_Number,\n" +
                    "ass_payment.ass_Payment_ThisYear,\n" +
                    "ass_payment.ass_Payment_date,\n" +
                    "ass_payment.ass_Payment_LY_Arrears,\n" +
                    "ass_payment.ass_Payment_LY_Warrant,\n" +
                    "ass_payment.ass_Payment_fullTotal,\n" +
                    "ass_payment.ass_Payment_idUser,\n" +
                    "ass_payment.Assessment_idAssessment,\n" +
                    "ass_payment.Receipt_idReceipt,\n" +
                    "ass_payment.ass_nature_idass_nature,\n" +
                    "ass_payment.ass_allocation_idass_allocation,\n" +
                    "ass_payment.ass_Payment_goto_debit,\n" +
                    "ass_payment.ass_Payment_Status,\n" +
                    "ass_payment.ass_cash,\n" +
                    "ass_payment.ass_check,\n" +
                    "ass_payment.ass_check_no,\n" +
                    "ass_payment.ass_bank,\n" +
                    "ass_payment.pay_user_id,\n" +
                    "ass_payment.cd_balance,\n" +
                    "receipt.receipt_print_no,\n" +
                    "receipt.idReceipt, receipt.office_idOffice,\n" +
                    "assessment.idAssessment,\n" +
                    "assessment.Customer_idCustomer,\n" +
                    "assessment.Ward_idWard,\n" +
                    "assessment.Street_idStreet,\n" +
                    "assessment.ass_nature_idass_nature,\n" +
                    "ass_nature.ass_nature_name,\n" +
                    "assessment.assessment_no,\n" +
                    "assessment.assessment_oder,\n" +
                    "customer.cus_name,\n" +
                    "street.street_name,\n" +
                    "ward.ward_no,\n" +
                    "receipt.receipt_status\n" +
                    "FROM\n" +
                    "ass_payment\n" +
                    "INNER JOIN receipt ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                    "INNER JOIN assessment ON ass_payment.Assessment_idAssessment = assessment.idAssessment\n" +
                    "INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n" +
                    "INNER JOIN street ON assessment.Street_idStreet = street.idStreet\n" +
                    "INNER JOIN ward ON assessment.Ward_idWard = ward.idWard\n" +
                    "INNER JOIN ass_nature ON ass_payment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                    "WHERE\n" +
                    "ass_payment.ass_Payment_date BETWEEN '" + start + "' AND '" + end + "' AND\n" +
                    "ass_payment.ass_Payment_Status = 1 AND receipt.office_idOffice = '" + selectedItem.getIdOffice() + "'\n";
        }


        ArrayList<RipHolder> list = new ArrayList<>();

        try {
            ResultSet data = DB.getData(quary);
            System.out.println("auery");

            while (data.next()) {
                RipHolder ripHolder = new RipHolder();
                System.out.println("asdf");

                double cd_balance = data.getDouble("cd_balance");
                ripHolder.setCd(cd_balance);

                ripHolder.setAssessNo(data.getString("assessment_no"));
                ripHolder.setAssessData(data.getInt("idAssessment"), data.getInt("ward_no"), data.getString("street_name"), data.getString("cus_name"));

                ripHolder.setLYAP(data.getDouble("ass_Payment_LY_Arrears"));
                System.out.println(data.getDouble("ass_Payment_LY_Arrears"));

                ripHolder.setLYWP(data.getDouble("ass_Payment_LY_Warrant"));
                System.out.println(data.getDouble("ass_Payment_LY_Warrant"));

                ripHolder.setReciptNo(data.getString("receipt_print_no"));
                ripHolder.setIdRecipt(data.getInt("idReceipt"));
                int idass_payment = data.getInt("idass_Payment");

                ripHolder.setIdPayment(idass_payment);
                ripHolder.setTyFullPay(data.getDouble("ass_Payment_fullTotal"));
                ripHolder.setOverPay(data.getDouble("ass_Payment_goto_debit"));
                ripHolder.setFullTotalPay(data.getDouble("ass_Payment_fullTotal") + data.getDouble("ass_Payment_goto_debit"));


                ResultSet payto = DB.getData("SELECT\n" +
                        "ass_payto.idass_payto,\n" +
                        "ass_payto.ass_payto_Qno,\n" +
                        "ass_payto.ass_payto_warrant,\n" +
                        "ass_payto.ass_payto_arrears,\n" +
                        "ass_payto.ass_payto_qvalue,\n" +
                        "ass_payto.ass_payto_discount,\n" +
                        "ass_payto.ass_payto_discount_rate,\n" +
                        "ass_payto.ass_Payment_idass_Payment,\n" +
                        "ass_payto.ass_payto_status\n" +
                        "FROM\n" +
                        "ass_payto\n" +
                        "WHERE\n" +
                        "ass_payto.ass_Payment_idass_Payment =" + idass_payment);
                double xs = 0;
                while (payto.next()) {

                    System.out.println("=================================");
                    System.out.println(payto.getString("idass_payto"));

                    int ass_payto_qno = payto.getInt("ass_payto_Qno");
                    switch (ass_payto_qno) {
                        case 1:
                            ripHolder.setQ1(payto.getDouble("ass_payto_arrears"), payto.getDouble("ass_payto_warrant"), 0, 0, payto.getDouble("ass_payto_discount_rate"), payto.getDouble("ass_payto_discount"), payto.getDouble("ass_payto_qvalue"));
                            break;
                        case 2:
                            ripHolder.setQ2(payto.getDouble("ass_payto_arrears"), payto.getDouble("ass_payto_warrant"), 0, 0, payto.getDouble("ass_payto_discount_rate"), payto.getDouble("ass_payto_discount"), payto.getDouble("ass_payto_qvalue"));
                            break;
                        case 3:
                            ripHolder.setQ3(payto.getDouble("ass_payto_arrears"), payto.getDouble("ass_payto_warrant"), 0, 0, payto.getDouble("ass_payto_discount_rate"), payto.getDouble("ass_payto_discount"), payto.getDouble("ass_payto_qvalue"));
                            break;
                        case 4:
                            ripHolder.setQ4(payto.getDouble("ass_payto_arrears"), payto.getDouble("ass_payto_warrant"), 0, 0, payto.getDouble("ass_payto_discount_rate"), payto.getDouble("ass_payto_discount"), payto.getDouble("ass_payto_qvalue"));
                            break;
                    }
                    xs += payto.getDouble("ass_payto_qvalue");
                }
                System.out.println(xs);
                list.add(ripHolder);
            }
            modle.GetInstans.getAssessReport().loadReportAWPayByDatasourc(list, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void ariaswarantPayReport(String start, String end, String username, int status) {

        String quary = "SELECT\n" +
                "\tass_payment.idass_Payment,\n" +
                "\tass_payment.ass_Payment_Q_Number,\n" +
                "\tass_payment.ass_Payment_ThisYear,\n" +
                "\tass_payment.ass_Payment_date,\n" +
                "\tass_payment.ass_Payment_LY_Arrears,\n" +
                "\tass_payment.ass_Payment_LY_Warrant,\n" +
                "\tass_payment.ass_Payment_fullTotal,\n" +
                "\tass_payment.ass_Payment_idUser,\n" +
                "\tass_payment.Assessment_idAssessment,\n" +
                "\tass_payment.Receipt_idReceipt,\n" +
                "\tass_payment.ass_nature_idass_nature,\n" +
                "\tass_payment.ass_allocation_idass_allocation,\n" +
                "\tass_payment.ass_Payment_goto_debit,\n" +
                "\tass_payment.ass_Payment_Status,\n" +
                "\tass_payment.ass_cash,\n" +
                "\tass_payment.ass_check,\n" +
                "\tass_payment.ass_check_no,\n" +
                "\tass_payment.ass_bank,\n" +
                "\tass_payment.cd_balance,\n" +
                "\treceipt.receipt_print_no,\n" +
                "\treceipt.idReceipt,\n" +
                "\tassessment.idAssessment,\n" +
                "\tassessment.Customer_idCustomer,\n" +
                "\tassessment.Ward_idWard,\n" +
                "\tassessment.Street_idStreet,\n" +
                "\tassessment.ass_nature_idass_nature,\n" +
                "\tass_nature.ass_nature_name,\n" +
                "\tassessment.assessment_no,\n" +
                "\tassessment.assessment_oder,\n" +
                "\tcustomer.cus_name,\n" +
                "\tstreet.street_name,\n" +
                "\tward.ward_no,\n" +
                "\treceipt.receipt_status, receipt.office_idOffice, \n" +
                "\tass_payment.pay_user_id,\n" +
                "\t`user`.idUser,\n" +
                "\t`user`.user_full_name\n" +
                "FROM\n" +
                "\tass_payment\n" +
                "INNER JOIN receipt ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                "INNER JOIN assessment ON ass_payment.Assessment_idAssessment = assessment.idAssessment\n" +
                "INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n" +
                "INNER JOIN street ON assessment.Street_idStreet = street.idStreet\n" +
                "INNER JOIN ward ON assessment.Ward_idWard = ward.idWard\n" +
                "INNER JOIN ass_nature ON ass_payment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                "INNER JOIN `user` ON ass_payment.ass_Payment_idUser = `user`.idUser\n" +
                "WHERE\n" +
                "\tass_payment.ass_Payment_date BETWEEN '" + start + "'\n" +
                "AND '" + end + "'\n" +
                "AND ass_payment.ass_Payment_Status = '" + status + "'\n" +
                "AND `user`.user_full_name = '" + username + "'";


        ArrayList<RipHolder> list = new ArrayList<>();

        try {
            ResultSet data = DB.getData(quary);
            System.out.println("auery");

            while (data.next()) {


                RipHolder ripHolder = new RipHolder();

                double cd_balance = data.getDouble("cd_balance");
                ripHolder.setCd(cd_balance);


                System.out.println("asdf");
                ripHolder.setAssessNo(data.getString("assessment_no"));
                ripHolder.setAssessData(data.getInt("idAssessment"), data.getInt("ward_no"), data.getString("street_name"), data.getString("cus_name"));

                ripHolder.setLYAP(data.getDouble("ass_Payment_LY_Arrears"));
                System.out.println(data.getDouble("ass_Payment_LY_Arrears"));

                ripHolder.setLYWP(data.getDouble("ass_Payment_LY_Warrant"));
                System.out.println(data.getDouble("ass_Payment_LY_Warrant"));

                ripHolder.setReciptNo(data.getString("receipt_print_no"));
                ripHolder.setIdRecipt(data.getInt("idReceipt"));
                int idass_payment = data.getInt("idass_Payment");

                ripHolder.setIdPayment(idass_payment);
                ripHolder.setTyFullPay(data.getDouble("ass_Payment_fullTotal"));
                ripHolder.setOverPay(data.getDouble("ass_Payment_goto_debit"));
                ripHolder.setFullTotalPay(data.getDouble("ass_Payment_fullTotal") + data.getDouble("ass_Payment_goto_debit"));

                ripHolder.setChequeNo(data.getString("ass_check_no"));
                ripHolder.setCashCheque(data.getDouble("ass_cash"), data.getDouble("ass_check"));

                ResultSet payto = DB.getData("SELECT\n" +
                        "ass_payto.idass_payto,\n" +
                        "ass_payto.ass_payto_Qno,\n" +
                        "ass_payto.ass_payto_warrant,\n" +
                        "ass_payto.ass_payto_arrears,\n" +
                        "ass_payto.ass_payto_qvalue,\n" +
                        "ass_payto.ass_payto_discount,\n" +
                        "ass_payto.ass_payto_discount_rate,\n" +
                        "ass_payto.ass_Payment_idass_Payment,\n" +
                        "ass_payto.ass_payto_status\n" +
                        "FROM\n" +
                        "ass_payto\n" +
                        "WHERE\n" +
                        "ass_payto.ass_Payment_idass_Payment =" + idass_payment);
                double xs = 0;
                while (payto.next()) {

                    System.out.println("=================================");
                    System.out.println(payto.getString("idass_payto"));

                    int ass_payto_qno = payto.getInt("ass_payto_Qno");
                    switch (ass_payto_qno) {
                        case 1:
                            ripHolder.setQ1(payto.getDouble("ass_payto_arrears"), payto.getDouble("ass_payto_warrant"), 0, 0, payto.getDouble("ass_payto_discount_rate"), payto.getDouble("ass_payto_discount"), payto.getDouble("ass_payto_qvalue"));
                            break;
                        case 2:
                            ripHolder.setQ2(payto.getDouble("ass_payto_arrears"), payto.getDouble("ass_payto_warrant"), 0, 0, payto.getDouble("ass_payto_discount_rate"), payto.getDouble("ass_payto_discount"), payto.getDouble("ass_payto_qvalue"));
                            break;
                        case 3:
                            ripHolder.setQ3(payto.getDouble("ass_payto_arrears"), payto.getDouble("ass_payto_warrant"), 0, 0, payto.getDouble("ass_payto_discount_rate"), payto.getDouble("ass_payto_discount"), payto.getDouble("ass_payto_qvalue"));
                            break;
                        case 4:
                            ripHolder.setQ4(payto.getDouble("ass_payto_arrears"), payto.getDouble("ass_payto_warrant"), 0, 0, payto.getDouble("ass_payto_discount_rate"), payto.getDouble("ass_payto_discount"), payto.getDouble("ass_payto_qvalue"));
                            break;
                    }

                    xs += payto.getDouble("ass_payto_qvalue");


                }
                System.out.println(xs);

                list.add(ripHolder);

            }

            modle.GetInstans.getAssessReport().loadReportAWPayByDatasourcAndUser(list, start, end, username);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @FXML
    private JFXDatePicker from_awd;

    @FXML
    private JFXDatePicker to_awd;

    @FXML
    private JFXButton btn_awpay;

    @FXML
    void clickOnAWPay(MouseEvent event) {
        if (from_awd.getValue() != null && to_awd.getValue() != null) {
            Date fromdate = Date.from(from_awd.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
            Date todate = Date.from(to_awd.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String from = simpleDateFormat.format(fromdate);
            String to = simpleDateFormat.format(todate);

            ariaswarantPayReport(from, to);
        }
    }


}
