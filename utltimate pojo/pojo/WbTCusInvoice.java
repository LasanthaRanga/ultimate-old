package pojo;
// Generated Oct 3, 2019 9:56:57 AM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * WbTCusInvoice generated by hbm2java
 */
public class WbTCusInvoice  implements java.io.Serializable {


     private Integer id;
     private WbMCustomer wbMCustomer;
     private String invoiceNo;
     private Date invoiceDate;
     private String month;
     private Integer consumption;
     private Double waterCharge;
     private Double tax;
     private Double fixCharge;
     private Double totalPayable;
     private Double payment;
     private Date dateCreated;
     private String cretedBy;
     private String status;
     private String payMethod;
     private String chequeNo;
     private Double arrearsToday;
     private Integer year;
     private Integer createYear;

    public WbTCusInvoice() {
    }

	
    public WbTCusInvoice(WbMCustomer wbMCustomer, String invoiceNo, Date invoiceDate, Date dateCreated, String cretedBy, String status, String payMethod) {
        this.wbMCustomer = wbMCustomer;
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.dateCreated = dateCreated;
        this.cretedBy = cretedBy;
        this.status = status;
        this.payMethod = payMethod;
    }
    public WbTCusInvoice(WbMCustomer wbMCustomer, String invoiceNo, Date invoiceDate, String month, Integer consumption, Double waterCharge, Double tax, Double fixCharge, Double totalPayable, Double payment, Date dateCreated, String cretedBy, String status, String payMethod, String chequeNo, Double arrearsToday, Integer year, Integer createYear) {
       this.wbMCustomer = wbMCustomer;
       this.invoiceNo = invoiceNo;
       this.invoiceDate = invoiceDate;
       this.month = month;
       this.consumption = consumption;
       this.waterCharge = waterCharge;
       this.tax = tax;
       this.fixCharge = fixCharge;
       this.totalPayable = totalPayable;
       this.payment = payment;
       this.dateCreated = dateCreated;
       this.cretedBy = cretedBy;
       this.status = status;
       this.payMethod = payMethod;
       this.chequeNo = chequeNo;
       this.arrearsToday = arrearsToday;
       this.year = year;
       this.createYear = createYear;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public WbMCustomer getWbMCustomer() {
        return this.wbMCustomer;
    }
    
    public void setWbMCustomer(WbMCustomer wbMCustomer) {
        this.wbMCustomer = wbMCustomer;
    }
    public String getInvoiceNo() {
        return this.invoiceNo;
    }
    
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
    public Date getInvoiceDate() {
        return this.invoiceDate;
    }
    
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    public String getMonth() {
        return this.month;
    }
    
    public void setMonth(String month) {
        this.month = month;
    }
    public Integer getConsumption() {
        return this.consumption;
    }
    
    public void setConsumption(Integer consumption) {
        this.consumption = consumption;
    }
    public Double getWaterCharge() {
        return this.waterCharge;
    }
    
    public void setWaterCharge(Double waterCharge) {
        this.waterCharge = waterCharge;
    }
    public Double getTax() {
        return this.tax;
    }
    
    public void setTax(Double tax) {
        this.tax = tax;
    }
    public Double getFixCharge() {
        return this.fixCharge;
    }
    
    public void setFixCharge(Double fixCharge) {
        this.fixCharge = fixCharge;
    }
    public Double getTotalPayable() {
        return this.totalPayable;
    }
    
    public void setTotalPayable(Double totalPayable) {
        this.totalPayable = totalPayable;
    }
    public Double getPayment() {
        return this.payment;
    }
    
    public void setPayment(Double payment) {
        this.payment = payment;
    }
    public Date getDateCreated() {
        return this.dateCreated;
    }
    
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    public String getCretedBy() {
        return this.cretedBy;
    }
    
    public void setCretedBy(String cretedBy) {
        this.cretedBy = cretedBy;
    }
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPayMethod() {
        return this.payMethod;
    }
    
    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }
    public String getChequeNo() {
        return this.chequeNo;
    }
    
    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }
    public Double getArrearsToday() {
        return this.arrearsToday;
    }
    
    public void setArrearsToday(Double arrearsToday) {
        this.arrearsToday = arrearsToday;
    }
    public Integer getYear() {
        return this.year;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getCreateYear() {
        return this.createYear;
    }
    
    public void setCreateYear(Integer createYear) {
        this.createYear = createYear;
    }




}


