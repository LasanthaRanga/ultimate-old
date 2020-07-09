package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * AtachmantEnv generated by hbm2java
 */
public class AtachmantEnv  implements java.io.Serializable {


     private Integer idAtachmantEnv;
     private Environment environment;
     private String atachType;
     private String nameAtach;
     private Date atachDate;
     private String atachPath;

    public AtachmantEnv() {
    }

	
    public AtachmantEnv(Environment environment) {
        this.environment = environment;
    }
    public AtachmantEnv(Environment environment, String atachType, String nameAtach, Date atachDate, String atachPath) {
       this.environment = environment;
       this.atachType = atachType;
       this.nameAtach = nameAtach;
       this.atachDate = atachDate;
       this.atachPath = atachPath;
    }
   
    public Integer getIdAtachmantEnv() {
        return this.idAtachmantEnv;
    }
    
    public void setIdAtachmantEnv(Integer idAtachmantEnv) {
        this.idAtachmantEnv = idAtachmantEnv;
    }
    public Environment getEnvironment() {
        return this.environment;
    }
    
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
    public String getAtachType() {
        return this.atachType;
    }
    
    public void setAtachType(String atachType) {
        this.atachType = atachType;
    }
    public String getNameAtach() {
        return this.nameAtach;
    }
    
    public void setNameAtach(String nameAtach) {
        this.nameAtach = nameAtach;
    }
    public Date getAtachDate() {
        return this.atachDate;
    }
    
    public void setAtachDate(Date atachDate) {
        this.atachDate = atachDate;
    }
    public String getAtachPath() {
        return this.atachPath;
    }
    
    public void setAtachPath(String atachPath) {
        this.atachPath = atachPath;
    }




}


