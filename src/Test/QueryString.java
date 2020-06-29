package Test;

import conn.DB;

import java.sql.ResultSet;

public class QueryString {
    static String ss = "SELECT\n" +
            "account_ps_three.report_ricipt_no,\n" +
            "account_ps_three.report_vort_id,\n" +
            "account_ps_three.report_ricipt_id,\n" +
            "receipt.receipt_total,\n" +
            "book.book_diposit,\n" +
            "book_place.book_place_name,\n" +
            "account_ps_three.report_account_id,\n" +
            "account_receipt_title.ART_vote_and_bal,\n" +
            "account_ps_three.report_amount,\n" +
            "book.book_date\n" +
            "FROM\n" +
            "account_ps_three\n" +
            "INNER JOIN receipt ON receipt.idReceipt = account_ps_three.report_ricipt_id\n" +
            "INNER JOIN book ON book.idbook = receipt.recept_applicationId\n" +
            "INNER JOIN book_place ON book.book_place_idbook_place = book_place.idbook_place\n" +
            "INNER JOIN account_receipt_title ON account_receipt_title.idAccount_receipt_title = account_ps_three.report_vort_id\n" +
            "WHERE\n" +
            "account_ps_three.report_application_cat_id = 10\n" +
            "AND account_ps_three.report_vort_id = '22'";


    String sss = "SELECT\n" +
            "\t`user`.user_full_name,\n" +
            "\t`user`.user_nic,\n" +
            "\t`user`.idUser,\n" +
            "\tuser_has_dipartment.Dipartment_idDipartment,\n" +
            "\tuser_has_subject.user_id,\n" +
            "\t`subject`.subject_name,\n" +
            "\t`subject`.idSubject,\n" +
            "\t`subject`.department_id\n" +
            "FROM\n" +
            "\tuser_has_dipartment\n" +
            "INNER JOIN `user` ON user_has_dipartment.User_idUser = `user`.idUser\n" +
            "LEFT JOIN user_has_subject ON `user`.idUser = user_has_subject.user_id\n" +
            "LEFT JOIN `subject` ON `subject`.idSubject = user_has_subject.subject_id\n" +
            "WHERE\n" +
            "`user`.user_status = 1 AND\n" +
            "user_has_dipartment.Dipartment_idDipartment = 1 AND\n" +
            "`subject`.idSubject = 3";

    String ff = "INSERT INTO `job_assign` (\n" +
            "\t`job_id`,\n" +
            "\t`user_id`,\n" +
            "\t`subject_id`,\n" +
            "\t`date_time`,\n" +
            "\t`status_id`,\n" +
            "\t`step`,\n" +
            "\t`step_name`\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\t\t\n" +
            "\t\t'21',\n" +
            "\t\t'15',\n" +
            "\t\tNULL,\n" +
            "\t\t'2019-11-13 13:43:29',\n" +
            "\t\t'1',\n" +
            "\t\t'1',\n" +
            "\t\t'Assign Job'\n" +
            "\t)";


    String fff ="SELECT\n" +
            "job_assign.idJob_assign,\n" +
            "job_assign.job_id,\n" +
            "job_assign.user_id,\n" +
            "job_assign.subject_id,\n" +
            "job_assign.date_time,\n" +
            "job_assign.status_id,\n" +
            "job_assign.step,\n" +
            "job_assign.step_name,\n" +
            "`user`.user_full_name,\n" +
            "`subject`.subject_name\n" +
            "FROM\n" +
            "job_assign\n" +
            "LEFT JOIN `user` ON `user`.idUser = job_assign.user_id\n" +
            "LEFT JOIN `subject` ON `subject`.idSubject = job_assign.subject_id\n" +
            "WHERE job_id = 3";

}
