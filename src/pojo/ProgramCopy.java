package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * ProgramCopy generated by hbm2java
 */
public class ProgramCopy  implements java.io.Serializable {


     private Integer idProgram;
     private String name;
     private String programcol;
     private Integer status;
     private Integer syn;

    public ProgramCopy() {
    }

    public ProgramCopy(String name, String programcol, Integer status, Integer syn) {
       this.name = name;
       this.programcol = programcol;
       this.status = status;
       this.syn = syn;
    }
   
    public Integer getIdProgram() {
        return this.idProgram;
    }
    
    public void setIdProgram(Integer idProgram) {
        this.idProgram = idProgram;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getProgramcol() {
        return this.programcol;
    }
    
    public void setProgramcol(String programcol) {
        this.programcol = programcol;
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




}


