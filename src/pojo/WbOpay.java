package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * WbOpay generated by hbm2java
 */
public class WbOpay  implements java.io.Serializable {


     private Integer wbOpayId;
     private WbMConnection wbMConnection;
     private Double wbOpayAmount;

    public WbOpay() {
    }

    public WbOpay(WbMConnection wbMConnection, Double wbOpayAmount) {
       this.wbMConnection = wbMConnection;
       this.wbOpayAmount = wbOpayAmount;
    }
   
    public Integer getWbOpayId() {
        return this.wbOpayId;
    }
    
    public void setWbOpayId(Integer wbOpayId) {
        this.wbOpayId = wbOpayId;
    }
    public WbMConnection getWbMConnection() {
        return this.wbMConnection;
    }
    
    public void setWbMConnection(WbMConnection wbMConnection) {
        this.wbMConnection = wbMConnection;
    }
    public Double getWbOpayAmount() {
        return this.wbOpayAmount;
    }
    
    public void setWbOpayAmount(Double wbOpayAmount) {
        this.wbOpayAmount = wbOpayAmount;
    }




}


