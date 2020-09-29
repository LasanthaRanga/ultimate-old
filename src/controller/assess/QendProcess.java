package controller.assess;

import com.jfoenix.controls.*;
import conn.DB;
import controller.adv.Customer_regController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javassist.bytecode.stackmap.BasicBlock;
import modle.GetInstans;
import modle.KeyVal;
import modle.Maths;
import modle.asses.*;
import view.buttons.BTN;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Ranga on 2020-03-18.
 */
public class QendProcess implements Initializable {
    @FXML
    private JFXCheckBox cheack_ward;
    @FXML
    private JFXComboBox<String> com_ward;
    @FXML
    private JFXComboBox<String> com_street;
    @FXML
    private JFXCheckBox cheack_street;
    @FXML
    private JFXComboBox<String> com_nature;
    @FXML
    private JFXCheckBox cheack_nature;
    @FXML
    private JFXTextField txt_assessment;
    @FXML
    private JFXCheckBox cheack_assessment;
    @FXML
    private JFXTextField txt_obsolete;
    @FXML
    private JFXCheckBox cheack_obsolete;
    @FXML
    private JFXTextField txt_customer;
    @FXML
    private JFXCheckBox cheack_customer;
    @FXML
    private TableView<HolderAssess> tbl_assess;
    @FXML
    private TableColumn<HolderAssess, Double> col_order;
    @FXML
    private TableColumn<HolderAssess, String> col_nature;
    @FXML
    private TableColumn<HolderAssess, String> col_ward;
    @FXML
    private TableColumn<HolderAssess, String> col_street;
    @FXML
    private TableColumn<HolderAssess, String> col_assess;
    @FXML
    private TableColumn<HolderAssess, String> col_obsalut;
    @FXML
    private TableColumn<HolderAssess, String> col_allocation;
    @FXML
    private TableColumn<HolderAssess, String> col_owner;
    @FXML
    private TableColumn<HolderAssess, String> col_status;
    @FXML
    private TableColumn<HolderAssess, JFXCheckBox> col_nchek;
    @FXML
    private Text txt_selected;
    @FXML
    private JFXCheckBox tbl_check;
    @FXML
    private JFXCheckBox selectAllCheck;


    @FXML
    private JFXButton btn_reload;

    @FXML
    private TableView<HolderAssess> tbl_assess1;
    @FXML
    private TableColumn<HolderAssess, Double> col_order1;
    @FXML
    private TableColumn<HolderAssess, String> col_nature1;
    @FXML
    private TableColumn<HolderAssess, String> col_ward1;
    @FXML
    private TableColumn<HolderAssess, String> col_street1;
    @FXML
    private TableColumn<HolderAssess, String> col_assess1;
    @FXML
    private TableColumn<HolderAssess, String> col_obsalut1;
    @FXML
    private TableColumn<HolderAssess, String> col_allocation1;
    @FXML
    private TableColumn<HolderAssess, String> col_owner1;
    @FXML
    private TableColumn<HolderAssess, String> col_status1;
    @FXML
    private TableColumn<HolderAssess, JFXCheckBox> col_nchek1;
    @FXML
    private JFXCheckBox selectAllCheck1;
    @FXML
    private JFXProgressBar progSwich;

    @FXML
    private JFXButton btn_disabled;

    @FXML
    private JFXButton btn_enabled;

    @FXML
    private JFXProgressBar progress;

    @FXML
    private Text count_with;

    @FXML
    private Text count_not;

    @FXML
    private JFXButton btn_process;

    @FXML
    private JFXTextField txt_arrears_val;

    @FXML
    private JFXButton updateMinVal;


    int currentYear;
    int cq = 0;
    String minVal = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txt_assessment.setDisable(true);
        txt_customer.setDisable(true);
        txt_obsolete.setDisable(true);
        com_ward.setDisable(true);
        com_street.setDisable(true);
        com_nature.setDisable(true);
        tableCollomAssing();

        currentYear = GetInstans.getQuater().getCurrentYear();
//       n = true;  // governmet office atha herima;
        modle.StaticViews.getMc().changeTitle(" Assessment Process");

        //      tbl_nature.setItems(modle.GetInstans.getNature().getNatureSelectList());
        cq = GetInstans.getQuater().getPrviasQuater();


        modle.StaticViews.getMc().systemDate();

        boolean b = new modle.asses.Process().anableProcessButton();
        if (b) {
            btn_process.setDisable(false);
        } else {
            btn_process.setDisable(true);
        }

        getMinVal();


    }

    public void getMinVal() {
        String val = KeyVal.getVal("Min_Arriars_For_Warrant");
        double v = Double.parseDouble(val);
        String s = Maths.rondAnd2String(v);
        System.out.println(s);
        minVal = s;
        txt_arrears_val.setText(minVal);
    }

    @FXML
    void reloadAll(ActionEvent event) {

        txt_assessment.setDisable(true);
        txt_customer.setDisable(true);
        txt_obsolete.setDisable(true);
        com_ward.setDisable(true);
        com_street.setDisable(true);
        com_nature.setDisable(true);
        tableCollomAssing();
        w = false;
        s = false;
        n = false;
        a = false;
        o = false;
        c = false;
        searchAssessment();

    }

    boolean w = false;
    boolean s = false;
    boolean n = false;
    boolean a = false;
    boolean o = false;
    boolean c = false;

    String ward;
    String street;
    String nature;
    String assessment;
    String obsolete;
    String customer;

    @FXML
    private void cheackWard(ActionEvent event) {
        if (cheack_ward.isSelected()) {
            w = true;
            com_ward.setDisable(false);
            com_ward.setItems(modle.GetInstans.getWardModle().getWardObservableListSQL());
        } else {
            w = false;
            ward = null;
            com_ward.setItems(null);
            com_ward.setDisable(true);
        }

    }

    @FXML
    private void selectWard(ActionEvent event) {
        ward = com_ward.getSelectionModel().getSelectedItem();
        if (s) {
            com_street.setItems(modle.GetInstans.getStreetModle().getStreetObservableListSQLByWard(ward));
        }
        searchAssessment();
    }

    @FXML
    private void selectStreet(ActionEvent event) {
        street = com_street.getSelectionModel().getSelectedItem();
        searchAssessment();
    }

    @FXML
    private void cheackStreet(ActionEvent event) {
        if (cheack_street.isSelected()) {
            com_street.setDisable(false);
            s = true;
            if (w) {
                if (ward == null) {
                    modle.Allert.notificationWorning("Ward Is Empty", "Please Select Ward");
                } else {
                    com_street.setItems(modle.GetInstans.getStreetModle().getStreetObservableListSQLByWard(ward));
                }
            } else {
                com_street.setItems(modle.GetInstans.getStreetModle().getAllStreetObservableListSQL());
            }
        } else {
            s = false;
            street = null;
            com_street.setItems(null);
            com_street.setDisable(true);
        }
    }

    @FXML
    private void selectNature(ActionEvent event) {
        nature = com_nature.getSelectionModel().getSelectedItem();
        searchAssessment();
    }

    @FXML
    private void cheackNature(ActionEvent event) {
        if (cheack_nature.isSelected()) {
            n = true;
            com_nature.setDisable(false);
            com_nature.setItems(modle.GetInstans.getNature().getNatureObservableListSQL());
        } else {
            n = false;
            com_nature.setItems(null);
            nature = null;
            com_nature.setDisable(true);
        }
    }

    @FXML
    private void typeAssess(KeyEvent event) {
        assessment = txt_assessment.getText();
        searchAssessment();
    }

    @FXML
    private void typeObsolete(KeyEvent event) {
        obsolete = txt_obsolete.getText();
        searchAssessment();
    }

    @FXML
    private void typeCustomer(KeyEvent event) {
        customer = txt_customer.getText();
        searchAssessment();
    }

    @FXML
    private void cheackAssessment(ActionEvent event) {
        if (cheack_assessment.isSelected()) {
            a = true;
            txt_assessment.setDisable(false);
        } else {
            a = false;
            assessment = null;
            txt_assessment.setText(null);
            txt_assessment.setDisable(true);
        }
    }

    @FXML
    private void cheackObsolete(ActionEvent event) {
        if (cheack_obsolete.isSelected()) {
            o = true;
            txt_obsolete.setDisable(false);
        } else {
            o = false;
            txt_obsolete.setText(null);
            txt_obsolete.setDisable(true);
            obsolete = null;
        }
    }

    @FXML
    private void cheackCustomer(ActionEvent event) {
        if (cheack_customer.isSelected()) {
            c = true;
            txt_customer.setDisable(false);
        } else {
            c = false;
            txt_customer.setDisable(true);
            txt_customer.setText(null);
            customer = null;
        }
    }

    @FXML
    void chechk_natureOnAction(ActionEvent event) {
        //      ObservableList<NatureHolder> items = tbl_nature.getItems();
//        if (tbl_check.isSelected()) {
//            for (NatureHolder nh : items) {
//                nh.getCheckBox().setSelected(true);
//            }
//        } else {
//            for (NatureHolder nh : items) {
//                nh.getCheckBox().setSelected(false);
//            }
//        }
    }


    public void searchAssessment() {

        boolean where = false;

        if (w) {
            where = true;
        }
        if (s) {
            where = true;
        }
        if (n) {
            where = true;
        }
        if (a) {
            where = true;
        }
        if (o) {
            where = true;
        }
        if (c) {
            where = true;
        }


        String qq = "SELECT\n"
                + "	assessment.idAssessment,\n"
                + "	assessment.Customer_idCustomer,\n"
                + "	assessment.Ward_idWard,\n"
                + "	assessment.Street_idStreet,\n"
                + "	assessment.ass_nature_idass_nature,\n"
                + "	assessment.ass_discription_idass_discription,\n"
                + "	assessment.User_idUser,\n"
                + "	assessment.assessment_oder,\n"
                + "	assessment.assessment_no,\n"
                + "	assessment.assessment_status,\n"
                + "	assessment.assessment_syn,\n"
                + "	assessment.assessment_comment,\n"
                + "	assessment.assessment_obsolete,\n"
                + "	customer.cus_name,\n"
                + "	customer.cus_nic,\n"
                + "	customer.cus_mobile,\n"
                + "	customer.cus_address_l1,\n"
                + "	customer.cus_tel,\n"
                + "	customer.cus_address_l2,\n"
                + "	customer.cus_address_l3,\n"
                + "	customer.cus_sity,\n"
                + "	customer.cus_status,\n"
                + "	customer.idCustomer,\n"
                + "	ward.ward_name,\n"
                + "	ward.idWard,\n"
                + "	street.idStreet,\n"
                + "	street.street_name,\n"
                + "	ass_nature.idass_nature,\n "
                + "     ass_allocation.ass_allocation, \n"
                + "	ass_nature.ass_nature_name \n"
                + "FROM\n"
                + "	assessment\n"
                + "	INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n"
                + "	INNER JOIN ward ON assessment.Ward_idWard = ward.idWard\n"
                + "	INNER JOIN street ON street.Ward_idWard = ward.idWard \n"
                + "	AND assessment.Street_idStreet = street.idStreet\n"
                + "	INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature "
                + "     INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment WHERE ass_allocation.ass_allocation_status = 1 AND isWarrant = 1 ";

        String r_qq = "SELECT\n"
                + "	assessment.idAssessment,\n"
                + "	assessment.Customer_idCustomer,\n"
                + "	assessment.Ward_idWard,\n"
                + "	assessment.Street_idStreet,\n"
                + "	assessment.ass_nature_idass_nature,\n"
                + "	assessment.ass_discription_idass_discription,\n"
                + "	assessment.User_idUser,\n"
                + "	assessment.assessment_oder,\n"
                + "	assessment.assessment_no,\n"
                + "	assessment.assessment_status,\n"
                + "	assessment.assessment_syn,\n"
                + "	assessment.assessment_comment,\n"
                + "	assessment.assessment_obsolete,\n"
                + "	customer.cus_name,\n"
                + "	customer.cus_nic,\n"
                + "	customer.cus_mobile,\n"
                + "	customer.cus_address_l1,\n"
                + "	customer.cus_tel,\n"
                + "	customer.cus_address_l2,\n"
                + "	customer.cus_address_l3,\n"
                + "	customer.cus_sity,\n"
                + "	customer.cus_status,\n"
                + "	customer.idCustomer,\n"
                + "	ward.ward_name,\n"
                + "	ward.idWard,\n"
                + "	street.idStreet,\n"
                + "	street.street_name,\n"
                + "	ass_nature.idass_nature,\n "
                + "     ass_allocation.ass_allocation, \n"
                + "	ass_nature.ass_nature_name \n"
                + "FROM\n"
                + "	assessment\n"
                + "	INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n"
                + "	INNER JOIN ward ON assessment.Ward_idWard = ward.idWard\n"
                + "	INNER JOIN street ON street.Ward_idWard = ward.idWard \n"
                + "	AND assessment.Street_idStreet = street.idStreet\n"
                + "	INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature "
                + "     INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment WHERE ass_allocation.ass_allocation_status = 1 AND isWarrant = 0 ";

        if (where) {
            if (w) {
                qq += " AND ward.ward_name = '" + ward + "' ";
                r_qq += " AND ward.ward_name = '" + ward + "' ";
            }
            if (s) {
                qq += " AND street.street_name = '" + street + "' ";
                r_qq += " AND street.street_name = '" + street + "' ";
            }
            if (n) {
                qq += " AND ass_nature.ass_nature_name = '" + nature + "' ";
                r_qq += " AND ass_nature.ass_nature_name = '" + nature + "' ";
//                qq += "AND (ass_nature.ass_nature_name = 'House' OR ass_nature.ass_nature_name ='Bussines' OR ass_nature.ass_nature_name ='Land' OR ass_nature.ass_nature_name ='Paddy Field')";
            }
            if (a) {
                qq += " AND assessment.assessment_no LIKE '%" + assessment + "%' ";
                r_qq += " AND assessment.assessment_no LIKE '%" + assessment + "%' ";
            }
            if (o) {
                qq += " AND assessment.assessment_obsolete LIKE '%" + obsolete + "%' ";
                r_qq += " AND assessment.assessment_obsolete LIKE '%" + obsolete + "%' ";
            }
            if (c) {
                qq += " AND customer.cus_name LIKE '%" + customer + "%' ";
                r_qq += " AND customer.cus_name LIKE '%" + customer + "%' ";
            }
        }

        qq += " ORDER BY assessment.assessment_oder ASC ";
        r_qq += " ORDER BY assessment.assessment_oder ASC ";
        executeQuary(qq);
        executeR_Quary(r_qq);
    }

    public ObservableList<HolderAssess> List = FXCollections.observableArrayList();

    public ObservableList<HolderAssess> R_List = FXCollections.observableArrayList();

    public void executeQuary(String qq) {
        try {
            ResultSet rs = DB.getData(qq);
            List.clear();
            while (rs.next()) {
                HolderAssess holderAssess = new HolderAssess(rs.getInt("idAssessment"), rs.getDouble("assessment_oder"), rs.getString("ass_nature.ass_nature_name"), rs.getString("ward_name"), rs.getString("street_name"), rs.getString("assessment_no"), rs.getString("assessment_obsolete"), rs.getDouble("ass_allocation.ass_allocation"), rs.getString("cus_name"), rs.getString("assessment_status"));
                List.add(holderAssess);
            }
            tbl_assess.setItems(List);
            count_with.setText(List.size() + "");
        } catch (Exception ex) {
            Logger.getLogger(Search_assessController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public void executeR_Quary(String r_qq) {
        try {
            ResultSet rs = DB.getData(r_qq);
            R_List.clear();
            while (rs.next()) {
                HolderAssess holderAssess = new HolderAssess(rs.getInt("idAssessment"), rs.getDouble("assessment_oder"), rs.getString("ass_nature.ass_nature_name"), rs.getString("ward_name"), rs.getString("street_name"), rs.getString("assessment_no"), rs.getString("assessment_obsolete"), rs.getDouble("ass_allocation.ass_allocation"), rs.getString("cus_name"), rs.getString("assessment_status"));
                R_List.add(holderAssess);
            }
            tbl_assess1.setItems(R_List);
            count_not.setText(R_List.size() + "");
        } catch (Exception ex) {
            Logger.getLogger(Search_assessController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public void tableCollomAssing() {
        col_order.setCellValueFactory(new PropertyValueFactory<>("order"));
        col_nature.setCellValueFactory(new PropertyValueFactory<>("Natrue"));
        col_ward.setCellValueFactory(new PropertyValueFactory<>("ward"));
        col_street.setCellValueFactory(new PropertyValueFactory<>("street"));
        col_assess.setCellValueFactory(new PropertyValueFactory<>("assessmentNo"));
        col_obsalut.setCellValueFactory(new PropertyValueFactory<>("osaleteNo"));
        col_allocation.setCellValueFactory(new PropertyValueFactory<>("alocation"));
        col_owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
        //   col_status.setCellValueFactory(new PropertyValueFactory<>("status"));


        col_nchek.setCellValueFactory(new PropertyValueFactory<>("ch"));


        col_order1.setCellValueFactory(new PropertyValueFactory<>("order"));
        col_nature1.setCellValueFactory(new PropertyValueFactory<>("Natrue"));
        col_ward1.setCellValueFactory(new PropertyValueFactory<>("ward"));
        col_street1.setCellValueFactory(new PropertyValueFactory<>("street"));
        col_assess1.setCellValueFactory(new PropertyValueFactory<>("assessmentNo"));
        col_obsalut1.setCellValueFactory(new PropertyValueFactory<>("osaleteNo"));
        col_allocation1.setCellValueFactory(new PropertyValueFactory<>("alocation"));
        col_owner1.setCellValueFactory(new PropertyValueFactory<>("owner"));
        //   col_status.setCellValueFactory(new PropertyValueFactory<>("status"));


        col_nchek1.setCellValueFactory(new PropertyValueFactory<>("ch"));
    }


    int idAssess = 0;

    @FXML
    private void selectFormTable(MouseEvent event) {
//        if (tbl_assess.getSelectionModel() != null) {
//            idAssess = tbl_assess.getSelectionModel().getSelectedItem().getIdAssess();
//            txt_selected.setText(idAssess + "");
//        }
    }


    //=====================================================================================================================

    @FXML
    void selectAll(ActionEvent event) {

        for (HolderAssess ho : List) {
            ho.setSelected(selectAllCheck.isSelected());
        }
    }

    @FXML
    void selectAllnotWarrant(ActionEvent event) {
        for (HolderAssess ho : R_List) {
            ho.setSelected(selectAllCheck1.isSelected());
        }
    }


    @FXML
    void clickOnAnable(MouseEvent event) {

        btn_enabled.setDisable(true);
        btn_disabled.setDisable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                anable();
            }
        }).start();

    }


    public void anable() {
        double size = R_List.size();
        double x = 1;
        for (HolderAssess ho : R_List) {
            if (ho.isSelected()) {
                int idAssess = ho.getIdAssess();

                try {
                    conn.DB.setData("UPDATE `assessment` SET `isWarrant`= 1 WHERE `idAssessment`=" + idAssess);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            double v = x / size;

            progSwich.setProgress(v);
            x = x + 1;
        }
        List.clear();
        R_List.clear();
        searchAssessment();
        progSwich.setProgress(0.0);
        btn_enabled.setDisable(false);
        btn_disabled.setDisable(false);
    }


    @FXML
    void clickOnDisabled(MouseEvent event) {
        btn_disabled.setDisable(true);
        btn_enabled.setDisable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                disable();
            }
        }).start();

    }

    public void disable() {
        double size = List.size();
        double x = 1;
        for (HolderAssess ho : List) {
            if (ho.isSelected()) {
                int idAssess = ho.getIdAssess();

                try {
                    conn.DB.setData("UPDATE `assessment` SET `isWarrant`= 0 WHERE `idAssessment`=" + idAssess);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            double v = x / size;

            progSwich.setProgress(v);
            x = x + 1;
        }
        List.clear();
        R_List.clear();
        searchAssessment();
        progSwich.setProgress(0.0);
        btn_disabled.setDisable(false);
        btn_enabled.setDisable(false);
    }


    @FXML
    void startProcess(MouseEvent event) {
        btn_process.setDisable(true);
        System.out.println("Please Wait");
        modle.Allert.notificationGood("Quarter End Process Started", "Please Wait ");

        new Thread(() -> {

            int currentQuater = GetInstans.getQuater().getCurrentQuater();
            if (currentQuater == 1) {
                YQendProcess.collectMainData(progress);
            } else {
                QSProcess.startProcess(progress);
            }


        }).start();


    }

    @FXML
    void updateMinVal(MouseEvent event) {
        System.out.println("Click On update");
        String text = txt_arrears_val.getText();
        try {
            if (text.length() > 0) {
                double v = Double.parseDouble(text);
                if (v >= 0) {
                    String min_arriars_for_warrant = KeyVal.updateVal("Min_Arriars_For_Warrant", text);
                    modle.Allert.notificationGood("Ok", min_arriars_for_warrant);
                } else {
                    modle.Allert.notificationError("Not Valid Input", "Please Re Check");
                }
            } else {
                modle.Allert.notificationError("Not Valid Input", "Please Re Check");
            }
        } catch (Exception e) {
            e.printStackTrace();
            modle.Allert.notificationError("Not Valid Input", "Please Re Check");
        }
    }


}

