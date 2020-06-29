package modle.mix;

import conn.DB;
import javafx.geometry.Pos;
import javafx.util.Duration;
import modle.AmounToWord;
import modle.KeyVal;
import modle.Round;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import javax.xml.stream.FactoryConfigurationError;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import modle.AmountToWord.Convert;

public class GenarateRecipt {

    private Connection getConnection() {
        try {
            return ((SessionFactoryImplementor) conn.NewHibernateUtil.getSessionFactory()).getConnectionProvider().getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public void genarateRecipt(int reciptid, boolean print) {

        HashMap param = new HashMap<String, String>();
        System.out.println("i am here");

        int appcat = 0;

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "receipt.recept_applicationId,\n" +
                    "mixincome.idMixincome,\n" +
                    "receipt.idReceipt,\n" +
                    "receipt.Application_Catagory_idApplication_Catagory,\n" +
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
                    "mixincome.mixincome_date,\n" +
                    "mixincome.mixincome_fulltotal,\n" +
                    "mixincome.mixincome_status,\n" +
                    "mixincome.mixincome_year,\n" +
                    "mixincome.customer_idCustomer,\n" +
                    "mixincome.mixincome_userid,\n" +
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
                    "receipt\n" +
                    "INNER JOIN mixincome ON mixincome.idMixincome = receipt.recept_applicationId\n" +
                    "INNER JOIN customer ON customer.idCustomer = mixincome.customer_idCustomer\n" +
                    "WHERE\n" +
                    "receipt.idReceipt =" + reciptid);

            if (data.last()) {
                appcat = data.getInt("Application_Catagory_idApplication_Catagory");
                int appid = data.getInt("recept_applicationId");
                double receipt_total = data.getDouble("receipt_total");
                int idRecipt = data.getInt("idReceipt");

                String cus_address_l1 = data.getString("cus_address_l1");
                String cus_address_l2 = data.getString("cus_address_l2");
                String cus_address_l3 = data.getString("cus_address_l3");


                String cusname = data.getString("cus_name");

                if (cus_address_l1 != null && cus_address_l1.length()>1) {
                    cusname += ", " + cus_address_l1 + "";
                }
                if (cus_address_l2!= null && cus_address_l2.length()>1) {
                    cusname += ", " + cus_address_l2 + "";
                }
                if (cus_address_l3!= null && cus_address_l3.length()>1) {
                    cusname += ", " + cus_address_l3 + ".";
                }

                String receipt_print_no = data.getString("receipt_print_no");
                String receipt_day = data.getString("receipt_day");


                param.put("idReceipt", idRecipt);
                param.put("cusname", cusname);
                param.put("receipt_print_no", receipt_print_no);
                param.put("receipt_day", receipt_day);
                param.put("receipt_total", modle.Round.roundToString(receipt_total));
                String s = Round.roundFormat(modle.Round.round(receipt_total));
                System.out.println(s);

                param.put("word", Convert.convertToWord(receipt_total));

                ResultSet dd = DB.getData("SELECT\n" +
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
                        "mixintype.bankinfo_idBank,\n" +
                        "bank_info.acount_no,\n" +
                        "account_receipt_title.ART_vote_and_bal\n" +
                        "FROM\n" +
                        "mixdata\n" +
                        "INNER JOIN mixintype ON mixdata.mixintype_idMixintype = mixintype.idMixintype\n" +
                        "INNER JOIN bank_info ON bank_info.idBank_Info = mixintype.bankinfo_idBank\n" +
                        "INNER JOIN account_receipt_title ON mixintype.account_receipt_title_idAccount_receipt_title = account_receipt_title.idAccount_receipt_title\n" +
                        "WHERE\n" +
                        "mixdata.mixincome_IdMixincome = " + appid);

                int x = 1;
                double vat = 0;
                double nbt = 0;
                double stamp = 0;

                while (dd.next()) {
                    String dis = "dis" + x;
                    String val = "dis" + x + "val";
                    String vote = "vote" + x;
                    String mixintype_name = dd.getString("mixintype_name") + " " + dd.getString("md_description");
                    double md_amount = dd.getDouble("md_amount");
                    param.put("acno", dd.getString("bank_info.acount_no"));
                    param.put(vote, dd.getString("ART_vote_and_bal"));
                    if (appcat == 9) {
                        param.put(dis, mixintype_name);
                    }


                    param.put(val, modle.Round.roundToString(md_amount));
                    vat += dd.getDouble("md_vat");
                    nbt += dd.getDouble("md_nbt");
                    stamp += dd.getDouble("md_stamp");
                    x++;
                }

                param.put("vat", modle.Round.roundToString(vat));
                param.put("nbt", modle.Round.roundToString(nbt));
                param.put("stamp", modle.Round.roundToString(stamp));

                if (param.get("dis1") == null) {
                    param.put("dis1", "");
                    param.put("dis1val", "");
                    param.put("vote1", "");
                }

                if (param.get("dis2") == null) {
                    param.put("dis2", "");
                    param.put("dis2val", "");
                    param.put("vote2", "");
                }
                if (param.get("dis3") == null) {
                    param.put("dis3", "");
                    param.put("dis3val", "");
                    param.put("vote3", "");
                }
                if (param.get("dis4") == null) {
                    param.put("dis4", "");
                    param.put("dis4val", "");
                    param.put("vote4", "");
                }
            }
            getBook(param, appcat, print);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void getBook(HashMap hm, int appcat, boolean print) {
        try {


            String path = "";// IN SYSTEM


            if (appcat == 9) {
                String assessbilltype = KeyVal.getVal("mix_bill_path");
                path = assessbilltype;// IN SYSTEM
            }
            if (appcat == 11) {
                path = "C:\\Ultimate\\Report\\mix\\vehicle_bill.jrxml";// IN SYSTEM
            }

            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = hm;

            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            if (print) {
                JasperPrintManager.printReport(jp, false);
            } else {
                JasperViewer.viewReport(jp, false);
            }

        } catch (Exception jRException) {
            //       modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "getBook", "modle.assess.AssessReport");
            jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }


    public ArrayList<String> getMixType() {
        ArrayList<String> ar = new ArrayList();
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "mixintype.idMixintype,\n" +
                    "mixintype.mixintype_name,\n" +
                    "mixintype.account_receipt_title_idAccount_receipt_title,\n" +
                    "mixintype.mixintype_status,\n" +
                    "mixintype.bankinfo_idBank,\n" +
                    "mixintype.active_status\n" +
                    "FROM `mixintype`\n" +
                    "WHERE active_status = 1");
            while (data.next()) {
                ar.add(data.getString("mixintype_name"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return ar;
    }


    public void getReport(String from, String to, String type) {

        try {

            String path = "";
            path = "C:\\Ultimate\\Report\\mix\\by_type.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap();
            param.put("from", from);
            param.put("to", to);
            param.put("type", type);

            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            JasperViewer.viewReport(jp, false);

        } catch (Exception jRException) {
            jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }


    }


}
