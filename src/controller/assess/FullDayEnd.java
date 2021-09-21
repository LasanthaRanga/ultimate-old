package controller.assess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXProgressBar;
import conn.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import jdk.nashorn.internal.objects.NativeError;
import jxl.write.DateTime;
import modle.GetResultFromUrl;
import modle.KeyVal;
import modle.StaticViews;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class FullDayEnd implements Initializable {

    @FXML
    private TableView<FDE> tbl_de;

    @FXML
    private TableColumn<FDE, JFXCheckBox> col_select;

    @FXML
    private JFXCheckBox ch_all;

    @FXML
    private TableColumn<FDE, String> col_apptype;

    @FXML
    private TableColumn<FDE, String> col_reciptno;

    @FXML
    private TableColumn<FDE, Double> col_total;

    @FXML
    private TableColumn<FDE, ?> col_button;

    @FXML
    private JFXDatePicker dp;
    @FXML
    private JFXButton btn_dayEnd;

    @FXML
    private JFXProgressBar dend_progras;

    @FXML
    private ProgressIndicator pro_to;

    @FXML
    private Text txt_server_messege;

    String processpath = "";
    String server_respond = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String format = simpleDateFormat.format(modle.GetInstans.getQuater().getSystemDate());

        loadAllRecipt(format);

        col_select.setCellValueFactory(new PropertyValueFactory<>("select"));
        col_apptype.setCellValueFactory(new PropertyValueFactory<>("appcat"));
        col_reciptno.setCellValueFactory(new PropertyValueFactory<>("risitNO"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("tot"));


        processpath = KeyVal.getVal("processpath");
        server_respond = KeyVal.getVal("server_respond");


        String text = "Server Is Online";
//        try {
//           text = GetResultFromUrl.getText(server_respond);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//        }
//        System.out.println(text);
//
//        if (text.equals("Server Is Not Responding")) {
//            txt_server_messege.setFill(Color.RED);
//            // btn_dayEnd.setDisable(true);
//        } else {
//            btn_dayEnd.setDisable(false);
//            txt_server_messege.setFill(Color.GREEN);
//        }
        txt_server_messege.setText(text);


    }

    @FXML
    void relesedOnDayEnd(MouseEvent event) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                runDayEnd();
            }

        }).start();
    }


    public void runDayEnd() {

        txt_server_messege.setText("Day End Process Start Please Wait");
        try {
            Date date = new Date();
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

            double size = 0;
            for (FDE fde : List) {
                if (fde.getSelect().isSelected()) {
                    size++;
                }
            }


            double x = 0;
            double i;
            for (FDE fde : List) {
                if (fde.getSelect().isSelected()) {
                    String qu = "INSERT INTO `de` (\n" +
                            "\t`receipt_id`,\n" +
                            "\t`appcat_id`,\n" +
                            "\t`user_id`,\n" +
                            "\t`staus`,\n" +
                            "\t`dayendtime`,\n" +
                            "\t`comment`,`receipt_date` \n" +
                            ")\n" +
                            "VALUES\n" +
                            "\t(\n" +
                            "\t\t'" + fde.getId() + "',\n" +
                            "\t\t'" + fde.getNun() + "',\n" +
                            "\t\t'" + modle.StaticViews.getLogUser().getIdUser() + "',\n" +
                            "\t\t'" + 0 + "',\n" +
                            "\t\t'" + format + "',\n" +
                            "\t\t'Send To Day End','" + fde.getEla() + "'\n" +
                            "\t)";

                    conn.DB.setData(qu);

                    ResultSet data = DB.getData("SELECT\n" +
                            "\tde.idde,\n" +
                            "\tde.receipt_id,\n" +
                            "\tde.appcat_id,\n" +
                            "\tde.user_id,\n" +
                            "\tde.staus,\n" +
                            "\tde.dayendtime,\n" +
                            "\tde.`comment`,\n" +
                            "\tde.receipt_date\n" +
                            "FROM\n" +
                            "\t`de`\n" +
                            "WHERE\n" +
                            "de.staus = 0 AND\n" +
                            "de.receipt_id = " + fde.getId());

                    int idde = 0;
                    if (data.last()) {
                        idde = data.getInt("idde");
                    }

                    ResultSet data1 = DB.getData("SELECT\n" +
                            "receipt.idReceipt,\n" +
                            "receipt.receipt_day\n" +
                            "FROM `receipt`\n" +
                            "WHERE\n" +
                            "receipt.idReceipt = " + fde.getId());


                    String receipt_day = "";

                    if (data1.last()) {
                        receipt_day = data1.getString("receipt_day");
                    }

                    Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(receipt_day);

                    if (new modle.asses.DayEndProcess().dayEndProcessForOne(fde.getId(), parse)) {

                        conn.DB.setData("UPDATE `de`\n"
                                + "SET \n"
                                + " `staus` = '1' \n"
                                + "WHERE\n"
                                + "\t(`idde` = '" + idde + "')");
                    } else {
                        //  conn.DB.setData("DELETE from de WHERE idde = " + idde);
                     //   modle.Allert.notificationWorning("Day End Not Completed", "Please Recheck  -  " + idde);
                        break;
                    }

                    x++;
                    i = x / size;
                    dend_progras.setProgress(i);
                    pro_to.setProgress(i);


                }
            }

            int i1 = DB.setData("DELETE from de WHERE staus = 0");

            if (i1 > 0) {
       //         modle.Allert.notificationWorning("Day End Not Completed", "Please Recheck");
            } else {
       //         modle.Allert.notificationGood("Day End Completed", "Thank You");
            }

            if (dp.getValue() != null) {

                Date selectDate = Date.from(dp.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String day = simpleDateFormat.format(selectDate);
                loadAllRecipt(day);

            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String day = simpleDateFormat.format(new Date());
                loadAllRecipt(day);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        try {
            txt_server_messege.setFill(Color.GREEN);
            txt_server_messege.setText("Process Complete");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    @FXML
    void dpOnAction(ActionEvent event) {
        Date selectDate = Date.from(dp.getValue().atStartOfDay().atZone(ZoneId.of("Asia/Colombo")).toInstant());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(selectDate);
        loadAllRecipt(format);
    }


    ObservableList<FDE> List = FXCollections.observableArrayList();

    public void loadAllRecipt(String day) {
        System.out.println("call day end table");
        Integer officeIdOffice = StaticViews.getLogUser().getOfficeIdOffice();

        String qu = "SELECT\n" +
                "\treceipt.receipt_print_no,\n" +
                "\treceipt.idReceipt,\n" +
                "\treceipt.Application_Catagory_idApplication_Catagory,\n" +
                "\treceipt.recept_applicationId,\n" +
                "\treceipt.cheack,\n" +
                "\treceipt.cesh,\n" +
                "\treceipt.receipt_total,\n" +
                "\treceipt.receipt_day,\n" +
                "\treceipt.receipt_status,\n" +
                "\treceipt.receipt_syn,\n" +
                "\treceipt.chque_no,\n" +
                "\treceipt.chque_date,\n" +
                "\treceipt.chque_bank,\n" +
                "\treceipt.oder,\n" +
                "\treceipt.office_idOffice,\n" +
                "\treceipt.receipt_account_id,\n" +
                "\treceipt.receipt_user_id,\n" +
                "\tapplication_catagory.application_name,\n" +
                "\tapplication_catagory.idApplication_Catagory\n" +
                "FROM\n" +
                "\treceipt\n" +
                "INNER JOIN application_catagory ON receipt.Application_Catagory_idApplication_Catagory = application_catagory.idApplication_Catagory\n" +
                "WHERE\n" +
                "\treceipt.receipt_day = '" + day + "'\n" +
                "AND Application_Catagory_idApplication_Catagory = 2\n" +
                "AND office_idOffice = '" + officeIdOffice + "'\n" +
                "AND receipt.idReceipt NOT IN (\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tde.receipt_id\n" +
                "\t\tFROM\n" +
                "\t\t\tde\n" +
                "\t\tWHERE\n" +
                "\t\t\tde.receipt_date = '" + day + "'\n" +
                "\t)\n" +
                ")\n" +
                "AND receipt.receipt_status = 1";

        List.clear();
        try {
            ResultSet data = DB.getData(qu);
            while (data.next()) {
                List.add(new FDE(data.getInt("idReceipt"), data.getString("application_name"), data.getString("receipt_print_no"), data.getDouble("receipt_total"), data.getInt("Application_Catagory_idApplication_Catagory"), data.getString("receipt_day")));
            }
            tbl_de.setItems(List);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


    @FXML
    void chAllOnAction(ActionEvent event) {
        if (ch_all.isSelected()) {
            for (FDE fde : List) {
                fde.setSelect(true);
            }
        } else {
            for (FDE fde : List) {
                fde.setSelect(false);
            }
        }
    }


    public class FDE {
        private Integer id;
        private JFXCheckBox select;
        private String appcat;
        private String risitNO;
        private Double tot;
        private Integer nun;
        private String ela;

        public FDE(Integer id, String appcat, String risitNO, Double tot, Integer nun, String ela) {
            this.id = id;
            this.select = new JFXCheckBox();
            this.appcat = appcat;
            this.risitNO = risitNO;
            this.tot = tot;
            this.nun = nun;
            this.ela = ela;
        }

        public Integer getId() {
            return id;
        }

        public JFXCheckBox getSelect() {
            return select;
        }

        public String getAppcat() {
            return appcat;
        }

        public String getRisitNO() {
            return risitNO;
        }

        public Double getTot() {
            return tot;
        }

        public Integer getNun() {
            return nun;
        }

        public String getEla() {
            return ela;
        }

        public void setSelect(boolean select) {
            this.select.setSelected(select);
        }


    }


}


