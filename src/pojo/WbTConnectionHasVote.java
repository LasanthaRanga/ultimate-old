package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * WbTConnectionHasVote generated by hbm2java
 */
public class WbTConnectionHasVote  implements java.io.Serializable {


     private Integer wbTConHasCatId;
     private WbMConnection wbMConnection;
     private WbTVoteCategory wbTVoteCategory;
     private Integer wbTConHasVoteStatus;

    public WbTConnectionHasVote() {
    }

    public WbTConnectionHasVote(WbMConnection wbMConnection, WbTVoteCategory wbTVoteCategory, Integer wbTConHasVoteStatus) {
       this.wbMConnection = wbMConnection;
       this.wbTVoteCategory = wbTVoteCategory;
       this.wbTConHasVoteStatus = wbTConHasVoteStatus;
    }
   
    public Integer getWbTConHasCatId() {
        return this.wbTConHasCatId;
    }
    
    public void setWbTConHasCatId(Integer wbTConHasCatId) {
        this.wbTConHasCatId = wbTConHasCatId;
    }
    public WbMConnection getWbMConnection() {
        return this.wbMConnection;
    }
    
    public void setWbMConnection(WbMConnection wbMConnection) {
        this.wbMConnection = wbMConnection;
    }
    public WbTVoteCategory getWbTVoteCategory() {
        return this.wbTVoteCategory;
    }
    
    public void setWbTVoteCategory(WbTVoteCategory wbTVoteCategory) {
        this.wbTVoteCategory = wbTVoteCategory;
    }
    public Integer getWbTConHasVoteStatus() {
        return this.wbTConHasVoteStatus;
    }
    
    public void setWbTConHasVoteStatus(Integer wbTConHasVoteStatus) {
        this.wbTConHasVoteStatus = wbTConHasVoteStatus;
    }




}


