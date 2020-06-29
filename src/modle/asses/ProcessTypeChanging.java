package modle.asses;

import com.jfoenix.controls.JFXComboBox;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.Assessment;
import pojo.AssessmentHasProcesstype;
import pojo.Processtype;
import pojo.UserLogin;

/**
 *
 * @author Ranga Rathnayake
 */
public class ProcessTypeChanging {

    public void LoadProcessTypeComboe(JFXComboBox<String> cob_processType) {
        String quary = "SELECT * FROM `processtype`";
        try {
            ResultSet data = conn.DB.getData(quary);
            ObservableList<String> oal = FXCollections.observableArrayList();
            while (data.next()) {
                oal.add(data.getString("Processtype_name"));
            }
            cob_processType.setItems(oal);
        } catch (Exception ex) {
            modle.ErrorLog.writeLog(ex.getMessage(), "ProcessTypeChange", "LoadProcessTypeCombo", "modle.assess");
            ex.printStackTrace();
        }
    }

    public void saveProcessType(int idAssess, String type, String comment) {
        Date systemDate = modle.GetInstans.getQuater().getSystemDate();
        UserLogin log = modle.StaticViews.getLog();

        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction beginTransaction = session.beginTransaction();
        try {
            Processtype pt = (Processtype) session.createCriteria(Processtype.class).add(Restrictions.eq("processtypeName", type)).uniqueResult();
            Assessment asess = (Assessment) session.load(Assessment.class, idAssess);

            Set<AssessmentHasProcesstype> ahpts = asess.getAssessmentHasProcesstypes();
            for (AssessmentHasProcesstype ahpt : ahpts) {
                if (ahpt.getAssessmentHasProcesstypeStatus() == 1) {
                    ahpt.setAssessmentHasProcesstypeStatus(0);
                    session.update(ahpt);
                }
            }
            AssessmentHasProcesstype ashasptype = new AssessmentHasProcesstype();
            ashasptype.setAssessment(asess);
            ashasptype.setProcesstype(pt);
            ashasptype.setAssessmentHasProcesstypeComent(comment);
            ashasptype.setAssessmentHasProcesstypeDate(systemDate);
            ashasptype.setAssessmentHasProcesstypeStatus(1);
          //  ashasptype.setAssessmentHasProcesstypeChangeBy(modle.StaticViews.getLogUser().getIdUser());
            session.save(ashasptype);
            beginTransaction.commit();
            modle.Allert.notificationGood("Process Type Change Compleet", type);
        } catch (Exception e) {
            beginTransaction.rollback();
            modle.ErrorLog.writeLog(e.getMessage(), "ProcessTypeChange", "saveProcessType", "modle.assess");
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

}
