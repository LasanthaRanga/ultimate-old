package modle.asses;

import conn.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class ApplicationsModle {


    public ObservableList<ApplicationHolder> getApplicationsList(int status) {

        ObservableList<ApplicationHolder> arrList = FXCollections.observableArrayList();

        String query = "SELECT\n" +
                "ass_app.idAssapp,\n" +
                "ass_app.assapp_date,\n" +
                "ass_app.assapp_user,\n" +
                "ass_app.assapp_step,\n" +
                "ass_app.assapp_description,\n" +
                "ass_app.assapp_status,\n" +
                "ass_app.assapp_type,\n" +
                "ass_app.assapp_refno,\n" +
                "ass_app.assapp_names,\n" +
                "`user`.idUser,\n" +
                "`user`.user_full_name,\n" +
                "ass_apptype.idAss_apptype,\n" +
                "ass_apptype.type_name\n" +
                "FROM\n" +
                "ass_app\n" +
                "INNER JOIN `user` ON `user`.idUser = ass_app.assapp_user\n" +
                "INNER JOIN ass_apptype ON ass_apptype.idAss_apptype = ass_app.assapp_type\n" +
                "WHERE\n" +
                "ass_app.assapp_status =" + status;

        try {
            ResultSet data = DB.getData(query);
            while (data.next()) {
                arrList.add(
                        new ApplicationHolder(
                                data.getInt("idAssapp"),
                                data.getString("assapp_refno"),
                                data.getString("assapp_date"),
                                data.getString("type_name"),
                                data.getInt("idAss_apptype"),
                                data.getString("assapp_names"),
                                data.getString("assapp_description"),
                                data.getString("user_full_name"),
                                data.getInt("idUser"),
                                data.getInt("assapp_status"),
                                data.getInt("assapp_step")
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return arrList;
    }


    public ObservableList<HolderAssess> loadAssessment(int id) {

        ObservableList<HolderAssess> arrayList = FXCollections.observableArrayList();
        arrayList.clear();

        String query = "SELECT\n" +
                "ass_newold.idAss_newold,\n" +
                "ass_newold.oldid,\n" +
                "ass_newold.newid,\n" +
                "ass_newold.appid,\n" +
                "ass_newold.newold_status\n" +
                "FROM\n" +
                "ass_newold\n" +
                "WHERE ass_newold.appid =" + id;
        try {
            ResultSet data = DB.getData(query);
            while (data.next()) {
                int oldid = data.getInt("oldid");
                ResultSet dd = DB.getData("SELECT\n" +
                        "\tassessment.Ward_idWard,\n" +
                        "\tstreet.street_name,\n" +
                        "\tcustomer.cus_name,\n" +
                        "\tassessment.assessment_no \n" +
                        "FROM\n" +
                        "\tassessment\n" +
                        "\tINNER JOIN street ON assessment.Street_idStreet = street.idStreet\n" +
                        "\tINNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer \n" +
                        "WHERE\n" +
                        "\tidAssessment = " + oldid);

                if (dd.last()) {
                    boolean notInList = true;
                    for (HolderAssess ha : arrayList) {
                        if (ha.getIdAssess() == oldid) {
                            notInList = false;
                        }
                    }
                    if (notInList) {
                        arrayList.add(new HolderAssess(
                                oldid,
                                0,
                                null,
                                dd.getString("Ward_idWard"),
                                dd.getString("street_name"),
                                dd.getString("assessment_no"),
                                null,
                                0,
                                null,
                                null)
                        );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return arrayList;
    }

    public ObservableList<HolderAssess> loadAssessment2(int id) {

        ObservableList<HolderAssess> arrayList = FXCollections.observableArrayList();
        arrayList.clear();

        String query = "SELECT\n" +
                "ass_newold.idAss_newold,\n" +
                "ass_newold.oldid,\n" +
                "ass_newold.newid,\n" +
                "ass_newold.appid,\n" +
                "ass_newold.newold_status\n" +
                "FROM\n" +
                "ass_newold\n" +
                "WHERE ass_newold.appid =" + id;
        try {
            ResultSet data = DB.getData(query);
            while (data.next()) {
                int newid = data.getInt("newid");
                ResultSet dd = DB.getData("SELECT\n" +
                        "\tassessment.Ward_idWard,\n" +
                        "\tstreet.street_name,\n" +
                        "\tcustomer.cus_name,\n" +
                        "\tassessment.assessment_no \n" +
                        "FROM\n" +
                        "\tassessment\n" +
                        "\tINNER JOIN street ON assessment.Street_idStreet = street.idStreet\n" +
                        "\tINNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer \n" +
                        "WHERE\n" +
                        "\tidAssessment = " + newid);

                if (dd.last()) {
                    boolean notInList = true;
                    for (HolderAssess ha : arrayList) {
                        if (ha.getIdAssess() == newid) {
                            notInList = false;
                        }
                    }
                    if (notInList) {
                        arrayList.add(new HolderAssess(
                                newid,
                                0,
                                null,
                                dd.getString("Ward_idWard"),
                                dd.getString("street_name"),
                                dd.getString("assessment_no"),
                                null,
                                0,
                                null,
                                null)
                        );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return arrayList;
    }


    public String getObsalutNumbers(int appid) {
        String obsalute = "";
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_app.idAssapp,\n" +
                    "ass_newold.appid,\n" +
                    "ass_newold.oldid,\n" +
                    "assessment.idAssessment,\n" +
                    "assessment.assessment_no\n" +
                    "FROM\n" +
                    "ass_app\n" +
                    "INNER JOIN ass_newold ON ass_newold.appid = ass_app.idAssapp\n" +
                    "INNER JOIN assessment ON assessment.idAssessment = ass_newold.oldid WHERE idAssapp = " + appid);

            while (data.next()) {
                obsalute += data.getString("assessment_no") + ",";
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return obsalute;
    }


    public void amalgamation(int appid, int assessid) {
        try {
            conn.DB.setData("UPDATE `ass_newold` \n" +
                    "SET \n" +
                    "`newid` = " + assessid + ",\n" +
                    "`newold_status` = 1 \n" +
                    "WHERE\t\n" +
                    "\tappid = " + appid);

            conn.DB.setData("UPDATE `ass_app` \n" +
                    "SET \n" +
                    "`assapp_step` = 10 \n" +
                    "WHERE\n" +
                    "\t`idAssapp` =" + appid);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    public void subDivition(int appid, int assessid) {
        try {

            ResultSet data = DB.getData("SELECT\n" +
                    "\tass_newold.idAss_newold,\n" +
                    "\tass_newold.oldid,\n" +
                    "\tass_newold.newid,\n" +
                    "\tass_newold.appid,\n" +
                    "\tass_newold.newold_status \n" +
                    "FROM\n" +
                    "\tass_newold \n" +
                    "WHERE\n" +
                    "\tappid = " + appid);

            if (data.last()) {
                int newid = data.getInt("newid");
                int oldid = data.getInt("oldid");


                if (newid == 0) {
                    conn.DB.setData("UPDATE `ass_newold` \n" +
                            "SET \n" +
                            "`newid` = " + assessid + ",\n" +
                            "`newold_status` = 2 \n" +
                            "WHERE\t\n" +
                            "\tappid = " + appid);
                } else {
                    conn.DB.setData("INSERT INTO `ass_newold`( `oldid`, `newid`, `appid`, `newold_status`) VALUES ( " + oldid + ", " + assessid + ", " + appid + ", 2)");
                }
            }


            conn.DB.setData("UPDATE `ass_app` \n" +
                    "SET \n" +
                    "`assapp_step` = 10 \n" +
                    "WHERE\n" +
                    "\t`idAssapp` =" + appid);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

}
