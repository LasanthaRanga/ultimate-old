package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * WebJobtype generated by hbm2java
 */
public class WebJobtype  implements java.io.Serializable {


     private Integer idWebJobtype;
     private String jobtypeName;
     private Integer jobtypeStatus;
     private Integer jobcat;

    public WebJobtype() {
    }

    public WebJobtype(String jobtypeName, Integer jobtypeStatus, Integer jobcat) {
       this.jobtypeName = jobtypeName;
       this.jobtypeStatus = jobtypeStatus;
       this.jobcat = jobcat;
    }
   
    public Integer getIdWebJobtype() {
        return this.idWebJobtype;
    }
    
    public void setIdWebJobtype(Integer idWebJobtype) {
        this.idWebJobtype = idWebJobtype;
    }
    public String getJobtypeName() {
        return this.jobtypeName;
    }
    
    public void setJobtypeName(String jobtypeName) {
        this.jobtypeName = jobtypeName;
    }
    public Integer getJobtypeStatus() {
        return this.jobtypeStatus;
    }
    
    public void setJobtypeStatus(Integer jobtypeStatus) {
        this.jobtypeStatus = jobtypeStatus;
    }
    public Integer getJobcat() {
        return this.jobcat;
    }
    
    public void setJobcat(Integer jobcat) {
        this.jobcat = jobcat;
    }




}

