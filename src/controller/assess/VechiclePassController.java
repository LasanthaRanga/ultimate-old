package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import conn.DB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import modle.GetInstans;
import modle.asses.PayNowModle;

import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Ranga on 2019-02-11.
 */
public class VechiclePassController implements Initializable {

    @FXML
    private JFXTextField txt_idAssessment;

    @FXML
    private Text txt_cusName;

    @FXML
    private Text txt_nic;

    @FXML
    private Text txt_ward;

    @FXML
    private Text txt_street;

    @FXML
    private Text txt_assessment;

    @FXML
    private Text txt_nature;

    @FXML
    private Text txt_allocation;

    @FXML
    private JFXTextField txt_nicfild;

    @FXML
    private JFXTextField txt_mobile;

    @FXML
    private JFXTextField txt_landno;

    @FXML
    private JFXTextField txt_ad1;

    @FXML
    private JFXTextField txt_ad2;

    @FXML
    private JFXTextField txt_ad3;

    @FXML
    private JFXTextField txt_vehicleNO;

    @FXML
    private JFXButton btn_save;


    int idAsses;
    int idCustomer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modle.StaticViews.getMc().changeTitle("Vehicle Pass");
    }

    @FXML
    void clickOnSave(MouseEvent event) {

        Date systemDate = GetInstans.getQuater().getSystemDate();

        String q1 = "UPDATE `customer`\n" +
                "SET " +
                " `cus_nic` = '" + txt_nicfild.getText() + "',\n" +
                " `cus_mobile` = '" + txt_mobile.getText() + "',\n" +
                " `cus_tel` = '" + txt_landno.getText() + "',\n" +
                " `cus_address_l1` = '" + txt_ad1.getText() + "',\n" +
                " `cus_address_l2` = '" + txt_ad2.getText() + "',\n" +
                " `cus_address_l3` = '" + txt_ad3.getText() + "',\n" +
                " `cus_status` = '1',\n" +
                " `cus_syn` = '1',\n" +
                " `cus_update_date` = '" + systemDate + "'\n" +
                "WHERE\n" +
                "\t(`idCustomer` = '" + idCustomer + "')";

        String q2 = "INSERT INTO `VehiclePass` (\t\n" +
                "\t`Assessment_idAssessment`,\n" +
                "\t`Vehicle_No`,\n" +
                "\t`Issue_date`,\n" +
                "\t`Customer_idCustomer`\n" +
                ")\n" +
                "VALUES\n" +
                "\t( " + idAsses + ", '" + txt_vehicleNO.getText() + "', '" + systemDate + "', '" + idCustomer + "')\n";

        try {
            DB.setData(q1);
            DB.setData(q2);
            modle.Allert.notificationGood("Success", txt_vehicleNO.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void idKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            loadData(txt_idAssessment.getText());
        }
        if (txt_idAssessment.getText().length() == 0) {
            clear();
        }
    }

    public void clear() {
        txt_cusName.setText("");
        txt_ward.setText("");
        txt_street.setText("");
        txt_assessment.setText("");
        txt_nature.setText("");
        txt_allocation.setText("");

        txt_nic.setText("");
        txt_nicfild.setText("");
        txt_mobile.setText("");
        txt_landno.setText("");
        txt_ad1.setText("");
        txt_ad2.setText("");
        txt_ad3.setText("");
        txt_vehicleNO.setText("");
        idAsses = 0;
        idCustomer = 0;
    }

    public void loadData(String no) {
        String qq = "SELECT\n" +
                "assessment.idAssessment,\n" +
                "customer.cus_name,\n" +
                "ass_nature.ass_nature_name,\n" +
                "ward.ward_no,\n" +
                "street.street_name,\n" +
                "assessment.assessment_no,\n" +
                "ass_allocation.ass_allocation,\n" +
                "customer.idCustomer,\n" +
                "customer.cus_nic,\n" +
                "customer.cus_mobile,\n" +
                "customer.cus_tel,\n" +
                "customer.cus_address_l1,\n" +
                "customer.cus_address_l2,\n" +
                "customer.cus_address_l3,\n" +
                "customer.cus_sity,\n" +
                "customer.cus_status,\n" +
                "customer.cus_syn,\n" +
                "customer.cus_reg_date,\n" +
                "customer.cus_update_date,\n" +
                "vehiclepass.idVehiclePass,\n" +
                "vehiclepass.Assessment_idAssessment,\n" +
                "vehiclepass.Vehicle_No,\n" +
                "vehiclepass.Issue_date,\n" +
                "vehiclepass.Customer_idCustomer, ward.ward_name " +
                "FROM\n" +
                "ward\n" +
                "INNER JOIN street ON street.Ward_idWard = ward.idWard\n" +
                "INNER JOIN assessment ON assessment.Street_idStreet = street.idStreet AND assessment.Ward_idWard = ward.idWard\n" +
                "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                "INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n" +
                "INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                "LEFT JOIN vehiclepass ON vehiclepass.Assessment_idAssessment = assessment.idAssessment\n" +
                "WHERE\n" +
                "assessment.idAssessment = '" + no + "' AND\n" +
                "ass_allocation.ass_allocation_status = 1";

        try {
            ResultSet data = DB.getData(qq);

            if (data.last()) {
                idAsses = data.getInt("idAssessment");
                idCustomer = data.getInt("idCustomer");
                txt_cusName.setText(data.getString("cus_name"));
                txt_nic.setText(data.getString("cus_nic"));
                txt_ward.setText(data.getString("ward_name"));
                txt_street.setText(data.getString("street_name"));
                txt_assessment.setText(data.getString("assessment_no"));
                txt_nature.setText(data.getString("ass_nature_name"));
                txt_allocation.setText(data.getString("ass_allocation"));

                txt_nicfild.setText(data.getString("cus_nic"));
                txt_mobile.setText(data.getString("cus_mobile"));
                txt_landno.setText(data.getString("cus_tel"));
                txt_ad1.setText(data.getString("cus_address_l1"));
                txt_ad2.setText(data.getString("cus_address_l2"));
                txt_ad3.setText(data.getString("cus_address_l3"));
                txt_vehicleNO.setText(data.getString("Vehicle_No"));

            } else {
                clear();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    private JFXDatePicker from_date;

    @FXML
    private JFXDatePicker to_date;

    @FXML
    private JFXButton btn_reports;

    @FXML
    void clickOnReports(MouseEvent event) {
        System.out.println("Click");
        if (from_date.getValue() != null && to_date.getValue() != null) {
            Date from = Date.from(from_date.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
            Date to = Date.from(to_date.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fromeString = simpleDateFormat.format(from);
            String toString = simpleDateFormat.format(to);

            System.out.println(fromeString);
            System.out.println(toString);
            modle.GetInstans.getAssessReport().getVehiclepassReport(fromeString, toString);
        }
    }


}
