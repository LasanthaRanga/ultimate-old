package modle.asses;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.util.Date;
import java.util.Set;
import javafx.fxml.FXML;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.AssCreditdebit;
import pojo.AssPayhistry;
import pojo.AssQstart;
import pojo.Assessment;

public class OldDataSave {

    public void saveOldData(int id, double arrias, double warrant, double over, double last, int paid) {

        Date date = new Quater().getSystemDate();

        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Assessment assessment = (Assessment) session.load(Assessment.class, id);

            //Qstart
            Set<AssQstart> assQstarts = assessment.getAssQstarts();
            for (AssQstart assQstart : assQstarts) {
                if (assQstart.getAssQstartStatus() == 1) {

                    System.out.println(arrias);
                    assQstart.setAssQstartLqArreas(arrias);
                    assQstart.setAssQstartLqcArreas(arrias);

                    assQstart.setAssQstartLqWarrant(warrant);
                    assQstart.setAssQstartLqcWarrant(warrant);
                    System.out.println(warrant);

                    assQstart.setAssQstartHaveToQpay(last);
                    System.out.println(last);

                    session.update(assQstart);
                    System.out.println("Quater Eka Update Una");
                }
            }

            //Credit Debit
            Set<AssCreditdebit> assCreditdebits = assessment.getAssCreditdebits();
            if (assCreditdebits.size() > 0) {
                for (AssCreditdebit debit : assCreditdebits) {
                    if (debit.getAssCreditDebitStatus() == 1) {
                        debit.setAssCreditDebitDiscription("Old Data To New Software");
                        debit.setAssCreditDebitDate(date);
                        debit.setAssCreditDebitCd(1);
                        debit.setAssCreditDebitAmount(over);
                        debit.setAssBalance(over);
                        debit.setAssCreditDebitStatus(1);
                        session.update(debit);
                    }
                }
            } else {
                AssCreditdebit debit = new AssCreditdebit(assessment);
                debit.setAssCreditDebitDiscription("Old Data To New Software");
                debit.setAssCreditDebitDate(date);
                debit.setAssCreditDebitCd(1);
                debit.setAssCreditDebitAmount(over);
                debit.setAssBalance(over);
                debit.setAssCreditDebitStatus(1);
                session.save(debit);
            }

            //Pay History
            Set<AssPayhistry> assPayhistries = assessment.getAssPayhistries();
            if (assPayhistries.size() > 0) {
                for (AssPayhistry payhistry : assPayhistries) {
                    payhistry.setAssessment(assessment);
                    payhistry.setAssPayHistryYear(2018);
                    payhistry.setAssPayHistryQcunt(paid);
                    payhistry.setAssPayHistryDate(date);
                    payhistry.setAssPayHistryDrq1(0.0);
                    payhistry.setAssPayHistryDrq2(0.0);
                    payhistry.setAssPayHistryDrq3(0.0);
                    payhistry.setAssPayHistryDrq4(0.0);
                    payhistry.setAssPayHistryStatus(1);
                    payhistry.setAssPayHistryComment("");
                    payhistry.setAssPayHistryTotalPayid(0.0);
                    payhistry.setAssPayHistryQ1(0.0);
                    payhistry.setAssPayHistryQ2(0.0);
                    payhistry.setAssPayHistryQ3(0.0);
                    payhistry.setAssPayHistryQ4(0.0);
                    payhistry.setAssPayHistryOver(over);
                    session.update(payhistry);
                }
            } else {
                AssPayhistry payhistry = new AssPayhistry();
                payhistry.setAssessment(assessment);
                payhistry.setAssPayHistryYear(2018);
                payhistry.setAssPayHistryQcunt(paid);
                payhistry.setAssPayHistryDate(date);
                payhistry.setAssPayHistryDrq1(0.0);
                payhistry.setAssPayHistryDrq2(0.0);
                payhistry.setAssPayHistryDrq3(0.0);
                payhistry.setAssPayHistryDrq4(0.0);
                payhistry.setAssPayHistryStatus(1);
                payhistry.setAssPayHistryComment("");
                payhistry.setAssPayHistryTotalPayid(0.0);
                payhistry.setAssPayHistryQ1(0.0);
                payhistry.setAssPayHistryQ2(0.0);
                payhistry.setAssPayHistryQ3(0.0);
                payhistry.setAssPayHistryQ4(0.0);
                payhistry.setAssPayHistryOver(over);
                session.save(payhistry);

            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }

    }

    public void getOldData(int id, JFXTextField txt_arrears, JFXTextField txt_warrant, JFXTextField txt_over, JFXCheckBox check_paied, JFXTextField txt_last) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().commit();
        try {

            Assessment assessment = (Assessment) session.load(Assessment.class, id);
            Set<AssPayhistry> assPayhistries = assessment.getAssPayhistries();
            if (assPayhistries.size() > 0) {
                for (AssPayhistry hitory : assPayhistries) {
                    if (hitory.getAssPayHistryQcunt() == 4) {
                        check_paied.setSelected(true);
                    } else {
                        check_paied.setSelected(false);
                    }
                }
                Set<AssQstart> assQstarts = assessment.getAssQstarts();
                for (AssQstart Qs : assQstarts) {
                    Double arrias = Qs.getAssQstartLqArreas();
                    txt_arrears.setText(arrias + "");

                    Double warrant = Qs.getAssQstartLqWarrant();
                    txt_warrant.setText(warrant + "");
                    Double have = Qs.getAssQstartHaveToQpay();
               
                    txt_last.setText(have + "");

                }
                Set<AssCreditdebit> assCreditdebits = assessment.getAssCreditdebits();
                for (AssCreditdebit debit : assCreditdebits) {
                    Double deb = debit.getAssCreditDebitAmount();
                    txt_over.setText(deb + "");

                }
            } else {
                check_paied.setSelected(false);
                txt_arrears.setText(null);
                txt_warrant.setText(null);
                txt_over.setText(null);
                txt_last.setText(null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
