package modle.asses;

import conn.DB;
import conn.NewHibernateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import modle.ComboItem;
import modle.GetInstans;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import pojo.*;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ranga on 2019-02-05.
 */
public class ChangeAllocation {

    controller.assess.AllocationChangeController acc;

    int idAssess = 0;
    int idNature = 0;
    int idAllocation = 0;
    int currentQuater = 0;
    double currentAllocation = 0;
    double newAllocation = 0;

    public ChangeAllocation(controller.assess.AllocationChangeController acc) {
        this.acc = acc;
    }

    public void clearAll() {
        idAssess = 0;
        idNature = 0;
        idAllocation = 0;
        currentQuater = 0;
        currentAllocation = 0;
        newAllocation = 0;


    }

    public String getOverPay() {
        String id = acc.txt_idAssess.getText();
        int currentYear = GetInstans.getQuater().getCurrentYear();
        double ass_payHistry_over = 0.0;
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "\tass_payhistry.ass_PayHistry_Over,\n" +
                    "\tass_payhistry.Assessment_idAssessment,\n" +
                    "\tass_payhistry.idass_PayHistry\n" +
                    "FROM\n" +
                    "\tass_payhistry\n" +
                    "WHERE\n" +
                    "\tass_payhistry.Assessment_idAssessment = '" + id + "'\n" +
                    "AND ass_payhistry.ass_PayHistry_year = '" + currentYear + "'\n");

            while (data.next()) {
                ass_payHistry_over += data.getDouble("ass_PayHistry_Over");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return modle.Round.roundToString(ass_payHistry_over);
    }


    public void updateOverPay(String over) {
        String id = acc.txt_idAssess.getText();
        int currentYear = GetInstans.getQuater().getCurrentYear();
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "\tass_payhistry.ass_PayHistry_Over,\n" +
                    "\tass_payhistry.Assessment_idAssessment,\n" +
                    "\tass_payhistry.idass_PayHistry\n" +
                    "FROM\n" +
                    "\tass_payhistry\n" +
                    "WHERE\n" +
                    "\tass_payhistry.Assessment_idAssessment = '" + id + "'\n" +
                    "AND ass_payhistry.ass_PayHistry_year = '" + currentYear + "'\n");
            int fetchSize = data.getFetchSize();

            System.out.println(fetchSize);
            int lastId = 0;
            while (data.next()) {
                int idass_payHistry = data.getInt("idass_PayHistry");
                lastId = idass_payHistry;
                conn.DB.setData("UPDATE `ass_payhistry` SET `ass_PayHistry_Over` = '0' WHERE `idass_PayHistry` = " + idass_payHistry);
            }
            conn.DB.setData("UPDATE `ass_payhistry` SET `ass_PayHistry_Over` = '" + over + "' WHERE `idass_PayHistry` = " + lastId);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getAllocation() {
        String id = acc.txt_idAssess.getText();
        try {
            int i = Integer.parseInt(id);
            getAssessmentById(i);
            loadAllocationChantTable(i);
        } catch (Exception e) {
            getAssessmentById(0);
            loadAllocationChantTable(0);
        }

    }


    public void getAssessmentById(int i) {
        idAssess = i;
        String qq = "SELECT\n" +
                "\tassessment.idAssessment,\n" +
                "\tward.ward_name,\n" +
                "\tstreet.street_name,\n" +
                "\tass_allocation.ass_allocation,\n" +
                "\tcustomer.cus_name,\n" +
                "\tass_nature.ass_nature_name, ass_nature.idass_nature,\n" +
                "\tass_nature.ass_nature_year_rate,\n" +
                "\tass_nature.idass_nature,\n" +
                "\tass_allocation.idass_allocation,\n" +
                "\tassessment.assessment_no\n" +
                "FROM\n" +
                "\tward\n" +
                "INNER JOIN street ON street.Ward_idWard = ward.idWard\n" +
                "INNER JOIN assessment ON assessment.Street_idStreet = street.idStreet\n" +
                "AND assessment.Ward_idWard = ward.idWard\n" +
                "INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n" +
                "INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                "WHERE\n" +
                "\tass_allocation.ass_allocation_status = 1\n" +
                "AND assessment.idAssessment =" + i;

        try {
            ResultSet data = DB.getData(qq);
            if (data.last()) {
                idAllocation = data.getInt("idass_allocation");
                idNature = data.getInt("idass_nature");
                acc.txt_ward.setText(data.getString("ward_name"));
                acc.txt_street.setText(data.getString("street_name"));
                acc.txt_assessment.setText(data.getString("assessment_no"));
                acc.txt_customer.setText(data.getString("cus_name"));
                acc.txt_nature.setText(data.getString("ass_nature_name") + "  -->  " + data.getString("ass_nature_year_rate") + "%");
                currentAllocation = data.getDouble("ass_allocation");
                acc.txt_allocation.setText(modle.Round.roundToString(currentAllocation));
                ComboItem comboItem = new ComboItem(data.getInt("idass_nature"), data.getString("ass_nature_name"));
                acc.com_nature.getSelectionModel().select(comboItem);
                acc.txt_newAllocation.setText(modle.Round.roundToString(currentAllocation));

                ResultSet data1 = DB.getData("SELECT\n" +
                        "ass_creditdebit.idAss_CreditDebit,\n" +
                        "ass_creditdebit.Ass_CreditDebit_discription,\n" +
                        "ass_creditdebit.Ass_CreditDebit_cd,\n" +
                        "ass_creditdebit.Ass_CreditDebit_amount,\n" +
                        "ass_creditdebit.Ass_balance,\n" +
                        "ass_creditdebit.Ass_CreditDebit_date,\n" +
                        "ass_creditdebit.Assessment_idAssessment,\n" +
                        "ass_creditdebit.Ass_CreditDebit_status\n" +
                        "FROM `ass_creditdebit`\n" +
                        "WHERE\n" +
                        "ass_creditdebit.Assessment_idAssessment = '" + i + "' AND\n" +
                        "ass_creditdebit.Ass_CreditDebit_status = 1");

                if (data1.last()) {
                    acc.txt_creditDebit.setText(modle.Round.roundToString(data1.getDouble("Ass_balance")));
                } else {
                    acc.txt_creditDebit.setText("00");
                }


            } else {
                clearAll();
                acc.txt_ward.setText("");
                acc.txt_street.setText("");
                acc.txt_assessment.setText("");
                acc.txt_customer.setText("");
                acc.txt_nature.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        getCurrentQuater();

    }


    public void getCurrentQuater() {
        currentQuater = GetInstans.getQuater().getCurrentQuater();
        acc.txt_quater.setText(currentQuater + "");
    }


    public void getQstartData() {
        String quary = "SELECT\n" +
                "\tass_qstart.idass_Qstart,\n" +
                "\tass_qstart.ass_Qstart_QuaterNumber,\n" +
                "\tass_qstart.ass_Qstart_process_date,\n" +
                "\tass_qstart.ass_Qstart_LY_Arreas,\n" +
                "\tass_qstart.ass_Qstart_LY_Warrant,\n" +
                "\tass_qstart.ass_Qstart_LYC_Arreas,\n" +
                "\tass_qstart.ass_Qstart_LYC_Warrant,\n" +
                "\tass_qstart.ass_Qstart_LQ_Arreas,\n" +
                "\tass_qstart.ass_Qstart_LQC_Arreas,\n" +
                "\tass_qstart.ass_Qstart_LQ_Warrant,\n" +
                "\tass_qstart.ass_Qstart_LQC_Warrant,\n" +
                "\tass_qstart.ass_Qstart_HaveToQPay,\n" +
                "\tass_qstart.ass_Qstart_QPay,\n" +
                "\tass_qstart.ass_Qstart_QDiscont,\n" +
                "\tass_qstart.ass_Qstart_QTot,\n" +
                "\tass_qstart.ass_Qstart_FullTotal,\n" +
                "\tass_qstart.ass_Qstart_status,\n" +
                "\tass_qstart.Assessment_idAssessment,\n" +
                "\tass_qstart.ass_Qstart_year\n" +
                "FROM\n" +
                "\tass_qstart\n" +
                "WHERE\n" +
                "ass_qstart.Assessment_idAssessment = '" + idAssess + "' AND\n" +
                "ass_qstart.ass_Qstart_status = 1";
        try {
            ResultSet data = DB.getData(quary);
            if (data.next()) {
                double lyca = data.getDouble("ass_Qstart_LYC_Arreas");
                double lycw = data.getDouble("ass_Qstart_LYC_Warrant");
                double lqca = data.getDouble("ass_Qstart_LQC_Arreas");
                double lqcw = data.getDouble("ass_Qstart_LQC_Warrant");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAllocationChangeHistory(double cd) {

        String text = acc.txt_newAllocation.getText();
        modle.GetInstans.getNature().getNatureObservableListSQL();
        int newNature = acc.com_nature.getSelectionModel().getSelectedItem().getId();
        String dis = acc.txt_description.getText();


        if (text.length() > 0) {
            if (dis.length() > 2) {
                if (newNature > 0) {
                    try {
                        newAllocation = Double.parseDouble(text);
                        Session session = NewHibernateUtil.getSessionFactory().openSession();
                        Transaction transaction = session.beginTransaction();

                        try {
                            acc.btn_Save.setDisable(true);
                            AssUpdateHistry auh = new AssUpdateHistry();
                            auh.setIdAssessment(idAssess);
                            auh.setAuDate(modle.GetInstans.getQuater().getSystemDateByQuary());
                            auh.setAuOldAllocation(currentAllocation);
                            auh.setAuNewAllocation(newAllocation);
                            auh.setAuOldNature(idNature);
                            auh.setAuNewNature(newNature);
                            auh.setAuComment(dis);
                            auh.setAuIdUseer(modle.StaticViews.getLogUser().getIdUser());
                            auh.setAuCd(cd);

                            Assessment ass = (Assessment) session.load(Assessment.class, idAssess);
                            Set<AssAllocation> assAllocations = ass.getAssAllocations();
                            for (AssAllocation alos : assAllocations) {
                                alos.setAssAllocationStatus(0);
                                session.update(alos);
                                //Old Allocation Status Change
                            }
                            //NEw Allocation
                            AssAllocation assAllocation = new AssAllocation();
                            assAllocation.setAssessment(ass);
                            assAllocation.setAssAllocationStatus(1);
                            assAllocation.setAssAllocationIdUser(modle.StaticViews.getLogUser().getIdUser());
                            assAllocation.setAssAllocationChangeDate(modle.GetInstans.getQuater().getSystemDateByQuary());
                            assAllocation.setAssAllocationDiscription(dis);
                            assAllocation.setAssAllocation(newAllocation);
                            session.save(assAllocation);
                            session.save(auh);

                            //change have to pay
                            AssNature assNature = ass.getAssNature();
                            double qvalue = assNature.getAssNatureYearRate() * newAllocation / 400;

                            int x = (Integer) session.createCriteria(AssPayhistry.class).setProjection(Projections.max("idassPayHistry")).add(Restrictions.eq("assessment", ass)).uniqueResult();
                            System.out.println(x);
                            AssPayhistry ph = (AssPayhistry) session.load(pojo.AssPayhistry.class, x);

                            Set<AssQstart> assQstarts = ass.getAssQstarts();
                            for (AssQstart qs : assQstarts) {
                                if (qs.getAssQstartStatus() == 1) {
                                    Integer qn = qs.getAssQstartQuaterNumber();

                                    if (qn == 1) {
                                        if (ph.getAssPayHistryQ1status() == 0) {
                                            double v = qvalue - ph.getAssPayHistryQ1();
                                            qs.setAssQstartHaveToQpay(v);
                                        }
                                    }

                                    if (qn == 2) {
                                        if (ph.getAssPayHistryQ2status() == 0) {
                                            double v = qvalue - ph.getAssPayHistryQ2();
                                            qs.setAssQstartHaveToQpay(v);
                                        }
                                    }

                                    if (qn == 3) {
                                        if (ph.getAssPayHistryQ3status() == 0) {
                                            double v = qvalue - ph.getAssPayHistryQ3();
                                            qs.setAssQstartHaveToQpay(v);
                                        }
                                    }

                                    if (qn == 4) {
                                        if (ph.getAssPayHistryQ4status() == 0) {
                                            double v = qvalue - ph.getAssPayHistryQ4();
                                            qs.setAssQstartHaveToQpay(v);
                                        }
                                    }

                                    session.update(qs);
                                }
                            }


                            //Credit Debit

                            double lastBal = Double.parseDouble(acc.txt_creditDebit.getText());

                            List<AssCreditdebit> cdlist = session.createCriteria(AssCreditdebit.class)
                                    .add(Restrictions.eq("assessment", ass))
                                    .add(Restrictions.eq("assCreditDebitStatus", 1)).list();
                            for (AssCreditdebit cdd : cdlist) {
                                cdd.setAssCreditDebitStatus(0);
                                session.update(cdd);
                            }

                            AssCreditdebit acd = new AssCreditdebit();
                            acd.setAssessment(ass);
                            acd.setAssCreditDebitAmount(cd);
                            if (cd > 0) {
                                acd.setAssCreditDebitCd(1);
                            } else if (cd < 0) {
                                acd.setAssCreditDebitCd(-1);
                            } else {
                                acd.setAssCreditDebitCd(0);
                            }
                            acd.setAssCreditDebitDiscription(dis);
                            acd.setAssBalance(lastBal + cd);
                            acd.setAssCreditDebitStatus(1);
                            acd.setAssCreditDebitDate(modle.GetInstans.getQuater().getSystemDateByQuary());
                            Serializable save = session.save(acd);

                            int i = Integer.parseInt(save.toString());

                            acc.txt_creditDebit.setText(modle.Round.roundToString(lastBal + cd));

                            transaction.commit();
                            conn.DB.setData("UPDATE `ass_creditdebit` SET `user_id` = '" + modle.StaticViews.getLogUser().getIdUser() + "' WHERE `idAss_CreditDebit` =" + i);

                            modle.Allert.notificationGood("Success", "New Balance" + (lastBal + cd));
                            acc.btn_Save.setDisable(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                            transaction.rollback();
                            modle.Allert.notificationError("Error", e.getMessage());
                        } finally {
                            session.close();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        //New Allocation
                    }
                } else {
                    modle.Allert.notificationInfo("Natrue Is Not Valid", "Please cheack It");
                }
            } else {
                modle.Allert.notificationInfo("Descrtiption Is too short", "Please cheack It");
            }
        }
        loadAllocationChantTable(idAssess);
    }


    public void loadAllocationChantTable(int i) {

        ObservableList<changeHistry> ol = FXCollections.observableArrayList();

        acc.col_allocation.setCellValueFactory(new PropertyValueFactory<>("oldAllocation"));
        acc.col_new_allocation.setCellValueFactory(new PropertyValueFactory<>("newAllocation"));
        acc.col_nature.setCellValueFactory(new PropertyValueFactory<>("oldNature"));
        acc.col_new_nature.setCellValueFactory(new PropertyValueFactory<>("newNature"));
        acc.col_cd.setCellValueFactory(new PropertyValueFactory<>("creditDebit"));
        acc.col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        acc.col_discription.setCellValueFactory(new PropertyValueFactory<>("coment"));
        acc.col_change.setCellValueFactory(new PropertyValueFactory<>("user"));

        String quary = "SELECT\n" +
                "\tass_update_histry.idAss_Update,\n" +
                "\tass_update_histry.idAssessment,\n" +
                "\tass_update_histry.au_date,\n" +
                "\tass_update_histry.au_old_allocation,\n" +
                "\tass_update_histry.au_new_allocation,\n" +
                "\tass_update_histry.au_old_nature,\n" +
                "\tass_update_histry.au_new_nature,\n" +
                "\tass_update_histry.au_comment,\n" +
                "\tass_update_histry.au_cd,\n" +
                "\tass_update_histry.au_idUseer,\n" +
                "\t`user`.user_full_name\n" +
                "FROM\n" +
                "\tass_update_histry\n" +
                "INNER JOIN `user` ON `user`.idUser = ass_update_histry.au_idUseer\n" +
                "WHERE\n" +
                "ass_update_histry.idAssessment =" + i;

        ol.clear();
        try {
            ResultSet data = DB.getData(quary);
            while (data.next()) {

                int newNature = data.getInt("au_new_nature");
                int oldNatrue = data.getInt("au_old_nature");
                String nnq = "SELECT\n" +
                        "\tass_nature.ass_nature_name,\n" +
                        "\tass_nature.ass_nature_year_rate\n" +
                        "FROM\n" +
                        "\tass_nature\n" +
                        "WHERE\n" +
                        "\tass_nature.idass_nature =" + newNature;

                String onq = "SELECT\n" +
                        "\tass_nature.ass_nature_name,\n" +
                        "\tass_nature.ass_nature_year_rate\n" +
                        "FROM\n" +
                        "\tass_nature\n" +
                        "WHERE\n" +
                        "\tass_nature.idass_nature =" + oldNatrue;


                String nn = "";
                ResultSet nnd = DB.getData(nnq);
                if (nnd.last()) {
                    nn = nnd.getString("ass_nature_name") + " - " + nnd.getString("ass_nature_year_rate") + "%";
                }

                String on = "";
                ResultSet ond = DB.getData(onq);
                if (ond.last()) {
                    on = ond.getString("ass_nature_name") + " - " + ond.getString("ass_nature_year_rate") + "%";
                }

                ol.add(new changeHistry(
                        data.getInt("idAss_Update"),
                        data.getInt("idAssessment"),
                        data.getString("au_date"),
                        data.getDouble("au_old_allocation"),
                        data.getDouble("au_new_allocation"),
                        on, nn, data.getString("au_comment"),
                        data.getDouble("au_cd"),
                        data.getString("user_full_name")
                ));
                data.getInt("idAss_Update");
            }
            acc.tbl_histry.setItems(ol);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public class changeHistry {

        private int id;
        private int idAssess;
        private SimpleStringProperty date;
        private double oldAllocation;
        private double newAllocation;
        private SimpleStringProperty oldNature;
        private SimpleStringProperty newNature;
        private SimpleStringProperty coment;
        private double creditDebit;
        private SimpleStringProperty user;

        changeHistry(int id, int idAssess, String date, double oldAllocation, double newAllocation, String oldNatrue, String newNature, String comment, double creditDebit, String user) {
            this.setId(id);
            this.setIdAssess(idAssess);
            this.setDate(new SimpleStringProperty(date));
            this.setOldAllocation(oldAllocation);
            this.setNewAllocation(newAllocation);
            this.setOldNature(new SimpleStringProperty(oldNatrue));
            this.setNewNature(new SimpleStringProperty(newNature));
            this.setComent(new SimpleStringProperty(comment));
            this.setCreditDebit(creditDebit);
            this.setUser(user);
        }

        public String getUser() {
            return user.get();
        }

        public void setUser(String user) {
            this.user = new SimpleStringProperty(user);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIdAssess() {
            return idAssess;
        }

        public void setIdAssess(int idAssess) {
            this.idAssess = idAssess;
        }

        public String getDate() {
            return date.get();
        }

        public void setDate(SimpleStringProperty date) {
            this.date = date;
        }

        public double getOldAllocation() {
            return oldAllocation;
        }

        public void setOldAllocation(double oldAllocation) {
            this.oldAllocation = oldAllocation;
        }

        public double getNewAllocation() {
            return newAllocation;
        }

        public void setNewAllocation(double newAllocation) {
            this.newAllocation = newAllocation;
        }

        public String getOldNature() {
            return oldNature.get();
        }

        public void setOldNature(SimpleStringProperty oldNature) {
            this.oldNature = oldNature;
        }

        public String getNewNature() {
            return newNature.get();
        }

        public void setNewNature(SimpleStringProperty newNature) {
            this.newNature = newNature;
        }

        public String getComent() {
            return coment.get();
        }

        public void setComent(SimpleStringProperty coment) {
            this.coment = coment;
        }

        public double getCreditDebit() {
            return creditDebit;
        }

        public void setCreditDebit(double creditDebit) {
            this.creditDebit = creditDebit;
        }
    }


}
