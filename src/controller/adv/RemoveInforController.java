package controller.adv;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import conn.DB;
import conn.NewHibernateUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import modle.adv.StaticBaduAdv;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.AdvAdvertising;
import view.buttons.Adv_removeController;

import java.net.ConnectException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Created by Ranga on 2019-03-02.
 */
public class RemoveInforController implements Initializable {


    @FXML
    private Text txt_boardNumber;


    @FXML
    private JFXTextField txt_back;

    @FXML
    private JFXTextArea txt_comment;

    @FXML
    private JFXButton btn_compleet;

    @FXML
    private Text txt_diposit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            int advid = StaticBaduAdv.getAdvApplicationID();

            String quary = "SELECT\n" +
                    "\tadv_advertising.idAdv_Advertising,\n" +
                    "\tadv_advertising.adv_start_date,\n" +
                    "\tadv_advertising.adv_end_date,\n" +
                    "\tadv_advertising.adv_board_no,\n" +
                    "\tadv_advertising.adv_discription,\n" +
                    "\tadv_advertising.adv_qr,\n" +
                    "\tadv_advertising.adv_pt,\n" +
                    "\tadv_advertising.adv_total,\n" +
                    "\tadv_advertising.adv_vat,\n" +
                    "\tadv_advertising.adv_nbt,\n" +
                    "\tadv_advertising.adv_stamp,\n" +
                    "\tadv_advertising.adv_diposit,\n" +
                    "\tadv_advertising.adv_ground_total,\n" +
                    "\tadv_advertising.adv_visiting_price,\n" +
                    "\tadv_advertising.adv_others,\n" +
                    "\tadv_advertising.adv_full_total,\n" +
                    "\tadv_advertising.adv_current_date,\n" +
                    "\tadv_advertising.adv_paid_notpaid,\n" +
                    "\tadv_advertising.adv_status,\n" +
                    "\tadv_advertising.adv_remove_comment,\n" +
                    "\tadv_advertising.adv_syn,\n" +
                    "\tadv_advertising.Customer_idCustomer,\n" +
                    "\tadv_advertising.adv_cheque,\n" +
                    "\tadv_advertising.adv_cash,\n" +
                    "\tadv_advertising.adv_cheque_date,\n" +
                    "\tadv_advertising.adv_cheque_bank,\n" +
                    "\tadv_advertising.adv_cheque_no,\n" +
                    "\tadv_advertising.receipt_idReceipt,\n" +
                    "\tadv_advertising.adv_diposit_status,\n" +
                    "\tadv_advertising.adv_diposit_back,\n" +
                    "\tadv_advertising.adv_diposit_back_date,\n" +
                    "\tadv_advertising.user_idUser\n" +
                    "FROM\n" +
                    "\t`adv_advertising`\n" +
                    "WHERE\n" +
                    "\tidAdv_Advertising =" + advid;


            ResultSet data = DB.getData(quary);
            if (data.last()) {
                txt_boardNumber.setText(data.getString("adv_board_no"));
                txt_diposit.setText(data.getString("adv_diposit"));
                txt_back.setText(data.getString("adv_diposit"));

            }


        } catch (Exception e) {

            e.printStackTrace();

        }


    }

    @FXML
    void clickOnComplete(MouseEvent event) {
        int advid = StaticBaduAdv.getAdvApplicationID();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            double dback  = Double.parseDouble(txt_back.getText());
            AdvAdvertising adv  = (AdvAdvertising) session.load(AdvAdvertising.class, advid);
            adv.setAdvDipositStatus(0);
            adv.setAdvDipositBack(dback);
            adv.setAdvRemoveComment(txt_comment.getText());
            adv.setAdvStatus(0);
            session.update(adv);
            transaction.commit();
            btn_compleet.getScene().getWindow().hide();
        }catch (Exception ex){ transaction.rollback();ex.printStackTrace();}finally {
            session.close();
        }

    }


}
