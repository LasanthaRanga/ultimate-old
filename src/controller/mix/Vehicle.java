package controller.mix;

import com.jfoenix.controls.*;
import conn.DB;
import conn.NewHibernateUtil;
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
import modle.GetInstans;
import modle.popup.BarcodeStatic;
import org.hibernate.Session;
import pojo.Customer;

import java.io.Serializable;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class Vehicle implements Initializable {
    @FXML
    private JFXTextField txt_nic;

    @FXML
    private JFXTextField txt_name;

    @FXML
    private JFXTextField txt_adl1;

    @FXML
    private JFXTextField txt_adl2;

    @FXML
    private JFXTextField txt_adl3;

    @FXML
    private JFXTextField txt_mobile;

    @FXML
    private JFXComboBox<MixCombo> com_type;

    @FXML
    private JFXTextField txt_des;

    @FXML
    private JFXTextField txt_val;

    @FXML
    private JFXCheckBox ch_vat;

    @FXML
    private JFXCheckBox ch_nbt;

    @FXML
    private JFXCheckBox ch_stamp;

    @FXML
    private Text txt_tot;

    @FXML
    private JFXButton btn_add;

    @FXML
    private TableView<MixData> tbl_mix;

    @FXML
    private TableColumn<Mixincome.MixData, String> col_appname;

    @FXML
    private TableColumn<Mixincome.MixData, String> col_des;

    @FXML
    private TableColumn<Mixincome.MixData, Double> col_val;

    @FXML
    private TableColumn<Mixincome.MixData, Double> col_vat;

    @FXML
    private TableColumn<Mixincome.MixData, Double> col_nbt;

    @FXML
    private TableColumn<Mixincome.MixData, Double> col_stamp;

    @FXML
    private TableColumn<Mixincome.MixData, Double> col_tot;

    @FXML
    private Text txt_fullTot;

    @FXML
    private JFXButton btn_pay;

    @FXML
    private JFXButton btn_remove;

    @FXML
    private JFXTextField txt_combo;

    @FXML
    private Text txt_vat;

    @FXML
    private Text txt_nbt;

    @FXML
    private Text txt_stamp;

    @FXML
    private JFXTextField txt_totalval;

    @FXML
    private JFXToggleButton btn_togal;

    @FXML
    private Text txt_account;


    public static boolean hasCustomer = false;
    public static int idcustomer = 0;
    public static String sdate = "";
    public static int year = 0;
    public static double vatr, nbtr, stampr;
    public int accid = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accid = 0;
        txt_val.setDisable(false);
        txt_totalval.setDisable(true);

        txt_name.setDisable(true);
        txt_adl1.setDisable(true);
        txt_adl2.setDisable(true);
        txt_adl3.setDisable(true);
        txt_mobile.setDisable(true);

        loadCombo(null);
        loadRate();
        Date systemDate = GetInstans.getQuater().getSystemDate();

        sdate = new SimpleDateFormat("yyyy-MM-dd").format(systemDate);
        year = GetInstans.getQuater().getCurrentYear();

        col_appname.setCellValueFactory(new PropertyValueFactory<>("typename"));
        col_des.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_val.setCellValueFactory(new PropertyValueFactory<>("value"));
        col_vat.setCellValueFactory(new PropertyValueFactory<>("vat"));
        col_nbt.setCellValueFactory(new PropertyValueFactory<>("nbt"));
        col_stamp.setCellValueFactory(new PropertyValueFactory<>("stamp"));
        col_tot.setCellValueFactory(new PropertyValueFactory<>("fulltot"));

    }

    @FXML
    void typeSelect(ActionEvent event) {
        int id = com_type.getSelectionModel().getSelectedItem().getId();
        System.out.println(id + "   I D");
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "mixintype.idMixintype,\n" +
                    "mixintype.mixintype_name,\n" +
                    "mixintype.account_receipt_title_idAccount_receipt_title,\n" +
                    "mixintype.mixintype_status,\n" +
                    "mixintype.bankinfo_idBank,\n" +
                    "bank_info.idBank_Info,\n" +
                    "bank_info.bank_name,\n" +
                    "bank_info.acount_no,\n" +
                    "bank_info.acount_name,\n" +
                    "bank_info.current_ballance,\n" +
                    "bank_info.`status`,\n" +
                    "bank_info.syn\n" +
                    "FROM\n" +
                    "mixintype\n" +
                    "INNER JOIN bank_info ON bank_info.idBank_Info = mixintype.bankinfo_idBank\n" +
                    "WHERE idMixintype =" + id);
            if (data.last()) {
                int size = tbl.size();
                if (size == 0) {
                    System.out.println(accid + " ACCID size 0");
                    txt_account.setText(data.getString("acount_name"));
                    accid = data.getInt("bankinfo_idBank");
                } else {
                    System.out.println(accid + " ACCID size >0");
                    if (accid == 0) {
                        txt_account.setText(data.getString("acount_name"));
                        accid = data.getInt("bankinfo_idBank");

                        System.out.println(accid + " ACCID  accid = 0");

                    } else {
                        if (accid == data.getInt("bankinfo_idBank")) {
                            txt_account.setText(data.getString("acount_name"));
                            accid = data.getInt("bankinfo_idBank");
                            System.out.println(accid + " samanai ");
                        } else {
                            modle.Allert.notificationWorning("Cannot Add Different Account", "Create New Bill");
                            String text = txt_combo.getText();
                            System.out.println(" wenas ");
                            if (text.length() > 0) {
                                loadCombo(text);
                            } else {
                                loadCombo(null);
                            }
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    void keyReleased(KeyEvent event) {
        String text = txt_combo.getText();
        if (event.getCode() == KeyCode.ENTER) {
            loadCombo(text);
        }
    }

    @FXML
    void nicKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            getCustomerData();
            txt_name.setDisable(false);
            txt_adl1.setDisable(false);
            txt_adl2.setDisable(false);
            txt_adl3.setDisable(false);
            txt_mobile.setDisable(false);
        }
    }


    double value = 0;
    double vat = 0;
    double nbt = 0;
    double stamp = 0;
    double fulltotal = 0;


    @FXML
    void checkBoxClick(ActionEvent event) {
        cal();
    }

    @FXML
    void keyReleaseOnValue(KeyEvent event) {
        if (txt_val.getText().matches("\\d*(\\.\\d*)?")) {
            if (txt_val.getText().length() > 0) {
                cal();
            } else {
                txt_val.setText("0");
                cal();
            }
        } else {
            java.awt.Toolkit.getDefaultToolkit().beep();
            txt_val.deletePreviousChar();
        }
    }


    public void cal() {
        value = 0;
        vat = 0;
        nbt = 0;
        stamp = 0;
        fulltotal = 0;

        double ft = 0;

        if (btn_togal.isSelected()) {
            txt_val.setDisable(true);
            txt_totalval.setDisable(false);
            double tt = 0;
            if (txt_totalval.getText().length() > 0) {
                tt = Double.parseDouble(txt_totalval.getText());
            }
            fulltotal = modle.Round.round(tt);

            txt_tot.setText(modle.Round.roundToString(tt));

            boolean v = false;
            boolean n = false;
            boolean s = false;

            if (ch_vat.isSelected()) {
                ft += vatr;
                v = true;
            } else {
                vat = 0;
                txt_vat.setText(modle.Round.roundToString(vat));
            }

            if (ch_nbt.isSelected()) {
                n = true;
                ft += nbtr;
            } else {
                nbt = 0;
                txt_nbt.setText(modle.Round.roundToString(nbt));
            }

            if (ch_stamp.isSelected()) {
                ft += stampr;
                s = true;
                txt_stamp.setText(modle.Round.roundToString(stamp));
            } else {
                stamp = 0;
                txt_stamp.setText(modle.Round.roundToString(stamp));
            }

            double val = tt / (100 + ft) * 100;

            value = modle.Round.round(val);

            if (v) {
                vat = value * vatr / 100;
                vat = modle.Round.round(vat);
                txt_vat.setText(modle.Round.roundToString(vat));
            }
            if (n) {
                nbt = value * nbtr / 100;
                nbt = modle.Round.round(nbt);
                txt_nbt.setText(modle.Round.roundToString(nbt));
            }
            if (s) {
                stamp = value * stampr / 100;
                stamp = modle.Round.round(stamp);
                txt_stamp.setText(modle.Round.roundToString(stamp));
            }

            double v2 = value + vat + nbt + stamp;
            double v3 = 0;
            if (value + vat + nbt + stamp == tt) {

            } else if (value + vat + nbt + stamp > tt) {
                v3 = v2 - tt;
                value -= v3;

            } else if (value + vat + nbt + stamp < tt) {
                v3 = tt - v2;
                value += v3;
            }

            txt_val.setText(modle.Round.roundToString(value));
            value = modle.Round.round(value);
        } else {
            txt_val.setDisable(false);
            txt_totalval.setDisable(true);

            try {
                value = 0;
                if (txt_val.getText().length() > 0) {
                    value = Double.parseDouble(txt_val.getText());
                }


                if (ch_vat.isSelected()) {
                    vat = value * vatr / 100;
                    vat = modle.Round.round(vat);
                    txt_vat.setText(modle.Round.roundToString(vat));
                } else {
                    vat = 0;
                    txt_vat.setText(modle.Round.roundToString(vat));
                }


                if (ch_nbt.isSelected()) {
                    nbt = value * nbtr / 100;
                    nbt = modle.Round.round(nbt);
                    txt_nbt.setText(modle.Round.roundToString(nbt));

                } else {
                    nbt = 0;
                    txt_nbt.setText(modle.Round.roundToString(nbt));
                }


                if (ch_stamp.isSelected()) {
                    stamp = value * stampr / 100;
                    stamp = modle.Round.round(stamp);
                    txt_stamp.setText(modle.Round.roundToString(stamp));
                } else {
                    stamp = 0;
                    txt_stamp.setText(modle.Round.roundToString(stamp));
                }

                fulltotal = value + vat + stamp + nbt;
                fulltotal = modle.Round.round(fulltotal);
                txt_totalval.setText(modle.Round.roundToString(fulltotal));
                txt_tot.setText(modle.Round.roundToString(fulltotal));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void clickOnAdd(MouseEvent event) {
        System.out.println("Add To Table");
        addToTable();
    }

    public double calval(double val, double vat, double nbt, double stamp) {


        return val;
    }

    @FXML
    void togalOnAction(ActionEvent event) {
        cal();
    }

    @FXML
    void keyReleaseTotalValue(KeyEvent event) {
        cal();
    }

    ObservableList<MixData> tbl = FXCollections.observableArrayList();

    public void addToTable() {
        System.out.println("ok");
        try {


            double totalfull = 0;
            int size = tbl.size();
            String text = txt_des.getText();
            if (text.length() > 2) {
                System.out.println("2 ta awa");
                if (size < 4) {

                    Vehicle.MixData mixData = new Vehicle.MixData(size + 1, com_type.getSelectionModel().getSelectedItem().getId(), com_type.getSelectionModel().getSelectedItem().getName(),
                            txt_des.getText(), value, vat, nbt, stamp, fulltotal);
                    tbl.add(mixData);
                    tbl_mix.setItems(tbl);


                    for (Vehicle.MixData md : tbl) {
                        totalfull += md.getFulltot();
                    }
                    totalfull = modle.Round.round(totalfull);

                    if (totalfull > 0) {
                        txt_des.setDisable(true);
                    } else {
                        txt_des.setDisable(false);
                    }

                    txt_fullTot.setText(modle.Round.roundToString(totalfull));

                } else {
                    modle.Allert.notificationWorning("Too Load", "Can not add more thean 4");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    @FXML
    void clickOnPay(MouseEvent event) {
        payment();
    }


    public void payment() {
        int size = tbl.size();
        if (size > 0) {
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            //  Transaction transaction = session.beginTransaction();
            try {

                pojo.Customer cus = null;
                double tot = Double.parseDouble(txt_fullTot.getText());
                if (hasCustomer) {
                    cus = (pojo.Customer) session.load(Customer.class, idcustomer);
                } else {
                    if (txt_name.getText().length() > 2) {
                        cus = new Customer();
                        cus.setCusNic(txt_nic.getText());
                        cus.setCusName(txt_name.getText());
                        cus.setCusMobile(txt_mobile.getText());
                        cus.setCusAddressL1(txt_adl1.getText());
                        cus.setCusAddressL2(txt_adl2.getText());
                        cus.setCusAddressL3(txt_adl3.getText());
                        Serializable save = session.save(cus);
                        cus = (pojo.Customer) session.load(pojo.Customer.class, save);
                    } else {
                        modle.Allert.notificationError("Customer Name", "Please Check Customer Name");
                    }
                }
                int riciptid = 0;
                if (cus != null) {
                    pojo.Mixincome mixincome = new pojo.Mixincome();
                    mixincome.setMixincomeDate(modle.GetInstans.getQuater().getSystemDate());
                    mixincome.setMixincomeUserid(modle.StaticViews.getLogUser().getIdUser());
                    mixincome.setMixincomeFulltotal(Double.parseDouble(txt_fullTot.getText()));
                    mixincome.setMixincomeYear(modle.GetInstans.getQuater().getCurrentYear());
                    mixincome.setCustomerIdCustomer(cus.getIdCustomer());
                    mixincome.setMixincomeStatus(0);

                    String format = new SimpleDateFormat("yyyy-MM-dd").format(GetInstans.getQuater().getSystemDate());

                    Serializable save = session.save(mixincome);
                    int mixindi = Integer.parseInt(save.toString());

                    conn.DB.setData("INSERT INTO `receipt` (\n" +
                            "\t`Application_Catagory_idApplication_Catagory`,\n" +
                            "\t`recept_applicationId`,\n" +

                            "\t`receipt_total`,\n" +
                            "\t`receipt_day`,\n" +
                            "\t`receipt_status`,\n" +
                            "\t`receipt_syn`,\n" +
                            "\t`chque_no`,\n" +
                            "\t`chque_bank`\n" +
                            ")\n" +
                            "VALUES\n" +
                            "\t(\n" +
                            "\t\t'11',\n" +
                            "\t\t'" + mixindi + "',\n" +
                            "\t\t'" + Double.parseDouble(txt_fullTot.getText()) + "',\n" +
                            "\t\t'" + format + "',\n" +
                            "\t\t'0',\n" +
                            "\t\t'1',\n" +
                            "\t\t'',\n" +
                            "\t\t''\n" +
                            "\t)\n");

                    ResultSet data = DB.getData("SELECT\n" +
                            "receipt.idReceipt\n" +
                            "FROM\n" +
                            "\t`receipt`\n" +
                            "WHERE\n" +
                            "\treceipt.Application_Catagory_idApplication_Catagory = 11\n" +
                            "AND receipt.receipt_print_no IS NULL\n" +
                            "ORDER BY\n" +
                            "\treceipt.idReceipt DESC\n" +
                            "LIMIT 1");


                    if (data.next()) {
                        riciptid = data.getInt("idReceipt");
                    }

                    session.beginTransaction().commit();

                    for (Vehicle.MixData md : tbl) {
                        conn.DB.setData("INSERT INTO `mixdata` (\n" +
                                "\t`md_description`,\n" +
                                "\t`md_amount`,\n" +
                                "\t`md_vat`,\n" +
                                "\t`md_nbt`,\n" +
                                "\t`md_stamp`,\n" +
                                "\t`md_total`,\n" +
                                "\t`mixintype_idMixintype`,\n" +
                                "\t`mixincome_IdMixincome`\n" +
                                ")\n" +
                                "VALUES\n" +
                                "\t(\n" +
                                "\t\t '" + md.getDescription() + "',\n" +
                                "\t\t'" + md.getValue() + "',\n" +
                                "\t\t'" + md.getVat() + "',\n" +
                                "\t\t'" + md.nbt + "',\n" +
                                "\t\t'" + md.stamp + "',\n" +
                                "\t\t'" + md.fulltot + "',\n" +
                                "\t\t'" + md.getTypeid() + "',\n" +
                                "\t\t'" + mixindi + "'\n" +
                                "\t)");


                    }
                }


//                modle.Allert.notificationGood("OK", "Success " + riciptid);
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Receipt Number");
//                alert.setHeaderText(null);
//                alert.setContentText("This Mix Income Payment ID IS :   " + riciptid);

                BarcodeStatic.customerName = cus.getCusName();
                BarcodeStatic.idRecipt = riciptid+"";
                BarcodeStatic.reTotal = tot;
                BarcodeStatic.subject = "Vehicle Service";


//                alert.showAndWait();

                clearall();
                popup();
            } catch (Exception e) {

                e.printStackTrace();
            } finally {
                session.close();
            }

        } else {
            modle.Allert.notificationError("Table Empty", "Please Check Data");
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
            BarcodeStatic.idRecipt = 0+"";
            BarcodeStatic.reTotal = 0;

        } else {

            modle.GetInstans.getPrintBarcode().print(BarcodeStatic.customerName, BarcodeStatic.idRecipt + "", modle.Round.roundToString(BarcodeStatic.reTotal), BarcodeStatic.subject);
            BarcodeStatic.customerName = null;
            BarcodeStatic.subject = null;
            BarcodeStatic.idRecipt = 0+"";
            BarcodeStatic.reTotal = 0;

        }

    }


    public void clearall() {

        txt_name.setText(null);

        txt_nic.setText(null);

        txt_adl1.setText(null);

        txt_adl2.setText(null);

        txt_adl3.setText(null);

        txt_mobile.setText(null);

        txt_des.setText(null);

        txt_combo.setText(null);

        txt_val.setText("0");

        ch_stamp.setSelected(false);
        ch_nbt.setSelected(false);
        ch_vat.setSelected(false);

        accid = 0;

        tbl.clear();


    }

    int idselected = 0;

    @FXML
    void clickOnRemove(MouseEvent event) {
        if (idselected > 0) {
            Vehicle.MixData selectedItem = tbl_mix.getSelectionModel().getSelectedItem();
            tbl.remove(selectedItem);
            double totalfull = 0;
            for (Vehicle.MixData md : tbl) {
                totalfull += md.getFulltot();
            }
            totalfull = modle.Round.round(totalfull);

            txt_fullTot.setText(modle.Round.roundToString(totalfull));
            if (totalfull > 0) {
                txt_des.setDisable(true);
            } else {
                txt_des.setDisable(false);
            }
        }
    }

    @FXML
    void clickOnTable(MouseEvent event) {
        if (tbl_mix.getSelectionModel() != null) {
            idselected = tbl_mix.getSelectionModel().getSelectedItem().getId();
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
                txt_adl1.setText(cus_address_l1);
                txt_adl2.setText(cus_address_l2);
                txt_adl3.setText(cus_address_l3);
                txt_mobile.setText(cus_mobile);
                hasCustomer = true;

            } else {
                txt_name.setText(null);
                txt_adl1.setText(null);
                txt_adl2.setText(null);
                txt_adl3.setText(null);
                txt_mobile.setText(null);
                idcustomer = 0;
                hasCustomer = false;
            }
        } catch (Exception e) {
        }
    }


    public void loadCombo(String vote) {
        ObservableList<MixCombo> applist = FXCollections.observableArrayList();
        try {

            String quary = "SELECT\n" +
                    "mixintype.idMixintype,\n" +
                    "mixintype.mixintype_name,\n" +
                    "mixintype.account_receipt_title_idAccount_receipt_title,\n" +
                    "mixintype.mixintype_status,\n" +
                    "mixintype.bankinfo_idBank\n" +
                    "FROM `mixintype`\n" +
                    "WHERE\n" +
                    "mixintype.mixintype_status = 2 ";
            if (vote != null) {
                quary += " AND\n" +
                        "mixintype.mixintype_name LIKE '%" + vote + "%'";
            }

            ResultSet data = DB.getData(quary);

            applist.clear();
            while (data.next()) {
                applist.add(new Vehicle.MixCombo(data.getInt("idMixintype"), data.getString("mixintype_name")));
            }
            com_type.setItems(applist);
        } catch (Exception e) {
            e.printStackTrace();
        }


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

            System.out.println(vatr + " vat");
            System.out.println(nbtr + " nbt");
            System.out.println(stampr + " stamp");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class MixCombo {
        private int id;
        private String name;

        MixCombo(int id, String name) {
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
            return this.getName();
        }
    }

    public class MixData {

        int id;
        int typeid;
        String typename;
        String description;
        double value;
        double vat;
        double nbt;
        double stamp;
        double fulltot;

        public MixData(int id, int typeid, String typename, String description, double value, double vat, double nbt, double stamp, double fulltot) {
            this.id = id;
            this.typeid = typeid;
            this.typename = typename;
            this.description = description;
            this.value = value;
            this.vat = vat;
            this.nbt = nbt;
            this.stamp = stamp;
            this.fulltot = fulltot;
        }


        public int getId() {
            return id;
        }

        public int getTypeid() {
            return typeid;
        }

        public String getTypename() {
            return typename;
        }

        public String getDescription() {
            return description;
        }

        public double getValue() {
            return value;
        }

        public double getVat() {
            return vat;
        }

        public double getNbt() {
            return nbt;
        }

        public double getStamp() {
            return stamp;
        }

        public double getFulltot() {
            return fulltot;
        }
    }


}
