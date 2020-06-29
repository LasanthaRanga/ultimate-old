package controller.tradelicens;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import conn.DB;
import conn.NewHibernateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import modle.tradelicens.StaticBadu;
import modle.tradelicens.TL_Approve;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.*;

import java.awt.*;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Ranga on 2019-01-13.
 */
public class MoreDetails implements Initializable {

    int appId = 0;
    @FXML
    private Text txt_appNo;

    @FXML
    private Text txt_cusname;

    @FXML
    private Text txt_tradename;

    @FXML
    private Text txt_tradtype;

    @FXML
    private Text txt_nature;

    @FXML
    private Text txt_ro;

    @FXML
    private Text txt_amount;

    @FXML
    private Text txt_ward;

    @FXML
    private Text txt_street;

    @FXML
    private Text txt_assessment;

    @FXML
    private Text txt_owner;

    @FXML
    private Text txt_date;

    @FXML
    private JFXTextArea txt_comment;

    @FXML
    private JFXButton btn_approve;

    @FXML
    private JFXButton btn_no;

    @FXML
    private TableView<StatusTable> tbl_list;

    @FXML
    private TableColumn<StatusTable, String> col_a;

    @FXML
    private TableColumn<StatusTable, String> col_b;

    @FXML
    private JFXTextArea txt_oldComment;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        appId = StaticBadu.getAppId();

        loadAll();

    }

    public void loadAll() {
        if (appId != 0) {


            Session session = NewHibernateUtil.getSessionFactory().openSession();
            try {
                Application app = (Application) session.load(Application.class, appId);

                txt_appNo.setText("No : " + app.getApplicationNo());
                txt_cusname.setText(app.getCusName());
                txt_tradename.setText(app.getTradeName());
                txt_date.setText("Date : " + new SimpleDateFormat("yyyy-MM-dd").format(app.getApplicationDate()));
                txt_tradtype.setText(app.getTradeType().getTypeName());
                txt_nature.setText(app.getTradeNature().getNature());
                txt_amount.setText(app.getTaxAmount() + "");
                if (app.getUser() != null) {
                    txt_ro.setText(app.getUser().getUserFullName());
                }

                Assessment assessment = app.getAssessment();
                if (assessment != null) {
                    txt_ward.setText(assessment.getWard().getWardName());
                    txt_street.setText(assessment.getStreet().getStreetName());
                    txt_assessment.setText(assessment.getAssessmentNo());
                    txt_owner.setText(assessment.getCustomer().getCusName());
                }


                List<Apprualstatues> list = session.createCriteria(Apprualstatues.class).add(Restrictions.eq("application", app)).list();
                ObservableList<StatusTable> List = FXCollections.observableArrayList();
                List.clear();
                col_a.setCellValueFactory(new PropertyValueFactory<>("cat"));
                col_b.setCellValueFactory(new PropertyValueFactory<>("name"));

                for (Apprualstatues as : list) {

                    System.out.println("FOR LOOP");

                    ApprovalCat appCat = (ApprovalCat) session.load(ApprovalCat.class, as.getIdOtheritisCat());

                    String status = "";
                    if (as.getStatues() == 0) {
                        status = "Pending";
                    } else if (as.getStatues() == 1) {
                        status = "Approve";
                    } else if (as.getStatues() == 2) {
                        status = "Not Approve";
                    }


                    List.add(new StatusTable(as.getIdApprualStatues(), appCat.getApprovalName(), status, as.getDescription()));


//                    txt_comment.setText(as.getDescription());
//                    if (as.getStatues() == 1) {
//                        txt_comment.setStyle("-fx-text-fill: #81C784");
//                    }
//                    if (as.getStatues() == 2) {
//                        txt_comment.setStyle("-fx-text-fill: #FF8A65");
//                    }
                }

                tbl_list.setItems(List);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        } else {
            modle.Allert.notificationInfo("Not Selected App", "Please Select First");
        }


    }


    @FXML
    void clickOnApprove(MouseEvent event) {
        if (txt_comment.getText().length() > 1) {
            approve(1, txt_comment.getText());
        } else {
            modle.Allert.notificationInfo("Empty Comment", "Please enter some comment");
        }
    }

    @FXML
    void clickOnNo(MouseEvent event) {
        if (txt_comment.getText().length() > 1) {
            approve(2, txt_comment.getText());
        } else {
            modle.Allert.notificationInfo("Empty Comment", "Please enter some comment");
        }
    }

    public void approve(int status, String comment) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            Application app = (Application) session.load(Application.class, appId);

            List<Apprualstatues> list = session.createCriteria(Apprualstatues.class).add(Restrictions.eq("application", app))
                    .add(Restrictions.eq("idOtheritisCat", modle.tradelicens.StaticBadu.getApprovalcatid())).list();

            //Set Approve coment
            for (Apprualstatues as : list) {

                as.setDescription(comment);
                as.setStatues(status);
                as.setApproveDate(new Date());
                as.setResiveUser(modle.StaticViews.getLogUser().getIdUser());
                session.update(as);
            }

            //''''''''''''''''''''''''''''''


            ApprovalCat cphi = (ApprovalCat) session.createCriteria(ApprovalCat.class).add(Restrictions.eq("approvalName", "CMOH or CPHI")).uniqueResult();
            ApprovalCat acco = (ApprovalCat) session.createCriteria(ApprovalCat.class).add(Restrictions.eq("approvalName", "Accountant")).uniqueResult();
            ApprovalCat engi = (ApprovalCat) session.createCriteria(ApprovalCat.class).add(Restrictions.eq("approvalName", "Engineer")).uniqueResult();

            System.out.println(acco.getIdApprovalCat());

            if (acco.getIdApprovalCat() == modle.tradelicens.StaticBadu.getApprovalcatid()) {
                //Approve to Pay
                if (status == 1) {
                    app.setApproveToPaymant(1);
                    session.update(app);
                }


            } else if (cphi.getIdApprovalCat() == modle.tradelicens.StaticBadu.getApprovalcatid()) {


                //send to engineere

                TradeNature tradeNature = app.getTradeNature();

                ResultSet data = DB.getData("SELECT\n" +
                        "gotoeng.NatureCat\n" +
                        "FROM\n" +
                        "gotoeng");

                boolean b = false;
                while (data.next()) {
                    int anInt = data.getInt("gotoeng.NatureCat");
                    if (anInt == tradeNature.getIdTradeNature()) {

                        b = true;
                        break;
                    }
                }

                if (b) {
                    //send to engineer

                    Apprualstatues apprualstatues = (Apprualstatues) session.createCriteria(Apprualstatues.class)
                            .add(Restrictions.eq("application", app))
                            .add(Restrictions.eq("idOtheritisCat", engi.getIdApprovalCat())).uniqueResult();

                    if (apprualstatues == null) {
                        apprualstatues = new Apprualstatues();
                    }
                    apprualstatues.setUser((User) session.load(User.class, modle.StaticViews.getLogUser().getIdUser()));
                    apprualstatues.setApplication(app);
                    apprualstatues.setSendDate(new Date());
                    apprualstatues.setStatues(0);
                    apprualstatues.setIdOtheritisCat(engi.getIdApprovalCat());
                    session.saveOrUpdate(apprualstatues);


                } else {
                    //send to accountent
                    //Send To Accountent
                    Apprualstatues apprualstatues = (Apprualstatues) session.createCriteria(Apprualstatues.class)
                            .add(Restrictions.eq("application", app))
                            .add(Restrictions.eq("idOtheritisCat", acco.getIdApprovalCat())).uniqueResult();

                    if (apprualstatues == null) {
                        apprualstatues = new Apprualstatues();
                    }
                    apprualstatues.setUser((User) session.load(User.class, modle.StaticViews.getLogUser().getIdUser()));
                    apprualstatues.setApplication(app);
                    apprualstatues.setSendDate(new Date());
                    apprualstatues.setStatues(0);
                    apprualstatues.setIdOtheritisCat(acco.getIdApprovalCat());
                    session.saveOrUpdate(apprualstatues);
                }


            } else {
                //Send To CPHI


                Apprualstatues apprualstatues = (Apprualstatues) session.createCriteria(Apprualstatues.class)
                        .add(Restrictions.eq("application", app))
                        .add(Restrictions.eq("idOtheritisCat", cphi.getIdApprovalCat())).uniqueResult();

                if (apprualstatues == null) {
                    apprualstatues = new Apprualstatues();
                }

                apprualstatues.setUser((User) session.load(User.class, modle.StaticViews.getLogUser().getIdUser()));
                apprualstatues.setApplication(app);
                apprualstatues.setSendDate(new Date());
                apprualstatues.setStatues(0);
                apprualstatues.setIdOtheritisCat(cphi.getIdApprovalCat());
                session.saveOrUpdate(apprualstatues);
            }


            //======================

            session.beginTransaction().commit();
            modle.Allert.notificationGood("Sent", comment);
            loadAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    @FXML
    void clickOnTable(MouseEvent event) {
        StatusTable selectedItem = tbl_list.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txt_oldComment.setText(selectedItem.getStatus());
        }
    }

    public class StatusTable {
        public StatusTable(int id, String cat, String name, String status) {
            this.id = id;
            this.cat = new SimpleStringProperty(cat);
            this.name = new SimpleStringProperty(name);
            this.status = new SimpleStringProperty(status);
        }

        private int id;
        private SimpleStringProperty cat;
        private SimpleStringProperty name;
        private SimpleStringProperty status;

        public int getId() {
            return id;
        }

        public String getCat() {
            return cat.get();
        }

        public String getName() {
            return name.get();
        }

        public String getStatus() {
            return status.get();
        }
    }


}
