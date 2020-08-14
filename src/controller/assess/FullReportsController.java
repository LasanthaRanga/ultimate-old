package controller.assess;

import com.jfoenix.controls.*;
import conn.DB;
import controller.adv.Customer_regController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import modle.GetInstans;
import modle.KeyVal;
import modle.asses.*;
import view.buttons.BTN;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


public class FullReportsController implements Initializable {

    @FXML
    private JFXCheckBox cheack_ward;
    @FXML
    private JFXComboBox<String> com_ward;
    @FXML
    private JFXComboBox<String> com_street;
    @FXML
    private JFXCheckBox cheack_street;
    @FXML
    private JFXComboBox<String> com_nature;
    @FXML
    private JFXCheckBox cheack_nature;
    @FXML
    private JFXTextField txt_assessment;
    @FXML
    private JFXCheckBox cheack_assessment;
    @FXML
    private JFXTextField txt_obsolete;
    @FXML
    private JFXCheckBox cheack_obsolete;
    @FXML
    private JFXTextField txt_customer;
    @FXML
    private JFXCheckBox cheack_customer;
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
    @FXML
    private JFXButton btn_paySelected;
    @FXML
    private Text txt_selected;
    @FXML
    private JFXButton btn_getBook;
    @FXML
    private JFXButton btn_warrant;
    @FXML
    private JFXTextField txt_more;
    @FXML
    private JFXTextField txt_less;
    @FXML
    private TableView<NatureHolder> tbl_nature;
    @FXML
    private TableColumn<NatureHolder, String> col_nname;
    @FXML
    private TableColumn<NatureHolder, JFXCheckBox> col_nchek;
    @FXML
    private JFXCheckBox tbl_check;


    int currentYear;
    int cq = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txt_assessment.setDisable(true);
        txt_customer.setDisable(true);
        txt_obsolete.setDisable(true);
        com_ward.setDisable(true);
        com_street.setDisable(true);
        com_nature.setDisable(true);
        tableCollomAssing();

        currentYear = GetInstans.getQuater().getCurrentYear();
        //  n = true;  // governmet office atha herima;
        modle.StaticViews.getMc().changeTitle(" Assessment Reports ");

        tbl_nature.setItems(modle.GetInstans.getNature().getNatureSelectList());
        cq = GetInstans.getQuater().getPrviasQuater();

    }

    boolean w = false;
    boolean s = false;
    boolean n = false;
    boolean a = false;
    boolean o = false;
    boolean c = false;

    String ward;
    String street;
    String nature;
    String assessment;
    String obsolete;
    String customer;

    @FXML
    private void cheackWard(ActionEvent event) {
        if (cheack_ward.isSelected()) {
            w = true;
            com_ward.setDisable(false);
            com_ward.setItems(modle.GetInstans.getWardModle().getWardObservableListSQL());
        } else {
            w = false;
            ward = null;
            com_ward.setItems(null);
            com_ward.setDisable(true);
        }

    }

    @FXML
    private void selectWard(ActionEvent event) {
        ward = com_ward.getSelectionModel().getSelectedItem();
        if (s) {
            com_street.setItems(modle.GetInstans.getStreetModle().getStreetObservableListSQLByWard(ward));
        }
        searchAssessment();
    }

    @FXML
    private void selectStreet(ActionEvent event) {
        street = com_street.getSelectionModel().getSelectedItem();
        searchAssessment();
    }

    @FXML
    private void cheackStreet(ActionEvent event) {
        if (cheack_street.isSelected()) {
            com_street.setDisable(false);
            s = true;
            if (w) {
                if (ward == null) {
                    modle.Allert.notificationWorning("Ward Is Empty", "Please Select Ward");
                } else {
                    com_street.setItems(modle.GetInstans.getStreetModle().getStreetObservableListSQLByWard(ward));
                }
            } else {
                com_street.setItems(modle.GetInstans.getStreetModle().getAllStreetObservableListSQL());
            }
        } else {
            s = false;
            street = null;
            com_street.setItems(null);
            com_street.setDisable(true);
        }
    }

    @FXML
    private void selectNature(ActionEvent event) {
        nature = com_nature.getSelectionModel().getSelectedItem();
        searchAssessment();
    }

    @FXML
    private void cheackNature(ActionEvent event) {
        if (cheack_nature.isSelected()) {
            n = true;
            com_nature.setDisable(false);
            com_nature.setItems(modle.GetInstans.getNature().getNatureObservableListSQL());
        } else {
            n = false;
            com_nature.setItems(null);
            nature = null;
            com_nature.setDisable(true);
        }
    }

    @FXML
    private void typeAssess(KeyEvent event) {
        assessment = txt_assessment.getText();
        searchAssessment();
    }

    @FXML
    private void typeObsolete(KeyEvent event) {
        obsolete = txt_obsolete.getText();
        searchAssessment();
    }

    @FXML
    private void typeCustomer(KeyEvent event) {
        customer = txt_customer.getText();
        searchAssessment();
    }

    @FXML
    private void cheackAssessment(ActionEvent event) {
        if (cheack_assessment.isSelected()) {
            a = true;
            txt_assessment.setDisable(false);
        } else {
            a = false;
            assessment = null;
            txt_assessment.setText(null);
            txt_assessment.setDisable(true);
        }
    }

    @FXML
    private void cheackObsolete(ActionEvent event) {
        if (cheack_obsolete.isSelected()) {
            o = true;
            txt_obsolete.setDisable(false);
        } else {
            o = false;
            txt_obsolete.setText(null);
            txt_obsolete.setDisable(true);
            obsolete = null;
        }
    }

    @FXML
    private void cheackCustomer(ActionEvent event) {
        if (cheack_customer.isSelected()) {
            c = true;
            txt_customer.setDisable(false);
        } else {
            c = false;
            txt_customer.setDisable(true);
            txt_customer.setText(null);
            customer = null;
        }
    }

    @FXML
    void chechk_natureOnAction(ActionEvent event) {
        ObservableList<NatureHolder> items = tbl_nature.getItems();
        if (tbl_check.isSelected()) {
            for (NatureHolder nh : items) {
                nh.getCheckBox().setSelected(true);
            }
        } else {
            for (NatureHolder nh : items) {
                nh.getCheckBox().setSelected(false);
            }
        }
    }

    public static String natureList = "";

    public void genarateNature() {
        try {
            natureList = "";
            ObservableList<NatureHolder> items = tbl_nature.getItems();
            ObservableList<NatureHolder> items2 = FXCollections.observableArrayList();
//            items2.removeAll();
            for (NatureHolder na : items) {
                if (na.getCheckBox().isSelected()) {
                    items2.add(na);
                }
            }

            int size = items2.size();
            for (int x = 0; x < size; x++) {
                if (x == size - 1) {
                    natureList += items2.get(x).getId();
                } else {
                    natureList += items2.get(x).getId() + ",";
                }
            }
            System.out.println(natureList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void searchAssessment() {

        genarateNature();
        if (!natureList.equals("")) {

            boolean where = false;

            if (w) {
                where = true;
            }
            if (s) {
                where = true;
            }
            if (n) {
                where = true;
            }
            if (a) {
                where = true;
            }
            if (o) {
                where = true;
            }
            if (c) {
                where = true;
            }

            String qq = "SELECT\n"
                    + "	assessment.idAssessment,\n"
                    + "	assessment.Customer_idCustomer,\n"
                    + "	assessment.Ward_idWard,\n"
                    + "	assessment.Street_idStreet,\n"
                    + "	assessment.ass_nature_idass_nature,\n"
                    + "	assessment.ass_discription_idass_discription,\n"
                    + "	assessment.User_idUser,\n"
                    + "	assessment.assessment_oder,\n"
                    + "	assessment.assessment_no,\n"
                    + "	assessment.assessment_status,\n"
                    + "	assessment.assessment_syn,\n"
                    + "	assessment.assessment_comment,\n"
                    + "	assessment.assessment_obsolete,\n"
                    + "	customer.cus_name,\n"
                    + "	customer.cus_nic,\n"
                    + "	customer.cus_mobile,\n"
                    + "	customer.cus_address_l1,\n"
                    + "	customer.cus_tel,\n"
                    + "	customer.cus_address_l2,\n"
                    + "	customer.cus_address_l3,\n"
                    + "	customer.cus_sity,\n"
                    + "	customer.cus_status,\n"
                    + "	customer.idCustomer,\n"
                    + "	ward.ward_name,\n"
                    + "	ward.idWard,\n"
                    + "	street.idStreet,\n"
                    + "	street.street_name,\n"
                    + "	ass_nature.idass_nature,\n "
                    + "     ass_allocation.ass_allocation, \n"
                    + "	ass_nature.ass_nature_name \n"
                    + "FROM\n"
                    + "	assessment\n"
                    + "	INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n"
                    + "	INNER JOIN ward ON assessment.Ward_idWard = ward.idWard\n"
                    + "	INNER JOIN street ON street.Ward_idWard = ward.idWard \n"
                    + "	AND assessment.Street_idStreet = street.idStreet\n"
                    + "	INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature "
                    + "     INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment WHERE ass_allocation.ass_allocation_status = 1";

            if (where) {
                if (w) {
                    qq += " AND ward.ward_name = '" + ward + "' ";
                }
                if (s) {
                    qq += " AND street.street_name = '" + street + "' ";
                }


//            if (n) {
//                qq += " AND ass_nature.ass_nature_name = '" + nature + "' ";
//                //  qq += "AND (ass_nature.ass_nature_name = 'House' OR ass_nature.ass_nature_name ='Bussines' OR ass_nature.ass_nature_name ='Land' OR ass_nature.ass_nature_name ='Paddy Field')";
//            }

                if (a) {
                    qq += " AND assessment.assessment_no LIKE '%" + assessment + "%' ";
                }
                if (o) {
                    qq += " AND assessment.assessment_obsolete LIKE '%" + obsolete + "%' ";
                }
                if (c) {
                    qq += " AND customer.cus_name LIKE '%" + customer + "%' ";
                }
            }

            qq += " AND  assessment.ass_nature_idass_nature IN (" + natureList + ")";
            qq += " ORDER BY assessment.assessment_oder ASC ";
            // System.out.println(qq);
            executeQuary(qq);
        } else {
            List.clear();
        }
    }

    public ObservableList<HolderAssess> List = FXCollections.observableArrayList();

    public void executeQuary(String qq) {
        try {
            ResultSet rs = DB.getData(qq);
            List.clear();
            while (rs.next()) {
                HolderAssess holderAssess = new HolderAssess(rs.getInt("idAssessment"), rs.getDouble("assessment_oder"), rs.getString("ass_nature.ass_nature_name"), rs.getString("ward_name"), rs.getString("street_name"), rs.getString("assessment_no"), rs.getString("assessment_obsolete"), rs.getDouble("ass_allocation.ass_allocation"), rs.getString("cus_name"), rs.getString("assessment_status"));
                List.add(holderAssess);
            }
            tbl_assess.setItems(List);
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

        col_nname.setCellValueFactory(new PropertyValueFactory<>("nature"));
        col_nchek.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
    }

    @FXML
    private void getKForm(ActionEvent event) {
        String ss = "";
        for (HolderAssess holderAssess : List) {
            ss += ",";
            ss += holderAssess.getIdAssess();
        }
        ss = ss.substring(1);

        System.out.println(ss);
        modle.GetInstans.getAssessReport().getKform(ss);

    }

    int idAssess = 0;

    @FXML
    private void selectFormTable(MouseEvent event) {
        if (tbl_assess.getSelectionModel().getSelectedItem() != null) {
            idAssess = tbl_assess.getSelectionModel().getSelectedItem().getIdAssess();
            txt_selected.setText(idAssess + "");
        }
    }

    @FXML
    private void clickOnPay(MouseEvent event) {

        if (idAssess != 0) {

            modle.asses.StaticBadu.setIdAssessment(idAssess);

            AnchorPane container = modle.StaticViews.getContainer();
            container.getChildren().removeAll();
            container.getChildren().clear();
            AnchorPane dashh;
            try {
                dashh = FXMLLoader.load(getClass().getResource("/view/assess/PayView.fxml"));
                container.getChildren().add(dashh);
            } catch (IOException ex) {
                ex.printStackTrace();
                Logger
                        .getLogger(Customer_regController.class
                                .getName()).log(Level.SEVERE, null, ex);
            }
            Set<String> keySet = modle.StaticViews.getButtonMap().keySet();
            String s = "/view/buttons/assess_pay2.fxml";

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
    private void clickOnBook(MouseEvent event) {

        String ss = "";
        for (HolderAssess holderAssess : List) {
            ss += ",";
            ss += holderAssess.getIdAssess();
        }
        ss = ss.substring(1);

        System.out.println(ss);
        modle.GetInstans.getAssessReport().getBook(ss);

    }

    //=====================================================================================================================

    @FXML
    private JFXButton btn_arrias;


    @FXML
    void clickOnCurrentArrias(MouseEvent event) {
        String text = txt_more.getText();
        String les = txt_less.getText();
        double l = 0;
        if (les.equals("∞")) {
            l = Double.MAX_VALUE;
        }
        try {
            if (l == 0) {
                l = Double.parseDouble(les);
            }

            double v = Double.parseDouble(text);

            if (v <= l) {


                ObservableList<HolderAssess> items = tbl_assess.getItems();
                ArrayList<RipHolder> list = new ArrayList<>();
                for (HolderAssess holderAssess : items) {
                    RipHolder assessDataByIDAssess = getAssessDataByIDAssess(holderAssess.getIdAssess());
                    System.out.println(assessDataByIDAssess.getIdAssessment());
                    if (assessDataByIDAssess.getIdAssessment() != 0) {
                        double tot = 0;
                        tot += assessDataByIDAssess.getThisYearCurrentWarrant();
                        tot += assessDataByIDAssess.getThisYearCurrentArrias();
                        tot += assessDataByIDAssess.getLYCA();
                        tot += assessDataByIDAssess.getLYCW();
                        tot += assessDataByIDAssess.getCd();
                        if (tot > 0 && tot >= v && tot <= l) {
                            list.add(assessDataByIDAssess);
                        }
                    }
                }
                String current_arriars_report_path = KeyVal.getVal("current_arriars_report_path");
                modle.GetInstans.getAssessReport().loadReportByDataSource(list, current_arriars_report_path);
                exportToExcel(list);
            } else {
                modle.Allert.notificationError("Enter Correct Value ", "Check Values");
            }
        } catch (Exception e) {
            e.printStackTrace();
            modle.Allert.notificationError("Enter Correct Value ", "Check Values");
        } finally {
        }
    }


    @FXML
    void clickOnWarrant(MouseEvent event) {
        ObservableList<HolderAssess> items = tbl_assess.getItems();
        ArrayList<RipHolder> list = new ArrayList<>();
        for (HolderAssess holderAssess : items) {
            //   RipHolder assessDataByIDAssess = getOldWarrantArriasByID(holderAssess.getIdAssess());
            RipHolder assessDataByIDAssess = getQendWarrantArriasByID(holderAssess.getIdAssess());

            System.out.println(assessDataByIDAssess.getIdAssessment());
            if (assessDataByIDAssess.getIdAssessment() != 0) {
                double tot = 0;
                tot += assessDataByIDAssess.getThisYearCurrentWarrant();
                tot += assessDataByIDAssess.getThisYearCurrentArrias();
                tot += assessDataByIDAssess.getLYCA();
                tot += assessDataByIDAssess.getLYCW();
                tot += assessDataByIDAssess.getCd();
                System.out.println("TOTAL ===== " + tot);
                if (tot > 0) {
                    list.add(assessDataByIDAssess);
                }
            }
        }
        String q_end_aw_report = KeyVal.getVal("Q_END_AW_REPORT");
        modle.GetInstans.getAssessReport().loadReportByDataSource(list, q_end_aw_report);
        exportToExcel(list);
    }


    public void exportToExcel(ArrayList<RipHolder> list) {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet ws = wb.createSheet();

            TreeMap<Integer, Object[]> tm = new TreeMap<>();
            tm.put(-1, new Object[]{"Assess ID", "Ward", "Street", "Assess NO", "Customer Name", "Quarter Value", "Last Year Warrant", "Last Year Arrears", "This Year Warrant", "This Year Arrears", "Additional Debit", "Total"});


            int x = 0;

            for (RipHolder rh : list) {
                x++;
                System.out.println(rh.getIdAssessment());
                System.out.println("export");

                tm.put(x , new Object[]{
                        rh.getIdAssessment(),
                        rh.getIdWard(),
                        rh.getStreetName(),
                        rh.getAssessNo(),
                        rh.getCustomerName(),
                        rh.getQuaterVal(),
                        rh.getLYW(),
                        rh.getLYA(),
                        rh.getThisYearCurrentWarrant(),
                        rh.getThisYearCurrentArrias(),
                        rh.getCd(),
                        modle.Maths.round2(rh.getLYW() + rh.getLYA() + rh.getThisYearCurrentArrias() + rh.getThisYearCurrentWarrant() + rh.getCd())});
            }


            Set<Integer> ids = tm.keySet();

            System.out.println("================");
            System.out.println(tm.keySet());
            System.out.println("================");
            System.out.println(tm.size());
            System.out.println("================");

            XSSFRow row;
            int rowId = 0;

            for (Integer id : ids) {
                row = ws.createRow(rowId++);

                Object[] values = tm.get(id);
                int celID = 0;
                for (Object value : values) {
                    Cell cell = row.createCell(celID);
                    cell.setCellValue(value.toString());

                    celID++;

                }

            }

            try {
                String format = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date());
                String excelExportPath = KeyVal.getVal("ExcelExportPath");
                FileOutputStream fos = new FileOutputStream(new File(excelExportPath + format + "report.xlsx"));
                wb.write(fos);
             //   fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            //  Logger.getLogger(ExportTo_excel.class.getName()).log(Level.SEVERE, null, ex);
        }finally {

        }

    }


    public RipHolder getQendWarrantArriasByID(int assessID) {


        String query = "SELECT\n" +
                "\tass_qstart.idass_Qstart,\n" +
                "\tass_qstart.ass_Qstart_QuaterNumber,\n" +
                "\tass_qstart.ass_Qstart_process_date,\n" +
                "\tass_qstart.ass_Qstart_LY_Arreas,\n" +
                "\tass_qstart.ass_Qstart_LY_Warrant,\n" +
                "\tass_qstart.ass_Qstart_LYC_Arreas,\n" +
                "\tass_qstart.ass_Qstart_LYC_Warrant,\n" +
                "\tass_qstart.ass_Qstart_LQ_Arreas,\n" +
                "\tass_qstart.ass_Qstart_LQC_Arreas,\n" +
                "\tass_qstart.ass_Qstart_LQ_Warrant,\n" +
                "\tass_qstart.ass_Qstart_LQC_Warrant,\n" +
                "\tass_qstart.ass_Qstart_HaveToQPay,\n" +
                "\tass_qstart.ass_Qstart_QPay,\n" +
                "\tass_qstart.ass_Qstart_QDiscont,\n" +
                "\tass_qstart.ass_Qstart_QTot,\n" +
                "\tass_qstart.ass_Qstart_FullTotal,\n" +
                "\tass_qstart.ass_Qstart_status,\n" +
                "\tass_qstart.Assessment_idAssessment,\n" +
                "\tass_qstart.ass_Qstart_year,\n" +
                "\tassessment.assessment_no,\n" +
                "\tstreet.street_name,\n" +
                "\tward.ward_no,\n" +
                "\tcustomer.cus_name,\n" +
                "\tass_qstart.ass_Qstart_tyold_arrias,\n" +
                "\tass_qstart.ass_Qstart_tyold_warant\n" +
                "FROM\n" +
                "\tass_qstart\n" +
                "INNER JOIN assessment ON ass_qstart.Assessment_idAssessment = assessment.idAssessment\n" +
                "INNER JOIN street ON assessment.Street_idStreet = street.idStreet\n" +
                "INNER JOIN ward ON assessment.Ward_idWard = ward.idWard\n" +
                "AND street.Ward_idWard = ward.idWard\n" +
                "INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n" +
                "WHERE\n" +
                "\tass_qstart.ass_Qstart_year = '" + currentYear + "'\n" +
                "AND ass_qstart.Assessment_idAssessment = '" + assessID + "'\n" +
                "AND assessment.assessment_syn = 0\n" +
                "AND ass_Qstart_status = 1";

        String cdbal = "SELECT\n" +
                "ass_creditdebit.Ass_balance\n" +
                "FROM `ass_creditdebit`\n" +
                "WHERE\n" +
                "ass_creditdebit.Ass_CreditDebit_status = 1 AND\n" +
                "ass_creditdebit.Ass_CreditDebit_amount > 0 AND\n" +
                "ass_creditdebit.Assessment_idAssessment = " + assessID;

        String qval = "SELECT\n" +
                "ROUND((ass_allocation.ass_allocation * ass_nature.ass_nature_year_rate /100) /4,2) as qv\n" +
                "FROM\n" +
                "assessment\n" +
                "INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                "WHERE\n" +
                "ass_allocation.ass_allocation_status = 1 AND\n" +
                "assessment.idAssessment = " + assessID;


        RipHolder ripHolder = new RipHolder();

        ripHolder.setCq(cq);
        ripHolder.setCy(currentYear - 1);

        try {
            ResultSet data = DB.getData(query);

            ResultSet cdResult = DB.getData(cdbal);

            double cdval = 0.0;

            if (cdResult.last()) {
                cdval = cdResult.getDouble("ass_creditdebit.Ass_balance");
            }
            ripHolder.setCd(cdval);

            double quval = 0.0;

            ResultSet qvReslt = DB.getData(qval);
            if (qvReslt.last()) {
                quval = qvReslt.getDouble("qv");
            }
            ripHolder.setQuaterVal(quval);
            if (data.last()) {
                ripHolder.setAssessNo(data.getString("assessment_no"));
                int ass_qstart_quaterNumber = data.getInt("ass_Qstart_QuaterNumber");
                int ass_qstart_status = data.getInt("ass_Qstart_status");
                ripHolder.setAssessData(assessID, data.getInt("ward_no"), data.getString("street_name"), data.getString("cus_name"));
                int qn = data.getInt("ass_Qstart_QuaterNumber");
                if (qn != 1) {
                    ripHolder.setThisYearCurrentArrias(data.getDouble("ass_Qstart_tyold_arrias"));
                    ripHolder.setThisYearCurrentWarrant(data.getDouble("ass_Qstart_tyold_warant"));
                } else {
                    ripHolder.setThisYearCurrentArrias(0.0);
                    ripHolder.setThisYearCurrentWarrant(0.0);
                    //   ripHolder.setThisYearCurrentArrias(data.getDouble("ass_Qstart_tyold_arrias"));
                    // ripHolder.setThisYearCurrentWarrant(data.getDouble("ass_Qstart_tyold_warant"));
                }
                ripHolder.setLasatYearArriars(data.getDouble("ass_Qstart_LYC_Arreas"), data.getDouble("ass_Qstart_LY_Arreas"), 0);
                ripHolder.setLastYearWarrant(data.getDouble("ass_Qstart_LYC_Warrant"), data.getDouble("ass_Qstart_LY_Warrant"), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


        return ripHolder;
    }

    public RipHolder getOldWarrantArriasByID(int assessID) {


        String query = "SELECT\n" +
                "\tass_qstart.idass_Qstart,\n" +
                "\tass_qstart.ass_Qstart_QuaterNumber,\n" +
                "\tass_qstart.ass_Qstart_process_date,\n" +
                "\tass_qstart.ass_Qstart_LY_Arreas,\n" +
                "\tass_qstart.ass_Qstart_LY_Warrant,\n" +
                "\tass_qstart.ass_Qstart_LYC_Arreas,\n" +
                "\tass_qstart.ass_Qstart_LYC_Warrant,\n" +
                "\tass_qstart.ass_Qstart_LQ_Arreas,\n" +
                "\tass_qstart.ass_Qstart_LQC_Arreas,\n" +
                "\tass_qstart.ass_Qstart_LQ_Warrant,\n" +
                "\tass_qstart.ass_Qstart_LQC_Warrant,\n" +
                "\tass_qstart.ass_Qstart_HaveToQPay,\n" +
                "\tass_qstart.ass_Qstart_QPay,\n" +
                "\tass_qstart.ass_Qstart_QDiscont,\n" +
                "\tass_qstart.ass_Qstart_QTot,\n" +
                "\tass_qstart.ass_Qstart_FullTotal,\n" +
                "\tass_qstart.ass_Qstart_status,\n" +
                "\tass_qstart.Assessment_idAssessment,\n" +
                "\tass_qstart.ass_Qstart_year,\n" +
                "\tassessment.assessment_no,\n" +
                "\tstreet.street_name,\n" +
                "\tward.ward_no,\n" +
                "\tcustomer.cus_name,\n" +
                "\tass_qstart.ass_Qstart_tyold_arrias,\n" +
                "\tass_qstart.ass_Qstart_tyold_warant\n" +
                "FROM\n" +
                "\tass_qstart\n" +
                "INNER JOIN assessment ON ass_qstart.Assessment_idAssessment = assessment.idAssessment\n" +
                "INNER JOIN street ON assessment.Street_idStreet = street.idStreet\n" +
                "INNER JOIN ward ON assessment.Ward_idWard = ward.idWard\n" +
                "AND street.Ward_idWard = ward.idWard\n" +
                "INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n" +
                "WHERE\n" +
                "\tass_qstart.ass_Qstart_year = '" + 2020 + "'\n" +
                "AND ass_qstart.Assessment_idAssessment = '" + assessID + "'\n" +
                "AND assessment.assessment_syn = 0\n" +
                "AND ass_Qstart_QuaterNumber = 1";

        String cdbal = "SELECT\n" +
                "ass_creditdebit.Ass_balance\n" +
                "FROM `ass_creditdebit`\n" +
                "WHERE\n" +
                "ass_creditdebit.Ass_CreditDebit_status = 1 AND\n" +
                "ass_creditdebit.Ass_CreditDebit_amount > 0 AND\n" +
                "ass_creditdebit.Assessment_idAssessment = " + assessID;

        String qval = "SELECT\n" +
                "ROUND((ass_allocation.ass_allocation * ass_nature.ass_nature_year_rate /100) /4,2) as qv\n" +
                "FROM\n" +
                "assessment\n" +
                "INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                "WHERE\n" +
                "ass_allocation.ass_allocation_status = 1 AND\n" +
                "assessment.idAssessment = " + assessID;


        RipHolder ripHolder = new RipHolder();

        ripHolder.setCq(cq);
        ripHolder.setCy(currentYear - 1);

        try {
            ResultSet data = DB.getData(query);

            ResultSet cdResult = DB.getData(cdbal);

            double cdval = 0.0;

            if (cdResult.last()) {
                cdval = cdResult.getDouble("ass_creditdebit.Ass_balance");
            }
            ripHolder.setCd(cdval);

            double quval = 0.0;

            ResultSet qvReslt = DB.getData(qval);
            if (qvReslt.last()) {
                quval = qvReslt.getDouble("qv");
            }
            ripHolder.setQuaterVal(quval);
            if (data.last()) {
                ripHolder.setAssessNo(data.getString("assessment_no"));
                int ass_qstart_quaterNumber = data.getInt("ass_Qstart_QuaterNumber");
                int ass_qstart_status = data.getInt("ass_Qstart_status");
                ripHolder.setAssessData(assessID, data.getInt("ward_no"), data.getString("street_name"), data.getString("cus_name"));
                int qn = data.getInt("ass_Qstart_QuaterNumber");
                if (qn != 1) {
                    ripHolder.setThisYearCurrentArrias(data.getDouble("ass_Qstart_tyold_arrias"));
                    ripHolder.setThisYearCurrentWarrant(data.getDouble("ass_Qstart_tyold_warant"));
                } else {
                    ripHolder.setThisYearCurrentArrias(0.0);
                    ripHolder.setThisYearCurrentWarrant(0.0);
                }
                ripHolder.setLasatYearArriars(data.getDouble("ass_Qstart_LYC_Arreas"), data.getDouble("ass_Qstart_LY_Arreas"), 0);
                ripHolder.setLastYearWarrant(data.getDouble("ass_Qstart_LYC_Warrant"), data.getDouble("ass_Qstart_LY_Warrant"), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


        return ripHolder;
    }


    public RipHolder getAssessDataByIDAssess(int assessID) {


        String quary = "SELECT\n" +
                "\tass_qstart.idass_Qstart,\n" +
                "\tass_qstart.ass_Qstart_QuaterNumber,\n" +
                "\tass_qstart.ass_Qstart_process_date,\n" +
                "\tass_qstart.ass_Qstart_LY_Arreas,\n" +
                "\tass_qstart.ass_Qstart_LY_Warrant,\n" +
                "\tass_qstart.ass_Qstart_LYC_Arreas,\n" +
                "\tass_qstart.ass_Qstart_LYC_Warrant,\n" +
                "\tass_qstart.ass_Qstart_LQ_Arreas,\n" +
                "\tass_qstart.ass_Qstart_LQC_Arreas,\n" +
                "\tass_qstart.ass_Qstart_LQ_Warrant,\n" +
                "\tass_qstart.ass_Qstart_LQC_Warrant,\n" +
                "\tass_qstart.ass_Qstart_HaveToQPay,\n" +
                "\tass_qstart.ass_Qstart_QPay,\n" +
                "\tass_qstart.ass_Qstart_QDiscont,\n" +
                "\tass_qstart.ass_Qstart_QTot,\n" +
                "\tass_qstart.ass_Qstart_FullTotal,\n" +
                "\tass_qstart.ass_Qstart_status,\n" +
                "\tass_qstart.Assessment_idAssessment,\n" +
                "\tass_qstart.ass_Qstart_year,\n" +
                "\tassessment.assessment_no,\n" +
                "\tstreet.street_name,\n" +
                "\tward.ward_no,\n" +
                "\tcustomer.cus_name\n" +
                "FROM\n" +
                "\tass_qstart\n" +
                "INNER JOIN assessment ON ass_qstart.Assessment_idAssessment = assessment.idAssessment\n" +
                "INNER JOIN street ON assessment.Street_idStreet = street.idStreet\n" +
                "INNER JOIN ward ON assessment.Ward_idWard = ward.idWard\n" +
                "AND street.Ward_idWard = ward.idWard\n" +
                "\n" +
                "INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n" +
                "WHERE\n" +
                "ass_qstart.ass_Qstart_year = '" + currentYear + "' AND\n" +
                "(ass_qstart.ass_Qstart_LYC_Arreas > 0 OR\n" +
                "ass_qstart.ass_Qstart_LYC_Warrant > 0 OR\n" +
                "ass_qstart.ass_Qstart_LQC_Arreas > 0 OR\n" +
                "ass_qstart.ass_Qstart_LQC_Warrant > 0) AND\n" +
                "ass_qstart.Assessment_idAssessment = '" + assessID + "'  AND\n" +
                " assessment.assessment_syn = 0";


        String cdbal = "SELECT\n" +
                "ass_creditdebit.Ass_balance\n" +
                "FROM `ass_creditdebit`\n" +
                "WHERE\n" +
                "ass_creditdebit.Ass_CreditDebit_status = 1 AND\n" +
                "ass_creditdebit.Ass_CreditDebit_amount > 0 AND\n" +
                "ass_creditdebit.Assessment_idAssessment = " + assessID;

        String qval = "SELECT\n" +
                "ROUND((ass_allocation.ass_allocation * ass_nature.ass_nature_year_rate /100) /4,2) as qv\n" +
                "FROM\n" +
                "assessment\n" +
                "INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                "WHERE\n" +
                "ass_allocation.ass_allocation_status = 1 AND\n" +
                "assessment.idAssessment = " + assessID;


        RipHolder ripHolder = new RipHolder();
        try {
            ResultSet data = DB.getData(quary);

            ResultSet cdResult = DB.getData(cdbal);

            double cdval = 0.0;

            if (cdResult.last()) {
                cdval = cdResult.getDouble("ass_creditdebit.Ass_balance");
            }
            ripHolder.setCd(cdval);

            double quval = 0.0;

            ResultSet qvReslt = DB.getData(qval);
            if (qvReslt.last()) {
                quval = qvReslt.getDouble("qv");
            }
            ripHolder.setQuaterVal(quval);


            while (data.next()) {


//                System.out.println(assessID);


                ripHolder.setAssessNo(data.getString("assessment_no"));
                int ass_qstart_quaterNumber = data.getInt("ass_Qstart_QuaterNumber");
                int ass_qstart_status = data.getInt("ass_Qstart_status");
                ripHolder.setAssessData(assessID, data.getInt("ward_no"), data.getString("street_name"), data.getString("cus_name"));

                if (ass_qstart_quaterNumber == 1) {
                    if (ass_qstart_status == 1) {
                        ripHolder.setLasatYearArriars(data.getDouble("ass_Qstart_LY_Arreas"), data.getDouble("ass_Qstart_LYC_Arreas"), 0);
                        ripHolder.setLastYearWarrant(data.getDouble("ass_Qstart_LY_Warrant"), data.getDouble("ass_Qstart_LYC_Warrant"), 0);
                    }
                }

                if (ass_qstart_quaterNumber == 2) {
                    ripHolder.setQ1(data.getDouble("ass_Qstart_LQ_Arreas"), data.getDouble("ass_Qstart_LQ_Warrant"), data.getDouble("ass_Qstart_LQC_Arreas"), data.getDouble("ass_Qstart_LQC_Warrant"), 0, data.getDouble("ass_Qstart_QDiscont"), data.getDouble("ass_Qstart_QPay"));

                    ripHolder.setThisYearCurrentArrias(data.getDouble("ass_Qstart_LQC_Arreas"));
                    ripHolder.setThisYearCurrentWarrant(data.getDouble("ass_Qstart_LQC_Warrant"));

                    if (ass_qstart_status == 1) {
                        ripHolder.setLasatYearArriars(data.getDouble("ass_Qstart_LY_Arreas"), data.getDouble("ass_Qstart_LYC_Arreas"), 0);
                        ripHolder.setLastYearWarrant(data.getDouble("ass_Qstart_LY_Warrant"), data.getDouble("ass_Qstart_LYC_Warrant"), 0);

                    }
                    ripHolder.setQ1(data.getDouble("ass_Qstart_LQ_Arreas"), data.getDouble("ass_Qstart_LQ_Warrant"), data.getDouble("ass_Qstart_LQC_Arreas"), data.getDouble("ass_Qstart_LQC_Warrant"), 0, 0, 0);


                }

                if (ass_qstart_quaterNumber == 3) {
                    ripHolder.setQ2(data.getDouble("ass_Qstart_LQ_Arreas"), data.getDouble("ass_Qstart_LQ_Warrant"), data.getDouble("ass_Qstart_LQC_Arreas"), data.getDouble("ass_Qstart_LQC_Warrant"), 0, data.getDouble("ass_Qstart_QDiscont"), data.getDouble("ass_Qstart_QPay"));
                    ripHolder.setThisYearCurrentArrias(ripHolder.getThisYearCurrentArrias() + data.getDouble("ass_Qstart_LQC_Arreas"));
                    ripHolder.setThisYearCurrentWarrant(ripHolder.getThisYearCurrentWarrant() + data.getDouble("ass_Qstart_LQC_Warrant"));
                    if (ass_qstart_status == 1) {
                        ripHolder.setLasatYearArriars(data.getDouble("ass_Qstart_LY_Arreas"), data.getDouble("ass_Qstart_LYC_Arreas"), 0);
                        ripHolder.setLastYearWarrant(data.getDouble("ass_Qstart_LY_Warrant"), data.getDouble("ass_Qstart_LYC_Warrant"), 0);

                    }
                    ripHolder.setQ2(data.getDouble("ass_Qstart_LQ_Arreas"), data.getDouble("ass_Qstart_LQ_Warrant"), data.getDouble("ass_Qstart_LQC_Arreas"), data.getDouble("ass_Qstart_LQC_Warrant"), 0, 0, 0);
                }

                if (ass_qstart_quaterNumber == 4) {
                    ripHolder.setQ3(data.getDouble("ass_Qstart_LQ_Arreas"), data.getDouble("ass_Qstart_LQ_Warrant"), data.getDouble("ass_Qstart_LQC_Arreas"), data.getDouble("ass_Qstart_LQC_Warrant"), 0, data.getDouble("ass_Qstart_QDiscont"), data.getDouble("ass_Qstart_QPay"));
                    ripHolder.setThisYearCurrentArrias(ripHolder.getThisYearCurrentArrias() + data.getDouble("ass_Qstart_LQC_Arreas"));
                    ripHolder.setThisYearCurrentWarrant(ripHolder.getThisYearCurrentWarrant() + data.getDouble("ass_Qstart_LQC_Warrant"));
                    if (ass_qstart_status == 1) {
                        ripHolder.setLasatYearArriars(data.getDouble("ass_Qstart_LY_Arreas"), data.getDouble("ass_Qstart_LYC_Arreas"), 0);
                        ripHolder.setLastYearWarrant(data.getDouble("ass_Qstart_LY_Warrant"), data.getDouble("ass_Qstart_LYC_Warrant"), 0);

                    }
                    ripHolder.setQ3(data.getDouble("ass_Qstart_LQ_Arreas"), data.getDouble("ass_Qstart_LQ_Warrant"), data.getDouble("ass_Qstart_LQC_Arreas"), data.getDouble("ass_Qstart_LQC_Warrant"), 0, 0, 0);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }


        return ripHolder;

    }

    @FXML
    void getWarrantLatter(MouseEvent event) {
        System.out.println(" Warrant Latter ");

        Date systemDate = GetInstans.getQuater().getSystemDate();

        System.out.println(systemDate);

        Calendar c = Calendar.getInstance();
        c.setTime(systemDate);

        c.add(Calendar.DATE, 20);

        Date currentDatePlusOne = c.getTime();

        System.out.println(currentDatePlusOne);

        String format = new SimpleDateFormat("yyyy-MM-dd").format(currentDatePlusOne);

        System.out.println(format);


        String ss = "";
        for (HolderAssess holderAssess : List) {
            ss += ",";
            ss += holderAssess.getIdAssess();
        }
        ss = ss.substring(1);

        System.out.println(ss);
        modle.GetInstans.getAssessReport().getWarrantLatter(ss, format, GetInstans.getQuater().getCurrentYear(), GetInstans.getQuater().getCurrentQuater());


    }


}
