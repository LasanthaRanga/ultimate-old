package controller.mix;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import conn.DB;
import groovy.transform.ToString;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import pojo.Receipt;

import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class Cancel implements Initializable {

    @FXML
    private JFXTextField txt_riciptid;

    @FXML
    private JFXButton btn_reprint;

    @FXML
    private JFXButton btn_cancel;

    @FXML
    private TableView<Mix> tbl_mix;

    @FXML
    private TableColumn<Mix, Integer> col_id;

    @FXML
    private TableColumn<Mix, String> col_no;

    @FXML
    private TableColumn<Mix, String> col_customer;

    @FXML
    private TableColumn<Mix, Double> col_total;

    @FXML
    private JFXDatePicker dp_day;

    @FXML
    private JFXComboBox<User> com_user;

    @FXML
    private Text txt_total;

    @FXML
    private Text txt_count;

    @FXML
    private JFXComboBox<Cancel.App> com_application;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUserCombo();
        loadApplicationCombo();
        col_id.setCellValueFactory(new PropertyValueFactory<>("idRecipt"));
        col_no.setCellValueFactory(new PropertyValueFactory<>("riciptno"));
        col_customer.setCellValueFactory(new PropertyValueFactory<>("cusname"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("amount"));
        modle.StaticViews.getMc().changeTitle("Receipt Details");
    }


    @FXML
    void applicationChange(ActionEvent event) {
        loadUserCombo();

        int id = 0;
        if (com_user.getSelectionModel().getSelectedItem() != null) {
            id = com_user.getSelectionModel().getSelectedItem().getId();
        }
        LocalDate value = dp_day.getValue();
        if (value != null) {
            Date selectDate = Date.from(dp_day.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
            String date = new SimpleDateFormat("yyyy-MM-dd").format(selectDate);
            loadTable(date, id, 0);
        }

    }

    @FXML
    void clickOnCancel(MouseEvent event) {
        Mix selectedItem = tbl_mix.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int idMix = selectedItem.getIdMix();
            int idRecipt = selectedItem.getIdRecipt();
            int idAppCat = selectedItem.getIdAppCat();


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are You Sure to Cancel This Bill");
            alert.setContentText(idRecipt + "");


            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (idAppCat == 9 || idAppCat == 11) {
                    try {
                        conn.DB.setData("UPDATE `mixincome`\n" +
                                "SET \n" +
                                " `mixincome_status` = '2' \n" +
                                "WHERE\n" +
                                "\t(`idMixincome` = '" + idMix + "')");

                        conn.DB.setData("UPDATE `receipt`\n" +
                                "SET \n" +
                                " `receipt_status` = '2' \n" +
                                "WHERE\n" +
                                "\t(`idReceipt` = '" + idRecipt + "')");
                        conn.DB.setData("UPDATE `account_ps_three`\n" +
                                "SET \n" +
                                " `report_status` = '2'\n" +
                                "WHERE\n" +
                                "\t(`report_ricipt_id` = '" + idRecipt + "')");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (idAppCat == 10) {
                    try {
                        conn.DB.setData("UPDATE `book`\n" +
                                "SET book_book_pay_status = '2',\n" +
                                " book_book_status = '0'\n" +
                                "WHERE\n" +
                                "\t(`idbook` = '" + idMix + "')");

                        conn.DB.setData("UPDATE `receipt`\n" +
                                "SET \n" +
                                " `receipt_status` = '2' \n" +
                                "WHERE\n" +
                                "\t(`idReceipt` = '" + idRecipt + "')");

                        conn.DB.setData("UPDATE `account_ps_three`\n" +
                                "SET \n" +
                                " `report_status` = '2'\n" +
                                "WHERE\n" +
                                "\t(`report_ricipt_id` = '" + idRecipt + "')");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                modle.Allert.notificationGood("Canceled", idRecipt + "");
                LocalDate value = dp_day.getValue();
                if (value != null) {
                    Date selectDate = Date.from(dp_day.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(selectDate);
                    loadTable(date, 0, 0);
                }
            } else {
                modle.Allert.notificationInfo("Not Canceled", idRecipt + "");
            }


        } else {
            modle.Allert.notificationInfo("Not Selected ", "Plz.. Select One Form Table");
        }
    }

    @FXML
    void clickOnReprint(MouseEvent event) {
        reprint(txt_riciptid.getText());
    }


    public void reprint(String idRecipt) {
        String quary1 = "SELECT\n" +
                "receipt.idReceipt,\n" +
                "receipt.Application_Catagory_idApplication_Catagory\n" +
                "FROM\n" +
                "receipt where idReceipt =" + idRecipt;
        try {
            ResultSet data = DB.getData(quary1);
            if (data.last()) {
                int apc = data.getInt("Application_Catagory_idApplication_Catagory");
                if (apc == 9 || apc == 11) {
                    modle.GetInstans.getGenarateRecipt().genarateRecipt(Integer.parseInt(idRecipt),false);
                }
                if (apc == 10) {
                    modle.book.Recipt.genarateBookingRecipt(idRecipt,false);
                }
            } else {
                modle.Allert.notificationInfo("No Recipt", "Place Recheck");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    void selectFromTable(MouseEvent event) {
        Mix selectedItem = tbl_mix.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int idRecipt = selectedItem.getIdRecipt();
            txt_riciptid.setText(idRecipt + "");
            try {
                ResultSet data = DB.getData("SELECT\n" +
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
                        "receipt.oder\n" +
                        "FROM `receipt`\n" +
                        "WHERE\n" +
                        "receipt.idReceipt = '" + idRecipt + "'\n");

                if (data.last()) {
                    int receipt_status = data.getInt("receipt_status");
                    if (receipt_status == 1) {
                        btn_cancel.setDisable(true);
                    } else {
                        btn_cancel.setDisable(false);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

    @FXML
    void com_onAction(ActionEvent event) {
        int id = com_user.getSelectionModel().getSelectedItem().getId();
        LocalDate value = dp_day.getValue();
        if (value != null) {
            Date selectDate = Date.from(dp_day.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
            String date = new SimpleDateFormat("yyyy-MM-dd").format(selectDate);
            loadTable(date, id, 0);
        }
    }

    @FXML
    void dpOnAction(ActionEvent event) {
        int id = 0;
        if (com_user.getSelectionModel().getSelectedItem() != null) {
            id = com_user.getSelectionModel().getSelectedItem().getId();
        }
        LocalDate value = dp_day.getValue();
        if (value != null) {
            Date selectDate = Date.from(dp_day.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
            String date = new SimpleDateFormat("yyyy-MM-dd").format(selectDate);
            loadTable(date, id, 0);
        }
    }

    @FXML
    void riciptIdKeyRelesec(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String text = txt_riciptid.getText();
            try {
                int i = Integer.parseInt(text);
                int id = 0;
                if (com_user.getSelectionModel().getSelectedItem() != null) {
                    id = com_user.getSelectionModel().getSelectedItem().getId();
                }
                LocalDate value = dp_day.getValue();
                if (value != null) {
                    Date selectDate = Date.from(dp_day.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(selectDate);
                    loadTable(date, id, i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    ObservableList<Cancel.App> applist = FXCollections.observableArrayList();

    public void loadApplicationCombo() {

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "application_catagory.idApplication_Catagory,\n" +
                    "application_catagory.application_name\n" +
                    "FROM\n" +
                    "application_catagory");
            applist.clear();
            while (data.next()) {
                applist.add(
                        new App(data.getInt("idApplication_Catagory"), data.getString("application_name"))
                );
            }
            com_application.setItems(applist);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }

    public class App {
        int id;
        String name;

        public App(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }


    ObservableList<Cancel.User> ulit = FXCollections.observableArrayList();

    public void loadUserCombo() {

        App selectedItem = com_application.getSelectionModel().getSelectedItem();


        try {
            String query = "";
            if (selectedItem != null) {
                query = "SELECT\n" +
                        "`user`.user_full_name,\n" +
                        "user_has_application_catagory.Application_Catagory_idApplication_Catagory,\n" +
                        "`user`.idUser\n" +
                        "FROM\n" +
                        "`user`\n" +
                        "INNER JOIN user_has_application_catagory ON user_has_application_catagory.User_idUser = `user`.idUser\n" +
                        "WHERE\n" +
                        "user_has_application_catagory.Application_Catagory_idApplication_Catagory = " + selectedItem.getId();
            } else {
                query = "SELECT\n" +
                        "`user`.user_full_name,\n" +
                        "user_has_application_catagory.Application_Catagory_idApplication_Catagory,\n" +
                        "`user`.idUser\n" +
                        "FROM\n" +
                        "`user`\n" +
                        "INNER JOIN user_has_application_catagory ON user_has_application_catagory.User_idUser = `user`.idUser";
            }

            ResultSet data = DB.getData(query);


            ulit.clear();
            while (data.next()) {
                ulit.add(new Cancel.User(data.getInt("idUser"), data.getString("user_full_name")));
            }
            com_user.setItems(ulit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class User {
        private int id;
        private String name;

        User(Integer id, String name) {
            super();
            this.setId(id);
            this.setName(name);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }


    ObservableList<Cancel.Mix> mixlist = FXCollections.observableArrayList();

    public void loadTable(String date, int uid, int id) {
        App selectedItem = com_application.getSelectionModel().getSelectedItem();
        if (null != selectedItem) {
            int appid = selectedItem.getId();
            if (appid == 9 || appid == 11) {//MIX INCOME
                try {
                    String qq = "SELECT\n" +
                            "receipt.idReceipt,\n" +
                            "customer.cus_name,\n" +
                            "customer.cus_nic,\n" +
                            "mixincome.mixincome_fulltotal,\n" +
                            "receipt.receipt_print_no,\n" +
                            "receipt.Application_Catagory_idApplication_Catagory,\n" +
                            "receipt.recept_applicationId,\n" +
                            "receipt.cheack,\n" +
                            "receipt.cesh,\n" +
                            "receipt.receipt_total,\n" +
                            "receipt.receipt_day,\n" +
                            "receipt.receipt_status,\n" +
                            "receipt.receipt_syn,\n" +
                            "receipt.chque_no,\n" +
                            "receipt.chque_date,\n" +
                            "receipt.chque_bank,\n" +
                            "mixincome.mixincome_userid, mixincome.idMixincome\n" +
                            "FROM\n" +
                            "mixincome\n" +
                            "INNER JOIN receipt ON receipt.recept_applicationId = mixincome.idMixincome\n" +
                            "INNER JOIN customer ON mixincome.customer_idCustomer = customer.idCustomer\n" +
                            "WHERE\n" +
                            "receipt.Application_Catagory_idApplication_Catagory = '" + appid + "' AND receipt.receipt_status <> 2 AND " +
                            " receipt.receipt_day = '" + date + "' \n";
                    if (uid > 0) {
                        qq += " AND mixincome.mixincome_userid = " + uid;
                    }

                    if (id > 0) {
                        qq += " AND receipt.idReceipt LIKE '%" + id + "%'";
                    }

                    ResultSet data = DB.getData(qq);
                    int x = 0;
                    double fulltot = 0;
                    mixlist.clear();
                    while (data.next()) {
                        x++;
                        mixlist.add(new Cancel.Mix(data.getInt("idMixincome"), data.getInt("idReceipt"), data.getString("receipt_print_no"), data.getString("cus_name"), data.getDouble("mixincome_fulltotal"), 9));
                        fulltot += data.getDouble("mixincome_fulltotal");
                    }
                    tbl_mix.setItems(mixlist);
                    txt_total.setText(modle.Round.roundToString(fulltot));
                    txt_count.setText(x + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (appid == 10) {//Booking
                String qq = "SELECT\n" +
                        "book.idbook,\n" +
                        "customer.cus_name,\n" +
                        "receipt.idReceipt,\n" +
                        "receipt.receipt_print_no,\n" +
                        "book.book_total,\n" +
                        "receipt.oder\n" +
                        "FROM\n" +
                        "receipt\n " +
                        "INNER JOIN book ON book.idbook = receipt.recept_applicationId\n" +
                        "INNER JOIN customer ON customer.idCustomer = book.customer_idCustomer\n" +
                        "WHERE\n receipt_status <> 2 AND " +
                        "receipt.Application_Catagory_idApplication_Catagory = 10\n AND\n" +
                        "receipt.receipt_day = '" + date + "'";

                if (id > 0) {
                    qq += " AND\n" +
                            "receipt.idReceipt LIKE '%" + id + "%'";
                }

                try {
                    ResultSet data = DB.getData(qq);
                    int x = 0;
                    double fulltot = 0;
                    mixlist.clear();
                    while (data.next()) {
                        x++;
                        mixlist.add(new Cancel.Mix(data.getInt("idbook"), data.getInt("idReceipt"), data.getString("receipt_print_no"), data.getString("cus_name"), data.getDouble("book_total"), 10));
                        fulltot += data.getDouble("book_total");
                    }
                    tbl_mix.setItems(mixlist);
                    txt_total.setText(modle.Round.roundToString(fulltot));
                    txt_count.setText(x + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            modle.Allert.notificationInfo("Select Application Cat", "Application is Empty");
        }


    }


    public class Mix {

        private int idMix;
        private int idRecipt;
        private String riciptno;
        private String cusname;
        private double amount;
        private int idAppCat;

        public Mix(int idMix, int idRecipt, String riciptno, String cusname, double amount, int idAppCat) {
            this.idMix = idMix;
            this.idRecipt = idRecipt;
            this.riciptno = riciptno;
            this.cusname = cusname;
            this.amount = amount;
            this.idAppCat = idAppCat;
        }


        public int getIdMix() {
            return idMix;
        }

        public void setIdMix(int idMix) {
            this.idMix = idMix;
        }

        public int getIdRecipt() {
            return idRecipt;
        }

        public void setIdRecipt(int idRecipt) {
            this.idRecipt = idRecipt;
        }

        public String getRiciptno() {
            return riciptno;
        }

        public void setRiciptno(String riciptno) {
            this.riciptno = riciptno;
        }

        public String getCusname() {
            return cusname;
        }

        public void setCusname(String cusname) {
            this.cusname = cusname;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getIdAppCat() {
            return idAppCat;
        }


    }

}
