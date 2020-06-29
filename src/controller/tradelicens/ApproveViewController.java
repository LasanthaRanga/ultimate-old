/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.tradelicens;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import conn.NewHibernateUtil;
import controller.adv.Customer_regController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import modle.GetInstans;
import modle.StaticViews;
import modle.tradelicens.StaticBadu;
import modle.tradelicens.TL_Approve;
import org.hibernate.Session;
import pojo.ApprovalCat;
import pojo.User;
import pojo.UserHasApprovalCat;
import view.buttons.BTN;

/**
 * FXML Controller class
 *
 * @author Ranga
 */
public class ApproveViewController implements Initializable {

    @FXML
    private TableView<TL_Approve.AppList> tbl_appList;

    @FXML
    private TableColumn<TL_Approve.AppList, Integer> col_id;

    @FXML
    private TableColumn<TL_Approve.AppList, String> col_no;

    @FXML
    private TableColumn<TL_Approve.AppList, String> col_tradeName;

    @FXML
    private TableColumn<TL_Approve.AppList, String> col_natrue;

    @FXML
    private TableColumn<TL_Approve.AppList, String> col_cusName;

    @FXML
    private TableColumn<TL_Approve.AppList, String> col_assessment;

    @FXML
    private Text txt_ApproveCat;


    @FXML
    private JFXTextField txt_tradeName;

    @FXML
    private JFXTextField txt_cusName;

    @FXML
    private JFXRadioButton radio_pending;

    @FXML
    private ToggleGroup status;

    @FXML
    private JFXRadioButton radio_approve;

    @FXML
    private JFXRadioButton radio_non;

    @FXML
    private JFXButton btn_more;

    @FXML
    private Text txt_appno;

    @FXML
    private Text txt_count;

    int approvalcatid = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        Integer idUser = StaticViews.getLogUser().getIdUser();

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            User load = (User) session.load(User.class, idUser);
            Set<UserHasApprovalCat> userHasApprovalCats = load.getUserHasApprovalCats();
            for (UserHasApprovalCat ac : userHasApprovalCats) {
                ApprovalCat approvalCat = ac.getApprovalCat();

                approvalcatid = approvalCat.getIdApprovalCat();
                modle.tradelicens.StaticBadu.setApprovalcatid(approvalcatid);
                txt_ApproveCat.setText(approvalCat.getApprovalName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }


        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_no.setCellValueFactory(new PropertyValueFactory<>("no"));
        col_tradeName.setCellValueFactory(new PropertyValueFactory<>("trade_name"));
        col_cusName.setCellValueFactory(new PropertyValueFactory<>("cus_name"));
        col_natrue.setCellValueFactory(new PropertyValueFactory<>("trade_nature"));
        col_assessment.setCellValueFactory(new PropertyValueFactory<>("assessment"));

        loadTable("", "");

    }

    int idApp = 0;

    @FXML
    void clickOnTable(MouseEvent event) {

        TL_Approve.AppList selectedItem = tbl_appList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            idApp = selectedItem.getId();
            txt_appno.setText(selectedItem.getNo());
        }

    }

    @FXML
    void statusRadioOnAction(ActionEvent event) {
        loadTable("", "");
    }


    public void loadTable(String cus_name, String trade_name) {
        int status = 0;
        if (radio_pending.isSelected()) {
            status = 0;
        }
        if (radio_approve.isSelected()) {
            status = 1;
        }
        if (radio_non.isSelected()) {
            status = 2;
        }

        ObservableList<TL_Approve.AppList> appLists = GetInstans.getTl_approve().loadApplicationToTable(approvalcatid, status, cus_name, trade_name);

        int size = appLists.size();

        txt_count.setText(size+"");

        tbl_appList.setItems(appLists);

    }

    @FXML
    void searchByCusNames(KeyEvent event) {
        txt_tradeName.setText("");
        loadTable(txt_cusName.getText(), txt_tradeName.getText());
    }

    @FXML
    void searchByTradeNames(KeyEvent event) {
        txt_cusName.setText("");
        loadTable(txt_cusName.getText(), txt_tradeName.getText());
    }

    @FXML
    void clickOnMore(MouseEvent event) {
        if (idApp > 0) {
            modle.tradelicens.StaticBadu.setAppId(idApp);


            AnchorPane container = modle.StaticViews.getContainer();
            container.getChildren().removeAll();
            container.getChildren().clear();
            AnchorPane dashh;
            try {
                dashh = FXMLLoader.load(getClass().getResource("/view/tradelicens/accApprove.fxml"));
                container.getChildren().add(dashh);
            } catch (IOException ex) {
                ex.printStackTrace();
                Logger
                        .getLogger(Customer_regController.class
                                .getName()).log(Level.SEVERE, null, ex);
            }
            Set<String> keySet = modle.StaticViews.getButtonMap().keySet();
            String s = "/view/buttons/TL_AccApprove.fxml";


            for (String string : keySet) {
                if (string.equals(s)) {
                    try {
                        JFXButton get = modle.StaticViews.getButtonMap().get(string);
                        BTN get1 = modle.StaticViews.getBtnConMap().get(string);
                        get1.setImage("/grafics/Details_b.png");
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


        } else {
            modle.Allert.notificationInfo("Not Selected", "please select application for more details");
        }


    }

    @FXML
    private JFXButton btn_print_pending;

    @FXML
    void clicOnPrintPanding(MouseEvent event) {
        System.out.println("approvalid is "+approvalcatid);
        modle .GetInstans.getTl_reports().getPendingList(approvalcatid+"");
    }

}
