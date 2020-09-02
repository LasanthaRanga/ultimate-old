package modle.asses;

import conn.DB;
import controller.assess.AssDelete;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ranga Rathnayake on 2020-08-27.
 */
public class AssDeleteRecipt {

    AssDelete ad;

    public AssDeleteRecipt(AssDelete ad) {
        this.ad = ad;
        int currentQuater = new Quater().getCurrentQuater();
    }

    public void getReciptData(String reciptno) {
        try {
            int quater_number = 0;

            ResultSet data2 = DB.getData("SELECT\n" +
                    "ass_process.quater_number\n" +
                    "FROM\n" +
                    "ass_process\n" +
                    "ORDER BY\n" +
                    "ass_process.idProcess DESC\n" +
                    "LIMIT 1");

            if (data2.last()) {
                quater_number = data2.getInt("quater_number");
            }


            ResultSet data = DB.getData("SELECT\n" +
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
                    "receipt.receipt_time,\n" +
                    "receipt.income_expense,\n" +
                    "receipt.cus_id,\n" +
                    "receipt.cross_recipt_or_voucher,\n" +
                    "receipt.pay_type,\n" +
                    "receipt.amount,\n" +
                    "ass_payment.idass_Payment,\n" +
                    "ass_payment.ass_Payment_Q_Number,\n" +
                    "ass_payment.ass_Payment_ThisYear,\n" +
                    "ass_payment.ass_Payment_date,\n" +
                    "ass_payment.ass_Payment_LY_Arrears,\n" +
                    "ass_payment.ass_Payment_LY_Warrant,\n" +
                    "ass_payment.ass_Payment_fullTotal,\n" +
                    "ass_payment.ass_Payment_idUser,\n" +
                    "ass_payment.Assessment_idAssessment,\n" +
                    "ass_payment.Receipt_idReceipt,\n" +
                    "ass_payment.ass_nature_idass_nature,\n" +
                    "ass_payment.ass_allocation_idass_allocation,\n" +
                    "ass_payment.ass_Payment_goto_debit,\n" +
                    "ass_payment.ass_Payment_Status,\n" +
                    "ass_payment.ass_cash,\n" +
                    "ass_payment.ass_check,\n" +
                    "ass_payment.ass_check_no,\n" +
                    "ass_payment.ass_bank,\n" +
                    "ass_payment.pay_user_id,\n" +
                    "ass_payment.cd_balance,\n" +
                    "ass_payment.office_idOffice\n" +
                    "FROM\n" +
                    "receipt\n" +
                    "INNER JOIN ass_payment ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                    "WHERE\n" +
                    "receipt.receipt_print_no = '" + reciptno + "'");

            if (data.last()) {
                int idReceipt = data.getInt("idReceipt");

                int receipt_status = data.getInt("receipt_status");
                int ass_payment_status = data.getInt("ass_Payment_Status");
                int assessment_idAssessment = data.getInt("Assessment_idAssessment");


                if (receipt_status == 1 && ass_payment_status == 1) {

                    ResultSet data1 = DB.getData("SELECT\n" +
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
                            "receipt.receipt_time,\n" +
                            "receipt.income_expense,\n" +
                            "receipt.cus_id,\n" +
                            "receipt.cross_recipt_or_voucher,\n" +
                            "receipt.pay_type,\n" +
                            "receipt.amount,\n" +
                            "ass_payment.idass_Payment,\n" +
                            "ass_payment.ass_Payment_Q_Number,\n" +
                            "ass_payment.ass_Payment_ThisYear,\n" +
                            "ass_payment.ass_Payment_date,\n" +
                            "ass_payment.ass_Payment_LY_Arrears,\n" +
                            "ass_payment.ass_Payment_LY_Warrant,\n" +
                            "ass_payment.ass_Payment_fullTotal,\n" +
                            "ass_payment.ass_Payment_idUser,\n" +
                            "ass_payment.Assessment_idAssessment,\n" +
                            "ass_payment.Receipt_idReceipt,\n" +
                            "ass_payment.ass_nature_idass_nature,\n" +
                            "ass_payment.ass_allocation_idass_allocation,\n" +
                            "ass_payment.ass_Payment_goto_debit,\n" +
                            "ass_payment.ass_Payment_Status,\n" +
                            "ass_payment.ass_cash,\n" +
                            "ass_payment.ass_check,\n" +
                            "ass_payment.ass_check_no,\n" +
                            "ass_payment.ass_bank,\n" +
                            "ass_payment.pay_user_id,\n" +
                            "ass_payment.cd_balance,\n" +
                            "ass_payment.office_idOffice\n" +
                            "FROM\n" +
                            "receipt\n" +
                            "INNER JOIN ass_payment ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                            "WHERE\n" +
                            "receipt.recept_applicationId = '" + assessment_idAssessment + "' AND\n" +
                            "receipt.Application_Catagory_idApplication_Catagory = 2\n" +
                            "ORDER BY\n" +
                            "receipt.idReceipt DESC\n" +
                            "LIMIT 1");

                    if (data1.last()) {

                        int idReceipt1 = data1.getInt("idReceipt");
                        int ass_payment_q_number = data1.getInt("ass_Payment_Q_Number");
                        String ass_payment_date = data1.getString("ass_Payment_date");
                        int idass_payment = data.getInt("idass_Payment");
                        double ass_payment_ly_arrears = data1.getDouble("ass_Payment_LY_Arrears");
                        double ass_payment_ly_warrant = data1.getDouble("ass_Payment_LY_Warrant");
                        double ass_payment_goto_debit = data1.getDouble("ass_Payment_goto_debit");
                        double cd_balance = data1.getDouble("cd_balance");


                        ResultSet data3 = DB.getData("SELECT\n" +
                                "ass_payto.idass_payto,\n" +
                                "ass_payto.ass_payto_Qno,\n" +
                                "ass_payto.ass_payto_warrant,\n" +
                                "ass_payto.ass_payto_arrears,\n" +
                                "ass_payto.ass_payto_qvalue,\n" +
                                "ass_payto.ass_payto_discount,\n" +
                                "ass_payto.ass_payto_discount_rate,\n" +
                                "ass_payto.ass_Payment_idass_Payment,\n" +
                                "ass_payto.ass_payto_status\n" +
                                "FROM\n" +
                                "ass_payto\n" +
                                "WHERE\n" +
                                "ass_payto.ass_Payment_idass_Payment = " + idass_payment);


                        if (idReceipt == idReceipt1) {
                            if (quater_number == ass_payment_q_number) {
                                this.ad.idRecipt.setText(idReceipt + "");
                                this.ad.btn_delete.setDisable(false);
                                HoldPay hp = new HoldPay();
                                hp.setIdAss(assessment_idAssessment);
                                hp.setIdPayment(idass_payment);
                                hp.setQn(quater_number);
                                hp.setLya(ass_payment_ly_arrears);
                                hp.setLyw(ass_payment_ly_warrant);
                                hp.setGotodebit(ass_payment_goto_debit);
                                hp.setCdbal(cd_balance);
                                hp.setPdate(ass_payment_date);
                                ArrayList<HoldPayTo> hpt = new ArrayList<>();

                                while (data3.next()) {
                                    int ass_payto_qno = data3.getInt("ass_payto_Qno");
                                    double ass_payto_arrears = data3.getDouble("ass_payto_arrears");
                                    double ass_payto_warrant = data3.getDouble("ass_payto_warrant");
                                    double ass_payto_qvalue = data3.getDouble("ass_payto_qvalue");
                                    double ass_payto_discount = data3.getDouble("ass_payto_discount");
                                    double ass_payto_discount_rate = data3.getDouble("ass_payto_discount_rate");
                                    HoldPayTo holdPayTo = new HoldPayTo(ass_payto_qno, ass_payto_arrears, ass_payto_warrant, ass_payto_qvalue, ass_payto_discount, ass_payto_discount_rate);
                                    hpt.add(holdPayTo);
                                }
                                hp.setQpaylist(hpt);

                                updateAssQstart(hp);

                            } else {
                                ad.idRecipt.setText("Can not delete this. Quarter End Process Completed");
                            }
                        } else {
                            ad.idRecipt.setText("Can not delete this. This is not the last receipt");
                        }
                    }
                } else {
                    ad.idRecipt.setText("Can not delete this. not day ended");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void deletePs3AndReciptStatusChnage(String idrecipt, int Idpayment, int idAssessment, String date) {
        try {
            conn.DB.setData("DELETE from account_ps_three WHERE report_ricipt_id = '" + idrecipt + "'");

            conn.DB.setData("UPDATE `receipt` SET `receipt_status`=2 WHERE `idReceipt`=" + idrecipt);

            conn.DB.setData("UPDATE `ass_payment` SET `ass_Payment_Status`=2 WHERE `idass_Payment`=" + Idpayment);

            ResultSet data = DB.getData("SELECT ass_payhistry.idass_PayHistry FROM ass_payhistry WHERE ass_payhistry.Assessment_idAssessment= '" + idAssessment + "' " +
                    "AND ass_payhistry.ass_PayHistry_Date='" + date + "' " +
                    "ORDER BY ass_payhistry.idass_PayHistry DESC\n");
            int idass_payHistry = 0;
            if (data.last()) {
                idass_payHistry = data.getInt("idass_PayHistry");
            }
            conn.DB.setData("DELETE from ass_payhistry WHERE idass_PayHistry = '" + idass_payHistry + "'");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAssQstart(HoldPay hp) {

        System.out.println("0000000000000000000000000000000");
        System.out.println(hp.idAss);
        System.out.println(hp.qpaylist.size());

        int currentQuater = new Quater().getCurrentQuater();
        int currentYear = new Quater().getCurrentYear();


        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_qstart.idass_Qstart,\n" +
                    "ass_qstart.ass_Qstart_QuaterNumber,\n" +
                    "ass_qstart.ass_Qstart_process_date,\n" +
                    "ass_qstart.ass_Qstart_LY_Arreas,\n" +
                    "ass_qstart.ass_Qstart_LY_Warrant,\n" +
                    "ass_qstart.ass_Qstart_LYC_Arreas,\n" +
                    "ass_qstart.ass_Qstart_LYC_Warrant,\n" +
                    "ass_qstart.ass_Qstart_LQ_Arreas,\n" +
                    "ass_qstart.ass_Qstart_LQC_Arreas,\n" +
                    "ass_qstart.ass_Qstart_LQ_Warrant,\n" +
                    "ass_qstart.ass_Qstart_LQC_Warrant,\n" +
                    "ass_qstart.ass_Qstart_HaveToQPay,\n" +
                    "ass_qstart.ass_Qstart_QPay,\n" +
                    "ass_qstart.ass_Qstart_QDiscont,\n" +
                    "ass_qstart.ass_Qstart_QTot,\n" +
                    "ass_qstart.ass_Qstart_FullTotal,\n" +
                    "ass_qstart.ass_Qstart_status,\n" +
                    "ass_qstart.Assessment_idAssessment,\n" +
                    "ass_qstart.ass_Qstart_year,\n" +
                    "ass_qstart.process_update_warant,\n" +
                    "ass_qstart.process_update_arrears,\n" +
                    "ass_qstart.process_update_comment,\n" +
                    "ass_qstart.ass_Qstart_tyold_arrias,\n" +
                    "ass_qstart.ass_Qstart_tyold_warant\n" +
                    "FROM\n" +
                    "ass_qstart\n" +
                    "WHERE\n" +
                    "ass_qstart.Assessment_idAssessment = '" + hp.idAss + "' AND\n" +
                    "ass_qstart.ass_Qstart_year = '" + currentYear + "' ");


            while (data.last()) {
                int idass_qstart = data.getInt("idass_Qstart");
                int ass_qstart_status = data.getInt("ass_Qstart_status");
                double ass_qstart_lyc_arreas = data.getDouble("ass_Qstart_LYC_Arreas");
                double ass_qstart_lyc_warrant = data.getDouble("ass_Qstart_LYC_Warrant");
                double ass_qstart_haveToQPay = data.getDouble("ass_Qstart_HaveToQPay");


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("0000000000000000000000000000000");
    }


    class HoldPay {
        int idAss, idPayment, qn;
        double lya, lyw, gotodebit, cdbal;
        String pdate;
        List<HoldPayTo> qpaylist;

        public int getIdAss() {
            return idAss;
        }

        public void setIdAss(int idAss) {
            this.idAss = idAss;
        }

        public int getIdPayment() {
            return idPayment;
        }

        public void setIdPayment(int idPayment) {
            this.idPayment = idPayment;
        }

        public int getQn() {
            return qn;
        }

        public void setQn(int qn) {
            this.qn = qn;
        }

        public double getLya() {
            return lya;
        }

        public void setLya(double lya) {
            this.lya = lya;
        }

        public double getLyw() {
            return lyw;
        }

        public void setLyw(double lyw) {
            this.lyw = lyw;
        }

        public double getGotodebit() {
            return gotodebit;
        }

        public void setGotodebit(double gotodebit) {
            this.gotodebit = gotodebit;
        }

        public double getCdbal() {
            return cdbal;
        }

        public void setCdbal(double cdbal) {
            this.cdbal = cdbal;
        }

        public String getPdate() {
            return pdate;
        }

        public void setPdate(String pdate) {
            this.pdate = pdate;
        }

        public List<HoldPayTo> getQpaylist() {
            return qpaylist;
        }

        public void setQpaylist(List<HoldPayTo> qpaylist) {
            this.qpaylist = qpaylist;
        }
    }

    class HoldPayTo {
        int qn;
        double arrears, warrant, qpay, discount, disrate;

        public HoldPayTo(int qn, double arrears, double warrant, double qpay, double discount, double disrate) {
            this.qn = qn;
            this.arrears = arrears;
            this.warrant = warrant;
            this.qpay = qpay;
            this.discount = discount;
            this.disrate = disrate;
        }

        public int getQn() {
            return qn;
        }

        public double getArrears() {
            return arrears;
        }

        public double getWarrant() {
            return warrant;
        }

        public double getQpay() {
            return qpay;
        }

        public double getDiscount() {
            return discount;
        }

        public double getDisrate() {
            return disrate;
        }


    }

}


