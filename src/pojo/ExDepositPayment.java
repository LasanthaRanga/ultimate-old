package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * ExDepositPayment generated by hbm2java
 */
public class ExDepositPayment  implements java.io.Serializable {


     private Integer exTitlePaidId;
     private Integer exTitleVoucherInfoId;
     private Integer exDepositUserId;
     private Double exPayAmount;
     private Integer exActiveStatus;

    public ExDepositPayment() {
    }

    public ExDepositPayment(Integer exTitleVoucherInfoId, Integer exDepositUserId, Double exPayAmount, Integer exActiveStatus) {
       this.exTitleVoucherInfoId = exTitleVoucherInfoId;
       this.exDepositUserId = exDepositUserId;
       this.exPayAmount = exPayAmount;
       this.exActiveStatus = exActiveStatus;
    }
   
    public Integer getExTitlePaidId() {
        return this.exTitlePaidId;
    }
    
    public void setExTitlePaidId(Integer exTitlePaidId) {
        this.exTitlePaidId = exTitlePaidId;
    }
    public Integer getExTitleVoucherInfoId() {
        return this.exTitleVoucherInfoId;
    }
    
    public void setExTitleVoucherInfoId(Integer exTitleVoucherInfoId) {
        this.exTitleVoucherInfoId = exTitleVoucherInfoId;
    }
    public Integer getExDepositUserId() {
        return this.exDepositUserId;
    }
    
    public void setExDepositUserId(Integer exDepositUserId) {
        this.exDepositUserId = exDepositUserId;
    }
    public Double getExPayAmount() {
        return this.exPayAmount;
    }
    
    public void setExPayAmount(Double exPayAmount) {
        this.exPayAmount = exPayAmount;
    }
    public Integer getExActiveStatus() {
        return this.exActiveStatus;
    }
    
    public void setExActiveStatus(Integer exActiveStatus) {
        this.exActiveStatus = exActiveStatus;
    }




}


