package modle.Payment;

import conn.DB;
import controller.assess.DayendController;
import modle.asses.DayEndProcess;

import javax.naming.ldap.Control;
import javax.sound.midi.ControllerEventListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RiBillComplete {

    modle.asses.DayEndProcess de = null;
    SimpleDateFormat sdf = null;

    public RiBillComplete() {
        super();
        de = new DayEndProcess();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("constructore call");
    }

    public void completeRi(String idRecit) {

        System.out.println(idRecit);
        System.out.println("ribill call");

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
                    "WHERE idRibill ='" + idRecit + "'");

            if (data.last()) {

                System.out.println("Get Ricit");
                String bill_no = data.getString("bill_no");
                String idrecit = data.getString("idRibill");

                System.out.println("ri appp id = " + idrecit);

                ResultSet data1 = DB.getData("SELECT\n" +
                        "ribill.idRibill,\n" +
                        "ribill.billdate,\n" +
                        "ribill.billtotal,\n" +
                        "ribill.userid,\n" +
                        "ribill.ribill_status,\n" +
                        "ribill.bill_no,\n" +
                        "ribill_list.idRibill_lsit,\n" +
                        "ribill_list.idRicit,\n" +
                        "ribill_list.ricitstatus,\n" +
                        "ribill_list.ricittotal,\n" +
                        "ribill_list.Ribill_idRibill\n" +
                        "FROM\n" +
                        "ribill\n" +
                        "INNER JOIN ribill_list ON ribill_list.Ribill_idRibill = ribill.idRibill\n" +
                        "WHERE\n" +
                        "ribill.idRibill = '" + idrecit + "'");

                System.out.println("query complte");
                String idri = "";
                int idRicit = 0;
                while (data1.next()) {
                    idRicit = data1.getInt("ribill_list.idRicit");
                    Date parse = sdf.parse(data1.getString("ribill.billdate"));
                    idri = data1.getString("ribill.idRibill");


                    conn.DB.setData("UPDATE `ribill_list`\n" +
                            "SET \n" +
                            " `ricitstatus` = '1' \n" +
                            "WHERE\n" +
                            "\t`idRibill_lsit` = '" + data1.getInt("idRibill_lsit") + "'");

                    conn.DB.setData("UPDATE `ribill`\n" +
                            "SET `ribill_status` = '1'\n" +
                            "WHERE\n" +
                            "\t`idRibill` = '" + data1.getInt("idRibill") + "'");

                    conn.DB.setData("UPDATE `receipt`\n" +
                            "SET\n" +
                            " `receipt_status` = '1',\n" +
                            " `ribno` = '"+bill_no+"', receipt_time='"+modle.Time.getTeime()+"'\n" +
                            "WHERE\n" +
                            "\t`idReceipt` = '"+idRicit+"'");
                }

                conn.DB.setData("UPDATE receipt SET receipt_status = '1' WHERE idReceipt = " + idRecit);

                System.out.println("loop end");






            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }



    }

    public void completeAssessmentBill(int idReicit) {
        de.dayEndProcessForOne(idReicit, modle.GetInstans.getQuater().getSystemDate());
    }

    public void ribilRePrint(String idRibil) {
        System.out.println();
        try {


            ResultSet data1 = DB.getData("SELECT\n" +
                    "receipt.recept_applicationId\n" +
                    "FROM `receipt`\n" +
                    "WHERE\n" +
                    "receipt.idReceipt = '" + idRibil + "'");

            int xx = 0;

            if(data1.last()){
                xx = data1.getInt("recept_applicationId");
            }

            System.out.println(xx+"  00000000000000000000000000000");


            String query = "SELECT\n" +
                    "ribill_list.idRibill_lsit,\n" +
                    "ribill_list.idRicit,\n" +
                    "ribill_list.ricitstatus,\n" +
                    "ribill_list.ricittotal,\n" +
                    "ribill_list.Ribill_idRibill,\n" +
                    "receipt.receipt_print_no,\n" +
                    "receipt.recept_applicationId,\n" +
                    "assessment.assessment_no,\n" +
                    "receipt.chque_no,\n" +
                    "receipt.cheack,\n" +
                    "receipt.cesh\n" +
                    "FROM\n" +
                    "ribill_list\n" +
                    "INNER JOIN receipt ON receipt.idReceipt = ribill_list.idRicit\n" +
                    "INNER JOIN assessment ON assessment.idAssessment = receipt.recept_applicationId\n" +
                    "WHERE\n" +
                    "ribill_list.Ribill_idRibill = " + xx;

            ResultSet data = DB.getData(query);
            String rinos = "";
            String cheque = "";
            while (data.next()) {
                rinos += data.getString("receipt_print_no")+" - "+data.getString("assessment_no") + " : " + (data.getDouble("receipt.cesh") + data.getDouble("receipt.cheack")) + " ,  ";
                if (data.getString("chque_no") != null) {
                    cheque += data.getString("chque_no") + ",  ";
                }

            }
            System.out.println(rinos +" \n"+ "");
            System.out.println(idRibil + "    ID ri Bill");
            modle.GetInstans.getAssessReport().RiBill(xx + "", rinos, cheque,false);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


}
