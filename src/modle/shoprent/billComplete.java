package modle.shoprent;

import conn.DB;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class billComplete {

    private Connection getConnection() {
        try {
            return ((SessionFactoryImplementor) conn.NewHibernateUtil.getSessionFactory()).getConnectionProvider().getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void loadShopRentBill(String idRecipt, boolean print) {

        try {


//            ResultSet data = DB.getData("SELECT\n" +
//                    "GROUP_CONCAT(CONCAT( sr_shop_payment.sr_shop_proc_year, '-', w_month.`month` )) as months,\n" +
//                    "sr_shop_payment.sr_receipt_no,\n" +
//                    "sr_shop.sr_shop_no,\n" +
//                    "CONCAT( sr_building.sr_building_name, ',', sr_flow.sr_flow_name ) as shop,\n" +
//                    "receipt.idReceipt\n" +
//                    "FROM\n" +
//                    "sr_shop_payment\n" +
//                    "INNER JOIN w_month ON sr_shop_payment.sr_shop_proc_month = w_month.id\n" +
//                    "INNER JOIN sr_shop ON sr_shop_payment.sr_shop_shop_id = sr_shop.idsr_shop\n" +
//                    "INNER JOIN sr_flow ON sr_shop.sr_flow_idsr_flow = sr_flow.idsr_flow\n" +
//                    "INNER JOIN sr_building ON sr_shop.sr_building_idsr_building = sr_building.idsr_building AND sr_flow.sr_building_idsr_building = sr_building.idsr_building\n" +
//                    "INNER JOIN receipt ON sr_shop_payment.sr_receipt_no = receipt.receipt_print_no\n" +
//                    "WHERE\n" +
//                    "sr_shop_payment.sr_shop_paid_over_pay_bal >= 0 AND\n" +
//                    "sr_shop_payment.sr_shop_paid_arrears_bal = 0 AND\n" +
//                    "sr_shop_payment.sr_shop_paid_rental_tot_bal = 0 AND\n" +
//                    "receipt.idReceipt =  '" + idRecipt + "'\n" +
//                    "ORDER BY sr_shop_payment.sr_shop_paid_id ASC");

            ResultSet data = DB.getData("SELECT\n" +
                    "GROUP_CONCAT(CONCAT( sr_shop_payment.sr_shop_proc_year, '-', w_month.`month` )) AS months,\n" +
                    "sr_shop_payment.sr_receipt_no,\n" +
                    "sr_shop.sr_shop_no,\n" +
                    "CONCAT( sr_building.sr_building_name, ',', sr_flow.sr_flow_name ) AS shop,\n" +
                    "receipt.idReceipt,\n" +
                    "sr_shop_payment.sr_shop_paid_proc_complete\n" +
                    "FROM\n" +
                    "sr_shop_payment\n" +
                    "INNER JOIN w_month ON sr_shop_payment.sr_shop_proc_month = w_month.id\n" +
                    "INNER JOIN sr_shop ON sr_shop_payment.sr_shop_shop_id = sr_shop.idsr_shop\n" +
                    "INNER JOIN sr_flow ON sr_shop.sr_flow_idsr_flow = sr_flow.idsr_flow\n" +
                    "INNER JOIN sr_building ON sr_shop.sr_building_idsr_building = sr_building.idsr_building AND sr_flow.sr_building_idsr_building = sr_building.idsr_building\n" +
                    "INNER JOIN receipt ON sr_shop_payment.sr_receipt_no = receipt.receipt_print_no\n" +
                    "WHERE\n" +
                    "sr_shop_payment.sr_shop_paid_proc_complete = 1 AND\n" +
                    "receipt.idReceipt = '" + idRecipt + "'\n" +
                    "ORDER BY\n" +
                    "sr_shop_payment.sr_shop_paid_id ASC\n");


            String shops = "";
            String months = "";
            while (data.next()) {
                months += data.getString("months");
                shops = data.getString("shop");
            }


            String path = "C:\\Ultimate\\Report\\shoprent\\shopbil.jrxml";// IN SYSTEM
            //    String path = "C:\\Users\\Ranga\\JaspersoftWorkspace\\MyReports\\shopbil.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, String>();
            param.put("idRecipt", idRecipt);
            param.put("months", months);
            param.put("shops", shops);


            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            if (print) {
                JasperPrintManager.printReport(jp, false);
            } else {
                JasperViewer.viewReport(jp, false);
            }


        } catch (Exception jRException) {
            jRException.printStackTrace();
        }


    }

    public void loadShopRentBillView(String idRecipt) {

        try {


            ResultSet data = DB.getData("SELECT\n" +
                    "GROUP_CONCAT(CONCAT( sr_shop_payment.sr_shop_proc_year, '-', w_month.`month` )) as months,\n" +
                    "sr_shop_payment.sr_receipt_no,\n" +
                    "sr_shop.sr_shop_no,\n" +
                    "CONCAT( sr_building.sr_building_name, ',', sr_flow.sr_flow_name ) as shop,\n" +
                    "receipt.idReceipt\n" +
                    "FROM\n" +
                    "sr_shop_payment\n" +
                    "INNER JOIN w_month ON sr_shop_payment.sr_shop_proc_month = w_month.id\n" +
                    "INNER JOIN sr_shop ON sr_shop_payment.sr_shop_shop_id = sr_shop.idsr_shop\n" +
                    "INNER JOIN sr_flow ON sr_shop.sr_flow_idsr_flow = sr_flow.idsr_flow\n" +
                    "INNER JOIN sr_building ON sr_shop.sr_building_idsr_building = sr_building.idsr_building AND sr_flow.sr_building_idsr_building = sr_building.idsr_building\n" +
                    "INNER JOIN receipt ON sr_shop_payment.sr_receipt_no = receipt.receipt_print_no\n" +
                    "WHERE\n" +
                    "sr_shop_payment.sr_shop_paid_over_pay_bal >= 0 AND\n" +
                    "sr_shop_payment.sr_shop_paid_arrears_bal = 0 AND\n" +
                    "sr_shop_payment.sr_shop_paid_rental_tot_bal = 0 AND\n" +
                    "receipt.idReceipt =  '" + idRecipt + "'\n" +
                    "ORDER BY sr_shop_payment.sr_shop_paid_id ASC");
            String shops = "";
            String months = "";
            while (data.next()) {
                months += data.getString("months");
                shops = data.getString("shop");
            }


            String path = "C:\\Ultimate\\Report\\shoprent\\shopbil.jrxml";// IN SYSTEM

            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = new HashMap<String, String>();
            param.put("idRecipt", idRecipt);
            param.put("months", months);
            param.put("shops", shops);


            JasperPrint jp = JasperFillManager.fillReport(jr, param, this.getConnection());
            JasperViewer.viewReport(jp, false);
            //JasperPrintManager.printReport(jp, false);


        } catch (Exception jRException) {
            jRException.printStackTrace();
        }


    }

    public void tradeLicensBill(String idRecipt, boolean print) {

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "tl_pay.tl_pay_detail_tbl_id,\n" +
                    "tl_pay_details.tl_pay_subtype_id,\n" +
                    "tl_pay.tl_ricipt_id,\n" +
                    "tl_pay.idTL_pay\n" +
                    "FROM\n" +
                    "tl_pay\n" +
                    "INNER JOIN tl_pay_details ON tl_pay.tl_pay_detail_tbl_id = tl_pay_details.idTL_pay_details\n" +
                    "WHERE\n" +
                    "tl_pay_details.tl_paid_status = '0' AND\n" +
                    "tl_pay.tl_ricipt_id = '" + idRecipt + "'\n");

            conn.DB.setData("UPDATE `tl_pay` SET `tl_pay_status`='1'  WHERE `tl_ricipt_id`='" + idRecipt + "'");
            conn.DB.setData("UPDATE `receipt` SET `receipt_status`='1'  WHERE `idReceipt`='" + idRecipt + "'");
            conn.DB.setData("UPDATE `account_ps_three` SET `report_status`='1'  WHERE `report_ricipt_id`='" + idRecipt + "'");

            while (data.next()) {
                int tl_pay_detail_tbl_id = data.getInt("tl_pay_detail_tbl_id");

                conn.DB.setData("UPDATE ` tl_cross_tbl_details` \n" +
                        "SET `tl_paid_status` = '1',\n" +
                        "`tl_date` = '' \n" +
                        "WHERE\n" +
                        "\t`tl_pay_details_id` = '" + tl_pay_detail_tbl_id + "'");


            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }


}
