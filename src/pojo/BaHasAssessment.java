package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * BaHasAssessment generated by hbm2java
 */
public class BaHasAssessment  implements java.io.Serializable {


     private Integer baAssessmentHasId;
     private Integer baId;
     private Integer baAssessmentId;

    public BaHasAssessment() {
    }

    public BaHasAssessment(Integer baId, Integer baAssessmentId) {
       this.baId = baId;
       this.baAssessmentId = baAssessmentId;
    }
   
    public Integer getBaAssessmentHasId() {
        return this.baAssessmentHasId;
    }
    
    public void setBaAssessmentHasId(Integer baAssessmentHasId) {
        this.baAssessmentHasId = baAssessmentHasId;
    }
    public Integer getBaId() {
        return this.baId;
    }
    
    public void setBaId(Integer baId) {
        this.baId = baId;
    }
    public Integer getBaAssessmentId() {
        return this.baAssessmentId;
    }
    
    public void setBaAssessmentId(Integer baAssessmentId) {
        this.baAssessmentId = baAssessmentId;
    }




}


