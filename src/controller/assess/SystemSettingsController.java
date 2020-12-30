package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXProgressBar;

import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import conn.DB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import modle.asses.Janawari10Discount;
import modle.asses.Process;
import modle.asses.QSProcess;
import modle.asses.YQendProcess;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.Systemdate;

import static javax.swing.text.StyleConstants.Background;


/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class SystemSettingsController implements Initializable {

    @FXML
    private JFXDatePicker dp_systemDate;
    @FXML
    public JFXProgressBar progras;
    @FXML
    private Text txt_qStart;

    @FXML
    private JFXButton btn_process;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modle.StaticViews.getMc().changeTitle("System Settings");
//       btn_process.setDisable(true);
    }


    @FXML
    private void dateOnAction(ActionEvent event) {

        Date selectDate = Date.from(dp_systemDate.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


        Calendar c = Calendar.getInstance();
        c.setTime(selectDate);
        c.add(Calendar.MONTH, -3);
        Date date = c.getTime();


        Date systemdate = new Date();


        String system = format.format(systemdate);
        String selected = format.format(selectDate);
        String priviars = format.format(date);

        System.out.println(priviars + " -- " + selected);


        boolean b = new Process().allwDateChange(selectDate);
        if (b) {
            if (system.equals(selected)) {
                setSystemDate(selectDate);
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("System Date");
                alert.setHeaderText("You are going to apply wrong system date please check again");
                alert.setContentText("If you want to apply this date \n click ok");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    modle.StaticBadu.setSelectedSystemDate(selectDate);
                    setSystemDate(selectDate);
                } else {
                    modle.StaticBadu.setSelectedSystemDate(null);
                }
            }
            System.out.println(modle.StaticBadu.getSelectedSystemDate());

        } else {

            String today = new SimpleDateFormat("MM-dd").format(selectDate);
            if (today.equals("01-01") || today.equals("04-01") || today.equals("07-01") || today.equals("10-01")) {

                boolean dayEnded = checkDayEnd(priviars, selected);

                if (dayEnded) {
                    setSystemDate(selectDate);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Check Day End");
                    alert.setHeaderText("Please recheck the day end process before the date change");
                    alert.setContentText("day end not completed");
                    alert.show();
                }
            }


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Quarter End Process");
            alert.setHeaderText("You Have to Do Quarters End Process before change the date");
            alert.setContentText("please do it");
            alert.show();
        }

    }

    public void setSystemDate(Date day) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Systemdate systemDate = (Systemdate) session.createCriteria(Systemdate.class).add(Restrictions.eq("systemDateStatus", 1)).uniqueResult();

            if (systemDate != null) {

                Date systemDate1 = systemDate.getSystemDate();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String dbDate = format.format(systemDate1);
                String input = format.format(day);

                if (dbDate.equals(input)) {

                    modle.Allert.notificationInfo("System is date already set", dbDate);
                    System.out.println("Samanai");

                } else {

                    systemDate.setSystemDateStatus(0);
                    session.update(systemDate);
                    Systemdate sd = new Systemdate();
                    sd.setSystemDate(day);
                    sd.setSystemDateStatus(1);
                    Integer uid = modle.StaticViews.getLogUser().getIdUser();
                    sd.setChangeUserId(uid);
                    session.save(sd);
                    System.out.println("UPDate Una");

                }

            } else {
                //
                Systemdate sd = new Systemdate();
                sd.setSystemDate(day);
                sd.setSystemDateStatus(1);
                Integer uid = modle.StaticViews.getLogUser().getIdUser();
                sd.setChangeUserId(uid);
                session.save(sd);
                System.out.println("EMptye Update");

            }


            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        modle.StaticViews.getMc().systemDate();

        boolean b = new Process().anableProcessButton();
        if (b) {
            btn_process.setDisable(false);
        } else {
            btn_process.setDisable(true);
        }

    }


    public boolean checkDayEnd(String from, String to) {
        boolean dayEnded = true;
        try {

            ResultSet data = DB.getData("SELECT\n" +
                    "receipt.idReceipt,\n" +
                    "de.receipt_id,\n" +
                    "receipt.receipt_total,\n" +
                    "receipt.receipt_day,\n" +
                    "de.staus,\n" +
                    "assessment.idAssessment,\n" +
                    "assessment.office_idOffice\n" +
                    "FROM\n" +
                    "receipt\n" +
                    "LEFT JOIN de ON de.receipt_id = receipt.idReceipt\n" +
                    "INNER JOIN assessment ON assessment.idAssessment = receipt.recept_applicationId\n" +
                    "WHERE\n" +
                    "receipt.Application_Catagory_idApplication_Catagory = 2 AND\n" +
                    "receipt.receipt_status = 1 AND\n" +
                    "(de.receipt_id IS NULL OR de.staus = 0) AND\n" +
                    "receipt.receipt_day BETWEEN '" + from + "' AND '" + to + "'\n" +
                    "ORDER BY\n" +
                    "receipt.idReceipt ASC");

            String pd = "";

            while (data.next()) {
                String receipt_day = data.getString("receipt_day");
                if (!pd.equals(receipt_day)) {
                    pd = receipt_day;
                    modle.Allert.notificationInfo("Not Day Ended ", pd);
                }
                dayEnded = false;
            }
            return dayEnded;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayEnded;
    }


    @FXML
    void clickOn10(ActionEvent event) {
        System.out.println("click on 10 persont");
        Janawari10Discount.getOverPay();
    }

    @FXML
    private void qstartAction(ActionEvent event) {
        btn_process.setDisable(true);
        System.out.println("click");

       // new Thread(() -> {

            //  YQendProcess.collectMainData(progras);

            // new QSProcess().startProcess(progras);

       // }).start();

    }


}
