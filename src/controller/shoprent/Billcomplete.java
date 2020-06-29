package controller.shoprent;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import conn.DB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import modle.GetInstans;
import modle.StaticViews;

import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Billcomplete implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txt_reciptid.setText(null);
                txt_reciptid.requestFocus();
            }
        });

    }

    @FXML
    private JFXButton btn_complete;

    @FXML
    private JFXTextField txt_reciptid;

    @FXML
    private Text txt_total;


    boolean rady = false;


    @FXML
    void codeKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

            if (txt_reciptid.getText() != null && txt_reciptid.getText().length() > 0) {
                System.out.println(txt_reciptid.getText());
                loadDataFromReciptID(txt_reciptid.getText());


            } else {
                rady = false;
                modle.Allert.notificationWorning("Receipt ID ", "Enter correct Receipt ID");
            }


        }
    }

    @FXML
    void completeOnAction(ActionEvent event) {


    }


    @FXML
    void clickOnComplete(MouseEvent event) {
        btn_complete.setDisable(true);
        System.out.println("onClick");


        modle.GetInstans.getBillComplete().loadShopRentBill(txt_reciptid.getText(),false);
        completeShopRent(txt_reciptid.getText());

        txt_total.setText("00");
        txt_reciptid.setText(null);
        txt_reciptid.requestFocus();
        btn_complete.setDisable(false);
        rady = false;
    }

    public void loadDataFromReciptID(String idRecipt) {
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
                "receipt.oder\n" +
                "FROM `receipt`\n" +
                "WHERE\n" +
                "receipt.idReceipt = " + idRecipt;

        try {
            ResultSet data = DB.getData(query);
            if (data.last()) {
                int receipt_status = data.getInt("receipt_status");
                int cat = data.getInt("Application_Catagory_idApplication_Catagory");
                int recept_applicationId = data.getInt("recept_applicationId");
                int idReceipt = data.getInt("idReceipt");
                double receipt_total = data.getDouble("receipt_total");

                if (receipt_status == 0) {
                    txt_total.setText(data.getString("receipt_total"));

                    if (cat == 4) {
                        modle.GetInstans.getBillComplete().loadShopRentBill(txt_reciptid.getText(),false);
                        completeShopRent(txt_reciptid.getText());
                    }

                    if(cat==8){
                        modle.GetInstans.getThreweel().getVehiclepassReport(idRecipt + "",false);
                    }

                    if(cat==9){
                        modle.Payment.PaymentByID.genarateRisiptNo(9, "", recept_applicationId, 0, receipt_total, "", null, "", 0);
                        updateMixIncomeStatus(recept_applicationId, 9);
                        modle.GetInstans.getGenarateRecipt().genarateRecipt(idReceipt,false);
                    }


                    txt_reciptid.requestFocus();
                    btn_complete.setDisable(true);
                    txt_total.setText("00");
                    txt_reciptid.setText(null);
                    txt_reciptid.requestFocus();
                    btn_complete.setDisable(false);
                  //  btn_complete.setText(txt_reciptid.getText());


                } else {
                    modle.Allert.notificationWorning("Already Completed", "Please Recheck");
                    rady = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }

    final static int VATID = 33;
    final static int NBTID = 34;
    final static int STAMPID = 35;
    final static int CASH = 65;
    final static int CHQUE = 66;

    public void updateMixIncomeStatus(int appid, int appcat) {
        try {
            conn.DB.setData("UPDATE mixincome\n" +
                    "SET \n" +
                    " mixincome_status = '1'\n" +
                    "WHERE\n" +
                    "\tidMixincome = '" + appid + "'");

            ResultSet data1 = DB.getData("SELECT\n" +
                    "\treceipt.idReceipt,\n" +
                    "\treceipt.receipt_print_no,\n" +
                    "\tmixincome.idMixincome,\n" +
                    "\tmixincome.mixincome_userid,\n" +
                    "\treceipt.cheack,\n" +
                    "\treceipt.cesh\n" +
                    "FROM\n" +
                    "\treceipt\n" +
                    "INNER JOIN mixincome ON mixincome.idMixincome = receipt.recept_applicationId\n" +
                    "WHERE\n" +
                    "\treceipt.Application_Catagory_idApplication_Catagory =  '" + appcat + "'" +
                    "AND mixincome.idMixincome = " + appid);

            String reciptNo = null;
            int idRecipt = 0;
            int mixincome_userid = 0;

            double cheack = 0;
            double cesh = 0;
            if (data1.last()) {
                reciptNo = data1.getString("receipt_print_no");
                idRecipt = data1.getInt("idReceipt");
                mixincome_userid = data1.getInt("mixincome_userid");

                cheack = data1.getDouble("cheack");
                cesh = data1.getDouble("cesh");


            }

            String quary = "SELECT\n" +
                    "mixdata.idMixdata,\n" +
                    "mixdata.md_description,\n" +
                    "mixdata.md_amount,\n" +
                    "mixdata.md_vat,\n" +
                    "mixdata.md_nbt,\n" +
                    "mixdata.md_stamp,\n" +
                    "mixdata.md_total,\n" +
                    "mixdata.mixintype_idMixintype,\n" +
                    "mixdata.mixincome_IdMixincome,\n" +
                    "mixintype.idMixintype,\n" +
                    "mixintype.mixintype_name,\n" +
                    "mixintype.account_receipt_title_idAccount_receipt_title,\n" +
                    "mixintype.mixintype_status,\n" +
                    "account_receipt_title.idAccount_receipt_title,\n" +
                    "mixintype.bankinfo_idBank\n" +
                    "FROM\n" +
                    "\tmixdata\n" +
                    "INNER JOIN mixintype ON mixdata.mixintype_idMixintype = mixintype.idMixintype\n" +
                    "INNER JOIN account_receipt_title ON mixintype.account_receipt_title_idAccount_receipt_title = account_receipt_title.idAccount_receipt_title\n" +
                    "WHERE\n" +
                    "\tmixincome_IdMixincome =" + appid;

            ResultSet data = DB.getData(quary);

            Date systemDate = GetInstans.getQuater().getSystemDate();
            String date = new SimpleDateFormat("yyyy-MM-dd").format(systemDate);
            Integer idUser = StaticViews.getLogUser().getIdUser();


            int bankinfo_idBank = 0;
            while (data.next()) {
                double md_amount = data.getDouble("md_amount");
                int idVote = data.getInt("account_receipt_title_idAccount_receipt_title");
                bankinfo_idBank = data.getInt("bankinfo_idBank");
                if (md_amount > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, idVote, bankinfo_idBank, md_amount, mixincome_userid, appid, appcat,1);
                }
                double vat = data.getDouble("md_vat");
                if (vat > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, VATID, bankinfo_idBank, vat, mixincome_userid, appid, appcat,1);
                }
                double nbt = data.getDouble("md_nbt");
                if (nbt > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, NBTID, bankinfo_idBank, nbt, mixincome_userid, appid, appcat,1);
                }
                double stamp = data.getDouble("md_stamp");
                if (stamp > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, STAMPID, bankinfo_idBank, stamp, mixincome_userid, appid, appcat,1);
                }
            }

            if (cesh > 0) {
                modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, CASH, bankinfo_idBank, cesh, mixincome_userid, appid, appcat,1);
            }

            if (cheack > 0) {
                modle.Payment.CompleteAcc.insertToAccount(date, reciptNo, idRecipt, CHQUE, bankinfo_idBank, cheack, mixincome_userid, appid, appcat,1);
            }

            conn.DB.setData("UPDATE \n" +
                    "`receipt`\n" +
                    "SET \n" +
                    " `receipt_account_id` = '" + bankinfo_idBank + "',\n" +
                    " `receipt_user_id` = '" + mixincome_userid + "'\n" +
                    "WHERE\n" +
                    "\t(`idReceipt` = '" + idRecipt + "');\n");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void completeShopRent(String idRecipt) {

        String qu = "SELECT\n" +
                "sr_shop_payment.sr_shop_proc_id1,\n" +
                "sr_shop_payment.sr_shop_paid_arrears_bal,\n" +
                "sr_shop_payment.sr_shop_paid_fine_bal,\n" +
                "sr_shop_payment.sr_shop_paid_rental_tot_bal,\n" +
                "sr_shop_payment.sr_shop_paid_over_pay_bal,\n" +
                "sr_shop_payment.sr_shop_paid_proc_complete,\n" +
                "receipt.idReceipt,\n" +
                "receipt.Application_Catagory_idApplication_Catagory,\n" +
                "receipt.receipt_print_no\n" +
                "FROM\n" +
                "sr_shop_payment\n" +
                "INNER JOIN receipt ON sr_shop_payment.sr_receipt_no = receipt.receipt_print_no\n" +
                "WHERE\n" +
                "receipt.idReceipt = " + idRecipt;
        try {
            ResultSet data = DB.getData(qu);
            String reciptNo = "";
            while (data.next()) {
                String qu2 = "UPDATE `sr_shop_proc`\n" +
                        "SET `sr_shop_arrears_bal` = '" + data.getDouble("sr_shop_paid_arrears_bal") + "',\n" +
                        " `sr_shop_fine_bal` = '" + data.getDouble("sr_shop_paid_fine_bal") + "',\n" +
                        " `sr_shop_rental_tot_bal` = '" + data.getDouble("sr_shop_paid_rental_tot_bal") + "',\n" +
                        " `sr_shop_over_pay_bal` = '" + data.getDouble("sr_shop_paid_over_pay_bal") + "',\n" +
                        " `sr_shop_proc_complete` = '" + data.getString("sr_shop_paid_proc_complete") + "'\n" +
                        "WHERE\n" +
                        "\t(\n" +
                        "\t\t`sr_shop_proc_id` = '" + data.getString("sr_shop_proc_id1") + "'\n" +
                        "\t)";
                conn.DB.setData(qu2);
                reciptNo = data.getString("receipt_print_no");
            }


            conn.DB.setData("UPDATE `sr_shop_payment` SET `sr_shop_payment_complete_or_not`= '1' WHERE `sr_receipt_no`='" + reciptNo + "'");
            conn.DB.setData("UPDATE `sr_shop_payment_cross_tbl` SET `sr_shop_payment2_complete_or_not`='1' WHERE `sr_shop_payment2_receipt_no`='" + reciptNo + "'");
            conn.DB.setData("UPDATE `sr_shop_cashflow` SET `sr_shop_cash_flow_complete_or_not`='1' WHERE `sr_shop_cash_flow_receipt_no`='" + reciptNo + "'");
            conn.DB.setData("UPDATE `account_ps_three` SET `report_status`='1'  WHERE `report_ricipt_no`='" + reciptNo + "'");
            conn.DB.setData("UPDATE `receipt` SET `receipt_status`='1' WHERE `receipt_print_no`='" + reciptNo + "'");
            modle.Allert.notificationGood("Completed", "" + idRecipt);
            rady = false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


}
