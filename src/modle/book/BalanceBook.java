package modle.book;

import conn.DB;
import modle.StaticViews;

import java.sql.ResultSet;

public class BalanceBook {

    final static int VATID = 33;
    final static int NBTID = 34;
    final static int STAMPID = 35;
    final static int CASH = 65;
    final static int CHEQUE = 66;

    public static void balanceBook(String idRecipt) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
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
                    "receipt.idReceipt,\n" +
                    "receipt.receipt_total,\n" +
                    "receipt.receipt_status,\n" +
                    "book_reson.idbook_reson,\n" +
                    "book_reson.book_reson_idVote,\n" +
                    "book_reson.book_reson_idAccount,\n" +
                    "book_reson.book_reson_diposit_vote,\n" +
                    "receipt.receipt_print_no,\n" +
                    "receipt.Application_Catagory_idApplication_Catagory,\n" +
                    "receipt.recept_applicationId,\n" +
                    "receipt.cheack,\n" +
                    "receipt.cesh,\n" +
                    "receipt.receipt_day,\n" +
                    "receipt.receipt_syn,\n" +
                    "receipt.chque_no,\n" +
                    "receipt.chque_date,\n" +
                    "receipt.chque_bank,\n" +
                    "receipt.oder,\n" +
                    "book_reson.book_reson_name,\n" +
                    "book_reson.book_reson_dayprice,\n" +
                    "book_reson.book_reson_houreprice,\n" +
                    "book_reson.book_reson_status,\n" +
                    "book_reson.book_place_idbook_place,\n" +
                    "book_reson.book_reson_diposit_amount, book.book_idUser\n" +
                    "FROM\n" +
                    "book\n" +
                    "INNER JOIN receipt ON receipt.recept_applicationId = book.idbook\n" +
                    "INNER JOIN book_reson ON book.book_reson_idbook_reson = book_reson.idbook_reson\n" +
                    "WHERE\n" +
                    "receipt.idReceipt = " + idRecipt);


            if (data.last()) {
//                Integer idUser = StaticViews.getLogUser().getIdUser();

                String book_date = data.getString("book_date");
                String receipt_print_no = data.getString("receipt_print_no");
                double cesh = data.getDouble("cesh");
                double cheack = data.getDouble("cheack");

                int idbook = data.getInt("idbook");
                int book_reson_idAccount = data.getInt("book_reson_idAccount");
                int idUser = data.getInt("book.book_idUser");
                int vote = data.getInt("book_reson_idVote");
                double book_amount = data.getDouble("book_amount");
                modle.Payment.CompleteAcc.insertToAccount(book_date, receipt_print_no, Integer.parseInt(idRecipt), vote, book_reson_idAccount, book_amount, idUser, idbook, 10,1);

                int dipvote = data.getInt("book_reson_diposit_vote");
                if (dipvote > 0) {
                    double book_diposit = data.getDouble("book_diposit");
                    modle.Payment.CompleteAcc.insertToAccount(book_date, receipt_print_no, Integer.parseInt(idRecipt), dipvote, book_reson_idAccount, book_diposit, idUser, idbook, 10,1);
                }

                double book_vat = data.getDouble("book_vat");
                if (book_vat > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(book_date, receipt_print_no, Integer.parseInt(idRecipt), VATID, book_reson_idAccount, book_vat, idUser, idbook, 10,1);
                }

                double book_nbt = data.getDouble("book_nbt");
                if (book_nbt > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(book_date, receipt_print_no, Integer.parseInt(idRecipt), NBTID, book_reson_idAccount, book_nbt, idUser, idbook, 10,1);
                }
                double book_stamp = data.getDouble("book_stamp");
                if (book_stamp > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(book_date, receipt_print_no, Integer.parseInt(idRecipt), STAMPID, book_reson_idAccount, book_stamp, idUser, idbook, 10,1);
                }

                if (cesh > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(book_date, receipt_print_no, Integer.parseInt(idRecipt), CASH, book_reson_idAccount, cesh, idUser, idbook, 10,1);
                }
                if (cheack > 0) {
                    modle.Payment.CompleteAcc.insertToAccount(book_date, receipt_print_no, Integer.parseInt(idRecipt), CHEQUE, book_reson_idAccount, cheack, idUser, idbook, 10,1);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


}
