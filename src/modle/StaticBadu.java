package modle;

import controller.assess.AppLaters;
import controller.assess.Applications;
import controller.mix.Mixincome;
import controller.mix.Search;
import javafx.collections.ObservableList;
import modle.asses.HolderAssess;
import modle.asses.SearchHolder;
import net.sf.jasperreports.web.servlets.Controller;

import java.util.Date;

public class StaticBadu {

    private static Date selectedSystemDate;

    private static int appcatid;
    private static int appid;
    private static SearchHolder sh;
    private static Search.Cross cross;
    private static Mixincome mixincome;

    public static void setMixincome(Mixincome mixincome) {
        StaticBadu.mixincome = mixincome;
    }

    public static Mixincome getMixincome() {
        return mixincome;
    }

    public static void setCross(Search.Cross cross) {
        StaticBadu.cross = cross;
    }

    public static Search.Cross getCross() {
        return cross;
    }

    private static ObservableList<HolderAssess> selectedList;

    public static void setSelectedList(ObservableList<HolderAssess> selectedList) {
        StaticBadu.selectedList = selectedList;
    }

    public static ObservableList<HolderAssess> getSelectedList() {
        return selectedList;
    }

    public static Date getSelectedSystemDate() {
        return selectedSystemDate;
    }

    public static void setSelectedSystemDate(Date aSelectedSystemDate) {
        selectedSystemDate = aSelectedSystemDate;
    }

    public static int getAppcatid() {
        return appcatid;
    }

    public static void setAppcatid(int appcatid) {
        StaticBadu.appcatid = appcatid;
    }

    public static int getAppid() {
        return appid;
    }

    public static void setAppid(int appid) {
        StaticBadu.appid = appid;
    }


    public static SearchHolder getSH() {
        if (sh == null) {
            sh = new SearchHolder();
        }
        return sh;
    }

    public static void setSH(SearchHolder sh) {
        StaticBadu.sh = sh;
    }


    private static int ass_app_id;


    private static int ass_app_type;

    public static void setAss_app_type(int ass_app_type) {
        StaticBadu.ass_app_type = ass_app_type;
    }

    public static int getAss_app_type() {
        return ass_app_type;
    }

    public static void setAss_app_id(int ass_app_id) {
        StaticBadu.ass_app_id = ass_app_id;
    }

    public static int getAss_app_id() {
        return ass_app_id;
    }

    public static Applications applications;

    public static Applications getApplications() {
        return applications;
    }

    public static int selectedNewAssess;
    public static int selectedApp;

    public static int getSelectedNewAssess() {
        return selectedNewAssess;
    }

    public static int getSelectedApp() {
        return selectedApp;
    }

    public static void setApplications(Applications applications) {
        StaticBadu.applications = applications;
    }

    public static void setSelectedApp(int selectedApp) {
        StaticBadu.selectedApp = selectedApp;
    }

    public static void setSelectedNewAssess(int selectedNewAssess) {
        StaticBadu.selectedNewAssess = selectedNewAssess;
    }

    public static SearchHolder getSh() {
        return sh;
    }

    public static AppLaters appLaters;

    public static void setAppLaters(AppLaters appLaters) {
        StaticBadu.appLaters = appLaters;
    }

    public static AppLaters getAppLaters() {
        return appLaters;
    }
}
