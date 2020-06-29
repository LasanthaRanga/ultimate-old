package modle.asses;

import conn.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.*;

import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class AmbalangodaPay {
    public static void main(String[] args) {
        AmbalangodaPay ap = new AmbalangodaPay();
        ap.paymethod();
    }


    public void paymethod() {

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ambalan.date2,\n" +
                    "ambalan.idAssessment,\n" +
                    "ambalan.total_pay,\n" +
                    "ambalan.cash,\n" +
                    "ambalan.cheque,\n" +
                    "ambalan.cheque_no,\n" +
                    "ambalan.recipt_no,\n" +
                    "ambalan.quater1,\n" +
                    "ambalan.quater2,\n" +
                    "ambalan.quater3,\n" +
                    "ambalan.quater4,\n" +
                    "ambalan.other,\n" +
                    "ambalan.discount\n" +
                    "FROM\n" +
                    "ambalan\n" +
                    "WHERE date2 = '22-02-2019'");

            while (data.next()){
                int idAssessment = data.getInt("idAssessment");
                double total_pay = data.getDouble("total_pay");
                double cash = data.getDouble("cash");
                double cheque = data.getDouble("cheque");
                String cheque_no = data.getString("cheque_no");
                String recipt_no = data.getString("recipt_no");

                int quater1 = data.getInt("quater1");
                int quater2 = data.getInt("quater2");
                int quater3 = data.getInt("quater3");
                int quater4 = data.getInt("quater4");

                double dicount = data.getDouble("discount");

                System.out.println(recipt_no);


                AmbalangodaObject a = new AmbalangodaObject();
                a.cal(idAssessment);
                a.WarrantArrirasCal(total_pay,0);
                a.pay(recipt_no,cash,cheque,cheque_no,quater1,quater2,quater3,quater4,dicount);




            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }





}
