package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User generated by hbm2java
 */
public class User  implements java.io.Serializable {


     private Integer idUser;
     private UserLevel userLevel;
     private String userFullName;
     private String userNic;
     private Date userDate;
     private Date userBirthDay;
     private Integer userStatus;
     private Integer userSyn;
     private String userQuestion;
     private String userAnswer;
     private String userUsername;
     private String userPassword;
     private Integer officeIdOffice;
     private String userNameSinhala;
     private String mobileNo;
     private Set<PrivilageCopy> privilageCopies = new HashSet<PrivilageCopy>(0);
     private Set<UserHasApprovalCat> userHasApprovalCats = new HashSet<UserHasApprovalCat>(0);
     private Set<WbMChangesRecordTbl> wbMChangesRecordTbls = new HashSet<WbMChangesRecordTbl>(0);
     private Set<WbTPaid> wbTPaids = new HashSet<WbTPaid>(0);
     private Set<Apprualstatues> apprualstatueses = new HashSet<Apprualstatues>(0);
     private Set<UserHasPrivilage> userHasPrivilages = new HashSet<UserHasPrivilage>(0);
     private Set<Application> applications = new HashSet<Application>(0);
     private Set<UserHasDipartment> userHasDipartments = new HashSet<UserHasDipartment>(0);
     private Set<AssessmentHasProcesstype> assessmentHasProcesstypes = new HashSet<AssessmentHasProcesstype>(0);
     private Set<SendToApprove> sendToApprovesForSendToUser = new HashSet<SendToApprove>(0);
     private Set<SendToApprove> sendToApprovesForSendByUser = new HashSet<SendToApprove>(0);
     private Set<TlApp> tlApps = new HashSet<TlApp>(0);
     private Set<Assessment> assessments = new HashSet<Assessment>(0);
     private Set<Userhasdivition> userhasdivitions = new HashSet<Userhasdivition>(0);
     private Set<Commetteemembers> commetteememberses = new HashSet<Commetteemembers>(0);
     private Set<UserLogin> userLogins = new HashSet<UserLogin>(0);
     private Set<WbMNatureChangeTbl> wbMNatureChangeTbls = new HashSet<WbMNatureChangeTbl>(0);
     private Set<UserLog> userLogs = new HashSet<UserLog>(0);
     private Set<UserHasApplicationCatagory> userHasApplicationCatagories = new HashSet<UserHasApplicationCatagory>(0);
     private Set<UserHasDepartment> userHasDepartments = new HashSet<UserHasDepartment>(0);
     private Set<Receipt> receipts = new HashSet<Receipt>(0);
     private Set<UserHasOtheritiscat> userHasOtheritiscats = new HashSet<UserHasOtheritiscat>(0);
     private Set<WbMDivisionHasMeterReader> wbMDivisionHasMeterReaders = new HashSet<WbMDivisionHasMeterReader>(0);
     private Set<UserHasCatagory> userHasCatagories = new HashSet<UserHasCatagory>(0);

    public User() {
    }

    public User(UserLevel userLevel, String userFullName, String userNic, Date userDate, Date userBirthDay, Integer userStatus, Integer userSyn, String userQuestion, String userAnswer, String userUsername, String userPassword, Integer officeIdOffice, String userNameSinhala, String mobileNo, Set<PrivilageCopy> privilageCopies, Set<UserHasApprovalCat> userHasApprovalCats, Set<WbMChangesRecordTbl> wbMChangesRecordTbls, Set<WbTPaid> wbTPaids, Set<Apprualstatues> apprualstatueses, Set<UserHasPrivilage> userHasPrivilages, Set<Application> applications, Set<UserHasDipartment> userHasDipartments, Set<AssessmentHasProcesstype> assessmentHasProcesstypes, Set<SendToApprove> sendToApprovesForSendToUser, Set<SendToApprove> sendToApprovesForSendByUser, Set<TlApp> tlApps, Set<Assessment> assessments, Set<Userhasdivition> userhasdivitions, Set<Commetteemembers> commetteememberses, Set<UserLogin> userLogins, Set<WbMNatureChangeTbl> wbMNatureChangeTbls, Set<UserLog> userLogs, Set<UserHasApplicationCatagory> userHasApplicationCatagories, Set<UserHasDepartment> userHasDepartments, Set<Receipt> receipts, Set<UserHasOtheritiscat> userHasOtheritiscats, Set<WbMDivisionHasMeterReader> wbMDivisionHasMeterReaders, Set<UserHasCatagory> userHasCatagories) {
       this.userLevel = userLevel;
       this.userFullName = userFullName;
       this.userNic = userNic;
       this.userDate = userDate;
       this.userBirthDay = userBirthDay;
       this.userStatus = userStatus;
       this.userSyn = userSyn;
       this.userQuestion = userQuestion;
       this.userAnswer = userAnswer;
       this.userUsername = userUsername;
       this.userPassword = userPassword;
       this.officeIdOffice = officeIdOffice;
       this.userNameSinhala = userNameSinhala;
       this.mobileNo = mobileNo;
       this.privilageCopies = privilageCopies;
       this.userHasApprovalCats = userHasApprovalCats;
       this.wbMChangesRecordTbls = wbMChangesRecordTbls;
       this.wbTPaids = wbTPaids;
       this.apprualstatueses = apprualstatueses;
       this.userHasPrivilages = userHasPrivilages;
       this.applications = applications;
       this.userHasDipartments = userHasDipartments;
       this.assessmentHasProcesstypes = assessmentHasProcesstypes;
       this.sendToApprovesForSendToUser = sendToApprovesForSendToUser;
       this.sendToApprovesForSendByUser = sendToApprovesForSendByUser;
       this.tlApps = tlApps;
       this.assessments = assessments;
       this.userhasdivitions = userhasdivitions;
       this.commetteememberses = commetteememberses;
       this.userLogins = userLogins;
       this.wbMNatureChangeTbls = wbMNatureChangeTbls;
       this.userLogs = userLogs;
       this.userHasApplicationCatagories = userHasApplicationCatagories;
       this.userHasDepartments = userHasDepartments;
       this.receipts = receipts;
       this.userHasOtheritiscats = userHasOtheritiscats;
       this.wbMDivisionHasMeterReaders = wbMDivisionHasMeterReaders;
       this.userHasCatagories = userHasCatagories;
    }
   
    public Integer getIdUser() {
        return this.idUser;
    }
    
    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
    public UserLevel getUserLevel() {
        return this.userLevel;
    }
    
    public void setUserLevel(UserLevel userLevel) {
        this.userLevel = userLevel;
    }
    public String getUserFullName() {
        return this.userFullName;
    }
    
    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }
    public String getUserNic() {
        return this.userNic;
    }
    
    public void setUserNic(String userNic) {
        this.userNic = userNic;
    }
    public Date getUserDate() {
        return this.userDate;
    }
    
    public void setUserDate(Date userDate) {
        this.userDate = userDate;
    }
    public Date getUserBirthDay() {
        return this.userBirthDay;
    }
    
    public void setUserBirthDay(Date userBirthDay) {
        this.userBirthDay = userBirthDay;
    }
    public Integer getUserStatus() {
        return this.userStatus;
    }
    
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
    public Integer getUserSyn() {
        return this.userSyn;
    }
    
    public void setUserSyn(Integer userSyn) {
        this.userSyn = userSyn;
    }
    public String getUserQuestion() {
        return this.userQuestion;
    }
    
    public void setUserQuestion(String userQuestion) {
        this.userQuestion = userQuestion;
    }
    public String getUserAnswer() {
        return this.userAnswer;
    }
    
    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
    public String getUserUsername() {
        return this.userUsername;
    }
    
    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }
    public String getUserPassword() {
        return this.userPassword;
    }
    
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public Integer getOfficeIdOffice() {
        return this.officeIdOffice;
    }
    
    public void setOfficeIdOffice(Integer officeIdOffice) {
        this.officeIdOffice = officeIdOffice;
    }
    public String getUserNameSinhala() {
        return this.userNameSinhala;
    }
    
    public void setUserNameSinhala(String userNameSinhala) {
        this.userNameSinhala = userNameSinhala;
    }
    public String getMobileNo() {
        return this.mobileNo;
    }
    
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public Set<PrivilageCopy> getPrivilageCopies() {
        return this.privilageCopies;
    }
    
    public void setPrivilageCopies(Set<PrivilageCopy> privilageCopies) {
        this.privilageCopies = privilageCopies;
    }
    public Set<UserHasApprovalCat> getUserHasApprovalCats() {
        return this.userHasApprovalCats;
    }
    
    public void setUserHasApprovalCats(Set<UserHasApprovalCat> userHasApprovalCats) {
        this.userHasApprovalCats = userHasApprovalCats;
    }
    public Set<WbMChangesRecordTbl> getWbMChangesRecordTbls() {
        return this.wbMChangesRecordTbls;
    }
    
    public void setWbMChangesRecordTbls(Set<WbMChangesRecordTbl> wbMChangesRecordTbls) {
        this.wbMChangesRecordTbls = wbMChangesRecordTbls;
    }
    public Set<WbTPaid> getWbTPaids() {
        return this.wbTPaids;
    }
    
    public void setWbTPaids(Set<WbTPaid> wbTPaids) {
        this.wbTPaids = wbTPaids;
    }
    public Set<Apprualstatues> getApprualstatueses() {
        return this.apprualstatueses;
    }
    
    public void setApprualstatueses(Set<Apprualstatues> apprualstatueses) {
        this.apprualstatueses = apprualstatueses;
    }
    public Set<UserHasPrivilage> getUserHasPrivilages() {
        return this.userHasPrivilages;
    }
    
    public void setUserHasPrivilages(Set<UserHasPrivilage> userHasPrivilages) {
        this.userHasPrivilages = userHasPrivilages;
    }
    public Set<Application> getApplications() {
        return this.applications;
    }
    
    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }
    public Set<UserHasDipartment> getUserHasDipartments() {
        return this.userHasDipartments;
    }
    
    public void setUserHasDipartments(Set<UserHasDipartment> userHasDipartments) {
        this.userHasDipartments = userHasDipartments;
    }
    public Set<AssessmentHasProcesstype> getAssessmentHasProcesstypes() {
        return this.assessmentHasProcesstypes;
    }
    
    public void setAssessmentHasProcesstypes(Set<AssessmentHasProcesstype> assessmentHasProcesstypes) {
        this.assessmentHasProcesstypes = assessmentHasProcesstypes;
    }
    public Set<SendToApprove> getSendToApprovesForSendToUser() {
        return this.sendToApprovesForSendToUser;
    }
    
    public void setSendToApprovesForSendToUser(Set<SendToApprove> sendToApprovesForSendToUser) {
        this.sendToApprovesForSendToUser = sendToApprovesForSendToUser;
    }
    public Set<SendToApprove> getSendToApprovesForSendByUser() {
        return this.sendToApprovesForSendByUser;
    }
    
    public void setSendToApprovesForSendByUser(Set<SendToApprove> sendToApprovesForSendByUser) {
        this.sendToApprovesForSendByUser = sendToApprovesForSendByUser;
    }
    public Set<TlApp> getTlApps() {
        return this.tlApps;
    }
    
    public void setTlApps(Set<TlApp> tlApps) {
        this.tlApps = tlApps;
    }
    public Set<Assessment> getAssessments() {
        return this.assessments;
    }
    
    public void setAssessments(Set<Assessment> assessments) {
        this.assessments = assessments;
    }
    public Set<Userhasdivition> getUserhasdivitions() {
        return this.userhasdivitions;
    }
    
    public void setUserhasdivitions(Set<Userhasdivition> userhasdivitions) {
        this.userhasdivitions = userhasdivitions;
    }
    public Set<Commetteemembers> getCommetteememberses() {
        return this.commetteememberses;
    }
    
    public void setCommetteememberses(Set<Commetteemembers> commetteememberses) {
        this.commetteememberses = commetteememberses;
    }
    public Set<UserLogin> getUserLogins() {
        return this.userLogins;
    }
    
    public void setUserLogins(Set<UserLogin> userLogins) {
        this.userLogins = userLogins;
    }
    public Set<WbMNatureChangeTbl> getWbMNatureChangeTbls() {
        return this.wbMNatureChangeTbls;
    }
    
    public void setWbMNatureChangeTbls(Set<WbMNatureChangeTbl> wbMNatureChangeTbls) {
        this.wbMNatureChangeTbls = wbMNatureChangeTbls;
    }
    public Set<UserLog> getUserLogs() {
        return this.userLogs;
    }
    
    public void setUserLogs(Set<UserLog> userLogs) {
        this.userLogs = userLogs;
    }
    public Set<UserHasApplicationCatagory> getUserHasApplicationCatagories() {
        return this.userHasApplicationCatagories;
    }
    
    public void setUserHasApplicationCatagories(Set<UserHasApplicationCatagory> userHasApplicationCatagories) {
        this.userHasApplicationCatagories = userHasApplicationCatagories;
    }
    public Set<UserHasDepartment> getUserHasDepartments() {
        return this.userHasDepartments;
    }
    
    public void setUserHasDepartments(Set<UserHasDepartment> userHasDepartments) {
        this.userHasDepartments = userHasDepartments;
    }
    public Set<Receipt> getReceipts() {
        return this.receipts;
    }
    
    public void setReceipts(Set<Receipt> receipts) {
        this.receipts = receipts;
    }
    public Set<UserHasOtheritiscat> getUserHasOtheritiscats() {
        return this.userHasOtheritiscats;
    }
    
    public void setUserHasOtheritiscats(Set<UserHasOtheritiscat> userHasOtheritiscats) {
        this.userHasOtheritiscats = userHasOtheritiscats;
    }
    public Set<WbMDivisionHasMeterReader> getWbMDivisionHasMeterReaders() {
        return this.wbMDivisionHasMeterReaders;
    }
    
    public void setWbMDivisionHasMeterReaders(Set<WbMDivisionHasMeterReader> wbMDivisionHasMeterReaders) {
        this.wbMDivisionHasMeterReaders = wbMDivisionHasMeterReaders;
    }
    public Set<UserHasCatagory> getUserHasCatagories() {
        return this.userHasCatagories;
    }
    
    public void setUserHasCatagories(Set<UserHasCatagory> userHasCatagories) {
        this.userHasCatagories = userHasCatagories;
    }




}


