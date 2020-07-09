package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * AdvBoardType generated by hbm2java
 */
public class AdvBoardType  implements java.io.Serializable {


     private Integer idAdvBoardType;
     private String advBoardTypeName;
     private Double advBordTypePrice;
     private Integer advBordTypeStatus;
     private Integer advBordTypeSyn;
     private Set<AdvBords> advBordses = new HashSet<AdvBords>(0);

    public AdvBoardType() {
    }

    public AdvBoardType(String advBoardTypeName, Double advBordTypePrice, Integer advBordTypeStatus, Integer advBordTypeSyn, Set<AdvBords> advBordses) {
       this.advBoardTypeName = advBoardTypeName;
       this.advBordTypePrice = advBordTypePrice;
       this.advBordTypeStatus = advBordTypeStatus;
       this.advBordTypeSyn = advBordTypeSyn;
       this.advBordses = advBordses;
    }
   
    public Integer getIdAdvBoardType() {
        return this.idAdvBoardType;
    }
    
    public void setIdAdvBoardType(Integer idAdvBoardType) {
        this.idAdvBoardType = idAdvBoardType;
    }
    public String getAdvBoardTypeName() {
        return this.advBoardTypeName;
    }
    
    public void setAdvBoardTypeName(String advBoardTypeName) {
        this.advBoardTypeName = advBoardTypeName;
    }
    public Double getAdvBordTypePrice() {
        return this.advBordTypePrice;
    }
    
    public void setAdvBordTypePrice(Double advBordTypePrice) {
        this.advBordTypePrice = advBordTypePrice;
    }
    public Integer getAdvBordTypeStatus() {
        return this.advBordTypeStatus;
    }
    
    public void setAdvBordTypeStatus(Integer advBordTypeStatus) {
        this.advBordTypeStatus = advBordTypeStatus;
    }
    public Integer getAdvBordTypeSyn() {
        return this.advBordTypeSyn;
    }
    
    public void setAdvBordTypeSyn(Integer advBordTypeSyn) {
        this.advBordTypeSyn = advBordTypeSyn;
    }
    public Set<AdvBords> getAdvBordses() {
        return this.advBordses;
    }
    
    public void setAdvBordses(Set<AdvBords> advBordses) {
        this.advBordses = advBordses;
    }




}


