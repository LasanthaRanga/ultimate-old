package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * WbMConnection generated by hbm2java
 */
public class WbMConnection  implements java.io.Serializable {


     private Integer wbMConnectionId;
     private ApplicationCatagory applicationCatagory;
     private Assessment assessment;
     private WbMConnectionTypeDetail wbMConnectionTypeDetail;
     private WbMDevision wbMDevision;
     private WbMNature wbMNature;
     private WbMReadingProblems wbMReadingProblems;
     private String wbMCustomerId;
     private String wbMAddress;
     private Date wbMFirstConnectionDate;
     private Integer wbMStreet;
     private String wbMMeterNo;
     private Integer wbMOrderId;
     private Integer wbMAssignCustomerOrNot;
     private Integer wbMAssignDivisionOrNot;
     private Integer wbMOfficeId;
     private Integer wbCreateUser;
     private Date wbCreateDate;
     private Date wbCreateTime;
     private Set<WbMChangesRecordTbl> wbMChangesRecordTbls = new HashSet<WbMChangesRecordTbl>(0);
     private Set<WbMDataClearTbl> wbMDataClearTbls = new HashSet<WbMDataClearTbl>(0);
     private Set<WbOpay> wbOpays = new HashSet<WbOpay>(0);
     private Set<WbTPaid> wbTPaids = new HashSet<WbTPaid>(0);
     private Set<WbTConnectionHasVote> wbTConnectionHasVotes = new HashSet<WbTConnectionHasVote>(0);
     private Set<WbMMainOwner> wbMMainOwners = new HashSet<WbMMainOwner>(0);
     private Set<WbMNatureChangeTbl> wbMNatureChangeTbls = new HashSet<WbMNatureChangeTbl>(0);
     private Set<WbMOverpay> wbMOverpays = new HashSet<WbMOverpay>(0);
     private Set<WbMReadingProb> wbMReadingProbs = new HashSet<WbMReadingProb>(0);
     private Set<WbTMeterResetDetails> wbTMeterResetDetailses = new HashSet<WbTMeterResetDetails>(0);
     private Set<WbTCusMeterRead> wbTCusMeterReads = new HashSet<WbTCusMeterRead>(0);
     private Set<WbTPayment> wbTPayments = new HashSet<WbTPayment>(0);
     private Set<WbTBillProcess> wbTBillProcesses = new HashSet<WbTBillProcess>(0);
     private Set<WbProcessTbl> wbProcessTbls = new HashSet<WbProcessTbl>(0);
     private Set<WbMSubOwner> wbMSubOwners = new HashSet<WbMSubOwner>(0);

    public WbMConnection() {
    }

    public WbMConnection(ApplicationCatagory applicationCatagory, Assessment assessment, WbMConnectionTypeDetail wbMConnectionTypeDetail, WbMDevision wbMDevision, WbMNature wbMNature, WbMReadingProblems wbMReadingProblems, String wbMCustomerId, String wbMAddress, Date wbMFirstConnectionDate, Integer wbMStreet, String wbMMeterNo, Integer wbMOrderId, Integer wbMAssignCustomerOrNot, Integer wbMAssignDivisionOrNot, Integer wbMOfficeId, Integer wbCreateUser, Date wbCreateDate, Date wbCreateTime, Set<WbMChangesRecordTbl> wbMChangesRecordTbls, Set<WbMDataClearTbl> wbMDataClearTbls, Set<WbOpay> wbOpays, Set<WbTPaid> wbTPaids, Set<WbTConnectionHasVote> wbTConnectionHasVotes, Set<WbMMainOwner> wbMMainOwners, Set<WbMNatureChangeTbl> wbMNatureChangeTbls, Set<WbMOverpay> wbMOverpays, Set<WbMReadingProb> wbMReadingProbs, Set<WbTMeterResetDetails> wbTMeterResetDetailses, Set<WbTCusMeterRead> wbTCusMeterReads, Set<WbTPayment> wbTPayments, Set<WbTBillProcess> wbTBillProcesses, Set<WbProcessTbl> wbProcessTbls, Set<WbMSubOwner> wbMSubOwners) {
       this.applicationCatagory = applicationCatagory;
       this.assessment = assessment;
       this.wbMConnectionTypeDetail = wbMConnectionTypeDetail;
       this.wbMDevision = wbMDevision;
       this.wbMNature = wbMNature;
       this.wbMReadingProblems = wbMReadingProblems;
       this.wbMCustomerId = wbMCustomerId;
       this.wbMAddress = wbMAddress;
       this.wbMFirstConnectionDate = wbMFirstConnectionDate;
       this.wbMStreet = wbMStreet;
       this.wbMMeterNo = wbMMeterNo;
       this.wbMOrderId = wbMOrderId;
       this.wbMAssignCustomerOrNot = wbMAssignCustomerOrNot;
       this.wbMAssignDivisionOrNot = wbMAssignDivisionOrNot;
       this.wbMOfficeId = wbMOfficeId;
       this.wbCreateUser = wbCreateUser;
       this.wbCreateDate = wbCreateDate;
       this.wbCreateTime = wbCreateTime;
       this.wbMChangesRecordTbls = wbMChangesRecordTbls;
       this.wbMDataClearTbls = wbMDataClearTbls;
       this.wbOpays = wbOpays;
       this.wbTPaids = wbTPaids;
       this.wbTConnectionHasVotes = wbTConnectionHasVotes;
       this.wbMMainOwners = wbMMainOwners;
       this.wbMNatureChangeTbls = wbMNatureChangeTbls;
       this.wbMOverpays = wbMOverpays;
       this.wbMReadingProbs = wbMReadingProbs;
       this.wbTMeterResetDetailses = wbTMeterResetDetailses;
       this.wbTCusMeterReads = wbTCusMeterReads;
       this.wbTPayments = wbTPayments;
       this.wbTBillProcesses = wbTBillProcesses;
       this.wbProcessTbls = wbProcessTbls;
       this.wbMSubOwners = wbMSubOwners;
    }
   
    public Integer getWbMConnectionId() {
        return this.wbMConnectionId;
    }
    
    public void setWbMConnectionId(Integer wbMConnectionId) {
        this.wbMConnectionId = wbMConnectionId;
    }
    public ApplicationCatagory getApplicationCatagory() {
        return this.applicationCatagory;
    }
    
    public void setApplicationCatagory(ApplicationCatagory applicationCatagory) {
        this.applicationCatagory = applicationCatagory;
    }
    public Assessment getAssessment() {
        return this.assessment;
    }
    
    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }
    public WbMConnectionTypeDetail getWbMConnectionTypeDetail() {
        return this.wbMConnectionTypeDetail;
    }
    
    public void setWbMConnectionTypeDetail(WbMConnectionTypeDetail wbMConnectionTypeDetail) {
        this.wbMConnectionTypeDetail = wbMConnectionTypeDetail;
    }
    public WbMDevision getWbMDevision() {
        return this.wbMDevision;
    }
    
    public void setWbMDevision(WbMDevision wbMDevision) {
        this.wbMDevision = wbMDevision;
    }
    public WbMNature getWbMNature() {
        return this.wbMNature;
    }
    
    public void setWbMNature(WbMNature wbMNature) {
        this.wbMNature = wbMNature;
    }
    public WbMReadingProblems getWbMReadingProblems() {
        return this.wbMReadingProblems;
    }
    
    public void setWbMReadingProblems(WbMReadingProblems wbMReadingProblems) {
        this.wbMReadingProblems = wbMReadingProblems;
    }
    public String getWbMCustomerId() {
        return this.wbMCustomerId;
    }
    
    public void setWbMCustomerId(String wbMCustomerId) {
        this.wbMCustomerId = wbMCustomerId;
    }
    public String getWbMAddress() {
        return this.wbMAddress;
    }
    
    public void setWbMAddress(String wbMAddress) {
        this.wbMAddress = wbMAddress;
    }
    public Date getWbMFirstConnectionDate() {
        return this.wbMFirstConnectionDate;
    }
    
    public void setWbMFirstConnectionDate(Date wbMFirstConnectionDate) {
        this.wbMFirstConnectionDate = wbMFirstConnectionDate;
    }
    public Integer getWbMStreet() {
        return this.wbMStreet;
    }
    
    public void setWbMStreet(Integer wbMStreet) {
        this.wbMStreet = wbMStreet;
    }
    public String getWbMMeterNo() {
        return this.wbMMeterNo;
    }
    
    public void setWbMMeterNo(String wbMMeterNo) {
        this.wbMMeterNo = wbMMeterNo;
    }
    public Integer getWbMOrderId() {
        return this.wbMOrderId;
    }
    
    public void setWbMOrderId(Integer wbMOrderId) {
        this.wbMOrderId = wbMOrderId;
    }
    public Integer getWbMAssignCustomerOrNot() {
        return this.wbMAssignCustomerOrNot;
    }
    
    public void setWbMAssignCustomerOrNot(Integer wbMAssignCustomerOrNot) {
        this.wbMAssignCustomerOrNot = wbMAssignCustomerOrNot;
    }
    public Integer getWbMAssignDivisionOrNot() {
        return this.wbMAssignDivisionOrNot;
    }
    
    public void setWbMAssignDivisionOrNot(Integer wbMAssignDivisionOrNot) {
        this.wbMAssignDivisionOrNot = wbMAssignDivisionOrNot;
    }
    public Integer getWbMOfficeId() {
        return this.wbMOfficeId;
    }
    
    public void setWbMOfficeId(Integer wbMOfficeId) {
        this.wbMOfficeId = wbMOfficeId;
    }
    public Integer getWbCreateUser() {
        return this.wbCreateUser;
    }
    
    public void setWbCreateUser(Integer wbCreateUser) {
        this.wbCreateUser = wbCreateUser;
    }
    public Date getWbCreateDate() {
        return this.wbCreateDate;
    }
    
    public void setWbCreateDate(Date wbCreateDate) {
        this.wbCreateDate = wbCreateDate;
    }
    public Date getWbCreateTime() {
        return this.wbCreateTime;
    }
    
    public void setWbCreateTime(Date wbCreateTime) {
        this.wbCreateTime = wbCreateTime;
    }
    public Set<WbMChangesRecordTbl> getWbMChangesRecordTbls() {
        return this.wbMChangesRecordTbls;
    }
    
    public void setWbMChangesRecordTbls(Set<WbMChangesRecordTbl> wbMChangesRecordTbls) {
        this.wbMChangesRecordTbls = wbMChangesRecordTbls;
    }
    public Set<WbMDataClearTbl> getWbMDataClearTbls() {
        return this.wbMDataClearTbls;
    }
    
    public void setWbMDataClearTbls(Set<WbMDataClearTbl> wbMDataClearTbls) {
        this.wbMDataClearTbls = wbMDataClearTbls;
    }
    public Set<WbOpay> getWbOpays() {
        return this.wbOpays;
    }
    
    public void setWbOpays(Set<WbOpay> wbOpays) {
        this.wbOpays = wbOpays;
    }
    public Set<WbTPaid> getWbTPaids() {
        return this.wbTPaids;
    }
    
    public void setWbTPaids(Set<WbTPaid> wbTPaids) {
        this.wbTPaids = wbTPaids;
    }
    public Set<WbTConnectionHasVote> getWbTConnectionHasVotes() {
        return this.wbTConnectionHasVotes;
    }
    
    public void setWbTConnectionHasVotes(Set<WbTConnectionHasVote> wbTConnectionHasVotes) {
        this.wbTConnectionHasVotes = wbTConnectionHasVotes;
    }
    public Set<WbMMainOwner> getWbMMainOwners() {
        return this.wbMMainOwners;
    }
    
    public void setWbMMainOwners(Set<WbMMainOwner> wbMMainOwners) {
        this.wbMMainOwners = wbMMainOwners;
    }
    public Set<WbMNatureChangeTbl> getWbMNatureChangeTbls() {
        return this.wbMNatureChangeTbls;
    }
    
    public void setWbMNatureChangeTbls(Set<WbMNatureChangeTbl> wbMNatureChangeTbls) {
        this.wbMNatureChangeTbls = wbMNatureChangeTbls;
    }
    public Set<WbMOverpay> getWbMOverpays() {
        return this.wbMOverpays;
    }
    
    public void setWbMOverpays(Set<WbMOverpay> wbMOverpays) {
        this.wbMOverpays = wbMOverpays;
    }
    public Set<WbMReadingProb> getWbMReadingProbs() {
        return this.wbMReadingProbs;
    }
    
    public void setWbMReadingProbs(Set<WbMReadingProb> wbMReadingProbs) {
        this.wbMReadingProbs = wbMReadingProbs;
    }
    public Set<WbTMeterResetDetails> getWbTMeterResetDetailses() {
        return this.wbTMeterResetDetailses;
    }
    
    public void setWbTMeterResetDetailses(Set<WbTMeterResetDetails> wbTMeterResetDetailses) {
        this.wbTMeterResetDetailses = wbTMeterResetDetailses;
    }
    public Set<WbTCusMeterRead> getWbTCusMeterReads() {
        return this.wbTCusMeterReads;
    }
    
    public void setWbTCusMeterReads(Set<WbTCusMeterRead> wbTCusMeterReads) {
        this.wbTCusMeterReads = wbTCusMeterReads;
    }
    public Set<WbTPayment> getWbTPayments() {
        return this.wbTPayments;
    }
    
    public void setWbTPayments(Set<WbTPayment> wbTPayments) {
        this.wbTPayments = wbTPayments;
    }
    public Set<WbTBillProcess> getWbTBillProcesses() {
        return this.wbTBillProcesses;
    }
    
    public void setWbTBillProcesses(Set<WbTBillProcess> wbTBillProcesses) {
        this.wbTBillProcesses = wbTBillProcesses;
    }
    public Set<WbProcessTbl> getWbProcessTbls() {
        return this.wbProcessTbls;
    }
    
    public void setWbProcessTbls(Set<WbProcessTbl> wbProcessTbls) {
        this.wbProcessTbls = wbProcessTbls;
    }
    public Set<WbMSubOwner> getWbMSubOwners() {
        return this.wbMSubOwners;
    }
    
    public void setWbMSubOwners(Set<WbMSubOwner> wbMSubOwners) {
        this.wbMSubOwners = wbMSubOwners;
    }




}


