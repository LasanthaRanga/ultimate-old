package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * TlAppCreateNo generated by hbm2java
 */
public class TlAppCreateNo  implements java.io.Serializable {


     private Integer tlAppCreateNoId;
     private Office office;
     private String tlAppCreateNo;
     private Integer tlAppYear;
     private Integer tlCreateStatus;

    public TlAppCreateNo() {
    }

    public TlAppCreateNo(Office office, String tlAppCreateNo, Integer tlAppYear, Integer tlCreateStatus) {
       this.office = office;
       this.tlAppCreateNo = tlAppCreateNo;
       this.tlAppYear = tlAppYear;
       this.tlCreateStatus = tlCreateStatus;
    }
   
    public Integer getTlAppCreateNoId() {
        return this.tlAppCreateNoId;
    }
    
    public void setTlAppCreateNoId(Integer tlAppCreateNoId) {
        this.tlAppCreateNoId = tlAppCreateNoId;
    }
    public Office getOffice() {
        return this.office;
    }
    
    public void setOffice(Office office) {
        this.office = office;
    }
    public String getTlAppCreateNo() {
        return this.tlAppCreateNo;
    }
    
    public void setTlAppCreateNo(String tlAppCreateNo) {
        this.tlAppCreateNo = tlAppCreateNo;
    }
    public Integer getTlAppYear() {
        return this.tlAppYear;
    }
    
    public void setTlAppYear(Integer tlAppYear) {
        this.tlAppYear = tlAppYear;
    }
    public Integer getTlCreateStatus() {
        return this.tlCreateStatus;
    }
    
    public void setTlCreateStatus(Integer tlCreateStatus) {
        this.tlCreateStatus = tlCreateStatus;
    }




}


