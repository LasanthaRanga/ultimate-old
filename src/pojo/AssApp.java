package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * AssApp generated by hbm2java
 */
public class AssApp  implements java.io.Serializable {


     private Integer idAssapp;
     private Date assappDate;
     private Integer assappUser;
     private Integer assappStep;
     private String assappDescription;
     private Integer assappStatus;
     private String assappType;
     private String assappRefno;
     private String assappNames;

    public AssApp() {
    }

    public AssApp(Date assappDate, Integer assappUser, Integer assappStep, String assappDescription, Integer assappStatus, String assappType, String assappRefno, String assappNames) {
       this.assappDate = assappDate;
       this.assappUser = assappUser;
       this.assappStep = assappStep;
       this.assappDescription = assappDescription;
       this.assappStatus = assappStatus;
       this.assappType = assappType;
       this.assappRefno = assappRefno;
       this.assappNames = assappNames;
    }
   
    public Integer getIdAssapp() {
        return this.idAssapp;
    }
    
    public void setIdAssapp(Integer idAssapp) {
        this.idAssapp = idAssapp;
    }
    public Date getAssappDate() {
        return this.assappDate;
    }
    
    public void setAssappDate(Date assappDate) {
        this.assappDate = assappDate;
    }
    public Integer getAssappUser() {
        return this.assappUser;
    }
    
    public void setAssappUser(Integer assappUser) {
        this.assappUser = assappUser;
    }
    public Integer getAssappStep() {
        return this.assappStep;
    }
    
    public void setAssappStep(Integer assappStep) {
        this.assappStep = assappStep;
    }
    public String getAssappDescription() {
        return this.assappDescription;
    }
    
    public void setAssappDescription(String assappDescription) {
        this.assappDescription = assappDescription;
    }
    public Integer getAssappStatus() {
        return this.assappStatus;
    }
    
    public void setAssappStatus(Integer assappStatus) {
        this.assappStatus = assappStatus;
    }
    public String getAssappType() {
        return this.assappType;
    }
    
    public void setAssappType(String assappType) {
        this.assappType = assappType;
    }
    public String getAssappRefno() {
        return this.assappRefno;
    }
    
    public void setAssappRefno(String assappRefno) {
        this.assappRefno = assappRefno;
    }
    public String getAssappNames() {
        return this.assappNames;
    }
    
    public void setAssappNames(String assappNames) {
        this.assappNames = assappNames;
    }




}


