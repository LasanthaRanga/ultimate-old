package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * ExSubtitle generated by hbm2java
 */
public class ExSubtitle  implements java.io.Serializable {


     private Integer idexprogramSubtitle;
     private String exSubProgramTitle;
     private String exSubProgramNameSin;
     private String exSubProgramNameEng;
     private String exSubProgramNameTamil;
     private Integer exSubProgramStatus;
     private Integer exSubProgramSyn;
     private int exProgramTitleTitle;
     private Integer exIncomeOrExpence;

    public ExSubtitle() {
    }

	
    public ExSubtitle(int exProgramTitleTitle) {
        this.exProgramTitleTitle = exProgramTitleTitle;
    }
    public ExSubtitle(String exSubProgramTitle, String exSubProgramNameSin, String exSubProgramNameEng, String exSubProgramNameTamil, Integer exSubProgramStatus, Integer exSubProgramSyn, int exProgramTitleTitle, Integer exIncomeOrExpence) {
       this.exSubProgramTitle = exSubProgramTitle;
       this.exSubProgramNameSin = exSubProgramNameSin;
       this.exSubProgramNameEng = exSubProgramNameEng;
       this.exSubProgramNameTamil = exSubProgramNameTamil;
       this.exSubProgramStatus = exSubProgramStatus;
       this.exSubProgramSyn = exSubProgramSyn;
       this.exProgramTitleTitle = exProgramTitleTitle;
       this.exIncomeOrExpence = exIncomeOrExpence;
    }
   
    public Integer getIdexprogramSubtitle() {
        return this.idexprogramSubtitle;
    }
    
    public void setIdexprogramSubtitle(Integer idexprogramSubtitle) {
        this.idexprogramSubtitle = idexprogramSubtitle;
    }
    public String getExSubProgramTitle() {
        return this.exSubProgramTitle;
    }
    
    public void setExSubProgramTitle(String exSubProgramTitle) {
        this.exSubProgramTitle = exSubProgramTitle;
    }
    public String getExSubProgramNameSin() {
        return this.exSubProgramNameSin;
    }
    
    public void setExSubProgramNameSin(String exSubProgramNameSin) {
        this.exSubProgramNameSin = exSubProgramNameSin;
    }
    public String getExSubProgramNameEng() {
        return this.exSubProgramNameEng;
    }
    
    public void setExSubProgramNameEng(String exSubProgramNameEng) {
        this.exSubProgramNameEng = exSubProgramNameEng;
    }
    public String getExSubProgramNameTamil() {
        return this.exSubProgramNameTamil;
    }
    
    public void setExSubProgramNameTamil(String exSubProgramNameTamil) {
        this.exSubProgramNameTamil = exSubProgramNameTamil;
    }
    public Integer getExSubProgramStatus() {
        return this.exSubProgramStatus;
    }
    
    public void setExSubProgramStatus(Integer exSubProgramStatus) {
        this.exSubProgramStatus = exSubProgramStatus;
    }
    public Integer getExSubProgramSyn() {
        return this.exSubProgramSyn;
    }
    
    public void setExSubProgramSyn(Integer exSubProgramSyn) {
        this.exSubProgramSyn = exSubProgramSyn;
    }
    public int getExProgramTitleTitle() {
        return this.exProgramTitleTitle;
    }
    
    public void setExProgramTitleTitle(int exProgramTitleTitle) {
        this.exProgramTitleTitle = exProgramTitleTitle;
    }
    public Integer getExIncomeOrExpence() {
        return this.exIncomeOrExpence;
    }
    
    public void setExIncomeOrExpence(Integer exIncomeOrExpence) {
        this.exIncomeOrExpence = exIncomeOrExpence;
    }




}


