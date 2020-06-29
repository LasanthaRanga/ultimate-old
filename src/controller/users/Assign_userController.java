/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.users;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableIntegerArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import modle.TableLoad;
import pojo.ApplicationCatagory;
import pojo.ApprovalCat;
import pojo.Dipartment;
import pojo.User;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class Assign_userController implements Initializable {

    @FXML
    private TableView<UserTable> tbl_1;

    @FXML
    private TableColumn<UserTable, String> col_uid;

    @FXML
    private TableColumn<UserTable, String> col_unic;
    @FXML
    private TableColumn<UserTable, String> col_fullname;
    @FXML
    private Label lbl_uname;
    @FXML
    private Label lbl_uid;
    @FXML
    private JFXComboBox<String> com_dipartment;
    @FXML
    private JFXComboBox<String> com_application;
    @FXML
    private JFXComboBox<String> com_approvalCat;
    @FXML
    private JFXButton btn_dipAssign;
    @FXML
    private JFXListView<String> list_dipartment;
    @FXML
    private JFXListView<String> list_application;
    @FXML
    private JFXListView<String> list_approval;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadUserTable();
        loadDipartmentCombo();
        loadApprovalCombo();
        loadApplicationCombo();
        modle.StaticViews.getMc().changeTitle("Assign Users");
    }

    public void loadUserTable() {
        List<User> users = new modle.user.User_Assign().getUsers();
        ObservableList List = FXCollections.observableArrayList();
        for (User user : users) {
            System.out.println(user.getUserFullName());
            List.add(new UserTable(user.getIdUser(), user.getUserFullName(), user.getUserNic()));
        }
        HashMap<TableColumn, String> hm = new HashMap<TableColumn, String>();
        hm.put(col_uid, "idUser");
        hm.put(col_fullname, "name");
        hm.put(col_unic, "nic");
        TableLoad tableLoad = new modle.TableLoad();
        tableLoad.load(List, tbl_1, hm);
    }

    public void loadDipartmentCombo() {
        List<Dipartment> dipartmants = modle.GetInstans.getDipartmentModle().getDipartmants();
        ObservableList<String> ol = FXCollections.observableArrayList();
        for (Dipartment dipartmant : dipartmants) {
            ol.add(dipartmant.getDipName());
        }
        com_dipartment.setItems(ol);
    }

    public void loadApprovalCombo() {
        List<ApprovalCat> apprvalCats = modle.GetInstans.getDipartmentModle().getApprvalCats();
        ObservableList<String> ol = FXCollections.observableArrayList();
        for (ApprovalCat apprvalCat : apprvalCats) {
            ol.add(apprvalCat.getApprovalName());
        }
        com_approvalCat.setItems(ol);
    }

    public void loadApplicationCombo() {
        List<ApplicationCatagory> acs = modle.GetInstans.getDipartmentModle().getApplicationCatagorys();
        ObservableList<String> ol = FXCollections.observableArrayList();
        for (ApplicationCatagory ac : acs) {
            ol.add(ac.getApplicationName());
        }
        com_application.setItems(ol);
    }

    @FXML
    private void selectByUserTable(MouseEvent event) {
        UserTable selectedItem = tbl_1.getSelectionModel().getSelectedItem();
        lbl_uid.setText(selectedItem.getIdUser() + "");
        lbl_uname.setText(selectedItem.getName());
        loadDipList(selectedItem.getIdUser());
        loadApplicationList(selectedItem.getIdUser());
        loadApprovalList(selectedItem.getIdUser());

    }

    // Assign
    @FXML
    private void clicDipartmentAssing(MouseEvent event) {
        if (!lbl_uid.getText().equals("")) {
            String selectedItem = com_dipartment.getSelectionModel().getSelectedItem();
            boolean assignDipartment = modle.GetInstans.getDipartmentModle().assignDipartment(lbl_uid.getText(), selectedItem);
            if (assignDipartment) {
                modle.Allert.notificationGood("Assign Complete", selectedItem);
                loadDipList(Integer.parseInt(lbl_uid.getText()));
            }
        }
    }

    @FXML
    private void clickApplicationAssing(MouseEvent event) {
        if (!lbl_uid.getText().equals("")) {
            String selectedItem = com_application.getSelectionModel().getSelectedItem();
            boolean assignApplication = modle.GetInstans.getDipartmentModle().assignApplication(lbl_uid.getText(), selectedItem);
            if (assignApplication) {
                modle.Allert.notificationGood("Assign Complete", selectedItem);
                loadApplicationList(Integer.parseInt(lbl_uid.getText()));
            }
        }
    }

    @FXML
    private void clickApprovAssing(MouseEvent event) {
        if (!lbl_uid.getText().equals("")) {
            String selectedItem = com_approvalCat.getSelectionModel().getSelectedItem();
            boolean assignDipartment = modle.GetInstans.getDipartmentModle().assignApproval(lbl_uid.getText(), selectedItem);
            if (assignDipartment) {
                modle.Allert.notificationGood("Assign Complete", selectedItem);
                loadApprovalList(Integer.parseInt(lbl_uid.getText()));
            }
        }

    }

    //Remove
    @FXML
    private void clickOnDipRemove(MouseEvent event) {
        if (!lbl_uid.getText().equals("")) {
            String selectedItem = list_dipartment.getSelectionModel().getSelectedItem();
            if (!list_dipartment.getSelectionModel().isEmpty()) {
                boolean removeDipartment = modle.GetInstans.getDipartmentModle().removeDipartment(lbl_uid.getText(), selectedItem);
                if (removeDipartment) {
                    loadDipList(Integer.parseInt(lbl_uid.getText()));
                    modle.Allert.notificationGood("Remove Successful", selectedItem);
                }
            }
        }

    }

    @FXML
    private void clickOnApplicationRemove(MouseEvent event) {
        if (!lbl_uid.getText().equals("")) {
            String selectedItem = list_application.getSelectionModel().getSelectedItem();
            if (!list_application.getSelectionModel().isEmpty()) {
                boolean removeDipartment = modle.GetInstans.getDipartmentModle().removeApplication(lbl_uid.getText(), selectedItem);
                if (removeDipartment) {
                    loadApplicationList(Integer.parseInt(lbl_uid.getText()));
                    modle.Allert.notificationGood("Remove Successful", selectedItem);
                }
            }
        }
    }

    @FXML
    private void onClickApprovalRemove(MouseEvent event) {
        if (!lbl_uid.getText().equals("")) {
            String selectedItem = list_approval.getSelectionModel().getSelectedItem();
            if (!list_approval.getSelectionModel().isEmpty()) {
                boolean removeDipartment = modle.GetInstans.getDipartmentModle().removeApproval(lbl_uid.getText(), selectedItem);
                if (removeDipartment) {
                    loadApprovalList(Integer.parseInt(lbl_uid.getText()));
                    modle.Allert.notificationGood("Remove Successful", selectedItem);
                }
            }
        }

    }

    public class UserTable extends RecursiveTreeObject<UserTable> {

        /**
         * @return the idUser
         */
        public int getIdUser() {
            return idUser;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name.get();
        }

        /**
         * @return the nic
         */
        public String getNic() {
            return nic.get();
        }

        private final int idUser;
        private final SimpleStringProperty name;
        private final SimpleStringProperty nic;

        public UserTable(int idUser, String name, String nic) {
            this.idUser = idUser;
            this.name = new SimpleStringProperty(name);
            this.nic = new SimpleStringProperty(nic);
        }

    }

    public void loadDipList(int uid) {
        List<String> idp = modle.GetInstans.getDipartmentModle().getDipartmentsByUser(uid);
        ObservableList<String> al = FXCollections.observableArrayList();
        for (String string : idp) {
            al.add(string);
        }
        list_dipartment.setItems(al);
    }

    public void loadApplicationList(int uid) {
        List<String> idp = modle.GetInstans.getDipartmentModle().getApplicaListByUser(uid);
        ObservableList<String> al = FXCollections.observableArrayList();
        for (String string : idp) {
            al.add(string);
        }
        list_application.setItems(al);
    }

    public void loadApprovalList(int uid) {
        List<String> idp = modle.GetInstans.getDipartmentModle().getApprovalListByUser(uid);
        ObservableList<String> al = FXCollections.observableArrayList();
        for (String string : idp) {
            al.add(string);
        }
        list_approval.setItems(al);
    }

}
