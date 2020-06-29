package pojo;
// Generated Sep 6, 2019 4:30:16 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * SrShopPayment generated by hbm2java
 */
public class SrShopPayment  implements java.io.Serializable {


     private Integer srShopPaidId;
     private SrShop srShop;
     private SrShopProc srShopProc;
     private Integer srShopProcYear;
     private Integer srShopProcMonth;
     private Double srShopPaidArrearsAmount;
     private Double srShopPaidArrearsBal;
     private Double srShopPaidFine;
     private Double srShopPaidFineBal;
     private Double srShopPaidRentalTot;
     private Double srShopPaidRentalTotBal;
     private Double srShopPaidOverPay;
     private Double srShopPaidOverPayBal;
     private Double srShopPaidVat;
     private Double srShopPaidNbt;
     private Double srShopPaidStamp;
     private Double srShopPaidSc1;
     private Double srShopPaidSc2;
     private Double srShopPaidSc3;
     private Double srShopPaidRent;
     private String srShopPaidProcComplete;
     private String srReceiptNo;
     private Date srPaidDate;
     private Date srCreatePaymentTime;
     private Integer srShopPaymentCompleteOrNot;
     private Integer srShopPaidUser;
     private Integer srShopReceiptOrder;

    public SrShopPayment() {
    }

	
    public SrShopPayment(SrShopProc srShopProc) {
        this.srShopProc = srShopProc;
    }
    public SrShopPayment(SrShop srShop, SrShopProc srShopProc, Integer srShopProcYear, Integer srShopProcMonth, Double srShopPaidArrearsAmount, Double srShopPaidArrearsBal, Double srShopPaidFine, Double srShopPaidFineBal, Double srShopPaidRentalTot, Double srShopPaidRentalTotBal, Double srShopPaidOverPay, Double srShopPaidOverPayBal, Double srShopPaidVat, Double srShopPaidNbt, Double srShopPaidStamp, Double srShopPaidSc1, Double srShopPaidSc2, Double srShopPaidSc3, Double srShopPaidRent, String srShopPaidProcComplete, String srReceiptNo, Date srPaidDate, Date srCreatePaymentTime, Integer srShopPaymentCompleteOrNot, Integer srShopPaidUser, Integer srShopReceiptOrder) {
       this.srShop = srShop;
       this.srShopProc = srShopProc;
       this.srShopProcYear = srShopProcYear;
       this.srShopProcMonth = srShopProcMonth;
       this.srShopPaidArrearsAmount = srShopPaidArrearsAmount;
       this.srShopPaidArrearsBal = srShopPaidArrearsBal;
       this.srShopPaidFine = srShopPaidFine;
       this.srShopPaidFineBal = srShopPaidFineBal;
       this.srShopPaidRentalTot = srShopPaidRentalTot;
       this.srShopPaidRentalTotBal = srShopPaidRentalTotBal;
       this.srShopPaidOverPay = srShopPaidOverPay;
       this.srShopPaidOverPayBal = srShopPaidOverPayBal;
       this.srShopPaidVat = srShopPaidVat;
       this.srShopPaidNbt = srShopPaidNbt;
       this.srShopPaidStamp = srShopPaidStamp;
       this.srShopPaidSc1 = srShopPaidSc1;
       this.srShopPaidSc2 = srShopPaidSc2;
       this.srShopPaidSc3 = srShopPaidSc3;
       this.srShopPaidRent = srShopPaidRent;
       this.srShopPaidProcComplete = srShopPaidProcComplete;
       this.srReceiptNo = srReceiptNo;
       this.srPaidDate = srPaidDate;
       this.srCreatePaymentTime = srCreatePaymentTime;
       this.srShopPaymentCompleteOrNot = srShopPaymentCompleteOrNot;
       this.srShopPaidUser = srShopPaidUser;
       this.srShopReceiptOrder = srShopReceiptOrder;
    }
   
    public Integer getSrShopPaidId() {
        return this.srShopPaidId;
    }
    
    public void setSrShopPaidId(Integer srShopPaidId) {
        this.srShopPaidId = srShopPaidId;
    }
    public SrShop getSrShop() {
        return this.srShop;
    }
    
    public void setSrShop(SrShop srShop) {
        this.srShop = srShop;
    }
    public SrShopProc getSrShopProc() {
        return this.srShopProc;
    }
    
    public void setSrShopProc(SrShopProc srShopProc) {
        this.srShopProc = srShopProc;
    }
    public Integer getSrShopProcYear() {
        return this.srShopProcYear;
    }
    
    public void setSrShopProcYear(Integer srShopProcYear) {
        this.srShopProcYear = srShopProcYear;
    }
    public Integer getSrShopProcMonth() {
        return this.srShopProcMonth;
    }
    
    public void setSrShopProcMonth(Integer srShopProcMonth) {
        this.srShopProcMonth = srShopProcMonth;
    }
    public Double getSrShopPaidArrearsAmount() {
        return this.srShopPaidArrearsAmount;
    }
    
    public void setSrShopPaidArrearsAmount(Double srShopPaidArrearsAmount) {
        this.srShopPaidArrearsAmount = srShopPaidArrearsAmount;
    }
    public Double getSrShopPaidArrearsBal() {
        return this.srShopPaidArrearsBal;
    }
    
    public void setSrShopPaidArrearsBal(Double srShopPaidArrearsBal) {
        this.srShopPaidArrearsBal = srShopPaidArrearsBal;
    }
    public Double getSrShopPaidFine() {
        return this.srShopPaidFine;
    }
    
    public void setSrShopPaidFine(Double srShopPaidFine) {
        this.srShopPaidFine = srShopPaidFine;
    }
    public Double getSrShopPaidFineBal() {
        return this.srShopPaidFineBal;
    }
    
    public void setSrShopPaidFineBal(Double srShopPaidFineBal) {
        this.srShopPaidFineBal = srShopPaidFineBal;
    }
    public Double getSrShopPaidRentalTot() {
        return this.srShopPaidRentalTot;
    }
    
    public void setSrShopPaidRentalTot(Double srShopPaidRentalTot) {
        this.srShopPaidRentalTot = srShopPaidRentalTot;
    }
    public Double getSrShopPaidRentalTotBal() {
        return this.srShopPaidRentalTotBal;
    }
    
    public void setSrShopPaidRentalTotBal(Double srShopPaidRentalTotBal) {
        this.srShopPaidRentalTotBal = srShopPaidRentalTotBal;
    }
    public Double getSrShopPaidOverPay() {
        return this.srShopPaidOverPay;
    }
    
    public void setSrShopPaidOverPay(Double srShopPaidOverPay) {
        this.srShopPaidOverPay = srShopPaidOverPay;
    }
    public Double getSrShopPaidOverPayBal() {
        return this.srShopPaidOverPayBal;
    }
    
    public void setSrShopPaidOverPayBal(Double srShopPaidOverPayBal) {
        this.srShopPaidOverPayBal = srShopPaidOverPayBal;
    }
    public Double getSrShopPaidVat() {
        return this.srShopPaidVat;
    }
    
    public void setSrShopPaidVat(Double srShopPaidVat) {
        this.srShopPaidVat = srShopPaidVat;
    }
    public Double getSrShopPaidNbt() {
        return this.srShopPaidNbt;
    }
    
    public void setSrShopPaidNbt(Double srShopPaidNbt) {
        this.srShopPaidNbt = srShopPaidNbt;
    }
    public Double getSrShopPaidStamp() {
        return this.srShopPaidStamp;
    }
    
    public void setSrShopPaidStamp(Double srShopPaidStamp) {
        this.srShopPaidStamp = srShopPaidStamp;
    }
    public Double getSrShopPaidSc1() {
        return this.srShopPaidSc1;
    }
    
    public void setSrShopPaidSc1(Double srShopPaidSc1) {
        this.srShopPaidSc1 = srShopPaidSc1;
    }
    public Double getSrShopPaidSc2() {
        return this.srShopPaidSc2;
    }
    
    public void setSrShopPaidSc2(Double srShopPaidSc2) {
        this.srShopPaidSc2 = srShopPaidSc2;
    }
    public Double getSrShopPaidSc3() {
        return this.srShopPaidSc3;
    }
    
    public void setSrShopPaidSc3(Double srShopPaidSc3) {
        this.srShopPaidSc3 = srShopPaidSc3;
    }
    public Double getSrShopPaidRent() {
        return this.srShopPaidRent;
    }
    
    public void setSrShopPaidRent(Double srShopPaidRent) {
        this.srShopPaidRent = srShopPaidRent;
    }
    public String getSrShopPaidProcComplete() {
        return this.srShopPaidProcComplete;
    }
    
    public void setSrShopPaidProcComplete(String srShopPaidProcComplete) {
        this.srShopPaidProcComplete = srShopPaidProcComplete;
    }
    public String getSrReceiptNo() {
        return this.srReceiptNo;
    }
    
    public void setSrReceiptNo(String srReceiptNo) {
        this.srReceiptNo = srReceiptNo;
    }
    public Date getSrPaidDate() {
        return this.srPaidDate;
    }
    
    public void setSrPaidDate(Date srPaidDate) {
        this.srPaidDate = srPaidDate;
    }
    public Date getSrCreatePaymentTime() {
        return this.srCreatePaymentTime;
    }
    
    public void setSrCreatePaymentTime(Date srCreatePaymentTime) {
        this.srCreatePaymentTime = srCreatePaymentTime;
    }
    public Integer getSrShopPaymentCompleteOrNot() {
        return this.srShopPaymentCompleteOrNot;
    }
    
    public void setSrShopPaymentCompleteOrNot(Integer srShopPaymentCompleteOrNot) {
        this.srShopPaymentCompleteOrNot = srShopPaymentCompleteOrNot;
    }
    public Integer getSrShopPaidUser() {
        return this.srShopPaidUser;
    }
    
    public void setSrShopPaidUser(Integer srShopPaidUser) {
        this.srShopPaidUser = srShopPaidUser;
    }
    public Integer getSrShopReceiptOrder() {
        return this.srShopReceiptOrder;
    }
    
    public void setSrShopReceiptOrder(Integer srShopReceiptOrder) {
        this.srShopReceiptOrder = srShopReceiptOrder;
    }




}


