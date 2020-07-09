package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Program generated by hbm2java
 */
public class Program  implements java.io.Serializable {


     private Integer idProgram;
     private String nameProgram;
     private String nameSinhala;
     private String programCode;
     private Integer programOder;
     private String programcol;
     private Integer statusProgram;
     private Integer synProgram;
     private Set<ExTitle> exTitles = new HashSet<ExTitle>(0);
     private Set<ProgramTitle> programTitles = new HashSet<ProgramTitle>(0);
     private Set<ProgramTitleCopy> programTitleCopies = new HashSet<ProgramTitleCopy>(0);

    public Program() {
    }

    public Program(String nameProgram, String nameSinhala, String programCode, Integer programOder, String programcol, Integer statusProgram, Integer synProgram, Set<ExTitle> exTitles, Set<ProgramTitle> programTitles, Set<ProgramTitleCopy> programTitleCopies) {
       this.nameProgram = nameProgram;
       this.nameSinhala = nameSinhala;
       this.programCode = programCode;
       this.programOder = programOder;
       this.programcol = programcol;
       this.statusProgram = statusProgram;
       this.synProgram = synProgram;
       this.exTitles = exTitles;
       this.programTitles = programTitles;
       this.programTitleCopies = programTitleCopies;
    }
   
    public Integer getIdProgram() {
        return this.idProgram;
    }
    
    public void setIdProgram(Integer idProgram) {
        this.idProgram = idProgram;
    }
    public String getNameProgram() {
        return this.nameProgram;
    }
    
    public void setNameProgram(String nameProgram) {
        this.nameProgram = nameProgram;
    }
    public String getNameSinhala() {
        return this.nameSinhala;
    }
    
    public void setNameSinhala(String nameSinhala) {
        this.nameSinhala = nameSinhala;
    }
    public String getProgramCode() {
        return this.programCode;
    }
    
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }
    public Integer getProgramOder() {
        return this.programOder;
    }
    
    public void setProgramOder(Integer programOder) {
        this.programOder = programOder;
    }
    public String getProgramcol() {
        return this.programcol;
    }
    
    public void setProgramcol(String programcol) {
        this.programcol = programcol;
    }
    public Integer getStatusProgram() {
        return this.statusProgram;
    }
    
    public void setStatusProgram(Integer statusProgram) {
        this.statusProgram = statusProgram;
    }
    public Integer getSynProgram() {
        return this.synProgram;
    }
    
    public void setSynProgram(Integer synProgram) {
        this.synProgram = synProgram;
    }
    public Set<ExTitle> getExTitles() {
        return this.exTitles;
    }
    
    public void setExTitles(Set<ExTitle> exTitles) {
        this.exTitles = exTitles;
    }
    public Set<ProgramTitle> getProgramTitles() {
        return this.programTitles;
    }
    
    public void setProgramTitles(Set<ProgramTitle> programTitles) {
        this.programTitles = programTitles;
    }
    public Set<ProgramTitleCopy> getProgramTitleCopies() {
        return this.programTitleCopies;
    }
    
    public void setProgramTitleCopies(Set<ProgramTitleCopy> programTitleCopies) {
        this.programTitleCopies = programTitleCopies;
    }




}


