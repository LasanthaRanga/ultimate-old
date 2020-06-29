/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.adv;

import com.jfoenix.controls.*;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import modle.ConfirmDialog;
import modle.adv.AdvTableRow;
import org.controlsfx.control.textfield.TextFields;
import pojo.AdvAdvertising;
import pojo.AdvBoardType;
import pojo.AdvBords;
import pojo.AdvPosition;
import view.buttons.BTN;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class ApplicationController implements Initializable {

    @FXML
    private JFXTextField txt_cusName;
    @FXML
    private JFXTextField txt_nic;
    @FXML
    private JFXDatePicker date_start;
    @FXML
    private JFXDatePicker date_end;
    @FXML
    private JFXTextField txt_board_no;
    @FXML
    private JFXRadioButton radio_temporary;
    @FXML
    private ToggleGroup TP;
    @FXML
    private JFXRadioButton radio_permanent;
    @FXML
    private JFXTextField txt_location;
    @FXML
    private JFXTextField txt_width;
    @FXML
    private JFXTextField txt_height;
    @FXML
    private JFXRadioButton radio_oneSide;
    @FXML
    private ToggleGroup side;
    @FXML
    private JFXRadioButton radio_borthSide;
    @FXML
    private JFXTextField txt_oneBoardSquare;
    @FXML
    private JFXTextField txt_qty;
    @FXML
    private JFXTextField txt_unitPrice;
    @FXML
    private JFXComboBox<String> com_boardType;
    @FXML
    private JFXTextField txt_total_Square;
    @FXML
    private JFXTextField txt_total_Price;
    @FXML
    private JFXButton btn_add;
    @FXML
    private TableView<modle.adv.AdvTableRow> tbl_board;
    @FXML
    private TableColumn<modle.adv.AdvTableRow, Integer> col_id;
    @FXML
    private TableColumn<modle.adv.AdvTableRow, String> col_location;
    @FXML
    private TableColumn<modle.adv.AdvTableRow, Double> col_groundRent;
    @FXML
    private TableColumn<modle.adv.AdvTableRow, String> col_boardType;
    @FXML
    private TableColumn<modle.adv.AdvTableRow, Double> col_unitPrice;
    @FXML
    private TableColumn<modle.adv.AdvTableRow, Double> col_width;
    @FXML
    private TableColumn<modle.adv.AdvTableRow, Double> col_height;
    @FXML
    private TableColumn<modle.adv.AdvTableRow, Integer> col_side;
    @FXML
    private TableColumn<modle.adv.AdvTableRow, Double> col_squareFeet;
    @FXML
    private TableColumn<modle.adv.AdvTableRow, Double> col_qty;
    @FXML
    private TableColumn<modle.adv.AdvTableRow, Double> col_total_price;
    @FXML
    private JFXButton btn_remove;
    @FXML
    private JFXButton btn_edit;
    @FXML
    private JFXTextField txt_dipositPrice;
    @FXML
    private JFXTextField txt_groundRent;
    @FXML
    private JFXTextField txt_visitPrice;
    @FXML
    private JFXTextField txt_otherPrice;
    @FXML
    private JFXTextField txt_tot;
    @FXML
    private JFXCheckBox check_vat;
    @FXML
    private JFXCheckBox check_nbt;
    @FXML
    private JFXCheckBox check_stamp;
    @FXML
    private JFXTextField txt_vat;
    @FXML
    private JFXTextField txt_nbt;
    @FXML
    private JFXTextField txt_stamp;
    @FXML
    private JFXButton btn_save;
    @FXML
    private JFXButton btn_send;
    @FXML
    private JFXButton btn_pay;
    @FXML
    private JFXTextField txt_full_total;
    @FXML
    private JFXTextField txt_discription;

    @FXML
    private JFXToggleButton btn_diposit;

    @FXML
    private Text txt_dayCount;

    int idCus;
    int advId = 0;
    int daycount = 1;

    AdvPosition position;
    @FXML
    private JFXTextField txt_groundTot;

    /**
     * position Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        modle.StaticViews.getMc().changeTitle("New Advertisement");
        loadBordTyps();
        positionAutoCompleter();
        if (modle.adv.StaticBaduAdv.getCustomer() != null) {
            txt_cusName.setText(modle.adv.StaticBaduAdv.getCustomer().getCusName());
            txt_nic.setText(modle.adv.StaticBaduAdv.getCustomer().getCusNic());
            idCus = modle.adv.StaticBaduAdv.getCustomer().getIdCustomer();
            txt_cusName.setEditable(false);
            txt_nic.setEditable(false);
        }

    }

    @FXML
    void dateOnAction(ActionEvent event) {
        if (date_start.getValue() != null && date_end.getValue() != null) {
            startDate = Date.from(date_start.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
            endDate = Date.from(date_end.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());

            long dl = endDate.getTime() - startDate.getTime();

            int dcount = (int) TimeUnit.DAYS.convert(dl, TimeUnit.MILLISECONDS);

            if (dcount <= 0) {
                modle.Allert.notificationInfo("please check date", "date not valid");
            } else {
                txt_dayCount.setText(dcount + "");
                daycount = dcount;
            }

        }

    }

    public void loadBordTyps() {
        List<AdvBoardType> boardTypes = modle.GetInstans.getBoardTypeModle().getBoardTypes();

        ObservableList<String> al = FXCollections.observableArrayList();

        for (AdvBoardType bt : boardTypes) {
            al.add(bt.getAdvBoardTypeName());
        }
        com_boardType.setItems(al);
    }

    @FXML
    private void clickOnAdd(MouseEvent event) {
        if (collectMainData()) {
            if (collectTableData()) {
                bordCalculation();
                addTableRow();
                calPaymantPrice();
                callFullTotal();
            }
        }
    }

    public void bordCalculation() {
        unitSquerFeet = width * hight;
        totSquerFeet = unitSquerFeet * qty;
        if (radio_oneSide.isSelected()) {

            if (radio_temporary.isSelected()) {
                totalPrice = totSquerFeet * unitPrice * daycount;
            }
            if (radio_permanent.isSelected()) {
                totalPrice = totSquerFeet * unitPrice;
            }

        } else {
            if (radio_temporary.isSelected()) {
                totalPrice = totSquerFeet * unitPrice * daycount;
            }
            if (radio_permanent.isSelected()) {
                totalPrice = totSquerFeet * unitPrice;
            }
            totalPrice += totalPrice * 0.75;// Both side presantage 
        }
        txt_oneBoardSquare.setText(modle.Round.roundToString(unitSquerFeet));
        txt_total_Square.setText(modle.Round.roundToString(totSquerFeet));
        txt_total_Price.setText(modle.Round.roundToString(totalPrice));
    }

    ObservableList<modle.adv.AdvTableRow> tableRows = FXCollections.observableArrayList();

    public void addTableRow() {
        int x = tableRows.size() + 1;
        tableRows.add(new AdvTableRow(x, bordSide, position.getAdvPositionName(), com_boardType.getSelectionModel().getSelectedItem(), unitPrice, width, hight, qty, unitSquerFeet, totSquerFeet, totalPrice, groundRent));
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
        tbl_board.setItems(tableRows);
        clear();

    }

    Date startDate = null;
    Date endDate = null;
    String boardNo = null;
    String discription;
    String pt;

    public boolean collectMainData() {
        if (idCus > 0) {
            if (date_start.getValue() != null && date_end.getValue() != null) {
                startDate = Date.from(date_start.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
                endDate = Date.from(date_end.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());

                long dl = endDate.getTime() - startDate.getTime();

                long dcount = TimeUnit.DAYS.convert(dl, TimeUnit.MILLISECONDS);

                System.out.println(dcount);

                daycount = (int) dcount;

                if (daycount <= 0) {
                    modle.Allert.notificationError("please cheack date", "date not valid");
                    return false;
                } else {
                    boardNo = txt_board_no.getText();
                    if (boardNo.length() > 1) {
                        discription = txt_discription.getText();

                        if (radio_temporary.isSelected()) {
                            pt = radio_temporary.getText();
                        } else {
                            pt = radio_permanent.getText();
                        }
                        return true;
                    } else {
                        modle.Allert.notificationError("Error", "Plese Enter Board No");
                        return false;
                    }
                }
            } else {
                modle.Allert.notificationError("Error", "Plese Select Date");
                return false;
            }
        } else {
            modle.Allert.notificationError("Error", "Customer Details not match");
            return false;
        }
    }

    double groundRent;
    double totalPrice;
    double unitSquerFeet;
    double totSquerFeet;
    double unitPrice;
    double width;
    double hight;
    int bordSide;
    double qty;
    double calVNS;
    String boardType;

    public boolean collectTableData() {
        if (radio_oneSide.isSelected()) {
            bordSide = 1;
        } else if (radio_borthSide.isSelected()) {
            bordSide = 2;
        }
        boardType = com_boardType.getSelectionModel().getSelectedItem();
        if (boardType != null) {
            position = modle.GetInstans.getPositionModle().getPosition(txt_location.getText());
            if (position != null) {
                groundRent = position.getAdvPositionGroundRent();
                try {
                    unitPrice = Double.parseDouble(txt_unitPrice.getText());
                    try {
                        width = Double.parseDouble(txt_width.getText());
                        hight = Double.parseDouble(txt_height.getText());
                        try {
                            qty = Double.parseDouble(txt_qty.getText());
                            return true;
                        } catch (NumberFormatException e) {
                            modle.Allert.notificationError("Error", "Please Check Qty");
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        modle.Allert.notificationError("Error", "Please Check Width and Hight");
                        return false;
                    }
                } catch (NumberFormatException e) {
                    modle.Allert.notificationError("Error", "Please Check Unit Price");
                    return false;
                }
            } else {
                modle.Allert.notificationError("Error", "Please Check Location");
                return false;
            }
        } else {
            modle.Allert.notificationError("Error", "Please Check BoardType");
            return false;
        }
    }

    public void positionAutoCompleter() {
        List<AdvPosition> pList = modle.GetInstans.getPositionModle().getPossitionList();
        ArrayList<String> pnameList = new ArrayList<String>();
        for (AdvPosition advPosition : pList) {
            pnameList.add(advPosition.getAdvPositionName());
        }
        TextFields.bindAutoCompletion(txt_location, pnameList);
    }

    @FXML
    private void locationOnAction(ActionEvent event) {

    }

    @FXML
    private void locationOnKeyRelesed(KeyEvent event) {
        AdvPosition position1 = modle.GetInstans.getPositionModle().getPosition(txt_location.getText());
        if (position1 != null) {
            txt_groundRent.setText(position1.getAdvPositionGroundRent() + "");
        } else {
            txt_groundRent.setText("");
        }

        if (event.getCode() == KeyCode.ENTER) {
            txt_unitPrice.requestFocus();
        }

    }

    @FXML
    private void calPriceAndSquareFeet(KeyEvent event) {
        unitPrice = 0;
        width = 0;
        hight = 0;
        qty = 0;
        if (txt_unitPrice.getText().matches("\\d*(\\.\\d*)?") && txt_width.getText().matches("\\d*(\\.\\d*)?") && txt_height.getText().matches("\\d*(\\.\\d*)?") && txt_qty.getText().matches("\\d*(\\.\\d*)?")) {
            try {
                unitPrice = Double.parseDouble(txt_unitPrice.getText());
                width = Double.parseDouble(txt_width.getText());
                hight = Double.parseDouble(txt_height.getText());
                qty = Double.parseDouble(txt_qty.getText());
                bordCalculation();
            } catch (Exception e) {
            }
        } else {
            java.awt.Toolkit.getDefaultToolkit().beep();
            JFXTextField tf = (JFXTextField) event.getSource();
            tf.deletePreviousChar();
        }
    }

    private void selectSide(ActionEvent event) {
        try {
            unitPrice = Double.parseDouble(txt_unitPrice.getText());
            width = Double.parseDouble(txt_width.getText());
            hight = Double.parseDouble(txt_height.getText());
            qty = Double.parseDouble(txt_qty.getText());
            bordCalculation();
        } catch (NumberFormatException e) {
            System.out.println("exception");
        }
    }

    @FXML
    private void selectSideToCal(MouseEvent event) {
        if (radio_oneSide.isSelected()) {
            bordSide = 1;
        } else {
            bordSide = 2;
        }
        try {
            unitPrice = Double.parseDouble(txt_unitPrice.getText());
            width = Double.parseDouble(txt_width.getText());
            hight = Double.parseDouble(txt_height.getText());
            qty = Double.parseDouble(txt_qty.getText());
            bordCalculation();
        } catch (NumberFormatException e) {
            System.out.println("exception");
        }

    }

    @FXML
    private void clickOnRemove(MouseEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            int selectedIndex = tbl_board.getSelectionModel().getSelectedIndex();
            tableRows.remove(selectedIndex);
            calPaymantPrice();
            callFullTotal();
        } else {
            // ... user chose CANCEL or closed the dialog           
        }

    }

    @FXML
    private void clickOnEdit(MouseEvent event) {
        loadBackToEdit();
        calPaymantPrice();
        callFullTotal();
    }

    public void clear() {
        txt_location.setText("");
        com_boardType.getSelectionModel().clearSelection();
        txt_unitPrice.setText("");
        txt_width.setText("");
        txt_height.setText("");
        txt_qty.setText("");
        txt_groundRent.setText("");
        txt_total_Square.setText("");
        txt_oneBoardSquare.setText("");
        txt_total_Price.setText("");
        totalPrice = 0;
        unitSquerFeet = 0;
        totSquerFeet = 0;
        unitPrice = 0;
        width = 0;
        hight = 0;
        qty = 0;
        boardType = null;
        position = null;
    }

    public void loadBackToEdit() {
        int id = tbl_board.getSelectionModel().getSelectedIndex();
        AdvTableRow row = tableRows.get(id);

        txt_location.setText(row.getLocation());
        com_boardType.getSelectionModel().select(row.getBoardType());
        txt_unitPrice.setText(row.getUnitPrice() + "");
        txt_width.setText(row.getWidth() + "");
        txt_height.setText(row.getHeight() + "");
        txt_qty.setText(row.getQty() + "");
        txt_total_Square.setText(row.getTotSq() + "");
        txt_oneBoardSquare.setText(row.getSqfeet() + "");
        txt_total_Price.setText(row.getTotPrice() + "");
        totalPrice = 0;
        unitSquerFeet = 0;
        totSquerFeet = 0;
        unitPrice = 0;
        width = 0;
        hight = 0;
        qty = 0;
        boardType = null;
        position = null;
        tableRows.remove(id);
    }

    double groundTot = 0;
    double allTot = 0;

    public void calPaymantPrice() {
        groundTot = 0;
        allTot = 0;
        for (AdvTableRow tableRow : tableRows) {
            allTot += tableRow.getTotPrice();
            groundTot += tableRow.getGroundRent();
        }

        double dip = allTot * 0.25;
        txt_dipositPrice.setText(dip + "");
        txt_tot.setText(allTot + "");
        txt_groundTot.setText(groundTot + "");

        calVNS = allTot;
    }

    double vat;
    double nbt;
    double stamp;

    @FXML
    private void vatCheack(ActionEvent event) {
        callFullTotal();
    }

    @FXML
    private void nbtCheack(ActionEvent event) {
        callFullTotal();
    }

    @FXML
    private void stampCheack(ActionEvent event) {
        callFullTotal();
    }

    double fullTOT;
    double diposit = 0;
    double visitAndCheack = 0;
    double otherPrice = 0;

    public void callFullTotal() {

        fullTOT = 0;

        fullTOT += modle.Round.round(Double.parseDouble(txt_tot.getText()));
        fullTOT += modle.Round.round(Double.parseDouble(txt_groundTot.getText()));
        //vat
        if (check_vat.isSelected()) {
            double interestRate = modle.GetInstans.getInterest().getInterestRate("VAT");
            vat = modle.Round.round(calVNS * interestRate / 100);
            txt_vat.setText(vat + "");
            fullTOT += Double.parseDouble(txt_vat.getText());
        } else {
            vat = 00;
            txt_vat.setText("00");
        }
        //nbt
        if (check_nbt.isSelected()) {
            double interestRate = modle.GetInstans.getInterest().getInterestRate("NBT");
            nbt = modle.Round.round(calVNS * interestRate / 100);
            txt_nbt.setText(nbt + "");
            fullTOT += Double.parseDouble(txt_nbt.getText());
        } else {
            nbt = 00;
            txt_nbt.setText("00");
        }
        //stamp
        if (check_stamp.isSelected()) {
            double interestRate = modle.GetInstans.getInterest().getInterestRate("STAMP");
            stamp = modle.Round.round(calVNS * interestRate / 100);
            txt_stamp.setText(stamp + "");
            fullTOT += Double.parseDouble(txt_stamp.getText());
        } else {
            stamp = 00;
            txt_stamp.setText("00");
        }
        //Diposit
        try {
            if (txt_dipositPrice.getText().length() > 0) {
                diposit = modle.Round.round(Double.parseDouble(txt_dipositPrice.getText()));
            } else {
                diposit = 0;
            }
        } catch (NumberFormatException e) {
        }
        //Visiting
        try {
            if (txt_visitPrice.getText().length() > 0) {
                visitAndCheack = modle.Round.round(Double.parseDouble(txt_visitPrice.getText()));
            } else {
                visitAndCheack = 0;
            }
        } catch (NumberFormatException e) {
        }
        //Other Price
        try {
            if (txt_otherPrice.getText().length() > 0) {
                otherPrice = modle.Round.round(Double.parseDouble(txt_otherPrice.getText()));
            } else {
                otherPrice = 0;
            }
        } catch (NumberFormatException e) {
        }

        if (btn_diposit.isSelected()) {

        } else {
            fullTOT += modle.Round.round(diposit);
        }


        fullTOT += modle.Round.round(visitAndCheack);
        fullTOT += modle.Round.round(otherPrice);

        txt_full_total.setText(modle.Round.round(fullTOT) + "");
    }

    @FXML
    private void dipositKeyReeased(KeyEvent event) {
        if (txt_dipositPrice.getText().matches("\\d*(\\.\\d*)?")) {
            callFullTotal();
        } else {
            txt_dipositPrice.deletePreviousChar();
            java.awt.Toolkit.getDefaultToolkit().beep();
        }

    }

    @FXML
    private void visitKeyReeased(KeyEvent event) {
        if (txt_visitPrice.getText().matches("\\d*(\\.\\d*)?")) {
            callFullTotal();
        } else {
            txt_visitPrice.deletePreviousChar();
            java.awt.Toolkit.getDefaultToolkit().beep();
        }

    }

    @FXML
    private void otherKeyReeased(KeyEvent event) {
        if (txt_otherPrice.getText().matches("\\d*(\\.\\d*)?")) {
            callFullTotal();
        } else {
            txt_otherPrice.deletePreviousChar();
            java.awt.Toolkit.getDefaultToolkit().beep();
        }
    }

    @FXML
    void removeDiposit(ActionEvent event) {
        callFullTotal();
    }

    @FXML
    private void clickOnSave(MouseEvent event) {
        AdvAdvertising adv = new AdvAdvertising();
        adv.setCustomerIdCustomer(idCus);
        adv.setAdvStartDate(startDate);
        adv.setAdvEndDate(endDate);
        adv.setAdvBoardNo(boardNo);
        adv.setAdvPt(pt);
        adv.setAdvDiscription(discription);

        adv.setAdvTotal(allTot);
        adv.setAdvVat(vat);
        adv.setAdvNbt(nbt);
        adv.setAdvStamp(stamp);

        adv.setAdvGroundTotal(groundTot);
        if (btn_diposit.isSelected()) {
            adv.setAdvDiposit(0.0);
        } else {
            adv.setAdvDiposit(diposit);
        }

        adv.setAdvOthers(otherPrice);
        adv.setAdvVisitingPrice(visitAndCheack);
        adv.setAdvFullTotal(fullTOT);

        adv.setAdvCurrentDate(new Date());

        adv.setAdvPaidNotpaid(0);
        adv.setAdvStatus(1);
        adv.setAdvSyn(1);

        ArrayList<AdvBords> bords = new ArrayList<AdvBords>();

        for (AdvTableRow tableRow : tableRows) {
            AdvBords bord = new AdvBords();
            bord.setAdvBordWidth(tableRow.getWidth());
            bord.setAdvBordHeight(tableRow.getHeight());
            bord.setAdvBordSquareFeet(tableRow.getSqfeet());
            bord.setAdvBordQty(tableRow.getQty());
            bord.setAdvBordSingleOrDouble(tableRow.getSide());
            bord.setAdvBordUnitPrice(tableRow.getUnitPrice());
            bord.setAdvBordGroundRent(tableRow.getGroundRent());
            bord.setAdvBordTotalPrice(tableRow.getTotPrice());
            bord.setAdvBordStatus(1);
            bord.setAdvBordSyn(1);
            bord.setAdvBoardType(modle.GetInstans.getBoardTypeModle().getBoardType(tableRow.getBoardType()));
            bord.setAdvPosition(modle.GetInstans.getPositionModle().getPosition(tableRow.getLocation()));
            bord.setAdvAdvertising(adv);
            bords.add(bord);
        }

        advId = modle.GetInstans.getAdvertisingModle().saveAdd(adv, bords);
        if (advId > 0) {
            modle.Allert.notificationGood("Save", boardNo);
            btn_send.setDisable(false);
            btn_pay.setDisable(false);
        } else {
            modle.Allert.notificationError("Error", boardNo);
        }

    }

    @FXML
    private void clickOnSend(MouseEvent event) {

        if (advId > 0) {
            boolean sendToApprove = modle.GetInstans.getSendToApprove().sendToApprove(advId);
            if (sendToApprove) {
                modle.Allert.notificationGood("Sent", "");
            }
        } else {
        }

    }

    @FXML
    private void clickOnPay(MouseEvent event) {

        modle.adv.StaticBaduAdv.setAdvApplicationID(advId);

        if (modle.adv.StaticBaduAdv.getAdvApplicationID() > 0) {

            AnchorPane container = modle.StaticViews.getContainer();
            container.getChildren().removeAll();
            container.getChildren().clear();
            AnchorPane dashh;

            try {
                dashh = FXMLLoader.load(getClass().getResource("/view/adv/Payment.fxml"));
                container.getChildren().add(dashh);
            } catch (IOException ex) {
                ex.printStackTrace();
                Logger.getLogger(Customer_regController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Set<String> keySet = modle.StaticViews.getButtonMap().keySet();
            String s = "/view/buttons/adv_payment.fxml";

            for (String string : keySet) {
                if (string.equals(s)) {
                    try {
                        JFXButton get = modle.StaticViews.getButtonMap().get(string);
                        BTN get1 = modle.StaticViews.getBtnConMap().get(string);
                        get1.setImage("/grafics/pay_b.png");
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

    @FXML
    private void widthKeyType(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txt_height.requestFocus();
        }
    }

    @FXML
    private void heightKeyType(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txt_qty.requestFocus();
        }
    }

    @FXML
    private void qtyKeyType(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            radio_oneSide.requestFocus();
        }
    }

    @FXML
    private void unitKeyType(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txt_width.requestFocus();
        }
    }

}
