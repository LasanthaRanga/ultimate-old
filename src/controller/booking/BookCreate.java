package controller.booking;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import conn.DB;
import controller.mix.Mixincome;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class BookCreate implements Initializable {

    @FXML
    private JFXTextField txt_place;

    @FXML
    private JFXButton btn_place;

    @FXML
    private JFXComboBox<Place> com_place;

    @FXML
    private JFXTextField txt_reson;

    @FXML
    private JFXTextField txt_day;

    @FXML
    private JFXTextField txt_hout;

    @FXML
    private JFXComboBox<String> com_account;

    @FXML
    private JFXComboBox<String> com_vote;

    @FXML
    private JFXButton btn_reson;

    @FXML
    private JFXTextField txt_diposit;

    @FXML
    private JFXComboBox<String> com_vote_dip;

    @FXML
    private TableView<Reson> tbl_reson;

    @FXML
    private TableColumn<Reson, String> col_place;

    @FXML
    private TableColumn<Reson, String> col_reson;

    @FXML
    private TableColumn<Reson, String> col_account;

    @FXML
    private TableColumn<Reson, String> col_avot;

    @FXML
    private TableColumn<Reson, Double> col_day;

    @FXML
    private TableColumn<Reson, Double> col_hour;

    @FXML
    private TableColumn<Reson, String> col_dvot;

    @FXML
    private TableColumn<Reson, Double> col_dip;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPlaceCombo();
        loadAccountCombo();
        loadVoteCombo();
        col_place.setCellValueFactory(new PropertyValueFactory<>("place"));
        col_reson.setCellValueFactory(new PropertyValueFactory<>("reson"));
        col_account.setCellValueFactory(new PropertyValueFactory<>("account"));
        col_avot.setCellValueFactory(new PropertyValueFactory<>("valueV"));
        col_dvot.setCellValueFactory(new PropertyValueFactory<>("dipV"));
        col_dip.setCellValueFactory(new PropertyValueFactory<>("diposit"));
        col_day.setCellValueFactory(new PropertyValueFactory<>("dayVal"));
        col_hour.setCellValueFactory(new PropertyValueFactory<>("hourVal"));
        loadResonsTable();
        modle.StaticViews.getMc().changeTitle("Create Book");
    }

    @FXML
    void clickOnCreatePlace(MouseEvent event) {
        try {
            String text = txt_place.getText();
            if (text.length() > 2) {
                String qu = "INSERT INTO `book_place` (\n" +
                        "\t`book_place_name`,\n" +
                        "\t`book_place_status`\n" +
                        ")\n" +
                        "VALUES\n" +
                        "\t( '" + text + "', 1)";
                conn.DB.setData(qu);
            }
            loadPlaceCombo();

            modle.Allert.notificationGood("OK", "Success " + text);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ObservableList<Place> comlist = FXCollections.observableArrayList();

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
                comlist.add(new Place(data.getInt("idbook_place"), data.getString("book_place_name")));
            }
            com_place.setItems(comlist);
        } catch (Exception e) {
            e.printStackTrace();
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

    public void loadVoteCombo() {
        ObservableList<String> vlist = FXCollections.observableArrayList();
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "\taccount_receipt_title.idAccount_receipt_title,\n" +
                    "\taccount_receipt_title.ART_vote_and_bal\n" +
                    "FROM\n" +
                    "\taccount_receipt_title\n" +
                    "ORDER BY\n" +
                    "\taccount_receipt_title.ART_vote_and_bal ASC");
            vlist.clear();
            while (data.next()) {
                String art_vote_and_bal = data.getString("ART_vote_and_bal");
                vlist.add(art_vote_and_bal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        com_vote.setItems(vlist);
        com_vote_dip.setItems(vlist);
    }

    public void loadAccountCombo() {
        ObservableList<String> banklist = FXCollections.observableArrayList();
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "bank_info.idBank_Info,\n" +
                    "bank_info.bank_name,\n" +
                    "bank_info.acount_no,\n" +
                    "bank_info.acount_name,\n" +
                    "bank_info.current_ballance,\n" +
                    "bank_info.`status`,\n" +
                    "bank_info.syn\n" +
                    "FROM\n" +
                    "bank_info\n");
            banklist.clear();
            while (data.next()) {
                String art_vote_and_bal = data.getString("acount_name");
                banklist.add(art_vote_and_bal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        com_account.setItems(banklist);
    }

    @FXML
    void clickOnReson(MouseEvent event) {
        int idPlace = com_place.getSelectionModel().getSelectedItem().getIdPlace();
        String account = com_account.getSelectionModel().getSelectedItem();
        String vote = com_vote.getSelectionModel().getSelectedItem();
        String dipvote = com_vote_dip.getSelectionModel().getSelectedItem();
        String reson = txt_reson.getText();
        double day = Double.parseDouble(txt_day.getText());
        double houre = Double.parseDouble(txt_hout.getText());
        double dipositamount = 0;
        if (txt_diposit.getText().length() > 0) {
            dipositamount = Double.parseDouble(txt_diposit.getText());
        }
        try {

            int idAccount = 0;
            int idVote = 0;
            int idDipVote = 0;
            ResultSet data = DB.getData("SELECT\n" +
                    "bank_info.idBank_Info\n" +
                    "FROM\n" +
                    "bank_info\n" +
                    "WHERE\n" +
                    "bank_info.acount_name = '" + account + "'");
            if (data.last()) {
                idAccount = data.getInt("idBank_Info");
            }

            if (com_vote_dip.getSelectionModel() != null) {
                ResultSet da = DB.getData("SELECT\n" +
                        "account_receipt_title.idAccount_receipt_title\n" +
                        "FROM\n" +
                        "account_receipt_title\n" +
                        "WHERE\n" +
                        "account_receipt_title.ART_vote_and_bal = '" + dipvote + "'");
                if (da.last()) {
                    idDipVote = da.getInt("idAccount_receipt_title");
                }
            }

            ResultSet data1 = DB.getData("SELECT\n" +
                    "account_receipt_title.idAccount_receipt_title\n" +
                    "FROM\n" +
                    "account_receipt_title\n" +
                    "WHERE\n" +
                    "account_receipt_title.ART_vote_and_bal = '" + vote + "'");
            if (data1.last()) {
                idVote = data1.getInt("idAccount_receipt_title");
            }

            if (idVote > 0 && idPlace > 0 && idAccount > 0) {
                conn.DB.setData("INSERT INTO `book_reson` (\n" +
                        "\t`book_reson_name`,\n" +
                        "\t`book_reson_dayprice`,\n" +
                        "\t`book_reson_houreprice`,\n" +
                        "\t`book_reson_idVote`,\n" +
                        "\t`book_reson_idAccount`,\n" +
                        "\t`book_reson_status`,\n" +
                        "\t`book_place_idbook_place`,\n" +
                        "\t`book_reson_diposit_vote`,\n" +
                        "\t`book_reson_diposit_amount`\n" +
                        ")\n" +
                        "VALUES\n" +
                        "\t(\n" +
                        "\t\t'" + reson + "',\n" +
                        "\t\t'" + day + "',\n" +
                        "\t\t'" + houre + "',\n" +
                        "\t\t'" + idVote + "',\n" +
                        "\t\t'" + idAccount + "',\n" +
                        "\t\t'1',\n" +
                        "\t\t'" + idPlace + "',\n" +
                        "\t\t'" + idDipVote + "',\n" +
                        "\t\t'" + dipositamount + "'\n" +
                        "\t)");

            }

            modle.Allert.notificationGood("OK", "Success " );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


        System.out.println(idPlace);
        System.out.println(account);
        System.out.println(vote);

        loadResonsTable();
    }


    public String loadVote(int vid) {
        String vote = "";
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "account_receipt_title.idAccount_receipt_title,\n" +
                    "account_receipt_title.ART_vote_and_bal\n" +
                    "FROM\n" +
                    "account_receipt_title\n" +
                    "WHERE\n" +
                    "account_receipt_title.idAccount_receipt_title = " + vid);
            if (data.last()) {
                vote = data.getString("ART_vote_and_bal");
            }
            return vote;
        } catch (Exception e) {
            e.printStackTrace();
            return vote;
        }
    }


    ObservableList<Reson> resons = FXCollections.observableArrayList();

    public void loadResonsTable() {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "book_reson.idbook_reson,\n" +
                    "book_reson.book_reson_name,\n" +
                    "book_reson.book_reson_dayprice,\n" +
                    "book_reson.book_reson_houreprice,\n" +
                    "book_reson.book_reson_idVote,\n" +
                    "book_reson.book_reson_idAccount,\n" +
                    "book_reson.book_reson_status,\n" +
                    "book_reson.book_place_idbook_place,\n" +
                    "book_reson.book_reson_diposit_vote,\n" +
                    "book_reson.book_reson_diposit_amount,\n" +
                    "book_place.idbook_place,\n" +
                    "book_place.book_place_name,\n" +
                    "book_place.book_place_status,\n" +
                    "bank_info.bank_name,\n" +
                    "bank_info.idBank_Info,\n" +
                    "bank_info.acount_name,\n" +
                    "bank_info.acount_no\n" +
                    "FROM\n" +
                    "book_reson\n" +
                    "INNER JOIN book_place ON book_reson.book_place_idbook_place = book_place.idbook_place\n" +
                    "INNER JOIN bank_info ON bank_info.idBank_Info = book_reson.book_reson_idAccount");

            resons.clear();
            while (data.next()) {
                int book_reson_idVote = data.getInt("book_reson_idVote");
                int book_reson_diposit_vote = data.getInt("book_reson_diposit_vote");
                String amountvote = loadVote(book_reson_idVote);
                String dipvote = loadVote(book_reson_diposit_vote);
                resons.add(new Reson(
                        data.getInt("idbook_reson"),
                        data.getInt("book_place_idbook_place"),
                        data.getInt("idBank_Info"),
                        book_reson_idVote, book_reson_diposit_vote,
                        data.getString("book_place_name"),
                        data.getString("book_reson_name"),
                        data.getString("acount_name"),
                        amountvote, dipvote,
                        data.getDouble("book_reson_diposit_amount"),
                        data.getDouble("book_reson_dayprice"),
                        data.getDouble("book_reson_houreprice")
                ));
            }
            tbl_reson.setItems(resons);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


    public class Reson {
        int idReson;
        int idPlace;
        int idAccount;
        int valueVote;
        int dipVote;
        String place;
        String reson;
        String account;
        String valueV;
        String dipV;
        double diposit;
        double dayVal;
        double hourVal;

        public Reson(int idReson, int idPlace, int idAccount, int valueVote, int dipVote, String place, String reson, String account, String valueV, String dipV, double diposit, double dayVal, double hourVal) {
            this.idReson = idReson;
            this.idPlace = idPlace;
            this.idAccount = idAccount;
            this.valueVote = valueVote;
            this.dipVote = dipVote;
            this.place = place;
            this.reson = reson;
            this.account = account;
            this.valueV = valueV;
            this.dipV = dipV;
            this.diposit = diposit;
            this.dayVal = dayVal;
            this.hourVal = hourVal;
        }

        public int getIdReson() {
            return idReson;
        }

        public int getIdPlace() {
            return idPlace;
        }

        public int getIdAccount() {
            return idAccount;
        }

        public int getValueVote() {
            return valueVote;
        }

        public int getDipVote() {
            return dipVote;
        }

        public String getPlace() {
            return place;
        }

        public String getReson() {
            return reson;
        }

        public String getAccount() {
            return account;
        }

        public String getValueV() {
            return valueV;
        }

        public String getDipV() {
            return dipV;
        }

        public double getDiposit() {
            return diposit;
        }

        public double getDayVal() {
            return dayVal;
        }

        public double getHourVal() {
            return hourVal;
        }
    }


}
