package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * WbMLastProcYearMonth generated by hbm2java
 */
public class WbMLastProcYearMonth  implements java.io.Serializable {


     private Integer lastProcYearMonthId;
     private WbMDevision wbMDevision;
     private Integer lastProcYearMonthYear;
     private Integer lastProcYearMonthMonth;
     private String nextProcCodeFirst;

    public WbMLastProcYearMonth() {
    }

    public WbMLastProcYearMonth(WbMDevision wbMDevision, Integer lastProcYearMonthYear, Integer lastProcYearMonthMonth, String nextProcCodeFirst) {
       this.wbMDevision = wbMDevision;
       this.lastProcYearMonthYear = lastProcYearMonthYear;
       this.lastProcYearMonthMonth = lastProcYearMonthMonth;
       this.nextProcCodeFirst = nextProcCodeFirst;
    }
   
    public Integer getLastProcYearMonthId() {
        return this.lastProcYearMonthId;
    }
    
    public void setLastProcYearMonthId(Integer lastProcYearMonthId) {
        this.lastProcYearMonthId = lastProcYearMonthId;
    }
    public WbMDevision getWbMDevision() {
        return this.wbMDevision;
    }
    
    public void setWbMDevision(WbMDevision wbMDevision) {
        this.wbMDevision = wbMDevision;
    }
    public Integer getLastProcYearMonthYear() {
        return this.lastProcYearMonthYear;
    }
    
    public void setLastProcYearMonthYear(Integer lastProcYearMonthYear) {
        this.lastProcYearMonthYear = lastProcYearMonthYear;
    }
    public Integer getLastProcYearMonthMonth() {
        return this.lastProcYearMonthMonth;
    }
    
    public void setLastProcYearMonthMonth(Integer lastProcYearMonthMonth) {
        this.lastProcYearMonthMonth = lastProcYearMonthMonth;
    }
    public String getNextProcCodeFirst() {
        return this.nextProcCodeFirst;
    }
    
    public void setNextProcCodeFirst(String nextProcCodeFirst) {
        this.nextProcCodeFirst = nextProcCodeFirst;
    }




}


