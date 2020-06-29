package controller.assess;

import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 * FXML Controller class
 *
 * @author Ranga Rathnayake
 */
public class ChangeOderController implements Initializable {

    @FXML
    private TableView<String> fxlist;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

//        observableArrayList.add(new Oder(0.1, 1.1, "asdf", "dfgsdfg"));
//        observableArrayList.add(new Oder(0.2, 2.2, "asdf", "dfgsdfg"));
//        observableArrayList.add(new Oder(0.3, 3.3, "asdf", "dfgsdfg"));
//        observableArrayList.add(new Oder(0.4, 4.4, "asdf", "dfgsdfg"));
//        observableArrayList.add(new Oder(0.5, 5.5, "asdf", "dfgsdfg"));
        observableArrayList.add("asdfasdfasdfasf");
        observableArrayList.add("asdfasdfasdfasf");
        observableArrayList.add("asdfasdfasdfasf");
        observableArrayList.add("asdfasdfasdfasf");
        observableArrayList.add("asdfasdfasdfasf");
        fxlist.setItems(observableArrayList);
    }
    ObservableList<String> observableArrayList = FXCollections.observableArrayList();

    @FXML
    private void onDragDetected(MouseEvent event) {
        String name = event.getEventType().getName();
        System.out.println(name);
        String selectedItem = fxlist.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {

            Dragboard db = fxlist.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent clipBoard = new ClipboardContent();
            db.setContent(clipBoard);
            event.consume();
        }

    }

    @FXML
    private void onDragOver(DragEvent event) {

        Dragboard db = event.getDragboard();
        if (event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();

    }

    @FXML
    private void onDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (event.getDragboard().hasString()) {
            observableArrayList.add("j;klmkl");
            fxlist.setItems(observableArrayList);
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    class Oder {

        /**
         * @return the oder
         */
        public Double getOder() {
            return oder;
        }

        /**
         * @param oder the oder to set
         */
        public void setOder(Double oder) {
            this.oder = oder;
        }

        /**
         * @return the newOder
         */
        public Double getNewOder() {
            return newOder;
        }

        /**
         * @param newOder the newOder to set
         */
        public void setNewOder(Double newOder) {
            this.newOder = newOder;
        }

        /**
         * @return the assno
         */
        public String getAssno() {
            return assno.get();
        }

        /**
         * @param assno the assno to set
         */
        public void setAssno(SimpleStringProperty assno) {
            this.assno = assno;
        }

        /**
         * @return the cusName
         */
        public String getCusName() {
            return cusName.get();
        }

        /**
         * @param cusName the cusName to set
         */
        public void setCusName(SimpleStringProperty cusName) {
            this.cusName = cusName;
        }

        public Oder(Double oder, Double newOder, String assno, String cusName) {
            this.oder = oder;
            this.newOder = newOder;
            this.assno = new SimpleStringProperty(assno);
            this.cusName = new SimpleStringProperty(cusName);
        }

        private Double oder;
        private Double newOder;
        private SimpleStringProperty assno;
        private SimpleStringProperty cusName;

    }

}
