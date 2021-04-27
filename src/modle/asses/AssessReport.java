package modle.asses;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import conn.DB;
import javafx.geometry.Pos;
import javafx.util.Duration;
import modle.KeyVal;
import modle.Round;
import modle.StaticViews;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRVerticalFiller.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import net.sf.jasperreports.view.JasperViewer;

import org.controlsfx.control.Notifications;
import org.hibernate.engine.spi.SessionFactoryImplementor;

/**
 * @author RM.LasanthaRanga@gmail.com
 */
public class AssessReport {

    private Connection getConnection() {
        try {
            return ((SessionFactoryImplementor) conn.NewHibernateUtil.getSessionFactory()).getConnectionProvider().getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void getKform(String list) {
        try {
            // String path = "C:\\UltimateCat\\Report\\adv_bill.jrxml";
            //  String path = "C:\\Users\\Punnajee\\JaspersoftWorkspace\\MyReports\\ultimate\\kfrom_1.jrxml";
//            String path = "C:\\Ultimate\\Report\\assessment\\kfrom_1.jrxml";// IN SYSTEM
            // String path = "C:\\Ultimate\\Report\\assessment\\kfrom_wattegama.jrxml";// IN SYSTEM

            String path = KeyVal.getVal("KFORM");

            System.out.println(path);

            String kform_type = KeyVal.getVal("KFORM_Type");

            if (2 == Integer.parseInt(kform_type)) {
                kformType2(list);
            } else {
                JasperReport jr = JasperCompileManager.compileReport(path);
                HashMap param = new HashMap<String, Integer>();
                param.put("assList", list);
                JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
                JasperViewer.viewReport(jp, false);
            }


        } catch (Exception jRException) {
            jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }


    public void getWarrantLatter(String list, String date, int year, int quater) {
        try {

            String path = "C:\\Ultimate\\Report\\assessment\\warant_latter_chilaw.jrxml";// IN SYSTEM


            System.out.println(path);


            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("list", list);
            param.put("year", year);
            param.put("day", date);
            param.put("quater", quater);
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

    public void kformType2(String list) {
        String institute_sinhala = "";
        String institute_english = "";
        String institute_tamil = "";
        String act_sinhala = "";
        String act_english = "";
        String act_tamil = "";
        String year = "";
        String des = "";
        String day = "";
        String sub = "";
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "kform_data.idkfom,\n" +
                    "kform_data.`key`,\n" +
                    "kform_data.valueSinhala,\n" +
                    "kform_data.valueEnglish,\n" +
                    "kform_data.valueTemil\n" +
                    "FROM\n" +
                    "kform_data\n");
            ResultSet data1 = DB.getData("SELECT office.office_name FROM  `user` INNER JOIN office ON office.idOffice = `user`.office_idOffice WHERE `user`.idUser = " + StaticViews.getLogUser().getIdUser());
            if (data1.last()) {
                sub = data1.getString("office_name");
            }

            while (data.next()) {
                String key = data.getString("key");

                if (key.equals("institute_name")) {
                    institute_sinhala = data.getString("valueSinhala");
                    institute_english = data.getString("valueEnglish");
                    institute_tamil = data.getString("valueTemil");
                }

                if (key.equals("year")) {
                    year = data.getString("valueEnglish");
                }

                if (key.equals("act")) {
                    act_sinhala = data.getString("valueSinhala");
                    act_english = data.getString("valueEnglish");
                    act_tamil = data.getString("valueTemil");
                }

                if (key.equals("arriars_day")) {
                    des = data.getString("valueSinhala");
                    day = data.getString("valueEnglish");
                    // act_tamil = data.getString("valueTemil");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


        try {
            String path = KeyVal.getVal("KFORM");
            // String path = "C:\\Ultimate\\Report\\test_kform.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("nameSinhala", institute_sinhala);
            param.put("nameEnglish", institute_english);
            param.put("nameTamil", institute_tamil);
            param.put("year", year);
            param.put("act_s", act_sinhala);
            param.put("act_e", act_english);
            param.put("act_t", act_tamil);
            param.put("assList", list);
            param.put("des", des);
            param.put("day", day);
            param.put("sub", sub);


            JasperPrint jp = JasperFillManager.fillReport(jr, param, getConnection());
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


    public void getBook(String list) {
        try {
            String path = "C:\\Ultimate\\Report\\assessment\\book.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("assList", list);
            param.put("year", modle.GetInstans.getQuater().getCurrentYear() + "");
            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            JasperViewer.viewReport(jp, false);

        } catch (Exception jRException) {
            modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "getBook", "modle.assess.AssessReport");
            jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }

    public void getReciptPrint(String id, double tyw, double tya) {//one
        try {

            String path = "C:\\Ultimate\\Report\\assessment/mbil.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("pid", id);
            param.put("tyw", tyw);
            param.put("tya", tya);
            this.getConnection().commit();
            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            JasperViewer.viewReport(jp, false);

        } catch (Exception jRException) {
            jRException.printStackTrace();
            modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "Ricipt", "modle.assess.AssessReport");
            //./ jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }

    public void getReciptPrintEdited1(String id, double tyw, double tya, boolean print, String subs, String word) {//one
        try {

            String path = "C:\\Ultimate\\Report\\assessment/mbilEdited.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("pid", id);
            param.put("tyw", tyw);
            param.put("tya", tya);
            param.put("subs", subs);
            param.put("ward", word);
            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            if (print) {
                JasperPrintManager.printReport(jp, false);
            } else {
                JasperViewer.viewReport(jp, false);
            }

        } catch (Exception jRException) {
            jRException.printStackTrace();
            modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "Ricipt", "modle.assess.AssessReport");
            //./ jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }


    public void getReciptPrintPlane(String id, double tyw, double tya, boolean print, String subs, String word) {//one
        try {

            String ass_bill_path = KeyVal.getVal("ass_bill_path");

            String path = ass_bill_path;// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("pid", id);
            param.put("tyw", tyw);
            param.put("tya", tya);
            param.put("subs", subs);
            param.put("ward", word);

            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            if (print) {
                JasperPrintManager.printReport(jp, false);
            } else {
                JasperViewer.viewReport(jp, false);
            }

        } catch (Exception jRException) {
            jRException.printStackTrace();
            modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "Ricipt", "modle.assess.AssessReport");
            //./ jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }


    public void getReciptView(String id, double tyw, double tya, boolean print, String subs, String word) {//two
        try {
            String path = "C:\\Ultimate\\Report\\assessment/mbil.jrxml";// IN SYSTEM
            //  String path="C:\\Users\\Ranga\\JaspersoftWorkspace\\MyReports\\Assessment\\assbill.jrxml" ;


            JasperReport jr = JasperCompileManager.compileReport(path);

            HashMap param = new HashMap<String, Integer>();
            param.put("pid", id);
            param.put("tyw", tyw);
            param.put("tya", tya);
            param.put("subs", subs);
            param.put("ward", word);
            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());


            if (print) {
                JasperPrintManager.printReport(jp, false);
            } else {
                JasperViewer.viewReport(jp, false);
            }


        } catch (Exception jRException) {
            modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "Ricipt", "modle.assess.AssessReport");
            jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }


    public void getDayEndShedule(String day) {
        try {
            String path = "C:\\Ultimate\\Report\\assessment\\dayend_1.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("day", day);
            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            //          JasperPrintManager.printReport(jp, false);
            JasperViewer.viewReport(jp, false);

        } catch (Exception jRException) {
            modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "Ricipt", "modle.assess.AssessReport");
            jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }

    public void getDayEndSheduleByOffice(String day, int idOffice) {
        try {
            String path = "C:\\Ultimate\\Report\\assessment\\dayend_byOffice.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("day", day);
            param.put("idOffice", idOffice);
            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            //          JasperPrintManager.printReport(jp, false);
            JasperViewer.viewReport(jp, false);

        } catch (Exception jRException) {
            modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "Ricipt", "modle.assess.AssessReport");
            jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }

    public void longBill(String pid, boolean print, String subs, String word) {

        System.out.println(pid);

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "receipt.receipt_print_no,\n" +
                    "receipt.receipt_day,\n" +
                    "customer.cus_name,\n" +
                    "assessment.assessment_no,\n" +
                    "ass_payment.ass_Payment_LY_Arrears,\n" +
                    "ass_payment.ass_Payment_LY_Warrant,\n" +
                    "ass_payment.ass_Payment_fullTotal,\n" +
                    "ass_payment.ass_Payment_idUser,\n" +
                    "ass_payment.ass_cash,\n" +
                    "ass_payment.ass_check_no,\n" +
                    "ass_payment.ass_bank,\n" +
                    "bank.bank_name,\n" +
                    "ass_payment.cd_balance,\n" +
                    "street.street_name,\n" +
                    "ward.ward_name,\n" +
                    "ass_payment.ass_Payment_goto_debit, receipt.receipt_total" +
                    " FROM\n" +
                    "receipt\n" +
                    "INNER JOIN ass_payment ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                    "INNER JOIN assessment ON ass_payment.Assessment_idAssessment = assessment.idAssessment\n" +
                    "INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n" +
                    "LEFT JOIN bank ON bank.idBank = ass_payment.ass_bank\n" +
                    "INNER JOIN street ON assessment.Street_idStreet = street.idStreet\n" +
                    "INNER JOIN ward ON assessment.Ward_idWard = ward.idWard AND street.Ward_idWard = ward.idWard\n" +
                    "WHERE\n" +
                    "ass_payment.idass_Payment =" + pid);


            ResultSet payto = DB.getData("SELECT\n" +
                    "ass_payto.idass_payto,\n" +
                    "ass_payto.ass_payto_Qno,\n" +
                    "ass_payto.ass_payto_warrant,\n" +
                    "ass_payto.ass_payto_arrears,\n" +
                    "ass_payto.ass_payto_qvalue,\n" +
                    "ass_payto.ass_payto_discount,\n" +
                    "ass_payto.ass_payto_discount_rate,\n" +
                    "ass_payto.ass_Payment_idass_Payment,\n" +
                    "ass_payto.ass_payto_status\n" +
                    "FROM\n" +
                    "ass_payto\n" +
                    "WHERE\n" +
                    "ass_payto.ass_Payment_idass_Payment =" + pid);


            double arrias = 0;
            double warant = 0;
            double qptot = 0;
            double fullpay = 0;
            double cd = 0;
            double overnext = 0;
            double discount = 0;
            double overquater = 0;
            String chequeno = "";
            String ricitno = "";
            String day = "";
            String asno = "";
            String cus_name = "";
            String des = "";
            System.out.println("thama Data ne");
            if (data.last()) {
                System.out.println("data thiyanawa");
                ricitno = data.getString("receipt_print_no");
                day = data.getString("receipt_day");
                asno = data.getString("ward_name") + " | " + data.getString("street_name") + " | " + data.getString("assessment_no");
                cus_name = data.getString("cus_name");
                fullpay = data.getDouble("receipt.receipt_total");
                cd = data.getDouble("cd_balance");
                overnext = data.getDouble("ass_Payment_goto_debit");
                warant += data.getDouble("ass_Payment_LY_Warrant");
                arrias += data.getDouble("ass_Payment_LY_Arrears");
                chequeno = data.getString("ass_check_no");
                if (chequeno.length() > 0) {
                    chequeno += " | " + data.getString("bank_name");
                } else {
                    chequeno = "";
                }

                System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
                System.out.println(arrias);
                System.out.println(warant);

                while (payto.next()) {
                    int paystatus = payto.getInt("ass_payto_status");
                    arrias += payto.getDouble("ass_payto_arrears");
                    warant += payto.getDouble("ass_payto_warrant");

                    if (paystatus == 1) {
                        qptot += payto.getDouble("ass_payto_qvalue");
                        des += " Q" + payto.getString("ass_payto_Qno") + ",  ";


                    } else {
                        overquater += payto.getDouble("ass_payto_qvalue");
                    }
                    discount += payto.getDouble("ass_payto_discount");
                }

                System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
                System.out.println(arrias);
                System.out.println(warant);


                // fullpay += cd + overnext;

            }

            System.out.println("cal hari");
            String path = "C:\\Ultimate\\Report\\assessment\\longbil.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();

            param.put("pid", pid + "");
            param.put("ricitno", ricitno);
            param.put("day", day);
            param.put("asno", asno);
            param.put("cus_name", cus_name);
            param.put("arrias", modle.Round.roundToString(arrias));
            param.put("warant", modle.Round.roundToString(warant));
            param.put("qptot", modle.Round.roundToString(qptot));
            param.put("qptot", modle.Round.roundToString(qptot));
            param.put("subs", subs);
            param.put("ward", word);

            if (cd < 0) {
                param.put("cd", modle.Round.roundToString(0));
            } else {
                param.put("cd", modle.Round.roundToString(cd));
            }


            param.put("overnext", modle.Round.roundToString(overnext + overquater));

            param.put("fullpay", modle.Round.roundToString(fullpay));

            param.put("des", des);

            param.put("discount", modle.Round.roundToString(discount));

            param.put("chequeno", chequeno);


            System.out.println("Full Pay = " + fullpay);
            System.out.println("OVER Pay = " + (overnext + overquater));


            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());

            if (print) {
                JasperPrintManager.printReport(jp, false);
            } else {
                JasperViewer.viewReport(jp, false);
            }

        } catch (Exception jRException) {
            jRException.printStackTrace();
            modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "Ricipt", "modle.assess.AssessReport");
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }

    }


    public void longBillPrint(String pid) {

        System.out.println(pid);

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "\treceipt.receipt_print_no,\n" +
                    "\treceipt.receipt_day,\n" +
                    "\tcustomer.cus_name,\n" +
                    "\tassessment.assessment_no,\n" +
                    "\tass_payment.ass_Payment_LY_Arrears,\n" +
                    "\tass_payment.ass_Payment_LY_Warrant,\n" +
                    "\tass_payment.ass_Payment_fullTotal,\n" +
                    "\tass_payment.ass_Payment_idUser,\n" +
                    "\tass_payment.ass_cash,\n" +
                    "\tass_payment.ass_check_no,\n" +
                    "\tass_payment.ass_bank,\n" +
                    "\tbank.bank_name,\n" +
                    "\tass_payment.cd_balance,\n" +
                    "\tstreet.street_name,\n" +
                    "\tward.ward_name,\n" +
                    "\tass_payment.ass_Payment_goto_debit\n" +
                    "FROM\n" +
                    "\treceipt\n" +
                    "LEFT JOIN ass_payment ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                    "LEFT JOIN assessment ON ass_payment.Assessment_idAssessment = assessment.idAssessment\n" +
                    "LEFT JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n" +
                    "LEFT JOIN bank ON bank.idBank = ass_payment.ass_bank\n" +
                    "LEFT JOIN street ON assessment.Street_idStreet = street.idStreet\n" +
                    "LEFT JOIN ward ON assessment.Ward_idWard = ward.idWard\n" +
                    "AND street.Ward_idWard = ward.idWard\n" +
                    "WHERE\n" +
                    "\tass_payment.idass_Payment =" + pid);

            ResultSet payto = DB.getData("SELECT\n" +
                    "ass_payto.idass_payto,\n" +
                    "ass_payto.ass_payto_Qno,\n" +
                    "ass_payto.ass_payto_warrant,\n" +
                    "ass_payto.ass_payto_arrears,\n" +
                    "ass_payto.ass_payto_qvalue,\n" +
                    "ass_payto.ass_payto_discount,\n" +
                    "ass_payto.ass_payto_discount_rate,\n" +
                    "ass_payto.ass_Payment_idass_Payment,\n" +
                    "ass_payto.ass_payto_status\n" +
                    "FROM\n" +
                    "ass_payto\n" +
                    "WHERE\n" +
                    "ass_payto.ass_Payment_idass_Payment =" + pid);
            double arrias = 0;
            double warant = 0;
            double qptot = 0;
            double fullpay = 0;
            double cd = 0;
            double overnext = 0;
            double discount = 0;
            double overquater = 0;
            String chequeno = "";
            String ricitno = "";
            String day = "";
            String asno = "";
            String cus_name = "";
            String des = "";

            System.out.println("THama Data ne");


            if (data.last()) {
                System.out.println("Data thiyanawa");
                ricitno = data.getString("receipt_print_no");
                day = data.getString("receipt_day");
                asno = data.getString("ward_name") + "|" + data.getString("street_name") + "|" + data.getString("assessment_no");
                cus_name = data.getString("cus_name");
                fullpay = data.getDouble("ass_Payment_fullTotal");
                cd = data.getDouble("cd_balance");
                overnext = data.getDouble("ass_Payment_goto_debit");
                warant += data.getDouble("ass_Payment_LY_Warrant");
                arrias += data.getDouble("ass_Payment_LY_Arrears");
                chequeno = data.getString("ass_check_no");
                if (chequeno.length() > 0) {
                    chequeno += " | " + data.getString("bank_name");
                } else {
                    chequeno = "";
                }
                System.out.println("Calqulation hari");


                while (payto.next()) {
                    int paystatus = payto.getInt("ass_payto_status");
                    arrias += payto.getDouble("ass_payto_arrears");
                    warant += payto.getDouble("ass_payto_warrant");
                    if (paystatus == 1) {
                        qptot += payto.getDouble("ass_payto_qvalue");
                        des += " Q" + payto.getString("ass_payto_Qno") + ",  ";

                    } else {
                        overquater += payto.getDouble("ass_payto_qvalue");
                    }
                    discount += payto.getDouble("ass_payto_discount");
                }

                System.out.println("methana cal hari");
                fullpay += cd + overnext;

            }


            String path = "C:\\Ultimate\\Report\\assessment\\longbil.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("pid", pid + "");
            param.put("ricitno", ricitno);
            param.put("day", day);
            param.put("asno", asno);
            param.put("cus_name", cus_name);
            param.put("arrias", modle.Round.roundToString(arrias));
            param.put("warant", modle.Round.roundToString(warant));
            param.put("qptot", modle.Round.roundToString(qptot));
            param.put("qptot", modle.Round.roundToString(qptot));
            param.put("cd", modle.Round.roundToString(cd));
            param.put("overnext", modle.Round.roundToString(overnext + overquater));
            param.put("fullpay", modle.Round.roundToString(fullpay));
            param.put("des", des);
            param.put("discount", modle.Round.roundToString(discount));
            param.put("chequeno", chequeno);


            System.out.println("Full Pay = " + fullpay);
            System.out.println("OVER Pay = " + (overnext + overquater));


            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            JasperPrintManager.printReport(jp, false);
            //  JasperViewer.viewReport(jp, false);

        } catch (Exception jRException) {
            jRException.printStackTrace();
            modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "Ricipt", "modle.assess.AssessReport");
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }

    }

    public void getReciptPrintStrretLine(String slid, boolean print) {
        try {

            String path = "C:\\Ultimate\\Report\\streetline\\strretline.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("reciptid", slid);
            System.out.println(slid);
            this.getConnection().commit();
            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            if (print) {
                JasperPrintManager.printReport(jp, false);
            } else {
                JasperViewer.viewReport(jp, false);
            }

        } catch (Exception jRException) {
            jRException.printStackTrace();
            modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "Ricipt", "modle.assess.AssessReport");
            //./ jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }


    public void getReciptPrintBOP(String slid, boolean print) {
        try {

            String bop_recipt_path = KeyVal.getVal("BOP_recipt_path");

          //  String path = "C:\\Ultimate\\Report\\bop\\Blank_A4.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(bop_recipt_path);
            HashMap param = new HashMap<String, Integer>();
            param.put("id", slid);
            System.out.println(slid);
            this.getConnection().commit();
            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            if (print) {
                JasperPrintManager.printReport(jp, false);
            } else {
                JasperViewer.viewReport(jp, false);
            }

        } catch (Exception jRException) {
            jRException.printStackTrace();
            modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "Ricipt", "modle.assess.AssessReport");
            //./ jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }


    public void PrintTradeLicens(String slid, boolean print) {
        try {

            String path = "C:\\Ultimate\\Report\\TL\\trade_licens_new.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("reciptid", slid);
            System.out.println(slid);
            this.getConnection().commit();
            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            if (print) {
                JasperPrintManager.printReport(jp, false);
            } else {
                JasperViewer.viewReport(jp, false);
            }

        } catch (Exception jRException) {
            jRException.printStackTrace();
            modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "Ricipt", "modle.assess.AssessReport");
            //./ jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }

    public void getReciptPrintNonVesting(String slid, boolean print) {
        try {

            String path = "C:\\Ultimate\\Report\\streetline\\nonvesting.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("reciptid", slid);
            System.out.println(slid);
            this.getConnection().commit();
            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            if (print) {
                JasperPrintManager.printReport(jp, false);
            } else {
                JasperViewer.viewReport(jp, false);
            }

        } catch (Exception jRException) {
            jRException.printStackTrace();
            modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "Ricipt", "modle.assess.AssessReport");
            //./ jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }


    public void getReciptPrintEnv(String reciptId, String description, boolean print) {
        try {
            String path = "C:\\Ultimate\\Report\\environment\\env1_kuliuc.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("reciptid", reciptId);
            param.put("description", description);
            System.out.println(reciptId);
            this.getConnection().commit();
            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            if (print) {
                JasperPrintManager.printReport(jp, false);
            } else {
                JasperViewer.viewReport(jp, false);
            }

        } catch (Exception jRException) {
            jRException.printStackTrace();
            modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "Ricipt", "modle.assess.AssessReport");
            //./ jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }


    public void getArriasReport1(ArrayList<RipHolder> holders) {
        try {

            String path = "C:\\Users\\Ranga\\JaspersoftWorkspace\\MyReports\\Assessment\\arriars_report.jrxml";// IN SYSTEM

            JasperReport jr = JasperCompileManager.compileReport(path);

            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(holders);

            HashMap param = new HashMap<String, Object>();

            param.put("Lists", jrBeanCollectionDataSource);

            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());

            JasperViewer.viewReport(jp, false);

        } catch (Exception jRException) {
            jRException.printStackTrace();
            modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "Ricipt", "modle.assess.AssessReport");
            //./ jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }


    public void getArriasList(String list) {
        try {
            // String path = "C:\\UltimateCat\\Report\\adv_bill.jrxml";
            //  String path = "C:\\Users\\Punnajee\\JaspersoftWorkspace\\MyReports\\ultimate\\kfrom_1.jrxml";
            String path = "C:\\Ultimate\\Report\\assessment\\LYAW.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("idList", list);
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


    public void getArriasPaidList(String list) {
        try {
            // String path = "C:\\UltimateCat\\Report\\adv_bill.jrxml";
            //  String path = "C:\\Users\\Punnajee\\JaspersoftWorkspace\\MyReports\\ultimate\\kfrom_1.jrxml";
            String path = "C:\\Ultimate\\Report\\assessment\\HigaLebeem.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("idList", list);
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


    public void getVehiclepassReport(String from, String to) {
        try {
            String path = "C:\\Ultimate\\Report\\assessment\\vpass.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, String>();
            param.put("from", from);
            param.put("to", to);
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

    public void getRiShedule(String from, String to, String username) {
        try {
            String path = "C:\\Ultimate\\Report\\assessment\\rishedule.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, String>();
            param.put("from", from);
            param.put("to", to);
            param.put("username", username);
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

    public void getRiShedulePending(String from, String to, String username) {
        try {
            String path = "C:\\Ultimate\\Report\\assessment\\rishedule_pending.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, String>();
            param.put("from", from);
            param.put("to", to);
            param.put("username", username);
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


    public static void loadReportByDataSource(ArrayList<RipHolder> list, String path) {

        try {


//            String path = current_arriars_report_path;// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, String>();

            JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);

            JasperPrint jp = JasperFillManager.fillReport(jr, param, jrDataSource);
            JasperViewer.viewReport(jp, false);

        } catch (Exception jRException) {
            jRException.printStackTrace();
        }


    }

    public static void loadReportAWPayByDatasourc(ArrayList<RipHolder> list, String from, String to) {

        try {

            String path = "C:\\Ultimate\\Report\\assessment\\AWPay.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, String>();
            param.put("from", from);
            param.put("to", to);

            JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);

            JasperPrint jp = JasperFillManager.fillReport(jr, param, jrDataSource);
            JasperViewer.viewReport(jp, false);

        } catch (Exception jRException) {
            jRException.printStackTrace();
        }


    }

    public static void loadReportAWPayByDatasourcAndUser(ArrayList<RipHolder> list, String from, String to, String ri_name) {

        try {

            String path = "C:\\Ultimate\\Report\\assessment\\AWPayByUser.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, String>();
            param.put("from", from);
            param.put("to", to);
            param.put("ri_name", ri_name);

            JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);

            JasperPrint jp = JasperFillManager.fillReport(jr, param, jrDataSource);
            JasperViewer.viewReport(jp, false);

        } catch (Exception jRException) {
            jRException.printStackTrace();
        }


    }

    public void RiBill(String idRibill, String billnos, String chequeno, boolean print) {
        try {
            String path = KeyVal.getVal("RI_bill_path");
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, String>();
            param.put("idRibill", idRibill);
            param.put("billnos", billnos);
            param.put("chequeno", chequeno);

            Connection connection = this.getConnection();
            connection.commit();

            JasperPrint jp = JasperFillManager.fillReport(jr, param, connection);
            if (print) {
                JasperPrintManager.printReport(jp, false);
            } else {
                JasperViewer.viewReport(jp, false);
            }

        } catch (Exception jRException) {
            jRException.printStackTrace();
        }


    }

    public void cdList(String from, String to) {
        try {
            String path = "C:\\Ultimate\\Report\\assessment\\cd_report.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, String>();
            param.put("from", from);
            param.put("to", to);
            Connection connection = this.getConnection();
            connection.commit();
            JasperPrint jp = JasperFillManager.fillReport(jr, param, connection);
            JasperViewer.viewReport(jp, false);
        } catch (Exception jRException) {
            jRException.printStackTrace();
        }
    }


    public void ownership(int id, String myno, String youno,
                          String address, String persontitle,
                          String title,
                          String ward, String street, String assessment,
                          String later1, String date2) {
        try {
            String path = "C:\\Ultimate\\Report\\assessment\\OwnerRegister.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, String>();
            param.put("id", id);
            param.put("myno", myno);
            param.put("youno", youno);
            param.put("address", address);
            param.put("persontitle", persontitle);
            param.put("title", title);
            param.put("ward", ward);
            param.put("street", street);
            param.put("assessment", assessment);
            param.put("later1", later1);
            param.put("date2", date2);


            Connection connection = this.getConnection();
            connection.commit();
            JasperPrint jp = JasperFillManager.fillReport(jr, param, connection);
            JasperViewer.viewReport(jp, false);
        } catch (Exception jRException) {
            jRException.printStackTrace();
            jRException.printStackTrace();
        }
    }


    public void mobileAssessTotalBill(String id) {
        try {
            String path = "C:\\Ultimate\\Report\\mobile\\mobile_total.jrxml";// IN SYSTEM


            ResultSet data = DB.getData("SELECT\n" +
                    "mobile_pay.idMobilePay,\n" +
                    "mobile_pay.app_id,\n" +
                    "mobile_pay.amount,\n" +
                    "mobile_pay.pay_type,\n" +
                    "mobile_pay.cheque_no,\n" +
                    "mobile_pay.bank_id,\n" +
                    "mobile_pay.oder,\n" +
                    "mobile_pay.mobile_recipt_no,\n" +
                    "mobile_pay.recipt_id,\n" +
                    "mobile_pay.recipt_no,\n" +
                    "mobile_pay.mob_tot_id,\n" +
                    "mobile_pay.user_id\n" +
                    "FROM\n" +
                    "mobile_pay\n" +
                    "WHERE\n" +
                    "mobile_pay.mob_tot_id = " + id);

            String details = "";

            while (data.next()) {

                String rn = data.getString("mobile_recipt_no");
                String amount = Round.roundFormat(data.getDouble("amount"));

                details += rn + " = " + amount + ",  ";

            }

            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, String>();

            param.put("bills", details);
            param.put("id", id);

            Connection connection = this.getConnection();
            connection.commit();
            JasperPrint jp = JasperFillManager.fillReport(jr, param, connection);
            JasperViewer.viewReport(jp, false);


        } catch (Exception jRException) {
            jRException.printStackTrace();
        }
    }



    public void buildingApplicationPrint(String id, boolean print) {
        System.out.println("printing building");
        try {
            String path = "C:\\Ultimate\\Report\\building\\building.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("id", id);
            this.getConnection().commit();
            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            if (print) {
                JasperPrintManager.printReport(jp, false);
            } else {
                JasperViewer.viewReport(jp, false);
            }

        } catch (Exception jRException) {
            jRException.printStackTrace();
            modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "Ricipt", "modle.assess.AssessReport");
            //./ jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }





}
