package modle.asses;

import conn.DB;
import controller.assess.DayendController;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import modle.BUP;
import modle.GetInstans;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.*;
import sun.java2d.pipe.SpanShapeRenderer;

public class DayEndProcess {

    HashMap<String, Integer> vids = null;


    public void pras3process(String text) {
        System.out.println(" PS3 Running =============");

        int currentYear = GetInstans.getQuater().getCurrentYear();

        getVoteAndID();
        String quer = "SELECT\n" +
                "ass_payment.idass_Payment,\n" +
                "ass_payment.ass_Payment_Q_Number,\n" +
                "ass_payment.ass_Payment_ThisYear,\n" +
                "ass_payment.ass_Payment_date,\n" +
                "ass_payment.ass_Payment_LY_Arrears,\n" +
                "ass_payment.ass_Payment_LY_Warrant,\n" +
                "ass_payment.ass_Payment_fullTotal,\n" +
                "ass_payment.ass_Payment_idUser,\n" +
                "ass_payment.Assessment_idAssessment,\n" +
                "ass_payment.Receipt_idReceipt,\n" +
                "ass_payment.ass_nature_idass_nature,\n" +
                "ass_payment.ass_allocation_idass_allocation,\n" +
                "ass_payment.ass_Payment_goto_debit,\n" +
                "ass_payment.ass_Payment_Status,\n" +
                "ass_payment.ass_cash,\n" +
                "ass_payment.ass_check,\n" +
                "ass_payment.ass_check_no,\n" +
                "ass_payment.ass_bank,\n" +
                "ass_payment.pay_user_id,\n" +
                "ass_payment.cd_balance,\n" +
                "ass_payment.office_idOffice,\n" +
                "receipt.idReceipt,\n" +
                "receipt.Application_Catagory_idApplication_Catagory,\n" +
                "receipt.recept_applicationId,\n" +
                "receipt.receipt_print_no,\n" +
                "receipt.cheack,\n" +
                "receipt.cesh,\n" +
                "receipt.receipt_total,\n" +
                "receipt.receipt_day,\n" +
                "receipt.receipt_status,\n" +
                "receipt.amount,\n" +
                "receipt.pay_type,\n" +
                "receipt.receipt_syn,\n" +
                "receipt.chque_no,\n" +
                "receipt.chque_date,\n" +
                "receipt.chque_bank,\n" +
                "receipt.oder,\n" +
                "receipt.office_idOffice\n" +
                "FROM\n" +
                "ass_payment\n" +
                "INNER JOIN receipt ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                "WHERE\n" +
                "receipt.idReceipt = " + text + " ";

        try {
            ResultSet data = DB.getData(quer);
            System.out.println(" PS3 Running =============  4");
            if (data.last()) {
                System.out.println(" PS3 Running =============  5");
                String recitno = data.getString("receipt_print_no");
                String day = data.getString("receipt_day");
                int office_idOffice = data.getInt("office_idOffice");
                int ass_payment_idUser = data.getInt("ass_Payment_idUser");
                int recept_applicationId = data.getInt("recept_applicationId");
                int pay_type = data.getInt("pay_type");
                double amount = data.getDouble("amount");
                int assessment_idAssessment = data.getInt("Assessment_idAssessment");

                System.out.println(" PS3 Running =============  6");

                double ass_payment_ly_arrears = data.getDouble("ass_Payment_LY_Arrears");
                double ass_payment_ly_warrant = data.getDouble("ass_Payment_LY_Warrant");
                double ass_cash = data.getDouble("ass_cash");
                double ass_check = data.getDouble("ass_check");
                double cd_balance = data.getDouble("cd_balance");
                double ass_payment_goto_debit = data.getDouble("ass_Payment_goto_debit");

                double ass_payment_thisYear = data.getDouble("ass_Payment_ThisYear"); // meka athyawassya ne

                int idass_payment = data.getInt("idass_Payment");

                ResultSet data1 = DB.getData("SELECT\n" +
                        "aha.idAHA,\n" +
                        "aha.appcat_id,\n" +
                        "aha.bankinfo_id,\n" +
                        "aha.office_id,\n" +
                        "aha.aha_status\n" +
                        "FROM `aha`\n" +
                        "WHERE\n" +
                        "aha.appcat_id = 2 AND\n" +
                        "aha.office_id = " + office_idOffice);

                System.out.println(" PS3 Running =============  7");

                int ACCOUNTID = 0;
                if (data1.last()) {
                    ACCOUNTID = data1.getInt("bankinfo_id");

                    System.out.println(" PS3 Running =============  8");

                }


                String q2 = "SELECT\n" +
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
                        "ass_payto.ass_Payment_idass_Payment = " + idass_payment;
                ResultSet d = DB.getData(q2);

                System.out.println(" PS3 Running =============  8");

                double typ = 0.0;
                double dis = 0.0;
                double tya = 0.0;
                double tyw = 0.0;

                while (d.next()) {
                    typ += d.getDouble("ass_payto_qvalue");
                    dis += d.getDouble("ass_payto_discount");
                    tya += d.getDouble("ass_payto_arrears");
                    tyw += d.getDouble("ass_payto_warrant");

                    System.out.println(" PS3 Running =============  9");
                }


                if (ass_payment_ly_arrears > 0) {// Last Year Arrias
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("LYA"), ACCOUNTID, modle.Round.round(ass_payment_ly_arrears), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                }

                if (ass_payment_ly_warrant > 0) {// Last Year Warrant
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("LYW"), ACCOUNTID, modle.Round.round(ass_payment_ly_warrant), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                }

                if (tya > 0) {// This Year arrias
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("TYA"), ACCOUNTID, modle.Round.round(tya), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                }

                if (tyw > 0) {// this year warrnt
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("TYW"), ACCOUNTID, modle.Round.round(tyw), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                }

                if (cd_balance < 0) {

                    System.out.println("PS3 ================= from last year");


                    conn.DB.setData("UPDATE ass_qstart \n" +
                            "SET ass_qstart.process_update_arrears = 0,\n" +
                            "ass_qstart.process_update_warant = " + cd_balance + " \n" +
                            "WHERE\n" +
                            "\tass_qstart.Assessment_idAssessment = " + assessment_idAssessment + " \n" +
                            "\tAND ass_qstart.ass_Qstart_QuaterNumber = 1 \n" +
                            "\tAND ass_Qstart_year = " + currentYear);


                    if (typ > 0) {// quater pay
                        insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("QP"), ACCOUNTID, modle.Round.round(typ + cd_balance), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                    }


                } else {
                    System.out.println("PS3 ================= No Balance");
                    if (typ > 0) {// quater pay
                        insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("QP"), ACCOUNTID, modle.Round.round(typ), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                    }
                    // if (cd_balance > 0) {// credit debit balance
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("CD"), ACCOUNTID, modle.Round.round(cd_balance), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                    // }
                }

                if (dis > 0) {// discount
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("D"), ACCOUNTID, modle.Round.round(dis), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                }

                if (ass_payment_goto_debit > 0) {// over pay to next year
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("OP"), ACCOUNTID, modle.Round.round(ass_payment_goto_debit), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                }

                if (ass_cash > 0) {// Cahs
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("CASH"), ACCOUNTID, modle.Round.round(ass_cash), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                }

                if (ass_check > 0) {// chque
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("CHQUE"), ACCOUNTID, modle.Round.round(ass_check), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                }

                if (pay_type == 5) {// chque
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("ON"), ACCOUNTID, modle.Round.round(amount), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


    public void getVoteAndID() {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_vote.idass_vote,\n" +
                    "ass_vote.ass_vote_no,\n" +
                    "ass_vote.ass_vote_table_id,\n" +
                    "ass_vote.description,\n" +
                    "ass_vote.`key`\n" +
                    "FROM `ass_vote`\n");

            vids = new HashMap<>();
            while (data.next()) {
                vids.put(data.getString("key"), data.getInt("ass_vote_table_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    public void insertToPrasa3(String day, String reciptNo, int idRecipt, int vortid, int accountid, double amount, int userid, int appid, int appcatid, int status, int officeid) {

        String query = "INSERT INTO `account_ps_three` (\t\n" +
                "\t`report_date`,\n" +
                "\t`report_ricipt_no`,\n" +
                "\t`report_ricipt_id`,\n" +
                "\t`report_vort_id`,\n" +
                "\t`report_account_id`,\n" +
                "\t`report_amount`,\n" +
                "\t`report_user_id`,\n" +
                "\t`report_application_id`,\n" +
                "\t`report_application_cat_id`,\n" +
                "\t`report_status`,\n" +
                "\t`office_idOffice`, income_or_expence\n" +
                ")\n" +
                "VALUES\n" +
                "\t(\t\t\n" +
                "\t\t'" + day + "',\n" +
                "\t\t'" + reciptNo + "',\n" +
                "\t\t'" + idRecipt + "',\n" +
                "\t\t'" + vortid + "',\n" +
                "\t\t'" + accountid + "',\n" +
                "\t\t'" + amount + "',\n" +
                "\t\t'" + userid + "',\n" +
                "\t\t'" + appid + "',\n" +
                "\t\t'" + appcatid + "',\n" +
                "\t\t'" + status + "',\n" +
                "\t\t'" + officeid + "', '1'\n" +
                "\t)";

        try {
            conn.DB.setData(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


    public boolean dayEndProcessForOne(int idPay, Date day) {

        //  backup("Befor_Day_End_Process ekata awa");
        int q1s = 0;
        int q2s = 0;
        int q3s = 0;
        int q4s = 0;

        Date systemDate = day;
        int systemYear = modle.GetInstans.getQuater().getCurrentYear();
        int cquater = modle.GetInstans.getQuater().getCurrentQuater();

        if (cquater == 1) {
            q1s = 0;
            q2s = 0;
            q3s = 0;
            q4s = 0;
        }
        if (cquater == 2) {
            q1s = 1;
            q2s = 0;
            q3s = 0;
            q4s = 0;
        }
        if (cquater == 3) {
            q1s = 1;
            q2s = 1;
            q3s = 0;
            q4s = 0;
        }
        if (cquater == 4) {
            q1s = 1;
            q2s = 1;
            q3s = 1;
            q4s = 0;
        }

        System.out.println("Bifor ============================");
        System.out.println("Q1 ==== " + q1s);
        System.out.println("Q1 ==== " + q2s);
        System.out.println("Q1 ==== " + q3s);
        System.out.println("Q1 ==== " + q4s);

        double q1pay = 0;
        double q2pay = 0;
        double q3pay = 0;
        double q4pay = 0;

        int oq1s = 0;
        int oq2s = 0;
        int oq3s = 0;
        int oq4s = 0;

        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        System.out.println(1 + " ================");
        try {

            Receipt re = (Receipt) session.load(Receipt.class, idPay);

            Integer idReceipt = re.getIdReceipt();


            re.setReceiptStatus(1);
            session.update(re);


            System.out.println("---------------");
            System.out.println(systemDate);
            String format = new SimpleDateFormat("yyyy-MM-dd").format(systemDate);
            System.out.println(format + "    format");

            System.out.println(re.getReceiptDay());
            System.out.println("---------------");

            Criteria cry = session.createCriteria(AssPayment.class);
            cry.add(Restrictions.eq("assPaymentStatus", 0));
            cry.add(Restrictions.eq("receipt", re));
            List<AssPayment> list = cry.list();

            System.out.println(2 + " ================");

            System.out.println(list.size() + "  ===================== size");

            for (AssPayment assPayment : list) {
                System.out.println(3 + " ================");
                boolean q1 = false;
                boolean q2 = false;
                boolean q3 = false;
                boolean q4 = false;

                Assessment assessment = assPayment.getAssessment();

                Criteria payHisCry = session.createCriteria(AssPayhistry.class);


                payHisCry.add(Restrictions.eq("assPayHistryYear", systemYear));
                payHisCry.add(Restrictions.eq("assPayHistryStatus", 1));
                payHisCry.add(Restrictions.eq("assessment", assessment));
                List<AssPayhistry> oldPayHistry = payHisCry.list();

                AssPayhistry aphold = null;

                System.out.println(4 + " ================");

                int qsp1 = q1s;
                int qsp2 = q2s;
                int qsp3 = q3s;
                int qsp4 = q4s;


                for (AssPayhistry assPayhistry : oldPayHistry) {
                    aphold = assPayhistry;
                    if (qsp1 == 0) {
                        if (assPayhistry.getAssPayHistryQ1status() == 1) {
                            qsp1 = 1;
                        }
                    }
                    if (qsp2 == 0) {
                        if (assPayhistry.getAssPayHistryQ2status() == 1) {
                            qsp2 = 1;
                        }
                    }
                    if (qsp3 == 0) {
                        if (assPayhistry.getAssPayHistryQ3status() == 1) {
                            qsp3 = 1;
                        }
                    }
                    if (qsp4 == 0) {
                        if (assPayhistry.getAssPayHistryQ4status() == 1) {
                            qsp4 = 1;
                        }
                    }
                }


                if (aphold != null) {

                    System.out.println(6 + " ================");

                    oq1s = aphold.getAssPayHistryQ1status();
                    oq2s = aphold.getAssPayHistryQ2status();
                    oq3s = aphold.getAssPayHistryQ3status();
                    oq4s = aphold.getAssPayHistryQ4status();

                    q1pay = aphold.getAssPayHistryQ1();
                    q2pay = aphold.getAssPayHistryQ2();
                    q3pay = aphold.getAssPayHistryQ3();
                    q4pay = aphold.getAssPayHistryQ4();

                }

                System.out.println(7 + " ================");

                Double cdBalance = assPayment.getCdBalance();

                AssCreditdebit assCreditdebit = (AssCreditdebit) session.createCriteria(AssCreditdebit.class)
                        .add(Restrictions.eq("assessment", assessment))
                        .add(Restrictions.eq("assCreditDebitStatus", 1)).uniqueResult();
                if (assCreditdebit != null) {
                    Double assBalance = assCreditdebit.getAssBalance();
                    double newbalans = assBalance - (cdBalance);
                    assCreditdebit.setAssBalance(newbalans);
                    if (newbalans == 0) {
                        assCreditdebit.setAssCreditDebitStatus(0);
                    }
                    session.update(assCreditdebit);
                }

                System.out.println(8 + " ================");
                //======================


                AssPayhistry aph = new AssPayhistry();

                aph.setAssessment(assessment);
                aph.setAssPayHistryQcunt(cquater);
                aph.setAssPayHistryYear(systemYear);
                aph.setAssPayHistryDate(systemDate);
                aph.setAssPayHistryStatus(1);
                aph.setAssPayHistryComment("");
                aph.setAssPayHistryTotalPayid(assPayment.getAssPaymentFullTotal());

                Set<AssPayto> assPaytos = assPayment.getAssPaytos();

                System.out.println(9 + " ================");

                for (AssPayto payto : assPaytos) {
                    Integer qno = payto.getAssPaytoQno();

                    if (qno == 1) {
                        q1pay = payto.getAssPaytoQvalue();
                        aph.setAssPayHistryQ1(q1pay);
                        aph.setAssPayHistryDrq1(payto.getAssPaytoDiscountRate());
                        q1 = true;
                        if (qsp1 == 0) {
                            if (payto.getAssPaytoStatus() == 1) {
                                qsp1 = 1;
                            }
                        }
                    } else if (qno == 2) {
                        q2pay = payto.getAssPaytoQvalue();
                        aph.setAssPayHistryQ2(q2pay);
                        aph.setAssPayHistryDrq2(payto.getAssPaytoDiscountRate());
                        q2 = true;
                        if (qsp2 == 0) {
                            if (payto.getAssPaytoStatus() == 1) {
                                qsp2 = 1;
                            }
                        }
                    } else if (qno == 3) {
                        q3pay = payto.getAssPaytoQvalue();
                        aph.setAssPayHistryQ3(q3pay);
                        aph.setAssPayHistryDrq3(payto.getAssPaytoDiscountRate());
                        q3 = true;
                        if (qsp3 == 0) {
                            if (payto.getAssPaytoStatus() == 1) {
                                qsp3 = 1;
                            }
                        }
                    } else if (qno == 4) {
                        q4pay = payto.getAssPaytoQvalue();
                        aph.setAssPayHistryQ4(q4pay);
                        aph.setAssPayHistryDrq4(payto.getAssPaytoDiscountRate());
                        q4 = true;
                        if (qsp4 == 0) {
                            if (payto.getAssPaytoStatus() == 1) {
                                qsp4 = 1;
                            }
                        }
                    }

                }

                if (!q1) {
                    aph.setAssPayHistryQ1(0.0);
                    aph.setAssPayHistryDrq1(0.0);
                }
                if (!q2) {
                    aph.setAssPayHistryQ2(0.0);
                    aph.setAssPayHistryDrq2(0.0);
                }
                if (!q3) {
                    aph.setAssPayHistryQ3(0.0);
                    aph.setAssPayHistryDrq3(0.0);
                }
                if (!q4) {
                    aph.setAssPayHistryQ4(0.0);
                    aph.setAssPayHistryDrq4(0.0);
                }
                aph.setAssPayHistryQ1status(qsp1);
                aph.setAssPayHistryQ2status(qsp2);
                aph.setAssPayHistryQ3status(qsp3);
                aph.setAssPayHistryQ4status(qsp4);


                Double assPaymentGotoDebit = assPayment.getAssPaymentGotoDebit();
                aph.setAssPayHistryOver(assPaymentGotoDebit);

                session.save(aph);

                Set<AssQstart> assQstarts = assessment.getAssQstarts();
                if (assQstarts.size() > 0) {
                    for (AssQstart Qstart : assQstarts) {
                        if (Qstart.getAssQstartYear() == systemYear) {

                            if (Qstart.getAssQstartQuaterNumber() == 2) {
                                for (AssPayto pt : assPaytos) {
                                    Integer qno = pt.getAssPaytoQno();
                                    if (qno == 1) {
                                        //Quater 1  parana arrias warant curunt balance update kirima
                                        Qstart.setAssQstartLqcArreas(modle.Round.round(Qstart.getAssQstartLqcArreas() - pt.getAssPaytoArrears()));
                                        Qstart.setAssQstartLqcWarrant(modle.Round.round(Qstart.getAssQstartLqcWarrant() - pt.getAssPaytoWarrant()));
                                    }
                                }
                            }

                            if (Qstart.getAssQstartQuaterNumber() == 3) {
                                for (AssPayto pt : assPaytos) {
                                    Integer qno = pt.getAssPaytoQno();
                                    if (qno == 2) {
                                        //Quater 2 parana arrias warant curunt balance update kirima
                                        Qstart.setAssQstartLqcArreas(modle.Round.round(Qstart.getAssQstartLqcArreas() - pt.getAssPaytoArrears()));
                                        Qstart.setAssQstartLqcWarrant(modle.Round.round(Qstart.getAssQstartLqcWarrant() - pt.getAssPaytoWarrant()));
                                    }
                                }
                            }

                            if (Qstart.getAssQstartQuaterNumber() == 4) {
                                for (AssPayto pt : assPaytos) {
                                    Integer qno = pt.getAssPaytoQno();
                                    if (qno == 3) {
                                        //Quater 2 parana arrias warant curunt balance update kirima
                                        Qstart.setAssQstartLqcArreas(modle.Round.round(Qstart.getAssQstartLqcArreas() - pt.getAssPaytoArrears()));
                                        Qstart.setAssQstartLqcWarrant(modle.Round.round(Qstart.getAssQstartLqcWarrant() - pt.getAssPaytoWarrant()));
                                    }
                                }
                            }


                            if (Qstart.getAssQstartQuaterNumber() == cquater) {

                                //Q start
                                Qstart.setAssQstartLycArreas(modle.Round.round(Qstart.getAssQstartLycArreas() - assPayment.getAssPaymentLyArrears()));
                                Qstart.setAssQstartLycWarrant(modle.Round.round(Qstart.getAssQstartLycWarrant() - assPayment.getAssPaymentLyWarrant()));
                                for (AssPayto pt : assPaytos) {
                                    Integer qno = pt.getAssPaytoQno();
                                    if (qno == cquater) {
                                        //Quater 2 parana arrias warant curunt balance update kirima
                                        Qstart.setAssQstartQpay(modle.Round.round(pt.getAssPaytoQvalue()));
                                        Qstart.setAssQstartQdiscont(modle.Round.round(pt.getAssPaytoDiscount()));
                                        Qstart.setAssQstartQtot(modle.Round.round(pt.getAssPaytoQvalue()));
                                        Qstart.setAssQstartFullTotal(modle.Round.round(assPayment.getAssPaymentFullTotal()));
                                        if (pt.getAssPaytoDiscount() > 0) {
                                            Qstart.setAssQstartHaveToQpay(0.0);// Have To pay Gana Update Kala Discunt Dila sampurnayen Geuwanam 0 Kala
                                        } else {
                                            Qstart.setAssQstartHaveToQpay(modle.Maths.round2(modle.Round.round(Qstart.getAssQstartHaveToQpay() - pt.getAssPaytoQvalue())));// Gewapu gana adukarala demma
                                        }
                                    }
                                }

                                //Pay History
                            } else {
                                // Me Quater ekata Qstart nehe
                            }

                            session.update(Qstart);

                        } else {// ME auruddata Qstart nehe
                        }
                    }
                } else {
                    //assQstart ekak wath nehe
                }

                assPayment.setAssPaymentStatus(1); // Tempery Payment Table Eke Status Eka maru karanawa day end eka awasan

                tr.commit();

                System.out.println("PS3   ====================================== ");
                pras3process(idReceipt + ""); // prasa 3 comment
                System.out.println("PS3   ====================================== ");
                return true;
            }
            return false;

        } catch (Exception e) {
            modle.ErrorLog.writeLog(e.getMessage(), "dayEndProcess", "dayEndProcess", "modle.assess");
            e.printStackTrace();
            tr.rollback();
            return false;

        } finally {
            session.close();
        }
    }

    public boolean backup(String text) {
        BUP bup = new modle.BUP();
        return bup.backupDataWithDatabase(text);
    }


}
