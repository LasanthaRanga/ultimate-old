package pojo;
// Generated Sep 6, 2019 4:30:16 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Environment generated by hbm2java
 */
public class Environment  implements java.io.Serializable {


     private Integer idEnvironment;
     private Customer customer;
     private String appNoEnv;
     private String addressEnv;
     private Date dateEnv;
     private String contactEnv;
     private String discription;
     private Integer statusEnv;
     private String synEnv;
     private Set<AtachmantEnv> atachmantEnvs = new HashSet<AtachmantEnv>(0);

    public Environment() {
    }

	
    public Environment(Customer customer) {
        this.customer = customer;
    }
    public Environment(Customer customer, String appNoEnv, String addressEnv, Date dateEnv, String contactEnv, String discription, Integer statusEnv, String synEnv, Set<AtachmantEnv> atachmantEnvs) {
       this.customer = customer;
       this.appNoEnv = appNoEnv;
       this.addressEnv = addressEnv;
       this.dateEnv = dateEnv;
       this.contactEnv = contactEnv;
       this.discription = discription;
       this.statusEnv = statusEnv;
       this.synEnv = synEnv;
       this.atachmantEnvs = atachmantEnvs;
    }
   
    public Integer getIdEnvironment() {
        return this.idEnvironment;
    }
    
    public void setIdEnvironment(Integer idEnvironment) {
        this.idEnvironment = idEnvironment;
    }
    public Customer getCustomer() {
        return this.customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public String getAppNoEnv() {
        return this.appNoEnv;
    }
    
    public void setAppNoEnv(String appNoEnv) {
        this.appNoEnv = appNoEnv;
    }
    public String getAddressEnv() {
        return this.addressEnv;
    }
    
    public void setAddressEnv(String addressEnv) {
        this.addressEnv = addressEnv;
    }
    public Date getDateEnv() {
        return this.dateEnv;
    }
    
    public void setDateEnv(Date dateEnv) {
        this.dateEnv = dateEnv;
    }
    public String getContactEnv() {
        return this.contactEnv;
    }
    
    public void setContactEnv(String contactEnv) {
        this.contactEnv = contactEnv;
    }
    public String getDiscription() {
        return this.discription;
    }
    
    public void setDiscription(String discription) {
        this.discription = discription;
    }
    public Integer getStatusEnv() {
        return this.statusEnv;
    }
    
    public void setStatusEnv(Integer statusEnv) {
        this.statusEnv = statusEnv;
    }
    public String getSynEnv() {
        return this.synEnv;
    }
    
    public void setSynEnv(String synEnv) {
        this.synEnv = synEnv;
    }
    public Set<AtachmantEnv> getAtachmantEnvs() {
        return this.atachmantEnvs;
    }
    
    public void setAtachmantEnvs(Set<AtachmantEnv> atachmantEnvs) {
        this.atachmantEnvs = atachmantEnvs;
    }




}


