package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * SrShopVotesCategory generated by hbm2java
 */
public class SrShopVotesCategory  implements java.io.Serializable {


     private Integer srShopVoteCategoryId;
     private String srShopVoteCategoryName;
     private Integer srShopVotePayment;
     private Integer srShopVoteArrears;
     private Integer srShopVoteFine;
     private Integer srShopVoteOverpayment;
     private Integer srShopVoteServiceCharge1;
     private Integer srShopVoteServiceCharge2;
     private Integer srShopVoteServiceCharge3;
     private Integer srShopVoteAccountId;
     private Integer srShopVoteVat;
     private Integer srShopVoteStamp;
     private Integer srShopVoteNbt;
     private Integer srShopVoteVatVoteId;
     private Integer srShopVoteStampVoteId;
     private Integer srShopVoteNbtVoteId;
     private Integer srCashVote;
     private Integer srChequeVote;
     private Integer srShopVoteLya;
     private Integer srShopVoteLyf;
     private Set<SrShopHasVote> srShopHasVotes = new HashSet<SrShopHasVote>(0);

    public SrShopVotesCategory() {
    }

    public SrShopVotesCategory(String srShopVoteCategoryName, Integer srShopVotePayment, Integer srShopVoteArrears, Integer srShopVoteFine, Integer srShopVoteOverpayment, Integer srShopVoteServiceCharge1, Integer srShopVoteServiceCharge2, Integer srShopVoteServiceCharge3, Integer srShopVoteAccountId, Integer srShopVoteVat, Integer srShopVoteStamp, Integer srShopVoteNbt, Integer srShopVoteVatVoteId, Integer srShopVoteStampVoteId, Integer srShopVoteNbtVoteId, Integer srCashVote, Integer srChequeVote, Integer srShopVoteLya, Integer srShopVoteLyf, Set<SrShopHasVote> srShopHasVotes) {
       this.srShopVoteCategoryName = srShopVoteCategoryName;
       this.srShopVotePayment = srShopVotePayment;
       this.srShopVoteArrears = srShopVoteArrears;
       this.srShopVoteFine = srShopVoteFine;
       this.srShopVoteOverpayment = srShopVoteOverpayment;
       this.srShopVoteServiceCharge1 = srShopVoteServiceCharge1;
       this.srShopVoteServiceCharge2 = srShopVoteServiceCharge2;
       this.srShopVoteServiceCharge3 = srShopVoteServiceCharge3;
       this.srShopVoteAccountId = srShopVoteAccountId;
       this.srShopVoteVat = srShopVoteVat;
       this.srShopVoteStamp = srShopVoteStamp;
       this.srShopVoteNbt = srShopVoteNbt;
       this.srShopVoteVatVoteId = srShopVoteVatVoteId;
       this.srShopVoteStampVoteId = srShopVoteStampVoteId;
       this.srShopVoteNbtVoteId = srShopVoteNbtVoteId;
       this.srCashVote = srCashVote;
       this.srChequeVote = srChequeVote;
       this.srShopVoteLya = srShopVoteLya;
       this.srShopVoteLyf = srShopVoteLyf;
       this.srShopHasVotes = srShopHasVotes;
    }
   
    public Integer getSrShopVoteCategoryId() {
        return this.srShopVoteCategoryId;
    }
    
    public void setSrShopVoteCategoryId(Integer srShopVoteCategoryId) {
        this.srShopVoteCategoryId = srShopVoteCategoryId;
    }
    public String getSrShopVoteCategoryName() {
        return this.srShopVoteCategoryName;
    }
    
    public void setSrShopVoteCategoryName(String srShopVoteCategoryName) {
        this.srShopVoteCategoryName = srShopVoteCategoryName;
    }
    public Integer getSrShopVotePayment() {
        return this.srShopVotePayment;
    }
    
    public void setSrShopVotePayment(Integer srShopVotePayment) {
        this.srShopVotePayment = srShopVotePayment;
    }
    public Integer getSrShopVoteArrears() {
        return this.srShopVoteArrears;
    }
    
    public void setSrShopVoteArrears(Integer srShopVoteArrears) {
        this.srShopVoteArrears = srShopVoteArrears;
    }
    public Integer getSrShopVoteFine() {
        return this.srShopVoteFine;
    }
    
    public void setSrShopVoteFine(Integer srShopVoteFine) {
        this.srShopVoteFine = srShopVoteFine;
    }
    public Integer getSrShopVoteOverpayment() {
        return this.srShopVoteOverpayment;
    }
    
    public void setSrShopVoteOverpayment(Integer srShopVoteOverpayment) {
        this.srShopVoteOverpayment = srShopVoteOverpayment;
    }
    public Integer getSrShopVoteServiceCharge1() {
        return this.srShopVoteServiceCharge1;
    }
    
    public void setSrShopVoteServiceCharge1(Integer srShopVoteServiceCharge1) {
        this.srShopVoteServiceCharge1 = srShopVoteServiceCharge1;
    }
    public Integer getSrShopVoteServiceCharge2() {
        return this.srShopVoteServiceCharge2;
    }
    
    public void setSrShopVoteServiceCharge2(Integer srShopVoteServiceCharge2) {
        this.srShopVoteServiceCharge2 = srShopVoteServiceCharge2;
    }
    public Integer getSrShopVoteServiceCharge3() {
        return this.srShopVoteServiceCharge3;
    }
    
    public void setSrShopVoteServiceCharge3(Integer srShopVoteServiceCharge3) {
        this.srShopVoteServiceCharge3 = srShopVoteServiceCharge3;
    }
    public Integer getSrShopVoteAccountId() {
        return this.srShopVoteAccountId;
    }
    
    public void setSrShopVoteAccountId(Integer srShopVoteAccountId) {
        this.srShopVoteAccountId = srShopVoteAccountId;
    }
    public Integer getSrShopVoteVat() {
        return this.srShopVoteVat;
    }
    
    public void setSrShopVoteVat(Integer srShopVoteVat) {
        this.srShopVoteVat = srShopVoteVat;
    }
    public Integer getSrShopVoteStamp() {
        return this.srShopVoteStamp;
    }
    
    public void setSrShopVoteStamp(Integer srShopVoteStamp) {
        this.srShopVoteStamp = srShopVoteStamp;
    }
    public Integer getSrShopVoteNbt() {
        return this.srShopVoteNbt;
    }
    
    public void setSrShopVoteNbt(Integer srShopVoteNbt) {
        this.srShopVoteNbt = srShopVoteNbt;
    }
    public Integer getSrShopVoteVatVoteId() {
        return this.srShopVoteVatVoteId;
    }
    
    public void setSrShopVoteVatVoteId(Integer srShopVoteVatVoteId) {
        this.srShopVoteVatVoteId = srShopVoteVatVoteId;
    }
    public Integer getSrShopVoteStampVoteId() {
        return this.srShopVoteStampVoteId;
    }
    
    public void setSrShopVoteStampVoteId(Integer srShopVoteStampVoteId) {
        this.srShopVoteStampVoteId = srShopVoteStampVoteId;
    }
    public Integer getSrShopVoteNbtVoteId() {
        return this.srShopVoteNbtVoteId;
    }
    
    public void setSrShopVoteNbtVoteId(Integer srShopVoteNbtVoteId) {
        this.srShopVoteNbtVoteId = srShopVoteNbtVoteId;
    }
    public Integer getSrCashVote() {
        return this.srCashVote;
    }
    
    public void setSrCashVote(Integer srCashVote) {
        this.srCashVote = srCashVote;
    }
    public Integer getSrChequeVote() {
        return this.srChequeVote;
    }
    
    public void setSrChequeVote(Integer srChequeVote) {
        this.srChequeVote = srChequeVote;
    }
    public Integer getSrShopVoteLya() {
        return this.srShopVoteLya;
    }
    
    public void setSrShopVoteLya(Integer srShopVoteLya) {
        this.srShopVoteLya = srShopVoteLya;
    }
    public Integer getSrShopVoteLyf() {
        return this.srShopVoteLyf;
    }
    
    public void setSrShopVoteLyf(Integer srShopVoteLyf) {
        this.srShopVoteLyf = srShopVoteLyf;
    }
    public Set<SrShopHasVote> getSrShopHasVotes() {
        return this.srShopHasVotes;
    }
    
    public void setSrShopHasVotes(Set<SrShopHasVote> srShopHasVotes) {
        this.srShopHasVotes = srShopHasVotes;
    }




}


