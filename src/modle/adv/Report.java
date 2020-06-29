/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modle.adv;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.geometry.Pos;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.controlsfx.control.Notifications;
import org.hibernate.engine.spi.SessionFactoryImplementor;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class Report {

    private Connection getConnection() {
        try {
            return ((SessionFactoryImplementor) conn.NewHibernateUtil.getSessionFactory()).getConnectionProvider().getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void pringAdvBill(int advid,boolean print) {
        
    
        try {
            String path = "C:\\Ultimate\\Report\\adv\\advrecipt.jrxml";


            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("advID", advid+"");
            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            if (print) {
                JasperPrintManager.printReport(jp, false);
            } else {
                JasperViewer.viewReport(jp, false);
            }

        } catch (JRException jRException) {
            jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }

    public void pringAdvShedule(String day) {


        try {
            String path = "C:\\Ultimate\\Report\\adv\\shedule.jrxml";


            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("day", day);
            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            JasperViewer.viewReport(jp, false);
        } catch (JRException jRException) {
            jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }

}
