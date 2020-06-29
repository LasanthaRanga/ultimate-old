/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modle.asses;

import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * @author RM.LasanthaRanga@gmail.com
 */
public class Nature {

    public ObservableList<String> getNatureObservableListSQL() {
        String quary = "SELECT ass_nature.ass_nature_name FROM ass_nature";
        ObservableList<String> wardObservableList = FXCollections.observableArrayList();
        try {
            ResultSet data = conn.DB.getData(quary);
            while (data.next()) {
                wardObservableList.add(data.getString("ass_nature_name"));
            }
            return wardObservableList;
        } catch (Exception ex) {
            Logger.getLogger(Ward.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public pojo.AssNature getNatrePojo(String nature) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            return (pojo.AssNature) session.createCriteria(pojo.AssNature.class).add(Restrictions.eq("assNatureName", nature)).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }

    }

    public int getNatureIdByQuary(String nature) {
        String quary = "SELECT  * FROM ass_nature WHERE ass_nature.ass_nature_name ='" + nature + "'";
        int id = 0;
        try {
            ResultSet data = conn.DB.getData(quary);
            if (data.last()) {
                id = data.getInt("idass_nature");
            }
            return id;
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(Ward.class.getName()).log(Level.SEVERE, null, ex);
            return id;
        }
    }

    public ObservableList<NatureHolder> getNatureSelectList() {
        ObservableList<NatureHolder> arrayList = FXCollections.observableArrayList();
        String quary = "SELECT  * FROM ass_nature";
        try {
            ResultSet data = conn.DB.getData(quary);
            while (data.next()) {
                int id = data.getInt("idass_nature");
                String ass_nature_name = data.getString("ass_nature_name");
                arrayList.add(new NatureHolder(id, ass_nature_name));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(Ward.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrayList;
    }


}

