package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * TlAppLicenceNoTbl generated by hbm2java
 */
public class TlAppLicenceNoTbl  implements java.io.Serializable {


     private int tlAppLicenceNoId;
     private TlSubType tlSubType;
     private String tlAppLicenceNo;

    public TlAppLicenceNoTbl() {
    }

	
    public TlAppLicenceNoTbl(int tlAppLicenceNoId) {
        this.tlAppLicenceNoId = tlAppLicenceNoId;
    }
    public TlAppLicenceNoTbl(int tlAppLicenceNoId, TlSubType tlSubType, String tlAppLicenceNo) {
       this.tlAppLicenceNoId = tlAppLicenceNoId;
       this.tlSubType = tlSubType;
       this.tlAppLicenceNo = tlAppLicenceNo;
    }
   
    public int getTlAppLicenceNoId() {
        return this.tlAppLicenceNoId;
    }
    
    public void setTlAppLicenceNoId(int tlAppLicenceNoId) {
        this.tlAppLicenceNoId = tlAppLicenceNoId;
    }
    public TlSubType getTlSubType() {
        return this.tlSubType;
    }
    
    public void setTlSubType(TlSubType tlSubType) {
        this.tlSubType = tlSubType;
    }
    public String getTlAppLicenceNo() {
        return this.tlAppLicenceNo;
    }
    
    public void setTlAppLicenceNo(String tlAppLicenceNo) {
        this.tlAppLicenceNo = tlAppLicenceNo;
    }




}

