package modle.book;

import conn.DB;
import controller.payment.UpdateStatus;
import javafx.geometry.Pos;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Recipt {

    private static Connection getConnection() {
        try {
            return ((SessionFactoryImplementor) conn.NewHibernateUtil.getSessionFactory()).getConnectionProvider().getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static int insertReciptWithoutNo(int idAppCat, int applicationID, double check, double cash, double total, String date, String chno, String chdate, int chbank, int office, int acoount, int user) {

        int x = 0;
        String rq = "";

        rq = "INSERT INTO `receipt` (\n" +

                "\t`Application_Catagory_idApplication_Catagory`,\n" +
                "\t`recept_applicationId`,\n" +
                "\t`cheack`,\n" +
                "\t`cesh`,\n" +
                "\t`receipt_total`,\n" +
                "\t`receipt_day`,\n" +
                "\t`receipt_status`,\n" +
                "\t`receipt_syn`,\n" +
                "\t`chque_no`,\n" +
                "\t`chque_date`,\n" +
                "\t`chque_bank`,\n" +
                "\t`office_idOffice`,\n" +
                "\t`receipt_account_id`,\n" +
                "\t`receipt_user_id`\n" +

                ")\n" +
                "VALUES\n" +
                "\t(\n" +
                "'" + idAppCat + "',\n" +
                "'" + applicationID + "',\n" +
                "'" + check + "',\n" +
                "'" + cash + "',\n" +
                "'" + total + "',\n" +
                "'" + date + "',\n" +
                "0,\n" +
                "1,\n" +
                "'" + chno + "',\n";
        if (chdate != null) {
            rq += "'" + chdate + "',\n";
        } else {
            rq += "" + chdate + ",\n";
        }
        rq += "'" + chbank + "',\n" +
                "'" + office + "',\n" +
                "'" + acoount + "',\n" +
                "'" + user + "' \n" +
                ");";


        try {
            conn.DB.setData(rq);
            ResultSet data = DB.getData("SELECT\n" +
                    "\treceipt.idReceipt\n" +
                    "FROM\n" +
                    "\t`receipt`\n" +
                    "WHERE\n" +
                    "\treceipt.Application_Catagory_idApplication_Catagory = '" + idAppCat + "'\n" +
                    "AND receipt.recept_applicationId = '" + applicationID + "'\n" +
                    "AND receipt.receipt_print_no IS NULL\n" +
                    "ORDER BY\n" +
                    "\treceipt.idReceipt DESC\n" +
                    "LIMIT 1");

            if (data.last()) {
                x = data.getInt("idReceipt");
            }
            return x;
        } catch (Exception e) {
            e.printStackTrace();
            return x;
        }
    }


    public static void updateReciptNo(int idRecipt, int idAppCat, String text) {
        String qu = "SELECT\n" +
                "MAX(receipt.oder) as max\n" +
                "FROM\n" +
                "receipt\n" +
                "WHERE\n" +
                "Application_Catagory_idApplication_Catagory = " + idAppCat;
        try {
            ResultSet max = DB.getData(qu);
            int x = 0;
            if (max.next()) {
                x = max.getInt("max");
            }
            int i = x + 1;
            text += i;
            System.out.println(text);
            conn.DB.setData("UPDATE `receipt`\n" +
                    "SET \n" +
                    " `receipt_print_no` = '" + text + "',\n" +
                    " `oder` = '" + i + "',\n" +
                    " `receipt_status` = '1'\n" +
                    "WHERE\n" +
                    "\t(`idReceipt` = '" + idRecipt + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void waterRecipt(HashMap hm, boolean print) {
        // System.out.println("print");
        try {
            String path = "C:\\Ultimate\\Report\\water\\water.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = hm;
            getConnection().commit();
            JasperPrint jp = JasperFillManager.fillReport(jr, param, getConnection());
            if (print) {
                JasperPrintManager.printReport(jp, false);
            } else {
                JasperViewer.viewReport(jp, false);
            }

        } catch (Exception jRException) {
            //       modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "getBook", "modle.assess.AssessReport");
            jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }


    public static void bookingRecipt(HashMap hm, boolean print) {
        try {
            String path = "C:\\Ultimate\\Report\\booking\\booking.jrxml";// IN SYSTEM
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap param = hm;
            getConnection().commit();
            JasperPrint jp = JasperFillManager.fillReport(jr, param, getConnection());
            if (print) {
                JasperPrintManager.printReport(jp, false);
            } else {
                JasperViewer.viewReport(jp, false);
            }

        } catch (Exception jRException) {
            //       modle.ErrorLog.writeLog(jRException.getMessage(), "AssessReport", "getBook", "modle.assess.AssessReport");
            jRException.printStackTrace();
            Notifications.create()
                    .title("Warning")
                    .text("Can not generate report. Something went wrong.\n(" + jRException.getMessage() + ")")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT).showWarning();
        }
    }

    public static void genarateBookingRecipt(String idRecipt, boolean print) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "receipt.idReceipt,\n" +
                    "book_date.idbook_date,\n" +
                    "book_date.book_idbook,\n" +
                    "book_date.book_date_day,\n" +
                    "book_date.book_date_start,\n" +
                    "book_date.book_date_end,\n" +
                    "book_date.book_date_amount,\n" +
                    "book_date.book_date_diposit,\n" +
                    "receipt.recept_applicationId,\n" +
                    "book.book_book_status\n" +
                    "FROM\n" +
                    "receipt\n" +
                    "INNER JOIN book ON book.idbook = receipt.recept_applicationId\n" +
                    "INNER JOIN book_date ON book_date.book_idbook = book.idbook\n" +
                    "WHERE\n" +
                    "receipt.Application_Catagory_idApplication_Catagory = 10 AND\n" +
                    "receipt.idReceipt = " + idRecipt);
            String dis = "";
            while (data.next()) {
                int book_book_status = data.getInt("book_book_status");
                if (book_book_status == 1) {
                    dis += " " + data.getString("book_date_day") + " ";
                }

                if (book_book_status == 2) {
                    dis += " " + data.getString("book_date_day") + " " + data.getString("book_date_start") + "-" + data.getString("book_date_end");
                }
            }
            HashMap<String, String> hm = new HashMap<>();
            hm.put("idRecipt", idRecipt);
            hm.put("description", dis);
            bookingRecipt(hm, print);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    public static void addToPrasa(int idrecipt) {
        try {
            String qq = "SELECT\n" +
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
                    "receipt.receipt_time\n" +
                    "FROM\n" +
                    "receipt WHERE idReceipt = " + idrecipt;


            String receipt_day = null;
            String receipt_print_no = null;
            int idReceipt = 0;
            int receipt_account_id = 0;
            int receipt_user_id = 0;
            int recept_applicationId = 0;


            ResultSet data1 = DB.getData(qq);
            if (data1.last()) {
                receipt_day = data1.getString("receipt_day");
                receipt_print_no = data1.getString("receipt_print_no");
                idReceipt = data1.getInt("idReceipt");
                receipt_account_id = data1.getInt("receipt_account_id");
                receipt_user_id = data1.getInt("receipt_user_id");
                recept_applicationId = data1.getInt("recept_applicationId");

            }

            String query = "SELECT\n" +
                    "book.idbook,\n" +
                    "book.book_date,\n" +
                    "book.customer_idCustomer,\n" +
                    "book.book_amount,\n" +
                    "book.book_diposit,\n" +
                    "book.book_vat,\n" +
                    "book.book_nbt,\n" +
                    "book.book_stamp,\n" +
                    "book.book_total,\n" +
                    "book.book_cash,\n" +
                    "book.book_chque,\n" +
                    "book.book_chque_no,\n" +
                    "book.book_book_pay_status,\n" +
                    "book.book_book_status,\n" +
                    "book.book_idRecipt,\n" +
                    "book.book_reson_idbook_reson,\n" +
                    "book.book_place_idbook_place,\n" +
                    "book.book_idUser,\n" +
                    "book.book_pay_type,\n" +
                    "book_reson.idbook_reson,\n" +
                    "book_reson.book_reson_name,\n" +
                    "book_reson.book_reson_dayprice,\n" +
                    "book_reson.book_reson_houreprice,\n" +
                    "book_reson.book_reson_idVote,\n" +
                    "book_reson.book_reson_idAccount,\n" +
                    "book_reson.book_reson_status,\n" +
                    "book_reson.book_place_idbook_place,\n" +
                    "book_reson.book_reson_diposit_vote,\n" +
                    "book_reson.book_reson_diposit_amount\n" +
                    "FROM\n" +
                    "book\n" +
                    "INNER JOIN book_reson ON book.book_reson_idbook_reson = book_reson.idbook_reson\n" +
                    "WHERE\n" +
                    "book.idbook = " + recept_applicationId;


            ResultSet data = DB.getData(query);
            if (data.last()) {

                double book_amount = data.getDouble("book_amount");
                int book_reson_idVote = data.getInt("book_reson_idVote");
                modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, idReceipt, book_reson_idVote, receipt_account_id, book_amount, receipt_user_id, recept_applicationId, 10, 1);


                double book_diposit = data.getDouble("book_diposit");
                int book_reson_diposit_vote = data.getInt("book_reson_diposit_vote");
                modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, idReceipt, book_reson_diposit_vote, receipt_account_id, book_diposit, receipt_user_id, recept_applicationId, 10, 1);

                double book_vat = data.getDouble("book_vat");
                int vatid = 33;
                modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, idReceipt, vatid, receipt_account_id, book_vat, receipt_user_id, recept_applicationId, 10, 1);

                double book_nbt = data.getDouble("book_nbt");
                int nbt = 34;
                modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, idReceipt, nbt, receipt_account_id, book_nbt, receipt_user_id, recept_applicationId, 10, 1);

                double book_stamp = data.getDouble("book_stamp");
                int stamp = 35;
                modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, idReceipt, stamp, receipt_account_id, book_stamp, receipt_user_id, recept_applicationId, 10, 1);

                double book_cash = data.getDouble("book_cash");
                int cash = 65;
                modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, idReceipt, cash, receipt_account_id, book_cash, receipt_user_id, recept_applicationId, 10, 1);

                double book_chque = data.getDouble("book_chque");
                int check = 66;
                modle.Payment.CompleteAcc.insertToAccount(receipt_day, receipt_print_no, idReceipt, check, receipt_account_id, book_chque, receipt_user_id, recept_applicationId, 10, 1);

                if (book_cash > 0) {
                    UpdateStatus.updateRecipt(idReceipt + "", 1, 0, 1, book_cash); // update Recipt Status
                }
                if (book_chque > 0) {
                    UpdateStatus.updateRecipt(idReceipt + "", 1, 0, 1, book_chque); // update Recipt Status
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
