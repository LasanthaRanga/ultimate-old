package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * SrShopSettings generated by hbm2java
 */
public class SrShopSettings  implements java.io.Serializable {


     private int srShopSettingId;
     private Integer srShopSettingPsOrMc;
     private String srShopSettingDescription;

    public SrShopSettings() {
    }

	
    public SrShopSettings(int srShopSettingId) {
        this.srShopSettingId = srShopSettingId;
    }
    public SrShopSettings(int srShopSettingId, Integer srShopSettingPsOrMc, String srShopSettingDescription) {
       this.srShopSettingId = srShopSettingId;
       this.srShopSettingPsOrMc = srShopSettingPsOrMc;
       this.srShopSettingDescription = srShopSettingDescription;
    }
   
    public int getSrShopSettingId() {
        return this.srShopSettingId;
    }
    
    public void setSrShopSettingId(int srShopSettingId) {
        this.srShopSettingId = srShopSettingId;
    }
    public Integer getSrShopSettingPsOrMc() {
        return this.srShopSettingPsOrMc;
    }
    
    public void setSrShopSettingPsOrMc(Integer srShopSettingPsOrMc) {
        this.srShopSettingPsOrMc = srShopSettingPsOrMc;
    }
    public String getSrShopSettingDescription() {
        return this.srShopSettingDescription;
    }
    
    public void setSrShopSettingDescription(String srShopSettingDescription) {
        this.srShopSettingDescription = srShopSettingDescription;
    }




}


