/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.users;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pojo.ApprovalCat;
import pojo.Dipartment;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class DipartmentController implements Initializable {

    @FXML
    private JFXTextField txt_dipartment;
    @FXML
    private JFXTextField txt_dip_hed;
    @FXML
    private JFXButton btn_save;
    @FXML
    private TableView<Dip> tbl_dip;
    @FXML
    private TableColumn<Dip, String> col_dip;
    @FXML
    private TableColumn<Dip, String> col_hed;
    @FXML
    private JFXTextField txt_approval_cat;
    @FXML
    private TableView<Aprov> tbl_approv_cats;
    @FXML
    private TableColumn<Aprov, String> col_aprov_catName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDipTable();
        loadApprovCatTable();
        modle.StaticViews.getMc().changeTitle("Department");
    }

    @FXML
    private void save(ActionEvent event) {

        String dip = txt_dipartment.getText();
        if (dip.length() > 1) {
            Dipartment dipartment = new Dipartment();
            dipartment.setDipName(dip);
            dipartment.setDipHead(txt_dip_hed.getText());
            dipartment.setDipSyn(1);
            dipartment.setDipStatus(1);
            boolean save = new modle.user.Dipartment().save(dipartment);
            if (save) {
                modle.Allert.notificationGood("Saved", dip);
                loadDipTable();
            }
        } else {
            modle.Allert.notificationError("Error", "Cheack Dipartment Name");
        }
    }

    public void loadDipTable() {
        List<Dipartment> dipartmants = new modle.user.Dipartment().getDipartmants();
        ObservableList<Dip> dips = FXCollections.observableArrayList();
        for (Dipartment dipartmant : dipartmants) {
            dips.add(new Dip(dipartmant.getIdDipartment(), dipartmant.getDipName(), dipartmant.getDipHead()));
        }
        HashMap<TableColumn, String> hashMap = new HashMap<TableColumn, String>();
        hashMap.put(col_dip, "Dipartment");
        hashMap.put(col_hed, "Head");
        new modle.TableLoad().load(dips, tbl_dip, hashMap);

    }

    public void loadApprovCatTable() {
        List<ApprovalCat> apprvalCats = modle.GetInstans.getDipartmentModle().getApprvalCats();
        ObservableList<Aprov> cats = FXCollections.observableArrayList();
        
        for (ApprovalCat apprvalCat : apprvalCats) {
            cats.add(new Aprov(apprvalCat.getIdApprovalCat(), apprvalCat.getApprovalName()));
        }
        
        col_aprov_catName.setCellValueFactory(new PropertyValueFactory<>("acname"));
        tbl_approv_cats.setItems(cats);
    }

    @FXML
    private void clickOnSaveApprovalCat(MouseEvent event) {
        String text = txt_approval_cat.getText();
        if (text.length() > 1) {
            ApprovalCat approvalCat = new ApprovalCat();
            approvalCat.setApprovalName(text);
            approvalCat.setStatusAppCat(1);
            approvalCat.setSynAppCat(1);
            boolean save = modle.GetInstans.getDipartmentModle().saveApprovalCat(approvalCat);
            if (save) {
                modle.Allert.notificationGood("Save", txt_approval_cat.getText());
                txt_approval_cat.setText("");
                loadApprovCatTable();
            } else {
                modle.Allert.notificationError("Error", txt_approval_cat.getText());
            }
        }

    }

    public class Dip {

        /**
         * @return the idDip
         */
        public int getIdDip() {
            return idDip;
        }

        /**
         * @return the Dipartment
         */
        public String getDipartment() {
            return Dipartment.get();
        }

        /**
         * @return the Head
         */
        public String getHead() {
            return Head.get();
        }

        private final int idDip;
        private final SimpleStringProperty Dipartment;
        private final SimpleStringProperty Head;

        public Dip(int idDip, String Dipartment, String Head) {
            this.idDip = idDip;
            this.Dipartment = new SimpleStringProperty(Dipartment);
            this.Head = new SimpleStringProperty(Head);
        }

    }

    public class Aprov {

        /**
         * @return the idAc
         */
        public int getIdAc() {
            return idAc;
        }

        /**
         * @return the acname
         */
        public String getAcname() {
            return acname.get();
        }

        private int idAc;
        private SimpleStringProperty acname;

        public Aprov(int idAc, String acname) {
            this.idAc = idAc;
            this.acname = new SimpleStringProperty(acname);
        }

    }

}
