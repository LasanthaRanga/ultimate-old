package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * SrShopBeforeAddAmount generated by hbm2java
 */
public class SrShopBeforeAddAmount  implements java.io.Serializable {


     private int srShopBeforeAddAmountId;
     private Double srShopBeforeAddAmount;

    public SrShopBeforeAddAmount() {
    }

	
    public SrShopBeforeAddAmount(int srShopBeforeAddAmountId) {
        this.srShopBeforeAddAmountId = srShopBeforeAddAmountId;
    }
    public SrShopBeforeAddAmount(int srShopBeforeAddAmountId, Double srShopBeforeAddAmount) {
       this.srShopBeforeAddAmountId = srShopBeforeAddAmountId;
       this.srShopBeforeAddAmount = srShopBeforeAddAmount;
    }
   
    public int getSrShopBeforeAddAmountId() {
        return this.srShopBeforeAddAmountId;
    }
    
    public void setSrShopBeforeAddAmountId(int srShopBeforeAddAmountId) {
        this.srShopBeforeAddAmountId = srShopBeforeAddAmountId;
    }
    public Double getSrShopBeforeAddAmount() {
        return this.srShopBeforeAddAmount;
    }
    
    public void setSrShopBeforeAddAmount(Double srShopBeforeAddAmount) {
        this.srShopBeforeAddAmount = srShopBeforeAddAmount;
    }




}


