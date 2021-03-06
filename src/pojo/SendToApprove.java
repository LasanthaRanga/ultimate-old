package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SendToApprove generated by hbm2java
 */
public class SendToApprove  implements java.io.Serializable {


     private Integer idSendToApprove;
     private ApplicationCatagory applicationCatagory;
     private Applications applications;
     private ApprovalBy approvalBy;
     private User userBySendToUser;
     private User userBySendByUser;
     private Integer statusApprove;
     private Date sendDateTime;
     private String sendToApprovecol;
     private Integer applicationId;
     private Integer statusPara;
     private Date doneDnt;
     private Set<ApproveDetails> approveDetailses = new HashSet<ApproveDetails>(0);

    public SendToApprove() {
    }

	
    public SendToApprove(ApplicationCatagory applicationCatagory, ApprovalBy approvalBy) {
        this.applicationCatagory = applicationCatagory;
        this.approvalBy = approvalBy;
    }
    public SendToApprove(ApplicationCatagory applicationCatagory, Applications applications, ApprovalBy approvalBy, User userBySendToUser, User userBySendByUser, Integer statusApprove, Date sendDateTime, String sendToApprovecol, Integer applicationId, Integer statusPara, Date doneDnt, Set<ApproveDetails> approveDetailses) {
       this.applicationCatagory = applicationCatagory;
       this.applications = applications;
       this.approvalBy = approvalBy;
       this.userBySendToUser = userBySendToUser;
       this.userBySendByUser = userBySendByUser;
       this.statusApprove = statusApprove;
       this.sendDateTime = sendDateTime;
       this.sendToApprovecol = sendToApprovecol;
       this.applicationId = applicationId;
       this.statusPara = statusPara;
       this.doneDnt = doneDnt;
       this.approveDetailses = approveDetailses;
    }
   
    public Integer getIdSendToApprove() {
        return this.idSendToApprove;
    }
    
    public void setIdSendToApprove(Integer idSendToApprove) {
        this.idSendToApprove = idSendToApprove;
    }
    public ApplicationCatagory getApplicationCatagory() {
        return this.applicationCatagory;
    }
    
    public void setApplicationCatagory(ApplicationCatagory applicationCatagory) {
        this.applicationCatagory = applicationCatagory;
    }
    public Applications getApplications() {
        return this.applications;
    }
    
    public void setApplications(Applications applications) {
        this.applications = applications;
    }
    public ApprovalBy getApprovalBy() {
        return this.approvalBy;
    }
    
    public void setApprovalBy(ApprovalBy approvalBy) {
        this.approvalBy = approvalBy;
    }
    public User getUserBySendToUser() {
        return this.userBySendToUser;
    }
    
    public void setUserBySendToUser(User userBySendToUser) {
        this.userBySendToUser = userBySendToUser;
    }
    public User getUserBySendByUser() {
        return this.userBySendByUser;
    }
    
    public void setUserBySendByUser(User userBySendByUser) {
        this.userBySendByUser = userBySendByUser;
    }
    public Integer getStatusApprove() {
        return this.statusApprove;
    }
    
    public void setStatusApprove(Integer statusApprove) {
        this.statusApprove = statusApprove;
    }
    public Date getSendDateTime() {
        return this.sendDateTime;
    }
    
    public void setSendDateTime(Date sendDateTime) {
        this.sendDateTime = sendDateTime;
    }
    public String getSendToApprovecol() {
        return this.sendToApprovecol;
    }
    
    public void setSendToApprovecol(String sendToApprovecol) {
        this.sendToApprovecol = sendToApprovecol;
    }
    public Integer getApplicationId() {
        return this.applicationId;
    }
    
    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }
    public Integer getStatusPara() {
        return this.statusPara;
    }
    
    public void setStatusPara(Integer statusPara) {
        this.statusPara = statusPara;
    }
    public Date getDoneDnt() {
        return this.doneDnt;
    }
    
    public void setDoneDnt(Date doneDnt) {
        this.doneDnt = doneDnt;
    }
    public Set<ApproveDetails> getApproveDetailses() {
        return this.approveDetailses;
    }
    
    public void setApproveDetailses(Set<ApproveDetails> approveDetailses) {
        this.approveDetailses = approveDetailses;
    }




}


