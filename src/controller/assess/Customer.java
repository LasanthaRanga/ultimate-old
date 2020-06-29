package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import conn.DB;
import conn.NewHibernateUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import modle.StaticBadu;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Customer implements Initializable {
    @FXML
    private Text txt_nameenglish;

    @FXML
    private JFXTextField txt_namesinhala;

    @FXML
    private JFXTextField txt_adl1;

    @FXML
    private JFXTextField txt_adl2;

    @FXML
    private JFXTextField txt_adl3;

    @FXML
    private JFXButton btn_update;

    int idcus = 0;

    @FXML
    void clickOnUpdate(ActionEvent event) {

        String name = txt_namesinhala.getText();
        String adl1 = txt_adl1.getText();
        String adl2 = txt_adl2.getText();
        String adl3 = txt_adl3.getText();

        if (name.length() > 0 && adl1.length() > 0 && adl2.length() > 0 && adl3.length() > 0) {
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            try {
                pojo.Customer cus = (pojo.Customer) session.load(pojo.Customer.class, idcus);
                cus.setCusNameSinhala(name);
                cus.setCusAddressL1Sinhala(adl1);
                cus.setCusAddressL2Sinhala(adl2);
                cus.setCusAddressL3Sinhala(adl3);
                session.update(cus);
                transaction.commit();
                modle.Allert.notificationGood("Updated",name);
                loadEnglishName();
                StaticBadu.getAppLaters().txt_address.setText(name+", \n"+adl1+", \n"+adl2+", \n"+adl3+".");
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
                modle.Allert.notificationGood("Error",e.getMessage());
            } finally {
                session.close();
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idcus = AppLaters.idcustomer;
        loadEnglishName();

    }


    public void loadEnglishName() {

        try {
            ResultSet data = DB.getData("SELECT\n" +
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
                    "customer.cus_address_l3_sinhala\n" +
                    "FROM\n" +
                    "customer\n" +
                    "WHERE idCustomer =" + idcus);

            if (data.last()) {
                String cus_name = data.getString("cus_name");
                txt_nameenglish.setText(cus_name);
                String cus_address_l1_sinhala = data.getString("cus_address_l1_sinhala");
                String cus_address_l2_sinhala = data.getString("cus_address_l2_sinhala");
                String cus_address_l3_sinhala = data.getString("cus_address_l3_sinhala");

                if (cus_address_l1_sinhala != null) {
                    txt_adl1.setText(cus_address_l1_sinhala);
                }
                if (cus_address_l2_sinhala != null) {
                    txt_adl2.setText(cus_address_l2_sinhala);
                }
                if (cus_address_l3_sinhala != null) {
                    txt_adl3.setText(cus_address_l3_sinhala);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }


}
