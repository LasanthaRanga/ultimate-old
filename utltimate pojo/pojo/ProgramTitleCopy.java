package pojo;
// Generated Sep 6, 2019 4:30:16 PM by Hibernate Tools 4.3.1



/**
 * ProgramTitleCopy generated by hbm2java
 */
public class ProgramTitleCopy  implements java.io.Serializable {


     private Integer idProgramTitle;
     private Program program;
     private String titleNo;
     private String titleName;
     private Integer status;
     private Integer int_;

    public ProgramTitleCopy() {
    }

	
    public ProgramTitleCopy(Program program) {
        this.program = program;
    }
    public ProgramTitleCopy(Program program, String titleNo, String titleName, Integer status, Integer int_) {
       this.program = program;
       this.titleNo = titleNo;
       this.titleName = titleName;
       this.status = status;
       this.int_ = int_;
    }
   
    public Integer getIdProgramTitle() {
        return this.idProgramTitle;
    }
    
    public void setIdProgramTitle(Integer idProgramTitle) {
        this.idProgramTitle = idProgramTitle;
    }
    public Program getProgram() {
        return this.program;
    }
    
    public void setProgram(Program program) {
        this.program = program;
    }
    public String getTitleNo() {
        return this.titleNo;
    }
    
    public void setTitleNo(String titleNo) {
        this.titleNo = titleNo;
    }
    public String getTitleName() {
        return this.titleName;
    }
    
    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getInt_() {
        return this.int_;
    }
    
    public void setInt_(Integer int_) {
        this.int_ = int_;
    }




}


