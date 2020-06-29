package controller.payment;

import com.jfoenix.controls.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import conn.DB;
import conn.NewHibernateUtil;
import controller.assess.DayendController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import modle.Allert;
import modle.GetInstans;
import modle.KeyVal;
import modle.StaticViews;
import modle.asses.PayNowModle;
import modle.popup.BarcodeStatic;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.AdvAdvertising;
import pojo.Customer;
import pojo.Street;

import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Ranga on 2019-03-05.
 */
public class PayByIdController implements Initializable {
    @FXML
    private JFXTextField txt_id;

    @FXML
    private Text txt_cusname;

    @FXML
    private Text txt_amount;

    @FXML
    private JFXTextField txt_cash;

    @FXML
    private JFXTextField txt_check;

    @FXML
    private JFXCheckBox cash;

    @FXML
    private JFXCheckBox check;

    @FXML
    private JFXTextField txt_chq_no;

    @FXML
    private JFXDatePicker check_date;

    @FXML
    private JFXComboBox<String> combo_bank;

    @FXML
    private JFXButton btn_pay;

    @FXML
    private JFXTextArea txt_dis;

    @FXML
    private JFXButton btn_print;

    public static DayendController dec;


    double fullTot;
    int appcat;
    int appid;
    int idRecipt;

    int appcatid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dec = new DayendController();
        modle.StaticViews.getMc().changeTitle("Pay By Barcode");
        loadBanksCombo();
        btn_print.setDisable(true);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txt_id.setText(null);
                txt_id.requestFocus();
            }
        });

    }

    public void loadBanksCombo() {
        combo_bank.setItems(modle.GetInstans.getBanks().loadBanksCombo());
    }

    @FXML
    void cbOnAction(ActionEvent event) {
        cheackBox();
    }

    @FXML
    void clickOnPay(MouseEvent event) {
        btn_pay.setDisable(true);
        System.out.println(appcat + " -> APP CAT");
        System.out.println(appid + " -> APP ID");
        String cq = txt_check.getText();
        String ca = txt_cash.getText();


        double cashe = 0;
        double cheque = 0;
        try {
            if (cq != null && cq.length() > 0) {
                cheque = Double.parseDouble(cq);
            }
            if (ca != null && ca.length() > 0) {
                cashe = Double.parseDouble(ca);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String bank = combo_bank.getSelectionModel().getSelectedItem();
        String chequNo = txt_chq_no.getText();

        if (appcat == 1) {
            new modle.adv.Report().pringAdvBill(appid, false);
            System.out.println(idRecipt);
            modle.GetInstans.getAdvPayModle().compleetePayment(idRecipt);
            btn_print.setDisable(false);
            clearAllData();

        } else if (appcat == 13) {
            modle.GetInstans.getRiBillComplete().completeRi(idRecipt + "");
            new modle.asses.RiBillPrint().PrintRiBill(idRecipt, false);
            enable();


        } else if (appcat == 2) {
            btn_print.setDisable(false);
            setAsPaid(idRecipt);

        } else if (cash.isSelected() || check.isSelected()) {

            if (cheque > 0) {
                if (check_date.getValue() != null) {
                    Date startDate = Date.from(check_date.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
                    String cheque_date = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
                    if (appcat == 7) {
                        modle.Payment.PaymentByID.genarateRisiptNo(7, "", appid, cheque, cashe, cheque_date, chequNo, bank, 0);
                        btn_print.setDisable(false);
                        updateStreetLineStatus(appid);
                    }
                    if (appcat == 8) {
                        modle.GetInstans.getThreweel().getVehiclepassReport(idRecipt + "",false);
                        txt_amount.setText("");
                        txt_cusname.setText("");
                    }
                    if (appcat == 9) {
                        modle.Payment.PaymentByID.genarateRisiptNo(9, "", appid, cheque, cashe, cheque_date, chequNo, bank, 0);
                        updateMixIncomeStatus(appid, 9);
                        btn_print.setDisable(false);
                    }
                    if (appcat == 10) {
                        modle.Payment.PaymentByID.genarateRisiptNo(10, "", appid, cheque, cashe, cheque_date, chequNo, bank, 0);
                        modle.book.Recipt.genarateBookingRecipt(idRecipt + "",false);
                        clearAllData();
                        enable();
                    }


                    if (appcat == 14) {
                        modle.Payment.PaymentByID.genarateRisiptNo(14, "", appid, cheque, cashe, cheque_date, chequNo, bank, 0);
                        btn_print.setDisable(false);
                        updateNonVesting(appid);
                    }
                } else {
                    modle.Allert.notificationWorning("Recheck All", "Cheque Data");
                }
            } else {
                chequNo = null;
                if (appcat == 7) {
                    modle.Payment.PaymentByID.genarateRisiptNo(7, "", appid, cheque, cashe, "", chequNo, bank, 0);
                    btn_print.setDisable(false);
                    updateStreetLineStatus(appid);
                }

                if (appcat == 9) {
                    modle.Payment.PaymentByID.genarateRisiptNo(9, "", appid, cheque, cashe, "", chequNo, bank, 0);
                    updateMixIncomeStatus(appid, 9);
                    btn_print.setDisable(false);
                }

                if (appcat == 8) {
                    modle.GetInstans.getThreweel().getVehiclepassReport(idRecipt + "",false);
                    txt_amount.setText("");
                    txt_cusname.setText("");
                }

                if (appcat == 10) {
                    modle.Payment.PaymentByID.genarateRisiptNo(10, "", appid, cheque, cashe, "", chequNo, bank, 0);
                    modle.book.Recipt.genarateBookingRecipt(idRecipt + "",false);
                    clearAllData();
                    enable();
                }

                if (appcat == 14) {
                    modle.Payment.PaymentByID.genarateRisiptNo(14, "", appid, cheque, cashe, "", chequNo, bank, 0);
                    btn_print.setDisable(false);
                    updateNonVesting(appid);
                }


            }
        } else {
            modle.Allert.notificationError("Please Select Payment Method", "Pay value is empty");
        }
        BarcodeStatic.idRecipt = 0 + "";
    }


    public void setAsPaid(int idRecipt) {
        System.out.println(idRecipt);
        try {
            conn.DB.setData("UPDATE `receipt`\n" +
                    "SET `receipt_status` = '1'\n" +
                    "WHERE\n" +
                    "\t`idReceipt` = '" + idRecipt + "'");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    public void updateStreetLineStatus(int appid) {

        String ss = "SELECT\n" +
                "sl_details.idStreetLine,\n" +
                "doc_hand_approve.approve_doc_hand_id\n" +
                "FROM\n" +
                "sl_details\n" +
                "INNER JOIN doc_hand_approve ON sl_details.idStreetLine = doc_hand_approve.doc_hand_subject_id\n" +
                "WHERE\n" +
                "sl_details.idStreetLine = " + appid;

        try {
            ResultSet data = DB.getData(ss);
            if (data.last()) {
                int approve_doc_hand_id = data.getInt("approve_doc_hand_id");


                conn.DB.setData("UPDATE `doc_hand_approve`\n" +
                        "SET \n" +
                        " `doc_hand_recevied_user_category` = '7',\n" +
                        " `doc_hand_accept_or_reject` = '4' \n" +
                        "WHERE\n" +
                        "\t(`approve_doc_hand_id` = '" + approve_doc_hand_id + "')");


            }

            ResultSet data1 = DB.getData("SELECT\n" +
                    "receipt.idReceipt,\n" +
                    "sl_details.idStreetLine,\n" +
                    "receipt.receipt_print_no,\n" +
                    "receipt.receipt_day\n" +
                    "FROM\n" +
                    "receipt\n" +
                    "INNER JOIN sl_details ON sl_details.idStreetLine = receipt.recept_applicationId\n" +
                    "WHERE\n" +
                    "receipt.Application_Catagory_idApplication_Catagory = 7 AND\n" +
                    "sl_details.idStreetLine = '" + appid + "'");

            if (data1.last()) {
                int idReceipt = data1.getInt("idReceipt");
                String receipt_day = data1.getString("receipt_day");
                String receipt_print_no = data1.getString("receipt_print_no");


                conn.DB.setData("UPDATE `account_ps_three`\n" +
                        "SET `report_date` = '" + receipt_day + "',\n" +
                        " `report_ricipt_no` = '" + receipt_print_no + "',\n" +
                        " `report_status` = '1'\n" +
                        "WHERE\n" +
                        "\t`report_ricipt_id` = '" + idReceipt + "'");


            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }

    public void updateNonVesting(int appid) {

        String ss = "SELECT\n" +
                "sl_details.idStreetLine,\n" +
                "doc_hand_approve.approve_doc_hand_id\n" +
                "FROM\n" +
                "sl_details\n" +
                "INNER JOIN doc_hand_approve ON sl_details.idStreetLine = doc_hand_approve.doc_hand_subject_id\n" +
                "WHERE\n" +
                "sl_details.idStreetLine = " + appid;

        try {
            ResultSet data = DB.getData(ss);
            if (data.last()) {
                int approve_doc_hand_id = data.getInt("approve_doc_hand_id");

                conn.DB.setData("UPDATE `doc_hand_approve`\n" +
                        "SET \n" +
                        " `doc_hand_accept_or_reject` = '4' \n" +
                        "WHERE\n" +
                        "\t(`approve_doc_hand_id` = '" + approve_doc_hand_id + "')");


            }

            ResultSet data1 = DB.getData("SELECT\n" +
                    "receipt.idReceipt,\n" +
                    "sl_details.idStreetLine,\n" +
                    "receipt.receipt_print_no,\n" +
                    "receipt.receipt_day\n" +
                    "FROM\n" +
                    "receipt\n" +
                    "INNER JOIN sl_details ON sl_details.idStreetLine = receipt.recept_applicationId\n" +
                    "WHERE\n" +
                    "receipt.Application_Catagory_idApplication_Catagory = 14 AND\n" +
                    "sl_details.idStreetLine = '" + appid + "'");

            if (data1.last()) {
                int idReceipt = data1.getInt("idReceipt");
                String receipt_day = data1.getString("receipt_day");
                String receipt_print_no = data1.getString("receipt_print_no");
                conn.DB.setData("UPDATE `account_ps_three`\n" +
                        "SET `report_date` = '" + receipt_day + "',\n" +
                        " `report_ricipt_no` = '" + receipt_print_no + "',\n" +
                        " `report_status` = '1'\n" +
                        "WHERE\n" +
                        "\t`report_ricipt_id` = '" + idReceipt + "'");
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }

    public void loadDataByEnter() {
        String text = txt_id.getText();


        String[] ss = text.split("_");

        if (ss.length > 1) {
            String s = ss[1];
            System.out.println(s);
            String idRi = ss[0];
            modle.Allert.notificationGood("RI Assessment Receipt", text);
            riBillData(idRi);
        } else {

            String qq = "SELECT\n" +
                    "receipt.idReceipt,\n" +
                    "receipt.Application_Catagory_idApplication_Catagory,\n" +
                    "receipt.recept_applicationId,\n" +
                    "receipt.receipt_print_no,\n" +
                    "receipt.cheack,\n" +
                    "receipt.cesh,\n" +
                    "receipt.receipt_total,\n" +
                    "receipt.receipt_day,\n" +
                    "receipt.receipt_status,\n" +
                    "receipt.receipt_syn,\n" +
                    "receipt.chque_no,\n" +
                    "receipt.chque_date,\n" +
                    "receipt.chque_bank\n" +
                    "FROM\n" +
                    "receipt where idReceipt = " + text;


            try {
                ResultSet rd = DB.getData(qq);

                if (rd.last()) {
                    appcatid = rd.getInt("Application_Catagory_idApplication_Catagory");
                    if (appcatid == 7) {
                        streetLine2(text);
                        modle.Allert.notificationGood("Street Line Receipt", text);
                    }

                    if (appcatid == 14) {
                        nonvesting(text);
                        modle.Allert.notificationGood("Non Vesting Receipt", text);
                    }

                    if (appcatid == 3) {
                        modle.Allert.notificationGood("BOP Receipt", text);
                    }

                    if (appcatid == 9) {
                        mixIncomeBillData(text);
                    }

                    if (appcatid == 1) {

                        ViewAdvBillDetails(text);
                    }

                    if (appcatid == 8) {
                        modle.Allert.notificationGood("Three Wheel Receipt", text);
                        viewThreeWheelBill(text);
                    }

                    if (appcatid == 10) {
                        viewBookingDetails(text);
                    }

                    if (appcatid == 11) {
                        modle.Allert.notificationGood("Vehicle Service Receipt", text);
                        mixIncomeBillData(text);
                        btn_pay.setDisable(true);
                    }

                    if (appcatid == 2) {
                        modle.Allert.notificationGood("Assessment Tax", text);
                        assessmentTaxBil(text);
                    }

                } else {
                    modle.Allert.notificationInfo("NO Receipt", text);
                    clearAllData();
                    enable();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }

    }


    @FXML
    void keyReleasedOnID(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            loadDataByEnter();
        } else {

        }

    }

    public void riBillData(String text) {
        try {
            String query = "SELECT\n" +
                    "ribill.idRibill,\n" +
                    "ribill.billdate,\n" +
                    "ribill.billtotal,\n" +
                    "ribill.userid,\n" +
                    "ribill.ribill_status,\n" +
                    "ribill.bill_no,\n" +
                    "ribill.office_id,\n" +
                    "ribill.oder,\n" +
                    "`user`.user_full_name,\n" +
                    "office.office_name\n" +
                    "FROM\n" +
                    "ribill\n" +
                    "INNER JOIN `user` ON `user`.idUser = ribill.userid\n" +
                    "LEFT JOIN office ON office.idOffice = ribill.office_id\n" +
                    "WHERE idRibill =" + text;
            ResultSet data = DB.getData(query);
            if (data.last()) {
                int ribill_status = data.getInt("ribill_status");

                if (ribill_status == 0) {
                    appcat = 13;
                    appid = data.getInt("idRibill");
                    idRecipt = data.getInt("idRibill");
                    txt_amount.setText(data.getString("billtotal"));
                    txt_cusname.setText(data.getString("user_full_name"));
                    fullTot = data.getDouble("billtotal");
                    dissableFilds();

                } else {
                    appcat = 13;
                    appid = data.getInt("idRibill");
                    idRecipt = data.getInt("idRibill");
                    txt_amount.setText(data.getString("billtotal"));
                    txt_cusname.setText(data.getString("user_full_name"));
                    fullTot = data.getDouble("billtotal");
                    btn_pay.setDisable(true);
                    btn_print.setText("Re Print Bill");
                    btn_print.setDisable(false);
                    modle.Allert.notificationWorning("Already completed", text);

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }

    public void assessmentTaxBil(String text) {
        try {

            String query = "SELECT\n" +
                    "customer.cus_name,\n" +
                    "receipt.cheack,\n" +
                    "receipt.cesh,\n" +
                    "assessment.assessment_no,\n" +
                    "receipt.idReceipt,\n" +
                    "receipt.receipt_status\n" +
                    "FROM\n" +
                    "receipt\n" +
                    "INNER JOIN assessment ON assessment.idAssessment = receipt.recept_applicationId\n" +
                    "INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n" +
                    "WHERE\n" +
                    "receipt.idReceipt = '" + text + "' AND\n" +
                    "receipt.Application_Catagory_idApplication_Catagory = 2";
            ResultSet data = DB.getData(query);
            if (data.last()) {
                int receipt_status = data.getInt("receipt_status");
                if (receipt_status == 0) {
                    appcat = 2;
//                    appid = data.getInt("recept_applicationId");
                    idRecipt = Integer.parseInt(text);
                    txt_amount.setText((data.getDouble("cheack") + data.getDouble("cesh")) + "");
                    txt_cusname.setText(data.getString("cus_name"));
                    fullTot = data.getDouble("cheack") + data.getDouble("cesh");
                    dissableFilds();
                } else {
                    appcat = 2;
//                    appid = data.getInt("recept_applicationId");
                    idRecipt = Integer.parseInt(text);
                    txt_amount.setText((data.getDouble("cheack") + data.getDouble("cesh")) + "");
                    txt_cusname.setText(data.getString("cus_name"));
                    fullTot = data.getDouble("cheack") + data.getDouble("cesh");
                    btn_pay.setDisable(true);
                    btn_print.setText("Re Print Bill");
                    btn_print.setDisable(false);
                    modle.Allert.notificationWorning("Already completed", text);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }


    public void streetLine2(String text) {
        String query = "SELECT\n" +
                "\treceipt.idReceipt,\n" +
                "\treceipt.Application_Catagory_idApplication_Catagory,\n" +
                "\treceipt.recept_applicationId,\n" +
                "\treceipt.receipt_print_no,\n" +
                "\treceipt.cheack,\n" +
                "\treceipt.cesh,\n" +
                "\treceipt.receipt_total,\n" +
                "\treceipt.receipt_day,\n" +
                "\treceipt.receipt_status,\n" +
                "\treceipt.receipt_syn,\n" +
                "\treceipt.chque_no,\n" +
                "\treceipt.chque_date,\n" +
                "\treceipt.chque_bank,\n" +
                "\treceipt.oder,\n" +
                "\treceipt.office_idOffice,\n" +
                "\tsl_details.idStreetLine,\n" +
                "\tsl_details.ass_id,\n" +
                "\tsl_details.slDate,\n" +
                "\tsl_details.sllotNo,\n" +
                "\tsl_details.slPlanNo,\n" +
                "\tsl_details.slDescription,\n" +
                "\tsl_details.slDeposit,\n" +
                "\tsl_details.slAmount,\n" +
                "\tsl_details.slVat,\n" +
                "\tsl_details.slNbt,\n" +
                "\tsl_details.slStamp,\n" +
                "\tsl_details.slOther1,\n" +
                "\tsl_details.slOther2,\n" +
                "\tsl_details.slApproveToPay,\n" +
                "\tsl_details.slServayOfficer,\n" +
                "\tsl_details.slServayDate,\n" +
                "\tsl_details.slPersonTitle,\n" +
                "\tsl_details.slYesNo_id,\n" +
                "\tsl_details.slStatus,\n" +
                "\tsl_details.office_idOffice,\n" +
                "\tsl_details.customer_idCustomer,\n" +
                "\tsl_details.sl_reference_no,\n" +
                "\tsl_details.slApproveDescription,\n" +
                "\tcustomer.cus_name\n" +
                "FROM\n" +
                "\treceipt\n" +
                "INNER JOIN sl_details ON receipt.recept_applicationId = sl_details.idStreetLine\n" +
                "INNER JOIN customer ON sl_details.customer_idCustomer = customer.idCustomer\n" +
                "WHERE\n" +
                "\treceipt.Application_Catagory_idApplication_Catagory = '7'\n" +
                "AND receipt.idReceipt = " + text;
        try {
            ResultSet data = DB.getData(query);

            if (data.last()) {
                int receipt_status = data.getInt("receipt_status");
                if (receipt_status == 0) {
                    appcat = data.getInt("Application_Catagory_idApplication_Catagory");
                    appid = data.getInt("recept_applicationId");
                    fullTot = data.getDouble("receipt_total");
                    idRecipt = data.getInt("idReceipt");
                    txt_cusname.setText(data.getString("cus_name"));
                    txt_amount.setText(fullTot + "");
                }

                if (receipt_status == 1) {
                    appcat = data.getInt("Application_Catagory_idApplication_Catagory");
                    appid = data.getInt("recept_applicationId");
                    fullTot = data.getDouble("receipt_total");
                    idRecipt = data.getInt("idReceipt");
                    txt_cusname.setText(data.getString("cus_name"));
                    txt_amount.setText(fullTot + "");
                    btn_pay.setDisable(true);
                    btn_print.setText("Re Print Bill");
                    btn_print.setDisable(false);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }

    public void nonvesting(String text) {
        String query = "SELECT\n" +
                "\treceipt.idReceipt,\n" +
                "\treceipt.Application_Catagory_idApplication_Catagory,\n" +
                "\treceipt.recept_applicationId,\n" +
                "\treceipt.receipt_print_no,\n" +
                "\treceipt.cheack,\n" +
                "\treceipt.cesh,\n" +
                "\treceipt.receipt_total,\n" +
                "\treceipt.receipt_day,\n" +
                "\treceipt.receipt_status,\n" +
                "\treceipt.receipt_syn,\n" +
                "\treceipt.chque_no,\n" +
                "\treceipt.chque_date,\n" +
                "\treceipt.chque_bank,\n" +
                "\treceipt.oder,\n" +
                "\treceipt.office_idOffice,\n" +
                "\tsl_details.idStreetLine,\n" +
                "\tsl_details.ass_id,\n" +
                "\tsl_details.slDate,\n" +
                "\tsl_details.sllotNo,\n" +
                "\tsl_details.slPlanNo,\n" +
                "\tsl_details.slDescription,\n" +
                "\tsl_details.slDeposit,\n" +
                "\tsl_details.slAmount,\n" +
                "\tsl_details.slVat,\n" +
                "\tsl_details.slNbt,\n" +
                "\tsl_details.slStamp,\n" +
                "\tsl_details.slOther1,\n" +
                "\tsl_details.slOther2,\n" +
                "\tsl_details.slApproveToPay,\n" +
                "\tsl_details.slServayOfficer,\n" +
                "\tsl_details.slServayDate,\n" +
                "\tsl_details.slPersonTitle,\n" +
                "\tsl_details.slYesNo_id,\n" +
                "\tsl_details.slStatus,\n" +
                "\tsl_details.office_idOffice,\n" +
                "\tsl_details.customer_idCustomer,\n" +
                "\tsl_details.sl_reference_no,\n" +
                "\tsl_details.slApproveDescription,\n" +
                "\tcustomer.cus_name\n" +
                "FROM\n" +
                "\treceipt\n" +
                "INNER JOIN sl_details ON receipt.recept_applicationId = sl_details.idStreetLine\n" +
                "INNER JOIN customer ON sl_details.customer_idCustomer = customer.idCustomer\n" +
                "WHERE\n" +
                "\treceipt.Application_Catagory_idApplication_Catagory = '14'\n" +
                "AND receipt.idReceipt = " + text;
        try {
            ResultSet data = DB.getData(query);

            if (data.last()) {
                appcat = data.getInt("Application_Catagory_idApplication_Catagory");
                appid = data.getInt("recept_applicationId");
                fullTot = data.getDouble("receipt_total");
                idRecipt = data.getInt("idReceipt");
                txt_cusname.setText(data.getString("cus_name"));
                txt_amount.setText(fullTot + "");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


    public void streetLineBillData(String text) {
        try {
            String quary = "SELECT\n" +
                    "receipt.idReceipt,\n" +
                    "receipt.Application_Catagory_idApplication_Catagory,\n" +
                    "receipt.recept_applicationId,\n" +
                    "receipt.receipt_print_no,\n" +
                    "receipt.cheack,\n" +
                    "receipt.cesh,\n" +
                    "receipt.receipt_total,\n" +
                    "receipt.receipt_day,\n" +
                    "receipt.receipt_status,\n" +
                    "receipt.receipt_syn,\n" +
                    "receipt.chque_no,\n" +
                    "receipt.chque_date,\n" +
                    "receipt.chque_bank,\n" +
                    "customer.cus_name,\n" +
                    "streetline.idStreetLine,\n" +
                    "streetline.customer_idCustomer,\n" +
                    "streetline.assessment_idAssessment,\n" +
                    "streetline.StreetLine_date,\n" +
                    "streetline.assessment_status,\n" +
                    "streetline.water_status,\n" +
                    "streetline.tradetax_status,\n" +
                    "streetline.parts_no,\n" +
                    "streetline.StreetLine_description,\n" +
                    "streetline.deposit,\n" +
                    "streetline.amount,\n" +
                    "streetline.vat,\n" +
                    "streetline.nbt,\n" +
                    "streetline.approve_top_pay_status,\n" +
                    "streetline.survy_officer,\n" +
                    "streetline.survey_date\n" +
                    "FROM\n" +
                    "receipt\n" +
                    "INNER JOIN streetline ON streetline.idStreetLine = receipt.recept_applicationId\n" +
                    "INNER JOIN customer ON streetline.customer_idCustomer = customer.idCustomer\n" +
                    "WHERE\n" +
                    "receipt.Application_Catagory_idApplication_Catagory = 7 AND\n" +
                    " idReceipt = " + text;

            ResultSet data = DB.getData(quary);
            if (data.last()) {

                int receipt_status = data.getInt("receipt_status");

                if (receipt_status != 0) {
                    btn_pay.setDisable(true);
                    cash.setDisable(true);
                    check.setDisable(true);
                } else {
                    btn_pay.setDisable(false);
                    cash.setDisable(false);
                    check.setDisable(false);
                }

                appcat = data.getInt("Application_Catagory_idApplication_Catagory");
                appid = data.getInt("recept_applicationId");
                fullTot = data.getDouble("receipt_total");
                idRecipt = data.getInt("idReceipt");
                txt_cusname.setText(data.getString("cus_name"));

                String dis = "Diposit : " + data.getString("deposit") + "\n" +
                        "Amount : " + data.getString("amount") + "\n" +
                        "VAT : " + data.getString("vat") + "\n" +
                        "NBT : " + data.getString("nbt") + "\n" +
                        "Total :" + data.getString("receipt_total");
                txt_dis.setText(dis);
                txt_amount.setText(fullTot + "");
            } else {
                modle.Allert.notificationInfo("NO Receipt", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void AdvertisingBill() {
        System.out.println(appid);
        new modle.adv.Report().pringAdvBill(appid, false);
    }

    public void ViewAdvBillDetails(String text) {
        idRecipt = Integer.parseInt(text);
        appcat = 1;
        int status = 0;
        String quary = "SELECT\n" +
                "receipt.recept_applicationId,\n receipt.receipt_status," +
                " receipt.idReceipt\n" +
                "FROM\n" +
                "receipt\n" +
                "WHERE\n" +
                "receipt.Application_Catagory_idApplication_Catagory = 1 AND\n" +
                "receipt.idReceipt = " + text;


        try {
            ResultSet data = DB.getData(quary);
            if (data.last()) {
                status = data.getInt("receipt.receipt_status");
                appid = data.getInt("recept_applicationId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (status == 0) {
            modle.Allert.notificationGood("Advertising Receipt", text);
            int advApplicationID = appid;
            btn_pay.setDisable(false);

            Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
            try {
                AdvAdvertising adv = (AdvAdvertising) session.load(AdvAdvertising.class, advApplicationID);

                Customer customer = (Customer) session.createCriteria(Customer.class).add(Restrictions.eq("idCustomer", adv.getCustomerIdCustomer())).uniqueResult();
                appid = adv.getIdAdvAdvertising();
                System.out.println(appid);
                txt_cusname.setText(customer.getCusName());

                fullTot = adv.getAdvFullTotal();
                txt_amount.setText(modle.Round.roundToString(adv.getAdvFullTotal()));

                if (adv.getAdvCash() > 0) {
                    txt_cash.setText(adv.getAdvCash() + "");
                    cash.setSelected(true);
                }

                if (adv.getAdvCheque() > 0) {
                    txt_check.setText(adv.getAdvCheque() + "");
                    check.setSelected(true);
                    txt_chq_no.setText(adv.getAdvChequeNo());
                    check_date.setValue(LocalDate.parse(adv.getAdvChequeDate().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    adv.getAdvChequeBank();
                    pojo.Bank bank = (pojo.Bank) session.load(pojo.Bank.class, adv.getAdvChequeBank());
                    System.out.println(bank.getBankName() + "============================= bank " + bank.getIdBank());
                    combo_bank.getSelectionModel().select(bank.getBankName());

                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        } else {
            modle.Allert.notificationInfo("Already Paid", "Please Check ");
            Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
            int advApplicationID = appid;
            appcat = 1;
            btn_pay.setDisable(true);
            try {
                AdvAdvertising adv = (AdvAdvertising) session.load(AdvAdvertising.class, advApplicationID);
                Customer customer = (Customer) session.createCriteria(Customer.class).add(Restrictions.eq("idCustomer", adv.getCustomerIdCustomer())).uniqueResult();
                appid = adv.getIdAdvAdvertising();
                System.out.println(appid);
                txt_cusname.setText(customer.getCusName());

                fullTot = adv.getAdvFullTotal();
                txt_amount.setText(modle.Round.roundToString(adv.getAdvFullTotal()));

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }

            btn_print.setText("Re Print Bill");
            btn_print.setDisable(false);
        }

    }

    public void viewThreeWheelBill(String text) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "3weel_park_details.3weel_park_details_name,\n" +
                    "CONCAT(\n" +
                    "\t\t\n" +
                    "\t\t3weel_prov_code.3weel_prov_code,\n" +
                    "\t\t' ',\n" +
                    "\t\t3weel_vehi_registration.3weel_vehi_reg_no\n" +
                    "\t\t\n" +
                    "\t) AS reg_no,\n" +
                    "3weel_park_details.3weel_park_details_name,\n" +
                    "customer.cus_name,\n" +
                    "3weel_payment.3weel_pay_date,\n" +
                    "customer.idCustomer,\n" +
                    "CONCAT('MCK/', customer.idCustomer) AS MCK_ID,\n" +
                    "3weel_payment.3weel_amount,\n" +
                    "3weel_payment.3weel_vat,\n" +
                    "3weel_payment.3weel_nbt,\n" +
                    "3weel_payment.3weel_stamp,\n" +
                    "3weel_payment.3weel_other,\n" +
                    "3weel_payment.3weel_pay_cash,\n" +
                    "3weel_payment.3weel_pay_cheque,\n" +
                    "3weel_payment.3weel_pay_chequeno,\n" +
                    "3weel_payment.3weel_pay_cheque_date,\n" +
                    "3weel_payment.3weel_pay_bank,\n" +
                    "3weel_payment.3weel_pay_total,\n" +
                    "3weel_park_type.3weel_park_type,\n" +
                    "3weel_payment.3weel_receipt_no,\n" +
                    "receipt.idReceipt\n" +
                    "FROM\n" +
                    "3weel_payment\n" +
                    "INNER JOIN 3weel_customer_has_vehicle ON 3weel_payment.3weel_pay_customer_vehi_id = 3weel_customer_has_vehicle.3weel_customer_has_vehicle_id\n" +
                    "INNER JOIN 3weel_customer ON 3weel_customer_has_vehicle.3weel_customer_idtbl = 3weel_customer.3weel_cus_cus_tbl_id\n" +
                    "INNER JOIN 3weel_park_details ON 3weel_customer_has_vehicle.3weel_cus_has_vehi_park_id = 3weel_park_details.3weel_park_details_id\n" +
                    "INNER JOIN 3weel_vehi_registration ON 3weel_customer_has_vehicle.3weel_vehicle_id_tbl = 3weel_vehi_registration.3weel_Vehi_reg_id\n" +
                    "INNER JOIN 3weel_prov_code ON 3weel_vehi_registration.3weel_provine_id = 3weel_prov_code.3weel_prov_code_id\n" +
                    "INNER JOIN customer ON 3weel_customer.3weel_cus_idcustomer = customer.idCustomer\n" +
                    "INNER JOIN 3weel_park_type ON 3weel_park_details.3weel_park_detail_type_id = 3weel_park_type.3weel_park_type_id\n" +
                    "INNER JOIN receipt ON 3weel_payment.3weel_receipt_no = receipt.receipt_print_no\n" +
                    "WHERE\n" +
                    "\treceipt.idReceipt = " + text);

            if (data.last()) {
                appcat = 8;
                idRecipt = Integer.parseInt(text);
                txt_amount.setText(data.getString("3weel_pay_total"));
                txt_cusname.setText(data.getString("cus_name"));
                fullTot = data.getDouble("3weel_pay_total");
                //txt_dis.setText("");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void viewBookingDetails(String text) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "\tbook.idbook,\n" +
                    "\tbook.book_date,\n" +
                    "\tbook.customer_idCustomer,\n" +
                    "\tbook.book_amount,\n" +
                    "\tbook.book_diposit,\n" +
                    "\tbook.book_vat,\n" +
                    "\tbook.book_nbt,\n" +
                    "\tbook.book_stamp,\n" +
                    "\tbook.book_total,\n" +
                    "\tbook.book_cash,\n" +
                    "\tbook.book_chque,\n" +
                    "\tbook.book_chque_no,\n" +
                    "\tbook.book_book_pay_status,\n" +
                    "\tbook.book_book_status,\n" +
                    "\tbook.book_idRecipt,\n" +
                    "\tbook.book_reson_idbook_reson,\n" +
                    "\tbook.book_place_idbook_place,\n" +
                    "\tcustomer.cus_name,\n" +
                    "\treceipt.idReceipt,\n" +
                    "\treceipt.receipt_total,\n" +
                    "\treceipt.receipt_status,\n" +
                    "\tbook.book_pay_type,\n" +
                    "\tbook.book_idUser\n" +
                    "FROM\n" +
                    "\tbook\n" +
                    "INNER JOIN customer ON customer.idCustomer = book.customer_idCustomer\n" +
                    "INNER JOIN receipt ON receipt.recept_applicationId = book.idbook\n" +
                    "WHERE\n" +
                    "\treceipt.idReceipt =" + text);

            if (data.last()) {
                if (data.getInt("receipt_status") == 0) {

                    modle.Allert.notificationGood("Booking Receipt", text);
                    int book_pay_type = data.getInt("book_pay_type");

                    appcat = 10;
                    appid = data.getInt("idbook");
                    idRecipt = Integer.parseInt(text);
                    txt_amount.setText(data.getString("receipt_total"));
                    txt_cusname.setText(data.getString("cus_name"));
                    fullTot = data.getDouble("receipt_total");

                    if (book_pay_type == 2) {
                        System.out.println("Cheque");
                        btn_pay.setDisable(true);
                        check.setSelected(true);
                        cash.setSelected(false);
                        cash.setDisable(true);
                        txt_check.setText(fullTot + "");
                        txt_check.setDisable(false);
                        check_date.setDisable(false);
                        combo_bank.setDisable(false);
                    }
                    if (book_pay_type == 1) {
                        check.setSelected(false);
                        check.setDisable(true);
                        cash.setSelected(true);
                        txt_cash.setText(fullTot + "");
                        txt_check.setDisable(true);
                        check_date.setDisable(true);
                        combo_bank.setDisable(true);

                        btn_pay.setDisable(false);
                        System.out.println("Cash");
                    }


                } else if (data.getInt("receipt_status") == 1) {
                    modle.Allert.notificationGood("Booking Receipt", text + " Reprint");
                    int book_pay_type = data.getInt("book_pay_type");
                    appcat = 10;
                    appid = data.getInt("idbook");
                    idRecipt = Integer.parseInt(text);
                    txt_amount.setText(data.getString("receipt_total"));
                    txt_cusname.setText(data.getString("cus_name"));
                    fullTot = data.getDouble("receipt_total");

                    btn_pay.setDisable(true);
                    btn_print.setText("Re Print Bill");
                    btn_print.setDisable(false);


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }


    public void mixIncomeBillData(String text) {
        txt_check.setEditable(false);
        txt_cash.setEditable(false);
        try {

            ResultSet data = DB.getData("SELECT\n" +
                    "\treceipt.recept_applicationId,\n" +
                    "\tmixincome.idMixincome,\n" +
                    "\treceipt.idReceipt,\n" +
                    "\treceipt.Application_Catagory_idApplication_Catagory,\n" +
                    "\treceipt.receipt_print_no,\n" +
                    "\treceipt.cheack,\n" +
                    "\treceipt.cesh,\n" +
                    "\treceipt.receipt_total,\n" +
                    "\treceipt.receipt_day,\n" +
                    "\treceipt.receipt_status,\n" +
                    "\treceipt.receipt_syn,\n" +
                    "\treceipt.chque_no,\n" +
                    "\treceipt.chque_date,\n" +
                    "\treceipt.chque_bank,\n" +
                    "\tmixincome.mixincome_date,\n" +
                    "\tmixincome.mixincome_fulltotal,\n" +
                    "\tmixincome.mixincome_status,\n" +
                    "\tmixincome.mixincome_year,\n" +
                    "\tmixincome.customer_idCustomer,\n" +
                    "\tmixincome.mixincome_userid,\n" +
                    "\tcustomer.idCustomer,\n" +
                    "\tcustomer.cus_name,\n" +
                    "\tcustomer.cus_nic,\n" +
                    "\tcustomer.cus_mobile,\n" +
                    "\tcustomer.cus_tel,\n" +
                    "\tcustomer.cus_address_l1,\n" +
                    "\tcustomer.cus_address_l2,\n" +
                    "\tcustomer.cus_address_l3,\n" +
                    "\tcustomer.cus_sity,\n" +
                    "\tcustomer.cus_status,\n" +
                    "\tcustomer.cus_syn,\n" +
                    "\tcustomer.cus_reg_date,\n" +
                    "\tcustomer.cus_update_date,\n" +
                    "\tcustomer.cus_name_sinhala,\n" +
                    "\tcustomer.cus_address_l1_sinhala,\n" +
                    "\tcustomer.cus_address_l2_sinhala,\n" +
                    "\tcustomer.cus_address_l3_sinhala,\n" +
                    "\tmixincome.mixincome_paytype\n" +
                    "FROM\n" +
                    "\treceipt\n" +
                    "INNER JOIN mixincome ON mixincome.idMixincome = receipt.recept_applicationId\n" +
                    "INNER JOIN customer ON customer.idCustomer = mixincome.customer_idCustomer\n" +
                    "WHERE\n" +
                    "\treceipt.idReceipt =" + text);

            if (data.last()) {
                int receipt_status = data.getInt("receipt_status");

                if (receipt_status == 0) {
                    modle.Allert.notificationGood("MIX Income Receipt", text);
                    int mixincome_paytype = data.getInt("mixincome_paytype");

                    appcat = data.getInt("Application_Catagory_idApplication_Catagory");
                    appid = data.getInt("recept_applicationId");
                    fullTot = data.getDouble("receipt_total");
                    idRecipt = data.getInt("idReceipt");
                    txt_cusname.setText(data.getString("cus_name"));
                    txt_amount.setText(fullTot + "");

                    if (mixincome_paytype == 2) {//cheque

                        System.out.println("Cheque");
                        btn_pay.setDisable(true);
                        check.setSelected(true);
                        cash.setSelected(false);
                        cash.setDisable(true);
                        txt_check.setText(fullTot + "");
                        txt_check.setDisable(false);
                        check_date.setDisable(false);
                        combo_bank.setDisable(false);


                    }
                    if (mixincome_paytype == 1) {//cash
                        check.setSelected(false);
                        check.setDisable(true);
                        cash.setSelected(true);
                        txt_cash.setText(fullTot + "");
                        txt_check.setDisable(true);
                        check_date.setDisable(true);
                        combo_bank.setDisable(true);

                        btn_pay.setDisable(false);
                        System.out.println("Cash");

                    }
                } else if (receipt_status == 1) {
                    appcat = data.getInt("Application_Catagory_idApplication_Catagory");
                    appid = data.getInt("recept_applicationId");
                    fullTot = data.getDouble("receipt_total");
                    idRecipt = data.getInt("idReceipt");
                    txt_cusname.setText(data.getString("cus_name"));
                    txt_amount.setText(fullTot + "");
                    modle.Allert.notificationInfo("Already Paid", "Please Check");
                    btn_pay.setDisable(true);
                    btn_print.setDisable(false);
                    btn_print.setText("Re Print Bill");
                }


            } else {
                modle.Allert.notificationInfo("NO Receipt", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    final static int VATID = 33;
    final static int NBTID = 34;
    final static int STAMPID = 35;
    final static int CASH = 65;
    final static int CHQUE = 66;

    public void updateMixIncomeStatus(int appid, int appcat) {
        try {
            conn.DB.setData("UPDATE mixincome\n" +
                    "SET \n" +
                    " mixincome_status = '1'\n" +
                    "WHERE\n" +
                    "\tidMixincome = '" + appid + "'");

            ResultSet data1 = DB.getData("SELECT\n" +
                    "\treceipt.idReceipt,\n" +
                    "\treceipt.receipt_print_no,\n" +
                    "\tmixincome.idMixincome,\n" +
                    "\tmixincome.mixincome_userid,\n" +
                    "\treceipt.cheack,\n" +
                    "\treceipt.cesh\n" +
                    "FROM\n" +
                    "\treceipt\n" +
                    "INNER JOIN mixincome ON mixincome.idMixincome = receipt.recept_applicationId\n" +
                    "WHERE\n" +
                    "\treceipt.Application_Catagory_idApplication_Catagory =  '" + appcat + "'" +
                    "AND mixincome.idMixincome = " + appid);

            String reciptNo = null;
            int idRecipt = 0;
            int mixincome_userid = 0;

            double cheack = 0;
            double cesh = 0;
            if (data1.last()) {
                reciptNo = data1.getString("receipt_print_no");
                idRecipt = data1.getInt("idReceipt");
                mixincome_userid = data1.getInt("mixincome_userid");

                cheack = data1.getDouble("cheack");
                cesh = data1.getDouble("cesh");


            }

            String quary = "SELECT\n" +
                    "mixdata.idMixdata,\n" +
                    "mixdata.md_description,\n" +
                    "mixdata.md_amount,\n" +
                    "mixdata.md_vat,\n" +
                    "mixdata.md_nbt,\n" +
                    "mixdata.md_stamp,\n" +
                    "mixdata.md_total,\n" +
                    "mixdata.mixintype_idMixintype,\n" +
                    "mixdata.mixincome_IdMixincome,\n" +
                    "mixintype.idMixintype,\n" +
                    "mixintype.mixintype_name,\n" +
                    "mixintype.account_receipt_title_idAccount_receipt_title,\n" +
                    "mixintype.mixintype_status,\n" +
                    "account_receipt_title.idAccount_receipt_title,\n" +
                    "mixintype.bankinfo_idBank\n" +
                    "FROM\n" +
                    "\tmixdata\n" +
                    "INNER JOIN mixintype ON mixdata.mixintype_idMixintype = mixintype.idMixintype\n" +
                    "INNER JOIN account_receipt_title ON mixintype.account_receipt_title_idAccount_receipt_title = account_receipt_title.idAccount_receipt_title\n" +
                    "WHERE\n" +
                    "\tmixincome_IdMixincome =" + appid;

            ResultSet data = DB.getData(quary);

            Date systemDate = GetInstans.getQuater().getSystemDate();
            String date = new SimpleDateFormat("yyyy-MM-dd").format(systemDate);
            Integer idUser = StaticViews.getLogUser().getIdUser();


            int bankinfo_idBank = 0;
            while (data.next()) {
                double md_amount = data.getDouble("md_amount");
                int idVote = data.getInt("account_receipt_title_idAccount_receipt_title");
                bankinfo_idBank = data.getInt("bankinfo_idBank");
                if (md_amount > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, idVote, bankinfo_idBank, md_amount, mixincome_userid, appid, appcat,1);
                }
                double vat = data.getDouble("md_vat");
                if (vat > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, VATID, bankinfo_idBank, vat, mixincome_userid, appid, appcat,1);
                }
                double nbt = data.getDouble("md_nbt");
                if (nbt > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, NBTID, bankinfo_idBank, nbt, mixincome_userid, appid, appcat,1);
                }
                double stamp = data.getDouble("md_stamp");
                if (stamp > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, STAMPID, bankinfo_idBank, stamp, mixincome_userid, appid, appcat,1);
                }
            }

            if (cesh > 0) {
                modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, CASH, bankinfo_idBank, cesh, mixincome_userid, appid, appcat,1);
            }

            if (cheack > 0) {
                modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, CHQUE, bankinfo_idBank, cheack, mixincome_userid, appid, appcat,1);
            }

            conn.DB.setData("UPDATE \n" +
                    "`receipt`\n" +
                    "SET \n" +
                    " `receipt_account_id` = '" + bankinfo_idBank + "',\n" +
                    " `receipt_user_id` = '" + mixincome_userid + "'\n" +
                    "WHERE\n" +
                    "\t(`idReceipt` = '" + idRecipt + "');\n");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void onKeyRelesed(KeyEvent event) {
        double ca = 0, ch = 0;
        JFXTextField tf = (JFXTextField) event.getSource();

        if (tf == txt_check) {
            if (txt_check.getText().matches("\\d*(\\.\\d*)?")) {
                if (txt_check.getText().length() == 0) {
                    ch = 0;
                    txt_cash.setText(fullTot + "");
                    txt_check.setText("0");
                } else {
                    ch = Double.parseDouble(txt_check.getText());
                    if (fullTot - ch > 0) {
                        txt_cash.setText(fullTot - ch + "");
                    } else {
                        java.awt.Toolkit.getDefaultToolkit().beep();
                        txt_check.deletePreviousChar();
                        ch = Double.parseDouble(txt_check.getText());
                    }
                }

            } else {
                java.awt.Toolkit.getDefaultToolkit().beep();
                txt_check.deletePreviousChar();
            }
        } else {
            java.awt.Toolkit.getDefaultToolkit().beep();
            txt_check.deletePreviousChar();
        }

        if (tf == txt_cash) {
            if (txt_cash.getText().matches("\\d*(\\.\\d*)?")) {
                if (txt_cash.getText().length() == 0) {
                    ca = 0;
                    txt_check.setText(fullTot + "");
                    txt_cash.setText("0");
                } else {
                    ca = Double.parseDouble(txt_cash.getText());
                    if (fullTot - ca > 0) {
                        txt_check.setText(fullTot - ca + "");
                    } else {
                        java.awt.Toolkit.getDefaultToolkit().beep();
                        txt_cash.deletePreviousChar();
                        ca = Double.parseDouble(txt_cash.getText());
                    }
                }

            } else {
                java.awt.Toolkit.getDefaultToolkit().beep();
                txt_cash.deletePreviousChar();
            }
        }
    }


    @FXML
    void chequeOnAction(ActionEvent event) {
        chequeValidate();
    }

    @FXML
    void chequeNoReleased(KeyEvent event) {
        chequeValidate();
    }

    public void chequeValidate() {
        if (check.isSelected()) {
            String text1 = txt_check.getText();
            String text = txt_chq_no.getText();
            LocalDate value = check_date.getValue();
            SingleSelectionModel<String> selectionModel = combo_bank.getSelectionModel();

            if (text1 != null && text.length() > 0 && text != null && text.length() > 0 && value != null && selectionModel != null) {
                btn_pay.setDisable(false);
            } else {
                btn_pay.setDisable(true);
            }

        }
    }


    public void cheackBox() {
        if (cash.isSelected() && check.isSelected()) {
            btn_pay.setDisable(true);
            txt_cash.setDisable(false);
            txt_check.setDisable(false);
            txt_cash.setEditable(true);
            txt_check.setEditable(true);
            txt_chq_no.setDisable(false);
            combo_bank.setDisable(false);
            btn_pay.setDisable(false);
        } else if (check.isSelected()) {
            btn_pay.setDisable(false);
            txt_check.setDisable(false);
            txt_check.setText(fullTot + "");
            txt_cash.setText("00");
            txt_cash.setDisable(true);
            txt_check.setEditable(false);
            txt_chq_no.setDisable(false);
            combo_bank.setDisable(false);
            btn_pay.setDisable(false);
        } else if (cash.isSelected()) {
            btn_pay.setDisable(false);
            txt_cash.setText(fullTot + "");
            txt_cash.setEditable(false);
            txt_cash.setDisable(false);
            txt_check.setDisable(true);
            txt_chq_no.setDisable(true);
            combo_bank.setDisable(true);
            txt_check.setText("00");
            btn_pay.setDisable(false);
        } else {
            btn_pay.setDisable(true);
            txt_check.setDisable(true);
            txt_cash.setDisable(true);
            txt_cash.setText("00");
            txt_check.setText("00");
            txt_chq_no.setDisable(true);
            combo_bank.setDisable(true);
            txt_chq_no.setDisable(true);
            combo_bank.setDisable(true);
            btn_pay.setDisable(true);
        }
    }

    @FXML
    void clickOnPrintBill(MouseEvent event) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().commit();
        session.close();

        if (appcat == 1) {
            new modle.adv.Report().pringAdvBill(appid, false);
        }

        if (appcat == 2) {
            dec.reprintAssessBill(idRecipt + "");
        }
        if (appcat == 7) {
            modle.GetInstans.getAssessReport().getReciptPrintStrretLine(idRecipt + "", false);
        }

        if (appcat == 9) {
            modle.GetInstans.getGenarateRecipt().genarateRecipt(idRecipt,false);
        }

        if (appcat == 10) {
            modle.book.Recipt.genarateBookingRecipt(idRecipt + "",false);
        }

        if (appcat == 11) {
            modle.GetInstans.getGenarateRecipt().genarateRecipt(idRecipt,false);
        }

        if (appcat == 13) {
            new modle.asses.RiBillPrint().PrintRiBill(idRecipt, false);
        }

        if (appcat == 14) {
            modle.GetInstans.getAssessReport().getReciptPrintNonVesting(idRecipt + "",false);
        }


        clearAllData();
        enable();
        System.gc();

    }

    public void clearAllData() {
        appcat = 0;
        appid = 0;
        idRecipt = 0;
        txt_id.setText("");
        txt_amount.setText("");
        txt_cusname.setText("");
        txt_dis.setText("");
        txt_check.setText("");
        txt_cash.setText("");
        txt_chq_no.setText("");
        cash.setSelected(false);
        check.setSelected(false);
        btn_pay.setDisable(true);
        btn_print.setDisable(true);
        modle.StaticBadu.setAppid(0);
        // combo_bank.setSelectionModel(null);
        loadBanksCombo();
        check_date.setValue(null);

        cheackBox();
    }

    public void dissableFilds() {
        txt_id.setStyle("-fx-background-color: #37ff0f");
        cash.setDisable(true);
        check.setDisable(true);
        txt_check.setDisable(true);
        txt_cash.setDisable(true);
        txt_chq_no.setDisable(true);
        combo_bank.setDisable(true);
        check_date.setDisable(true);
        btn_pay.setDisable(false);
    }

    public void enable() {
        txt_id.setText(null);
        txt_cusname.setText(null);
        txt_amount.setText("00");
        txt_id.setStyle("-fx-background-color: #FFFFFF");
        cash.setDisable(false);
        check.setDisable(false);
        txt_check.setDisable(false);
        txt_cash.setDisable(false);
        txt_chq_no.setDisable(false);
        combo_bank.setDisable(false);
        btn_pay.setDisable(false);
        check_date.setDisable(false);
        btn_print.setText("Print Bill");
    }


}
