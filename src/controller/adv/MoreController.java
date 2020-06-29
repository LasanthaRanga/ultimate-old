/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.adv;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import modle.adv.AdvTableRow;
import org.hibernate.Session;
import pojo.AdvAdvertising;
import pojo.AdvBords;
import pojo.Customer;
import pojo.SendToApprove;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class MoreController implements Initializable {

    @FXML
    private Label lbl_sdate;
    @FXML
    private Label lbl_edate;
    @FXML
    private Text lbl_discription;
    @FXML
    private TableView<AdvTableRow> tbl_board;
    @FXML
    private TableColumn<?, ?> col_id;
    @FXML
    private TableColumn<?, ?> col_location;
    @FXML
    private TableColumn<?, ?> col_groundRent;
    @FXML
    private TableColumn<?, ?> col_boardType;
    @FXML
    private TableColumn<?, ?> col_unitPrice;
    @FXML
    private TableColumn<?, ?> col_width;
    @FXML
    private TableColumn<?, ?> col_height;
    @FXML
    private TableColumn<?, ?> col_side;
    @FXML
    private TableColumn<?, ?> col_squareFeet;
    @FXML
    private TableColumn<?, ?> col_qty;
    @FXML
    private TableColumn<?, ?> col_total_price;
    @FXML
    private Label lbl_add_tot;
    @FXML
    private Label lbl_ground_tot;
    @FXML
    private Label lbl_diposit_price;
    @FXML
    private Label lbl_visiting_price;
    @FXML
    private Label lbl_other_price;
    @FXML
    private Label lbl_vat;
    @FXML
    private Label lbl_nbt;
    @FXML
    private Label lbl_stamp;
    @FXML
    private Label lbl_fullTot;
    @FXML
    private Label lbl_bordNo;
    @FXML
    private Label lbl_customerName;
    @FXML
    private Label lbl_nic;
    @FXML
    private JFXButton btn_close;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadApplicationData();
    }

    public void loadApplicationData() {

        if (modle.adv.StaticBaduAdv.getAdvApplicationID() > 0) {
            Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
            try {
                //  SendToApprove sta = (pojo.SendToApprove) session.load(pojo.SendToApprove.class, sendToApprovID);
                AdvAdvertising adv = (AdvAdvertising) session.load(AdvAdvertising.class, modle.adv.StaticBaduAdv.getAdvApplicationID());
                Customer customer = (Customer) session.load(Customer.class, adv.getCustomerIdCustomer());
                lbl_bordNo.setText(adv.getAdvBoardNo());
                lbl_sdate.setText(adv.getAdvStartDate().toString());
                lbl_edate.setText(adv.getAdvEndDate().toString());
                lbl_discription.setText(adv.getAdvDiscription());
                lbl_customerName.setText(customer.getCusName());
                lbl_nic.setText(customer.getCusNic());
                //============ Price Load
                lbl_add_tot.setText(adv.getAdvTotal().toString());
                lbl_diposit_price.setText(adv.getAdvDiposit().toString());
                lbl_ground_tot.setText(adv.getAdvGroundTotal().toString());
                lbl_visiting_price.setText(adv.getAdvVisitingPrice().toString());
                lbl_other_price.setText(adv.getAdvOthers().toString());
                lbl_vat.setText(adv.getAdvVat().toString());
                lbl_nbt.setText(adv.getAdvNbt().toString());
                lbl_stamp.setText(adv.getAdvStamp().toString());
                lbl_fullTot.setText(adv.getAdvFullTotal().toString());

                //Table Load
                col_id.setCellValueFactory(new PropertyValueFactory<>("rowId"));
                col_location.setCellValueFactory(new PropertyValueFactory<>("location"));
                col_groundRent.setCellValueFactory(new PropertyValueFactory<>("groundRent"));
                col_boardType.setCellValueFactory(new PropertyValueFactory<>("boardType"));
                col_unitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
                col_width.setCellValueFactory(new PropertyValueFactory<>("width"));
                col_height.setCellValueFactory(new PropertyValueFactory<>("height"));
                col_qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
                col_squareFeet.setCellValueFactory(new PropertyValueFactory<>("sqfeet"));
                col_squareFeet.setCellValueFactory(new PropertyValueFactory<>("sqfeet"));
                col_side.setCellValueFactory(new PropertyValueFactory<>("side"));
                col_total_price.setCellValueFactory(new PropertyValueFactory<>("totPrice"));
                //=====
                ObservableList<AdvTableRow> tableRows = FXCollections.observableArrayList();
                Set<AdvBords> advBordses = adv.getAdvBordses();
                for (AdvBords bord : advBordses) {
                    tableRows.add(new AdvTableRow(bord.getIdAdvBords(), bord.getAdvBordSingleOrDouble(),
                            bord.getAdvPosition().getAdvPositionName(),
                            "Type", bord.getAdvBordUnitPrice(),
                            bord.getAdvBordWidth(),
                            bord.getAdvBordHeight(),
                            bord.getAdvBordQty(),
                            bord.getAdvBordSquareFeet(),
                            bord.getAdvBordQty() * bord.getAdvBordSquareFeet(),
                            bord.getAdvBordTotalPrice(),
                            bord.getAdvBordGroundRent()));
                }
                tbl_board.setItems(tableRows);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        } else {

            int sendToApprovID = modle.adv.StaticBaduAdv.getSendToApprovID();
            Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
            try {
                SendToApprove sta = (SendToApprove) session.load(SendToApprove.class, sendToApprovID);
                AdvAdvertising adv = (AdvAdvertising) session.load(AdvAdvertising.class, sta.getApplicationId());
                Customer customer = (Customer) session.load(Customer.class, adv.getCustomerIdCustomer());
                lbl_bordNo.setText(adv.getAdvBoardNo());
                lbl_sdate.setText(adv.getAdvStartDate().toString());
                lbl_edate.setText(adv.getAdvEndDate().toString());
                lbl_discription.setText(adv.getAdvDiscription());
                lbl_customerName.setText(customer.getCusName());
                lbl_nic.setText(customer.getCusNic());
                //============ Price Load
                lbl_add_tot.setText(adv.getAdvTotal().toString());
                lbl_diposit_price.setText(adv.getAdvDiposit().toString());
                lbl_ground_tot.setText(adv.getAdvGroundTotal().toString());
                lbl_visiting_price.setText("00");
                lbl_other_price.setText(adv.getAdvOthers().toString());
                lbl_vat.setText(adv.getAdvVat().toString());
                lbl_nbt.setText(adv.getAdvNbt().toString());
                lbl_stamp.setText(adv.getAdvStamp().toString());
                lbl_fullTot.setText(adv.getAdvFullTotal().toString());

                //Table Load
                col_id.setCellValueFactory(new PropertyValueFactory<>("rowId"));
                col_location.setCellValueFactory(new PropertyValueFactory<>("location"));
                col_groundRent.setCellValueFactory(new PropertyValueFactory<>("groundRent"));
                col_boardType.setCellValueFactory(new PropertyValueFactory<>("boardType"));
                col_unitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
                col_width.setCellValueFactory(new PropertyValueFactory<>("width"));
                col_height.setCellValueFactory(new PropertyValueFactory<>("height"));
                col_qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
                col_squareFeet.setCellValueFactory(new PropertyValueFactory<>("sqfeet"));
                col_squareFeet.setCellValueFactory(new PropertyValueFactory<>("sqfeet"));
                col_side.setCellValueFactory(new PropertyValueFactory<>("side"));
                col_total_price.setCellValueFactory(new PropertyValueFactory<>("totPrice"));
                //=====
                ObservableList<AdvTableRow> tableRows = FXCollections.observableArrayList();
                Set<AdvBords> advBordses = adv.getAdvBordses();
                for (AdvBords bord : advBordses) {
                    tableRows.add(new AdvTableRow(bord.getIdAdvBords(), bord.getAdvBordSingleOrDouble(),
                            bord.getAdvPosition().getAdvPositionName(),
                            "Type", bord.getAdvBordUnitPrice(),
                            bord.getAdvBordWidth(),
                            bord.getAdvBordHeight(),
                            bord.getAdvBordQty(),
                            bord.getAdvBordSquareFeet(),
                            bord.getAdvBordQty() * bord.getAdvBordSquareFeet(),
                            bord.getAdvBordTotalPrice(),
                            bord.getAdvBordGroundRent()));
                }
                tbl_board.setItems(tableRows);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }

        }

    }

    @FXML
    private void close(ActionEvent event) {
        modle.adv.StaticBaduAdv.setAdvApplicationID(0);
        btn_close.getScene().getWindow().hide();
        modle.adv.StaticBaduAdv.setSendToApprovID(0);
    }

}
