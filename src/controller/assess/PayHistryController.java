package controller.assess;

import com.jfoenix.controls.JFXButton;
import conn.DB;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import modle.asses.StaticBadu;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Created by Ranga on 2019-02-21.
 */
public class PayHistryController implements Initializable {

    @FXML
    private TableView<Histry> tbl_oldbill;

    @FXML
    private TableColumn<Object, Object> col_date;

    @FXML
    private TableColumn<Object, Object> col_risitno;

    @FXML
    private TableColumn<Histry, Double> col_total;
    @FXML
    private TableColumn<Histry, String> col_chequeNo;

    @FXML
    private JFXButton btn_oldbill;

    @FXML
    private Text txt_assessid;

    @FXML
    private Text txt_assessno;

    @FXML
    private Text txt_cusname;
    @FXML
    private JFXButton btn_close;

    int idpay;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Integer idAssessment = StaticBadu.getIdAssessment();
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_risitno.setCellValueFactory(new PropertyValueFactory<>("riciptno"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("paid"));
        col_chequeNo.setCellValueFactory(new PropertyValueFactory<>("chequeno"));
        loadData(idAssessment);
        loadTable(idAssessment);
    }


    @FXML
    void selectFromTable(MouseEvent event) {


        System.out.println(tbl_oldbill.getSelectionModel().getSelectedItem().getPid());
        System.gc();

    }


    public void loadData(Integer id) {
        String quary = "SELECT\n" +
                "customer.cus_name,\n" +
                "assessment.assessment_no\n" +
                "FROM\n" +
                "assessment\n" +
                "INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n" +
                "WHERE\n" +
                "assessment.idAssessment =" + id;
        try {
            ResultSet data = DB.getData(quary);
            if (data.last()) {
                txt_assessid.setText(id + "");
                txt_assessno.setText(data.getString("assessment_no"));
                txt_cusname.setText(data.getString("cus_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ObservableList<Histry> list = FXCollections.observableArrayList();

    public void loadTable(Integer id) {
        String quary = "SELECT\n" +
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
                "receipt.idReceipt,\n" +
                "receipt.Application_Catagory_idApplication_Catagory,\n" +
                "receipt.recept_applicationId,\n" +
                "receipt.receipt_print_no,\n" +
                "receipt.cheack,\n" +
                "receipt.cesh,\n" +
                "receipt.receipt_total,\n" +
                "receipt.receipt_day,\n" +
                "receipt.receipt_status,\n" +
                "receipt.receipt_syn\n" +
                "FROM\n" +
                "ass_payment\n" +
                "INNER JOIN receipt ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                "WHERE\n" +
                "ass_payment.ass_Payment_Status = 1 AND\n" +
                "ass_payment.Assessment_idAssessment =" + id;
        try {
            ResultSet data = DB.getData(quary);
            list.clear();
            while (data.next()) {
                list.add(new Histry(
                        data.getInt("idReceipt"), data.getString("ass_Payment_date"), data.getString("receipt_print_no"), data.getString("ass_check_no"),
                        modle.Round.round(data.getDouble("receipt_total")))
                );
            }
            tbl_oldbill.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @FXML
    void clickOnClose(MouseEvent event) {
        modle.asses.StaticBadu.setIdAssessment(0);
        btn_close.getScene().getWindow().hide();
    }

    @FXML
    void clickOnViewOldBill(MouseEvent event) {
        System.out.println(idpay);
        new DayendController().reprintAssessBill(tbl_oldbill.getSelectionModel().getSelectedItem().getPid() + "");

    }


    public class Histry {
        private int pid;
        private SimpleStringProperty date;
        private SimpleStringProperty riciptno;
        private SimpleStringProperty chequeno;
        private double paid;

        public Histry(int pid, String date, String riciptno, String chequeno, double paid) {
            this.pid = pid;
            this.date = new SimpleStringProperty(date);
            this.riciptno = new SimpleStringProperty(riciptno);
            this.chequeno = new SimpleStringProperty(chequeno);
            this.paid = paid;
        }


        public int getPid() {
            return pid;
        }

        public String getDate() {
            return date.get();
        }

        public String getRiciptno() {
            return riciptno.get();
        }

        public String getChequeno() {
            return chequeno.get();
        }

        public double getPaid() {
            return paid;
        }

    }


}
