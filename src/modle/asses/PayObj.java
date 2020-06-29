package modle.asses;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import conn.DB;
import controller.assess.DayendController;
import controller.payment.UpdateStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modle.GetInstans;
import modle.KeyVal;
import modle.popup.BarcodeStatic;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.omg.PortableServer.IMPLICIT_ACTIVATION_POLICY_ID;
import pojo.*;

import javax.swing.plaf.TreeUI;

//Pay Status 1 = full complete; 2 = not complete; 0 = not paid;
public class PayObj {

    Logger logger = Logger.getLogger(PayObj.class.getName());

    int idAssessmant;
    double quaterValue;

    int idNature;
    double yearRate;
    double warrantRate;

    double lyArrears;
    double lyWarrant;

    double lqArrears;
    double lqWarrant;

    double allocation;

    double compleeteTot;

    int cheackDiscountDate;

    Assessment assessment;
    AssAllocation allocationPojo;
    pojo.AssNature nature;
    controller.assess.PayViewController pvc;
    Quater quater;

    int currentDay;
    int currentQuater;
    Date systemDate;
    int currentMonth;
    int currentYear;
    int prviasQuater;

    double TYA;
    double TYW;
    double totalAW;

    double Q1w;
    double Q1a;

    double Q2w;
    double Q2a;

    double Q3w;
    double Q3a;

    double Q4w;
    double Q4a;

    Double creditBalance;


    double fromly = 0;

    @SuppressWarnings("deprecation")
    public void cal(int idAssess, controller.assess.PayViewController pvc) {
        this.pvc = pvc;
        pvc.btn_pay.setDisable(true);
        quater = modle.GetInstans.getQuater();
        currentDay = quater.getCurrentDay();
        currentQuater = quater.getCurrentQuater();
        systemDate = quater.getSystemDate();
        currentMonth = quater.getCurrentMonth();
        currentYear = quater.getCurrentYear();
        prviasQuater = quater.getPrviasQuater();
        cheackDiscountDate = quater.cheackDiscountDate();

        pvc.txt_currentYear.setText(currentYear + "");
        pvc.txt_currentQuater.setText(currentQuater + "");

        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().commit();
        session = conn.NewHibernateUtil.getSessionFactory().openSession();

        boolean b = false; // ada dawase payment ekk kara thibe nam false


        if (currentQuater > 1) {
            b1 = false;
            pvc.cq1.setSelected(true);
            pvc.cq1.setDisable(true);
        }
        if (currentQuater > 2) {
            b2 = false;
            pvc.cq2.setSelected(true);
            pvc.cq2.setDisable(true);
        }
        if (currentQuater > 3) {
            b3 = false;
            pvc.cq3.setSelected(true);
            pvc.cq3.setDisable(true);
        }


        try {
            assessment = (Assessment) session.get(Assessment.class, idAssess);


            if (assessment.getAssessmentSyn() == 0) {


                Criteria payCry = session.createCriteria(AssPayment.class);

                payCry.add(Restrictions.eq("assPaymentStatus", 0));
                payCry.add(Restrictions.eq("assessment", assessment));
                payCry.add(Restrictions.eq("assPaymentThisYear", currentYear));
                payCry.add(Restrictions.eq("assPaymentDate", systemDate));
                List<AssPayment> listPay = payCry.list();

                ResultSet data = conn.DB.getData("SELECT\n"
                        + "	*\n"
                        + "FROM\n"
                        + "	ass_payment\n"
                        + "WHERE\n"
                        + "	ass_payment.ass_Payment_Status = 0\n"
                        + "AND ass_payment.Assessment_idAssessment = " + assessment.getIdAssessment() + "\n"
                        + "AND ass_payment.ass_Payment_ThisYear = " + currentYear + "\n"
                        + "AND ass_payment.ass_Payment_date = '" + systemDate + "'");

                if (data.last()) {
                    modle.Allert.notificationInfo("Olredy Paied Today " + assessment.getAssessmentNo(), " You have to do day end");
                    modle.Allert.notificationInfo(listPay.size() + "  ---  " + assessment.getAssessmentNo(), " You have to do day end");
                    b = false;
                } else {
                    b = true;// ada payment kara nometha a nisa true;
                    pvc.btn_pay.setDisable(true);
                    if (assessment != null) {

                        pvc.txt_ward.setText(assessment.getWard().getWardName());
                        pvc.txt_street.setText(assessment.getStreet().getStreetName());
                        pvc.txt_assessmant.setText(assessment.getAssessmentNo());
                        pvc.txt_Obsaloot.setText(assessment.getAssessmentObsolete());
                        pvc.txt_customer.setText(assessment.getCustomer().getCusName());

                        yearRate = modle.Round.round(assessment.getAssNature().getAssNatureYearRate()); // NEW ROUND
                        warrantRate = modle.Round.round(assessment.getAssNature().getAssNatureWarrantRate()); // NEW ROUND

                        pvc.txt_nature.setText(assessment.getAssNature().getAssNatureName() + " - " + yearRate + "%");
                        nature = assessment.getAssNature();
                        Set<AssAllocation> assAllocations = assessment.getAssAllocations();

                        for (AssAllocation assAllocation : assAllocations) {
                            if (assAllocation.getAssAllocationStatus() == 1) {

                                allocationPojo = assAllocation;
                                allocation = modle.Round.round(assAllocation.getAssAllocation());// NEW ROUND
                                pvc.txt_allocation.setText(modle.Maths.get2String(allocation));

                                quaterValue = allocation * yearRate / 100 / 4;
                                quaterValue = modle.Maths.round2(quaterValue);
                            }
                        }

                        Criteria cry = session.createCriteria(AssQstart.class);
                        cry.add(Restrictions.eq("assessment", assessment));
                        cry.add(Restrictions.eq("assQstartYear", currentYear));

                        List<AssQstart> list = cry.list();

                        TYA = 0;
                        TYW = 0;

                        Q1w = 0;
                        Q1a = 0;

                        Q2w = 0;
                        Q2a = 0;

                        Q3w = 0;
                        Q3a = 0;

                        Q4w = 0;
                        Q4a = 0;

                        for (AssQstart assQstart : list) {

//                        TYA += assQstart.getAssQstartLqcArreas();
//                        TYW += assQstart.getAssQstartLqcWarrant();
                            if (assQstart.getAssQstartQuaterNumber() == 1) {
//                            Q1a = assQstart.getAssQstartLqcArreas();
//                            Q1w = assQstart.getAssQstartLqcWarrant();

                                if (assQstart.getProcessUpdateArrears() != null) {
                                    fromly = modle.Round.round(assQstart.getProcessUpdateArrears());
                                    pvc.balance.setText(modle.Round.roundToString(fromly));
                                } else {
                                    fromly = 0;
                                    pvc.balance.setText("");
                                }
                            }
                            if (assQstart.getAssQstartQuaterNumber() == 2) {
                                Q1a = modle.Round.round(assQstart.getAssQstartLqcArreas()); // NEW ROUND
                                Q1w = modle.Round.round(assQstart.getAssQstartLqcWarrant()); // NEW ROUND
                            }
                            if (assQstart.getAssQstartQuaterNumber() == 3) {
                                Q2a = modle.Round.round(assQstart.getAssQstartLqcArreas()); // NEW ROUND
                                Q2w = modle.Round.round(assQstart.getAssQstartLqcWarrant()); // NEW ROUND
                            }
                            if (assQstart.getAssQstartQuaterNumber() == 4) {
                                Q3a = modle.Round.round(assQstart.getAssQstartLqcArreas()); // NEW ROUND
                                Q3w = modle.Round.round(assQstart.getAssQstartLqcWarrant()); // NEW ROUND
                            }
                        }

                        TYA = Q1a + Q2a + Q3a;
                        TYW = Q1w + Q2w + Q3w;

                        creditBalance = 0.0;
                        Set<AssCreditdebit> cds = assessment.getAssCreditdebits();
                        for (AssCreditdebit cd : cds) {
                            if (cd.getAssCreditDebitStatus() == 1) {
                                creditBalance = cd.getAssBalance();
                                pvc.txt_cd.setText(modle.Maths.get2String(creditBalance));
                            }
                        }

                        Criteria cryPayhistory = session.createCriteria(pojo.AssPayhistry.class);
                        cryPayhistory.add(Restrictions.eq("assessment", assessment));
                        cryPayhistory.add(Restrictions.eq("assPayHistryYear", currentYear));
                        cryPayhistory.add(Restrictions.eq("assPayHistryStatus", 1));
                        List<pojo.AssPayhistry> list1 = cryPayhistory.list();
                        pojo.AssPayhistry payhistry = null;

                        Q1paid = 0;
                        Q2paid = 0;
                        Q3paid = 0;
                        Q4paid = 0;

                        double overpay = 0;

                        for (pojo.AssPayhistry object : list1) {
                            payhistry = object;
                            Q1paid += modle.Round.round(payhistry.getAssPayHistryQ1());
                            Q2paid += modle.Round.round(payhistry.getAssPayHistryQ2());
                            Q3paid += modle.Round.round(payhistry.getAssPayHistryQ3());
                            Q4paid += modle.Round.round(payhistry.getAssPayHistryQ4());
                            overpay += modle.Round.round(payhistry.getAssPayHistryOver());
                        }

                        pvc.txt_over.setText(modle.Maths.rondAnd2String(overpay));

                        Q1paidDis = 0;
                        Q2paidDis = 0;
                        Q3paidDis = 0;
                        Q4paidDis = 0;

                        Q1Status = 0;
                        Q2Status = 0;
                        Q3Status = 0;
                        Q4Status = 0;

                        b1 = true; // meka true nam Q1 wla calculation eka wenawa
                        b2 = true;
                        b3 = true;
                        b4 = true;

                        pvc.cq1.setSelected(false);
                        pvc.cq1.setDisable(false);
                        pvc.cq2.setSelected(false);
                        pvc.cq2.setDisable(false);
                        pvc.cq3.setSelected(false);
                        pvc.cq3.setDisable(false);
                        pvc.cq4.setSelected(false);
                        pvc.cq4.setDisable(false);


                        if (payhistry != null) {


                            Q1paidDis = modle.Round.round(payhistry.getAssPayHistryDrq1());
                            Q2paidDis = modle.Round.round(payhistry.getAssPayHistryDrq2());
                            Q3paidDis = modle.Round.round(payhistry.getAssPayHistryDrq3());
                            Q4paidDis = modle.Round.round(payhistry.getAssPayHistryDrq4());

                            Q1Status = payhistry.getAssPayHistryQ1status();
                            Q2Status = payhistry.getAssPayHistryQ2status();
                            Q3Status = payhistry.getAssPayHistryQ3status();
                            Q4Status = payhistry.getAssPayHistryQ4status();


                            int currentQuater = GetInstans.getQuater().getCurrentQuater();

                            if (Q1Status == 1 || currentQuater > 1) {
                                // Q1Status 1 kiyanne q1 quater eke kalaya awasan
                                // Q1Status 1 kiyanne q1 quater eke kalaya awasan
//                            modle.Allert.notificationError("Q1", "not pay");
                                b1 = false;
                                pvc.cq1.setSelected(true);
                                pvc.cq1.setDisable(true);
                            }
                            if (Q2Status == 1 || currentQuater > 2) {
//                            modle.Allert.notificationError("Q2", "not pay");
                                b2 = false;
                                pvc.cq2.setSelected(true);
                                pvc.cq2.setDisable(true);
                            }
                            if (Q3Status == 1 || currentQuater > 3) {
//                            modle.Allert.notificationError("Q3", "not pay");
                                b3 = false;
                                pvc.cq3.setSelected(true);
                                pvc.cq3.setDisable(true);
                            }
                            if (Q4Status == 1) {
//                            modle.Allert.notificationError("Q4", "not pay");
                                b4 = false;
                                pvc.cq4.setSelected(true);
                                pvc.cq4.setDisable(true);
                            }

                        } else {
                            //Old Data not set messeage

//                        modle.Allert.notificationInfo("Old Data did not set", "please cheack old data");
                        }

                        cry.add(Restrictions.eq("assQstartQuaterNumber", currentQuater));

                        List<AssQstart> lastQstartlist = cry.list();

                        if (lastQstartlist.size() > 0) {
                            AssQstart lastQstart = lastQstartlist.get(0);
                            lyArrears = modle.Round.round(lastQstart.getAssQstartLycArreas());
                            lyWarrant = modle.Round.round(lastQstart.getAssQstartLycWarrant());


                        }

                        pvc.txt_lyArrears.setText(modle.Maths.rondAnd2String(lyArrears));
                        pvc.txt_lyWarrant.setText(modle.Maths.rondAnd2String(lyWarrant));

                        pvc.txt_tyArrears.setText(modle.Maths.rondAnd2String(TYA));
                        pvc.txt_tyWarrant.setText(modle.Maths.rondAnd2String(TYW));

                        totalAW = TYA + TYW + lyArrears + lyWarrant;
                        totalAW = modle.Maths.round2(totalAW);

                        pvc.txt_Total.setText(modle.Maths.rondAnd2String(totalAW));
                        //=======================================
                        ful = modle.Round.round(allocation * yearRate / 100); // ROUND
                        yd = ful * 10 / 100;
                        yd = modle.Maths.round2(yd);
                        yarpay = ful - yd;

                        ydd = quaterValue * 10 / 100;
                        yarPayQuater = quaterValue - ydd;
                        yarPayQuater = modle.Maths.round2(yarPayQuater);

                        qd = quaterValue * 5 / 100;
                        qd = modle.Maths.round2(qd);
                        qpay = quaterValue - qd;
                        qpay = modle.Maths.round2(qpay);

                        //=================================================
                        pvc.txt_quaterValue.setText(modle.Maths.get2String(quaterValue));
                        if (currentMonth == 1 || currentMonth == 4 || currentMonth == 7 || currentMonth == 10) {
                            compleeteTot = modle.Maths.round2(totalAW + qpay);
                        } else {
                            compleeteTot = modle.Maths.round2(totalAW + quaterValue);
                        }

//                    pvc.txt_Complete.setText(modle.Maths.get2String(compleeteTot));
                    } else {
                        modle.Allert.notificationWorning("Cheack ID", "This id is not available");
                    }
                }
            } else {
                modle.Allert.notificationWorning("Deleted Assessment", "Deleted Assessment Can't PAY");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        clearPay();

        if (b) {

            viewPay();
        }

    }

    double Q1paid;
    double Q2paid;
    double Q3paid;
    double Q4paid;

    double Q1paidDis;
    double Q2paidDis;
    double Q3paidDis;
    double Q4paidDis;

    int Q1Status;
    int Q2Status;
    int Q3Status;
    int Q4Status;

    double pay;
    double balance;

    double lywPay;
    double lywCurrnet;

    double lyaPay;
    double lyaCurrent;

    double Q1wp, Q1wc;
    double Q1ap, Q1ac;

    double Q2wp, Q2wc;
    double Q2ap, Q2ac;

    double Q3wp, Q3wc;
    double Q3ap, Q3ac;

    double Q4wp, Q4wc;
    double Q4ap, Q4ac;

    double Q1pay, Q1discount, Q1disrate;
    double Q2pay, Q2discount, Q2disrate;
    double Q3pay, Q3discount, Q3disrate;
    double Q4pay, Q4discount, Q4disrate;

    double QuaterPayCount;
    double creditpaid;

    ObservableList<modle.asses.TablePay> oal = FXCollections.observableArrayList();

    double fulpay = 00;

    public void WarrantArrirasCal(double payValue) {

        fulpay = payValue;
        pvc.col_dis.setCellValueFactory(new PropertyValueFactory<>("discription"));
        pvc.col_amount.setCellValueFactory(new PropertyValueFactory<>("amounts"));
        pvc.col_discount.setCellValueFactory(new PropertyValueFactory<>("disconts"));
        pvc.col_value.setCellValueFactory(new PropertyValueFactory<>("values"));

        clearPay();

        if (fromly > 0) {
            oal.add(new TablePay("From Last Year Balance", modle.Maths.round2(fromly), 0.0, fromly));
            payValue += fromly;
        }


        pay = payValue;

        balance = payValue;

        if (creditBalance != 0) {

            if (creditBalance > 0) {
                if (balance > creditBalance) {
                    creditpaid = creditBalance;
                    balance = balance - creditpaid;
                } else {
                    creditpaid = balance;
                    balance = balance - creditpaid;
                }

            } else if (creditBalance < 0) {
                creditpaid = creditBalance;
                balance = balance - (creditBalance);
            }

            balance = modle.Maths.round2(balance);
            oal.add(new TablePay("Credit Debit Balance", modle.Maths.round2(creditBalance), 0.0, balance));
        }

        if (lyWarrant > 0 && balance > 0) {
            if (balance >= lyWarrant) {
                balance = balance - lyWarrant;
                lywPay = lyWarrant;
                lywCurrnet = lyWarrant - lywPay;
            } else {
                lywPay = balance;
                lywCurrnet = lyWarrant - lywPay;
                balance = 0;
            }
            lywPay = round(lywPay);
            lywCurrnet = round(lywCurrnet);
            oal.add(new TablePay("Last Year Warrant", lyWarrant, 0.0, lywPay));
        }

        if (lyArrears > 0 && balance > 0) {
            if (balance >= lyArrears) {
                balance = balance - lyArrears;
                lyaPay = lyArrears;
                lyaCurrent = 0;
            } else {
                lyaPay = balance;
                lyaCurrent = lyArrears - lyaPay;
                balance = 0;
            }
            lyaPay = round(lyaPay);
            lyaCurrent = round(lyaCurrent);
            oal.add(new TablePay("Last Year Arrears", lyArrears, 0.0, lyaPay));
        }
        // Quarer 1 ==========================
        if (Q1w > 0 && balance > 0) {
            if (balance >= Q1w) {
                balance = balance - Q1w;
                Q1wp = Q1w;
                Q1wc = 0;
            } else {
                Q1wp = balance;
                Q1wc = Q1w - Q1wp;
                balance = 0;
            }

            Q1wp = round(Q1wp);
            Q1wc = round(Q1wc);
            oal.add(new TablePay("Quater one warrant", Q1w, 0.0, Q1wp));
        }

        if (Q1a > 0 && balance > 0) {
            if (balance >= Q1a) {
                balance = balance - Q1a;
                Q1ap = Q1a;
                Q1ac = 0;
            } else {
                Q1ap = balance;
                Q1ac = Q1a - Q1wp;
                balance = 0;
            }

            Q1ap = round(Q1ap);
            Q1ac = round(Q1ac);
            oal.add(new TablePay("Quater one arrears", Q1a, 0.0, Q1ap));
        }
        //==============================
        // Quarer 2 ==========================
        if (Q2w > 0 && balance > 0) {
            if (balance >= Q2w) {
                balance = balance - Q2w;
                Q2wp = Q2w;
                Q2wc = 0;
            } else {
                Q2wp = balance;
                Q2wc = Q2w - Q2wp;
                balance = 0;
            }
            Q2wp = round(Q2wp);
            Q2wc = round(Q2wc);
            oal.add(new TablePay("Quater two warrant", Q2w, 0.0, Q2wp));
        }

        if (Q2a > 0 && balance > 0) {
            if (balance >= Q2a) {
                balance = balance - Q2a;
                Q2ap = Q2a;
                Q2ac = 0;
            } else {
                Q2ap = balance;
                Q2ac = Q2a - Q2wp;
                balance = 0;
            }
            Q2ap = round(Q2ap);
            Q2ac = round(Q2ac);
            oal.add(new TablePay("Quater two arrears", Q2a, 0.0, Q2ap));
        }
        //==============================
        // Quarer 3 ==========================
        if (Q3w > 0 && balance > 0) {
            if (balance >= Q3w) {
                balance = balance - Q3w;
                Q3wp = Q3w;
                Q3wc = 0;
            } else {
                Q3wp = balance;
                Q3wc = Q3w - Q3wp;
                balance = 0;
            }
            Q3wp = round(Q3wp);
            Q3wc = round(Q3wc);
            oal.add(new TablePay("Quater Three warrant", Q3w, 0.0, Q3wp));
        }

        if (Q3a > 0 && balance > 0) {
            if (balance >= Q3a) {
                balance = balance - Q3a;
                Q3ap = Q3a;
                Q3ac = 0;
            } else {
                Q3ap = balance;
                Q3ac = Q3a - Q3wp;
                balance = 0;
            }
            Q3ap = round(Q3ap);
            Q3ac = round(Q3ac);
            oal.add(new TablePay("Quater Three arrears", Q3a, 0.0, Q3ap));
        }
        //==============================

        // Quarer 4 ==========================
        if (Q4w > 0 && balance > 0) {
            if (balance >= Q4w) {
                balance = balance - Q4w;
                Q4wp = Q4w;
                Q4wc = 0;
            } else {
                Q4wp = balance;
                Q4wc = Q4w - Q4wp;
                balance = 0;
            }
            Q4wp = round(Q4wp);
            Q4wc = round(Q4wc);
            oal.add(new TablePay("Quater four warrant", Q4w, 0.0, Q4wp));
        }

        if (Q4a > 0 && balance > 0) {
            if (balance >= Q4a) {
                balance = balance - Q4a;
                Q4ap = Q4a;
                Q4ac = 0;
            } else {
                Q4ap = balance;
                Q4ac = Q4a - Q4wp;
                balance = 0;
            }
            Q4ap = round(Q4ap);
            Q4ac = round(Q4ac);
            oal.add(new TablePay("Quater four arrears", Q4a, 0.0, Q4ap));
        }
        //==============================

        QuaterPayCal();
    }

    public void viewPay() {

        pvc.q1pidv.setText(modle.Maths.rondAnd2String(Q1paid));
        pvc.q2pidv.setText(modle.Maths.rondAnd2String(Q2paid));
        pvc.q3pidv.setText(modle.Maths.rondAnd2String(Q3paid));
        pvc.q4pidv.setText(modle.Maths.rondAnd2String(Q4paid));


        if (pvc.cq1.isSelected() && pvc.cq2.isSelected() && pvc.cq3.isSelected() && pvc.cq4.isSelected()) {
            if (currentMonth == 1) {
                pvc.q1_val.setText(modle.Round.roundToString(yarPayQuater - Q1paid));
                pvc.q2_val.setText(modle.Round.roundToString(yarPayQuater - Q2paid));
                pvc.q3_val.setText(modle.Round.roundToString(yarPayQuater - Q3paid));
                pvc.q4_val.setText(modle.Round.roundToString(yarPayQuater - Q4paid));
            } else if (currentMonth > 1 && currentMonth <= 4) {
                pvc.q1_val.setText(modle.Round.roundToString(quaterValue - Q1paid));
                pvc.q2_val.setText(modle.Round.roundToString(qpay - Q2paid));
                pvc.q3_val.setText(modle.Round.roundToString(qpay - Q3paid));
                pvc.q4_val.setText(modle.Round.roundToString(qpay - Q4paid));
            } else if (currentMonth >= 5 && currentMonth <= 7) {
                pvc.q1_val.setText(modle.Round.roundToString(quaterValue - Q1paid));
                pvc.q2_val.setText(modle.Round.roundToString(quaterValue - Q2paid));
                pvc.q3_val.setText(modle.Round.roundToString(qpay - Q3paid));
                pvc.q4_val.setText(modle.Round.roundToString(qpay - Q4paid));

            } else if (currentMonth >= 7 && currentMonth <= 10) {
                pvc.q1_val.setText(modle.Round.roundToString(quaterValue - Q1paid));
                pvc.q2_val.setText(modle.Round.roundToString(quaterValue - Q2paid));
                pvc.q3_val.setText(modle.Round.roundToString(quaterValue - Q3paid));
                pvc.q4_val.setText(modle.Round.roundToString(qpay - Q4paid));

            } else if (currentMonth > 10) {
                pvc.q1_val.setText(modle.Round.roundToString(quaterValue - Q1paid));
                pvc.q2_val.setText(modle.Round.roundToString(quaterValue - Q2paid));
                pvc.q3_val.setText(modle.Round.roundToString(quaterValue - Q3paid));
                pvc.q4_val.setText(modle.Round.roundToString(quaterValue - Q4paid));
            }
        } else {

            if (pvc.cq1.isSelected()) {
                if (currentMonth == 1) {
                    pvc.q1_val.setText(modle.Round.roundToString(qpay - Q1paid));
                } else {
                    pvc.q1_val.setText(modle.Round.roundToString(quaterValue - Q1paid));
                }
            } else {
                pvc.q1_val.setText(modle.Round.roundToString(0));
            }


            if (pvc.cq2.isSelected()) {
                if (currentMonth <= 4) {
                    pvc.q2_val.setText(modle.Round.roundToString(qpay - Q2paid));
                } else {
                    pvc.q2_val.setText(modle.Round.roundToString(quaterValue - Q2paid));
                }
            } else {
                pvc.q2_val.setText(modle.Round.roundToString(0));
            }


            if (pvc.cq3.isSelected()) {
                if (currentMonth <= 7) {
                    pvc.q3_val.setText(modle.Round.roundToString(qpay - Q3paid));
                } else {
                    pvc.q3_val.setText(modle.Round.roundToString(quaterValue - Q3paid));
                }
            } else {
                pvc.q3_val.setText(modle.Round.roundToString(0));
            }
            if (pvc.cq4.isSelected()) {
                if (currentMonth <= 10) {
                    pvc.q4_val.setText(modle.Round.roundToString(qpay - Q4paid));
                } else {
                    pvc.q4_val.setText(modle.Round.roundToString(quaterValue - Q4paid));
                }
            } else {
                pvc.q4_val.setText(modle.Round.roundToString(0));
            }
        }

        if (b1 && b2 && b3 && b4) {
        } else if (b2 && b3 && b4) {
            pvc.q1_val.setText(modle.Round.roundToString(0));
        } else if (b3 && b4) {
            pvc.q1_val.setText(modle.Round.roundToString(0));
            pvc.q2_val.setText(modle.Round.roundToString(0));
        } else if (b4) {
            pvc.q1_val.setText(modle.Round.roundToString(0));
            pvc.q2_val.setText(modle.Round.roundToString(0));
            pvc.q3_val.setText(modle.Round.roundToString(0));
        } else {
            pvc.q1_val.setText(modle.Round.roundToString(0));
            pvc.q2_val.setText(modle.Round.roundToString(0));
            pvc.q3_val.setText(modle.Round.roundToString(0));
            pvc.q4_val.setText(modle.Round.roundToString(0));
        }

        double q1 = Double.parseDouble(pvc.q1_val.getText());
        double q2 = Double.parseDouble(pvc.q2_val.getText());
        double q3 = Double.parseDouble(pvc.q3_val.getText());
        double q4 = Double.parseDouble(pvc.q4_val.getText());


        BigDecimal rawValue = new BigDecimal(totalAW + q1 + q2 + q3 + q4 + creditBalance - fromly);
        BigDecimal value = rawValue.setScale(0, RoundingMode.UP);
        pvc.q4_tot.setText(value.toString() + ".00");

    }


    boolean b1 = true; // meka true nam Q1 wla calculation eka wenawa
    boolean b2 = true;
    boolean b3 = true;
    boolean b4 = true;

    double ful;
    double yd;
    double yarpay;
    double ydd;
    double yarPayQuater;
    double qd;
    double qpay;

    //Quater Pay Status 0 = not compleet 1 = full compleet
    int q1ps = 0;
    int q2ps = 0;
    int q3ps = 0;
    int q4ps = 0;

    public void Q1() {

        double q1overpaid = Q1paid;

        if (b1) {
            balance = balance + Q1paid;
            if (yarPayQuater * 4 <= balance) {
                oal.add(new TablePay("Quater One Pay", quaterValue, ydd, yarPayQuater));
                oal.add(new TablePay("Quater Two Pay", quaterValue, ydd, yarPayQuater));
                oal.add(new TablePay("Quater Three Pay", quaterValue, ydd, yarPayQuater));
                oal.add(new TablePay("Quater Four Pay", quaterValue, ydd, yarPayQuater));

                q1ps = 1;
                q2ps = 1;
                q3ps = 1;
                q4ps = 1;

                Q1pay = yarPayQuater;
                Q2pay = yarPayQuater;
                Q3pay = yarPayQuater;
                Q4pay = yarPayQuater;

                Q1discount = ydd;
                Q2discount = ydd;
                Q3discount = ydd;
                Q4discount = ydd;

                Q1disrate = 10;
                Q2disrate = 10;
                Q3disrate = 10;
                Q4disrate = 10;

                QuaterPayCount = 4;

                balance = balance - yarPayQuater * 4;
            } else if (balance >= qpay * 3) {
                oal.add(new TablePay("Quater One Pay", quaterValue, qd, qpay));
                oal.add(new TablePay("Quater Two Pay", quaterValue, qd, qpay));
                oal.add(new TablePay("Quater Three Pay", quaterValue, qd, qpay));

                q1ps = 1;
                q2ps = 1;
                q3ps = 1;
                q4ps = 0;
                Q1pay = qpay;
                Q2pay = qpay;
                Q3pay = qpay;
                Q1discount = qd;
                Q2discount = qd;
                Q3discount = qd;
                Q1disrate = 5;
                Q2disrate = 5;
                Q3disrate = 5;

                balance = balance - qpay * 3;
                if (balance > 0 && balance < qpay) {
                    oal.add(new TablePay("Quater Four Pay", quaterValue, 0.0, balance));
                    oal.add(new TablePay("Quater Four Not Complete", quaterValue, 0.0, quaterValue - balance));
                    Q4pay = balance;
                    Q4discount = 0;
                    Q4disrate = 0;
                    balance = 0;
                }
            } else if (balance >= qpay * 2) {

                oal.add(new TablePay("Quater One Pay", quaterValue, qd, qpay));
                oal.add(new TablePay("Quater Two Pay", quaterValue, qd, qpay));
                q1ps = 1;
                q2ps = 1;
                q3ps = 0;
                q4ps = 0;
                Q1pay = qpay;
                Q1discount = qd;
                Q1disrate = 5;
                Q2pay = qpay;
                Q2discount = qd;
                Q2disrate = 5;
                balance = balance - qpay * 2;
                if (balance > 0 && balance < qpay) {
                    oal.add(new TablePay("Quater Three Pay", quaterValue, 0.0, balance));
                    oal.add(new TablePay("Quater Three Not Complete", quaterValue, 0.0, quaterValue - balance));
                    Q3pay = balance;
                    Q3discount = 0;
                    Q3disrate = 0;
                    balance = 0;
                }
            } else if (balance >= qpay) {

                oal.add(new TablePay("Quater One Pay", quaterValue, qd, qpay));
                q1ps = 1;
                q2ps = 0;
                q3ps = 0;
                q4ps = 0;
                balance = balance - qpay;
                Q1pay = qpay;
                Q1discount = qd;
                Q1disrate = 5;
                if (balance > 0 && balance < qpay) {
                    oal.add(new TablePay("Quater Two Pay", quaterValue, 0.0, balance));
                    oal.add(new TablePay("Quater Two Not Complete", quaterValue, 0.0, quaterValue - balance));
                    Q2pay = balance;
                    Q2discount = 0;
                    Q2disrate = 0;
                    balance = 0;
                }
            } else if (balance > 0) {
                oal.add(new TablePay("Quater One Pay", quaterValue, 0.0, balance));
                oal.add(new TablePay("Quater One Not Complete", quaterValue, 0.0, quaterValue - balance));
                Q1pay = balance;
                Q1discount = 0;
                Q1disrate = 0;
                balance = 0;
            }
        } else {
            Q2();
        }
    }

    public void Q1_23() {
        if (b1) {
            balance = balance + Q1paid;
            if (balance >= quaterValue) {
                oal.add(new TablePay("Quater One Pay", quaterValue, 0.0, quaterValue));
                q1ps = 1;
                q2ps = 0;
                q3ps = 0;
                q4ps = 0;
                Q1pay = quaterValue;
                Q1discount = 0;
                Q1disrate = 0;
                balance = balance - quaterValue;
            } else {
                oal.add(new TablePay("Quater One Pay", quaterValue, 0.0, balance));
                oal.add(new TablePay("Quater One Not Complete", quaterValue, 0.0, quaterValue - balance));
                Q1pay = balance;
                Q1discount = 0;
                Q1disrate = 0;
                balance = 0;
                q1ps = 0;
            }

            if (balance >= qpay * 3) {
                oal.add(new TablePay("Quater Two Pay", quaterValue, qd, qpay));
                oal.add(new TablePay("Quater Three Pay", quaterValue, qd, qpay));
                oal.add(new TablePay("Quater Four Pay", quaterValue, qd, qpay));
                q2ps = 1;
                q3ps = 1;
                q4ps = 1;
                Q2pay = qpay;
                Q2discount = qd;
                Q2disrate = 5;

                Q3pay = qpay;
                Q3discount = qd;
                Q3disrate = 5;

                Q4pay = qpay;
                Q4discount = qd;
                Q4disrate = 5;

                balance = balance - qpay * 3;
            } else if (balance >= qpay * 2) {
                oal.add(new TablePay("Quater Two Pay", quaterValue, qd, qpay));
                oal.add(new TablePay("Quater Three Pay", quaterValue, qd, qpay));
                q2ps = 1;
                q3ps = 1;
                q4ps = 0;
                Q2pay = qpay;
                Q2discount = qd;
                Q2disrate = 5;

                Q3pay = qpay;
                Q3discount = qd;
                Q3disrate = 5;
                balance = balance - qpay * 2;
                if (balance > 0 && balance < qpay) {
                    oal.add(new TablePay("Quater Four Pay", quaterValue, 0.0, balance));
                    oal.add(new TablePay("Quater Four Not Complete", quaterValue, 0.0, quaterValue - balance));
                    Q4pay = balance;
                    Q4discount = 0;
                    Q4disrate = 0;
                    balance = 0;
                }
            } else if (balance >= qpay) {
                oal.add(new TablePay("Quater two Pay", quaterValue, qd, qpay));
                q2ps = 1;
                q3ps = 0;
                q4ps = 0;
                Q2pay = qpay;
                Q2discount = qd;
                Q2disrate = 5;
                balance = balance - qpay;
                if (balance > 0 && balance < qpay) {
                    oal.add(new TablePay("Quater Three Pay", quaterValue, 0.0, balance));
                    oal.add(new TablePay("Quater Three Not Complete", quaterValue, 0.0, quaterValue - balance));
                    Q3pay = balance;
                    Q3discount = 0;
                    Q3disrate = 0;
                    balance = 0;
                }
            } else if (balance > 0) {
                oal.add(new TablePay("Quater Two Pay", quaterValue, 0.0, balance));
                oal.add(new TablePay("Quater Two Not Complete", quaterValue, 0.0, quaterValue - balance));
                Q2pay = balance;
                Q2discount = 0;
                Q2disrate = 0;
                balance = 0;
            }
        } else {
            Q2();
        }
    }

    public void Q2() {
        if (b2) {
            balance = balance + Q2paid;
            if (balance >= qpay * 3) {
                oal.add(new TablePay("Quater Two Pay", quaterValue, qd, qpay));
                oal.add(new TablePay("Quater Three Pay", quaterValue, qd, qpay));
                oal.add(new TablePay("Quater Four Pay", quaterValue, qd, qpay));

                q2ps = 1;
                q3ps = 1;
                q4ps = 1;

                Q2pay = qpay;
                Q2discount = qd;
                Q2disrate = 5;

                Q3pay = qpay;
                Q3discount = qd;
                Q3disrate = 5;

                Q4pay = qpay;
                Q4discount = qd;
                Q4disrate = 5;

                balance = balance - qpay * 3;
            } else if (balance >= qpay * 2) {
                oal.add(new TablePay("Quater Two Pay", quaterValue, qd, qpay));
                oal.add(new TablePay("Quater Three Pay", quaterValue, qd, qpay));

                q2ps = 1;
                q3ps = 1;
                q4ps = 0;

                Q2pay = qpay;
                Q2discount = qd;
                Q2disrate = 5;

                Q3pay = qpay;
                Q3discount = qd;
                Q3disrate = 5;

                balance = balance - qpay * 2;
                if (balance > 0 && balance < qpay) {
                    oal.add(new TablePay("Quater Four Pay", quaterValue, 0.0, balance));
                    oal.add(new TablePay("Quater Four Not Complete", quaterValue, 0.0, quaterValue - balance));
                    Q4pay = balance;
                    Q4discount = 0;
                    Q4disrate = 0;
                    balance = 0;
                }
            } else if (balance >= qpay) {
                oal.add(new TablePay("Quater two Pay", quaterValue, qd, qpay));
                q2ps = 1;
                q3ps = 0;
                q4ps = 0;

                Q2pay = qpay;
                Q2discount = qd;
                Q2disrate = 5;
                balance = balance - qpay;
                if (balance > 0 && balance < qpay) {
                    oal.add(new TablePay("Quater Three Pay", quaterValue, 0.0, balance));
                    oal.add(new TablePay("Quater Three Not Complete", quaterValue, 0.0, quaterValue - balance));
                    Q3pay = balance;
                    Q3discount = 0;
                    Q3disrate = 0;

                    balance = 0;
                }
            } else if (balance > 0) {
                oal.add(new TablePay("Quater Two Pay", quaterValue, 0.0, balance));
                oal.add(new TablePay("Quater Two Not Complete", quaterValue, 0.0, quaterValue - balance));
                Q2pay = balance;
                Q2discount = 0;
                Q2disrate = 0;
                balance = 0;
            }
        } else {
            Q3();
        }
    }

    public void Q2_56() {
        if (b2) {
            balance = balance + Q2paid;
            if (balance >= quaterValue) {
                oal.add(new TablePay("Quater Two Pay", quaterValue, 0.0, quaterValue));

                q2ps = 1;
                q3ps = 0;
                q4ps = 0;
                Q2pay = quaterValue;
                Q2discount = 0;
                Q2disrate = 0;
                balance = balance - quaterValue;
                System.out.println("mekata awa 2 pay " + balance + "  " + quaterValue);
            } else {
                oal.add(new TablePay("Quater Two Pay", quaterValue, 0.0, balance));
                oal.add(new TablePay("Quater Two Not Complete", quaterValue, 0.0, quaterValue - balance));
                Q2pay = balance;
                Q2discount = 0;
                Q2disrate = 0;
                balance = 0;

                System.out.println("mekata awa 2 not complete" + balance + "  " + quaterValue);
            }


            if (balance >= qpay * 2) {
                oal.add(new TablePay("Quater Three Pay", quaterValue, qd, qpay));
                oal.add(new TablePay("Quater Four Pay", quaterValue, qd, qpay));

                q3ps = 1;
                q4ps = 1;

                Q3pay = qpay;
                Q3discount = qd;
                Q3disrate = 5;

                Q4pay = qpay;
                Q4discount = qd;
                Q4disrate = 5;

                balance = balance - qpay * 2;
            } else if (balance >= qpay) {
                oal.add(new TablePay("Quater Three Pay", quaterValue, qd, qpay));

                q3ps = 1;
                q4ps = 0;

                Q3pay = qpay;
                Q3discount = qd;
                Q3disrate = 5;
                balance = balance - qpay;
                if (balance > 0 && balance < qpay) {
                    oal.add(new TablePay("Quater Four Pay", quaterValue, 0.0, balance));
                    oal.add(new TablePay("Quater Four Not Complete", quaterValue, 0.0, quaterValue - balance));
                    Q4pay = balance;
                    Q4discount = 0;
                    Q4disrate = 0;
                    balance = 0;
                }
            } else if (balance > 0) {
                oal.add(new TablePay("Quater Three Pay", quaterValue, 0.0, balance));
                oal.add(new TablePay("Quater Three Not Complete", quaterValue, 0.0, quaterValue - balance));
                Q3pay = balance;
                Q3discount = 0;
                Q3disrate = 0;
                balance = 0;
            }
        } else {
            Q3();
        }
    }

    public void Q3() {
        if (b3) {
            balance = balance + Q3paid;
            if (balance >= qpay * 2) {
                oal.add(new TablePay("Quater Three Pay", quaterValue, qd, qpay));
                oal.add(new TablePay("Quater Four Pay", quaterValue, qd, qpay));

                q3ps = 1;
                q4ps = 1;

                Q3pay = qpay;
                Q3discount = qd;
                Q3disrate = 5;

                Q4pay = qpay;
                Q4discount = qd;
                Q4disrate = 5;

                balance = balance - qpay * 2;
            } else if (balance >= qpay) {
                oal.add(new TablePay("Quater Three Pay", quaterValue, qd, qpay));

                q3ps = 1;
                q4ps = 0;

                Q3pay = qpay;
                Q3discount = qd;
                Q3disrate = 5;

                balance = balance - qpay;
                if (balance > 0 && balance < qpay) {
                    oal.add(new TablePay("Quater Four Pay", quaterValue, 0.0, balance));
                    oal.add(new TablePay("Quater Four Not Complete", quaterValue, 0.0, quaterValue - balance));
                    Q4pay = balance;
                    Q4discount = 0;
                    Q4disrate = 0;
                    balance = 0;
                }
            } else if (balance > 0) {
                oal.add(new TablePay("Quater Three Pay", quaterValue, 0.0, balance));
                oal.add(new TablePay("Quater Three Not Complete", quaterValue, 0.0, quaterValue - balance));
                Q3pay = balance;
                Q3discount = 0;
                Q3disrate = 0;
                balance = 0;
            }
        } else {
            Q4();
        }
    }

    public void Q3_89() {
        if (b3) {
            balance = balance + Q3paid;
            if (balance >= quaterValue) {
                oal.add(new TablePay("Quater Three Pay", quaterValue, 0.0, quaterValue));
                q3ps = 1;
                q4ps = 0;
                Q3pay = quaterValue;
                Q3discount = 0;
                Q3disrate = 0;
                balance = balance - quaterValue;
            } else {
                oal.add(new TablePay("Quater Three Pay", quaterValue, 0.0, balance));
                oal.add(new TablePay("Quater Three Not Complete", quaterValue, 0.0, quaterValue - balance));
                Q3pay = balance;
                Q3discount = 0;
                Q3disrate = 0;
                balance = 0;
            }

            if (balance >= qpay) {
                oal.add(new TablePay("Quater Four Pay", quaterValue, qd, qpay));

                q4ps = 1;
                Q4pay = qpay;
                Q4discount = qd;
                Q4disrate = 5;
                balance = balance - qpay;


//                if (balance > 0 && balance < qpay) {
//                    oal.add(new TablePay("Quater Four Pay", quaterValue, 0.0, balance));
//                    oal.add(new TablePay("Quater Four Not Complete", quaterValue, 0.0, quaterValue - balance));
//                    Q4pay = balance;
//                    Q4discount = 0;
//                    Q4disrate = 0;
//                    balance = 0;
//                    System.out.println("=============================");
//                }


            } else if (balance > 0) {
                oal.add(new TablePay("Quater Four Pay", quaterValue, 0.0, balance));
                oal.add(new TablePay("Quater Four Not Complete", quaterValue, 0.0, quaterValue - balance));
                Q4pay = balance;
                Q4discount = 0;
                Q4disrate = 0;
                balance = 0;
                System.out.println("==========++++++++++++++++===");
            }
        } else {
            Q4();
        }
    }

    public void Q4() {
        if (b4) {
            balance = balance + Q4paid;
            if (balance >= qpay) {
                oal.add(new TablePay("Quater Four Pay", quaterValue, qd, qpay));
                q4ps = 1;
                Q4pay = qpay;
                Q4discount = qd;
                Q4disrate = 5;
                balance = balance - qpay;
            } else if (balance > 0) {
                oal.add(new TablePay("Quater Four Pay", quaterValue, 0.0, balance));
                oal.add(new TablePay("Quater Four Not Complete", quaterValue, 0.0, quaterValue - balance));
                Q4pay = balance;
                Q4discount = 0;
                Q4disrate = 0;
                balance = 0;
            }
        }
    }

    public void Q4_1112() {
        if (b4) {
            balance = balance + Q4paid;
            if (balance >= quaterValue) {
                oal.add(new TablePay("Quater Four Pay", quaterValue, 0.0, quaterValue));
                q4ps = 1;
                Q4pay = quaterValue;
                Q4discount = 0;
                Q4disrate = 0;
                balance = balance - quaterValue;
            } else {
                oal.add(new TablePay("Quater Four Pay", quaterValue, 0.0, balance));
                oal.add(new TablePay("Quater Four Not Complete", quaterValue, 0.0, quaterValue - balance));
                Q4pay = balance;
                Q4discount = 0;
                Q4disrate = 0;
                balance = 0;
            }
        }
    }

    public void QuaterPayCal() {
        if (balance > 0) {
            if (currentMonth == 1) {
                Q1();
            } else if (currentMonth == 2 || currentMonth == 3) {
                Q1_23();
            } else if (currentMonth == 4) {
                Q2();
            } else if (currentMonth == 5 || currentMonth == 6) {
                Q2_56();
            } else if (currentMonth == 7) {
                Q3();
            } else if (currentMonth == 8 || currentMonth == 9) {
                Q3_89();
            } else if (currentMonth == 10) {
                Q4();
            } else if (currentMonth == 11 || currentMonth == 12) {
                Q4_1112();
            }

            balance = round(balance);

            if (balance > 0) {
                oal.add(new TablePay("Over Pay", 0.0, 0.0, balance));
            }
        }

        pvc.table_pay.setItems(oal);

    }

    public double round(double d) {
        return modle.Maths.round2(d);
    }

    public void clearPay() {
        oal.clear();
        pay = 0;
        balance = 0;

        lywPay = 0;
        lywCurrnet = 0;

        lyaPay = 0;
        lyaCurrent = 0;

        Q1wp = 0;
        Q1wc = 0;
        Q1ap = 0;
        Q1ac = 0;

        Q2wp = 0;
        Q2wc = 0;
        Q2ap = 0;
        Q2ac = 0;

        Q3wp = 0;
        Q3wc = 0;
        Q3ap = 0;
        Q3ac = 0;

        Q4wp = 0;
        Q4wc = 0;
        Q4ap = 0;
        Q4ac = 0;

        Q1pay = 0;
        Q2pay = 0;
        Q3pay = 0;
        Q4pay = 0;

        q1ps = 0; // Q1 eka gewa sampurna nam 1 nethnam 0
        q2ps = 0;
        q3ps = 0;
        q4ps = 0;

        Q1discount = 0;
        Q2discount = 0;
        Q3discount = 0;
        Q4discount = 0;

        Q1disrate = 0;
        Q2disrate = 0;
        Q3disrate = 0;
        Q4disrate = 0;

        QuaterPayCount = 0;

    }

    String rno = "";

    public boolean pay() {


        System.out.println("CD" + creditpaid + creditBalance);


        double ca = 0;
        double ch = 0;
        String bank = "";
        int b_no = 0;
        String chno = "";

        boolean b = false;

        if (pvc.cash.isSelected()) {
            ca = Double.parseDouble(pvc.txt_cash.getText());
            b = true;
        }

        if (pvc.check.isSelected()) {
            ch = Double.parseDouble(pvc.txt_check.getText());
            bank = pvc.combo_bank.getSelectionModel().getSelectedItem();
            chno = pvc.txt_chq_no.getText();
            if (bank != null && chno.length() > 0) {
                b = true;
            } else {
                b = false;
                modle.Allert.notificationError("Please recheack payment details", "Banka, Cheack");
            }
        }


        if (!pvc.cash.isSelected() && !pvc.check.isSelected()) {
            ca = Double.parseDouble(pvc.txt_cash.getText());
            ch = Double.parseDouble(pvc.txt_check.getText());
        }


        if (pvc.cash.isSelected() && pvc.check.isSelected()) {
            ca = Double.parseDouble(pvc.txt_cash.getText());
            ch = Double.parseDouble(pvc.txt_check.getText());
            bank = pvc.combo_bank.getSelectionModel().getSelectedItem();
            chno = pvc.txt_chq_no.getText();
        }

        if (b1) {
            Q1pay = Q1pay - Q1paid; ///============================================================================================================
        }  // Else if mekuwa
        if (b2) {
            Q2pay = Q2pay - Q2paid;
        }
        if (b3) {
            Q3pay = Q3pay - Q3paid;
        }
        if (b4) {
            Q4pay = Q4pay - Q4paid;
        }

        Q1pay = modle.Round.round(Q1pay);
        Q2pay = modle.Round.round(Q2pay);
        Q3pay = modle.Round.round(Q3pay);
        Q4pay = modle.Round.round(Q4pay);

        Q1discount = modle.Round.round(Q1discount);
        Q2discount = modle.Round.round(Q2discount);
        Q3discount = modle.Round.round(Q3discount);
        Q4discount = modle.Round.round(Q4discount);

        Q1wp = modle.Round.round(Q1wp);
        Q2wp = modle.Round.round(Q2wp);
        Q3wp = modle.Round.round(Q3wp);
        Q4wp = modle.Round.round(Q4wp);

        Q1ap = modle.Round.round(Q1ap);
        Q2ap = modle.Round.round(Q2ap);
        Q3ap = modle.Round.round(Q3ap);
        Q4ap = modle.Round.round(Q4ap);

        lyaPay = modle.Round.round(lyaPay);
        lywPay = modle.Round.round(lywPay);

        double payTot = lywPay + lyaPay + Q1wp + Q2wp + Q3wp + Q4wp + Q1ap + Q2ap + Q3ap + Q4ap + Q1pay + Q2pay + Q3pay + Q4pay - fromly;

        payTot = modle.Maths.round2(payTot);
        System.out.println("======  over  =======" + balance);

        if (b) {
            pvc.btn_pay.setDisable(true);
            Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction().commit();
            Transaction transaction = session.beginTransaction();
            try {
                System.out.println("mekata awa 1");
                assessment = (Assessment) session.load(Assessment.class, assessment.getIdAssessment());
                allocationPojo = (AssAllocation) session.load(AssAllocation.class, allocationPojo.getIdassAllocation());
                nature = (pojo.AssNature) session.load(pojo.AssNature.class, nature.getIdassNature());
                Receipt receipt = new Receipt();
                receipt.setApplicationCatagory((pojo.ApplicationCatagory) session.load(pojo.ApplicationCatagory.class, 2));
                receipt.setReceptApplicationId(assessment.getIdAssessment());
                receipt.setReceiptDay(systemDate);
                System.out.println("mewkata awa 2");
                //===========================
                // Me tika fill karanna
                receipt.setCesh(ca);
                receipt.setCheack(ch);
                receipt.setReceiptTotal(ca + ch);

                receipt.setReceiptStatus(0);
                receipt.setReceiptSyn(1);


                Serializable save = session.save(receipt);
                Receipt rn = (Receipt) session.load(Receipt.class, save);
                rno = rn.getIdReceipt() + "";

                //================================
                //Bank
                if (bank.length() > 0) {
                    List<pojo.Bank> list = session.createCriteria(pojo.Bank.class).add(Restrictions.eq("bankName", bank)).list();
                    b_no = list.get(0).getIdBank();
                }

                //================================
                AssPayment assPayment = new AssPayment();
                assPayment.setAssessment(assessment);
                assPayment.setReceipt(receipt);
                assPayment.setAssNature(nature);
                assPayment.setAssAllocation(allocationPojo);
                assPayment.setAssPaymentQNumber(currentQuater);
                assPayment.setAssPaymentThisYear(currentYear);
                assPayment.setAssPaymentDate(systemDate);
                assPayment.setAssPaymentLyArrears(lyaPay);
                assPayment.setAssPaymentLyWarrant(lywPay);
                assPayment.setAssPaymentFullTotal(payTot);// calculate this and replace
                assPayment.setAssPaymentIdUser(modle.StaticViews.getLogUser().getIdUser());
                assPayment.setOfficeIdOffice(modle.StaticViews.getLogUser().getOfficeIdOffice());

                assPayment.setAssPaymentGotoDebit(balance);// Think This And Fill

                assPayment.setAssPaymentStatus(0);


                if (ca + ch == payTot + balance + creditpaid) {

                    assPayment.setAssCash(ca);
                    assPayment.setAssCheck(ch);

                    fulpay = ca + ch;

                } else {
                    if (ca > 0) {
                        assPayment.setAssCash(payTot + balance + creditpaid);
                        assPayment.setAssCheck(ch);
                        fulpay = payTot + balance + creditpaid;

                    }
                    if (ch > 0) {
                        assPayment.setAssCash(ca);
                        assPayment.setAssCheck(payTot + balance + creditpaid);
                        fulpay = payTot + balance + creditpaid;
                    }
                }

                if (fromly > 0) {
                    assPayment.setCdBalance(modle.Round.round(-1 * fromly));
                } else {
                    assPayment.setCdBalance(creditpaid);
                }

                assPayment.setAssBank(b_no);
                assPayment.setAssCheckNo(chno);


                Serializable payy = session.save(assPayment);
                AssPayment load = (AssPayment) session.load(AssPayment.class, payy);

                boolean pato = false;

                if (Q1wp > 0 || Q1ap > 0 || Q1pay > 0) {
                    AssPayto payto = new AssPayto();
                    payto.setAssPayment(assPayment);
                    payto.setAssPaytoQno(1);
                    payto.setAssPaytoWarrant(Q1wp);
                    payto.setAssPaytoArrears(Q1ap);
                    payto.setAssPaytoQvalue(Q1pay);
                    payto.setAssPaytoDiscount(Q1discount);
                    payto.setAssPaytoDiscountRate(Q1disrate);
                    payto.setAssPaytoStatus(q1ps);
                    session.save(payto);
                    pato = true;
                }

                if (Q2wp > 0 || Q2ap > 0 || Q2pay > 0) {
                    AssPayto payto = new AssPayto();
                    payto.setAssPayment(assPayment);
                    payto.setAssPaytoQno(2);
                    payto.setAssPaytoWarrant(Q2wp);
                    payto.setAssPaytoArrears(Q2ap);
                    payto.setAssPaytoQvalue(Q2pay);
                    payto.setAssPaytoDiscount(Q2discount);
                    payto.setAssPaytoDiscountRate(Q2disrate);
                    payto.setAssPaytoStatus(q2ps);
                    session.save(payto);
                    pato = true;
                }

                if (Q3wp > 0 || Q3ap > 0 || Q3pay > 0) {
                    AssPayto payto = new AssPayto();
                    payto.setAssPayment(assPayment);
                    payto.setAssPaytoQno(3);
                    payto.setAssPaytoWarrant(Q3wp);
                    payto.setAssPaytoArrears(Q3ap);
                    payto.setAssPaytoQvalue(Q3pay);
                    payto.setAssPaytoDiscount(Q3discount);
                    payto.setAssPaytoDiscountRate(Q3disrate);
                    payto.setAssPaytoStatus(q3ps);
                    session.save(payto);
                    pato = true;
                }

                if (Q4wp > 0 || Q4ap > 0 || Q4pay > 0) {
                    AssPayto payto = new AssPayto();
                    payto.setAssPayment(assPayment);
                    payto.setAssPaytoQno(4);
                    payto.setAssPaytoWarrant(Q4wp);
                    payto.setAssPaytoArrears(Q4ap);
                    payto.setAssPaytoQvalue(Q4pay);
                    payto.setAssPaytoDiscount(Q4discount);
                    payto.setAssPaytoDiscountRate(Q4disrate);
                    payto.setAssPaytoStatus(q4ps);
                    session.save(payto);
                    pato = true;
                }

                if (!pato) {
                    AssPayto payto = new AssPayto();
                    payto.setAssPayment(assPayment);
                    payto.setAssPaytoQno(0);
                    payto.setAssPaytoWarrant(0.0);
                    payto.setAssPaytoArrears(0.0);
                    payto.setAssPaytoQvalue(0.0);
                    payto.setAssPaytoDiscount(0.0);
                    payto.setAssPaytoDiscountRate(0.0);
                    payto.setAssPaytoStatus(0);
                    session.save(payto);
                }


                System.out.println(payy.toString());


                modle.asses.StaticBadu.setPayid("ID : " + rno);
                modle.asses.StaticBadu.setPayTot("Total Rs : " + fulpay + "/=");
                modle.asses.StaticBadu.setCusid(assessment.getCustomer().getIdCustomer());

                BarcodeStatic.subject = "Assessment";
                BarcodeStatic.customerName = assessment.getCustomer().getCusName();
                BarcodeStatic.reTotal = fulpay;
                BarcodeStatic.idRecipt = rno;

                System.out.println("+++++++++++++++++++++++++++++++");
                System.out.println();
                System.out.println(fulpay);
                System.out.println();
                System.out.println("+++++++++++++++++++++++++++++++");


                transaction.commit();
                modle.Allert.notificationGood("Payment Succsess", assessment.getAssessmentNo());

                genarateRisitNoByOfficeAndOder(modle.StaticViews.getLogUser().getOfficeIdOffice(), rn.getIdReceipt());

                clearPay();

                String assessbilltype = KeyVal.getVal("ass_barcode_yes_no");

                System.out.println(assessbilltype);


                pvc.check.setSelected(false);
                pvc.cash.setSelected(false);
                if (assessbilltype.equals("no")) {
                    new DayendController().reprintAssessBill(rno);
                } else {
                    viewId();
                }

                if (ca > 0) {
                    UpdateStatus.updateRecipt(rno + "", 1, 0, 1, ca); // update Recipt Status
                }
                if (ch > 0) {
                    UpdateStatus.updateRecipt(rno + "", 2, 0, 1, ch); // update Recipt Status
                }


                return true;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.out.println("Errorr");
                modle.ErrorLog.writeLog(e.getMessage(), e.getClass().toString(), "pay", "modle.assess");
                modle.Allert.notificationError("Samthing Wrong", e.getMessage());
                transaction.rollback();
                return false;
            } finally {
                session.close();
                System.gc();
            }
        } else {
            return false;
        }

    }

    public void viewId() {


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


//        Parent root;
//        try {
//            root = FXMLLoader.load(getClass().getResource("/view/assess/ViewPayID.fxml"));
//            Stage stage = new Stage();
//            stage.initStyle(StageStyle.TRANSPARENT);
//
//            stage.getIcons().add(new Image("/grafics/info.png"));
//            Scene scene = new Scene(root);
//            scene.setFill(Color.TRANSPARENT);
//            stage.setResizable(false);
//            stage.setScene(scene);
//
//            stage.show();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            //   Logger.getLogger(AssessmangController.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public String genarateRisitNoByOfficeAndOder(int officeid, int idRecipt) {

        int currentYear = GetInstans.getQuater().getCurrentYear();


        String no = "";
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "receipt_code_create.receipt_code\n" +
                    "FROM `receipt_code_create`\n" +
                    "WHERE\n" +
                    "receipt_code_create.application_id = 2 AND\n" +
                    "receipt_code_create.receipt_code_office_id = " + officeid);

            ResultSet data2 = DB.getData("SELECT\n" +
                    "aha.idAHA,\n" +
                    "aha.bankinfo_id\n" +
                    "FROM\n" +
                    "\t`aha`\n" +
                    "WHERE\n" +
                    "aha.aha_status = 1\n" +
                    "AND aha.appcat_id = 2\n" +
                    "AND aha.office_id = '" + officeid + "'");

            int bankinfo_id = 0;

            if (data2.last()) {
                bankinfo_id = data2.getInt("bankinfo_id");
            }

            int xx = 1;
            ResultSet data1 = DB.getData("SELECT\n" +
                    "Max(receipt.oder)\n" +
                    "FROM\n" +
                    "receipt\n" +
                    "WHERE\n" +
                    "receipt.Application_Catagory_idApplication_Catagory = 2 AND\n" +
                    "receipt.office_idOffice = " + officeid + " AND\n" +
                    "EXTRACT(YEAR FROM receipt.receipt_day)= " + currentYear);

            if (data1.last()) {
                xx = data1.getInt("MAX(receipt.oder)");
                xx++;
            }

            if (data.last()) {
                String receipt_code = data.getString("receipt_code");
                no += receipt_code;
            }


            no += currentYear + "/ " + xx;
            conn.DB.setData("UPDATE `receipt`\n" +
                    "SET \n" +
                    " `receipt_print_no` = '" + no + "', \n" +
                    " `oder` = '" + xx + "',\n" +
                    " `office_idOffice` = '" + officeid + "',\n" +
                    " `receipt_account_id` = '" + bankinfo_id + "',\n" +
                    " `receipt_user_id` = '" + modle.StaticViews.getLogUser().getIdUser() + "'\n" +
                    "WHERE\n" +
                    "\t(`idReceipt` = '" + idRecipt + "')");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


        return no;
    }


    public String genarateRisiptNo(Session session) {

//        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        String assbill_text = KeyVal.getVal("assbill_text");
        double x = Double.parseDouble(KeyVal.getVal("assbill_startat"));


        String rn = "";
        try {
            Criteria criteria = session.createCriteria(Receipt.class);
            List<Receipt> list = criteria.add(Restrictions.eq("applicationCatagory", (pojo.ApplicationCatagory) session.load(pojo.ApplicationCatagory.class, 2))).list();
            int size = list.size();

            double y = size + x;

            String format = new DecimalFormat("#").format(y);

            rn = assbill_text + format;


            List receiptPrintNo = session.createCriteria(Receipt.class).add(Restrictions.eq("receiptPrintNo", rn)).list();
            if (receiptPrintNo != null || receiptPrintNo.size() > 0) {
                String rnn = ifDuplicate(session, size, assbill_text, x);
                while (rn.equals(rnn)) {
                    rnn = ifDuplicate(session, size++, assbill_text, x);
                }
                rn = rnn;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        System.out.println("ok");
        return rn;
    }

    public String ifDuplicate(Session session, double size, String text, double x) {
        String rn = "";
        List receiptPrintNo = session.createCriteria(Receipt.class).add(Restrictions.eq("receiptPrintNo", rn)).list();
        if (receiptPrintNo != null || receiptPrintNo.size() > 0) {
            double y = size + x + 1;

            String format = new DecimalFormat("#").format(y);

            rn = text + format;

        }
        return rn;
    }


}
