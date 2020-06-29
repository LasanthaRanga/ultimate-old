///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package controller.sr;
//
//import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXTextField;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.Pane;
//import modle.GetInstans;
//import modle.sr.NodeUtils;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
///**
// * FXML Controller class
// *
// * @author Sampath
// */
//public class BuildingController implements Initializable {
//
//    @FXML
//    private JFXTextField txt_building_name;
//    @FXML
//    private JFXTextField txt_building_code;
//    @FXML
//    private TableView<modle.sr.Building._srBuilding> tbl_Building;
//    @FXML
//    private TableColumn<?, ?> col_b_name;
//    @FXML
//    private TableColumn<?, ?> col_b_code;
//
//    @FXML
//    private TableColumn<?, ?> col_f_name;
//    @FXML
//    private TableColumn<?, ?> col_f_code;
//    @FXML
//    private JFXTextField txt_flow_name;
//    @FXML
//    private JFXTextField txt_flow_number;
//    @FXML
//    private JFXTextField txt_flow_code;
//
//    private modle.sr.Building._srBuilding selectedBuilding;
//    @FXML
//    private Pane pane_flows;
//    @FXML
//    private Label lbl_selected_building;
//    @FXML
//    private TableView<modle.sr.Building._srBuildingFlows> tbl_Building_flow;
//    @FXML
//    private TableColumn<?, ?> col_f_no;
//    private modle.sr.Building._srBuildingFlows selectedBuildingFlow;
//    @FXML
//    private Pane pane_building;
//    @FXML
//    private Pane pane_flow;
//    @FXML
//    private JFXButton btn_buildingSave;
//    @FXML
//    private JFXButton btn_flowSave;
//
//    /**
//     * Initializes the controller class.
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // TODO
//
//        GetInstans.getBuilding().buildingTableLoad(tbl_Building, col_b_name, col_b_code);
//
//    }
//
//    @FXML
//    private void selectFromTable(MouseEvent event) {
//        final modle.sr.Building._srBuilding selectedBuilding = tbl_Building.getSelectionModel().getSelectedItem();
//        setSelectedBuilding(selectedBuilding);
//        btn_buildingSave.setText("Update");
//    }
//
//    @FXML
//    private void onCickSave(MouseEvent event) {
//
//        if (isTextFieldsEmpty(txt_building_name, txt_building_code)) {
//            return;
//        }
//
//        if (GetInstans.getBuilding().save(selectedBuilding , txt_building_name.getText(), txt_building_code.getText())) {
//            GetInstans.getBuilding().buildingTableLoad(tbl_Building, col_b_name, col_b_code);
//            resetBuildingForm();
//        }
//    }
//
//    private void setSelectedBuilding(modle.sr.Building._srBuilding selectedBuilding) {
//        this.selectedBuilding = selectedBuilding;
//        txt_building_name.setText(selectedBuilding.getName());
//        txt_building_code.setText(selectedBuilding.getCode());
//        lbl_selected_building.setText(selectedBuilding.getName());
//        pane_flows.setDisable(false);
//        GetInstans.getBuilding().flowTableLoad(selectedBuilding, tbl_Building_flow, col_f_name, col_f_code, col_f_no);
//    }
//
//    @FXML
//    private void saveFlow(ActionEvent event) {
//        if (isTextFieldsEmpty(txt_flow_name, txt_flow_code, txt_flow_number)) {
//            return;
//        }
//        int fNo;
//        try {
//            fNo = Integer.parseInt(txt_flow_number.getText());
//        } catch (Exception e) {
//            modle.Allert.notificationError("ERROR", "Enter valid flow number ");
//            return;
//        }
//        if (GetInstans.getBuilding().saveFlow(selectedBuildingFlow, selectedBuilding.getBuilding(), txt_flow_name.getText(), txt_flow_code.getText(), fNo)) {
//            GetInstans.getBuilding().flowTableLoad(selectedBuilding, tbl_Building_flow, col_f_name, col_f_code, col_f_no);
//            resetFlowForm();
//        }
//    }
//
//    private boolean isTextFieldsEmpty(TextField... fields) {
//        boolean e = false;
//        for (TextField textField : fields) {
//            if (textField.getText() == null || textField.getText().trim().isEmpty()) {
//                modle.Allert.notificationError("ERROR", "Enter " + textField.getPromptText());
//                e = true;
//                break;
//            }
//        }
//        return e;
//    }
//
//
//    @FXML
//    private void selectFromFlowTable(MouseEvent event) {
//        setSelectedBuildingFlow(tbl_Building_flow.getSelectionModel().getSelectedItem());
//        btn_flowSave.setText("Update");
//    }
//
//    private void setSelectedBuildingFlow(modle.sr.Building._srBuildingFlows selectedBuildingFlow) {
//        this.selectedBuildingFlow = selectedBuildingFlow;
//        txt_flow_name.setText(selectedBuildingFlow.getSrFlowName());
////        txt_flow_code.setText(selectedBuildingFlow.getSrFlowCode());
//        txt_flow_number.setText(String.valueOf(selectedBuildingFlow.getSrFlowNumber()));
//
//    }
//
//    @FXML
//    private void resetBuildingForm() {
//        selectedBuilding = null;
//        NodeUtils.cleanNodsIn(pane_building);
//        pane_flows.setDisable(true);
//        tbl_Building_flow.getItems().clear();
//        resetFlowForm();
//        lbl_selected_building.setText("");
//        btn_buildingSave.setText("Save");
//    }
//
//    @FXML
//    private void resetFlowForm() {
//
//        selectedBuildingFlow = null;
//        NodeUtils.cleanNodsIn(pane_flow);
//        btn_flowSave.setText("Save");
//    }
//
//}
