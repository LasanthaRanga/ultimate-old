/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.adv.Customer_regController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import modle.asses.HolderAssess;
import view.buttons.BTN;

/**
 * FXML Controller class
 *
 * @author Ranga Rathnayake
 */
public class ChangeProcessTypeController implements Initializable {

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
    private Text txt_selected;
    @FXML
    private JFXComboBox<String> cob_processType;
    @FXML
    private JFXButton btn_applayProcessType;
    @FXML
    private JFXTextArea txt_comment;

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

        modle.GetInstans.getProcessTypeChanging().LoadProcessTypeComboe(cob_processType);

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
                + "     INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment WHERE ass_allocation.ass_allocation_status = 1";

        if (where) {
            if (w) {
                qq += " AND ward.ward_name = '" + ward + "' ";
            }
            if (s) {
                qq += " AND street.street_name = '" + street + "' ";
            }
            if (n) {
                qq += " AND ass_nature.ass_nature_name = '" + nature + "' ";
            }
            if (a) {
                qq += " AND assessment.assessment_no LIKE '%" + assessment + "%' ";
            }
            if (o) {
                qq += " AND assessment.assessment_obsolete LIKE '%" + obsolete + "%' ";
            }
            if (c) {
                qq += " AND customer.cus_name LIKE '%" + customer + "%' ";
            }
        }

        qq += " ORDER BY assessment.assessment_oder ASC ";
        executeQuary(qq);
    }

    public ObservableList<HolderAssess> List = FXCollections.observableArrayList();

    public void executeQuary(String qq) {
        try {
            ResultSet rs = conn.DB.getData(qq);
            List.clear();
            while (rs.next()) {
                HolderAssess holderAssess = new HolderAssess(rs.getInt("idAssessment"), rs.getDouble("assessment_oder"), rs.getString("ass_nature.ass_nature_name"), rs.getString("ward_name"), rs.getString("street_name"), rs.getString("assessment_no"), rs.getString("assessment_obsolete"), rs.getDouble("ass_allocation.ass_allocation"), rs.getString("cus_name"), rs.getString("assessment_status"));
                List.add(holderAssess);
            }
            tbl_assess.setItems(List);
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
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void getKForm(ActionEvent event) {
        String ss = "";
        for (HolderAssess holderAssess : List) {
            ss += ",";
            ss += holderAssess.getIdAssess();
        }
        ss = ss.substring(1);

        System.out.println(ss);
        modle.GetInstans.getAssessReport().getKform(ss);

    }

    int idAssess = 0;

    @FXML
    private void selectFormTable(MouseEvent event) {
        idAssess = tbl_assess.getSelectionModel().getSelectedItem().getIdAssess();
        txt_selected.setText(idAssess + "");
    }

    @FXML
    private void clickOnApplyProcessType(MouseEvent event) {
        idAssess = tbl_assess.getSelectionModel().getSelectedItem().getIdAssess();
        String selectedItem = cob_processType.getSelectionModel().getSelectedItem();
        String comment  = txt_comment.getText();
        modle.GetInstans.getProcessTypeChanging().saveProcessType(idAssess, selectedItem, comment);
        
    }

}
