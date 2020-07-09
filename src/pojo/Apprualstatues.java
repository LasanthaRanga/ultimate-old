package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Apprualstatues generated by hbm2java
 */
public class Apprualstatues  implements java.io.Serializable {


     private Integer idApprualStatues;
     private Application application;
     private User user;
     private String description;
     private Date sendDate;
     private Date approveDate;
     private Integer idOtheritisCat;
     private Integer statues;
     private Integer resiveUser;
     private Integer syn;

    public Apprualstatues() {
    }

	
    public Apprualstatues(Application application, User user) {
        this.application = application;
        this.user = user;
    }
    public Apprualstatues(Application application, User user, String description, Date sendDate, Date approveDate, Integer idOtheritisCat, Integer statues, Integer resiveUser, Integer syn) {
       this.application = application;
       this.user = user;
       this.description = description;
       this.sendDate = sendDate;
       this.approveDate = approveDate;
       this.idOtheritisCat = idOtheritisCat;
       this.statues = statues;
       this.resiveUser = resiveUser;
       this.syn = syn;
    }
   
    public Integer getIdApprualStatues() {
        return this.idApprualStatues;
    }
    
    public void setIdApprualStatues(Integer idApprualStatues) {
        this.idApprualStatues = idApprualStatues;
    }
    public Application getApplication() {
        return this.application;
    }
    
    public void setApplication(Application application) {
        this.application = application;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getSendDate() {
        return this.sendDate;
    }
    
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
    public Date getApproveDate() {
        return this.approveDate;
    }
    
    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }
    public Integer getIdOtheritisCat() {
        return this.idOtheritisCat;
    }
    
    public void setIdOtheritisCat(Integer idOtheritisCat) {
        this.idOtheritisCat = idOtheritisCat;
    }
    public Integer getStatues() {
        return this.statues;
    }
    
    public void setStatues(Integer statues) {
        this.statues = statues;
    }
    public Integer getResiveUser() {
        return this.resiveUser;
    }
    
    public void setResiveUser(Integer resiveUser) {
        this.resiveUser = resiveUser;
    }
    public Integer getSyn() {
        return this.syn;
    }
    
    public void setSyn(Integer syn) {
        this.syn = syn;
    }




}


