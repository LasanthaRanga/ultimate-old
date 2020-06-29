package modle;

import java.sql.ResultSet;
import java.util.List;

import conn.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import pojo.Bank;

/**
 *
 * @author Ranga Rathnayake
 */
public class Banks {
    public ObservableList<String> loadBanksCombo() {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().commit();
        ObservableList<String> bankList = FXCollections.observableArrayList();
        try {
            List<Bank> list = session.createCriteria(Bank.class).list();
            for (Bank bank : list) {
                bankList.add(bank.getBankName());
            }
            return bankList;
        } catch (Exception e) {
            e.printStackTrace();
            return bankList;
        } finally {
            session.close();
        }
    }

    public int getBankIdByBankName(String bankName){
        int bid = 0;
        try {
            ResultSet data = DB.getData("SELECT \tbank.idBank, bank.bank_name FROM bank WHERE bank.bank_name = '"+bankName+"'");
            if(data.last()){
               bid = data.getInt("idBank");
            }
            return bid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bid;
    }

    public String getBankNameId(String bankName){
        String bid = "";
        try {
            ResultSet data = DB.getData("SELECT \tbank.idBank, bank.bank_name FROM bank WHERE bank.bank_name = '"+bankName+"'");
            if(data.last()){
                bid = data.getString("bank_name");
            }
            return bid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bid;
    }



}
