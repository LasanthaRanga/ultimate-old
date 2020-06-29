package controller.adv;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import pojo.Customer;
import view.buttons.BTN;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class Customer_regController implements Initializable {

    String nic, name, mobile, adl, ad2, ad3;
    Customer cus;
    @FXML
    private JFXTextField txt_nic;
    @FXML
    private JFXTextField txt_cusName;
    @FXML
    private JFXTextField txt_cusMobile;
    @FXML
    private JFXTextField txt_adl1;
    @FXML
    private JFXTextField txt_adl2;
    @FXML
    private JFXTextField txt_adl3;

    @FXML
    private JFXButton btn_save;
    @FXML
    private TableView<CusTable> tbl_customer;
    @FXML
    private TableColumn<CusTable, Integer> col_id;
    @FXML
    private TableColumn<CusTable, String> col_name;
    @FXML
    private TableColumn<CusTable, String> col_nic;
    @FXML
    private TableColumn<CusTable, String> col_current;
    @FXML
    private JFXButton btn_save1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        new Thread(() -> {
            loadCusTable();
        }).start();
        modle.StaticViews.getMc().changeTitle("Customer Registration");
    }

    @FXML
    private void nicKeyReleased(KeyEvent event) {

    }

    public boolean collectData() {
        nic = txt_nic.getText();
        if (nic.length() > 9) {
            name = txt_cusName.getText();
            if (name.length() > 1) {
                mobile = txt_cusMobile.getText();
                adl = txt_adl1.getText();
                ad2 = txt_adl2.getText();
                ad3 = txt_adl3.getText();
                return true;
            } else {
                modle.Allert.notificationError("Error", "Please Cheack Name");
                return false;
            }
        } else {
            modle.Allert.notificationError("Error", "Please Cheack NIC");
            return false;
        }
    }

    @FXML
    private void cleckSave(MouseEvent event) {
        if (collectData()) {
            save();
        }
    }

    public void save() {
        Customer customer;
        if (cus == null) {
            customer = new Customer();
        } else {
            customer = cus;
        }
        customer.setCusNic(nic);
        customer.setCusName(name);
        customer.setCusMobile(mobile);
        customer.setCusAddressL1(adl);
        customer.setCusAddressL2(ad2);
        customer.setCusAddressL3(ad3);
        customer.setCusStatus(1);
        customer.setCusSyn(1);
        Customer save = new modle.adv.customer().save(customer);
        if (save != null) {
            cus = save;
            modle.Allert.notificationGood("Save", nic);
            loadCusTable();
        }
    }

    public void thread() {

    }

    public void clear() {
        cus = null;
        txt_nic.setText(null);
        txt_cusName.setText(null);
        txt_adl1.setText(null);
        txt_adl2.setText(null);
        txt_adl3.setText(null);
        txt_cusMobile.setText(null);
        Runtime.getRuntime().gc();

    }

    public void loadCusDataFromQuarry() {
//        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
//        col_name.setCellValueFactory(new PropertyValueFactory<>("cusName"));
//        col_nic.setCellValueFactory(new PropertyValueFactory<>("nic"));
//        col_current.setCellValueFactory(new PropertyValueFactory<>("current"));
//        ObservableList<CusTable> ol = FXCollections.observableArrayList();
//        try {
//            ResultSet dataSet = conn.DB.getData("SELECT * FROM `customer`");
//            while (dataSet.next()) {
//                ol.add(new CusTable(dataSet.getInt("idCustomer"), dataSet.getString("cus_name"), dataSet.getString("cus_nic"), ""));// meke eroor ekak enawa welawakata
//                tbl_customer.setItems(ol);
//            }
//
//        } catch (Exception ex) {
//            Logger.getLogger(Customer_regController.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public void loadCusTable() {

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        col_nic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        col_current.setCellValueFactory(new PropertyValueFactory<>("current"));
        ObservableList<CusTable> ol = FXCollections.observableArrayList();
        ArrayList<Customer> advCustomerList = modle.GetInstans.getAdvCustomerModle().getAdvCustomerList();

        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {

            for (Customer customer : advCustomerList) {

                Criteria cry = session.createCriteria(pojo.AdvAdvertising.class);
                cry.add(Restrictions.eq("customerIdCustomer", customer.getIdCustomer()));
                cry.add(Restrictions.eq("advStatus", 1));

                int cadd = cry.list().size();

                customer = (Customer) session.load(Customer.class, customer.getIdCustomer());
                ol.add(new CusTable(customer.getIdCustomer(), customer.getCusName(), customer.getCusNic(), cadd + ""));// meke eroor ekak enawa welawakata
                tbl_customer.setItems(ol);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @FXML
    private void selectCustomerFromTable(MouseEvent event) {

        CusTable selectedItem = tbl_customer.getSelectionModel().getSelectedItem();

        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();

        try {
            cus = (Customer) session.load(Customer.class, selectedItem.getId());

            txt_nic.setText(cus.getCusNic());
            txt_cusName.setText(cus.getCusName());
            txt_cusMobile.setText(cus.getCusMobile());
            txt_adl1.setText(cus.getCusAddressL1());
            txt_adl2.setText(cus.getCusAddressL2());
            txt_adl3.setText(cus.getCusAddressL3());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @FXML
    private void cleckClear(MouseEvent event) {

        clear();
        new Thread(() -> {
            loadCusTable();
        }).start();
    }

    @FXML
    private void clickOnCreateApplication(MouseEvent event) {

        if (cus != null) {

            modle.adv.StaticBaduAdv.setCustomer(cus);

            AnchorPane container = modle.StaticViews.getContainer();
            container.getChildren().removeAll();
            container.getChildren().clear();
            AnchorPane dashh;
            try {
                dashh = FXMLLoader.load(getClass().getResource("/view/adv/application.fxml"));
                container.getChildren().add(dashh);
            } catch (IOException ex) {
                ex.printStackTrace();
                Logger
                        .getLogger(Customer_regController.class
                                .getName()).log(Level.SEVERE, null, ex);
            }
            Set<String> keySet = modle.StaticViews.getButtonMap().keySet();
            String s = "/view/buttons/adv_application_reg.fxml";

            for (String string : keySet) {
                if (string.equals(s)) {
                    try {
                        JFXButton get = modle.StaticViews.getButtonMap().get(string);
                        BTN get1 = modle.StaticViews.getBtnConMap().get(string);
                        get1.setImage("/grafics/app_b.png");
                        get.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #03A9F4;");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JFXButton btn = modle.StaticViews.getButtonMap().get(string);
                        btn.setStyle("-fx-background-color: #03A9F4; -fx-text-fill: #FFFFFF;");
                        BTN get = modle.StaticViews.getBtnConMap().get(string);
                        if (get != null) {
                            get.setImage();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }
        }

    }

    public class CusTable {

        private int id;
        private SimpleStringProperty cusName;
        private SimpleStringProperty nic;
        private SimpleStringProperty current;

        /**
         * @return the id
         */
        public int getId() {
            return id;
        }

        /**
         * @return the cusName
         */
        public String getCusName() {
            return cusName.get();
        }

        /**
         * @return the nic
         */
        public String getNic() {
            return nic.get();
        }

        /**
         * @return the current
         */
        public String getCurrent() {
            return current.get();
        }

        public CusTable(int id, String cusName, String nic, String current) {
            this.id = id;
            this.cusName = new SimpleStringProperty(cusName);
            this.nic = new SimpleStringProperty(nic);
            this.current = new SimpleStringProperty(current);
        }

    }

}
