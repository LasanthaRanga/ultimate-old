package modle.tradelicens;

import javafx.geometry.Pos;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class TL_Reports {

    private Connection getConnection() {
        try {
            return ((SessionFactoryImplementor) conn.NewHibernateUtil.getSessionFactory()).getConnectionProvider().getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void getPendingList(String idApprovalCat) {
        try {

            String path = "C:\\Ultimate\\Report\\TL\\pendinglist.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("idApCat", idApprovalCat);
            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            JasperViewer.viewReport(jp, false);

        } catch (Exception jRException) {
            jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }


}
