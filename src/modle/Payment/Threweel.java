package modle.Payment;

import javafx.geometry.Pos;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class Threweel {

    private Connection getConnection() {
        try {
            return ((SessionFactoryImplementor) conn.NewHibernateUtil.getSessionFactory()).getConnectionProvider().getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void getVehiclepassReport(String riciptID, boolean print) {

        System.out.println("awa");
        System.out.println(riciptID);

        try {
            String path = "C:\\Ultimate\\Report\\mix\\threeweel.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, String>();
            param.put("idRecipt", riciptID);
            this.getConnection().commit();
            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            if (print) {
                JasperPrintManager.printReport(jp, false);
            } else {
                JasperViewer.viewReport(jp, false);
            }
            System.out.println("print");
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
