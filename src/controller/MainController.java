package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;

import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import conn.DB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import modle.CheckConnection;
import modle.GetInstans;
import modle.KeyVal;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.Systemdate;

/**
 * FXML Controller class
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane anchor_main;
    @FXML
    private JFXHamburger hambuger;
    @FXML
    private Label lbl_logUser;
    @FXML
    private JFXButton btn_min;
    @FXML
    private JFXButton btn_exit;
    @FXML
    private AnchorPane container;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private Text txt_systemdate;
    @FXML
    private Label txt_name;

    @FXML
    private WebView wv_main;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadName();
        try {
            VBox box = FXMLLoader.load(getClass().getResource("/view/Box.fxml"));
            drawer.setSidePane(box);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HamburgerNextArrowBasicTransition hamt = new HamburgerNextArrowBasicTransition(hambuger);

        hamt.setRate(1);
        hambuger.setStyle("-fx-ripple-color:WHITE");
        hambuger.addEventHandler(MouseEvent.MOUSE_CLICKED,
                (e) -> {

                    hamt.setRate(hamt.getRate() * -1);
                    hamt.play();
                    //====
                    if (drawer.isShown()) {
                        drawer.close();
                    } else {
                        drawer.open();
                    }
                    //====
                    Runtime.getRuntime().gc();
                }
        );
        modle.StaticViews.setContainer(container);
        lbl_logUser.setText(modle.GetInstans.getUser_Login().getLogUserName(modle.StaticViews.getLogUser().getIdUser()));


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    systemDate();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
                while (true) {


//                    boolean b = CheckConnection.checkInternet();
//                    boolean local = CheckConnection.checkLocal();

//                    if (!b) {
//                        modle.Allert.notificationConnection("Connection", "Error");
//                        Platform.exit();
//                        System.exit(0);
//                    }


                    try {
                        systemDate();
                        Thread.sleep(30000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                    }
                }
            }
        }).start();


        modle.StaticViews.setMc(this);

        drawer.close();

        String homepage = KeyVal.getVal("homepage");
        System.out.println(homepage);
        wv_main.getEngine().load(homepage);


    }

    public void setText() {
        System.out.println("Click");
    }

    @FXML
    private void minimize(ActionEvent event) {
        Stage st = (Stage) btn_min.getParent().getScene().getWindow();
        st.setIconified(true);
    }

    @FXML
    private void exit(ActionEvent event) {
        new modle.user.User_Login().logout(modle.StaticViews.getLogUser().getIdUser(), modle.StaticViews.getLog().getIdUserLogin());
        System.out.println("EXIT");
        Platform.exit();
        System.exit(0);
    }


    public void systemDate() {
        try {

            Date dbDate = GetInstans.getQuater().getSystemDate();
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String db = format.format(dbDate);
            String today = format.format(date);
            if (db.equals(today)) {
                txt_systemdate.setFill(Color.WHITE);
                txt_systemdate.setText(db);
            } else {
                txt_systemdate.setFill(Color.RED);
                txt_systemdate.setText(db + "  : System Date Doesn't match please check ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void mouseReleased(MouseEvent event) {
    }

    @FXML
    private void mouseDragged(MouseEvent event) {
    }

    @FXML
    private void mousePressed(MouseEvent event) {
    }

    public int close = 1;

    @FXML
    private void cloaseDrawerEnterd(MouseEvent event) {
        if (close == 1) {
            if (!drawer.isShown()) {
                drawer.open();
            }
        }
    }

    @FXML
    private void closeDrawer(MouseEvent event) {
    }

    private void onMouseExited(MouseEvent event) {


    }

    @FXML
    private void onMouseExitedFromDraver(MouseEvent event) {
//        drawer.open();
    }

    @FXML
    private void onMouseEnterToDraver(MouseEvent event) {
    }


    public void loadName() {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "fxkeyvalue.id,\n" +
                    "fxkeyvalue.`key`,\n" +
                    "fxkeyvalue.`value`\n" +
                    "FROM `fxkeyvalue` WHERE fxkeyvalue.key = 'name';\n");

            if (data.last()) {
                String string = data.getString("fxkeyvalue.value");
                txt_name.setText(string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeTitle(String title) {
        txt_name.setText(title);

    }


}
