package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * JobAssign generated by hbm2java
 */
public class JobAssign  implements java.io.Serializable {


     private Integer idJobAssign;
     private Integer jobId;
     private Integer userId;
     private Integer subjectId;
     private Date dateTime;
     private Integer statusId;
     private Integer step;
     private String stepName;

    public JobAssign() {
    }

    public JobAssign(Integer jobId, Integer userId, Integer subjectId, Date dateTime, Integer statusId, Integer step, String stepName) {
       this.jobId = jobId;
       this.userId = userId;
       this.subjectId = subjectId;
       this.dateTime = dateTime;
       this.statusId = statusId;
       this.step = step;
       this.stepName = stepName;
    }
   
    public Integer getIdJobAssign() {
        return this.idJobAssign;
    }
    
    public void setIdJobAssign(Integer idJobAssign) {
        this.idJobAssign = idJobAssign;
    }
    public Integer getJobId() {
        return this.jobId;
    }
    
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getSubjectId() {
        return this.subjectId;
    }
    
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }
    public Date getDateTime() {
        return this.dateTime;
    }
    
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public Integer getStatusId() {
        return this.statusId;
    }
    
    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }
    public Integer getStep() {
        return this.step;
    }
    
    public void setStep(Integer step) {
        this.step = step;
    }
    public String getStepName() {
        return this.stepName;
    }
    
    public void setStepName(String stepName) {
        this.stepName = stepName;
    }




}


