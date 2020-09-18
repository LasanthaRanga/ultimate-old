package controller.assess;

import com.jfoenix.controls.JFXButton;
import conn.DB;
import conn.NewHibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import modle.GetInstans;
import modle.KeyVal;
import modle.StaticViews;
import modle.asses.RibHolder;
import modle.asses.RibilsHolder;
import modle.asses.RipHolder;
import modle.book.Recipt;
import modle.popup.BarcodeStatic;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Receipt;
import pojo.RibillList;
import pojo.User;

import java.io.Serializable;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class Ribill implements Initializable {
    @FXML
    private TableView<RibilsHolder> tbl_not;

    @FXML
    private TableColumn<RibilsHolder, String> col_rino;

    @FXML
    private TableColumn<RibilsHolder, Double> col_tot;

    @FXML
    private TableColumn<RibilsHolder, String> col_pm;

    @FXML
    private TableView<RibilsHolder> tbl_ok;

    @FXML
    private TableColumn<RibilsHolder, String> col_rino2;

    @FXML
    private TableColumn<RibilsHolder, Double> col_tot2;

    @FXML
    private TableColumn<RibilsHolder, String> col_pm2;

    @FXML
    private Text txt_not_count;

    @FXML
    private Text txt_not_total;

    @FXML
    private Text txt_not_count2;

    @FXML
    private Text txt_not_total2;

    @FXML
    private JFXButton btn_make;

    @FXML
    private TableView<RibHolder> tbl_bill;

    @FXML
    private TableColumn<RibHolder, String> col_billno;

    @FXML
    private TableColumn<RibHolder, String> col_total;

    @FXML
    private TableColumn<RibHolder, String> col_status;

    @FXML
    private JFXButton btn_cancel;

    @FXML
    private Text txt_billNo;


    @FXML
    void clickOnNot(MouseEvent event) {
        User logUser = StaticViews.getLogUser();


        RibilsHolder selectedItem = tbl_not.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (added == null || added.size() == 0) {
                added.add(selectedItem);
                pendingList.remove(selectedItem);
            } else {
                RibilsHolder holder = null;
                for (RibilsHolder ho : added) {
                    holder = ho;
                }
                if (holder.getPayby() == selectedItem.getPayby()) {
                    added.add(selectedItem);
                    pendingList.remove(selectedItem);
                } else {
                    modle.Allert.notificationWorning("Payment Method Not Match", "Select One Payment Method first");
                }
            }


        }
        tbl_ok.setItems(added);
        count();
        modle.StaticViews.getMc().changeTitle("RI Assessment Bill");
    }

    @FXML
    void clickOnOk(MouseEvent event) {
        RibilsHolder selectedItem = tbl_ok.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            added.remove(selectedItem);
            pendingList.add(selectedItem);
        }
        count();
    }


    int selectedIdribill = 0;
    String billno = "";

    @FXML
    void clickOnRiTable(MouseEvent event) {
        RibHolder selectedItem = tbl_bill.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            billno = selectedItem.getBillno();
            txt_billNo.setText(billno);
            selectedIdribill = selectedItem.getId();
        }
    }


    @FXML
    void clickOnBtn(MouseEvent event) {
        if (selectedIdribill > 0) {
            boolean b = checkRiBillIsCompleted(selectedIdribill);
            if (!b) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cancle Bill");
                alert.setHeaderText("You are going to cancle This Bill \n Are you sure to delete this? \n Click Ok");
                alert.setContentText("Recipt ID :" + billno);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    cancleRiBill(selectedIdribill);
                    loadRibill();
                    loadRiBillTable();
                } else {
                    // ... user chose CANCEL or closed the dialog්‍
                }

            } else {
                modle.Allert.notificationWorning("Can't Delete this", "Day End Completed");
            }
        }
    }


    public boolean checkRiBillIsCompleted(int idRibill) {
        boolean de = false;
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ribill.bill_no,\n" +
                    "receipt.idReceipt,\n" +
                    "receipt.receipt_print_no,\n" +
                    "receipt.receipt_status,\n" +
                    "ass_payment.idass_Payment,\n" +
                    "ass_payment.ass_Payment_Status\n" +
                    "FROM\n" +
                    "ribill\n" +
                    "INNER JOIN ribill_list ON ribill_list.Ribill_idRibill = ribill.idRibill\n" +
                    "INNER JOIN receipt ON receipt.idReceipt = ribill_list.idRicit\n" +
                    "INNER JOIN ass_payment ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                    "WHERE\n" +
                    "ribill.idRibill = " + idRibill);

            while (data.next()) {
                int ass_payment_status = data.getInt("ass_Payment_Status");
                if (ass_payment_status == 1) {
                    de = true;
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return de;
    }


    public void cancleRiBill(int idRibill) {

        try {

            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Confirm");
            dialog.setHeaderText("Are you sure to cancel this receipt ? \nRecipt ID : " + idRibill);
            dialog.setContentText("Please enter the reason :");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                if (result.get().length() > 0) {
                    String reason = result.get();
                    System.out.println("Reason : " + result.get());

                    ResultSet data = DB.getData("SELECT\n" +
                            "ribill.idRibill,\n" +
                            "ribill_list.idRicit,\n" +
                            "ribill.ribill_status\n" +
                            "FROM\n" +
                            "ribill\n" +
                            "INNER JOIN ribill_list ON ribill_list.Ribill_idRibill = ribill.idRibill\n" +
                            "WHERE\n" +
                            "ribill.idRibill = " + idRibill);
                    while (data.next()) {
                        int idRicit = data.getInt("idRicit");
                        modle.Payment.CancleRecipt.cancleRecipt(idRicit, reason);
                        conn.DB.setData("UPDATE `ass_payment`\n" +
                                "SET \n" +
                                " `ass_Payment_Status` = '2' \n" +
                                "WHERE\n" +
                                "\t(`Receipt_idReceipt` = '" + idRicit + "')");

                    }
                    conn.DB.setData("UPDATE `ribill`\n" +
                            "SET \n" +
                            " `ribill_status` = '2'\n" +
                            "WHERE\n" +
                            "\t(`idRibill` = '" + idRibill + "')");
                    modle.Allert.notificationGood("Cancel Complete", billno);
                } else {
                    cancleRiBill(idRibill);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        col_rino.setCellValueFactory(new PropertyValueFactory<>("assess"));
        col_rino2.setCellValueFactory(new PropertyValueFactory<>("assess"));
        col_tot.setCellValueFactory(new PropertyValueFactory<>("total"));
        col_tot2.setCellValueFactory(new PropertyValueFactory<>("total"));
        col_pm.setCellValueFactory(new PropertyValueFactory<>("paymethod"));
        col_pm2.setCellValueFactory(new PropertyValueFactory<>("paymethod"));

        col_billno.setCellValueFactory(new PropertyValueFactory<>("billno"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("complete"));


        loadRibill();
        loadRiBillTable();

    }

    ObservableList<RibilsHolder> pendingList = FXCollections.observableArrayList();
    ObservableList<RibilsHolder> added = FXCollections.observableArrayList();

    public void loadRibill() {
        try {
            String query = "SELECT\n" +
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
                    "receipt.oder,\n" +
                    "receipt.office_idOffice,\n" +
                    "ass_payment.ass_Payment_idUser,\n" +
                    "ass_payment.Assessment_idAssessment,\n" +
                    "ass_payment.ass_check_no,\n" +
                    "assessment.assessment_no\n" +
                    "FROM\n" +
                    "receipt\n" +
                    "INNER JOIN ass_payment ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                    "INNER JOIN assessment ON ass_payment.Assessment_idAssessment = assessment.idAssessment\n" +
                    "WHERE\n" +
                    "\treceipt.receipt_status = '0'\n" +
                    "AND receipt.Application_Catagory_idApplication_Catagory = 2\n" +
                    "AND ass_payment.ass_Payment_idUser = '" + modle.StaticViews.getLogUser().getIdUser() + "'";


            ResultSet data = DB.getData(query);
            while (data.next()) {
                double cheack = data.getDouble("cheack");
                double cesh = data.getDouble("cesh");
                String paymethod = "";
                int pm = 0;
                if (cesh > 0 && cheack == 0) {
                    pm = 1;
                    paymethod = "cash";
                } else if (cheack > 0 && cesh == 0) {
                    pm = 2;
                    paymethod = "chque";
                } else {
                    pm = 3;
                    paymethod = "error";
                }
                pendingList.add(new RibilsHolder(data.getInt("idReceipt"), data.getString("receipt_print_no"), cesh + cheack, paymethod, pm, data.getString("ass_payment.ass_check_no"), data.getString("assessment.assessment_no")));
            }
            tbl_not.setItems(pendingList);
            count();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    public void count() {

        int pcount = 0;
        int okcount = 0;

        double pval = 0;
        double okval = 0;

        for (RibilsHolder ho : added) {
            okcount++;
            okval += ho.getTotal();
        }

        for (RibilsHolder ho : pendingList) {
            pcount++;
            pval += ho.getTotal();
        }

        txt_not_count.setText("Count : " + pcount);
        txt_not_total.setText("Total : " + modle.Round.roundToString(pval));

        txt_not_count2.setText("Count : " + okcount);
        txt_not_total2.setText("Total : " + modle.Round.roundToString(okval));

    }

    @FXML
    void clickOnMake(MouseEvent event) {
        if (added.size() > 0) {
            riBill();
            added.clear();
        }


        loadRiBillTable();
    }


    public void riBill() {
        Date systemDateByQuary = GetInstans.getQuater().getSystemDateByQuary();


        double tot = 0;
        for (RibilsHolder ho : added) {
            tot += ho.getTotal();
        }

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {

            int ribillid = 0;
            String ricitnos = "";
            String chequenos = "";
            String lastChequeNo = "";
            String ribillno = "";


            ResultSet dd = DB.getData("SELECT\n" +
                    "receipt_code_create.receipt_code_id,\n" +
                    "receipt_code_create.receipt_code,\n" +
                    "receipt_code_create.application_id,\n" +
                    "receipt_code_create.cheque_code,\n" +
                    "receipt_code_create.receipt_code_office_id\n" +
                    "FROM\n" +
                    "receipt_code_create\n" +
                    "WHERE\n" +
                    "receipt_code_create.application_id = 13 AND\n" +
                    "receipt_code_create.receipt_code_office_id = " + modle.StaticViews.getLogUser().getOfficeIdOffice());

            if (dd.last()) {
                ribillno += dd.getString("receipt_code");
            }


            ResultSet ddd = DB.getData("SELECT\n" +
                    "Max(ribill.oder)\n" +
                    "FROM `ribill`\n" +
                    "WHERE\n" +
                    "ribill.office_id =" + StaticViews.getLogUser().getOfficeIdOffice());

            int anInt = 0;
            if (ddd.last()) {
                anInt = ddd.getInt("Max(ribill.oder)");
                anInt = anInt + 1;
                ribillno += anInt;
            }

            pojo.Ribill ribill = new pojo.Ribill();
            ribill.setBilldate(systemDateByQuary);
            ribill.setBilltotal(modle.Round.round(tot));
            ribill.setUserid(modle.StaticViews.getLogUser().getIdUser());
            ribill.setBillNo(ribillno);
            ribill.setRibillStatus(0);
            ribill.setOder(anInt);
            ribill.setOfficeId(StaticViews.getLogUser().getOfficeIdOffice());
            Serializable save = session.save(ribill);
            ribillid = Integer.parseInt(save.toString());


            for (RibilsHolder ho : added) {

                RibillList list = new RibillList();
                list.setIdRicit(ho.getId());
                list.setRicitstatus(0);
                list.setRicittotal(ho.getTotal());
                list.setRibill(ribill);
                session.save(list);

                pojo.Receipt re = (pojo.Receipt) session.load(Receipt.class, ho.getId());
                re.setReceiptStatus(3);
                session.update(re);

                ricitnos += ho.getRiciptNo() + " - " + "No. " + ho.getAssess() + ": " + modle.Round.roundToString(ho.getTotal()) + ",  |  ";

                if (lastChequeNo.equals(ho.getChequeNo())) {

                } else {
                    chequenos += ho.getChequeNo() + ", ";
                }

                lastChequeNo = ho.getChequeNo();
            }


            String idRecit = ribillid + "_RI";

            transaction.commit();


            String ass_barcode_yes_no = KeyVal.getVal("ass_barcode_yes_no");
            if (ass_barcode_yes_no.equals("no")) {
                modle.GetInstans.getAssessReport().RiBill(ribillid + "", ricitnos, chequenos, false);
            } else {
                BarcodeStatic.subject = "Assessment RI Bill";
                BarcodeStatic.customerName = modle.StaticViews.getLogUser().getUserFullName();
                BarcodeStatic.reTotal = tot;
                BarcodeStatic.idRecipt = idRecit;
                viewId();
            }

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }


    }


    ObservableList<RibHolder> bills = FXCollections.observableArrayList();

    public void loadRiBillTable() {

        Date systemDateByQuary = GetInstans.getQuater().getSystemDateByQuary();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(systemDateByQuary);

        try {

            String qq = "SELECT\n" +
                    "ribill.idRibill,\n" +
                    "ribill.billdate,\n" +
                    "ribill.billtotal,\n" +
                    "ribill.userid,\n" +
                    "ribill.ribill_status,\n" +
                    "ribill.bill_no\n" +
                    "FROM\n" +
                    "ribill\n" +
                    "WHERE\n" +
                    "ribill.userid = '" + modle.StaticViews.getLogUser().getIdUser() + "' AND\n" +
                    "ribill.billdate = '" + format + "'";

            ResultSet data = DB.getData(qq);
            bills.clear();
            while (data.next()) {
                int ribill_status = data.getInt("ribill_status");
                String complete = "";
                if (ribill_status == 0) {
                    complete = "Pending";
                }
                if (ribill_status == 1) {
                    complete = "Completed";
                }
                if (ribill_status == 2) {
                    complete = "Canceled";
                }

                bills.add(new RibHolder(data.getInt("idRibill"), data.getString("bill_no"), modle.Round.round(data.getDouble("billtotal")), data.getInt("ribill_status"), complete, 0));
            }
            tbl_bill.setItems(bills);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    public String createRecipt(int riid, double cheque, double cash, double tot, Date day, String chqueno, String recitNo, Session session) {


        String idRecit = "";
        try {
            Receipt recit = new Receipt();
            pojo.ApplicationCatagory appcat = (pojo.ApplicationCatagory) session.load(pojo.ApplicationCatagory.class, 13);
            recit.setApplicationCatagory(appcat);
            recit.setOfficeIdOffice(modle.StaticViews.getLogUser().getOfficeIdOffice()); //Ofiice ID log wela inna user gen ganna
            recit.setReceptApplicationId(riid);
            recit.setReceiptPrintNo(recitNo);
            recit.setCheack(cheque);
            recit.setCesh(cash);
            recit.setReceiptTotal(tot);
            recit.setReceiptDay(day);
            recit.setReceiptStatus(0);
            recit.setReceiptSyn(1);
            recit.setChqueNo(chqueno);
            recit.setOder(riid);

            recit.setOfficeIdOffice(modle.StaticViews.getLogUser().getOfficeIdOffice());

            Serializable save = session.save(recit);
            idRecit = save.toString();


        } catch (Exception e) {
            e.printStackTrace();

        } finally {

        }
        return idRecit;
    }

    public void viewId() {


        System.out.println("Print Barcode");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Print Barcode");

        alert.setHeaderText(null);

        alert.setContentText(BarcodeStatic.idRecipt + "");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {


            modle.GetInstans.getPrintBarcode().print(BarcodeStatic.customerName, BarcodeStatic.idRecipt + "", modle.Round.roundToString(BarcodeStatic.reTotal), BarcodeStatic.subject);
            BarcodeStatic.customerName = null;
            BarcodeStatic.subject = null;
            BarcodeStatic.idRecipt = 0 + "";
            BarcodeStatic.reTotal = 0;

        } else {

//            modle.GetInstans.getPrintBarcode().print(BarcodeStatic.customerName, BarcodeStatic.idRecipt + "", modle.Round.roundToString(BarcodeStatic.reTotal), BarcodeStatic.subject);
            BarcodeStatic.customerName = null;
            BarcodeStatic.subject = null;
            BarcodeStatic.idRecipt = 0 + "";
            BarcodeStatic.reTotal = 0;

        }

    }


}
