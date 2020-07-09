package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * SubNature generated by hbm2java
 */
public class SubNature  implements java.io.Serializable {


     private Integer idSubNature;
     private TradeNature tradeNature;
     private String subNatureNo;
     private String subNature;
     private Integer status;
     private Integer syn;
     private Set<Application> applications = new HashSet<Application>(0);

    public SubNature() {
    }

	
    public SubNature(TradeNature tradeNature) {
        this.tradeNature = tradeNature;
    }
    public SubNature(TradeNature tradeNature, String subNatureNo, String subNature, Integer status, Integer syn, Set<Application> applications) {
       this.tradeNature = tradeNature;
       this.subNatureNo = subNatureNo;
       this.subNature = subNature;
       this.status = status;
       this.syn = syn;
       this.applications = applications;
    }
   
    public Integer getIdSubNature() {
        return this.idSubNature;
    }
    
    public void setIdSubNature(Integer idSubNature) {
        this.idSubNature = idSubNature;
    }
    public TradeNature getTradeNature() {
        return this.tradeNature;
    }
    
    public void setTradeNature(TradeNature tradeNature) {
        this.tradeNature = tradeNature;
    }
    public String getSubNatureNo() {
        return this.subNatureNo;
    }
    
    public void setSubNatureNo(String subNatureNo) {
        this.subNatureNo = subNatureNo;
    }
    public String getSubNature() {
        return this.subNature;
    }
    
    public void setSubNature(String subNature) {
        this.subNature = subNature;
    }
    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getSyn() {
        return this.syn;
    }
    
    public void setSyn(Integer syn) {
        this.syn = syn;
    }
    public Set<Application> getApplications() {
        return this.applications;
    }
    
    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }




}


