package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * TlPaidNotStatus generated by hbm2java
 */
public class TlPaidNotStatus  implements java.io.Serializable {


     private int tlPaidStatusId;
     private String tlPaidStatus;

    public TlPaidNotStatus() {
    }

	
    public TlPaidNotStatus(int tlPaidStatusId) {
        this.tlPaidStatusId = tlPaidStatusId;
    }
    public TlPaidNotStatus(int tlPaidStatusId, String tlPaidStatus) {
       this.tlPaidStatusId = tlPaidStatusId;
       this.tlPaidStatus = tlPaidStatus;
    }
   
    public int getTlPaidStatusId() {
        return this.tlPaidStatusId;
    }
    
    public void setTlPaidStatusId(int tlPaidStatusId) {
        this.tlPaidStatusId = tlPaidStatusId;
    }
    public String getTlPaidStatus() {
        return this.tlPaidStatus;
    }
    
    public void setTlPaidStatus(String tlPaidStatus) {
        this.tlPaidStatus = tlPaidStatus;
    }




}


