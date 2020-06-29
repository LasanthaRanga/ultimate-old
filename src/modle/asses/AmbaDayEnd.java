package modle.asses;

import controller.assess.DayendController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import modle.BUP;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.*;
import sun.java2d.pipe.SpanShapeRenderer;

public class AmbaDayEnd {


    public void dayEndProcess() {

        backup("Befor_Day_End_Process");

        int q1s = 0;
        int q2s = 0;
        int q3s = 0;
        int q4s = 0;
        Date systemDate = modle.GetInstans.getQuater().getSystemDate();
        int systemYear = modle.GetInstans.getQuater().getCurrentYear();
        int cquater = modle.GetInstans.getQuater().getCurrentQuater();

        if (cquater <= 3) {
            q1s = 0;
            q2s = 0;
            q3s = 0;
            q4s = 0;
        }
        if (cquater <= 6) {
            q1s = 1;
            q2s = 0;
            q3s = 0;
            q4s = 0;
        }
        if (cquater <= 9) {
            q1s = 1;
            q2s = 1;
            q3s = 0;
            q4s = 0;
        }
        if (cquater <= 12) {
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

        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();

        try {

            Criteria cry = session.createCriteria(AssPayment.class);
            cry.add(Restrictions.eq("assPaymentDate", systemDate));
            cry.add(Restrictions.eq("assPaymentStatus", 0));
            List<AssPayment> list = cry.list();
            for (AssPayment assPayment : list) {

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

//                for (AssPayhistry ph : oldPayHistry) {
//                    ph.setAssPayHistryStatus(0);
//                    session.update(ph);
//                }
                AssPayhistry aph = new AssPayhistry();

                aph.setAssessment(assessment);
                aph.setAssPayHistryQcunt(cquater);
                aph.setAssPayHistryYear(systemYear);
                aph.setAssPayHistryDate(systemDate);
                aph.setAssPayHistryStatus(1);
                aph.setAssPayHistryComment("");
                aph.setAssPayHistryTotalPayid(assPayment.getAssPaymentFullTotal());

                Set<AssPayto> assPaytos = assPayment.getAssPaytos();
                for (AssPayto payto : assPaytos) {
                    Integer qno = payto.getAssPaytoQno();

                    if (qno == 1) {
                        aph.setAssPayHistryQ1status(payto.getAssPaytoStatus());// Q1 Quater kalaya awasan ho gewa smpurna nam 1 nethnam 0
                        aph.setAssPayHistryQ1(payto.getAssPaytoQvalue());
                        aph.setAssPayHistryDrq1(payto.getAssPaytoDiscountRate());
                        q1 = true;
                    } else if (qno == 2) {
                        aph.setAssPayHistryQ2status(payto.getAssPaytoStatus());// Q2 Quater kalaya awasan ho gewa smpurna nam 1 nethnam 0
                        aph.setAssPayHistryQ2(payto.getAssPaytoQvalue());
                        aph.setAssPayHistryDrq2(payto.getAssPaytoDiscountRate());
                        q2 = true;
                    } else if (qno == 3) {
                        aph.setAssPayHistryQ3status(payto.getAssPaytoStatus());// Q3 Quater kalaya awasan ho gewa smpurna nam 1 nethnam 0
                        aph.setAssPayHistryQ3(payto.getAssPaytoQvalue());
                        aph.setAssPayHistryDrq3(payto.getAssPaytoDiscountRate());
                        q3 = true;

                    } else if (qno == 4) {
                        aph.setAssPayHistryQ4status(payto.getAssPaytoStatus());// Q4 Quater kalaya awasan ho gewa smpurna nam 1 nethnam 0
                        aph.setAssPayHistryQ4(payto.getAssPaytoQvalue());
                        aph.setAssPayHistryDrq4(payto.getAssPaytoDiscountRate());
                        q4 = true;
                    }

                }

                if (!q1) {
                    aph.setAssPayHistryQ1status(q1s);// Q1 Quater kalaya awasan ho gewa smpurna nam 1 nethnam 0
                    aph.setAssPayHistryQ1(0.0);
                    aph.setAssPayHistryDrq1(0.0);
                    q1 = true;
                }
                if (!q2) {
                    aph.setAssPayHistryQ2status(q2s);// Q2 Quater kalaya awasan ho gewa smpurna nam 1 nethnam 0
                    aph.setAssPayHistryQ2(0.0);
                    aph.setAssPayHistryDrq2(0.0);
                    q2 = true;
                }
                if (!q3) {
                    aph.setAssPayHistryQ3status(q3s);// Q3 Quater kalaya awasan ho gewa smpurna nam 1 nethnam 0
                    aph.setAssPayHistryQ3(0.0);
                    aph.setAssPayHistryDrq3(0.0);
                    q3 = true;
                }
                if (!q4) {
                    aph.setAssPayHistryQ4status(q3s);// Q4 Quater kalaya awasan ho gewa smpurna nam 1 nethnam 0
                    aph.setAssPayHistryQ4(0.0);
                    aph.setAssPayHistryDrq4(0.0);
                    q4 = true;
                }

                System.out.println("after ============================");
                System.out.println("Q1 ==== " + q1s);
                System.out.println("Q1 ==== " + q2s);
                System.out.println("Q1 ==== " + q3s);
                System.out.println("Q1 ==== " + q4s);

                Double assPaymentGotoDebit = assPayment.getAssPaymentGotoDebit();
                aph.setAssPayHistryOver(assPaymentGotoDebit);

                session.save(aph);

                Set<AssQstart> assQstarts = assessment.getAssQstarts();
                if (assQstarts.size() > 0) {
                    for (AssQstart Qstart : assQstarts) {
                        if (Qstart.getAssQstartYear() == systemYear) {

                            if (Qstart.getAssQstartQuaterNumber() == 1) {
                                for (AssPayto pt : assPaytos) {
                                    Integer qno = pt.getAssPaytoQno();
                                    if (qno == 1) {
                                        //Quater 1  parana arrias warant curunt balance update kirima
                                        Qstart.setAssQstartLqcArreas(Qstart.getAssQstartLqcArreas() - pt.getAssPaytoArrears());
                                        Qstart.setAssQstartLqcWarrant(Qstart.getAssQstartLqcWarrant() - pt.getAssPaytoWarrant());
                                    }
                                }
                            }

                            if (Qstart.getAssQstartQuaterNumber() == 2) {
                                for (AssPayto pt : assPaytos) {
                                    Integer qno = pt.getAssPaytoQno();
                                    if (qno == 2) {
                                        //Quater 2 parana arrias warant curunt balance update kirima
                                        Qstart.setAssQstartLqcArreas(Qstart.getAssQstartLqcArreas() - pt.getAssPaytoArrears());
                                        Qstart.setAssQstartLqcWarrant(Qstart.getAssQstartLqcWarrant() - pt.getAssPaytoWarrant());
                                    }
                                }
                            }

                            if (Qstart.getAssQstartQuaterNumber() == 3) {
                                for (AssPayto pt : assPaytos) {
                                    Integer qno = pt.getAssPaytoQno();
                                    if (qno == 3) {
                                        //Quater 2 parana arrias warant curunt balance update kirima
                                        Qstart.setAssQstartLqcArreas(Qstart.getAssQstartLqcArreas() - pt.getAssPaytoArrears());
                                        Qstart.setAssQstartLqcWarrant(Qstart.getAssQstartLqcWarrant() - pt.getAssPaytoWarrant());
                                    }
                                }
                            }

                            if (Qstart.getAssQstartQuaterNumber() == 4) {
                                for (AssPayto pt : assPaytos) {
                                    Integer qno = pt.getAssPaytoQno();
                                    if (qno == 4) {
                                        //Quater 2 parana arrias warant curunt balance update kirima
                                        Qstart.setAssQstartLqcArreas(Qstart.getAssQstartLqcArreas() - pt.getAssPaytoArrears());
                                        Qstart.setAssQstartLqcWarrant(Qstart.getAssQstartLqcWarrant() - pt.getAssPaytoWarrant());
                                    }
                                }
                            }

                            if (Qstart.getAssQstartQuaterNumber() == cquater) {

                                //Q start
                                Qstart.setAssQstartLycArreas(Qstart.getAssQstartLycArreas() - assPayment.getAssPaymentLyArrears());
                                Qstart.setAssQstartLycWarrant(Qstart.getAssQstartLycWarrant() - assPayment.getAssPaymentLyWarrant());
                                for (AssPayto pt : assPaytos) {
                                    Integer qno = pt.getAssPaytoQno();
                                    if (qno == cquater) {
                                        //Quater 2 parana arrias warant curunt balance update kirima
                                        Qstart.setAssQstartQpay(pt.getAssPaytoQvalue());
                                        Qstart.setAssQstartQdiscont(pt.getAssPaytoDiscount());
                                        Qstart.setAssQstartQtot(pt.getAssPaytoQvalue());
                                        Qstart.setAssQstartFullTotal(assPayment.getAssPaymentFullTotal());
                                        if (pt.getAssPaytoDiscount() > 0) {
                                            Qstart.setAssQstartHaveToQpay(0.0);// Have To pay Gana Update Kala Discunt Dila sampurnayen Geuwanam 0 Kala
                                        } else {
                                            Qstart.setAssQstartHaveToQpay(modle.Maths.round2(Qstart.getAssQstartHaveToQpay() - pt.getAssPaytoQvalue()));// Gewapu gana adukarala demma
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
                Transaction tr = session.beginTransaction();
                tr.commit();
            }

        } catch (Exception e) {
            modle.ErrorLog.writeLog(e.getMessage(), "dayEndProcess", "dayEndProcess", "modle.assess");
            e.printStackTrace();

        } finally {
            session.close();

        }

        backup("After_Day_End_Process");

    }

    public void dayEndProcessForOne(int idPay, Date day) {

        //   backup("Befor_Day_End_Process");
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

        try {

            Receipt re = (Receipt) session.load(Receipt.class, idPay);
            re.setReceiptStatus(1);
            session.update(re);
            Criteria cry = session.createCriteria(AssPayment.class);
            cry.add(Restrictions.eq("assPaymentDate", systemDate));
            cry.add(Restrictions.eq("assPaymentStatus", 0));
            cry.add(Restrictions.eq("receipt", re));
            List<AssPayment> list = cry.list();

            for (AssPayment assPayment : list) {

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

                for (AssPayhistry assPayhistry : oldPayHistry) {
                    aphold = assPayhistry;

                }

                if (aphold != null) {

                    oq1s = aphold.getAssPayHistryQ1status();
                    oq2s = aphold.getAssPayHistryQ2status();
                    oq3s = aphold.getAssPayHistryQ3status();
                    oq4s = aphold.getAssPayHistryQ4status();

                    q1pay = aphold.getAssPayHistryQ1();
                    q2pay = aphold.getAssPayHistryQ2();
                    q3pay = aphold.getAssPayHistryQ3();
                    q4pay = aphold.getAssPayHistryQ4();

                }

//                for (AssPayhistry ph : oldPayHistry) {
//                    ph.setAssPayHistryStatus(0);
//                    session.update(ph);
//                }


                //credit debit update
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

                for (AssPayto payto : assPaytos) {
                    Integer qno = payto.getAssPaytoQno();

                    if (qno == 1) {

                        aph.setAssPayHistryQ1status(payto.getAssPaytoStatus());// Q1 Quater kalaya awasan ho gewa smpurna nam 1 nethnam 0

                        if (oq1s == 0) {
                            q1pay = payto.getAssPaytoQvalue() + q1pay;

                        } else {
                            q1pay = payto.getAssPaytoQvalue();
                        }
                        aph.setAssPayHistryQ1(q1pay);
                        aph.setAssPayHistryDrq1(payto.getAssPaytoDiscountRate());
                        q1 = true;

                    } else if (qno == 2) {
                        aph.setAssPayHistryQ2status(payto.getAssPaytoStatus());// Q2 Quater kalaya awasan ho gewa smpurna nam 1 nethnam 0
                        if (oq2s == 0) {
                            q2pay = payto.getAssPaytoQvalue() + q2pay;

                        } else {
                            q2pay = payto.getAssPaytoQvalue();
                        }
                        aph.setAssPayHistryQ2(q2pay);

                        aph.setAssPayHistryDrq2(payto.getAssPaytoDiscountRate());
                        q2 = true;
                    } else if (qno == 3) {
                        aph.setAssPayHistryQ3status(payto.getAssPaytoStatus());// Q3 Quater kalaya awasan ho gewa smpurna nam 1 nethnam 0
                        if (oq3s == 0) {
                            q3pay = payto.getAssPaytoQvalue() + q3pay;

                        } else {
                            q3pay = payto.getAssPaytoQvalue();
                        }
                        aph.setAssPayHistryQ3(q3pay);
                        aph.setAssPayHistryDrq3(payto.getAssPaytoDiscountRate());
                        q3 = true;

                    } else if (qno == 4) {
                        aph.setAssPayHistryQ4status(payto.getAssPaytoStatus());// Q4 Quater kalaya awasan ho gewa smpurna nam 1 nethnam 0
                        if (oq4s == 0) {
                            q4pay = payto.getAssPaytoQvalue() + q4pay;

                        } else {
                            q4pay = payto.getAssPaytoQvalue();
                        }
                        aph.setAssPayHistryQ4(q4pay);
                        aph.setAssPayHistryDrq4(payto.getAssPaytoDiscountRate());
                        q4 = true;
                    }

                }

                if (!q1) {
//                    if (aphold == null) {
//                        aph.setAssPayHistryQ1status(q1s);
//                    } else {
//                        if (cquater == 1) {
//
//                        } else if (cquater == 2) {
//
//                        } else if (cquater == 3) {
//
//                        } else if (cquater == 4) {
//
//                        }
//                    }

                    aph.setAssPayHistryQ1status(q1s);// Q1 Quater kalaya awasan ho gewa smpurna nam 1 nethnam 0
                    aph.setAssPayHistryQ1(0.0);
                    aph.setAssPayHistryDrq1(0.0);
                    q1 = true;
                }
                if (!q2) {
                    if (cquater == 1) {

                    }
                    aph.setAssPayHistryQ2status(q2s);// Q2 Quater kalaya awasan ho gewa smpurna nam 1 nethnam 0
                    aph.setAssPayHistryQ2(0.0);
                    aph.setAssPayHistryDrq2(0.0);
                    q2 = true;
                }
                if (!q3) {
                    if (cquater == 1) {

                    }
                    aph.setAssPayHistryQ3status(q3s);// Q3 Quater kalaya awasan ho gewa smpurna nam 1 nethnam 0
                    aph.setAssPayHistryQ3(0.0);
                    aph.setAssPayHistryDrq3(0.0);
                    q3 = true;
                }
                if (!q4) {
                    if (cquater == 1) {

                    }
                    aph.setAssPayHistryQ4status(q4s);// Q4 Quater kalaya awasan ho gewa smpurna nam 1 nethnam 0
                    aph.setAssPayHistryQ4(0.0);
                    aph.setAssPayHistryDrq4(0.0);
                    q4 = true;
                }

                System.out.println("after ============================");
                System.out.println("Q1 ==== " + q1s);
                System.out.println("Q1 ==== " + q2s);
                System.out.println("Q1 ==== " + q3s);
                System.out.println("Q1 ==== " + q4s);

                Double assPaymentGotoDebit = assPayment.getAssPaymentGotoDebit();
                aph.setAssPayHistryOver(assPaymentGotoDebit);

                session.save(aph);

                Set<AssQstart> assQstarts = assessment.getAssQstarts();
                if (assQstarts.size() > 0) {
                    for (AssQstart Qstart : assQstarts) {
                        if (Qstart.getAssQstartYear() == systemYear) {

                            if (Qstart.getAssQstartQuaterNumber() == 1) {
                                for (AssPayto pt : assPaytos) {
                                    Integer qno = pt.getAssPaytoQno();
                                    if (qno == 1) {
                                        //Quater 1  parana arrias warant curunt balance update kirima
                                        Qstart.setAssQstartLqcArreas(Qstart.getAssQstartLqcArreas() - pt.getAssPaytoArrears());
                                        Qstart.setAssQstartLqcWarrant(Qstart.getAssQstartLqcWarrant() - pt.getAssPaytoWarrant());
                                    }
                                }
                            }

                            if (Qstart.getAssQstartQuaterNumber() == 2) {
                                for (AssPayto pt : assPaytos) {
                                    Integer qno = pt.getAssPaytoQno();
                                    if (qno == 2) {
                                        //Quater 2 parana arrias warant curunt balance update kirima
                                        Qstart.setAssQstartLqcArreas(Qstart.getAssQstartLqcArreas() - pt.getAssPaytoArrears());
                                        Qstart.setAssQstartLqcWarrant(Qstart.getAssQstartLqcWarrant() - pt.getAssPaytoWarrant());
                                    }
                                }
                            }

                            if (Qstart.getAssQstartQuaterNumber() == 3) {
                                for (AssPayto pt : assPaytos) {
                                    Integer qno = pt.getAssPaytoQno();
                                    if (qno == 3) {
                                        //Quater 2 parana arrias warant curunt balance update kirima
                                        Qstart.setAssQstartLqcArreas(Qstart.getAssQstartLqcArreas() - pt.getAssPaytoArrears());
                                        Qstart.setAssQstartLqcWarrant(Qstart.getAssQstartLqcWarrant() - pt.getAssPaytoWarrant());
                                    }
                                }
                            }

                            if (Qstart.getAssQstartQuaterNumber() == 4) {
                                for (AssPayto pt : assPaytos) {
                                    Integer qno = pt.getAssPaytoQno();
                                    if (qno == 4) {
                                        //Quater 2 parana arrias warant curunt balance update kirima
                                        Qstart.setAssQstartLqcArreas(Qstart.getAssQstartLqcArreas() - pt.getAssPaytoArrears());
                                        Qstart.setAssQstartLqcWarrant(Qstart.getAssQstartLqcWarrant() - pt.getAssPaytoWarrant());
                                    }
                                }
                            }

                            if (Qstart.getAssQstartQuaterNumber() == cquater) {

                                //Q start
                                Qstart.setAssQstartLycArreas(Qstart.getAssQstartLycArreas() - assPayment.getAssPaymentLyArrears());
                                Qstart.setAssQstartLycWarrant(Qstart.getAssQstartLycWarrant() - assPayment.getAssPaymentLyWarrant());
                                for (AssPayto pt : assPaytos) {
                                    Integer qno = pt.getAssPaytoQno();
                                    if (qno == cquater) {
                                        //Quater 2 parana arrias warant curunt balance update kirima
                                        Qstart.setAssQstartQpay(pt.getAssPaytoQvalue());
                                        Qstart.setAssQstartQdiscont(pt.getAssPaytoDiscount());
                                        Qstart.setAssQstartQtot(pt.getAssPaytoQvalue());
                                        Qstart.setAssQstartFullTotal(assPayment.getAssPaymentFullTotal());
                                        if (pt.getAssPaytoDiscount() > 0) {
                                            Qstart.setAssQstartHaveToQpay(0.0);// Have To pay Gana Update Kala Discunt Dila sampurnayen Geuwanam 0 Kala
                                        } else {
                                            Qstart.setAssQstartHaveToQpay(modle.Maths.round2(Qstart.getAssQstartHaveToQpay() - pt.getAssPaytoQvalue()));// Gewapu gana adukarala demma
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
                Transaction tr = session.beginTransaction();
                tr.commit();
            }

        } catch (Exception e) {
            modle.ErrorLog.writeLog(e.getMessage(), "dayEndProcess", "dayEndProcess", "modle.assess");
            e.printStackTrace();

        } finally {
            session.close();

        }

        //    backup("After_Day_End_Process");
    }

    public boolean backup(String text) {
        BUP bup = new modle.BUP();
        return bup.backupDataWithDatabase(text);
    }

}

