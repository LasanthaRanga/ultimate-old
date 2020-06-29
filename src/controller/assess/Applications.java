package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modle.GetInstans;
import modle.asses.ApplicationHolder;
import modle.asses.ApplicationsModle;
import modle.asses.HolderAssess;

import java.net.URL;
import java.util.ResourceBundle;

public class Applications implements Initializable {
    @FXML
    private TableView<ApplicationHolder> tbl_applications;

    @FXML
    private TableColumn<ApplicationHolder, String> col_refno;

    @FXML
    private TableColumn<ApplicationHolder, String> col_apptype;

    @FXML
    private TableColumn<ApplicationHolder, String> col_date;

    @FXML
    private TableColumn<ApplicationHolder, String> col_names;

    @FXML
    private JFXToggleButton btn_completed;

    @FXML
    private Text txt_user;

    @FXML
    private Text txt_description;

    @FXML
    private TableView<HolderAssess> tbl_assessment;

    @FXML
    private TableColumn<HolderAssess, String> col_ward;

    @FXML
    private TableColumn<HolderAssess, String> col_street;

    @FXML
    private TableColumn<HolderAssess, String> col_assno;

    @FXML
    private JFXButton btn_application;

    @FXML
    private TableView<HolderAssess> tbl_assessment1;

    @FXML
    private TableColumn<HolderAssess, String> col_ward1;

    @FXML
    private TableColumn<HolderAssess, String> col_street1;

    @FXML
    private TableColumn<HolderAssess, String> col_assno1;

    @FXML
    private JFXButton btn_laters;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableCollomAssing();
        loadAppList();
    }

    public void tableCollomAssing() {
        col_refno.setCellValueFactory(new PropertyValueFactory<>("refno"));
        col_apptype.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_names.setCellValueFactory(new PropertyValueFactory<>("names"));

        col_ward.setCellValueFactory(new PropertyValueFactory<>("ward"));
        col_street.setCellValueFactory(new PropertyValueFactory<>("street"));
        col_assno.setCellValueFactory(new PropertyValueFactory<>("assessmentNo"));

        col_ward1.setCellValueFactory(new PropertyValueFactory<>("ward"));
        col_street1.setCellValueFactory(new PropertyValueFactory<>("street"));
        col_assno1.setCellValueFactory(new PropertyValueFactory<>("assessmentNo"));


    }


    @FXML
    void togalOnAction(ActionEvent event) {
        txt_description.setText(null);
        txt_description.setText(null);
        loadAppList();
        tbl_applications.setDisable(false);
    }

    public void loadAppList() {
        ObservableList<ApplicationHolder> applicationsList = null;
        if (btn_completed.isSelected()) {
            applicationsList = GetInstans.getApplicationsModle().getApplicationsList(1);
            System.out.println("call");
        } else {
            applicationsList = GetInstans.getApplicationsModle().getApplicationsList(0);
            System.out.println("call 1");
        }
        tbl_applications.setItems(applicationsList);
    }

    int selectedAppId = 0;

    @FXML
    void selectFromTable(MouseEvent event) {
        ApplicationHolder selectedItem = tbl_applications.getSelectionModel().getSelectedItem();

        if (selectedAppId == 0) {
            if (selectedItem != null) {
                txt_user.setText(selectedItem.getUser());
                txt_description.setText(selectedItem.getDescriptions());
                loadAssesTable();
                loadAssesTable2();
            }
        }
    }

    public void loadAssesTable() {
        ApplicationHolder selectedItem = tbl_applications.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int id = selectedItem.getId();
            ObservableList<HolderAssess> holderAssesses = GetInstans.getApplicationsModle().loadAssessment(id);
            tbl_assessment.setItems(holderAssesses);

        }
    }

    public void loadAssesTable2() {
        ApplicationHolder selectedItem = tbl_applications.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int id = selectedItem.getId();
            ObservableList<HolderAssess> holderAssesses = GetInstans.getApplicationsModle().loadAssessment2(id);
            tbl_assessment1.setItems(holderAssesses);

        }
    }


    @FXML
    void clickOnApplication(MouseEvent event) {
        ApplicationHolder selectedItem = tbl_applications.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedAppId = selectedItem.getId();
            tbl_applications.setDisable(true);
            System.out.println(selectedAppId);
            if (selectedItem.getTypeid() == 1) {
                // sub divition
                modle.StaticBadu.setAss_app_id(selectedAppId);
                modle.StaticBadu.setAss_app_type(1);
                addNew();
            }

            if (selectedItem.getTypeid() == 2) {
                // Amalgamation
                modle.StaticBadu.setAss_app_id(selectedAppId);
                modle.StaticBadu.setAss_app_type(2);
                addNew();

            }
        }
    }


    public void addNew() {

        modle.StaticBadu.setApplications(this);

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/assess/update_excel.fxml"));
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

    @FXML
    void clickOnLaters(ActionEvent event) {
        if (tbl_applications.getSelectionModel().getSelectedItem() != null && tbl_assessment1.getSelectionModel().getSelectedItem() != null) {
            int appid = tbl_applications.getSelectionModel().getSelectedItem().getId();
            int idAssess = tbl_assessment1.getSelectionModel().getSelectedItem().getIdAssess();
            modle.StaticBadu.setSelectedNewAssess(idAssess);
            modle.StaticBadu.setSelectedApp(appid);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/assess/AppLaters.fxml"));
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


    @FXML
    void selectFromNew(MouseEvent event) {
//        HolderAssess newassess = tbl_assessment1.getSelectionModel().getSelectedItem();
//        ApplicationHolder appid = tbl_applications.getSelectionModel().getSelectedItem();
//        if (newassess != null && appid != null) {
//            modle.StaticBadu.setSelectedNewAssess(newassess.getIdAssess());
//            modle.StaticBadu.setSelectedApp(appid.getId());
//
//
//        }
    }


}
