package controller.adv;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import modle.GetInstans;
import pojo.Assessment;
import pojo.Street;
import pojo.Ward;
import org.controlsfx.control.textfield.TextFields;
import org.hibernate.Session;
import pojo.AdvPosition;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class Position_regController implements Initializable {

    String positionString, woner, nic, ward, street, assessmant, address;

    double limit, rent;

    int idAssessmant, idPosition;

    AdvPosition positionPojo;

    @FXML
    private JFXTextField txt_position;
    @FXML
    private JFXTextField txt_limt;
    @FXML
    private JFXTextField txt_owner;
    @FXML
    private JFXTextField txt_nic;
    @FXML
    private JFXComboBox<String> com_ward;
    @FXML
    private JFXComboBox<String> com_street;
    @FXML
    private JFXTextField txt_assessmant;
    @FXML
    private JFXTextArea txt_address;
    @FXML
    private JFXTextField txt_rent;
    @FXML
    private JFXButton btn_save;
    @FXML
    private TableView<PositionTable> tbl_possiton;
    @FXML
    private TableColumn<PositionTable, String> col_pos;
    @FXML
    private TableColumn<PositionTable, String> col_limit;
    @FXML
    private TableColumn<PositionTable, String> col_current;
    @FXML
    private TableColumn<PositionTable, String> col_owner;
    @FXML
    private TableColumn<PositionTable, String> col_rent;
    @FXML
    private JFXButton btn_save1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadWardCombo();
        loadPositionTable();
        modle.StaticViews.getMc().changeTitle("Advertisement Places");
    }

    @FXML
    private void wardOnAction(ActionEvent event) {
        if (com_ward.getSelectionModel().getSelectedItem() != null) {
            loadSteetCombo();
        }
    }

    @FXML
    private void streetOnAction(ActionEvent event) {
        if (com_street.getSelectionModel().getSelectedItem() != null) {
            autoCompleteAssessmant();
        }
    }

    @FXML
    private void saveOnClick(MouseEvent event) {
        if (collectData()) {
            int x = 0;

            if (positionPojo == null) {
                //save
                System.out.println("save ekata awa");
                AdvPosition po = new AdvPosition();

                po.setAdvPositionName(positionString);
                po.setAdvPositionLimit(limit);
                po.setAdvPositionOwner(woner);
                po.setAdvPositionNic(nic);
                po.setAdvPositionAddress(address);
                po.setAdvPositionGroundRent(rent);
                po.setAdvPositionStatus(1);
                po.setAdvPositionSyn(1);
                po.setAdvPositionAssessmantNo(0 + "");
                if (ward != null && street != null) {
                    idAssessmant = modle.GetInstans.getAssessmantModle().isExistAssessmant(ward, street, assessmant);
                    if (idAssessmant == 0) {
                        x = 1;
                        modle.Allert.notificationError("Error", "Please Cheack Assessmant No");
                        po.setAdvPositionAssessmantNo(0 + "");
                    } else {
                        x = 0;
                        po.setAdvPositionAssessmantNo(idAssessmant + "");
                    }
                }
                if (x == 0) {
                    boolean savePosition = modle.GetInstans.getPositionModle().savePosition(po);
                    if (savePosition) {
                        modle.Allert.notificationGood("Save Position", positionString);
                        clear();
                    }
                }

            } else {
                // update
                System.out.println("update ekata awa");
                positionPojo.setAdvPositionName(positionString);
                positionPojo.setAdvPositionLimit(limit);
                positionPojo.setAdvPositionOwner(woner);
                positionPojo.setAdvPositionNic(nic);
                positionPojo.setAdvPositionAddress(address);
                positionPojo.setAdvPositionGroundRent(rent);
                positionPojo.setAdvPositionStatus(1);
                positionPojo.setAdvPositionSyn(1);
                positionPojo.setAdvPositionAssessmantNo(0 + "");
                if (ward != null && street != null) {
                    idAssessmant = modle.GetInstans.getAssessmantModle().isExistAssessmant(ward, street, assessmant);
                    if (idAssessmant == 0) {
                        x = 1;
                        modle.Allert.notificationError("Error", "Please Cheack Assessmant No");
                        positionPojo.setAdvPositionAssessmantNo(0 + "");
                    } else {
                        x = 0;
                        positionPojo.setAdvPositionAssessmantNo(idAssessmant + "");
                    }
                }
                if (x == 0) {
                    boolean savePosition = modle.GetInstans.getPositionModle().savePosition(positionPojo);
                    if (savePosition) {
                        modle.Allert.notificationGood("Save Position", positionString);
                        clear();
                    }
                }
            }
        }
        loadPositionTable();
    }

    public boolean collectData() {
        positionString = txt_position.getText();
        if (modle.GetInstans.getPositionModle().isExsist(positionString) && positionPojo == null) {
            modle.Allert.notificationError("Location Name Is Exsist", "change location name");
            return false;
        } else {
            if (positionString.length() > 1) {
                try {
                    limit = Double.parseDouble(txt_limt.getText());
                    woner = txt_owner.getText();
                    nic = txt_nic.getText();
                    ward = com_ward.getSelectionModel().getSelectedItem();
                    street = com_street.getSelectionModel().getSelectedItem();
                    assessmant = txt_assessmant.getText();
                    address = txt_address.getText();
                    try {
                        rent = Double.parseDouble(txt_rent.getText());
                        return true;
                    } catch (NumberFormatException e) {
                        modle.Allert.notificationError("Error", "Cheack Rent");
                        return false;
                    }
                } catch (NumberFormatException e) {
                    modle.Allert.notificationError("Error", "Cheack Limit");
                    return false;
                }
            } else {
                modle.Allert.notificationError("Error", "Cheack Possition");
                return false;
            }
        }
    }

    @FXML
    private void onKeyRelesed(KeyEvent event) {
        if (event.getSource() == txt_limt) {
            if (txt_limt.getText().matches("\\d*(\\.\\d*)?")) {
                try {
                    limit = Double.parseDouble(txt_limt.getText());
                } catch (Exception e) {
                    modle.Allert.notificationError("Error", "please Cheack Limit");
                }
            } else {
                java.awt.Toolkit.getDefaultToolkit().beep();
                txt_limt.deletePreviousChar();
            }
        }
        if (event.getSource() == txt_rent) {
            if (txt_rent.getText().matches("\\d*(\\.\\d*)?")) {
                try {
                    rent = Double.parseDouble(txt_rent.getText());
                } catch (Exception e) {
                    modle.Allert.notificationError("Error", "please Cheack Rnet");
                }
            } else {
                java.awt.Toolkit.getDefaultToolkit().beep();
                txt_rent.deletePreviousChar();
            }
        }
    }

    public void loadWardCombo() {
        List<Ward> wardList = modle.GetInstans.getWardModle().getWardList();
        ObservableList<String> oal_ward = FXCollections.observableArrayList();
        for (Ward w : wardList) {
            oal_ward.add(w.getWardName());
        }
        com_ward.setItems(oal_ward);
    }

    public void loadSteetCombo() {
        List<Street> streets = modle.GetInstans.getStreetModle().getStreetListByWard(com_ward.getSelectionModel().getSelectedItem());
        ObservableList<String> oal_street = FXCollections.observableArrayList();
        for (Street s : streets) {
            oal_street.add(s.getStreetName());
        }
        com_street.setItems(oal_street);
    }

    public void autoCompleteAssessmant() {
        ArrayList<String> assessNoList = modle.GetInstans.getAssessmantModle().getAssessmantNoListByStreetAndWard(com_ward.getSelectionModel().getSelectedItem(), com_street.getSelectionModel().getSelectedItem());

        TextFields.bindAutoCompletion(txt_assessmant, assessNoList);
    }

    public void clear() {
        positionPojo = null;
        idAssessmant = 0;
        idPosition = 0;
        txt_position.setText(null);
        txt_limt.setText("0");
        txt_owner.setText(null);
        txt_nic.setText(null);
        com_ward.getSelectionModel().clearSelection();
        com_street.getSelectionModel().clearSelection();
        txt_assessmant.setText(null);
        txt_address.setText(null);
        txt_rent.setText("0");
        loadPositionTable();
        Runtime.getRuntime().gc();
    }

    public void loadPositionTable() {
        ObservableList<PositionTable> arList = FXCollections.observableArrayList();
        List<AdvPosition> possitionList = modle.GetInstans.getPositionModle().getPossitionList();
        for (AdvPosition p : possitionList) {
            arList.add(new PositionTable(p.getIdAdvPosition(), p.getAdvPositionName(), p.getAdvPositionLimit().toString(), modle.GetInstans.getPositionModle().getCurrentAddByPossitionID(p.getIdAdvPosition()).toString(), p.getAdvPositionOwner(), p.getAdvPositionGroundRent().toString()));
        }

        col_pos.setCellValueFactory(new PropertyValueFactory<>("posname"));
        col_limit.setCellValueFactory(new PropertyValueFactory<>("limit"));
        col_current.setCellValueFactory(new PropertyValueFactory<>("currentAdd"));
        col_owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
        col_rent.setCellValueFactory(new PropertyValueFactory<>("rent"));

        tbl_possiton.setItems(arList);

    }

    @FXML
    private void clickOnClear(MouseEvent event) {
        clear();
    }

    public class PositionTable {

        private int idPosition;
        private SimpleStringProperty posname;
        private SimpleStringProperty limit;
        private SimpleStringProperty currentAdd;
        private SimpleStringProperty owner;
        private SimpleStringProperty rent;

        public PositionTable(int idPosition, String posname, String limit, String currentAdd, String owner, String rent) {
            this.idPosition = idPosition;
            this.posname = new SimpleStringProperty(posname);
            this.limit = new SimpleStringProperty(limit);
            this.currentAdd = new SimpleStringProperty(currentAdd);
            this.owner = new SimpleStringProperty(owner);
            this.rent = new SimpleStringProperty(rent);
        }

        /**
         * @return the idPosition
         */
        public int getIdPosition() {
            return idPosition;
        }

        /**
         * @return the posname
         */
        public String getPosname() {
            return posname.get();
        }

        /**
         * @return the limit
         */
        public String getLimit() {
            return limit.get();
        }

        /**
         * @return the currentAdd
         */
        public String getCurrentAdd() {
            return currentAdd.get();
        }

        /**
         * @return the owner
         */
        public String getOwner() {
            return owner.get();
        }

        /**
         * @return the rent
         */
        public String getRent() {
            return rent.get();
        }

    }

    @FXML
    private void selectFromPossitionTable(MouseEvent event) {
        PositionTable selectedItem = tbl_possiton.getSelectionModel().getSelectedItem();
        idPosition = selectedItem.getIdPosition();
        fillDataBySelected();
    }

    public void fillDataBySelected() {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            positionPojo = (AdvPosition) session.load(AdvPosition.class, idPosition);
            //   positionPojo = pos;
            txt_position.setText(positionPojo.getAdvPositionName());
            txt_limt.setText(positionPojo.getAdvPositionLimit().toString());
            txt_owner.setText(positionPojo.getAdvPositionOwner());
            txt_nic.setText(positionPojo.getAdvPositionNic());
            txt_address.setText(positionPojo.getAdvPositionAddress());
            txt_rent.setText(positionPojo.getAdvPositionGroundRent().toString());
            System.out.println(positionPojo.getAdvPositionAssessmantNo());
            if (!positionPojo.getAdvPositionAssessmantNo().equals("0")) {
                Assessment ass = (Assessment) session.load(Assessment.class, Integer.parseInt(positionPojo.getAdvPositionAssessmantNo()));
                txt_assessmant.setText(ass.getAssessmentNo());
                com_ward.getSelectionModel().select(ass.getStreet().getWard().getWardName());
                loadSteetCombo();
                com_street.getSelectionModel().select(ass.getStreet().getStreetName());
            } else {
                com_ward.getSelectionModel().clearSelection();
                com_street.getSelectionModel().clearSelection();
                txt_assessmant.setText(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @FXML
    void assessKeyReleased(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {

            String ward = com_ward.getSelectionModel().getSelectedItem();
            String street = com_street.getSelectionModel().getSelectedItem();
            String text = txt_assessmant.getText();
            if (ward != null && street != null && text != null) {

                String assOwnerNameByWSA = GetInstans.getAssessmantModle().getAssOwnerNameByWSA(ward, street, txt_assessmant.getText());
                txt_owner.setText(assOwnerNameByWSA);
            }
        }
    }

}
