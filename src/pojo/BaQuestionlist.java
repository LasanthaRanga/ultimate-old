package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * BaQuestionlist generated by hbm2java
 */
public class BaQuestionlist  implements java.io.Serializable {


     private Integer baQuestionId;
     private String baQuestion;
     private Integer baStatus;
     private Integer baLoadStatus;

    public BaQuestionlist() {
    }

    public BaQuestionlist(String baQuestion, Integer baStatus, Integer baLoadStatus) {
       this.baQuestion = baQuestion;
       this.baStatus = baStatus;
       this.baLoadStatus = baLoadStatus;
    }
   
    public Integer getBaQuestionId() {
        return this.baQuestionId;
    }
    
    public void setBaQuestionId(Integer baQuestionId) {
        this.baQuestionId = baQuestionId;
    }
    public String getBaQuestion() {
        return this.baQuestion;
    }
    
    public void setBaQuestion(String baQuestion) {
        this.baQuestion = baQuestion;
    }
    public Integer getBaStatus() {
        return this.baStatus;
    }
    
    public void setBaStatus(Integer baStatus) {
        this.baStatus = baStatus;
    }
    public Integer getBaLoadStatus() {
        return this.baLoadStatus;
    }
    
    public void setBaLoadStatus(Integer baLoadStatus) {
        this.baLoadStatus = baLoadStatus;
    }




}


