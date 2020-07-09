package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * AssessmentHasProcesstype generated by hbm2java
 */
public class AssessmentHasProcesstype  implements java.io.Serializable {


     private Integer idAssessmentHasProcesstype;
     private Assessment assessment;
     private Processtype processtype;
     private User user;
     private Date assessmentHasProcesstypeDate;
     private Integer assessmentHasProcesstypeStatus;
     private String assessmentHasProcesstypeComent;

    public AssessmentHasProcesstype() {
    }

	
    public AssessmentHasProcesstype(Assessment assessment, Processtype processtype) {
        this.assessment = assessment;
        this.processtype = processtype;
    }
    public AssessmentHasProcesstype(Assessment assessment, Processtype processtype, User user, Date assessmentHasProcesstypeDate, Integer assessmentHasProcesstypeStatus, String assessmentHasProcesstypeComent) {
       this.assessment = assessment;
       this.processtype = processtype;
       this.user = user;
       this.assessmentHasProcesstypeDate = assessmentHasProcesstypeDate;
       this.assessmentHasProcesstypeStatus = assessmentHasProcesstypeStatus;
       this.assessmentHasProcesstypeComent = assessmentHasProcesstypeComent;
    }
   
    public Integer getIdAssessmentHasProcesstype() {
        return this.idAssessmentHasProcesstype;
    }
    
    public void setIdAssessmentHasProcesstype(Integer idAssessmentHasProcesstype) {
        this.idAssessmentHasProcesstype = idAssessmentHasProcesstype;
    }
    public Assessment getAssessment() {
        return this.assessment;
    }
    
    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }
    public Processtype getProcesstype() {
        return this.processtype;
    }
    
    public void setProcesstype(Processtype processtype) {
        this.processtype = processtype;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public Date getAssessmentHasProcesstypeDate() {
        return this.assessmentHasProcesstypeDate;
    }
    
    public void setAssessmentHasProcesstypeDate(Date assessmentHasProcesstypeDate) {
        this.assessmentHasProcesstypeDate = assessmentHasProcesstypeDate;
    }
    public Integer getAssessmentHasProcesstypeStatus() {
        return this.assessmentHasProcesstypeStatus;
    }
    
    public void setAssessmentHasProcesstypeStatus(Integer assessmentHasProcesstypeStatus) {
        this.assessmentHasProcesstypeStatus = assessmentHasProcesstypeStatus;
    }
    public String getAssessmentHasProcesstypeComent() {
        return this.assessmentHasProcesstypeComent;
    }
    
    public void setAssessmentHasProcesstypeComent(String assessmentHasProcesstypeComent) {
        this.assessmentHasProcesstypeComent = assessmentHasProcesstypeComent;
    }




}


