package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * ProgramSubtitle generated by hbm2java
 */
public class ProgramSubtitle  implements java.io.Serializable {


     private Integer idprogramSubtitle;
     private ProgramTitle programTitle;
     private String subProgramTitle;
     private String subProgramName;
     private Integer subProgramStatus;
     private Integer subProgramSyn;
     private Set<Vort> vorts = new HashSet<Vort>(0);
     private Set<Vote> votes = new HashSet<Vote>(0);

    public ProgramSubtitle() {
    }

	
    public ProgramSubtitle(ProgramTitle programTitle) {
        this.programTitle = programTitle;
    }
    public ProgramSubtitle(ProgramTitle programTitle, String subProgramTitle, String subProgramName, Integer subProgramStatus, Integer subProgramSyn, Set<Vort> vorts, Set<Vote> votes) {
       this.programTitle = programTitle;
       this.subProgramTitle = subProgramTitle;
       this.subProgramName = subProgramName;
       this.subProgramStatus = subProgramStatus;
       this.subProgramSyn = subProgramSyn;
       this.vorts = vorts;
       this.votes = votes;
    }
   
    public Integer getIdprogramSubtitle() {
        return this.idprogramSubtitle;
    }
    
    public void setIdprogramSubtitle(Integer idprogramSubtitle) {
        this.idprogramSubtitle = idprogramSubtitle;
    }
    public ProgramTitle getProgramTitle() {
        return this.programTitle;
    }
    
    public void setProgramTitle(ProgramTitle programTitle) {
        this.programTitle = programTitle;
    }
    public String getSubProgramTitle() {
        return this.subProgramTitle;
    }
    
    public void setSubProgramTitle(String subProgramTitle) {
        this.subProgramTitle = subProgramTitle;
    }
    public String getSubProgramName() {
        return this.subProgramName;
    }
    
    public void setSubProgramName(String subProgramName) {
        this.subProgramName = subProgramName;
    }
    public Integer getSubProgramStatus() {
        return this.subProgramStatus;
    }
    
    public void setSubProgramStatus(Integer subProgramStatus) {
        this.subProgramStatus = subProgramStatus;
    }
    public Integer getSubProgramSyn() {
        return this.subProgramSyn;
    }
    
    public void setSubProgramSyn(Integer subProgramSyn) {
        this.subProgramSyn = subProgramSyn;
    }
    public Set<Vort> getVorts() {
        return this.vorts;
    }
    
    public void setVorts(Set<Vort> vorts) {
        this.vorts = vorts;
    }
    public Set<Vote> getVotes() {
        return this.votes;
    }
    
    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }




}


