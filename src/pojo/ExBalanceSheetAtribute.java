package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * ExBalanceSheetAtribute generated by hbm2java
 */
public class ExBalanceSheetAtribute  implements java.io.Serializable {


     private Integer exBalSheetAtributeId;
     private Integer exBalSheetAtributeSubtitleId;
     private Integer exBalSheetAtributeTypeId;
     private Integer exBalSheetAtributeSubTypeId;
     private Integer exBalSheetAtributeStatus;

    public ExBalanceSheetAtribute() {
    }

    public ExBalanceSheetAtribute(Integer exBalSheetAtributeSubtitleId, Integer exBalSheetAtributeTypeId, Integer exBalSheetAtributeSubTypeId, Integer exBalSheetAtributeStatus) {
       this.exBalSheetAtributeSubtitleId = exBalSheetAtributeSubtitleId;
       this.exBalSheetAtributeTypeId = exBalSheetAtributeTypeId;
       this.exBalSheetAtributeSubTypeId = exBalSheetAtributeSubTypeId;
       this.exBalSheetAtributeStatus = exBalSheetAtributeStatus;
    }
   
    public Integer getExBalSheetAtributeId() {
        return this.exBalSheetAtributeId;
    }
    
    public void setExBalSheetAtributeId(Integer exBalSheetAtributeId) {
        this.exBalSheetAtributeId = exBalSheetAtributeId;
    }
    public Integer getExBalSheetAtributeSubtitleId() {
        return this.exBalSheetAtributeSubtitleId;
    }
    
    public void setExBalSheetAtributeSubtitleId(Integer exBalSheetAtributeSubtitleId) {
        this.exBalSheetAtributeSubtitleId = exBalSheetAtributeSubtitleId;
    }
    public Integer getExBalSheetAtributeTypeId() {
        return this.exBalSheetAtributeTypeId;
    }
    
    public void setExBalSheetAtributeTypeId(Integer exBalSheetAtributeTypeId) {
        this.exBalSheetAtributeTypeId = exBalSheetAtributeTypeId;
    }
    public Integer getExBalSheetAtributeSubTypeId() {
        return this.exBalSheetAtributeSubTypeId;
    }
    
    public void setExBalSheetAtributeSubTypeId(Integer exBalSheetAtributeSubTypeId) {
        this.exBalSheetAtributeSubTypeId = exBalSheetAtributeSubTypeId;
    }
    public Integer getExBalSheetAtributeStatus() {
        return this.exBalSheetAtributeStatus;
    }
    
    public void setExBalSheetAtributeStatus(Integer exBalSheetAtributeStatus) {
        this.exBalSheetAtributeStatus = exBalSheetAtributeStatus;
    }




}


