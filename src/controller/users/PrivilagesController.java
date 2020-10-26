package controller.users;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import modle.ComboItem;
import modle.ComboLoad;
import modle.TableLoad;
import pojo.Privilage;
import pojo.User;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class PrivilagesController implements Initializable {

    @FXML
    private TableView<UserTable> tbl_1;
    @FXML
    private TableColumn<UserTable, String> col_uid;
    @FXML
    private TableColumn<UserTable, String> col_fullname;
    @FXML
    private TableColumn<UserTable, String> col_unic;
    @FXML
    private Label lbl_selected;
    @FXML
    private TableView<Privilege> tbl_privilege;
    @FXML
    private TableColumn<Privilege, String> col_priv_id;
    //    @FXML
//    private TableColumn<Privilege, String> col_prive;
    @FXML
    private TableColumn<Privilege, String> col_btn;
    @FXML
    private TableColumn<Privilege, JFXCheckBox> col_set;
    @FXML
    private JFXButton btn_set;
    @FXML
    private Label lbl_uid;
    @FXML
    private JFXComboBox<ComboItem> apps;

    modle.user.Privilege privilege;
    int selectedAppCat = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadUserTable();
        loadPrivilageTable();
        privilege = new modle.user.Privilege();
        modle.StaticViews.getMc().changeTitle("Privilege");
        loadAppsCombo();

    }

    public void loadAppsCombo() {
        ObservableList<ComboItem> comboItems = ComboLoad.loadCombo("SELECT application_catagory.idApplication_Catagory,application_catagory.application_name FROM application_catagory");
        this.apps.setItems(comboItems);
    }

    @FXML
    void appsOnAction(ActionEvent event) {
        int id = apps.getSelectionModel().getSelectedItem().getId();
        selectedAppCat = id;
        loadPrivilageTableByApp(id);
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

    @FXML
    private void selectRow(MouseEvent event) {

        lbl_selected.setText(tbl_1.getSelectionModel().getSelectedItem().getName());
        lbl_uid.setText(tbl_1.getSelectionModel().getSelectedItem().getIdUser() + "");
        loadPrivilageTableByUser();
    }

    @FXML
    private void setPrivilage(ActionEvent event) {

        ObservableList<Privilege> items = tbl_privilege.getItems();
        for (Privilege item : items) {
            int idPrivilege = item.getIdPrivilege();
            JFXCheckBox select = item.getSelect();
            if (select.isSelected()) {
                privilege.setPrivilage(idPrivilege, Integer.parseInt(lbl_uid.getText()));
            } else {
                privilege.removePrivilage(idPrivilege, Integer.parseInt(lbl_uid.getText()));
            }
        }

    }

    public class UserTable {

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

    public void loadPrivilageTable() {
        List<Privilage> prv = modle.GetInstans.getPrivilege().getPrivilages();
        ObservableList<Privilege> privilagess = FXCollections.observableArrayList();
        for (Privilage privilage : prv) {
            privilagess.add(new Privilege(privilage.getIdPrivilage(), privilage.getView(), privilage.getBtn()));
        }
        HashMap<TableColumn, String> hashMap = new HashMap();
        hashMap.put(col_priv_id, "idPrivilege");
//        hashMap.put(col_prive, "View");
        hashMap.put(col_btn, "btn");
        hashMap.put(col_set, "select");

        modle.GetInstans.getTableLoad().load(privilagess, tbl_privilege, hashMap);

    }

    public void loadPrivilageTableByApp(int appid) {

        int i = Integer.parseInt(lbl_uid.getText());
        if (i == 0) {

            List<Privilage> prv = modle.GetInstans.getPrivilege().getPrivilagesById(appid);
            ObservableList<Privilege> privilagess = FXCollections.observableArrayList();
            for (Privilage privilage : prv) {
                privilagess.add(new Privilege(privilage.getIdPrivilage(), privilage.getView(), privilage.getBtn()));
            }
            HashMap<TableColumn, String> hashMap = new HashMap();
            hashMap.put(col_priv_id, "idPrivilege");
//        hashMap.put(col_prive, "View");
            hashMap.put(col_btn, "btn");
            hashMap.put(col_set, "select");

            modle.GetInstans.getTableLoad().load(privilagess, tbl_privilege, hashMap);

        } else {
            List<Privilage> prv = modle.GetInstans.getPrivilege().getPrivilagesById(appid);
            ObservableList<Privilege> privilagess = FXCollections.observableArrayList();
            for (Privilage privilage : prv) {
                Privilege pri = new Privilege(privilage.getIdPrivilage(), privilage.getView(), privilage.getBtn());
                if (privilege.havePrivilage(Integer.parseInt(lbl_uid.getText()), privilage.getIdPrivilage())) {
                    pri.getSelect().setSelected(true);
                }
                privilagess.add(pri);
            }
            HashMap<TableColumn, String> hashMap = new HashMap();
            hashMap.put(col_priv_id, "idPrivilege");
//        hashMap.put(col_prive, "View");
            hashMap.put(col_btn, "btn");
            hashMap.put(col_set, "select");

            modle.GetInstans.getTableLoad().load(privilagess, tbl_privilege, hashMap);

        }

    }


    public void loadPrivilageTableByUser() {
        List<Privilage> prv = null;
        if (selectedAppCat == 0) {
            prv = modle.GetInstans.getPrivilege().getPrivilages();
        } else {
            prv = modle.GetInstans.getPrivilege().getPrivilagesById(selectedAppCat);
        }


        ObservableList<Privilege> privilagess = FXCollections.observableArrayList();
        for (Privilage privilage : prv) {
            Privilege pri = new Privilege(privilage.getIdPrivilage(), privilage.getView(), privilage.getBtn());
            if (privilege.havePrivilage(Integer.parseInt(lbl_uid.getText()), privilage.getIdPrivilage())) {
                pri.getSelect().setSelected(true);
            }
            privilagess.add(pri);
        }
        HashMap<TableColumn, String> hashMap = new HashMap();
        hashMap.put(col_priv_id, "idPrivilege");
//        hashMap.put(col_prive, "View");
        hashMap.put(col_btn, "btn");
        hashMap.put(col_set, "select");

        modle.GetInstans.getTableLoad().load(privilagess, tbl_privilege, hashMap);
    }

    public class Privilege {

        /**
         * @return the idPrivilege
         */
        public int getIdPrivilege() {
            return idPrivilege;
        }

        /**
         * @return the View
         */
        public String getView() {
            return View.get();
        }

        /**
         * @return the btn
         */
        public String getBtn() {
            return btn.get();
        }

        /**
         * @return the select
         */
        public JFXCheckBox getSelect() {
            return select;
        }

        /**
         * @param select the select to set
         */
        public void setSelect(JFXCheckBox select) {
            this.select = select;
        }

        private final int idPrivilege;
        private final SimpleStringProperty View;
        private final SimpleStringProperty btn;
        private JFXCheckBox select;

        public Privilege(int idPrivilege, String View, String btn) {
            this.idPrivilege = idPrivilege;
            this.View = new SimpleStringProperty(View);
            this.btn = new SimpleStringProperty(btn);
            this.select = new JFXCheckBox();
        }

    }

}
