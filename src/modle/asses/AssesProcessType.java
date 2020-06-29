package modle.asses;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Assessment;
import pojo.AssessmentHasProcesstype;
import pojo.Processtype;

public class AssesProcessType {

    public void GenarateAssessmentTypeNormalForAll() {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Date date = new Date();
        try {
            Processtype pt = (Processtype) session.load(Processtype.class, 1);
            List<Assessment> list = session.createCriteria(Assessment.class).list();
            for (Assessment assessment : list) {
                AssessmentHasProcesstype ahp = new AssessmentHasProcesstype();
                ahp.setProcesstype(pt);
                ahp.setAssessment(assessment);
                ahp.setAssessmentHasProcesstypeDate(date);
                ahp.setAssessmentHasProcesstypeStatus(1);
                session.save(ahp);           
                System.out.println(assessment.getIdAssessment());
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
