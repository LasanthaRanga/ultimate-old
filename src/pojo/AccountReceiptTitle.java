package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * AccountReceiptTitle generated by hbm2java
 */
public class AccountReceiptTitle  implements java.io.Serializable {


     private Integer idAccountReceiptTitle;
     private String artVoteAndBal;
     private String artProNameEng;
     private Integer artProCode;
     private String artTitleNameEng;
     private Integer atrTitleCode;
     private String artSubtitleNameEng;
     private Integer artSubtitleCode;
     private Integer artType1Id;
     private String artType1Name;
     private Integer artType2Id;
     private String artType2Name;
     private Integer artVoteOrBal;
     private Integer artOrder;
     private Integer artVBId;
     private String artProNameSinhala;
     private String artTitleNameSinhala;
     private String artSubtitleNameSinhala;
     private String artVoteAndBalCode;
     private String artVoteAndBalName;
     private String artVoteAndBalNameSinhala;
     private Integer artProjectId;
     private Integer artTitleId;
     private Integer artSubTitleId;
     private String artProNameTamil;
     private String artSubtitleNameTamil;
     private String artVoteAndBalNameTamil;
     private String artProjectNameSin;
     private String artProjectNameEng;
     private String artProjectNameTamil;
     private String artProId;
     private Integer artVoteTypeStatus;
     private Integer exIncomeOrExpence;
     private Set<Mixintype> mixintypes = new HashSet<Mixintype>(0);

    public AccountReceiptTitle() {
    }

	
    public AccountReceiptTitle(String artVoteAndBal) {
        this.artVoteAndBal = artVoteAndBal;
    }
    public AccountReceiptTitle(String artVoteAndBal, String artProNameEng, Integer artProCode, String artTitleNameEng, Integer atrTitleCode, String artSubtitleNameEng, Integer artSubtitleCode, Integer artType1Id, String artType1Name, Integer artType2Id, String artType2Name, Integer artVoteOrBal, Integer artOrder, Integer artVBId, String artProNameSinhala, String artTitleNameSinhala, String artSubtitleNameSinhala, String artVoteAndBalCode, String artVoteAndBalName, String artVoteAndBalNameSinhala, Integer artProjectId, Integer artTitleId, Integer artSubTitleId, String artProNameTamil, String artSubtitleNameTamil, String artVoteAndBalNameTamil, String artProjectNameSin, String artProjectNameEng, String artProjectNameTamil, String artProId, Integer artVoteTypeStatus, Integer exIncomeOrExpence, Set<Mixintype> mixintypes) {
       this.artVoteAndBal = artVoteAndBal;
       this.artProNameEng = artProNameEng;
       this.artProCode = artProCode;
       this.artTitleNameEng = artTitleNameEng;
       this.atrTitleCode = atrTitleCode;
       this.artSubtitleNameEng = artSubtitleNameEng;
       this.artSubtitleCode = artSubtitleCode;
       this.artType1Id = artType1Id;
       this.artType1Name = artType1Name;
       this.artType2Id = artType2Id;
       this.artType2Name = artType2Name;
       this.artVoteOrBal = artVoteOrBal;
       this.artOrder = artOrder;
       this.artVBId = artVBId;
       this.artProNameSinhala = artProNameSinhala;
       this.artTitleNameSinhala = artTitleNameSinhala;
       this.artSubtitleNameSinhala = artSubtitleNameSinhala;
       this.artVoteAndBalCode = artVoteAndBalCode;
       this.artVoteAndBalName = artVoteAndBalName;
       this.artVoteAndBalNameSinhala = artVoteAndBalNameSinhala;
       this.artProjectId = artProjectId;
       this.artTitleId = artTitleId;
       this.artSubTitleId = artSubTitleId;
       this.artProNameTamil = artProNameTamil;
       this.artSubtitleNameTamil = artSubtitleNameTamil;
       this.artVoteAndBalNameTamil = artVoteAndBalNameTamil;
       this.artProjectNameSin = artProjectNameSin;
       this.artProjectNameEng = artProjectNameEng;
       this.artProjectNameTamil = artProjectNameTamil;
       this.artProId = artProId;
       this.artVoteTypeStatus = artVoteTypeStatus;
       this.exIncomeOrExpence = exIncomeOrExpence;
       this.mixintypes = mixintypes;
    }
   
    public Integer getIdAccountReceiptTitle() {
        return this.idAccountReceiptTitle;
    }
    
    public void setIdAccountReceiptTitle(Integer idAccountReceiptTitle) {
        this.idAccountReceiptTitle = idAccountReceiptTitle;
    }
    public String getArtVoteAndBal() {
        return this.artVoteAndBal;
    }
    
    public void setArtVoteAndBal(String artVoteAndBal) {
        this.artVoteAndBal = artVoteAndBal;
    }
    public String getArtProNameEng() {
        return this.artProNameEng;
    }
    
    public void setArtProNameEng(String artProNameEng) {
        this.artProNameEng = artProNameEng;
    }
    public Integer getArtProCode() {
        return this.artProCode;
    }
    
    public void setArtProCode(Integer artProCode) {
        this.artProCode = artProCode;
    }
    public String getArtTitleNameEng() {
        return this.artTitleNameEng;
    }
    
    public void setArtTitleNameEng(String artTitleNameEng) {
        this.artTitleNameEng = artTitleNameEng;
    }
    public Integer getAtrTitleCode() {
        return this.atrTitleCode;
    }
    
    public void setAtrTitleCode(Integer atrTitleCode) {
        this.atrTitleCode = atrTitleCode;
    }
    public String getArtSubtitleNameEng() {
        return this.artSubtitleNameEng;
    }
    
    public void setArtSubtitleNameEng(String artSubtitleNameEng) {
        this.artSubtitleNameEng = artSubtitleNameEng;
    }
    public Integer getArtSubtitleCode() {
        return this.artSubtitleCode;
    }
    
    public void setArtSubtitleCode(Integer artSubtitleCode) {
        this.artSubtitleCode = artSubtitleCode;
    }
    public Integer getArtType1Id() {
        return this.artType1Id;
    }
    
    public void setArtType1Id(Integer artType1Id) {
        this.artType1Id = artType1Id;
    }
    public String getArtType1Name() {
        return this.artType1Name;
    }
    
    public void setArtType1Name(String artType1Name) {
        this.artType1Name = artType1Name;
    }
    public Integer getArtType2Id() {
        return this.artType2Id;
    }
    
    public void setArtType2Id(Integer artType2Id) {
        this.artType2Id = artType2Id;
    }
    public String getArtType2Name() {
        return this.artType2Name;
    }
    
    public void setArtType2Name(String artType2Name) {
        this.artType2Name = artType2Name;
    }
    public Integer getArtVoteOrBal() {
        return this.artVoteOrBal;
    }
    
    public void setArtVoteOrBal(Integer artVoteOrBal) {
        this.artVoteOrBal = artVoteOrBal;
    }
    public Integer getArtOrder() {
        return this.artOrder;
    }
    
    public void setArtOrder(Integer artOrder) {
        this.artOrder = artOrder;
    }
    public Integer getArtVBId() {
        return this.artVBId;
    }
    
    public void setArtVBId(Integer artVBId) {
        this.artVBId = artVBId;
    }
    public String getArtProNameSinhala() {
        return this.artProNameSinhala;
    }
    
    public void setArtProNameSinhala(String artProNameSinhala) {
        this.artProNameSinhala = artProNameSinhala;
    }
    public String getArtTitleNameSinhala() {
        return this.artTitleNameSinhala;
    }
    
    public void setArtTitleNameSinhala(String artTitleNameSinhala) {
        this.artTitleNameSinhala = artTitleNameSinhala;
    }
    public String getArtSubtitleNameSinhala() {
        return this.artSubtitleNameSinhala;
    }
    
    public void setArtSubtitleNameSinhala(String artSubtitleNameSinhala) {
        this.artSubtitleNameSinhala = artSubtitleNameSinhala;
    }
    public String getArtVoteAndBalCode() {
        return this.artVoteAndBalCode;
    }
    
    public void setArtVoteAndBalCode(String artVoteAndBalCode) {
        this.artVoteAndBalCode = artVoteAndBalCode;
    }
    public String getArtVoteAndBalName() {
        return this.artVoteAndBalName;
    }
    
    public void setArtVoteAndBalName(String artVoteAndBalName) {
        this.artVoteAndBalName = artVoteAndBalName;
    }
    public String getArtVoteAndBalNameSinhala() {
        return this.artVoteAndBalNameSinhala;
    }
    
    public void setArtVoteAndBalNameSinhala(String artVoteAndBalNameSinhala) {
        this.artVoteAndBalNameSinhala = artVoteAndBalNameSinhala;
    }
    public Integer getArtProjectId() {
        return this.artProjectId;
    }
    
    public void setArtProjectId(Integer artProjectId) {
        this.artProjectId = artProjectId;
    }
    public Integer getArtTitleId() {
        return this.artTitleId;
    }
    
    public void setArtTitleId(Integer artTitleId) {
        this.artTitleId = artTitleId;
    }
    public Integer getArtSubTitleId() {
        return this.artSubTitleId;
    }
    
    public void setArtSubTitleId(Integer artSubTitleId) {
        this.artSubTitleId = artSubTitleId;
    }
    public String getArtProNameTamil() {
        return this.artProNameTamil;
    }
    
    public void setArtProNameTamil(String artProNameTamil) {
        this.artProNameTamil = artProNameTamil;
    }
    public String getArtSubtitleNameTamil() {
        return this.artSubtitleNameTamil;
    }
    
    public void setArtSubtitleNameTamil(String artSubtitleNameTamil) {
        this.artSubtitleNameTamil = artSubtitleNameTamil;
    }
    public String getArtVoteAndBalNameTamil() {
        return this.artVoteAndBalNameTamil;
    }
    
    public void setArtVoteAndBalNameTamil(String artVoteAndBalNameTamil) {
        this.artVoteAndBalNameTamil = artVoteAndBalNameTamil;
    }
    public String getArtProjectNameSin() {
        return this.artProjectNameSin;
    }
    
    public void setArtProjectNameSin(String artProjectNameSin) {
        this.artProjectNameSin = artProjectNameSin;
    }
    public String getArtProjectNameEng() {
        return this.artProjectNameEng;
    }
    
    public void setArtProjectNameEng(String artProjectNameEng) {
        this.artProjectNameEng = artProjectNameEng;
    }
    public String getArtProjectNameTamil() {
        return this.artProjectNameTamil;
    }
    
    public void setArtProjectNameTamil(String artProjectNameTamil) {
        this.artProjectNameTamil = artProjectNameTamil;
    }
    public String getArtProId() {
        return this.artProId;
    }
    
    public void setArtProId(String artProId) {
        this.artProId = artProId;
    }
    public Integer getArtVoteTypeStatus() {
        return this.artVoteTypeStatus;
    }
    
    public void setArtVoteTypeStatus(Integer artVoteTypeStatus) {
        this.artVoteTypeStatus = artVoteTypeStatus;
    }
    public Integer getExIncomeOrExpence() {
        return this.exIncomeOrExpence;
    }
    
    public void setExIncomeOrExpence(Integer exIncomeOrExpence) {
        this.exIncomeOrExpence = exIncomeOrExpence;
    }
    public Set<Mixintype> getMixintypes() {
        return this.mixintypes;
    }
    
    public void setMixintypes(Set<Mixintype> mixintypes) {
        this.mixintypes = mixintypes;
    }




}


