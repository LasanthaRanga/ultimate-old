package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * ExVoteId generated by hbm2java
 */
public class ExVoteId  implements java.io.Serializable {


     private int idExAccountReceiptTitle;
     private String exVoteAndBal;

    public ExVoteId() {
    }

    public ExVoteId(int idExAccountReceiptTitle, String exVoteAndBal) {
       this.idExAccountReceiptTitle = idExAccountReceiptTitle;
       this.exVoteAndBal = exVoteAndBal;
    }
   
    public int getIdExAccountReceiptTitle() {
        return this.idExAccountReceiptTitle;
    }
    
    public void setIdExAccountReceiptTitle(int idExAccountReceiptTitle) {
        this.idExAccountReceiptTitle = idExAccountReceiptTitle;
    }
    public String getExVoteAndBal() {
        return this.exVoteAndBal;
    }
    
    public void setExVoteAndBal(String exVoteAndBal) {
        this.exVoteAndBal = exVoteAndBal;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ExVoteId) ) return false;
		 ExVoteId castOther = ( ExVoteId ) other; 
         
		 return (this.getIdExAccountReceiptTitle()==castOther.getIdExAccountReceiptTitle())
 && ( (this.getExVoteAndBal()==castOther.getExVoteAndBal()) || ( this.getExVoteAndBal()!=null && castOther.getExVoteAndBal()!=null && this.getExVoteAndBal().equals(castOther.getExVoteAndBal()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdExAccountReceiptTitle();
         result = 37 * result + ( getExVoteAndBal() == null ? 0 : this.getExVoteAndBal().hashCode() );
         return result;
   }   


}

