package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * ProgramTitle generated by hbm2java
 */
public class ProgramTitle  implements java.io.Serializable {


     private Integer idProgramTitle;
     private Program program;
     private String titleNo;
     private String titleName;
     private Integer statusProgramTitle;
     private Integer synProgramTitle;
     private Set<ProgramSubtitleCopy> programSubtitleCopies = new HashSet<ProgramSubtitleCopy>(0);
     private Set<ProgramSubtitle> programSubtitles = new HashSet<ProgramSubtitle>(0);

    public ProgramTitle() {
    }

	
    public ProgramTitle(Program program) {
        this.program = program;
    }
    public ProgramTitle(Program program, String titleNo, String titleName, Integer statusProgramTitle, Integer synProgramTitle, Set<ProgramSubtitleCopy> programSubtitleCopies, Set<ProgramSubtitle> programSubtitles) {
       this.program = program;
       this.titleNo = titleNo;
       this.titleName = titleName;
       this.statusProgramTitle = statusProgramTitle;
       this.synProgramTitle = synProgramTitle;
       this.programSubtitleCopies = programSubtitleCopies;
       this.programSubtitles = programSubtitles;
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
    public Integer getStatusProgramTitle() {
        return this.statusProgramTitle;
    }
    
    public void setStatusProgramTitle(Integer statusProgramTitle) {
        this.statusProgramTitle = statusProgramTitle;
    }
    public Integer getSynProgramTitle() {
        return this.synProgramTitle;
    }
    
    public void setSynProgramTitle(Integer synProgramTitle) {
        this.synProgramTitle = synProgramTitle;
    }
    public Set<ProgramSubtitleCopy> getProgramSubtitleCopies() {
        return this.programSubtitleCopies;
    }
    
    public void setProgramSubtitleCopies(Set<ProgramSubtitleCopy> programSubtitleCopies) {
        this.programSubtitleCopies = programSubtitleCopies;
    }
    public Set<ProgramSubtitle> getProgramSubtitles() {
        return this.programSubtitles;
    }
    
    public void setProgramSubtitles(Set<ProgramSubtitle> programSubtitles) {
        this.programSubtitles = programSubtitles;
    }




}


