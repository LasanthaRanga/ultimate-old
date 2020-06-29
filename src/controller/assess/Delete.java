package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import conn.NewHibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import modle.StaticViews;
import modle.asses.HolderAssess;
import modle.popup.BarcodeStatic;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Assessment;
import pojo.User;
import sun.plugin2.util.PojoUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Delete implements Initializable {

    @FXML
    private JFXTextField idAssessment;

    @FXML
    private Text txt_ass;

    @FXML
    private Text txt_customer;

    @FXML
    private JFXTextArea txt_comment;

    @FXML
    private JFXButton btn_delete;

    @FXML
    private TableView<HolderAssess> tbl_assess;
    @FXML
    private TableColumn<HolderAssess, Double> col_order;
    @FXML
    private TableColumn<HolderAssess, String> col_nature;
    @FXML
    private TableColumn<HolderAssess, String> col_ward;
    @FXML
    private TableColumn<HolderAssess, String> col_street;
    @FXML
    private TableColumn<HolderAssess, String> col_assess;
    @FXML
    private TableColumn<HolderAssess, String> col_obsalut;
    @FXML
    private TableColumn<HolderAssess, String> col_allocation;
    @FXML
    private TableColumn<HolderAssess, String> col_owner;
    @FXML
    private TableColumn<HolderAssess, String> col_status;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modle.StaticViews.getMc().changeTitle("Delete Property");
        btn_delete.setDisable(true);
        tableCollomAssing();
        loadTable();
    }

    public void loadTable() {
        String qq = "SELECT\n" +
                "\tassessment.idAssessment,\n" +
                "\tassessment.Customer_idCustomer,\n" +
                "\tassessment.Ward_idWard,\n" +
                "\tassessment.Street_idStreet,\n" +
                "\tassessment.ass_nature_idass_nature,\n" +
                "\tassessment.ass_discription_idass_discription,\n" +
                "\tassessment.User_idUser,\n" +
                "\tassessment.assessment_oder,\n" +
                "\tassessment.assessment_no,\n" +
                "\tassessment.assessment_status,\n" +
                "\tassessment.assessment_syn,\n" +
                "\tassessment.assessment_comment,\n" +
                "\tassessment.assessment_obsolete,\n" +
                "\tcustomer.cus_name,\n" +
                "\tcustomer.cus_nic,\n" +
                "\tcustomer.cus_mobile,\n" +
                "\tcustomer.cus_address_l1,\n" +
                "\tcustomer.cus_tel,\n" +
                "\tcustomer.cus_address_l2,\n" +
                "\tcustomer.cus_address_l3,\n" +
                "\tcustomer.cus_sity,\n" +
                "\tcustomer.cus_status,\n" +
                "\tcustomer.idCustomer,\n" +
                "\tward.ward_name,\n" +
                "\tward.idWard,\n" +
                "\tstreet.idStreet,\n" +
                "\tstreet.street_name,\n" +
                "\tass_nature.idass_nature,\n" +
                "\tass_allocation.ass_allocation,\n" +
                "\tass_nature.ass_nature_name\n" +
                "FROM\n" +
                "\tassessment\n" +
                "INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n" +
                "INNER JOIN ward ON assessment.Ward_idWard = ward.idWard\n" +
                "INNER JOIN street ON street.Ward_idWard = ward.idWard\n" +
                "AND assessment.Street_idStreet = street.idStreet\n" +
                "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                "INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                "WHERE\n" +
                "\tass_allocation.ass_allocation_status = 1\n" +
                "AND assessment_syn <> 0";

        try {
            executeQuary(qq);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }

    public ObservableList<HolderAssess> List = FXCollections.observableArrayList();

    public void executeQuary(String qq) {
        try {
            ResultSet rs = conn.DB.getData(qq);
            System.out.println(rs.toString());

            List.clear();
            while (rs.next()) {
//                System.out.println("whil ethule");
                int assessment_syn = rs.getInt("assessment_syn");
                String status = "";
                if (assessment_syn == 0) {
                    status = "Active";
                } else {
                    status = "Deleted";
                }
                HolderAssess holderAssess = new HolderAssess(rs.getInt("idAssessment"), rs.getDouble("assessment_oder"), rs.getString("ass_nature.ass_nature_name"), rs.getString("ward_name"), rs.getString("street_name"), rs.getString("assessment_no"), rs.getString("assessment_obsolete"), rs.getDouble("ass_allocation.ass_allocation"), rs.getString("cus_name"), status);
                List.add(holderAssess);
            }
            tbl_assess.setItems(List);
            System.out.println("add to list");
        } catch (Exception ex) {
            Logger.getLogger(Search_assessController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }


    public void tableCollomAssing() {
        col_order.setCellValueFactory(new PropertyValueFactory<>("order"));
        col_nature.setCellValueFactory(new PropertyValueFactory<>("Natrue"));
        col_ward.setCellValueFactory(new PropertyValueFactory<>("ward"));
        col_street.setCellValueFactory(new PropertyValueFactory<>("street"));
        col_assess.setCellValueFactory(new PropertyValueFactory<>("assessmentNo"));
        col_obsalut.setCellValueFactory(new PropertyValueFactory<>("osaleteNo"));
        col_allocation.setCellValueFactory(new PropertyValueFactory<>("alocation"));
        col_owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }


    @FXML
    void clickOnDelete(MouseEvent event) {


        String text = txt_comment.getText();
        if (text.length() > 3) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Delete Assessment");

            alert.setHeaderText(txt_ass.getText());

            Date date = new Date();


            alert.setContentText(idAssessment.getText() + "  Are you sure to delete this");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                Session session = NewHibernateUtil.getSessionFactory().openSession();
                Transaction transaction = session.beginTransaction();
                try {

                    User logUser = StaticViews.getLogUser();
                    logUser = (pojo.User) session.load(User.class, logUser.getIdUser());


                    int i = Integer.parseInt(idAssessment.getText());
                    Assessment ass = (pojo.Assessment) session.load(pojo.Assessment.class, i);

                    ass.setAssessmentSyn(2);
                    ass.setAssessmentComment(txt_comment.getText() + " \n  - " + logUser.getUserFullName() + " \n  - " + date);

                    session.update(ass);

                    transaction.commit();
                    modle.Allert.notificationGood("Deleted", txt_ass.getText());
                    clear();
                    loadTable();
                } catch (Exception e) {
                    e.printStackTrace();
                    transaction.rollback();
                } finally {
                    session.close();
                }


            } else {

            }

        } else {
            modle.Allert.notificationInfo("Please Add Comment", "Comment Empty");
        }


    }

    @FXML
    void idKeyReleas(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String text = idAssessment.getText();
            loadAssessmet();
        }
    }


    public void loadAssessmet() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            int i = Integer.parseInt(idAssessment.getText());
            Assessment ass = (pojo.Assessment) session.load(pojo.Assessment.class, i);
            txt_ass.setText(ass.getWard().getWardName() + " - " + ass.getStreet().getStreetName() + " - " + ass.getAssessmentNo());
            txt_customer.setText(ass.getCustomer().getCusName());
            btn_delete.setDisable(false);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }


    }

    public void clear() {
        txt_comment.setText("");
        txt_customer.setText("");
        txt_ass.setText("");
        idAssessment.setText("");
        btn_delete.setDisable(true);


    }


}
