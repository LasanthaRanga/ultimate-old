package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * Aha generated by hbm2java
 */
public class Aha  implements java.io.Serializable {


     private Integer idAha;
     private Integer appcatId;
     private Integer bankinfoId;
     private Integer officeId;
     private Integer ahaStatus;

    public Aha() {
    }

    public Aha(Integer appcatId, Integer bankinfoId, Integer officeId, Integer ahaStatus) {
       this.appcatId = appcatId;
       this.bankinfoId = bankinfoId;
       this.officeId = officeId;
       this.ahaStatus = ahaStatus;
    }
   
    public Integer getIdAha() {
        return this.idAha;
    }
    
    public void setIdAha(Integer idAha) {
        this.idAha = idAha;
    }
    public Integer getAppcatId() {
        return this.appcatId;
    }
    
    public void setAppcatId(Integer appcatId) {
        this.appcatId = appcatId;
    }
    public Integer getBankinfoId() {
        return this.bankinfoId;
    }
    
    public void setBankinfoId(Integer bankinfoId) {
        this.bankinfoId = bankinfoId;
    }
    public Integer getOfficeId() {
        return this.officeId;
    }
    
    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }
    public Integer getAhaStatus() {
        return this.ahaStatus;
    }
    
    public void setAhaStatus(Integer ahaStatus) {
        this.ahaStatus = ahaStatus;
    }




}


