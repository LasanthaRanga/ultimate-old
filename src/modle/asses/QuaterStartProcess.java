package modle.asses;

import com.jfoenix.controls.JFXProgressBar;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javafx.scene.text.Text;
import modle.BUP;
import org.hibernate.Criteria;
import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.AssAllocation;
import pojo.AssPayhistry;

import pojo.AssQstart;
import pojo.Assessment;

public class QuaterStartProcess {

    String getAllAssessment = "SELECT\n"
            + "assessment.idAssessment,\n"
            + "assessment.Customer_idCustomer,\n"
            + "assessment.Ward_idWard,\n"
            + "assessment.Street_idStreet,\n"
            + "assessment.ass_nature_idass_nature,\n"
            + "assessment.ass_discription_idass_discription,\n"
            + "assessment.User_idUser,\n"
            + "assessment.assessment_oder,\n"
            + "assessment.assessment_no,\n"
            + "assessment.assessment_status,\n"
            + "assessment.assessment_syn,\n"
            + "assessment.assessment_comment,\n"
            + "assessment.assessment_obsolete,\n"
            + "ass_nature.idass_nature,\n"
            + "ass_nature.ass_nature_name,\n"
            + "ass_nature.ass_nature_year_rate,\n"
            + "ass_nature.ass_nature_status,\n"
            + "ass_nature.ass_nature_warrant_rate,\n"
            + "assessment_has_processtype.Assessment_has_Processtype_date,\n"
            + "assessment_has_processtype.Assessment_has_Processtype_status,\n"
            + "processtype.idProcesstype,\n"
            + "processtype.Processtype_name,\n"
            + "ass_allocation.idass_allocation,\n"
            + "ass_allocation.ass_allocation,\n"
            + "ass_allocation.ass_allocation_status,\n"
            + "ass_allocation.Assessment_idAssessment,\n"
            + "ass_allocation.ass_allocation_change_date,\n"
            + "ass_allocation.ass_allocation_discription,\n"
            + "ass_allocation.ass_allocation_idUser\n"
            + "FROM\n"
            + "assessment\n"
            + "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n"
            + "LEFT JOIN assessment_has_processtype ON assessment_has_processtype.Assessment_idAssessment = assessment.idAssessment\n"
            + "LEFT JOIN processtype ON assessment_has_processtype.Processtype_idProcesstype = processtype.idProcesstype\n"
            + "LEFT JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n"
            + "WHERE\n"
            + "assessment_has_processtype.Assessment_has_Processtype_status = 1 AND\n"
            + "ass_allocation.ass_allocation_status = 1 AND assessment.assessment_syn = 0";
    String RowCount = "SELECT\n"
            + "COUNT(idAssessment)\n"
            + "FROM\n"
            + "assessment\n"
            + "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n"
            + "LEFT JOIN assessment_has_processtype ON assessment_has_processtype.Assessment_idAssessment = assessment.idAssessment\n"
            + "LEFT JOIN processtype ON assessment_has_processtype.Processtype_idProcesstype = processtype.idProcesstype\n"
            + "LEFT JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n"
            + "WHERE\n"
            + "assessment_has_processtype.Assessment_has_Processtype_status = 1 AND\n"
            + "ass_allocation.ass_allocation_status = 1 AND assessment.assessment_syn = 0";

    public void normalProcessUsingQuary(JFXProgressBar progras, Text txt_qStartText) {

        txt_qStartText.setText(" Start Process ");
        txt_qStartText.setText(" Start Backup Befor Process ");

        if (backup("Befor_QStart_process")) {
            txt_qStartText.setText(" Backup Process Complete");
        } else {
            txt_qStartText.setText(" Backup Process Not Complete");
        }
        //  System.out.println("Ela");
        txt_qStartText.setText(" Run SQl ");
        Quater QClass = new Quater();
        int currentQuater = QClass.getCurrentQuater();
        Date systemDate = QClass.getSystemDate();
        int prviasQuater = QClass.getPrviasQuater();
        int currentYear = QClass.getCurrentYear();
        double rowcount = 0;
        double x = 0;
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        txt_qStartText.setText(" Start Session ");
        //  System.out.println("Ela 2");
        try {
            ResultSet data = conn.DB.getData(RowCount);
            if (data.last()) {
                rowcount = data.getDouble("COUNT(idAssessment)");
                //      System.out.println(rowcount+"-- Row");
            }
            txt_qStartText.setText("Process Count is : " + rowcount + " please wait");
            ResultSet al = conn.DB.getData(getAllAssessment);
            //   System.out.println("ELa 33");

            while (al.next()) {
                //        System.out.println("ELA while");
                x = x + 1;
                double pro = x / rowcount;
                progras.setProgress(pro);
                int idAssessment = al.getInt("idAssessment");
                double allocation = al.getDouble("ass_allocation");
                double yarRate = al.getDouble("ass_nature_year_rate");
                double warrant = al.getDouble("ass_nature_warrant_rate");

                int idProcessType = al.getInt("idProcesstype");

                if (idProcessType == 1) {
                    process2018to2019(session, idAssessment, currentQuater, prviasQuater, systemDate, currentYear);

                    //newQuaterStartWithZeroAmount(session, idAssessment, currentQuater, systemDate, currentQuater);
                }

            }
            transaction.commit();
            txt_qStartText.setText(" Session Commit ");
        } catch (Exception ex) {
            transaction.rollback();
            ex.printStackTrace();
            txt_qStartText.setText(" Error Process ");
        } finally {
            session.close();
            txt_qStartText.setText(" Session Cloose ");
        }

        txt_qStartText.setText(" Start Backup After Process ");
        if (backup("After_QStart_pocess")) {
            txt_qStartText.setText(" After Process Complete");
        } else {
            txt_qStartText.setText(" Backup Process Not Complete");
        }
        txt_qStartText.setText(" Full Process Completed");
    }

    public String newQuaterStartWithZeroAmount(Session session, int assessment, int Cqno, Date qurentDate, int currentYear) {

        try {
            // Assessment assessment1 = (pojo.Assessment) session.load(pojo.Assessment.class, assessment);
//            Query query = session.createQuery("from assessment where idAssessment = :code");
//            query.setParameter("code", assessment);
//            Assessment assessment1 = (pojo.Assessment) query.uniqueResult();
//            AssQstart aq = new pojo.AssQstart();
//            aq.setAssessment(assessment1);
//            aq.setAssQstartQuaterNumber(Cqno);
//            aq.setAssQstartProcessDate(qurentDate);
//            aq.setAssQstartYear(currentYear);
//
//            aq.setAssQstartLyArreas(0.0);
//            aq.setAssQstartLycArreas(0.0);
//
//            aq.setAssQstartLyWarrant(0.0);
//            aq.setAssQstartLycWarrant(0.0);
//
//            aq.setAssQstartLqArreas(0.0);
//            aq.setAssQstartLqcArreas(0.0);
//
//            aq.setAssQstartLqWarrant(0.0);
//            aq.setAssQstartLqcWarrant(0.0);
//            aq.setAssQstartHaveToQpay(0.0);
//
//            aq.setAssQstartQpay(0.0);
//            aq.setAssQstartQdiscont(0.0);
//            aq.setAssQstartQtot(0.0);
//
//            aq.setAssQstartFullTotal(0.0);
//            aq.setAssQstartStatus(1);
//
//            session.save(aq);
//
//            System.out.println(assessment1.getIdAssessment());
//            return "Assessmant ID : " + assessment1.getIdAssessment();
            return null;
        } catch (Exception e) {
            modle.ErrorLog.writeLog(e.getMessage(), "QuaterStartProcess", "newQuaterStartWithZeroAmount", "modle.asess");
            e.printStackTrace();
            return "ERROR  newQuaterStartWithZeroAmount() method";
        } finally {
        }

    }

    public boolean backup(String text) {
        BUP bup = new modle.BUP();
        return bup.backupDataWithDatabase(text);
    }

    public void process2018to2019(Session session, int assessment, int Cqno, int Oqno, Date qurentDate, int currentYear) {

        try {
            Query query = session.createQuery("from Assessment where idAssessment = :code");
            query.setParameter("code", assessment);
            Assessment assessment1 = (Assessment) query.uniqueResult();

            Set<AssQstart> assQstarts = assessment1.getAssQstarts();

            for (AssQstart assQstart : assQstarts) {
                if (Cqno == 1) {// curr
                    if (assQstart.getAssQstartYear() == (currentYear - 1)) {
                        if (assQstart.getAssQstartQuaterNumber() == Oqno) {
                            oldQuaterUpdateAndCreateNew(assQstart, session, assessment1, Cqno, Oqno, qurentDate, currentYear);
                        }
                    }
                } else {
                    if (assQstart.getAssQstartYear() == (currentYear)) {
                        if (assQstart.getAssQstartQuaterNumber() == Oqno) {

                        }
                    }
                }
            }

            System.out.println(assessment1.getIdAssessment());

        } catch (Exception e) {
            modle.ErrorLog.writeLog(e.getMessage(), "QuaterStartProcess", "process2018to2019", "modle.asess");
            e.printStackTrace();
        } finally {
        }

    }

    public void oldQuaterUpdateAndCreateNew(AssQstart assQstart, Session session, Assessment assessment, int Cqno, int Oqno, Date qurentDate, int currentYear) {

       // Criteria cry = session.createCriteria(pojo.AssPayhistry.class);
        
      
      //  cry.add(Restrictions.eq("assessment", assessment));
      //  cry.add(Restrictions.eq("assPayHistryYear", 2018));      
      //  List<pojo.AssPayhistry> list = cry.list();
      
        Query query  = session.createQuery("from AssPayhistry where assPayHistryYear = 2018" +" and assessment = :assessment ");
        query.setParameter("assessment", assessment);
        List<AssPayhistry> list = query.list();
      
      

        Double havetopay = 0.0;

        AssQstart aq = new AssQstart();
        AssPayhistry payhistry = new AssPayhistry();
        payhistry.setAssessment(assessment);

        aq.setAssessment(assessment);
        aq.setAssQstartQuaterNumber(Cqno);
        aq.setAssQstartProcessDate(qurentDate);
        aq.setAssQstartYear(currentYear);
        double over = 0;
        double allocation = 0.0;
        double quaterAmount = 0.0;
        Double assNatureWarrantRate = assessment.getAssNature().getAssNatureWarrantRate();

        Set<AssAllocation> assAllocations = assessment.getAssAllocations();

        for (AssAllocation assAllocation : assAllocations) {
            if (assAllocation.getAssAllocationStatus() == 1) {
                allocation = assAllocation.getAssAllocation();
            }
        }

        quaterAmount = modle.Round.round(allocation * assessment.getAssNature().getAssNatureYearRate() / 100 / 4);

        for (AssPayhistry ph : list) {
            Integer qu = ph.getAssPayHistryQcunt();
            over = ph.getAssPayHistryOver();
            havetopay = assQstart.getAssQstartHaveToQpay();

            Double lyca = assQstart.getAssQstartLycArreas();
            Double lycw = assQstart.getAssQstartLycWarrant();
            Double lqca = assQstart.getAssQstartLqcArreas();
            Double lqcw = assQstart.getAssQstartLqcWarrant();

            double warrant = havetopay * assNatureWarrantRate / 100;
            warrant = modle.Round.round(warrant);

            if (qu == 3) {
                aq.setAssQstartLyArreas(lyca + lqca + havetopay - over);
                aq.setAssQstartLycArreas(lyca + lqca + havetopay - over);

                aq.setAssQstartLqArreas(havetopay - over);
                aq.setAssQstartLqcArreas(havetopay - over);

                aq.setAssQstartLyWarrant(lycw + lqcw + warrant);
                aq.setAssQstartLycWarrant(lycw + lqcw + warrant);

                aq.setAssQstartLqWarrant(warrant);
                aq.setAssQstartLqcWarrant(warrant);

                aq.setAssQstartHaveToQpay(quaterAmount);
                aq.setAssQstartQpay(0.0);
            }

            if (qu == 4) {
                double nextover = over - assQstart.getAssQstartHaveToQpay();

                if (nextover >= 0) {// Dada ne Over Thiyanawa

                    aq.setAssQstartLyArreas(0.0);
                    aq.setAssQstartLycArreas(0.0);

                    aq.setAssQstartLyWarrant(0.0);
                    aq.setAssQstartLycWarrant(0.0);

                    aq.setAssQstartLqArreas(0.0);
                    aq.setAssQstartLqcArreas(0.0);

                    aq.setAssQstartLqWarrant(0.0);
                    aq.setAssQstartLqcWarrant(0.0);

                    aq.setAssQstartHaveToQpay(quaterAmount);
                    aq.setAssQstartQpay(nextover);

                } else { // Dada hedenawa Over nehe
                    double arias = havetopay - over;

                    aq.setAssQstartLyArreas(arias);
                    aq.setAssQstartLycArreas(arias);

                    aq.setAssQstartLyWarrant(warrant);
                    aq.setAssQstartLycWarrant(warrant);

                    aq.setAssQstartLqArreas(arias);
                    aq.setAssQstartLqcArreas(arias);

                    aq.setAssQstartLqWarrant(warrant);
                    aq.setAssQstartLqcWarrant(warrant);

                    aq.setAssQstartHaveToQpay(quaterAmount);
                    aq.setAssQstartQpay(0.0);

                }

                aq.setAssQstartQdiscont(0.0);
                aq.setAssQstartQtot(0.0);

                aq.setAssQstartFullTotal(0.0);
                aq.setAssQstartStatus(1);

                payhistry.setAssPayHistryYear(2019);
                payhistry.setAssPayHistryQcunt(0);
                payhistry.setAssPayHistryDate(qurentDate);
                payhistry.setAssPayHistryDrq1(0.0);
                payhistry.setAssPayHistryDrq2(0.0);
                payhistry.setAssPayHistryDrq3(0.0);
                payhistry.setAssPayHistryDrq4(0.0);
                payhistry.setAssPayHistryStatus(1);
                payhistry.setAssPayHistryComment("0");
                payhistry.setAssPayHistryTotalPayid(0.0);
                payhistry.setAssPayHistryQ1(0.0);
                payhistry.setAssPayHistryQ2(0.0);
                payhistry.setAssPayHistryQ3(0.0);
                payhistry.setAssPayHistryQ4(0.0);
                payhistry.setAssPayHistryOver(over);

                assQstart.setAssQstartStatus(1);

                session.update(assQstart);

                session.save(payhistry);

                session.save(aq);

            }
        }

    }

}
