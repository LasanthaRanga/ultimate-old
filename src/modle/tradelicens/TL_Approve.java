package modle.tradelicens;

import conn.DB;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modle.asses.HolderAssess;

import java.sql.ResultSet;

/**
 * Created by Ranga on 2019-01-13.
 */
public class TL_Approve {

    public ObservableList<AppList> loadApplicationToTable(int approvalCat, int status, String cus_name, String trade_name) {

        String quary2 = "SELECT\n" +
                "application.application_no,\n" +
                "apprualstatues.idApprualStatues,\n" +
                "approval_cat.approval_name,\n" +
                "approval_cat.idApproval_cat,\n" +
                "apprualstatues.SendDate,\n" +
                "trade_nature.nature,\n" +
                "application.idApplication,\n" +
                "application.Customer_idCustomer,\n" +
                "application.Trade_Type_idTrade_Type,\n" +
                "application.Trade_Nature_idTrade_Nature,\n" +
                "application.Sub_Nature_idSub_Nature,\n" +
                "application.User_idUser,\n" +
                "application.Assessment_idAssessment,\n" +
                "application.application_date,\n" +
                "application.`year`,\n" +
                "application.`month`,\n" +
                "application.allocation,\n" +
                "application.trade_name,\n" +
                "application.trade_address1,\n" +
                "application.trade_address2,\n" +
                "application.trade_address3,\n" +
                "application.trade_nature,\n" +
                "application.tax_amount,\n" +
                "application.discription,\n" +
                "application.statues,\n" +
                "application.syn,\n" +
                "application.User_Log_idUser_Log,\n" +
                "application.approveTo_Paymant,\n" +
                " application.cus_name,\n" +
                " application.cus_nic,\n" +
                " application.sr_shop_idsr_shop\n" +
                "FROM\n" +
                "\tapplication\n" +
                "INNER JOIN apprualstatues ON apprualstatues.Application_idApplication = application.idApplication\n" +
                "INNER JOIN approval_cat ON approval_cat.idApproval_cat = apprualstatues.idOtheritisCat\n" +
                "INNER JOIN trade_nature ON application.Trade_Nature_idTrade_Nature = trade_nature.idTrade_Nature\n" +
                "WHERE\n" +
                "\tapprualstatues.statues = " + status + "\n  ";


        String Quary = "SELECT\n" +
                "\tapplication.application_no,\n" +
                "\tapprualstatues.idApprualStatues,\n" +
                "\tapproval_cat.approval_name,\n" +
                "\tapproval_cat.idApproval_cat,\n" +
                "\tapprualstatues.SendDate,\n" +
                "\tassessment.assessment_no,\n" +
                "\tstreet.street_name,\n" +
                "\tward.ward_no,\n" +
                "\ttrade_nature.nature,\n" +
                "\tapplication.idApplication,\n" +
                "\tapplication.Customer_idCustomer,\n" +
                "\tapplication.Trade_Type_idTrade_Type,\n" +
                "\tapplication.Trade_Nature_idTrade_Nature,\n" +
                "\tapplication.Sub_Nature_idSub_Nature,\n" +
                "\tapplication.User_idUser,\n" +
                "\tapplication.Assessment_idAssessment,\n" +
                "\tapplication.application_date,\n" +
                "\tapplication.`year`,\n" +
                "\tapplication.`month`,\n" +
                "\tapplication.allocation,\n" +
                "\tapplication.trade_name,\n" +
                "\tapplication.trade_address1,\n" +
                "\tapplication.trade_address2,\n" +
                "\tapplication.trade_address3,\n" +
                "\tapplication.trade_nature,\n" +
                "\tapplication.tax_amount,\n" +
                "\tapplication.discription,\n" +
                "\tapplication.statues,\n" +
                "\tapplication.syn,\n" +
                "\tapplication.User_Log_idUser_Log,\n" +
                "\tapplication.approveTo_Paymant,\n" +
                "\tapplication.cus_name,\n" +
                "\tapplication.cus_nic,\n" +
                "\tapplication.sr_shop_idsr_shop\n" +
                "FROM\n" +
                "\tapplication\n" +
                "INNER JOIN apprualstatues ON apprualstatues.Application_idApplication = application.idApplication\n" +
                "INNER JOIN approval_cat ON approval_cat.idApproval_cat = apprualstatues.idOtheritisCat\n" +
                "LEFT JOIN assessment ON application.Assessment_idAssessment = assessment.idAssessment\n" +
                "INNER JOIN street ON assessment.Street_idStreet = street.idStreet\n" +
                "INNER JOIN ward ON assessment.Ward_idWard = ward.idWard\n" +
                "AND street.Ward_idWard = ward.idWard\n" +
                "INNER JOIN trade_nature ON application.Trade_Nature_idTrade_Nature = trade_nature.idTrade_Nature\n" +
                "WHERE\n" +
                "\tapprualstatues.statues = " + status + "\n  ";

        if (approvalCat == 15) {
//            Quary += " AND application.approveTo_Paymant = 0 ";
        } else {
            quary2 += " AND application.approveTo_Paymant = 0 ";
        }

        quary2 += " AND approval_cat.idApproval_cat = " + approvalCat + "\n" +
                "AND application.cus_name LIKE '%" + cus_name + "%'\n" +
                "AND application.trade_name LIKE '%" + trade_name + "%' GROUP BY \n" +
                "application.idApplication";
        try {

            ObservableList<AppList> List = FXCollections.observableArrayList();
            List.clear();
            ResultSet data = DB.getData(quary2);
            while (data.next()) {

                String assid = data.getString("application.Assessment_idAssessment");
                String assessAddress = "";
                if (assid != null) {
                    ResultSet asdata = DB.getData("SELECT\n" +
                            "assessment.assessment_no,\n" +
                            "street.street_name,\n" +
                            "ward.ward_name\n" +
                            "FROM\n" +
                            "assessment\n" +
                            "INNER JOIN street ON assessment.Street_idStreet = street.idStreet\n" +
                            "INNER JOIN ward ON assessment.Ward_idWard = ward.idWard AND street.Ward_idWard = ward.idWard\n" +
                            "WHERE\n" +
                            "assessment.idAssessment = " + assid);
                    if (asdata.last()) {
                        assessAddress += asdata.getString("ward.ward_name") + " / " + asdata.getString("street.street_name") + " / " + asdata.getString("assessment.assessment_no");
                    }
                } else {
                    String shopno = data.getString("application.sr_shop_idsr_shop");
                    ResultSet shopdata = DB.getData("SELECT\n" +
                            "\tsr_shop.sr_shop_no,\n" +
                            "\tsr_flow.sr_flow_name,\n" +
                            "\tsr_building.sr_building_name\n" +
                            "FROM\n" +
                            "\tsr_shop\n" +
                            "INNER JOIN sr_flow ON sr_shop.sr_flow_idsr_flow = sr_flow.idsr_flow\n" +
                            "INNER JOIN sr_building ON sr_shop.sr_building_idsr_building = sr_building.idsr_building\n" +
                            "AND sr_flow.sr_building_idsr_building = sr_building.idsr_building\n" +
                            "WHERE sr_shop.idsr_shop = " + shopno);
                    if (shopdata.last()) {
                        assessAddress += shopdata.getString("sr_building.sr_building_name") + " / " + shopdata.getString("sr_flow.sr_flow_name") + " / " + shopdata.getString("sr_shop.sr_shop_no");
                    }

                }

                AppList appList = new AppList(data.getInt("idApplication"),
                        data.getString("application_no"),
                        data.getString("trade_name"),
                        data.getString("nature"),
                        data.getString("cus_name"),
                        assessAddress);
                List.add(appList);
            }
            return List;
        } catch (Exception e) {
            e.printStackTrace();
            modle.ErrorLog.writeLog(e.getMessage(), "TL_approve", "loadApplicationToTable", "modle.tradelicens");
            return null;
        }
    }


    public class AppList {
        public AppList(int id, String no, String trade_name, String trade_nature, String cus_name, String assessment) {
            this.id = id;
            this.no = new SimpleStringProperty(no);
            this.trade_name = new SimpleStringProperty(trade_name);
            this.trade_nature = new SimpleStringProperty(trade_nature);
            this.cus_name = new SimpleStringProperty(cus_name);
            this.assessment = new SimpleStringProperty(assessment);
        }

        private int id;
        private SimpleStringProperty no;
        private SimpleStringProperty trade_name;
        private SimpleStringProperty trade_nature;
        private SimpleStringProperty cus_name;
        private SimpleStringProperty assessment;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNo() {
            return no.get();
        }

        public void setNo(SimpleStringProperty no) {
            this.no = no;
        }

        public String getTrade_name() {
            return trade_name.get();
        }

        public void setTrade_name(SimpleStringProperty trade_name) {
            this.trade_name = trade_name;
        }

        public String getTrade_nature() {
            return trade_nature.get();
        }

        public void setTrade_nature(SimpleStringProperty trade_nature) {
            this.trade_nature = trade_nature;
        }

        public String getCus_name() {
            return cus_name.get();
        }

        public void setCus_name(SimpleStringProperty cus_name) {
            this.cus_name = cus_name;
        }

        public String getAssessment() {
            return assessment.get();
        }

        public void setAssessment(SimpleStringProperty assessment) {
            this.assessment = assessment;
        }
    }

}
