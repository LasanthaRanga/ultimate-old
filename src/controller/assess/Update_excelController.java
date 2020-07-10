package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.ResultSet;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modle.ComboItem;
import modle.ComboLoad;
import modle.GetInstans;
import modle.StaticBadu;
import modle.asses.OldDataSave;
import modle.asses.Quater;
import modle.asses.TableAsses;
import org.controlsfx.control.textfield.TextFields;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.*;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class Update_excelController implements Initializable {

    @FXML
    private JFXTextField txt_assessment;
    @FXML
    private JFXComboBox<String> com_ward;
    @FXML
    private JFXComboBox<String> com_street;
    @FXML
    private JFXTextField txt_obserloot;
    @FXML
    private JFXTextField txt_book_no;
    @FXML
    private JFXTextField txt_customer;
    @FXML
    private JFXTextField txt_nic;
    @FXML
    private JFXTextField txt_adl1;
    @FXML
    private JFXTextField txt_adl2;
    @FXML
    private JFXTextField txt_adl3;
    @FXML
    private TableView<TableAsses> tbl;
    @FXML
    private TableColumn<TableAsses, String> col_bookNo;
    @FXML
    private TableColumn<TableAsses, String> col_assessmant;
    @FXML
    private TableColumn<TableAsses, String> col_customer;
    @FXML
    private TableColumn<TableAsses, String> col_alocation;
    @FXML
    private TableColumn<TableAsses, String> col_status;
    @FXML
    private JFXComboBox<String> com_nature;
    @FXML
    private JFXTextField txt_allocation;
    @FXML
    private JFXTextField txt_discription;

    @FXML
    private JFXButton btn_save;
    @FXML
    private JFXButton btn_subOwner;

    @FXML
    private JFXTextField txt_mobile;
    @FXML
    private JFXTextField txt_others;
    @FXML
    private JFXButton btn_new;

    @FXML
    private TableView<Subowner> tbl_sub;

    @FXML
    private TableColumn<Subowner, String> col_owner;

    @FXML
    private TableColumn<Subowner, String> col_nic;

    @FXML
    private TableColumn<Subowner, String> col_print;

    @FXML
    private JFXComboBox<ComboItem> com_title;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        int ass_app_id = StaticBadu.getAss_app_id();
        if (ass_app_id > 0) {
            String obsalutNumbers = GetInstans.getApplicationsModle().getObsalutNumbers(ass_app_id);
            txt_obserloot.setText(obsalutNumbers);
        }


        btn_subOwner.setDisable(true);
        loadWardCombo();
        loadNatureCombo();
        loadTabel();
        ArrayList list = modle.GetInstans.getDiscription().getCustomerFnameList();
        TextFields.bindAutoCompletion(txt_discription, list);

        if (a != null) {
            btn_save.setText("UPDATE");
            btn_subOwner.setDisable(false);
        } else {
            btn_save.setText("SAVE");
            btn_subOwner.setDisable(true);
        }
        modle.StaticViews.getMc().changeTitle("New Assessment");

        col_owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
        col_nic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        col_print.setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<ComboItem> comboItems = ComboLoad.loadCombo("SELECT person_title.title_id, person_title.title_name FROM person_title");

        com_title.setItems(comboItems);


    }

    public String book_no;
    public String ward;
    public String street;
    public String nature;
    public String assessmant;
    public String obsaloot;
    public String allocation;
    public String customer;
    public String discription;
    public String adl1;
    public String adl2;
    public String adl3;
    public String nic;
    public String phone;
    public String mobile;
    public String others;
    public int status;
    public double alo;
    public double book;
    public AssDiscription disPojo;
    public Assessment a;
    public String persanalTitle = null;

    private void collectData() {
        book_no = txt_book_no.getText();
        try {
            book = Double.parseDouble(book_no);
            //===
            ward = com_ward.getSelectionModel().getSelectedItem();
            street = com_street.getSelectionModel().getSelectedItem();
            nature = com_nature.getSelectionModel().getSelectedItem();
            if (com_title.getSelectionModel().getSelectedItem() != null) {
                persanalTitle = com_title.getSelectionModel().getSelectedItem().getId() + "";
            }

            //===
            String[] split = txt_assessment.getText().split(" ");
            if (split.length > 1) {
                modle.Allert.notificationError("Error", "Ther is a SPACE");
            } else {
                assessmant = txt_assessment.getText();

                boolean exsist = modle.GetInstans.getAssessmantModle().isExsist(ward, street, assessmant);

                if (exsist && a == null) {
                    modle.Allert.notificationError("Error", "This Assessment Is Duplicate");
                } else {
                    //===
                    String[] split1 = txt_obserloot.getText().split(" ");
                    if (split1.length > 1) {
                        modle.Allert.notificationError("Error", "Ther is a SPACE");
                    } else {
                        obsaloot = txt_obserloot.getText();
                        //===
                        allocation = txt_allocation.getText();
                        try {
                            alo = Double.parseDouble(allocation);
                            //==
                            customer = txt_customer.getText();
                            discription = txt_discription.getText();
                            // disPojo.setAssDiscription(discription);
                            adl1 = txt_adl1.getText();
                            adl2 = txt_adl2.getText();
                            adl3 = txt_adl3.getText();
                            nic = txt_nic.getText();
                            mobile = txt_mobile.getText();
                            others = txt_others.getText();

                            status = 1;

                            if (a == null) {
                                if (saveall()) {


                                    int ass_app_id = StaticBadu.getAss_app_id();
                                    if (ass_app_id > 0) {
                                        if (modle.StaticBadu.getAss_app_type() == 2) {
                                            cleareAll();
                                        }
                                        if (modle.StaticBadu.getAss_app_type() == 2) {
                                            cleareAll();
                                            String obsalutNumbers = GetInstans.getApplicationsModle().getObsalutNumbers(ass_app_id);
                                            txt_obserloot.setText(obsalutNumbers);
                                        }

                                    } else {
                                        cleareAll();
                                    }


                                }
                            } else {
                                if (update()) {

                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Update");
                                    alert.setHeaderText("You are going to update old Data \n Are you sure to update this? \n Click Ok");
                                    alert.setContentText("If you want to save this. \n Click Cancel and Click again SAVE button");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        // ... user chose OK
                                        modle.Allert.notificationGood("Update", assessmant);
                                        cleareAll();
                                    } else {
                                        // ... user chose CANCEL or closed the dialog    
                                        a = null;
                                        modle.asses.StaticBadu.setAssessment(null);
                                        btn_save.setText("Save");
                                    }

                                }
                            }

                        } catch (Exception e) {
                            modle.Allert.notificationError("Error", "please Cheack Allocation");
                        }

                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            modle.Allert.notificationError("Error", "please Cheack Book No");
        }

    }

    public boolean saveall() {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {

            Ward w = (Ward) session.createCriteria(Ward.class).add(Restrictions.eq("wardName", ward)).uniqueResult();
            AssNature n = (AssNature) session.createCriteria(AssNature.class).add(Restrictions.eq("assNatureName", nature)).uniqueResult();
            Street s = (Street) session.createCriteria(Street.class).add(Restrictions.and(Restrictions.eq("ward", w), Restrictions.eq("streetName", street))).uniqueResult();
            User u = (User) session.load(User.class, modle.StaticViews.getLogUser().getIdUser());
            //Discription
            List<AssDiscription> list = session.createCriteria(AssDiscription.class).add(Restrictions.eq("assDiscription", discription)).list();
            AssDiscription d = null;
            if (list.size() > 0) {
                d = list.get(0);
            }
            if (d == null) {
                d = new AssDiscription();
                d.setAssDiscription(discription);
                session.save(d);
            }

            if (w != null && n != null && s != null && u != null && d != null) {

                //customer
                if (customer.length() > 1 && assessmant.length() > 0) {
                    pojo.Customer cus = new pojo.Customer();
                    cus.setCusName(customer);
                    cus.setCusNic(nic);
                    cus.setCusAddressL1(adl1);
                    cus.setCusAddressL2(adl2);
                    cus.setCusAddressL3(adl3);
                    cus.setCusMobile(mobile);
                    cus.setCusPersonTitle(persanalTitle);
                    System.out.println(cus);
                    session.save(cus);

                    //assessment
                    Assessment asess = new Assessment();
                    asess.setAssDiscription(d);
                    asess.setAssNature(n);
                    asess.setCustomer(cus);
                    asess.setWard(w);
                    asess.setStreet(s);
                    asess.setUser(u);
                    asess.setAssessmentOder(book);
                    asess.setAssessmentNo(assessmant);
                    asess.setAssessmentStatus(status);
                    asess.setAssessmentObsolete(obsaloot);
                    asess.setAssessmentSyn(0);
                    asess.setAssessmentComment(others);
                    Serializable save = session.save(asess);

                    //allocation
                    modle.asses.StaticBadu.setAssessment(asess);
                    AssAllocation allocation = new AssAllocation(asess);
                    allocation.setAssAllocation(alo);
                    allocation.setAssAllocationStatus(1);
                    session.save(allocation);

                    double qval = allocation.getAssAllocation() * n.getAssNatureYearRate() / 400;


                    Quater quater = GetInstans.getQuater();
                    Date selectedSystemDate = quater.getSystemDate();
                    int currentYear = quater.getCurrentYear();
                    int currentQuater = quater.getCurrentQuater();

                    AssQstart qs = new AssQstart();
                    qs.setAssessment(asess);
                    qs.setAssQstartQuaterNumber(currentQuater);
                    qs.setAssQstartProcessDate(selectedSystemDate);
                    qs.setAssQstartLyArreas(0.0);
                    qs.setAssQstartLycArreas(0.0);
                    qs.setAssQstartLqcArreas(0.0);
                    qs.setAssQstartLqArreas(0.0);
                    qs.setAssQstartLyWarrant(0.0);
                    qs.setAssQstartLycWarrant(0.0);
                    qs.setAssQstartLqWarrant(0.0);
                    qs.setAssQstartLqcWarrant(0.0);
                    qs.setAssQstartHaveToQpay(qval);
                    qs.setAssQstartQpay(0.0);
                    qs.setAssQstartQdiscont(0.0);
                    qs.setAssQstartQtot(0.0);
                    qs.setAssQstartFullTotal(0.0);
                    qs.setAssQstartStatus(1);
                    qs.setAssQstartYear(currentYear);
                    qs.setProcessUpdateArrears(0.0);
                    qs.setProcessUpdateWarant(0.0);
                    qs.setAssQstartTyoldArrias(0.0);
                    qs.setAssQstartTyoldWarant(0.0);
                    session.save(qs);

                    AssPayhistry ph = new AssPayhistry();
                    ph.setAssessment(asess);
                    ph.setAssPayHistryQcunt(currentQuater);
                    ph.setAssPayHistryYear(currentYear);
                    ph.setAssPayHistryDate(selectedSystemDate);
                    ph.setAssPayHistryStatus(1);
                    ph.setAssPayHistryComment("new");
                    ph.setAssPayHistryTotalPayid(0.0);
                    ph.setAssPayHistryQ1(0.0);
                    ph.setAssPayHistryQ2(0.0);
                    ph.setAssPayHistryQ3(0.0);
                    ph.setAssPayHistryQ4(0.0);
                    ph.setAssPayHistryOver(0.0);
                    ph.setAssPayHistryQ1status(0);
                    ph.setAssPayHistryQ2status(0);
                    ph.setAssPayHistryQ3status(0);
                    ph.setAssPayHistryQ4status(0);
                    ph.setAssPayHistryDrq1(0.0);
                    ph.setAssPayHistryDrq2(0.0);
                    ph.setAssPayHistryDrq3(0.0);
                    ph.setAssPayHistryDrq4(0.0);
                    session.save(ph);


                    transaction.commit();

                    if (modle.StaticBadu.getAss_app_type() == 2) {
                        modle.GetInstans.getApplicationsModle().amalgamation(modle.StaticBadu.getAss_app_id(), Integer.parseInt(save.toString())); //Amalgamation
                        modle.StaticBadu.getApplications().loadAssesTable2();
                    }

                    if (modle.StaticBadu.getAss_app_type() == 1) {
                        modle.GetInstans.getApplicationsModle().subDivition(modle.StaticBadu.getAss_app_id(), Integer.parseInt(save.toString())); //Amalgamation
                        modle.StaticBadu.getApplications().loadAssesTable2();
                    }

                    modle.Allert.notificationGood("Save", assessmant);
                    return true;
                } else {
                    modle.Allert.notificationError("Check Data", "Customer Name, Assessment No");
                    return false;
                }
            } else {
                modle.Allert.notificationError("Select Combo box", "ward, street, nature");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }

    }

    public boolean update() {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {

            Ward w = (Ward) session.createCriteria(Ward.class).add(Restrictions.eq("wardName", ward)).uniqueResult();
            AssNature n = (AssNature) session.createCriteria(AssNature.class).add(Restrictions.eq("assNatureName", nature)).uniqueResult();
            Street s = (Street) session.createCriteria(Street.class).add(Restrictions.and(Restrictions.eq("ward", w), Restrictions.eq("streetName", street))).uniqueResult();
            User u = (User) session.load(User.class, modle.StaticViews.getLogUser().getIdUser());

            //Discription
            List<AssDiscription> list = session.createCriteria(AssDiscription.class).add(Restrictions.eq("assDiscription", discription)).list();
            AssDiscription d = null;
            if (list.size() > 0) {
                d = list.get(0);
            }
            if (d == null) {
                d = new AssDiscription();
                d.setAssDiscription(discription);
                session.save(d);
            }

            if (customer.length() > 1 && assessmant.length() > 0) {

                Integer idCustomer = a.getCustomer().getIdCustomer();
                System.out.println(idCustomer);
                pojo.Customer cus = (pojo.Customer) session.load(pojo.Customer.class, a.getCustomer().getIdCustomer());
                System.out.println(cus);
                cus.setCusName(customer);
                cus.setCusNic(nic);
                cus.setCusAddressL1(adl1);
                cus.setCusAddressL2(adl2);
                cus.setCusAddressL3(adl3);
                cus.setCusMobile(mobile);
                cus.setCusStatus(status);
                cus.setCusPersonTitle(persanalTitle);
                session.save(cus);

                Assessment asess = (Assessment) session.load(Assessment.class, a.getIdAssessment());

                asess.setAssDiscription(d);
                asess.setAssNature(n);
                asess.setStreet(s);
                asess.setUser(u);
                asess.setWard(w);

                asess.setAssessmentOder(book);
                asess.setAssessmentNo(assessmant);

                asess.setAssessmentStatus(status);
                asess.setAssessmentObsolete(obsaloot);
                asess.setAssessmentSyn(0);
                asess.setAssessmentComment(others);
                session.update(asess);

                modle.asses.StaticBadu.setAssessment(asess);

                Set<AssAllocation> assAllocations = a.getAssAllocations();
                for (AssAllocation assAllocation : assAllocations) {
                    if (assAllocation.getAssAllocationStatus() == 1) {
                        assAllocation.setAssAllocationStatus(0);
                        session.update(assAllocation);
                    }
                }

                AssAllocation allocation = new AssAllocation(asess);
                allocation.setAssAllocation(alo);
                allocation.setAssAllocationStatus(1);
                allocation.setAssAllocationIdUser(u.getIdUser());
                session.save(allocation);

                transaction.commit();
                return true;
            } else {
                modle.Allert.notificationError("Check Data", "Customer Name, Assessment No");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @FXML
    private void keyReleased(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            String[] split = txt_assessment.getText().split(" ");
            if (split.length > 1) {
                modle.Allert.notificationError("Error", "Ther is a SPACE");
                System.out.println(split.length);
                // java.awt.Toolkit.getDefaultToolkit().beep();
            } else {
                assessmant = txt_assessment.getText();
                ward = com_ward.getSelectionModel().getSelectedItem();
                street = com_street.getSelectionModel().getSelectedItem();
                boolean exsist = modle.GetInstans.getAssessmantModle().isExsist(ward, street, assessmant);
                if (exsist) {
                    modle.Allert.notificationError("Error", "This Assessment Is Duplicate");
                    java.awt.Toolkit.getDefaultToolkit().beep();
                } else {
                    txt_obserloot.requestFocus();
                }
            }
        }
        loadTabelByWardStreetAsess(txt_assessment.getText());

        if (event.getCode() == KeyCode.SPACE) {
            txt_assessment.deletePreviousChar();
            java.awt.Toolkit.getDefaultToolkit().beep();
        }

    }

    @FXML
    private void wardOnAction(ActionEvent event) {
        loadStreetCombo();
        ward = com_ward.getSelectionModel().getSelectedItem();
    }

    public void loadStreetCombo() {
        String selectedWard = com_ward.getSelectionModel().getSelectedItem();
        loadStrretCombo(selectedWard);
    }

    @FXML
    private void streetOnAction(ActionEvent event) {
        loadTabelByWardStreet();
        street = com_street.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void obsalootKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String[] split = txt_obserloot.getText().split(" ");
            if (split.length > 1) {
                modle.Allert.notificationError("Error", "Ther is a SPACE");
                System.out.println(split.length);
            } else {
                obsaloot = txt_obserloot.getText();
                txt_allocation.requestFocus();
            }
        }
        if (event.getCode() == KeyCode.SPACE) {
            txt_obserloot.deletePreviousChar();
            java.awt.Toolkit.getDefaultToolkit().beep();

        }
    }

    @FXML
    private void bookKeyReleased(KeyEvent event) {
        if (txt_book_no.getText().matches("\\d*(\\.\\d*)?")) {
            try {
                book = Double.parseDouble(txt_book_no.getText());
            } catch (Exception e) {
                modle.Allert.notificationError("Error", "please Cheack Book No");
            }
        } else {
            java.awt.Toolkit.getDefaultToolkit().beep();
            txt_book_no.deletePreviousChar();
        }
        if (event.getCode() == KeyCode.ENTER) {
            txt_assessment.requestFocus();
        }
    }

    @FXML
    private void cusNameKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txt_discription.requestFocus();
        }
    }

    @FXML
    private void nicKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txt_adl1.requestFocus();
        }
    }

    @FXML
    private void adl1KeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txt_adl2.requestFocus();
        }
    }

    @FXML
    private void adl2KeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txt_adl3.requestFocus();
        }
    }

    @FXML
    private void adl3KeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

        }
    }

    int idAsses = 0;


    ObservableList<Subowner> subowner = FXCollections.observableArrayList();

    @FXML
    private void tableSelect(MouseEvent event) {


        if (tbl.getSelectionModel().getSelectedItem() != null) {
            idAsses = tbl.getSelectionModel().getSelectedItem().getIdAsses();
        }


        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            a = (Assessment) session.load(Assessment.class, idAsses);
            btn_save.setText("Update");
            btn_subOwner.setDisable(false);
            modle.asses.StaticBadu.setAssessment(a);
            txt_book_no.setText(a.getAssessmentOder() + "");
            txt_assessment.setText(a.getAssessmentNo());
            txt_obserloot.setText(a.getAssessmentObsolete());
            txt_customer.setText(a.getCustomer().getCusName());
            com_nature.getSelectionModel().select(a.getAssNature().getAssNatureName());
            com_ward.getSelectionModel().select(a.getStreet().getWard().getWardName());
            com_street.getSelectionModel().select(a.getStreet().getStreetName());

            Set<AssAllocation> assAllocations = a.getAssAllocations();
            for (AssAllocation assAllocation : assAllocations) {
                if (assAllocation.getAssAllocationStatus() == 1) {
                    String allocation = String.format("%.2f", assAllocation.getAssAllocation());
                    txt_allocation.setText(allocation);
                }
            }

            txt_discription.setText(a.getAssDiscription().getAssDiscription());
            txt_nic.setText(a.getCustomer().getCusNic());
            txt_adl1.setText(a.getCustomer().getCusAddressL1());
            txt_adl2.setText(a.getCustomer().getCusAddressL2());
            txt_adl3.setText(a.getCustomer().getCusAddressL3());
            txt_mobile.setText(a.getCustomer().getCusMobile());
            txt_others.setText(a.getAssessmentComment());

            subowner.clear();
            Set<AssSubowner> assSubowners = a.getAssSubowners();
            for (AssSubowner su : assSubowners) {
                String st = "";
                if (su.getAssSubOwnerStatus() == 1) {
                    st = "Print";
                } else {
                    st = "Not Print";
                }
                subowner.add(new Subowner(su.getIdassSubOwner(), su.getAssSubOwnerName(), su.getAssSubOwnerNic(), st));
            }
            tbl_sub.setItems(subowner);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @FXML
    private void natureOnAction(ActionEvent event) {
        nature = com_nature.getSelectionModel().getSelectedItem();

    }

    @FXML
    private void alocationKeyReleased(KeyEvent event) {
        if (txt_allocation.getText().matches("\\d*(\\.\\d*)?")) {
            try {
                alo = Double.parseDouble(txt_allocation.getText());
                if (event.getCode() == KeyCode.ENTER) {
                    txt_customer.requestFocus();
                }
            } catch (Exception e) {
                modle.Allert.notificationError("Error", "please Alocation Value");
            }
        } else {
            java.awt.Toolkit.getDefaultToolkit().beep();
            txt_allocation.deletePreviousChar();
        }
    }

    @FXML
    private void discriptionKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txt_nic.requestFocus();
        }
    }


    @FXML
    private void subOwner(ActionEvent event) {

        if (modle.asses.StaticBadu.getAssessment() != null) {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/view/assess/subOwners.fxml"));
                Stage stage = new Stage();
                stage.initStyle(StageStyle.TRANSPARENT);

                stage.getIcons().add(new Image("/grafics/info.png"));
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setResizable(false);
                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
                //   Logger.getLogger(AssessmangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void phoneKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txt_mobile.requestFocus();
        }
    }

    @FXML
    private void mobileKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txt_others.requestFocus();
        }
    }

    @FXML
    private void othersKeyReleased(KeyEvent event) {
    }

    @FXML
    private void newButtoneCleck(ActionEvent event) {
        cleareAll2();
    }

    public void loadWardCombo() {
        List<Ward> list = modle.GetInstans.getWardModle().getWardList();
        ObservableList List = FXCollections.observableArrayList();
        for (Ward ward : list) {
            List.add(ward.getWardName());
        }
        com_ward.setItems(List);
    }

    public void loadNatureCombo() {
        ObservableList<String> natureObservableListSQL = modle.GetInstans.getNature().getNatureObservableListSQL();
        com_nature.setItems(natureObservableListSQL);
    }

    public void loadStrretCombo(String ward) {
        ObservableList<String> streetObservableListSQLByWard = modle.GetInstans.getStreetModle().getStreetObservableListSQLByWard(ward);
        com_street.setItems(streetObservableListSQLByWard);
    }

    public void cleareAll2() {
        a = null;

        loadWardCombo();
        loadStreetCombo();
        loadNatureCombo();
        ArrayList list = modle.GetInstans.getDiscription().getCustomerFnameList();
        TextFields.bindAutoCompletion(txt_discription, list);
        modle.asses.StaticBadu.setAssessment(null);
        txt_book_no.setText("");
        txt_assessment.setText("");
        txt_obserloot.setText("");
        txt_customer.setText("");
        txt_allocation.setText("");
        txt_discription.setText("");
        txt_nic.setText("");
        txt_adl1.setText("");
        txt_adl2.setText("");
        txt_adl3.setText("");

        txt_mobile.setText("");
        txt_others.setText("");
//        radio_no.setSelected(true);
        btn_save.setText("Save");
        btn_subOwner.setDisable(true);
        loadTabel();

        int ass_app_id = StaticBadu.getAss_app_id();
        if (ass_app_id > 0) {
            String obsalutNumbers = GetInstans.getApplicationsModle().getObsalutNumbers(ass_app_id);
            txt_obserloot.setText(obsalutNumbers);
        }


    }

    public void cleareAll() {
        //a = null;
        //StaticBadu.setAssessment(null);

        loadWardCombo();
        loadStreetCombo();
        loadNatureCombo();
        //  loadTabel();

        txt_book_no.setText("");
        txt_assessment.setText("");
        txt_obserloot.setText("");
        txt_customer.setText("");
        txt_allocation.setText("");
        txt_discription.setText("");
        txt_nic.setText("");
        txt_adl1.setText("");
        txt_adl2.setText("");
        txt_adl3.setText("");

        txt_mobile.setText("");
        txt_others.setText("");
//        radio_no.setSelected(true);

        btn_subOwner.setDisable(false);
        btn_save.setText("Update");
    }

    ObservableList ola = FXCollections.observableArrayList();

    public void loadTabel() {

        col_bookNo.setCellValueFactory(new PropertyValueFactory<>("bookNo"));
        col_assessmant.setCellValueFactory(new PropertyValueFactory<>("assesNo"));
        col_customer.setCellValueFactory(new PropertyValueFactory<>("cus"));
        col_alocation.setCellValueFactory(new PropertyValueFactory<>("alocation"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        String qq = "SELECT\n" +
                "ward.ward_name,\n" +
                "street.street_name,\n" +
                "assessment.assessment_oder,\n" +
                "assessment.assessment_no,\n" +
                "assessment.assessment_status,\n" +
                "assessment.assessment_obsolete,\n" +
                "customer.cus_name,\n" +
                "assessment.idAssessment,\n" +
                "ass_allocation.ass_allocation,\n" +
                "assessment.assessment_syn\n" +
                "FROM\n" +
                "\tward\n" +
                "INNER JOIN street ON street.Ward_idWard = ward.idWard\n" +
                "INNER JOIN assessment ON assessment.Street_idStreet = street.idStreet\n" +
                "AND assessment.Ward_idWard = ward.idWard\n" +
                "INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n" +
                "LEFT JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                "WHERE\n" +
                "\tass_allocation.ass_allocation_status = 1\n" +
                "ORDER BY\n" +
                "\tassessment.idAssessment DESC";

        ResultSet data = null;
        try {
            data = conn.DB.getData(qq);
            ola.clear();
            while (data.next()) {
                String status;
                if (data.getInt("assessment_syn") == 0) {
                    status = "Active";
                } else {
                    status = "Deleted";
                }
                ola.add(new modle.asses.TableAsses(data.getInt("idAssessment"), data.getString("assessment_oder"), data.getString("assessment_no"), data.getString("cus_name"), data.getString("ass_allocation"), status));
                tbl.setItems(ola);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void loadTabelByWardStreet() {

        String qq = "SELECT\n"
                + "ward.ward_name,\n"
                + "street.street_name,\n"
                + "assessment.assessment_oder,\n"
                + "assessment.assessment_no,\n"
                + "assessment.assessment_status,\n"
                + "assessment.assessment_obsolete,\n"
                + "customer.cus_name,\n"
                + "assessment.idAssessment,\n"
                + "ass_allocation.ass_allocation,\n"
                + "assessment.assessment_syn \n"
                + "FROM\n"
                + "ward\n"
                + "INNER JOIN street ON street.Ward_idWard = ward.idWard\n"
                + "INNER JOIN assessment ON assessment.Street_idStreet = street.idStreet AND assessment.Ward_idWard = ward.idWard\n"
                + "INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n"
                + "LEFT JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n"
                + "WHERE\n"
                + "ass_allocation.ass_allocation_status = 1 AND\n"
                + "assessment.assessment_syn = 0 AND\n"
                + "ward.ward_name = '" + com_ward.getSelectionModel().getSelectedItem() + "' AND\n"
                + "street.street_name = '" + com_street.getSelectionModel().getSelectedItem() + "'\n"
                + "ORDER BY\n"
                + "assessment.idAssessment DESC";

        col_bookNo.setCellValueFactory(new PropertyValueFactory<>("bookNo"));
        col_assessmant.setCellValueFactory(new PropertyValueFactory<>("assesNo"));
        col_customer.setCellValueFactory(new PropertyValueFactory<>("cus"));
        col_alocation.setCellValueFactory(new PropertyValueFactory<>("alocation"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        ResultSet data = null;
        try {
            data = conn.DB.getData(qq);
            ola.clear();
            while (data.next()) {
                String status;
                if (data.getInt("assessment_syn") == 0) {
                    status = "Active";
                } else {
                    status = "Deleted";
                }
                ola.add(new modle.asses.TableAsses(data.getInt("idAssessment"), data.getString("assessment_oder"), data.getString("assessment_no"), data.getString("cus_name"), data.getString("ass_allocation"), status));
                tbl.setItems(ola);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadTabelByWardStreetAsess(String assessno) {

        String qq = "SELECT\n"
                + "ward.ward_name,\n"
                + "street.street_name,\n"
                + "assessment.assessment_oder,\n"
                + "assessment.assessment_no,\n"
                + "assessment.assessment_status,\n"
                + "assessment.assessment_obsolete,\n"
                + "customer.cus_name,\n"
                + "assessment.idAssessment,\n"
                + "ass_allocation.ass_allocation, \n"
                + "assessment.assessment_syn \n"
                + "FROM\n"
                + "ward\n"
                + "INNER JOIN street ON street.Ward_idWard = ward.idWard\n"
                + "INNER JOIN assessment ON assessment.Street_idStreet = street.idStreet AND assessment.Ward_idWard = ward.idWard\n"
                + "INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n"
                + "LEFT JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n"
                + "WHERE\n"
                + "ass_allocation.ass_allocation_status = 1 AND\n"
                + "assessment.assessment_syn = 0 AND\n"
                + "ward.ward_name = '" + com_ward.getSelectionModel().getSelectedItem() + "' AND\n"
                + "street.street_name = '" + com_street.getSelectionModel().getSelectedItem() + "' AND\n"
                + "assessment.assessment_no LIKE '%" + assessno + "%' \n"
                + "ORDER BY\n"
                + "assessment.idAssessment DESC";

        col_bookNo.setCellValueFactory(new PropertyValueFactory<>("bookNo"));
        col_assessmant.setCellValueFactory(new PropertyValueFactory<>("assesNo"));
        col_customer.setCellValueFactory(new PropertyValueFactory<>("cus"));
        col_alocation.setCellValueFactory(new PropertyValueFactory<>("alocation"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        ResultSet data = null;
        try {
            data = conn.DB.getData(qq);
            ola.clear();
            while (data.next()) {
                String status;
                if (data.getInt("assessment_syn") == 0) {
                    status = "Active";
                } else {
                    status = "Deleted";
                }
                ola.add(new modle.asses.TableAsses(data.getInt("idAssessment"), data.getString("assessment_oder"), data.getString("assessment_no"), data.getString("cus_name"), data.getString("ass_allocation"), status));
                tbl.setItems(ola);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void save(MouseEvent event) {
        collectData();
    }


    public class Subowner {
        Integer id;
        String owner;
        String nic;
        String status;

        public Subowner(Integer id, String owner, String nic, String status) {
            this.id = id;
            this.owner = owner;
            this.nic = nic;
            this.status = status;
        }

        public Integer getId() {
            return id;
        }

        public String getOwner() {
            return owner;
        }

        public String getNic() {
            return nic;
        }

        public String getStatus() {
            return status;
        }
    }


}
