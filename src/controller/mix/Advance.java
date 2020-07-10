package controller.mix;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import conn.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import modle.ComboItem;
import modle.ComboLoad;
import modle.StaticBadu;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Created by Ranga Rathnayake on 2020-06-04.
 */
public class Advance implements Initializable {

    @FXML
    private JFXComboBox<ComboItem> com_1;

    @FXML
    private JFXComboBox<ComboItem> com2;


    @FXML
    private JFXButton btn_close;

    @FXML
    private TableView<AdvancData> tbl;

    @FXML
    private TableColumn<AdvancData, Integer> id;

    @FXML
    private TableColumn<AdvancData, String> vid;

    @FXML
    private TableColumn<AdvancData, String> name;

    @FXML
    private TableColumn<AdvancData, Double> amount;

    @FXML
    private JFXTextField txt_search;

    @FXML
    private JFXTextField txt_amount;

    @FXML
    private JFXTextField txt_description;

    @FXML
    private JFXButton btn_pay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        modle.StaticViews.getMc().changeTitle("Advance Settlement");

        loadBalanceSheetTitle();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        vid.setCellValueFactory(new PropertyValueFactory<>("ref"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        loadTable();
    }

    public void loadBalanceSheetTitle() {
        String qq = "SELECT acc_bal_sheet_title.bal_sheet_title_id, " +
                "acc_bal_sheet_title.bal_sheet_title_name " +
                " FROM `acc_bal_sheet_title`";

        ObservableList<ComboItem> comboItems = ComboLoad.loadCombo(qq);
        com_1.setItems(comboItems);
    }


    ObservableList<AdvancData> tbl_array = FXCollections.observableArrayList();

    AdvancData Selected = null;


    @FXML
    void searchKeyRelese(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            System.out.println(txt_search.getText());
            int length = txt_search.getText().length();
            if (length > 0) {
                loadTableBySearch(txt_search.getText());
            } else {
                loadTable();
            }

        }
    }

    public void loadTableBySearch(String text) {
        tbl.setItems(null);
        tbl_array.clear();
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ex_voucher_infomation.ex_voucher_calmnter_name,\n" +
                    "ex_advance.voucher_amount,\n" +
                    "ex_voucher_infomation.ex_voucher_ref_no,\n" +
                    "ex_advance.voucher_info_id, ex_voucher_infomation.ex_cus_id\n" +
                    "FROM\n" +
                    "ex_advance\n" +
                    "INNER JOIN ex_voucher_infomation ON ex_advance.voucher_info_id = ex_voucher_infomation.ex_voucher_information_id\n" +
                    "WHERE\n" +
                    "ex_advance.active_status = 1 AND\n" +
                    "ex_voucher_infomation.ex_voucher_ref_no LIKE '%" + text + "%'");
            while (data.next()) {
                tbl_array.add(new AdvancData(data.getInt("voucher_info_id"), data.getString("ex_voucher_ref_no"), data.getString("ex_voucher_calmnter_name"), data.getDouble("voucher_amount"), data.getInt("ex_cus_id")));
            }
            tbl.setItems(tbl_array);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTable() {
        tbl.setItems(null);
        tbl_array.clear();
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ex_voucher_infomation.ex_voucher_calmnter_name,\n" +
                    "ex_advance.voucher_amount,\n" +
                    "ex_voucher_infomation.ex_voucher_ref_no,\n" +
                    "ex_advance.voucher_info_id, ex_voucher_infomation.ex_cus_id\n" +
                    "FROM\n" +
                    "ex_advance\n" +
                    "INNER JOIN ex_voucher_infomation ON ex_advance.voucher_info_id = ex_voucher_infomation.ex_voucher_information_id\n" +
                    "WHERE\n" +
                    "ex_advance.active_status = 1");
            while (data.next()) {
                tbl_array.add(new AdvancData(data.getInt("voucher_info_id"), data.getString("ex_voucher_ref_no"), data.getString("ex_voucher_calmnter_name"), data.getDouble("voucher_amount"), data.getInt("ex_cus_id")));
            }
            tbl.setItems(tbl_array);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void close_on_action(ActionEvent event) {
        btn_close.getScene().getWindow().hide();
    }


    @FXML
    void clickOnTable(MouseEvent event) {
        Selected = tbl.getSelectionModel().getSelectedItem();
        if (Selected != null) {
            txt_amount.setText(Selected.getAmount() + "");
        }
    }

    @FXML
    void com1Action(ActionEvent event) {
        int x = com_1.getSelectionModel().getSelectedItem().getId();
        ObservableList<ComboItem> comboItems = ComboLoad.loadCombo("SELECT\n" +
                "\taccount_receipt_title.ART_vote_and_bal,\n" +
                "\taccount_receipt_title.ART_vote_and_bal_name\n" +
                "FROM\n" +
                "\taccount_receipt_title \n" +
                "WHERE\n" +
                "\taccount_receipt_title.ART_Title_id = '" + x + "' \n" +
                "\tAND account_receipt_title.ART_vote_or_bal = 2");
        com2.setItems(comboItems);
    }

    @FXML
    void com2Action(ActionEvent event) {
        if (com2.getSelectionModel().getSelectedItem() != null) {
            int selectedItem = com2.getSelectionModel().getSelectedItem().getId();
            System.out.println(selectedItem);
            loadMixIncomeType(selectedItem);
        }
    }


    public void loadMixIncomeType(int id) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "mixintype.idMixintype,\n" +
                    "mixintype.mixintype_name,\n" +
                    "mixintype.account_receipt_title_idAccount_receipt_title,\n" +
                    "mixintype.mixintype_status,\n" +
                    "mixintype.bankinfo_idBank,\n" +
                    "mixintype.active_status,\n" +
                    "mixintype.office_id,\n" +
                    "mixintype.extra_int,\n" +
                    "mixintype.extra_string\n" +
                    "FROM\n" +
                    "mixintype\n" +
                    "WHERE\n" +
                    "mixintype.account_receipt_title_idAccount_receipt_title = " + id);

            while (data.next()) {
                System.out.println(data.getInt("idMixintype"));
                System.out.println(data.getString("mixintype_name"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class AdvancData {

        private Integer id;
        private String ref;
        private String name;
        private Double amount;
        private Integer cusId;


        public AdvancData(Integer id, String ref, String name, Double amount, Integer cusId) {
            this.id = id;
            this.ref = ref;
            this.name = name;
            this.amount = amount;
            this.cusId = cusId;
        }

        public Integer getId() {
            return id;
        }

        public String getRef() {
            return ref;
        }

        public String getName() {
            return name;
        }

        public Double getAmount() {
            return amount;
        }

        public Integer getCusId() {
            return cusId;
        }
    }


    @FXML
    void clickOnPay(MouseEvent event) {
        StaticBadu.setAdvancData(Selected);
        StaticBadu.getMixincome().loadCrossData(Selected.cusId);
        btn_close.getScene().getWindow().hide();

//        try {
//            String text = txt_amount.getText();
//            Integer id = Selected.getId();
//            double v = Double.parseDouble(text);
//            conn.DB.setData("INSERT INTO `ex_advance_settle` ( `ex_info_id`, `ex_amount`, `ex_for_info_id`, `ex_active_status`, `ex_voucher_or_recipt` )\n" +
//                    "VALUES\n" +
//                    "\t( '100000', '" + v + "', '" + id + "', '0', '1' )");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


    @FXML
    void cloce(ActionEvent event) {
        btn_close.getScene().getWindow().hide();
    }

}
