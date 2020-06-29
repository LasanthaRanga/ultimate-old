package modle;

import modle.Payment.RiBillComplete;
import modle.Payment.Threweel;
import modle.account.AHA;
import modle.account.Program;
import modle.adv.Report;
import modle.asses.*;
import modle.mix.GenarateRecipt;
import modle.mix.MixPrasaThre;
import modle.popup.PrintBarcode;
import modle.shoprent.billComplete;
import modle.tradelicens.TL_Approve;
import modle.tradelicens.TL_Reports;
import modle.user.User_Login;

/**
 * @author RM.LasanthaRanga@gmail.com
 */
public class GetInstans {

    private static modle.user.Privilege privilege;
    private static User_Login user_Login;
    private static TableLoad tableLoad;
    private static Interest interest;
    private static modle.user.Dipartment dipartmentModle;
    //Addvertising
    private static modle.adv.Advertising advertisingModle;
    private static modle.adv.SendToApprove sendToApprove;
    private static modle.adv.Pay advPayModle;
    private static modle.adv.Position positionModle;
    private static modle.adv.customer advCustomerModle;
    private static modle.adv.BoardType boardTypeModle;
    private static Report advReport;

    //AssessMent
    private static modle.asses.Ward wardModle;
    private static modle.asses.Street streetModle;
    private static modle.asses.Assessmant assessmantModle;
    private static modle.asses.Nature nature;
    private static AssessReport assessReport;
    private static Discription discription;
    private static Quater quater;
    private static ProcessTypeChanging processTypeChanging;
    private static modle.Payment.RiBillComplete riBillComplete;


    //Trade Licens
    private static TL_Approve tl_approve;
    private static TL_Reports tl_reports;
    //Shop Rental
//    private static Building building;
//    private static Shop shop;


    //Account
    private static modle.account.Program program;

    //MIX
    private static modle.mix.GenarateRecipt genarateRecipt;

    //Popup
    private static modle.popup.PrintBarcode printBarcode;


    public static PrintBarcode getPrintBarcode() {
        if (printBarcode == null) {
            printBarcode = new PrintBarcode();
        }
        return printBarcode;
    }

    //BillComplete ShopRent
    private static modle.shoprent.billComplete billComplete;

    public static modle.shoprent.billComplete getBillComplete() {
        if (billComplete == null) {
            billComplete = new billComplete();
        }
        return billComplete;
    }

    //Three Wheel
    private static modle.Payment.Threweel threweel;

//    public static Shop getShop() {
//        return shop == null ? new Shop() : shop;
//    }

    //Bank
    private static Banks banks;

    public static modle.user.Privilege getPrivilege() {
        if (privilege == null) {
            privilege = new modle.user.Privilege();
        }
        return privilege;
    }

    /**
     * @return the tableLoad
     */
    public static TableLoad getTableLoad() {
        if (tableLoad == null) {
            tableLoad = new TableLoad();
        }
        return tableLoad;
    }

    /**
     * @return the wardModle
     */
    public static modle.asses.Ward getWardModle() {
        if (wardModle == null) {
            wardModle = new modle.asses.Ward();
        }
        return wardModle;
    }

    /**
     * @return the streetModle
     */
    public static modle.asses.Street getStreetModle() {
        if (streetModle == null) {
            streetModle = new modle.asses.Street();
        }
        return streetModle;
    }

    /**
     * @return the assessmantModle
     */
    public static modle.asses.Assessmant getAssessmantModle() {
        if (assessmantModle == null) {
            assessmantModle = new modle.asses.Assessmant();
        }
        return assessmantModle;
    }

    /**
     * @return the positionModle
     */
    public static modle.adv.Position getPositionModle() {
        if (positionModle == null) {
            positionModle = new modle.adv.Position();
        }
        return positionModle;
    }

    /**
     * @return the advCustomerModle
     */
    public static modle.adv.customer getAdvCustomerModle() {
        if (advCustomerModle == null) {
            advCustomerModle = new modle.adv.customer();
        }
        return advCustomerModle;
    }

    /**
     * @return the boardTypeModle
     */
    public static modle.adv.BoardType getBoardTypeModle() {
        if (boardTypeModle == null) {
            boardTypeModle = new modle.adv.BoardType();
        }
        return boardTypeModle;
    }

    /**
     * @return the interest
     */
    public static Interest getInterest() {
        if (interest == null) {
            interest = new Interest();
        }
        return interest;
    }

    /**
     * @return the advertisingModle
     */
    public static modle.adv.Advertising getAdvertisingModle() {
        if (advertisingModle == null) {
            advertisingModle = new modle.adv.Advertising();
        }
        return advertisingModle;
    }

    /**
     * @return the dipartmentModle
     */
    public static modle.user.Dipartment getDipartmentModle() {
        if (dipartmentModle == null) {
            dipartmentModle = new modle.user.Dipartment();
        }
        return dipartmentModle;
    }

    /**
     * @return the sendToApprove
     */
    public static modle.adv.SendToApprove getSendToApprove() {
        if (sendToApprove == null) {
            sendToApprove = new modle.adv.SendToApprove();
        }
        return sendToApprove;
    }

    /**
     * @return the advPayModle
     */
    public static modle.adv.Pay getAdvPayModle() {
        if (advPayModle == null) {
            advPayModle = new modle.adv.Pay();
        }
        return advPayModle;
    }

    /**
     * @return the nature
     */
    public static modle.asses.Nature getNature() {
        if (nature == null) {
            nature = new modle.asses.Nature();
        }
        return nature;
    }

    /**
     * @return the assessReport
     */
    public static AssessReport getAssessReport() {
        if (assessReport == null) {
            assessReport = new AssessReport();
        }
        return assessReport;
    }

    /**
     * @return the user_Login
     */
    public static User_Login getUser_Login() {
        if (user_Login == null) {
            user_Login = new User_Login();
        }
        return user_Login;
    }

    /**
     * @return the discription
     */
    public static Discription getDiscription() {
        if (discription == null) {
            discription = new Discription();
        }
        return discription;
    }

    /**
     * @return the quater
     */
    public static Quater getQuater() {
        if (quater == null) {
            quater = new Quater();
        }
        return quater;
    }

    /**
     * @return the banks
     */
    public static Banks getBanks() {
        if (banks == null) {
            banks = new Banks();
        }
        return banks;
    }

    /**
     * @return the processTypeChanging
     */
    public static ProcessTypeChanging getProcessTypeChanging() {
        if (processTypeChanging == null) {
            processTypeChanging = new ProcessTypeChanging();
        }
        return processTypeChanging;
    }

    /**
     * @return the TL_Approve
     */
    public static TL_Approve getTl_approve() {
        if (tl_approve == null) {
            tl_approve = new TL_Approve();
        }
        return tl_approve;
    }

    /**
     * @return the building
     */
//    public static Building getBuilding() {
//        if (building == null) {
//            building = new Building();
//        }
//        return building;
//    }
//
//    /**
//     * @param aBuilding the building to set
//     */
//    public static void setBuilding(Building aBuilding) {
//        building = aBuilding;
//    }
    public static Report getAdvReport() {
        if (advReport == null) {
            advReport = new Report();
        }
        return advReport;
    }


    public static TL_Reports getTl_reports() {
        if (tl_reports == null) {
            tl_reports = new TL_Reports();
        }
        return tl_reports;
    }

    public static modle.account.Program getProgram() {
        if (program == null) {
            program = new Program();
        }
        return program;
    }

    public static void setProgram(modle.account.Program program) {
        GetInstans.program = program;
    }

    public static modle.mix.GenarateRecipt getGenarateRecipt() {
        if (genarateRecipt == null) {
            genarateRecipt = new GenarateRecipt();
        }
        return genarateRecipt;
    }

    public static modle.Payment.Threweel getThreweel() {
        if (threweel == null) {
            threweel = new Threweel();
        }
        return threweel;
    }

    public static void setThreweel(modle.Payment.Threweel threweel) {
        GetInstans.threweel = threweel;
    }

    public static RiBillComplete getRiBillComplete() {
        if (riBillComplete == null) {
            riBillComplete = new RiBillComplete();
        }
        return riBillComplete;
    }

    //Office
    private static Office office;

    public static Office getOffice() {
        if (office == null) {
            office = new Office();
        }
        return office;
    }

    public static AHA getAha() {
        if (aha == null) {
            aha = new AHA();
        }
        return aha;
    }

    public static AHA aha;

    public static MixPrasaThre mixPrasaThre;


    public static GetArrias getArrias;

    public static GetArrias getGetArrias() {
        if (getArrias == null) {
            getArrias = new GetArrias();
        }
        return getArrias;
    }

    public static MixPrasaThre getMixPrasaThre() {
        if (mixPrasaThre == null) {
            mixPrasaThre = new MixPrasaThre();
        }
        return mixPrasaThre;
    }


    public static CDlist cdList;

    public static CDlist getCdlist() {
        if (cdList == null) {
            cdList = new CDlist();
        }
        return cdList;
    }

    public static AppType appType;

    public static AppType getAppType() {
        if (appType == null) {
            appType = new AppType();
        }
        return appType;
    }

    public static ApplicationsModle applicationsModle;

    public static ApplicationsModle getApplicationsModle() {
        if(applicationsModle==null){
            applicationsModle = new ApplicationsModle();
        }
        return applicationsModle;
    }
}
