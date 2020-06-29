package modle.asses;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class CreditProcess {


    public boolean creditBalance(int idPay) {

        int q1s = 0;
        int q2s = 0;
        int q3s = 0;
        int q4s = 0;

        Date systemDate = modle.GetInstans.getQuater().getSystemDate();
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
        System.out.println(1 + " ================");
        try {




            Criteria cry = session.createCriteria(AssPayment.class);

            pojo.AssPayment assPayment = (pojo.AssPayment) session.load(pojo.AssPayment.class,idPay);

          //  List<AssPayment> list = cry.list();

            System.out.println(2 + " ================");


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
                Transaction tr = session.beginTransaction();
                tr.commit();

            return true;
        } catch (Exception e) {
            modle.ErrorLog.writeLog(e.getMessage(), "dayEndProcess", "dayEndProcess", "modle.assess");
            e.printStackTrace();
            return false;

        } finally {
            session.close();
        }
    }


}
