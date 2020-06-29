package controller.mix;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.text.Text;
import modle.StaticBadu;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Search implements Initializable {

    @FXML
    private TableView<Cross> tbl;

    @FXML
    private TableColumn<Cross, String> col_number;

    @FXML
    private TableColumn<Cross, String> col_customer;

    @FXML
    private TableColumn<Cross, String> col_nic;

    @FXML
    private TableColumn<Cross, Double> col_amount;

    @FXML
    private JFXTextField txt_number;

    @FXML
    private Text txt_selected;

    @FXML
    private JFXButton select;

    @FXML
    private JFXButton btn_close;

    ObservableList<Search.Cross> croslist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_number.setCellValueFactory(new PropertyValueFactory<>("ref"));
        col_customer.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        col_nic.setCellValueFactory(new PropertyValueFactory<>("cusNic"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));


        loadTable(null);

    }

    @FXML
    void clickOnRow(MouseEvent event) {
        if (tbl.getSelectionModel().getSelectedItem() != null) {
            txt_selected.setText(tbl.getSelectionModel().getSelectedItem().getRef());
        }
    }

    @FXML
    void clickOnUsethis(ActionEvent event) {
        if (tbl.getSelectionModel().getSelectedItem() != null) {
            StaticBadu.setCross(tbl.getSelectionModel().getSelectedItem());
            StaticBadu.getMixincome().loadCrossData(tbl.getSelectionModel().getSelectedItem());
        }
        select.getScene().getWindow().hide();
    }

    @FXML
    void close(ActionEvent event) {
        select.getScene().getWindow().hide();
    }

    @FXML
    void keyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            loadTable(txt_number.getText());
        }
    }


    public void loadTable(String ref) {
        try {
            String query = "";

            if (ref == null) {
                query = "SELECT\n" +
                        "ex_cross.ex_croess_detail_id,\n" +
                        "ex_cross.ex_refno,\n" +
                        "ex_cross.ex_amount,\n" +
                        "ex_cross.ex_cross_vote_id,\n" +
                        "ex_cross.ex_income_or_expence,\n" +
                        "ex_cross.ex_active_status,\n" +
                        "ex_cross.ex_status,\n" +
                        "ex_cross.date,\n" +
                        "ex_cross.voucher_id,\n" +
                        "ex_cross.recipt_id,\n" +
                        "ex_cross.is_cross,\n" +
                        "ex_cross.cus_id,\n" +
                        "customer.idCustomer,\n" +
                        "customer.cus_name,\n" +
                        "customer.cus_nic\n" +
                        "FROM\n" +
                        "ex_cross\n" +
                        "INNER JOIN customer ON customer.idCustomer = ex_cross.cus_id\n" +
                        "WHERE\n" +
                        "ex_cross.is_cross = 1 AND ex_active_status = 2";
            } else {
                query = "SELECT\n" +
                        "ex_cross.ex_croess_detail_id,\n" +
                        "ex_cross.ex_refno,\n" +
                        "ex_cross.ex_amount,\n" +
                        "ex_cross.ex_cross_vote_id,\n" +
                        "ex_cross.ex_income_or_expence,\n" +
                        "ex_cross.ex_active_status,\n" +
                        "ex_cross.ex_status,\n" +
                        "ex_cross.date,\n" +
                        "ex_cross.voucher_id,\n" +
                        "ex_cross.recipt_id,\n" +
                        "ex_cross.is_cross,\n" +
                        "ex_cross.cus_id,\n" +
                        "customer.idCustomer,\n" +
                        "customer.cus_name,\n" +
                        "customer.cus_nic\n" +
                        "FROM\n" +
                        "ex_cross\n" +
                        "INNER JOIN customer ON customer.idCustomer = ex_cross.cus_id\n" +
                        "WHERE\n" +
                        "ex_cross.is_cross = 1 AND ex_active_status = 2 AND\n" +
                        "ex_cross.ex_refno LIKE '%" + ref + "%'\n";
            }


            ResultSet data = DB.getData(query);


            croslist.clear();
            while (data.next()) {
                croslist.add(new Cross(data.getInt("ex_croess_detail_id"), data.getString("ex_refno"), data.getDouble("ex_amount"), data.getString("cus_name"), data.getString("cus_nic"), data.getInt("cus_id")));
            }
            tbl.setItems(croslist);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }


    public class Cross {
        public int getId() {
            return id;
        }

        public String getRef() {
            return ref;
        }

        public double getAmount() {
            return amount;
        }

        public String getCusName() {
            return cusName;
        }

        public String getCusNic() {
            return cusNic;
        }

        public int getCusID() {
            return cusID;
        }

        public Cross(int id, String ref, double amount, String cusName, String cusNic, int cusID) {
            this.id = id;
            this.ref = ref;
            this.amount = amount;
            this.cusName = cusName;
            this.cusNic = cusNic;
            this.cusID = cusID;
        }

        int id;
        String ref;
        double amount;
        String cusName;
        String cusNic;
        int cusID;


    }


}
