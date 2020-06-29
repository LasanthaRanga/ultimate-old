package controller.booking;

import com.jfoenix.controls.*;
import conn.DB;
import conn.NewHibernateUtil;
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
import javafx.util.Callback;
import modle.GetInstans;
import modle.StaticBadu;
import modle.StaticViews;
import modle.book.Recipt;
import modle.popup.BarcodeStatic;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Customer;

import java.io.Serializable;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class Book implements Initializable {

    @FXML
    private JFXTextField txt_nic;

    @FXML
    private JFXTextField txt_name;

    @FXML
    private JFXTextField txt_mobile;

    @FXML
    private JFXTextField txt_address1;

    @FXML
    private JFXTextField txt_address2;

    @FXML
    private JFXTextField txt_address3;

    @FXML
    private JFXComboBox<Book.Place> com_place;

    @FXML
    private JFXComboBox<Book.Reson> com_reson;

    @FXML
    private JFXRadioButton radio_full;

    @FXML
    private ToggleGroup type;

    @FXML
    private JFXRadioButton radio_hours;

    @FXML
    private JFXDatePicker dp_day;

    @FXML
    private JFXTimePicker time_form;

    @FXML
    private JFXTimePicker time_to;

    @FXML
    private TableView<Arrange> tbl_book;

    @FXML
    private TableColumn<Arrange, String> col_day;

    @FXML
    private TableColumn<Arrange, String> col_type;

    @FXML
    private TableColumn<Arrange, String> col_from;

    @FXML
    private TableColumn<Arrange, String> col_to;

    @FXML
    private TableColumn<Arrange, Double> col_amount;


    @FXML
    private TableColumn<Arrange, Double> col_diposit;

    @FXML
    private JFXButton btn_book;

    @FXML
    private Text txt_total;

    @FXML
    private JFXCheckBox ch_vat;

    @FXML
    private Text txt_vat;

    @FXML
    private JFXCheckBox ch_nbt;

    @FXML
    private Text txt_nbt;

    @FXML
    private JFXCheckBox ch_stamp;

    @FXML
    private Text txt_stamp;

    @FXML
    private Text txt_fulltotal;

    @FXML
    private JFXButton btn_add;

    @FXML
    private Text txt_dipo;


    @FXML
    private JFXButton btn_remove;

    @FXML
    private JFXRadioButton radio_cheque;

    @FXML
    private ToggleGroup cc;

    @FXML
    private JFXRadioButton radio_cash;

    @FXML
    private JFXTextField txt_chequeNo;

    @FXML
    private JFXDatePicker dp_chque;

    @FXML
    private JFXComboBox<String> com_bank;


    int idcustomer = 0;
    boolean hasCustomer = false;
    String nic;
    String name;
    String mobile;
    String adl1, adl2, adl3;
    String stringtype;
    int idPlace, idReson, idAccount, idVote;
    int booktype;// 1= full day 2= houre;
    String selectDate;
    String tfrom, tto;
    double dprice, hprice, hoamunt, dipPrice;

    int idReciptShow;


    double total, vat, nbt, stamp, fulltotal, diptotal;
    double vatr, nbtr, stampr;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPlaceCombo();
        loadRate();
        com_bank.setItems(modle.GetInstans.getBanks().loadBanksCombo());
        col_day.setCellValueFactory(new PropertyValueFactory<>("day"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_from.setCellValueFactory(new PropertyValueFactory<>("from"));
        col_to.setCellValueFactory(new PropertyValueFactory<>("to"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("amout"));
        col_diposit.setCellValueFactory(new PropertyValueFactory<>("diposit"));
        btn_book.setDisable(true);
        modle.StaticViews.getMc().changeTitle("Booking");
    }


    public void loadRate() {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "interest.idInterest,\n" +
                    "interest.`name`,\n" +
                    "interest.rate,\n" +
                    "interest.`status`\n" +
                    "FROM\n" +
                    "interest");

            while (data.next()) {
                if (data.getString("name").equals("VAT")) {
                    vatr = data.getDouble("rate");
                }

                if (data.getString("name").equals("NBT")) {
                    nbtr = data.getDouble("rate");
                }

                if (data.getString("name").equals("STAMP")) {
                    stampr = data.getDouble("rate");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void keyReleaseOnNic(KeyEvent event) {
        System.out.println("type");
        if (event.getCode() == KeyCode.ENTER) {
            getCustomerData();
            System.out.println("enter");
        }
    }

    public void getCustomerData() {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "customer.idCustomer,\n" +
                    "customer.cus_name,\n" +
                    "customer.cus_nic,\n" +
                    "customer.cus_mobile,\n" +
                    "customer.cus_tel,\n" +
                    "customer.cus_address_l1,\n" +
                    "customer.cus_address_l2,\n" +
                    "customer.cus_address_l3,\n" +
                    "customer.cus_sity,\n" +
                    "customer.cus_status,\n" +
                    "customer.cus_syn,\n" +
                    "customer.cus_reg_date,\n" +
                    "customer.cus_update_date,\n" +
                    "customer.cus_name_sinhala,\n" +
                    "customer.cus_address_l1_sinhala,\n" +
                    "customer.cus_address_l2_sinhala,\n" +
                    "customer.cus_address_l3_sinhala\n" +
                    "FROM\n" +
                    "customer\n" +
                    "WHERE\n" +
                    "customer.cus_nic = '" + txt_nic.getText() + "'");
            if (data.last()) {
                idcustomer = data.getInt("idcustomer");
                String cus_name = data.getString("cus_name");
                String cus_mobile = data.getString("cus_mobile");
                String cus_address_l1 = data.getString("cus_address_l1");
                String cus_address_l2 = data.getString("cus_address_l2");
                String cus_address_l3 = data.getString("cus_address_l3");
                txt_name.setText(cus_name);
                txt_address1.setText(cus_address_l1);
                txt_address2.setText(cus_address_l2);
                txt_address3.setText(cus_address_l3);
                txt_mobile.setText(cus_mobile);
                hasCustomer = true;
                System.out.println("getData");
            } else {
                txt_name.setText(null);
                txt_address1.setText(null);
                txt_address2.setText(null);
                txt_address3.setText(null);
                txt_mobile.setText(null);
                idcustomer = 0;
                hasCustomer = false;
                System.out.println("null");
            }
        } catch (Exception e) {
        }
    }

    ObservableList<Book.Place> comlist = FXCollections.observableArrayList();

    public void loadPlaceCombo() {
        String qu = "SELECT\n" +
                "book_place.idbook_place,\n" +
                "book_place.book_place_name,\n" +
                "book_place.book_place_status\n" +
                "FROM `book_place`";
        try {
            ResultSet data = DB.getData(qu);
            comlist.clear();
            while (data.next()) {
                comlist.add(new Book.Place(data.getInt("idbook_place"), data.getString("book_place_name")));
            }
            com_place.setItems(comlist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void markDates() {
        String qq = "SELECT\n" +
                "book_date.idbook_date,\n" +
                "book_date.book_idbook,\n" +
                "book_date.book_date_day,\n" +
                "book_date.book_date_start,\n" +
                "book_date.book_date_end,\n" +
                "book_date.book_date_amount,\n" +
                "book.book_book_status\n" +
                "FROM\n" +
                "book\n" +
                "INNER JOIN book_date ON book_date.book_idbook = book.idbook\n" +
                "WHERE\n" +
                "book.book_place_idbook_place =" + com_place.getSelectionModel().getSelectedItem().getIdPlace();
        try {
            ArrayList<LocalDate> listRegisteredTOTDays = new ArrayList<>();
            ResultSet data = DB.getData(qq);

            while (data.next()) {
                String book_date_day = data.getString("book_date_day");
                LocalDate parse = LocalDate.parse(book_date_day, formatter);
                listRegisteredTOTDays.add(parse);
            }


            final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            if (!empty) {
                                if (listRegisteredTOTDays.contains(item)) {
//                                    setStyle("-fx-background-color: #99e699;");
                                    setStyle("-fx-background-color: #e53935;");
                                    if (booktype == 1) {
                                        setDisable(true);
                                    }
                                }
                            }
                        }
                    };
                }

            };

            dp_day.setDayCellFactory(dayCellFactory);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    public class Place {

        public Place(int idPlace, String name) {
            this.idPlace = idPlace;
            this.name = name;
        }

        private int idPlace;
        private String name;

        public int getIdPlace() {
            return idPlace;
        }

        public void setIdPlace(int idPlace) {
            this.idPlace = idPlace;
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

    ObservableList<Book.Reson> resonsList = FXCollections.observableArrayList();

    @FXML
    void placeOnAction(ActionEvent event) {
        loadResonCombo();
        markDates();
    }

    @FXML
    void clickOnCalander(MouseEvent event) {
        System.out.println("click On Calander");
        markDates();
    }

    public void loadResonCombo() {
        String qq = "SELECT\n" +
                "book_reson.idbook_reson,\n" +
                "book_reson.book_reson_name,\n" +
                "book_reson.book_reson_dayprice,\n" +
                "book_reson.book_reson_houreprice,\n" +
                "book_reson.book_reson_idVote,\n" +
                "book_reson.book_reson_idAccount,\n" +
                "book_reson.book_reson_status,\n" +
                "book_reson.book_place_idbook_place,\n" +
                "book_reson.book_reson_diposit_vote,\n" +
                "book_reson.book_reson_diposit_amount\n" +
                "FROM\n" +
                "book_reson\n" +
                "WHERE\n" +
                "book_reson.book_place_idbook_place =" + com_place.getSelectionModel().getSelectedItem().getIdPlace();
        try {
            ResultSet data = DB.getData(qq);
            resonsList.clear();
            while (data.next()) {
                resonsList.add(new Reson(data.getInt("idbook_reson"),
                        data.getInt("book_place_idbook_place"),
                        data.getString("book_reson_name"),
                        data.getDouble("book_reson_dayprice"),
                        data.getDouble("book_reson_houreprice"),
                        data.getInt("book_reson_idAccount"),
                        data.getInt("book_reson_idVote"),
                        data.getInt("book_reson_diposit_vote"),
                        data.getDouble("book_reson_diposit_amount")));
            }
            com_reson.setItems(resonsList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public class Reson {
        int idReson;
        int idPlace;
        String name;
        double day;
        double houre;
        int idAccount;
        int idVote;
        int dipVote;
        double dipPrice;

        public Reson(int idReson, int idPlace, String name, double day, double houre, int idAccount, int idVote, int dipVote, double dipPrice) {
            this.idReson = idReson;
            this.idPlace = idPlace;
            this.name = name;
            this.day = day;
            this.houre = houre;
            this.idAccount = idAccount;
            this.idVote = idVote;
            this.dipVote = dipVote;
            this.dipPrice = dipPrice;
        }

        public int getIdReson() {
            return idReson;
        }

        public int getIdPlace() {
            return idPlace;
        }

        public String getName() {
            return name;
        }

        public double getDay() {
            return day;
        }

        public double getHoure() {
            return houre;
        }

        public int getIdAccount() {
            return idAccount;
        }

        public int getIdVote() {
            return idVote;
        }

        public int getDipVote() {
            return dipVote;
        }

        public double getDipPrice() {
            return dipPrice;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @FXML
    void radioOnAction(ActionEvent event) {
        if (radio_full.isSelected()) {
            time_form.setDisable(true);
            time_to.setDisable(true);
            booktype = 1;
            stringtype = "Full Day";
        }
        if (radio_hours.isSelected()) {
            time_form.setDisable(false);
            time_to.setDisable(false);
            booktype = 2;
            stringtype = "Hours";
        }
        markDates();
    }


    @FXML
    void dayOnAction(ActionEvent event) {
        Date selectDate = Date.from(dp_day.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
        this.selectDate = new SimpleDateFormat("yyyy-MM-dd").format(selectDate);
        if (radio_full.isSelected()) {
            ladTable();
        }


    }

    LocalTime f, t;

    @FXML
    void timeFromOnAction(ActionEvent event) {
        LocalTime value = time_form.getValue();
        f = value;
    }

    @FXML
    void timeToOnAction(ActionEvent event) {
        LocalTime value = time_to.getValue();
        t = value;

    }

    @FXML
    void clickOnAdd(MouseEvent event) {

        if (f != null && t != null) {
            long elapsedHours = Duration.between(f, t).toHours();
            long elapsedMinutes = Duration.between(f, t).toMinutes();
            System.out.println(elapsedMinutes + " Minits");
            double dd = elapsedMinutes / 60.00;
            if (dd < 0) {
                modle.Allert.notificationWorning("Please Check The Time", "Time is Wrong");
            } else {
                tfrom = f.toString();
                tto = t.toString();
                hoamunt = hprice * dd;
                ladTable();
            }
        } else {
            modle.Allert.notificationWorning("Please Select The Time", "Time is Empty");
        }

    }

    @FXML
    void resonOnAction(ActionEvent event) {
        Reson re = com_reson.getSelectionModel().getSelectedItem();
        idPlace = re.getIdPlace();
        idAccount = re.getIdAccount();
        idVote = re.getIdVote();
        idReson = re.getIdReson();
        dprice = re.getDay();
        hprice = re.getHoure();
        dipPrice = re.getDipPrice();


    }

    ObservableList<Book.Arrange> arranges = FXCollections.observableArrayList();

    public void ladTable() {
        if (booktype == 1) {
            Arrange arrange = new Arrange(selectDate, stringtype, booktype, tfrom, tto, dprice, dipPrice);
            arranges.add(arrange);
        } else if (booktype == 2) {
            Arrange arrange = new Arrange(selectDate, stringtype, booktype, tfrom, tto, hoamunt, dipPrice);
            arranges.add(arrange);
        }
        double tot = 0;
        double diptot = 0;
        for (Arrange ar : arranges) {
            tot += ar.getAmout();
            diptot += ar.getDiposit();
        }
        total = modle.Round.round(tot);
        diptotal = modle.Round.round(diptot);
        tbl_book.setItems(arranges);
        txt_total.setText(modle.Round.roundToString(tot));
        txt_dipo.setText(modle.Round.roundToString(diptotal));

        if (arranges.size() > 0) {
            com_place.setDisable(true);
            com_reson.setDisable(true);
            radio_hours.setDisable(true);
            radio_full.setDisable(true);
        }
        checkOnAction(new ActionEvent());

    }

    Arrange selected = null;

    @FXML
    void selectFromTable(MouseEvent event) {
        selected = tbl_book.getSelectionModel().getSelectedItem();
    }

    @FXML
    void clickOnRemove(MouseEvent event) {
        if (selected != null) {
            arranges.remove(selected);
            if (arranges.size() > 0) {
                com_place.setDisable(true);
                com_reson.setDisable(true);
                radio_hours.setDisable(true);
                radio_full.setDisable(true);
            } else {
                com_place.setDisable(false);
                com_reson.setDisable(false);
                radio_hours.setDisable(false);
                radio_full.setDisable(false);
            }
            double tot = 0;
            double diptot = 0;
            for (Arrange ar : arranges) {
                tot += ar.getAmout();
                diptot += ar.getDiposit();
            }
            diptotal = modle.Round.round(diptot);
            total = modle.Round.round(tot);
            tbl_book.setItems(arranges);
            txt_total.setText(modle.Round.roundToString(tot));
            txt_dipo.setText(modle.Round.roundToString(diptotal));
            checkOnAction(new ActionEvent());
        }


    }


    public class Arrange {
        String day;
        String type;
        int bookType;
        String from;
        String to;
        double amout;
        double diposit;

        public Arrange(String day, String type, int bookType, String from, String to, double amout, double diposit) {
            this.day = day;
            this.type = type;
            this.bookType = bookType;
            this.from = from;
            this.to = to;
            this.amout = amout;
            this.diposit = diposit;
        }

        public String getDay() {
            return day;
        }

        public String getType() {
            return type;
        }

        public int getBookType() {
            return bookType;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }

        public double getAmout() {
            return amout;
        }

        public double getDiposit() {
            return diposit;
        }
    }


    @FXML
    void checkOnAction(ActionEvent event) {

        if (ch_vat.isSelected()) {
            vat = total * vatr / 100;
            vat = modle.Round.round(vat);
            txt_vat.setText(vat + "");
        } else {
            vat = 0;
            txt_vat.setText(vat + "");
        }
        if (ch_nbt.isSelected()) {
            nbt = total * nbtr / 100;
            nbt = modle.Round.round(nbt);
            txt_nbt.setText(nbt + "");
        } else {
            nbt = 0;
            txt_nbt.setText(nbt + "");
        }
        if (ch_stamp.isSelected()) {
            stamp = total * stampr / 100;
            stamp = modle.Round.round(stamp);
            txt_stamp.setText(stamp + "");
        } else {
            stamp = 0;
            txt_stamp.setText(stamp + "");
        }

        fulltotal = total + vat + nbt + stamp + diptotal;
        fulltotal = modle.Round.round(fulltotal);
        txt_fulltotal.setText(fulltotal + "");

    }

    int paytype = 0;

    @FXML
    void radioPayBy(ActionEvent event) {

        if (radio_cash.isSelected()) {
            btn_book.setDisable(false);
            paytype = 1;
            System.out.println(paytype);

            txt_chequeNo.setDisable(true);
            com_bank.setDisable(true);
            dp_chque.setDisable(true);

        } else if (radio_cheque.isSelected()) {
            btn_book.setDisable(true);
            paytype = 2;
            System.out.println(paytype);

            txt_chequeNo.setDisable(false);
            com_bank.setDisable(false);
            dp_chque.setDisable(false);


        } else {
            btn_book.setDisable(true);
            System.out.println(paytype);
            paytype = 0;
        }


    }


    @FXML
    void clickOnBook(MouseEvent event) {
        Date systemDate = GetInstans.getQuater().getSystemDate();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(systemDate);



        if (!hasCustomer) {
            if (txt_name.getText().length() > 2) {
                Session session = NewHibernateUtil.getSessionFactory().openSession();
                Transaction transaction = session.beginTransaction();
                try {
                    Customer customer = new Customer();
                    if (txt_nic.getText().length() > 0) {
                        String nic = txt_nic.getText();
                        customer.setCusNic(nic);
                    }
                    customer.setCusName(txt_name.getText());
                    customer.setCusMobile(txt_mobile.getText());
                    customer.setCusAddressL1(txt_address1.getText());
                    customer.setCusAddressL2(txt_address2.getText());
                    customer.setCusAddressL3(txt_address3.getText());
                    Serializable save = session.save(customer);
                    idcustomer = Integer.parseInt(save.toString());
                    transaction.commit();
                } catch (Exception e) {
                    transaction.rollback();
                    e.printStackTrace();
                } finally {
                }
            } else {
                modle.Allert.notificationError("Fill Customer Data", "Check Customer");
            }
        }

        double cahs = 0;
        double cheque = 0;
        String chequeno = "";

        if (radio_cheque.isSelected()) {
            cheque = fulltotal;
            chequeno = txt_chequeNo.getText() + " - " +com_bank.getSelectionModel().getSelectedItem();
        }

        if (radio_cash.isSelected()) {
            cahs = fulltotal;
        }


        if (idcustomer > 0) {


            String qq = "INSERT INTO `book` (\n" +
                    "\t`book_date`,\n" +
                    "\t`customer_idCustomer`,\n" +
                    "\t`book_amount`, " +
                    "`book_diposit`,\n" +
                    "\t`book_vat`,\n" +
                    "\t`book_nbt`,\n" +
                    "\t`book_stamp`,\n" +
                    "\t`book_total`,\n" +
                    "\t`book_cash`,\n" +
                    "\t`book_chque`,\n" +
                    "\t`book_chque_no`,\n" +
                    "\t`book_book_pay_status`,\n" +
                    "\t`book_book_status`,\n" +
                    "\t`book_idRecipt`,\n" +
                    "\t`book_reson_idbook_reson`,\n" +
                    "\t`book_place_idbook_place`, " +
                    "`book_idUser`, " +
                    " book_pay_type \n" +
                    ")\n" +
                    "VALUES\n" +
                    "\t(\n" +
                    "\t\t'" + today + "',\n" +
                    "\t\t'" + idcustomer + "',\n" +
                    "\t\t'" + total + "',\n" +
                    "\t\t'" + diptotal + "',\n" +
                    "\t\t'" + vat + "',\n" +
                    "\t\t'" + nbt + "',\n" +
                    "\t\t'" + stamp + "',\n" +
                    "\t\t'" + fulltotal + "',\n" +
                    "\t\t'" + cahs + "',\n" +
                    "\t\t'" + cheque + "',\n" +
                    "\t\t'" + chequeno + "',\n" +
                    "\t\t'0',\n" +
                    "\t\t'" + booktype + "',\n" +
                    "\t\tNULL,\n" +
                    "\t\t'" + idReson + "',\n" +
                    "\t\t'" + idPlace + "',\n" +
                    "\t\t'" + modle.StaticViews.getLogUser().getIdUser() + "',\n" +
                    "\t\t'" + paytype + "'\n" +
                    "\t)";

            try {
                int i = DB.setData(qq);
                ResultSet data = DB.getData("SELECT idBook FROM book ORDER BY idbook DESC LIMIT 1");
                int idBook = 0;
                if (data.last()) {
                    idBook = data.getInt("idbook");


                    String chno = null;
                    int idbank = 0;
                    String chdate = null;

                    if (txt_chequeNo.getText() != null && txt_chequeNo.getText().length() > 0 && com_bank.getSelectionModel().getSelectedItem() != null && com_bank.getSelectionModel().getSelectedItem().length() > 0 && dp_chque.getValue() != null) {
                        chno = txt_chequeNo.getText();
                        idbank = GetInstans.getBanks().getBankIdByBankName(com_bank.getSelectionModel().getSelectedItem());
                        Date startDate = Date.from(dp_chque.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
                        chdate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);

                        System.out.println(chdate);

                    }


                    int appAccountByOffice = GetInstans.getAha().getAppAccountByOffice(10, StaticViews.getLogUser().getOfficeIdOffice());


                    int riciptID = Recipt.insertReciptWithoutNo(10, idBook, cheque, cahs, fulltotal, today, chno, chdate, idbank, StaticViews.getLogUser().getOfficeIdOffice(), appAccountByOffice, StaticViews.getLogUser().getIdUser());
                    if (riciptID > 0) {
                        conn.DB.setData("UPDATE `book`\n" +
                                "SET \n" +
                                " `book_idRecipt` = '" + riciptID + "' \n" +
                                "WHERE\n" +
                                "\t(`idbook` = '" + idBook + "')");

                        idReciptShow = riciptID;
                        for (Arrange ar : arranges) {
                            String from = ar.getFrom();
                            String to = ar.getTo();
                            if (from == null) {
                                from = "00:00";
                            }
                            if (to == null) {
                                to = "00:00";
                            }
                            conn.DB.setData("INSERT INTO `book_date` (\n" +
                                    "\t`book_idbook`,\n" +
                                    "\t`book_date_day`,\n" +
                                    "\t`book_date_start`,\n" +
                                    "\t`book_date_end`,\n" +
                                    "\t`book_date_amount`,\n" +
                                    "\t`book_date_diposit`\n" +
                                    ")\n" +
                                    "VALUES\n" +
                                    "\t('" + idBook + "', '" + ar.getDay() + "', '" + from + "', '" + to + "', '" + ar.getAmout() + "','" + ar.getDiposit() + "')");
                        }
                    }//recipt save and retern id
                }//book save and retern id

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }

          //  modle.Allert.notificationGood("OK", "Success " + idReciptShow);
          //  Alert alert = new Alert(Alert.AlertType.INFORMATION);
          //  alert.setTitle("Receipt Number");
         //   alert.setHeaderText(null);
         //   alert.setContentText("This Booking Payment ID IS :   " + idReciptShow);

        //    alert.showAndWait();


            BarcodeStatic.customerName = txt_name.getText();
            BarcodeStatic.idRecipt = idReciptShow + "";
            BarcodeStatic.reTotal = fulltotal;
            BarcodeStatic.subject = "Mix Income";
            popup();



            clear();
        }

    }

    public void popup() {


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


    public void clear() {
        idcustomer = 0;
        hasCustomer = false;
        txt_nic.setText(null);
        txt_name.setText(null);
        txt_mobile.setText(null);
        txt_address1.setText(null);
        txt_address2.setText(null);
        txt_address3.setText(null);
        arranges.clear();

        idPlace = 0;
        idReson = 0;
        idAccount = 0;
        idVote = 0;
        selectDate = null;
        dprice = 0;
        hprice = 0;
        hoamunt = 0;
        dipPrice = 0;
        idReciptShow = 0;
        total = 0;
        vat = 0;
        nbt = 0;
        stamp = 0;
        fulltotal = 0;
        diptotal = 0;

        tbl_book.setItems(null);
        txt_dipo.setText("00");
        txt_total.setText("00");
        txt_vat.setText("00");
        txt_nbt.setText("00");
        txt_stamp.setText("00");
        txt_fulltotal.setText("00");

        ch_vat.setSelected(false);
        ch_nbt.setSelected(false);
        ch_stamp.setSelected(false);

        radio_cash.setSelected(false);
        radio_cheque.setSelected(false);

    }

    @FXML
    void selectBank(ActionEvent event) {
        String text = txt_chequeNo.getText();
        String selectedItem = com_bank.getSelectionModel().getSelectedItem();
        LocalDate value = dp_chque.getValue();
        if (text != null && text.length() > 0 && selectedItem != null && selectedItem.length() > 0 && value != null) {
            btn_book.setDisable(false);
        }
    }


}
