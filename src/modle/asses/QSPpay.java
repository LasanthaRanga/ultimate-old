package modle.asses;


import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.*;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class QSPpay {


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

    public void cal(int idAssess) {


        quater = modle.GetInstans.getQuater();
        currentDay = quater.getCurrentDay();
        currentQuater = quater.getCurrentQuater();
        systemDate = quater.getSystemDate();
        currentMonth = quater.getCurrentMonth();
        currentYear = quater.getCurrentYear();
        prviasQuater = quater.getPrviasQuater();
        cheackDiscountDate = quater.cheackDiscountDate();

        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().commit();
        session = conn.NewHibernateUtil.getSessionFactory().openSession();


        try {
            assessment = (Assessment) session.get(Assessment.class, idAssess);

            Criteria payCry = session.createCriteria(AssPayment.class);


            if (assessment != null) {


                yearRate = assessment.getAssNature().getAssNatureYearRate();
                warrantRate = assessment.getAssNature().getAssNatureWarrantRate();


                nature = assessment.getAssNature();
                Set<AssAllocation> assAllocations = assessment.getAssAllocations();

                for (AssAllocation assAllocation : assAllocations) {
                    if (assAllocation.getAssAllocationStatus() == 1) {

                        allocationPojo = assAllocation;
                        allocation = assAllocation.getAssAllocation();

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
                    if (assQstart.getAssQstartQuaterNumber() == 1) {
//                        Q4a = assQstart.getAssQstartLqcArreas();
//                        Q4w = assQstart.getAssQstartLqcWarrant();
                    }
                    if (assQstart.getAssQstartQuaterNumber() == 2) {
                        Q1a = assQstart.getAssQstartLqcArreas();
                        Q1w = assQstart.getAssQstartLqcWarrant();
                    }
                    if (assQstart.getAssQstartQuaterNumber() == 3) {
                        Q2a = assQstart.getAssQstartLqcArreas();
                        Q2w = assQstart.getAssQstartLqcWarrant();
                    }
                    if (assQstart.getAssQstartQuaterNumber() == 4) {
                        Q3a = assQstart.getAssQstartLqcArreas();
                        Q3w = assQstart.getAssQstartLqcWarrant();
                    }
                }

                TYA = Q1a + Q2a + Q3a;
                TYW = Q1w + Q2w + Q3w;

                creditBalance = 0.0;
                Set<AssCreditdebit> cds = assessment.getAssCreditdebits();
                for (AssCreditdebit cd : cds) {
                    if (cd.getAssCreditDebitStatus() == 1) {
                        creditBalance = cd.getAssBalance();
                        if (creditBalance < 0) {
                            creditBalance = -1 * cd.getAssBalance();
                        } else {
                            creditBalance = 0.0;
                        }
                    }
                }

                Criteria cryPayhistory = session.createCriteria(pojo.AssPayhistry.class);
                cryPayhistory.add(Restrictions.eq("assessment", assessment));
                cryPayhistory.add(Restrictions.eq("assPayHistryYear", currentYear));
                cryPayhistory.add(Restrictions.eq("assPayHistryStatus", 1));
                List<pojo.AssPayhistry> list1 = cryPayhistory.list();
                pojo.AssPayhistry payhistry = null;
                for (pojo.AssPayhistry object : list1) {
                    payhistry = object;
                }

                Q1paid = 0;
                Q2paid = 0;
                Q3paid = 0;
                Q4paid = 0;

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

                    Q1paid = payhistry.getAssPayHistryQ1();
                    Q2paid = payhistry.getAssPayHistryQ2();
                    Q3paid = payhistry.getAssPayHistryQ3();
                    Q4paid = payhistry.getAssPayHistryQ4();

                    Q1paidDis = payhistry.getAssPayHistryDrq1();
                    Q2paidDis = payhistry.getAssPayHistryDrq2();
                    Q3paidDis = payhistry.getAssPayHistryDrq3();
                    Q4paidDis = payhistry.getAssPayHistryDrq4();

                    Q1Status = payhistry.getAssPayHistryQ1status();
                    Q2Status = payhistry.getAssPayHistryQ2status();
                    Q3Status = payhistry.getAssPayHistryQ3status();
                    Q4Status = payhistry.getAssPayHistryQ4status();


                    if (Q1Status == 1) {

                        b1 = false;

                    }
                    if (Q2Status == 1) {

                        b2 = false;

                    }
                    if (Q3Status == 1) {

                        b3 = false;

                    }
                    if (Q4Status == 1) {

                        b4 = false;

                    }

                } else {

                }

                cry.add(Restrictions.eq("assQstartQuaterNumber", currentQuater));

                List<AssQstart> lastQstartlist = cry.list();

                if (lastQstartlist.size() > 0) {
                    AssQstart lastQstart = lastQstartlist.get(0);
                    lyArrears = lastQstart.getAssQstartLycArreas();
                    lyWarrant = lastQstart.getAssQstartLycWarrant();
                }


                totalAW = TYA + TYW + lyArrears + lyWarrant;
                totalAW = modle.Maths.round2(totalAW);


                //=======================================
                ful = allocation * yearRate / 100;
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

            } else {
                modle.Allert.notificationWorning("Cheack ID", "This id is not available");
            }

            clearPay();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }


    }


    public void clearPay() {

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

    double fulpay;

    public double round(double d) {
        return modle.Maths.round2(d);
    }

    public void WarrantArrirasCal(double payValue) {

        fulpay = payValue;


        clearPay();
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

        }
        //==============================

        QuaterPayCal();
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
                // Balance eka ithuru unoth
            }
        }


    }


    public void Q1() {

        double q1overpaid = Q1paid;

        if (b1) {
            balance = balance + Q1paid;
            if (yarPayQuater * 4 <= balance) {
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
                    Q4pay = balance;
                    Q4discount = 0;
                    Q4disrate = 0;
                    balance = 0;
                }
            } else if (balance >= qpay * 2) {

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
                    Q3pay = balance;
                    Q3discount = 0;
                    Q3disrate = 0;
                    balance = 0;
                }
            } else if (balance >= qpay) {
                q1ps = 1;
                q2ps = 0;
                q3ps = 0;
                q4ps = 0;
                balance = balance - qpay;
                Q1pay = qpay;
                Q1discount = qd;
                Q1disrate = 5;
                if (balance > 0 && balance < qpay) {
                    Q2pay = balance;
                    Q2discount = 0;
                    Q2disrate = 0;
                    balance = 0;
                }
            } else if (balance > 0) {
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
                q1ps = 1;
                q2ps = 0;
                q3ps = 0;
                q4ps = 0;
                Q1pay = quaterValue;
                Q1discount = 0;
                Q1disrate = 0;
                balance = balance - quaterValue;
            } else {

                Q1pay = balance;
                Q1discount = 0;
                Q1disrate = 0;
                balance = 0;
                q1ps = 0;
            }

            if (balance >= qpay * 3) {

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

                    Q4pay = balance;
                    Q4discount = 0;
                    Q4disrate = 0;
                    balance = 0;
                }
            } else if (balance >= qpay) {

                q2ps = 1;
                q3ps = 0;
                q4ps = 0;
                Q2pay = qpay;
                Q2discount = qd;
                Q2disrate = 5;
                balance = balance - qpay;
                if (balance > 0 && balance < qpay) {

                    Q3pay = balance;
                    Q3discount = 0;
                    Q3disrate = 0;
                    balance = 0;
                }
            } else if (balance > 0) {

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

                    Q4pay = balance;
                    Q4discount = 0;
                    Q4disrate = 0;
                    balance = 0;
                }
            } else if (balance >= qpay) {

                q2ps = 1;
                q3ps = 0;
                q4ps = 0;

                Q2pay = qpay;
                Q2discount = qd;
                Q2disrate = 5;
                balance = balance - qpay;
                if (balance > 0 && balance < qpay) {

                    Q3pay = balance;
                    Q3discount = 0;
                    Q3disrate = 0;

                    balance = 0;
                }
            } else if (balance > 0) {

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

                q2ps = 1;
                q3ps = 0;
                q4ps = 0;
                Q2pay = quaterValue;
                Q2discount = 0;
                Q2disrate = 0;
                balance = balance - quaterValue;
            } else {


                Q2pay = balance;
                Q2discount = 0;
                Q2disrate = 0;
                balance = 0;
            }

            balance = balance - quaterValue;
            if (balance >= qpay * 2) {


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


                q3ps = 1;
                q4ps = 0;

                Q3pay = qpay;
                Q3discount = qd;
                Q3disrate = 5;
                balance = balance - qpay;
                if (balance > 0 && balance < qpay) {

                    Q4pay = balance;
                    Q4discount = 0;
                    Q4disrate = 0;
                    balance = 0;
                }
            } else if (balance > 0) {

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


                q3ps = 1;
                q4ps = 0;

                Q3pay = qpay;
                Q3discount = qd;
                Q3disrate = 5;

                balance = balance - qpay;
                if (balance > 0 && balance < qpay) {

                    Q4pay = balance;
                    Q4discount = 0;
                    Q4disrate = 0;
                    balance = 0;
                }
            } else if (balance > 0) {

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

                q4ps = 0;
                Q3pay = quaterValue;
                Q3discount = 0;
                Q3disrate = 0;
                balance = balance - quaterValue;
            } else {

                Q3pay = balance;
                Q3discount = 0;
                Q3disrate = 0;
                balance = 0;
            }
            if (balance >= qpay) {


                q4ps = 1;
                Q4pay = qpay;
                Q4discount = qd;
                Q4disrate = 5;
                balance = balance - qpay;
                if (balance > 0 && balance < qpay) {

                    Q4pay = balance;
                    Q4discount = 0;
                    Q4disrate = 0;
                    balance = 0;
                }
            } else if (balance > 0) {

                Q3pay = balance;
                Q3discount = 0;
                Q3disrate = 0;
                balance = 0;
            }
        } else {
            Q4();
        }
    }

    public void Q4() {
        if (b4) {
            balance = balance + Q4paid;
            if (balance >= qpay) {

                q4ps = 1;
                Q4pay = qpay;
                Q4discount = qd;
                Q4disrate = 5;
                balance = balance - qpay;
            } else if (balance > Q1paid) {

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
                q4ps = 1;
                Q4pay = quaterValue;
                Q4discount = 0;
                Q4disrate = 0;
                balance = balance - quaterValue;
            } else {
                Q4pay = balance;
                Q4discount = 0;
                Q4disrate = 0;
                balance = 0;
            }
        }
    }




    public boolean pay() {


//        String risiptNo = genarateRisiptNo();

        System.out.println(lywPay + " Last Year Warant Pay ");
        System.out.println(lyaPay + "Last Year Arrias Pay");
        System.out.println("++++++++++++++");
        System.out.println(Q1wp + " Quater 1 Warrant Pay");
        System.out.println(Q2wp + " Quater 2 Warrant Pay");
        System.out.println(Q3wp + " Quater 3 Warrant Pay");
        System.out.println(Q4wp + " Quater 4 Warrant Pay");
        System.out.println("+++++++++++++++++++++++");
        System.out.println(Q1ap + "  Q 1 Arrias Pay ");
        System.out.println(Q2ap + "  Q 2 Arrias Pay ");
        System.out.println(Q3ap + "  Q 3 Arrias Pay ");
        System.out.println(Q4ap + "  Q 4 Arrias Pay ");
        System.out.println("");
        System.out.println("=================");


        System.out.println(Q1pay + " Q 1 Pay");
        System.out.println(Q2pay + " Q 2 Pay");
        System.out.println(Q3pay + " Q 3 Pay");
        System.out.println(Q4pay + " Q 4 Pay");
        System.out.println("");
        System.out.println("=================");


        System.out.println(Q1discount + " Q 1 Discount");
        System.out.println(Q2discount + " Q 2 Discount");
        System.out.println(Q3discount + " Q 3 Discount");
        System.out.println(Q4discount + " Q 4 Discount");


        System.out.println("Q 1 Discount Rate " + Q1disrate);
        System.out.println("Q 2 Discount Rate " + Q2disrate);
        System.out.println("Q 3 Discount Rate " + Q3disrate);
        System.out.println("Q 4 Discount Rate " + Q4disrate);

        System.out.println("Paid Status");
        System.out.println("Q1 status " + q1ps);
        System.out.println("Q2 status " + q2ps);
        System.out.println("Q3 status " + q3ps);
        System.out.println("Q4 status " + q4ps);

        System.out.println("CD" + creditpaid + creditBalance);


//        double ca = 0;
//        double ch = 0;
        String bank = "";
        int b_no = 0;
        String chno = "";

        boolean b = false;


        if (b1) {
            Q1pay = Q1pay - Q1paid;
        } else if (b2) {
            Q2pay = Q2pay - Q2paid;
        } else if (b3) {
            Q3pay = Q3pay - Q3paid;
        } else if (b4) {
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

        double payTot = lywPay + lyaPay + Q1wp + Q2wp + Q3wp + Q4wp + Q1ap + Q2ap + Q3ap + Q4ap + Q1pay + Q2pay + Q3pay + Q4pay;

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







                clearPay();


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
            }
        } else {
            return false;
        }
    }





}
