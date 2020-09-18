package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import conn.DB;

import java.net.URL;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import conn.NewHibernateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import modle.AmountToWord.Convert;
import modle.KeyVal;
import modle.asses.DayEndProcess;
import modle.asses.Quater;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import pojo.AssPayment;
import pojo.AssPayto;
import pojo.Assessment;
import pojo.Receipt;
import sun.text.resources.FormatData;

/**
 * FXML Controller class
 *
 * @author Ranga Rathnayake
 */
public class DayendController implements Initializable {

    @FXML
    public TableView<DayEndTbl> tbl_DE;
    @FXML
    public TableColumn<DayEndTbl, Integer> col_id;
    @FXML
    public TableColumn<DayEndTbl, String> col_ward;
    @FXML
    public TableColumn<DayEndTbl, String> col_street;
    @FXML
    public TableColumn<DayEndTbl, String> col_assess;

    @FXML
    public TableColumn<DayEndTbl, Double> col_value;
    @FXML
    private TableColumn<DayEndTbl, String> col_rn;

    @FXML
    private Text txt_idSelected;
    @FXML
    private JFXButton btn_DayEnd;
    @FXML
    private JFXButton btn_dayEndOne;
    @FXML
    private TableView<DayEndTbl> tbl_DE_completed;
    @FXML
    private TableColumn<DayEndTbl, Integer> col_id1;
    @FXML
    private TableColumn<DayEndTbl, String> col_ward1;
    @FXML
    private TableColumn<DayEndTbl, String> col_street1;
    @FXML
    private TableColumn<DayEndTbl, String> col_assess1;

    @FXML
    private TableColumn<DayEndTbl, Double> col_value1;
    @FXML
    private TableColumn<DayEndTbl, String> col_rn1;

    @FXML
    private JFXTextField text_search;

    Date systemDate = null;
    @FXML
    private JFXButton btn_reprint;
    @FXML
    private Text txt_reprintid;
    @FXML
    private JFXDatePicker day_select;
    @FXML
    private Text txt_day;

    SimpleDateFormat format;


    @FXML
    private TableView<DayEndTbl> tbl_DE_canclled;

    @FXML
    private TableColumn<DayEndTbl, Integer> col_idc1;

    @FXML
    private TableColumn<DayEndTbl, String> col_ward11;

    @FXML
    private TableColumn<DayEndTbl, String> col_street11;

    @FXML
    private TableColumn<DayEndTbl, String> col_assess11;


    @FXML
    private TableColumn<DayEndTbl, String> col_valuec1;

    @FXML
    private TableColumn<DayEndTbl, String> col_rnc1;

    @FXML
    private Text text_cancelledID;

    @FXML
    private JFXButton btn_reprint1;


    int officeid = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modle.StaticViews.getMc().changeTitle("Day End Controller");
        // TODO
        systemDate = modle.GetInstans.getQuater().getSystemDate();

        format = new SimpleDateFormat("yyyy-MM-dd");
        Date systemdate = new Date();
        String system = format.format(systemdate);
        txt_day.setText(system);

        officeid = modle.StaticViews.getLogUser().getOfficeIdOffice();

        new Thread(() -> {
            try {

                col_assess.setCellValueFactory(new PropertyValueFactory<>("asess"));
                col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
                col_ward.setCellValueFactory(new PropertyValueFactory<>("ward"));
                col_street.setCellValueFactory(new PropertyValueFactory<>("street"));
//                col_cusname.setCellValueFactory(new PropertyValueFactory<>("cus"));
                col_value.setCellValueFactory(new PropertyValueFactory<>("value"));
                col_rn.setCellValueFactory(new PropertyValueFactory<>("print"));

                col_assess1.setCellValueFactory(new PropertyValueFactory<>("asess"));
                col_id1.setCellValueFactory(new PropertyValueFactory<>("id"));
                col_ward1.setCellValueFactory(new PropertyValueFactory<>("ward"));
                col_street1.setCellValueFactory(new PropertyValueFactory<>("street"));
//                col_cusname1.setCellValueFactory(new PropertyValueFactory<>("cus"));
                col_value1.setCellValueFactory(new PropertyValueFactory<>("value"));
                col_rn1.setCellValueFactory(new PropertyValueFactory<>("print"));

                col_assess11.setCellValueFactory(new PropertyValueFactory<>("asess"));
                col_idc1.setCellValueFactory(new PropertyValueFactory<>("id"));
                col_ward11.setCellValueFactory(new PropertyValueFactory<>("ward"));
                col_street11.setCellValueFactory(new PropertyValueFactory<>("street"));
//                col_cusnamec1.setCellValueFactory(new PropertyValueFactory<>("cus"));
                col_valuec1.setCellValueFactory(new PropertyValueFactory<>("value"));
                col_rnc1.setCellValueFactory(new PropertyValueFactory<>("print"));

                loadTodayPaid(null);
                loadTodayPaidCompleet(null);
                loadTodayCanceled(null);
            } catch (Exception e) {
                modle.Allert.notificationGood("Errorr", e.getMessage());
                modle.ErrorLog.writeLog(e.getMessage(), "dayEnd", "init", "");
            }
        }).start();

    }

    ObservableList<DayEndTbl> cancelList = FXCollections.observableArrayList();
    ObservableList<DayEndTbl> oalcomplete = FXCollections.observableArrayList();
    ObservableList<DayEndTbl> oal = FXCollections.observableArrayList();

    public void loadTodayPaid(String id) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().commit();

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
                    "ass_payment.office_idOffice\n" +
                    "FROM\n" +
                    "\treceipt\n" +
                    "LEFT JOIN ass_payment ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                    "WHERE\n" +
                    "\treceipt.receipt_day = '" + txt_day.getText() + "'\n" +
                    "AND receipt.receipt_status = 0 AND receipt.office_idOffice = '" + officeid + "'";

            if (id != null) {
                quary += " AND (idReceipt LIKE '%" + id + "%' OR receipt_print_no LIKE '%" + id + "%')";
            }

            ResultSet data = DB.getData(quary);

            oal.clear();
            while (data.next()) {
                int appcatid = data.getInt("Application_Catagory_idApplication_Catagory");


                oal.add(new DayEndTbl(data.getInt("idReceipt"), data.getInt("Application_Catagory_idApplication_Catagory"), "", "", "", "", modle.Round.round(data.getDouble("receipt_total")), data.getString("receipt_print_no")));


            }

            tbl_DE.setItems(oal);

        } catch (Exception e) {
            modle.Allert.notificationGood("Error", "Errorr");
            modle.ErrorLog.writeLog(e.getMessage(), "DayendController", "LadTodayPaid", "controller.assess");
            e.printStackTrace();

        } finally {
            session.close();
        }
    }

    public void loadTodayPaidCompleet(String id) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().commit();

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
                    "ass_payment.office_idOffice\n" +
                    "FROM\n" +
                    "\treceipt\n" +
                    "LEFT JOIN ass_payment ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                    "WHERE\n" +
                    "\treceipt.receipt_day = '" + txt_day.getText() + "'\n" +
                    "AND receipt.receipt_status = 1 AND receipt.office_idOffice = '" + officeid + "'";
            if (id != null) {
                quary += "AND (idReceipt LIKE '%" + id + "%' OR receipt_print_no LIKE '%" + id + "%')";
            }

            ResultSet data = DB.getData(quary);

            oalcomplete.clear();
            while (data.next()) {
//                AssPayment assPayment = (pojo.AssPayment) session.load(pojo.AssPayment.class, data.getInt("idass_Payment"));
//                oalcomplete.add(new DayEndTbl(assPayment.getAssessment().getIdAssessment(), assPayment.getReceipt().getIdReceipt(), assPayment.getAssessment().getWard().getWardName(), assPayment.getAssessment().getStreet().getStreetName(), assPayment.getAssessment().getAssessmentNo(), assPayment.getAssessment().getCustomer().getCusName(), assPayment.getAssPaymentFullTotal() + assPayment.getAssPaymentGotoDebit(), data.getString("receipt_print_no")));
                oalcomplete.add(new DayEndTbl(data.getInt("idReceipt"), data.getInt("Application_Catagory_idApplication_Catagory"), "", "", "", "", modle.Round.round(data.getDouble("receipt_total")), data.getString("receipt_print_no")));

            }

            tbl_DE_completed.setItems(oalcomplete);

        } catch (Exception e) {
            modle.Allert.notificationGood("Error", "Errorr");
            modle.ErrorLog.writeLog(e.getMessage(), "DayendController", "LadTodayPaid", "controller.assess");
            e.printStackTrace();

        } finally {
            session.close();
        }

    }


    public void loadTodayCanceled(String id) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().commit();

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
                    "ass_payment.office_idOffice\n" +
                    "FROM\n" +
                    "\treceipt\n" +
                    "LEFT JOIN ass_payment ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                    "WHERE\n" +
                    "\treceipt.receipt_day = '" + txt_day.getText() + "'\n" +
                    "AND receipt.receipt_status = 2 AND receipt.office_idOffice = '" + officeid + "' ";
            if (id != null) {
                quary += "AND (idReceipt LIKE '%" + id + "%' OR receipt_print_no LIKE '%" + id + "%')";
            }

            ResultSet data = DB.getData(quary);

            cancelList.clear();
            while (data.next()) {
//                AssPayment assPayment = (pojo.AssPayment) session.load(pojo.AssPayment.class, data.getInt("idass_Payment"));
//                oalcomplete.add(new DayEndTbl(assPayment.getAssessment().getIdAssessment(), assPayment.getReceipt().getIdReceipt(), assPayment.getAssessment().getWard().getWardName(), assPayment.getAssessment().getStreet().getStreetName(), assPayment.getAssessment().getAssessmentNo(), assPayment.getAssessment().getCustomer().getCusName(), assPayment.getAssPaymentFullTotal() + assPayment.getAssPaymentGotoDebit(), data.getString("receipt_print_no")));
                cancelList.add(new DayEndTbl(data.getInt("idReceipt"), data.getInt("Application_Catagory_idApplication_Catagory"), "", "", "", "", modle.Round.round(data.getDouble("receipt_total")), data.getString("receipt_print_no")));

            }

            tbl_DE_canclled.setItems(cancelList);

        } catch (Exception e) {
            modle.Allert.notificationGood("Error", "Errorr");
            modle.ErrorLog.writeLog(e.getMessage(), "DayendController", "LadTodayPaid", "controller.assess");
            e.printStackTrace();

        } finally {
            session.close();
        }

    }

    int idrecit = 0;

    @FXML
    private void selectFromTable(MouseEvent event) {
        idrecit = tbl_DE.getSelectionModel().getSelectedItem().getId();
        txt_idSelected.setText(idrecit + "");
        txt_reprintid.setText("");
        btn_reprint.setDisable(true);
    }

    @FXML
    private void clickOnCancleBill(MouseEvent event) {
        // Integer id = tbl_DE_completed.getSelectionModel().getSelectedItem().getId();
        // Integer idPay = tbl_DE.getSelectionModel().getSelectedItem().getIdPay();
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Cancle Bill");
//        alert.setHeaderText("You are going to cancle This Bill \n Are you sure to delete this? \n Click Ok");
//        alert.setContentText("Recipt ID :" + idrecit);
//
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.get() == ButtonType.OK) {
//            // ... user chose OK
//           cancleBill(idrecit);
//            loadTodayPaid(null);
//        } else {
//            // ... user chose CANCEL or closed the dialog්‍
//        }


        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Confirm");
        dialog.setHeaderText("Are you sure to cancel this receipt ? \nRecipt ID : " + idrecit);
        dialog.setContentText("Please enter the reason :");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().length() > 0) {
                System.out.println("Reason : " + result.get());
                cancleBill(idrecit, result.get());
            } else {
                clickOnCancleBill(event);
            }

        }


    }

    public void cancleBill(int idRecipt, String reason) {

        int appid = 0;

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "receipt.Application_Catagory_idApplication_Catagory, receipt.recept_applicationId\n" +
                    "FROM `receipt`\n" +
                    "WHERE\n" +
                    "receipt.idReceipt = '" + idRecipt + "'");

            int appcat = 0;
            if (data.last()) {
                appcat = data.getInt("Application_Catagory_idApplication_Catagory");
                appid = data.getInt("recept_applicationId");
            }

            switch (appcat) {
                case 1:
                    modle.Payment.CancleRecipt.cancleRecipt(idRecipt, reason);

                    conn.DB.setData("UPDATE `adv_advertising` SET   `adv_paid_notpaid`='2' WHERE (`receipt_idReceipt`='" + idRecipt + "')");
                    break;
                case 2:


                    ResultSet com = DB.getData("SELECT\n" +
                            "ass_payment.Receipt_idReceipt,\n" +
                            "ass_payment.ass_Payment_Status\n" +
                            "FROM\n" +
                            "ass_payment\n" +
                            "WHERE\n" +
                            "ass_payment.Receipt_idReceipt = '" + idRecipt + "'\n");

                    if (com.last()) {
                        int ass_payment_status = com.getInt("ass_Payment_Status");
                        if (ass_payment_status == 0) {
                            modle.Payment.CancleRecipt.cancleRecipt(idRecipt, reason);

                            conn.DB.setData("UPDATE `ass_payment`\n" +
                                    "SET \n" +
                                    " ass_Payment_Status = 2\n" +
                                    "WHERE\n" +
                                    "\t(`Receipt_idReceipt` = '" + idRecipt + "')");

                        } else {
                            modle.Allert.notificationInfo("Can Not Delete This", "Day End Completed");
                        }

                    }


                    break;

                case 9:
                    conn.DB.setData("UPDATE `mixincome`\n" +
                            "SET \n" +
                            " `mixincome_status` = '2' \n" +
                            "WHERE\n" +
                            "\t(`idMixincome` = '" + appid + "')");

                    modle.Payment.CancleRecipt.cancleRecipt(idRecipt, reason);

                    conn.DB.setData("UPDATE `account_ps_three`\n" +
                            "SET \n" +
                            " `report_status` = '2'\n" +
                            "WHERE\n" +
                            "\t(`report_ricipt_id` = '" + idRecipt + "')");
                    break;
                case 10:
                    modle.Payment.CancleRecipt.cancleRecipt(idRecipt, reason);
                    conn.DB.setData("UPDATE `account_ps_three` \n" +
                            "SET \n" +
                            "`report_status` = 2\n" +
                            "WHERE\n" +
                            "\t`report_ricipt_id` = " + idRecipt);
                    break;

                default:
//                    conn.DB.setData("UPDATE `receipt`\n" +
//                            "SET \n" +
//                            " `receipt_status` = '2'\n" +
//                            "WHERE\n" +
//                            "\t(`idReceipt` = '" + idRecipt + "')");
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        loadTodayPaid(null);
        loadTodayPaidCompleet(null);
        loadTodayCanceled(null);
        txt_idSelected.setText("");
        text_search.setText("");

    }

    @FXML
    private void clickOnDayEnd(MouseEvent event) {

    }

    @FXML
    private void clickOnSelectedOneProcess(MouseEvent event) {

        if (!txt_idSelected.getText().equals("")) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = null;
            try {
                d = sdf.parse(txt_day.getText());
                btn_dayEndOne.setDisable(true);

                Integer idPay = tbl_DE.getSelectionModel().getSelectedItem().getId();

                if (tbl_DE.getSelectionModel().getSelectedItem().getIdPay() == 2) {

                    // DayEndProcess de = new modle.asses.DayEndProcess();
                    //  de.dayEndProcessForOne(idPay, d);

                }

                if (tbl_DE.getSelectionModel().getSelectedItem().getIdPay() == 1) {
                    System.out.println(tbl_DE.getSelectionModel().getSelectedItem().getPrint());
                    //  modle.GetInstans.getAdvPayModle().compleetePayment(idPay);
                }

                if (tbl_DE.getSelectionModel().getSelectedItem().getIdPay() == 13) {
                    // modle.GetInstans.getRiBillComplete().completeRi(idPay + "");
                }

                loadTodayPaid(null);
                loadTodayPaidCompleet(null);

            } catch (ParseException ex) {
                Logger.getLogger(DayEndProcess.class.getName()).log(Level.SEVERE, null, ex);
            }

            btn_dayEndOne.setDisable(false);

        } else {
            modle.Allert.notificationInfo("Empty Selection", "please select in table");
        }
    }

    @FXML
    private void onKeyTypeOnSearch(KeyEvent event) {
        if (text_search.getText().length() > 0) {
            loadTodayPaidCompleet(text_search.getText());
            loadTodayPaid(text_search.getText());
            loadTodayCanceled(text_search.getText());
        } else {
            loadTodayPaid(null);
            loadTodayPaidCompleet(null);
        }

    }


    String pid = "";

    @FXML
    private void clickOnReprint(MouseEvent event) {
//        int recept_applicationId = 0;
//        try {
//            ResultSet data = DB.getData("SELECT\n" +
//                    "receipt.Application_Catagory_idApplication_Catagory, receipt.recept_applicationId\n" +
//                    "FROM `receipt`\n" +
//                    "WHERE\n" +
//                    "receipt.idReceipt = '" + txt_reprintid.getText() + "'");
//
//            int appcat = 0;
//            if (data.last()) {
//                appcat = data.getInt("Application_Catagory_idApplication_Catagory");
//                recept_applicationId = data.getInt("recept_applicationId");
//
//                System.out.println(appcat);
//                System.out.println(recept_applicationId);
//
//            }
//            switch (appcat) {
//                case 1:
//                    new modle.adv.Report().pringAdvBill(recept_applicationId);
//                    System.gc();
//                    break;
//                case 2:
        reprintAssessBill(txt_reprintid.getText());
//                    break;
//                default:
//                    break;
//            }

//    } catch(
//    Exception e)
//
//    {
//        e.printStackTrace();
//    } finally
//
//    {
//    }
    }

    @FXML
    private void selectFromTablePaid(MouseEvent event) {
        idrecit = tbl_DE_completed.getSelectionModel().getSelectedItem().getId();
        pid = idrecit + "";
        System.out.println(pid);
        txt_reprintid.setText(idrecit + "");
        txt_idSelected.setText(idrecit + "");
        btn_reprint.setDisable(false);
    }

    @FXML
    private void clickOnRefresh(MouseEvent event) {
        loadTodayPaid(null);
        loadTodayPaidCompleet(null);
        loadTodayCanceled(null);
        txt_idSelected.setText("");
        text_search.setText("");

    }

    @FXML
    private void set_date(ActionEvent event) {
        Date selectDate = Date.from(day_select.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());

        String selected = format.format(selectDate);
        txt_day.setText(selected);
    }

    public class DayEndTbl {

        /**
         * @return the id
         */
        public Integer getId() {
            return id;
        }

        /**
         * @return the ward
         */
        public String getWard() {
            return ward.get();
        }

        /**
         * @return the street
         */
        public String getStreet() {
            return street.get();
        }

        /**
         * @return the asess
         */
        public String getAsess() {
            return asess.get();
        }

        /**
         * @return the cus
         */
        public String getCus() {
            return cus.get();
        }

        /**
         * @return the value
         */
        public Double getValue() {
            return value;
        }

        private final Integer id;
        private final Integer idPay;
        private final SimpleStringProperty ward;
        private final SimpleStringProperty street;
        private final SimpleStringProperty asess;
        private final SimpleStringProperty cus;
        private final Double value;
        private final SimpleStringProperty print;

        public DayEndTbl(Integer id, Integer idPay, String ward, String street, String asess, String cus, Double value, String print) {
            this.id = id;
            this.idPay = idPay;
            this.ward = new SimpleStringProperty(ward);
            this.street = new SimpleStringProperty(street);
            this.asess = new SimpleStringProperty(asess);
            this.cus = new SimpleStringProperty(cus);
            this.value = value;
            this.print = new SimpleStringProperty(print);
        }

        /**
         * @return the idPay
         */
        public Integer getIdPay() {
            return idPay;
        }

        public String getPrint() {
            return print.get();
        }

    }


    public void reprintAssessBill(String idRecipt) {

        String quary1 = "SELECT\n" +
                "receipt.idReceipt,\n" +
                "receipt.Application_Catagory_idApplication_Catagory, receipt.receipt_total\n" +
                "FROM\n" +
                "receipt where idReceipt =" + idRecipt;


        try {
            ResultSet data = DB.getData(quary1);
            if (data.last()) {
                String s = Convert.convertToWord(data.getDouble("receipt_total"));
                int apc = data.getInt("Application_Catagory_idApplication_Catagory");
                if (apc == 2) {
                    String quary = "SELECT\n" +
                            "\tass_payment.idass_Payment, ass_payment.Assessment_idAssessment \n" +
                            "FROM\n" +
                            "\tass_payment\n" +
                            "WHERE\n" +
                            "\tass_payment.Receipt_idReceipt =" + idRecipt;
                    ResultSet d = DB.getData(quary);
                    if (d.last()) {


                        int idAssessment = d.getInt("Assessment_idAssessment");

                        ResultSet sub = DB.getData("SELECT\n" +
                                "ass_subowner.ass_subOwner_name\n" +
                                "FROM\n" +
                                "ass_subowner\n" +
                                "WHERE\n" +
                                "ass_subowner.Assessment_idAssessment = " + idAssessment + " AND\n" +
                                "ass_subowner.ass_subOwner_status = 1");

                        String subowners = "";
                        while (sub.next()) {
                            subowners += ", " + sub.getString("ass_subOwner_name");
                        }


                        String idass_payment = d.getString("idass_Payment");
                        ResultSet d2 = DB.getData("SELECT * FROM\n" +
                                "ass_payment\n" +
                                "INNER JOIN ass_payto ON ass_payto.ass_Payment_idass_Payment = ass_payment.idass_Payment\n" +
                                "WHERE idass_Payment = '" + idass_payment + "'");

                        double tya = 0;
                        double tyw = 0;

                        while (d2.next()) {
                            System.out.println(d2.getString("idass_Payment"));
                            tya += d2.getDouble("ass_payto.ass_payto_arrears");
                            tyw += d2.getDouble("ass_payto.ass_payto_warrant");
                        }


                        String assessbilltype = KeyVal.getVal("assessbilltype");
                        if (assessbilltype.equals("short")) {
                            modle.GetInstans.getAssessReport().getReciptView(idass_payment, tyw, tya, false, subowners, s);
                        } else if (assessbilltype.equals("long")) {
                            modle.GetInstans.getAssessReport().longBill(idass_payment, false, subowners, s);
                        } else if (assessbilltype.equals("plane")) {
                            modle.GetInstans.getAssessReport().getReciptPrintPlane(idass_payment + "", tyw, tya, false, subowners, s); //Plane
                        } else {
                            modle.GetInstans.getAssessReport().getReciptPrintEdited1(idass_payment + "", tyw, tya, false, subowners, s); //Report
                        }
                    }

                } else if (apc == 1) {
                    String quary2 = "SELECT\n" +
                            "adv_advertising.idAdv_Advertising\n" +
                            "FROM\n" +
                            "adv_advertising\n" +
                            "where receipt_idReceipt = " + idRecipt;
                    ResultSet d2 = DB.getData(quary2);
                    if (d2.last()) {
                        int idAdv_advertising = d2.getInt("idAdv_Advertising");
                        modle.GetInstans.getAdvReport().pringAdvBill(idAdv_advertising, false);
                    }
                } else if (apc == 7) {
                    String quary2 = "SELECT\n" +
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
                            "\treceipt.chque_bank\n" +
                            "FROM\n" +
                            "\t`receipt`\n" +
                            "WHERE\n" +
                            "\tidReceipt = " + idRecipt;
                    ResultSet adv = DB.getData(quary2);
                    if (adv.last()) {
                        int recept_applicationId = adv.getInt("recept_applicationId");
                        System.out.println(recept_applicationId + " APP ID");
                        modle.GetInstans.getAssessReport().getReciptPrintStrretLine(idRecipt, false);
//                        modle.GetInstans.getAdvReport().pringAdvBill(recept_applicationId);
                    }
                } else if (apc == 9) {
                    modle.GetInstans.getGenarateRecipt().genarateRecipt(Integer.parseInt(idRecipt), false);
                } else if (apc == 8) {
                    modle.GetInstans.getThreweel().getVehiclepassReport(idRecipt, false);
                } else if (apc == 4) {
                    modle.GetInstans.getBillComplete().loadShopRentBillView(idRecipt);
                } else if (apc == 13) {
                    modle.GetInstans.getRiBillComplete().ribilRePrint(idRecipt + "");
                } else if (apc == 14) {
                    String quary2 = "SELECT\n" +
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
                            "\treceipt.chque_bank\n" +
                            "FROM\n" +
                            "\t`receipt`\n" +
                            "WHERE\n" +
                            "\tidReceipt = " + idRecipt;
                    ResultSet adv = DB.getData(quary2);
                    if (adv.last()) {
                        int recept_applicationId = adv.getInt("recept_applicationId");
                        System.out.println(recept_applicationId + " APP ID");
                        modle.GetInstans.getAssessReport().getReciptPrintNonVesting(idRecipt, false);
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void reprintAssessBill(String idRecipt, boolean print) {

        String quary1 = "SELECT\n" +
                "receipt.idReceipt,\n" +
                "receipt.Application_Catagory_idApplication_Catagory, receipt.receipt_total \n" +
                "FROM\n" +
                "receipt where idReceipt =" + idRecipt;


        try {
            ResultSet data = DB.getData(quary1);
            if (data.last()) {


                String s = Convert.convertToWord(data.getDouble("receipt_total"));
                System.out.println(s);


                int apc = data.getInt("Application_Catagory_idApplication_Catagory");
                if (apc == 2) {
                    String quary = "SELECT\n" +
                            "\tass_payment.idass_Payment, ass_payment.Assessment_idAssessment \n" +
                            "FROM\n" +
                            "\tass_payment\n" +
                            "WHERE\n" +
                            "\tass_payment.Receipt_idReceipt =" + idRecipt;
                    ResultSet d = DB.getData(quary);
                    if (d.last()) {

                        int idAssessment = d.getInt("Assessment_idAssessment");

                        ResultSet sub = DB.getData("SELECT\n" +
                                "ass_subowner.ass_subOwner_name\n" +
                                "FROM\n" +
                                "ass_subowner\n" +
                                "WHERE\n" +
                                "ass_subowner.Assessment_idAssessment = " + idAssessment + " AND\n" +
                                "ass_subowner.ass_subOwner_status = 1");

                        String subowners = "";
                        while (sub.next()) {
                            subowners += ", " + sub.getString("ass_subOwner_name");
                        }


                        String idass_payment = d.getString("idass_Payment");
                        ResultSet d2 = DB.getData("SELECT * FROM\n" +
                                "ass_payment\n" +
                                "INNER JOIN ass_payto ON ass_payto.ass_Payment_idass_Payment = ass_payment.idass_Payment\n" +
                                "WHERE idass_Payment = '" + idass_payment + "'");

                        double tya = 0;
                        double tyw = 0;

                        while (d2.next()) {
                            System.out.println(d2.getString("idass_Payment"));
                            tya += d2.getDouble("ass_payto.ass_payto_arrears");
                            tyw += d2.getDouble("ass_payto.ass_payto_warrant");
                        }


                        String assessbilltype = KeyVal.getVal("assessbilltype");
                        if (assessbilltype.equals("short")) {
                            modle.GetInstans.getAssessReport().getReciptView(idass_payment, tyw, tya, print, subowners, s);
                        } else if (assessbilltype.equals("long")) {
                            modle.GetInstans.getAssessReport().longBill(idass_payment, print, subowners, s);
                        } else if (assessbilltype.equals("plane")) {
                            modle.GetInstans.getAssessReport().getReciptPrintPlane(idass_payment + "", tyw, tya, print, subowners, s); //Plane
                        } else {
                            modle.GetInstans.getAssessReport().getReciptPrintEdited1(idass_payment + "", tyw, tya, print, subowners, s); //Report
                        }
                    }


                } else if (apc == 1) {
                    String quary2 = "SELECT\n" +
                            "adv_advertising.idAdv_Advertising\n" +
                            "FROM\n" +
                            "adv_advertising\n" +
                            "where receipt_idReceipt = " + idRecipt;
                    ResultSet d2 = DB.getData(quary2);
                    if (d2.last()) {
                        int idAdv_advertising = d2.getInt("idAdv_Advertising");
                        modle.GetInstans.getAdvReport().pringAdvBill(idAdv_advertising, false);
                    }
                } else if (apc == 7) {
                    String quary2 = "SELECT\n" +
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
                            "\treceipt.chque_bank\n" +
                            "FROM\n" +
                            "\t`receipt`\n" +
                            "WHERE\n" +
                            "\tidReceipt = " + idRecipt;
                    ResultSet adv = DB.getData(quary2);
                    if (adv.last()) {
                        int recept_applicationId = adv.getInt("recept_applicationId");
                        System.out.println(recept_applicationId + " APP ID");
                        modle.GetInstans.getAssessReport().getReciptPrintStrretLine(idRecipt, false);
//                        modle.GetInstans.getAdvReport().pringAdvBill(recept_applicationId);
                    }
                } else if (apc == 9) {
                    modle.GetInstans.getGenarateRecipt().genarateRecipt(Integer.parseInt(idRecipt), false);
                } else if (apc == 8) {
                    modle.GetInstans.getThreweel().getVehiclepassReport(idRecipt, false);
                } else if (apc == 4) {
                    modle.GetInstans.getBillComplete().loadShopRentBillView(idRecipt);
                } else if (apc == 13) {
                    modle.GetInstans.getRiBillComplete().ribilRePrint(idRecipt + "");
                } else if (apc == 14) {
                    String quary2 = "SELECT\n" +
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
                            "\treceipt.chque_bank\n" +
                            "FROM\n" +
                            "\t`receipt`\n" +
                            "WHERE\n" +
                            "\tidReceipt = " + idRecipt;
                    ResultSet adv = DB.getData(quary2);
                    if (adv.last()) {
                        int recept_applicationId = adv.getInt("recept_applicationId");
                        System.out.println(recept_applicationId + " APP ID");
                        modle.GetInstans.getAssessReport().getReciptPrintNonVesting(idRecipt, false);
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTodayPaidAll(String id) {
        String quary = "SELECT\n" +
                "receipt.Application_Catagory_idApplication_Catagory,\n" +
                "receipt.idReceipt,\n" +
                "receipt.recept_applicationId,\n" +
                "receipt.receipt_print_no,\n" +
                "receipt.cheack,\n" +
                "receipt.cesh,\n" +
                "receipt.receipt_total,\n" +
                "receipt.receipt_day,\n" +
                "receipt.receipt_status,\n" +
                "receipt.receipt_syn\n" +
                "FROM `receipt`\n" +
                "where receipt_day = '" + txt_day.getText() + "' AND receipt_status = 0 ";

        if (id != null) {
            quary += " AND (idReceipt LIKE '%" + id + "%' OR receipt_print_no LIKE '%" + id + "%')";
        }


        try {
            ResultSet list = DB.getData(quary);
            oalcomplete.clear();
            while (list.next()) {
                int apcat = list.getInt("Application_Catagory_idApplication_Catagory");
                int idReceipt = list.getInt("idReceipt");
                if (apcat == 1) {

                    oalcomplete.add(new DayEndTbl(idReceipt, list.getInt("Application_Catagory_idApplication_Catagory"), "", "", "", "", list.getDouble("receipt_total"), list.getString("receipt_print_no")));
                    tbl_DE_completed.setItems(oalcomplete);
                } else if (apcat == 2) {

                    String q = "SELECT\n" +
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
                            "ass_payment.pay_user_id, ass_payment.cd_balance\n" +
                            "FROM\n" +
                            "receipt\n" +
                            "LEFT JOIN ass_payment ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                            "WHERE\n" +
                            "receipt.receipt_day = '" + txt_day.getText() + "' AND\n" +
                            "receipt.receipt_status = 1 AND idReceipt =" + idReceipt;


                    ResultSet data = DB.getData(quary);


                    while (data.next()) {
//                AssPayment assPayment = (pojo.AssPayment) session.load(pojo.AssPayment.class, data.getInt("idass_Payment"));
//                oalcomplete.add(new DayEndTbl(assPayment.getAssessment().getIdAssessment(), assPayment.getReceipt().getIdReceipt(), assPayment.getAssessment().getWard().getWardName(), assPayment.getAssessment().getStreet().getStreetName(), assPayment.getAssessment().getAssessmentNo(), assPayment.getAssessment().getCustomer().getCusName(), assPayment.getAssPaymentFullTotal() + assPayment.getAssPaymentGotoDebit(), data.getString("receipt_print_no")));
                        //                oalcomplete.add(new DayEndTbl(data.getInt("idReceipt"), data.getInt("Application_Catagory_idApplication_Catagory"), "", "", "", "", data.getDouble("receipt_total") + data.getDouble("ass_Payment_goto_debit") + data.getDouble("cd_balance"), data.getString("receipt_print_no")));

                    }

                    tbl_DE_completed.setItems(oalcomplete);


                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    void clickOnSendToPending(MouseEvent event) {

    }

}
