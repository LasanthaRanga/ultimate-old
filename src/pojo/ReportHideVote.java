package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * ReportHideVote generated by hbm2java
 */
public class ReportHideVote  implements java.io.Serializable {


     private Integer hideVoteId;
     private String hideVote;

    public ReportHideVote() {
    }

    public ReportHideVote(String hideVote) {
       this.hideVote = hideVote;
    }
   
    public Integer getHideVoteId() {
        return this.hideVoteId;
    }
    
    public void setHideVoteId(Integer hideVoteId) {
        this.hideVoteId = hideVoteId;
    }
    public String getHideVote() {
        return this.hideVote;
    }
    
    public void setHideVote(String hideVote) {
        this.hideVote = hideVote;
    }




}


