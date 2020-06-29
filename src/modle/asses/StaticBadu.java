package modle.asses;

import java.util.Date;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class StaticBadu {

    private static pojo.Assessment assessment;
    private static Integer idAssessment;
    private static String payid;
    private static String payTot;
    private static int cusid;
    
    

    /**
     * @return the assessment
     */
    public static pojo.Assessment getAssessment() {
        return assessment;
    }

    /**
     * @param aAssessment the assessment to set
     */
    public static void setAssessment(pojo.Assessment aAssessment) {
        assessment = aAssessment;
    }

    /**
     * @return the idAssessment
     */
    public static Integer getIdAssessment() {
        return idAssessment;
    }

    /**
     * @param aIdAssessment the idAssessment to set
     */
    public static void setIdAssessment(Integer aIdAssessment) {
        idAssessment = aIdAssessment;
    }

    /**
     * @return the payid
     */
    public static String getPayid() {
        return payid;
    }

    /**
     * @param aPayid the payid to set
     */
    public static void setPayid(String aPayid) {
        payid = aPayid;
    }

    /**
     * @return the payTot
     */
    public static String getPayTot() {
        return payTot;
    }

    /**
     * @param aPayTot the payTot to set
     */
    public static void setPayTot(String aPayTot) {
        payTot = aPayTot;
    }

    /**
     * @return the cusid
     */
    public static int getCusid() {
        return cusid;
    }

    /**
     * @param aCusid the cusid to set
     */
    public static void setCusid(int aCusid) {
        cusid = aCusid;
    }

    /**
     * @return the selectedSystemDate
     */
}
