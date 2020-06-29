///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package controller.sr;
//
//import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXComboBox;
//import com.jfoenix.controls.JFXTextArea;
//import com.jfoenix.controls.JFXTextField;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.Pane;
//import modle.sr.Shop;
//import modle.sr.NodeUtils;
//
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
///**
// * FXML Controller class
// *
// * @author Sampath
// */
//public class ShopController implements Initializable {
//
//    @FXML
//    private JFXComboBox<Shop._SrBuilding> com_building;
//    @FXML
//    private JFXComboBox<Shop._SrFlow> com_flow;
//    @FXML
//    public JFXComboBox<Shop._SrShopType> com_shopType;
//    @FXML
//    private JFXTextField txt_shop_number;
//    @FXML
//    private JFXTextField txt_shop_code;
//    @FXML
//    private JFXButton btn_save;
//    @FXML
//    private JFXTextArea ta_description;
//    @FXML
//    private TableView<Shop._SrShop> table_shop;
//    @FXML
//    private TableColumn<?, ?> tc_building;
//    @FXML
//    private TableColumn<?, ?> tc_flow;
//    @FXML
//    private TableColumn<?, ?> tc_shopType;
//    @FXML
//    private TableColumn<?, ?> tc_shopCode;
//    @FXML
//    private TableColumn<?, ?> tc_shopDescription;
//    @FXML
//    private TableColumn<?, ?> tc_shopNumber;
//    @FXML
//    private Pane form;
//
//    /**
//     * Initializes the controller class.
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        modle.GetInstans.getShop().LoadBuildings(com_building);
//        modle.GetInstans.getShop().LoadShopTypes(com_shopType);
//        modle.GetInstans.getShop().LoadShops(table_shop, tc_building, tc_flow, tc_shopType, tc_shopCode, tc_shopDescription, tc_shopNumber);
//    }
//
//    @FXML
//    private void loadFlows(ActionEvent event) {
//        Shop._SrBuilding selectedBuilding = com_building.getSelectionModel().getSelectedItem();
//        modle.GetInstans.getShop().LoadBuildingFlows(com_flow, selectedBuilding);
//
//    }
//
//    @FXML
//    private void save(ActionEvent event) {
//        final Shop._SrBuilding building = com_building.getSelectionModel().getSelectedItem();
//        final Shop._SrFlow flow = com_flow.getSelectionModel().getSelectedItem();
//        final Shop._SrShopType shopType = com_shopType.getSelectionModel().getSelectedItem();
//        System.out.println(shopType);
//        if (com_building.getSelectionModel().isEmpty() || building.getSrBuilding() == null) {
//            com_building.requestFocus();
//            modle.Allert.notificationError("ERROR", "Select Building");
//            return;
//        } else if (com_flow.getSelectionModel().isEmpty() || flow.getSrFlow() == null) {
//            com_flow.requestFocus();
//            modle.Allert.notificationError("ERROR", "Select Flow");
//            return;
//        } else if (com_shopType.getSelectionModel().isEmpty() || shopType.getSrShopType() == null) {
//            com_shopType.requestFocus();
//            modle.Allert.notificationError("ERROR", "Select Shop Type");
//            return;
//        } else if ("".equals(txt_shop_number.getText().trim())) {
//            modle.Allert.notificationError("ERROR", "Add Shop Number");
//            return;
//        }
//        if ("".equals(txt_shop_code.getText().trim())) {
//            modle.Allert.notificationError("ERROR", "Add Shop Code");
//            return;
//        }
//        if (ta_description.getText() != null && "".equals(ta_description.getText().trim())) {
//            modle.Allert.notificationError("ERROR", "Add Shop Description");
//            return;
//        }
//        int shop_number;
//        try {
//            shop_number = Integer.parseInt(txt_shop_number.getText());
//        } catch (Exception e) {
//            modle.Allert.notificationError("ERROR", "Add valid shop number");
//            return;
//        }
//
//        if (modle.GetInstans.getShop().saveShop(
//                selectedShop,
//                building.getSrBuilding(),
//                flow.getSrFlow(),
//                shopType.getSrShopType(),
//                shop_number,
//                txt_shop_code.getText(),
//                ta_description.getText()
//        )) {
//            clearForm();
//            modle.GetInstans.getShop().LoadShops(table_shop, tc_building, tc_flow, tc_shopType, tc_shopCode, tc_shopDescription, tc_shopNumber);
//            modle.Allert.notificationGood("Save", "shop Saved");
//        }
//
//    }
//
//    private void clearForm() {
//        NodeUtils.cleanNodsIn(form);
//    }
//
//    Shop._SrShop selectedShop;
//
//    @FXML
//    private void onSelectShop(MouseEvent event) {
//        selectedShop = table_shop.getSelectionModel().getSelectedItem();
//        loadShopDataToForm(selectedShop);
//        btn_save.setText("Update");
//    }
//
//    private void loadShopDataToForm(Shop._SrShop selectedShop) {
////        clearForm();
//
//        com_building.getSelectionModel().select(0);
//        com_shopType.getSelectionModel().select(0);
//
//        com_building.getSelectionModel().select(new Shop._SrBuilding(selectedShop.getSrShop().getSrBuilding()));
//        com_shopType.getSelectionModel().select(new Shop._SrShopType(selectedShop.getSrShop().getSrShopType()));
//
//        txt_shop_number.setText(String.valueOf(selectedShop.getSrShopNumber()));
//        txt_shop_code.setText(selectedShop.getSrShopNo());
//        ta_description.setText(selectedShop.getSrShopDis());
//
//
//        com_flow.getSelectionModel().select(0);
//        com_flow.getSelectionModel().select(new Shop._SrFlow(selectedShop.getSrShop().getSrFlow()));
//
//
//
//    }
//
//
//
//
//    @FXML
//    private void onResetClick(ActionEvent event) {
//        selectedShop = null;
//        clearForm();
//        btn_save.setText("Save");
//    }
//}