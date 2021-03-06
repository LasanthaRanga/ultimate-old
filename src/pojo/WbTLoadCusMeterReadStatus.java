package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * WbTLoadCusMeterReadStatus generated by hbm2java
 */
public class WbTLoadCusMeterReadStatus  implements java.io.Serializable {


     private Integer wbTInvoiceComId;
     private Integer cusMeterReadTblId;
     private String invoiceNo;
     private Integer comStatus;
     private Double totPayablePaid;
     private Double overPay;
     private Integer receiptIdCusMReadStatus;

    public WbTLoadCusMeterReadStatus() {
    }

    public WbTLoadCusMeterReadStatus(Integer cusMeterReadTblId, String invoiceNo, Integer comStatus, Double totPayablePaid, Double overPay, Integer receiptIdCusMReadStatus) {
       this.cusMeterReadTblId = cusMeterReadTblId;
       this.invoiceNo = invoiceNo;
       this.comStatus = comStatus;
       this.totPayablePaid = totPayablePaid;
       this.overPay = overPay;
       this.receiptIdCusMReadStatus = receiptIdCusMReadStatus;
    }
   
    public Integer getWbTInvoiceComId() {
        return this.wbTInvoiceComId;
    }
    
    public void setWbTInvoiceComId(Integer wbTInvoiceComId) {
        this.wbTInvoiceComId = wbTInvoiceComId;
    }
    public Integer getCusMeterReadTblId() {
        return this.cusMeterReadTblId;
    }
    
    public void setCusMeterReadTblId(Integer cusMeterReadTblId) {
        this.cusMeterReadTblId = cusMeterReadTblId;
    }
    public String getInvoiceNo() {
        return this.invoiceNo;
    }
    
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
    public Integer getComStatus() {
        return this.comStatus;
    }
    
    public void setComStatus(Integer comStatus) {
        this.comStatus = comStatus;
    }
    public Double getTotPayablePaid() {
        return this.totPayablePaid;
    }
    
    public void setTotPayablePaid(Double totPayablePaid) {
        this.totPayablePaid = totPayablePaid;
    }
    public Double getOverPay() {
        return this.overPay;
    }
    
    public void setOverPay(Double overPay) {
        this.overPay = overPay;
    }
    public Integer getReceiptIdCusMReadStatus() {
        return this.receiptIdCusMReadStatus;
    }
    
    public void setReceiptIdCusMReadStatus(Integer receiptIdCusMReadStatus) {
        this.receiptIdCusMReadStatus = receiptIdCusMReadStatus;
    }




}


