package controller.mix;

import com.jfoenix.controls.*;
import conn.DB;
import conn.NewHibernateUtil;
import controller.adv.Customer_regController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modle.GetInstans;
import modle.KeyVal;
import modle.StaticBadu;
import modle.StaticViews;
import modle.asses.PayNowModle;
import modle.popup.BarcodeStatic;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.event.spi.LoadEventListener;
import pojo.Customer;
import pojo.Mixdata;
import pojo.Receipt;
import pojo.User;
import view.buttons.BTN;


import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mixincome implements Initializable {
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
    private TableColumn<MixData, String> col_appname;

    @FXML
    private TableColumn<MixData, String> col_des;

    @FXML
    private TableColumn<MixData, Double> col_val;

    @FXML
    private TableColumn<MixData, Double> col_vat;

    @FXML
    private TableColumn<MixData, Double> col_nbt;

    @FXML
    private TableColumn<MixData, Double> col_stamp;

    @FXML
    private TableColumn<MixData, Double> col_tot;

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

    @FXML
    private JFXRadioButton radio_cheque;

    @FXML
    private JFXRadioButton radio_no;

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

    @FXML
    private JFXTextField txt_ref;

    @FXML
    private JFXButton btn_search;

    @FXML
    private JFXButton btn_clear;

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
        com_bank.setItems(modle.GetInstans.getBanks().loadBanksCombo());
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
        btn_pay.setDisable(true);
        modle.StaticViews.getMc().changeTitle("Mix Income");

        StaticBadu.setMixincome(this);
    }

    public static Search.Cross cross = null;

    public void loadCrossData(Search.Cross cross) {
        this.cross = cross;
        txt_ref.setText(cross.ref);
        int cusID = cross.getCusID();
        getCustomerById(cusID);
        btn_clear.setDisable(false);
    }


    @FXML
    void clearVoucher(ActionEvent event) {
        clearVoucher();
    }


    public void clearVoucher() {
        txt_ref.setText(null);
        txt_name.setText(null);
        txt_adl1.setText(null);
        txt_adl2.setText(null);
        txt_adl3.setText(null);
        txt_mobile.setText(null);
        idcustomer = 0;
        hasCustomer = false;
        btn_clear.setDisable(true);
        this.cross = null;
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
        double totalfull = 0;
        int size = tbl.size();

        if (size < 4) {
            MixData mixData = new MixData(size + 1, com_type.getSelectionModel().getSelectedItem().getId(), com_type.getSelectionModel().getSelectedItem().getName(),
                    txt_des.getText(), value, vat, nbt, stamp, fulltotal);
            tbl.add(mixData);
            tbl_mix.setItems(tbl);
            for (MixData md : tbl) {
                totalfull += md.getFulltot();
            }
            totalfull = modle.Round.round(totalfull);

            txt_fullTot.setText(modle.Round.roundToString(totalfull));

        } else {
            modle.Allert.notificationWorning("Too Load", "Can not add more thean 4");
        }

        txt_des.setText("");
        txt_val.setText("0");
        cal();


    }


    @FXML
    void clickOnPay(MouseEvent event) {
        btn_pay.setDisable(false);
        payment();


    }

    int print = 0;

    public void payment() {
        int cusid = 0;
        int size = tbl.size();
        if (size > 0) {
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            //  Transaction transaction = session.beginTransaction();
            try {

                pojo.Customer cus = null;
                double tot = Double.parseDouble(txt_fullTot.getText());

                if (hasCustomer) {
                    cus = (pojo.Customer) session.load(Customer.class, idcustomer);
                    cusid = cus.getIdCustomer();
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
                        cusid = cus.getIdCustomer();
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
                    mixincome.setMixincomePaytype(paytype);

                    if (cross != null) {
                        mixincome.setCrosRef(cross.getRef());
                    }


                    String format = new SimpleDateFormat("yyyy-MM-dd").format(GetInstans.getQuater().getSystemDate());

                    Serializable save = session.save(mixincome);

                    int mixindi = Integer.parseInt(save.toString());


                    for (MixData md : tbl) {

                        Mixdata mixdata = new Mixdata();
                        mixdata.setMixincome(mixincome);
                        mixdata.setMixintype((pojo.Mixintype) session.load(pojo.Mixintype.class, md.getTypeid()));
                        mixdata.setMdTotal(md.getFulltot());
                        mixdata.setMdVat(md.getVat());
                        mixdata.setMdNbt(md.getNbt());
                        mixdata.setMdStamp(md.stamp);
                        mixdata.setMdAmount(md.getValue());
                        mixdata.setMdDescription(md.getDescription());
                        session.save(mixdata);

//                    conn.DB.setData("INSERT INTO `mixdata` (\n" +
//                                "\t`md_description`,\n" +
//                                "\t`md_amount`,\n" +
//                                "\t`md_vat`,\n" +
//                                "\t`md_nbt`,\n" +
//                                "\t`md_stamp`,\n" +
//                                "\t`md_total`,\n" +
//                                "\t`mixintype_idMixintype`,\n" +
//                                "\t`mixincome_IdMixincome`\n" +
//                                ")\n" +
//                                "VALUES\n" +
//                                "\t(\n" +
//                                "\t\t '" + md.getDescription() + "',\n" +
//                                "\t\t'" + md.getValue() + "',\n" +
//                                "\t\t'" + md.getVat() + "',\n" +
//                                "\t\t'" + md.nbt + "',\n" +
//                                "\t\t'" + md.stamp + "',\n" +
//                                "\t\t'" + md.fulltot + "',\n" +
//                                "\t\t'" + md.getTypeid() + "',\n" +
//                                "\t\t'" + mixindi + "'\n" +
//                                "\t)");


                    }


                    double ca = 0;
                    double cq = 0;
                    double nc = 0;

                    if (paytype == 1) {
                        ca = Double.parseDouble(txt_fullTot.getText());
                    }
                    if (paytype == 2) {
                        cq = Double.parseDouble(txt_fullTot.getText());
                    }
                    if (paytype == 3) {
                        nc = Double.parseDouble(txt_fullTot.getText());
                    }


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


                    int typeid = 0;

                    for (MixData md : tbl) {
                        typeid = md.getTypeid();
                    }

                    int bankinfoIdBank = 0;
                    ResultSet getbank = DB.getData("SELECT\n" +
                            "bank_info.idBank_Info,\n" +
                            "bank_info.bank_name,\n" +
                            "mixintype.idMixintype,\n" +
                            "mixintype.mixintype_name\n" +
                            "FROM\n" +
                            "mixintype\n" +
                            "INNER JOIN bank_info ON bank_info.idBank_Info = mixintype.bankinfo_idBank\n" +
                            "WHERE\n" +
                            "mixintype.idMixintype = " + typeid);
                    if (getbank.last()) {
                        bankinfoIdBank = getbank.getInt("idBank_Info");
                    }


                    String qq = "INSERT INTO `receipt` (\n" +
                            "\t`Application_Catagory_idApplication_Catagory`,\n" +
                            "\t`recept_applicationId`,\n" +
                            "\t`cheack`,\n" +
                            "\t`cesh`,\n" +
                            "\t`receipt_total`,\n" +
                            "\t`receipt_day`,\n" +
                            "\t`receipt_status`,\n" +
                            "\t`receipt_syn`,\n" +
                            "\t`chque_no`,\n" +
                            "\t`chque_date`,\n" +
                            "\t`chque_bank`,\n" +
                            "\t`office_idOffice`,\n" +
                            "\t`receipt_account_id`,\n" +
                            "\t`receipt_user_id`, `cus_id` \n" +
                            ")\n" +
                            "VALUES\n" +
                            "\t(\n" +
                            "\t\t'9',\n" +
                            "\t\t'" + mixindi + "',\n" +
                            "\t\t'" + cq + "',\n" +
                            "\t\t'" + ca + "',\n" +
                            "\t\t'" + Double.parseDouble(txt_fullTot.getText()) + "',\n" +
                            "\t\t'" + format + "',\n" +
                            "\t\t'0',\n" +
                            "\t\t'1',\n" +
                            "\t\t'" + chno + "',\n";

                    if (chdate == null) {
                        qq += "\t\t" + chdate + ",\n";
                    } else {
                        qq += "\t\t'" + chdate + "',\n";
                    }

                    qq += "\t\t'" + idbank + "',\n" +
                            "\t\t'" + modle.StaticViews.getLogUser().getOfficeIdOffice() + "',\n" +
                            "\t\t'" + bankinfoIdBank + "',\n" +
                            "\t'" + modle.StaticViews.getLogUser().getIdUser() + "', '" + cusid + "' \n" +
                            "\t)";

                    conn.DB.setData(qq);

                    ResultSet data = DB.getData("SELECT\n" +
                            "receipt.idReceipt\n" +
                            "FROM\n" +
                            "\t`receipt`\n" +
                            "WHERE\n" +
                            "\treceipt.Application_Catagory_idApplication_Catagory = 9\n" +
                            "AND receipt.receipt_print_no IS NULL\n" +
                            "ORDER BY\n" +
                            "\treceipt.idReceipt DESC\n" +
                            "LIMIT 1");




                    if (data.next()) {
                        riciptid = data.getInt("idReceipt");
                    }

                    session.beginTransaction().commit();

                }


                String mix_barcode_yes_no = KeyVal.getVal("mix_barcode_yes_no"); // Barcode Yes Or No

                if (mix_barcode_yes_no.equals("no")) {
                    BarcodeStatic.idRecipt = riciptid + "";
                    AnchorPane container = modle.StaticViews.getContainer();
                    container.getChildren().removeAll();
                    container.getChildren().clear();

                    try {

                        container.getChildren().add(FXMLLoader.load(getClass().getResource("/view/payment/payById.fxml")));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        Logger
                                .getLogger(Customer_regController.class
                                        .getName()).log(Level.SEVERE, null, ex);
                    }
                    Set<String> keySet = modle.StaticViews.getButtonMap().keySet();
                    String s = "/view/buttons/pay_by_id.fxml";

                    for (String string : keySet) {
                        if (string.equals(s)) {
                            try {
                                JFXButton get = modle.StaticViews.getButtonMap().get(string);
                                BTN get1 = modle.StaticViews.getBtnConMap().get(string);
                                get1.setImage("/grafics/pay_b.png");
                                get.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #03A9F4;");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                JFXButton btn = modle.StaticViews.getButtonMap().get(string);
                                btn.setStyle("-fx-background-color: #03A9F4; -fx-text-fill: #FFFFFF;");
                                BTN get = modle.StaticViews.getBtnConMap().get(string);
                                if (get != null) {
                                    get.setImage();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                    }


                } else {
                    BarcodeStatic.customerName = cus.getCusName();
                    BarcodeStatic.idRecipt = riciptid + "";
                    BarcodeStatic.reTotal = tot;
                    BarcodeStatic.subject = "Mix Income";
                    popup();
                }


                clearall();
                clearVoucher();
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


    public void clearall() {

        txt_name.setText(null);

        txt_nic.setText(null);

        txt_adl1.setText(null);

        txt_adl2.setText(null);

        txt_adl3.setText(null);

        txt_mobile.setText(null);

        txt_des.setText(null);

        txt_combo.setText(null);

        txt_des.setText(null);

        txt_val.setText("00");
        txt_vat.setText("00");
        txt_nbt.setText("00");
        txt_stamp.setText("00");
        txt_totalval.setText("00");
        txt_tot.setText("00");


        ch_stamp.setSelected(false);
        ch_nbt.setSelected(false);
        ch_vat.setSelected(false);

        accid = 0;

        tbl.clear();

        radio_cheque.setSelected(false);
        radio_cash.setSelected(false);
        txt_fullTot.setText(null);

        // loadCombo(null);

    }


    int idselected = 0;

    @FXML
    void clickOnRemove(MouseEvent event) {
        if (idselected > 0) {
            MixData selectedItem = tbl_mix.getSelectionModel().getSelectedItem();
            tbl.remove(selectedItem);
            double totalfull = 0;
            for (MixData md : tbl) {
                totalfull += md.getFulltot();
            }
            totalfull = modle.Round.round(totalfull);

            txt_fullTot.setText(modle.Round.roundToString(totalfull));
        }
    }

    @FXML
    void clickOnTable(MouseEvent event) {
        if (tbl_mix.getSelectionModel() != null) {
            idselected = tbl_mix.getSelectionModel().getSelectedItem().getId();
        }
    }

    public void getCustomerById(int id) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "customer.idCustomer,\n" +
                    "customer.cus_name,\n" +
                    "customer.cus_person_title,\n" +
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
                    "customer.idCustomer = " + id);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

//            String quary = "SELECT\n" +
//                    "mixintype.idMixintype,\n" +
//                    "mixintype.mixintype_name,\n" +
//                    "mixintype.account_receipt_title_idAccount_receipt_title,\n" +
//                    "mixintype.mixintype_status,\n" +
//                    "mixintype.bankinfo_idBank,\n" +
//                    "mixintype.active_status\n" +
//                    "FROM\n" +
//                    "mixintype\n" +
//                    "WHERE\n" +
//                    "mixintype.active_status = 1 ";

            String quary = "SELECT\n" +
                    "mixintype.idMixintype,\n" +
                    "mixintype.mixintype_name,\n" +
                    "mixintype.account_receipt_title_idAccount_receipt_title,\n" +
                    "mixintype.mixintype_status,\n" +
                    "mixintype.bankinfo_idBank,\n" +
                    "mixintype.active_status,\n" +
                    "mixintype.office_id\n" +
                    "FROM\n" +
                    "\tmixintype\n" +
                    "WHERE\n" +
                    "\tmixintype.active_status = 1\n" +
                    "AND mixintype.office_id = ' " + modle.StaticViews.getLogUser().getOfficeIdOffice() + "' ";

            if (vote != null) {
                quary += "AND  mixintype.mixintype_name LIKE '%" + vote + "%'";
            }

            ResultSet data = DB.getData(quary);

            applist.clear();
            while (data.next()) {
                applist.add(new MixCombo(data.getInt("idMixintype"), data.getString("mixintype_name")));
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


    int paytype = 0;


    @FXML
    void noCashRadio(ActionEvent event) {
        radioOnAction(event);
        txt_ref.setDisable(false);
        btn_search.setDisable(false);


    }


    @FXML
    void radioOnAction(ActionEvent event) {

        if (radio_no.isSelected()) {
            btn_pay.setDisable(false);
            System.out.println("NO");
            paytype = 3;

            txt_chequeNo.setDisable(true);
            com_bank.setDisable(true);
            dp_chque.setDisable(true);


        } else if (radio_cash.isSelected()) {
            btn_pay.setDisable(false);
            System.out.println("CASH");
            paytype = 1;

            txt_chequeNo.setDisable(true);
            com_bank.setDisable(true);
            dp_chque.setDisable(true);
            txt_ref.setDisable(true);
            btn_search.setDisable(true);

        } else if (radio_cheque.isSelected()) {
            btn_pay.setDisable(true);
            System.out.println("CHQUE");
            paytype = 2;

            txt_chequeNo.setDisable(false);
            com_bank.setDisable(false);
            dp_chque.setDisable(false);
            txt_ref.setDisable(true);
            btn_search.setDisable(true);

        } else {
            btn_pay.setDisable(true);
            System.out.println("CHQUE");
            paytype = 0;
        }

    }

    @FXML
    void selectBank(ActionEvent event) {
        String text = txt_chequeNo.getText();
        String selectedItem = com_bank.getSelectionModel().getSelectedItem();
        LocalDate value = dp_chque.getValue();
        if (text != null && text.length() > 0 && selectedItem != null && selectedItem.length() > 0 && value != null) {
            btn_pay.setDisable(false);
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


    @FXML
    void clickOnSearch(ActionEvent event) {
        System.out.println("click on  search");

        Parent root;
        try {

            root = FXMLLoader.load(getClass().getResource("/view/mix/search.fxml"));

            Stage stage = new Stage();

            stage.initStyle(StageStyle.TRANSPARENT);


            stage.getIcons().add(new Image("/grafics/info.png"));
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            //   Logger.getLogger(AssessmangController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
