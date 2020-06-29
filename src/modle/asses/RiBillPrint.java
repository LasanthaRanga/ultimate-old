package modle.asses;

import conn.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modle.KeyVal;
import pojo.Receipt;
import pojo.RibillList;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RiBillPrint {


    public void PrintRiBill(int idRibil, boolean print) {
        ObservableList<RibilsHolder> pendingList = FXCollections.observableArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ribill.idRibill,\n" +
                    "ribill.billdate,\n" +
                    "ribill.billtotal,\n" +
                    "ribill.userid,\n" +
                    "ribill.ribill_status,\n" +
                    "ribill.bill_no,\n" +
                    "ribill.office_id,\n" +
                    "ribill.oder,\n" +
                    "`user`.user_full_name,\n" +
                    "office.office_name\n" +
                    "FROM\n" +
                    "ribill\n" +
                    "INNER JOIN `user` ON `user`.idUser = ribill.userid\n" +
                    "LEFT JOIN office ON office.idOffice = ribill.office_id\n" +
                    "WHERE idRibill ='" + idRibil + "'");
            if (data.last()) {
                System.out.println("Get Ricit");
                String bill_no = data.getString("bill_no");
                String idrecit = data.getString("idRibill");
                System.out.println("ri appp id = " + idrecit);
                ResultSet data1 = DB.getData("SELECT\n" +
                        "ribill_list.idRibill_lsit,\n" +
                        "ribill_list.idRicit,\n" +
                        "ribill_list.ricitstatus,\n" +
                        "ribill_list.ricittotal,\n" +
                        "ribill_list.Ribill_idRibill,\n" +
                        "ribill.idRibill,\n" +
                        "ribill.billdate,\n" +
                        "ribill.billtotal,\n" +
                        "ribill.userid,\n" +
                        "ribill.ribill_status,\n" +
                        "ribill.bill_no,\n" +
                        "ribill.office_id,\n" +
                        "ribill.oder,\n" +
                        "assessment.assessment_no,\n" +
                        "receipt.idReceipt,\n" +
                        "receipt.Application_Catagory_idApplication_Catagory,\n" +
                        "receipt.recept_applicationId,\n" +
                        "receipt.receipt_print_no,\n" +
                        "receipt.cheack,\n" +
                        "receipt.cesh,\n" +
                        "receipt.receipt_total,\n" +
                        "receipt.receipt_day,\n" +
                        "receipt.receipt_status,\n" +
                        "receipt.receipt_syn,\n" +
                        "receipt.chque_no,\n" +
                        "receipt.chque_date,\n" +
                        "receipt.chque_bank,\n" +
                        "receipt.oder,\n" +
                        "receipt.office_idOffice,\n" +
                        "receipt.receipt_account_id,\n" +
                        "receipt.receipt_user_id,\n" +
                        "receipt.ribno,\n" +
                        "ass_payment.ass_check_no\n" +
                        "FROM\n" +
                        "ribill_list\n" +
                        "INNER JOIN ribill ON ribill_list.Ribill_idRibill = ribill.idRibill\n" +
                        "INNER JOIN receipt ON receipt.idReceipt = ribill_list.idRicit\n" +
                        "INNER JOIN assessment ON assessment.idAssessment = receipt.recept_applicationId\n" +
                        "INNER JOIN ass_payment ON ass_payment.Assessment_idAssessment = assessment.idAssessment AND ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                        "where idRibill =" + idRibil);
                while (data1.next()) {
                    double cheack = data1.getDouble("cheack");
                    double cesh = data1.getDouble("cesh");
                    String paymethod = "";
                    int pm = 0;
                    if (cesh > 0 && cheack == 0) {
                        pm = 1;
                        paymethod = "cash";
                    } else if (cheack > 0 && cesh == 0) {
                        pm = 2;
                        paymethod = "chque";
                    } else {
                        pm = 3;
                        paymethod = "error";
                    }
                    pendingList.add(new RibilsHolder(data1.getInt("idReceipt"), data1.getString("receipt_print_no"), cesh + cheack, paymethod, pm, data1.getString("ass_payment.ass_check_no"), data1.getString("assessment.assessment_no")));
                }
                String ricitnos = "";
                String chequenos = "";
                String lastChequeNo = "";
                for (RibilsHolder ho : pendingList) {
                    ricitnos += ho.getRiciptNo() + " - " + "No. " + ho.getAssess() + ": " + modle.Round.roundToString(ho.getTotal()) + ",  |  ";
                    if (lastChequeNo.equals(ho.getChequeNo())) {
                    } else {
                        chequenos += ho.getChequeNo() + ", ";
                    }
                    lastChequeNo = ho.getChequeNo();
                }
                modle.GetInstans.getAssessReport().RiBill(idRibil + "", ricitnos, chequenos, print);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


}
