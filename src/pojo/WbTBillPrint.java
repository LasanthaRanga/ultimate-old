package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * WbTBillPrint generated by hbm2java
 */
public class WbTBillPrint  implements java.io.Serializable {


     private Integer id;
     private String cusId;
     private Integer month;
     private String invoiceNo;
     private String status;

    public WbTBillPrint() {
    }

	
    public WbTBillPrint(String invoiceNo, String status) {
        this.invoiceNo = invoiceNo;
        this.status = status;
    }
    public WbTBillPrint(String cusId, Integer month, String invoiceNo, String status) {
       this.cusId = cusId;
       this.month = month;
       this.invoiceNo = invoiceNo;
       this.status = status;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCusId() {
        return this.cusId;
    }
    
    public void setCusId(String cusId) {
        this.cusId = cusId;
    }
    public Integer getMonth() {
        return this.month;
    }
    
    public void setMonth(Integer month) {
        this.month = month;
    }
    public String getInvoiceNo() {
        return this.invoiceNo;
    }
    
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }




}


