package pojo;
// Generated Oct 3, 2019 9:56:57 AM by Hibernate Tools 4.3.1



/**
 * WbMLastProcYearMonth generated by hbm2java
 */
public class WbMLastProcYearMonth  implements java.io.Serializable {


     private Integer lastProcYearMonthId;
     private WbMDevision wbMDevision;
     private Integer lastProcYearMonthYear;
     private Integer lastProcYearMonthMonth;
     private Integer nextProcYearMonthYear;
     private Integer nextProcYearMonthMonth;
     private String nextProcCodeFirst;

    public WbMLastProcYearMonth() {
    }

    public WbMLastProcYearMonth(WbMDevision wbMDevision, Integer lastProcYearMonthYear, Integer lastProcYearMonthMonth, Integer nextProcYearMonthYear, Integer nextProcYearMonthMonth, String nextProcCodeFirst) {
       this.wbMDevision = wbMDevision;
       this.lastProcYearMonthYear = lastProcYearMonthYear;
       this.lastProcYearMonthMonth = lastProcYearMonthMonth;
       this.nextProcYearMonthYear = nextProcYearMonthYear;
       this.nextProcYearMonthMonth = nextProcYearMonthMonth;
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
    public Integer getNextProcYearMonthYear() {
        return this.nextProcYearMonthYear;
    }
    
    public void setNextProcYearMonthYear(Integer nextProcYearMonthYear) {
        this.nextProcYearMonthYear = nextProcYearMonthYear;
    }
    public Integer getNextProcYearMonthMonth() {
        return this.nextProcYearMonthMonth;
    }
    
    public void setNextProcYearMonthMonth(Integer nextProcYearMonthMonth) {
        this.nextProcYearMonthMonth = nextProcYearMonthMonth;
    }
    public String getNextProcCodeFirst() {
        return this.nextProcCodeFirst;
    }
    
    public void setNextProcCodeFirst(String nextProcCodeFirst) {
        this.nextProcCodeFirst = nextProcCodeFirst;
    }




}


