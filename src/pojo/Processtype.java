package pojo;
// Generated Sep 6, 2019 4:30:16 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Processtype generated by hbm2java
 */
public class Processtype  implements java.io.Serializable {


     private Integer idProcesstype;
     private String processtypeName;
     private Set<AssessmentHasProcesstype> assessmentHasProcesstypes = new HashSet<AssessmentHasProcesstype>(0);

    public Processtype() {
    }

    public Processtype(String processtypeName, Set<AssessmentHasProcesstype> assessmentHasProcesstypes) {
       this.processtypeName = processtypeName;
       this.assessmentHasProcesstypes = assessmentHasProcesstypes;
    }
   
    public Integer getIdProcesstype() {
        return this.idProcesstype;
    }
    
    public void setIdProcesstype(Integer idProcesstype) {
        this.idProcesstype = idProcesstype;
    }
    public String getProcesstypeName() {
        return this.processtypeName;
    }
    
    public void setProcesstypeName(String processtypeName) {
        this.processtypeName = processtypeName;
    }
    public Set<AssessmentHasProcesstype> getAssessmentHasProcesstypes() {
        return this.assessmentHasProcesstypes;
    }
    
    public void setAssessmentHasProcesstypes(Set<AssessmentHasProcesstype> assessmentHasProcesstypes) {
        this.assessmentHasProcesstypes = assessmentHasProcesstypes;
    }




}


