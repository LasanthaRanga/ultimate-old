package modle.other;

import conn.DB;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Ranga on 2019-03-08.
 */
public class PayHistryCheck {
    public static void main(String[] args) {
        getAllPayMortThanOne();
    }

    public static void getAllPayMortThanOne() {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_payhistry.idass_PayHistry,\n" +
                    "ass_payhistry.ass_PayHistry_Qcunt,\n" +
                    "ass_payhistry.ass_PayHistry_year,\n" +
                    "ass_payhistry.ass_PayHistry_Date,\n" +
                    "ass_payhistry.ass_PayHistry_status,\n" +
                    "ass_payhistry.ass_PayHistry_comment,\n" +
                    "ass_payhistry.ass_PayHistry_TotalPayid,\n" +
                    "ass_payhistry.ass_PayHistry_Q1,\n" +
                    "ass_payhistry.ass_PayHistry_Q2,\n" +
                    "ass_payhistry.ass_PayHistry_Q3,\n" +
                    "ass_payhistry.ass_PayHistry_Q4,\n" +
                    "ass_payhistry.ass_PayHistry_Over,\n" +
                    "ass_payhistry.Assessment_idAssessment,\n" +
                    "ass_payhistry.ass_PayHistry_DRQ1,\n" +
                    "ass_payhistry.ass_PayHistry_DRQ2,\n" +
                    "ass_payhistry.ass_PayHistry_DRQ3,\n" +
                    "ass_payhistry.ass_PayHistry_DRQ4,\n" +
                    "ass_payhistry.ass_PayHistry_Q1Status,\n" +
                    "ass_payhistry.ass_PayHistry_Q2Status,\n" +
                    "ass_payhistry.ass_PayHistry_Q3Status,\n" +
                    "ass_payhistry.ass_PayHistry_Q4Status\n" +
                    "FROM\n" +
                    "ass_payhistry\n" +
                    "WHERE\n" +
                    "ass_payhistry.ass_PayHistry_year = 2019 AND\n" +
                    "ass_payhistry.ass_PayHistry_TotalPayid > 0"
            );


            HashSet<Integer> integers = new HashSet<>();
            HashSet<Integer> dup = new HashSet<>();

            while (data.next()) {
                if (!integers.add(data.getInt("Assessment_idAssessment"))) {
                    dup.add(data.getInt("Assessment_idAssessment"));
                }
            }

            HashMap<Integer, saveStatus> hm = new HashMap();

           for (Integer asid : dup) {
//            int asid = 2070;
            String qq = "SELECT\n" +
                    "ass_payhistry.idass_PayHistry,\n" +
                    "ass_payhistry.ass_PayHistry_Qcunt,\n" +
                    "ass_payhistry.ass_PayHistry_year,\n" +
                    "ass_payhistry.ass_PayHistry_Date,\n" +
                    "ass_payhistry.ass_PayHistry_status,\n" +
                    "ass_payhistry.ass_PayHistry_comment,\n" +
                    "ass_payhistry.ass_PayHistry_TotalPayid,\n" +
                    "ass_payhistry.ass_PayHistry_Q1,\n" +
                    "ass_payhistry.ass_PayHistry_Q2,\n" +
                    "ass_payhistry.ass_PayHistry_Q3,\n" +
                    "ass_payhistry.ass_PayHistry_Q4,\n" +
                    "ass_payhistry.ass_PayHistry_Over,\n" +
                    "ass_payhistry.Assessment_idAssessment,\n" +
                    "ass_payhistry.ass_PayHistry_DRQ1,\n" +
                    "ass_payhistry.ass_PayHistry_DRQ2,\n" +
                    "ass_payhistry.ass_PayHistry_DRQ3,\n" +
                    "ass_payhistry.ass_PayHistry_DRQ4,\n" +
                    "ass_payhistry.ass_PayHistry_Q1Status,\n" +
                    "ass_payhistry.ass_PayHistry_Q2Status,\n" +
                    "ass_payhistry.ass_PayHistry_Q3Status,\n" +
                    "ass_payhistry.ass_PayHistry_Q4Status\n" +
                    "FROM `ass_payhistry`\n" +
                    "WHERE\n" +
                    "ass_payhistry.ass_PayHistry_year = 2019 AND\n" +
                    "ass_payhistry.ass_PayHistry_TotalPayid > 0 AND\n" +
                    "ass_payhistry.Assessment_idAssessment =" + asid + "  ORDER BY\n" +
                    "ass_payhistry.idass_PayHistry ASC\n";


            ResultSet data2 = DB.getData(qq);

            while (data2.next()) {

                int q1s = data2.getInt("ass_payhistry.ass_PayHistry_Q1Status");
                int q2s = data2.getInt("ass_payhistry.ass_PayHistry_Q2Status");
                int q3s = data2.getInt("ass_payhistry.ass_PayHistry_Q3Status");
                int q4s = data2.getInt("ass_payhistry.ass_PayHistry_Q4Status");

                saveStatus old = hm.get(asid);

                if (old == null) {
                    saveStatus saveStatus = new saveStatus(1, 1, q3s, q4s);
                    hm.put(asid,saveStatus);
                } else {

                    int idass_payHistry = data2.getInt("idass_PayHistry");


//                    System.out.println("===============" + asid);
                    if(old.getQ1s()>q1s){
                     //  update(idass_payHistry,1);
                        System.out.println(asid);
                        System.out.println("badu awa 1");
                    }
                    if(old.getQ2s()>q2s){
                     //  update(idass_payHistry,2);
                        System.out.println(asid);
                        System.out.println("badu awa 2");
                    }
                    if(old.getQ3s()>q3s){
                   //    update(idass_payHistry,3);
                        System.out.println("badu awa 3");
                        System.out.println(asid);
                    }
                    if(old.getQ4s()>q4s){
                     //   update(idass_payHistry,4);
                        System.out.println("badu awa 4");
                        System.out.println(asid);
                    }
                    saveStatus saveStatus = new saveStatus(q1s, q2s, q3s, q4s);
                    hm.put(asid,saveStatus);
                }


            }


          }


            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void update(int idPayhistry, int idQuater){
        try {
            DB.setData("UPDATE `ass_payhistry`\n" +
                    "SET \n" +
                    " `ass_PayHistry_Q"+idQuater+"Status` = 1\n" +
                    "WHERE\n" +
                    "\t(`idass_PayHistry` = '"+idPayhistry+"')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}


class saveStatus {
    saveStatus(int q1s, int q2s, int q3s, int q4s) {
        this.setQ1s(q1s);
        this.setQ2s(q2s);
        this.setQ3s(q3s);
        this.setQ4s(q4s);

    }

    private static int q1s;
    private static int q2s;
    private static int q3s;
    private static int q4s;

    public static int getQ1s() {
        return q1s;
    }

    public static void setQ1s(int q1s) {
        saveStatus.q1s = q1s;
    }

    public static int getQ2s() {
        return q2s;
    }

    public static void setQ2s(int q2s) {
        saveStatus.q2s = q2s;
    }

    public static int getQ3s() {
        return q3s;
    }

    public static void setQ3s(int q3s) {
        saveStatus.q3s = q3s;
    }

    public static int getQ4s() {
        return q4s;
    }

    public static void setQ4s(int q4s) {
        saveStatus.q4s = q4s;
    }
}