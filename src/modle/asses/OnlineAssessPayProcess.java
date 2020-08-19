package modle.asses;

import conn.DB;
import controller.assess.DayendController;
import controller.payment.UpdateStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import modle.GetInstans;
import modle.KeyVal;
import modle.StaticViews;
import modle.popup.BarcodeStatic;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.*;
import sun.java2d.pipe.SpanShapeRenderer;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

public class OnlineAssessPayProcess {

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

    pojo.Assessment assessment;
    AssAllocation allocationPojo;
    pojo.AssNature nature;

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


    public int getCurrentQuater(Date date) {
        int q = 0;
        Date sd = date;
        if (sd != null) {
            int month = Integer.parseInt(new SimpleDateFormat("MM").format(sd));
            if (month <= 3) {
                q = 1;
            } else if (month <= 6) {
                q = 2;
            } else if (month <= 9) {
                q = 3;
            } else if (month <= 12) {
                q = 4;
            }
        }
        //    System.out.println(q);
        return q;
    }

    public int getPrviasQuater(Date day) {
        int currentQuater = getCurrentQuater(day);
        int pq = 0;
        if (currentQuater > 0 && currentQuater <= 4) {
            pq = currentQuater - 1;
            if (pq == 0) {
                pq = 4;
            }
        }
        return pq;
    }

    public int cheackDiscountDate(int currentDay, int currentMonth) {
//        int currentDay = getCurrentDay();
//        int currentMonth = getCurrentMonth();

        if (currentMonth == 1 && currentDay <= 31) {
            return 1;
        } else if (currentMonth == 4 && currentDay <= 30) {
            return 2;
        } else if (currentMonth == 7 && currentDay <= 31) {
            return 3;
        } else if (currentMonth == 10 && currentDay <= 31) {
            return 4;
        } else {
            return 0;
        }

    }

    public int getCurrentYear(Date day) {
        int y = 0;
        Date sd = day;
        if (sd != null) {
            y = Integer.parseInt(new SimpleDateFormat("yyyy").format(sd));
        }
        // System.out.println(y);
        return y;
    }

    public int getCurrentMonth(Date day) {
        int m = 0;
        Date sd = day;
        if (sd != null) {
            m = Integer.parseInt(new SimpleDateFormat("MM").format(sd));
        }
        //  System.out.println(m);
        return m;
    }

    public int getCurrentDay(Date day) {
        int d = 0;
        Date sd = day;
        if (sd != null) {
            d = Integer.parseInt(new SimpleDateFormat("dd").format(sd));
        }
        //  System.out.println(d);
        return d;
    }


    public void cal(int idAssess, Date date) {

        quater = modle.GetInstans.getQuater();

        currentDay = getCurrentDay(date);

        currentQuater = getCurrentQuater(date);

        systemDate = date;

        currentMonth = getCurrentMonth(date);

        currentYear = getCurrentYear(date);

        prviasQuater = getPrviasQuater(date);

        cheackDiscountDate = cheackDiscountDate(currentDay, currentMonth);


        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().commit();
        session = conn.NewHibernateUtil.getSessionFactory().openSession();

        boolean b = false; // ada dawase payment ekk kara thibe nam false


        try {
            System.out.println(idAssess + "  ====");
            assessment = (Assessment) session.get(Assessment.class, idAssess);


            assessment = (pojo.Assessment) session.createCriteria(pojo.Assessment.class).add(Restrictions.eq("idAssessment", idAssess)).uniqueResult();

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

                    if (assessment != null) {


                        yearRate = modle.Round.round(assessment.getAssNature().getAssNatureYearRate()); // NEW ROUND
                        warrantRate = modle.Round.round(assessment.getAssNature().getAssNatureWarrantRate()); // NEW ROUND


                        nature = assessment.getAssNature();
                        Set<AssAllocation> assAllocations = assessment.getAssAllocations();

                        for (AssAllocation assAllocation : assAllocations) {
                            if (assAllocation.getAssAllocationStatus() == 1) {

                                allocationPojo = assAllocation;
                                allocation = modle.Round.round(assAllocation.getAssAllocation());// NEW ROUND


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

                                } else {
                                    fromly = 0;

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

                        for (pojo.AssPayhistry object : list1) {
                            payhistry = object;
                            Q1paid += modle.Round.round(payhistry.getAssPayHistryQ1());
                            Q2paid += modle.Round.round(payhistry.getAssPayHistryQ2());
                            Q3paid += modle.Round.round(payhistry.getAssPayHistryQ3());
                            Q4paid += modle.Round.round(payhistry.getAssPayHistryQ4());
                        }


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

                            }
                            if (Q2Status == 1 || currentQuater > 2) {
//                            modle.Allert.notificationError("Q2", "not pay");
                                b2 = false;

                            }
                            if (Q3Status == 1 || currentQuater > 3) {
//                            modle.Allert.notificationError("Q3", "not pay");
                                b3 = false;

                            }
                            if (Q4Status == 1) {
//                            modle.Allert.notificationError("Q4", "not pay");
                                b4 = false;

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


                        totalAW = TYA + TYW + lyArrears + lyWarrant;
                        totalAW = modle.Maths.round2(totalAW);


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

    ObservableList<TablePay> oal = FXCollections.observableArrayList();

    double fulpay = 00;

    public void WarrantArrirasCal(double payValue) {

        fulpay = payValue;

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

    public int pay(int idOnPay) {

        int re = 0;

        System.out.println("CD" + creditpaid + creditBalance);


        double ca = 0;
        double ch = 0;
        String bank = "";
        int b_no = 0;
        String chno = "";

        boolean b = true;


        if (b1) {
            if (Q1pay > 0) {
                Q1pay = Q1pay - Q1paid;
            }
        }
        if (b2) {

            if (Q2pay > 0) {
                Q2pay = Q2pay - Q2paid;
            }
        }
        if (b3) {
            if (Q3pay > 0) {
                Q3pay = Q3pay - Q3paid;
            }

        }
        if (b4) {
            if (Q4pay > 0) {
                Q4pay = Q4pay - Q4paid;
            }
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
                receipt.setReceiptTotal(fulpay);

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


                fulpay = payTot + balance + creditpaid;


                if (fromly > 0) {
                    assPayment.setCdBalance(modle.Round.round(-1 * fromly));
                } else {
                    assPayment.setCdBalance(creditpaid);
                }


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


                //   modle.asses.StaticBadu.setPayid("ID : " + rno);
                //   modle.asses.StaticBadu.setPayTot("Total Rs : " + fulpay + "/=");
                //   modle.asses.StaticBadu.setCusid(assessment.getCustomer().getIdCustomer());

                //   BarcodeStatic.subject = "Assessment";
                //  BarcodeStatic.customerName = assessment.getCustomer().getCusName();
                //   BarcodeStatic.reTotal = fulpay;
                //  BarcodeStatic.idRecipt = rno;


                transaction.commit();
                session.beginTransaction().commit();

                System.out.println("+++++++++++++++++++++++++++++++");
                System.out.println();
                System.out.println("ID : " + rno);
                System.out.println(fulpay);
                System.out.println();
                System.out.println("+++++++++++++++++++++++++++++++");


                genarateRisitNoByOfficeAndOder(1, rn.getIdReceipt(), idOnPay);


                re = Integer.parseInt(rno);
                //  UpdateStatus.updateRecipt(rno + "", 5, 0, 1, payTot); // update Recipt Status
                clearPay();

                return re;

            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
                return 0;
            } finally {
                session.close();
                System.gc();
            }
        } else {
            return 0;
        }

    }


    public String genarateRisitNoByOfficeAndOder(int officeid, int idRecipt, int onlineid) {

        System.out.println("Genarate Recipt No");
        int currentYear = GetInstans.getQuater().getCurrentYear();


        String no = "";
        try {

            ResultSet data = DB.getData("SELECT\n" +
                    "receipt_code_create.receipt_code\n" +
                    "FROM\n" +
                    "receipt_code_create\n" +
                    "WHERE\n" +
                    "receipt_code_create.receipt_code_id = 1000");

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
                    "Max(online_pay.oder)\n" +
                    "FROM\n" +
                    "online_pay\n" +
                    "WHERE\n" +
                    "online_pay.appcat_id = 2 AND\n" +
                    "YEAR(online_pay.date) = " + currentYear);

            if (data.last()) {
                no = data.getString("receipt_code");
            }

            if (data1.last()) {
                xx = data1.getInt("Max(online_pay.oder)");
                xx++;
            }


            no += currentYear + "/ " + xx;
            int i = DB.setData("UPDATE `receipt`\n" +
                    "SET \n" +
                    " `receipt_print_no` = '" + no + "', \n" +
                    " `oder` = '" + xx + "',\n" +
                    " `office_idOffice` = '" + officeid + "',\n" +
                    " `receipt_account_id` = '" + bankinfo_id + "',\n" +
                    " `receipt_user_id` = '" + StaticViews.getLogUser().getIdUser() + "',\n" +
                    " `income_expense` = 1,\n" +
                    " `cross_recipt_or_voucher` = 1, \n" +
                    " `pay_type` = 5,\n" +
                    " `amount` = " + fulpay + " \n" +
                    " WHERE\n" +
                    "\t(`idReceipt` = '" + idRecipt + "')");

            System.out.println("Updated Recipt ------------>    " + i);


            conn.DB.setData("UPDATE `online_pay` \n" +
                    "SET \n" +
                    "`status` = 2,\n" +
                    "`description` = 'Completed',\n" +
                    "`oder` = '" + xx + "' \n" +
                    "WHERE\n" +
                    "\t`idOnPaid` = " + onlineid);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        System.out.println(no);
        return no;
    }


    public boolean dayEnd(int idRecipt) {
        boolean re = false;
        Date date = new Date();
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        String rdate = new SimpleDateFormat("yyyy-MM-dd").format(systemDate);

        try {


            String qu = "INSERT INTO `de` (\n" +
                    "\t`receipt_id`,\n" +
                    "\t`appcat_id`,\n" +
                    "\t`user_id`,\n" +
                    "\t`staus`,\n" +
                    "\t`dayendtime`,\n" +
                    "\t`comment`,`receipt_date` \n" +
                    ")\n" +
                    "VALUES\n" +
                    "\t(\n" +
                    "\t\t'" + idRecipt + "',\n" +
                    "\t\t'2',\n" +
                    "\t\t'" + modle.StaticViews.getLogUser().getIdUser() + "',\n" +
                    "\t\t'" + 0 + "',\n" +
                    "\t\t'" + format + "',\n" +
                    "\t\t'Send To Day End','" + rdate + "'\n" +
                    "\t)";

            conn.DB.setData(qu);

            ResultSet data = DB.getData("SELECT\n" +
                    "\tde.idde,\n" +
                    "\tde.receipt_id,\n" +
                    "\tde.appcat_id,\n" +
                    "\tde.user_id,\n" +
                    "\tde.staus,\n" +
                    "\tde.dayendtime,\n" +
                    "\tde.`comment`,\n" +
                    "\tde.receipt_date\n" +
                    "FROM\n" +
                    "\t`de`\n" +
                    "WHERE\n" +
                    "de.staus = 0 AND\n" +
                    "de.receipt_id = " + idRecipt);

            int idde = 0;
            if (data.last()) {
                idde = data.getInt("idde");
            }

            if (new modle.asses.DayEndProcess().dayEndProcessForOne(idRecipt, systemDate)) {

                conn.DB.setData("UPDATE `de`\n"
                        + "SET \n"
                        + " `staus` = '1' \n"
                        + "WHERE\n"
                        + "\t(`idde` = '" + idde + "')");
                re = true;
            } else {
                //  conn.DB.setData("DELETE from de WHERE idde = " + idde);
                modle.Allert.notificationWorning("Day End Not Completed", "Please Recheck  -  " + idde);
                re = false;
                int i1 = DB.setData("DELETE from de WHERE staus = 0");
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return re;

    }


}
