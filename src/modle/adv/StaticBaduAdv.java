/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modle.adv;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class StaticBaduAdv {

    private static pojo.Customer customer;
    private static int sendToApprovID;
    private static int advApplicationID = 0;

    /**
     * @return the customer
     */
    public static pojo.Customer getCustomer() {
        return customer;
    }

    /**
     * @param aCustomer the customer to set
     */
    public static void setCustomer(pojo.Customer aCustomer) {
        customer = aCustomer;
    }

    /**
     * @return the sendToApprovID
     */
    public static int getSendToApprovID() {
        return sendToApprovID;
    }

    /**
     * @param aSendToApprovID the sendToApprovID to set
     */
    public static void setSendToApprovID(int aSendToApprovID) {
        sendToApprovID = aSendToApprovID;
    }

    /**
     * @return the advApplicationID
     */
    public static int getAdvApplicationID() {
        return advApplicationID;
    }

    /**
     * @param aAdvApplicationID the advApplicationID to set
     */
    public static void setAdvApplicationID(int aAdvApplicationID) {
        advApplicationID = aAdvApplicationID;
    }

    

}
