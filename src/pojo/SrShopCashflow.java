package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * SrShopCashflow generated by hbm2java
 */
public class SrShopCashflow  implements java.io.Serializable {


     private Integer srShopCashFlowId;
     private SrShopNow srShopNow;
     private Double srShopCashFlowCash;
     private Double srShopCashFlowCheque;
     private String srShopCashFlowChequeNo;
     private Integer srShopCashFlowBankId;
     private String srShopCashFlowReceiptNo;
     private Date srShopCashFlowChequeDate;
     private Date srShopCashFlowCreateDate;
     private Date srShopCashFlowTime;
     private Integer srShopCashFlowCompleteOrNot;
     private Double srShopCashFlowTotalAmount;
     private Integer srShopCashFlowPaidCashStatus;
     private Integer srShopCashFlowPaidChequeStatus;

    public SrShopCashflow() {
    }

    public SrShopCashflow(SrShopNow srShopNow, Double srShopCashFlowCash, Double srShopCashFlowCheque, String srShopCashFlowChequeNo, Integer srShopCashFlowBankId, String srShopCashFlowReceiptNo, Date srShopCashFlowChequeDate, Date srShopCashFlowCreateDate, Date srShopCashFlowTime, Integer srShopCashFlowCompleteOrNot, Double srShopCashFlowTotalAmount, Integer srShopCashFlowPaidCashStatus, Integer srShopCashFlowPaidChequeStatus) {
       this.srShopNow = srShopNow;
       this.srShopCashFlowCash = srShopCashFlowCash;
       this.srShopCashFlowCheque = srShopCashFlowCheque;
       this.srShopCashFlowChequeNo = srShopCashFlowChequeNo;
       this.srShopCashFlowBankId = srShopCashFlowBankId;
       this.srShopCashFlowReceiptNo = srShopCashFlowReceiptNo;
       this.srShopCashFlowChequeDate = srShopCashFlowChequeDate;
       this.srShopCashFlowCreateDate = srShopCashFlowCreateDate;
       this.srShopCashFlowTime = srShopCashFlowTime;
       this.srShopCashFlowCompleteOrNot = srShopCashFlowCompleteOrNot;
       this.srShopCashFlowTotalAmount = srShopCashFlowTotalAmount;
       this.srShopCashFlowPaidCashStatus = srShopCashFlowPaidCashStatus;
       this.srShopCashFlowPaidChequeStatus = srShopCashFlowPaidChequeStatus;
    }
   
    public Integer getSrShopCashFlowId() {
        return this.srShopCashFlowId;
    }
    
    public void setSrShopCashFlowId(Integer srShopCashFlowId) {
        this.srShopCashFlowId = srShopCashFlowId;
    }
    public SrShopNow getSrShopNow() {
        return this.srShopNow;
    }
    
    public void setSrShopNow(SrShopNow srShopNow) {
        this.srShopNow = srShopNow;
    }
    public Double getSrShopCashFlowCash() {
        return this.srShopCashFlowCash;
    }
    
    public void setSrShopCashFlowCash(Double srShopCashFlowCash) {
        this.srShopCashFlowCash = srShopCashFlowCash;
    }
    public Double getSrShopCashFlowCheque() {
        return this.srShopCashFlowCheque;
    }
    
    public void setSrShopCashFlowCheque(Double srShopCashFlowCheque) {
        this.srShopCashFlowCheque = srShopCashFlowCheque;
    }
    public String getSrShopCashFlowChequeNo() {
        return this.srShopCashFlowChequeNo;
    }
    
    public void setSrShopCashFlowChequeNo(String srShopCashFlowChequeNo) {
        this.srShopCashFlowChequeNo = srShopCashFlowChequeNo;
    }
    public Integer getSrShopCashFlowBankId() {
        return this.srShopCashFlowBankId;
    }
    
    public void setSrShopCashFlowBankId(Integer srShopCashFlowBankId) {
        this.srShopCashFlowBankId = srShopCashFlowBankId;
    }
    public String getSrShopCashFlowReceiptNo() {
        return this.srShopCashFlowReceiptNo;
    }
    
    public void setSrShopCashFlowReceiptNo(String srShopCashFlowReceiptNo) {
        this.srShopCashFlowReceiptNo = srShopCashFlowReceiptNo;
    }
    public Date getSrShopCashFlowChequeDate() {
        return this.srShopCashFlowChequeDate;
    }
    
    public void setSrShopCashFlowChequeDate(Date srShopCashFlowChequeDate) {
        this.srShopCashFlowChequeDate = srShopCashFlowChequeDate;
    }
    public Date getSrShopCashFlowCreateDate() {
        return this.srShopCashFlowCreateDate;
    }
    
    public void setSrShopCashFlowCreateDate(Date srShopCashFlowCreateDate) {
        this.srShopCashFlowCreateDate = srShopCashFlowCreateDate;
    }
    public Date getSrShopCashFlowTime() {
        return this.srShopCashFlowTime;
    }
    
    public void setSrShopCashFlowTime(Date srShopCashFlowTime) {
        this.srShopCashFlowTime = srShopCashFlowTime;
    }
    public Integer getSrShopCashFlowCompleteOrNot() {
        return this.srShopCashFlowCompleteOrNot;
    }
    
    public void setSrShopCashFlowCompleteOrNot(Integer srShopCashFlowCompleteOrNot) {
        this.srShopCashFlowCompleteOrNot = srShopCashFlowCompleteOrNot;
    }
    public Double getSrShopCashFlowTotalAmount() {
        return this.srShopCashFlowTotalAmount;
    }
    
    public void setSrShopCashFlowTotalAmount(Double srShopCashFlowTotalAmount) {
        this.srShopCashFlowTotalAmount = srShopCashFlowTotalAmount;
    }
    public Integer getSrShopCashFlowPaidCashStatus() {
        return this.srShopCashFlowPaidCashStatus;
    }
    
    public void setSrShopCashFlowPaidCashStatus(Integer srShopCashFlowPaidCashStatus) {
        this.srShopCashFlowPaidCashStatus = srShopCashFlowPaidCashStatus;
    }
    public Integer getSrShopCashFlowPaidChequeStatus() {
        return this.srShopCashFlowPaidChequeStatus;
    }
    
    public void setSrShopCashFlowPaidChequeStatus(Integer srShopCashFlowPaidChequeStatus) {
        this.srShopCashFlowPaidChequeStatus = srShopCashFlowPaidChequeStatus;
    }




}


