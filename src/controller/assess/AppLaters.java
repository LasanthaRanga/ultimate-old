package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TextArea;
import conn.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modle.StaticBadu;

import java.awt.*;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AppLaters implements Initializable {

    @FXML
    private JFXTextField txt_myno;

    @FXML
    private JFXTextField txt_youno;

    @FXML
    public JFXTextArea txt_address;

    @FXML
    private Text txt_ward;

    @FXML
    private Text txt_street;

    @FXML
    private Text txt_assno;

    @FXML
    private JFXButton btn_genarate;

    @FXML
    private JFXButton btnPrint;

    @FXML
    private JFXButton btn_cusdata;

    @FXML
    private TextArea txt_description;

    public static int idcustomer = 0;
    int selectedApp = 0;
    int selectedNewAssess = 0;


    String name;
    String adl1;
    String adl2;
    String adl3;
    String assapp_refno;
    String date;
    int assapp_type = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedApp = StaticBadu.getSelectedApp();
        selectedNewAssess = StaticBadu.getSelectedNewAssess();
        loadString();
    }

    public void loadString() {

        System.out.println(selectedApp);

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_app.idAssapp,\n" +
                    "ass_app.assapp_date,\n" +
                    "ass_app.assapp_user,\n" +
                    "ass_app.assapp_step,\n" +
                    "ass_app.assapp_description,\n" +
                    "ass_app.assapp_status,\n" +
                    "ass_app.assapp_type,\n" +
                    "ass_app.assapp_refno,\n" +
                    "ass_app.assapp_names\n" +
                    "FROM\n" +
                    "ass_app WHERE idAssapp = " + selectedApp);
            System.out.println("asdf asdf");
            if (data.last()) {
                assapp_refno = data.getString("assapp_refno");
                System.out.println(assapp_refno);
                txt_youno.setText(assapp_refno);
                date = data.getString("assapp_date");
                assapp_type = data.getInt("assapp_type");
            }

            ResultSet data1 = DB.getData("SELECT\n" +
                    "assessment.idAssessment,\n" +
                    "assessment.Customer_idCustomer,\n" +
                    "assessment.Ward_idWard,\n" +
                    "assessment.Street_idStreet,\n" +
                    "assessment.ass_nature_idass_nature,\n" +
                    "assessment.ass_discription_idass_discription,\n" +
                    "assessment.User_idUser,\n" +
                    "assessment.assessment_oder,\n" +
                    "assessment.assessment_no,\n" +
                    "assessment.assessment_status,\n" +
                    "assessment.assessment_syn,\n" +
                    "assessment.assessment_comment,\n" +
                    "assessment.assessment_obsolete,\n" +
                    "assessment.office_idOffice,\n" +
                    "customer.idCustomer,\n" +
                    "customer.cus_name,\n" +
                    "customer.cus_person_title,\n" +
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
                    "customer.cus_name_sinhala,\n" +
                    "customer.cus_address_l1_sinhala,\n" +
                    "customer.cus_address_l2_sinhala,\n" +
                    "customer.cus_address_l3_sinhala,\n" +
                    "street.street_no,\n" +
                    "street.street_name,\n" +
                    "street.street_name_sinhala,\n" +
                    "ward.ward_no\n" +
                    "FROM\n" +
                    "assessment\n" +
                    "INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n" +
                    "INNER JOIN street ON assessment.Street_idStreet = street.idStreet\n" +
                    "INNER JOIN ward ON assessment.Ward_idWard = ward.idWard AND street.Ward_idWard = ward.idWard WHERE idAssessment =" + selectedNewAssess);

            if (data1.last()) {
                idcustomer = data1.getInt("idCustomer");
                name = data1.getString("cus_name_sinhala");
                adl1 = data1.getString("cus_address_l1_sinhala");
                adl2 = data1.getString("cus_address_l2_sinhala");
                adl3 = data1.getString("cus_address_l3_sinhala");
                txt_address.setText(name + ", \n" + adl1 + ", \n" + adl2 + ", \n" + adl3 + ".");
                txt_ward.setText(data1.getString("ward_no"));
                txt_street.setText(data1.getString("street_name_sinhala"));
                txt_assno.setText(data1.getString("assessment_no"));
            }

            conn.DB.getData("SELECT\n" +
                    "ass_subowner.ass_subOwner_namesinhala,\n" +
                    "assessment.idAssessment,\n" +
                    "ass_subowner.idass_subOwner,\n" +
                    "ass_subowner.ass_subOwner_name,\n" +
                    "ass_subowner.title,\n" +
                    "person_title.title_name\n" +
                    "FROM\n" +
                    "assessment\n" +
                    "INNER JOIN ass_subowner ON ass_subowner.Assessment_idAssessment = assessment.idAssessment\n" +
                    "LEFT JOIN person_title ON person_title.title_id = ass_subowner.title WHERE idAssessment = "+ selectedNewAssess);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    String description;

    @FXML
    void genarate(ActionEvent event) {
        String latter = "";
        String myno = txt_myno.getText();
        String uno = txt_youno.getText();


        String address = txt_address.getText();


        String l1 = "දේපල අයිතිය ලියාපදිංචි කිරීම සම්බන්ධව මා වෙත එවන ලද ඔබගේ ";
        String l2 = " දින දරණ අයදුම්පත හා ඔප්පු උධෘතය හෝ ලිපිය අනුව ඉහත සඳහන්";
        String s1 = " අංක ";
        String l3 = " දරණ";
        String s2 = " දේපලෙහි";
        String l4 = " අයිතිකරු / සම අයිතිකරු ලෙස ඔබගේ නම මෙම කාර්යාලයේ වරිපනම් ලේඛනයේ ලියාපදිංචි කරන ලද බව කාරුණිකව දන්වමි." + "\n\n\n";

        latter += l1;
        latter += date;
        latter += l2;
        latter += s1;
        latter += l3;
        latter += s2;


        latter += l4;

        txt_description.setText(latter);
    }

    @FXML
    void print(ActionEvent event) {
        String text = txt_description.getText();
        modle.GetInstans.getAssessReport().ownership(selectedApp,txt_myno.getText(),txt_youno.getText(),txt_address.getText(),"මහත්මයානණි, මහත්මියණි",
                "දේපල අයිතිය ලියාපදිංචි කිරීම",txt_ward.getText(),txt_street.getText(),txt_address.getText(),text,modle.GetInstans.getQuater().getSystemDateStringByQuary());
    }

    @FXML
    void cusDataOnAction(ActionEvent event) {
        modle.StaticBadu.setAppLaters(this);
        if (idcustomer > 0) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/assess/Customer.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
//            scene.setFill(Color.TRANSPARENT);
//            stage.initStyle(StageStyle.TRANSPARENT);
                stage.getIcons().add(new Image("/grafics/info.png"));
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
