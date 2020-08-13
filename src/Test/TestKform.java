package Test;

import conn.DB;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TestKform {

    private static Connection getConnection() {
        try {
            return ((SessionFactoryImplementor) conn.NewHibernateUtil.getSessionFactory()).getConnectionProvider().getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        System.out.println("ela ");
        getKformtest("");

    }


    public void printKform() {


    }

    public static void getKformtest(String list) {

        String institute_sinhala = "";
        String institute_english = "";
        String institute_tamil = "";
        String act_sinhala = "";
        String act_english = "";
        String act_tamil = "";
        String year = "";
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "kform_data.idkfom,\n" +
                    "kform_data.`key`,\n" +
                    "kform_data.valueSinhala,\n" +
                    "kform_data.valueEnglish,\n" +
                    "kform_data.valueTemil\n" +
                    "FROM\n" +
                    "kform_data\n");

            while (data.next()) {
                String key = data.getString("key");

                if (key.equals("institute_name")) {
                    institute_sinhala = data.getString("valueSinhala");
                    institute_english = data.getString("valueEnglish");
                    institute_tamil = data.getString("valueTemil");
                }

                if (key.equals("year")) {
                    year = data.getString("valueEnglish");
                }

                if (key.equals("act")) {
                    act_sinhala = data.getString("valueSinhala");
                    act_english = data.getString("valueEnglish");
                    act_tamil = data.getString("valueTemil");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


        try {
            String path = "C:\\Ultimate\\Report\\test_kform.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, Integer>();
            param.put("nameSinhala", institute_sinhala);
            param.put("nameEnglish", institute_english);
            param.put("nameTamil", institute_tamil);
            param.put("year", year);
            param.put("act_s", act_sinhala);
            param.put("act_e", act_english);
            param.put("act_t", act_tamil);




            JasperPrint jp = JasperFillManager.fillReport(jr, param, getConnection());
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
